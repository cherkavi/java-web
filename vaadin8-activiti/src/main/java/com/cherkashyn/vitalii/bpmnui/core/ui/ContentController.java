package com.cherkashyn.vitalii.bpmnui.core.ui;

import com.vaadin.ui.Component;

/**
 * change content
 */
public interface ContentController {

    /**
     * change content with new representation
     * @param clazz representation to be shown
     */
    void setRoot(Class<? extends Component>  clazz);

    /**
     * change content with new representation
     * @param component will be shown
     */
    void setRoot(Component  component);
}
