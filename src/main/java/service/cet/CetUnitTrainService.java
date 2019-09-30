package service.cet;

import bean.UserBean;
import controller.global.OpException;
import domain.cadre.CadreView;
import domain.cet.CetTraineeType;
import domain.cet.CetUnitProject;
import domain.cet.CetUnitTrain;
import domain.cet.CetUnitTrainExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.LoginUserService;
import service.sys.SysUserService;
import service.sys.UserBeanService;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.tags.CmTag;

import java.util.*;

@Service
public class CetUnitTrainService extends CetBaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private UserBeanService userBeanService;
    @Autowired
    private CetTraineeTypeService cetTraineeTypeService;

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
        updateTotalCount(record.getProjectId());
    }


    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        Set<Integer> projectIdSet = new HashSet<>();
        if (ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN)) {

            List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
            for (Integer id : ids) {

                CetUnitTrain cetUnitTrain = cetUnitTrainMapper.selectByPrimaryKey(id);
                int projectId = cetUnitTrain.getProjectId();
                projectIdSet.add(projectId);
                CetUnitProject cetUnitProject = cetUnitProjectMapper.selectByPrimaryKey(projectId);
                if (!adminPartyIdList.contains(cetUnitProject.getPartyId())) {
                    throw new OpException("没有权限。");
                }

            }
        }

        CetUnitTrainExample example = new CetUnitTrainExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetUnitTrainMapper.deleteByExample(example);

        for (Integer projectId : projectIdSet) {
            updateTotalCount(projectId);
        }
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetUnitTrain record) {

        CetUnitTrain cetUnitTrain = cetUnitTrainMapper.selectByPrimaryKey(record.getId());
        CetUnitProject cetUnitProject = cetUnitProjectMapper.selectByPrimaryKey(cetUnitTrain.getProjectId());
        if (ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN)) {
            List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
            if (!adminPartyIdList.contains(cetUnitProject.getPartyId())) {
                throw new OpException("没有权限。");
            }
        }

        record.setProjectId(null);
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
    public void batchAdd(int projectId, int traineeTypeId, Integer[] userIds) {

        CetUnitProject cetUnitProject = cetUnitProjectMapper.selectByPrimaryKey(projectId);
        if (ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN)) {
            List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
            if (!adminPartyIdList.contains(cetUnitProject.getPartyId())) {
                throw new OpException("没有权限。");
            }
        }

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        Date now = new Date();
        for (Integer userId : userIds) {

            if (get(projectId, userId) != null) continue;
            CetUnitTrain record = new CetUnitTrain();
            record.setProjectId(projectId);
            record.setTraineeTypeId(traineeTypeId);
            record.setUserId(userId);
            CadreView cv = CmTag.getCadreByUserId(userId);
            if (cv != null) {
                record.setTitle(cv.getTitle());
                record.setPostType(cv.getPostType());
            } else {
                UserBean userBean = userBeanService.get(userId);
                if (userBean != null) {
                    record.setTitle(userBean.getUnit());
                }
            }
            record.setPeriod(cetUnitProject.getPeriod());
            record.setAddUserId(currentUserId);
            record.setAddTime(now);

            cetUnitTrainMapper.insertSelective(record);
        }

        updateTotalCount(projectId);
    }

    @Transactional
    public Map<String, Object> imports(List<Map<Integer, String>> xlsRows, int projectId) {

        CetUnitProject cetUnitProject = cetUnitProjectMapper.selectByPrimaryKey(projectId);

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        Date now = new Date();

        Map<String, Integer> _traineeTypeMap = new HashMap<>();
        Map<Integer, CetTraineeType> traineeTypeMap = cetTraineeTypeService.findAll();
        for (CetTraineeType cetTraineeType : traineeTypeMap.values()) {
            _traineeTypeMap.put(cetTraineeType.getName(), cetTraineeType.getId());
        }

        int success = 0;
        List<Map<Integer, String>> failedXlsRows = new ArrayList<>();
        for (Map<Integer, String> xlsRow : xlsRows) {

            CetUnitTrain record = new CetUnitTrain();

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

            String _traineeType = StringUtils.trim(xlsRow.get(2));
            if (StringUtils.isBlank(_traineeType)) continue;
            Integer traineeTypeId = _traineeTypeMap.get(_traineeType);
            record.setTraineeTypeId(traineeTypeId);

            record.setProjectId(projectId);
            record.setUserId(userId);
            CadreView cv = CmTag.getCadreByUserId(userId);
            if (cv != null) {
                record.setTitle(cv.getTitle());
                record.setPostType(cv.getPostType());
            } else {
                UserBean userBean = userBeanService.get(userId);
                if (userBean != null) {
                    record.setTitle(userBean.getUnit());
                }
            }
            record.setPeriod(cetUnitProject.getPeriod());
            record.setAddUserId(currentUserId);
            record.setAddTime(now);

            cetUnitTrainMapper.insertSelective(record);
            success++;
        }

        updateTotalCount(projectId);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", success);
        resultMap.put("failedXlsRows", failedXlsRows);
        return resultMap;
    }

    // 更新参训人数量
    public void updateTotalCount(int projectId){

        CetUnitTrainExample example = new CetUnitTrainExample();
        example.createCriteria().andProjectIdEqualTo(projectId);
        int totalCount = (int) cetUnitTrainMapper.countByExample(example);

        CetUnitProject record = new CetUnitProject();
        record.setId(projectId);
        record.setTotalCount(totalCount);

        cetUnitProjectMapper.updateByPrimaryKeySelective(record);
    }
}
