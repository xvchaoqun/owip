package service.cadre;

import domain.cadre.CadreBookExample;
import domain.cadre.CadreCompanyExample;
import domain.cadre.CadreCourseExample;
import domain.cadre.CadreFamliyAbroadExample;
import domain.cadre.CadreInfoCheck;
import domain.cadre.CadrePaperExample;
import domain.cadre.CadreParttimeExample;
import domain.cadre.CadrePostAdminExample;
import domain.cadre.CadrePostProExample;
import domain.cadre.CadrePostWorkExample;
import domain.cadre.CadreResearchExample;
import domain.cadre.CadreRewardExample;
import domain.cadre.CadreTrainExample;
import domain.cadre.CadreView;
import domain.sys.SysUserInfo;
import domain.sys.TeacherInfo;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.common.bean.ICadreInfoCheck;
import service.BaseMapper;
import sys.constants.SystemConstants;

@Service
public class CadreInfoCheckService extends BaseMapper {

    @Autowired
    private CadreEduService cadreEduService;

    // 基本信息
    public byte baseCheck(int cadreId, String name) {

        CadreView cv = cadreViewMapper.selectByPrimaryKey(cadreId);
        int userId = cv.getUserId();

        String tableName = "sys_user_info";
        int count = iModifyMapper.baseModifyApplyCount(userId, tableName, name);
        if (count > 0) return SystemConstants.CADRE_INFO_CHECK_RESULT_MODIFY;

        boolean exist = false;
        SysUserInfo ui = sysUserInfoMapper.selectByPrimaryKey(userId);
        switch (name) {
            case "avatar":
                exist = StringUtils.isNotBlank(ui.getAvatar());
                break;
            case "native_place":
                exist = StringUtils.isNotBlank(ui.getNativePlace());
                break;
            case "homeplace":
                exist = StringUtils.isNotBlank(ui.getHomeplace());
                break;
            case "household":
                exist = StringUtils.isNotBlank(ui.getHousehold());
                break;
            case "health":
                exist = (ui.getHealth() != null && ui.getHealth() > 0);
                break;
            case "specialty":
                exist = StringUtils.isNotBlank(ui.getSpecialty());
                break;
            case "mobile":
                exist = StringUtils.isNotBlank(ui.getMobile());
                break;
            case "phone":
                exist = StringUtils.isNotBlank(ui.getPhone());
                break;
            case "email":
                exist = StringUtils.isNotBlank(ui.getEmail());
                break;
        }

        return exist ? SystemConstants.CADRE_INFO_CHECK_RESULT_EXIST
                : SystemConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST;
    }

    // 人事信息
    public byte staffCheck(int cadreId, String name) {

        CadreView cv = cadreViewMapper.selectByPrimaryKey(cadreId);
        int userId = cv.getUserId();

        String tableName = "sys_teacher_info";
        int count = iModifyMapper.baseModifyApplyCount(userId, tableName, name);
        if (count > 0) return SystemConstants.CADRE_INFO_CHECK_RESULT_MODIFY;

        boolean exist = false;
        TeacherInfo ti = teacherInfoMapper.selectByPrimaryKey(userId);
        switch (name) {
            case "work_time":
                exist = (ti.getWorkTime() != null);
                break;
        }

        return exist ? SystemConstants.CADRE_INFO_CHECK_RESULT_EXIST
                : SystemConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST;
    }

    // 最高学历
    public Byte cadreHighEduCheck(int cadreId) {

        if (cadreEduService.hasHighEdu(null, cadreId, true, SystemConstants.RECORD_STATUS_MODIFY))
            return SystemConstants.CADRE_INFO_CHECK_RESULT_MODIFY;
        if (cadreEduService.hasHighEdu(null, cadreId, true, SystemConstants.RECORD_STATUS_FORMAL))
            return SystemConstants.CADRE_INFO_CHECK_RESULT_EXIST;
        else
            return SystemConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST;
    }

    // 最高学位
    public Byte cadreHighDegreeCheck(int cadreId) {

        if (cadreEduService.hasHighDegree(null, cadreId, true, SystemConstants.RECORD_STATUS_MODIFY))
            return SystemConstants.CADRE_INFO_CHECK_RESULT_MODIFY;
        if (cadreEduService.hasHighDegree(null, cadreId, true, SystemConstants.RECORD_STATUS_FORMAL))
            return SystemConstants.CADRE_INFO_CHECK_RESULT_EXIST;
        else
            return SystemConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST;
    }

    // 干部科研项目信息检查
    public byte cadreResearchCheck(int cadreId, byte researchType) {

        ICadreInfoCheck iCadreInfoCheck = iModifyMapper.cadreResearchCheck(cadreId, researchType);
        if (iCadreInfoCheck!=null && iCadreInfoCheck.getModifyCount()!=null && iCadreInfoCheck.getModifyCount() > 0) return SystemConstants.CADRE_INFO_CHECK_RESULT_MODIFY;

        return (iCadreInfoCheck!=null && iCadreInfoCheck.getFormalCount()!=null && iCadreInfoCheck.getFormalCount() > 0) ? SystemConstants.CADRE_INFO_CHECK_RESULT_EXIST
                : SystemConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST;
    }

    // 干部奖励信息检查
    public byte cadreRewardCheck(int cadreId, byte rewardType) {

        ICadreInfoCheck iCadreInfoCheck = iModifyMapper.cadreRewardCheck(cadreId, rewardType);
        if (iCadreInfoCheck!=null && iCadreInfoCheck.getModifyCount()!=null && iCadreInfoCheck.getModifyCount() > 0) return SystemConstants.CADRE_INFO_CHECK_RESULT_MODIFY;

        return (iCadreInfoCheck!=null && iCadreInfoCheck.getFormalCount()!=null && iCadreInfoCheck.getFormalCount() > 0) ? SystemConstants.CADRE_INFO_CHECK_RESULT_EXIST
                : SystemConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST;
    }

    // 除以上之外的干部信息检查
    public byte cadreInfoModifyCheck(int cadreId, String name) {

        ICadreInfoCheck iCadreInfoCheck = iModifyMapper.cadreInfoModifyCheck(cadreId, name);
        if (iCadreInfoCheck!=null && iCadreInfoCheck.getModifyCount()!=null && iCadreInfoCheck.getModifyCount() > 0) return SystemConstants.CADRE_INFO_CHECK_RESULT_MODIFY;

        return (iCadreInfoCheck!=null && iCadreInfoCheck.getFormalCount()!=null && iCadreInfoCheck.getFormalCount() > 0) ? SystemConstants.CADRE_INFO_CHECK_RESULT_EXIST
                : SystemConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST;
    }

    // 除以上之外的干部信息检查（没有修改申请的表）
    public byte cadreInfoExistCheck(int cadreId, String name) {

        ICadreInfoCheck iCadreInfoCheck = iModifyMapper.cadreInfoExistCheck(cadreId, name);

        return  (iCadreInfoCheck!=null && iCadreInfoCheck.getFormalCount()!=null && iCadreInfoCheck.getFormalCount() > 0) ? SystemConstants.CADRE_INFO_CHECK_RESULT_EXIST
                : SystemConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST;
    }


    // 必须相应模块的记录数为0， 才可以更新“无此记录”
    public boolean canUpdateInfoCheck(int cadreId, String name) {

        if (StringUtils.isBlank(name)) return false;

        int count = 0;
        switch (name) {
            case "post_pro": {
                CadrePostProExample example = new CadrePostProExample();
                example.createCriteria().andCadreIdEqualTo(cadreId);
                count = cadrePostProMapper.countByExample(example);
            }
            break;
            case "post_admin": {
                CadrePostAdminExample example = new CadrePostAdminExample();
                example.createCriteria().andCadreIdEqualTo(cadreId);
                count = cadrePostAdminMapper.countByExample(example);
            }
            break;
            case "post_work": {
                CadrePostWorkExample example = new CadrePostWorkExample();
                example.createCriteria().andCadreIdEqualTo(cadreId);
                count = cadrePostWorkMapper.countByExample(example);
            }
            break;
            case "parttime": {
                CadreParttimeExample example = new CadreParttimeExample();
                example.createCriteria().andCadreIdEqualTo(cadreId).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                count = cadreParttimeMapper.countByExample(example);
            }
            break;
            case "train": {
                CadreTrainExample example = new CadreTrainExample();
                example.createCriteria().andCadreIdEqualTo(cadreId).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                count = cadreTrainMapper.countByExample(example);
            }
            break;
            case "course": {
                CadreCourseExample example = new CadreCourseExample();
                example.createCriteria().andCadreIdEqualTo(cadreId).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                count = cadreCourseMapper.countByExample(example);
            }
            break;
            case "course_reward": {
                CadreRewardExample example = new CadreRewardExample();
                example.createCriteria().andCadreIdEqualTo(cadreId)
                        .andRewardTypeEqualTo(SystemConstants.CADRE_REWARD_TYPE_TEACH)
                        .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                count = cadreRewardMapper.countByExample(example);
            }
            break;
            case "research_direct": {
                CadreResearchExample example = new CadreResearchExample();
                example.createCriteria().andCadreIdEqualTo(cadreId)
                        .andResearchTypeEqualTo(SystemConstants.CADRE_RESEARCH_TYPE_DIRECT)
                        .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                count = cadreResearchMapper.countByExample(example);
            }
            break;
            case "research_in": {
                CadreResearchExample example = new CadreResearchExample();
                example.createCriteria().andCadreIdEqualTo(cadreId)
                        .andResearchTypeEqualTo(SystemConstants.CADRE_RESEARCH_TYPE_IN)
                        .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                count = cadreResearchMapper.countByExample(example);
            }
            break;
            case "book": {
                CadreBookExample example = new CadreBookExample();
                example.createCriteria().andCadreIdEqualTo(cadreId).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                count = cadreBookMapper.countByExample(example);
            }
            break;
            case "paper": {
                CadrePaperExample example = new CadrePaperExample();
                example.createCriteria().andCadreIdEqualTo(cadreId).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                count = (int) cadrePaperMapper.countByExample(example);
            }
            break;
            case "research_reward": {
                CadreRewardExample example = new CadreRewardExample();
                example.createCriteria().andCadreIdEqualTo(cadreId)
                        .andRewardTypeEqualTo(SystemConstants.CADRE_REWARD_TYPE_RESEARCH)
                        .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                count = cadreRewardMapper.countByExample(example);
            }
            break;
            case "reward": {
                CadreRewardExample example = new CadreRewardExample();
                example.createCriteria().andCadreIdEqualTo(cadreId)
                        .andRewardTypeEqualTo(SystemConstants.CADRE_REWARD_TYPE_OTHER)
                        .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                count = cadreRewardMapper.countByExample(example);
            }
            break;
            case "famliy_abroad": {
                CadreFamliyAbroadExample example = new CadreFamliyAbroadExample();
                example.createCriteria().andCadreIdEqualTo(cadreId);
                count = cadreFamliyAbroadMapper.countByExample(example);
            }
            break;
            case "company": {
                CadreCompanyExample example = new CadreCompanyExample();
                example.createCriteria().andCadreIdEqualTo(cadreId).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                count = cadreCompanyMapper.countByExample(example);
            }
            break;
        }

        return count == 0;
    }

    public Boolean canUpdate(int cadreId, String name) {

        if (StringUtils.isBlank(name)) return false;

        return BooleanUtils.isNotTrue(iCadreMapper.cadreInfoCheck(cadreId, name));
    }

    @Transactional
    public void update(int cadreId, String name, boolean isChecked) {

        if (StringUtils.isBlank(name)) return;

        Assert.isTrue(canUpdateInfoCheck(cadreId, name), "当前不可更新");

        CadreInfoCheck cadreInfoCheck = cadreInfoCheckMapper.selectByPrimaryKey(cadreId);
        if (cadreInfoCheck == null) {
            iCadreMapper.cadreInfoCheckInsert(cadreId, name, isChecked);
        } else {
            iCadreMapper.cadreInfoCheckUpdate(cadreId, name, isChecked);
        }
    }
}
