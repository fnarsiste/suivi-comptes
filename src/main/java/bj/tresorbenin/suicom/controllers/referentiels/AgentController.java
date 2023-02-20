package bj.tresorbenin.suicom.controllers.referentiels;

import bj.tresorbenin.suicom.controllers.MasterController;
import bj.tresorbenin.suicom.entities.referentiels.Agent;
import bj.tresorbenin.suicom.services.referentiels.AgentService;
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
@RequestMapping("/{APP_module:referentiels}/{APP_directory:agents}")
public class AgentController extends MasterController<Agent> {
    @Autowired
    private AgentService agentService;

    @Override
    public void beforePersist(Agent entity) throws Exception {

    }

    @Override
    protected void initForm(Model model, String id) throws Exception {
        super.initForm(model, id);
    }

    @Override
    protected void showCreateForm(Model model) throws Exception {
        pageTitle = "MSG.title.agents.create";
    }

    @Override
    protected void showUpdateForm(Model model, Agent entity) throws Exception {
        pageTitle = "MSG.title.agents.edit";
    }

    @Override
    public void showList(Model model) throws Exception {
        pageTitle = "MSG.title.agents.list";
        entities = agentService.getAll();
        super.showList(model);
    }

    @Override
    public void insert(Model model, Agent form) throws Exception {
        agentService.create(form);
        redirectView();
    }

    @Override
    public void update(Model model, Agent form) throws Exception {
        agentService.update(form);
        redirectView();
    }

    @Override
    public void delete(Model model, Object id) throws Exception {
        agentService.delete(Long.valueOf(id.toString()));
    }

    @Override
    public Agent getById(Object id) {
        return agentService.get(id);
    }

    @Override
    protected void find(Model model, HttpServletRequest request, Map<String, String> params) {
    }

    @Override
    public void doGetCredentialsSession() {
    }
}
