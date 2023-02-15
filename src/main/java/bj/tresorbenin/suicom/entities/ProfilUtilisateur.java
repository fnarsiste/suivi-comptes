package bj.tresorbenin.suicom.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="profil_utilisateurs")
public class ProfilUtilisateur extends NamedEntity{
    Utilisateur utilisateur;
    Profil profil;
}
