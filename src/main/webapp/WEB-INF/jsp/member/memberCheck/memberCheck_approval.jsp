<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/modify/constants.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
            <span style="margin-left: 10px">
                姓名：${sysUser.realname}&nbsp;&nbsp;学工号：${sysUser.code}&nbsp;&nbsp;所在党组织：${cm:displayParty(memberCheck.partyId, memberCheck.branchId)}
            </span>
        </h4>
    </div>
        <div class="widget-body">
        <div class="widget-box ${param.opType=='check'?'collapsed':''}">
            <div class="widget-header">
                <h4 class="widget-title"><i class="ace-icon fa fa-list blue "></i> 修改前的党员信息<c:if
                        test="${param.opType=='check'}">（可点击查看）</c:if></h4>
                <div class="widget-toolbar">
                    <a href="javascript:;" data-action="collapse">
                        <i class="ace-icon fa fa-chevron-${param.opType=='check'?'down':'up'}"></i>
                    </a>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <table id="nowTable" class="table table-unhover table-bordered table-striped">
                        <tr>
                            <td rowspan="5" style="text-align: center;vertical-align: middle;
                                     width: 50px;background-color: #fff;">
                                <img src="${ctx}/avatar?path=${cm:encodeURI(original.avatar)}" class="avatar">
                            </td>
                            <td data-code="nativePlace" class="text-right">籍贯</td>
                            <td class="bg-left">${original.nativePlace}</td>
                            <td data-code="mobile" class="text-right">手机号</td>
                            <td class="bg-left">${original.mobile}</td>
                            <c:if test="${sysUser.type==USER_TYPE_JZG}">
                                <td data-code="phone" class="text-right">办公电话</td>
                                <td class="bg-left">${original.phone}</td>
                            </c:if>
                            <td data-code="email" class="text-right">邮箱</td>
                            <td class="bg-left" colspan="${sysUser.type==USER_TYPE_JZG?1:3}">${original.email}</td>
                        </tr>
                        <tr>
                            <td data-code="politicalStatus">政治面貌</td>
                            <td class="bg-left">${MEMBER_POLITICAL_STATUS_MAP.get(original.politicalStatus)}</td>
                            <td data-code="transferTime">组织关系转入时间</td>
                            <td class="bg-left">${cm:formatDate(original.transferTime,'yyyy-MM-dd')}</td>
                            <td data-code="applyTime">提交书面申请书时间</td>
                            <td class="bg-left">${cm:formatDate(original.applyTime,'yyyy-MM-dd')}</td>
                            <td data-code="activeTime">确定为入党积极分子时间</td>
                            <td class="bg-left">${cm:formatDate(original.activeTime,'yyyy-MM-dd')}</td>
                        </tr>
                        <tr>
                            <td data-code="candidateTime">确定为发展对象时间</td>
                            <td class="bg-left">${cm:formatDate(original.candidateTime,'yyyy-MM-dd')}</td>
                            <td data-code="sponsor">入党介绍人</td>
                            <td class="bg-left">${original.sponsor}</td>
                            <td data-code="growTime">入党时间</td>
                            <td class="bg-left">${cm:formatDate(original.growTime,'yyyy-MM-dd')}</td>
                            <td data-code="growBranch">入党时所在党支部</td>
                            <td class="bg-left">${original.growBranch}</td>
                        </tr>
                        <tr>
                            <td data-code="positiveTime">转正时间</td>
                            <td class="bg-left">${cm:formatDate(original.positiveTime,'yyyy-MM-dd')}</td>
                            <td data-code="positiveBranch">转正时所在党支部</td>
                            <td class="bg-left">${original.positiveBranch}</td>
                            <td data-code="partyPost">党内职务</td>
                            <td class="bg-left">${original.partyPost}</td>
                            <td data-code="partyReward">党内奖励</td>
                            <td class="bg-left">${original.partyReward}</td>
                        </tr>
                        <tr>
                            <td data-code="otherReward">其他奖励</td>
                            <td colspan="7" class="bg-left">${original.otherReward}</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>

        <div class="widget-box">
            <div class="widget-header">
                <h4 class="widget-title"><i class="ace-icon fa fa-edit blue "></i>
                    党员信息修改申请（申请时间：${cm:formatDate(memberCheck.createTime, "yyyy.MM.dd")}）</h4>
                <div class="widget-toolbar">
                    <a href="javascript:;" data-action="collapse">
                        <i class="ace-icon fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <table id="modifyTable" class="table table-unhover table-bordered table-striped">
                        <tr>
                            <td rowspan="5" style="text-align: center;vertical-align: middle;
                                     width: 50px;background-color: #fff;">
                                <img src="${ctx}/avatar?path=${cm:encodeURI(memberCheck.avatar)}" class="avatar">
                            </td>
                            <td data-code="nativePlace" class="text-right">籍贯</td>
                            <td class="bg-left">${memberCheck.nativePlace}</td>
                            <td data-code="mobile" class="text-right">手机号</td>
                            <td class="bg-left">${memberCheck.mobile}</td>
                            <c:if test="${sysUser.type==USER_TYPE_JZG}">
                                <td data-code="phone" class="text-right">办公电话</td>
                                <td class="bg-left">${memberCheck.phone}</td>
                            </c:if>
                            <td data-code="email" class="text-right">邮箱</td>
                            <td class="bg-left" colspan="${sysUser.type==USER_TYPE_JZG?1:3}">${memberCheck.email}</td>
                        </tr>
                        <tr>
                            <td data-code="politicalStatus">政治面貌</td>
                            <td class="bg-left">${MEMBER_POLITICAL_STATUS_MAP.get(memberCheck.politicalStatus)}</td>
                            <td data-code="transferTime">组织关系转入时间</td>
                            <td class="bg-left">${cm:formatDate(memberCheck.transferTime,'yyyy-MM-dd')}</td>
                            <td data-code="applyTime">提交书面申请书时间</td>
                            <td class="bg-left">${cm:formatDate(memberCheck.applyTime,'yyyy-MM-dd')}</td>
                            <td data-code="activeTime">确定为入党积极分子时间</td>
                            <td class="bg-left">${cm:formatDate(memberCheck.activeTime,'yyyy-MM-dd')}</td>
                        </tr>
                        <tr>
                            <td data-code="candidateTime">确定为发展对象时间</td>
                            <td class="bg-left">${cm:formatDate(memberCheck.candidateTime,'yyyy-MM-dd')}</td>
                            <td data-code="sponsor">入党介绍人</td>
                            <td class="bg-left">${memberCheck.sponsor}</td>
                            <td data-code="growTime">入党时间</td>
                            <td class="bg-left">${cm:formatDate(memberCheck.growTime,'yyyy-MM-dd')}</td>
                            <td data-code="growBranch">入党时所在党支部</td>
                            <td class="bg-left">${memberCheck.growBranch}</td>
                        </tr>
                        <tr>
                            <td data-code="positiveTime">转正时间</td>
                            <td class="bg-left">${cm:formatDate(memberCheck.positiveTime,'yyyy-MM-dd')}</td>
                            <td data-code="positiveBranch">转正时所在党支部</td>
                            <td class="bg-left">${memberCheck.positiveBranch}</td>
                            <td data-code="partyPost">党内职务</td>
                            <td class="bg-left">${memberCheck.partyPost}</td>
                            <td data-code="partyReward">党内奖励</td>
                            <td class="bg-left">${memberCheck.partyReward}</td>
                        </tr>
                        <tr>
                            <td data-code="otherReward">其他奖励</td>
                            <td colspan="7" class="bg-left">${memberCheck.otherReward}</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <c:if test="${param.opType=='check'}">
            <shiro:hasPermission name="memberCheck:approval">
                <div class="jqgrid-vertical-offset widget-box">
                    <div class="widget-header">
                        <h4 class="widget-title"><i class="ace-icon fa fa-check-square-o blue "></i> 审核</h4>
                        <div class="widget-toolbar">
                            <a href="javascript:;" data-action="collapse">
                                <i class="ace-icon fa fa-chevron-down"></i>
                            </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main">
                            <form class="form-horizontal" action="${ctx}/member/memberCheck_approval" id="approvalForm"
                                  method="post">
                                <input type="hidden" name="id" value="${memberCheck.id}">
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">审核意见</label>
                                    <div class="col-xs-8 label-text" style="font-size: 15px;">
                                        <div class="input-group">
                                            <input type="checkbox" class="big" value="1"/> 通过审核
                                            <input type="checkbox" class="big" value="2"/> 未通过审核
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">审核备注</label>
                                    <div class="col-xs-6">
                                        <textarea class="form-control limited" type="text" name="reason"
                                                  rows="2"></textarea>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </shiro:hasPermission>
        </c:if>
        <div class="clearfix form-actions center">
            <c:if test="${param.opType=='check'}">
                <shiro:hasPermission name="memberCheck:approval">
                    <button class="btn btn-success" type="button" id="approvalBtn">
                        <i class="ace-icon fa fa-check"></i>
                        审核
                    </button>
                </shiro:hasPermission>
                &nbsp;&nbsp;&nbsp;&nbsp;
            </c:if>
            <button class="hideView btn btn-default" type="button">
                <i class="ace-icon fa fa-undo"></i>
                返回
            </button>
        </div>
    </div>
</div>
<script>

    $("#modifyTable td[data-code]").each(function () {
        var $this = $(this);
        var code = $this.data("code");
        var $nowTd = $("#nowTable td[data-code=" + code + "]");

        if ($.trim($this.next().html()) != $.trim($nowTd.next().html())) {
            $this.addClass("text-danger bolder");
        }
    });

    $("#approvalBtn").click(function () {
        $("#approvalForm").submit();
        return false;
    })
    $("#approvalForm").validate({
        submitHandler: function (form) {

            var type = $('#approvalForm input[type=checkbox]:checked').val();
            if (type != 1 && type != 2) {
                SysMsg.warning("请选择审核意见");
                return;
            }

            $(form).ajaxSubmit({
                data: {pass: (type == 1)},
                success: function (ret) {
                    if (ret.success) {
                        $("#jqGrid").trigger("reloadGrid");
                        $.hideView();
                    }
                }
            });
        }
    });
</script>