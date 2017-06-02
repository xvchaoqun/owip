<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2015/12/7
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 党员退休</h4>

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
                        教工号
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${sysUser.realname}
                    </td>
                    <td class="bg-right">
                        退休后所在分党委
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${party.name}
                    </td>
                    <c:if test="${not empty branch}">
                    <td class="bg-right">
                        退休后所在党支部
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                       ${branch.name}
                    </td>
                    </c:if>
                    <td class="bg-right">
                        填报时间
                    </td>
                    <td class="bg-left" style="min-width: 120px">
                        ${cm:formatDate(retireApply.createTime,'yyyy-MM-dd HH:mm')}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        审核状态
                    </td>
                    <td class="bg-left" style="min-width: 80px" colspan="7">
                        ${retireApply.status==0?"未审核":"已审核"}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
