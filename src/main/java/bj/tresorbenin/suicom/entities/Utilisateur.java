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
@Table(name="utilisateurs")
public class Utilisateur extends Agent{
    String login;
    String motpasse;
}
