<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row footer-margin">
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 组织关系暂留申请</h4>

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
                        ${userBean.realname}
                    </td>
                    <td class="bg-right">
                        学工号
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${userBean.code}
                    </td>
                    <td class="bg-right">
                        性别
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                       ${GENDER_MAP.get(userBean.gender)}
                    </td>
                    <td class="bg-right">
                        年龄
                    </td>
                    <td class="bg-left" style="min-width: 120px">
                            ${cm:intervalYearsUntilNow(userBean.birth)}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        民族
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${userBean.nation}
                    </td>
                    <td class="bg-right">
                        党籍状态
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${MEMBER_POLITICAL_STATUS_MAP.get(userBean.politicalStatus)}
                    </td>
                    <td class="bg-right">
                        身份证号
                    </td>
                    <td class="bg-left" style="min-width: 80px" colspan="3">
                        ${userBean.idcard}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        入党时间
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${cm:formatDate(userBean.growTime,'yyyy-MM-dd')}
                    </td>

                    <td class="bg-right">
                        留学国别
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${memberStay.country}
                    </td>
                    <td class="bg-right">
                        出国时间
                    </td>
                    <td class="bg-left" style="min-width: 120px" colspan="3">
                        ${cm:formatDate(memberStay.abroadTime,'yyyy-MM-dd')}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        预计回国时间
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${cm:formatDate(memberStay.returnTime,'yyyy-MM-dd')}
                    </td>

                    <td class="bg-right">
                        党费缴纳至年月
                    </td>
                    <td class="bg-left" style="min-width: 120px" >
                        ${cm:formatDate(memberStay.payTime,'yyyy-MM')}
                    </td>

                    <td class="bg-right">
                        手机号码
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${memberStay.mobile}
                    </td>

                    <td class="bg-right">
                        电子邮箱
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${memberStay.email}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        国内联系人姓名
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${memberStay.contactName}
                    </td>
                    <td class="bg-right">
                        国内联系人手机号码
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${memberStay.contactMobile}
                    </td>
                    <td class="bg-right">
                        状态
                    </td>
                    <td class="bg-left" style="min-width: 80px" colspan="3">
                        ${MEMBER_STAY_STATUS_MAP.get(memberStay.status)}
                            &nbsp;
                            <c:if test="${memberStay.status==MEMBER_STAY_STATUS_APPLY}">
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
</div>
<script>
    function _applyBack(){
        bootbox.confirm("确定撤销申请吗？", function (result) {
            if(result){
                $.post("${ctx}/user/memberStay_back",function(ret){

                    if(ret.success){
                        bootbox.alert("撤销成功。",function(){
                            location.reload();
                        });
                    }
                });
            }
        });
    }
</script>
