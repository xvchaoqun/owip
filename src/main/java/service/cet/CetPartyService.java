package service.cet;

import domain.cet.CetParty;
import domain.cet.CetPartyExample;
import domain.cet.CetPartyView;
import domain.cet.CetPartyViewExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysUserService;
import sys.constants.RoleConstants;

import java.util.Arrays;
import java.util.List;

@Service
public class CetPartyService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;

    public boolean idDuplicate(Integer id, int partyId){

        CetPartyExample example = new CetPartyExample();
        CetPartyExample.Criteria criteria = example.createCriteria().andPartyIdEqualTo(partyId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetPartyMapper.countByExample(example) > 0;
    }

    public CetPartyView getView(int id) {

        CetPartyViewExample example = new CetPartyViewExample();
        example.createCriteria().andIdEqualTo(id);
        List<CetPartyView> cetPartyViews = cetPartyViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return cetPartyViews.size() == 1 ? cetPartyViews.get(0) : null;
    }

    // 设置管理员（先删除原管理员，再添加新管理员）
    @Transactional
    public void setAdmin(int id, Integer userId) {

        CetParty cetParty = cetPartyMapper.selectByPrimaryKey(id);
        Integer oldUserId = cetParty.getUserId();
        if(oldUserId!=null){
            if(userId!=null && userId.intValue()==oldUserId) return ;

            commonMapper.excuteSql("update cet_party set user_id=null where id=" + id);
            // 删除原管理员权限
            sysUserService.delRole(oldUserId, RoleConstants.ROLE_CET_ADMIN_PARTY);
        }

        if(userId!=null){

            CetParty record = new CetParty();
            record.setId(id);
            record.setUserId(userId);
            cetPartyMapper.updateByPrimaryKeySelective(record);

            // 添加管理员权限
            sysUserService.addRole(userId, RoleConstants.ROLE_CET_ADMIN_PARTY);
        }
    }

    @Transactional
    public void insertSelective(CetParty record){

        record.setUserId(null);
        cetPartyMapper.insertSelective(record);
    }


    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        {
            CetPartyExample example = new CetPartyExample();
            example.createCriteria().andIdIn(Arrays.asList(ids)).andUserIdIsNotNull();
            List<CetParty> cetPartys = cetPartyMapper.selectByExample(example);
            for (CetParty cetParty : cetPartys) {

                // 删除管理员权限
                sysUserService.delRole(cetParty.getUserId(), RoleConstants.ROLE_CET_ADMIN_PARTY);
            }
        }

        CetPartyExample example = new CetPartyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetPartyMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetParty record){

        record.setUserId(null);
        return cetPartyMapper.updateByPrimaryKeySelective(record);
    }


}
