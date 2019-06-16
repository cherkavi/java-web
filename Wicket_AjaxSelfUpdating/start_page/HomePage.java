/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wicket_ejb.wicket.start_page;

import com.test.service.dao.db.TableTestFacadeLocal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;

/**
 */
public class HomePage extends WebPage{
    private Model<String> timeModel=new Model<String>();

    /*
    @EJB (name="WicketEJB-ejb/TableTest")
    private TableTestFacadeLocal localFacade;
     *
     */

    public HomePage(){
        WebMarkupContainer labelContainer=new WebMarkupContainer("label_container");
        this.add(labelContainer);
        final Label label=new Label("label_main", timeModel);
        label.setOutputMarkupId(true);
        labelContainer.add(label);
        labelContainer.setOutputMarkupId(true);
        labelContainer.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(2)){
            @Override
            protected void onPostProcessTarget(AjaxRequestTarget target) {
                super.onPostProcessTarget(target);
                SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
                timeModel.setObject(sdf.format(new Date()));
                target.addComponent(label);
            }
        });
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        this.timeModel.setObject(sdf.format(new Date()));
        // this.timeModel.setObject(sdf.format(new Date())+ this.getNameFromTableTest());
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
