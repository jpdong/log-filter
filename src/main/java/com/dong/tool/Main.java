package com.dong.tool;

import java.io.*;

public class Main {

    private static final String TAG = "Main";

    public static void main(String[] args) {
        System.out.println("start");
        if (args == null || args.length == 0) {
            Log.d(TAG, "args null");
        } else {
            String dirPath = args[0];
            if (dirPath == null || dirPath.length() == 0) {
                Log.d(TAG, "path is null");
            } else {
                MapReduce mapReduce = new MapReduce();
                Scanner scanner = new Scanner(mapReduce);
                scanner.scan(dirPath);
                String reportString = mapReduce.reduce();
                File reportFile = new File(dirPath + File.separator + "Crash.txt");
                if (!reportFile.exists()) {
                    try {
                        reportFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                reportToFile(reportFile,reportString);
                System.out.println(reportFile.getAbsolutePath());
            }
        }
        /*String dirPath = "F:\\Work\\Java\\debug\\2020-04-26\\com.excean.gspace\\515";
        MapReduce mapReduce = new MapReduce();
        Scanner scanner = new Scanner(mapReduce);
        scanner.scan(dirPath);
        String reportString = mapReduce.reduce();
        File reportFile = new File(dirPath + File.separator + "Crash.txt");
        if (!reportFile.exists()) {
            try {
                reportFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        reportToFile(reportFile,reportString);*/
    }

    private static void reportToFile(File file,String reportString) {
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            bufferedOutputStream.write(reportString.getBytes());
            bufferedOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
