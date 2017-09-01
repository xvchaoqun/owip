<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<jsp:include page="menu.jsp"/>
<div style="padding: 20px;">
    <div class="bs-callout bs-callout-warning">
        <h4>党委委员候选人初步人选推荐提名汇总表（“${PCS_STAGE_MAP.get(cm:toByte(param.stage))}”阶段）</h4>
        <a href="${ctx}/pcsParty_export?file=2-1&stage=${param.stage}&type=${PCS_USER_TYPE_DW}"
           class="btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载汇总表</a>
    </div>

    <div class="bs-callout bs-callout-warning">
        <h4>纪委委员候选人初步人选推荐提名汇总表（“${PCS_STAGE_MAP.get(cm:toByte(param.stage))}”阶段）</h4>
        <a href="${ctx}/pcsParty_export?file=2-1&stage=${param.stage}&type=${PCS_USER_TYPE_JW}"
           class="btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载汇总表</a>
    </div>
   <%-- <div class="bs-callout bs-callout-info">
        <h4>附表3. 参加两委委员候选人推荐提名情况汇总表（院系级党组织用）</h4>
        <a href="${ctx}/pcsParty_export?file=3&stage=${param.stage}"
           class="btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载</a>
    </div>
--%>
</div>

<div class="modal-footer center" style="margin-top: 20px">
    <button id="submitBtn" ${!allowModify?"disabled":""}
            class="btn btn-success btn-lg"><i class="fa fa-random"></i> ${!allowModify?"已报送数据":"报&nbsp;&nbsp;送"}
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
<script>
    $("#submitBtn").click(function () {
        $.post("${ctx}/pcsParty_report_check",{stage:${param.stage}},function(ret){

            if(ret.success){
                console.log(ret.beans)
                if(ret.beans.length>0){
                    var msg = _.template($("#alertTpl").html())({beans:ret.beans});
                    bootbox.dialog({
                        title: '您还未完成填报',
                        message: msg,
                        buttons: {
                            ok: {
                                label: '<i class="fa fa-reply"></i> 返回填报',
                                className: 'btn-primary',
                                callback: function(){
                                    location.hash = "#${ctx}/pcsRecommend?stage=${param.stage}";
                                }
                            }
                        },
                        draggable: true
                    });

                }else{

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
                        message: "<div style='padding: 50px;font-size: 22px;font-weight: bolder;color: red;'><i class='fa fa-info-circle'></i> 报送之前务必下载汇总表。报送之后不可以修改，请认真核实后报送。</div>",
                        callback: function (result) {
                            if (result) {
                                $.post("${ctx}/pcsParty_report", {stage:${param.stage}}, function (ret) {
                                    if (ret.success) {

                                        $.loadPage({url: "${ctx}/pcsParty?cls=3&stage=${param.stage}"});
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