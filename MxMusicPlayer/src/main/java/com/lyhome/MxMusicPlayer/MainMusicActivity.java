package com.lyhome.MxMusicPlayer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.lyhome.MxMusicPlayer.adapter.MusicPagerAdapter;
import com.lyhome.MxMusicPlayer.fragment.LogicFragment;
import com.lyhome.MxMusicPlayer.fragment.OnlineFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//实现OnClickListener的接口
public class MainMusicActivity extends AppCompatActivity implements View.OnClickListener {
    //定义activity_main.xml的控件对象
    private TextView logicTv;
    private TextView onlineTv;
    private ViewPager viewPager;
    private ImageView menuImagv;
    private ImageView seachImagv;
    //将Fragment放入List集合中，存放fragment对象
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_main);

        //绑定id
        bangdingID();
        //设置监听
        jianting();
        //创建fragment对象
        LogicFragment logicFragment = new LogicFragment();
        OnlineFragment onlineFragment = new OnlineFragment();
        //将fragment对象添加到fragmentList中
        fragmentList.add(logicFragment);
        fragmentList.add(onlineFragment);
        //通过MusicPagerAdapter类创建musicPagerAdapter的适配器，下面我将添加MusicPagerAdapter类的创建方法
        MusicPagerAdapter musicPagerAdapter = new MusicPagerAdapter(getSupportFragmentManager(), fragmentList);
        //viewPager绑定适配器
        viewPager.setAdapter(musicPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        logicTv.setTextColor(ContextCompat.getColor(MainMusicActivity.this, R.color.white));
                        onlineTv.setTextColor(ContextCompat.getColor(MainMusicActivity.this, R.color.white_60P));
                        break;
                    case 1:
                        onlineTv.setTextColor(ContextCompat.getColor(MainMusicActivity.this, R.color.white));
                        logicTv.setTextColor(ContextCompat.getColor(MainMusicActivity.this, R.color.white_60P));
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void jianting() {
        logicTv.setOnClickListener(this);
        onlineTv.setOnClickListener(this);
        menuImagv.setOnClickListener(this);
        seachImagv.setOnClickListener(this);
    }

    private void bangdingID() {
        logicTv = findViewById(R.id.main_logic_tv);
        onlineTv = findViewById(R.id.main_online_tv);
        viewPager = findViewById(R.id.main_vp);
        menuImagv = findViewById(R.id.main_menu_imgv);
        seachImagv = findViewById(R.id.main_search_imgv);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Calendar calendar;
        if (id == R.id.main_logic_tv) {//实现点击TextView切换fragment
            viewPager.setCurrentItem(0);
        } else if (id == R.id.main_online_tv) {
            viewPager.setCurrentItem(1);
        } else if (id == R.id.main_menu_imgv) {
            calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(MainMusicActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    Toast.makeText(MainMusicActivity.this, "所选择的日期是：" + year + "-" + (month + 1) + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();

                }
            }, year, month, day);
            datePickerDialog.show();
        } else if (id == R.id.main_search_imgv) {
            calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR);
            int minute = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(MainMusicActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Toast.makeText(MainMusicActivity.this, "所选择的时间是：" + hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();

                }
            }, hour, minute, true);
            timePickerDialog.show();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            final View view = LayoutInflater.from(this).inflate(R.layout.dialog1_layout, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("退出提示");
            builder.setView(view);
            builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    Toast.makeText(MainMusicActivity.this, "自定义消息", Toast.LENGTH_SHORT).show();

                }
            });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }
}

