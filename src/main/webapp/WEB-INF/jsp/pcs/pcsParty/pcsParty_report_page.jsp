<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<jsp:include page="menu.jsp"/>
<div style="padding: 20px;">
    <div class="bs-callout bs-callout-warning">
        <h4>附表2-1. 党委委员候选人推荐提名汇总表（院系级党组织用）</h4>
        <a href="${ctx}/pcsParty_export?file=2-1&stage=${param.stage}&type=${PCS_USER_TYPE_DW}"
           class="btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载汇总表</a>
    </div>

    <div class="bs-callout bs-callout-warning">
        <h4>附表2-2. 纪委委员候选人推荐提名汇总表（院系级党组织用）</h4>
        <a href="${ctx}/pcsParty_export?file=2-1&stage=${param.stage}&type=${PCS_USER_TYPE_JW}"
           class="btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载汇总表</a>
    </div>
    <div class="bs-callout bs-callout-info">
        <h4>附表3. 参加两委委员候选人推荐提名情况汇总表（院系级党组织用）</h4>
        <a href="${ctx}/pcsParty_export?file=3&stage=${param.stage}"
           class="btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载</a>
    </div>

</div>

<div class="modal-footer center" style="margin-top: 20px">
    <button id="submitBtn" ${!allowModify?"disabled":""}
            class="btn btn-success btn-lg"><i class="fa fa-random"></i> ${!allowModify?"已上报数据":"上&nbsp;&nbsp;报"}
    </button>
</div>
<style>
    .bs-callout-warning {
        border-left-color: #f0ad4e !important;
    }
    .bs-callout-info {
        border-left-color: #5bc0de !important;
    }
    .bs-callout {
        padding: 20px;
        margin: 20px 0;
        border: 1px solid #eee;
        border-left-width: 5px;
        border-radius: 3px;
    }

    .bs-callout-warning h4 {
        color: #f0ad4e;
    }

    .bs-callout-info h4 {
        color: #5bc0de;
    }

    .bs-callout h4 {
        font-size: 24px !important;
        font-family: inherit;
        font-weight: 500;
        line-height: 1.1;
        margin-top: 0;
        margin-bottom: 5px;
    }

    .bs-callout *:last-child {
        margin-bottom: 0;
    }

    .btn-outline {
        color: #563d7c;
        background-color: transparent;
        border-color: #563d7c;
    }
</style>
<script>
    $("#submitBtn").click(function () {
        bootbox.confirm({
            className: "confirm-modal",
            buttons: {
                confirm: {
                    label: '<i class="fa fa-check"></i> 确认上报',
                    className: 'btn-danger'
                },
                cancel: {
                    label: '<i class="fa fa-reply"></i> 返回',
                    className: 'btn-default btn-show'
                }
            },
            message: "<div style='padding: 50px;font-size: 22px;font-weight: bolder;color: red;'>上报数据后不可以修改，请认真核实后上报。</div>",
            callback: function (result) {
                if (result) {
                    $.post("${ctx}/pcsParty_report", {stage:${param.stage}}, function (ret) {
                        if (ret.success) {

                            $.loadPage({url: "${ctx}/pcsParty?cls=3&stage=${param.stage}"});
                        }
                    });
                }
            },
            title: '上报'
        });

        return false;
    })
</script>