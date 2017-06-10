package indi.zhuyst.mysqlimage.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by zhuyst on 2017/6/9.
 * 能够在任何位置获取到HttpSession的工具类
 */
public class SessionUtil {
    //获取HttpSession
    public static HttpSession getSession(){
        return ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }
}
