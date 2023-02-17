package bj.tresorbenin.suicom.entities.referentiels;

import bj.tresorbenin.suicom.entities.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agents",
        indexes = {
                @Index(name = "agt_code_ix", columnList = "code"),
                @Index(name = "agt_matricule_ix", columnList = "matricule"),
                @Index(name = "fct_nom_ix", columnList = "noms"),
                @Index(name = "fct_prenom_ix", columnList = "prenoms"),
                @Index(name = "fct_email_ix", columnList = "email"),
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "agents_uk", columnNames = {"code", "date_cessation"})
        }
)
public class Agent extends BaseEntity {
    @Comment("Numero matricule agent")
    @Column(length = 20, nullable = false)
    String matricule;

    @Comment("Noms agent")
    @Column(name = "noms", length = 64, nullable = false)
    String lastName;

    @Comment("Prénoms agent")
    @Column(name = "prenoms", length = 64, nullable = false)
    String firstName;

    @Column(name = "email", length = 64, nullable = false)
    @Comment("Adresse email agent")
    String emailAddress;

    @Comment("Adresse agent")
    String adresse;
}
