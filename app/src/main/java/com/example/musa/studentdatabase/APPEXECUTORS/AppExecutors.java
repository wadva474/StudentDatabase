package com.example.musa.studentdatabase.APPEXECUTORS;

import android.os.Handler;
import android.os.Looper;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    public static  AppExecutors sintance;
    private Executor DataIO;
    private Executor NetworkIO;

    public Executor getDataIO() {
        return DataIO;
    }

    public Executor getNetworkIO() {
        return NetworkIO;
    }

    public Executor getMainThread() {
        return MainThread;
    }

    private Executor MainThread;
    public static Object LOCK=new Object();

    public AppExecutors(Executor dataIO, Executor networkIO, Executor mainThread) {
        DataIO = dataIO;
        NetworkIO = networkIO;
        MainThread = mainThread;
    }

    public static AppExecutors getsintance() {
        if (sintance==null){
            synchronized (LOCK){
                sintance=new AppExecutors(Executors.newSingleThreadExecutor(),Executors.newFixedThreadPool(3),new MainExecutor());
            }
        }
        return sintance;
    }
    private static class MainExecutor implements Executor{
    private  Handler handlerThread=new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {handlerThread.post(command);
        }
    }

}
