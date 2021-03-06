package com.zhan.ironfuture.db;

import com.zhan.framework.common.context.GlobalContext;

import org.aisen.orm.SqliteUtility;
import org.aisen.orm.SqliteUtilityBuilder;

public class DBHelper {
    public static SqliteUtility getVipTeacherSqlite() {
        final String db_name="iron_future_db";
        if (SqliteUtility.getInstance(db_name) == null)
            new SqliteUtilityBuilder().configDBName(db_name).configVersion(1).build(GlobalContext.getInstance());

        return SqliteUtility.getInstance(db_name);
    }
}
