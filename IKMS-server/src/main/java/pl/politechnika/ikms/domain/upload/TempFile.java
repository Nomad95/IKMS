package pl.politechnika.ikms.domain.upload;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;

import javax.persistence.*;

@Data
@Entity
@Table(name = "temp_files")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name="temp_files_seq_name",sequenceName="temp_files_seq", allocationSize = 1)
public class TempFile extends AbstractEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "temp_files_seq_name")
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Lob
    @Column(name = "content", nullable = false)
    private byte[] content;
}
