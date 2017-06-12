package indi.zhuyst.mysqlimage.service;

import indi.zhuyst.mysqlimage.dao.ImageDao;
import indi.zhuyst.mysqlimage.pojo.Image;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuyst on 2017/6/8.
 * Image的服务类
 */
@Service
public class ImageService {
    @Autowired
    private ImageDao imageDao;

    public Image getImage(Integer id){
        return imageDao.getImage(id);
    }

    public List<Image> listImage(){
        return imageDao.listImage();
    }

    /**
     * 批量向数据库插入图片
     * @param map 经由FileUploadUtil工具出来的HashMap
     * @return 是否插入成功
     */
    public boolean insertImage(HashMap<String,byte[]> map){
        for(Map.Entry<String,byte[]> entry : map.entrySet()){
            Image image = new Image();
            image.setName(entry.getKey());
            try {
                Blob blob = new SerialBlob(entry.getValue());
                image.setImage(blob);
            } catch (SQLException e) {
                return false;
            }
            if(!imageDao.insertImage(image)){
                return false;
            }
        }
        return true;
    }

    /**
     * 将Response修改为图片流
     * @param response 将被修改的原生HttpServletResponse
     * @param id 图片的id
     */
    public void showImage(HttpServletResponse response,Integer id){
        Image image = this.getImage(id);
        String name = image.getName().toLowerCase();
        if(name.contains(".jpg")){
            response.setContentType("image/jpeg");
        }
        else if(name.contains(".gif")){
            response.setContentType("image/gif");
        }
        else {
            response.setContentType("image/png");
        }

        try {
            OutputStream outputStream = response.getOutputStream();
            InputStream inputStream = image.getImage().getBinaryStream();
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteImage(Integer id){
        return imageDao.deleteImage(id);
    }
}
