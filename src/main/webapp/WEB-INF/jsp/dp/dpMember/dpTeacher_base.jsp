<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="DP_MEMBER_SOURCE_MAP" value="<%=DpConstants.DP_MEMBER_SOURCE_MAP%>"/>
<c:set var="DP_MEMBER_POLITICAL_STATUS_GROW" value="<%=DpConstants.DP_MEMBER_POLITICAL_STATUS_GROW%>"/>
<c:set var="DP_MEMBER_POLITICAL_STATUS_MAP" value="<%=DpConstants.DP_MEMBER_POLITICAL_STATUS_MAP%>"/>
<c:set var="DP_MEMBER_STATUS_MAP" value="<%=DpConstants.DP_MEMBER_STATUS_MAP%>"/>

<div class="widget-box">
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
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-star blue"></i> 党籍信息</h4>

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
                        所属组织机构
                    </td>
                    <td class="bg-left" colspan="5">
                        <span class="${dpParty.isDeleted ? "delete" :""}">${dpParty.name}</span>
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">党籍状态</td>
                    <td class="bg-left" width="300">
                        ${MEMBER_POLITICAL_STATUS_MAP.get(dpMember.politicalStatus)}
                    </td>
                    <td class="bg-right">状态</td>
                    <td class="bg-left">
                        ${DP_MEMBER_STATUS_MAP.get(dpMember.status)}
                    </td>
                    <td class="bg-right">
                        党内职务
                    </td>
                    <td class="bg-left">
                        ${dpMember.partyPost}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        入党时间
                    </td>
                    <td class="bg-left" width="150">
                        ${cm:formatDate(dpMember.growTime,'yyyy-MM-dd')}
                    </td>
                    <td class="bg-right">
                        入党介绍人
                    </td>
                    <td class="bg-left">
                        ${dpMember.sponsor}
                    </td>
                    <td class="bg-right">
                        转正时间
                    </td>
                    <td class="bg-left">
                        ${cm:formatDate(dpMember.positiveTime,'yyyy-MM-dd')}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">提交书面申请书时间</td>
                    <td class="bg-left">
                        ${cm:formatDate(dpMember.applyTime,'yyyy-MM-dd')}
                    </td>
                    <td class="bg-right">
                        确定为入党积极分子时间
                    </td>

                    <td class="bg-left">
                        ${cm:formatDate(dpMember.activeTime,'yyyy-MM-dd')}
                    </td>
                    <td class="bg-right">
                        确定为发展对象时间
                    </td>
                    <td class="bg-left">
                        ${cm:formatDate(dpMember.candidateTime,'yyyy-MM-dd')}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        党内奖励
                    </td>
                    <td class="bg-left" colspan="5">
                        ${dpMember.partyReward}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        其他奖励
                    </td>
                    <td class="bg-left" colspan="5">
                        ${dpMember.otherReward}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<shiro:hasPermission name="sysSync:user">
    <div class="clearfix form-actions center">
        <c:if test="${sysUser.source==USER_SOURCE_JZG}">
            <button class="btn btn-info  btn-pink" onclick="_sync(${param.userId}, this)" type="button"
                    data-loading-text="<i class='fa fa-refresh fa-spin'></i> 同步中..." autocomplete="off">
                <i class="ace-icon fa fa-random "></i>
                同步学校信息
            </button>
            &nbsp; &nbsp; &nbsp;
        </c:if>
    </div>
</shiro:hasPermission>
<script>
    function _reload() {
        $("#body-content-view #view-box .nav-tabs li.active a").click();
    }

    function _sync(userId, btn) {

        var $btn = $(btn).button('loading')
        var $container = $("#view-box");
        $container.showLoading({
            'afterShow':
                function () {
                    setTimeout(function () {
                        $container.hideLoading();
                        $btn.button('reset');
                    }, 10000);
                }
        });
        $.post("${ctx}/sync_user", {userId: userId}, function (ret) {

            if (ret.success) {
                $container.hideLoading();
                _reload();
                $btn.button('reset');
            }
        });
    }
</script>