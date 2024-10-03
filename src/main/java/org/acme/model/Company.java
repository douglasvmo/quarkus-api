package org.acme.model;

import jakarta.persistence.*;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String cnpj;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    private Account account;
}
