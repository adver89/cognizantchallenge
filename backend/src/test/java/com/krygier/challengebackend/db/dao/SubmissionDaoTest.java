package com.krygier.challengebackend.db.dao;

import com.krygier.challengebackend.db.model.Submission;
import com.krygier.challengebackend.db.model.SubmissionProjection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SubmissionDaoTest {

    @Autowired
    private SubmissionDao submissionDao;


    @BeforeAll
    public void setUp() {
        // Player 1
        submissionDao.save(getSubmission("player1", true));
        submissionDao.save(getSubmission("player1", true));
        submissionDao.save(getSubmission("player1", false));
        submissionDao.save(getSubmission("player1", true));

        // Player 2
        submissionDao.save(getSubmission("player2", true));
        submissionDao.save(getSubmission("player2", false));
        submissionDao.save(getSubmission("player2", false));
        submissionDao.save(getSubmission("player2", true));

        // Player 3
        submissionDao.save(getSubmission("player3", true));

        // Player 4
        submissionDao.save(getSubmission("player4", false));

    }

    @Test
    public void bestScoresTest() {

        List<SubmissionProjection> results = submissionDao.getAllBest(PageRequest.of(0,3));

        System.out.println("---> before");
        results.forEach(s -> System.out.println(s.getAuthorName() + ", " + s.getNumberOfCorrectSolutions()));
        System.out.println("---> after");

        assertEquals(3, results.size());

    }

    private Submission getSubmission(String playerName, boolean isCorrect) {
        Submission submission1 = new Submission();
        submission1.setCorrect(isCorrect);
        submission1.setAuthorName(playerName);
        return submission1;
    }


}
