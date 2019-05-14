package service.modify;

import domain.modify.ModifyBaseApply;
import domain.modify.ModifyBaseItem;
import domain.modify.ModifyBaseItemExample;
import domain.sys.SysUser;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.global.CacheHelper;
import shiro.ShiroHelper;
import sys.constants.ModifyConstants;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;
import sys.utils.IpUtils;

import java.util.Date;
import java.util.List;

@Service
public class ModifyBaseItemService extends BaseMapper {

    @Autowired
    private CacheHelper cacheHelper;

    // 查找当前申请的所有修改项
    public List<ModifyBaseItem> list(int applyId) {

        ModifyBaseItemExample example = new ModifyBaseItemExample();
        example.createCriteria().andApplyIdEqualTo(applyId);

        return modifyBaseItemMapper.selectByExample(example);
    }

    // 删除
    @Transactional
    public void del(int id) {

        ModifyBaseItemExample example = new ModifyBaseItemExample();
        example.createCriteria().andIdEqualTo(id)
                .andStatusEqualTo(ModifyConstants.MODIFY_BASE_ITEM_STATUS_APPLY); // 只有待审批的记录可以删除

        modifyBaseItemMapper.deleteByExample(example);
    }

    // 更新申请变更的值
    @Transactional
    public void update(int id, String modifyValue) {

        ModifyBaseItem record = new ModifyBaseItem();
        record.setModifyValue(modifyValue);
        record.setCreateTime(new Date());
        record.setIp(IpUtils.getRealIp(ContextHelper.getRequest()));

        ModifyBaseItemExample example = new ModifyBaseItemExample();
        example.createCriteria().andIdEqualTo(id)
                .andStatusEqualTo(ModifyConstants.MODIFY_BASE_ITEM_STATUS_APPLY); // 只有待审批的记录可以更新

        modifyBaseItemMapper.updateByExampleSelective(record, example);
    }

    // 更新申请变更的值
    @Transactional
    public void approval(int id, Boolean status, String checkRemark, String checkReason) {

        ModifyBaseItem mbi = modifyBaseItemMapper.selectByPrimaryKey(id);
        if (mbi == null) return;
        int applyId = mbi.getApplyId();
        ModifyBaseApply mba = modifyBaseApplyMapper.selectByPrimaryKey(applyId);
        if (mba == null) return;

        String tableName = mbi.getTableName();
        SysUser _sysUser = sysUserMapper.selectByPrimaryKey(mba.getUserId());

        String ip = IpUtils.getRealIp(ContextHelper.getRequest());
        { // 先审核
            ModifyBaseItem record = new ModifyBaseItem();
            record.setId(id);
            record.setStatus(BooleanUtils.isTrue(status) ? ModifyConstants.MODIFY_BASE_ITEM_STATUS_PASS :
                    ModifyConstants.MODIFY_BASE_ITEM_STATUS_DENY);
            record.setCheckRemark(checkRemark);
            record.setCheckReason(checkReason);
            record.setCheckUserId(ShiroHelper.getCurrentUserId());
            record.setCheckTime(new Date());
            record.setCheckIp(ip);

            modifyBaseItemMapper.updateByPrimaryKeySelective(record);
        }

        {
            if(BooleanUtils.isTrue(status) && StringUtils.isNotBlank(tableName)){

                if(StringUtils.equals("work_time", mbi.getCode())){
                    String modifyValue = mbi.getModifyValue();
                    if(modifyValue!=null){
                        Date date = DateUtils.parseDate(modifyValue, DateUtils.YYYYMM);
                        mbi.setModifyValue(DateUtils.formatDate(date, DateUtils.YYYY_MM_DD));
                    }
                }

                // 更新数据库内容，主键值必须是用户ID
                boolean needSingleQuotes = (mbi.getType()!=ModifyConstants.MODIFY_BASE_ITEM_TYPE_INT);
                String sql = "update " + tableName + " set " + mbi.getCode() + " = " + (needSingleQuotes?"'":"") +
                        StringEscapeUtils.escapeSql(mbi.getModifyValue().replace("\\", "\\\\")) + (needSingleQuotes ? "'" : "")
                        + " where "+ mbi.getTableIdName() + "=" + _sysUser.getId();
                commonMapper.excuteSql(sql);
            }
        }

        { // 更新申请记录的审核状态
            int applyCount = 0; // 未审核数量
            int checkCount = 0; // 已审核数量
            {
                ModifyBaseItemExample example = new ModifyBaseItemExample();
                example.createCriteria().andApplyIdEqualTo(applyId)
                        .andStatusEqualTo(ModifyConstants.MODIFY_BASE_ITEM_STATUS_APPLY);
                applyCount = modifyBaseItemMapper.countByExample(example);
            }
            {
                ModifyBaseItemExample example = new ModifyBaseItemExample();
                example.createCriteria().andApplyIdEqualTo(applyId)
                        .andStatusNotEqualTo(ModifyConstants.MODIFY_BASE_ITEM_STATUS_APPLY);
                checkCount = modifyBaseItemMapper.countByExample(example);
            }
            Assert.isTrue(checkCount > 0, "wrong check count"); // 刚才审核了一个，肯定大于0
            {
                ModifyBaseApply record = new ModifyBaseApply();
                record.setId(mba.getId());
                if (applyCount == 0)
                    record.setStatus(ModifyConstants.MODIFY_BASE_APPLY_STATUS_ALL_CHECK);
                if (applyCount > 0)
                    record.setStatus(ModifyConstants.MODIFY_BASE_APPLY_STATUS_PART_CHECK);
                record.setCheckTime(new Date());
                record.setCheckIp(ip);

                modifyBaseApplyMapper.updateByPrimaryKeySelective(record);
            }
        }

        // 没审核通过或者不需要更新数据的，则不更新缓存
        if(BooleanUtils.isNotTrue(status) || StringUtils.isBlank(tableName)) return;

        cacheHelper.clearUserCache(_sysUser);
        cacheHelper.clearCadreCache();
    }
}
