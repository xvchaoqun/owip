<%@ page import="org.apache.shiro.web.servlet.ShiroHttpServletRequest" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%
    HttpSession httpSession = ((HttpServletRequest) ((ShiroHttpServletRequest) request).getRequest()).getSession();
    Integer reg = (Integer) httpSession.getAttribute("reg");
    request.setAttribute("reg", reg);
%>
<div class="widget-box" style="width: 800px;margin: 0 auto">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 账号注册信息</h4>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <c:if test="${reg==1}">
            <div class="well green well-lg center bolder large" style="font-size: 24px;">您注册的账号是：${memberReg.username}，请牢记。</div>
            </c:if>
            <table class="table table-bordered table-striped">
                <tbody>
                <tr>
                    <td class="bg-right">
                        注册账号
                    </td>
                    <td class="bg-left">
                        ${memberReg.username}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        类别
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${USER_TYPE_MAP.get(memberReg.type)}
                    </td>
                    </tr>
                <tr>
                    <td class="bg-right">
                        ${(memberReg.type==USER_TYPE_JZG)?"工作证号":"学号"}
                        <br/>（仅用于本系统）
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${memberReg.code}
                    </td>
                    </tr>
                <tr>
                    <td class="bg-right">
                        真实姓名
                    </td>
                    <td class="bg-left" style="min-width: 120px">
                        ${memberReg.realname}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        联系党委
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                            ${cm:displayParty(memberReg.partyId, null)}
                    </td>
                    </tr>
                <tr>
                    <td class="bg-right">
                        身份证号码
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        <t:mask src="${memberReg.idcard}" type="idCard"/>
                    </td>
                    </tr>
                <tr>
                    <td class="bg-right">
                        手机号码
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        <t:mask src="${memberReg.phone}" type="mobile"/>
                    </td>
                    </tr>
                <tr>
                    <td class="bg-right">
                        状态
                    </td>
                    <td class="bg-left" style="min-width: 80px;background-color: #f2dede !important">
                        请等待${_p_partyName}审核通过后使用
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
