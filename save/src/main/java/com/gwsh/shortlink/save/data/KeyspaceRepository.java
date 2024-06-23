package com.gwsh.shortlink.save.data;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import com.gwsh.shortlink.save.service.CassandraService;
import lombok.RequiredArgsConstructor;

import static com.gwsh.shortlink.save.common.CassandraVariables.KEYSPACE_1;

@RequiredArgsConstructor
public class KeyspaceRepository {

    private final CassandraService cassandraService;
    private CqlSession session;

    public void createKeyspace(String keyspace, int replicas){
        session = cassandraService.openSession(keyspace);

        CreateKeyspace createKeyspace = SchemaBuilder.createKeyspace(keyspace)
                .ifNotExists()
                .withSimpleStrategy(replicas);

        session.execute(createKeyspace.build());

        cassandraService.close();
    }

    public void useKeyspace(String keyspace) {
        session = cassandraService.openSession(keyspace);
        session.execute("USE " + CqlIdentifier.fromCql(keyspace));
        cassandraService.close();
    }
}
