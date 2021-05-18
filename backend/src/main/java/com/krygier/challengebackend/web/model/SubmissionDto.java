package com.krygier.challengebackend.web.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SubmissionDto {

    @NotNull
    private Long taskId;

    @NotBlank
    private String author;

    @NotEmpty
    private String language;

    private String code;

}
