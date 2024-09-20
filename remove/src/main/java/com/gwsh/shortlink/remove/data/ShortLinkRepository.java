package com.gwsh.shortlink.remove.data;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.delete.Delete;
import com.datastax.oss.driver.api.querybuilder.insert.RegularInsert;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.gwsh.shortlink.remove.common.CassandraVariables;
import com.gwsh.shortlink.remove.service.CassandraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;


@Component
@RequiredArgsConstructor
public class ShortLinkRepository {

    private final CassandraService cassandraService;

    public void deleteLatestRecord() {
        // Step 1: Fetch the latest record (implicitly ordered by 'added_date' clustering column)
        Select select = QueryBuilder.selectFrom("SHORT_LINKS")
                .columns("short_link", "added_date")
                .limit(1);  // Only the latest record will be fetched

        // Step 2: Prepare and execute the select statement
        PreparedStatement selectStatement = cassandraService.getSession().prepare(select.build());
        ResultSet resultSet = cassandraService.getSession().execute(selectStatement.bind());

        // Step 3: Check if a row exists
        Row row = resultSet.one();
        if (row != null) {
            String shortLinkToDelete = row.getString("short_link");
            Instant addedDate = row.getInstant("added_date");

            // Step 4: Delete the record with the fetched 'short_link' and 'added_date'
            Delete delete = QueryBuilder.deleteFrom(CassandraVariables.KEYSPACE_1, "SHORT_LINKS")
                    .whereColumn("short_link").isEqualTo(QueryBuilder.bindMarker())
                    .whereColumn("added_date").isEqualTo(QueryBuilder.bindMarker());

            PreparedStatement deleteStatement = cassandraService.getSession().prepare(delete.build());
            BoundStatement deleteBoundStatement = deleteStatement.bind(shortLinkToDelete, addedDate);

            // Step 5: Execute the delete statement
            cassandraService.getSession().execute(deleteBoundStatement);
            System.out.println("Deleted the latest record with short_link: " + shortLinkToDelete);
        } else {
            System.out.println("No records found to delete.");
        }
    }



}
