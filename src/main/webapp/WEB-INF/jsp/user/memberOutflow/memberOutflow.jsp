<%@ page import="sys.constants.SystemConstants" %>
<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2015/12/7
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_TYPE_MAP" value="<%=SystemConstants.MEMBER_TYPE_MAP%>"/>
<c:set var="OR_STATUS_MAP" value="<%=SystemConstants.OR_STATUS_MAP%>"/>
<c:set var="MEMBER_OUTFLOW_STATUS_MAP" value="<%=SystemConstants.MEMBER_OUTFLOW_STATUS_MAP%>"/>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 党员流出申请</h4>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
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
                        ${sysUser.realname}
                    </td>
                    <td class="bg-right">
                        类别
                    </td>
                    <td  class="bg-left" style="min-width: 80px">
                        ${MEMBER_TYPE_MAP.get(memberOutflow.type)}
                    </td>
                    <td class="bg-right">
                        所属组织机构
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${memberOutflow.partyName}
                        <c:if test="${not empty memberOutflow.branchName}">
                        -${memberOutflow.branchName}
                            </c:if>
                    </td>
                    <td class="bg-right">
                        原职业
                    </td>
                    <td class="bg-left" style="min-width: 120px">
                        ${jobMap.get(memberOutflow.originalJob).name}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">外出流向</td>
                    <td class="bg-left">
                        ${flowDirectionMap.get(memberOutflow.direction).name}
                    </td>
                    <td class="bg-right">
                        流出时间
                    </td>
                    <td class="bg-left">${cm:formatDate(memberOutflow.flowTime,'yyyy-MM-dd')}
                    </td>
                    <td class="bg-right">
                        流出省份
                    </td>
                    <td class="bg-left">
                        ${locationMap.get(memberOutflow.province).name}
                    </td>
                    <td class="bg-right">
                        流出原因
                    </td>
                    <td class="bg-left">
                        ${memberOutflow.reason}
                    </td>
                </tr>
                <tr>
                <td class="bg-right">
                    是否持有《中国共产党流动党员活动证》
                </td>
                <td class="bg-left">
                    ${memberOutflow.hasPapers?"是":"否"}
                </td>
                <td class="bg-right">组织关系状态</td>
                <td class="bg-left">
                    ${OR_STATUS_MAP.get(memberOutflow.orStatus)}
                </td>
                <td class="bg-right">提交时间</td>
                <td class="bg-left">
                    ${cm:formatDate(memberOutflow.createTime,'yyyy-MM-dd HH:mm')}
                </td>
                <td class="bg-right">申请状态</td>
                <td  class="bg-left">
                    ${MEMBER_OUTFLOW_STATUS_MAP.get(memberOutflow.status)}
                        &nbsp;
                        <c:if test="${memberOutflow.status==0}">
                            <small>
                                <button class="btn btn-white btn-warning" onclick="_applyBack()">
                                    <i class="fa fa-undo"></i>
                                    撤销申请
                                </button>
                            </small>
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
                $.post("${ctx}/user/memberOutflow_back",function(ret){

                    if(ret.success){
                        bootbox.alert("撤销成功。",function(){
                            _reload();
                        });
                    }
                });
            }
        });
    }
</script>
