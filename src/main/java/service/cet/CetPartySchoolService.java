package service.cet;

import domain.cet.CetPartySchool;
import domain.cet.CetPartySchoolExample;
import domain.cet.CetPartySchoolView;
import domain.cet.CetPartySchoolViewExample;
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
public class CetPartySchoolService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;

    public boolean idDuplicate(Integer id, int partySchoolId) {

        CetPartySchoolExample example = new CetPartySchoolExample();
        CetPartySchoolExample.Criteria criteria = example.createCriteria().andPartySchoolIdEqualTo(partySchoolId);
        if (id != null) criteria.andIdNotEqualTo(id);

        return cetPartySchoolMapper.countByExample(example) > 0;
    }

    public CetPartySchoolView getView(int id) {

        CetPartySchoolViewExample example = new CetPartySchoolViewExample();
        example.createCriteria().andIdEqualTo(id);
        List<CetPartySchoolView> cetPartySchoolViews = cetPartySchoolViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return cetPartySchoolViews.size() == 1 ? cetPartySchoolViews.get(0) : null;
    }

    // 设置管理员（先删除原管理员，再添加新管理员）
    @Transactional
    public void setAdmin(int id, Integer userId) {

        CetPartySchool cetPartySchool = cetPartySchoolMapper.selectByPrimaryKey(id);
        Integer oldUserId = cetPartySchool.getUserId();
        if(oldUserId!=null){
            if(userId!=null && userId.intValue()==oldUserId) return ;

            // 删除原管理员权限
            sysUserService.delRole(oldUserId, RoleConstants.ROLE_CET_ADMIN_PS);
        }

        if(userId!=null){

            CetPartySchool record = new CetPartySchool();
            record.setId(id);
            record.setUserId(userId);
            cetPartySchoolMapper.updateByPrimaryKeySelective(record);

            // 添加管理员权限
            sysUserService.addRole(userId, RoleConstants.ROLE_CET_ADMIN_PS);
        }
    }

    @Transactional
    public void insertSelective(CetPartySchool record) {

        record.setUserId(null);
        cetPartySchoolMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        {
            CetPartySchoolExample example = new CetPartySchoolExample();
            example.createCriteria().andIdIn(Arrays.asList(ids)).andUserIdIsNotNull();
            List<CetPartySchool> cetPartySchools = cetPartySchoolMapper.selectByExample(example);
            for (CetPartySchool cetPartySchool : cetPartySchools) {

                // 删除管理员权限
                sysUserService.delRole(cetPartySchool.getUserId(), RoleConstants.ROLE_CET_ADMIN_PS);
            }
        }

        CetPartySchoolExample example = new CetPartySchoolExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetPartySchoolMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetPartySchool record) {

        record.setUserId(null);
        return cetPartySchoolMapper.updateByPrimaryKeySelective(record);
    }
}
