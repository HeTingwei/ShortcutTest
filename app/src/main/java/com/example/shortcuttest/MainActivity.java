package com.example.shortcuttest;


import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/*
* 学习网址https://developer.android.com/guide/topics/ui/shortcuts.html
* */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View v) {
        createShortCut();
    }

    //创建桌面快捷键方法
    public void createShortCut() {
        // Android 8.0之下的，且
        // 国内手机得用户在权限界面去赋予创建快捷键的权限才有效)
        //卸载应用时会自动去除快捷方式
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            Intent addShortcut = new Intent(
                    "com.android.launcher.action.INSTALL_SHORTCUT");
            Parcelable icon = Intent.ShortcutIconResource.fromContext(this,
                    R.drawable.ic_launcher);
            addShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,
                    getString(R.string.app_name));
            addShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
            addShortcut.putExtra("duplicate", false);
            addShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
            sendBroadcast(addShortcut);
        } else {

            Intent shortcutIntent = new Intent(getApplicationContext(), MainActivity.class);
            shortcutIntent.setAction(Intent.ACTION_CREATE_SHORTCUT);
            shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //Android 8.0创建桌面快捷键，有个问题是图标右下角还会显示应用图标
            ShortcutInfo.Builder mShortcutInfoBuilder = new ShortcutInfo.Builder(MainActivity.this, getString(R.string.app_name));
            mShortcutInfoBuilder.
                    setShortLabel(getString(R.string.app_name)).
                    setLongLabel(getString(R.string.app_name)).
                    setIcon(Icon.createWithResource(MainActivity.this, R.mipmap.ic_launcher_round)).
                    setIntent(shortcutIntent)
                    .build();
            ShortcutInfo mShortcutInfo = mShortcutInfoBuilder.build();
            ShortcutManager mShortcutManager = getSystemService(ShortcutManager.class);
            mShortcutManager.requestPinShortcut(mShortcutInfo, null);
        }

    }


}
