package org.belajar.api.entity.ojk;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.belajar.api.entity.event.Event;

@Entity
@Table(name = "ojk_file", schema = "dbo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OjkFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;
    private String fileName;
    private Long fileSize;
    private String fileType;

    @OneToOne
    @JoinColumn(name = "ojkId")
    private Ojk ojk;
}
