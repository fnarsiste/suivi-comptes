package bj.tresorbenin.suicom.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.lang.reflect.ParameterizedType;

@Controller
@SuppressWarnings("all")
@SessionAttributes({"DATE_DEBUT", "DATE_FIN"})
public abstract class MasterController<T> extends GlobalVars<T> {

   public MasterController() {
      //addAttribute("CONTENT_TITLE", "Titre de la page");
      clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
   }
}
