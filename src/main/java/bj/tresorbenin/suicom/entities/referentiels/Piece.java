package bj.tresorbenin.suicom.entities.referentiels;

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
@Table(name="pieces")
public class Piece extends NamedEntity {
    String refPiece;
}
