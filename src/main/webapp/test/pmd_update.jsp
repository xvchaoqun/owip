<%@ page import="domain.pmd.PmdBranch" %>
<%@ page import="domain.pmd.PmdBranchExample" %>
<%@ page import="domain.pmd.PmdMonth" %>
<%@ page import="domain.pmd.PmdMonthExample" %>
<%@ page import="domain.pmd.PmdParty" %>
<%@ page import="domain.pmd.PmdPartyExample" %>
<%@ page import="persistence.pmd.PmdBranchMapper" %>
<%@ page import="persistence.pmd.PmdMonthMapper" %>
<%@ page import="persistence.pmd.PmdPartyMapper" %>
<%@ page import="persistence.pmd.common.IPmdMapper" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page import="java.util.List" %>
<%@ page import="persistence.pmd.common.PmdReportBean" %>
<%@ page import="sys.utils.JSONUtils" %>
<%@ page import="sys.utils.DateUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    PmdMonthMapper pmdMonthMapper = CmTag.getBean(PmdMonthMapper.class);
    PmdPartyMapper pmdPartyMapper = CmTag.getBean(PmdPartyMapper.class);
    PmdBranchMapper pmdBranchMapper = CmTag.getBean(PmdBranchMapper.class);
    IPmdMapper iPmdMapper = CmTag.getBean(IPmdMapper.class);

    PmdMonthExample example = new PmdMonthExample();
    example.setOrderByClause("id asc");
    List<PmdMonth> pmdMonths = pmdMonthMapper.selectByExample(example);

    for(PmdMonth pmdMonth: pmdMonths){
        int monthId = pmdMonth.getId();

        PmdPartyExample example1 = new PmdPartyExample();
        example1.createCriteria().andMonthIdEqualTo(monthId);
        List<PmdParty> pmdPartyList = pmdPartyMapper.selectByExample(example1);

        for (PmdParty pmdParty : pmdPartyList) {


            int partyId = pmdParty.getPartyId();


            PmdReportBean r1 = null;

            if(CmTag.isDirectBranch(partyId)) {
                r1 = iPmdMapper.getBranchPmdReportBean(monthId, partyId, null);
            }else {

                PmdBranchExample example2 = new PmdBranchExample();
                example2.createCriteria().andMonthIdEqualTo(monthId).andPartyIdEqualTo(partyId);
                List<PmdBranch> pmdBranchList = pmdBranchMapper.selectByExample(example2);

                for (PmdBranch pmdBranch : pmdBranchList) {

                    int branchId = pmdBranch.getBranchId();
                    PmdReportBean r2 = iPmdMapper.getBranchPmdReportBean(monthId, partyId, branchId);

                    PmdBranch record = new PmdBranch();
                    record.setId(pmdBranch.getId());
                    /*record.setOnlineRealPay(r2.getOnlineRealPay());
                    record.setOnlineRealDelayPay(r2.getOnlineRealDelayPay());
                    record.setCashRealPay(r2.getCashRealPay());
                    record.setCashRealDelayPay(r2.getCashRealDelayPay());
                    record.setRealPay(r2.getRealPay());*/
                    record.setOnlineFinishMemberCount(r2.getOnlineFinishMemberCount());

                    out.println("---更新支部---" + pmdBranch.getBranchName());
                    //out.println(JSONUtils.toString(record, false));
                    pmdBranchMapper.updateByPrimaryKeySelective(record);
                }

                r1 = iPmdMapper.getPartyPmdReportBean(monthId, partyId);
            }


            PmdParty record = new PmdParty();
            record.setId(pmdParty.getId());
            /*record.setOnlineRealPay(r1.getOnlineRealPay());
            record.setOnlineRealDelayPay(r1.getOnlineRealDelayPay());
            record.setCashRealPay(r1.getCashRealPay());
            record.setCashRealDelayPay(r1.getCashRealDelayPay());
            record.setRealPay(r1.getRealPay());*/
            record.setOnlineFinishMemberCount(r1.getOnlineFinishMemberCount());

            out.println("===更新党委===" + pmdParty.getPartyName());
            //out.println( pmdParty.getPartyName() + ":" + JSONUtils.toString(record, false));
            pmdPartyMapper.updateByPrimaryKeySelective(record);
        }

        PmdReportBean r = iPmdMapper.getOwPmdReportBean(monthId);

        PmdMonth record = new PmdMonth();
        record.setStatus(pmdMonth.getStatus());
        record.setId(pmdMonth.getId());
        /*record.setOnlineRealPay(r.getOnlineRealPay());
        record.setOnlineRealDelayPay(r.getOnlineRealDelayPay());
        record.setCashRealPay(r.getCashRealPay());
        record.setCashRealDelayPay(r.getCashRealDelayPay());
        record.setRealPay(r.getRealPay());*/
        record.setOnlineFinishMemberCount(r.getOnlineFinishMemberCount());

        out.println("<更新月份>" + DateUtils.formatDate(pmdMonth.getPayMonth(), "yyyy-MM"));
        //out.println(DateUtils.formatDate(pmdMonth.getPayMonth(), "yyyy-MM") + ":" +JSONUtils.toString(record, false));
        pmdMonthMapper.updateByPrimaryKeySelective(record);
    }

    out.println("[完成]" + DateUtils.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
%>
