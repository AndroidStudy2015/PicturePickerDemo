package com.example.apple.picturepickerdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.example.apple.picturepickerdemo.R;
import com.example.apple.picturepickerdemo.activity.PicturePickerActivity;
import com.example.apple.picturepickerdemo.utils.ImageUtils;

import java.util.List;

/**
 * Created by apple on 2017/8/18.
 */

public class PicturePickerAdapter extends RecyclerView.Adapter<PicturePickerAdapter.FirstViewHolder> {
    private Context mContext;
    private List<String> imgs;
    OnPicturePickerItemClickLisnter onPicturePickerItemClickLisnter;

    public void setOnPicturePickerItemClickLisnter(OnPicturePickerItemClickLisnter onPicturePickerItemClickLisnter) {
        this.onPicturePickerItemClickLisnter = onPicturePickerItemClickLisnter;
    }

    public PicturePickerAdapter(Context context, List<String> imgs) {
        mContext = context;
        this.imgs = imgs;
    }

    @Override
    public FirstViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new FirstViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_pic_picker, parent, false));
    }

    @Override
    public void onBindViewHolder(final FirstViewHolder holder, final int position) {
//        ImageUtils.loadImage(mContext, absolutePath + "/" + imgs.get(position), holder.picPickerImagerView);
        ImageUtils.loadImage(mContext, imgs.get(position), holder.picPickerImagerView);

        if (PicturePickerActivity.mTempSelectPathList.contains(imgs.get(position))) {
            holder.checkbox.setChecked(true);
        } else {
            holder.checkbox.setChecked(false);

        }


        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPicturePickerItemClickLisnter != null) {
                    onPicturePickerItemClickLisnter.onCheckboxClick(imgs, holder.checkbox, holder.blackTranslate, position);
                }

            }
        });


        holder.picPickerImagerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPicturePickerItemClickLisnter != null) {
                    onPicturePickerItemClickLisnter.onImageClick(holder.picPickerImagerView, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return imgs == null ? 0 : imgs.size();
    }


    class FirstViewHolder extends RecyclerView.ViewHolder {


        private View blackTranslate;
        private CheckBox checkbox;
        private ImageView picPickerImagerView;

        public FirstViewHolder(View itemView) {
            super(itemView);
            picPickerImagerView = (ImageView) itemView.findViewById(R.id.pic_picker_imageview);
            blackTranslate = itemView.findViewById(R.id.black_translate);
            checkbox = (CheckBox) itemView.findViewById(R.id.cb_select);


            int screenWidth = ImageUtils.getScreenWidth(mContext);
            ViewGroup.LayoutParams layoutParams = picPickerImagerView.getLayoutParams();
            layoutParams.width = screenWidth / 3;
            layoutParams.height = screenWidth / 3;
            picPickerImagerView.setLayoutParams(layoutParams);
            blackTranslate.setLayoutParams(layoutParams);
        }
    }


    public interface OnPicturePickerItemClickLisnter {
        void onCheckboxClick(List<String> imgs, CheckBox checkbox, View blackTranslate, int position);

        void onImageClick(ImageView picPickerImagerView, int position);

    }
}
