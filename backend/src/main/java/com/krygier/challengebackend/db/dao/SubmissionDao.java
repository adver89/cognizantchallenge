package com.krygier.challengebackend.db.dao;

import com.krygier.challengebackend.db.model.Submission;
import com.krygier.challengebackend.db.model.SubmissionProjection;
import com.krygier.challengebackend.db.model.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionDao extends JpaRepository<Submission, Long> {

//    List<Submission> getAll();//ByCorrectTrue();

    @Query("select s.authorName as authorName, count(s) as numberOfCorrectSolutions from Submission s " +
            "where s.correct = true group by s.authorName order by numberOfCorrectSolutions desc")
    List<SubmissionProjection> getAllBest(Pageable pageable);
//    List<SubmissionProjection> getAllBest();
//    List<Submission> getAllBest();
}
