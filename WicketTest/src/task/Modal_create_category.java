package task;

import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;


/**
 * @author Matej Knopp
 * 
 */
public class Modal_create_category extends WebPage
{
    private boolean FLAG_DEBUG=false;
    private Modal_warning field_body_modal_warning;
    private ModalWindow field_modal_warning;
    private TextField field_input_name;
    private String field_name;
    private String field_title;
    
    /** if value is empty - CREATE, if not empty - */
    private String field_original_value="";
    /** @param window
      * @param title
     */
    public Modal_create_category(final ModalWindow window){
        
        // label title
        add(new Label("field_label_title",new PropertyModel(this,"title")));
        // OK
        add(new AjaxLink("close_OK"){
            public void onClick(AjaxRequestTarget target){
                if((field_name==null)||(field_name.trim().equals(""))){
                    field_body_modal_warning.setTitle("<center><b>Warning</b></center>");
                    field_body_modal_warning.setMessage("filling field <b>NAME<b>");
                    field_modal_warning.setInitialHeight(100);
                    field_modal_warning.setInitialWidth(180);
                    field_modal_warning.show(target);
                }else{
                    if(check()==true){
                        if(save()==true){
                            field_modal_warning.close(target);
                        }else{
                            field_body_modal_warning.setTitle("<center><b>Error</b></center>");
                            field_body_modal_warning.setMessage("Data <b>not<b> save");
                            field_modal_warning.setInitialHeight(100);
                            field_modal_warning.setInitialWidth(180);
                            field_modal_warning.show(target);
                        }
                    }else{
                        field_body_modal_warning.setTitle("<center><b>Warning</b></center>");
                        field_body_modal_warning.setMessage("Category already exists");
                        field_modal_warning.setInitialHeight(100);
                        field_modal_warning.setInitialWidth(180);
                        field_modal_warning.show(target);
                    }
                }
            }
        });
        // Cancel
        add(new AjaxLink("close_Cancel"){
            public void onClick(AjaxRequestTarget target){
                field_modal_warning.close(target);
            }
        });
        // Modal Window for Warning's
        field_modal_warning=new ModalWindow("field_modal_warning");
        this.field_body_modal_warning=new Modal_warning(field_modal_warning,"title","message");
        field_modal_warning.setPageCreator(new ModalWindow.PageCreator(){
            public Page createPage() {
                return field_body_modal_warning;
            }
        });
        field_modal_warning.setResizable(false);
        field_modal_warning.setInitialHeight(150);
        field_modal_warning.setInitialWidth(200);
        add(field_modal_warning);
        
        // Input type=text, NAME
        this.field_input_name=new TextField("field_input_name",new PropertyModel(this,"name"));
        this.field_input_name.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                // required for data transmit
            }
        });
        add(field_input_name);
    }
    
    /**
     * Method for debug
     */
    private void out(String information){
        if(this.FLAG_DEBUG==true){
            System.out.print("Modal_create_category#");
            System.out.println(information);
        }
    }
    
    /**
     * save data
     */
    private boolean save(){
        boolean return_value=false;
/*        String category=Start.field_database.get_another_field("CATEGORY","FIELD_NAME",this.field_category,"KOD");
        if((category!=null)&&(!category.equals(""))){
            return_value=Start.field_database.insert("CONTACTS",new String[]{"FIELD_NAME","FIELD_PHONE","FIELD_KOD_CATEGORY"},new Object[]{this.field_name,this.field_phone,new Integer(category)});
        }else{
            return_value=Start.field_database.insert("CONTACTS",new String[]{"FIELD_NAME","FIELD_PHONE"},new Object[]{this.field_name,this.field_phone});
        }
  */      
        if((this.getOriginal_value()!=null)&&(!this.getOriginal_value().equals(""))){
            // update
            String kod=Start.field_database.get_another_field("CATEGORY","FIELD_NAME",this.getOriginal_value(),"KOD");
            if((kod!=null)&&(!kod.equals(""))){
                out("save,   update,    old:"+this.getOriginal_value()+"  new:"+this.getName());
                return_value=Start.field_database.update("CATEGORY",new String[]{"FIELD_NAME"},new Object[]{this.getName()},new String[]{"KOD"},new Object[]{new Integer(kod)});
            }else{
                out("save,   update,    error in getting kod:"+this.getOriginal_value());
                // error in getting kod
                return_value=false;
            }
        }else{
            // insert
            out("save,   insert,    new:"+this.getName());
            return_value=Start.field_database.insert("CATEGORY",new String[]{"FIELD_NAME"},new Object[]{this.getName()});
        }
        return return_value;
    }
    /**
     * check for repeat
     * @return true, if OK
     * @return false, if record exists
     */
    private boolean check(){
        boolean return_value=false;
        try{
            String kod=Start.field_database.get_another_field("CATEGORY","FIELD_NAME",this.getName(),"KOD");
            if((kod==null)||(kod.equals(""))){
                // kod not found
                return_value=true;
            }else{
                // kod founded
                return_value=false;
            }
        }catch(Exception ex){
            return_value=false;
        }
        return return_value;
    }
    // Bean for IModel
    public String getName(){
        return this.field_name;
    }
    public void setName(String value){
        this.field_name=value;
    }
    
    public String getTitle(){
        return this.field_title;
    }
    public void setTitle(String value){
        this.field_title=value;
    }
    
    public String getOriginal_value(){
        return this.field_original_value;
    }
    public void setOriginal_value(String value){
        this.field_original_value=value;
    }
}
