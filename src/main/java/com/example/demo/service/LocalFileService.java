package com.example.demo.service;

import java.io.File;
import java.net.URI;

public class LocalFileService extends File {

    boolean isSelected;

    public LocalFileService(String pathname, boolean isSelected) {
        super(pathname);
        this.isSelected = isSelected;
    }

    public LocalFileService(String parent, String child, boolean isSelected) {
        super(parent, child);
        this.isSelected = isSelected;
    }

    public LocalFileService(File parent, String child, boolean isSelected) {
        super(parent, child);
        this.isSelected = isSelected;
    }

    public LocalFileService(URI uri, boolean isSelected) {
        super(uri);
        this.isSelected = isSelected;
    }
}
