package bj.tresorbenin.suicom.controllers;

import bj.tresorbenin.suicom.entities.Demande;
import bj.tresorbenin.suicom.entities.StructureTitulaire;
import bj.tresorbenin.suicom.entities.administration.Role;
import bj.tresorbenin.suicom.entities.administration.User;
import bj.tresorbenin.suicom.entities.administration.UserRole;
import bj.tresorbenin.suicom.entities.referentiels.Statut;
import bj.tresorbenin.suicom.services.DemandeService;
import bj.tresorbenin.suicom.services.FicheService;
import bj.tresorbenin.suicom.services.LettreAutorisationService;
import bj.tresorbenin.suicom.services.StructureTitulaireService;
import bj.tresorbenin.suicom.services.referentiels.StatutService;
import bj.tresorbenin.suicom.utils.ConstantUtils;
import bj.tresorbenin.suicom.utils.JavaUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static bj.tresorbenin.suicom.utils.ConstantUtils.CATCH_ERROR;
import static bj.tresorbenin.suicom.utils.JavaUtils.getParams;

@Slf4j
@Controller
@SuppressWarnings("all")

@RequestMapping("/{APP_module:demandes}/{APP_directory:enregistrement|traitement|resultat}")
//@SessionAttributes({"STATUT_DEMANDE_SAISIE"})
public class DemandeController extends MasterController<Demande>{

    @Autowired
    private DemandeService demandeService;
    @Autowired
    private FicheService ficheService;
    @Autowired
    private LettreAutorisationService autorisationService;
    @Autowired
    private StatutService statutService;
    @Autowired
    private StructureTitulaireService sttService;
    @Override
    public void doGetCredentialsSession() {

    }
    @Override
    protected void initForm(Model model, String id) throws Exception {
        List<StructureTitulaire> structureTitulaires = sttService.getAll();
        model.addAttribute("structures", structureTitulaires);
        super.initForm(model, id);
    }
    @Override
    protected void showCreateForm(Model model) throws Exception {
        pageTitle = "MSG.title.demandes.create";
    }

    @Override
    protected void showUpdateForm(Model model, Demande entity) throws Exception {
        if(APP_directory.equals("enregistrement"))
            pageTitle = "MSG.title.demandes.edit";
        else if(APP_directory.equals("traitement"))
            pageTitle = "MSG.title.fiches.edit";
        else if(APP_directory.equals("resultat")){
            pageTitle = "MSG.title.resultats.edit";
            //List<String> codesStatutsResultat = ConstantUtils.getStatutsResultat();
            model.addAttribute("STATUTS_RESULTATS", getStatutsOfDemandesResults());
        }
        APP_directory="enregistrement";
    }

    @Override
    public void showList(Model model) throws Exception {
        pageTitle = "MSG.title.demandes.list";
        model.addAttribute("STATUT_DEM_SAISIE",ConstantUtils.STATUT_DEMANDE_SAISIE);
        entities = demandeService.getAll();
        super.showList(model);
    }

    @Override
    protected void find(Model model, HttpServletRequest request, Map<String, String> params) {
        String action = getParams(params, "action");
        String dateDebut;
        String dateFin;

        model.addAttribute("ACTION", action);
        model.addAttribute("STATUT_DEM_SAISIE",ConstantUtils.STATUT_DEMANDE_SAISIE);
        switch (action) {
            case "search":
                dateDebut= getParams(params, "dateDebut");
                dateFin= getParams(params, "dateFin");
                if(JavaUtils.notNullString(dateDebut) && JavaUtils.notNullString(dateFin)){
                    //
                    entities=demandeService.searchByPeriod(dateDebut, dateFin);
                    //model.addAttribute("LIST", entities);
                    model.addAttribute("DATE_DEBUT", dateDebut);
                    model.addAttribute("DATE_FIN", dateFin);
                }else
                    entities=demandeService.getAll();
                model.addAttribute("LIST", entities);
                model.addAttribute("STATUT_DEMANDE_SAISIE",ConstantUtils.STATUT_DEMANDE_SAISIE);

                break;
        }
        internView(model);
    }

    @Override
    public void delete(Model model, Object id) throws Exception {
        demandeService.delete(Long.valueOf(id.toString()));
    }

    @Override
    public void insert(Model model, Demande form) throws Exception {
        Statut statut=statutService.getByCode(ConstantUtils.STATUT_DEMANDE_SAISIE);
        if(statut==null) throw new Exception("Pas d'enregistrement de type Demande saisie");
         //StructureTitulaire structureTitulaire=sttService.getByCode("TDATL");
       StructureTitulaire stl = new StructureTitulaire();
        if(form.getStructureTitulaire()!=null)
            stl=sttService.getById(form.getStructureTitulaire().getId());
        if(stl!=null)
            form.setStructureTitulaire(stl);
        //if(structureTitulaire==null) throw new Exception("TDATL n'existe pas");
        form.setStatut(statut);
        //form.setStructureTitulaire(structureTitulaire);
        form.setStructureTitulaire(stl);
        demandeService.create(form);
        redirectView();
    }

    @Override
    public void update(Model model, Demande form) throws Exception {
        Statut statut = new Statut();
        Demande demande=new Demande();
        if(APP_directory.equals("enregistrement")) {
            if (form.getStatut() != null)
                statut = statutService.getById(form.getStatut().getId());
            if (statut != null)
                form.setStatut(statut);
            demandeService.update(form);
            redirectView();
        } else if (APP_directory.equals("traitement")) {
            demande=demandeService.getById(form.getId());
            statut=statutService.getByCode(ConstantUtils.STATUT_DEMANDE_TRAITEE);
            if(statut==null) throw new Exception("Pas d'enregistrement de type Demande traitée");
            demande.setStatut(statut);
            demande.setReferenceFiche(form.getReferenceFiche());
            demande.setDateFiche(form.getDateFiche());
            demande.setRecommandations(form.getRecommandations());
            demandeService.update(demande);
            //redirectOtherModuleView(APP_module+"/enregistrement/liste");
            APP_directory="enregistrement";
        } else if (APP_directory.equals("resultat")) {
            demande=demandeService.getById(form.getId());
            statut=statutService.getById(form.getStatut().getId());
            if(statut==null) throw new Exception("Pas de statut de résultat");
            demande.setStatut(statut);
            demande.setRefLettre(form.getRefLettre());
            demande.setDateLettre(form.getDateLettre());
            demande.setObjetLettre(form.getObjetLettre());
            demande.setLimites(form.getLimites());
            demande.setLieuLettre(form.getLieuLettre());
            demandeService.update(demande);
            //redirectOtherModuleView(APP_module+"/enregistrement/liste");
            APP_directory="enregistrement";
        }
        redirectView();
    }

    @Override
    public void beforePersist(Demande entity) throws Exception {

    }
    @Override
    public Demande getById(Object id) {
        return demandeService.get(id);
    }
    public List<Statut> getStatutsOfDemandesResults(){
        List<Statut> statutsList=new ArrayList<Statut>();
        Statut statut = null;
        for (String codeStatut:ConstantUtils.getStatutsResultat()) {
            if(JavaUtils.notNullString(codeStatut))
                statut=statutService.getByCode(codeStatut);
            if(statut!=null)
                statutsList.add(statut);
        }
        return statutsList;
    }
}
