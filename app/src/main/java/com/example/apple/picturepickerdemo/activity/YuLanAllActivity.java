package com.example.apple.picturepickerdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.picturepickerdemo.R;
import com.example.apple.picturepickerdemo.adapter.MyViewPagerYuLanAllAdapter;
import com.example.apple.picturepickerdemo.utils.CunZhi;
import com.example.apple.picturepickerdemo.utils.DisplayUtil;
import com.example.apple.picturepickerdemo.utils.ImageUtils;
import com.example.apple.picturepickerdemo.view.HackyViewPager;

import java.util.ArrayList;

/**
 * 这个Activity是用来预览本文件夹下的所有的照片集合，下面的横向的scrollview显示的是已经选中的集合（存在一个微信也同样存在的bug，跨文件夹预览点击bug）
 */
public class YuLanAllActivity extends AppCompatActivity implements View.OnClickListener {


    public static final int RESULT_CODE_SELECTED_PATH_LIST_YU_LAN = 11;
    private ArrayList<String> mSelectedYuLanLists;
    private HackyViewPager mViewPager;
    private TextView mTvYuLanIndex;
    private Button mBtFinish;
    private ImageView mIvFinish;
    private CheckBox mCbSelect;
    private MyViewPagerYuLanAllAdapter mAdapter;
    private RelativeLayout mRlTopYuLan;
    private RelativeLayout mRlBottomYuLan;
    private Animation mBottomExitAnimation;
    private Animation mBottomEnteryAnimation;
    private boolean isShow = true;
    private Animation mTopEnteryAnimation;
    private Animation mTopExitAnimation;
    private int mDurationMillis = 300;
    private HorizontalScrollView mHorizontalScrollView;
    private LinearLayout mSuoLvTuLinearLayout;
    private LayoutInflater mInflater;
    private int mSuoLvTuWidth;
    private int mMargin;
    private int mScreenWidth;
    private int lastPositon;
    private RelativeLayout mRlSelect;
    private int mInputPosition;
    private ArrayList<String> mALLYuLanLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_yu_lan);

        mScreenWidth = ImageUtils.getScreenWidth(this);
        mSuoLvTuWidth = DisplayUtil.dip2px(this, 70);
        mMargin = DisplayUtil.dip2px(this, 10);

        initAnimation();

        initData();
        initView();
        setAdapater();
        intitHorizontalScrollView();
    }

    private void initAnimation() {
        mBottomExitAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_exit);
        mBottomEnteryAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_enter);
        mBottomExitAnimation.setDuration(mDurationMillis);
        mBottomEnteryAnimation.setDuration(mDurationMillis);
        mBottomExitAnimation.setFillAfter(true);
        mBottomEnteryAnimation.setFillAfter(true);

        mTopEnteryAnimation = AnimationUtils.loadAnimation(this, R.anim.top_enter);
        mTopExitAnimation = AnimationUtils.loadAnimation(this, R.anim.top_exit);

        mTopEnteryAnimation.setDuration(mDurationMillis);
        mTopExitAnimation.setDuration(mDurationMillis);

        mTopEnteryAnimation.setFillAfter(true);
        mTopExitAnimation.setFillAfter(true);
    }

    private void initData() {
        mALLYuLanLists = getIntent().getStringArrayListExtra("mImgs");
        mInputPosition = getIntent().getIntExtra("position", -1);
        mSelectedYuLanLists = getIntent().getStringArrayListExtra("yuLanList");
    }

    private void initView() {
        mRlTopYuLan = (RelativeLayout) findViewById(R.id.rl_top);
        mRlBottomYuLan = (RelativeLayout) findViewById(R.id.rl_bottomed);
        mViewPager = (HackyViewPager) findViewById(R.id.viewPager);
        mTvYuLanIndex = (TextView) findViewById(R.id.tv_yu_lan_index);

        mBtFinish = (Button) findViewById(R.id.bt_finish);
        mBtFinish.setOnClickListener(this);

        mIvFinish = (ImageView) findViewById(R.id.iv_back);
        mIvFinish.setOnClickListener(this);
        mCbSelect = (CheckBox) findViewById(R.id.cb_select);
        mCbSelect.setClickable(false);
        mRlSelect = (RelativeLayout) findViewById(R.id.rl_select);
        setCbSelect(mInputPosition);
        mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.suo_lv_tu_scrollView);
        if (mSelectedYuLanLists == null || mSelectedYuLanLists.size() == 0) {
            mHorizontalScrollView.setVisibility(View.GONE);
        } else {
            mHorizontalScrollView.setVisibility(View.VISIBLE);

        }
        mSuoLvTuLinearLayout = (LinearLayout) findViewById(R.id.ll_suo_lv_tu);

        if (mSelectedYuLanLists.size() != 0) {

            mBtFinish.setEnabled(true);
            mBtFinish.setText("完成(" + (mSelectedYuLanLists.size()) + "/" + CunZhi.mMaxSelectCount + ")");
        } else {

            mBtFinish.setEnabled(false);
            mBtFinish.setText("完  成");

        }
    }

    private void setAdapater() {
//        mAdapter = new MyViewPagerYuLanAllAdapter(this, mALLYuLanLists, mSelectedYuLanLists, mInputPosition);
        mAdapter = new MyViewPagerYuLanAllAdapter(this, mALLYuLanLists);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mInputPosition);
        mTvYuLanIndex.setText((mInputPosition + 1) + "/" + mALLYuLanLists.size());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTvYuLanIndex.setText((position + 1) + "/" + mALLYuLanLists.size());
                setCbSelect(position);

                if (mSelectedYuLanLists == null || mSelectedYuLanLists.size() == 0) {
                    return;
                }


                lianDong(position);


                if (mSelectedYuLanLists.contains(mALLYuLanLists.get(position))) {
                    int selectedPosition = mSelectedYuLanLists.indexOf(mALLYuLanLists.get(position));


                    for (int j = 0; j < mSelectedYuLanLists.size(); j++) {
                        View force_iamge = ((ViewGroup) (mSuoLvTuLinearLayout.getChildAt(j))).getChildAt(2);
                        if (j == selectedPosition) {
                            force_iamge.setVisibility(View.VISIBLE);
                            mCbSelect.setChecked(true);

                        } else {
                            force_iamge.setVisibility(View.GONE);

                        }
                    }

                } else {
                    for (int j = 0; j < mSelectedYuLanLists.size(); j++) {
                        View force_iamge = ((ViewGroup) (mSuoLvTuLinearLayout.getChildAt(j))).getChildAt(2);
                        force_iamge.setVisibility(View.GONE);
                        mCbSelect.setChecked(false);

                    }

                }


            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mAdapter.setOnPhotoViewTapListener(new MyViewPagerYuLanAllAdapter.OnPhotoViewTapListener() {
            @Override
            public void onPhotoViewTap() {
                if (isShow) {
                    isShow = false;
                    mRlBottomYuLan.startAnimation(mBottomExitAnimation);
                    mRlTopYuLan.startAnimation(mTopExitAnimation);
                } else {
                    isShow = true;
                    mRlBottomYuLan.startAnimation(mBottomEnteryAnimation);
                    mRlTopYuLan.startAnimation(mTopEnteryAnimation);
                }
            }
        });
    }

    private void lianDong(int position) {
        int selectedPosition1 = mSelectedYuLanLists.indexOf(mALLYuLanLists.get(position));
        if (selectedPosition1 < mSelectedYuLanLists.size() && selectedPosition1 >= 0) {
//              处理联动
            View childAt = mSuoLvTuLinearLayout.getChildAt(selectedPosition1);
            int xLeft = (int) childAt.getX();
            int xRight = xLeft + mSuoLvTuWidth + mMargin;
            int scrollX = mHorizontalScrollView.getScrollX();
//                Log.e("get", x + "------" + position + "=======" + scrollX);

//                只要当前的条目可以完全显示，就不去scroll

            if ((xLeft >= scrollX) && (xRight <= scrollX + mScreenWidth)) {//完全显示，不去改变scrollX
                Log.e("xianshi", "完全显示" + selectedPosition1);
            } else {//没有完全显示的话，需要去改变scrollX，以使得选中的位置完全显示
                if (lastPositon < selectedPosition1) {
//                        scroll到这个条目的最左侧
                    mHorizontalScrollView.scrollTo(xRight - mScreenWidth, 0);
                } else {
//                        scroll到这个条目的最右侧
                    mHorizontalScrollView.scrollTo(xLeft, 0);
                }
            }
            lastPositon = selectedPosition1;
        }
    }


    private void intitHorizontalScrollView() {
        mInflater = LayoutInflater.from(this);
        if (mSelectedYuLanLists == null || mSelectedYuLanLists.size() == 0) {
            return;
        }
        int selectedPosition = mSelectedYuLanLists.indexOf(mALLYuLanLists.get(mInputPosition));

        for (int i = 0; i < mSelectedYuLanLists.size(); i++) {
            FrameLayout framelayout = (FrameLayout) mInflater.inflate(R.layout.item_suo_lv_tu, null);
            mSuoLvTuLinearLayout.addView(framelayout, mSuoLvTuWidth, mSuoLvTuWidth);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) framelayout.getLayoutParams();
            layoutParams.setMargins(mMargin, mMargin, mMargin, mMargin);
            final ImageView imageview = (ImageView) framelayout.findViewById(R.id.imageview);
            ImageUtils.loadImage(this, mSelectedYuLanLists.get(i), imageview);
            final ImageView force_image = (ImageView) framelayout.findViewById(R.id.force_image);
            final ImageView delete_view = (ImageView) framelayout.findViewById(R.id.delete_view);
            delete_view.setImageResource(R.drawable.translate_yu_lan);


            if (i == selectedPosition) {//选中效果
                force_image.setVisibility(View.VISIBLE);
                mCbSelect.setChecked(true);// TODO: 2017/8/29

            } else {
                force_image.setVisibility(View.GONE);


            }
            final int finalI = i;
            framelayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(mALLYuLanLists.indexOf(mSelectedYuLanLists.get(finalI)));


                }
            });

        }
        setCbSelect(mInputPosition);//初始化显示，因为addOnPageChangeListener方法要在滑动页面后才调用


    }

    private void setCbSelect(final int position) {

        mRlSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCbSelect.isChecked()) {
                    //点击之前没选选中


                    if (mSelectedYuLanLists.size() == CunZhi.mMaxSelectCount) {
                        Toast.makeText(YuLanAllActivity.this, "最多选择" + CunZhi.mMaxSelectCount + "张", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mCbSelect.setChecked(true);

                    mSelectedYuLanLists.add(mALLYuLanLists.get(position));

                    FrameLayout framelayout = (FrameLayout) mInflater.inflate(R.layout.item_suo_lv_tu, null);
                    mSuoLvTuLinearLayout.addView(framelayout, mSuoLvTuWidth, mSuoLvTuWidth);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) framelayout.getLayoutParams();
                    layoutParams.setMargins(mMargin, mMargin, mMargin, mMargin);
                    final ImageView imageview = (ImageView) framelayout.findViewById(R.id.imageview);
                    ImageUtils.loadImage(YuLanAllActivity.this, mALLYuLanLists.get(position), imageview);
                    final ImageView force_image = (ImageView) framelayout.findViewById(R.id.force_image);

                    force_image.setVisibility(View.VISIBLE);
                    mCbSelect.setChecked(true);


//              处理联动// TODO: 2017/8/29  必须等待一小会
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lianDong(position);
                        }
                    }, 100);

                } else {
                    mCbSelect.setChecked(false);
                    int delePositon = mSelectedYuLanLists.indexOf(mALLYuLanLists.get(position));
                    mSelectedYuLanLists.remove(mALLYuLanLists.get(position));

                    mSuoLvTuLinearLayout.removeView(mSuoLvTuLinearLayout.getChildAt(delePositon));
                }

//                由于mSelectedYuLanLists增减了条目，需要更新一遍监听事件，否则的话，会错乱
                for (int i = 0; i < mSelectedYuLanLists.size(); i++) {
                    View childAt = mSuoLvTuLinearLayout.getChildAt(i);
                    final int finalI = i;
                    childAt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mViewPager.setCurrentItem(mALLYuLanLists.indexOf(mSelectedYuLanLists.get(finalI)));

                        }
                    });
                }


                if (mSelectedYuLanLists.size() != 0) {

                    mBtFinish.setEnabled(true);
                    mBtFinish.setText("完成(" + (mSelectedYuLanLists.size()) + "/" + CunZhi.mMaxSelectCount + ")");
                } else {

                    mBtFinish.setEnabled(false);
                    mBtFinish.setText("完  成");

                }
                if (mSelectedYuLanLists == null || mSelectedYuLanLists.size() == 0) {
                    mHorizontalScrollView.setVisibility(View.GONE);
                } else {
                    mHorizontalScrollView.setVisibility(View.VISIBLE);

                }
            }


        });

    }


    private void finishAndSetResult() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra("mSencondShaiXuanYuLanLists", mSelectedYuLanLists);
        setResult(RESULT_CODE_SELECTED_PATH_LIST_YU_LAN, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finishAndSetResult();
                break;
            case R.id.bt_finish:

                Intent intent = new Intent();
                intent.setAction("com.example.hello");
                sendBroadcast(intent);

                CunZhi.mSelectPathList.clear();
                CunZhi.mSelectPathList.addAll(mSelectedYuLanLists);

                Intent intent1 = new Intent(YuLanAllActivity.this, FirstActivity.class);

                startActivity(intent1);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        finishAndSetResult();

//        super.onBackPressed();
    }
}
