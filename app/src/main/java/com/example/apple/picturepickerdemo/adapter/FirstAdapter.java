package com.example.apple.picturepickerdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.apple.picturepickerdemo.R;
import com.example.apple.picturepickerdemo.utils.CunZhi;
import com.example.apple.picturepickerdemo.utils.ImageUtils;

import java.util.List;

/**
 * Created by apple on 2017/8/18.
 */

public class FirstAdapter extends RecyclerView.Adapter<FirstAdapter.FirstViewHolder> {
    private Context mContext;
    private List<String> mSelectImagPathList;
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public FirstAdapter(Context context, List<String> selectImagPathList) {
        mContext = context;
        mSelectImagPathList = selectImagPathList;
    }

    @Override
    public FirstViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new FirstViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_first, parent, false));
    }

    @Override
    public void onBindViewHolder(FirstViewHolder holder, final int position) {


        if (position == mSelectImagPathList.size()) {
            ImageUtils.loadImage(mContext, R.mipmap.add, holder.imageview);
            if (position == CunZhi.mMaxSelectCount) {
                holder.imageview.setVisibility(View.GONE);
            }
        } else {
            ImageUtils.loadImage(mContext, mSelectImagPathList.get(position), holder.imageview);

        }
        holder.imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mSelectImagPathList.size() + 1;
    }


    class FirstViewHolder extends RecyclerView.ViewHolder {


        private ImageView imageview;

        public FirstViewHolder(View itemView) {
            super(itemView);
            imageview = (ImageView) itemView.findViewById(R.id.imageview);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
