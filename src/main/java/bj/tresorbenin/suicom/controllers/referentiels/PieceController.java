package bj.tresorbenin.suicom.controllers.referentiels;

import bj.tresorbenin.suicom.controllers.MasterController;
import bj.tresorbenin.suicom.entities.referentiels.Piece;
import bj.tresorbenin.suicom.entities.referentiels.Statut;
import bj.tresorbenin.suicom.services.referentiels.PieceService;
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
@RequestMapping("/{APP_module:referentiels}/{APP_directory:pieces}")
public class PieceController extends MasterController<Piece> {
    @Autowired
    private PieceService pieceService;

    @Override
    protected void initForm(Model model, String id) throws Exception {
        super.initForm(model, id);
    }

    @Override
    protected void showCreateForm(Model model) throws Exception {
        pageTitle = "MSG.title.pieces.create";
    }

    @Override
    protected void showUpdateForm(Model model, Piece entity) throws Exception {
        pageTitle = "MSG.title.pieces.edit";
    }

    @Override
    public void showList(Model model) throws Exception {
        pageTitle = "MSG.title.pieces.list";
        entities = pieceService.getAll();
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
        pieceService.delete(Long.valueOf(id.toString()));
    }

    @Override
    public void insert(Model model, Piece form) throws Exception {
        pieceService.create(form);
        redirectView();
    }

    @Override
    public void update(Model model, Piece form) throws Exception {
        pieceService.update(form);
        redirectView();
    }

    @Override
    public void beforePersist(Piece entity) throws Exception {

    }
    @Override
    public Piece getById(Object id) {
        return pieceService.get(id);
    }
}


