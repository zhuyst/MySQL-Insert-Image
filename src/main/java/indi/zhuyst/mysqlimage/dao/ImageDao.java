package indi.zhuyst.mysqlimage.dao;

import indi.zhuyst.mysqlimage.pojo.Image;
import indi.zhuyst.mysqlimage.utils.SessionUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by zhuyst on 2017/6/8.
 * Image的访问类
 */
@Repository
public class ImageDao {
    @Autowired
    private QueryRunner queryRunner;

    public boolean insertImage(Image image){
        String sql = "INSERT INTO image (name,image) VALUES (?,?)";
        try {
            Long id = queryRunner.insert(sql,new ScalarHandler<>(1),image.getName(),image.getImage());
            HttpSession session = SessionUtil.getSession();
            session.setAttribute("id",id);
            return id > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteImage(Integer id){
        String sql = "DELETE FROM image WHERE id=?";
        try {
            return queryRunner.update(sql,id) > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<Image> listImage(){
        String sql = "SELECT id,name FROM image";
        List<Image> list;
        try {
            list = queryRunner.query(sql, new BeanListHandler<>(Image.class));
            //由于DBUtils不支持直接将Blob取出，所以使用原生JDBC的方法
            for(Image image : list){
                image.setImage(getBlob(image.getId()));
            }
            return list;
        } catch (SQLException e) {
            return null;
        }
    }

    public Image getImage(Integer id){
        String sql = "SELECT id,name FROM image WHERE id=?";
        try {
            Image image = queryRunner.query(sql, new BeanHandler<>(Image.class),id);
            //由于DBUtils不支持直接将Blob取出，所以使用原生JDBC的方法
            image.setImage(getBlob(id));
            return image;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * 由于DBUtils不支持直接将Blob取出，所以使用原生JDBC的方法
     * @param id Image的id
     * @return 从SQL取出的Blob
     */
    private SerialBlob getBlob(Integer id){
        String sql = "SELECT image FROM image WHERE id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = queryRunner.getDataSource().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(id));
            resultSet = preparedStatement.executeQuery();

            resultSet.next();
            InputStream inputStream = resultSet.getBinaryStream(1);
            return new SerialBlob(IOUtils.toByteArray(inputStream));
        } catch (SQLException | IOException e) {
            return null;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
