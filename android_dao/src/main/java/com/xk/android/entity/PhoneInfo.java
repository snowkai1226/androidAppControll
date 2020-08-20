package com.xk.android.entity;

public class PhoneInfo {
    private int id;
    private String model;
    private String version;
    private String root;
    private String size;
    private String centerX;
    private String centerY;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCenterX() {
        return centerX;
    }

    public void setCenterX(String centerX) {
        this.centerX = centerX;
    }

    public String getCenterY() {
        return centerY;
    }

    public void setCenterY(String centerY) {
        this.centerY = centerY;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
