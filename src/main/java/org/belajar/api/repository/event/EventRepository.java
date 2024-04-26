package org.belajar.api.repository.event;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.belajar.api.entity.event.Event;
import org.belajar.api.enumeration.EventEnum;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EventRepository implements PanacheRepositoryBase<Event, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void deleteByUserIdAndEventId(Long userId, Long eventId) {
        entityManager.createNativeQuery("DELETE FROM user_event WHERE event_id = :eventId AND user_id = :userId")
                .setParameter("eventId", eventId)
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Transactional
    public Optional<List<Event>> findByTittle(String tittle) {
        return Optional.of(entityManager.createQuery(
                        "SELECT e FROM Event e WHERE e.tittle LIKE CONCAT('%', :tittle, '%')", Event.class)
                .setParameter("tittle", tittle)
                .getResultList());
    }

    public Optional<List<Event>>findByCat(Long catId){
        return Optional.of(entityManager.createQuery(
                        "SELECT e FROM Event e JOIN e.category c WHERE c.catId = :catId", Event.class)
                .setParameter("catId", catId)
                .getResultList());
    }


    public Optional<List<Event>>findByEventTheme(Long etId){
        return Optional.of(entityManager.createQuery(
                        "SELECT e FROM Event e JOIN e.eventTheme et WHERE et.etId = :etId", Event.class)
                .setParameter("etId", etId)
                .getResultList());
    }

    public Optional<List<Event>>findByVolunteer(EventEnum.needVolunteer needVolunteer){
        return Optional.of(entityManager.createQuery(
                        "SELECT e FROM Event e WHERE e.needVolunteer = :needVolunteer", Event.class)
                .setParameter("needVolunteer", needVolunteer)
                .getResultList());
    }

    public Optional<List<Event>>findByAudience(EventEnum.audience audience){
        return Optional.of(entityManager.createQuery(
                        "SELECT e FROM Event e WHERE e.audience = :audience", Event.class)
                .setParameter("audience", audience)
                .getResultList());
    }

    public Optional<List<Event>>findByStatus(EventEnum.status status){
        return Optional.of(entityManager.createQuery(
                        "SELECT e FROM Event e WHERE e.status = :status", Event.class)
                .setParameter("status", status)
                .getResultList());
    }

    public Optional<List<Event>>findByStartDate(LocalDate startDate){
        return Optional.of(entityManager.createQuery(
                "SELECT e FROM Event e WHERE e.startDate = :startDate", Event.class)
                .setParameter("startDate",startDate)
                .getResultList());
    }

    public Optional<List<Event>>findByEndDate(LocalDate endDate){
        return Optional.of(entityManager.createQuery(
                        "SELECT e FROM Event e WHERE e.endDate = :endDate", Event.class)
                .setParameter("endDate",endDate)
                .getResultList());
    }

    public PanacheQuery<Event> findAllPaginated(int pageIndex, int pageSize) {
        return findAll().page(pageIndex, pageSize);
    }
}
