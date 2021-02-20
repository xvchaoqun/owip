package service.dp;

import controller.global.OpException;
import domain.dp.DpMember;
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
import sys.constants.DpConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;

import java.util.*;

@Service
public class DpNpmService extends DpBaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private DpMemberService dpMemberService;

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

    //添加无党派和退出人士
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
        dpCommonService.updateMemberRole(userId);

        return isAdd;
    }

    @Transactional
    public void insertSelective(DpNpm record){

        int userId = record.getUserId();
        dpCommonService.findOrCreateCadre(userId);

        record.setSortOrder(getNextSortOrder("dp_npm", null));
        dpNpmMapper.insertSelective(record);

        dpCommonService.updateMemberRole(userId);
    }

    @Transactional
    public void del(Integer id){

        dpNpmMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DpNpmExample example = new DpNpmExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        List<DpNpm> dpNpmList = dpNpmMapper.selectByExample(example);
        dpNpmMapper.deleteByExample(example);

        for (DpNpm dpNpm : dpNpmList) {
            dpCommonService.updateMemberRole(dpNpm.getUserId());
        }
    }

    @Transactional
    public void updateByPrimaryKeySelective(DpNpm record){

        int userId = record.getUserId();
        dpCommonService.findOrCreateCadre(userId);
        
        dpNpmMapper.updateByPrimaryKeySelective(record);

        dpCommonService.updateMemberRole(userId);
    }

    @Transactional
    public void transferNpm(Integer[] ids, Integer partyId, String transferTime){

        Date _transferTime = DateUtils.parseStringToDate(transferTime);
        for (Integer id : ids) {

            Integer userId =  dpNpmMapper.selectByPrimaryKey(id).getUserId();

            dpNpmMapper.deleteByPrimaryKey(id);
            //转为某民主党派成员
            DpMember dpMemberAdd = dpMemberMapper.selectByPrimaryKey(userId);
            if (dpMemberAdd != null ){
                dpMemberAdd.setPartyId(partyId);
                dpMemberAdd.setSource(DpConstants.DP_MEMBER_SOURCE_NPM_TRAN);
                dpMemberAdd.setDpGrowTime(_transferTime);
                sysUserService.addRole(id, RoleConstants.ROLE_DP_MEMBER);

                dpMemberService.updateByPrimaryKeySelective(dpMemberAdd);
            }else {
                DpMember dpMember = new DpMember();
                dpMember.setUserId(userId);
                dpMember.setPartyId(partyId);
                dpMember.setType(DpConstants.DP_MEMBER_TYPE_TEACHER);
                dpMember.setStatus(DpConstants.DP_MEMBER_STATUS_NORMAL);
                dpMember.setSource(DpConstants.DP_MEMBER_SOURCE_NPM_TRAN);
                dpMember.setDpGrowTime(_transferTime);

                dpMemberService.add(dpMember);
            }
        }
    }

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
    public void changeOrder(int id, int addNum) {

        changeOrder("dp_npm", null, ORDER_BY_DESC, id, addNum);
    }
}
