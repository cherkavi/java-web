package com.cherkashyn.vitalii.bpmnui;

import com.cherkashyn.vitalii.bpmnui.core.bpm.PrintActivitiEnvironment;
import com.cherkashyn.vitalii.bpmnui.core.bpm.listener.ProcessEndListener;
import com.cherkashyn.vitalii.bpmnui.core.bpm.listener.TaskEndListener;
import com.cherkashyn.vitalii.bpmnui.core.bpm.listener.ProcessStartListener;
import com.cherkashyn.vitalii.bpmnui.core.bpm.listener.TaskStartListener;
import com.cherkashyn.vitalii.bpmnui.core.ui.common.Caption;
import com.cherkashyn.vitalii.bpmnui.core.ui.common.Exchange;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.annotation.SessionScope;

import javax.sql.DataSource;
import java.util.Locale;

@SpringBootApplication
public class ApplicationTest {

    @Value("${storage.originalfiles.temp}")
    private String fileSystemOriginalFilesTemp;

    @Value("${storage.originalfiles.permanent}")
    private String fileSystemOriginalFilesPermanent;

    @Value("${storage.icons}")
    private String fileSystemIcons;

    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class, args);
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

    @Bean
    @SessionScope
    // SpringComponent
    Exchange getShared(){
        return new Exchange();
    }
    // -----------------------------------

    @Bean
    public ProcessEngine springProcessEngineConfiguration(){
        // standalone configuration
        ProcessEngineConfigurationImpl configuration = (ProcessEngineConfigurationImpl) SpringProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        return configuration.buildProcessEngine();
    }

    // ------------ DataSources ---------------
    @Primary
    @Bean("usermanagerDataSourceProperties")
    @ConfigurationProperties(prefix = "datasource.usermanager")
    public DataSourceProperties userManagerDataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean("usermanagerDataSource")
    public DataSource usermanagerDataSource(@Qualifier("usermanagerDataSourceProperties") DataSourceProperties properties) {
        return buildDataSource(properties);
    }

    private DataSource buildDataSource(DataSourceProperties properties){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        dataSource.setUrl(properties.getUrl());
        dataSource.setDriverClassName(properties.getDriverClassName());
        return dataSource;
    }
    // -----------------------------------

    @Bean
    public TaskListener listenerStartTask(){
        return new TaskStartListener();
    }
    @Bean
    public TaskListener listenerEndTask(){
        return new TaskEndListener();
    }
    @Bean
    public ExecutionListener listenerStartProcess(){
        return new ProcessStartListener();
    }
    @Bean
    public ExecutionListener listenerEndProcess(){
        return new ProcessEndListener();
    }

    @Bean
    public PrintActivitiEnvironment printActivitiEnvironment(){
        return new PrintActivitiEnvironment();
    }
    // -----------------------------------

//    @Bean
//    public CommandLineRunner readProcesses(ProcessEngine processEngine){
//        return (args)->{
//                String path= "processes/direct-run.bpmn";
//                System.out.println(String.format("deploy app: %s ", path));
//                processEngine.getRepositoryService().createDeployment()
//                        // .addInputStream()
//                    .addClasspathResource(path)
//                    .deploy();
//        };
//    }

}
