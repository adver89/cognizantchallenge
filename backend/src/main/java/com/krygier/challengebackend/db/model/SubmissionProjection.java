package com.krygier.challengebackend.db.model;

import lombok.Data;

//@Data
public interface SubmissionProjection {
    String getAuthorName();
    Long getNumberOfCorrectSolutions();
}
