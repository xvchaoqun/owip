<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="DP_MEMBER_POLITICAL_STATUS_GROW" value="<%=DpConstants.DP_MEMBER_POLITICAL_STATUS_GROW%>"/>

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
            <jsp:include page="/WEB-INF/jsp/ext/teacher_info_table.jsp"/>
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
                        ${cm:displayParty(member.partyId, member.branchId)}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">党籍状态</td>
                    <td class="bg-left" width="300">
                        ${MEMBER_POLITICAL_STATUS_MAP.get(member.politicalStatus)}
                        <shiro:hasPermission name="member:edit">
                            <c:if test="${member.politicalStatus==DP_MEMBER_POLITICAL_STATUS_GROW}">
                                &nbsp;
                                <button class="confirm btn btn-xs btn-primary"
                                        data-title="同步预备党员"
                                        data-msg="确定将此预备党员信息导入<span style='color:red;font-weight:bolder;'>[入党申请管理-预备党员]支部审核阶段</span>？"
                                        data-url="${ctx}/snyc_memberApply?userId=${param.userId}">
                                    <i class="fa fa-random "></i> 同步至入党申请列表
                                </button>
                            </c:if>
                        </shiro:hasPermission>
                    </td>
                    <td class="bg-right">状态</td>
                    <td class="bg-left">
                        ${MEMBER_STATUS_MAP.get(member.status)}
                    </td>
                    <td class="bg-right">
                        党内职务
                    </td>
                    <td class="bg-left">
                        ${member.partyPost}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        入党时间
                    </td>
                    <td class="bg-left" width="150">
                        ${cm:formatDate(member.growTime,'yyyy-MM-dd')}
                    </td>
                    <td class="bg-right">
                        入党介绍人
                    </td>
                    <td class="bg-left">
                        ${member.sponsor}
                    </td>
                    <td class="bg-right">
                        入党时所在党支部
                    </td>
                    <td class="bg-left">
                        ${member.growBranch}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        转正时间
                    </td>
                    <td class="bg-left">
                        ${cm:formatDate(member.positiveTime,'yyyy-MM-dd')}
                    </td>
                    <td class="bg-right">
                        转正时所在党支部
                    </td>
                    <td class="bg-left" width="150">
                        ${member.positiveBranch}
                    </td>
                    <td class="bg-right">
                        进入系统方式
                    </td>
                    <td class="bg-left">
                        ${MEMBER_SOURCE_MAP.get(member.source)}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">提交书面申请书时间</td>
                    <td class="bg-left">
                        ${cm:formatDate(member.applyTime,'yyyy-MM-dd')}
                    </td>
                    <td class="bg-right">
                        确定为入党积极分子时间
                    </td>

                    <td class="bg-left">
                        ${cm:formatDate(member.activeTime,'yyyy-MM-dd')}
                    </td>
                    <td class="bg-right">
                        确定为发展对象时间
                    </td>
                    <td class="bg-left">
                        ${cm:formatDate(member.candidateTime,'yyyy-MM-dd')}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        党内奖励
                    </td>
                    <td class="bg-left" colspan="5">
                        ${member.partyReward}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        其他奖励
                    </td>
                    <td class="bg-left" colspan="5">
                        ${member.otherReward}
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