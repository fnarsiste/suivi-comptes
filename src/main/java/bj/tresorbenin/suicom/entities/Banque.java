package bj.tresorbenin.suicom.entities;

import jakarta.persistence.*;
import lombok.*;

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
                @UniqueConstraint(name = "BANQUE_UK", columnNames = {"code", "sigle", "date_cessation"})
        }
)
@Data
@EqualsAndHashCode(callSuper = false)
public class Banque extends NamedEntity implements Cloneable {

    @Column(nullable = false, length = 32)
    private String sigle;

    private String adresse;

    @Override
    public Banque clone() {
        try {
            return (Banque) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
