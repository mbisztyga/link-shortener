package com.gwsh.shortlink.save.common;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;

import com.gwsh.shortlink.save.data.repository.KeyspaceRepository;
import com.gwsh.shortlink.save.service.CassandraService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class DbInit {

    private final CassandraService service;

    @PostConstruct
    private synchronized void initCassandra() {
        createKeyspace();
        createTable();
    }

    private void createKeyspace() {
        log.info("Keyspace init");
        KeyspaceRepository keyspaceRepo = new KeyspaceRepository(service);

        keyspaceRepo.createKeyspace(CassandraVariables.KEYSPACE_1, 1);
        keyspaceRepo.useKeyspace(CassandraVariables.KEYSPACE_1);
        log.info("Keyspace created");
    }

    private void createTable() {
        log.info("Table init");
        CqlSession session = service.openSession(CassandraVariables.KEYSPACE_1);
        SimpleStatement dropTableStatement = SchemaBuilder
                .dropTable(CassandraVariables.KEYSPACE_1,"short_links")
                .ifExists().build();
        session.execute(dropTableStatement);
        log.info("Table dropped if it existed");
        CreateTable createTableStatement = SchemaBuilder
                .createTable(CassandraVariables.KEYSPACE_1,"SHORT_LINKS")
                .withPartitionKey("short_link", DataTypes.TEXT)
                .withColumn("full_link", DataTypes.TEXT);

        SimpleStatement statement = createTableStatement.build().setKeyspace(CassandraVariables.KEYSPACE_1);

        session.execute(statement);
        log.info("Table created");

        service.close();
    }
}
