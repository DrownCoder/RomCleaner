package com.study.xuan.romcleaner;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Author : xuan.
 * Date : 2019/1/9.
 * Description :异步清理Rom和SD图片缓存
 */
public class RomCleanTask extends AsyncTask<Void, Void, Boolean> {
    private static final String WARN_TIPS = "检测到您的磁盘内存低，可能会影响程序使用，建议清理磁盘！";
    private static final Long LESS_ROM = 100L;
    private WeakReference<Context> contextWk;

    public RomCleanTask(Context contextWk) {
        this.contextWk = new WeakReference<>(contextWk);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        if (contextWk.get() == null) {
            return false;
        }
        Context context = contextWk.get();
        if (RomUtils.getRomSpace() < LESS_ROM) {
            //rom小于100MB时，清理Rom
            doCleanRom(context);
            //清理Glide磁盘缓存
            doCleanGlide(context);
            //清理ImageLoader磁盘缓存
            doCleanIL(context);
            return true;
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean isLess) {
        if (contextWk.get() == null) {
            return;
        }
        if (isLess) {
            Toast.makeText(contextWk.get(), WARN_TIPS, Toast.LENGTH_SHORT).show();
        }
    }

    private void doCleanIL(Context context) {
        //ImagImageLoader.getInstance().clearDiscCache();
    }

    private void doCleanGlide(Context context) {
        //设置禁用全局磁盘缓存

        //清除Glide磁盘缓存
        //Glide.get(context).clearDiskCache();
    }

    /**
     * 清除Rom下的Cache文件夹，data/data/package/cache/
     */
    private void doCleanRom(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                RomUtils.deleteDir(dir);
            }
        } catch (Exception e) {
            Log.e(RomCleanEngine.TAG, e.getMessage());
        }
    }
}
