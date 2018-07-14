package com.cherkashyn.vitalii.bpmnui;

import com.cherkashyn.vitalii.bpmnui.core.bpm.PrintActivitiEnvironment;
import com.cherkashyn.vitalii.bpmnui.core.bpm.listener.ProcessEndListener;
import com.cherkashyn.vitalii.bpmnui.core.bpm.listener.ProcessStartListener;
import com.cherkashyn.vitalii.bpmnui.core.bpm.listener.TaskEndListener;
import com.cherkashyn.vitalii.bpmnui.core.bpm.listener.TaskStartListener;
import com.cherkashyn.vitalii.bpmnui.core.ui.common.Caption;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
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
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
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
        String[] disabledCommands = {"--spring.shell.command.quit.enabled=false"};
        String[] fullArgs = StringUtils.concatenateStringArrays(args, disabledCommands);
        SpringApplication.run(Application.class, fullArgs);
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


    // ------------ DataSources ---------------
    @Bean("usermanagerDataSourceProperties")
    @ConfigurationProperties(prefix = "datasource.usermanager")
    public DataSourceProperties userManagerDataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean("usermanagerDataSource")
    public DataSource usermanagerDataSource(@Qualifier("usermanagerDataSourceProperties") DataSourceProperties properties) {
        return buildDataSource(properties);
    }

    @Bean("activitiDataSourceProperties")
    @Primary
    @ConfigurationProperties(prefix = "datasource.activiti")
    public DataSourceProperties activitiDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("activitiDataSource")
    public DataSource activitiDataSource(@Qualifier("activitiDataSourceProperties") DataSourceProperties properties) {
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


    // ------------ Activiti ---------------
    @Bean
    public ProcessEngineConfigurationImpl springProcessEngineConfiguration(@Qualifier("activitiDataSource") DataSource activitiDataSource){
        // standalone configuration
        // ProcessEngineConfigurationImpl configuration = (ProcessEngineConfigurationImpl) SpringProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();

        // skip using TransactionManager
        // SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration(){public CommandInterceptor createTransactionInterceptor() {return null;}};

        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setTransactionManager(new DataSourceTransactionManager(activitiDataSource));
        configuration.setDataSource(activitiDataSource);
        // configuration.setDatabaseSchemaUpdate("create-drop");
        // configuration.setHistoryLevel(HistoryLevel.AUDIT);
        return configuration;
    }

    @Bean
    public ProcessEngine activitiProcessEngine(ProcessEngineConfigurationImpl configuration, ApplicationContext context) throws Exception {
        ProcessEngineFactoryBean factory = new ProcessEngineFactoryBean();
        factory.setApplicationContext(context);
        factory.setProcessEngineConfiguration(configuration);
        ProcessEngine processEngine= factory.getObject();
        return processEngine;
    }

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
