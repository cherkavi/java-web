/*
 * panel_data_sorter.java
 *
 * Created on 20 липня 2008, 6:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 *
 * @author Technik
 */
public class panel_data_provider extends SortableDataProvider{
    private boolean FLAG_DEBUG=false;
    private List<DataRow> field_list;
    
    /**
     * method for Trace
     */
    private void out(String value){
        if(this.FLAG_DEBUG==true){
            System.out.print("panel_data_provider#");System.out.println(value);
        }
    }
        
    /** Creates a new instance of panel_data_sorter */
    public panel_data_provider(List<DataRow> list) {
        this.field_list=list;
    }

    
    public Iterator iterator(int first, int count) {
        out("iterator: first:"+first+"    count:"+count);
        return this.field_list.subList(first,count+first).iterator();
    }

    public int size() {
        out("size: "+this.field_list.size());
        return this.field_list.size();
    }


    public IModel model(Object object) {
        return new panel_data_model((DataRow)object);
    }
}
