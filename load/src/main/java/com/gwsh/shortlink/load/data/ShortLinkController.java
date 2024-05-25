package com.gwsh.shortlink.load.data;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ShortLinkController {

    private final ShortLinkRepository repository;

    @GetMapping("/")
    public String getShortLink() {
        repository.
    }
}
