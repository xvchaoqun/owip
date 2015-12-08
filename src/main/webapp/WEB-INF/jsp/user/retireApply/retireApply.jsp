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
<c:set var="OR_STATUS_MAP" value="<%=SystemConstants.OR_STATUS_MAP%>"/>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 党员退休</h4>

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
                    <td>
                        教工号
                    </td>
                    <td style="min-width: 80px">
                        ${sysUser.realname}
                    </td>
                    <td>
                        退休后所在分党委
                    </td>
                    <td style="min-width: 80px">
                        ${party.name}
                    </td>
                    <c:if test="${not empty branch}">
                    <td>
                        退休后所在党支部
                    </td>
                    <td style="min-width: 80px">
                       ${branch.name}
                    </td>
                    </c:if>
                    <td>
                        填报时间
                    </td>
                    <td style="min-width: 120px">
                        ${cm:formatDate(retireApply.createTime,'yyyy-MM-dd HH:mm')}
                    </td>
                </tr>
                <tr>
                    <td>
                        审核状态
                    </td>
                    <td style="min-width: 80px" colspan="7">
                        ${retireApply.status==0?"未审核":"已审核"}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
