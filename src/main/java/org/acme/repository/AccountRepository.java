package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.model.Account;

import java.util.UUID;

@Transactional
@ApplicationScoped
public class AccountRepository implements PanacheRepository<Account> {
    public Account findByUUId(UUID uuid) {
        return find("uuid = :uuid", Parameters.with("uuid", uuid)).firstResult();
    }

    public long deleteByUUid(UUID uuid) {
        return delete("uuid = :uuid", Parameters.with("uuid", uuid));
    }

    public Account findByEmail(String email) {
        return find("email = ?1", email).firstResult();
    }
}
