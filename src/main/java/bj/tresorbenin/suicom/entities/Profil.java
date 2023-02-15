package bj.tresorbenin.suicom.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name="profils")
@Data
@EqualsAndHashCode(callSuper=false)
public class Profil extends NamedEntity{
}
