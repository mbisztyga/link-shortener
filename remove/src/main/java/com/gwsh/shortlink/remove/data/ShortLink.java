package com.gwsh.shortlink.remove.data;

import lombok.Data;

import java.time.Instant;

@Data
public class ShortLink {
    private String fullLink;
    private String shortLink;
    private Instant addedDate;
}
