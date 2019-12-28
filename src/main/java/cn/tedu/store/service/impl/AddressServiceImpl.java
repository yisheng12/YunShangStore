package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.IDistrictService;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressCountLimitException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;

/**
 * 处理收货地址数据的业务层实现类
 */
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private IDistrictService districtService;

    @Value("${project.address-max-count}")
    private Integer maxCount;

    @Override
    public void addnew(Integer uid, String username, Address address) {
        // 根据参数uid查询该用户的收货地址的数量
        Integer count = countByUid(uid);
        // 判断数量是否达到上限(>=5)
        if (count >= maxCount) {
            // 是：抛出AddressCountLimitException
            throw new AddressCountLimitException(
                    "收货地址数量已经达到上限(" + maxCount + ")");
        }

        // 判断数量是否为0
        // 是：isDefault=1
        // 否：isDefault=0
        Integer isDefault = count == 0 ? 1 : 0;

        // 补全数据：uid
        address.setUid(uid);
        // 补全数据：is_default
        address.setIsDefault(isDefault);
        // 补全数据：省/市/区的名称
        String provinceName = getDistrictName(address.getProvinceCode());
        String cityName = getDistrictName(address.getCityCode());
        String areaName = getDistrictName(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);
        // 补全数据：4项日志
        Date now = new Date();
        address.setCreatedUser(username);
        address.setCreatedTime(now);
        address.setModifiedUser(username);
        address.setModifiedTime(now);

        // 执行插入数据，获取返回值
        insert(address);
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = findByUid(uid);
        for (Address address : list) {
            address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setCreatedUser(null);
            address.setCreatedTime(null);
            address.setModifiedUser(null);
            address.setModifiedTime(null);
        }
        return list;
    }

    @Override
    @Transactional
    public void setDefault(Integer aid, Integer uid, String username) {
        // 根据参数aid查询收货地址数据
        Address result = findByAid(aid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：AddressNotFoundException
            throw new AddressNotFoundException(
                    "尝试访问的收货地址数据不存在");
        }

        // 判断查询结果中的uid与参数uid是否不一致(使用equals)
        if (!result.getUid().equals(uid)) {
            // 是：AccessDeniedException
            throw new AccessDeniedException("非法访问");
        }

        // 将该用户的所有收货地址设置为非默认，并获取返回值
        updateNonDefault(uid);

        // 将指定的收货地址设置为默认，并获取返回值
        updateDefault(aid, username, new Date());
    }

    @Override
    @Transactional
    public void delete(Integer aid, Integer uid, String username) {
        // 根据参数aid查询收货地址数据
        Address result = findByAid(aid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：AddressNotFoundException
            throw new AddressNotFoundException(
                    "尝试访问的收货地址数据不存在");
        }

        // 判断查询结果中的uid与参数uid是否不一致
        if (!result.getUid().equals(uid)) {
            // 是：AccessDeniedException
            throw new AccessDeniedException(
                    "非法访问");
        }

        // 执行删除，获取返回的受影响行数
        deleteByAid(aid);

        // 判断查询结果中的is_defalut是否不为1
        if (!result.getIsDefault().equals(1)) {
            return;
        }

        // 刚才删除的是默认收货地址，则调用countByUid()统计当前收货地址数据的数量
        // Integer count = addressMapper.countByUid(uid);
        // 判断收货地址数量是否为0
        // if (count.equals(0)) {
        //	return;
        // }

        // 则才删除的是默认收货地址，则找出最近修改的那一条收货地址
        Address lastModifed = findLastModified(uid);
        // 判断是否还能找到数据
        if (lastModifed == null) {
            // 没有找到，则没有更多数据了，直接结束
            return;
        }

        // 取出最近修改的收货地址的id
        Integer lastModifiedAid = lastModifed.getAid();
        // 将这条收货地址设置为默认，并获取返回的受影响行数
        updateDefault(lastModifiedAid, username, new Date());
    }

    @Override
    public Address getByAid(Integer aid) {
        Address result = findByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException(
                    "尝试访问的收货地址数据不存在");
        }

        result.setCreatedUser(null);
        result.setCreatedTime(null);
        result.setModifiedUser(null);
        result.setModifiedTime(null);

        return result;
    }

    /**
     * 根据省/市/区的代号，获取省/市/区的名称
     *
     * @param districtCode 省/市/区的代号
     * @return 匹配的省/市/区的名称，如果没有匹配的数据，则返回null
     */
    private String getDistrictName(String districtCode) {
        District district = districtService.getByCode(districtCode);
        if (district != null) {
            return district.getName();
        }
        return null;
    }

    /**
     * 插入收货地址数据
     *
     * @param address 收货地址数据
     */
    private void insert(Address address) {
        Integer rows = addressMapper.insert(address);
        if (rows != 1) {
            throw new InsertException("增加收货地址数据时出现未知错误，请联系系统管理员");
        }
    }

    /**
     * 根据收货地址id删除数据
     *
     * @param aid 收货地址id
     */
    private void deleteByAid(Integer aid) {
        Integer rows = addressMapper.deleteByAid(aid);
        if (rows != 1) {
            throw new DeleteException("删除收货地址数据时出现未知错误，请联系系统管理员");
        }
    }

    /**
     * 将指定的收货地址设置为默认
     *
     * @param aid      收货地址的id
     * @param username 当前登录的用户名
     * @param date     执行操作时的时间
     */
    private void updateDefault(Integer aid, String username, Date date) {
        // 执行修改
        Integer rows = addressMapper.updateDefault(aid, username, date);
        // 判断返回值是否不为1
        if (rows != 1) {
            // 是：抛出UpdateException
            throw new UpdateException("更新数据时出现未知错误，请联系系统管理员！[2]");
        }
    }

    /**
     * 将某用户的所有收货地址全部设置为非默认
     *
     * @param uid 用户的id
     */
    private void updateNonDefault(Integer uid) {
        Integer rows = addressMapper.updateNonDefault(uid);
        if (rows < 1) {
            throw new UpdateException("更新数据时出现未知错误，请联系系统管理员！[2]");
        }
    }

    /**
     * 统计某用户的收货地址数据的数量
     *
     * @param uid 用户的id
     * @return 该用户的收货地址数据的数量
     */
    private Integer countByUid(Integer uid) {
        return addressMapper.countByUid(uid);
    }

    /**
     * 查询某用户的收货地址列表
     *
     * @param uid 用户的id
     * @return 该用户的收货地址列表
     */
    private List<Address> findByUid(Integer uid) {
        return addressMapper.findByUid(uid);
    }

    /**
     * 根据收货地址id，查询收货地址详情
     *
     * @param aid 收货地址id
     * @return 匹配的收货地址详情，如果没有匹配的数据，则返回null
     */
    private Address findByAid(Integer aid) {
        if (aid == null) {
            return null;
        }
        Address result = addressMapper.findByAid(aid);
        return result;
    }

    /**
     * 查询最近修改的收货地址详情
     *
     * @param uid 收货地址归属的用户的id
     * @return 匹配的收货地址详情，如果没有匹配的数据，则返回null
     */
    private Address findLastModified(Integer uid) {
        return addressMapper.findLastModified(uid);
    }


}





