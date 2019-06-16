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
            // ��������� �� ������� � ������ ����������� �������������� ������������
            UserHolder userHolder=(UserHolder)context.getApplication().evaluateExpressionGet(context, "#{user_holder}", UserHolder.class);
            if((userHolder!=null)&&(userHolder.getUser()!=null)&&(userHolder.getUser().getId()>0)){
                // ������������ ���������� - �� �������� ������� ���������
            }else{
                // ������������ �������� ������ � ����� �����, ������� ������� �������������
                ViewHandler viewHandler=context.getApplication().getViewHandler();
                UIViewRoot view=viewHandler.createView(context, "/index.xhtml");
                context.setViewRoot(view);
            }
        }else{
            // ������������ ����� �������� �� ������������-����� �� ������ 
        }
    }

    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

}
