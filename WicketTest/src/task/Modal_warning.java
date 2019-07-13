package task;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.PropertyModel;


/**
 * @author Matej Knopp
 * 
 */
public class Modal_warning extends WebPage
{
    private String field_title;
    private String field_message;
    /**
     * @param window
     * @param title
     */
    public Modal_warning(final ModalWindow window,String title,String message){
        // label title
        add(new Label("field_label_title",new PropertyModel(this,"title")).setEscapeModelStrings(false));
        add(new Label("field_label_message",new PropertyModel(this,"message")).setEscapeModelStrings(false));

        // OK
        add(new AjaxLink("close_OK"){
            public void onClick(AjaxRequestTarget target){
                window.close(target);
            }
         });
    }
    
    // Bean for IModel
    public String getTitle(){
        return this.field_title;
    }
    public void setTitle(String value){
        this.field_title=value;
    }
    
    public String getMessage(){
        return this.field_message;
    }
    
    public void setMessage(String value){
        this.field_message=value;
    }
    
}
