package bj.tresorbenin.suicom.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="banques")
@Data
@EqualsAndHashCode(callSuper=false)
public class Banque extends NamedEntity {
    String code;
    String adresse;
}
