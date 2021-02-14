package service.dp;

import controller.global.OpException;
import domain.dp.DpNpr;
import domain.dp.DpNprExample;
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
public class DpNprService extends DpBaseMapper {

    @Autowired
    private SysUserService sysUserService;

    public boolean idDuplicate(Integer id, Integer userId){

        Assert.isTrue(userId!=null, "null");

        DpNprExample example = new DpNprExample();
        DpNprExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return dpNprMapper.countByExample(example) > 0;
    }

    @Transactional
    public int batchImportDpNpr(List<DpNpr> records){
        int addCount = 0;
        for (DpNpr dpNpr : records){
            if (add(dpNpr)){
                addCount++;
            }
        }
        return addCount;
    }

    //添加党外代表人士
    public boolean add(DpNpr record){

        Integer userId = record.getUserId();
        SysUserView uv = sysUserService.findById(userId);
        Byte type = uv.getType();

        if (type != SystemConstants.USER_TYPE_JZG){
            throw new OpException("账号不是教职工。" + uv.getCode() + "," + uv.getRealname());
        }

        boolean isAdd = false;
        DpNpr dpNpr = get(userId);
        if (dpNpr == null){
            Assert.isTrue(dpNprMapper.insertSelective(record) == 1, "dp insert failed");
            isAdd = true;
        }else if (dpNpr != null){
            record.setId(dpNpr.getId());
            Assert.isTrue(dpNprMapper.updateByPrimaryKeySelective(record) == 1, "dp insert failed");
        }

        return isAdd;
    }

    //根据userId得到人员
    public DpNpr get(Integer userId){

        if (dpNprMapper == null) return null;
        DpNprExample example = new DpNprExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<DpNpr> dpNprs = dpNprMapper.selectByExample(example);
        if (dpNprs.size() == 1){
            for (DpNpr dpNpr : dpNprs){
                return dpNpr;
            }
        }

        return null;
    }

    @Transactional
    public void insertSelective(DpNpr record){

        Assert.isTrue(!idDuplicate(null, record.getUserId()), "duplicate");
        record.setSortOrder(getNextSortOrder("dp_npr", null));

        int userId = record.getUserId();
        dpCommonService.findOrCreateCadre(userId);

        dpNprMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        dpNprMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DpNprExample example = new DpNprExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        dpNprMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(DpNpr record){
        if(record.getUserId() != null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getUserId()), "duplicate");

        int userId = record.getUserId();
        dpCommonService.findOrCreateCadre(userId);

        dpNprMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, DpNpr> findAll() {

        DpNprExample example = new DpNprExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<DpNpr> records = dpNprMapper.selectByExample(example);
        Map<Integer, DpNpr> map = new LinkedHashMap<>();
        for (DpNpr record : records) {
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
    @CacheEvict(value = "DpNpr:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        changeOrder("dp_npr", null, ORDER_BY_DESC, id, addNum);
    }
}
