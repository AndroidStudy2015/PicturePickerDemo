package com.example.apple.picturepickerdemo.bean;

import java.io.File;

/**
 * Created by apple on 2017/8/21.
 */

public class FolderBean {

    private String folderName;
    private String folderFirstPic;
    private File folderFile;
    private String  folderPath;
    private int  imagCount;

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderFirstPic() {
        return folderFirstPic;
    }

    public void setFolderFirstPic(String folderFirstPic) {
        this.folderFirstPic = folderFirstPic;
    }

    public File getFolderFile() {
        return folderFile;
    }

    public void setFolderFile(File folderFile) {
        this.folderFile = folderFile;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public int getImagCount() {
        return imagCount;
    }

    public void setImagCount(int imagCount) {
        this.imagCount = imagCount;
    }

    @Override
    public String toString() {
        return "FolderBean{" +
                "folderName='" + folderName + '\'' +
                ", folderFirstPic='" + folderFirstPic + '\'' +
                ", folderFile=" + folderFile +
                ", folderPath='" + folderPath + '\'' +
                ", imagCount=" + imagCount +
                '}';
    }
}
