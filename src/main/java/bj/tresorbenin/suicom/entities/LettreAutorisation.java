package bj.tresorbenin.suicom.entities;

import bj.tresorbenin.suicom.entities.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "LettreAutorisation")
@Table(name="autorisations")
public class LettreAutorisation extends BaseEntity {
    private String refLettre;
    private String objet;
    private String dateLettre;
    private String lieu;
    private String limites;
    //private Demande demande;
}
