package service.sys;

import domain.sys.SysApprovalLog;
import domain.sys.SysApprovalLogExample;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import shiro.ShiroHelper;
import sys.utils.ContextHelper;
import sys.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class SysApprovalLogService extends BaseMapper {
    
    public void add(int recordId, int applyUserId, byte userType, byte type,
                                String stage, byte status, String remark){

        HttpServletRequest request = ContextHelper.getRequest();

        SysApprovalLog record = new SysApprovalLog();
        record.setRecordId(recordId);
        record.setApplyUserId(applyUserId);
        record.setUserId(ShiroHelper.getCurrentUserId());
        record.setUserType(userType);
        record.setType(type);
        record.setStage(stage);
        record.setRemark(remark);
        record.setStatus(status);
        record.setCreateTime(new Date());
        record.setIp(IpUtils.getRealIp(request));

        sysApprovalLogMapper.insertSelective(record);
    }

    public List<SysApprovalLog> find(int recordId, byte type){

        SysApprovalLogExample example = new SysApprovalLogExample();
        example.createCriteria().andTypeEqualTo(type).andRecordIdEqualTo(recordId);
        example.setOrderByClause("id asc");

        return sysApprovalLogMapper.selectByExample(example);
    }

}
