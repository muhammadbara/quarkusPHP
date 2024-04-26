package org.belajar.api.repository.ojk;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.belajar.api.entity.ojk.OjkDepartmentUser;

@ApplicationScoped
public class OjkDepartmentUserRepository implements PanacheRepository<OjkDepartmentUser> {
    @Transactional
    public OjkDepartmentUser findByOduIdAndOjk_OjkId(Long oduId, Long ojkId) {
        return find("oduId = ?1 and ojk.ojkId = ?2", oduId, ojkId).firstResult();
    }
}
