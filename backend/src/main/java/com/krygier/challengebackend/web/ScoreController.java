package com.krygier.challengebackend.web;

import com.krygier.challengebackend.db.dao.SubmissionDao;
import com.krygier.challengebackend.db.model.SubmissionProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScoreController {

    @Autowired
    private SubmissionDao submissionDao;

    @GetMapping("/scores")
    public ResponseEntity getBestScores() {
        List<SubmissionProjection> bestResults = submissionDao.getAllBest(PageRequest.of(0, 3));
        return ResponseEntity.ok(bestResults);
    }
}
