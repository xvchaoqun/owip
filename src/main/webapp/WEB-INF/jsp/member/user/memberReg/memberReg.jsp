<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 账号注册信息</h4>
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
                        注册账号
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${memberReg.username}
                    </td>
                    <td class="bg-right">
                        类别
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${USER_TYPE_MAP.get(memberReg.type)}
                    </td>
                    <td class="bg-right">
                        ${(memberReg.type==USER_TYPE_JZG)?"教工号":"学号"}
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${memberReg.code}
                    </td>
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
                    <td class="bg-right">
                        身份证号码
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${memberReg.idcard}
                    </td>
                    <td class="bg-right">
                        手机号码
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${memberReg.phone}
                    </td>
                    <td class="bg-right">
                        状态
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${_p_partyName}待审核
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
