package com.example.apple.picturepickerdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.apple.picturepickerdemo.R;
import com.example.apple.picturepickerdemo.bean.FolderBean;
import com.example.apple.picturepickerdemo.utils.ImageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/8/21.
 */

public class PopupWindowSelectDirAdapter extends RecyclerView.Adapter<PopupWindowSelectDirAdapter.MyVH> {
    private Context context;
    private List<FolderBean> dirPathList;
    private OnItemClickListener mOnItemClickListener;
    private List<Boolean> isMyCheckedList = new ArrayList<>();

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public PopupWindowSelectDirAdapter(Context context, List<FolderBean> dirPathList) {
        this.context = context;
        this.dirPathList = dirPathList;

        for (int i = 0; i < dirPathList.size(); i++) {
            if (i == 0) {
                isMyCheckedList.add(true);
            } else {
                isMyCheckedList.add(false);
            }
        }
    }

    @Override
    public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyVH(LayoutInflater.from(context).inflate(R.layout.item_pop_select_dir, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyVH holder, final int position) {
        final FolderBean folderBean = dirPathList.get(position);
//        ImageUtils.loadImage(context, folderBean.getFolderPath() + "/" + folderBean.getFolderFirstPic(), holder.mIvFirstImg);
        ImageUtils.loadImage(context, folderBean.getFolderFirstPic(), holder.mIvFirstImg);
        holder.mTvTitle.setText(folderBean.getFolderName());
        holder.mTvImgCount.setText(folderBean.getImagCount() + "");
        holder.mRadio.setChecked(true);
        Boolean isMyChecked = isMyCheckedList.get(position);
        if (isMyChecked) {
            holder.mRadio.setVisibility(View.VISIBLE);

        } else {
            holder.mRadio.setVisibility(View.GONE);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        setChecked(position);
                    notifyDataSetChanged();
                    mOnItemClickListener.onItemClick(folderBean.getFolderFile());
                }
            }
        });
    }

    private void setChecked(int position) {
        isMyCheckedList.clear();
        for (int i = 0; i < dirPathList.size(); i++) {
            if (i == position) {
                isMyCheckedList.add(true);

            } else {
                isMyCheckedList.add(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dirPathList.size();
    }


    class MyVH extends RecyclerView.ViewHolder {

        private ImageView mIvFirstImg;
        private TextView mTvTitle;
        private TextView mTvImgCount;
        private RadioButton mRadio;
        private View itemView;

        public MyVH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            mIvFirstImg = (ImageView) itemView.findViewById(R.id.iv_first_img);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTvImgCount = (TextView) itemView.findViewById(R.id.tv_img_count);
            mRadio = (RadioButton) itemView.findViewById(R.id.radio);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(File mImgDir);

    }
}
