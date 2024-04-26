package org.belajar.api.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.belajar.api.entity.ojk.OjkDepartmentUser;

import java.util.List;

@Entity
@Table(name = "users", schema = "dbo")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String position;

    @OneToMany(mappedBy = "users")
    @JsonIgnore
    private List<OjkDepartmentUser> ojkDepartmentUser;



}
