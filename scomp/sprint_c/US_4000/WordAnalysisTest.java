package jobs4u.base.applicationmanagement.domain;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * IT IS IMPORTANT TO PREFACE THESE TESTS BY SPECIFYING THIS.
 * ALL THE VALUES USED TO ENSURE SOFTWARE QUALITY THROUGH TESTING WERE ARRIVED TO USING THE 'GREP' FUNCTION ON UNIX
 */
public class WordAnalysisTest {

    /**
     * Ensures that the single-threaded word count is accurate.
     * This test writes a sample string to a temporary file and verifies that the word count for "THE" is correct.
     */
    @Test
    public void ensureSingleThreadCountAccurate() throws IOException, InterruptedException {
        String content = "The quick brown fox jumps over the lazy dog. The fox is quick.";
        Path tempFile = Files.createTempFile("testSingleThreadWordCount", ".txt");
        Files.writeString(tempFile, content);

        Set<String> filePaths = new HashSet<>();
        filePaths.add(tempFile.toString());

        WordAnalysis wordAnalysis = new WordAnalysis();
        wordAnalysis.countWords(filePaths);

        List<WordAnalysis.WordInfo> result = wordAnalysis.countWords(filePaths);
        assertEquals(3, result.stream().filter(w -> w.getWord().equals("THE")).findFirst().orElseThrow().getCount());

        Files.delete(tempFile);
    }

    /**
     * Ensures that the multi-threaded word count is accurate.
     * This test checks the word count for a large file with varying byte thresholds.
     */
    @Test
    public void ensureMultiThreadedWordCountAccurate() throws IOException, InterruptedException {
        String f1 = "src/test/java/jobs4u/base/applicationmanagement/domain/files/1-big-file-1.txt";
        //jobs4u/base/applicationmanagement/domain/WordAnalysisTest.java
        Set<String> files = new HashSet<>();
        files.add(f1);

        WordAnalysis analysis = new WordAnalysis();
        List<WordAnalysis.WordInfo> result = analysis.countWords(files);

        Random random = new Random();

        // Generate a random number between 1 and 2000000
        int randomNumber;

        for (int i = 0; i < 10; i++) {
            randomNumber = random.nextInt(2000000) + 1;
            analysis.setByteThreshold(randomNumber);

            result = analysis.countWords(files);
            assertEquals(((ArrayList<WordAnalysis.WordInfo>) result).get(0).getCount(), 67914);
            assertEquals(((ArrayList<WordAnalysis.WordInfo>) result).get(1).getCount(), 53382);
        }
    }

    /**
     * Ensures that multiple files are processed correctly.
     * This test writes a sample string to a temporary file and adds another large file to verify the combined word count.
     */
    @Test
    public void ensureMultipleFilesWork() throws IOException, InterruptedException {
        String content = "The quick brown fox jumps over the lazy dog. The fox is quick.";
        Path tempFile = Files.createTempFile("testSingleThreadWordCount", ".txt");
        Files.writeString(tempFile, content);
        String f1 = "src/test/java/jobs4u/base/applicationmanagement/domain/files/1-big-file-1.txt";

        Set<String> filePaths = new HashSet<>();
        filePaths.add(tempFile.toString());
        filePaths.add(f1);

        WordAnalysis wordAnalysis = new WordAnalysis();
        wordAnalysis.countWords(filePaths);

        WordAnalysis analysis = new WordAnalysis();
        List<WordAnalysis.WordInfo> result = analysis.countWords(filePaths);

        assertEquals(67917, result.stream().filter(w -> w.getWord().equals("THE")).findFirst().orElseThrow().getCount());

        Files.delete(tempFile);
    }

    /**
     * Ensures that empty files return no word counts.
     * This test creates an empty temporary file and verifies that the word count result is empty.
     */
    @Test
    public void ensureEmptyFilesReturnsNothing() throws IOException, InterruptedException {
        String content = "";
        Path tempFile = Files.createTempFile("testMultiThreadWordCount", ".txt");
        Files.writeString(tempFile, content);

        Set<String> filePaths = new HashSet<>();
        filePaths.add(tempFile.toString());

        WordAnalysis wordAnalysis = new WordAnalysis();
        wordAnalysis.countWords(filePaths);

        Set<WordAnalysis.WordInfo> result = wordAnalysis.getMostUsedWords();
        assertTrue(result.isEmpty());

        Files.delete(tempFile);
    }

    /**
     * Ensures word count is accurate with different delimiters.
     * This test writes a sample string with various delimiters to a temporary file and verifies the word count for "THE".
     */
    @Test
    public void testWordCountWithDelimiters() throws IOException, InterruptedException {
        String content = "The quick brown,fox;jumps:over@the lazy-dog!The fox is (quick).";
        Path tempFile = Files.createTempFile("testWordCountWithDelimiters", ".txt");
        Files.writeString(tempFile, content);

        Set<String> filePaths = new HashSet<>();
        filePaths.add(tempFile.toString());

        WordAnalysis wordAnalysis = new WordAnalysis();
        wordAnalysis.countWords(filePaths);

        Set<WordAnalysis.WordInfo> result = wordAnalysis.getMostUsedWords();
        assertEquals(3, result.stream().filter(w -> w.getWord().equals("THE")).findFirst().orElseThrow().getCount());

        Files.delete(tempFile);
    }

    /**
     * Tests the `isNotNumber` method.
     * This test verifies that the `isNotNumber` method correctly identifies strings that are not purely numeric.
     */
    @Test
    public void ensureIsNotNumber() {
        WordCountThread thread = new WordCountThread("dummyPath", 0, 0, new HashMap<>());
        assertTrue(thread.isNotNumber("Hello"));
        assertFalse(thread.isNotNumber("123"));
        assertTrue(thread.isNotNumber("123abc"));
        assertTrue(thread.isNotNumber("abc123"));
    }


    @Test
    public void assertMostOptimalNumberOfBytes() throws IOException, InterruptedException {
        String f1 = "src/test/java/jobs4u/base/applicationmanagement/domain/files/1-big-file-1.txt";
        String f2 = "src/test/java/jobs4u/base/applicationmanagement/domain/files/3-big-file1.txt";
        String f3 = "src/test/java/jobs4u/base/applicationmanagement/domain/files/4-big-file-1.txt";


        Set<String> files = new HashSet<>();
        files.add(f1);
        files.add(f2);
        files.add(f3);

        WordAnalysis analysis = new WordAnalysis();


        int THRESHOLD = 1000000;
        int chop = THRESHOLD /10;

        analysis.setByteThreshold(THRESHOLD);

        boolean flag = true;

        Long time1 = System.currentTimeMillis();
        analysis.countWords(files);
        Long time2 = System.currentTimeMillis();
        Long previousTime = time2 - time1;
        THRESHOLD -= chop;


        while(flag) {
            analysis = new WordAnalysis();
            analysis.setByteThreshold(THRESHOLD);

            time1 = System.currentTimeMillis();
            analysis.countWords(files);
            time2 = System.currentTimeMillis();

            if((time2-time1) > previousTime){

                THRESHOLD += chop;
                chop /= 10;
            }else{
                previousTime = (time2 - time1);
            }


            if(chop <= 10000 || previousTime - (time2- time1) < 50){
                flag = false;
            }else{
                THRESHOLD -= chop;
            }



        }

        System.out.println("The desired byte threshold for this machine is: " + THRESHOLD);
    }
}