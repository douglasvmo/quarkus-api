package org.acme.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    private OffsetDateTime scheduled;

    @Column(name = "create_at", updatable = false)
    private OffsetDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Account doctor;

}
