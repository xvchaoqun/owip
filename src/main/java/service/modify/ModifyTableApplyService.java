package service.modify;

import domain.modify.ModifyTableApply;
import domain.modify.ModifyTableApplyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.SpringProps;
import service.cadre.*;
import service.sys.AvatarService;
import shiro.ShiroHelper;
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


    // 本人删除（真删除）
    @Transactional
    public void back(Integer id) {

        if (id == null) return;

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(id);
        if (mta.getUserId().intValue() != currentUserId ||
                mta.getStatus() != SystemConstants.MODIFY_TABLE_APPLY_STATUS_APPLY) {
            throw new RuntimeException(String.format("您没有权限删除该记录[申请序号:%s]", id));
        }

        // 删除对应表中的数据
        String sql = "delete from " + mta.getTableName() + " where id=" + mta.getModifyId()
                + " and status=" + SystemConstants.RECORD_STATUS_MODIFY;
        commonMapper.excuteSql(sql);

        ModifyTableApplyExample example = new ModifyTableApplyExample();
        example.createCriteria().andIdEqualTo(id)
                .andStatusEqualTo(SystemConstants.MODIFY_BASE_APPLY_STATUS_APPLY); // 只有待审核时才可以删除
        modifyTableApplyMapper.deleteByExample(example);
    }

    // 管理员删除（假删除）
    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ModifyTableApplyExample example = new ModifyTableApplyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids))
                .andStatusEqualTo(SystemConstants.MODIFY_BASE_APPLY_STATUS_APPLY); // 只有待审核时才可以操作
        ModifyTableApply record = new ModifyTableApply();

        record.setStatus(SystemConstants.MODIFY_BASE_APPLY_STATUS_DELETE);
        modifyTableApplyMapper.updateByExampleSelective(record, example);
    }

    // 审核
    @Transactional
    public void approval(int id, Boolean status, String checkRemark, String checkReason) {

        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(id);

        ModifyTableApply record = new ModifyTableApply();
        record.setId(mta.getId());

        if (status) { // 审核通过，需要更新对应的信息
            switch (mta.getModule()) {
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_EDU:
                    cadreEduService.approval(mta, record);
                    break;
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_WORK:
                    cadreWorkService.approval(mta, record);
                    break;
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK:
                    cadreBookService.approval(mta, record);
                    break;
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY:
                    cadreCompanyService.approval(mta, record);
                    break;
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE:
                    cadreCourseService.approval(mta, record);
                    break;
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER:
                    cadrePaperService.approval(mta, record);
                    break;
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME:
                    cadreParttimeService.approval(mta, record);
                    break;
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT:
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN:
                    cadreResearchService.approval(mta, record);
                    break;
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH:
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH:
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER:
                    cadreRewardService.approval(mta, record);
                    break;
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN:
                    cadreTrainService.approval(mta, record);
                    break;
                default:
                    break;
            }
        }

        record.setStatus(status ? SystemConstants.MODIFY_TABLE_APPLY_STATUS_PASS
                : SystemConstants.MODIFY_TABLE_APPLY_STATUS_DENY);
        record.setCheckRemark(checkRemark);
        record.setCheckReason(checkReason);
        record.setCheckUserId(ShiroHelper.getCurrentUserId());
        record.setCheckTime(new Date());
        record.setCheckIp(IpUtils.getRealIp(ContextHelper.getRequest()));

        modifyTableApplyMapper.updateByPrimaryKeySelective(record);
    }
}
