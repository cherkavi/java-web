/*
 * panel_data_model.java
 *
 * Created on 21 липня 2008, 17:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package task;

import org.apache.wicket.model.LoadableDetachableModel;

/**
 *
 * @author Technik
 */
public class panel_data_model extends LoadableDetachableModel {
    private DataRow field_data;
    /** Creates a new instance of panel_data_model */
    public panel_data_model(DataRow data) {
        this.field_data=data;
    }

    protected Object load() {
        return this.field_data;
    };
    
    
    
}
