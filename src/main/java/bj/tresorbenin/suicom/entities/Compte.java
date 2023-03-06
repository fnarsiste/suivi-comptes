package bj.tresorbenin.suicom.entities;


import bj.tresorbenin.suicom.entities.base.NamedEntity;
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
@Entity(name = "Compte")
@Table(name = "comptes")
public class Compte extends NamedEntity {
    private String numCpte;
    private String dateOuv;
    private String dateClot;
    private LettreAutorisation autorisation;

    public String getIntitule() {
        return getLibelle();
    }

}
