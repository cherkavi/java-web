package com.cherkashyn.vitalii.bpmnui;

import com.cherkashyn.vitalii.bpmnui.core.ui.common.Caption;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Locale;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // ------------ VAADIN ---------------
    @Bean
    Locale getLocale() {
        return Locale.ENGLISH;
    }

    @Bean
    Caption getCaption(){
        return new Caption(getLocale());
    }

    // -----------------------------------


}
