/*
 * panel_velocity.java
 *
 * Created on 17 липня 2008, 16:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package task;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.resource.IStringResourceStream;
import org.apache.wicket.util.resource.PackageResourceStream;
import org.apache.wicket.velocity.markup.html.VelocityPanel;
/**
 *
 * @author Technik
 */
public class panel_velocity extends Panel implements panel_manager{
    private boolean FLAG_DEBUG=false;
    private int field_category_index;
    private String field_search_pattern="";
    private VelocityPanel field_velocity_panel;
    private IModel field_model;
    private String field_current_order="KOD ASC";
    private int field_length=3;
    private AjaxLink[] field_ajax_link=new AjaxLink[field_length];
    private boolean[] field_order=new boolean[field_length];    
    /** list of data */
    private List<DataRow> field_list=new ArrayList();

    private String[] field_order_name;

    
    /**
     * method for Trace
     */
    private void out(String value){
        if(this.FLAG_DEBUG==true){
            System.out.println("panel_velocity#"+value);
        }
    }
    
    /** Creates a new instance of panel_velocity */
    public panel_velocity() {
        super("field_panel_data");
        this.setOutputMarkupId(true);
        this.create_elements();
        this.field_category_index=0;
        this.create_list(this.field_category_index);
        
    }

    public void create_list(int category_index) {
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
        list=Start.field_database.get_query_result(this.field_order_name,"CONTACTS",new database_join[]{inner_join},where_string.toString(),new String[]{this.field_current_order});
        // convert List<String[]> to List<DataRow>
        out("list size:"+list.size());
        for(int row_counter=0;row_counter<list.size();row_counter++){
            DataRow row=new DataRow();
            try{
                row.setColumn_1(list.get(row_counter)[0]);
                row.setColumn_2(list.get(row_counter)[1]);
                row.setColumn_3(list.get(row_counter)[2]);
                out("0:"+row.getColumn_1()+"  1:"+row.getColumn_2()+"   2:"+row.getColumn_3());
            }catch(Exception ex){
                out("create +_list error in get value from Array"+ex.getMessage());
            }
            this.field_list.add(row);
        }
        
        // show list on Page
/*        if(this.field_table_property==null){
            this.field_table_property=new PropertyListView("table"){
                // @Override
                public void populateItem(final ListItem listItem){
                    listItem.add(new Label("column_1"));
                    listItem.add(new Label("column_2"));
                    listItem.add(new Label("column_3"));
                }
            };
            this.add(field_table_property);
        };
        this.field_table_property.setList(this.field_list);
        */
        
        
	Map<String, List<DataRow>> map = new HashMap<String, List<DataRow>>();
	map.put("contacts", this.field_list);

        this.field_velocity_panel.setModel(Model.valueOf(map));
        
        out("create_list:end");
    }

    public String getSearch() {
        return field_search_pattern;
    }

    public void setSearch(String value) {
        this.field_search_pattern=value;
    }

    public void setCategoryIndex(int value) {
    }

    private void create_elements() {
        this.field_order_name=new String[]{"FIELD_NAME","FIELD_PHONE","CATEGORY.FIELD_NAME"};
        
        // velocity panel
        this.field_model=Model.valueOf(this.field_list);
        final IStringResourceStream field_template=new PackageResourceStream(panel_velocity.class,"panel_velocity.vm");
        this.field_velocity_panel= new VelocityPanel("field_velocity_panel",this.field_model){
            protected IStringResourceStream getTemplateResource(){
                return field_template;
            }

            protected boolean parseGeneratedMarkup(){
                return true;
            }
        };
        add(this.field_velocity_panel);

        // header link
        for(int counter=0;counter<this.field_length;counter++){
            this.field_order[counter]=false;
            final Integer counter_wrap=new Integer(counter);
            field_ajax_link[counter]=new AjaxLink("field_ajax_link_"+(counter+1)){
                public void onClick(AjaxRequestTarget target){
                    field_order[counter_wrap]=!field_order[counter_wrap];
                    set_order(field_order_name[counter_wrap]);
                    create_list(field_category_index);
                    target.addComponent(panel_velocity.this);
                }
            };
            this.field_velocity_panel.add(field_ajax_link[counter]);
        };

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
    
}
