package bj.tresorbenin.suicom.controllers;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;

import jakarta.servlet.http.HttpServletRequest;

@Component
@Scope(
   value = WebApplicationContext.SCOPE_SESSION,
   proxyMode = ScopedProxyMode.TARGET_CLASS
)
public class GlobalVars<T> implements Serializable {
   protected static final long serialVersionID = 1L;

   protected String template;
   protected String view;
   protected boolean readonly;
   protected Model model;
   protected List<T> entities;
   protected T entity;
   protected String APP_module;
   protected String APP_groupe;
   protected String APP_directory;
   protected String APP_ecran;
   protected String APP_action;
   protected String id;
   protected HttpMethod httpMethod;
   protected HttpServletRequest request;
   protected Class<?> clazz;

}
