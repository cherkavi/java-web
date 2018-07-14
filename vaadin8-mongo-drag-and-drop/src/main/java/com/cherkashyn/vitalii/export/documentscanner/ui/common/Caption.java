package com.cherkashyn.vitalii.export.documentscanner.ui.common;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Caption {

    private final ResourceBundle resourceBundle;
    private final static String FILE_PREFIX = "captions";

    public Caption(@Autowired Locale locale){
        resourceBundle = ResourceBundle.getBundle(FILE_PREFIX,locale);
    }

    public String get(String key) {
        try{
            return  resourceBundle.getString(key);
        }catch(MissingResourceException e){
            //LOGGER.warn("Resource "+key+" was not found in the resources file");
            return key;
        }
    }
}
