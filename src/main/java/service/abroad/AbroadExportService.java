package service.abroad;

import domain.abroad.ApplySelf;
import domain.abroad.ApprovalLog;
import domain.abroad.ApproverType;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.abroad.common.ApprovalResult;
import service.base.MetaTypeService;
import service.common.FreemarkerService;
import service.sys.SysUserService;
import sys.constants.AbroadConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

@Service
public class AbroadExportService extends AbroadBaseMapper {

    @Autowired
    private FreemarkerService freemarkerService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private ApplySelfService applySelfService;
    @Autowired
    private ApproverTypeService approverTypeService;

    // 导出申请表
    public void process(int applySelfId, Writer out) throws IOException, TemplateException {

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfId);
        Integer cadreId = applySelf.getCadreId();

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        SysUserView uv = sysUserService.findById(cadre.getUserId());

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("sysUser", uv);
        dataMap.put("cadre", cadre);
        dataMap.put("applySelf", applySelf);

        dataMap.put("gender", uv.getGender()==null?"":SystemConstants.GENDER_MAP.get(uv.getGender()));
        dataMap.put("adminLevel", metaTypeService.getName(cadre.getAdminLevel()));

        //dataMap.put("day", DateUtils.getDayCountBetweenDate(applySelf.getStartDate(), applySelf.getEndDate()));
        dataMap.put("reason", StringUtils.replace(applySelf.getReason(), "+++", ","));

        List<String> needPassports = new ArrayList<>();
        String _needPassports = applySelf.getNeedPassports();
        for (String _typeId : _needPassports.split(",")) {
            needPassports.add(metaTypeService.getName(Integer.valueOf(_typeId)));
        }
        dataMap.put("needPassports", StringUtils.join(needPassports, ","));

        dataMap.put("schoolName", CmTag.getSysConfig().getSchoolName());

        Map<Integer, ApprovalResult> approvalResultMap = applySelfService.getApprovalResultMap(applySelfId);
        Map<Integer, ApproverType> approverTypeMap = approverTypeService.findAll();

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
            ApprovalResult approvalResult = approvalResultMap.get(key);
            if(key>0&& (approvalResult.getValue()==null || approvalResult.getValue()!=-1)) {
                ApproverType approverType = approverTypeMap.get(key);
                if(approverType.getType()== AbroadConstants.ABROAD_APPROVER_TYPE_LEADER) {
                    hasLeader = true;
                    ApprovalLog approvalLog = approvalResult.getApprovalLog();
                    if(approvalLog!=null) {
                        Integer userId = approvalLog.getUserId();
                        SysUserView _uv = sysUserService.findById(userId);
                        leaderName =_uv.getRealname();
                        leaderRemark = approvalLog.getRemark();
                        leaderApprovalDate = approvalLog.getCreateTime();
                    }
                }else if(approverType.getType()==AbroadConstants.ABROAD_APPROVER_TYPE_SECRETARY) {
                    ApprovalLog approvalLog = approvalResult.getApprovalLog();
                    if(approvalLog!=null) {
                        Integer userId = approvalLog.getUserId();
                        SysUserView _uv = sysUserService.findById(userId);
                        secretaryName =_uv.getRealname();
                        secretaryRemark = approvalLog.getRemark();
                        secretaryApprovalDate = approvalLog.getCreateTime();
                    }
                }else if(approverType.getType()==AbroadConstants.ABROAD_APPROVER_TYPE_MASTER){
                    ApprovalLog approvalLog = approvalResult.getApprovalLog();
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
        int currentYear = DateUtils.getYear(applySelf.getApplyDate());
        List<ApplySelf> applySelfs = applySelfService.getAnnualApplyList(cadreId, currentYear);
        int applySelfCount = applySelfs.size();
        for (int i = 0; i < 4 - applySelfCount; i++) {
            applySelfs.add(new ApplySelf());
        }
        dataMap.put("applySelfs", applySelfs);
        dataMap.put("applySelfCount", applySelfCount);

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

        freemarkerService.process("/abroad/apply_self2.ftl", dataMap, out);
    }
}
