package org.belajar.api.entity.event;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.belajar.api.entity.user.Users;
import org.belajar.api.enumeration.EventEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "event", schema = "dbo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    private String tittle;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private EventEnum.needVolunteer needVolunteer;
    @Enumerated(EnumType.STRING)
    private EventEnum.audience audience;
    @Enumerated(EnumType.STRING)
    private EventEnum.status status;
    private String imagePath;
    private LocalDateTime modifyDate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "catId")
    private Category category;

    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL)
    @JsonIgnore
    private EventImage eventImages;

    @OneToMany(mappedBy = "event", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<History> history;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "etId")
    private EventTheme eventTheme;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_event",
            joinColumns = @JoinColumn(name = "eventId"),
            inverseJoinColumns = @JoinColumn(name = "userId"))
    @JsonIgnore
    private List<Users> users;

}
