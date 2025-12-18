public class WordAnalysis { 
 
 private HashMap<String, HashMap<String, Integer>> wordCounts = new HashMap<>();

private List<Thread> threads = new ArrayList<>();

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
}