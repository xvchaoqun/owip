<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<jsp:include page="menu.jsp"/>
<div style="padding: 20px;">
    <div class="bs-callout bs-callout-warning">
        <h4>${_p_partyName}酝酿党员代表大会代表候选人${param.stage==PCS_STAGE_FIRST?'初步':'预备'}人选名单（“${PCS_STAGE_MAP.get(cm:toByte(param.stage))}”阶段）</h4>
        <a href="${ctx}/pcs/pcsPrParty_export?file=3&stage=${param.stage}"
           class="btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载汇总表</a>
    </div>

    <div class="bs-callout bs-callout-warning">
        <h4>${_p_partyName}酝酿党员代表大会代表候选人${param.stage==PCS_STAGE_FIRST?'初步':'预备'}人选统计表（“${PCS_STAGE_MAP.get(cm:toByte(param.stage))}”阶段）</h4>
        <a href="${ctx}/pcs/pcsPrParty_export?file=4&stage=${param.stage}"
           class="btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载汇总表</a>
    </div>
</div>

<div class="modal-footer center" style="margin-top: 20px">
    <button id="submitBtn" ${!allowModify?"disabled":""}
            class="btn btn-success btn-lg"><i class="fa fa-random"></i> ${pcsPrRecommend.hasReport?"已报送数据":"报&nbsp;&nbsp;送"}
    </button>
</div>
<script type="text/template" id="successTpl">
    <div style='font-size: 22px;font-weight: bolder;'>
        <div style="color: green; font-size: 38px;margin-bottom: 30px;margin-top: 20px;">
            <i class='fa fa-check-circle fa-lx'></i> 报送成功
        </div>
        <div style="text-indent: 2em">
            <c:if test="${param.stage==PCS_STAGE_FIRST}">
                党代表选举“一下一上”阶段已完成报送
            <%--党代表选举“一下一上”阶段已完成报送，党委组织部审核通过后会短信提醒${_p_partyName}管理员。
            “二下二上”阶段的时间是9月8日至11日，请务必按时完成。--%>
            </c:if>
            <c:if test="${param.stage==PCS_STAGE_SECOND}">
                党代表选举“二下二上”阶段已完成报送
                <%--党代表选举“二下二上”阶段已完成报送，党委组织部审核通过后短信提醒${_p_partyName}管理员。--%>
            </c:if>
        </div>
    </div>
</script>
<script>
    $("#submitBtn").click(function () {
        bootbox.confirm({
            className: "confirm-modal",
            buttons: {
                confirm: {
                    label: '<i class="fa fa-check"></i> 确认报送',
                    className: 'btn-danger'
                },
                cancel: {
                    label: '<i class="fa fa-reply"></i> 返回',
                    className: 'btn-default btn-show'
                }
            },
            message: "<div style='padding: 50px;font-size: 22px;font-weight: bolder;color: red;'><i class='fa fa-info-circle'></i> 报送之前务必下载汇总表。报送之后不可以修改，请认真核实后报送。<div>",
            callback: function (result) {
                if (result) {
                    $.post("${ctx}/pcs/pcsPrParty_report", {stage:${param.stage}}, function (ret) {
                        if (ret.success) {

                            SysMsg.info(_.template($("#successTpl").html())(), function () {
                                $.loadPage({url: "${ctx}/pcs/pcsPrParty?cls=3&stage=${param.stage}"});
                            })
                        }
                    });
                }
            },
            title: '报送'
        });

        return false;
    })
</script>