package service.cet;

import controller.global.OpException;
import domain.cet.CetUnitProject;
import domain.cet.CetUnitTrainExample;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shiro.ShiroHelper;
import sys.constants.CetConstants;
import sys.constants.RoleConstants;

import java.util.Set;

@Service
public class CetUnitProjectService extends CetBaseMapper {
    
    @Autowired
    protected CetUpperTrainAdminService cetUpperTrainAdminService;
    
    @Transactional
    public void insertSelective(CetUnitProject record) {
        
        cetUnitProjectMapper.insertSelective(record);
    }
    
    @Transactional
    public void del(Integer id) {
        
        CetUnitProject oldRecord = cetUnitProjectMapper.selectByPrimaryKey(id);
        
        byte upperType = CetConstants.CET_UPPER_TRAIN_UNIT;
        byte addType = oldRecord.getAddType();
        Integer unitId = oldRecord.getUnitId();
        
        int currentUserId = ShiroHelper.getCurrentUserId();
        
        // 如果有审批通过的培训记录，则不允许删除
        {
            CetUnitTrainExample example = new CetUnitTrainExample();
            example.createCriteria().andProjectIdEqualTo(id).andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_PASS);
            if (cetUnitTrainMapper.countByExample(example) > 0) {
                throw new OpException("培训班中存在已审批通过的培训记录，不允许删除。");
            }
        }
        
        if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW) { // 组织部添加的只有组织部可以删除
            
            SecurityUtils.getSubject().checkPermission("cetUnitProject:del");
            
        } else if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT) { // 单位添加的组织部、本单位可以删除
            
            if (!ShiroHelper.isPermitted("cetUnitProject:del")) {
                
                SecurityUtils.getSubject().checkRole(RoleConstants.ROLE_CET_ADMIN_UNIT);
                
                Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIdSet(upperType, currentUserId);
                if (unitId == null || !adminUnitIdSet.contains(unitId)) {
                    throw new UnauthorizedException(); // 非单位管理员
                }
            }
        } else {
            throw new UnauthorizedException();
        }
        
        cetUnitProjectMapper.deleteByPrimaryKey(id);
    }
    
    @Transactional
    public void batchDel(Integer[] ids) {
        
        if (ids == null || ids.length == 0) return;
        
        for (Integer id : ids) {
            del(id);
        }
    }
    
    @Transactional
    public int updateByPrimaryKeySelective(CetUnitProject record) {
        return cetUnitProjectMapper.updateByPrimaryKeySelective(record);
    }
}
