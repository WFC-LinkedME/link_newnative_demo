package cc.lkme.linknewnativedemo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import cc.lkme.linknewnative.callback.OnAdDialogListener;
import cc.lkme.linknewnative.callback.OnAdStatusListener;
import cc.lkme.linknewnative.data.AdInfo;
import cc.lkme.linknewnative.view.GridAdDialog;
import cc.lkme.linknewnative.view.GridAdView;

public class MainActivity extends AppCompatActivity {

    private boolean existPopAd;
    private boolean existMenuAd;
    private GridAdView gridAdView;
    private GridAdDialog popDialog;
    private GridAdDialog menuDialog;
    private RadioGroup adToastMenu;
    private boolean existAd;
    private AlertDialog alertDialog;
    private int currentSelected = 0;
    private boolean hasPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1000);
        } else {
            hasPermission = true;
            initAd();
        }

    }

    void initAd() {
        adToastMenu = findViewById(R.id.ad_toast_menu);
        adToastMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.pop:
                        currentSelected = 0;
                        break;
                    case R.id.menu:
                        currentSelected = 1;
                        break;
                    case R.id.custom:
                        currentSelected = 2;
                        break;
                }
            }
        });
        popDialog = new GridAdDialog(this, false, new OnAdDialogListener() {

            @Override
            public void onConfirm() {
                Toast.makeText(MainActivity.this, "退出APP", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "关闭弹窗", Toast.LENGTH_SHORT).show();
            }
        });
//        popDialog.setAdTitleColor(R.color.colorAccent);
        popDialog.loadAd("4000063_384", null, new OnAdStatusListener() {
            @Override
            public void onClick(AdInfo adInfo) {
                super.onClick(adInfo);
                Toast.makeText(MainActivity.this, "广告被点击了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGetAd(boolean status) {
                super.onGetAd(status);
                existPopAd = status;
                Toast.makeText(MainActivity.this, "广告获取状态：" + status, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGetAd(boolean status, AdInfo adInfo) {
                super.onGetAd(status, adInfo);
                // 无效回调
            }

            @Override
            public void onClose() {
                super.onClose();
                // 无效回调
            }

            @Override
            public void onExposure(AdInfo adInfo) {
                super.onExposure(adInfo);
                Toast.makeText(MainActivity.this, "广告被曝光了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReady() {
                super.onReady();
                // 无效回调
            }
        });
        menuDialog = new GridAdDialog(this, true, new OnAdDialogListener() {

            @Override
            public void onConfirm() {
                Toast.makeText(MainActivity.this, "退出APP", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "关闭弹窗", Toast.LENGTH_SHORT).show();
            }
        });
//        menuDialog.setAdTitle("提示信息");
//        menuDialog.setAdTitleColor(R.color.colorAccent);
//        menuDialog.setAdTitleSize(20);
        menuDialog.loadAd("4000063_384", null, new OnAdStatusListener() {
            @Override
            public void onClick(AdInfo adInfo) {
                super.onClick(adInfo);
                Toast.makeText(MainActivity.this, "广告被点击了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGetAd(boolean status) {
                super.onGetAd(status);
                existMenuAd = status;
            }

            @Override
            public void onGetAd(boolean status, AdInfo adInfo) {
                super.onGetAd(status, adInfo);
            }

            @Override
            public void onClose() {
                super.onClose();
            }

            @Override
            public void onExposure(AdInfo adInfo) {
                super.onExposure(adInfo);
                Toast.makeText(MainActivity.this, "广告被曝光了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReady() {
                super.onReady();
            }
        });
        gridAdView = new GridAdView(this);
        gridAdView.loadAd("4000063_384", null, new OnAdStatusListener() {
            @Override
            public void onClick(AdInfo adInfo) {
                super.onClick(adInfo);
                Toast.makeText(MainActivity.this, "广告被点击了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGetAd(boolean status) {
                super.onGetAd(status);
                existAd = status;
            }

            @Override
            public void onGetAd(boolean status, AdInfo adInfo) {
                super.onGetAd(status, adInfo);
            }

            @Override
            public void onClose() {
                super.onClose();
            }

            @Override
            public void onExposure(AdInfo adInfo) {
                super.onExposure(adInfo);
                Toast.makeText(MainActivity.this, "广告被曝光了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReady() {
                super.onReady();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                hasPermission = true;
                initAd();
            } else {
                Toast.makeText(this, "请先授权", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public boolean showExitDialog() {
        if (existAd) {
            gridAdView.showAd();
        } else {
            return false;
        }
        if (alertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("猜您想去以下APP");
            builder.setView(gridAdView);
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    MainActivity.this.finish();
                }
            });

            alertDialog = builder.create();
        }
        alertDialog.show();
        return true;
    }

    /**
     * 返回键退回到登陆页面
     **/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!hasPermission) {
                Toast.makeText(this, "正常返回", Toast.LENGTH_SHORT).show();
                return false;
            }
            switch (currentSelected) {
                case 0:
                    if (existPopAd) {
                        popDialog.showAd();
                        return true;
                    } else {
                        Toast.makeText(this, "无广告可展示", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 1:
                    if (existMenuAd) {
                        menuDialog.showAd();
                        return true;
                    } else {
                        Toast.makeText(this, "无广告可展示", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    if (showExitDialog()) {
                        return true;
                    }
                    break;
            }
        }
        return false;
    }
}
