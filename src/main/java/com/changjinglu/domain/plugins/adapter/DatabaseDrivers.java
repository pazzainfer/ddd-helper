package com.changjinglu.domain.plugins.adapter;

import com.changjinglu.domain.plugins.entity.DataSource;
import lombok.Getter;

/**
 * <p> DatabaseDrivers </p>
 * <p> 数据库驱动枚举 </p>
 *
 * @author fengxiao
 * @since 2019/9/23 4:55 下午
 */
public enum DatabaseDrivers {
    MYSQL_V_5_1_47("Mysql",
            DataSource.MYSQL,
            "5.1.47",
            "com.mysql.jdbc.Driver",
            new MysqlDriverAdapter(),
            "https://repo1.maven.org/maven2/mysql/mysql-connector-java/5.1.47/mysql-connector-java-5.1.47.jar"),
    MYSQL("Mysql",
            DataSource.MYSQL,
            "6.0.6",
            "com.mysql.cj.jdbc.Driver",
            new MysqlDriverAdapter(),
            "classpath:lib/mysql-connector-java-6.0.6.jar"),
    MYSQL_V_8_0_16("Mysql",
            DataSource.MYSQL,
            "8.0.16",
            "com.mysql.cj.jdbc.Driver",
            new MysqlDriverAdapter(),
            "https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.16/mysql-connector-java-8.0.16.jar"),
    POSTGRE_SQL("PostgreSQL",
            DataSource.POSTGRE_SQL,
            "42.2.5",
            "org.postgresql.Driver",
            new PostgreSQLDriverAdapter(),
            "http://central.maven.org/maven2/org/postgresql/postgresql/42.2.5/postgresql-42.2.5.jar"),
    ;
    public static final String CLASSPATH_PREFIX = "classpath:";
    @Getter
    private final String type;
    @Getter
    private final DataSource type2;
    @Getter
    private final String version;
    @Getter
    private final String driverClass;
    @Getter
    private final DriverAdapter driverAdapter;
    @Getter
    private final String url;

    DatabaseDrivers(String type, DataSource type2, String version, String driverClass,
                    DriverAdapter driverAdapter, String url) {
        this.type = type;
        this.type2 = type2;
        this.version = version;
        this.driverClass = driverClass;
        this.driverAdapter = driverAdapter;
        this.url = url;
    }

    public String getJarFilename() {
        return toString() + ".jar";
    }

    @Override
    public String toString() {
        return type + "-" + version;
    }
}
