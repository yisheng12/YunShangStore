package cn.fmz.store.controller;

import cn.fmz.store.entity.Address;
import cn.fmz.store.service.IAddressService;
import cn.fmz.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("addresses")
public class AddressController extends BaseController {

    @Autowired
    private IAddressService addressService;

    @RequestMapping("addnew")
    public JsonResult<Void> addNew(Address address, HttpSession session) {
        // 从session中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 执行增加收货地址
        addressService.addnew(uid, username, address);
        // 响应成功
        return new JsonResult<>(SUCCESS);
    }

    @GetMapping("/")
    public JsonResult<List<Address>> getByUid(HttpSession session) {
        // 从session中获取uid
        Integer uid = getUidFromSession(session);
        // 调用业务对象执行获取数据
        List<Address> data = addressService.getByUid(uid);
        // 响应：成功，数据
        return new JsonResult<>(SUCCESS, data);
    }

    @RequestMapping("{aid}/set_default")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid, HttpSession session) {
        // 从session中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 调用业务层对象执行
        addressService.setDefault(aid, uid, username);
        // 响应
        return new JsonResult<>(SUCCESS);
    }

    @RequestMapping("{aid}/delete")
    public JsonResult<Void> delete(@PathVariable("aid") Integer aid, HttpSession session) {
        // 从session中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 调用业务层对象执行
        addressService.delete(aid, uid, username);
        // 响应
        return new JsonResult<>(SUCCESS);
    }

}









