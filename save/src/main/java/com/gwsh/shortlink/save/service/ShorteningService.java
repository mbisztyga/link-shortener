package com.gwsh.shortlink.save.service;

import com.gwsh.shortlink.save.data.ShortLinkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class ShorteningService {
    private static final String BASE_URL = "http://short.url/";
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 6;

    private final ShortLinkRepository shortLinkRepository;

    public String shortUrl(String longUrl) {
            String shortUrl = generateShortUrl();
            shortLinkRepository.add(longUrl, shortUrl);
            return shortUrl;
        }

        private String generateShortUrl() {
            Random random = new Random();
            StringBuilder shortUrl = new StringBuilder();
            for (int i = 0; i < SHORT_URL_LENGTH; i++) {
                int index = random.nextInt(CHARACTERS.length());
                shortUrl.append(CHARACTERS.charAt(index));
            }
            return BASE_URL + shortUrl.toString();
        }
    }