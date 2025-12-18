package jobs4u.base.applicationmanagement.domain;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

/**
 * A class to perform word count analysis on a set of files.
 * It uses multiple threads to efficiently process large files.
 */
public class WordAnalysis {

    /**
     * A map to store word counts, where the key is the file path
     * and the value is another map containing word counts.
     */
    private HashMap<String, HashMap<String, Integer>> wordCounts = new HashMap<>();

    /**
     * A list to keep track of the threads used for processing.
     */
    private List<Thread> threads = new ArrayList<>();

    /**
     * The threshold size (in bytes) to determine the size of chunks for processing.
     */
    private int BYTE_THRESHOLD = 2000000;

    /**
     * The number of available threads.
     */
    private int AVAILABLE_THREADS = 10;

    /**
     * The maximum number of threads that can be used for processing a single file.
     */
    private final int MAX_THREADS_PER_FILE = 4;

    /**
     * Counts words in the specified set of file paths.
     *
     * @param filePaths the set of file paths to process
     * @return a list of WordInfo objects containing the word counts and the files they appear in
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if a thread is interrupted
     */
    public List<WordInfo> countWords(Set<String> filePaths) throws IOException, InterruptedException {

        instantiateMap(filePaths);

        for (String path : filePaths) {
            File check = new File(path);
            int length = (int) check.length();

            int numberOfThreads = (int) Math.ceil((double) length / BYTE_THRESHOLD);

            if (numberOfThreads > MAX_THREADS_PER_FILE) {
                numberOfThreads = MAX_THREADS_PER_FILE;
            }

            if (numberOfThreads > AVAILABLE_THREADS) {
                int threadsNeeded = numberOfThreads - AVAILABLE_THREADS;
                for (int i = 0; i < threadsNeeded; i++) {
                    threads.get(i).join();
                    AVAILABLE_THREADS++;
                }
            }

            if (numberOfThreads == 1) {
                WordCountThread wordThread = new WordCountThread(path, 0, length, wordCounts);
                Thread thread = new Thread(wordThread);
                thread.start();
                threads.add(thread);
                AVAILABLE_THREADS--;

            } else {
                int begin = 0;
                try (RandomAccessFile fileR = new RandomAccessFile(check, "r")) {
                    for (int i = 0; i < numberOfThreads; i++) {
                        int end = begin + Math.round(check.length() / numberOfThreads);
                        if (end > length) end = length;

                        // Adjust end to the nearest delimiter
                        fileR.seek(end);
                        while (end < length && !isDelimiter(fileR.readByte())) {
                            end--;
                            fileR.seek(end);
                        }
                        WordCountThread wordThread = new WordCountThread(path, begin, end, wordCounts);
                        Thread thread = new Thread(wordThread);
                        thread.start();
                        threads.add(thread);
                        begin = end;
                    }
                }

                AVAILABLE_THREADS -= numberOfThreads;
            }
        }

        for (Thread t : threads) {
            t.join();
        }

        Set<WordInfo> wordCompilation = getMostUsedWords();
        List<WordInfo> words = new ArrayList<>();

        for (WordInfo word : wordCompilation) {
            words.add(word);
        }

        return words;
    }

    /**
     * Initializes the word count map for each file path.
     *
     * @param filePaths the set of file paths to initialize
     */
    private void instantiateMap(Set<String> filePaths) {
        for (String file : filePaths) {
            wordCounts.put(file, new HashMap<>());
        }
    }

    /**
     * Checks if a byte represents a delimiter character.
     *
     * @param b the byte to check
     * @return true if the byte is a delimiter, false otherwise
     */
    private boolean isDelimiter(byte b) {
        return b == ' ' || b == '?' || b == ':' || b == '@' || b == '!' || b == ',' || b == '.' || b == '\n' || b == '-' || b == '(' || b == ')' || b == '{' || b == '}' || b == ';' || b == '*' || b == '[' || b == ']' || Character.isWhitespace(b);
    }

    /**
     * Aggregates the word counts from all files and returns a set of WordInfo objects.
     *
     * @return a set of WordInfo objects containing the word counts and the files they appear in
     */
    public Set<WordInfo> getMostUsedWords() {
        Map<String, Integer> aggregateWordCount = new TreeMap<>();
        Set<WordInfo> analysisResult = new TreeSet<>();

        for (String file : wordCounts.keySet()) {
            for (String word : wordCounts.get(file).keySet()) {
                aggregateWordCount.merge(word, wordCounts.get(file).get(word), Integer::sum);
            }
        }

        for (String entry : aggregateWordCount.keySet()) {
            Set<String> filesContain = new HashSet<>();
            for (String s : wordCounts.keySet()) {
                if (wordCounts.get(s).containsKey(entry)) {
                    filesContain.add(s);
                }
            }
            analysisResult.add(new WordInfo(entry, aggregateWordCount.get(entry), filesContain));
        }
        return analysisResult;
    }

    /**
     * A class to represent information about a word, including its count and the files it appears in.
     */
    public static class WordInfo implements Comparable<WordInfo> {
        private int count;
        private Set<String> files;
        private String word;

        /**
         * Constructs a WordInfo object.
         *
         * @param word  the word
         * @param count the count of the word
         * @param files the set of files the word appears in
         */
        public WordInfo(String word, int count, Set<String> files) {
            this.word = word;
            this.count = count;
            this.files = files;
        }

        /**
         * Gets the count of the word.
         *
         * @return the count of the word
         */
        public int getCount() {
            return count;
        }

        /**
         * Gets the set of files the word appears in.
         *
         * @return the set of files
         */
        public Set<String> getFiles() {
            return files;
        }

        /**
         * Gets the word.
         *
         * @return the word
         */
        public String getWord() {
            return word;
        }

        /**
         * Compares this WordInfo object with another based on the word count.
         *
         * @param o the other WordInfo object to compare to
         * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
         */
        @Override
        public int compareTo(WordInfo o) {
            return Integer.compare(o.getCount(), this.getCount());
        }
    }

    /**
     * Sets the byte threshold for splitting file processing.
     *
     * @param number the new byte threshold
     */
    public void setByteThreshold(int number) {
        this.BYTE_THRESHOLD = number;
    }
}
