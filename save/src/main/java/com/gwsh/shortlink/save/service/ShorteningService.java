package com.gwsh.shortlink.save.service;

import org.springframework.stereotype.Service;

@Service
public class ShorteningService {
    private static final String BASE_URL = "http://short.url/";
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 6;

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    public String shortenUrl(String longUrl) {
        UrlMapping existingMapping = urlMappingRepository.findByLongUrl(longUrl);
        if (existingMapping != null) {
            return BASE_URL + existingMapping.getShortUrl();
        }

        String shortUrl;
        do {
            shortUrl = generateShortUrl();
        } while (urlMappingRepository.findByShortUrl(shortUrl) != null);

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl(longUrl);
        urlMapping.setShortUrl(shortUrl);
        urlMappingRepository.save(urlMapping);

        return BASE_URL + shortUrl;
    }

    public String getLongUrl(String shortUrl) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl.replace(BASE_URL, ""));
        return urlMapping != null ? urlMapping.getLongUrl() : "URL not found";
    }

    private String generateShortUrl() {
        Random random = new Random();
        StringBuilder shortUrl = new StringBuilder();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            shortUrl.append(CHARACTERS.charAt(index));
        }
        return shortUrl.toString();
    }
}
