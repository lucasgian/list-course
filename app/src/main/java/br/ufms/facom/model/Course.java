package br.ufms.facom.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String name, thumbnailUrl;
    private String code;
    private String shift; // turno
    private String center;

    public Course()
    {

    }

    public Course(String name, String thumbnailUrl, String code, String shift, String center)
    {
        setName(name);
        setCenter(center);
        setCode(code);
        setShift(shift);
        setThumbnailUrl(thumbnailUrl);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }
}
