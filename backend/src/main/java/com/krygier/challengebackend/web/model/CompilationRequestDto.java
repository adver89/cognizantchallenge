package com.krygier.challengebackend.web.model;

import lombok.Data;

@Data
public class CompilationRequestDto {
    private String clientId;

    private String clientSecret;

    private String language;

    private String versionIndex;

    private String script;

    private String stdin;
}
