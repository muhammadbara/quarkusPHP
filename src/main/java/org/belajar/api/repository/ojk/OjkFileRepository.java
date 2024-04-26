package org.belajar.api.repository.ojk;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.belajar.api.entity.event.Event;
import org.belajar.api.entity.ojk.OjkFile;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class OjkFileRepository implements PanacheRepository<OjkFile> {

    @PersistenceContext
    private EntityManager entityManager;
    public Optional<OjkFile> findByOjkId(Long ojkId) {
            OjkFile ojkFile = entityManager.createQuery(
                            "SELECT of FROM OjkFile of JOIN of.ojk o WHERE o.ojkId = :ojkId", OjkFile.class)
                    .setParameter("ojkId", ojkId)
                    .getSingleResult();
            return Optional.of(ojkFile);

    }
}
