package service.abroad;

import domain.abroad.*;
import domain.cadre.Cadre;
import domain.cadre.CadreExample;
import domain.sys.SysUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sys.constants.CadreConstants;

import java.util.*;

/**
 * Created by lm on 2018/7/20.
 */
@Service
public class AbroadService extends AbroadBaseMapper {

    @Autowired
    ApplicatTypeService applicatTypeService;
    @Autowired
    ApproverTypeService approverTypeService;
    @Autowired
    ApplySelfService applySelfService;

    // 某个申请人对应的审批人类型及所有的审批人
    public Map<Integer, List<SysUserView>> getCadreApproverListMap(int cadreId){

        Map<Integer, ApproverType> approverTypeMap = approverTypeService.findAll();
        Set<Integer> needApprovalTypeSet = needApprovalTypeSet(cadreId);

        Map<Integer, List<SysUserView>> approverListMap = new LinkedHashMap<>();
        for (ApproverType approverType : approverTypeMap.values()) {

            if(needApprovalTypeSet.contains(approverType.getId())) {
                List<SysUserView> approvers = applySelfService.findApprovers(cadreId, approverType.getId());
                approverListMap.put(approverType.getId(), approvers);
            }else{
                approverListMap.put(approverType.getId(), null);
            }
        }

        return approverListMap;
    }

    // 所有干部对应的审批人类型及所有的审批人
    public Map<Integer, Map<Integer, List<SysUserView>>> getCadreApproverListMap(){

        Map<Integer,  Map<Integer, List<SysUserView>>> cadreApproverListMap = new LinkedHashMap<>();

        CadreExample example = new CadreExample();
        example.createCriteria().andStatusEqualTo(CadreConstants.CADRE_STATUS_MIDDLE);
        example.setOrderByClause("sort_order desc");
        List<Cadre> cadres = cadreMapper.selectByExample(example);
        for (Cadre cadre : cadres) {

            cadreApproverListMap.put(cadre.getId(), getCadreApproverListMap(cadre.getId()));
        }

        return cadreApproverListMap;
    }

    // 查找申请人对应的审批人类型
    public Set<Integer> needApprovalTypeSet(int cadreId){

        Set<Integer> needApprovalTypeSet = new HashSet<>();

        Integer applicatTypeId = null;
        {   // 查询申请人身份
            ApplicatCadreExample example = new ApplicatCadreExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            List<ApplicatCadre> applicatCadres = applicatCadreMapper.selectByExample(example);
            if (applicatCadres.size() == 0) {
                return needApprovalTypeSet;// 异常情况，不允许申请人没有任何身份
            }
            ApplicatCadre applicatCadre = applicatCadres.get(0);
            applicatTypeId = applicatCadre.getTypeId();
        }
        List<ApprovalOrder> approvalOrders = null;
        {  // 所需要的审批人身份列表
            ApprovalOrderExample example = new ApprovalOrderExample();
            example.createCriteria().andApplicatTypeIdEqualTo(applicatTypeId);
            example.setOrderByClause("sort_order desc");
            approvalOrders = approvalOrderMapper.selectByExample(example);
            for (ApprovalOrder approvalOrder : approvalOrders) {
                needApprovalTypeSet.add(approvalOrder.getApproverTypeId());
            }
        }

        return needApprovalTypeSet;
    }
}
