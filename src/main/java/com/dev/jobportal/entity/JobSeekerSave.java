package com.dev.jobportal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "job_seeker_save", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "job"})
})
public class JobSeekerSave implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private JobSeekerProfile userId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "job")
    private JobPostActivity job;


}
