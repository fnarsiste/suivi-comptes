package bj.tresorbenin.suicom.controllers;

import bj.tresorbenin.suicom.entities.Demande;
import bj.tresorbenin.suicom.entities.StructureTitulaire;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

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

    }

    @Override
    public void delete(Model model, Object id) throws Exception {
        demandeService.delete(Long.valueOf(id.toString()));
    }

    @Override
    public void insert(Model model, Demande form) throws Exception {
        Statut statut=statutService.getByCode(ConstantUtils.STATUT_DEMANDE_SAISIE);
        if(statut==null) throw new Exception("Pas d'enregistrement de type nouveau statut");
        StructureTitulaire structureTitulaire=sttService.getByCode("TC01");
        if(structureTitulaire==null) throw new Exception("TC01 n'existe pas");
        form.setStatut(statut);
        form.setStructureTitulaire(structureTitulaire);
        demandeService.create(form);
        redirectView();
    }

    @Override
    public void update(Model model, Demande form) throws Exception {
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
