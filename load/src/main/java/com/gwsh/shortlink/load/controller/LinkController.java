package com.gwsh.shortlink.load.controller;

import com.gwsh.shortlink.load.common.CassandraVariables;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.gwsh.shortlink.load.data.ShortLinkRepository;

@RestController
@RequiredArgsConstructor
public class LinkController {

    private final ShortLinkRepository shortLinkRepository;

    @GetMapping("/load/{link}")
    public String getFullLink(@PathVariable String link) throws  ChangeSetPersister.NotFoundException {
        return shortLinkRepository.getLongLinkByShortLink(link, CassandraVariables.KEYSPACE_1);
    }

    @GetMapping("/exists/{link}")
    public String getShortLinkForExistingLongLink(@PathVariable String link) throws  ChangeSetPersister.NotFoundException {
        return shortLinkRepository.getLongLinkByShortLink(link, CassandraVariables.KEYSPACE_1);
    }
}
