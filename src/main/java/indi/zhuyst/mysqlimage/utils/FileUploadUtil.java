package indi.zhuyst.mysqlimage.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by zhuyst on 2017/6/8.
 * 上传文件的工具类
 */
public class FileUploadUtil {
    /**
     * 支持多文件上传的工具
     * @param files 上传的文件的集合
     * @return 以文件原名为Key,文件的字节数组为Value的HashMap
     */
    public static HashMap<String,byte[]> upload(MultipartFile[] files){
        HashMap<String,byte[]> map = new HashMap<>();

        for(MultipartFile file : files){
            //获取文件原名
            String fileName = file.getOriginalFilename();
            try {
                //获取文件的字节数组
                byte[] bytes = file.getBytes();
                map.put(fileName,bytes);
            } catch (IOException e) {
                return null;
            }
        }

        return map;
    }
}
