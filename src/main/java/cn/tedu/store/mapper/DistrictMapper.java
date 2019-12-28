package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.District;

/**
 * 处理省/市/区数据的持久层接口
 */
public interface DistrictMapper {

    /**
     * 根据省/市/区的代号查询详情
     *
     * @param code 省/市/区的代号
     * @return 省/市/区的详情
     */
    District findByCode(String code);

    /**
     * 获取全国所有省/某省所有市/某省所有区的列表
     *
     * @param parent 父级单位的行政代号，如果需要获取全国所有的省，该参数应该使用"86"
     * @return 全国所有省/某省所有市/某省所有区的列表
     */
    List<District> findByParent(String parent);

}






