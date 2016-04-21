package service.party;

import domain.ApplyApprovalLog;
import domain.ApplyApprovalLogExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import service.BaseMapper;
import sys.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class ApplyApprovalLogService extends BaseMapper {
    
    public void add(int recordId, int partyId, Integer branchId, int applyUserId,
                                int userId, byte type,
                                String stage, byte status, String remark){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        ApplyApprovalLog record = new ApplyApprovalLog();
        record.setRecordId(recordId);
        record.setPartyId(partyId);
        record.setBranchId(branchId);
        record.setApplyUserId(applyUserId);
        record.setUserId(userId);
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
