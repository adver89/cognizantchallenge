package com.krygier.challengebackend.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDto {

    @NotNull
    private Long taskId;

    @NotBlank
    private String author;

    @NotEmpty
    private String language;

    private String code;

    private Boolean correct;

}
