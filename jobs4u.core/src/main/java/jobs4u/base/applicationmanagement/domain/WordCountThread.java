package jobs4u.base.applicationmanagement.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A Runnable class that counts words in a specified section of a file.
 * The word counts are accumulated in a shared result map.
 */
public class WordCountThread implements Runnable {

    /**
     * A map to store the word count results, where the key is the file path
     * and the value is another map containing word counts.
     */
    private HashMap<String, HashMap<String, Integer>> resultMap;

    /**
     * The path of the file to be processed.
     */
    private String filePath;

    /**
     * The starting byte position of the section to be processed.
     */
    private int begin;

    /**
     * The ending byte position of the section to be processed.
     */
    private int end;

    /**
     * Constructs a WordCountThread to process a specific section of a file.
     *
     * @param filePath  the path of the file to be processed
     * @param begin     the starting byte position of the section
     * @param end       the ending byte position of the section
     * @param resultMap the shared result map to accumulate word counts
     */
    public WordCountThread(String filePath, int begin, int end, HashMap<String, HashMap<String, Integer>> resultMap) {
        this.filePath = filePath;
        this.begin = begin;
        this.end = end;
        this.resultMap = resultMap;
    }

    /**
     * Reads the specified section of the file, counts the occurrences of each word,
     * and updates the shared result map with the word counts.
     */
    @Override
    public void run() {
        Map<String, Integer> wordCount = new HashMap<>();

        File file = new File(filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.skip(begin);

            StringBuilder content = new StringBuilder();
            int charsRead = 0;
            char[] buffer = new char[1024];

            while (charsRead < end - begin) {
                int remaining = end - begin - charsRead;
                int charsToRead = Math.min(buffer.length, remaining);
                int readCount = reader.read(buffer, 0, charsToRead);
                if (readCount == -1) break; // EOF reached

                content.append(buffer, 0, readCount);
                charsRead += readCount;
            }

            String text = content.toString();

            Pattern wordPattern = Pattern.compile("\\b\\w+\\b");
            Matcher matcher = wordPattern.matcher(text);

            ArrayList<String> words = new ArrayList<>();

            while (matcher.find()) {
                words.add(matcher.group());
            }

            for (String word : words) {
                word = word.trim(); // Ensure no leading/trailing spaces
                if (!word.isEmpty() && isNotNumber(word) && word.length() > 1) {
                    String upperCaseWord = word.toUpperCase();

                    if (!wordCount.containsKey(upperCaseWord)) {
                        wordCount.put(upperCaseWord, 1);
                    } else {
                        wordCount.replace(upperCaseWord, wordCount.get(upperCaseWord) + 1);
                    }
                }
            }

            synchronized (resultMap) {
                Map<String, Integer> fileWordCount = resultMap.get(filePath);

                if (fileWordCount == null || fileWordCount.isEmpty()) {
                    resultMap.put(filePath, new HashMap<>(wordCount));
                } else {
                    for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
                        fileWordCount.merge(entry.getKey(), entry.getValue(), Integer::sum);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a given word contains any letters (i.e., it is not purely numeric).
     *
     * @param word the word to check
     * @return true if the word contains letters, false if it is purely numeric
     */
    public boolean isNotNumber(String word) {
        for (byte b : word.getBytes()) {
            char character = (char) b;
            if (Character.isLetter(character)) {
                return true;
            }
        }
        return false;
    }
}
