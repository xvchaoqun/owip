package service.member;

import controller.global.OpException;
import domain.member.MemberCertify;
import domain.member.MemberCertifyExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.LoginUserService;
import service.party.PartyMemberService;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.constants.SystemConstants;
import sys.helper.PartyHelper;

import java.util.*;

@Service
public class MemberCertifyService extends MemberBaseMapper {

    @Autowired
    private ApplyApprovalLogService applyApprovalLogService;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private PartyMemberService partyMemberService;

    @Transactional
    public void insertSelective(MemberCertify record, Boolean apply){

        record.setSn(generateSn(record.getYear()));
        record.setApplyTime(new Date());
        record.setCreateTime(new Date());

        memberCertifyMapper.insertSelective(record);
        applyApprovalLogService.add(record.getId(),
                record.getPartyId(), record.getBranchId(), record.getUserId(),
                ShiroHelper.getCurrentUserId(),
                BooleanUtils.isTrue(apply) ? OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_SELF : OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_CERTIFY,
                BooleanUtils.isTrue(apply) ? "本人操作" : "后台操作",
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                "提交");
    }

    public Integer generateSn(Integer year) {

        int sn = 1;
        MemberCertifyExample example = new MemberCertifyExample();
        example.createCriteria().andYearEqualTo(year);
        example.setOrderByClause("sn desc");
        List<MemberCertify> memberCertifies = memberCertifyMapper.selectByExample(example);
        if (memberCertifies.size() > 0) {
            String _sn = String.valueOf(memberCertifies.get(0).getSn()).substring(3);
            System.out.println(_sn);
            sn = Integer.parseInt(_sn) + 1;
        }
        return Integer.valueOf(String.format("%s%03d", year, sn));
    }


    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;
        for (Integer id : ids){
            MemberCertify record = memberCertifyMapper.selectByPrimaryKey(id);
            if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)){
                if (record.getStatus() >= MemberConstants.MEMBER_CERTIFY_STATUS_PARTY_VERIFY)
                    throw new OpException("不能删除已通过审核的介绍信");
            }
            memberCertifyMapper.deleteByPrimaryKey(id);
        }
    }

    @Transactional
    public void updateByPrimaryKeySelective(MemberCertify record, Boolean reApply, Boolean apply){

        if (reApply != null){
            record.setIsBack(false);
        }
        memberCertifyMapper.updateByPrimaryKeySelective(record);
        applyApprovalLogService.add(record.getId(),
                record.getPartyId(), record.getBranchId(), record.getUserId(),
                ShiroHelper.getCurrentUserId(),
                BooleanUtils.isTrue(apply) ? OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_SELF : OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_CERTIFY,
                BooleanUtils.isTrue(apply) ? "本人操作" : "后台操作",
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                BooleanUtils.isTrue(reApply) ? "重新申请" : "修改");
    }

    @Transactional
    public void batchCheck(Integer[] ids, byte type, Boolean pass, String reason) {
        if (null != ids && ids.length > 0) {
            for (Integer id : ids) {
                MemberCertify record = memberCertifyMapper.selectByPrimaryKey(id);
                if (pass) {
                    if(type == 2 && ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {
                        // 组织部审批
                        record.setStatus(MemberConstants.MEMBER_CERTIFY_STATUS_OW_VERIFY);
                    } else {
                        // 分党委审批
                        record.setStatus(MemberConstants.MEMBER_CERTIFY_STATUS_PARTY_VERIFY);
                    }
                }else {
                    record.setStatus((byte) (record.getStatus() - 1));
                }
                record.setIsBack(!pass);
                record.setReason(reason);
                memberCertifyMapper.updateByPrimaryKey(record);
                applyApprovalLogService.add(id, record.getPartyId(), record.getBranchId(), record.getUserId(),
                        ShiroHelper.getCurrentUserId(), (type == 1) ? OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY :
                                OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_OW,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_CERTIFY, (type == 1)
                                ? "分党委审核" : "组织部审核",
                        pass ?  OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS : OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_DENY, reason);
            }
        }
    }

    public int count(byte type, byte cls) {

        MemberCertifyExample example = new MemberCertifyExample();
        MemberCertifyExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if (type == 1){//分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_CERTIFY_STATUS_APPLY);
        }else if (type == 2){//组织部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_CERTIFY_STATUS_PARTY_VERIFY);
        }
        if (cls == 1 || cls == 4){
            criteria.andIsBackNotEqualTo(true);
        }else if (cls == 2 || cls == 5){
            criteria.andIsBackEqualTo(true);
        }

        return (int)memberCertifyMapper.countByExample(example);
    }

    @Transactional
    public void batchBack(Integer[] ids, byte status, String reason, SysUserView loginUser) {

        boolean odAdmin = ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL);
        byte type = 1;
        for (Integer id : ids) {

            MemberCertify memberCertify = memberCertifyMapper.selectByPrimaryKey(id);
            if (memberCertify.getStatus() >= MemberConstants.MEMBER_CERTIFY_STATUS_PARTY_VERIFY){
                if (!odAdmin) throw new UnauthorizedException();
                type = 2;
            }
            if (memberCertify.getStatus() > MemberConstants.MEMBER_CERTIFY_STATUS_BACK){
                if (!PartyHelper.hasPartyAuth(loginUser.getUserId(), memberCertify.getPartyId())) throw new UnauthorizedException();
            }

            MemberCertify record = new MemberCertify();
            record.setId(memberCertify.getId());
            record.setStatus(status);
            record.setReason(reason);
            memberCertifyMapper.updateByPrimaryKeySelective(record);
            applyApprovalLogService.add(id, record.getPartyId(), record.getBranchId(), record.getUserId(),
                    ShiroHelper.getCurrentUserId(), (type == 1) ? OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY :
                            OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_OW,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_CERTIFY,
                    (type == 1) ? "分党委审核" : "组织部审核",
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_BACK, reason);
        }
    }

}
