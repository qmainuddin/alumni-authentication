package org.miu.alumni.alumniauthentication.aspect;

import org.miu.alumni.alumniauthentication.aspect.data.OffensiveRequest;
import org.miu.alumni.alumniauthentication.aspect.data.OffensiveWordDictionary;
import org.miu.alumni.alumniauthentication.exceptions.MaxBadWordRequestLimitException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Aspect
@Component
public class WaaOffensiveWordsAspect {
    private final long BAN_TIME = 15 * 60 * 1000;
    private final HashMap<String, OffensiveRequest> offensiveRequestsByUsers = new HashMap<>();
    private final HashMap<String, Long> bannedUsers = new HashMap<>();

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods() {}
    @Before("restControllerMethods()")
    public void logOffensiveWords(JoinPoint joinPoint) {
        List<String> words = new ArrayList<>(getWords(joinPoint.getArgs()));
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (bannedUsers.containsKey(userName) && bannedUsers.get(userName) > System.currentTimeMillis()) {
            throw new MaxBadWordRequestLimitException(String.format("Max Bad Words Requests Limit has been Reached. You need wait for %d minutes.",
                    getRemainingTime(userName)));
        }
        if (OffensiveWordDictionary.hasOffensiveWord(words)) {
            OffensiveRequest offensiveRequest = offensiveRequestsByUsers.getOrDefault(userName,
                    new OffensiveRequest(userName));
            offensiveRequest.addOffensiveWords(words);
            offensiveRequestsByUsers.put(userName, offensiveRequest);
            if (offensiveRequest.shouldBan()) {
                bannedUsers.put(userName, System.currentTimeMillis() + BAN_TIME);
            }
        }

    }

    private Set<String> getWords(Object[] args){
        Set<String> words = new HashSet<>();
        for (Object arg : args) {
            if (arg instanceof String) {
                if (OffensiveWordDictionary.isOffensiveWord((String)arg)) {
                    words.addAll(OffensiveWordDictionary.getOffensiveWords((String)arg));
                }
            } else {
                try {
                    words.addAll(getWords(arg));
                } catch (IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return words;
    }

    private Set<String> getWords(Object obj) throws IllegalAccessException {
        Class<?> objClass = obj.getClass();
        Set<String> words = new HashSet<>();
        for (Field field : objClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getType() == String.class) {
                String value = (String) field.get(obj);
                if (OffensiveWordDictionary.isOffensiveWord(value.toLowerCase())) {
                    words.addAll(OffensiveWordDictionary.getOffensiveWords(value.toLowerCase()));
                    value = OffensiveWordDictionary.hideOffensiveWord(value);
                }
                field.set(obj, value);
            }
            else if (!field.getType().isPrimitive()) {
                words.addAll(getWords(field));
            }
        }
        return words;
    }

    private long getRemainingTime(String username) {
        return (bannedUsers.get(username) - System.currentTimeMillis()) / (60 * 1000);
    }
}
