package bj.tresorbenin.suicom.entities.referentiels;

import bj.tresorbenin.suicom.entities.base.NamedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "fonctions",
        indexes = {
                @Index(name = "fct_code_ix", columnList = "code"),
                @Index(name = "fct_libelle_ix", columnList = "libelle")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "fonctions_uk", columnNames = {"code", "date_cessation"})
        }
)
public class Fonction extends NamedEntity implements Cloneable {

    public Fonction(String code, String libelle) {
        super(libelle);
        setCode(code);
    }

    @Override
    public Fonction clone() {
        try {
            return (Fonction) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
