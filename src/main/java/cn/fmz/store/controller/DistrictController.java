package cn.fmz.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.fmz.store.entity.District;
import cn.fmz.store.service.IDistrictService;
import cn.fmz.store.util.JsonResult;

@RestController
@RequestMapping("districts")
public class DistrictController extends BaseController {

    @Autowired
    private IDistrictService districtService;

    @GetMapping("/")
    public JsonResult<List<District>> getByParent(String parent) {
        List<District> data = districtService.getByParent(parent);
        return new JsonResult<>(SUCCESS, data);
    }

}







