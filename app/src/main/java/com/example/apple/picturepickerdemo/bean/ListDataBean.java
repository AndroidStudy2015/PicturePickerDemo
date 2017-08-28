package com.example.apple.picturepickerdemo.bean;

import java.io.File;
import java.util.List;

/**
 * Created by apple on 2017/8/23.
 */

public class ListDataBean {
    private File parentFile;
    private List<String> childImgPath;

    public File getParentFile() {
        return parentFile;
    }

    public void setParentFile(File parentFile) {
        this.parentFile = parentFile;
    }

    public List<String> getChildImgPath() {
        return childImgPath;
    }

    public void setChildImgPath(List<String> childImgPath) {
        this.childImgPath = childImgPath;
    }

    @Override
    public String toString() {
        return "ListDataBean{" +
                "parentFile=" + parentFile +
                ", childImgPath=" + childImgPath +
                '}';
    }
}
