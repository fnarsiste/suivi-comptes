package bj.tresorbenin.suicom.entities.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class NamedEntity extends BaseEntity {

    @Column(name = "libelle", nullable = false)
    @Comment("Libelle / Dénomination.")
    protected String libelle;

    @Override
    public String toString() {
        return this.getLibelle();
    }

}
