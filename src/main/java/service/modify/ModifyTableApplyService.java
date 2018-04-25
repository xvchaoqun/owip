package service.modify;

import controller.global.OpException;
import domain.modify.ModifyTableApply;
import domain.modify.ModifyTableApplyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.SpringProps;
import service.cadre.CadreBookService;
import service.cadre.CadreCompanyService;
import service.cadre.CadreCourseService;
import service.cadre.CadreEduService;
import service.cadre.CadreFamilyAbroadService;
import service.cadre.CadreFamilyService;
import service.cadre.CadrePaperService;
import service.cadre.CadreParttimeService;
import service.cadre.CadrePostAdminService;
import service.cadre.CadrePostProService;
import service.cadre.CadrePostWorkService;
import service.cadre.CadreResearchService;
import service.cadre.CadreRewardService;
import service.cadre.CadreTrainService;
import service.cadre.CadreWorkService;
import service.sys.AvatarService;
import shiro.ShiroHelper;
import sys.constants.ModifyConstants;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.IpUtils;

import java.util.Arrays;
import java.util.Date;

@Service
public class ModifyTableApplyService extends BaseMapper {

    @Autowired
    protected SpringProps springProps;
    @Autowired
    protected AvatarService avatarService;
    @Autowired
    protected CadreEduService cadreEduService;
    @Autowired
    protected CadreCompanyService cadreCompanyService;
    @Autowired
    protected CadreRewardService cadreRewardService;
    @Autowired
    protected CadreBookService cadreBookService;
    @Autowired
    protected CadreResearchService cadreResearchService;
    @Autowired
    protected CadrePaperService cadrePaperService;
    @Autowired
    protected CadreParttimeService cadreParttimeService;
    @Autowired
    protected CadreTrainService cadreTrainService;
    @Autowired
    protected CadreCourseService cadreCourseService;
    @Autowired
    protected CadreWorkService cadreWorkService;
    @Autowired
    protected CadrePostProService cadrePostProService;
    @Autowired
    protected CadrePostAdminService cadrePostAdminService;
    @Autowired
    protected CadrePostWorkService cadrePostWorkService;
    @Autowired
    protected CadreFamilyService cadreFamilyService;
    @Autowired
    protected CadreFamilyAbroadService cadreFamilyAbroadService;


    // 本人删除（真删除）
    @Transactional
    public void back(Integer id) {

        if (id == null) return;

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(id);
        if (mta.getUserId().intValue() != currentUserId ||
                mta.getStatus() != ModifyConstants.MODIFY_TABLE_APPLY_STATUS_APPLY) {
            throw new OpException(String.format("您没有权限删除该记录[申请序号:%s]", id));
        }

        // 删除对应表中的数据
        String sql = "delete from " + mta.getTableName() + " where id=" + mta.getModifyId()
                + " and status=" + SystemConstants.RECORD_STATUS_MODIFY;
        commonMapper.excuteSql(sql);

        ModifyTableApplyExample example = new ModifyTableApplyExample();
        example.createCriteria().andIdEqualTo(id)
                .andStatusEqualTo(ModifyConstants.MODIFY_BASE_APPLY_STATUS_APPLY); // 只有待审核时才可以删除
        modifyTableApplyMapper.deleteByExample(example);
    }

    // 管理员删除（假删除）
    @Transactional
    public void fakeDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ModifyTableApplyExample example = new ModifyTableApplyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids))
                .andStatusEqualTo(ModifyConstants.MODIFY_BASE_APPLY_STATUS_APPLY); // 只有待审核时才可以操作
        ModifyTableApply record = new ModifyTableApply();

        record.setStatus(ModifyConstants.MODIFY_BASE_APPLY_STATUS_DELETE);
        modifyTableApplyMapper.updateByExampleSelective(record, example);
    }

    // 管理员删除（真删除）
    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ModifyTableApplyExample example = new ModifyTableApplyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        modifyTableApplyMapper.deleteByExample(example);
    }

    // 审核
    @Transactional
    public void approval(int id, Boolean status, String checkRemark, String checkReason) {

        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(id);

        ModifyTableApply record = new ModifyTableApply();
        record.setId(mta.getId());

        if (status) { // 审核通过，需要更新对应的信息
            switch (mta.getModule()) {
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_EDU:
                    cadreEduService.approval(mta, record);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_WORK:
                    cadreWorkService.approval(mta, record);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK:
                    cadreBookService.approval(mta, record);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY:
                    cadreCompanyService.approval(mta, record);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE:
                    cadreCourseService.approval(mta, record);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER:
                    cadrePaperService.approval(mta, record);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME:
                    cadreParttimeService.approval(mta, record);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT:
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN:
                    cadreResearchService.approval(mta, record);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH:
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH:
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER:
                    cadreRewardService.approval(mta, record);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN:
                    cadreTrainService.approval(mta, record);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_POSTPRO:
                    cadrePostProService.approval(mta, record);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_POSTADMIN:
                    cadrePostAdminService.approval(mta, record);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_POSTWORK:
                    cadrePostWorkService.approval(mta, record);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILY:
                    cadreFamilyService.approval(mta, record);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILYABROAD:
                    cadreFamilyAbroadService.approval(mta, record);
                    break;
                default:
                    throw new OpException("审核类型有误。");
            }
        }

        record.setStatus(status ? ModifyConstants.MODIFY_TABLE_APPLY_STATUS_PASS
                : ModifyConstants.MODIFY_TABLE_APPLY_STATUS_DENY);
        record.setCheckRemark(checkRemark);
        record.setCheckReason(checkReason);
        record.setCheckUserId(ShiroHelper.getCurrentUserId());
        record.setCheckTime(new Date());
        record.setCheckIp(IpUtils.getRealIp(ContextHelper.getRequest()));

        modifyTableApplyMapper.updateByPrimaryKeySelective(record);
    }
}
