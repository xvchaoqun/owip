package service.pmd;

import domain.base.ContentTpl;
import domain.party.Branch;
import domain.party.BranchExample;
import domain.party.Party;
import domain.pmd.*;
import domain.sys.SysUserView;
import ext.service.ShortMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ext.service.OneSendService;
import service.party.BranchService;
import service.party.PartyService;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.ContentTplConstants;
import sys.utils.DateUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by lm on 2017/12/10.
 */
@Service
public class PmdSendMsgService extends PmdBaseMapper {

    @Autowired
    private PmdMonthService pmdMonthService;
    @Autowired
    private PmdMemberService pmdMemberService;
    @Autowired
    private PmdConfigMemberService pmdConfigMemberService;
    @Autowired
    private PmdPartyAdminService pmdPartyAdminService;
    @Autowired
    private PmdBranchAdminService pmdBranchAdminService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private OneSendService oneSendService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;

    /**
     * 通知所有分党委管理员
     * <p>
     * 组织部管理员启动当月党费收缴之后，给需要交党费的分党委书记和管理员发短信。
     */
    public void notifyPartyAdmins() {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth == null) return;
        Integer monthId = currentPmdMonth.getId();
        Date payMonth = currentPmdMonth.getPayMonth();

        List<String> userList = new ArrayList<>();
        List<String> realnameList = new ArrayList<>();

        PmdPartyViewExample example = new PmdPartyViewExample();
        example.createCriteria().andMonthIdEqualTo(monthId);
        example.setOrderByClause("sort_order desc");
        List<PmdPartyView> pmdParties = pmdPartyViewMapper.selectByExample(example);
        for (PmdPartyView pmdParty : pmdParties) {

            List<PmdPartyAdmin> pmdPartyAdmins = pmdPartyAdminService.getAllPmdPartyAdmins(pmdParty.getPartyId());
            for (PmdPartyAdmin pmdPartyAdmin : pmdPartyAdmins) {
                Integer userId = pmdPartyAdmin.getUserId();
                SysUserView uv = sysUserService.findById(userId);
                userList.add(uv.getCode());
                realnameList.add(uv.getRealname());
            }
        }

        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_PMD_NOTIFY_PARTY);
        String msg = MessageFormat.format(tpl.getContent(), DateUtils.formatDate(payMonth, "yyyy年MM月"));

        oneSendService.sendMsg(ShiroHelper.getCurrentUserId(), userList, realnameList, msg);
    }

    // 给某个分党委所有管理员发通知
    public void notifyPartyAdmins(int partyId) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth == null) return;
        Date payMonth = currentPmdMonth.getPayMonth();

        List<String> userList = new ArrayList<>();
        List<String> realnameList = new ArrayList<>();

        List<PmdPartyAdmin> pmdPartyAdmins = pmdPartyAdminService.getAllPmdPartyAdmins(partyId);
        for (PmdPartyAdmin pmdPartyAdmin : pmdPartyAdmins) {
            Integer userId = pmdPartyAdmin.getUserId();
            SysUserView uv = sysUserService.findById(userId);
            userList.add(uv.getCode());
            realnameList.add(uv.getRealname());
        }

        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_PMD_NOTIFY_PARTY);
        String msg = MessageFormat.format(tpl.getContent(), DateUtils.formatDate(payMonth, "yyyy年MM月"));

        oneSendService.sendMsg(ShiroHelper.getCurrentUserId(), userList, realnameList, msg);

    }

    /**
     * 通知所有支部管理员
     * <p>
     * 分党委管理员登录系统，给所有的支部书记和支部管理员发短信。
     */
    public void notifyBranchAdmins(int partyId) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth == null) return;
        Date payMonth = currentPmdMonth.getPayMonth();

        List<Branch> branchs = new ArrayList<>();
        {
            BranchExample example = new BranchExample();
            example.createCriteria().andPartyIdEqualTo(partyId)
                    .andIsDeletedEqualTo(false);
            branchs = branchMapper.selectByExample(example);
        }

        Party party = partyService.findAll().get(partyId);

        for (Branch branch : branchs) {

            int branchId = branch.getId();

            PmdBranchAdminExample example = new PmdBranchAdminExample();
            example.createCriteria().andPartyIdEqualTo(partyId).andBranchIdEqualTo(branchId);
            example.setOrderByClause("type asc");
            List<PmdBranchAdmin> pmdBranchAdmins = pmdBranchAdminMapper.selectByExample(example);

            List<String> userList = new ArrayList<>();
            List<String> realnameList = new ArrayList<>();

            for (PmdBranchAdmin pmdBranchAdmin : pmdBranchAdmins) {

                Integer userId = pmdBranchAdmin.getUserId();
                SysUserView uv = sysUserService.findById(userId);
                userList.add(uv.getCode());
                realnameList.add(uv.getRealname());
            }

            if(userList.size()==0) continue;

            ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_PMD_NOTIFY_BRANCH);
            String msg = MessageFormat.format(tpl.getContent(),
                    branch.getName(),
                    DateUtils.formatDate(payMonth, "yyyy年MM月"),
                    party.getName());

            oneSendService.sendMsg(ShiroHelper.getCurrentUserId(), userList, realnameList, msg);
        }
    }

    /**
     * 通知支部党员
     * <p>
     * 1、支部管理员登录系统，给本支部所有的未缴费党员发短信。
     * 2、支部管理员可以设置提醒时间，到了时间后，系统自动给未按时缴费的党员发短信提醒。
     * 提醒时间：设置一个时间点，然后设置提醒间隔（比如每1天或每2天一次）。
     */
    public void urgeMembers(Integer[] ids, int partyId, Integer branchId) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth == null) return;
        Integer monthId = currentPmdMonth.getId();
        Date payMonth = currentPmdMonth.getPayMonth();

        PmdMemberExample example = new PmdMemberExample();
        PmdMemberExample.Criteria criteria =
                example.createCriteria().andMonthIdEqualTo(monthId)
                        .andPartyIdEqualTo(partyId)
                        .andConfigMemberTypeIdIsNotNull()
                        .andHasPayEqualTo(false)
                        .andIsDelayEqualTo(false);
        if(branchId!=null){
            criteria.andBranchIdEqualTo(branchId);
        }
        if(ids!=null && ids.length>0){
            criteria.andIdIn(Arrays.asList(ids));
        }

        Party party = partyService.findAll().get(partyId);
        String branchName = party.getName();
        if(branchId!=null){
            Branch branch = branchService.findAll().get(branchId);
            branchName = branch.getName();
        }

        List<PmdMember> pmdMembers = pmdMemberMapper.selectByExample(example);

        List<String> userList = new ArrayList<>();
        List<String> realnameList = new ArrayList<>();

        for (PmdMember pmdMember : pmdMembers) {

            Integer userId = pmdMember.getUserId();
            SysUserView uv = sysUserService.findById(userId);
            userList.add(uv.getCode());
            realnameList.add(uv.getRealname());
        }

        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_PMD_URGE_MEMBERS);
        String msg = MessageFormat.format(tpl.getContent(),
                DateUtils.formatDate(payMonth, "yyyy年MM月"),
                branchName);

        oneSendService.sendMsg(ShiroHelper.getCurrentUserId(), userList, realnameList, msg);
    }

    // 本月党费收缴已经启动，短信通知本支部党员缴纳党费
    public void notifyMembers(int partyId, Integer branchId) {

        String msg = notifyMembersMsg(partyId, branchId);

        List<String> userList = new ArrayList<>();
        List<String> realnameList = new ArrayList<>();

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth == null) return;
        Integer monthId = currentPmdMonth.getId();

        PmdMemberExample example = new PmdMemberExample();
        PmdMemberExample.Criteria criteria =
                example.createCriteria().andMonthIdEqualTo(monthId)
                        .andPartyIdEqualTo(partyId);
        if(branchId!=null){
            criteria.andBranchIdEqualTo(branchId);
        }

        List<PmdMember> pmdMembers = pmdMemberMapper.selectByExample(example);
        for (PmdMember pmdMember : pmdMembers) {
            SysUserView uv = sysUserService.findById(pmdMember.getUserId());
            userList.add(uv.getCode());
            realnameList.add(uv.getRealname());
        }

        oneSendService.sendMsg(ShiroHelper.getCurrentUserId(), userList, realnameList, msg);
    }

    public String notifyMembersMsg(int partyId, Integer branchId) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth == null) return null;
        Date payMonth = currentPmdMonth.getPayMonth();

        Party party = partyService.findAll().get(partyId);
        String branchName = party.getName();
        if(branchId!=null){
            Branch branch = branchService.findAll().get(branchId);
            branchName = branch.getName();
        }

        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_PMD_NOTIFY_MEMBERS);

        return  MessageFormat.format(tpl.getContent(),
                branchName,
                DateUtils.formatDate(payMonth, "yyyy年MM月"),
                branchName);
    }


    /*// 通知个人短信内容
    public ShortMsgBean notifyMemberMsg(int pmdMemberId) {

        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(pmdMemberId);
        int userId = pmdMember.getUserId();
        PmdConfigMember pmdConfigMember = pmdConfigMemberService.getPmdConfigMember(userId);
        SysUserView uv = pmdMember.getUser();

        String mobile = pmdConfigMember.getMobile();
        if (StringUtils.isBlank(mobile)) {
            mobile = uv.getMobile();
        }

        Date payMonth = pmdMember.getPayMonth();
        String branchName = null;
        Integer branchId = pmdMember.getBranchId();
        if (branchId != null) {
            Branch branch = branchService.findAll().get(branchId);
            branchName = (branch != null) ? branch.getName() : null;
        } else {
            Integer partyId = pmdMember.getPartyId();
            Party party = partyService.findAll().get(partyId);
            branchName = (party != null) ? party.getName() : null;
        }
        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_PMD_NOTIFY_MEMBER);
        ShortMsgBean bean = new ShortMsgBean();
        shortMsgService.initShortMsgBeanParams(bean, tpl);
        bean.setSender(ShiroHelper.getCurrentUserId());
        bean.setReceiver(userId);
        bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);

        bean.setRelateId(tpl.getId());
        bean.setType(tpl.getName());
        String msg = MessageFormat.format(tpl.getContent(), uv.getRealname(),
                uv.isTeacher() ? "老师" : "同学",
                DateUtils.formatDate(payMonth, "yyyy年MM月"), branchName);
        bean.setContent(msg);
        bean.setMobile(mobile);

        return bean;
    }

    // 通知个人
    public void notify(int pmdMemberId, String mobile, String ip) {

        ShortMsgBean shortMsgBean = notifyMemberMsg(pmdMemberId);
        shortMsgBean.setMobile(mobile);

        // 更新手机号
        int userId = shortMsgBean.getReceiver();
        PmdConfigMember pmdConfigMember = pmdConfigMemberService.getPmdConfigMember(userId);
        if (!StringUtils.equals(mobile, pmdConfigMember.getMobile())) {
            PmdConfigMember record = new PmdConfigMember();
            record.setUserId(userId);
            record.setMobile(mobile);
            pmdConfigMemberService.updateByPrimaryKeySelective(record);
        }

        shortMsgService.send(shortMsgBean, ip);

        sysApprovalLogService.add(pmdMemberId, userId,
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                "短信催交", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }*/
}
