<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${_pMap['memberApply_timeLimit']=='true'}" var="_memberApply_timeLimit"/>

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
		<jsp:include page="/ext/student_info_table.jsp"/>
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
                <c:if test="${member.status!=MEMBER_STATUS_TRANSFER}">
                    <tr>
                        <td class="bg-right">
                            所在党组织
                        </td>
                        <td class="bg-left" colspan="5">
                            ${cm:displayParty(member.partyId, member.branchId)}
                        </td>

                    </tr>
                </c:if>
                <tr>
                    <td class="bg-right">党籍状态</td>
                    <td class="bg-left">
                        ${MEMBER_POLITICAL_STATUS_MAP.get(member.politicalStatus)}
                        <shiro:hasPermission name="member:edit">
                            <c:if test="${member.politicalStatus==MEMBER_POLITICAL_STATUS_GROW}">
                                &nbsp;
                                <button class="confirm btn btn-xs btn-primary"
                                        data-title="同步预备党员"
                                        data-msg="确定将此预备党员信息导入<span style='color:red;font-weight:bolder;'>[入党申请管理-预备党员]支部审核阶段</span>？"
                                        data-url="${ctx}/snyc_memberApply?userId=${param.userId}">
                                    <i class="fa fa-random "></i> 同步至党员发展列表
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
                    <td class="bg-left" width="150" id="growTime">
                        ${cm:formatDate(member.growTime,'yyyy.MM.dd')}
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
                    <td class="bg-left" id="positiveTime">
                        ${cm:formatDate(member.positiveTime,'yyyy.MM.dd')}
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
                    <td class="bg-left" id="applyTime">
                        ${cm:formatDate(member.applyTime,'yyyy.MM.dd')}
                    </td>
                    <td class="bg-right">
                        确定为入党积极分子时间
                    </td>

                    <td class="bg-left" id="activeTime">
                        ${cm:formatDate(member.activeTime,'yyyy.MM.dd')}
                    </td>
                    <td class="bg-right">
                        确定为发展对象时间
                    </td>
                    <td class="bg-left" id="candidateTime">
                        ${cm:formatDate(member.candidateTime,'yyyy.MM.dd')}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        增加类型
                    </td>
                    <td class="bg-left">
                        ${cm:getMetaType(member.addType).name}
                    </td>
                    <td class="bg-right">
                        党内奖励
                    </td>
                    <td class="bg-left">
                        ${member.partyReward}
                    </td>
                    <td class="bg-right">
                        其他奖励
                    </td>
                    <td class="bg-left">
                        ${member.otherReward}
                    </td>
                </tr>

                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="clearfix form-actions center">
    <shiro:hasPermission name="sysSync:user">
        <c:if test="${uv.source==USER_SOURCE_YJS || uv.source==USER_SOURCE_BKS}">
            <button class="btn btn-info  btn-pink" onclick="_sync(${param.userId}, this)" type="button"
                    data-loading-text="<i class='fa fa-refresh fa-spin'></i> 同步中..." autocomplete="off">
                <i class="ace-icon fa fa-random "></i>
                同步学校信息
            </button>
            &nbsp; &nbsp; &nbsp;
        </c:if>
    </shiro:hasPermission>
</div>

<script>
    function _reload() {
        $("ul[data-target='#partyMemberViewContent'] li.active a").click();
    }

    var jsObj = ${cm:toJSONObject(member)};
    var _limit = ${_memberApply_timeLimit}
    //console.log(_limit)
    $('#applyTime').html($.memberApplyTime(_limit, jsObj.applyTime, jsObj.birth, 0));
    $('#activeTime').html($.memberApplyTime(_limit, jsObj.activeTime, jsObj.applyTime, 2));
    $('#candidateTime').html($.memberApplyTime(_limit, jsObj.candidateTime, jsObj.activeTime, 3));
    $('#positiveTime').html($.memberApplyTime(_limit, jsObj.positiveTime, jsObj.growTime, 7));

    function _sync(userId, btn) {

        var $btn = $(btn).button('loading')
        $.post("${ctx}/sync_user", {userId: userId}, function (ret) {
            if (ret.success) {
                _reload();
                $btn.button('reset');
                SysMsg.success('同步完成。', '成功');
            }
        });
    }
</script>