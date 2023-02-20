package bj.tresorbenin.suicom.controllers.referentiels;

import bj.tresorbenin.suicom.controllers.MasterController;
import bj.tresorbenin.suicom.entities.referentiels.Statut;
import bj.tresorbenin.suicom.services.referentiels.StatutService;
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
@RequestMapping("/{APP_module:referentiels}/{APP_directory:statuts}")
public class StatutController extends MasterController<Statut> {
    @Autowired
    private StatutService statutService;

    @Override
    public void beforePersist(Statut entity) throws Exception {

    }

    @Override
    protected void initForm(Model model, String id) throws Exception {
        super.initForm(model, id);
    }

    @Override
    protected void showCreateForm(Model model) throws Exception {
        pageTitle = "MSG.title.statuts.create";
    }

    @Override
    protected void showUpdateForm(Model model, Statut entity) throws Exception {
        pageTitle = "MSG.title.statuts.edit";
    }

    @Override
    public void showList(Model model) throws Exception {
        pageTitle = "MSG.title.statuts.list";
        entities = statutService.getAll();
        super.showList(model);
    }

    @Override
    public void insert(Model model, Statut form) throws Exception {
        statutService.create(form);
        redirectView();
    }

    @Override
    public void update(Model model, Statut form) throws Exception {
        statutService.update(form);
        redirectView();
    }

    @Override
    public void delete(Model model, Object id) throws Exception {
        statutService.delete(Long.valueOf(id.toString()));
    }

    @Override
    public Statut getById(Object id) {
        return statutService.get(id);
    }

    @Override
    protected void find(Model model, HttpServletRequest request, Map<String, String> params) {
    }

    @Override
    public void doGetCredentialsSession() {
    }
}
