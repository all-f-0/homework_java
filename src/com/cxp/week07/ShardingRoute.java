package com.cxp.week07;

import org.apache.shardingsphere.infra.hint.HintManager;

public class ShardingRoute {

    public static void demo(String dbName) {
        try (HintManager manager = HintManager.getInstance()) {
            manager.setDataSourceName(dbName);
            // 执行sql逻辑
        }
    }
}
