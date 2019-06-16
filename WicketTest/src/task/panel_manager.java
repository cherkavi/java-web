/*
 * panel_manager.java
 *
 * Created on 17 липня 2008, 14:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package task;

/**
 * this interface for governing panel with data
 */
public interface panel_manager {
    /**
     * create list of data
     * @param category_index - this is CATEGORY.KOD ( when selected)
     */
    public void create_list(int category_index);
    /** GET Search*/
    public String getSearch();
    /** SET Search*/
    public void setSearch(String value);
    /**
     * set Category.KOD for query
     */
    public void setCategoryIndex(int value);    
}
