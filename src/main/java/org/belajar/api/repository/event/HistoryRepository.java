package org.belajar.api.repository.event;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.belajar.api.entity.event.History;

@ApplicationScoped
public class HistoryRepository implements PanacheRepository<History> {
}
