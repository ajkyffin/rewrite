package org.ocpsoft.rewrite.annotation.action;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;

@Named
@RequestScoped
@Join(path = "/action", to = "/action.jsp")
public class RequestActionBean
{

   private String log = "nothing happend";

   @RequestAction
   public void action()
   {
      log = "action invoked";
   }

   public String getLog()
   {
      return log;
   }

}
