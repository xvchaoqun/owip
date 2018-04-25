package service.cadre;

import domain.cadre.CadreBookExample;
import domain.cadre.CadreCompanyExample;
import domain.cadre.CadreCourseExample;
import domain.cadre.CadreFamilyAbroadExample;
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
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.cadre.common.ICadreInfoCheck;
import service.BaseMapper;
import sys.constants.CadreConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

@Service
public class CadreInfoCheckService extends BaseMapper {

    @Autowired
    private CadreEduService cadreEduService;

    // 完整性校验结果
    public static boolean perfectCadreInfo(int userId){

        SysUserView uv = CmTag.getUserById(userId);
        CadreView cv = CmTag.getCadreByUserId(userId);
        if(uv==null || cv==null) return false;

        int cadreId = cv.getId();
        if(notComplete(CmTag.cadreInfoCheck(cadreId, "avatar", 1))) return false;
        if(notComplete(CmTag.cadreInfoCheck(cadreId, "native_place", 1))) return false;
        if(notComplete(CmTag.cadreInfoCheck(cadreId, "homeplace", 1))) return false;
        if(notComplete(CmTag.cadreInfoCheck(cadreId, "household", 1))) return false;
        if(notComplete(CmTag.cadreInfoCheck(cadreId, "health", 1))) return false;
        if(notComplete(CmTag.cadreInfoCheck(cadreId, "specialty", 1))) return false;
        if(notComplete(CmTag.cadreInfoCheck(cadreId, "mobile", 1))) return false;
        if(notComplete(CmTag.cadreInfoCheck(cadreId, "phone", 1))) return false;
        if(notComplete(CmTag.cadreInfoCheck(cadreId, "email", 1))) return false;
        if(notComplete(CmTag.cadreInfoCheck(cadreId, "work_time", 2))) return false;

        //普通教师需要校验'所在单位及职务'
        if(CmTag.hasRole(uv.getUsername(), RoleConstants.ROLE_CADRERECRUIT)
                && notComplete(CmTag.cadreInfoCheck(cadreId, "title", 7))) return false;

        // 最高学历
        if(notComplete(CmTag.cadreInfoCheck(cadreId, null, 5))) return false;
        // 最高学位
        if(notComplete(CmTag.cadreInfoCheck(cadreId, null, 6))) return false;

        if(notComplete(CmTag.cadreInfoCheck(cadreId, "work", 3))) return false;
        if(notComplete(cadreId, "post_pro", 4)) return false;
        if(notComplete(cadreId, "post_admin", 4)) return false;
        if(notComplete(cadreId, "post_work", 4)) return false;

        if(notComplete(cadreId, "parttime", 3)) return false;
        if(notComplete(cadreId, "train", 3)) return false;
        if(notComplete(cadreId, "course", 3)) return false;

        if(notCompleteReward(cadreId, "course_reward")) return false;
        if(notCompleteResearch(cadreId, "research_direct")) return false;
        if(notCompleteResearch(cadreId, "research_in")) return false;
        if(notComplete(cadreId, "book", 3)) return false;
        if(notComplete(cadreId, "paper", 3)) return false;
        if(notCompleteReward(cadreId, "research_reward")) return false;
        if(notCompleteReward(cadreId, "reward")) return false;
        // 家庭信息
        if(notComplete(CmTag.cadreInfoCheck(cadreId, null, 8))) return false;
        if(notComplete(cadreId, "family_abroad", 4)) return false;
        if(notComplete(cadreId, "company", 4)) return false;

        return true;
    }

    private static boolean notComplete(Byte result){
        return(result==null || result == CadreConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST);
    }

    private static boolean notComplete(int cadreId, String updateName, int type){

        Byte result = CadreConstants.CADRE_INFO_CHECK_RESULT_EXIST;
        if(CmTag.canUpdate(cadreId, updateName)){
            result = CmTag.cadreInfoCheck(cadreId, updateName, type);
        }
        return notComplete(result);
    }

    private static boolean notCompleteReward(int cadreId, String updateName){

        Byte rewardType = null;
        switch (updateName){
            case "course_reward":
                rewardType = CadreConstants.CADRE_REWARD_TYPE_TEACH;
                break;
            case "research_reward":
                rewardType = CadreConstants.CADRE_REWARD_TYPE_RESEARCH;
                break;
            case "reward":
                rewardType = CadreConstants.CADRE_REWARD_TYPE_OTHER;
                break;
        }
        Byte result = CadreConstants.CADRE_INFO_CHECK_RESULT_EXIST;
        if(CmTag.canUpdate(cadreId, updateName)){
            result = CmTag.cadreRewardCheck(cadreId, rewardType);
        }

        return notComplete(result);
    }

    private static boolean notCompleteResearch(int cadreId, String updateName){

        Byte researchType = null;
        switch (updateName){
            case "research_direct":
                researchType = CadreConstants.CADRE_RESEARCH_TYPE_DIRECT;
                break;
            case "research_in":
                researchType = CadreConstants.CADRE_RESEARCH_TYPE_IN;
                break;
        }
        Byte result = CadreConstants.CADRE_INFO_CHECK_RESULT_EXIST;
        if(CmTag.canUpdate(cadreId, updateName)){
            result = CmTag.cadreResearchCheck(cadreId, researchType);
        }

        return notComplete(result);
    }

    // 基本信息
    public byte baseCheck(int cadreId, String name) {

        CadreView cv = cadreViewMapper.selectByPrimaryKey(cadreId);
        int userId = cv.getUserId();

        String tableName = "sys_user_info";
        int count = iModifyMapper.baseModifyApplyCount(userId, tableName, name);
        if (count > 0) return CadreConstants.CADRE_INFO_CHECK_RESULT_MODIFY;

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

        return exist ? CadreConstants.CADRE_INFO_CHECK_RESULT_EXIST
                : CadreConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST;
    }

    // 人事信息
    public byte staffCheck(int cadreId, String name) {

        CadreView cv = cadreViewMapper.selectByPrimaryKey(cadreId);
        int userId = cv.getUserId();

        String tableName = "sys_teacher_info";
        int count = iModifyMapper.baseModifyApplyCount(userId, tableName, name);
        if (count > 0) return CadreConstants.CADRE_INFO_CHECK_RESULT_MODIFY;

        boolean exist = false;
        TeacherInfo ti = teacherInfoMapper.selectByPrimaryKey(userId);
        switch (name) {
            case "work_time":
                exist = (ti!=null && ti.getWorkTime() != null);
                break;
        }

        return exist ? CadreConstants.CADRE_INFO_CHECK_RESULT_EXIST
                : CadreConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST;
    }

    // 干部信息
    public byte cadreCheck(int cadreId, String name) {

        CadreView cv = cadreViewMapper.selectByPrimaryKey(cadreId);
        int userId = cv.getUserId();

        boolean exist = false;
        switch (name) {
            case "title":
                exist = StringUtils.isNotBlank(cv.getTitle());
                break;
        }

        return exist ? CadreConstants.CADRE_INFO_CHECK_RESULT_EXIST
                : CadreConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST;
    }

    // 最高学历
    public Byte cadreHighEduCheck(int cadreId) {

        if (cadreEduService.hasHighEdu(null, cadreId, true, SystemConstants.RECORD_STATUS_MODIFY))
            return CadreConstants.CADRE_INFO_CHECK_RESULT_MODIFY;
        if (cadreEduService.hasHighEdu(null, cadreId, true, SystemConstants.RECORD_STATUS_FORMAL))
            return CadreConstants.CADRE_INFO_CHECK_RESULT_EXIST;
        else
            return CadreConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST;
    }

    // 最高学位
    public Byte cadreHighDegreeCheck(int cadreId) {

        if (cadreEduService.hasHighDegree(null, cadreId, true, SystemConstants.RECORD_STATUS_MODIFY))
            return CadreConstants.CADRE_INFO_CHECK_RESULT_MODIFY;
        if (cadreEduService.hasHighDegree(null, cadreId, true, SystemConstants.RECORD_STATUS_FORMAL))
            return CadreConstants.CADRE_INFO_CHECK_RESULT_EXIST;
        else
            return CadreConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST;
    }

    // 家庭信息
    public Byte familyCheck(int cadreId) {

        if(CmTag.cadreInfoCheck(cadreId, "family", 4)==CadreConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST){
            return CadreConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST;
        }
        ICadreInfoCheck iCadreInfoCheck = iModifyMapper.familyCheck(cadreId);
        if(iCadreInfoCheck!=null && iCadreInfoCheck.getFormalCount()!=null && iCadreInfoCheck.getFormalCount()>0)
            return CadreConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST;

        if(iCadreInfoCheck!=null && iCadreInfoCheck.getModifyCount()!=null && iCadreInfoCheck.getModifyCount()>0)
            return CadreConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST;

        return CadreConstants.CADRE_INFO_CHECK_RESULT_EXIST;
    }

    // 干部科研项目信息检查
    public byte cadreResearchCheck(int cadreId, byte researchType) {

        ICadreInfoCheck iCadreInfoCheck = iModifyMapper.cadreResearchCheck(cadreId, researchType);
        if (iCadreInfoCheck!=null && iCadreInfoCheck.getModifyCount()!=null && iCadreInfoCheck.getModifyCount() > 0) return CadreConstants.CADRE_INFO_CHECK_RESULT_MODIFY;

        return (iCadreInfoCheck!=null && iCadreInfoCheck.getFormalCount()!=null && iCadreInfoCheck.getFormalCount() > 0) ? CadreConstants.CADRE_INFO_CHECK_RESULT_EXIST
                : CadreConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST;
    }

    // 干部奖励信息检查
    public byte cadreRewardCheck(int cadreId, byte rewardType) {

        ICadreInfoCheck iCadreInfoCheck = iModifyMapper.cadreRewardCheck(cadreId, rewardType);
        if (iCadreInfoCheck!=null && iCadreInfoCheck.getModifyCount()!=null && iCadreInfoCheck.getModifyCount() > 0) return CadreConstants.CADRE_INFO_CHECK_RESULT_MODIFY;

        return (iCadreInfoCheck!=null && iCadreInfoCheck.getFormalCount()!=null && iCadreInfoCheck.getFormalCount() > 0) ? CadreConstants.CADRE_INFO_CHECK_RESULT_EXIST
                : CadreConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST;
    }

    // 除以上之外的干部信息检查
    public byte cadreInfoModifyCheck(int cadreId, String name) {

        ICadreInfoCheck iCadreInfoCheck = iModifyMapper.cadreInfoModifyCheck(cadreId, name);
        if (iCadreInfoCheck!=null && iCadreInfoCheck.getModifyCount()!=null && iCadreInfoCheck.getModifyCount() > 0) return CadreConstants.CADRE_INFO_CHECK_RESULT_MODIFY;

        return (iCadreInfoCheck!=null && iCadreInfoCheck.getFormalCount()!=null && iCadreInfoCheck.getFormalCount() > 0) ? CadreConstants.CADRE_INFO_CHECK_RESULT_EXIST
                : CadreConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST;
    }

    // 除以上之外的干部信息检查（没有修改申请的表）
    public byte cadreInfoExistCheck(int cadreId, String tableName) {

        ICadreInfoCheck iCadreInfoCheck = iModifyMapper.cadreInfoExistCheck(cadreId, tableName);

        return  (iCadreInfoCheck!=null && iCadreInfoCheck.getFormalCount()!=null && iCadreInfoCheck.getFormalCount() > 0) ? CadreConstants.CADRE_INFO_CHECK_RESULT_EXIST
                : CadreConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST;
    }


    // 必须相应模块的记录数为0， 才可以更新“无此记录”
    public boolean canUpdateInfoCheck(int cadreId, String name) {

        if (StringUtils.isBlank(name)) return false;

        long count = 0;
        switch (name) {
            case "post_pro": {
                CadrePostProExample example = new CadrePostProExample();
                example.createCriteria().andCadreIdEqualTo(cadreId)
                        .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                count = cadrePostProMapper.countByExample(example);
            }
            break;
            case "post_admin": {
                CadrePostAdminExample example = new CadrePostAdminExample();
                example.createCriteria().andCadreIdEqualTo(cadreId)
                        .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                count = cadrePostAdminMapper.countByExample(example);
            }
            break;
            case "post_work": {
                CadrePostWorkExample example = new CadrePostWorkExample();
                example.createCriteria().andCadreIdEqualTo(cadreId)
                        .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
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
                        .andRewardTypeEqualTo(CadreConstants.CADRE_REWARD_TYPE_TEACH)
                        .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                count = cadreRewardMapper.countByExample(example);
            }
            break;
            case "research_direct": {
                CadreResearchExample example = new CadreResearchExample();
                example.createCriteria().andCadreIdEqualTo(cadreId)
                        .andResearchTypeEqualTo(CadreConstants.CADRE_RESEARCH_TYPE_DIRECT)
                        .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                count = cadreResearchMapper.countByExample(example);
            }
            break;
            case "research_in": {
                CadreResearchExample example = new CadreResearchExample();
                example.createCriteria().andCadreIdEqualTo(cadreId)
                        .andResearchTypeEqualTo(CadreConstants.CADRE_RESEARCH_TYPE_IN)
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
                        .andRewardTypeEqualTo(CadreConstants.CADRE_REWARD_TYPE_RESEARCH)
                        .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                count = cadreRewardMapper.countByExample(example);
            }
            break;
            case "reward": {
                CadreRewardExample example = new CadreRewardExample();
                example.createCriteria().andCadreIdEqualTo(cadreId)
                        .andRewardTypeEqualTo(CadreConstants.CADRE_REWARD_TYPE_OTHER)
                        .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                count = cadreRewardMapper.countByExample(example);
            }
            break;
            case "family_abroad": {
                CadreFamilyAbroadExample example = new CadreFamilyAbroadExample();
                example.createCriteria().andCadreIdEqualTo(cadreId)
                        .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                count = cadreFamilyAbroadMapper.countByExample(example);
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
