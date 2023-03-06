package bj.tresorbenin.suicom.entities;

import bj.tresorbenin.suicom.entities.administration.User;
import bj.tresorbenin.suicom.entities.base.BaseEntity;
import bj.tresorbenin.suicom.entities.referentiels.Statut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Demande")
@Table(name = "demandes",
        indexes = {
            @Index(name = "de_code_ix", columnList = "code")
            //,@Index(name = "de_reference_ix", columnList = "reference")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "DEMANDE_UK", columnNames = {"code", "date_cessation"})
        })
public class Demande extends BaseEntity {
    /*@Column(nullable = false, length = 128)
    @Comment("reference de la demande")
    String reference;*/

    @Column(nullable = false,length = 255)
    @Comment("Objet de la demande")
    private String objet;

    @Column(nullable = false)
    @Comment("Date de la demande")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDemande;

    @Column(length = 128)
    @Comment("lieu de la demande")
    private String lieu;

    @Comment("Structure bénéficiaire")
    //@ManyToOne(optional = false,cascade = CascadeType.ALL)
   // @JoinColumn(name = "id")
    private StructureTitulaire structureTitulaire;


    @Comment("Statut: Saisie, accord,  refus ou accord exceptionnel")
    //@ManyToOne(optional = false,cascade = CascadeType.ALL)
    //@JoinColumn(name = "id")
    private Statut statut;


   //@OneToOne(optional = false, mappedBy = "demande", cascade = CascadeType.ALL)
    private Fiche fiche;

}
