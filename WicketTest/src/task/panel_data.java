/*
 * panel_data.java
 *
 * Created on 11 липня 2008, 17:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

/**
 *
 * @author Technik
 */
public class panel_data extends Panel implements panel_manager{
    private boolean FLAG_DEBUG=false;
    private int field_category_index;
    /** property list view*/
    //private PropertyListView field_table_property;
    /** view in DataTable */
    private DataView field_table_data;
    
    private int field_length=3;
    private AjaxLink[] field_ajax_link=new AjaxLink[field_length];
    private boolean[] field_order=new boolean[field_length];
    private String[] field_order_name=new String[field_length];
    private String field_current_order="KOD ASC";
    /** list of data */
    private List<DataRow> field_list=new ArrayList();
    /**  search string   */
    private String field_search_pattern="";
    /** Page for displaying EDIT CONTAKT*/
    private Modal_create_contakt field_page_body;
    private ModalWindow field_modal_create_contakt;
    /** Creates a new instance of panel_data 
     * @param page_body - body of modal Page
     */
    public panel_data(Modal_create_contakt page_body,ModalWindow modal_create_contakt) {
        super("field_panel_data");
        this.field_page_body=page_body;
        this.field_modal_create_contakt=modal_create_contakt;
        this.setOutputMarkupId(true);
        this.create_elements();
        this.field_category_index=0;
        this.create_list(this.field_category_index);
    }
    /**
     * method for Trace
     */
    private void out(String value){
        if(this.FLAG_DEBUG==true){
            System.out.println("panel_data#"+value);
        }
    }
    /** 
     * ORDER set method
     */
    private void set_order(String value){
        //out("set_order# before:"+value+"   :"+this.field_current_order);
        if(this.field_current_order.indexOf(value)>=0){
            // check for DESC
            if(this.field_current_order.indexOf(" DESC")>=0){
                this.field_current_order=value;
            }else{
                this.field_current_order+=" DESC";
            }
        }else{
            this.field_current_order=value;
        }
        //out("set_order# after:"+value+"   :"+this.field_current_order);
    }
    
    /**
     * method for created Element's
     */
    private void create_elements(){
        this.field_order_name=new String[]{"FIELD_NAME","FIELD_PHONE","CATEGORY.FIELD_NAME"};
        for(int counter=0;counter<this.field_length;counter++){
            this.field_order[counter]=false;
            final Integer counter_wrap=new Integer(counter);
            field_ajax_link[counter]=new AjaxLink("field_ajax_link_"+(counter+1)){
                public void onClick(AjaxRequestTarget target){
                    field_order[counter_wrap]=!field_order[counter_wrap];
                    set_order(field_order_name[counter_wrap]);
                    create_list(field_category_index);
                    target.addComponent(panel_data.this);
                }
            };
            this.add(field_ajax_link[counter]);
        };
    }
    /**
     * set Category.KOD for query
     */
    public void setCategoryIndex(int value){
        this.field_category_index=value;
    }
    /**
     * create list of data
     * @param category_index - this is CATEGORY.KOD ( when selected)
     */
    public void create_list(int category_index){
        out("create_list:begin, category:"+category_index);
        this.field_list.clear();
        database_join inner_join=new database_join(database_join.JOIN_LEFT,"CONTACTS","FIELD_KOD_CATEGORY","CATEGORY","KOD");
        List<String[]> list=null;
        StringBuffer where_string=new StringBuffer();
        if(category_index>0){
            // fill list
            where_string.append("FIELD_KOD_CATEGORY="+category_index);
        };
        if((this.field_search_pattern!=null)&&(this.field_search_pattern.trim().length()>0)){
            if(where_string.length()>0){
                where_string.append(" AND \n");
            };
            where_string.append(" FIELD_PHONE LIKE '%"+this.field_search_pattern+"%'");
        }
        list=Start.field_database.get_query_result(new String[]{"KOD","FIELD_NAME","FIELD_PHONE","CATEGORY.FIELD_NAME"},
                                                   "CONTACTS",
                                                   new database_join[]{inner_join},
                                                   where_string.toString(),
                                                   new String[]{this.field_current_order});
        // convert List<String[]> to List<DataRow>
        out("row size:"+list.size());
        for(int row_counter=0;row_counter<list.size();row_counter++){
            DataRow row=new DataRow();
            try{
                row.setKod(list.get(row_counter)[0]);
                row.setColumn_1(list.get(row_counter)[1]);
                row.setColumn_2(list.get(row_counter)[2]);
                row.setColumn_3(list.get(row_counter)[3]);
                out(" counter:"+row_counter+"  column_1:"+list.get(row_counter)[1]+"   column_2:"+list.get(row_counter)[2]+"    column_3:"+list.get(row_counter)[3]);
            }catch(Exception ex){
                out("create +_list error in get value from Array"+ex.getMessage());
            }
            this.field_list.add(row);
        }
        // show list on Page
        // PropertyListView
/*        add(new Label("field_navigator",""));
        if(this.field_table_property==null){
            this.field_table_property=new PropertyListView("table"){
                  public void populateItem(final ListItem listItem){
                    AjaxLink ajax_link=new AjaxLink("field_ajax_kod") {
                        public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                            System.out.println("getIndex:"+listItem.getIndex());
                            panel_data.this.field_page_body.load_by_kod(Integer.toString(listItem.getIndex()));
                            panel_data.this.field_page_body.setEdit(true);
                            field_modal_create_contakt.show(ajaxRequestTarget);
                        }
                    };
                    ajax_link.add(new Label("column_1"));
                    listItem.add(ajax_link);
                    listItem.add(new Label("column_2"));
                    listItem.add(new Label("column_3"));
                    listItem.add(new AttributeModifier("bgcolor",true,new AbstractReadOnlyModel(){
                        public Object getObject() {
                            return (listItem.getIndex()%2==0)?"silver":"white";
                        }
                        
                    }));
                }
            };
            this.add(field_table_property);
        };
        this.field_table_property.setList(this.field_list);
*/

        if(this.field_table_data==null){
            this.field_table_data=new DataView("table",new panel_data_provider(this.field_list)){
                protected void populateItem(final Item listItem){
                    //out("populateItem:"+listItem+"    Class:"+listItem.getModelObject().getClass().toString()+"  Value:"+listItem.getModelObject());
                    DataRow current_data=(DataRow)listItem.getModelObject();
                    AjaxLink ajax_link=new AjaxLink("field_ajax_kod") {
                        public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                            panel_data.this.field_page_body.load_by_kod(((DataRow)listItem.getModelObject()).getKod());
                            panel_data.this.field_page_body.setEdit(true);
                            field_modal_create_contakt.show(ajaxRequestTarget);
                        }
                    };
                    ajax_link.add(new Label("column_1",current_data.getColumn_1()));
                    //ajax_link.add(new Label("column_1"));
                    listItem.add(ajax_link);
                    listItem.add(new Label("column_2",current_data.getColumn_2()));
                    //listItem.add(new Label("column_2"));
                    listItem.add(new Label("column_3",current_data.getColumn_3()));
                    //listItem.add(new Label("column_3"));
                    listItem.add(new AttributeModifier("bgcolor",true,new AbstractReadOnlyModel(){
                        public Object getObject() {
                            return (listItem.getIndex()%2==0)?"silver":"white";
                        }
                    }));
                }
            };
            this.field_table_data.setItemsPerPage(5);
            add(this.field_table_data);
            add(new PagingNavigator("field_navigator",this.field_table_data));
        }
 
        out("create_list:end");
    }
    /** GET Search*/
    public String getSearch(){
        return this.field_search_pattern;
    }
    /** SET Search*/
    public void setSearch(String value){
        this.field_search_pattern=value;
    }
    
}
