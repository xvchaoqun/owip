package service.member;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.global.OpException;
import domain.base.ApiKey;
import domain.base.ApiKeyExample;
import domain.member.*;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUser;
import domain.sys.SysUserExample;
import domain.sys.SysUserView;
import ext.domain.ExtYjs;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.LoginUserService;
import service.base.ApiKeyService;
import service.party.BranchService;
import service.party.MemberService;
import service.party.PartyService;
import service.sys.LogService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.constants.SystemConstants;
import sys.helper.PartyHelper;
import sys.tags.CmTag;
import sys.tool.tree.TreeNode;
import sys.utils.ContentUtils;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;
import sys.utils.MD5Util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MemberApplyService extends MemberBaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private EnterApplyService enterApplyService;
    @Autowired
    protected ApplyApprovalLogService applyApprovalLogService;
    @Autowired
    protected ApplySnService applySnService;
    @Autowired
    protected ApiKeyService apiKeyService;
    @Autowired
    protected LogService logService;

    @Transactional
    public int batchImport(List<MemberApply> records) {

        if (records.size() == 0) return 0;

        int addCount = 0;
        int row = 1;
        for (MemberApply record : records) {

            row++;
            SysUserView uv = record.getUser();
            int userId = record.getUserId();
            byte stage = record.getStage();
            Integer applySnId = record.getApplySnId();

            Member member = memberService.get(userId);
            if(member!=null){
                if(member.getPoliticalStatus()==MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE
                    && stage < OwConstants.OW_APPLY_STAGE_POSITIVE){
                     throw new OpException("第{0}行{1}已是正式党员", row, uv.getRealname());
                }else if(member.getPoliticalStatus()==MemberConstants.MEMBER_POLITICAL_STATUS_GROW
                    && stage < OwConstants.OW_APPLY_STAGE_GROW){
                     throw new OpException("第{0}行{1}已是预备党员", row, uv.getRealname());
                }
            }

            MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
            if (memberApply == null) {

                // 确定申请不重复
                enterApplyService.checkCurrentApply(userId, OwConstants.OW_ENTER_APPLY_TYPE_MEMBERAPPLY);

                memberApplyMapper.insertSelective(record);

                // 更新志愿书编码状态
                if(applySnId!=null){
                    applySnService.use(applySnId, userId);
                }

                // 判断是否是预备党员/正式党员
                addMemberIfNeeded(record);
                addCount++;

            } else {

                // 不允许 预备党员/正式党员 回退
                if (memberApply.getStage() >= OwConstants.OW_APPLY_STAGE_GROW
                        && stage < OwConstants.OW_APPLY_STAGE_GROW) {
                    throw new OpException("第{0}行{1}处于[{2}]阶段，不允许更新为[{3}]", row, uv.getRealname(),
                            OwConstants.OW_APPLY_STAGE_MAP.get(memberApply.getStage()),
                            OwConstants.OW_APPLY_STAGE_MAP.get(stage));
                }

                if (memberApply.getStage() < OwConstants.OW_APPLY_STAGE_GROW
                        && stage >= OwConstants.OW_APPLY_STAGE_GROW) {

                    addMemberIfNeeded(record);
                }

                if(record.getApplyStage()==null){
                    record.setApplyStage(OwConstants.OW_APPLY_STAGE_INIT);
                }
                memberApplyMapper.updateByPrimaryKey(record);

                // 更新志愿书编码状态
                Integer oldApplySnId = memberApply.getApplySnId();
                if(oldApplySnId!=null){
                    if(applySnId!=null ){
                        if(applySnId.intValue()!=oldApplySnId){

                            applySnService.clearUse(oldApplySnId, true);
                            applySnService.use(applySnId, userId);
                        }
                    }else{

                        applySnService.clearUse(oldApplySnId, true);
                    }
                }else if(applySnId!=null){

                    applySnService.use(applySnId, userId);
                }
            }

            applyApprovalLogService.add(userId,
                    record.getPartyId(), record.getBranchId(), userId,
                    ShiroHelper.getCurrentUserId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                    OwConstants.OW_APPLY_STAGE_MAP.get(stage),
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                    memberApply==null?"导入添加":"导入更新");
        }

        return addCount;
    }

    private void addMemberIfNeeded(MemberApply memberApply) {

        byte stage = memberApply.getStage();
        if (stage == OwConstants.OW_APPLY_STAGE_GROW
                || stage == OwConstants.OW_APPLY_STAGE_POSITIVE) {

            int userId = memberApply.getUserId();
            Member member = new Member();
            member.setUserId(userId);
            member.setPartyId(memberApply.getPartyId());
            member.setBranchId(memberApply.getBranchId());
            if (stage == OwConstants.OW_APPLY_STAGE_GROW) {
                member.setPoliticalStatus(MemberConstants.MEMBER_POLITICAL_STATUS_GROW); // 预备党员
            } else {
                member.setPoliticalStatus(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE); // 正式党员
            }

            member.setStatus(MemberConstants.MEMBER_STATUS_NORMAL); // 正常党员
            member.setSource(MemberConstants.MEMBER_SOURCE_IMPORT); // 导入
            member.setAddType(CmTag.getMetaTypeByCode("mt_member_add_type_old").getId());
            member.setApplyTime(memberApply.getApplyTime());
            member.setActiveTime(memberApply.getActiveTime());
            member.setCandidateTime(memberApply.getCandidateTime());
            member.setGrowTime(memberApply.getGrowTime());

            member.setCreateTime(new Date());

            // 进入党员库
            memberService.addOrUpdate(member, "批量导入（党员发展信息）");
        }
    }

    // 积极分子选择树
    public TreeNode getTree(Set<Integer> selectIdSet, byte stage) {

        Map<Integer, List<MemberApplyView>> groupMap = new LinkedHashMap<>();

        {
            MemberApplyViewExample example = new MemberApplyViewExample();
            example.createCriteria()
                    .andIsRemoveEqualTo(false)
                    .andStageEqualTo(stage);
            example.setOrderByClause("party_sort_order desc, branch_sort_order desc,create_time desc");

            List<MemberApplyView> memberApplyViews = memberApplyViewMapper.selectByExample(example);

            for (MemberApplyView mav : memberApplyViews) {

                int partyId = mav.getPartyId();
                List<MemberApplyView> uvs = groupMap.get(partyId);
                if (uvs == null) {
                    uvs = new ArrayList<>();
                    groupMap.put(partyId, uvs);
                }
                uvs.add(mav);
            }
        }

        TreeNode root = new TreeNode();
        root.title = OwConstants.OW_APPLY_STAGE_MAP.get(stage);
        root.expand = true;
        root.isFolder = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        Map<Integer, Party> partyMap = partyService.findAll();
        Map<Integer, Branch> branchMap = branchService.findAll();
        for (Map.Entry<Integer, List<MemberApplyView>> entry : groupMap.entrySet()) {
            List<MemberApplyView> entryValue = entry.getValue();
            if (entryValue.size() > 0) {

                TreeNode titleNode = new TreeNode();
                titleNode.expand = false;
                titleNode.isFolder = true;
                List<TreeNode> titleChildren = new ArrayList<TreeNode>();
                titleNode.children = titleChildren;

                int selectCount = 0;
                for (MemberApplyView mav : entryValue) {

                    String branchName = (mav.getBranchId() == null) ? null : branchMap.get(mav.getBranchId()).getName();
                    SysUserView uv = mav.getUser();
                    int userId = mav.getUserId();
                    TreeNode node = new TreeNode();
                    node.title = uv.getRealname() + (branchName != null ? ("-" + branchName) : "");

                    int key = userId;
                    node.key = key + "";

                    if (selectIdSet.contains(key)) {
                        selectCount++;
                        node.select = true;
                    }

                    titleChildren.add(node);
                }

                titleNode.title = partyMap.get(entry.getKey()).getName() + String.format("(%s", selectCount > 0 ? selectCount + "/" : "") + entryValue.size() + "人)";
                rootChildren.add(titleNode);
            }
        }
        return root;
    }

    /*@Transactional
    public int insertSelective(MemberApply record) {
        return memberApplyMapper.insertSelective(record);
    }*/

    /**
     * 党员库中直接添加或更新党员时，更新相关的党员发展记录
     * <p>
     * 情况一：添加或更新为预备党员：
     * 1、党员发展记录不存在时，需要加入党员发展流程（预备党员阶段）;
     * 2、党员发展记录存在时，则修改为预备党员阶段
     *
     * 情况二：添加或更新为正式党员：
     * 1、党员发展记录不存在时，不需要操作;
     * 2、党员发展记录存在时，则修改为正式党员阶段
     *
     * @param userId
     */
    public void updateByMember(int userId) {

        Member member = memberService.get(userId);
        Integer currentUserId = ShiroHelper.getCurrentUserId();
        if (member != null && member.getStatus() == MemberConstants.MEMBER_STATUS_NORMAL) {

            Date now = new Date();
            MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);

            MemberApply record = new MemberApply();
            record.setUserId(userId);
            record.setPartyId(member.getPartyId());
            record.setBranchId(member.getBranchId());
            record.setApplyTime(member.getApplyTime() == null ? now : member.getApplyTime());
            record.setActiveTime(member.getActiveTime());
            record.setCandidateTime(member.getCandidateTime());
            record.setGrowTime(member.getGrowTime());

            byte politicalStatus = member.getPoliticalStatus();
            byte stage = OwConstants.OW_APPLY_STAGE_GROW;
            if (memberApply == null) { // 还没有党员发展信息

                if(politicalStatus == MemberConstants.MEMBER_POLITICAL_STATUS_GROW) {

                    record.setGrowStatus(OwConstants.OW_APPLY_STATUS_UNCHECKED);
                    record.setStage(stage);
                    record.setRemark("预备党员信息添加后同步");
                    record.setFillTime(now);
                    record.setCreateTime(now);
                    record.setIsRemove(false);
                    memberApplyMapper.insertSelective(record);
                }else{
                    stage = OwConstants.OW_APPLY_STAGE_POSITIVE;
                }
            } else {

                if(politicalStatus == MemberConstants.MEMBER_POLITICAL_STATUS_GROW) {

                    if(memberApply.getGrowStatus()==null){
                        record.setGrowStatus(OwConstants.OW_APPLY_STATUS_UNCHECKED);
                    }
                    record.setStage(stage);
                    record.setRemark("预备党员信息同步");
                    commonMapper.excuteSql("update ow_member set positive_time=null where user_id=" + userId);
                    // 考虑更新为直属党支部的情况
                    commonMapper.excuteSql("update ow_member_apply set branch_id=null, positive_status=null, positive_time=null where user_id=" + userId);
                }else{

                    stage = OwConstants.OW_APPLY_STAGE_POSITIVE;

                    record.setPositiveStatus(OwConstants.OW_APPLY_STATUS_CHECKED);
                    record.setPositiveTime(member.getPositiveTime());
                    record.setStage(stage);
                    record.setRemark("正式党员信息同步");
                    // 考虑更新为直属党支部的情况
                    commonMapper.excuteSql("update ow_member_apply set branch_id=null where user_id=" + userId);
                }

                memberApplyMapper.updateByPrimaryKeySelective(record);
            }

            applyApprovalLogService.add(userId,
                    member.getPartyId(), member.getBranchId(), userId,
                    currentUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                    OwConstants.OW_APPLY_STAGE_MAP.get(stage),
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                    "后台添加或修改党员信息");
        }
    }

    // status=-1代表 isNULL
    public int count(Integer partyId, Integer branchId, Byte applyType, Byte stage, Byte status) {

        MemberApplyViewExample example = new MemberApplyViewExample();
        MemberApplyViewExample.Criteria criteria = example.createCriteria().andMemberStatusEqualTo(0);

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(applyType==MemberConstants.MEMBER_TYPE_TEACHER){
            criteria.andUserTypeIn(Arrays.asList(SystemConstants.USER_TYPE_JZG,
                    SystemConstants.USER_TYPE_RETIRE));
        }else{
            criteria.andUserTypeIn(Arrays.asList(SystemConstants.USER_TYPE_BKS,
                    SystemConstants.USER_TYPE_SS, SystemConstants.USER_TYPE_BS));
        }
        if (stage != null) {
            criteria.andStageEqualTo(stage);
            if (stage == OwConstants.OW_APPLY_STAGE_INIT) {
                criteria.andApplyStageEqualTo(OwConstants.OW_APPLY_STAGE_INIT);
            }
            if (status != null) {
                switch (stage) {
                    case OwConstants.OW_APPLY_STAGE_ACTIVE:
                        if (status == -1) criteria.andCandidateStatusIsNull();
                        else criteria.andCandidateStatusEqualTo(status);
                        break;
                    case OwConstants.OW_APPLY_STAGE_CANDIDATE:
                        if (status == -1) criteria.andPlanStatusIsNull();
                        else criteria.andPlanStatusEqualTo(status);
                        break;
                    case OwConstants.OW_APPLY_STAGE_PLAN:
                        /*if(status==-1) criteria.andDrawStatusIsNull();
                        else criteria.andDrawStatusEqualTo(status);*/
                        criteria.andDrawStatusIsNull();
                        break;
                    case OwConstants.OW_APPLY_STAGE_DRAW:
                        if (status == -1) criteria.andGrowStatusIsNull();
                        else criteria.andGrowStatusEqualTo(status);
                        break;
                    case OwConstants.OW_APPLY_STAGE_GROW:
                        if (status == -1) criteria.andPositiveStatusIsNull();
                        else criteria.andPositiveStatusEqualTo(status);
                        break;
                }
            }

            // 已移除的记录
            if(stage == -3){
                criteria.andIsRemoveEqualTo(true);
            }else{
                criteria.andIsRemoveEqualTo(false);
            }
        }
        if (partyId != null) criteria.andPartyIdEqualTo(partyId);
        if (branchId != null) criteria.andBranchIdEqualTo(branchId);

        return (int) memberApplyViewMapper.countByExample(example);
    }

    // 上一个 （查找比当前记录的“创建时间”  小  的记录中的  最大  的“创建时间”的记录）
    public MemberApply next(Byte applyType, Byte stage, Byte status, MemberApply memberApply) {

        MemberApplyViewExample example = new MemberApplyViewExample();
        MemberApplyViewExample.Criteria criteria = example.createCriteria().andMemberStatusEqualTo(0);

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(applyType==MemberConstants.MEMBER_TYPE_TEACHER){
            criteria.andUserTypeIn(Arrays.asList(SystemConstants.USER_TYPE_JZG,
                    SystemConstants.USER_TYPE_RETIRE));
        }else{
            criteria.andUserTypeIn(Arrays.asList(SystemConstants.USER_TYPE_BKS,
                    SystemConstants.USER_TYPE_SS, SystemConstants.USER_TYPE_BS));
        }
        if (stage != null) {
            criteria.andStageEqualTo(stage);
            if (stage == OwConstants.OW_APPLY_STAGE_INIT) {
                criteria.andApplyStageEqualTo(OwConstants.OW_APPLY_STAGE_INIT);
            }
            if (status != null) {
                switch (stage) {
                    case OwConstants.OW_APPLY_STAGE_ACTIVE:
                        if (status == -1) criteria.andCandidateStatusIsNull();
                        else criteria.andCandidateStatusEqualTo(status);
                        break;
                    case OwConstants.OW_APPLY_STAGE_CANDIDATE:
                        if (status == -1) criteria.andPlanStatusIsNull();
                        else criteria.andPlanStatusEqualTo(status);
                        break;
                    case OwConstants.OW_APPLY_STAGE_PLAN:
                        /*if(status==-1) criteria.andDrawStatusIsNull();
                        else criteria.andDrawStatusEqualTo(status);*/
                        criteria.andDrawStatusIsNull();
                        break;
                    case OwConstants.OW_APPLY_STAGE_DRAW:
                        if (status == -1) criteria.andGrowStatusIsNull();
                        else criteria.andGrowStatusEqualTo(status);
                        break;
                    case OwConstants.OW_APPLY_STAGE_GROW:
                        if (status == -1) criteria.andPositiveStatusIsNull();
                        else criteria.andPositiveStatusEqualTo(status);
                        break;
                }
            }

            // 已移除的记录
            if(stage == -3){
                criteria.andIsRemoveEqualTo(true);
            }else{
                criteria.andIsRemoveEqualTo(false);
            }
        }
        if (memberApply != null) {
            criteria.andUserIdNotEqualTo(memberApply.getUserId())
                    .andCreateTimeLessThanOrEqualTo(memberApply.getCreateTime());
        }
       example.setOrderByClause("create_time desc");

        List<MemberApplyView> memberApplies = memberApplyViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return (memberApplies.size() == 0) ? null : get(memberApplies.get(0).getUserId());
    }

    // 下一个（查找比当前记录的“创建时间” 大  的记录中的  最小  的“创建时间”的记录）
    public MemberApply last(Byte applyType, Byte stage, Byte status, MemberApply memberApply) {

        MemberApplyViewExample example = new MemberApplyViewExample();
        MemberApplyViewExample.Criteria criteria = example.createCriteria().andMemberStatusEqualTo(0);

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if (applyType != null) {

            if(applyType==MemberConstants.MEMBER_TYPE_TEACHER){
                criteria.andUserTypeIn(Arrays.asList(SystemConstants.USER_TYPE_JZG,
                        SystemConstants.USER_TYPE_RETIRE));
            }else{
                criteria.andUserTypeIn(Arrays.asList(SystemConstants.USER_TYPE_BKS,
                        SystemConstants.USER_TYPE_SS, SystemConstants.USER_TYPE_BS));
            }

            if (stage == OwConstants.OW_APPLY_STAGE_INIT) {
                criteria.andApplyStageEqualTo(OwConstants.OW_APPLY_STAGE_INIT);
            }
        }
        if (stage != null) {
            criteria.andStageEqualTo(stage);
            if (status != null) {
                switch (stage) {
                    case OwConstants.OW_APPLY_STAGE_ACTIVE:
                        if (status == -1) criteria.andCandidateStatusIsNull();
                        else criteria.andCandidateStatusEqualTo(status);
                        break;
                    case OwConstants.OW_APPLY_STAGE_CANDIDATE:
                        if (status == -1) criteria.andPlanStatusIsNull();
                        else criteria.andPlanStatusEqualTo(status);
                        break;
                    case OwConstants.OW_APPLY_STAGE_PLAN:
                        if (status == -1) criteria.andDrawStatusIsNull();
                        else criteria.andDrawStatusEqualTo(status);
                        break;
                    case OwConstants.OW_APPLY_STAGE_DRAW:
                        if (status == -1) criteria.andGrowStatusIsNull();
                        else criteria.andGrowStatusEqualTo(status);
                        break;
                    case OwConstants.OW_APPLY_STAGE_GROW:
                        if (status == -1) criteria.andPositiveStatusIsNull();
                        else criteria.andPositiveStatusEqualTo(status);
                        break;
                }
            }

            // 已移除的记录
            if(stage == -3){
                criteria.andIsRemoveEqualTo(true);
            }else{
                criteria.andIsRemoveEqualTo(false);
            }
        }

        if (memberApply != null) {
            criteria.andUserIdNotEqualTo(memberApply.getUserId())
                    .andCreateTimeGreaterThanOrEqualTo(memberApply.getCreateTime());
        }
        example.setOrderByClause("create_time asc");

        List<MemberApplyView> memberApplies = memberApplyViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size() == 0) ? null : get(memberApplies.get(0).getUserId());
    }

    public MemberApply get(int userId) {

        return memberApplyMapper.selectByPrimaryKey(userId);
    }

    // 直接删除
    public void del(int userId) {

        MemberApplyExample example = new MemberApplyExample();
        example.createCriteria().andUserIdEqualTo(userId);
        memberApplyMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByExampleSelective(int userId, MemberApply record, MemberApplyExample example) {

        Member member = memberService.get(userId);
        // 修改党员发展申请人员的所在分党委和支部，如果是在预备或正式党员中修改，则相应的要修改党员信息
        if (record.getPartyId() != null) {

            MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
            if (member != null && memberApply != null) {

                if (record.getBranchId() == null) {
                    // 修改为直属党支部
                    Assert.isTrue(partyService.isDirectBranch(record.getPartyId()), "not direct branch");
                    iMemberMapper.updateToDirectBranch("ow_member_apply", "user_id", userId, record.getPartyId());
                }

                if (memberApply.getStage() == OwConstants.OW_APPLY_STAGE_GROW
                        || memberApply.getStage() == OwConstants.OW_APPLY_STAGE_POSITIVE) {
                    Member _member = new Member();
                    _member.setUserId(userId);
                    _member.setPartyId(record.getPartyId());
                    _member.setBranchId(record.getBranchId());
                    memberService.updateByPrimaryKeySelective(_member, "党员发展中修改所在党组织");
                }
            }
        }

        if (record.getApplyTime() != null) { // 如果修改了提交书面申请书时间，相应的党员信息的也要修改
            if (member != null) {
                if (member.getApplyTime() == null || !member.getApplyTime().equals(record.getApplyTime())) {
                    Member _member = new Member();
                    _member.setUserId(userId);
                    _member.setApplyTime(record.getApplyTime());
                    memberService.updateByPrimaryKeySelective(_member, "党员发展中提交或修改提交书面申请书时间");
                }
            }
        }

        if (record.getActiveTime() != null) { // 如果修改了确定为入党积极分子时间，相应的党员信息的也要修改
            if (member != null) {
                if (member.getActiveTime() == null || !member.getActiveTime().equals(record.getActiveTime())) {
                    Member _member = new Member();
                    _member.setUserId(userId);
                    _member.setActiveTime(record.getActiveTime());
                    memberService.updateByPrimaryKeySelective(_member, "党员发展中提交或修改确定为入党积极分子时间");
                }
            }
        }
        if (record.getCandidateTime() != null) { // 如果修改了确定为发展对象时间，相应的党员信息的也要修改
            if (member != null) {
                if (member.getCandidateTime() == null || !member.getCandidateTime().equals(record.getCandidateTime())) {
                    Member _member = new Member();
                    _member.setUserId(userId);
                    _member.setCandidateTime(record.getCandidateTime());
                    memberService.updateByPrimaryKeySelective(_member, "党员发展中提交或修改确定为发展对象时间");
                }
            }
        }

        if (record.getGrowTime() != null) { // 如果修改了入党时间，相应的党员信息的入党时间也要修改
            if (member != null) {
                if (member.getGrowTime() == null || !member.getGrowTime().equals(record.getGrowTime())) {
                    Member _member = new Member();
                    _member.setUserId(userId);
                    _member.setGrowTime(record.getGrowTime());
                    memberService.updateByPrimaryKeySelective(_member, "党员发展中提交或修改入党时间");
                }
            }
        }
        if (record.getPositiveTime() != null) { // 如果修改了转正时间
            if (member != null && member.getPoliticalStatus() == MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE) {
                if (member.getPositiveTime() == null || !member.getPositiveTime().equals(record.getPositiveTime())) {
                    Member _member = new Member();
                    _member.setUserId(userId);
                    _member.setPositiveTime(record.getPositiveTime());
                    memberService.updateByPrimaryKeySelective(_member, "党员发展中提交或修改转正时间");
                }
            }
        }

        return memberApplyMapper.updateByExampleSelective(record, example);
    }

    // 分党委审核预备党员信息，跳过下一步的组织部审核
    @Transactional
    public void applyPositiveCheckByParty(int userId, MemberApply record, MemberApplyExample example) {

        if (memberApplyMapper.updateByExampleSelective(record, example) > 0) {
            memberPositive(userId);
        }
    }

    @Transactional
    public void denyWhenDeleteMember(int userId) {
        MemberApply _memberApply = memberApplyMapper.selectByPrimaryKey(userId);
        if (_memberApply != null && _memberApply.getStage() != OwConstants.OW_APPLY_STAGE_DENY) {
            // 状态检查
            enterApplyService.checkCurrentApply(userId, OwConstants.OW_ENTER_APPLY_TYPE_MEMBERAPPLY);

            MemberApply record = new MemberApply();
            record.setStage(OwConstants.OW_APPLY_STAGE_DENY);
            record.setPassTime(new Date());// 用"通过时间"记录处理时间
            record.setRemark("系统退回");
            MemberApplyExample example = new MemberApplyExample();
            example.createCriteria().andUserIdEqualTo(userId);
            Assert.isTrue(memberApplyMapper.updateByExampleSelective(record, example) > 0, "db update failed");

            applyApprovalLogService.add(_memberApply.getUserId(),
                    _memberApply.getPartyId(), _memberApply.getBranchId(), _memberApply.getUserId(),
                    ShiroHelper.getCurrentUserId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                    "撤回",
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                    "删除党员时，同时退回党员发展信息");
        }
    }

    // 成为正式党员
    @Transactional
    public void memberPositive(int userId) {

        MemberApply memberApply = get(userId);
        if (memberApply == null || memberApply.getIsRemove())
            throw new OpException("操作失败，记录不存在或已被移除");

        MemberApply record = new MemberApply();
        record.setStage(OwConstants.OW_APPLY_STAGE_POSITIVE);
        record.setPositiveStatus(OwConstants.OW_APPLY_STATUS_OD_CHECKED);

        MemberApplyExample example = new MemberApplyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStageEqualTo(OwConstants.OW_APPLY_STAGE_GROW);

        if(memberApply.getPositiveStatus()==null){
            throw new OpException("支部还没有提交预备党员转正");
        }
        /*if(memberApply.getPositiveStatus()==OwConstants.OW_APPLY_STATUS_CHECKED){
            String partyName = CmTag.getStringProperty("partyName");
            throw new OpException(partyName + "还没有审核");
        }*/

        // 1. 更新申请状态
        updateByExampleSelective(userId, record, example);

        //Member member = memberMapper.selectByPrimaryKey(userId);
        Member _record = new Member();
        _record.setUserId(userId);
        _record.setPoliticalStatus(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE);
        _record.setPositiveTime(memberApply.getPositiveTime());
        //_record.setBranchId(member.getBranchId());
        // 2. 更新党员政治面貌
        memberService.updateByPrimaryKeySelective(_record);
    }

    // 成为预备党员 (组织部审核之后，直属党支部提交发展时间)
    @Transactional
    public void memberGrowByDirectParty(int userId, Date growTime) {

        SysUserView sysUser = sysUserService.findById(userId);
        MemberApply memberApply = get(userId);
        if (sysUser == null || memberApply == null || memberApply.getIsRemove())
            throw new OpException("记录不存在或已被移除");

        MemberApply record = new MemberApply();
        record.setStage(OwConstants.OW_APPLY_STAGE_GROW);
        record.setGrowStatus(OwConstants.OW_APPLY_STATUS_CHECKED);
        record.setGrowTime(growTime);

        MemberApplyExample example = new MemberApplyExample();
        if (CmTag.getBoolProperty("ignore_plan_and_draw")){
            example.createCriteria().andUserIdEqualTo(userId)
                    .andStageEqualTo(OwConstants.OW_APPLY_STAGE_CANDIDATE);
        }else {
            example.createCriteria().andUserIdEqualTo(userId)
                    .andStageEqualTo(OwConstants.OW_APPLY_STAGE_DRAW)
                    .andGrowStatusEqualTo(OwConstants.OW_APPLY_STATUS_OD_CHECKED);
        }

        //1. 更新申请状态
        if (updateByExampleSelective(userId, record, example) == 0)
            throw new OpException("需要组织部审核之后，才可以发展为预备党员");

        Member member = new Member();
        member.setUserId(userId);
        member.setPartyId(memberApply.getPartyId());
        member.setBranchId(memberApply.getBranchId());
        member.setPoliticalStatus(MemberConstants.MEMBER_POLITICAL_STATUS_GROW); // 预备党员
        member.setStatus(MemberConstants.MEMBER_STATUS_NORMAL); // 正常党员
        member.setSource(MemberConstants.MEMBER_SOURCE_GROW); // 本校发展党员
        member.setAddType(CmTag.getMetaTypeByCode("mt_member_add_type_new").getId());
        member.setApplyTime(memberApply.getApplyTime());
        member.setActiveTime(memberApply.getActiveTime());
        member.setCandidateTime(memberApply.getCandidateTime());
        member.setGrowTime(growTime);

        member.setCreateTime(new Date());

        //3. 进入党员库
        memberService.addOrUpdate(member, "发展为预备党员");
    }

    // 成为预备党员 (支部提交之后，分党委审核)
    @Transactional
    public void memberGrowByParty(int userId) {

        SysUserView sysUser = sysUserService.findById(userId);
        MemberApply memberApply = get(userId);
        if (sysUser == null || memberApply == null || memberApply.getIsRemove())
            throw new OpException("记录不存在或已被移除");

        MemberApply record = new MemberApply();
        record.setStage(OwConstants.OW_APPLY_STAGE_GROW);
        record.setGrowStatus(OwConstants.OW_APPLY_STATUS_CHECKED);

        MemberApplyExample example = new MemberApplyExample();
        if (CmTag.getBoolProperty("ignore_plan_and_draw")){
            example.createCriteria().andUserIdEqualTo(userId)
                    .andStageEqualTo(OwConstants.OW_APPLY_STAGE_CANDIDATE);
            record.setPlanStatus(OwConstants.OW_APPLY_STATUS_CHECKED);
            record.setDrawStatus(OwConstants.OW_APPLY_STATUS_CHECKED);
        }else {
            example.createCriteria().andUserIdEqualTo(userId)
                    .andStageEqualTo(OwConstants.OW_APPLY_STAGE_DRAW)
                    .andGrowStatusEqualTo(OwConstants.OW_APPLY_STATUS_UNCHECKED);
        }

        //1. 更新申请状态
        if (updateByExampleSelective(userId, record, example) == 0)
            throw new OpException("需要党支部提交发展时间之后，才可以审核");

        memberApplyToMember(memberApply);
    }

    public Member memberApplyToMember(MemberApply memberApply){

        Member member = new Member();
        member.setUserId(memberApply.getUserId());
        member.setPartyId(memberApply.getPartyId());
        member.setBranchId(memberApply.getBranchId());
        member.setPoliticalStatus(MemberConstants.MEMBER_POLITICAL_STATUS_GROW); // 预备党员
        member.setStatus(MemberConstants.MEMBER_STATUS_NORMAL); // 正常党员
        member.setSource(MemberConstants.MEMBER_SOURCE_GROW); // 本校发展党员
        member.setAddType(CmTag.getMetaTypeByCode("mt_member_add_type_new").getId());
        member.setApplyTime(memberApply.getApplyTime());
        member.setActiveTime(memberApply.getActiveTime());
        member.setCandidateTime(memberApply.getCandidateTime());
        member.setSponsor(memberApply.getSponsorUsers()); // 入党介绍人
        member.setGrowTime(memberApply.getGrowTime());

        member.setCreateTime(new Date());

        //3. 进入党员库
        memberService.addOrUpdate(member, "发展为预备党员");

        return member;
    }

    // 只用于更新部分字段
    @Transactional
    public void updateByPrimaryKeySelective(MemberApply record) {

        memberApplyMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void memberApply_back(int userId, byte stage) {

        Member member = memberService.get(userId);
        if(member!=null){
            if(stage==OwConstants.OW_APPLY_STAGE_GROW){
                commonMapper.excuteSql("update ow_member set positive_time=null where user_id=" + userId);
                Member record = new Member();
                record.setUserId(userId);
                record.setPoliticalStatus(MemberConstants.MEMBER_POLITICAL_STATUS_GROW);
                memberService.updateByPrimaryKeySelective(record, "在党员发展中，退回至预备党员初始状态");
            }else if(stage<OwConstants.OW_APPLY_STAGE_GROW){
                // 由正式党员或预备党员退回至预备党员之前的阶段，需要删除党员信息
                memberService.batchDel(new Integer[]{userId}, false);
            }
        }

        switch (stage) {
            case OwConstants.OW_APPLY_STAGE_GROW: // 党员(正式或预备)退回至预备党员初始状态
                iMemberMapper.memberApplyBackToGrow(userId);
                break;
            case OwConstants.OW_APPLY_STAGE_DRAW:  // 当前状态为领取志愿书，退回领取志愿书初始状态

                Byte growStatus = null;
                if(!CmTag.getBoolProperty("draw_od_check")) {
                    growStatus = OwConstants.OW_APPLY_STATUS_OD_CHECKED;
                }
                iMemberMapper.memberApplyBackToDraw(userId, growStatus);
                break;
            case OwConstants.OW_APPLY_STAGE_PLAN:  // 当前状态为领取志愿书之前(_stage<= OwConstants.OW_APPLY_STAGE_DRAW)
                iMemberMapper.memberApplyBackToPlan(userId);
                break;
            case OwConstants.OW_APPLY_STAGE_CANDIDATE:
                iMemberMapper.memberApplyBackToCandidate(userId);
                break;
            case OwConstants.OW_APPLY_STAGE_ACTIVE:
                iMemberMapper.memberApplyBackToActive(userId);
                break;
            case OwConstants.OW_APPLY_STAGE_INIT:
                iMemberMapper.memberApplyBackToInit(userId);
                break;
            case OwConstants.OW_APPLY_STAGE_DENY:
                iMemberMapper.memberApplyBackToInit(userId);
                enterApplyService.applyBack(userId, "退回申请",
                        OwConstants.OW_ENTER_APPLY_TYPE_MEMBERAPPLY, OwConstants.OW_ENTER_APPLY_STATUS_ADMIN_ABORT);
                break;
        }
    }

    // 更换学工号
    @Transactional
    public void changeCode(int userId, int newUserId, String remark) {

        if(userId == newUserId){

            throw new OpException("请选择新的学工号。");
        }

        MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
        if(memberApply==null || memberApply.getStage()<OwConstants.OW_APPLY_STAGE_INIT
            || memberApply.getStage() >= OwConstants.OW_APPLY_STAGE_GROW){
            throw new OpException("原学工号错误或已不在党员发展库中(申请~领取志愿书阶段)。");
        }

        if (!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(),
                memberApply.getPartyId(), memberApply.getBranchId())) {

            throw new UnauthorizedException();
        }

        MemberApply newMemberApply = memberApplyMapper.selectByPrimaryKey(newUserId);
        if(newMemberApply!=null){
            throw new OpException("新学工号已经在党员发展库中，无法更换。");
        }

        commonMapper.excuteSql("update ow_member_apply set user_id=" + newUserId + " where user_id="+ userId);

        commonMapper.excuteSql("update ow_apply_approval_log set record_id="
                + newUserId + " where type="+ OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY +" and record_id="+ userId);

        applyApprovalLogService.add(newUserId,
                    memberApply.getPartyId(), memberApply.getBranchId(), newUserId,
                    ShiroHelper.getCurrentUserId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                    "更换学工号",
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                    String.format(StringUtils.trimToEmpty(remark)+"(%s->%s)", CmTag.getUserById(userId).getCode(),
                            CmTag.getUserById(newUserId).getCode())
                    );
    }

    // 更换党组织
    @Transactional
    public void changeParty(Integer[] ids, int partyId, Integer branchId, String remark) {

        for (Integer userId : ids) {
            MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
            if (memberApply == null || memberApply.getStage() < OwConstants.OW_APPLY_STAGE_INIT
                    || memberApply.getStage() >= OwConstants.OW_APPLY_STAGE_GROW) {
                throw new OpException("原学工号错误或已不在党员发展库中(申请~领取志愿书阶段)。");
            }

            if (!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(),
                    memberApply.getPartyId(), memberApply.getBranchId())) {

                throw new UnauthorizedException();
            }

            commonMapper.excuteSql("update ow_member_apply set party_id=" + partyId
                    + ", branch_id=" + branchId + " where user_id=" + userId);

            applyApprovalLogService.add(userId,
                    partyId, branchId, userId,
                    ShiroHelper.getCurrentUserId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                    "更换党组织",
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                    remark);

        }
    }

    @Transactional
    public void acceptSmisData(String url, String app) throws IOException, IntrospectionException, InvocationTargetException, IllegalAccessException {

        List<Byte> typeIds = new ArrayList<>();
        typeIds.add(SystemConstants.USER_TYPE_BKS);
        typeIds.add(SystemConstants.USER_TYPE_SS);
        typeIds.add(SystemConstants.USER_TYPE_BS);
        SysUserExample example = new SysUserExample();
        example.createCriteria().andTypeIn(typeIds).andLockedEqualTo(false);
        List<SysUser> sysUserList = sysUserMapper.selectByExample(example);
        List<String> codeList = sysUserList.stream().map(SysUser::getCode).collect(Collectors.toList());

        ApiKeyExample apiKeyExample = new ApiKeyExample();
        apiKeyExample.createCriteria().andAppEqualTo(app);
        List<ApiKey> apiKeyList = apiKeyMapper.selectByExample(apiKeyExample);
        if (apiKeyList == null && apiKeyList.size() == 0)
            throw new OpException("推送学生党员发展接口数据异常");

        String key = apiKeyList.get(0).getSecret();
        for (String code : codeList) {

            SysUserView sysUser = sysUserService.findByCode(code);
            Integer userId = sysUser.getUserId();
            MemberApply _memberApply = memberApplyMapper.selectByPrimaryKey(userId);//组工data
            String stage = null;
            if (_memberApply != null) {
                stage = _memberApply.getStage()+"";
            }

            String _signStr = String.format("app=%s&code=%s&key=%s", app, code, key);
            String sign = MD5Util.md5Hex(_signStr, "utf-8");

            List<BasicNameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("stage", stage));
            urlParameters.add(new BasicNameValuePair("code", code));
            urlParameters.add(new BasicNameValuePair("app", app));
            urlParameters.add(new BasicNameValuePair("sign", sign));
            HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(postParams);
            CloseableHttpResponse res = httpclient.execute(httppost);

            Map map= JSON.parseObject(EntityUtils.toString(res.getEntity()), Map.class);
            if (StringUtils.equals(map.get("Success").toString(), "false")) continue;
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            MemberApply memberApply = gson.fromJson(map.get("bean").toString(), MemberApply.class);
            if (_memberApply != null && _memberApply.getStage()>memberApply.getStage()){
                logger.info(logService.log(LogConstants.LOG_MEMBER,
                        "发展阶段有误，未推送学生党员发展信息："+memberApply.getUserId()));
                continue;
            }

            String partyCode = map.get("partyCode").toString();
            String branchCode = map.get("branchCode").toString();
            Party party = partyService.getByCode(partyCode);
            Integer partyId = party.getId();
            memberApply.setPartyId(partyId);
            if (!PartyHelper.isDirectBranch(partyId) && org.apache.commons.lang.StringUtils.isNotBlank(branchCode)){
                Branch branch = branchService.getByCode(branchCode);
                memberApply.setBranchId(branch.getId());
            }

            if (_memberApply == null) {
                memberApply.setUserId(userId);
                memberApply.setFillTime(new Date());
                memberApply.setIsRemove(false);
                memberApplyMapper.insertSelective(memberApply);
            }

            List<String> hasChangeField = new ArrayList<>();
            int count = 0;

            // 判断是否有需要更新的字段
            Class clazz = memberApply.getClass();
            PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz,
                    Object.class).getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {// 所有的属性

                String name = pd.getName();// 属性名
                String columnName = ContentUtils.camelToUnderline(name);

                Method readMethod = pd.getReadMethod();// get方法
                // 在memberApply上调用get方法等同于获得memberApply的属性值
                Object o1 = readMethod.invoke(memberApply);
                // 在_memberApply上调用get方法等同于获得_memberApply的属性值
                Object o2 = readMethod.invoke(_memberApply);

                if (o1 instanceof Date) {
                    o1 = DateUtils.formatDate((Date) o1, DateUtils.YYYY_MM_DD_HH_MM_CHINA);
                }
                if (o2 instanceof Date) {
                    o2 = DateUtils.formatDate((Date) o2, DateUtils.YYYY_MM_DD_HH_MM_CHINA);
                }

                if (o1 == null && o2 == null) {
                    continue;
                } else if (o1 == null && o2 != null) {// 只有这几个值为空时需要置空，其他的直接跳过
                    if (o1 instanceof Date || org.apache.commons.lang.StringUtils.equals(columnName, "branch_id")) {
                        commonMapper.excuteSql("update ow_member_apply set " + columnName + "=null where user_id=" + userId);
                        hasChangeField.add(columnName);
                        count++;
                    }
                    continue;
                }
                if (!o1.equals(o2)) {// 比较这两个值是否相等,不等放入list
                    hasChangeField.add(columnName);
                }
            }

            if (hasChangeField.size() - count > 0){
                memberApply.setUserId(userId);
                MemberApplyExample memberApplyExample = new MemberApplyExample();
                memberApplyExample.createCriteria().andUserIdEqualTo(userId);
                memberApplyMapper.updateByExampleSelective(memberApply, memberApplyExample);
            }

            if (hasChangeField.size() > 0){
                logger.info(logService.log(LogConstants.LOG_MEMBER,
                        "推送学生党员发展信息："+memberApply.getUserId()+",更新字段为："+StringUtils.join(hasChangeField, ",")));
            }
        }

        logger.info("推送学生党员发展信息成功");
    }
}
