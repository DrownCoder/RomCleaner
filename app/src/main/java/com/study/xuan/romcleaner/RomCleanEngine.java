package com.study.xuan.romcleaner;

import android.content.Context;
import android.os.Looper;
import android.os.MessageQueue;

/**
 * Author : xuan.
 * Date : 2019/1/9.
 * Description : 检测清理Rom和磁盘
 */
public class RomCleanEngine {
    public static final String TAG = "RomClean";
    public static void checkRom(final Context context) {
        //空闲时，开启线程，清理磁盘
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                RomCleanTask task = new RomCleanTask(context);
                task.execute();
                return false;
            }
        });
    }
}
