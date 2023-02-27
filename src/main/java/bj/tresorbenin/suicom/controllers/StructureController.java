package bj.tresorbenin.suicom.controllers;

import bj.tresorbenin.suicom.entities.StructureTitulaire;
import bj.tresorbenin.suicom.entities.referentiels.Banque;
import bj.tresorbenin.suicom.services.StructureTitulaireService;
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
@RequestMapping("/{APP_module:referentiels}/{APP_directory:structures}")
public class StructureController extends MasterController<StructureTitulaire>{
    @Autowired
    private StructureTitulaireService structureTitulaireService;
    @Override
    protected void initForm(Model model, String id) throws Exception {
        super.initForm(model, id);
    }

    @Override
    protected void showCreateForm(Model model) throws Exception {
        pageTitle = "MSG.title.structures.titulaires.create";
    }

    @Override
    protected void showUpdateForm(Model model, StructureTitulaire entity) throws Exception {
        pageTitle = "MSG.title.structures.titulaires.edit";
    }

    @Override
    public void showList(Model model) throws Exception {
        pageTitle = "MSG.title.structures.titulaires.list";
        entities = structureTitulaireService.getAll();
        super.showList(model);
    }
    @Override
    public void doGetCredentialsSession() {

    }

    @Override
    protected void find(Model model, HttpServletRequest request, Map<String, String> params) {

    }

    @Override
    public void delete(Model model, Object id) throws Exception {
        structureTitulaireService.delete(Long.valueOf(id.toString()));
        redirectView();
    }

    @Override
    public void insert(Model model, StructureTitulaire form) throws Exception {
        structureTitulaireService.create(form);
        redirectView();
    }

    @Override
    public void update(Model model, StructureTitulaire form) throws Exception {
        structureTitulaireService.update(form);
        redirectView();
    }
    @Override
    public StructureTitulaire getById(Object id) {
        return structureTitulaireService.get(id);
    }

    @Override
    public void beforePersist(StructureTitulaire entity) throws Exception {
    }
}
