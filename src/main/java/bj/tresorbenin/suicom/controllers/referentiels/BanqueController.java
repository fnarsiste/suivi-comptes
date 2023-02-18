package bj.tresorbenin.suicom.controllers.referentiels;

import bj.tresorbenin.suicom.controllers.MasterController;
import bj.tresorbenin.suicom.entities.referentiels.Banque;
import bj.tresorbenin.suicom.services.referentiels.BanqueService;
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
@RequestMapping("/{APP_module:referentiels}/{APP_directory:banques}")
public class BanqueController extends MasterController<Banque> {
    @Autowired
    private BanqueService banqueService;

    @Override
    public void beforePersist(Banque entity) throws Exception {

    }

    @Override
    protected void initForm(Model model, String id) throws Exception {
        super.initForm(model, id);
    }

    @Override
    protected void showCreateForm(Model model) throws Exception {
        pageTitle = "MSG.title.banques.create";
    }

    @Override
    protected void showUpdateForm(Model model, Banque entity) throws Exception {
        pageTitle = "MSG.title.banques.edit";
    }

    @Override
    public void showList(Model model) throws Exception {
        pageTitle = "MSG.title.banques.list";
        entities = banqueService.getAll();
        super.showList(model);
    }

    @Override
    public void insert(Model model, Banque form) throws Exception {
        banqueService.create(form);
        redirectView();
    }

    @Override
    public void update(Model model, Banque form) throws Exception {
        banqueService.update(form);
        redirectView();
    }

    @Override
    public void delete(Model model, Object id) throws Exception {
        banqueService.delete(Long.valueOf(id.toString()));
    }

    @Override
    public Banque getById(Object id) {
        return banqueService.get(id);
    }

    @Override
    protected void find(Model model, HttpServletRequest request, Map<String, String> params) {
    }

    @Override
    public void doGetCredentialsSession() {
    }
}
