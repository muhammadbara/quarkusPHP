package org.belajar.api.entity.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.belajar.api.enumeration.EventEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "history", schema = "dbo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;
    @Enumerated(EnumType.STRING)
    private EventEnum.action action;
    private LocalDateTime actionTime;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "eventId")
    private Event event;


}
