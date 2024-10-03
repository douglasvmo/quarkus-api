package org.acme.model;

import jakarta.persistence.*;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String cpf;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    private Account account;

}
