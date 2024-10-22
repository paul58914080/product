CREATE SCHEMA IF NOT EXISTS ${spring.liquibase.liquibase-schema:LIQUIBASE};
CREATE SCHEMA IF NOT EXISTS ${spring.liquibase.default-schema:PRODUCT};
CREATE SCHEMA IF NOT EXISTS ${spring.jpa.properties.org.hibernate.envers:PRODUCT_AUDIT};