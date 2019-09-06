package service.dp;

import controller.global.OpException;
import domain.dp.DpNpm;
import domain.dp.DpNpmExample;
import domain.sys.SysUserView;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.sys.SysUserService;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DpNpmService extends DpBaseMapper {

    @Autowired
    private SysUserService sysUserService;

    public boolean idDuplicate(Integer id, Integer userId){

        Assert.isTrue(StringUtils.isNotBlank(userId+""), "null");

        DpNpmExample example = new DpNpmExample();
        DpNpmExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return dpNpmMapper.countByExample(example) > 0;
    }

    //根据userId得到人员
    public DpNpm get(Integer userId){
        if (dpNpmMapper == null) return null;
        DpNpmExample example = new DpNpmExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<DpNpm> dpNpms = dpNpmMapper.selectByExample(example);
        if (dpNpms.size() == 1){
            for (DpNpm dpNpm : dpNpms){
                return dpNpm;
            }
        }
        return null;
    }

    @Transactional
    public int batchImportDpDpm(List<DpNpm> records){
        int addCount = 0;
        for (DpNpm dpNpm : records){
            if (add(dpNpm)){
                addCount++;
            }
        }
        return addCount;
    }

    //添加无党派人士
    public boolean add(DpNpm record){

        Integer userId = record.getUserId();
        SysUserView uv = sysUserService.findById(userId);
        Byte type = uv.getType();

        if (type != SystemConstants.USER_TYPE_JZG){
            throw new OpException("账号不是教职工。" + uv.getCode() + "," + uv.getRealname());
        }

        boolean isAdd = false;
        DpNpm dpNpm = get(userId);
        if (dpNpm == null){
            Assert.isTrue(dpNpmMapper.insertSelective(record) == 1, "dp insert failed");
            isAdd = true;
        }else if (dpNpm != null){
            record.setId(dpNpm.getId());
            Assert.isTrue(dpNpmMapper.updateByPrimaryKeySelective(record) == 1, "dp insert failed");
        }

        return isAdd;
    }

    @Transactional
    @CacheEvict(value="DpNpm:ALL", allEntries = true)
    public void insertSelective(DpNpm record){

        Assert.isTrue(!idDuplicate(null, record.getUserId()), "duplicate");
        record.setSortOrder(getNextSortOrder("dp_npm", null));
        dpNpmMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="DpNpm:ALL", allEntries = true)
    public void del(Integer id){

        dpNpmMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="DpNpm:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DpNpmExample example = new DpNpmExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        dpNpmMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="DpNpm:ALL", allEntries = true)
    public void updateByPrimaryKeySelective(DpNpm record){
        if(record.getTransferTime() != null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getUserId()), "duplicate");
        dpNpmMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="DpNpm:ALL")
    public Map<Integer, DpNpm> findAll() {

        DpNpmExample example = new DpNpmExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<DpNpm> records = dpNpmMapper.selectByExample(example);
        Map<Integer, DpNpm> map = new LinkedHashMap<>();
        for (DpNpm record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "DpNpm:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        changeOrder("dp_npm", null, ORDER_BY_DESC, id, addNum);
    }
}
