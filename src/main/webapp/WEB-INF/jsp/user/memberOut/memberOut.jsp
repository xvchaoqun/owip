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
<c:set var="GENDER_MAP" value="<%=SystemConstants.GENDER_MAP%>"/>
<c:set var="MEMBER_POLITICAL_STATUS_MAP" value="<%=SystemConstants.MEMBER_POLITICAL_STATUS_MAP%>"/>
<c:set var="MEMBER_INOUT_TYPE_MAP" value="<%=SystemConstants.MEMBER_INOUT_TYPE_MAP%>"/>
<c:set var="MEMBER_OUT_STATUS_MAP" value="<%=SystemConstants.MEMBER_OUT_STATUS_MAP%>"/>

<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 组织关系转出申请</h4>

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
                        姓名
                    </td>
                    <td style="min-width: 80px">
                        ${memberOut.realname}
                    </td>
                    <td>
                        介绍信编号
                    </td>
                    <td style="min-width: 80px">
                        ${memberOut.code}
                    </td>
                    <td>
                        性别
                    </td>
                    <td style="min-width: 80px">
                       ${GENDER_MAP.get(memberOut.gender)}
                    </td>
                    <td>
                        年龄
                    </td>
                    <td style="min-width: 120px">
                        ${memberOut.age}
                    </td>
                </tr>
                <tr>
                    <td>
                        民族
                    </td>
                    <td style="min-width: 80px">
                        ${memberOut.nation}
                    </td>
                    <td>
                        政治面貌
                    </td>
                    <td style="min-width: 80px">
                        ${MEMBER_POLITICAL_STATUS_MAP.get(memberOut.politicalStatus)}
                    </td>
                    <td>
                        身份证号
                    </td>
                    <td style="min-width: 80px">
                        ${memberOut.idcard}
                    </td>
                    <td>
                        类别
                    </td>
                    <td style="min-width: 120px">
                        ${MEMBER_INOUT_TYPE_MAP.get(memberOut.type)}
                    </td>
                </tr>
                <tr>
                    <td>
                        转入单位抬头
                    </td>
                    <td style="min-width: 80px">
                        ${memberOut.toTitle}
                    </td>
                    <td>
                        转入单位
                    </td>
                    <td style="min-width: 80px">
                        ${memberOut.toUnit}
                    </td>
                    <td>
                        转出单位
                    </td>
                    <td style="min-width: 80px">
                        ${memberOut.fromUnit}
                    </td>
                    <td>
                        转出单位地址
                    </td>
                    <td style="min-width: 120px">
                        ${memberOut.fromAddress}
                    </td>
                </tr>
                <tr>
                    <td>
                        转出单位联系电话
                    </td>
                    <td style="min-width: 80px">
                        ${memberOut.fromPhone}
                    </td>
                    <td>
                        转出单位传真
                    </td>
                    <td style="min-width: 80px">
                        ${memberOut.fromFax}
                    </td>
                    <td>
                        转出单位邮编
                    </td>
                    <td style="min-width: 80px">
                        ${memberOut.fromPostCode}
                    </td>
                    <td>
                        党费缴纳至年月
                    </td>
                    <td style="min-width: 120px">
                            ${cm:formatDate(memberOut.payTime,'yyyy-MM-dd')}
                    </td>
                </tr>
                <tr>
                    <td>
                        介绍信有效期天数
                    </td>
                    <td style="min-width: 80px">
                        ${memberOut.validDays}
                    </td>
                    <td>
                        办理时间
                    </td>
                    <td style="min-width: 80px">
                            ${cm:formatDate(memberOut.handleTime,'yyyy-MM-dd')}
                    </td>
                    <td>
                        状态
                    </td>
                    <td style="min-width: 80px" colspan="3">
                        ${MEMBER_OUT_STATUS_MAP.get(memberOut.status)}

                        <c:if test="${memberOut.status==0}">
                            <small>
                                <button class="btn btn-white btn-warning btn-mini" onclick="_applyBack()">
                                    <i class="fa fa-undo"></i>
                                    撤销
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
                $.post("${ctx}/user/memberOut_back",function(ret){

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