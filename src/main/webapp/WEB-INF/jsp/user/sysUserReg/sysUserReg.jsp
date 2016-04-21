<%@ page import="sys.constants.SystemConstants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 账号注册信息</h4>
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
                        注册账号
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${sysUserReg.username}
                    </td>
                    <td class="bg-right">
                        类别
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${USER_TYPE_MAP.get(sysUserReg.type)}
                    </td>
                    <td class="bg-right">
                        ${(sysUserReg.type==USER_TYPE_JZG)?"教工号":"学号"}
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${sysUserReg.code}
                    </td>
                    <td class="bg-right">
                        真实姓名
                    </td>
                    <td class="bg-left" style="min-width: 120px">
                        ${sysUserReg.realname}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        所属组织机构
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${partyMap.get(sysUserReg.partyId).name}
                    </td>
                    <td class="bg-right">
                        身份证号码
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${sysUserReg.idcard}
                    </td>
                    <td class="bg-right">
                        手机号码
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${sysUserReg.phone}
                    </td>
                    <td class="bg-right">
                        状态
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        分党委待审核
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
