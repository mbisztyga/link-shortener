package com.gwsh.shortlink.save.common;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;
import com.gwsh.shortlink.load.repository.KeyspaceRepository;
import com.gwsh.shortlink.load.service.CassandraService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbInit {

    private final CassandraService service;

    @Autowired
    public DbInit(CassandraService service) {
        this.service = service;
    }

    @PostConstruct
    private void createKeyspace() {
        KeyspaceRepository keyspaceRepo = new KeyspaceRepository(service);

        keyspaceRepo.createKeyspace(CassandraVariables.KEYSPACE_1, 1);
        keyspaceRepo.useKeyspace(CassandraVariables.KEYSPACE_1);
    }

    @PostConstruct
    private void createTable() {
        CqlSession session = service.openSession(CassandraVariables.KEYSPACE_1);
        CreateTable createTableStatement = SchemaBuilder.createTable("SHORT_LINKS")
                .withPartitionKey("short_link", DataTypes.TEXT)
                .withColumn("full_link", DataTypes.TEXT);

        SimpleStatement statement = createTableStatement.build().setKeyspace(CassandraVariables.KEYSPACE_1);

        session.execute(statement);

        service.close();
    }
}
