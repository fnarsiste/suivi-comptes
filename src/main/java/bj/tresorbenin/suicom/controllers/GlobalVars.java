package bj.tresorbenin.suicom.controllers;

import bj.tresorbenin.suicom.session.MapFlash;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.util.List;

@Component
@Scope(
   value = WebApplicationContext.SCOPE_SESSION,
   proxyMode = ScopedProxyMode.TARGET_CLASS
)
public class GlobalVars<T> implements Serializable {
   protected static final long serialVersionID = 1L;
   protected String id;
   protected String template;
   protected String view;
   protected String ajax;
   protected String APP_module;
   protected String APP_groupe;
   protected String APP_directory;
   protected String APP_ecran;
   protected String APP_action;
   protected String currentScreen;
   protected String operationMsg;
   protected String headerTitle;
   protected String pageTitle = "MSG.blank";
   protected String findPartsDelimiter = "||";
   protected String navbar;
   protected String pageRoute;

   protected boolean readonly;

   protected T entity;
   protected List<T> entities;
   protected HttpMethod httpMethod;
   protected Model modelTemplate;
   protected HttpServletRequest request;
   protected Class<?> clazz;
   protected MapFlash flashObject;
}
