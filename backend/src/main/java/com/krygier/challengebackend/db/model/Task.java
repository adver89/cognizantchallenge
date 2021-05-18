package com.krygier.challengebackend.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
public class Task implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private String codeInput;

    private String expectedResult;

    public Task(Long id) {
        this.id = id;
    }
}
