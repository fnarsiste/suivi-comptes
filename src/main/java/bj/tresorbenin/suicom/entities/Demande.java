package bj.tresorbenin.suicom.entities;

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
                ,@Index(name = "de_referenceFiche_ix", columnList = "referenceFiche")
                ,@Index(name = "de_refLettre_ix", columnList = "refLettre")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "DEMANDE_UK", columnNames = {"code", "date_cessation"})
        })

public class Demande extends BaseEntity {
    /*
    @Column(nullable = false, length = 128)
    @Comment("reference de la demande")
    String reference;
    */
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
    //private Fiche fiche;                // A enlever après



                                            /**********Fiche********/

    @Column(length = 128)
    @Comment("reference de la fiche")
    String referenceFiche;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Comment("Date de création de la fiche")
    private Date dateFiche;

    @Column(length = 1024)
    @Comment("Recommandations issues du traitement de la demande")
    private String recommandations;


                                    /**********Lettre d'autorisation********/

    @Column(length = 128)
    @Comment("reference de la lettre d'autorisation")
    private String refLettre;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Comment("Date de la lettre d'autorisation")
    private String dateLettre;

    @Column(length = 255)
    @Comment("Objet de la lettre d'autorisation")
    private String objetLettre;

    @Column(length = 1024)
    @Comment("Limites de l'autorisation ")
    private String limites;


    @Column(length = 128)
    @Comment("lieu de la lettre d'autorisation")
    private String lieuLettre;

}
