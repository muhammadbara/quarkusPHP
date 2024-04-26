package org.belajar.api.repository.user;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.belajar.api.entity.user.Users;
@ApplicationScoped
public class UsersRepository implements PanacheRepository<Users> {
    @PersistenceContext
    private EntityManager entityManager;


}
