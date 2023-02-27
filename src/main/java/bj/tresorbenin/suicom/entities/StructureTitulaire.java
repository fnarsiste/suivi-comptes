package bj.tresorbenin.suicom.entities;

import bj.tresorbenin.suicom.entities.base.NamedEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "StructureTitulaire")
@Table(name = "structures_titulaires",
        indexes = {
        @Index(name = "stl_code_ix", columnList = "code"),
        @Index(name = "stl_denomination_ix", columnList = "libelle")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "STRUCTURE_TITULAIRE_UK", columnNames = {"code", "date_cessation"})
        })
@Data
@EqualsAndHashCode(callSuper = false)
public class StructureTitulaire extends NamedEntity {
    //String codeStruct;
    @Comment("Adresse de la structure titulaire")
    @Column(name = "adresse_structure", length = 255)
    String adresse;

    public String getDenomination() {
        return getLibelle();
    }

}
