/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wicket_ejb.wicket.start_page;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;


/**
 */
public class HomePage extends WebPage{
    private AbstractDefaultAjaxBehavior click=null;
    private Model<String> modelForLabel=new Model<String>("");
    /*
    @EJB (name="WicketEJB-ejb/TableTest")
    private TableTestFacadeLocal localFacade;
     *
     */

    public HomePage(){
        // auto update

        final Label labelPointer=new Label("pointer",modelForLabel);
        this.add(labelPointer);
        labelPointer.setOutputMarkupId(true);
        click=new AbstractDefaultAjaxBehavior() {
            @Override
            protected void respond(AjaxRequestTarget target) {
                Integer x=Integer.parseInt(RequestCycle.get().getRequest().getParameter("x"));
                Integer y=Integer.parseInt(RequestCycle.get().getRequest().getParameter("y"));

                modelForLabel.setObject("Mouse clicked("+new SimpleDateFormat("HH:mm:ss").format(new Date())+"): "+x+" : "+y);
                target.addComponent(labelPointer);
            }
        };

        WebMarkupContainer mouseArea=new WebMarkupContainer("mouse_area"){

            @Override
            protected void onComponentTag(ComponentTag tag) {
                tag.put("style", "width:500px;height:500px;background-color:gray;");
                tag.put("onMouseDown", "wicketAjaxGet('"+click.getCallbackUrl()+"&x='+event.pageX+'&y='+event.pageY);");
            }
        };
        mouseArea.add(click);
        this.add(mouseArea);
    }

    /*
    private String getNameFromTableTest(){
        try{
            return this.localFacade.find(new Integer(1)).getName();
        }catch(Exception ex){
            return ex.getMessage();
        }
    }
     *
     */
}
