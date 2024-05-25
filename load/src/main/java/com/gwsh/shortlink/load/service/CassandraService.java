package com.gwsh.shortlink.load.service;

import com.gwsh.shortlink.load.common.CassandraVariables;
import com.datastax.oss.driver.api.core.CqlSession;
import com.gwsh.shortlink.load.repository.KeyspaceRepository;
import lombok.Getter;

import java.net.InetSocketAddress;

public class CassandraService {

    @Getter
    private CqlSession session;

    public CqlSession openSession(String keyspace) {
        session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(CassandraVariables.NODE, CassandraVariables.PORT))
                .withLocalDatacenter(CassandraVariables.DATACENTER_1)
                .withAuthCredentials("cassandra", "cassandra")
                .build();

        KeyspaceRepository keyspaceRepo = new KeyspaceRepository(session);

        keyspaceRepo.createKeyspace(keyspace, 1);
        keyspaceRepo.useKeyspace(keyspace);

        return session;
    }

    public void close() {
        session.close();
    }
}
