/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.ejbutil;

/**
 *
 * @author hisham
 */
import java.sql.Connection;
import java.sql.SQLException;
 
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;
 
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
//import org.flywaydb.core.Flyway;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;
//import org.flywaydb.core.api.MigrationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Startup
@TransactionManagement(TransactionManagementType.BEAN)
public class FlyWayConfig {
    private final Logger log = LoggerFactory.getLogger(FlyWayConfig.class);
    private static final String STAGE = "development";
	private static final String CHANGELOG_FILE = "liquibase/changelog-master.xml";
 
    @Resource(lookup = "jdbc/sypron")
    private DataSource dataSource;

    @PostConstruct
    public void initFlyWay() {
        
        if (dataSource == null) {
            log.error("no datasource found to execute the db migrations!");
            throw new EJBException(
                    "no datasource found to execute the db migrations!");
        }
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor(getClass().getClassLoader());
        try (Connection connection = dataSource.getConnection()) {
            JdbcConnection jdbcConnection = new JdbcConnection(connection);
            Database db = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);

            Liquibase liquiBase = new Liquibase(CHANGELOG_FILE, resourceAccessor, db);
            liquiBase.update(STAGE);
        } catch (SQLException | LiquibaseException e) {
        }
//        Flyway flyway = new Flyway();
//        MigrationInfo migrationInfo = flyway.info().current();
//
//        if (migrationInfo == null) {
//            log.info("No existing database at the actual datasource");
//        } else {
//            log.info("Found a database with the version: {}", migrationInfo.getVersion() + " : "
//                    + migrationInfo.getDescription());
//        }
//        flyway.setDataSource(dataSource);
//        flyway.migrate();
//        log.info("Successfully migrated to database version: {}", flyway.info().current().getVersion());
    }
}

 
   
 
 
    

 
