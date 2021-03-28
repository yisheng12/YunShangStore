package cn.fmz.store.controller;

import javax.servlet.http.HttpSession;

import cn.fmz.store.service.ex.AccessDeniedException;
import cn.fmz.store.service.ex.DeleteException;
import cn.fmz.store.service.ex.ProductNotFoundException;
import cn.fmz.store.service.ex.UsernameDuplicateException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import cn.fmz.store.controller.ex.FileEmptyException;
import cn.fmz.store.controller.ex.FileIOException;
import cn.fmz.store.controller.ex.FileSizeException;
import cn.fmz.store.controller.ex.FileStateException;
import cn.fmz.store.controller.ex.FileTypeException;
import cn.fmz.store.controller.ex.FileUploadException;
import cn.fmz.store.service.ex.AddressCountLimitException;
import cn.fmz.store.service.ex.AddressNotFoundException;
import cn.fmz.store.service.ex.InsertException;
import cn.fmz.store.service.ex.PasswordNotMatchException;
import cn.fmz.store.service.ex.ServiceException;
import cn.fmz.store.service.ex.UpdateException;
import cn.fmz.store.service.ex.UserNotFoundException;
import cn.fmz.store.util.JsonResult;

/**
 * 控制器类的基类
 */
public abstract class BaseController {

    /**
     * 操作成功时的状态码
     */
    public static final int SUCCESS = 2000;

    /**
     * 从Session中获取当前登录的用户的id
     *
     * @param session HttpSession对象
     * @return 当前登录的用户的id
     */
    protected Integer getUidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 从Session中获取当前登录的用户名
     *
     * @param session HttpSession对象
     * @return 当前登录的用户名
     */
    protected String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }

    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonResult<Void> handleException(Throwable ex) {
        // 创建响应结果对象
        JsonResult<Void> jsonResult = new JsonResult<>(ex);

        // 逐一判断异常类型
        if (ex instanceof UsernameDuplicateException) {
            // 4000-用户名冲突异常，例如：注册时，用户名已经被占用
            jsonResult.setState(4000);
        } else if (ex instanceof UserNotFoundException) {
            // 4001-用户数据不存在
            jsonResult.setState(4001);
        } else if (ex instanceof PasswordNotMatchException) {
            // 4002-验证密码失败
            jsonResult.setState(4002);
        } else if (ex instanceof AddressCountLimitException) {
            // 4003-收货地址的数量达到上限的异常
            jsonResult.setState(4003);
        } else if (ex instanceof AccessDeniedException) {
            // 4004-拒绝访问，例如尝试访问他人的数据
            jsonResult.setState(4004);
        } else if (ex instanceof AddressNotFoundException) {
            // 4005-收货地址数据不存在的异常
            jsonResult.setState(4005);
        } else if (ex instanceof ProductNotFoundException) {
            // 4006-商品数据不存在的异常
            jsonResult.setState(4006);
        } else if (ex instanceof InsertException) {
            // 5000-插入数据异常
            jsonResult.setState(5000);
        } else if (ex instanceof UpdateException) {
            // 5001-更新数据失败
            jsonResult.setState(5001);
        } else if (ex instanceof DeleteException) {
            // 5002-删除数据失败
            jsonResult.setState(5001);
        } else if (ex instanceof FileEmptyException) {
            // 6000-上传的文件为空的异常
            jsonResult.setState(6000);
        } else if (ex instanceof FileSizeException) {
            // 6001-上传的文件大小超出限制
            jsonResult.setState(6001);
        } else if (ex instanceof FileTypeException) {
            // 6002-上传的文件类型超出了限制
            jsonResult.setState(6002);
        } else if (ex instanceof FileStateException) {
            // 6003-上传的文件状态异常
            jsonResult.setState(6003);
        } else if (ex instanceof FileIOException) {
            // 6004-上传文件文件时出现读写错误
            jsonResult.setState(6004);
        }

        return jsonResult;
    }

}
