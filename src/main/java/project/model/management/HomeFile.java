package project.model.management;

import lombok.*;
import project.model.Auditable;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "home_files")
public class HomeFile extends Auditable<String> {
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "home_id", referencedColumnName = "id")
    private Home home;

    @Column(name = "file_name")
    private String filename;

    @Column(name = "file_size")
    private Long size;

    @Column(name = "file_extension")
    private String extension;

    @Column(name = "modified_name")
    private String modifiedName;

    @Column(name = "upload_path")
    private String uploadPath;

    @Column(name = "absolute_path")
    private String absolutePath;
}
