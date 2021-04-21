package service.parttime;

import domain.cadre.CadreView;
import domain.member.MemberView;
import domain.parttime.*;
import domain.pm.PmMeeting;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.base.MetaTypeService;
import service.common.FreemarkerService;
import service.sys.SysUserService;
import sys.constants.ParttimeConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sys.constants.PmConstants.PARTY_MEETING_BRANCH_ACTIVITY;

@Service
public class ParttimeExportService extends ParttimeBaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private ParttimeApplyService parttimeApplyService;
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
        ParttimeApplyFileExample fileExample = new ParttimeApplyFileExample();
        fileExample.createCriteria().andApplyIdEqualTo(parttimeApply.getId());
        List<ParttimeApplyFile> list = parttimeApplyFileMapper.selectByExample(fileExample);

        ParttimeApprovalLogExample parttimeApprovalLogExample = new ParttimeApprovalLogExample();
        parttimeApprovalLogExample.setOrderByClause("create_time desc");
        parttimeApprovalLogExample.createCriteria().andApplyIdEqualTo(parttimeApply.getId());
        List<ParttimeApprovalLog> list2 = parttimeApprovalLogMapper.selectByExample(parttimeApprovalLogExample);

        if (cadreView != null) {
            SysUserView sysUserView = cadreView.getUser();
            String userName = sysUserView.getRealname();
            dataMap.put("username", userName);
            String code = sysUserView.getCode();
            dataMap.put("code", code);
            dataMap.put("sex", sysUserView.getGender() == 1 ? "男" : "女");
            dataMap.put("idCard", sysUserView.getIdcard());
            dataMap.put("phone", sysUserView.getPhone());
            dataMap.put("isFirst", parttimeApply.getIsFirst() ? "是" : "否");
            dataMap.put("background", parttimeApply.getBackground() ? "是" : "否");
            dataMap.put("hasPay", parttimeApply.getHasPay() ? "是": "否");
            dataMap.put("balance", parttimeApply.getBalance());
            dataMap.put("reason", parttimeApply.getReason());
            dataMap.put("remark", parttimeApply.getRemark());
            if (list.size() > 0) {
                dataMap.put("files", list.get(0).getFileName());
            }
            dataMap.put("username", userName);
        }

        Map<Integer, ParttimeApproverType> map = parttimeApproverTypeService.findAll();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        for (ParttimeApprovalLog parttimeApprovalLog: list2) {
            System.out.println(parttimeApprovalLog.getTypeId());
            if (parttimeApprovalLog != null) {
                String remark = parttimeApprovalLog.getRemark();
                SysUserView sysUserView = sysUserService.findById(parttimeApprovalLog.getUserId());
                Integer typeId = parttimeApprovalLog.getTypeId();
                if (typeId != null) {
                    String name = map.get(typeId).getName();
                    if (StringUtils.isNotBlank(name)) {
                        if (name.equals(ParttimeConstants.PARTTIME_APPROVER_TYPE_MAP.get(ParttimeConstants.PARTTIME_APPROVER_TYPE_UNIT))) {
                            dataMap.put("approvalOrganze", remark);
                            dataMap.put("approvalOrganzeUser", sysUserView.getRealname());
                            dataMap.put("approvalOrganzeDate", simpleDateFormat.format(parttimeApprovalLog.getCreateTime()));
                        } else if (name.equals(ParttimeConstants.PARTTIME_APPROVER_TYPE_MAP.get(ParttimeConstants.PARTTIME_APPROVER_TYPE_FOREIGN))) {
                            dataMap.put("approvalForign", remark);
                            dataMap.put("approvalForignUser", sysUserView.getRealname());
                            dataMap.put("approvalForignDate", simpleDateFormat.format(parttimeApprovalLog.getCreateTime()));
                        } else if (name.equals(ParttimeConstants.PARTTIME_APPROVER_TYPE_MAP.get(ParttimeConstants.PARTTIME_APPROVER_TYPE_LEADER))) {
                            dataMap.put("approvalLeader", remark);
                            dataMap.put("approvalLeaderUser", sysUserView.getRealname());
                            dataMap.put("approvalLeaderDate", simpleDateFormat.format(parttimeApprovalLog.getCreateTime()));
                        }
                    }
                }

                if (parttimeApprovalLog.getOdType() == ParttimeConstants.PARTTIME_APPROVER_LOG_OD_TYPE_FIRST) {
                    dataMap.put("approvalFirst", remark);
                    dataMap.put("approvalFirstUser", sysUserView.getRealname());
                    dataMap.put("approvalFirstDate", simpleDateFormat.format(parttimeApprovalLog.getCreateTime()));
                } else if (parttimeApprovalLog.getOdType() == ParttimeConstants.PARTTIME_APPROVER_LOG_OD_TYPE_LAST) {
                    dataMap.put("approvalEnd", remark);
                    dataMap.put("approvalEndUser", sysUserView.getRealname());
                    dataMap.put("approvalEndDate", simpleDateFormat.format(parttimeApprovalLog.getCreateTime()));
                }
            }
        }
        freemarkerService.process(path, dataMap, out);
        out.close();
    }

}
