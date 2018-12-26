package service.cet;

import domain.cet.CetUnit;
import domain.cet.CetUnitExample;
import domain.cet.CetUnitView;
import domain.cet.CetUnitViewExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sys.SysUserService;
import sys.constants.RoleConstants;

import java.util.Arrays;
import java.util.List;

@Service
public class CetUnitService extends CetBaseMapper {

    @Autowired
    private SysUserService sysUserService;

    public boolean idDuplicate(Integer id, int unitId){

        CetUnitExample example = new CetUnitExample();
        CetUnitExample.Criteria criteria = example.createCriteria().andUnitIdEqualTo(unitId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetUnitMapper.countByExample(example) > 0;
    }

    public CetUnitView getView(int id) {

        CetUnitViewExample example = new CetUnitViewExample();
        example.createCriteria().andIdEqualTo(id);
        List<CetUnitView> cetUnitViews = cetUnitViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return cetUnitViews.size() == 1 ? cetUnitViews.get(0) : null;
    }

    // 设置管理员（先删除原管理员，再添加新管理员）
    @Transactional
    public void setAdmin(int id, Integer userId) {

        CetUnit cetUnit = cetUnitMapper.selectByPrimaryKey(id);
        Integer oldUserId = cetUnit.getUserId();
        if(oldUserId!=null){
            if(userId!=null && userId.intValue()==oldUserId) return ;

            commonMapper.excuteSql("update cet_unit set user_id=null where id=" + id);
            // 删除原管理员权限
            sysUserService.delRole(oldUserId, RoleConstants.ROLE_CET_ADMIN_UPPER);
        }

        if(userId!=null){

            CetUnit record = new CetUnit();
            record.setId(id);
            record.setUserId(userId);
            cetUnitMapper.updateByPrimaryKeySelective(record);

            // 添加管理员权限
            sysUserService.addRole(userId, RoleConstants.ROLE_CET_ADMIN_UPPER);
        }
    }

    @Transactional
    public void insertSelective(CetUnit record){

        record.setUserId(null);
        cetUnitMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        {
            CetUnitExample example = new CetUnitExample();
            example.createCriteria().andIdIn(Arrays.asList(ids)).andUserIdIsNotNull();
            List<CetUnit> cetUnits = cetUnitMapper.selectByExample(example);
            for (CetUnit cetUnit : cetUnits) {

                // 删除管理员权限
                sysUserService.delRole(cetUnit.getUserId(), RoleConstants.ROLE_CET_ADMIN_UPPER);
            }
        }

        CetUnitExample example = new CetUnitExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetUnitMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetUnit record){

        record.setUserId(null);
        return cetUnitMapper.updateByPrimaryKeySelective(record);
    }
}
