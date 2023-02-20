package bj.tresorbenin.suicom.controllers.referentiels;

import bj.tresorbenin.suicom.controllers.MasterController;
import bj.tresorbenin.suicom.entities.referentiels.Fonction;
import bj.tresorbenin.suicom.services.referentiels.FonctionService;
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
@RequestMapping("/{APP_module:referentiels}/{APP_directory:fonctions}")
public class FonctionController extends MasterController<Fonction> {
    @Autowired
    private FonctionService fonctionService;

    @Override
    public void beforePersist(Fonction entity) throws Exception {

    }

    @Override
    protected void initForm(Model model, String id) throws Exception {
        super.initForm(model, id);
    }

    @Override
    protected void showCreateForm(Model model) throws Exception {
        pageTitle = "MSG.title.fonctions.create";
    }

    @Override
    protected void showUpdateForm(Model model, Fonction entity) throws Exception {
        pageTitle = "MSG.title.fonctions.edit";
    }

    @Override
    public void showList(Model model) throws Exception {
        pageTitle = "MSG.title.fonctions.list";
        entities = fonctionService.getAll();
        super.showList(model);
    }

    @Override
    public void insert(Model model, Fonction form) throws Exception {
        fonctionService.create(form);
        redirectView();
    }

    @Override
    public void update(Model model, Fonction form) throws Exception {
        fonctionService.update(form);
        redirectView();
    }

    @Override
    public void delete(Model model, Object id) throws Exception {
        fonctionService.delete(Long.valueOf(id.toString()));
    }

    @Override
    public Fonction getById(Object id) {
        return fonctionService.get(id);
    }

    @Override
    protected void find(Model model, HttpServletRequest request, Map<String, String> params) {
    }

    @Override
    public void doGetCredentialsSession() {
    }
}
