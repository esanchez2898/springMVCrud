package org.example.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class JpaPersistence {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        // Creates the main JPA EntityManagerFactory bean used by Spring to manage persistence.
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        // Injects the HikariCP DataSource (connection pool).
        em.setDataSource(getHikariDataSource());

        // Tells Spring where to scan for @Entity classes.
        em.setPackagesToScan("org.example.entity");

        // Configures Hibernate as the JPA provider.
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        // Adds Hibernate-specific properties (DDL auto, dialect, etc.)
        em.setJpaProperties(additionalProperties());

        // Explicitly sets the EntityManagerFactory interface (rarely used, optional).
        em.setEntityManagerFactoryInterface(EntityManagerFactory.class);

        return em;
    }

    @Bean
    public HikariDataSource getHikariDataSource() {
        // Creates a HikariCP DataSource — a fast and modern JDBC connection pool.
        HikariDataSource dataSource = new HikariDataSource();

        // PostgreSQL JDBC driver.
        dataSource.setDriverClassName("org.postgresql.Driver");

        // Database connection URL.
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/online_store");

        // Database credentials.
        dataSource.setUsername("postgres");
        dataSource.setPassword("1505");

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        // Creates a transaction manager that integrates Spring transactions with JPA/Hibernate.
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        // Links the transaction manager to the EntityManagerFactory.
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        // Enables automatic translation of low-level persistence exceptions
        // into Spring's DataAccessException hierarchy.
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties additionalProperties() {
        // Defines additional Hibernate configuration properties.
        Properties properties = new Properties();

        // Automatically updates the database schema based on entity changes.
        properties.setProperty("hibernate.hbm2ddl.auto", "update");

        // Specifies the SQL dialect for PostgreSQL.
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        return properties;
    }
}
