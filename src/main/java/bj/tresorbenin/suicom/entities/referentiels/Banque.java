package bj.tresorbenin.suicom.entities.referentiels;

import bj.tresorbenin.suicom.entities.base.NamedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "banques",
        indexes = {
                @Index(name = "IDX_CODE", columnList = "code"),
                @Index(name = "IDX_LIBELLE", columnList = "libelle")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "BANQUE_UK", columnNames = {"code", "date_cessation"})
        }
)
@Data
@EqualsAndHashCode(callSuper = false)
public class Banque extends NamedEntity {

    @Comment("Adresse de la banque")
    private String adresse;

    /*@Override
    public Banque clone() {
        try {
            return (Banque) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }*/
}
