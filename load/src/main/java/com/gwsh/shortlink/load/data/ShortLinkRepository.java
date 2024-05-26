package com.gwsh.shortlink.load.data;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.gwsh.shortlink.load.service.CassandraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ShortLinkRepository {

    private final CassandraService cassandraService;

    public String getShortLinkByLongLink(String longLink, String keyspace) throws ChangeSetPersister.NotFoundException {
        CqlSession session = cassandraService.getSession();

        Select select = QueryBuilder
                .selectFrom("SHORT_LINKS").all()
                .whereColumn("full_link").isEqualTo(QueryBuilder.literal(longLink))
                .allowFiltering();
        Optional<Row> optionalRow = Optional.ofNullable(session.execute(select.build()).one());

        try {
            Row row = optionalRow.stream().findFirst().orElseThrow(ChangeSetPersister.NotFoundException::new);
            return row.getString("short_link");
        } catch (ChangeSetPersister.NotFoundException nfe) {
            return null;
        }
    }

    public String getLongLinkByShortLink(String shortLink, String keyspace) throws ChangeSetPersister.NotFoundException {
        CqlSession session = cassandraService.getSession();

        Select select = QueryBuilder
                .selectFrom("SHORT_LINKS").all()
                .whereColumn("short_link").isEqualTo(QueryBuilder.literal(shortLink))
                .allowFiltering();
        Optional<Row> optionalRow = Optional.ofNullable(session.execute(select.build()).one());

        try {
            Row row = optionalRow.stream().findFirst().orElseThrow(ChangeSetPersister.NotFoundException::new);
            return row.getString("long_link");
        } catch (ChangeSetPersister.NotFoundException nfe) {
            return null;
        }
    }
}