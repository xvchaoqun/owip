package service.cet;

import bean.UserBean;
import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.cet.*;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.base.MetaTypeService;
import service.sys.SysUserService;
import service.sys.UserBeanService;
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
    private UserBeanService userBeanService;
    @Autowired
    private CetTraineeTypeService cetTraineeTypeService;
    @Autowired
    private MetaTypeService metaTypeService;

    public boolean idDuplicate(Integer id, int projectId, int userId) {

        CetUnitTrainExample example = new CetUnitTrainExample();
        CetUnitTrainExample.Criteria criteria = example.createCriteria().andProjectIdEqualTo(projectId)
                .andUserIdEqualTo(userId);
        if (id != null) criteria.andIdNotEqualTo(id);

        return cetUnitTrainMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CetUnitTrain record) {

        int userId = record.getUserId();
        CadreView cv = CmTag.getCadreByUserId(userId);
        if (cv != null) {
            if(StringUtils.isBlank(record.getTitle())) {
                record.setTitle(cv.getTitle());
            }
            if(record.getPostType()==null) {
                record.setPostType(cv.getPostType());
            }
        } else {
            UserBean userBean = userBeanService.get(userId);
            if (userBean != null && StringUtils.isBlank(record.getTitle())) {
                record.setTitle(userBean.getUnit());
            }
        }

        cetUnitTrainMapper.insertSelective(record);
        updateTotalCount(record.getProjectId());
    }


    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        Set<Integer> projectIdSet = new HashSet<>();
        if (!RoleConstants.isCetAdmin()) {

            List<Integer> adminPartyIdList = iCetMapper.getAdminPartyIds(ShiroHelper.getCurrentUserId());
            for (Integer id : ids) {

                CetUnitTrain cetUnitTrain = cetUnitTrainMapper.selectByPrimaryKey(id);
                int projectId = cetUnitTrain.getProjectId();
                projectIdSet.add(projectId);
                CetUnitProject cetUnitProject = cetUnitProjectMapper.selectByPrimaryKey(projectId);
                if (!adminPartyIdList.contains(cetUnitProject.getCetPartyId())) {
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
        if (!RoleConstants.isCetAdmin()) {
            List<Integer> adminPartyIdList = iCetMapper.getAdminPartyIds(ShiroHelper.getCurrentUserId());;
            if (!adminPartyIdList.contains(cetUnitProject.getCetPartyId())) {
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
            if(traineeTypeId != null) {
                record.setTraineeTypeId(traineeTypeId);
            }else{
                record.setTraineeTypeId(0);
                record.setOtherTraineeType(StringUtils.trim(xlsRow.get(2)));
            }
            String _identity = StringUtils.trimToNull(xlsRow.get(3));
            if (StringUtils.isNotBlank(_identity)) {
                String[] identities = _identity.split(",|，|、");
                List<Integer> identityList = new ArrayList<>();
                for (String s : identities) {
                    MetaType metaType1 = metaTypeService.findByName("mc_cet_identity", s);
                    if (metaType1 != null) {
                        identityList.add(metaType1.getId());
                    }
                }
                if(identityList.size()>0) {
                    record.setIdentity("," + StringUtils.join(identityList, ",") + ",");
                }
            }else {
                record.setIdentity(""); // 为了更新时覆盖
            }

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
        example.createCriteria().andProjectIdEqualTo(projectId).andStatusEqualTo(CetConstants.CET_UNITTRAIN_RERECORD_PASS);
        int totalCount = (int) cetUnitTrainMapper.countByExample(example);

        CetUnitProject record = new CetUnitProject();
        record.setId(projectId);
        record.setTotalCount(totalCount);

        cetUnitProjectMapper.updateByPrimaryKeySelective(record);
    }

    //参训人员删除补录信息
    @Transactional
    public void userBatchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;
        for (Integer id : ids) {
            cetUnitTrainMapper.deleteByPrimaryKey(id);
        }
    }

    @Transactional
    public void batchCheck(Integer[] ids, Boolean pass, String reason) {

        if (null != ids && ids.length > 0) {
            Integer projectId = null;
            for (Integer id : ids) {
                CetUnitTrain record = cetUnitTrainMapper.selectByPrimaryKey(id);
                projectId=record.getProjectId();
                if (pass) {
                    if(RoleConstants.isCetAdmin()) {
                        // 组织部审批
                        record.setStatus(CetConstants.CET_UNITTRAIN_RERECORD_PASS);
                    } else {
                        // 二级党委审批
                        record.setStatus(CetConstants.CET_UNITTRAIN_RERECORD_CET);
                    }
                }else {
                    record.setStatus(CetConstants.CET_UNITTRAIN_RERECORD_SAVE);
                }
                record.setReason(reason);
                cetUnitTrainMapper.updateByPrimaryKey(record);
            }
            updateTotalCount(projectId);
        }
    }

    public List<Integer> getProjectIds(Integer cetPartyId) {

        CetPartyExample cetPartyExample = new CetPartyExample();
        cetPartyExample.createCriteria().andIdEqualTo(cetPartyId);
        List<CetParty> cetParties = cetPartyMapper.selectByExample(cetPartyExample);
        if (cetParties != null && cetParties.size() > 0) {
            List<Integer> cetPartyIds = new ArrayList<>();
            for (CetParty cetParty : cetParties) {
                cetPartyIds.add(cetParty.getId());
            }
            CetUnitProjectExample projectExample = new CetUnitProjectExample();
            projectExample.createCriteria().andCetPartyIdIn(cetPartyIds);
            List<CetUnitProject> cetUnitProjects = cetUnitProjectMapper.selectByExample(projectExample);
            if (cetUnitProjects != null && cetUnitProjects.size() > 0){
                List<Integer> projectIds = new ArrayList<>();
                for (CetUnitProject cetUnitProject : cetUnitProjects) {
                    projectIds.add(cetUnitProject.getId());
                }
                return projectIds;
            }
        }
        return null;
    }
}
