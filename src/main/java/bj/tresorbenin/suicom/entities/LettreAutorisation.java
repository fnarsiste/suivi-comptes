package bj.tresorbenin.suicom.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="autorisations")
public class LettreAutorisation extends BaseEntity implements Serializable {
    String refLettre;
    String objet;
    String dateLettre;
    String lieu;
    String limites;
}
