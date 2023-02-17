package bj.tresorbenin.suicom.entities.administration;

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
@Table(name = "profils",
        indexes = {
                @Index(name = "pf_code_ix", columnList = "code"),
                @Index(name = "pf_libelle_ix", columnList = "libelle")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "PROFIL_UK", columnNames = {"code", "date_cessation"})
        }
)
public class Profil extends NamedEntity {
}
