package com.gwsh.shortlink.load.data;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;
import com.gwsh.shortlink.load.common.CassandraVariables;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShortLinkRepository {

    private final CqlSession session;

    @PostConstruct
    private void createTable() {
        CreateTable createTableStatement = SchemaBuilder.createTable("SHORT_LINKS")
                .withPartitionKey("short_link_id", DataTypes.UUID)
                .withColumn("full_link", DataTypes.TEXT)
                .withColumn("short_link", DataTypes.TEXT);

        SimpleStatement statement = createTableStatement.build().setKeyspace(CassandraVariables.KEYSPACE_1);

        session.execute(statement);
    }
}
