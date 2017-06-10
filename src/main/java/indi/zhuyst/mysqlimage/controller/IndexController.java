package indi.zhuyst.mysqlimage.controller;

import indi.zhuyst.mysqlimage.pojo.Image;
import indi.zhuyst.mysqlimage.service.ImageService;
import indi.zhuyst.mysqlimage.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhuyst on 2017/6/9.
 * 主页的Controller
 */
@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private ImageService imageService;

    @RequestMapping("/")
    public String index(Model model, HttpSession session){
        List<Image> images = imageService.listImage();
        Long id = (Long) session.getAttribute("id");
        String msg = (String) session.getAttribute("msg");

        if(id != null){
            model.addAttribute("id",id);
            session.removeAttribute("id");
        }
        if(msg != null){
            model.addAttribute("msg",msg);
            session.removeAttribute("msg");
        }

        model.addAttribute("list",images);
        return "index";
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String upload(HttpSession session,@RequestParam("image")MultipartFile[] image){
        HashMap<String,byte[]> map = FileUploadUtil.upload(image);

        String msg;
        if(map == null){
            msg = "上传图片过大";
        }

        if(imageService.insertImage(map)){
            msg = "上传成功";
        }
        else msg = "上传失败";

        session.setAttribute("msg",msg);
        return "redirect:/";
    }

    @RequestMapping(value = "/showImage",method = RequestMethod.GET)
    public void showImage(HttpServletResponse response,Integer id){
        imageService.showImage(response,id);
    }

    @RequestMapping("/delete")
    public String deleteImage(HttpSession session,Integer id){
        String msg;
        if(imageService.deleteImage(id)){
            msg = "删除成功";
        }
        else msg = "删除失败";

        session.setAttribute("msg",msg);
        return "redirect:/";
    }
}
