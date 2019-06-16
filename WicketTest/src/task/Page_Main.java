package task;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.IClusterable;
import org.apache.wicket.Page;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.form.AjaxFormValidatingBehavior;

import org.apache.wicket.ajax.markup.html.*;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.*;
import org.apache.wicket.extensions.markup.html.tabs.*;
import org.apache.wicket.feedback.*;
import org.apache.wicket.markup.html.*;
import org.apache.wicket.markup.html.basic.*;
import org.apache.wicket.markup.html.border.*;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.link.*;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.*;
import org.apache.wicket.model.*;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.util.thread.Task;


public class Page_Main extends WebPage
{
    private boolean FLAG_DEBUG=false;
    /** id=field_dropdown*/
    private DropDownChoice field_dropdown_category;
    /** default value for category*/
    private int field_dropdown_category_index=0;
    /** form for button "Search" */
    private Form field_form_search;
    /** button search*/
    private Button field_button_search;
    /** text field search */
    private TextField field_textfield_search;
    /** "Save changes" */
    private Button field_button_save;
    /** "Revert" */
    private Button field_button_revert;
    /** Ajax link "Create new contact"*/
    private AjaxLink field_link_create_contakt;
    /** category values*/
    private List<String> field_category_values;
    /**  Ajax container for refreshing data*/
    private WebMarkupContainer field_ajax_container;
    /** panel for displaying database data*/
    //private panel_data field_panel_data;
    /** interface for governing and displaying data*/
    private panel_manager field_panel_manager;
    /** Bean for Search*/
    private Bean field_search_bean=new Bean();
    /** empty image*/
    private Image field_image_empty;
    /** modal for create new contakt */
    private ModalWindow field_modal_create_contakt;
    /**  button for editing Category*/
    private Button field_button_category;
    /** modal window for edit/create Category */
    private ModalWindow field_modal_create_category;
    /** modal window for create contakts*/
    private Modal_create_contakt field_body_create_contakt;
    /** modal window for create category*/
    private Modal_create_category field_modal_create_category_body;
    /**
     * Constructor
     */
    public Page_Main(){
        create_element();
    }

    /**  create all element */
    private void create_element(){
        out("---create_and_fill_category");
        this.create_and_fill_category();
        out("---create_for_search");
        this.create_form_search();
        out("---create_buttons");
        this.create_buttons();
        out("---create_list");
        if(this.field_panel_manager==null){
            field_ajax_container=new WebMarkupContainer("field_ajax_panel_data");
            // simple panel of data
            out("field_panel_data:begin;");
            panel_data field_panel_data=new panel_data(this.field_body_create_contakt,this.field_modal_create_contakt);
            
            this.field_panel_manager=(panel_manager)field_panel_data;
            field_ajax_container.add(field_panel_data);
            out("field_panel_data:end;");
            // velocity panel
/*            panel_velocity field_panel_velocity=new panel_velocity();
            this.field_panel_manager=(panel_manager)field_panel_velocity;
            field_ajax_container.add(field_panel_velocity);
*/
            // spring panel
/*            panel_spring field_panel_spring=new panel_spring();
            this.field_panel_manager=(panel_manager)field_panel_spring;
            field_ajax_container.add(field_panel_spring);
*/
            this.add(field_ajax_container.setOutputMarkupId(true));
        }
    }

    /**
     * Trace method
     */
    private void out(String value){
        if(this.FLAG_DEBUG==true){
            System.out.print("Page_Main#");System.out.println(value);
        }
    }
    /** create button's */
    private void create_buttons(){
        this.field_button_save=new Button("field_button_save");
        this.field_button_save.add(new AjaxFormComponentUpdatingBehavior("onClick") {
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                out("--COMMIT--");
                Start.field_database.commit();
                ajaxRequestTarget.appendJavascript("alert('Save changes')");
                field_panel_manager.create_list(field_dropdown_category_index);
                ajaxRequestTarget.addComponent(field_ajax_container);
                fill_dropdown_object();
                ajaxRequestTarget.addComponent(field_dropdown_category);
            }
        });
        this.field_button_revert=new Button("field_button_revert");
        this.field_button_revert.add(new AjaxFormComponentUpdatingBehavior("onClick") {
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                out("--REVERT--");
                Start.field_database.rollback();
                ajaxRequestTarget.appendJavascript("alert('Revert')");
                field_panel_manager.create_list(field_dropdown_category_index);
                ajaxRequestTarget.addComponent(field_ajax_container);
                fill_dropdown_object();
                ajaxRequestTarget.addComponent(field_dropdown_category);
                
            }
        });
        
        this.add(field_button_save);
        this.add(field_button_revert);
    }
    
    /** create image for clear search field */
    private void create_image_empty(){
        Link field_link_empty=new Link("field_link_empty"){
            public void onClick() {
                setResponsePage(Page_Main.class);
            }
        };
        //this.field_image_empty=new Image("field_image_empty","clear.png");
        this.field_image_empty=new Image("field_image_empty","clear_empty.png");
        http://localhost:8080/task/resources/task.Page_Main/clear_empty.png
        this.field_image_empty.setOutputMarkupId(true);
        field_link_empty.add(this.field_image_empty);
        this.field_form_search.add(field_link_empty);
        /*
         ImageMap field_image_map=new ImageMap("field_image_empty");
        field_image_map.addRectangleLink(0,0,25,25,field_link_empty);
        this.field_form_search.add(field_image_map);
         */
        // image empty
/*        this.field_image_empty=new Image("field_image_empty","clear.png");
        this.field_image_empty.setOutputMarkupId(true);
        this.field_image_empty.setVisibilityAllowed(true);
  */
        //this.field_image_empty.setVisible(true);
/*        this.field_image_empty.add(new AjaxEventBehavior("onClick") {
            protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
                field_textfield_search.clearInput();
                Page_Main.this.field_search_bean.setField_textfield_search("");
                field_panel_data.create_list(field_dropdown_category_index);                
                ajaxRequestTarget.addComponent(field_ajax_container);
            }
        });
        field_link_empty.add(this.field_image_empty);
        */
        
        
        // !!! BAG
        //this.field_image_empty.setVisible(false);        
    }
    
    /** create search form*/
    private void create_form_search(){
        this.field_form_search=new Form("field_form_search",new CompoundPropertyModel(this.field_search_bean));
        this.field_button_search=new Button("field_button_search");
        this.field_button_search.add(new AjaxFormComponentUpdatingBehavior("onClick") {
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                out("onClickdown:");
                /* !!! BAG
                if(Page_Main.this.field_search_bean.getField_textfield_search()==null){
                    ajaxRequestTarget.addComponent(field_image_empty.setVisible(false));
                }else{
                    Page_Main.this.field_image_empty.setVisible(true);
                    ajaxRequestTarget.addComponent(field_image_empty);
                }
                 */
                if((Page_Main.this.field_search_bean.getField_textfield_search()==null)||(Page_Main.this.field_search_bean.getField_textfield_search().trim().equals(""))){
                    field_image_empty.setImageResourceReference(new ResourceReference(Page_Main.class,"clear_empty.png"));
                    ajaxRequestTarget.addComponent(field_image_empty);
                }else{
                    Page_Main.this.field_image_empty.setVisible(true);
                    field_image_empty.setImageResourceReference(new ResourceReference(Page_Main.class,"clear.png"));                    
                    ajaxRequestTarget.addComponent(field_image_empty);
                }
            }
        });
        
        // field search
        out("field search");
        this.field_textfield_search=new TextField("field_textfield_search");
        this.field_textfield_search.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                out("onkeyup:");
                field_panel_manager.create_list(field_dropdown_category_index);
                // !!! BAG
                /*
                if(Page_Main.this.field_search_bean.getField_textfield_search()==null){
                    ajaxRequestTarget.addComponent(field_image_empty.setVisible(false));
                }else{
                    Page_Main.this.field_image_empty.setVisible(true);
                    ajaxRequestTarget.addComponent(field_image_empty);
                }
                 */
                if(Page_Main.this.field_search_bean.getField_textfield_search()==null){
                    field_image_empty.setImageResourceReference(new ResourceReference(Page_Main.class,"clear_empty.png"));
                    ajaxRequestTarget.addComponent(field_image_empty);
                }else{
                    Page_Main.this.field_image_empty.setVisible(true);
                    field_image_empty.setImageResourceReference(new ResourceReference(Page_Main.class,"clear.png"));                    
                    ajaxRequestTarget.addComponent(field_image_empty);
                }
                
                ajaxRequestTarget.addComponent(field_ajax_container);
            }
        });
        this.create_image_empty();
        this.field_form_search.add(this.field_button_search);
        this.field_form_search.add(this.field_textfield_search);
        this.add(this.field_form_search);
        
        // field create contact Link
            // modal window
        out("modal window");
        this.field_modal_create_contakt=new ModalWindow("field_modal_create_contakt");
        this.field_body_create_contakt=new Modal_create_contakt("Create new contact");
        this.field_modal_create_contakt.setPageCreator(new ModalWindow.PageCreator(){
            public Page createPage() {
                //return get_modal_create_contakt(field_modal_create_contakt);
                return  Page_Main.this.field_body_create_contakt;
            }
        });
        this.field_modal_create_contakt.setInitialHeight(200);
        this.field_modal_create_contakt.setInitialWidth(200);
        this.field_modal_create_contakt.setWindowClosedCallback(new WindowClosedCallback(){
            public void onClose(AjaxRequestTarget ajaxRequestTarget) {
                field_panel_manager.create_list(field_dropdown_category_index);
                ajaxRequestTarget.addComponent(field_ajax_container);
            }
        });
        this.add(this.field_modal_create_contakt);
            // link
        out("link");
        this.field_link_create_contakt=new AjaxLink("field_link_create_contakt") {
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                Page_Main.this.field_body_create_contakt.setEdit(false);
                field_modal_create_contakt.show(ajaxRequestTarget);
                //field_panel_data.create_list(field_dropdown_category_index);
                //ajaxRequestTarget.addComponent(field_ajax_container);
            }
        };
        this.add(this.field_link_create_contakt);
    }
    
    /** return ModalWindow*/
    private Page get_modal_create_contakt(ModalWindow modalEdit) {
       return new Modal_create_contakt("Create new contact");        
        //return new ModalContent1Page(ModalWindowPage.this,modalEdit);        
    }
    
    private void fill_dropdown_object(){
        this.field_category_values.clear();
        this.field_category_values.addAll(Start.field_database.get_field_from_table("CATEGORY","FIELD_NAME","KOD"));
        this.field_category_values.add(0,"All categories");
    }
    /** 
     * creating and filling CATEGORY into field_dropdown
     */
    private void create_and_fill_category(){
        // read data from base
        if(this.field_dropdown_category==null){
            this.field_category_values=new ArrayList();
            out("fill dropdown object");
            fill_dropdown_object();
            out("create dropdown object in markup");
            this.field_dropdown_category=new DropDown("field_dropdown_category",new PropertyModel(this,"category"),this.field_category_values);
            this.field_dropdown_category.setOutputMarkupId(true);
            // Ajax HTTP request
            this.field_dropdown_category.add(new AjaxFormComponentUpdatingBehavior("onChange") {
                protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                    out("update:");
                    //create_and_fill_category();
                    field_panel_manager.create_list(field_dropdown_category_index);
                    ajaxRequestTarget.addComponent(field_ajax_container);
                    ajaxRequestTarget.addComponent(field_button_category);
                }
            });
            this.add(this.field_dropdown_category);
        }
        this.field_dropdown_category.setChoices(this.field_category_values);
        
        // button for create/edit category
        out("button for create/edit category");
        this.field_button_category=new Button("field_button_category",new PropertyModel(this,"button_category"));
        this.field_button_category.setOutputMarkupId(true);
        this.field_button_category.add(new AjaxFormComponentUpdatingBehavior("onClick"){
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                if(getButton_category().equals("create")){
                    field_modal_create_category_body.setOriginal_value("");
                    field_modal_create_category_body.setTitle("Create new");
                    field_modal_create_category_body.setName("");
                }else{
                    field_modal_create_category_body.setOriginal_value(getCategory());
                    field_modal_create_category_body.setTitle("Edit");
                    field_modal_create_category_body.setName(getCategory());
                }
                // calling callback function
                field_modal_create_category.setWindowClosedCallback(new WindowClosedCallback(){
                    public void onClose(AjaxRequestTarget ajaxRequestTarget) {
                        field_panel_manager.create_list(field_dropdown_category_index);
                        ajaxRequestTarget.addComponent(field_ajax_container);
                        fill_dropdown_object();
                        ajaxRequestTarget.addComponent(field_dropdown_category);
                        //ajaxRequestTarget.appendJavascript("document.all('"+field_button_search.getId()+"').click()");
                    }
                });
                field_modal_create_category.show(ajaxRequestTarget);
                field_panel_manager.create_list(field_dropdown_category_index);
                ajaxRequestTarget.addComponent(field_ajax_container);
                //ajaxRequestTarget.appendJavascript("document.all('"+field_button_search.getId()+"').click()");
            }
        });
        this.add(this.field_button_category);
                    // modal window category
        out("modal window category");
        this.field_modal_create_category=new ModalWindow("field_modal_create_category");
        this.field_modal_create_category_body=new Modal_create_category(this.field_modal_create_category);
        this.field_modal_create_category.setPageCreator(new ModalWindow.PageCreator(){
            public Page createPage() {
                return field_modal_create_category_body;
            }
        });
        this.field_modal_create_category.setInitialHeight(100);
        this.field_modal_create_category.setInitialWidth(200);
        this.add(this.field_modal_create_category);

    }
    // Bean "button_category"
    public void setButton_category(String value){
        
    }
    public String getButton_category(){
        return (this.field_dropdown_category_index==0)?"create":"edit";
    }
    // block for full HTTP request when change CATEGORY ComboBox
    public void setCategory(String category){
        out("set category: "+category+"    category Index:"+this.field_dropdown_category_index);
        this.field_dropdown_category_index=this.field_category_values.indexOf(category);
    }
    public String getCategory(){
        return this.field_category_values.get(this.field_dropdown_category_index);
    }
    // ---------------------------
    // ------- inner classes
    /** class DropDownChoice for return Default Value */
    class DropDown extends DropDownChoice {
        /** values */
        private List<String> field_values;
        /** id */
        private String field_id;
        /**
         * constructor
         */
        public DropDown(String id, IModel model,List<String> values){
            super(id,model,values);
            this.field_id=id;
            this.field_values=values;
        }
        public DropDown(String id, List<String> values){
            super(id,values);
            this.field_id=id;
            this.field_values=values;
        }
        
        /**
         * @Override
         */
        protected String getDefaultChoice(Object selected){
            return  this.field_values.get(field_dropdown_category_index);
        }
        
        public void onSelectionChanged(Object newSelection){
            super.onSelectionChanged(newSelection);
            out("new selection:"+newSelection);
            out("new selection class:"+newSelection.getClass().getName());
        }
        protected boolean wantOnSelectionChangedNotifications(){
            return true;
        }
        
        public String toString(){
            return this.getModelValue();
        }
    }
    /** class for getting data form Form*/
    class Bean implements IClusterable{
        private String field_search="";
        public void setField_textfield_search(String value){
            //this.field_search=value;
            out("Bean#setField_textfield_search:"+value);
            this.field_search=value;
            Page_Main.this.field_panel_manager.setSearch(value);
        }
        public String getField_textfield_search(){
            //return field_search;
            out("Bean#getField_textfield_search:"+Page_Main.this.field_panel_manager.getSearch());
            this.field_search=Page_Main.this.field_panel_manager.getSearch();
            return Page_Main.this.field_panel_manager.getSearch();
        }
    }
}
