<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_INFLOW_OUT_STATUS_MAP" value="<%=MemberConstants.MEMBER_INFLOW_OUT_STATUS_MAP%>"/>
<c:set var="MEMBER_INFLOW_OUT_STATUS_APPLY" value="<%=MemberConstants.MEMBER_INFLOW_OUT_STATUS_APPLY%>"/>

<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 流入党员转出申请</h4>

        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <table class="table table-bordered table-striped">
                <tbody>
                <tr>
                    <td class="bg-right">
                        姓名
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${_user.realname}
                    </td>
                    <td class="bg-right">
                        所属组织机构
                    </td>
                    <td  class="bg-left" style="min-width: 80px">
                            ${cm:displayParty(memberInflow.partyId, memberInflow.branchId)}
                    </td>
                    <td class="bg-right">
                        原职业
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${cm:getMetaType(memberInflow.originalJob).name}
                    </td>
                    <td class="bg-right">
                        流入前所在省份
                    </td>
                    <td class="bg-left" style="min-width: 120px">
                        ${locationMap.get(memberInflow.province).name}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">流入原因</td>
                    <td class="bg-left">
                        ${memberInflow.reason}
                    </td>
                    <td class="bg-right">
                        流入时间
                    </td>
                    <td class="bg-left">${cm:formatDate(memberInflow.flowTime,'yyyy-MM-dd')}
                    </td>
                    <td class="bg-right">
                        入党时间
                    </td>
                    <td class="bg-left">
                        ${cm:formatDate(memberInflow.growTime,'yyyy-MM-dd')}
                    </td>
                    <td class="bg-right">
                        组织关系所在地
                    </td>
                    <td class="bg-left">
                        ${memberInflow.orLocation}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right" colspan="3">
                        是否持有《中国共产党流动党员活动证》
                    </td>
                    <td class="bg-left" colspan="5">
                        ${memberInflow.hasPapers?"是":"否"}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">转出单位</td>
                    <td class="bg-left">
                        ${memberInflow.outUnit}
                    </td>
                    <td class="bg-right">
                        转出地
                    </td>
                    <td class="bg-left">
                        ${locationMap.get(memberInflow.outLocation).name}
                    </td>
                    <td class="bg-right">
                        转出时间
                    </td>
                    <td class="bg-left">
                        ${cm:formatDate(memberInflow.outTime,'yyyy-MM-dd')}
                    </td>
                    <td class="bg-right">
                        转出状态
                    </td>
                    <td class="bg-left" style="min-width: 80px" colspan="3">
                        ${MEMBER_INFLOW_OUT_STATUS_MAP.get(memberInflow.outStatus)}
                        &nbsp;
                        <c:if test="${memberInflow.outStatus==MEMBER_INFLOW_OUT_STATUS_APPLY}">
                            <button class="btn btn-white btn-warning" onclick="_applyBack()">
                                <i class="fa fa-undo"></i>
                                撤销申请
                            </button>
                        </c:if>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script>
    function _applyBack(){
        bootbox.confirm("确定撤销申请吗？", function (result) {
            if(result){
                $.post("${ctx}/user/memberInflowOut_back",function(ret){

                    if(ret.success){
                        SysMsg.success("撤销成功。",function(){
                            $.hashchange();
                        });
                    }
                });
            }
        });
    }
</script>
