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

import java.util.List;
import java.util.Map;

import static bj.tresorbenin.suicom.utils.ConstantUtils.CATCH_ERROR;
import static bj.tresorbenin.suicom.utils.JavaUtils.getParams;

@Slf4j
@Controller
@SuppressWarnings("all")

@RequestMapping("/{APP_module:demandes}/{APP_directory:enregistrement}")
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
        pageTitle = "MSG.title.demandes.edit";
    }

    @Override
    public void showList(Model model) throws Exception {
        pageTitle = "MSG.title.demandes.list";
        entities = demandeService.getAll();
        super.showList(model);
    }

    @Override
    protected void find(Model model, HttpServletRequest request, Map<String, String> params) {
        String action = getParams(params, "action");
        String dateDebut;
        String dateFin;

        model.addAttribute("ACTION", action);
        switch (action) {
            case "search":
                dateDebut= getParams(params, "dateDebut");
                dateFin= getParams(params, "dateFin");
                if(JavaUtils.notNullString(dateDebut) && JavaUtils.notNullString(dateFin)){
                    //
                    entities=demandeService.searchByPeriod(dateDebut, dateFin);
                    model.addAttribute("DATE_DEBUT", dateDebut);
                    model.addAttribute("LIST", entities);
                    model.addAttribute("DATE_FIN", dateFin);
                }
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
        if(statut==null) throw new Exception("Pas d'enregistrement de type nouveau statut");
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
        if(form.getStatut()!=null)
            statut=statutService.getById(form.getStatut().getId());
        if(statut!=null)
            form.setStatut(statut);
        demandeService.update(form);
        redirectView();
    }

    @Override
    public void beforePersist(Demande entity) throws Exception {

    }
    @Override
    public Demande getById(Object id) {
        return demandeService.get(id);
    }
}
