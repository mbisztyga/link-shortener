package com.gwsh.shortlink.load.service;

import com.gwsh.shortlink.load.common.CassandraVariables;
import com.datastax.oss.driver.api.core.CqlSession;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;

@Service
@Component
public class CassandraService {

    @Getter
    private CqlSession session;

    public CqlSession openSession(String keyspace) {
        session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(CassandraVariables.NODE, CassandraVariables.PORT))
                .withLocalDatacenter(CassandraVariables.DATACENTER_1)
                .withAuthCredentials("cassandra", "cassandra")
                .build();

        return session;
    }

    public void close() {
        session.close();
    }
}
