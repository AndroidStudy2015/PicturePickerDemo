package com.example.apple.picturepickerdemo.utils;

import com.example.apple.picturepickerdemo.bean.FolderBean;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by apple on 2017/8/10.
 */

public class ComparatorUtils {

    /**
     * 把一个文件夹下的所有png/jpg文件按照时间顺序排列，最新的在最前
     *
     * @param file
     * @return 所有文件名的集合{img1.png,img2.jpg......}
     */
    public static List<String> orderByDate(File file) {

        if (file == null) {
            return null;
        }
//        File file = new File(filePath);
        File[] files = file.listFiles();
        if (files == null) {
            return null;
        }
        Arrays.sort(files, new Comparator<File>() {
            public int compare(File f1, File f2) {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff > 0)
                    return -1;
                else if (diff == 0)
                    return 0;
                else
                    return 1;//如果 if 中修改为 返回-1 同时此处修改为返回 1  排序就会是递减
            }

            public boolean equals(Object obj) {
                return true;
            }

        });
//        for (int i = 0; i < files.length; i++) {
//            System.out.println(files[i].getName());
//            System.out.println(new Date(files[i].lastModified()));
//        }
        List<String> mImgs = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            String filename = files[i].getName();
            if (filename.endsWith(".jpg") || filename.endsWith(".png")) {
                mImgs.add(file.getAbsolutePath() + "/" + filename);
            }
        }
        return mImgs;
    }



    /**
     * 按照照片文件夹内照片的数量排序，数量最多的在最前
     *
     * @param folderBeanList
     * @return
     */

    public static List<FolderBean> orderByImgCount(List<FolderBean> folderBeanList) {


        Collections.sort(folderBeanList, new Comparator<FolderBean>() {

            @Override
            public int compare(FolderBean f1, FolderBean f2) {
                if (f1.getImagCount() >= (f2.getImagCount())) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        return folderBeanList;
    }
}
