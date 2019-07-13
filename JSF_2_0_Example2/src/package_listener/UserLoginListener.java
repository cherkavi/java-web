/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package package_listener;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import users.UserHolder;

/**
 *
 * @author Technik
 */
public class UserLoginListener implements PhaseListener{
	private final static long serialVersionUID=1L;

    public void afterPhase(PhaseEvent event) {
    }

    public void beforePhase(PhaseEvent event) {
        FacesContext context=FacesContext.getCurrentInstance();
        String viewId=context.getViewRoot().getViewId();
        if(!viewId.equals("/index.xhtml")){
            // проверить на наличие в сессии уникального идентификатора пользователя
            UserHolder userHolder=(UserHolder)context.getApplication().evaluateExpressionGet(context, "#{user_holder}", UserHolder.class);
            if((userHolder!=null)&&(userHolder.getUser()!=null)&&(userHolder.getUser().getId()>0)){
                // пользователь установлен - не изменять течение программы
            }else{
                // пользователь пытается пройти в часть сайта, которая требует идентификации
                ViewHandler viewHandler=context.getApplication().getViewHandler();
                UIViewRoot view=viewHandler.createView(context, "/index.xhtml");
                context.setViewRoot(view);
            }
        }else{
            // отображаться будет страница по логгированию-входу на ресурс 
        }
    }

    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

}
