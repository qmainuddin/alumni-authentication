package org.miu.alumni.alumniauthentication.aspect.data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum OffensiveWordDictionary {
    SPRING("spring"),
    EXAMPLE("example"),
    SAMPLE("sample");

    private final String offensiveWord;

    OffensiveWordDictionary(String offensiveWord) {
        this.offensiveWord = offensiveWord;
    }
    public String getOffensiveWord() {
        return offensiveWord;
    }

    public static boolean isOffensiveWord(String word){
        return Arrays.stream(OffensiveWordDictionary.values())
                .anyMatch(offensiveWordDictionary -> word.toLowerCase()
                        .contains(offensiveWordDictionary.getOffensiveWord().toLowerCase()));
    }
    private static String generateHiddenString(String mainString) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mainString.length(); i++) {
            builder.append("*");
        }
        return builder.toString();
    }

    public static String hideOffensiveWord(String word) {
        List<String> foundOffensiveString =  getOffensiveWords(word);
        if (foundOffensiveString == null) return "";
        for (String str : foundOffensiveString) {
            String hiddenString = generateHiddenString(str);
            word = word.replace(str, hiddenString);
        }
        return word;
    }

    public static List<String> getOffensiveWords(String word) {
        if (!isOffensiveWord(word)) return null;
        String newWord = word.toLowerCase();
        return  Arrays.stream(OffensiveWordDictionary.values())
                .filter(offensiveWordDictionary -> newWord.contains(offensiveWordDictionary.getOffensiveWord().toLowerCase()))
                .map(OffensiveWordDictionary::getOffensiveWord)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    public static boolean hasOffensiveWord(List<String> words){
        return Arrays.stream(OffensiveWordDictionary.values())
                .anyMatch(offensiveWordDictionary ->
                        words.stream()
                                .anyMatch(word -> word.toLowerCase()
                                        .contains(offensiveWordDictionary.getOffensiveWord().toLowerCase())));
    }
}
