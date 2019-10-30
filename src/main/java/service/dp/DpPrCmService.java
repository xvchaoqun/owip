package service.dp;

import controller.global.OpException;
import domain.dp.DpPrCm;
import domain.dp.DpPrCmExample;
import domain.sys.SysUserView;
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
public class DpPrCmService extends DpBaseMapper {

    @Autowired
    private SysUserService sysUserService;

    public boolean idDuplicate(Integer id, Integer userId, Integer type, Integer electSession){

        Assert.isTrue(userId != null  && electSession != null && type != null, "null");

        DpPrCmExample example = new DpPrCmExample();
        DpPrCmExample.Criteria criteria = example.createCriteria().andStatusEqualTo(true).andUserIdEqualTo(userId)
                .andElectSessionEqualTo(electSession).andTypeEqualTo(type);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return dpPrCmMapper.countByExample(example) > 0;
    }

    @Transactional
    public int batchImportDpPrCm(List<DpPrCm> records){
        int addCount = 0;
        for (DpPrCm dpPrCm : records){
            if (add(dpPrCm)){
                addCount++;
            }
        }
        return addCount;
    }

    //添加党外代表人士
    public boolean add(DpPrCm record){

        Integer userId = record.getUserId();
        SysUserView uv = sysUserService.findById(userId);
        Byte type = uv.getType();

        if (type != SystemConstants.USER_TYPE_JZG){
            throw new OpException("账号不是教职工。" + uv.getCode() + "," + uv.getRealname());
        }

        boolean isAdd = false;
        DpPrCm dpPrCm = get(userId);
        if (dpPrCm == null){
            Assert.isTrue(dpPrCmMapper.insertSelective(record) == 1, "dp insert failed");
            isAdd = true;
        }else if (dpPrCm != null){
            record.setId(dpPrCm.getId());
            Assert.isTrue(dpPrCmMapper.updateByPrimaryKeySelective(record) == 1, "dp insert failed");
        }

        return isAdd;
    }

    //根据userId得到人员
    public DpPrCm get(Integer userId){

        if (dpPrCmMapper == null) return null;
        DpPrCmExample example = new DpPrCmExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<DpPrCm> dpPrCms = dpPrCmMapper.selectByExample(example);
        if (dpPrCms.size() == 1){
            for (DpPrCm dpOm : dpPrCms){
                return dpOm;
            }
        }

        return null;
    }

    @Transactional
    @CacheEvict(value="DpPrCm:ALL", allEntries = true)
    public void insertSelective(DpPrCm record){

        if (idDuplicate(null, record.getUserId(), record.getElectSession(), record.getType())){
            throw new OpException("添加重复");
        }
        record.setSortOrder(getNextSortOrder("dp_pr_cm", null));
        dpPrCmMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="DpPrCm:ALL", allEntries = true)
    public void del(Integer id){

        dpPrCmMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="DpPrCm:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DpPrCmExample example = new DpPrCmExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        dpPrCmMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="DpPrCm:ALL", allEntries = true)
    public void updateByPrimaryKeySelective(DpPrCm record){
        if(record.getUserId() != null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getUserId(), record.getElectSession(), record.getType()), "duplicate");
        dpPrCmMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="DpPrCm:ALL")
    public Map<Integer, DpPrCm> findAll() {

        DpPrCmExample example = new DpPrCmExample();
        example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");
        List<DpPrCm> records = dpPrCmMapper.selectByExample(example);
        Map<Integer, DpPrCm> map = new LinkedHashMap<>();
        for (DpPrCm record : records) {
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
    @CacheEvict(value = "DpPrCm:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        changeOrder("dp_pr_cm", null, ORDER_BY_DESC, id, addNum);
    }
}
