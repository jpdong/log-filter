package com.dong.tool;

import java.util.HashMap;
import java.util.Map;

public class MapReduce {

    Map<String, LogData> dataMap = new HashMap<>();

    public void map(LogData logData) {
        LogData value = dataMap.get(logData.key);
        if (value == null) {
            logData.count = 1;
            dataMap.put(logData.key, logData);
        } else {
            value.count++;
        }
    }

    public String reduce() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, LogData> entry : dataMap.entrySet()) {
            LogData logData = entry.getValue();
            stringBuilder.append(logData.value);
            stringBuilder.append("\n");
            stringBuilder.append("count:" + logData.count);
            stringBuilder.append("\n");
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
