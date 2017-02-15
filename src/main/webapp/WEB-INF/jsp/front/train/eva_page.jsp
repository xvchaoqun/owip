<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<ul class="preview title nav nav-tabs tab-color-blue background-blue"
    style="padding-right: 10px;margin-bottom: 10px!important; padding-left: 10px!important;">
    ${tc.name}
    <div class="buttons pull-right" style="margin-bottom: 8px;margin-left: 10px; ">
        <a href="${ctx}/train/index" class="btn btn-xs btn-success">
            <i class="ace-icon fa fa-list"></i>
            全部课程
        </a>
    </div>
</ul>
<div class="tabbable">
    <ul class="nav nav-tabs" id="myTab">
        <li class="active">
            <a data-toggle="tab" href="#detail">
                <i class="green ace-icon fa fa-hand-o-down  bigger-120"></i>
                课程评价
            </a>
        </li>
    </ul>
    <c:set var="trainEvaResult" value="${tempdata.trainEvaResultMap.get(norm.id)}"/>
    <div class="tab-content">
        <div class="alert alert-block alert-success">
            第${step}/${maxStep}步：
            <c:if test="${step<=maxStep-1}">
            <c:if test="${not empty norm.topNorm}">${norm.topNorm.name} - </c:if>${norm.name}
            </c:if>
            <c:if test="${step==maxStep}">
            评估总分和意见建议
            </c:if>
        </div>
        <c:if test="${step<=maxStep-1}">
        <table id="rank-table" class="table table-striped table-bordered ">
            <tbody>
            <c:forEach items="${ranks}" var="rank" varStatus="vs">
                <tr>
                    <c:if test="${vs.first}">
                        <td rowspan="${rankNum}" width="80" style="font-size: 25pt;padding: 10px; text-align: center">
                            评估等级
                        </td>
                    </c:if>
                    <td style="border-right: none;" class="rank-name">${rank.name}（${rank.scoreShow}）</td>
                    <td style="border-left: none;width: 50px" class="rank-check">
                        <i class="fa fa-check fa-lg green" style="${trainEvaResult.rankId==rank.id?'':'display: none'}" data-rank-id="${rank.id}"></i>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </c:if>
        <c:if test="${step==maxStep}">
        <table class="table">
            <tr>
                <td align="right" style="border-top: none; vertical-align: middle;
                width: 80px;white-space: nowrap; padding-right: 0">评估总分：</td>
                <td style="border-top: none; vertical-align: middle">
                    <span id="score" class="ui-slider-red"></span>
                </td>
                <td style="border-top: none; vertical-align: middle;width: 20px;">
                    <span id="scoreShow">80</span>
                </td>
            </tr>
            <tr>
                <td colspan="3" style="border-top: none">
                    <textarea id="feedback" placeholder="请在此输入意见或建议" class="form-control limited" rows="8">${tempdata.feedback}</textarea>
                </td>
            </tr>
            </table>
        </c:if>
        <div class="clearfix center nowrap">
            <button ${step==1?"disabled":""} class="first-step btn btn-info" style="padding-left:5px;padding-right: 5px;" type="button">
                <i class="fa fa-fast-backward"></i>
                </button>

            <button ${step==1?"disabled":""} class="last-step btn btn-info" type="button">
                上一步
            </button>
            <button style="${step==maxStep?"display:none":""}" class="next-step btn btn-info" type="button">
                下一步
            </button>
            <button style="${step==maxStep?"":"display:none"}" id="submitBtn" class="btn btn-success" type="button">
                完<span style="visibility: hidden">一</span>成
            </button>
            <button ${step==maxStep?"disabled":""} class="max-step btn btn-info" style="padding-left:5px;padding-right: 5px;" type="button">
                <i class="fa fa-fast-forward"></i>
            </button>

        </div>
    </div>
</div>
<link rel="stylesheet" href="${ctx}/assets/css/jquery-ui.custom.css" />
<script src="${ctx}/assets/js/jquery-ui.custom.js"></script>
<script src="${ctx}/assets/js/jquery.ui.touch-punch.js"></script>
<script>
    function refreshScore() {
        var score = $( "#score" ).slider( "value" );
        $("#scoreShow").text( score);
    }
    $("#score").css({width:'100%', 'float':'left'}).empty().slider({
        value: $("#scoreShow").text(),
        range: "min",
        animate: true,
        slide: refreshScore,
        change: refreshScore
    });

    $("#submitBtn").click(function(){

        var feedback = $.trim($("textarea#feedback").val());
        if(feedback==''){
            $("textarea#feedback").val('').focus();
            return;
        }
        var score = $( "#score" ).slider( "value" );
        $.post("${ctx}/train/eva",{score:score,feedback:feedback, id:'${tic.id}'},function(ret){
            if(ret.success){
                location.href="${ctx}/train/index";
            }
        });
    });

    function getSelectedRankId(){

        var $i = $("#rank-table").find("i:visible");
        //console.log($i.length)
        return ($i!=undefined&&$i.length>0)?$i.data("rank-id"):-1;
    }
    //alert(getSelectedRankId())

    register_click(".rank-name, .rank-check", function () {
        $("#rank-table i").hide();
        // console.log($(this).closest("tr").find("i"))
        var $i = $(this).closest("tr").find("i");
        $i.show();
    })

    register_click(".last-step", function () {
        if (!$(this).prop("disabled")) {
            var rankId = getSelectedRankId();
            //return;
            location.href = "${ctx}/train/eva?courseId=${tc.id}&step=${step-1}&lastStep=${step}&lastRankId=" + rankId;
        }
    })

    register_click(".next-step", function () {
        if (!$(this).prop("disabled")) {
            var rankId = getSelectedRankId();
            //return;
            if (rankId == -1) {
                SysMsg.warning('请选择评估等级')
                return;
            }
            location.href = "${ctx}/train/eva?courseId=${tc.id}&step=${step+1}&lastStep=${step}&lastRankId=" + rankId;
        }
    })

    register_click(".first-step", function () {
        if (!$(this).prop("disabled")) {
            location.href = "${ctx}/train/eva?courseId=${tc.id}&step=1";
        }
    })
    register_click(".max-step", function () {
        if (!$(this).prop("disabled")) {
            location.href = "${ctx}/train/eva?courseId=${tc.id}&step=${maxStep}";
        }
    })

</script>