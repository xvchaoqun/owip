package service.parttime;

import domain.cadre.*;
import domain.parttime.*;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.common.FreemarkerService;
import service.sys.SysUserService;
import sys.constants.ParttimeConstants;
import sys.tags.CmTag;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParttimeExportService extends ParttimeBaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ParttimeApproverTypeService parttimeApproverTypeService;
    @Autowired
    private FreemarkerService freemarkerService;

    //导出word
    @Transactional
    public void getExportWord(Integer id, Writer out)throws Exception {

        Map<String, Object> dataMap = new HashMap<String, Object>();
        String path="parttime/parttime_export.ftl";
        ParttimeApply parttimeApply = parttimeApplyMapper.selectByPrimaryKey(id);
        CadreView cadreView = parttimeApply.getCadre();

        ParttimeApprovalLogExample parttimeApprovalLogExample = new ParttimeApprovalLogExample();
        parttimeApprovalLogExample.setOrderByClause("create_time desc");
        parttimeApprovalLogExample.createCriteria().andApplyIdEqualTo(parttimeApply.getId());
        List<ParttimeApprovalLog> list2 = parttimeApprovalLogMapper.selectByExample(parttimeApprovalLogExample);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        dataMap.put("applyDate", simpleDateFormat.format(parttimeApply.getApplyTime()));
        dataMap.put("schoolName", CmTag.getSysConfig().getSchoolName());
        dataMap.put("unitName", cadreView.getUnitName());
        dataMap.put("type", parttimeApply.getType());
        dataMap.put("startTime", simpleDateFormat.format(parttimeApply.getStartTime()));
        dataMap.put("endTime", simpleDateFormat.format(parttimeApply.getEndTime()));

        if (cadreView != null) {
            CadreCompanyExample cadreCompanyExample = new CadreCompanyExample();
            cadreCompanyExample.setOrderByClause("start_time desc");
            cadreCompanyExample.createCriteria().andCadreIdEqualTo(cadreView.getId());
            List<CadreCompany> cadreCompanys = cadreCompanyMapper.selectByExample(cadreCompanyExample);
            List<CadreCompany> data = new ArrayList<>();
            if (cadreCompanys.size() > 0) {
                for (CadreCompany cadreCompany: cadreCompanys) {
                    cadreCompany.setPost((cadreCompany.getUnit() == null ? "" : cadreCompany.getUnit()) + (cadreCompany.getPost() == null ? "" : cadreCompany.getPost()));
                    data.add(cadreCompany);
                }
            }
            dataMap.put("total", data.size());
            dataMap.put("cadreParttimes", data);
            dataMap.put("unitPost", cadreView.getTitle());
            dataMap.put("post", cadreView.getIsPrincipal());
            SysUserView sysUserView = cadreView.getUser();
            String userName = sysUserView.getRealname();
            dataMap.put("userName", userName);
            String code = sysUserView.getCode();
            dataMap.put("code", code);
            dataMap.put("phone", cadreView.getMobile());
            dataMap.put("isFirst", parttimeApply.getIsFirst());
            dataMap.put("background", parttimeApply.getBackground());
            dataMap.put("hasPay", parttimeApply.getHasPay());
            dataMap.put("balance", parttimeApply.getBalance());
            dataMap.put("reason", parttimeApply.getReason());
            dataMap.put("remark", parttimeApply.getRemark());
            dataMap.put("title", parttimeApply.getTitle());
        }

        Map<Integer, ParttimeApproverType> map = parttimeApproverTypeService.findAll();
        for (ParttimeApprovalLog parttimeApprovalLog: list2) {
            if (parttimeApprovalLog != null) {
                String remark = parttimeApprovalLog.getRemark();
                SysUserView sysUserView = sysUserService.findById(parttimeApprovalLog.getUserId());
                Integer typeId = parttimeApprovalLog.getTypeId();
                boolean status = parttimeApprovalLog.getStatus();
                if (typeId != null) {
                    String name = map.get(typeId).getName();
                    if (StringUtils.isNotBlank(name)) {
                        if (name.equals(ParttimeConstants.PARTTIME_APPROVER_TYPE_MAP.get(ParttimeConstants.PARTTIME_APPROVER_TYPE_UNIT))) {
                            dataMap.put("approvalOrganze", remark);
                            dataMap.put("approvalOrganzeUser", sysUserView.getRealname());
                            dataMap.put("approvalOrganzeDate", simpleDateFormat.format(parttimeApprovalLog.getCreateTime()));
                            dataMap.put("approvalOrganzeResult", status ? "通过" : "未通过");
                        } else if (name.equals(ParttimeConstants.PARTTIME_APPROVER_TYPE_MAP.get(ParttimeConstants.PARTTIME_APPROVER_TYPE_FOREIGN))) {
                            dataMap.put("approvalForign", remark);
                            dataMap.put("approvalForignUser", sysUserView.getRealname());
                            dataMap.put("approvalForignDate", simpleDateFormat.format(parttimeApprovalLog.getCreateTime()));
                            dataMap.put("approvalForignResult", status ? "通过" : "未通过");
                        } else if (name.equals(ParttimeConstants.PARTTIME_APPROVER_TYPE_MAP.get(ParttimeConstants.PARTTIME_APPROVER_TYPE_LEADER))) {
                            dataMap.put("approvalLeader", remark);
                            dataMap.put("approvalLeaderUser", sysUserView.getRealname());
                            dataMap.put("approvalLeaderDate", simpleDateFormat.format(parttimeApprovalLog.getCreateTime()));
                            dataMap.put("approvalLeaderResult", status ? "通过" : "未通过");
                        }
                    }
                }
                if (parttimeApprovalLog.getOdType() == ParttimeConstants.PARTTIME_APPROVER_LOG_OD_TYPE_FIRST) {
                    dataMap.put("approvalFirst", remark);
                    dataMap.put("approvalFirstUser", sysUserView.getRealname());
                    dataMap.put("approvalFirstDate", simpleDateFormat.format(parttimeApprovalLog.getCreateTime()));
                    dataMap.put("approvalFirstResult", status ? "通过" : "未通过");
                } else if (parttimeApprovalLog.getOdType() == ParttimeConstants.PARTTIME_APPROVER_LOG_OD_TYPE_LAST) {
                    dataMap.put("approvalEnd", remark);
                    dataMap.put("approvalEndUser", sysUserView.getRealname());
                    dataMap.put("approvalEndDate", simpleDateFormat.format(parttimeApprovalLog.getCreateTime()));
                    dataMap.put("approvalEndResult", status ? "通过" : "未通过");
                }
            }
        }
        freemarkerService.process(path, dataMap, out);
        out.close();
    }

}
