package bj.tresorbenin.suicom.controllers.administration;

import bj.tresorbenin.suicom.controllers.MasterController;
import bj.tresorbenin.suicom.entities.administration.Role;
import bj.tresorbenin.suicom.entities.administration.User;
import bj.tresorbenin.suicom.entities.administration.UserRole;
import bj.tresorbenin.suicom.entities.referentiels.Agent;
import bj.tresorbenin.suicom.services.administration.UserService;
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
@RequestMapping("/{APP_module:administration}/{APP_directory:users}")
public class UserController extends MasterController<User> {
    @Autowired
    private UserService userService;

    @Override
    protected void initForm(Model model, String id) throws Exception {
        List<Agent> agents = userService.getAgentService().getAccountableAgent();
        model.addAttribute("agents", agents);
        model.addAttribute("show_form", !agents.isEmpty());
        super.initForm(model, id);
    }

    @Override
    protected void showCreateForm(Model model) throws Exception {
        pageTitle = "MSG.title.utilisateur.create";
    }

    @Override
    protected void showUpdateForm(Model model, User entity) throws Exception {
        pageTitle = "MSG.title.utilisateur.edit";
    }

    @Override
    public void showList(Model model) throws Exception {
        pageTitle = "MSG.title.utilisateur.list";
        entities = userService.getAll();
        super.showList(model);
    }

    @Override
    public void beforePersist(User entity) throws Exception {
        Agent agent = userService.getAgentService().get(entity.getAgent().getId());
        entity.setAgent(agent);
        entity.setOtpCode(JavaUtils.generateOtpCode(6));
        agent.setUser(entity);
        entity.setActive(false);
    }

    @Override
    public void insert(Model model, User form) throws Exception {
        beforePersist(form);
        userService.create(form);
        redirectView();
    }

    @Override
    public void update(Model model, User form) throws Exception {
        userService.update(form);
        redirectView();
    }

    @Override
    public void delete(Model model, Object id) throws Exception {
        userService.delete(Long.valueOf(id.toString()));
    }

    @Override
    public User getById(Object id) {
        return userService.get(id);
    }

    @Override
    protected void find(Model model, HttpServletRequest request, Map<String, String> params) {
        String action = getParams(params, "action");
        model.addAttribute("ACTION", action);
        User user;
        switch (action) {
            case "show-profil":
                long id = Long.parseLong(getParams(params, "userid"));
                user = userService.get(id);
                List<UserRole> userRoles = userService.getRoleByUser(user);
                List<Role> notActiveRoles = userService.getNotActiveRoleByUser(user);
                model.addAttribute("user", user);
                model.addAttribute("userRoles", userRoles);
                model.addAttribute("hasRole", !userRoles.isEmpty());
                model.addAttribute("notActiveRoles", notActiveRoles);
                model.addAttribute("hasInactRole", !notActiveRoles.isEmpty());
                model.addAttribute("chk_show_profil_dialog", true);
                break;
            case "save-profil-data":
                log.info(params.toString());
                JSONObject jsonParam = null;
                try {
                    String userId = getParams(params, "user");
                    jsonParam = (JSONObject) (new JSONParser()).parse(getParams(params, "json"));
                    JSONObject report = new JSONObject(userService.saveUserRoles(userId, jsonParam));
                    model.addAttribute("report", report);
                    model.addAttribute("chk_profil_saved", true);
                } catch (Exception e) {
                    log.info(e.getMessage());
                    model.addAttribute("HAS_ERROR", !true);
                    model.addAttribute(CATCH_ERROR, true);
                    model.addAttribute("ERROR_TXT_MSG", e.getMessage());
                }
                break;
        }
        internView(model);
    }

    @Override
    public void doGetCredentialsSession() {
    }
}
