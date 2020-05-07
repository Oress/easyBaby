package com.bshop;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.postgresql.ds.PGPoolingDataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;


@Configuration
public class SpringFactory {
    @Bean(name = "entityManagerFactory")
    public SessionFactory getSessionFactory() throws NamingException, SQLException {

        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .applySetting(Environment.DATASOURCE, getDataSource())
                .configure()
                .build();

        Metadata metadata = new MetadataSources(standardRegistry)
                .getMetadataBuilder()
                .build();

        return metadata.getSessionFactoryBuilder().build();
    }

    @Bean(name = "transactionManager")
    public TransactionManager getTransactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    public static DataSource getDataSource() throws SQLException {
//        PGSimpleDataSource dataSource = new PGSimpleDataSource();
//        dataSource.setUrl("jdbc:postgresql://localhost:5432/bshop");
//        dataSource.setUser("postgres");
//        dataSource.setPassword("pwd");
//        return dataSource;

//        PGPoolingDataSource ds = new PGPoolingDataSource();
//        ds.setUrl("jdbc:postgresql://localhost:5432/bshop");
//        ds.setUser("postgres");
//        ds.setPassword("pwd");

        MysqlDataSource ds = new MysqlDataSource();
        ds.setUrl("jdbc:mysql://localhost:3306/bshop");
        ds.setPassword("pwd");
        ds.setUser("root");

//        Properties props = new Properties();
//
//        props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
//        props.setProperty("dataSource.portNumber", "5432");
//        props.setProperty("dataSource.user", "postgres");
//        props.setProperty("dataSource.password", "pwd");
//        props.setProperty("dataSource.databaseName", "bshop");
//        props.put("dataSource.logWriter", new PrintWriter(System.out));
//
//        HikariConfig config = new HikariConfig(props);
//        HikariDataSource ds = new HikariDataSource(config);
        return ds;
    }
}
