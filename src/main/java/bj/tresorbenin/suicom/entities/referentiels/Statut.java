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
@Table(name = "statuts",
        indexes = {
                @Index(name = "stt_code_ix", columnList = "code"),
                @Index(name = "stt_libelle_ix", columnList = "libelle")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "statuts_uk", columnNames = {"code", "date_cessation"})
        })
public class Statut extends NamedEntity implements Cloneable {
    public Statut(String code, String libelle) {
        super(code);
        setLibelle(libelle);
    }

    @Override
    public Statut clone() {
        try {
            return (Statut) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
