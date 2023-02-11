package bj.tresorbenin.suicom.controllers;

import bj.tresorbenin.suicom.entities.Banque;
import bj.tresorbenin.suicom.services.BanqueService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/administration/banques/")
public class BanqueController extends BaseController<Banque> {
    private static final Log log= LogFactory.getLog(BanqueController.class);
    @Autowired
    private BanqueService banqueService;

    @Override
    @GetMapping("/nouveau")
    public String showCreateOrUpdateForm(Model model, Banque entity) {
        entity=new Banque();
        log.info("id: " +entity.getId()+"; Code: " + entity.getCode() + "; Libelle: " + entity.getLibelle() + "; Adresse:" + entity.getAdresse());
        model.addAttribute("entity",entity);
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
        log.info("Code: " + entity.getCode() + "; Libelle: " + entity.getLibelle() + "; Adresse:" + entity.getAdresse());
        return "redirect:liste";
    }

    @Override
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
        /*
         *
         * */
        List<Banque> banques=banqueService.findAll();
        model.addAttribute("List", banques);
        return "/admin/banques/liste";
    }

    @Override
    public Banque getByName(String nom) {
        return null;
    }

    @Override
    public Banque getById(Long id) {
        //toto
        return banqueService.findById(id);
    }
}
