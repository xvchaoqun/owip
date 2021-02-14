<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="DP_MEMBER_SOURCE_MAP" value="<%=DpConstants.DP_MEMBER_SOURCE_MAP%>"/>
<c:set var="DP_MEMBER_STATUS_MAP" value="<%=DpConstants.DP_MEMBER_STATUS_MAP%>"/>

<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 基本信息</h4>

        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <jsp:include page="/WEB-INF/jsp/dp/dpUserInfo/dpTeacher_info_table.jsp"/>
        </div>
    </div>
</div>

<div class="widget-box transparent">
    <div class="widget-header widget-header-flat">
        <h4 class="widget-title lighter">
            <i class="ace-icon fa fa-phone-square blue"></i>
            联系方式
        </h4>

        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>

    <div class="widget-body">
        <div class="widget-main no-padding">
            <table class="table table-unhover table-bordered table-striped">
                <tbody>
                <tr>
                    <td>
                        手机号
                    </td>
                    <td style="min-width: 150px">
                        <t:mask src="${dpMember.mobile}" type="mobile"/>
                    </td>
                    <td>
                        办公电话
                    </td>
                    <td style="min-width: 150px">
                        <t:mask src="${uv.phone}" type="fixedPhone"/>
                    </td>

                    <td>
                        电子邮箱
                    </td>
                    <td style="min-width: 150px">
                        <t:mask src="${uv.email}" type="email"/>
                    </td>
                </tr>

                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-star blue"></i> 党派成员信息</h4>

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
                        所属党派
                    </td>
                    <td class="bg-left" style="min-width: 150px">
                        <span class="${dpParty.isDeleted ? "delete" :""}">${dpParty.name}</span>
                    </td>
                    <td class="bg-right">
                        加入党派时间
                    </td>
                    <td class="bg-left" style="min-width: 150px">
                        ${cm:formatDate(dpMember.dpGrowTime,'yyyy.MM.dd')}
                    </td>
                    <td class="bg-right">
                        部门
                    </td>
                    <td class="bg-left" style="min-width: 150px">
                        ${dpMember.unit}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">党派成员状态</td>
                    <td class="bg-left">
                        ${DP_MEMBER_STATUS_MAP.get(dpMember.status)}
                    </td>
                    <td class="bg-right">
                        党派内职务
                    </td>
                    <td class="bg-left">
                        ${dpMember.dpPost}
                    </td>
                    <td class="bg-right">
                        兼职(其他校外职务）
                    </td>
                    <td class="bg-left">
                        ${dpMember.partTimeJob}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        是否是中国共产党
                    </td>
                    <td class="bg-left">
                        ${dpMember.isPartyMember ? "是" : "否"}
                    </td>
                    <td class="bg-right">
                        政治表现
                    </td>
                    <td class="bg-left">
                        ${dpMember.politicalAct}
                    </td>
                    <td class="bg-right">
                        党内奖励
                    </td>
                    <td class="bg-left">
                        ${dpMember.partyReward}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        其他奖励
                    </td>
                    <td class="bg-left">
                        ${dpMember.otherReward}
                    </td>
                    <td class="bg-right">
                        培训情况
                    </td>
                    <td class="bg-left">
                        ${dpMember.trainState}
                    </td>
                    <td class="bg-right">
                        手机号
                    </td>
                    <td class="bg-left">
                        <t:mask src="${dpMember.mobile}" type="mobile"/>
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        邮箱
                    </td>
                    <td class="bg-left">
                        <t:mask src="${dpMember.email}" type="email"/>
                    </td>
                    <td class="bg-right">
                        通讯地址
                    </td>
                    <td class="bg-left">
                        ${dpMember.address}
                    </td>
                    <td class="bg-right">
                        备注
                    </td>
                    <td class="bg-left" colspan="3">
                        ${dpMember.remark}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div class="widget-box transparent">
    <div class="widget-header widget-header-flat">
        <h4 class="widget-title lighter">
            <i class="ace-icon fa fa-info-circle blue"></i>
            人事信息
        </h4>

        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>

    <div class="widget-body">
        <div class="widget-main no-padding">
            <jsp:include page="/ext/cadre_info_table.jsp"/>
        </div>
    </div>
</div>
<div class="widget-box transparent">
    <div class="widget-header widget-header-flat">
        <h4 class="widget-title lighter">
            <i class="ace-icon fa fa-star blue"></i>
            职称信息
        </h4>

        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>

    <div class="widget-body">
        <div class="widget-main no-padding">
            <jsp:include page="/ext/cadre_post_table.jsp"/>
        </div>
    </div>
</div>
<div class="widget-box transparent">
    <div class="widget-header widget-header-flat">
        <h4 class="widget-title lighter">
            <i class="ace-icon fa fa-graduation-cap blue"></i>
            学历学位信息
        </h4>

        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>

    <div class="widget-body">
        <div class="widget-main no-padding">
            <table class="table table-unhover table-bordered table-striped">
                <tbody>
                    <tr>
                        <td>
                            最高学历
                        </td>
                        <td style="min-width: 150px">
                            ${teacherInfo.education}
                        </td>
                        <td>
                            学历毕业学校
                        </td>
                        <td style="min-width: 150px">
                            ${teacherInfo.school}
                        </td>
                        <td>
                            学历毕业学校类型
                        </td>
                        <td style="min-width: 150px">
                            ${teacherInfo.schoolType}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            最高学位
                        </td>
                        <td style="min-width: 150px">
                            ${teacherInfo.degree}
                        </td>
                        <td>
                            学位毕业学校
                        </td>
                        <td style="min-width: 80px">
                            ${teacherInfo.degreeSchool}
                        </td>
                        <td>
                            所学专业
                        </td>
                        <td>
                            ${teacherInfo.major}
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<shiro:hasPermission name="sysSync:user">
    <%--<div class="clearfix form-actions center">
        <c:if test="${sysUser.source==USER_SOURCE_JZG}">
            <button class="btn btn-info  btn-pink" onclick="_sync(${param.userId}, this)" type="button"
                    data-loading-text="<i class='fa fa-refresh fa-spin'></i> 同步中..." autocomplete="off">
                <i class="ace-icon fa fa-random "></i>
                同步学校信息
            </button>
            &nbsp; &nbsp; &nbsp;
        </c:if>
    </div>--%>
</shiro:hasPermission>
<script>
    function _reload() {
        $("ul[data-target=\"#dp-content\"] li.active a").click();
    }
</script>