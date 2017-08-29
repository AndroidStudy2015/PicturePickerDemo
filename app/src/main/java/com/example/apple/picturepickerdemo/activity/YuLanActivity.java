package com.example.apple.picturepickerdemo.activity;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.apple.picturepickerdemo.R;
import com.example.apple.picturepickerdemo.adapter.MyViewPagerYuLanAdapter;
import com.example.apple.picturepickerdemo.utils.CunZhi;
import com.example.apple.picturepickerdemo.utils.DisplayUtil;
import com.example.apple.picturepickerdemo.utils.ImageUtils;
import com.example.apple.picturepickerdemo.view.HackyViewPager;

import java.util.ArrayList;


public class YuLanActivity extends AppCompatActivity implements View.OnClickListener {


    public static final int RESULT_CODE_SELECTED_PATH_LIST_YU_LAN = 11;
    private ArrayList<String> mYuLanLists;
    private ArrayList<String> mSencondShaiXuanYuLanLists;
    private HackyViewPager mViewPager;
    private TextView mTvYuLanIndex;
    private Button mBtFinish;
    private ImageView mIvFinish;
    private CheckBox mCbSelect;
    private MyViewPagerYuLanAdapter mAdapter;
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
    private int mMargin ;
    private int mScreenWidth;
    private int lastPositon;
    private RelativeLayout mRlSelect;


    /**
     * 这个Activity是用来预览所有选中的照片集合
     *
     *
     * 因为这个Activity下方的横向ScrollView要显示进来这个Activity时候的选中集合
     * 和在本Activity里又再次筛选后的选中集合，所以一进来就复制了一个mSencondShanXuanYuLanLists
     * mYuLanLists用来记录从外界传递来的第一次选中集合
     * mSencondShanXuanYuLanLists用来记录第二次筛选后的记录，注意这个集合是关闭本Activity后向外传递的数据
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_yu_lan);

        mScreenWidth = ImageUtils.getScreenWidth(this);
        mSuoLvTuWidth = DisplayUtil.dip2px(this,70);
        mMargin = DisplayUtil.dip2px(this,10);

        initAnimation();

        initData();
        initView();
        setAdapater();
        intitHorizontalScrollView();
    }


    private void intitHorizontalScrollView() {
        mInflater = LayoutInflater.from(this);

        for (int i = 0; i < mYuLanLists.size(); i++) {
            FrameLayout framelayout = (FrameLayout) mInflater.inflate(R.layout.item_suo_lv_tu, null);
            mSuoLvTuLinearLayout.addView(framelayout, mSuoLvTuWidth, mSuoLvTuWidth);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) framelayout.getLayoutParams();
            layoutParams.setMargins(mMargin, mMargin, mMargin, mMargin);
            final ImageView imageview = (ImageView) framelayout.findViewById(R.id.imageview);
            ImageUtils.loadImage(this, mYuLanLists.get(i), imageview);
            final ImageView force_image = (ImageView) framelayout.findViewById(R.id.force_image);
            final ImageView delete_view = (ImageView) framelayout.findViewById(R.id.delete_view);
            delete_view.setImageResource(R.drawable.translate_yu_lan);

            if (i == 0) {//选中效果
                force_image.setVisibility(View.VISIBLE);
            } else {
                force_image.setVisibility(View.GONE);


            }
            final int finalI = i;
            framelayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mViewPager.setCurrentItem(finalI);


                }
            });

        }
        setCbSelect(0);//初始化显示，因为addOnPageChangeListener方法要在滑动页面后才调用


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
        mYuLanLists = getIntent().getStringArrayListExtra("yuLanList");
        mSencondShaiXuanYuLanLists = new ArrayList<>();
        mSencondShaiXuanYuLanLists.addAll(mYuLanLists);
    }

    private void setAdapater() {
        mAdapter = new MyViewPagerYuLanAdapter(this, mYuLanLists);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTvYuLanIndex.setText((position + 1) + "/" + mYuLanLists.size());

                for (int j = 0; j < mYuLanLists.size(); j++) {
                    View force_iamge = ((ViewGroup) (mSuoLvTuLinearLayout.getChildAt(j))).getChildAt(2);
                    if (position == j) {
                        force_iamge.setVisibility(View.VISIBLE);
                    } else {
                        force_iamge.setVisibility(View.GONE);

                    }
                }

//              处理联动
                View childAt = mSuoLvTuLinearLayout.getChildAt(position);
                int xLeft = (int) childAt.getX();
                int xRight = xLeft + mSuoLvTuWidth + mMargin;
                int scrollX = mHorizontalScrollView.getScrollX();
//                Log.e("get", x + "------" + position + "=======" + scrollX);

//                只要当前的条目可以完全显示，就不去scroll

                if ((xLeft >= scrollX) && (xRight <= scrollX + mScreenWidth)) {//完全显示，不去改变scrollX
                    Log.e("xianshi", "完全显示" + position);
                } else {//没有完全显示的话，需要去改变scrollX，以使得选中的位置完全显示
                    if (lastPositon < position) {
//                        scroll到这个条目的最左侧
                        mHorizontalScrollView.scrollTo(xRight - mScreenWidth, 0);
                    } else {
//                        scroll到这个条目的最右侧
                        mHorizontalScrollView.scrollTo(xLeft, 0);
                    }
                }
                lastPositon = position;


                setCbSelect(position);

            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mAdapter.setOnPhotoViewTapListener(new MyViewPagerYuLanAdapter.OnPhotoViewTapListener() {
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

    private void setCbSelect(final int position) {
        final View delete_view = ((ViewGroup) (mSuoLvTuLinearLayout.getChildAt(position))).getChildAt(1);

//        显示
        if (mSencondShaiXuanYuLanLists.contains(mYuLanLists.get(position))) {
            mCbSelect.setChecked(true);

        } else {
            mCbSelect.setChecked(false);
        }

        mRlSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCbSelect.isChecked()) {//点击之前没选选中
                    mCbSelect.setChecked(true);

                    mSencondShaiXuanYuLanLists.add(mYuLanLists.get(position));
                    delete_view.setBackgroundResource(R.drawable.translate_yu_lan);


                } else {
                    mCbSelect.setChecked(false);

                    mSencondShaiXuanYuLanLists.remove(mYuLanLists.get(position));
                    delete_view.setBackgroundResource(R.drawable.delete_yu_lan);
                }

                if (mSencondShaiXuanYuLanLists.size() != 0) {

                    mBtFinish.setEnabled(true);
                    mBtFinish.setText("完成(" + (mSencondShaiXuanYuLanLists.size()) + "/" + CunZhi.mMaxSelectCount + ")");
                } else {

                    mBtFinish.setEnabled(false);
                    mBtFinish.setText("完  成");

                }
            }


        });

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
        mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.suo_lv_tu_scrollView);
        mSuoLvTuLinearLayout = (LinearLayout) findViewById(R.id.ll_suo_lv_tu);

        mTvYuLanIndex.setText("1/" + mSencondShaiXuanYuLanLists.size());
        if (mSencondShaiXuanYuLanLists.size() != 0) {

            mBtFinish.setEnabled(true);
            mBtFinish.setText("完成(" + (mSencondShaiXuanYuLanLists.size()) + "/" + CunZhi.mMaxSelectCount + ")");
        } else {

            mBtFinish.setEnabled(false);
            mBtFinish.setText("完  成");

        }
    }


    private void finishAndSetResult() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra("mSencondShaiXuanYuLanLists", mSencondShaiXuanYuLanLists);
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
                CunZhi.mSelectPathList.addAll(mSencondShaiXuanYuLanLists);

                Intent intent1 = new Intent(YuLanActivity.this, FirstActivity.class);

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
