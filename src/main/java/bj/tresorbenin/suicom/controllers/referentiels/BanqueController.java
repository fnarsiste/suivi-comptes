package bj.tresorbenin.suicom.controllers.referentiels;

import bj.tresorbenin.suicom.controllers.BaseController;
import bj.tresorbenin.suicom.entities.Banque;
import bj.tresorbenin.suicom.services.BanqueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/{APP_module:referentiels}/{APP_directory:banques}")
//public class BanqueController extends MasterController<Banque> { //} extends BaseController<Banque> {
public class BanqueController extends BaseController<Banque> {
    private BanqueService banqueService;

    public BanqueController(BanqueService banqueService) {
        super();
        this.banqueService = banqueService;
    }

    @Override
    @GetMapping("/nouveau")
    public String showCreateOrUpdateForm(Model model, Banque entity) {
        entity=new Banque();
        model.addAttribute("CONTENT_TITLE", "Nouvelle banque");
        model.addAttribute("entity", entity);
        return "admin/banques/form";
    }

    @Override
    @GetMapping("/modifier")
    public String showCreateOrUpdateForm(Model model, Long banqueId) {
        Banque entity = new Banque();
        if(banqueId!=null)
            entity=getById(banqueId);
        model.addAttribute("entity",entity);
        return "admin/banques/form";
    }

    @Override
    @PostMapping("/enregistrer")
    public String update(Model model, Banque entity) {
        banqueService.save(entity);
       // log.info("Code: " + entity.getCode() + "; Libelle: " + entity.getLibelle() + "; Adresse:" + entity.getAdresse());
        return "redirect:liste";
    }

    public String delete(Model model, Banque entity) {
        banqueService.delete(entity);
        return "redirect:liste";
    }

    @Override
    @GetMapping("/supprimer")
    public String delete(Model model, Long banqueId) {
        banqueService.deleteById(banqueId);
        return "redirect:liste";
    }

    @Override
    @GetMapping({"/", "/liste"})
    public String showListe(Model model) {
        List<Banque> banques=banqueService.findAll();
        model.addAttribute("CONTENT_TITLE", "Liste des banques");
        model.addAttribute("List", banques);
        return "admin/banques/liste";
    }

    @Override
    public Banque getByName(String nom) {
        return null;
    }

    @Override
    public Banque getById(Long id) {
        return banqueService.findById(id);
    }
}