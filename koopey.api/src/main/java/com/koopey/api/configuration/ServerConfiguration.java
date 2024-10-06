package com.koopey.api.configuration;

import java.util.logging.Logger;

import org.apache.catalina.connector.Connector;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
//import org.springframework.boot.web.embedded.tomcat.
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class ServerConfiguration implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                connector.setProperty("maxHttpResponseHeaderSize", "100000");
            }
        });
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /*
     * @Autowired private Environment environment;
     * 
     * @Bean(destroyMethod = "close") public DataSource dataSource() {
     * BasicDataSource dataSource = new BasicDataSource();
     * dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
     * dataSource.setUrl(env.getProperty("jdbc.url"));
     * dataSource.setUsername(env.getProperty("jdbc.username"));
     * dataSource.setPassword(env.getProperty("jdbc.password")); return dataSource;
     * }
     * 
     * @Bean public JpaTransactionManager jpaTransactionManager() {
     * JpaTransactionManager transactionManager = new JpaTransactionManager();
     * transactionManager.setEntityManagerFactory(entityManagerFactoryBean().
     * getObject()); return transactionManager; }
     * 
     * private HibernateJpaVendorAdapter vendorAdaptor() { HibernateJpaVendorAdapter
     * vendorAdapter = new HibernateJpaVendorAdapter();
     * vendorAdapter.setShowSql(true); return vendorAdapter; }
     */
    // @Bean
    // public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {

    //     LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    //     entityManagerFactoryBean.setJpaVendorAdapter(vendorAdaptor());
    //     entityManagerFactoryBean.setDataSource(dataSource());
    //     entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
    //     entityManagerFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
    //     entityManagerFactoryBean.setJpaProperties(jpaHibernateProperties());

    //     return entityManagerFactoryBean;
    // }

}