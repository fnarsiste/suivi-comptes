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
                @Index(name = "IDX_CODE", columnList = "code"),
                @Index(name = "IDX_LIBELLE", columnList = "libelle")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "PROFIL_UK", columnNames = {"code", "date_cessation"})
        }
)
public class Profil extends NamedEntity {
}
