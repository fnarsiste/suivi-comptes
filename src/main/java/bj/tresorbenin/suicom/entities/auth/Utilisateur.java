package bj.tresorbenin.suicom.entities.auth;

import bj.tresorbenin.suicom.entities.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "utilisateurs")
public class Utilisateur extends BaseEntity {
    String login;
    String motpasse;
}
