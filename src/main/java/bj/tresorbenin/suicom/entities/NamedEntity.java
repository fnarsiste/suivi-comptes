package bj.tresorbenin.suicom.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = false)
public class NamedEntity extends BaseEntity {

    @Column(name = "libelle", nullable = false)
    @Comment("Libelle / Dénomination.")
    protected String libelle;

    @Override
    public String toString() {
        return this.getLibelle();
    }

}
