package bj.tresorbenin.suicom.entities;

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
@Entity
@Table(name="structures_titulaires")
public class StructureTitulaire extends NamedEntity {
    String codeStruct;
    String adress;

    public String getDenomination(){
        return getLibelle();
    }

}
