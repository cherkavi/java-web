package de.smava.recrt.persistence.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Repository configuration
 * Created by pvitic on 11.05.15.
 */
@Configuration
@EnableJpaRepositories(basePackages = {"de.smava.recrt.persistence.repository"})
@EnableTransactionManagement
public class PersistenceConfig {

    private static final Logger LOG = Logger.getLogger(PersistenceConfig.class);

    private EmbeddedDatabase embeddedDb;

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Value("${hibernate.show_sql}")
    private String hibernateShowSql;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateHbm2ddlAuto;

    @Value("${hibernate.default_schema}")
    private String hibernateSchema;

    @PreDestroy
    public void destroy() {
        LOG.info("Shutting down!");
        if (embeddedDb != null) embeddedDb.shutdown();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(this.entityManagerFactory().getObject());
        return tm;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean factory
                = new LocalContainerEntityManagerFactoryBean();

        factory.setDataSource(getDataSource());
        factory.setPackagesToScan("de.smava.recrt.persistence.model");
        factory.setPersistenceUnitName("recrt");
        factory.setJpaVendorAdapter(getJpaVendorAdapter());
        factory.setJpaProperties(getJpaProperties());
        factory.afterPropertiesSet();

        return factory;
    }


    private DataSource getDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        embeddedDb = builder.setType(EmbeddedDatabaseType.H2)
                .addScript("sql/init.sql")
                .build();
        return embeddedDb;
    }

    private JpaVendorAdapter getJpaVendorAdapter() {
        HibernateJpaVendorAdapter adaptor = new HibernateJpaVendorAdapter();
        adaptor.setDatabase(Database.H2);
        adaptor.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
        return adaptor;
    }

    private Properties getJpaProperties() {
        Properties ps = new Properties();
        ps.put("hibernate.default_schema", hibernateSchema);
        ps.put("hibernate.dialect", hibernateDialect);
        ps.put("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
        ps.put("hibernate.show_sql", hibernateShowSql);
        ps.put("flushMode", "COMMIT/AUTO");
        return ps;
    }

}
