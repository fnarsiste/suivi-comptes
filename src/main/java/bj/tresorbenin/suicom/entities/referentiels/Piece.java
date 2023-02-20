package bj.tresorbenin.suicom.entities.referentiels;

import bj.tresorbenin.suicom.entities.base.NamedEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Entity(name = "Piece")
@Table(name="pieces",
indexes = {
    @Index(name = "piece_code_ix", columnList = "code"),
    @Index(name = "piece_libelle_ix", columnList = "libelle")
    }, uniqueConstraints = {
        @UniqueConstraint(name = "pieces_uk", columnNames = {"code", "date_cessation"})
    })
public class Piece extends NamedEntity {

    //String refPiece;

    public Piece(String code, String libelle) {
        super(code);
        setLibelle(libelle);
    }
}
