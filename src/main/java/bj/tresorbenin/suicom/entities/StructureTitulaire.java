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
@Table(name="structures_titulaires")
public class StructureTitulaire extends NamedEntity implements Serializable {
    String codeStruct;
    String adress;

    public String getDenomination(){
        return getLibelle();
    }

}
