package me.fallblank.propertyanimationexample.utils;

import android.support.annotation.NonNull;
import android.util.Log;
import me.fallblank.propertyanimationexample.BuildConfig;

/**
 * Created by fallb on 2016/6/8.
 */
public final class LogUtil {
    //获取是否为模式调试
    static boolean sDebug = BuildConfig.DEBUG;

    /**
     * 打印日志时地方的信息
     */
    private static String sClassName, sMethodName;
    private static int sLineNumber;

    private static void getMethodInfo(StackTraceElement[] stackTraceElements) {
        sClassName = stackTraceElements[1].getFileName();
        sMethodName = stackTraceElements[1].getMethodName();
        sLineNumber = stackTraceElements[1].getLineNumber();
    }

    /**
     * 创建日志的信息体，包含方法名，类名，行号等信息
     * @param message   需要打印的信息
     * @return  包装后的信息体
     */
    @NonNull
    private static String createLog(String message) {
        StringBuilder builder = new StringBuilder();
        builder.append(sMethodName)
            .append("(")
            .append(sClassName)
            .append(":")
            .append(sLineNumber).append(")")
            .append(message);
        return builder.toString();
    }

    @NonNull
    public static void e(String message){
        if (!sDebug)    return;
        getMethodInfo(new Throwable().getStackTrace());
        Log.e(sClassName,createLog(message));
    }

    @NonNull
    public static void i(String message){
        if (!sDebug)    return;
        getMethodInfo(new Throwable().getStackTrace());
        Log.i(sClassName,createLog(message));
    }

    @NonNull
    public static void d(String message){
        if (!sDebug)    return;
        getMethodInfo(new Throwable().getStackTrace());
        Log.d(sClassName,createLog(message));
    }

    @NonNull
    public static void w(String message){
        if (!sDebug)    return;
        getMethodInfo(new Throwable().getStackTrace());
        Log.w(sClassName,createLog(message));
    }

    @NonNull
    public static void v(String message){
        if (!sDebug)    return;
        getMethodInfo(new Throwable().getStackTrace());
        Log.v(sClassName,createLog(message));
    }

    @NonNull
    public static void wtf(String message){
        if (!sDebug)    return;
        getMethodInfo(new Throwable().getStackTrace());
        Log.wtf(sClassName,createLog(message));
    }
}