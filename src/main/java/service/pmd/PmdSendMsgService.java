package service.pmd;

import domain.base.ContentTpl;
import domain.party.Branch;
import domain.party.BranchExample;
import domain.party.Party;
import domain.pmd.PmdBranchAdmin;
import domain.pmd.PmdBranchAdminExample;
import domain.pmd.PmdMember;
import domain.pmd.PmdMemberExample;
import domain.pmd.PmdMonth;
import domain.pmd.PmdPartyAdmin;
import domain.pmd.PmdPartyView;
import domain.pmd.PmdPartyViewExample;
import domain.sys.SysUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.base.OneSendService;
import service.base.ShortMsgService;
import service.party.BranchService;
import service.party.PartyService;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lm on 2017/12/10.
 */
@Service
public class PmdSendMsgService extends BaseMapper {

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
    public void notifyAllPartyAdmins() {

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

        ContentTpl tpl = shortMsgService.getShortMsgTpl(SystemConstants.CONTENT_TPL_PMD_NOTIFY_PARTY);
        String msg = MessageFormat.format(tpl.getContent(), DateUtils.formatDate(payMonth, "yyyy年MM月"));

        oneSendService.sendMsg(userList, realnameList, msg);
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

        ContentTpl tpl = shortMsgService.getShortMsgTpl(SystemConstants.CONTENT_TPL_PMD_NOTIFY_PARTY);
        String msg = MessageFormat.format(tpl.getContent(), DateUtils.formatDate(payMonth, "yyyy年MM月"));

        oneSendService.sendMsg(userList, realnameList, msg);

    }

    /**
     * 通知所有支部管理员
     * <p>
     * 分党委管理员登录系统，给所有的支部书记和支部管理员发短信。
     */
    public void notifyAllBranchAdmins(int partyId) {

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

            ContentTpl tpl = shortMsgService.getShortMsgTpl(SystemConstants.CONTENT_TPL_PMD_NOTIFY_BRANCH);
            String msg = MessageFormat.format(tpl.getContent(),
                    branch.getName(),
                    DateUtils.formatDate(payMonth, "yyyy年MM月"),
                    branch.getName());

            oneSendService.sendMsg(userList, realnameList, msg);
        }
    }

    /**
     * 通知所有支部党员
     * <p>
     * 1、支部管理员登录系统，给本支部所有的未缴费党员发短信。
     * 2、支部管理员可以设置提醒时间，到了时间后，系统自动给未按时缴费的党员发短信提醒。
     * 提醒时间：设置一个时间点，然后设置提醒间隔（比如每1天或每2天一次）。
     */
    public void notifyAllMembers(int partyId, Integer branchId) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth == null) return;
        Integer monthId = currentPmdMonth.getId();
        Date payMonth = currentPmdMonth.getPayMonth();

        PmdMemberExample example = new PmdMemberExample();
        PmdMemberExample.Criteria criteria =
                example.createCriteria().andMonthIdEqualTo(monthId)
                        .andPartyIdEqualTo(partyId)
                        .andHasPayEqualTo(false)
                        .andIsDelayEqualTo(false);
        if(branchId!=null){
            criteria.andBranchIdEqualTo(branchId);
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

        ContentTpl tpl = shortMsgService.getShortMsgTpl(SystemConstants.CONTENT_TPL_PMD_NOTIFY_MEMBER);
        String msg = MessageFormat.format(tpl.getContent(),
                DateUtils.formatDate(payMonth, "yyyy年MM月"),
                branchName);

        oneSendService.sendMsg(userList, realnameList, msg);
    }

    // 通知未缴费的个人
    public void notifyMember(int pmdMemberId) {

        String msg = notifyMemberMsg(pmdMemberId);

        List<String> userList = new ArrayList<>();
        List<String> realnameList = new ArrayList<>();

        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(pmdMemberId);
        SysUserView uv = sysUserService.findById(pmdMember.getUserId());
        userList.add(uv.getCode());
        realnameList.add(uv.getRealname());

        oneSendService.sendMsg(userList, realnameList, msg);
    }

    public String notifyMemberMsg(int pmdMemberId) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth == null) return null;
        Date payMonth = currentPmdMonth.getPayMonth();

        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(pmdMemberId);
        if(pmdMember==null || pmdMember.getHasPay() || pmdMember.getIsDelay()) return null;

        int partyId = pmdMember.getPartyId();
        Integer branchId = pmdMember.getBranchId();

        Party party = partyService.findAll().get(partyId);
        String branchName = party.getName();
        if(branchId!=null){
            Branch branch = branchService.findAll().get(branchId);
            branchName = branch.getName();
        }

        ContentTpl tpl = shortMsgService.getShortMsgTpl(SystemConstants.CONTENT_TPL_PMD_NOTIFY_MEMBER);

        return  MessageFormat.format(tpl.getContent(),
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
        ShortMsgBean bean = new ShortMsgBean();
        bean.setSender(ShiroHelper.getCurrentUserId());
        bean.setReceiver(userId);
        bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);

        ContentTpl tpl = shortMsgService.getShortMsgTpl(SystemConstants.CONTENT_TPL_PMD_NOTIFY_MEMBER);
        bean.setRelateId(tpl.getId());
        bean.setType(tpl.getName());
        String msg = MessageFormat.format(tpl.getContent(), uv.getRealname(),
                uv.getType() == SystemConstants.USER_TYPE_JZG ? "老师" : "同学",
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
