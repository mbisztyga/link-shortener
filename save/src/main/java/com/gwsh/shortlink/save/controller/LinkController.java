package com.gwsh.shortlink.save.controller;

import com.gwsh.shortlink.save.service.ShorteningService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LinkController {

    private final ShorteningService shorteningService;

    @GetMapping("/save/{link}")
    public String saveLink(@PathVariable String link) throws ChangeSetPersister.NotFoundException {
        return shorteningService.shortUrl(link);
    }

}
