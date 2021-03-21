package service.member;

import controller.global.OpException;
import domain.member.*;
import domain.sys.SysUserView;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.LoginUserService;
import service.party.MemberService;
import service.party.PartyService;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.constants.RoleConstants;
import sys.helper.PartyHelper;
import sys.tags.CmTag;
import sys.utils.ContextHelper;
import sys.utils.IpUtils;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class MemberInService extends MemberBaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberOutService memberOutService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private EnterApplyService enterApplyService;

    @Autowired
    protected ApplyApprovalLogService applyApprovalLogService;
    private VerifyAuth<MemberIn> checkVerityAuth(int id){
        MemberIn memberIn = memberInMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth(memberIn, memberIn.getPartyId(), memberIn.getBranchId());
    }

    private VerifyAuth<MemberIn> checkVerityAuth2(int id){
        MemberIn memberIn = memberInMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth2(memberIn, memberIn.getPartyId());
    }
    
    public int count(Integer partyId, Integer branchId, byte type){

        MemberInExample example = new MemberInExample();
        MemberInExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_IN_STATUS_APPLY);
        } else if(type==2){ //组织部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_IN_STATUS_PARTY_VERIFY);
        }else{
            throw new OpException("审核类型错误");
        }
        if(partyId!=null) criteria.andPartyIdEqualTo(partyId);
        if(branchId!=null) criteria.andBranchIdEqualTo(branchId);

        return (int) memberInMapper.countByExample(example);
    }

    // 上一个 （查找比当前记录的“创建时间”  小  的记录中的  最大  的“创建时间”的记录）
    public MemberIn next(byte type, MemberIn memberIn){

        MemberInExample example = new MemberInExample();
        MemberInExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_IN_STATUS_APPLY);
        } else if(type==2){ //组织部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_IN_STATUS_PARTY_VERIFY);
        }else{
            throw new OpException("审核类型错误");
        }

        if(memberIn!=null)
            criteria.andUserIdNotEqualTo(memberIn.getUserId()).andCreateTimeLessThanOrEqualTo(memberIn.getCreateTime());
        example.setOrderByClause("create_time desc");

        List<MemberIn> memberApplies = memberInMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }

    // 下一个（查找比当前记录的“创建时间” 大  的记录中的  最小  的“创建时间”的记录）
    public MemberIn last(byte type, MemberIn memberIn){

        MemberInExample example = new MemberInExample();
        MemberInExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_IN_STATUS_APPLY);
        } else if(type==2){ //组织部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_IN_STATUS_PARTY_VERIFY);
        }else{
            throw new OpException("审核类型错误");
        }

        if(memberIn!=null)
            criteria.andUserIdNotEqualTo(memberIn.getUserId()).andCreateTimeGreaterThanOrEqualTo(memberIn.getCreateTime());
        example.setOrderByClause("create_time asc");

        List<MemberIn> memberApplies = memberInMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }

    /*public boolean idDuplicate(Integer id, Integer userId){

        MemberInExample example = new MemberInExample();
        MemberInExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return memberInMapper.countByExample(example) > 0;
    }*/

    // 获取最新没有完成审批的记录，为了可以再次转入( 允许转出后用原账号转入 )
    public MemberIn get(int userId) {

        MemberInExample example = new MemberInExample();
        example.createCriteria().andUserIdEqualTo(userId).andStatusNotEqualTo(MemberConstants.MEMBER_IN_STATUS_OW_VERIFY);
        example.setOrderByClause("create_time desc");
        List<MemberIn> memberIns = memberInMapper.selectByExample(example);
        if(memberIns.size()>0) return memberIns.get(0);

        return null;
    }

    // 直属党支部审核通过
    @Transactional
    public void checkMember(int userId, boolean hasReceipt){

        MemberIn memberIn = get(userId);
        if(memberIn.getStatus()!= MemberConstants.MEMBER_IN_STATUS_APPLY)
            throw new OpException("状态异常");
        MemberIn record = new MemberIn();
        record.setId(memberIn.getId());
        record.setStatus(MemberConstants.MEMBER_IN_STATUS_PARTY_VERIFY);
        record.setHasReceipt(hasReceipt);
        //record.setBranchId(memberIn.getBranchId());
        updateByPrimaryKeySelective(record);
    }

    // 分党委、党总支审核， 不需要下一步组织部审核
    @Transactional
    public void checkByParty(int userId, byte politicalStatus, boolean hasReceipt){

        checkMember(userId, hasReceipt);
        addMemberByIn(userId, politicalStatus);
    }

    // 组织部审核通过
    @Transactional
    public void addMemberByIn(int userId, byte politicalStatus){
        
        MemberIn memberIn = get(userId);
        if(memberIn.getStatus()!= MemberConstants.MEMBER_IN_STATUS_PARTY_VERIFY)
            throw new OpException("分党委还未审核通过");

        MemberIn record = new MemberIn();
        record.setId(memberIn.getId());
        record.setStatus(MemberConstants.MEMBER_IN_STATUS_OW_VERIFY);
        //record.setBranchId(memberIn.getBranchId());
        updateByPrimaryKeySelective(record);

        // 归档已转出记录（如果存在）
        memberOutService.archive(userId);

        // 添加党员操作
        Member member = new Member();
        member.setUserId(userId);
        member.setPartyId(memberIn.getPartyId());
        member.setBranchId(memberIn.getBranchId());
        member.setPoliticalStatus(politicalStatus);

        member.setStatus(MemberConstants.MEMBER_STATUS_NORMAL); // 正常党员
        member.setSource(MemberConstants.MEMBER_SOURCE_TRANSFER); // 转入党员
        member.setAddType(CmTag.getMetaTypeByCode("mt_member_add_type_tran").getId());
        member.setApplyTime(memberIn.getApplyTime());
        member.setActiveTime(memberIn.getActiveTime());
        member.setCandidateTime(memberIn.getCandidateTime());
        member.setGrowTime(memberIn.getGrowTime());
        member.setPositiveTime(memberIn.getPositiveTime());
        member.setCreateTime(new Date());

        //3. 进入党员库
        memberService.addOrUpdate(member, "组织关系转入");
    }
    @Transactional
    public int insertSelective(MemberIn record){

        return  memberInMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        memberInMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MemberInExample example = new MemberInExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberInMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberIn record){

        if(record.getPartyId()!=null && record.getBranchId()==null){
            // 修改为直属党支部
            Assert.isTrue(partyService.isDirectBranch(record.getPartyId()), "非直属党支部，请选择所属支部。");
            iMemberMapper.updateToDirectBranch("ow_member_in", "id", record.getId(), record.getPartyId());
        }

        return memberInMapper.updateByPrimaryKeySelective(record);
    }

    // 组织部审批之后，如再有修改需要保存修改记录
    @Transactional
    public void updateAfterOwVerify(MemberIn record){

        {
            MemberInModifyExample example = new MemberInModifyExample();
            example.createCriteria().andInIdEqualTo(record.getId());
            if(memberInModifyMapper.countByExample(example)==0){ // 第一次修改，需要保留原纪录
                addModify(record.getId(), true);
            }
        }

        /* 20200703 不可同步至党员库，如果修改了党员信息，比如转到别的支部后，再次修改，又被覆盖还原了 */
        /*Member member = new Member();
        member.setUserId(userId);
        member.setPartyId(record.getPartyId());
        member.setBranchId(record.getBranchId());
        member.setPoliticalStatus(record.getPoliticalStatus());
        member.setApplyTime(record.getApplyTime());
        member.setActiveTime(record.getActiveTime());
        member.setCandidateTime(record.getCandidateTime());
        member.setGrowTime(record.getGrowTime());
        member.setPositiveTime(record.getPositiveTime());
        memberService.updateByPrimaryKeySelective(member, "更新组织关系转入记录");*/

        record.setIsModify(true);
        memberInMapper.updateByPrimaryKeySelective(record);

        if(record.getPartyId()!=null && record.getBranchId()==null){
            // 修改为直属党支部
            Assert.isTrue(partyService.isDirectBranch(record.getPartyId()), "not direct branch");
            iMemberMapper.updateToDirectBranch("ow_member_in", "id", record.getId(), record.getPartyId());
            //iMemberMapper.updateToDirectBranch("ow_member", "user_id", record.getUserId(), record.getPartyId());
        }

        addModify(record.getId(), false);
    }

    private void addModify(int id, boolean first){

        MemberIn memberIn = memberInMapper.selectByPrimaryKey(id);
        Assert.isTrue(memberIn.getStatus()==MemberConstants.MEMBER_IN_STATUS_OW_VERIFY, "wrong status");
        MemberInModify modify = new MemberInModify();
        /*try {
            ConvertUtils.register(new DateConverter(null), Date.class);
            BeanUtils.copyProperties(modify, memberIn);
            modify.setId(null);
            modify.setApplyUserId(memberIn.getUserId());
        } catch (IllegalAccessException e) {
            logger.error("异常", e);
        } catch (InvocationTargetException e) {
            logger.error("异常", e);
        }*/

        try {
            PropertyUtils.copyProperties(modify, memberIn);
            modify.setId(null);
            modify.setApplyUserId(memberIn.getUserId());
        } catch (Exception e) {
            logger.error("异常", e);
        }

        modify.setInId(id);
        modify.setUserId(first?memberIn.getUserId(): ShiroHelper.getCurrentUserId());
        modify.setCreateTime(new Date());
        modify.setIp(IpUtils.getRealIp(ContextHelper.getRequest()));
        memberInModifyMapper.insertSelective(modify);
    }

    @Transactional
    public int updateByExampleSelective(MemberIn record, MemberInExample example){
        return memberInMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void memberIn_check(Integer[] ids, Boolean hasReceipt, byte type, int loginUserId){

        for (int id : ids) {
            MemberIn memberIn = memberInMapper.selectByPrimaryKey(id);
            int userId = memberIn.getUserId();

            // 检查是否已经后台添加成了党员，如果是，则提示退回申请
            Member member = memberService.get(userId);
            if(member!=null && member.getStatus()==MemberConstants.MEMBER_STATUS_NORMAL){
                SysUserView uv = CmTag.getUserById(userId);
                throw new OpException(uv.getRealname() + "已经是党员，请退回该转入申请。");
            }

            if(type==1) {
                VerifyAuth<MemberIn> verifyAuth = checkVerityAuth2(id);
                memberIn = verifyAuth.entity;
                boolean isParty = verifyAuth.isParty;
                Boolean isPartyGeneralBranch = PartyHelper.isPartyGeneralBranch(memberIn.getPartyId());

                hasReceipt = (hasReceipt==null)?false:hasReceipt;
                if (isParty || isPartyGeneralBranch) { // 分党委、党总支审核，需要跳过下一步的组织部审核
                    checkByParty(memberIn.getUserId(), memberIn.getPoliticalStatus(), hasReceipt);
                } else {
                    checkMember(memberIn.getUserId(), hasReceipt);
                }
            }
            if(type==2) {
                ShiroHelper.checkPermission(RoleConstants.PERMISSION_PARTYVIEWALL);
                addMemberByIn(memberIn.getUserId(), memberIn.getPoliticalStatus());
            }

            applyApprovalLogService.add(memberIn.getId(),
                    memberIn.getPartyId(), memberIn.getBranchId(), userId,
                    loginUserId, (type == 1)? OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY:
                            OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_OW,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_IN, (type == 1)
                            ? "分党委审核" : "组织部审核", (byte) 1, null);
        }
    }

    @Transactional
    public void memberIn_back(Integer[] userIds, byte status, String reason, int loginUserId){

        boolean odAdmin = ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL);
        for (int userId : userIds) {

            MemberIn memberIn = memberInMapper.selectByPrimaryKey(userId);
            if(memberIn.getStatus() >= MemberConstants.MEMBER_IN_STATUS_PARTY_VERIFY){
                if(!odAdmin) throw new UnauthorizedException();
            }
            if(memberIn.getStatus() >= MemberConstants.MEMBER_IN_STATUS_BACK){
                if(!PartyHelper.hasPartyAuth(loginUserId, memberIn.getPartyId())) throw new UnauthorizedException();
            }

            back(memberIn, status, loginUserId, reason);
        }
    }

    // 单条记录退回至某一状态
    private  void back(MemberIn memberIn, byte status, int loginUserId, String reason){

        byte _status = memberIn.getStatus();
        if(_status==MemberConstants.MEMBER_IN_STATUS_OW_VERIFY){
            throw new OpException("审核流程已经完成，不可以退回。");
        }
        if(status>_status || status<MemberConstants.MEMBER_IN_STATUS_BACK ){
            throw new OpException("参数有误。");
        }

        Integer id = memberIn.getId();
        Integer userId = memberIn.getUserId();

        if(status==MemberConstants.MEMBER_IN_STATUS_BACK ) { // 后台退回申请，需要重置入口提交状态
            // 状态检查
            enterApplyService.checkCurrentApply(userId,
                    OwConstants.OW_ENTER_APPLY_TYPE_MEMBERIN);
        }

        iMemberMapper.memberIn_back(id, status);

        MemberIn record = new MemberIn();
        record.setId(id);
        record.setUserId(userId);
        record.setReason(reason);
        record.setIsBack(true);
        updateByPrimaryKeySelective(record);

        applyApprovalLogService.add(id,
                memberIn.getPartyId(), memberIn.getBranchId(), userId,
                loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_IN, MemberConstants.MEMBER_IN_STATUS_MAP.get(status),
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_BACK, reason);
    }

    public MemberIn getLatest(int userId) {

        MemberInExample example = new MemberInExample();
        example.createCriteria().andUserIdEqualTo(userId);
        example.setOrderByClause("apply_time desc");
        List<MemberIn> memberIns = memberInMapper.selectByExample(example);
        if (memberIns.size() > 0) return memberIns.get(0);

        return null;
    }

    // 批量导入
    @Transactional
    public int batchImport(List<MemberIn> records) {

        int addCount = 0;
        for (MemberIn record : records) {
            int userId = record.getUserId();
            MemberIn memberIn = getLatest(userId);
            if (memberIn != null
                    && memberIn.getStatus() < MemberConstants.MEMBER_IN_STATUS_OW_VERIFY) {
                record.setId(memberIn.getId());
            }

            if (ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)) {
                record.setStatus(MemberConstants.MEMBER_IN_STATUS_OW_VERIFY);
            } else {
                record.setStatus(MemberConstants.MEMBER_IN_STATUS_PARTY_VERIFY);
            }

            if (record.getId() == null) {
                Member member = memberService.get(userId);
                if(member!=null) {
                    record.setPoliticalStatus(member.getPoliticalStatus());
                }
                if (member.getStatus() == MemberConstants.MEMBER_STATUS_NORMAL) {
                    throw new OpException("第{0}条记录已经是党员，不需要进行转入操作。", ++addCount);
                }
                memberInMapper.insertSelective(record);
                addCount++;
            } else {
                memberInMapper.updateByPrimaryKeySelective(record);
            }
            applyApprovalLogService.add(record.getId(),
                    record.getPartyId(), record.getBranchId(), userId,
                    ShiroHelper.getCurrentUserId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_IN, "批量导入",
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED, null);
        }

        return addCount;
    }

}
