package org.belajar.api.entity.ojk;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.belajar.api.entity.event.EventImage;
import org.belajar.api.entity.user.Users;
import org.belajar.api.enumeration.OjkEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ojk", schema = "dbo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ojk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ojkId;
    private String title;
    @Enumerated(EnumType.STRING)
    private OjkEnum.status status;
    private String emailBody;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private OjkEnum.reminderType reminderType;
    @Enumerated(EnumType.STRING)
    private OjkEnum.priority priority;
    private String attachment;
    private LocalDateTime modifyDate;

    @OneToMany(mappedBy = "ojk",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OjkDepartmentUser> ojkDepartmentUser;

    @OneToOne(mappedBy = "ojk", cascade = CascadeType.ALL)
    @JsonIgnore
    private OjkFile ojkFile;

}
