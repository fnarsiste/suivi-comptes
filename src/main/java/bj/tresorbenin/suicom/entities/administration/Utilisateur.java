package bj.tresorbenin.suicom.entities.administration;

import bj.tresorbenin.suicom.entities.base.BaseEntity;
import bj.tresorbenin.suicom.entities.referentiels.Agent;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users",
        indexes = {
                @Index(name = "usr_code_ix", columnList = "code"),
                @Index(name = "usr_login_ix", columnList = "login"),
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "USERS_UK", columnNames = {"code", "date_cessation"})
        }
)
public class Utilisateur extends BaseEntity {

    @Column(name = "login", nullable = false, length = 32)
    @Comment("Login ID.")
    private String login;

    @Column(name = "motdepasse", length = 128, nullable = false)
    @Comment("Mot de passe")
    private String password;

    @Column(name = "etat", nullable = false)
    @Comment("Etat du compte : Actif (1), Inactif (0)")
    private boolean active;

    @Column(length = 6)
    @Comment("Code authenticattion a double factor")
    private String otpCode;

    @OneToOne(optional=false)
    @JoinColumn(name="agent_id", unique=true, nullable=false)
    private Agent agent;

    public String getFullname() {
        return agent.getFirstName() + " " + agent.getLastName();
    }
}
