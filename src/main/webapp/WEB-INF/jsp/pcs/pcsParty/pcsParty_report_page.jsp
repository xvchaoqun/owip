<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<jsp:include page="menu.jsp"/>
<div style="padding: 20px;">
    <div class="bs-callout bs-callout-warning">
        <h4>党委委员候选人<%--初步--%>推荐人选推荐提名汇总表（“${PCS_STAGE_MAP.get(cm:toByte(param.stage))}”阶段）</h4>
        <a onclick="javascript:;" data-url="${ctx}/pcs/pcsParty_export?file=2-1&stage=${param.stage}&type=${PCS_USER_TYPE_DW}"
           class="downloadBtn btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载汇总表</a>
        <div style="color:red;font-size: x-large;font-weight: bolder">（此表是系统根据上传的党支部推荐情况自动汇总的结果表，报送后请下载打印盖章送至组织部）</div>
    </div>

    <div class="bs-callout bs-callout-warning">
        <h4>纪委委员候选人<%--初步--%>推荐人选推荐提名汇总表（“${PCS_STAGE_MAP.get(cm:toByte(param.stage))}”阶段）</h4>
        <a onclick="javascript:;" data-url="${ctx}/pcs/pcsParty_export?file=2-1&stage=${param.stage}&type=${PCS_USER_TYPE_JW}"
           class="downloadBtn btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载汇总表</a>
        <div style="color:red;font-size: x-large;font-weight: bolder">（此表是系统根据上传的党支部推荐情况自动汇总的结果表，报送后请下载打印盖章送至组织部）</div>
    </div>
    <%-- <div class="bs-callout bs-callout-info">
         <h4>附表3. 参加两委委员候选人推荐提名情况汇总表（院系级党组织用）</h4>
         <a href="${ctx}/pcs/pcsParty_export?file=3&stage=${param.stage}"
            class="btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载</a>
     </div>
 --%>
</div>

<div class="modal-footer center" style="margin-top: 20px">
    <button id="submitBtn" ${!allowModify?"disabled":""}
            class="btn btn-success btn-lg"><i class="fa fa-random"></i> ${hasReport?"已报送数据":"报&nbsp;&nbsp;送"}
    </button>
</div>
<script type="text/template" id="alertTpl">
    <div class="tip">
        <div style="font-size: 18px; font-weight: bolder;color:red">以下支部的推荐情况未录入系统：</div>
        <ul style="padding: 10px 0 0 100px;">
            {{_.each(beans, function(b, idx){ }}
            <li>
                {{=b.name}}
            </li>
            {{})}}
        </ul>
    </div>
</script>
<script type="text/template" id="successTpl">
    <div style='font-size: 22px;font-weight: bolder;'>
        <div style="color: green; font-size: 38px;margin-bottom: 30px;margin-top: 20px;">
            <i class='fa fa-check-circle fa-lx'></i> 报送成功
        </div>
        <div style="text-indent: 2em">

            <c:if test="${param.stage==PCS_STAGE_FIRST}">
                两委委员候选人酝酿提名“一下一上”阶段已完成报送
                <%--两委委员候选人酝酿提名“一下一上”阶段已完成报送，学校党委研究确定“二下”名单之后会自动下发并短信提醒${_p_partyName}管理员。
                “二下二上”阶段的时间是9月8日至11日，请务必按时完成。--%>
            </c:if>
            <c:if test="${param.stage==PCS_STAGE_SECOND}">
                两委委员候选人酝酿提名“二下二上”阶段已完成报送
                <%--两委委员候选人酝酿提名“二下二上”阶段已完成报送，学校党委研究确定“三下”名单之后，通过本系统下发并短信提醒${_p_partyName}管理员。--%>
            </c:if>
            <c:if test="${param.stage==PCS_STAGE_THIRD}">
                两委委员候选人酝酿提名“三下三上”阶段已完成报送。
            </c:if>
        </div>
    </div>
</script>
<script>
    $("#submitBtn").click(function () {
        $.post("${ctx}/pcs/pcsParty_report_check", {stage:${param.stage}}, function (ret) {

            if (ret.success) {
                console.log(ret.beans)
                if (ret.beans.length > 0) {
                    var msg = _.template($("#alertTpl").html())({beans: ret.beans});
                    bootbox.dialog({
                        title: '您还未完成填报',
                        message: msg,
                        buttons: {
                            ok: {
                                label: '<i class="fa fa-reply"></i> 返回填报',
                                className: 'btn-primary',
                                callback: function () {
                                    location.hash = "#${ctx}/pcs/pcsRecommend?stage=${param.stage}";
                                }
                            }
                        },
                        draggable: true
                    });

                } else {

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
                        message: "<div style='padding: 50px;font-size: 22px;font-weight: bolder;color: red;'><i class='fa fa-info-circle'></i> 报送之前务必下载汇总表进行核对确认。报送之后不可以修改，请认真核实后报送。</div>",
                        callback: function (result) {
                            if (result) {
                                $.post("${ctx}/pcs/pcsParty_report", {stage:${param.stage}}, function (ret) {
                                    if (ret.success) {

                                        SysMsg.info(_.template($("#successTpl").html())(), function () {
                                            $.loadPage({url: "${ctx}/pcs/pcsParty?cls=3&stage=${param.stage}"});
                                        })

                                    }
                                });
                            }
                        },
                        title: '报送'
                    });
                }
            }
        });

        return false;
    })
</script>