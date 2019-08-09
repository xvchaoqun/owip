package service.modify;

import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.CadreParty;
import domain.cadre.CadrePartyExample;
import domain.member.Member;
import domain.member.MemberExample;
import domain.modify.ModifyBaseApply;
import domain.modify.ModifyBaseItem;
import domain.modify.ModifyBaseItemExample;
import domain.sys.SysUser;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.cadre.CadrePartyService;
import service.global.CacheHelper;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.HttpResponseMethod;
import sys.constants.*;
import sys.tags.CmTag;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ModifyBaseItemService extends BaseMapper implements HttpResponseMethod {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CacheHelper cacheHelper;
    @Autowired
    private CadrePartyService cadrePartyService;
    @Autowired
    private SysUserService sysUserService;

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
        String code = mbi.getCode();
        int userId = mba.getUserId();
        SysUser _sysUser = sysUserMapper.selectByPrimaryKey(userId);

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
            if (BooleanUtils.isTrue(status)) {

                if (StringUtils.equals(code, "political_status")) {

                    Map<Integer, MetaType> democraticPartyMap = CmTag.getMetaTypes("mc_democratic_party");

                    if(StringUtils.isBlank(mbi.getOrginalValue())
                            && StringUtils.isNotBlank(mbi.getModifyValue())){ // 无 -> 党派

                        int modifyValue = Integer.valueOf(mbi.getModifyValue());
                        if(modifyValue==0) {
                            // 无 -> 中共党员

                            // 只插入或更新 中共党员干部库，不管党员库
                            CadreParty _cadreParty = cadrePartyService.getOwOrFirstDp(userId, CadreConstants.CADRE_PARTY_TYPE_OW);
                            if (_cadreParty == null) {

                                CadreParty cadreParty = new CadreParty();
                                cadreParty.setUserId(userId);
                                cadreParty.setType(CadreConstants.CADRE_PARTY_TYPE_OW);
                                cadreParty.setRemark("本人修改申请（无 -> 中共党员）");

                                cadrePartyMapper.insertSelective(cadreParty);
                            } else {

                                _cadreParty.setRemark("本人修改申请（无 -> 中共党员）");
                                cadrePartyMapper.updateByPrimaryKeySelective(_cadreParty);
                            }
                        }else if(democraticPartyMap.containsKey(modifyValue)){
                            // 无 -> 民主党派
                            CadreParty _cadreParty = cadrePartyService.getOwOrFirstDp(userId, CadreConstants.CADRE_PARTY_TYPE_DP);
                            if (_cadreParty == null) {

                                CadreParty cadreParty = new CadreParty();
                                cadreParty.setUserId(userId);
                                cadreParty.setType(CadreConstants.CADRE_PARTY_TYPE_DP);
                                cadreParty.setClassId(modifyValue);
                                cadreParty.setRemark("本人修改申请（无 -> " + democraticPartyMap.get(modifyValue) + "）");
                                cadreParty.setIsFirst(true);

                                cadrePartyMapper.insertSelective(cadreParty);
                            } else {

                                _cadreParty.setClassId(modifyValue);
                                _cadreParty.setRemark("本人修改申请（无 -> " + democraticPartyMap.get(modifyValue) + "）");

                                cadrePartyMapper.updateByPrimaryKeySelective(_cadreParty);
                            }
                        }
                    }else if(StringUtils.isNotBlank(mbi.getOrginalValue())
                            && StringUtils.isBlank(mbi.getModifyValue())){  // 党派 -> 无

                        int orginalValue = Integer.valueOf(mbi.getOrginalValue());

                        if(orginalValue==0){
                            // 中共党员 -> 清空

                            // 可能在中共党员干部库
                            {
                                CadreParty owParty = cadrePartyService.getOwOrFirstDp(userId, CadreConstants.CADRE_PARTY_TYPE_OW);
                                if (owParty != null) {
                                    cadrePartyMapper.deleteByPrimaryKey(owParty.getId());

                                    logger.info(log(LogConstants.LOG_ADMIN,"审批修改党派申请，从党员干部库中删除党员{0}",
                                            JSONUtils.toString(owParty, false)));
                                }
                            }

                            // 也可能在党员库
                            {
                                Member _member = memberMapper.selectByPrimaryKey(userId);
                                if (_member != null) {
                                    memberMapper.deleteByPrimaryKey(userId);

                                    logger.info(log(LogConstants.LOG_ADMIN,"审批修改党派申请，从党员库中删除党员{0}",
                                            JSONUtils.toString(_member, false)));
                                }
                            }

                            // 删除党员身份
                            sysUserService.changeRole(userId, RoleConstants.ROLE_MEMBER, RoleConstants.ROLE_GUEST);
                        }else{
                            // 民主党派 -> 清空
                            CadreParty dpParty = cadrePartyService.getOwOrFirstDp(userId, CadreConstants.CADRE_PARTY_TYPE_DP);
                            cadrePartyMapper.deleteByPrimaryKey(dpParty.getId());

                            logger.info(log(LogConstants.LOG_ADMIN,"审批修改党派申请，删除民主党派{0}",
                                            JSONUtils.toString(dpParty, false)));
                        }

                    }else if(StringUtils.isNotBlank(mbi.getOrginalValue())
                            && StringUtils.isNotBlank(mbi.getModifyValue())) {  // 党派 -> 党派

                        int orginalValue = Integer.valueOf(mbi.getOrginalValue());
                        int modifyValue = Integer.valueOf(mbi.getModifyValue());

                        // 中共党员 -> 民主党派
                        if (orginalValue == 0 && democraticPartyMap.containsKey(modifyValue)) {

                            Date growTime = null; // 保留原来的党派加入时间

                            /** 先删除中共党员 **/

                            // 可能在中共党员干部库
                            {
                                CadreParty owParty = cadrePartyService.getOwOrFirstDp(userId, CadreConstants.CADRE_PARTY_TYPE_OW);
                                if (owParty != null) {
                                    growTime = owParty.getGrowTime();
                                    cadrePartyMapper.deleteByPrimaryKey(owParty.getId());
                                    logger.info(log(LogConstants.LOG_ADMIN,"审批修改党派申请，从党员干部库中删除党员{0}",
                                            JSONUtils.toString(owParty, false)));
                                }
                            }

                            // 也可能在党员库
                            {
                                Member _member = memberMapper.selectByPrimaryKey(userId);
                                if (_member != null) {

                                    growTime = _member.getGrowTime(); // 以党员库的时间为准
                                    memberMapper.deleteByPrimaryKey(userId);

                                    logger.info(log(LogConstants.LOG_ADMIN,"审批修改党派申请，从党员库中删除党员{0}",
                                            JSONUtils.toString(_member, false)));
                                }
                            }

                            // 删除党员身份
                            sysUserService.changeRole(userId, RoleConstants.ROLE_MEMBER, RoleConstants.ROLE_GUEST);

                            CadreParty _cadreParty = cadrePartyService.getOwOrFirstDp(userId, CadreConstants.CADRE_PARTY_TYPE_DP);
                            if (_cadreParty == null) {

                                CadreParty cadreParty = new CadreParty();
                                cadreParty.setUserId(userId);
                                cadreParty.setType(CadreConstants.CADRE_PARTY_TYPE_DP);
                                cadreParty.setGrowTime(growTime);
                                cadreParty.setClassId(modifyValue);
                                cadreParty.setRemark("本人修改申请（中共党员 -> " + democraticPartyMap.get(orginalValue) + "）");
                                cadreParty.setIsFirst(true);

                                cadrePartyMapper.insertSelective(cadreParty);
                            } else {

                                _cadreParty.setClassId(modifyValue);
                                _cadreParty.setGrowTime(growTime);
                                _cadreParty.setRemark("本人修改申请（中共党员 -> " + democraticPartyMap.get(orginalValue) + "）");

                                cadrePartyMapper.updateByPrimaryKeySelective(_cadreParty);
                            }

                        } else if (modifyValue == 0 && democraticPartyMap.containsKey(orginalValue)) {

                            // 民主党派 -> 中共党员
                            CadreParty dpParty = cadrePartyService.getOwOrFirstDp(userId, CadreConstants.CADRE_PARTY_TYPE_DP);

                            // 只插入或更新 中共党员干部库，不管党员库
                            CadreParty _cadreParty = cadrePartyService.getOwOrFirstDp(userId, CadreConstants.CADRE_PARTY_TYPE_OW);
                            if (_cadreParty == null) {

                                CadreParty cadreParty = new CadreParty();
                                cadreParty.setUserId(userId);
                                cadreParty.setGrowTime(dpParty.getGrowTime());
                                cadreParty.setType(CadreConstants.CADRE_PARTY_TYPE_OW);
                                cadreParty.setRemark("本人修改申请（" + democraticPartyMap.get(orginalValue) + " -> 中共党员）");

                                cadreParty.setIsFirst(false);
                                cadrePartyMapper.insertSelective(cadreParty);
                            } else {

                                _cadreParty.setGrowTime(dpParty.getGrowTime());
                                _cadreParty.setRemark("本人修改申请（" + democraticPartyMap.get(orginalValue) + " -> 中共党员）");
                                cadrePartyMapper.updateByPrimaryKeySelective(_cadreParty);
                            }

                            // 删除民主党派
                            cadrePartyMapper.deleteByPrimaryKey(dpParty.getId());

                        } else if (democraticPartyMap.containsKey(orginalValue)
                                && democraticPartyMap.containsKey(modifyValue)) {

                            // 民主党派 -> 民主党派
                            CadreParty _cadreParty = cadrePartyService.getOwOrFirstDp(userId, CadreConstants.CADRE_PARTY_TYPE_DP);
                            if (_cadreParty == null) {

                                CadreParty cadreParty = new CadreParty();
                                cadreParty.setUserId(userId);
                                cadreParty.setType(CadreConstants.CADRE_PARTY_TYPE_OW);
                                cadreParty.setClassId(modifyValue);
                                cadreParty.setRemark("本人修改申请（" + democraticPartyMap.get(orginalValue)
                                        + " -> " + democraticPartyMap.get(modifyValue) + "）");

                                cadreParty.setIsFirst(true);
                                cadrePartyMapper.insertSelective(cadreParty);
                            } else {
                                _cadreParty.setClassId(modifyValue);
                                _cadreParty.setRemark("本人修改申请（" + democraticPartyMap.get(orginalValue)
                                        + " -> " + democraticPartyMap.get(modifyValue) + "）");

                                cadrePartyMapper.updateByPrimaryKeySelective(_cadreParty);
                            }
                        }
                    }else{
                        throw new OpException("修改前后值有误，请审批不通过后通知用户重新申请。");
                    }
                } else if (StringUtils.equals(code, "grow_time")) {

                    // 判断是否仍然还有未审核的“政治面貌”修改申请，如果有，则不允许修改党派加入时间
                    List<ModifyBaseItem> modifyBaseItems = iModifyMapper.selectModifyBaseItems(userId, "political_status",
                            ModifyConstants.MODIFY_BASE_ITEM_STATUS_APPLY);
                    if (modifyBaseItems.size() > 0) {
                        throw new OpException("当前不允许修改党派加入时间：该用户存在未审批的“政治面貌”修改申请");
                    }

                    Member member = memberMapper.selectByPrimaryKey(userId);
                    boolean isOwParty = (member!=null && (member.getStatus()==1 || member.getStatus()==4));
                    if(!isOwParty) {
                        CadreParty owParty = cadrePartyService.getOwOrFirstDp(userId, CadreConstants.CADRE_PARTY_TYPE_OW);
                        isOwParty = (owParty != null);
                    }
                    CadreParty dpParty = cadrePartyService.getOwOrFirstDp(userId, CadreConstants.CADRE_PARTY_TYPE_DP);
                    boolean isDpParty = (dpParty != null);
                    int owType = 2; // 两个党派以上时或民主党派时，默认为民主党派的加入时间
                    if(isOwParty && !isDpParty){
                        owType = 1;
                    }

                    Date growTime = DateUtils.parseStringToDate(mbi.getModifyValue());
                    if (owType == 1) { // 修改加入中共党员时间

                        boolean inOwParty = false; // 判断是否在中共党派干部库或党员库中，如果不在则不允许修改
                        // 可能在中共党员干部库
                        {
                            CadreParty record = new CadreParty();
                            record.setGrowTime(growTime);

                            CadrePartyExample example = new CadrePartyExample();
                            example.createCriteria().andUserIdEqualTo(userId)
                                    .andTypeEqualTo(CadreConstants.CADRE_PARTY_TYPE_OW);
                            if(cadrePartyMapper.updateByExampleSelective(record, example)==1){
                                inOwParty = true;
                            }
                        }

                        // 也可能在 党员库
                        {
                            MemberExample example = new MemberExample();
                            example.createCriteria().andUserIdEqualTo(userId)
                                    .andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL);

                            Member record = new Member();
                            record.setGrowTime(growTime);
                            if(memberMapper.updateByExampleSelective(record, example)==1){
                                inOwParty = true;
                            }
                        }
                        if(!inOwParty){
                            throw new OpException("该用户不在中共党员库中。");
                        }

                    } else if (owType == 2) { // 修改加入民主党派时间

                        CadreParty _cadreParty = cadrePartyService.getOwOrFirstDp(userId, CadreConstants.CADRE_PARTY_TYPE_DP);
                        if(_cadreParty==null){
                            throw new OpException("该用户不在民主党员干部库中。");
                        }

                        if(growTime==null){
                            commonMapper.excuteSql("update cadre_party set grow_time=null " +
                                    "where user_id="+userId + " and is_first=1 and type="+CadreConstants.CADRE_PARTY_TYPE_DP);
                        }else {
                            CadreParty record = new CadreParty();
                            record.setGrowTime(growTime);

                            CadrePartyExample example = new CadrePartyExample();
                            example.createCriteria().andUserIdEqualTo(userId)
                                    .andTypeEqualTo(CadreConstants.CADRE_PARTY_TYPE_DP)
                                    .andIsFirstEqualTo(true);

                            cadrePartyMapper.updateByExampleSelective(record, example);
                        }
                    }
                } else if (StringUtils.isNotBlank(tableName)) {

                    if (StringUtils.equals("work_time", mbi.getCode())) {
                        String modifyValue = mbi.getModifyValue();
                        if (modifyValue != null) {
                            Date date = DateUtils.parseDate(modifyValue, DateUtils.YYYYMM);
                            mbi.setModifyValue(DateUtils.formatDate(date, DateUtils.YYYY_MM_DD));
                        }
                    }

                    // 更新数据库内容，主键值必须是用户ID
                    boolean needSingleQuotes = (mbi.getType() != ModifyConstants.MODIFY_BASE_ITEM_TYPE_INT);
                    String sql = "update " + tableName + " set " + mbi.getCode() + " = " + (needSingleQuotes ? "'" : "") +
                            StringEscapeUtils.escapeSql(mbi.getModifyValue().replace("\\", "\\\\")) + (needSingleQuotes ? "'" : "")
                            + " where " + mbi.getTableIdName() + "=" + _sysUser.getId();
                    commonMapper.excuteSql(sql);
                }
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
        if (BooleanUtils.isNotTrue(status) || StringUtils.isBlank(tableName)) return;

        cacheHelper.clearUserCache(_sysUser);
        cacheHelper.clearCadreCache();
    }
}
