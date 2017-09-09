<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<jsp:include page="menu.jsp"/>
<div style="padding: 20px;">
    <div class="bs-callout bs-callout-warning">
        <h4>党代表名单</h4>
        <a href="${ctx}/pcsPrParty_export?file=pl"
           class="btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载汇总表</a>
    </div>

    <div class="bs-callout bs-callout-warning">
        <h4>党代表数据统计表</h4>
        <a href="${ctx}/pcsPrParty_export?file=4&stage=${PCS_STAGE_THIRD}"
           class="btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载统计表</a>
    </div>
</div>

<div class="modal-footer center" style="margin-top: 20px">
    <button id="submitBtn" ${!allowModify?"disabled":""}
            class="btn btn-success btn-lg"><i class="fa fa-random"></i> ${pcsPrRecommend.hasReport?"已报送数据":"报&nbsp;&nbsp;送"}
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
<script type="text/template" id="successTpl">
    <div style='padding: 50px;font-size: 22px;font-weight: bolder;'>
        <div style="color: green; font-size: 38px;text-align: center;margin-bottom: 30px;margin-top: 20px;">
            <i class='fa fa-check-circle fa-lx'></i> 报送成功
        </div>
        <div style="text-indent: 2em">
            党代表选举“三下三上”阶段已完成报送。
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
            message: "<div style='padding: 50px;font-size: 22px;font-weight: bolder;color: red;'><i class='fa fa-info-circle'></i> 报送数据后不可以修改，请认真核实后报送。<div>",
            callback: function (result) {
                if (result) {
                    $.post("${ctx}/pcsPrList_report", {stage:${PCS_STAGE_THIRD}}, function (ret) {
                        if (ret.success) {

                            SysMsg.info(_.template($("#successTpl").html()), function () {
                                $.loadPage({url: "${ctx}/pcsPrList?cls=3"});
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