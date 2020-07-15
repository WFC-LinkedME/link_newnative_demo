package cc.lkme.linknewnativedemo;

import android.app.Application;

import cc.lkme.linknewnative.LinkNewNative;


public class CustomApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LinkNewNative.getInstance(this, "cfe92546f57bb2ac45eee5f8645ffd8a");
        if (BuildConfig.DEBUG) {
            //设置debug模式下打印LinkedME日志
            LinkNewNative.getInstance().setDebug(true);
        }
    }
}
