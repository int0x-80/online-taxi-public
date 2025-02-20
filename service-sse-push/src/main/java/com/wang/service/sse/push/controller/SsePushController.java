package com.wang.service.sse.push.controller;

import com.wang.common.utils.SsePrefixUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Wang Yinuo
 * @create: 2025-02-18:PM1:21
 */
@RestController
public class SsePushController {

    private static Map<String, SseEmitter> emitterMap = new HashMap<>();

    @GetMapping("/connect")
    public SseEmitter connect(@RequestParam Long userId, @RequestParam String identity) {
        SseEmitter sseEmitter = new SseEmitter(0L);
        String key = SsePrefixUtil.generateKey(userId, identity);
        emitterMap.put(key, sseEmitter);
        return sseEmitter;
    }

    @GetMapping("/disconnect")
    public String disconnect(@RequestParam Long userId, @RequestParam String identity) {
        String key = SsePrefixUtil.generateKey(userId, identity);
        if (emitterMap.containsKey(key)) {
            SseEmitter remove = emitterMap.remove(key);
            remove.complete();
        }
        return "close";
    }

    @GetMapping("/push")
    public String push(@RequestParam Long userId, @RequestParam String identity, @RequestParam String message) {
        String key = SsePrefixUtil.generateKey(userId, identity);
        try {
            if (emitterMap.containsKey(key)) {
                SseEmitter sseEmitter = emitterMap.get(key);
                if (sseEmitter != null) {
                    sseEmitter.send(message);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "ok";
    }
}
