package com.example.agsale.base;

import android.os.Environment;

import com.example.agsale.MyApplication;

public class Constant {

    public final static String CRASH_FILE_DIR = getCrashFilePath();

    public static String API_BASE_URL = "http://180.101.147.217:9000/consumer/";

    public static String API_BASE_URL_consumption = "http://180.101.147.217:9000/consumption/";

    public static final String PRIVACY_URL = API_BASE_URL + "question/{qid}.html";

    public static String getCrashFilePath() {
        String filePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            filePath = MyApplication.getMyApplication().getExternalCacheDir().getPath() + "/";
        } else {
            filePath = MyApplication.getMyApplication().getCacheDir().getPath() + "/";
        }

        return filePath + "crash/";
    }
}
