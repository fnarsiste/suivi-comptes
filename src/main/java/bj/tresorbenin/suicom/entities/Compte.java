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
@Entity
@Table(name="comptes")
public class Compte extends NamedEntity {
String numCpte;
String dateOuv;
String dateClot;

 public String getIntitule(){
     return getLibelle();
 }

}
