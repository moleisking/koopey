package com.koopey.api.configuration;

import com.koopey.api.ServerApplication;

import java.util.logging.Logger;

import jakarta.activation.DataSource;
//import java.util.logging.Logger;
import jakarta.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
/*import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;*/
import org.springframework.context.annotation.Configuration;
/*import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;*/

@Configuration
//@EnableJpaRepositories(basePackageClasses = {ServerApplication.class})
//@EnableTransactionManagement
//@ConditionalOnBean(DataSource.class)
class HibernateConfiguration {

 /* @Autowired()
  private DataSource dataSource;

  @Bean
  public EntityManagerFactory entityManagerFactory() {

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setGenerateDdl(true);
    vendorAdapter.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", "update");

    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setJpaVendorAdapter(vendorAdapter);
    factory.setPackagesToScan(ServerApplication.class.getPackage().getName());
    factory.setDataSource(this.dataSource);
    factory.afterPropertiesSet();

    vendorAdapter = null;

    return factory.getObject();
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(entityManagerFactory());
    return txManager;
  }*/
}
