package bj.tresorbenin.suicom.controllers.administration;

import bj.tresorbenin.suicom.controllers.MasterController;
import bj.tresorbenin.suicom.entities.administration.Utilisateur;
import bj.tresorbenin.suicom.entities.referentiels.Agent;
import bj.tresorbenin.suicom.services.administration.UtilisateurService;
import bj.tresorbenin.suicom.services.referentiels.AgentService;
import bj.tresorbenin.suicom.utils.JavaUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

import static bj.tresorbenin.suicom.utils.JavaUtils.getParams;

@Slf4j
@Controller
@SuppressWarnings("all")
@RequestMapping("/{APP_module:administration}/{APP_directory:users}")
public class UtilisateurController extends MasterController<Utilisateur> {
    @Autowired
    private UtilisateurService userService;

    @Autowired
    private AgentService agentService;

    @Override
    protected void initForm(Model model, String id) throws Exception {
        List<Agent> agents = agentService.getAccountableAgent();
        model.addAttribute("agents", agents);
        model.addAttribute("show_form", !agents.isEmpty());
        super.initForm(model, id);
    }

    @Override
    protected void showCreateForm(Model model) throws Exception {
        pageTitle = "MSG.title.utilisateur.create";
    }

    @Override
    protected void showUpdateForm(Model model, Utilisateur entity) throws Exception {
        pageTitle = "MSG.title.utilisateur.edit";
    }

    @Override
    public void showList(Model model) throws Exception {
        pageTitle = "MSG.title.utilisateur.list";
        entities = userService.getAll();
        super.showList(model);
    }

    @Override
    public void beforePersist(Utilisateur entity) throws Exception {
        Agent agent = agentService.get(entity.getAgent().getId());
        entity.setAgent(agent);
        entity.setOtpCode(JavaUtils.generateOtpCode(6));
        agent.setUser(entity);
        entity.setActive(false);
    }

    @Override
    public void insert(Model model, Utilisateur form) throws Exception {
        beforePersist(form);
        userService.create(form);
        redirectView();
    }

    @Override
    public void update(Model model, Utilisateur form) throws Exception {
        userService.update(form);
        redirectView();
    }

    @Override
    public void delete(Model model, Object id) throws Exception {
        userService.delete(Long.valueOf(id.toString()));
    }

    @Override
    public Utilisateur getById(Object id) {
        return userService.get(id);
    }

    @Override
    protected void find(Model model, HttpServletRequest request, Map<String, String> params) {
        String action = getParams(params, "action");
        model.addAttribute("ACTION", action);
        switch (action) {
            case "show-profil":
                long id = Long.parseLong(getParams(params, "userid"));
                Utilisateur user = userService.get(id);
                model.addAttribute("user", user);
                model.addAttribute("chk_show_profil_dialog", true);
                break;
        }
        internView(model);
    }

    @Override
    public void doGetCredentialsSession() {
    }
}
