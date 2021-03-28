package cn.fmz.store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.fmz.store.entity.Order;
import cn.fmz.store.service.IOrderService;
import cn.fmz.store.util.JsonResult;

/**
 * 处理订单相关请求的控制器类
 */
@RestController
@RequestMapping("orders")
public class OrderController extends BaseController {

    @Autowired
    private IOrderService orderService;

    @RequestMapping("create")
    public JsonResult<Order> create(
            Integer aid, Integer[] cids, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Order data = orderService.createOrder(aid, cids, uid, username);
        return new JsonResult<>(SUCCESS, data);
    }

}










