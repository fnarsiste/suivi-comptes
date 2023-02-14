package bj.tresorbenin.suicom.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="banques")
@Data
@EqualsAndHashCode(callSuper=false)
public class Banque extends NamedEntity implements Cloneable {
    String code;
    String adresse;

    @Override
    public Banque clone() {
        try {
            return (Banque) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
