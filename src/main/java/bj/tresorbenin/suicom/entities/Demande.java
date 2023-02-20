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
@Entity(name = "Demande")
@Table(name = "demandes")
public class Demande extends BaseEntity {
    String refDem;
    String objet;
    String dateDem;
    String lieu;
}
