package com.krygier.challengebackend.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Submission implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String authorName;

    private boolean correct;

    private LocalDateTime time;

    @ManyToOne
    private Task task;

}
