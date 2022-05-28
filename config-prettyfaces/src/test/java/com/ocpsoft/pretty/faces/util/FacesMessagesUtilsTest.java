package com.ocpsoft.pretty.faces.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jakarta.faces.application.Application;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.FacesMessage.Severity;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseStream;
import jakarta.faces.context.ResponseWriter;
import jakarta.faces.render.RenderKit;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FacesMessagesUtilsTest
{

   @Test
   public void testSaveMessages()
   {

      // set up FacesContext
      FacesContextMock facesContext = new FacesContextMock();
      facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Summary A", "Detail A"));
      facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Summary B", "Detail B"));
      facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Summary B", "Detail B"));
      facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Summary B", "Detail B"));

      // call saveMessages()
      Map<String, Object> sessionMap = new HashMap<String, Object>();
      int saved = new FacesMessagesUtils().saveMessages(facesContext, sessionMap);

      // messages stored in session
      assertThat(saved).isEqualTo(3);
      assertThat(sessionMap.get(FacesMessagesUtils.token)).isNotNull();
      assertThat(((Collection<?>) sessionMap.get(FacesMessagesUtils.token)).size()).isEqualTo(3);
   }

   @Test
   public void testDuplicatedMessages()
   {

      /*
       * Step 1: Save
       */

      // class under test
      FacesMessagesUtils utils = new FacesMessagesUtils();

      // set up FacesContext
      FacesContextMock facesContext = new FacesContextMock();
      facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Summary A", "Detail A"));
      facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Summary B", "Detail B"));
      facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Summary C", "Detail C"));

      // call saveMessages()
      Map<String, Object> sessionMap = new HashMap<String, Object>();
      int saved = utils.saveMessages(facesContext, sessionMap);

      // check message are saved
      assertThat(saved).isEqualTo(3);
      assertThat(sessionMap.get(FacesMessagesUtils.token)).isNotNull();
      assertThat(((Collection<?>) sessionMap.get(FacesMessagesUtils.token)).size()).isEqualTo(3);

      /*
       * Step 2: Restore
       */

      // Setup new FacesContext with two messages
      facesContext.clearAllMesages();
      facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Summary B", "Detail B"));
      facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Summary B", "Detail B"));

      // restore messages
      int restored = utils.restoreMessages(facesContext, sessionMap);

      // check that two of the three messages have been restored
      assertThat(restored).isEqualTo(2);
      assertThat(facesContext.getAllMessages().size()).isEqualTo(4);

   }

   /**
    * Simple mock of {@link FacesContext} that just handles the message queue.
    * 
    */
   private static class FacesContextMock extends FacesContext
   {

      private List<FacesMessage> messages = new ArrayList<FacesMessage>();

      public List<FacesMessage> getAllMessages()
      {
         return messages;
      }

      public void clearAllMesages()
      {
         messages.clear();
      }

      @Override
      public void addMessage(String arg0, FacesMessage arg1)
      {
         messages.add(arg1);
      }

      @Override
      public Application getApplication()
      {
         return null;
      }

      @Override
      public Iterator<String> getClientIdsWithMessages()
      {
         return null;
      }

      @Override
      public ExternalContext getExternalContext()
      {
         return null;
      }

      @Override
      public Severity getMaximumSeverity()
      {
         return null;
      }

      @Override
      public Iterator<FacesMessage> getMessages()
      {
         return messages.iterator();
      }

      @Override
      public Iterator<FacesMessage> getMessages(String arg0)
      {
         return messages.iterator();
      }

      @Override
      public RenderKit getRenderKit()
      {
         return null;
      }

      @Override
      public boolean getRenderResponse()
      {
         return false;
      }

      @Override
      public boolean getResponseComplete()
      {
         return false;
      }

      @Override
      public ResponseStream getResponseStream()
      {
         return null;
      }

      @Override
      public ResponseWriter getResponseWriter()
      {
         return null;
      }

      @Override
      public UIViewRoot getViewRoot()
      {
         return null;
      }

      @Override
      public void release()
      {

      }

      @Override
      public void renderResponse()
      {

      }

      @Override
      public void responseComplete()
      {

      }

      @Override
      public void setResponseStream(ResponseStream arg0)
      {

      }

      @Override
      public void setResponseWriter(ResponseWriter arg0)
      {

      }

      @Override
      public void setViewRoot(UIViewRoot arg0)
      {

      }

   }

}
