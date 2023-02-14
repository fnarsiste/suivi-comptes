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
   protected String template;
   protected String view;
   protected String ajax;
   protected boolean readonly;
   protected Model modelTemplate;
   protected List<T> entities;
   protected T entity;
   protected String APP_module;
   protected String APP_groupe;
   protected String APP_directory;
   protected String APP_ecran;
   protected String APP_action;
   protected String currentScreen;
   protected String id;
   protected HttpMethod httpMethod;
   protected HttpServletRequest request;
   protected Class<?> clazz;
   protected MapFlash flashObject;
   protected String operationMsg;
   protected String headerTitle;
   protected String pageTitle;
   protected String findPartsDelimiter = "||";
   protected String navbar;
}
