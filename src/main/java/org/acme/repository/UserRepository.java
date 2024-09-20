package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.model.User;

import java.util.UUID;

@Transactional
@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    public User findByUUId(UUID uuid) {
        return find("uuid = :uuid", Parameters.with("uuid", uuid)).firstResult();
    }

    public long deleteByUUid(UUID uuid) {
        return delete("uuid = :uuid", Parameters.with("uuid", uuid));
    }
}
