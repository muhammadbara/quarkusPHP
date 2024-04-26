package org.belajar.api.entity.ojk;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.belajar.api.entity.user.Users;

@Entity
@Table(name = "ojk_department_user", schema = "dbo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OjkDepartmentUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oduId;

    @ManyToOne
    @JoinColumn(name = "deptId")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users users;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "ojkId")
    private Ojk ojk;
}
