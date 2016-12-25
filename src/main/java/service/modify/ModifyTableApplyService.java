package service.modify;

import domain.cadre.CadreEdu;
import domain.modify.ModifyTableApply;
import domain.modify.ModifyTableApplyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.SpringProps;
import service.cadre.CadreEduService;
import sys.utils.ContextHelper;
import shiro.ShiroHelper;
import service.sys.AvatarService;
import sys.constants.SystemConstants;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;

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
        updateMapper.excuteSql(sql);

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
        Integer originalId = mta.getOriginalId();
        Integer modifyId = mta.getModifyId();
        Byte type = mta.getType();

        ModifyTableApply record = new ModifyTableApply();
        record.setId(mta.getId());

        if (status) { // 审核通过，需要更新对应的信息
            switch (mta.getModule()) {
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_EDU: // 学习经历
                    if (type == SystemConstants.MODIFY_TABLE_APPLY_TYPE_ADD) {

                        CadreEdu modify = cadreEduMapper.selectByPrimaryKey(modifyId);
                        modify.setId(null);
                        modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

                        cadreEduService.checkUpdate(modify);

                        cadreEduMapper.insertSelective(modify); // 插入新纪录
                        record.setOriginalId(modify.getId()); // 添加申请，更新原纪录ID

                    } else if (type == SystemConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY) {

                        CadreEdu modify = cadreEduMapper.selectByPrimaryKey(modifyId);
                        modify.setId(originalId);
                        modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

                        cadreEduService.checkUpdate(modify);

                        cadreEduMapper.updateByPrimaryKey(modify); // 覆盖原纪录

                    } else if (type == SystemConstants.MODIFY_TABLE_APPLY_TYPE_DELETE) {

                        // 更新最后删除的记录内容
                        record.setOriginalJson(JSONUtils.toString(cadreEduMapper.selectByPrimaryKey(originalId), false));
                        // 删除原纪录
                        cadreEduMapper.deleteByPrimaryKey(originalId);
                    }
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
