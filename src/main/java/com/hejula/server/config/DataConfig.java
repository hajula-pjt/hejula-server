/*
package com.hejula.server.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.dialect.MySQLDialect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

*/
/**
 * @author jooyeon
 * @since 2021.07.17
 *//*

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "hejulaEntityManager",
        transactionManagerRef = "hejulaTransactionManager",
        basePackages = {"com.hejula.server.repository"}
)
public class DataConfig {
    @Bean(name = "dataSourceConfig")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Primary
    @Bean(name = "hejulaDataSource")
    public DataSource dataSource() {
        DataSource dataSource = new HikariDataSource(hikariConfig());
        return dataSource;
    }

    @Primary
    @Bean(name = "hejulaEntityManager")
    protected LocalContainerEntityManagerFactoryBean hejulaEntityManager(
            @Qualifier("hejulaDataSource") DataSource hejulaDataSource) throws Exception {

        LocalContainerEntityManagerFactoryBean containerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        containerEntityManagerFactoryBean.setPersistenceUnitName("hejula");
        configureEntityFactory(containerEntityManagerFactoryBean, hejulaDataSource);

        return containerEntityManagerFactoryBean;
    }

    @Primary
    @Bean(name = "hejulaTransactionManager")
    public PlatformTransactionManager transactionManagerFactory(
            @Qualifier("hejulaDataSource") DataSource hejulaDataSource) throws Exception {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(hejulaEntityManager(hejulaDataSource).getObject());
        return transactionManager;
    }


    protected void configureEntityFactory(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean, DataSource dataSource) {

        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan("com.hejula.server.entities");

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);

        entityManagerFactoryBean.setJpaProperties(hibernateProperties());
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", MySQLDialect.class);
        properties.put("spring.jpa.properties.hibernate.use_sql_comments", true);
        properties.put("spring.jpa.show_sql", true);
        properties.put("spring.jpa.properties.hibernate.format_sql", true);

        properties.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        properties.put("hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");

        return properties;
    }
}
*/
