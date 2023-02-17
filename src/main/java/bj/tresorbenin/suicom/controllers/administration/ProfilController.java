package bj.tresorbenin.suicom.controllers.administration;

import bj.tresorbenin.suicom.controllers.MasterController;
import bj.tresorbenin.suicom.entities.administration.Profil;
import bj.tresorbenin.suicom.services.administration.ProfilService;
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
@RequestMapping("/{APP_module:administration}/{APP_directory:profils}")
public class ProfilController extends MasterController<Profil> {
    @Autowired
    private ProfilService profilService;

    @Override
    protected void initForm(Model model, String id) throws Exception {
        super.initForm(model, id);
    }

    @Override
    protected void showCreateForm(Model model) throws Exception {
        pageTitle = "MSG.title.profils.create";
    }

    @Override
    protected void showUpdateForm(Model model, Profil entity) throws Exception {
        pageTitle = "MSG.title.profils.edit";
    }

    @Override
    public void showList(Model model) throws Exception {
        pageTitle = "MSG.title.profils.list";
        entities = profilService.getAll();
        super.showList(model);
    }

    @Override
    public void insert(Model model, Profil form) throws Exception {
        profilService.create(form);
        redirectView();
    }

    @Override
    public void update(Model model, Profil form) throws Exception {
        profilService.update(form);
        redirectView();
    }

    @Override
    public void delete(Model model, Object id) throws Exception {
        profilService.delete(Long.valueOf(id.toString()));
    }

    @Override
    public Profil getById(Object id) {
        return profilService.get(id);
    }

    @Override
    protected void find(Model model, HttpServletRequest request, Map<String, String> params) {
    }

    @Override
    public void doGetCredentialsSession() {
    }
}
