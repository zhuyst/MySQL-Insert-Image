package indi.zhuyst.mysqlimage.pojo;

import java.sql.Blob;

/**
 * Created by zhuyst on 2017/6/8.
 * Image的实体类
 */
public class Image {
    private Integer id;

    private String name;

    private Blob image;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }
}
