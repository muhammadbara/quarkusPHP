package org.belajar.api.repository.ojk;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.belajar.api.entity.event.Event;
import org.belajar.api.entity.ojk.Ojk;
import org.belajar.api.enumeration.EventEnum;
import org.belajar.api.enumeration.OjkEnum;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class OjkRepository implements PanacheRepository<Ojk> {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<List<Ojk>> findByTittle(String title) {
        return Optional.of(entityManager.createQuery(
                        "SELECT o FROM Ojk o WHERE o.title LIKE CONCAT('%', :title, '%')", Ojk.class)
                .setParameter("title", title)
                .getResultList());
    }

    public Optional<List<Ojk>>findByStatus(OjkEnum.status status){
        return Optional.of(entityManager.createQuery(
                        "SELECT o FROM Ojk o WHERE o.status = :status", Ojk.class)
                .setParameter("status", status)
                .getResultList());
    }

    public Optional<List<Ojk>>findByAttachment(String attachment){
        return Optional.of(entityManager.createQuery(
                        "SELECT o FROM Ojk o WHERE o.attachment LIKE CONCAT('%', :attachment, '%')", Ojk.class)
                .setParameter("attachment", attachment)
                .getResultList());
    }

    public Optional<List<Ojk>>findByReminderType(OjkEnum.reminderType reminderType){
        return Optional.of(entityManager.createQuery(
                "SELECT o FROM Ojk o WHERE o.reminderType=:reminderType",Ojk.class)
                .setParameter("reminderType",reminderType)
                .getResultList());
    }

    public Optional<List<Ojk>>findByPriority(OjkEnum.priority priority){
        return Optional.of(entityManager.createQuery(
                "SELECT o FROM Ojk o WHERE o.priority=priority",Ojk.class)
                .setParameter("priority",priority)
                .getResultList());
    }

    public Optional<List<Ojk>>findByStartDate(LocalDate startDate){
        return Optional.of(entityManager.createQuery(
                        "SELECT o FROM Ojk o WHERE o.startDate = :startDate", Ojk.class)
                .setParameter("startDate",startDate)
                .getResultList());
    }

    public Optional<List<Ojk>>findByEndDate(LocalDate endDate){
        return Optional.of(entityManager.createQuery(
                        "SELECT o FROM Ojk o WHERE o.endDate = :endDate", Ojk.class)
                .setParameter("endDate",endDate)
                .getResultList());
    }



}
