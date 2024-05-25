package com.gwsh.shortlink.load.repository;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KeyspaceRepository {

    private final CqlSession session;

    public void createKeyspace(String keyspaceName, int replicas){
        CreateKeyspace createKeyspace = SchemaBuilder.createKeyspace(keyspaceName)
                .ifNotExists()
                .withSimpleStrategy(replicas);

        session.execute(createKeyspace.build());
    }

    public void useKeyspace(String keyspace) {
        session.execute("USE " + CqlIdentifier.fromCql(keyspace));
    }
}
