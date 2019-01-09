package com.study.xuan.romcleaner;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;

/**
 * Author : xuan.
 * Date : 2019/1/9.
 * Description :磁盘检测
 */
public class RomUtils {
    public static long getRomSpace() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        //long blockCount = stat.getBlockCount();
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();

        //String totalSize = Formatter.formatFileSize(context, blockCount * blockSize);
        //String availableSize = Formatter.formatFileSize(context, availableBlocks * blockSize);

        //return "手机Rom总容量:" + totalSize + "\n手机Rom可用容量:" + availableSize;
        Log.i(RomCleanEngine.TAG, blockSize+"");
        return fileMBSize(blockSize * availableBlocks);
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        if (dir != null) {
            return dir.delete();
        } else {
            return false;
        }
    }

    private static long fileMBSize(long size) {
        long result = 0;
        if (size >= 1000) {
            size /= 1000;
            if (size >= 1000) {
                size /= 1000;
                result = size;
            } else {
                //小于1MB
                result = 1;
            }
        }
        Log.i(RomCleanEngine.TAG, "剩余Rom大小：" + result + "MB");
        return result;
    }
}
