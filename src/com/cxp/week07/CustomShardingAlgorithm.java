package com.cxp.week07;

import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomShardingAlgorithm implements HintShardingAlgorithm<String> {
    @Override
    public Collection<String> doSharding(Collection<String> collection, HintShardingValue<String> hintShardingValue) {
        List<String> result = new ArrayList<>();
        for (String dbName : collection) {
            for (String value : hintShardingValue.getValues()) {
                if (StringUtils.equals(dbName, value)) {
                    result.add(dbName);
                }
            }
        }
        return result;
    }

    @Override
    public void init() {

    }

    @Override
    public String getType() {
        return null;
    }
}
