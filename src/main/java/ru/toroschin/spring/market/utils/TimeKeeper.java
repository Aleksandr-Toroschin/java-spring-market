package ru.toroschin.spring.market.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
@Slf4j
public class TimeKeeper {
    private final Map<String, Long> timing;

    public TimeKeeper() {
        timing = new HashMap<>();
    }

    public void addTime(String key, Long time) {
        if (timing.containsKey(key)) {
            Long allTime = timing.get(key) + time;
            timing.put(key, allTime);
        } else {
            timing.put(key, time);
        }
//        log.info("Статистика тайминга: " + timing);
    }
}
