package com.gwsh.shortlink.save.data;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.insert.RegularInsert;
import com.gwsh.shortlink.save.common.CassandraVariables;
import com.gwsh.shortlink.save.service.CassandraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ShortLinkRepository {

    private final CassandraService cassandraService;

    public String add(String fullLink, String shortLink) {
        CqlSession session = cassandraService.openSession(CassandraVariables.KEYSPACE_1);

        RegularInsert insert = QueryBuilder.insertInto("SHORT_LINKS")
                .value("full_link", QueryBuilder.bindMarker())
                .value("short_link", QueryBuilder.bindMarker());
        SimpleStatement insertStatement = insert.build();

        insertStatement = insertStatement.setKeyspace(CassandraVariables.KEYSPACE_1);

        PreparedStatement ps = session.prepare(insertStatement);
        BoundStatement statement = ps.bind()
                .setString(0, fullLink)
                .setString(1, shortLink);

        session.execute(statement);
        cassandraService.close();

        return shortLink;
    }

}
