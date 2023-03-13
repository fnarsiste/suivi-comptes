package bj.tresorbenin.suicom.entities;

import bj.tresorbenin.suicom.entities.base.BaseEntity;
import bj.tresorbenin.suicom.entities.referentiels.Agent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Fiche")
@Table(name = "fiches",
        indexes = {
                @Index(name = "fch_code_ix", columnList = "code")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "FICHE_UK", columnNames = {"code", "date_cessation"})
        })
public class Fiche extends BaseEntity {
    @Column(nullable = false)
    @Comment("Date de création de la fiche")
    private Date dateFiche;

    @Comment("Recommandations issues du traitement de la demande")
    private String recommandations;


    //@OneToOne(optional=false)
    //@JoinColumn(name="demande_id", unique=true, nullable=false)
    private Demande demande;

}
