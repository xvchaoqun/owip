package service.member;

import domain.member.ApplyApprovalLog;
import domain.member.ApplyApprovalLogExample;
import org.springframework.stereotype.Service;
import sys.utils.ContextHelper;
import sys.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class ApplyApprovalLogService extends MemberBaseMapper {
    
    public void add(Integer recordId, Integer partyId, Integer branchId, Integer applyUserId,
                                Integer userId, byte userType, byte type,
                                String stage, byte status, String remark){

        HttpServletRequest request = ContextHelper.getRequest();

        ApplyApprovalLog record = new ApplyApprovalLog();
        record.setRecordId(recordId);
        record.setPartyId(partyId);
        record.setBranchId(branchId);
        record.setApplyUserId(applyUserId);
        record.setUserId(userId);
        record.setUserType(userType);
        record.setType(type);
        record.setStage(stage);
        record.setRemark(remark);
        record.setStatus(status);
        record.setCreateTime(new Date());
        record.setIp(IpUtils.getRealIp(request));

        applyApprovalLogMapper.insertSelective(record);
    }

    public List<ApplyApprovalLog> find(int recordId, byte type){

        ApplyApprovalLogExample example = new ApplyApprovalLogExample();
        example.createCriteria().andTypeEqualTo(type).andRecordIdEqualTo(recordId);
        example.setOrderByClause("id asc");

        return applyApprovalLogMapper.selectByExample(example);
    }

}
