package bo.gob.aj.rio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "msEntityManagerFactory", transactionManagerRef = "msTransactionManager", basePackages = {"bo.gob.aj.rio.mssql.repository"})
public class SqlServerDbConfig {

    @Autowired
    private Environment env;

    @Bean(name = "msDataSource")
    @ConfigurationProperties(prefix = "mssql.datasource")
    public DataSource msDataSource() {

        /*return DataSourceBuilder
                .create()
                .build();*/

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("mssql.datasource.driverClassName"));
        dataSource.setUrl(env.getProperty("mssql.datasource.url"));
        dataSource.setUsername(env.getProperty("mssql.datasource.username"));
        dataSource.setPassword(env.getProperty("mssql.datasource.password"));

        return dataSource;
    }

    @Bean(name = "msEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean msEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(msDataSource())
                .packages("bo.gob.aj.rio.mssql.entity")
                .persistenceUnit("bar")
                .build();
    }

    @Bean(name = "msTransactionManager")
    public PlatformTransactionManager msTransactionManager(@Qualifier("msEntityManagerFactory") EntityManagerFactory msEntityManagerFactory) {
        return new JpaTransactionManager(msEntityManagerFactory);
    }
}
