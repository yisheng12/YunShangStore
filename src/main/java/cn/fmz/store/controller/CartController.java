package cn.fmz.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.fmz.store.service.ICartService;
import cn.fmz.store.util.JsonResult;
import cn.fmz.store.vo.CartVO;

/**
 * 处理购物车数据相关请求的控制器类
 */
@RestController
@RequestMapping("carts")
public class CartController extends BaseController {

    @Autowired
    private ICartService cartService;

    @RequestMapping("add_to_cart")
    public JsonResult<Void> addToCart(Integer pid, Integer num, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        cartService.addToCart(pid, num, uid, username);
        return new JsonResult<>(SUCCESS);
    }

    @GetMapping("/")
    public JsonResult<List<CartVO>> getByUid(HttpSession session) {
        Integer uid = getUidFromSession(session);
        List<CartVO> data = cartService.getByUid(uid);
        return new JsonResult<>(SUCCESS, data);
    }

    @RequestMapping("{cid}/add_num")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session) {
        // 从session中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 调用业务对象执行增加
        Integer data = cartService.addNum(cid, uid, username);
        // 响应：成功，新的数量
        return new JsonResult<>(SUCCESS, data);
    }

    @GetMapping("get_by_cids")
    public JsonResult<List<CartVO>> getByCids(Integer[] cids, HttpSession session) {
        Integer uid = getUidFromSession(session);
        List<CartVO> data = cartService.getByCids(cids, uid);
        return new JsonResult<>(SUCCESS, data);
    }

}














