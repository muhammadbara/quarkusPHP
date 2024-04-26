package org.belajar.api.repository.ojk;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.belajar.api.entity.ojk.Department;

@ApplicationScoped
public class DepartmentRepository implements PanacheRepository<Department> {
}
