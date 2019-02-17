package service.cet;

import controller.global.OpException;
import domain.cadre.CadreView;
import domain.cet.CetUnitProject;
import domain.cet.CetUnitTrain;
import domain.cet.CetUnitTrainExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.CetConstants;
import sys.constants.RoleConstants;
import sys.tags.CmTag;

import java.util.*;

@Service
public class CetUnitTrainService extends CetBaseMapper {
    
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CetUpperTrainAdminService cetUpperTrainAdminService;
    
    
    public boolean idDuplicate(Integer id, int projectId, int userId) {
        
        CetUnitTrainExample example = new CetUnitTrainExample();
        CetUnitTrainExample.Criteria criteria = example.createCriteria().andProjectIdEqualTo(projectId)
                .andUserIdEqualTo(userId);
        if (id != null) criteria.andIdNotEqualTo(id);
        
        return cetUnitTrainMapper.countByExample(example) > 0;
    }
    
    @Transactional
    public void insertSelective(CetUnitTrain record) {
        
        cetUnitTrainMapper.insertSelective(record);
    }
    
    
    @Transactional
    public void batchDel(Integer[] ids, byte addType) {
        
        if (ids == null || ids.length == 0) return;
        
        Set<Integer> adminUnitIds = cetUpperTrainAdminService.adminUnitIds(CetConstants.CET_UPPER_TRAIN_UNIT, addType);
        
        for (Integer id : ids) {
            
            CetUnitTrain cetUnitTrain = cetUnitTrainMapper.selectByPrimaryKey(id);
            CetUnitProject cetUnitProject = cetUnitProjectMapper.selectByPrimaryKey(cetUnitTrain.getProjectId());
            if (!adminUnitIds.contains(cetUnitProject.getUnitId())) {
                throw new OpException("没有权限。");
            }
            
            if (cetUnitTrain.getStatus() == CetConstants.CET_UPPER_TRAIN_STATUS_PASS
                    && ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN)) {
                
                throw new OpException("已审批通过的培训记录不允许删除。");
            }
        }
        
        CetUnitTrainExample example = new CetUnitTrainExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetUnitTrainMapper.deleteByExample(example);
    }
    
    @Transactional
    public int updateByPrimaryKeySelective(CetUnitTrain record) {
        
        CetUnitTrain cetUnitTrain = cetUnitTrainMapper.selectByPrimaryKey(record.getId());
        CetUnitProject cetUnitProject = cetUnitProjectMapper.selectByPrimaryKey(cetUnitTrain.getProjectId());
        Set<Integer> adminUnitIds = cetUpperTrainAdminService.adminUnitIds(CetConstants.CET_UPPER_TRAIN_UNIT, record.getAddType());
        if (!adminUnitIds.contains(cetUnitProject.getUnitId())) {
            throw new OpException("没有权限。");
        }
        
        if (cetUnitTrain.getStatus() == CetConstants.CET_UPPER_TRAIN_STATUS_PASS
                && ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN)) {
            
            throw new OpException("已审批通过的培训记录不允许修改。");
        }
        
        return cetUnitTrainMapper.updateByPrimaryKeySelective(record);
    }
    
    public CetUnitTrain get(int projectId, int userId) {
        
        CetUnitTrainExample example = new CetUnitTrainExample();
        example.createCriteria().andProjectIdEqualTo(projectId).andUserIdEqualTo(userId);
        List<CetUnitTrain> cetUnitTrains = cetUnitTrainMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return cetUnitTrains.size() == 1 ? cetUnitTrains.get(0) : null;
    }
    
    // 批量添加参训人员
    @Transactional
    public void batchAdd(int projectId, Integer[] userIds, byte addType) {
        
        CetUnitProject cetUnitProject = cetUnitProjectMapper.selectByPrimaryKey(projectId);
        
        Set<Integer> adminUnitIds = cetUpperTrainAdminService.adminUnitIds(CetConstants.CET_UPPER_TRAIN_UNIT, addType);
        if (!adminUnitIds.contains(cetUnitProject.getUnitId())) {
            throw new OpException("没有权限。");
        }
        
        Byte status = null;
        if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT) {
            status = CetConstants.CET_UPPER_TRAIN_STATUS_INIT;
        } else if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW) {
            status = CetConstants.CET_UPPER_TRAIN_STATUS_PASS;
        }
        
        Integer currentUserId = ShiroHelper.getCurrentUserId();
        Date now = new Date();
        for (Integer userId : userIds) {
            
            if (get(projectId, userId) != null) continue;
            
            CadreView cv = CmTag.getCadreByUserId(userId);
            
            CetUnitTrain record = new CetUnitTrain();
            record.setProjectId(projectId);
            record.setUserId(userId);
            record.setTitle(cv.getTitle());
            record.setPostType(cv.getPostType());
            record.setPeriod(cetUnitProject.getPeriod());
            record.setAddType(addType);
            record.setAddUserId(currentUserId);
            record.setAddTime(now);
            record.setStatus(status);
            
            cetUnitTrainMapper.insertSelective(record);
        }
        
    }
    
    @Transactional
    public Map<String, Object> imports(List<Map<Integer, String>> xlsRows, int projectId) {
        
        CetUnitProject cetUnitProject = cetUnitProjectMapper.selectByPrimaryKey(projectId);
        
        Integer currentUserId = ShiroHelper.getCurrentUserId();
        Date now = new Date();
        
        int success = 0;
        List<Map<Integer, String>> failedXlsRows = new ArrayList<>();
        for (Map<Integer, String> xlsRow : xlsRows) {
            
            String code = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(code)) continue;
            SysUserView uv = sysUserService.findByCode(code);
            if (uv == null) {
                failedXlsRows.add(xlsRow);
                continue;
            }
            int userId = uv.getId();
            CetUnitTrain cetUnitTrain = get(projectId, userId);
            if (cetUnitTrain != null) {
                failedXlsRows.add(xlsRow);
                continue;
            }
            
            CadreView cv = CmTag.getCadreByUserId(userId);
            
            CetUnitTrain record = new CetUnitTrain();
            record.setProjectId(projectId);
            record.setUserId(userId);
            record.setTitle(cv.getTitle());
            record.setPostType(cv.getPostType());
            record.setPeriod(cetUnitProject.getPeriod());
            record.setAddType(CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW);
            record.setAddUserId(currentUserId);
            record.setAddTime(now);
            record.setStatus(CetConstants.CET_UPPER_TRAIN_STATUS_PASS);
            
            cetUnitTrainMapper.insertSelective(record);
            success++;
        }
        
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", success);
        resultMap.put("failedXlsRows", failedXlsRows);
        return resultMap;
    }
}
