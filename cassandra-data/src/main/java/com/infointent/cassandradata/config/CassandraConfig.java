package com.infointent.cassandradata.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.core.cql.session.init.KeyspacePopulator;
import org.springframework.data.cassandra.core.cql.session.init.ResourceKeyspacePopulator;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.List;

@EnableCassandraRepositories(basePackages = "com.infointent.cassandrdata.repository")
@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.keyspace-name:data-keyspace}")
    private String keySpace;

    @Value("${spring.data.cassandra.contact-points:localhost}")
    private String contactPoint;

    @Value("${spring.data.cassandra.port:9042}")
    private Integer port;

    String createTableSQL = "CREATE TABLE IF NOT EXISTS t_product (" +
            "                productid text PRIMARY KEY, product_name text," +
            "                product_price double, is_available boolean );";
    String dropTableSql = "DROP TABLE IF EXISTS t_product;";

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    protected String getKeyspaceName() {
        return keySpace;
    }

    public String getContactPoints() {
        return contactPoint;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"com.infointent.cassandrdata.repository"};
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        // create keyspace after startup
        return Arrays.asList(
                CreateKeyspaceSpecification.createKeyspace(keySpace)
                        .ifNotExists()
                        .with(KeyspaceOption.DURABLE_WRITES, true)
                        .withSimpleReplication());
    }


    @Nullable
    @Override
    protected KeyspacePopulator keyspacePopulator() {
        // create table after startup
        return new ResourceKeyspacePopulator(scriptOf(createTableSQL));
    }

    /* @Override
    protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
        // drop keyspace before shutdown
        return List.of(DropKeyspaceSpecification.dropKeyspace(keySpace));
    }

    @Nullable
    @Override
    protected KeyspacePopulator keyspaceCleaner() {
         // drop table before shutdown
        return new ResourceKeyspacePopulator(scriptOf(dropTableSql));
    }*/

}
