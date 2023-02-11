package bj.tresorbenin.suicom.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

public abstract class BaseController <T>{
    public abstract String showCreateOrUpdateForm(Model model, T entity);

    public abstract String showCreateOrUpdateForm(Model model, Long id);

    public abstract String update(Model model, T entity);

    public abstract String delete(Model model, T entity);

    public abstract String delete(Model model, Long id);
    public abstract String showListe(Model model);

    public abstract T getByName(String nom);
    public abstract T getById(Long id);
}
