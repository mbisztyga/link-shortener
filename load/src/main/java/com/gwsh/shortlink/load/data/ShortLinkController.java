package com.gwsh.shortlink.load.data;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortLinkController {


    @GetMapping("/")
    public String getShortLink() {
        return "";
    }
}
