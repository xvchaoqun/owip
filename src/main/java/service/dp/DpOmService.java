package service.dp;

import controller.global.OpException;
import domain.dp.DpOm;
import domain.dp.DpOmExample;
import domain.sys.SysUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.dp.dpCommon.DpCommonService;
import service.sys.SysUserService;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DpOmService extends DpBaseMapper {

    @Autowired
    protected DpCommonService dpCommonService;
    @Autowired
    private SysUserService sysUserService;

    public boolean idDuplicate(Integer id, Integer userId){

        Assert.isTrue(userId != null, "null");

        DpOmExample example = new DpOmExample();
        DpOmExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return dpOmMapper.countByExample(example) > 0;
    }

    @Transactional
    public int batchImportDpOm(List<DpOm> records){
        int addCount = 0;
        for (DpOm dpOm : records){
            if (add(dpOm)){
                addCount++;
            }
        }
        return addCount;
    }

    //添加其他统战人员
    public boolean add(DpOm record){

        Integer userId = record.getUserId();
        SysUserView uv = sysUserService.findById(userId);
        Byte type = uv.getType();

        if (type != SystemConstants.USER_TYPE_JZG){
            throw new OpException("账号不是教职工。" + uv.getCode() + "," + uv.getRealname());
        }

        boolean isAdd = false;
        DpOm dpOm = get(userId);
        if (dpOm == null){
            Assert.isTrue(dpOmMapper.insertSelective(record) == 1, "dp insert failed");
            isAdd = true;
        }else if (dpOm != null){
            record.setId(dpOm.getId());
            Assert.isTrue(dpOmMapper.updateByPrimaryKeySelective(record) == 1, "dp insert failed");
        }

        dpCommonService.updateMemberRole(userId);

        return isAdd;
    }

    //根据userId得到人员
    public DpOm get(Integer userId){

        if (dpOmMapper == null) return null;
        DpOmExample example = new DpOmExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<DpOm> dpOms = dpOmMapper.selectByExample(example);
        if (dpOms.size() == 1){
            for (DpOm dpOm : dpOms){
                return dpOm;
            }
        }

        return null;
    }

    @Transactional
    public void insertSelective(DpOm record){

        Integer userId = record.getUserId();
        record.setSortOrder(getNextSortOrder("dp_om", null));
        dpOmMapper.insertSelective(record);

        dpCommonService.updateMemberRole(userId);
    }

    @Transactional
    public void del(Integer id){

        dpOmMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DpOmExample example = new DpOmExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        List<DpOm> dpOmList = dpOmMapper.selectByExample(example);
        dpOmMapper.deleteByExample(example);

        for (DpOm dpOm : dpOmList) {
            dpCommonService.updateMemberRole(dpOm.getUserId());
        }
    }

    @Transactional
    public void updateByPrimaryKeySelective(DpOm record){

        Integer userId = record.getUserId();
        dpOmMapper.updateByPrimaryKeySelective(record);

        dpCommonService.updateMemberRole(userId);
    }

    public Map<Integer, DpOm> findAll() {

        DpOmExample example = new DpOmExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<DpOm> records = dpOmMapper.selectByExample(example);
        Map<Integer, DpOm> map = new LinkedHashMap<>();
        for (DpOm record : records) {
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
    public void changeOrder(int id, int addNum) {

        changeOrder("dp_om", null, ORDER_BY_DESC, id, addNum);
    }

    @Transactional
    public void cancel(Integer[] ids, String transferTime) {

        DpOm dpOm = new DpOm();
        dpOm.setIsDeleted(true);
        dpOm.setTransferTime(DateUtils.parseStringToDate(transferTime));

        DpOmExample example = new DpOmExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        List<DpOm> dpOmList = dpOmMapper.selectByExample(example);
        dpOmMapper.updateByExampleSelective(dpOm, example);

        for (DpOm om : dpOmList) {
            dpCommonService.updateMemberRole(om.getUserId());
        }
    }
}
