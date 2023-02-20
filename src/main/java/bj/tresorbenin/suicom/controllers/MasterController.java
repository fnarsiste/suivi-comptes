package bj.tresorbenin.suicom.controllers;

import bj.tresorbenin.suicom.session.MapFlash;
import bj.tresorbenin.suicom.utils.ConstantUtils;
import bj.tresorbenin.suicom.utils.JavaUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import static bj.tresorbenin.suicom.utils.ConstantUtils.*;

@Slf4j
@Controller
@SuppressWarnings("all")
@SessionAttributes({"DATE_DEBUT", "DATE_FIN"})
public abstract class MasterController<T> extends GlobalVars<T> {

    public abstract void doGetCredentialsSession();

    protected abstract void find(Model model, HttpServletRequest request, Map<String, String> params);

    public abstract void delete(Model model, Object id) throws Exception;

    public abstract void insert(Model model, T form) throws Exception;

    public abstract void update(Model model, T form) throws Exception;

    public abstract void beforePersist(T entity) throws Exception;

    public MasterController() {
        clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @GetMapping({
            "/{id}/{APP_ecran:modifier|liste|find|differe}",
            "/{APP_ecran:nouveau|liste|find}"
    })
    public String seutp(@ModelAttribute("List") T list, BindingResult result, Model model,
                        HttpServletRequest request, @RequestParam Map<String, String> params,
                        @ModelAttribute("FLASH") MapFlash flashObject, RedirectAttributes redirectAttributes)
            throws Exception {
        String secureLink = "VALID"; // TODO "module de securelink";
        if(!"VALID".equals(secureLink)) return  secureLink;
        doSetFlashObject(flashObject);
        doCommonCall(model, request, HttpMethod.GET, params, "MASTER_SETUP");
        try {
            switch (APP_ecran) {
                /** initialiser le formulaire de creation/modification **/
                case SCREEN_FORM:
                    this.currentScreen = APP_ecran;
                    switch (APP_action) {
                        case CRUD_CREATE:
                            id = null;
                        case CRUD_UPDATE:
                        case CRUD_DIFFERE:
                            initForm(model, id);
                            break;
                    }
                    break;
                case SCREEN_LIST:
                    this.currentScreen = APP_ecran;
                    String createLink = String.format("/%s/nouveau", getMiddleUrl(true));
                    model.addAttribute("LIEN_CREATE", createLink);
                    /** gestion des boutons actions per User **/
                   // doUserPrivilege(model);
                    switch (APP_action) {
                        case CRUD_DELETE:
                            this.modelTemplate = model;
                            operationMsg = ConstantUtils.MSG_OPERATION_DELETE;
                            delete(model, id);
                            break;
                        case CRUD_READ_ALL:
                            /** Afficher la liste avec les donnees **/
                            showList(model);
                            break;
                    }
                    break;
                /** Find : Pour le traitement de recherche (ajax) **/
                case SCREEN_FIND:
                    /** call find du child class **/
                    find(model, request, params);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            if (currentScreen.equals(SCREEN_FORM))
                doInitForm(model, id, true, true);
            else if (currentScreen.equals(SCREEN_LIST))
                doShowList(model);
            doCatchExceptionHandler(result, model, e, SETUP_ERROR);
        }
        pageRoute = "/" + getRoute();
        model.addAttribute("pageRoute", pageRoute);
        return this.view;
    }

    @PostMapping(
            value = { "/{APP_action:nouveau}", "/{id}/{APP_action:modifier}", "/{id}/{APP_action:differe}" })
    public @ResponseBody String doActionsCreateAndUpdate(@Validated @ModelAttribute(MODEL_ATTRIBUTE_FORM) T form, BindingResult result, Model model,
                                                         HttpServletRequest request, @RequestParam Map<String, String> params,
                                                         RedirectAttributes redirectAttrib) throws Exception {
        return doActions(form, result, model, request, params, redirectAttrib);
    }

    @PostMapping(
            value = { "/{APP_action:find}", "/{id}/{APP_action:find}", "/{id}/{APP_action:find}" })
    public String doActionsFind(@Validated @ModelAttribute(MODEL_ATTRIBUTE_FORM) T form, BindingResult result, Model model, HttpServletRequest request,
                                @RequestParam Map<String, String> params, RedirectAttributes redirectAttrib) throws Exception {
        return doActions(form, result, model, request, params, redirectAttrib);
    }

    @PostMapping("/{id}/{APP_ecran:supprimer}")
    public @ResponseBody String doDeleteRecord(
            Model model, @PathVariable(name = "id") String id,
            @RequestBody Map<String, String> requestBody) throws Exception {
        JSONObject jsonResult = new JSONObject();
        boolean success = false;
        try {
            delete(model, id);
            success = true;
            operationMsg = "Opération de suppression effecuée avec succès.";
        } catch (Exception e) {
            e.printStackTrace();
            operationMsg = String.format("Une s'est produite lors de la suppression<hr>%s - %s", e.getMessage(), e.getLocalizedMessage());
        }
        jsonResult.put("result", success);
        jsonResult.put("message",operationMsg);
        return jsonResult.toJSONString();
    }

    private void doCommonCall(Model model, HttpServletRequest request, HttpMethod httpMethod, Map<String, String> params, String type) {
        this.request = request;
        this.httpMethod = httpMethod;
        String typeEcran = "";
        if (MASTER_SETUP.equals(type))
            typeEcran = JavaUtils.pathParams(request, REQUEST_PARAM_SCREEN);
        else if (MASTER_ACTION.equals(type))
            typeEcran = JavaUtils.pathParams(request, REQUEST_PARAM_ACTION);
        prepend(request, typeEcran);
        doGetInfoFromUrl(request, params);
        doGetCredentialsSession();
        doSetPageInfo(null);
        if (MASTER_SETUP.equals(type)) {
            /** recupere le patron(template) de la page à afficher **/
            doGetTemplate();
            initLabelElement(model);
        }
        model.addAttribute("APP_MODULE", APP_module);
        model.addAttribute("APP_DIRECTORY", APP_directory);
        model.addAttribute("APP_GROUPE", APP_groupe);
        model.addAttribute("APP_ECRAN", APP_ecran);
        model.addAttribute("APP_ACTION", APP_action);
    }

    public String doActions(T form, BindingResult result, Model model, HttpServletRequest request, Map<String, String> params,
                            RedirectAttributes redirectAttrib) throws Exception {
        boolean asyncSubmission = false;
        JSONObject jsonResult = new JSONObject();
        /** isLoggedIn verifie si user isConnected!! **/
        /** hasMenuRight verifie si user has access menu right!! **/
        String secureLink = "VALID"; // TODO secureLogic.handleAccess(APP_module, APP_groupe, APP_directory);
        if ("VALID".equals(secureLink)) {
            doCommonCall(model, request, HttpMethod.POST, params, MASTER_ACTION);
            /** pass objet du formulaire **/
            entity = form;
            try {
                /** necessaire pour interrompe popup with succes **/
                model.addAttribute(CATCH_ERROR, false);
                model.addAttribute(ACTION_ERROR, "MSG.blank");
                switch (APP_action.toLowerCase()) {
                    case CRUD_CREATE:
                        asyncSubmission = true;
                        if (!result.hasErrors()) {
                            this.modelTemplate = model;
                            operationMsg = ConstantUtils.MSG_OPERATION_CREATE;
                            insert(model, entity);
                        } else {
                            this.modelTemplate = null;
                            doInitForm(model, id, true, true);
                        }
                        break;
                    case CRUD_UPDATE:
                    case CRUD_DIFFERE:
                        asyncSubmission = true;
                        if (CRUD_UPDATE.equals(APP_action.toLowerCase()))
                            operationMsg = ConstantUtils.MSG_OPERATION_UPDATE;
                        if (CRUD_DIFFERE.equals(APP_action.toLowerCase()))
                            operationMsg = ConstantUtils.MSG_OPERATION_DIFFERE;
                        if (!result.hasErrors()) {
                            this.modelTemplate = model;
                            update(model, entity);
                        } else {
                            this.modelTemplate = null;
                            doInitForm(model, id, true, true);
                        }
                        break;
                    /** Find : Pour le traitement de recherche (ajax) **/
                    case CRUD_READ_FIND:
                        find(model, request, params);
                        break;
                    default:
                        break;
                }
                if (asyncSubmission)
                    if (!result.hasErrors()) {
                        jsonResult.put("result", "success");
                        jsonResult.put("msgType", JavaUtils.doNVL(operationMsg, "SUCCES"));
                        jsonResult.put("endPage", JavaUtils.getModelMapValue(modelTemplate, "PAGE"));
                    } else {
                        jsonResult.put("result", "error");
                        jsonResult.put("type", "FieldError");
                        jsonResult.put("message", JavaUtils.getMsgPropertiesValue(doHandleResult(result)));
                    }
            } catch (Exception e) {
                doCatchExceptionHandler(result, model, e, CATCH_ERROR);
                doInitForm(model, id, true, true);
                if (asyncSubmission) {
                    jsonResult.put("result", "error");
                    jsonResult.put("type", e.getClass().getSimpleName());
                    //TODO : FINALISER LA METHODE doCatchExceptionHandler POUR NOURRIR result
                    String message = result.getAllErrors().isEmpty() ? "" : result.getAllErrors().get(0).getCode();
                    jsonResult.put("message", JavaUtils.getMsgPropertiesValue(message));
                    model.addAttribute(CATCH_ERROR, false);
                }
            }
        } else
            return secureLink;
        if (asyncSubmission)
            return jsonResult.toJSONString();
        else
            return this.view;
    }

    public void showList(Model model) throws Exception {
        doShowList(model);
    }

    private void doShowList(Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_PAGE_TITLE, pageTitle);
        model.addAttribute(MODEL_ATTRIBUTE_LIST, entities);
        model.addAttribute(MODEL_ATTRIBUTE_FIND_PARTS_DELIMITER, findPartsDelimiter);
        internView(model);
    }

    protected void initForm(Model model, String id) throws Exception {
        doInitForm(model, id, false, false);
    }

    private void doInitForm(Model model, Object id, boolean skipCallForm, boolean hasError) throws Exception {
        try {
            model.addAttribute("lbl_close_btn", "MSG.btn.fermer");
            readonly = id != null;
            view = null;
            if (!skipCallForm)
                operationMsg = null;
            /**** ***/
            if (id == null) {
                if (httpMethod.equals(HttpMethod.GET) && !hasError && !skipCallForm)
                    entity = (T) clazz.newInstance();
                if (!skipCallForm)
                    showCreateForm(model);
            } else {
                if (!httpMethod.equals(HttpMethod.POST) && !hasError && !skipCallForm) {
                    entity = getById(id);
                    if (entity == null) {
                        entity = (T) clazz.newInstance();
                        throw new Exception("MSG.entity.null.error");
                    }
                }
                if (!skipCallForm)
                    showUpdateForm(model, entity);
            }
            model.addAttribute(MODEL_ATTRIBUTE_FORM, entity);
            internView(model, view);
        } catch (Exception e) {
            Exception catchEx = e instanceof Exception ? (Exception) e : new Exception(e);
            throw catchEx;
        } finally {
            model.addAttribute(MODEL_ATTRIBUTE_HEADER_TITLE, headerTitle);
            model.addAttribute(MODEL_ATTRIBUTE_PAGE_TITLE, pageTitle);
            model.addAttribute(MODEL_ATTRIBUTE_READ_ONLY, readonly);
            model.addAttribute(MODEL_ATTRIBUTE_FIND_PARTS_DELIMITER, findPartsDelimiter);
            model.addAttribute("lbl_add_btn", id == null ? "MSG.btn.enregistrer" : "MSG.btn.modifier");
            model.addAttribute("cible", APP_action);
            model.addAttribute("listeUrl", getMiddleUrl(true)+"/liste");
        }
    }

    public void internView(Model model) {
        internView(model, null);
    }

    public void internView(Model model, String pageJsp) {
        if (pageJsp == null)
        /** construit le nom du fichier jsp(ex: Adp + List + .jsp) **/
            pageJsp = APP_directory + Character.toUpperCase(APP_ecran.charAt(0)) + APP_ecran.substring(1);
        /**
         * set la valeur de content_file dans maintemplate
         * (ex:references/AdpList.jsp)
         **/
        model.addAttribute(MODEL_ATTRIBUTE_CONTENT_FILE, getMiddleUrl(false) + "/" + pageJsp);
        model.addAttribute(MODEL_ATTRIBUTE_NAVBAR, navbar);
        /** set la valeur du template **/
        this.view = template; //ajax == null ? template : TEMPLATE_AJAX;
    }

    protected T getById(Object id) throws Exception {
        return null;
    }

    protected void showUpdateForm(Model model, T entity) throws Exception {
    }

    protected void showCreateForm(Model model) throws Exception {
    }

    protected String getMiddleUrl(boolean withSibDirectory) {
        String base = APP_module;
        String baseWithSibDirectory = APP_module + "/" + APP_directory;
        if (withSibDirectory)
            return JavaUtils.notNullString(APP_groupe) ? APP_module + "/" + APP_groupe + "/" + APP_directory : baseWithSibDirectory;
        else
            return JavaUtils.notNullString(APP_groupe) ? APP_module + "/" + APP_groupe : base;
    }

    private void initLabelElement(Model model) {
        model.addAttribute("lbl_code", "MSG.code");
        model.addAttribute("lbl_libelle", "MSG.libelle");
        model.addAttribute("lbl_action", "MSG.action");
        model.addAttribute("lbl_ajouter", "MSG.btn.ajouter");
        model.addAttribute("lbl_niveau", "MSG.niveau");
        model.addAttribute("lbl_type", "MSG.type");
        model.addAttribute("lbl_parent", "MSG.parent");
        model.addAttribute("lbl_description", "MSG.description");
    }

    private String getRoute() {
        return  String.format(
                "%s%s/%s/",
                APP_module,
                APP_groupe==null ? "" : String.format("/%s", APP_groupe),
                APP_directory);
    }

    private void doGetTemplate() {
        if (APP_module.contains("profilchoice"))
            template = TEMPLATE_PROFILCHOICE;
        else {
            template = MAIN_TEMPLATE;
            String calcTemplate = String.format("%s%s", getRoute(), APP_ecran);
            template = calcTemplate;
        }
    }

    private void doSetPageInfo(String pageInfo) {
    }

    private void doGetInfoFromUrl(HttpServletRequest request, Map<String, String> params) {
        ajax = null;
        APP_module = JavaUtils.pathParams(request, REQUEST_PARAM_MODULE);
        APP_groupe = JavaUtils.pathParams(request, REQUEST_PARAM_GROUPE);
        APP_directory = JavaUtils.pathParams(request, REQUEST_PARAM_ENTITY);
        APP_ecran = JavaUtils.pathParams(request, REQUEST_PARAM_SCREEN);
        if(APP_ecran == null) {
            APP_ecran = request.getAttribute(REQUEST_PARAM_SCREEN).toString();
        }
        APP_action = JavaUtils.pathParams(request, REQUEST_PARAM_ACTION);
        if(APP_action == null) {
            APP_action = request.getAttribute(REQUEST_PARAM_ACTION).toString();
        }
        id = JavaUtils.pathParams(request, REQUEST_PARAM_ENTITY_ID);
        ajax = JavaUtils.getParams(params, REQUEST_PARAM_AJAX);
    }

    private void prepend(HttpServletRequest request, String typeEcran) {
        Map<String, Object> pathVariables = (Map<String, Object>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        pathVariables = new HashMap<>(pathVariables);
        String paramEcran = "";
        String paramAction = "";
        if (CRUD_READ_ALL.equals(typeEcran)) {
            paramEcran = SCREEN_LIST;
            paramAction = CRUD_READ_ALL;
        } else if (CRUD_DELETE.equals(typeEcran)) {
            paramEcran = SCREEN_LIST;
            paramAction = typeEcran;
        } else if (CRUD_CREATE.equals(typeEcran) || CRUD_UPDATE.equals(typeEcran) || CRUD_DIFFERE.equals(typeEcran)) {
            paramEcran = SCREEN_FORM;
            paramAction = typeEcran;
        } else if (CRUD_READ_FIND.equals(typeEcran)) {
            paramEcran = SCREEN_FIND;
            paramAction = CRUD_READ_FIND;
        }
        pathVariables.put(REQUEST_PARAM_SCREEN, paramEcran);
        pathVariables.put(REQUEST_PARAM_ACTION, paramAction);
        // Contournement
        request.setAttribute(REQUEST_PARAM_SCREEN, paramEcran);
        request.setAttribute(REQUEST_PARAM_ACTION, paramAction);
        // Ceci ne sert pas a grand chose
        request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, pathVariables);
    }

    /**
     * Redirige vers la page par defaut
     */
    public void redirectView() {
        redirectView(null);
    }

    /**
     * Redirige vers la page par defaut
     */
    public void redirectNewView() {
    }

    /**
     * Prepare le lien pour la redirection vers une page
     *
     * @param view
     *            : lien de redirection
     */
    public void redirectView(String view) {
        redirectViewWithPopup(view, "SUCCES");
    }

    /**
     * logic de redirection par défaut après une action
     * (insert, update, delete)
     *
     * @param view
     * @param callPopup
     */
    public void redirectViewWithPopup(String view, String callPopup) {
        if (view == null)
            view = CRUD_READ_ALL; // + "." + FILE_HTML;
        // pour forcer la redirection set la valeur de modelTemplate a NULL
        if(modelTemplate == null) {
            this.view = "redirect:/" + getMiddleUrl(true) + "/" + view;
            return;
        }
        if ("SUCCES".equals(callPopup)) {
            modelTemplate.addAttribute(MODEL_ATTRIBUTE_POPUP_OPERATION, callPopup);
        } else {
            switch (APP_ecran) {
                case SCREEN_FORM:
                    switch (APP_action) {
                        case CRUD_CREATE:
                            operationMsg = JavaUtils.doNVL(operationMsg, "CREATE_SUCCES");
                            modelTemplate.addAttribute(MODEL_ATTRIBUTE_POPUP_OPERATION, operationMsg);
                            break;
                        case CRUD_UPDATE:
                            operationMsg = JavaUtils.doNVL(operationMsg, "UPDATE_SUCCES");
                            modelTemplate.addAttribute(MODEL_ATTRIBUTE_POPUP_OPERATION, operationMsg);
                            break;
                        case CRUD_DIFFERE:
                            operationMsg = JavaUtils.doNVL(operationMsg, "DIFFERE_SUCCES");
                            modelTemplate.addAttribute(MODEL_ATTRIBUTE_POPUP_OPERATION, operationMsg);
                            break;
                    }
                    break;
                case SCREEN_LIST:
                    switch (APP_action) {
                        case CRUD_DELETE:
                            operationMsg = JavaUtils.doNVL(operationMsg, "DELETE_SUCCES");
                            modelTemplate.addAttribute(MODEL_ATTRIBUTE_POPUP_OPERATION, operationMsg);
                            break;
                        case CRUD_READ_ALL:
                            operationMsg = JavaUtils.doNVL(operationMsg, "SUCCES");
                            modelTemplate.addAttribute(MODEL_ATTRIBUTE_POPUP_OPERATION, operationMsg);
                            break;
                    }
                    break;
                default:
                    operationMsg = JavaUtils.doNVL(operationMsg, "SUCCES");
                    modelTemplate.addAttribute(MODEL_ATTRIBUTE_POPUP_OPERATION, operationMsg);
                    break;
            }
        }
        String pageValue = request.getContextPath() + "/" + getMiddleUrl(true) + "/" + view;
        modelTemplate.addAttribute("PAGE", pageValue);
        modelTemplate.addAttribute(MODEL_ATTRIBUTE_NAVBAR, navbar);
        this.view = template;
    }

    /**
     * Prepare le lien pour la redirection vers une page autre que celle
     * par defaut
     *
     * @param view
     *            : lien de redirection
     */
    public void redirectOtherModuleView(String view) {
        modelTemplate.addAttribute(MODEL_ATTRIBUTE_POPUP_OPERATION, JavaUtils.doNVL(operationMsg, "SUCCES"));
        modelTemplate.addAttribute("PAGE", request.getContextPath() + "/" + view);
        modelTemplate.addAttribute(MODEL_ATTRIBUTE_NAVBAR, navbar);
        this.view = template;
    }

    public void redirectViewWithPopupDataAfterLoad(String view, String callPopup, String dataHandled, String dataRemaining) {
        if (view == null)
            view = CRUD_READ_ALL + "." + FILE_HTML;
        // pour forcer la redirection set la valeur de modelTemplate a NULL
        if (modelTemplate != null) {
            modelTemplate.addAttribute("HANDLED", dataHandled);
            modelTemplate.addAttribute("REMAINING", dataRemaining);
            modelTemplate.addAttribute(MODEL_ATTRIBUTE_POPUP_OPERATION, callPopup);
            modelTemplate.addAttribute("PAGE", request.getContextPath() + "/" + getMiddleUrl(true) + "/" + view);
            modelTemplate.addAttribute(MODEL_ATTRIBUTE_NAVBAR, navbar);
            this.view = template;
        } else
            this.view = "redirect:/" + getMiddleUrl(true) + "/" + view;
    }

    private void doSetFlashObject(MapFlash flashObject) {
        this.flashObject = flashObject;
    }

    public static boolean setModelAttribute(Model model, String key, Object value, Object def) {
        return JavaUtils.setModelAttribute(model, key, value, def);
    }

    public static boolean setModelAttribute(Model model, String key, Object value) {
        return JavaUtils.setModelAttribute(model, key, value);
    }

    protected void setFindPartsDelimiter(String delimiter) {
        findPartsDelimiter = delimiter;
    }

    private void doCatchExceptionHandler(BindingResult result, Model model, Exception e, String errorPage) {
        Exception catchEx = e instanceof Exception ? (Exception) e : new Exception(e);
        //catchEx.setCredentials(credentials);
        //doErrorLogic(result, catchEx, model, errorPage);
        model.addAttribute(CATCH_ERROR, true);
    }

    private String doHandleResult(BindingResult result) {
        String errorMsgCode = null;
        String[] errFullTxt = null;
        if (JavaUtils.notEmptyArrayList(result.getAllErrors())) {
            for (ObjectError o : result.getAllErrors()) {
                String[] data = o.getCodes()[0].split("." + o.getObjectName() + ".");
                if (data.length <= 1)
                    data = o.getCodes()[0].split("." + o.getObjectName());
                errFullTxt = data[0].split("::");
                if (errFullTxt != null && errFullTxt.length > 1) {
                }
                errorMsgCode = errFullTxt[0];
            }
            if (!errorMsgCode.contains("MSG."))
                errorMsgCode = "MSG." + errorMsgCode + ".error";
        }
        return errorMsgCode;
    }
}
