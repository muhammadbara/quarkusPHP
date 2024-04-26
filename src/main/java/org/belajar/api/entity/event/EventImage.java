package org.belajar.api.entity.event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "eventImage")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;
    private String imageName;
    private Long imageSize;
    private String imageType;

    @OneToOne
    @JoinColumn(name = "eventId")
    private Event event;

}
