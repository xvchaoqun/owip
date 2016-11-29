package service.modify;

import domain.modify.ModifyBaseApply;
import domain.modify.ModifyBaseApplyExample;
import domain.modify.ModifyBaseItem;
import domain.modify.ModifyBaseItemExample;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.helper.ContextHelper;
import service.helper.ShiroSecurityHelper;
import sys.constants.SystemConstants;
import sys.utils.IpUtils;

import java.util.Arrays;
import java.util.Date;

@Service
public class ModifyBaseItemService extends BaseMapper {

    // 更新申请变更的值
    @Transactional
    public void update(int id, String modifyValue) {

        ModifyBaseItem record = new ModifyBaseItem();
        record.setModifyValue(modifyValue);
        record.setCreateTime(new Date());
        record.setIp(IpUtils.getRealIp(ContextHelper.getRequest()));

        ModifyBaseItemExample example = new ModifyBaseItemExample();
        example.createCriteria().andIdEqualTo(id)
                .andStatusEqualTo(SystemConstants.MODIFY_BASE_ITEM_STATUS_APPLY); // 只有待审批的记录可以更新

        modifyBaseItemMapper.updateByExampleSelective(record, example);
    }

    // 更新申请变更的值
    @Transactional
    public void approval(int id, Boolean status, String checkRemark, String checkReason) {

        ModifyBaseItem mbi = modifyBaseItemMapper.selectByPrimaryKey(id);
        if(mbi==null) return;
        int applyId = mbi.getApplyId();
        ModifyBaseApply mba = modifyBaseApplyMapper.selectByPrimaryKey(applyId);
        if(mba==null) return;

        String ip = IpUtils.getRealIp(ContextHelper.getRequest());
        { // 先审核
            ModifyBaseItem record = new ModifyBaseItem();
            record.setId(id);
            record.setStatus(BooleanUtils.isTrue(status) ? SystemConstants.MODIFY_BASE_ITEM_STATUS_PASS :
                    SystemConstants.MODIFY_BASE_ITEM_STATUS_DENY);
            record.setCheckRemark(checkRemark);
            record.setCheckReason(checkReason);
            record.setCheckTime(new Date());
            record.setCheckIp(ip);

            modifyBaseItemMapper.updateByPrimaryKey(record);
        }

        {// 实际去更新值！！！！！！！


        }

        { // 更新申请记录的审核状态
            int applyCount = 0; // 未审核数量
            int checkCount = 0; // 已审核数量
            {
                ModifyBaseItemExample example = new ModifyBaseItemExample();
                example.createCriteria().andApplyIdEqualTo(applyId)
                        .andStatusEqualTo(SystemConstants.MODIFY_BASE_ITEM_STATUS_APPLY);
                applyCount = modifyBaseItemMapper.countByExample(example);
            }
            {
                ModifyBaseItemExample example = new ModifyBaseItemExample();
                example.createCriteria().andApplyIdEqualTo(applyId)
                        .andStatusNotEqualTo(SystemConstants.MODIFY_BASE_ITEM_STATUS_APPLY);
                checkCount = modifyBaseItemMapper.countByExample(example);
            }
            Assert.isTrue(checkCount > 0); // 刚才审核了一个，肯定大于0
            {
                ModifyBaseApply record = new ModifyBaseApply();
                record.setId(mba.getId());
                if (applyCount == 0)
                    record.setStatus(SystemConstants.MODIFY_BASE_APPLY_STATUS_ALL_CHECK);
                if (applyCount > 0)
                    record.setStatus(SystemConstants.MODIFY_BASE_APPLY_STATUS_PART_CHECK);
                record.setCheckTime(new Date());
                record.setCheckIp(ip);
            }
        }
    }
}
