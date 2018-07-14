package com.cherkashyn.vitalii.export.documentscanner;

import com.cherkashyn.vitalii.export.documentscanner.persistent.fs.OrderFSProcessorStorage;
import com.cherkashyn.vitalii.export.documentscanner.persistent.fs.OrderFSStorage;
import com.cherkashyn.vitalii.export.documentscanner.persistent.fs.SetPostProcessor;
import com.cherkashyn.vitalii.export.documentscanner.persistent.nosql.Order;
import com.cherkashyn.vitalii.export.documentscanner.persistent.nosql.OrderFileItem;
import com.cherkashyn.vitalii.export.documentscanner.ui.common.Caption;
import com.cherkashyn.vitalii.export.documentscanner.ui.common.Exchange;
import com.cherkashyn.vitalii.export.documentscanner.utility.BarCodeRecognizer;
import com.cherkashyn.vitalii.export.documentscanner.utility.PdfMerger;
import com.vaadin.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.util.Locale;

@SpringBootApplication
public class Application {

    @Value("${storage.originalfiles.temp}")
    private String fileSystemOriginalFilesTemp;

    @Value("${storage.originalfiles.permanent}")
    private String fileSystemOriginalFilesPermanent;

    @Value("${storage.icons}")
    private String fileSystemIcons;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    Locale getLocale() {
        return Locale.ENGLISH;
    }

    @Bean
    Caption getCaption(){
        return new Caption(getLocale());
    }

    @Bean
    @VaadinSessionScope
    Exchange getShared(){
        return new Exchange();
    }

    @Bean
    BarCodeRecognizer barCodeRecognizer(){
        return new BarCodeRecognizer();
    }

    @Bean
    PdfMerger getPdfMerger(){
        return new PdfMerger();
    }

    @Bean
    OrderFSProcessorStorage fsOriginalFilesTemp(){
        return new OrderFSProcessorStorage(this.fileSystemOriginalFilesTemp,
                new SetPostProcessor(
                        this.fileSystemIcons,
                        "convert %s -resize x400 %s",
                        "jpg", "png", "jpeg"){
                    @Override
                    public File getRealFile(Order order, OrderFileItem fileItem) {
                        return new File(super.getRealFile(order, fileItem).getPath()+".jpg");
                    }
                },
        new SetPostProcessor(
                this.fileSystemIcons,
                "convert -density 50 %s -quality 25 %s",
                "pdf"){
            @Override
            public File getRealFile(Order order, OrderFileItem fileItem) {
                return new File(super.getRealFile(order, fileItem).getPath()+".jpg");
            }
        });
    }

    @Bean
    OrderFSStorage fsOriginalFilesPermanent(){
        return new OrderFSStorage(this.fileSystemOriginalFilesPermanent);
    }

    @Bean
    OrderFSStorage fsIcons(){
        return new OrderFSStorage(this.fileSystemIcons){
            @Override
            public File getRealFile(Order order, OrderFileItem fileItem) {
                return new File(super.getRealFile(order, fileItem).getPath()+".jpg");
            }
        };
    }

}
