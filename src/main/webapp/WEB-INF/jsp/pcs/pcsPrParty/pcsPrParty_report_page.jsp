<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<jsp:include page="menu.jsp"/>
<div style="padding: 20px;">
    <div class="bs-callout bs-callout-warning">
        <h4>附件3. 分党委酝酿代表候选人初步名单（分党委上报组织部）</h4>
        <a href="${ctx}/pcsPrParty_export?file=3&stage=${param.stage}"
           class="btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载汇总表</a>
    </div>

    <div class="bs-callout bs-callout-warning">
        <h4>附件4. 分党委酝酿代表候选人初步人选统计表（分党委上报组织部）</h4>
        <a href="${ctx}/pcsPrParty_export?file=4&stage=${param.stage}"
           class="btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载汇总表</a>
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
            message: "<div style='padding: 50px;font-size: 22px;font-weight: bolder;color: red;'>上报数据后不可以修改，请认真核实后上报。<div>",
            callback: function (result) {
                if (result) {
                    $.post("${ctx}/pcsPrParty_report", {stage:${param.stage}}, function (ret) {
                        if (ret.success) {

                            $.loadPage({url: "${ctx}/pcsPrParty?cls=3&stage=${param.stage}"});
                        }
                    });
                }
            },
            title: '上报'
        });

        return false;
    })
</script>