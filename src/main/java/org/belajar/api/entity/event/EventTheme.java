package org.belajar.api.entity.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "eventTheme",schema = "dbo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventTheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long etId;
    private String name;
    private String description;

    @OneToMany(mappedBy = "eventTheme", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Event> events;
}
