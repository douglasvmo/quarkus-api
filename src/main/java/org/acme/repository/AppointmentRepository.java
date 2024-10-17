package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import org.acme.model.Account;

import java.util.List;
import java.util.UUID;

public class AppointmentRepository implements PanacheRepository<Account> {

    public List<Account> findByDoctor(UUID uuid) {
        return find("doctor_id = (SELECT id FROM account WHERE uuid = :uuid)", Parameters.with("uuid", uuid)).stream().toList();
    }
}
