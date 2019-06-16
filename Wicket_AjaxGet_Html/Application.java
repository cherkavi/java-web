/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wicket_ejb.wicket;

import com.wicket_ejb.wicket.start_page.HomePage;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.javaee.injection.JavaEEComponentInjector;
import org.apache.wicket.application.IComponentInstantiationListener;

/**
 *
 * @author Администратор
 */
public class Application extends WebApplication{

    @Override
    protected void init() {
        super.init();
        // addComponentInstantiationListener( (IComponentInstantiationListener) new JavaEEComponentInjector(this));
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

}
