package bj.tresorbenin.suicom.utils;

import java.util.ArrayList;
import java.util.List;

public class ConstantUtils {
    public static final String MASTER_ACTION = "MASTER_ACTION";
    public static final String MASTER_SETUP = "MASTER_SETUP";
    public static final String MAIN_TEMPLATE = "mainTemplate";
    public static final String TEMPLATE_PROFILCHOICE = "profilchoice";
    public static final String TEMPLATE_AJAX = "ajaxTemplate";
    public static final String FILE_HTML = "html";
    public static final String SCREEN_FORM = "form";
    public static final String SCREEN_LIST = "liste";
    public static final String SCREEN_FIND = "find";
    public static final String CRUD_CREATE = "nouveau";
    public static final String CRUD_READ_FIND = "find";
    public static final String CRUD_READ_ALL = "liste";
    public static final String CRUD_UPDATE = "modifier";
    public static final String CRUD_DIFFERE = "differe";
    public static final String CRUD_DELETE = "supprimer";
    public static final String REQUEST_PARAM_MODULE = "APP_module";
    public static final String REQUEST_PARAM_GROUPE = "APP_groupe";
    public static final String REQUEST_PARAM_ENTITY = "APP_directory";
    public static final String REQUEST_PARAM_SCREEN = "APP_ecran";
    public static final String REQUEST_PARAM_ACTION = "APP_action";
    public static final String REQUEST_PARAM_ENTITY_ID = "id";
    public static final String REQUEST_PARAM_AJAX = "ajax";
    public final static String MSG_OPERATION_DELETE = "DELETE_SUCCES";
    public static final String MODEL_ATTRIBUTE_FORM = "Form";
    public static final String MODEL_ATTRIBUTE_LIST = "List";
    public static final String MODEL_ATTRIBUTE_PAGE_TITLE = "pageTitle";
    public static final String MODEL_ATTRIBUTE_HEADER_TITLE = "headerTitle";
    public static final String MODEL_ATTRIBUTE_READ_ONLY = "readonly";
    public static final String MODEL_ATTRIBUTE_FIND_PARTS_DELIMITER = "_FIND_PARTS_DELIMITER";
    public static final String MODEL_ATTRIBUTE_CONTENT_FILE = "CONTENTFILE";
    public static final String MODEL_ATTRIBUTE_NAVBAR = "NAVBAR";
    public static final String CATCH_ERROR = "CATCH_ERROR";
    public static final String ACTION_ERROR = "ACTION_ERROR";
    public static final String SETUP_ERROR = "SETUP_ERROR";
    public final static String MSG_OPERATION_CREATE = "CREATE_SUCCES";
    public final static String MSG_OPERATION_UPDATE = "UPDATE_SUCCES";
    public final static String MSG_OPERATION_DIFFERE = "DIFFERE_SUCCES";
    public static final String MODEL_ATTRIBUTE_POPUP_OPERATION = "POPUP_OPERATION";
    public static final String STATUT_DEMANDE_SAISIE = "SAI";
    public static final String STATUT_DEMANDE_TRAITEE = "TRT";
    public static final String STATUT_AUTORISATION_ACCORD = "ACC";
    public static final String STATUT_AUTORISATION_ACCORD_EXCEPTIONNEL = "EXC";
    public static final String STATUT_AUTORISATION_REFUS = "REF";

    public static List<String> getStatutsResultat() {
        //return new ArrayList<String>(List.of("ACC", "EXC", "REF"));
        //new ArrayList<String>(List.of(new String[]{"ACC", "EXC", "REF"}));
        return List.of(STATUT_AUTORISATION_ACCORD, STATUT_AUTORISATION_ACCORD_EXCEPTIONNEL, STATUT_AUTORISATION_REFUS);
    }
}
