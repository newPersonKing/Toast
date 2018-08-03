package com.gy.universaltoast;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gy.universaltoast.toast.UniversalToast;


public class MainActivity extends AppCompatActivity {
    private long lastBackTime = 0;

    public static final String[] ITEMS = {"通用toast", "强调toast", "可点击toast"
            , "通用 + 成功toast", "通用 + 警告toast", "通用 + 错误toast"
            , "强调 + 成功toast", "强调 + 警告toast", "强调 + 错误toast"
            , "可点击 + 成功toast", "可点击 + 警告toast", "可点击 + 错误toast"};
    private static final int REQUEST_PERMISSION_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listview);
        final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "toast clicked!!!");
            }
        };
        listView.setAdapter(new ArrayAdapter(MainActivity.this, R.layout.item, ITEMS));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!requestPermission()) {
                    return;
                }
                switch (position) {
                    case 0:
                        UniversalToast.makeText(MainActivity.this, "关注成功", UniversalToast.LENGTH_SHORT, UniversalToast.UNIVERSAL)
                                .setGravity(Gravity.CENTER, 0, 0)
                                .setIcon(R.drawable.ic_done_white_24dp)
                                .show();
                        break;
                    case 1:
                        UniversalToast.makeText(MainActivity.this, "关注成功", UniversalToast.LENGTH_SHORT, UniversalToast.EMPHASIZE)
                                .setIcon(R.drawable.ic_check_circle_white_24dp)
                                .show();
                        break;
                    case 2:
                        UniversalToast.makeText(MainActivity.this, "关注成功", UniversalToast.LENGTH_SHORT, UniversalToast.CLICKABLE)
                                .setIcon(R.drawable.ic_done_white_24dp)
                                .setGravity(Gravity.TOP, 0, 0)
                                .setClickCallBack("查看", onClickListener)
                                .show();
                        break;
                    case 3:
                        UniversalToast.makeText(MainActivity.this, "关注成功", UniversalToast.LENGTH_SHORT).showSuccess();
                        break;
                    case 4:
                        UniversalToast.makeText(MainActivity.this, "请先登录", UniversalToast.LENGTH_SHORT).showWarning();
                        break;
                    case 5:
                        UniversalToast.makeText(MainActivity.this, "关注失败", UniversalToast.LENGTH_SHORT).showError();
                        break;
                    case 6:
                        UniversalToast.makeText(MainActivity.this, "关注成功", UniversalToast.LENGTH_SHORT, UniversalToast.EMPHASIZE).showSuccess();
                        break;
                    case 7:
                        UniversalToast.makeText(MainActivity.this, "请先登录", UniversalToast.LENGTH_SHORT, UniversalToast.EMPHASIZE).showWarning();
                        break;
                    case 8:
                        UniversalToast.makeText(MainActivity.this, "关注失败", UniversalToast.LENGTH_SHORT, UniversalToast.EMPHASIZE).showError();
                        break;
                    case 9:
                        UniversalToast.makeText(MainActivity.this, "关注成功", UniversalToast.LENGTH_SHORT, UniversalToast.CLICKABLE)
                                .setClickCallBack("查看", onClickListener)
                                .showSuccess();
                        break;
                    case 10:
                        UniversalToast.makeText(MainActivity.this, "请先登录", UniversalToast.LENGTH_SHORT, UniversalToast.CLICKABLE)
                                .setClickCallBack("查看", onClickListener)
                                .showWarning();
                        break;
                    case 11:
                        UniversalToast.makeText(MainActivity.this, "关注失败", UniversalToast.LENGTH_SHORT, UniversalToast.CLICKABLE)
                                .setClickCallBack("查看", onClickListener)
                                .showError();
                        break;
                    default:
                }
            }
        });
    }

    private boolean requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!Settings.canDrawOverlays(this)) {
                UniversalToast.makeText(this, "请允许悬浮窗权限", UniversalToast.LENGTH_SHORT).showWarning();
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_PERMISSION_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && requestCode == REQUEST_PERMISSION_CODE) {
            String text = Settings.canDrawOverlays(this) ? "已获取悬浮窗权限" : "请打开悬浮窗权限";
            UniversalToast.makeText(this, text, UniversalToast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastBackTime < 2000) {
            finish();
        } else {
            lastBackTime = System.currentTimeMillis();
            UniversalToast.makeText(this, "再次返回退出应用", UniversalToast.LENGTH_SHORT)
                    .setGravity(Gravity.CENTER, 0, 0)
                    .show();
        }
    }

}