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
public class Modal_create_contakt extends WebPage
{
    Modal_warning field_body_modal_warning;
    ModalWindow field_modal_warning;
    private TextField field_input_name;
    private TextField field_input_phone;
    private DropDownChoice field_combobox_category;
    private List<String> field_list_category;
    private String field_kod;
    private String field_name;
    private String field_phone;
    private String field_category;
    private boolean field_edit=false;
    /** @param window
      * @param title
     */
    public Modal_create_contakt(String title){
        
        // label title
        add(new Label("field_label_title",title));
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
                    if(Modal_create_contakt.this.isEdit()){
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
                        if(Modal_create_contakt.this.name_is_unique()){
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
                            field_body_modal_warning.setMessage("<b>NAME<b> must by unique");
                            field_modal_warning.setInitialHeight(120);
                            field_modal_warning.setInitialWidth(180);
                            field_modal_warning.show(target);
                        }
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
        // Input type=text, PHONE
        this.field_input_phone=new TextField("field_input_phone",new PropertyModel(this,"phone"));
        this.field_input_phone.add(new AjaxFormComponentUpdatingBehavior("onkeyup"){
            protected void onUpdate(AjaxRequestTarget target){
                // required for data transmit
            }
        });
        add(field_input_phone);
        // Select CATEGORY
        /*try{
            this.field_list_category=Start.field_database.get_field_from_table("CATEGORY","FIELD_NAME","KOD");
        }catch(Exception ex){
            System.out.println("ex:"+ex.getMessage());
        }*/
        this.field_list_category=Start.field_database.get_field_from_table("CATEGORY","FIELD_NAME","KOD");
        this.field_category=this.field_list_category.get(0);
        this.field_combobox_category=new DropDownChoice("field_combobox_category",new PropertyModel(this,"category"),this.field_list_category){
            protected String getDefaultChoice(Object selected){
                return field_list_category.get(0);
            }
        };
        this.field_combobox_category.add(new AjaxFormComponentUpdatingBehavior("onChange") {
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                // required for data transmit
            }
        });
        add(field_combobox_category);
    }
    
    /**
     * current Name is unique ?
     * @return true if name is unique
     * @return false if name is repeated
     */
    private boolean name_is_unique(){
        boolean return_value=false;
        String kod=Start.field_database.get_another_field("CONTACTS","FIELD_NAME",this.getName(),"KOD");
        if((kod==null)||(kod.equals(""))){
            return_value=true;
        }
        return return_value;
    }
    
    /**
     * save input values
     */
    private boolean save(){
        boolean return_value=false;
        String category=Start.field_database.get_another_field("CATEGORY","FIELD_NAME",this.field_category,"KOD");
        if(this.isEdit()){
            // EDIT
            if((category!=null)&&(!category.equals(""))){
                return_value=Start.field_database.update("CONTACTS",
                                                         new String[]{"FIELD_NAME","FIELD_PHONE","FIELD_KOD_CATEGORY"},
                                                         new Object[]{this.field_name,this.field_phone,new Integer(category)},
                                                         new String[]{"KOD"},
                                                         new Object[]{new Integer(this.field_kod)});
            }else{
                return_value=Start.field_database.update("CONTACTS",
                                                         new String[]{"FIELD_NAME","FIELD_PHONE","FIELD_KOD_CATEGORY"},
                                                         new Object[]{this.field_name,this.field_phone,null},
                                                         new String[]{"KOD"},
                                                         new Object[]{new Integer(this.field_kod)});
            }
        }else{
            // INSERT
            if((category!=null)&&(!category.equals(""))){
                return_value=Start.field_database.insert("CONTACTS",new String[]{"FIELD_NAME","FIELD_PHONE","FIELD_KOD_CATEGORY"},new Object[]{this.field_name,this.field_phone,new Integer(category)});
            }else{
                return_value=Start.field_database.insert("CONTACTS",new String[]{"FIELD_NAME","FIELD_PHONE"},new Object[]{this.field_name,this.field_phone});
            }
        }
        if(return_value==true){
            this.setName("");
            this.setPhone("");
        }
        
        return return_value;
    }
    /**
     * set modal window to EDIT state
     */
    public void setEdit(boolean value){
        this.field_edit=value;
    }
    /**
     * @return true if in EDIT state
     */
    public boolean isEdit(){
        return this.field_edit;
    }
    /**
     * load from CONTACTS on KOD
     * @param field_kod - CONTACTS.KOD
     * @return true if contacts.kod exists and loaded
     * @return false if exception
     * @return false if KOD not found
     */
    public boolean load_by_kod(String field_kod){
        boolean return_value=false;
        try{
            database_join join=new database_join(database_join.JOIN_LEFT,"CONTACTS","FIELD_KOD_CATEGORY","CATEGORY","KOD");
            List<String[]> result=Start.field_database.get_query_result(new String[]{"KOD","FIELD_NAME","FIELD_PHONE","CATEGORY.FIELD_NAME"},"CONTACTS",new database_join[]{join}," CONTACTS.KOD="+field_kod,new String[]{"KOD"});
            if(result.size()>0){
                this.field_kod=field_kod;
                this.setName(result.get(0)[1]);
                this.setPhone(result.get(0)[2]);
                this.setCategory(result.get(0)[3]);
                this.setEdit(true);
            }else{
                return_value=false;
            }
        }catch(Exception ex){
            return_value=false;
        }
        return return_value;
    }
    // Bean for IModel
    public String getCategory(){
        return this.field_category;
    }
    public void setCategory(String value){
        this.field_category=value;
    }
    
    public String getName(){
        return this.field_name;
    }
    public void setName(String value){
        this.field_name=value;
    }
    
    public String getPhone(){
        return this.field_phone;
    }
    public void setPhone(String value){
        this.field_phone=value;
    }
}
