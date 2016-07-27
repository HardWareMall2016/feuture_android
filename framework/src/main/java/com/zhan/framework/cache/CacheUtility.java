package com.zhan.framework.cache;

import com.zhan.framework.common.context.GlobalContext;
import org.aisen.orm.SqliteUtility;
import org.aisen.orm.SqliteUtilityBuilder;
import org.aisen.orm.extra.Extra;

import java.util.List;

/**
 * 作者：伍岳 on 2016/3/16 14:20
 * 邮箱：wuyue8512@163.com
 //
 //         .............................................
 //                  美女坐镇                  BUG辟易
 //         .............................................
 //
 //                       .::::.
 //                     .::::::::.
 //                    :::::::::::
 //                 ..:::::::::::'
 //              '::::::::::::'
 //                .::::::::::
 //           '::::::::::::::..
 //                ..::::::::::::.
 //              ``::::::::::::::::
 //               ::::``:::::::::'        .:::.
 //              ::::'   ':::::'       .::::::::.
 //            .::::'      ::::     .:::::::'::::.
 //           .:::'       :::::  .:::::::::' ':::::.
 //          .::'        :::::.:::::::::'      ':::::.
 //         .::'         ::::::::::::::'         ``::::.
 //     ...:::           ::::::::::::'              ``::.
 //    ```` ':.          ':::::::::'                  ::::..
 //                       '.:::::'                    ':'````..
 //
 */
public class CacheUtility {
    private static SqliteUtility getCacheSqlite() {
        final String db_name="cache_db";
        if (SqliteUtility.getInstance(db_name) == null)
            new SqliteUtilityBuilder().configDBName(db_name).configVersion(1).build(GlobalContext.getInstance());

        return SqliteUtility.getInstance(db_name);
    }

    public static <T> List<T> findCacheData(Extra key, Class<T> responseCls) {
        List<T> beanList = getCacheSqlite().select(key, responseCls);

        return beanList;
    }

    public static <T> void putCacheData(Extra key, List<T> dataList,Class<T> cls){
        //先清空对应的缓存
        getCacheSqlite().deleteAll(key, cls);

        if(dataList!=null&&dataList.size()>0){
            getCacheSqlite().insert(key, dataList);
        }
    }

    public static <T> void clearCacheData(Class<T> responseCls){
        getCacheSqlite().deleteAll(null,responseCls);
    }

    public static void clearAllCacheData(){
        getCacheSqlite().deleteAll();
    }
}
