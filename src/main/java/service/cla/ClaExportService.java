package service.cla;

import domain.cadre.CadreView;
import domain.cla.ClaApply;
import domain.cla.ClaApprovalLog;
import domain.cla.ClaApproverType;
import domain.sys.SysUserView;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.cla.common.ClaApprovalResult;
import service.base.MetaTypeService;
import service.common.FreemarkerService;
import service.sys.SysUserService;
import sys.constants.ClaConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClaExportService extends ClaBaseMapper {

    @Autowired
    private FreemarkerService freemarkerService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private ClaApplyService claApplyService;
    @Autowired
    private ClaApproverTypeService claApproverTypeService;

    // 导出申请表
    public void process(int applyId, Writer out) throws IOException, TemplateException {

        ClaApply claApply = claApplyMapper.selectByPrimaryKey(applyId);
        Integer cadreId = claApply.getCadreId();

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        SysUserView uv = sysUserService.findById(cadre.getUserId());

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("sysUser", uv);
        dataMap.put("cadre", cadre);
        dataMap.put("claApply", claApply);

        dataMap.put("gender", uv.getGender()==null?"":SystemConstants.GENDER_MAP.get(uv.getGender()));
        dataMap.put("adminLevel", metaTypeService.getName(cadre.getTypeId()));

        //dataMap.put("day", DateUtils.getDayCountBetweenDate(claApply.getStartDate(), claApply.getEndDate()));
        dataMap.put("reason", StringUtils.replace(claApply.getReason(), "+++", ","));

        dataMap.put("schoolName", CmTag.getSysConfig().getSchoolName());

        Map<Integer, ClaApprovalResult> approvalResultMap = claApplyService.getApprovalResultMap(applyId);
        Map<Integer, ClaApproverType> approverTypeMap = claApproverTypeService.findAll();

        // 分管校领导
        boolean hasLeader = false;
        String leaderName = null;
        String leaderRemark = null;
        Date leaderApprovalDate = null;

        // 书记
        String secretaryName = null;
        String secretaryRemark = null;
        Date secretaryApprovalDate = null;

        // 校长
        String masterName = null;
        String masterRemark = null;
        Date masterApprovalDate = null;

        for (Integer key : approvalResultMap.keySet()) {
            ClaApprovalResult approvalResult = approvalResultMap.get(key);
            if(key>0&& (approvalResult.getValue()==null || approvalResult.getValue()!=-1)) {
                ClaApproverType approverType = approverTypeMap.get(key);
                if(approverType.getType()== ClaConstants.CLA_APPROVER_TYPE_LEADER) {
                    hasLeader = true;
                    ClaApprovalLog approvalLog = approvalResult.getApprovalLog();
                    if(approvalLog!=null) {
                        Integer userId = approvalLog.getUserId();
                        SysUserView _uv = sysUserService.findById(userId);
                        leaderName =_uv.getRealname();
                        leaderRemark = approvalLog.getRemark();
                        leaderApprovalDate = approvalLog.getCreateTime();
                    }
                }else if(approverType.getType()==ClaConstants.CLA_APPROVER_TYPE_SECRETARY) {
                    ClaApprovalLog approvalLog = approvalResult.getApprovalLog();
                    if(approvalLog!=null) {
                        Integer userId = approvalLog.getUserId();
                        SysUserView _uv = sysUserService.findById(userId);
                        secretaryName =_uv.getRealname();
                        secretaryRemark = approvalLog.getRemark();
                        secretaryApprovalDate = approvalLog.getCreateTime();
                    }
                }else if(approverType.getType()==ClaConstants.CLA_APPROVER_TYPE_MASTER){
                    ClaApprovalLog approvalLog = approvalResult.getApprovalLog();
                    if(approvalLog!=null) {
                        Integer userId = approvalLog.getUserId();
                        SysUserView _uv = sysUserService.findById(userId);
                        masterName =_uv.getRealname();
                        masterRemark = approvalLog.getRemark();
                        masterApprovalDate = approvalLog.getCreateTime();
                    }
                }
            }
        }

        // 本年度申请记录
        int currentYear = DateUtils.getYear(claApply.getApplyDate());
        List<ClaApply> claApplys = claApplyService.getAnnualApplyList(cadreId, currentYear);
        int claApplyCount = claApplys.size();
        for (int i = 0; i < 4 - claApplyCount; i++) {
            claApplys.add(new ClaApply());
        }
        dataMap.put("claApplys", claApplys);
        dataMap.put("claApplyCount", claApplyCount);

        dataMap.put("hasLeader", hasLeader);
        dataMap.put("leaderName", leaderName);
        dataMap.put("leaderRemark", leaderRemark);
        dataMap.put("leaderApprovalDate", leaderApprovalDate);

        dataMap.put("secretaryName", secretaryName);
        dataMap.put("secretaryRemark", secretaryRemark);
        dataMap.put("secretaryApprovalDate", secretaryApprovalDate);

        dataMap.put("masterName", masterName);
        dataMap.put("masterRemark", masterRemark);
        dataMap.put("masterApprovalDate", masterApprovalDate);

        freemarkerService.process("/cla/apply.ftl", dataMap, out);
    }
}
