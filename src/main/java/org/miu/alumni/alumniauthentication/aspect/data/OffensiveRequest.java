package org.miu.alumni.alumniauthentication.aspect.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OffensiveRequest {
    public static final int MAX_BAD_WORD_REQUESTS = 5;
    public static final long TIME_RANGE_FOR_BAN_MILLIS = 30 * 60 * 1000;

    private HashMap<OffensiveWordDictionary, Integer> offensiveWordFrequencies = new HashMap<>();
    private String userName;
    private Long lastAccessTime;

    public OffensiveRequest(String userName){
        this.userName = userName;
        this.offensiveWordFrequencies.clear();
    }

    public OffensiveRequest(String userName, Set<String> offensiveWords) {
        this.userName = userName;
        initialize(offensiveWords);
    }

    public void initialize(Set<String> offensiveWords){
        offensiveWordFrequencies.clear();
        offensiveWords.stream().filter(OffensiveWordDictionary::isOffensiveWord)
                .forEach(offensiveWord ->
                        offensiveWordFrequencies.put(OffensiveWordDictionary.valueOf(offensiveWord), 1));
        lastAccessTime = System.currentTimeMillis();
    }

    public void addOffensiveWords(List<String> offensiveWords){
        offensiveWords.stream().filter(OffensiveWordDictionary::isOffensiveWord)
                .forEach(offensiveWord ->
                        offensiveWordFrequencies.put(OffensiveWordDictionary.valueOf(offensiveWord.toUpperCase()),
                                offensiveWordFrequencies.getOrDefault(OffensiveWordDictionary.valueOf(offensiveWord.toUpperCase()), 0) + 1));
        lastAccessTime = System.currentTimeMillis();
    }
    public void addOffensiveWord(String offensiveWord){
        try {
            if (OffensiveWordDictionary.isOffensiveWord(offensiveWord)) {
                OffensiveWordDictionary offensiveWordDictionary = OffensiveWordDictionary.valueOf(offensiveWord.toUpperCase());
                offensiveWordFrequencies.put(offensiveWordDictionary,
                        offensiveWordFrequencies.getOrDefault(offensiveWordDictionary, 0) + 1);
                lastAccessTime = System.currentTimeMillis();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("No Offensive Word Found");
        } catch (RuntimeException e) {
            System.out.println("No Offensive Word Found");
        }

    }

    public int getOffensiveWordTotalFrequency() {
        return offensiveWordFrequencies.entrySet().stream().map(Map.Entry::getValue).reduce(0, Integer::sum);
    }

    public boolean shouldBan(){
        if (System.currentTimeMillis() - lastAccessTime > TIME_RANGE_FOR_BAN_MILLIS){
            this.offensiveWordFrequencies.clear();
        }
        return getOffensiveWordTotalFrequency() >= MAX_BAD_WORD_REQUESTS;
    }
}
