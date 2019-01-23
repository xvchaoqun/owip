package service.pcs;

import bean.ShortMsgBean;
import controller.global.OpException;
import domain.base.ContentTpl;
import domain.pcs.*;
import domain.sys.SysUserView;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.base.ContentTplService;
import service.base.ShortMsgService;
import service.sys.UserBeanService;
import shiro.ShiroHelper;
import sys.constants.ContentTplConstants;
import sys.constants.PcsConstants;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;
import sys.utils.NumberUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PcsProposalService extends PcsBaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private ContentTplService contentTplService;
    @Autowired
    private UserBeanService userBeanService;

    // 党代表提交提案通知管理员
    @Async
    public void sendPcsProposalSubmitMsgToAdmin(int proposalId, String ip){

        PcsProposalView pcsProposal = pcsProposalViewMapper.selectByPrimaryKey(proposalId);
        SysUserView applyUser = pcsProposal.getUser();

        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_PCS_PROPOSAL_SUMIT_INFO);
        List<SysUserView> receivers = contentTplService.getShorMsgReceivers(tpl.getId());

        for (SysUserView uv : receivers) {
            try {
                int userId = uv.getId();
                String mobile = userBeanService.getMsgMobile(userId);
                String msgTitle = userBeanService.getMsgTitle(userId);

                String msg = MessageFormat.format(tpl.getContent(), msgTitle,applyUser.getRealname(),
                        DateUtils.formatDate(pcsProposal.getCreateTime(), DateUtils.YYYY_MM_DD_CHINA),
                        pcsProposal.getName());

                ShortMsgBean bean = new ShortMsgBean();
                bean.setSender(applyUser.getId());
                bean.setReceiver(userId);
                bean.setMobile(mobile);
                bean.setContent(msg);
                bean.setRelateId(tpl.getId());
                bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
                bean.setType(tpl.getName());

                shortMsgService.send(bean, ip);
            }catch (Exception ex){
                logger.error("异常", ex);
                logger.error("党代表提交提案通知管理员失败。申请人：{}， 审核人：{}, {},{}", new Object[]{
                        applyUser.getRealname(), uv.getRealname(), uv.getMobile(), ex.getMessage()
                });
            }
        }
    }

    // 短信通知邀请附议人（审核通过时发送）
    @Async
    public void infoInviteSeconders(int configId, PcsProposalView pcsProposal) {

        String realname = pcsProposal.getUser().getRealname();
        String date = DateUtils.formatDate(pcsProposal.getCreateTime(), DateUtils.YYYY_MM_DD_CHINA);

        List<PcsPrCandidateView> inviteCandidates = getInviteCandidates(configId, pcsProposal);
        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_PCS_INVITE_SECONDER);

        for (PcsPrCandidateView candidate : inviteCandidates) {
            try {
                int userId = candidate.getUserId();
                String mobile = candidate.getMobile();
                String msgTitle = candidate.getRealname();

                String msg = MessageFormat.format(tpl.getContent(), msgTitle, realname, date, pcsProposal.getName());

                ShortMsgBean bean = new ShortMsgBean();
                bean.setSender(ShiroHelper.getCurrentUserId());
                bean.setReceiver(userId);
                bean.setMobile(mobile);
                bean.setContent(msg);
                bean.setRelateId(tpl.getId());
                bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
                bean.setType(tpl.getName());

                shortMsgService.send(bean, "127.0.0.1");
            } catch (Exception ex) {
                logger.error("异常", ex);
                logger.error("党代表-给提案附议人发短信提醒失败。被邀请人：{}，审核人：{}, {}",
                        candidate.getRealname(), ShiroHelper.getCurrentUser().getRealname(), ex.getMessage());
            }
        }
    }

    // 读取已经邀请的附议人
    public List<PcsPrCandidateView> getInviteCandidates(int configId, PcsProposalView pcsProposal) {

        List<Integer> inviteUserIdList = new ArrayList<>();
        String inviteUserIds = pcsProposal.getInviteUserIds();
        if (StringUtils.isNotBlank(inviteUserIds)) {
            String[] _inviteUserIds = inviteUserIds.split(",");
            for (String inviteUserId : _inviteUserIds) {
                inviteUserIdList.add(Integer.valueOf(inviteUserId));
            }
        }

        PcsPrCandidateViewExample example = new PcsPrCandidateViewExample();
        PcsPrCandidateViewExample.Criteria criteria = example.createCriteria()
                .andConfigIdEqualTo(configId).andStageEqualTo(PcsConstants.PCS_STAGE_SECOND)
                .andIsChosenEqualTo(true).andIsProposalEqualTo(true);
        if (inviteUserIdList.size() > 0)
            criteria.andUserIdIn(inviteUserIdList);
        else
            criteria.andUserIdIsNull();

        example.setOrderByClause("proposal_sort_order asc");
        return pcsPrCandidateViewMapper.selectByExample(example);
    }

    // 读取附议人
    public List<PcsPrCandidateView> getSeconderCandidates(int configId, PcsProposalView pcsProposal) {

        List<Integer> userIdList = new ArrayList<>();
        String seconderIds = pcsProposal.getSeconderIds();
        if (StringUtils.isNotBlank(seconderIds)) {
            String[] _seconderIds = seconderIds.split(",");
            for (String seconderId : _seconderIds) {
                userIdList.add(Integer.valueOf(seconderId));
            }
        }

        PcsPrCandidateViewExample example = new PcsPrCandidateViewExample();
        PcsPrCandidateViewExample.Criteria criteria = example.createCriteria()
                .andConfigIdEqualTo(configId).andStageEqualTo(PcsConstants.PCS_STAGE_SECOND)
                .andIsChosenEqualTo(true).andIsProposalEqualTo(true);
        if (userIdList.size() > 0)
            criteria.andUserIdIn(userIdList);
        else
            criteria.andUserIdIsNull();

        example.setOrderByClause("proposal_sort_order asc");
        return pcsPrCandidateViewMapper.selectByExample(example);
    }

    public boolean idDuplicate(Integer id, String code) {

        Assert.isTrue(StringUtils.isNotBlank(code), "提案编码为空");

        PcsProposalExample example = new PcsProposalExample();
        PcsProposalExample.Criteria criteria = example.createCriteria()
                .andCodeEqualTo(code);
        if (id != null) criteria.andIdNotEqualTo(id);

        return pcsProposalMapper.countByExample(example) > 0;
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {
            PcsProposal pcsProposal = pcsProposalMapper.selectByPrimaryKey(id);
            if (!ShiroHelper.isPermitted("pcsProposalOw:*")) {
                // 审核前或审核未通过，本人才可以删除操作
                if (pcsProposal.getUserId().intValue() != ShiroHelper.getCurrentUserId()
                        || !NumberUtils.contains(pcsProposal.getStatus(),
                        PcsConstants.PCS_PROPOSAL_STATUS_SAVE,
                        PcsConstants.PCS_PROPOSAL_STATUS_INIT,
                        PcsConstants.PCS_PROPOSAL_STATUS_DENY)) {
                    throw new OpException("没有删除权限");
                }
            }

            pcsProposalMapper.deleteByPrimaryKey(id);

            // 删除相关附件
            PcsProposalFileExample example = new PcsProposalFileExample();
            example.createCriteria().andProposalIdEqualTo(id);
            pcsProposalFileMapper.deleteByExample(example);
        }
    }

    @Transactional
    public void saveOrUpdate(PcsProposal record, List<PcsProposalFile> pcsProposalFiles, Integer[] inviteIds) {

        int userId = ShiroHelper.getCurrentUserId();
        Assert.isTrue(StringUtils.isBlank(record.getCode())
                || !idDuplicate(record.getId(), record.getCode()), "提案编码重复");
        if (record.getId() == null) {

            record.setUserId(userId);  // 管理员提交如何处理?
            record.setCreateTime(new Date());
            pcsProposalMapper.insertSelective(record);
        } else {
            PcsProposal pcsProposal = pcsProposalMapper.selectByPrimaryKey(record.getId());

            // 只能修改本人的记录
            if (!ShiroHelper.isPermitted("pcsProposalOw:*")) {

                // 审核前或审核未通过，本人才可以更新操作
                if (pcsProposal.getUserId().intValue() != userId
                        || !NumberUtils.contains(pcsProposal.getStatus(),
                        PcsConstants.PCS_PROPOSAL_STATUS_SAVE,
                        PcsConstants.PCS_PROPOSAL_STATUS_INIT,
                        PcsConstants.PCS_PROPOSAL_STATUS_DENY)) {
                    throw new OpException("没有更新权限");
                }

            } else {
                // 管理员不更新状态?
                record.setStatus(null);
            }

            pcsProposalMapper.updateByPrimaryKeySelective(record);
        }
        Integer proposalId = record.getId();
        // 保存附件
        for (PcsProposalFile pcsProposalFile : pcsProposalFiles) {
            pcsProposalFile.setProposalId(proposalId);
            pcsProposalFileMapper.insertSelective(pcsProposalFile);
        }

        {
            // 重新邀请附议人
            PcsProposalSeconderExample example = new PcsProposalSeconderExample();
            example.createCriteria().andProposalIdEqualTo(proposalId)
                    /*.andIsInvitedEqualTo(true).andIsFinishedEqualTo(false)*/;
            pcsProposalSeconderMapper.deleteByExample(example);

            for (Integer inviteId : inviteIds) {

                if (inviteId == userId) continue;

                PcsProposalSeconder seconder = new PcsProposalSeconder();
                seconder.setProposalId(proposalId);
                seconder.setUserId(inviteId);
                seconder.setInviteTime(new Date());
                seconder.setIsInvited(true);
                seconder.setIsFinished(false);
                pcsProposalSeconderMapper.insertSelective(seconder);
            }
        }
    }

    // 删除附件
    @Transactional
    public void batchDelFiles(Integer[] ids) {

        for (Integer id : ids) {
            PcsProposalFile pcsProposalFile = pcsProposalFileMapper.selectByPrimaryKey(id);
            PcsProposal pcsProposal = pcsProposalMapper.selectByPrimaryKey(pcsProposalFile.getProposalId());
            if (!ShiroHelper.isPermitted("pcsProposalOw:*")) {
                // 审核前或审核未通过，本人才可以删除操作
                if (pcsProposal.getUserId().intValue() != ShiroHelper.getCurrentUserId()
                        || !NumberUtils.contains(pcsProposal.getStatus(),
                        PcsConstants.PCS_PROPOSAL_STATUS_SAVE,
                        PcsConstants.PCS_PROPOSAL_STATUS_INIT,
                        PcsConstants.PCS_PROPOSAL_STATUS_DENY)) {
                    throw new OpException("没有删除权限");
                }
            }

            pcsProposalFileMapper.deleteByPrimaryKey(id);
        }
    }

    // 党代表附议
    @Transactional
    public void seconder(int id) {

        int userId = ShiroHelper.getCurrentUserId();
        PcsProposal pcsProposal = pcsProposalMapper.selectByPrimaryKey(id);
        if (pcsProposal.getUserId().intValue() == userId) {
            throw new OpException("不能附议自己的提案。");
        }

        PcsProposalSeconder seconder = new PcsProposalSeconder();
        {
            // 如果是被邀请附议人，则更新
            PcsProposalSeconderExample example = new PcsProposalSeconderExample();
            example.createCriteria().andProposalIdEqualTo(id)
                    .andUserIdEqualTo(userId);
            List<PcsProposalSeconder> pcsProposalSeconders = pcsProposalSeconderMapper.selectByExample(example);
            if (pcsProposalSeconders.size() > 0) seconder = pcsProposalSeconders.get(0);
        }

        seconder.setIsFinished(true);
        seconder.setCreateTime(new Date());
        seconder.setIp(ContextHelper.getRealIp());

        if (seconder.getId() != null) {
            pcsProposalSeconderMapper.updateByPrimaryKeySelective(seconder);
        } else {
            seconder.setProposalId(id);
            seconder.setUserId(userId);
            seconder.setIsInvited(false);

            pcsProposalSeconderMapper.insertSelective(seconder);
        }
    }
}
