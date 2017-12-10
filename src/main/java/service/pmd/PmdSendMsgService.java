package service.pmd;

import bean.ShortMsgBean;
import domain.base.ContentTpl;
import domain.party.Branch;
import domain.party.Party;
import domain.pmd.PmdConfigMember;
import domain.pmd.PmdMember;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.base.ShortMsgService;
import service.party.BranchService;
import service.party.PartyService;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;

import java.text.MessageFormat;
import java.util.Date;

/**
 * Created by lm on 2017/12/10.
 */
@Service
public class PmdSendMsgService extends BaseMapper {

    @Autowired
    private PmdConfigMemberService pmdConfigMemberService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;

    public ShortMsgBean getShortMsgBean(int id){

        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(id);
        int userId = pmdMember.getUserId();
        PmdConfigMember pmdConfigMember = pmdConfigMemberService.getPmdConfigMember(userId);
        SysUserView uv = pmdMember.getUser();

        String mobile = pmdConfigMember.getMobile();
        if(StringUtils.isBlank(mobile)){
            mobile = uv.getMobile();
        }

        Date payMonth = pmdMember.getPayMonth();
        String branchName = null;
        Integer branchId = pmdMember.getBranchId();
        if(branchId!=null){
            Branch branch = branchService.findAll().get(branchId);
            branchName = (branch!=null)?branch.getName():null;
        }else{
            Integer partyId = pmdMember.getPartyId();
            Party party = partyService.findAll().get(partyId);
            branchName = (party!=null)?party.getName():null;
        }
        ShortMsgBean bean = new ShortMsgBean();
        bean.setSender(ShiroHelper.getCurrentUserId());
        bean.setReceiver(userId);
        bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);

        ContentTpl tpl = shortMsgService.getShortMsgTpl(SystemConstants.CONTENT_TPL_PMD_NOTIFY);
        bean.setRelateId(tpl.getId());
        bean.setType(tpl.getName());
        String msg = MessageFormat.format(tpl.getContent(), uv.getRealname(),
                uv.getType()==SystemConstants.USER_TYPE_JZG?"老师":"同学",
                DateUtils.formatDate(payMonth, "yyyy年MM月"), branchName);
        bean.setContent(msg);
        bean.setMobile(mobile);

        return bean;
    }

    // 催交短信
    public void notify(int id, String mobile, String ip) {

        ShortMsgBean shortMsgBean = getShortMsgBean(id);
        shortMsgBean.setMobile(mobile);

        // 更新手机号
        int userId = shortMsgBean.getReceiver();
        PmdConfigMember pmdConfigMember = pmdConfigMemberService.getPmdConfigMember(userId);
        if(!StringUtils.equals(mobile, pmdConfigMember.getMobile())){
            PmdConfigMember record = new PmdConfigMember();
            record.setUserId(userId);
            record.setMobile(mobile);
            pmdConfigMemberService.updateByPrimaryKeySelective(record);
        }

        shortMsgService.send(shortMsgBean, ip);

        sysApprovalLogService.add(id, userId,
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                "短信催交", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }
}
