package com.example.apple.picturepickerdemo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.apple.picturepickerdemo.R;
import com.example.apple.picturepickerdemo.adapter.FirstAdapter;
import com.example.apple.picturepickerdemo.utils.CunZhi;

import java.util.ArrayList;

public class FirstActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    ArrayList<String> mSelectImagPathList = new ArrayList<>();
    public static final int OPEN_PICTURE_PICKER_ACTIVITY = 0x001;
    private FirstAdapter mFirstAdapter;
    private MyReceiver mMyReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(gridLayoutManager);

//        以下的三步：是为了确保选择图片Activity加载大量图片，内存吃紧，会把现在的这个Activity内存回收，导致回到本Activity是重新走了onCreate方法
//        导致没有收到照片，但是由于他们选择的图片已经存在了CunZhi.mSelectPathList里了，这里可以直接加载
        mSelectImagPathList.clear();
        mSelectImagPathList.addAll(CunZhi.mSelectPathList);
        mFirstAdapter = new FirstAdapter(this, mSelectImagPathList);

        mRecyclerView.setAdapter(mFirstAdapter);
        mFirstAdapter.setOnItemClickListener(new FirstAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == mSelectImagPathList.size()) {
                    Intent intent = new Intent(FirstActivity.this, PicturePickerActivity.class);
                    startActivityForResult(intent, OPEN_PICTURE_PICKER_ACTIVITY);
                } else {
                    Toast.makeText(FirstActivity.this, position + "", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mMyReceiver = new MyReceiver();
        registerReceiver(mMyReceiver, new IntentFilter("com.example.hello"));

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        ArrayList<String> mTempYuLanLists = intent.getStringArrayListExtra("mTempYuLanLists");
        Log.e("onResume", "onResume" + mTempYuLanLists);

        if (mTempYuLanLists != null) {
            Log.e("onResume", mTempYuLanLists.size() + "");

//            mSelectImagPathList.clear();


            mSelectImagPathList.addAll(CunZhi.mSelectPathList);
            mFirstAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == PicturePickerActivity.RESULT_CODE_SELECTED_PATH_LIST) {
            mSelectImagPathList.clear();


            mSelectImagPathList.addAll(CunZhi.mSelectPathList);
            mFirstAdapter.notifyDataSetChanged();
        }


    }

    private void initData() {
        CunZhi.mSelectPathList.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CunZhi.mSelectPathList.clear();
        unregisterReceiver(mMyReceiver);
    }


    /**
     * 接收AllYuLan的数据，本应该AllYuLan和YuLan这两个写在一个Activity的两个Fragment里的，
     * 这样一个onActivityResult接可以接受数据了，现在是写在了两个不同的Activity里，所以AllYuLanActivity
     * 返回的数据，只能是通过打开FirstActivity和发送一个广播，来更新数据了
     */
    public class MyReceiver extends BroadcastReceiver {
        public MyReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            mSelectImagPathList.clear();

            mSelectImagPathList.addAll(CunZhi.mSelectPathList);
            mFirstAdapter.notifyDataSetChanged();
        }
    }
}
