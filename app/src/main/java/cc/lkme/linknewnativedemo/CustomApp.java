package cc.lkme.linknewnativedemo;

import android.app.Application;

import cc.lkme.linknewnative.LinkNewNative;


public class CustomApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LinkNewNative.getInstance(this, "d02c958d10e0cb2eefad762d86b4124a");
        if (BuildConfig.DEBUG) {
            //设置debug模式下打印LinkedME日志
            LinkNewNative.getInstance().setDebug(true);
        }
    }
}
