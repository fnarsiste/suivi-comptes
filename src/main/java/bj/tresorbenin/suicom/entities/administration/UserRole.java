package bj.tresorbenin.suicom.entities.administration;

import bj.tresorbenin.suicom.entities.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "UserRole")
@Table(name = "profil_utilisateurs",
        indexes = {
                @Index(name = "pfu_code_ix", columnList = "code")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "PROFIL_UTIL_UK", columnNames = {"code", "user_id", "profil_id", "date_cessation"})
        })
public class UserRole extends BaseEntity {
    @Column(name = "etat")
    @Comment("Etat du profil utilisateur.")
    boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Comment("ID utilisateur.")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profil_id", nullable = false)
    @Comment("ID profil.")
    Role role;
}
