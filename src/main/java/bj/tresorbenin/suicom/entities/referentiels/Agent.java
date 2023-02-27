package bj.tresorbenin.suicom.entities.referentiels;

import bj.tresorbenin.suicom.entities.administration.User;
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
@Entity(name = "Agent")
@Table(name = "agents",
        indexes = {
                @Index(name = "agt_code_ix", columnList = "code"),
                @Index(name = "agt_matricule_ix", columnList = "matricule"),
                @Index(name = "agt_nom_ix", columnList = "nom"),
                @Index(name = "agt_prenom_ix", columnList = "prenoms"),
                @Index(name = "agt_email_ix", columnList = "email")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "agents_uk", columnNames = {"code", "date_cessation"})
        }
)
public class Agent extends BaseEntity {
    @Comment("Numero matricule agent")
    @Column(length = 20, nullable = false)
    String matricule;

    @Comment("Nom agent")
    @Column(name = "nom", length = 64, nullable = false)
    String lastName;

    @Comment("Prénoms agent")
    @Column(name = "prenoms", length = 64, nullable = false)
    String firstName;

    @Column(name = "email", length = 64, nullable = false)
    @Comment("Adresse email agent")
    String emailAddress;

    @Column(name = "adresse_agent", length = 255)
    @Comment("Adresse agent")
    String adresse;

    @OneToOne(optional = false, mappedBy = "agent", cascade = CascadeType.ALL)
    private User user;

    public String getFullnameCombo() {
        return String.format("%s - %s %s", getMatricule(), getFirstName(), getLastName());
    }
}
