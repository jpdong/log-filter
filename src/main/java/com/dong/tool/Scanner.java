package com.dong.tool;

import java.io.*;

public class Scanner {

    MapReduce mMapReduce;

    public Scanner(MapReduce mapReduce) {
        mMapReduce = mapReduce;
    }

    public void scan(String dirPath) {
        File dirFile = new File(dirPath);
        if (dirFile.exists()) {
            scanDirOrFile(dirFile);
        }
    }

    public void scanDirOrFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File logFile : files) {
                    scanDirOrFile(logFile);
                }
            }
        } else if (file.isFile()) {
            LogData logData = readFromFile(file);
            if (logData != null) {
                mMapReduce.map(logData);
            }
        }
    }

    private LogData readFromFile(File file) {
        LogData data = new LogData();
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line = null;
            stringBuilder = new StringBuilder();
            boolean isCodeLineOpen = false;
            while ((line = bufferedReader.readLine()) != null) {
                if (data.key == null) {
                    if (line.startsWith("at") && line.contains("com.exce")) {
                        data.key = line;
                    }
                    if (line.contains("10019")) {
                        isCodeLineOpen = true;
                    }
                }
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            if (LogData.sLogType == LogData.LOG_TYPE_ONLY_CODE_LINE_OPEN && isCodeLineOpen == false) {
                return null;
            }
            LogData.sTotalOutputCount++;
            System.out.print("\r");
            System.out.print("record count:" + LogData.sTotalOutputCount);
            data.value = stringBuilder.toString();
            if (data.key == null) {
                int traceIndex = stringBuilder.indexOf("java");
                if (traceIndex == -1) {
                    traceIndex = stringBuilder.indexOf("android");
                }
                if (traceIndex == -1) {
                    data.key = data.value;
                } else {
                    String traceString = stringBuilder.substring(traceIndex);
                    int atIndex = traceString.indexOf("at");
                    if (atIndex == -1) {
                        data.key = traceString;
                    } else {
                        data.key = traceString.substring(0, traceString.indexOf("at"));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

}
