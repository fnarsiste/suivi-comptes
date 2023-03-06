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
@Entity(name = "ReleveCompte")
@Table(name="releves_comptes")
public class ReleveCompte extends BaseEntity {
    private String numReleve;
    private String solde;
    private String date;

}
