package com.finki.ukim.mk.demo.domain.model.dto;

import lombok.Data;

@Data
public class QueryDTO {
    private Long id;
    private String queryName;
    private String queryVal;
    private String format;
    private String url;
    private String defaultDatasetName;
    private int timeout;

}
