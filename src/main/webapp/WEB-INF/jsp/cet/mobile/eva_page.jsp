<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<ul class="preview title nav nav-tabs tab-color-blue background-blue"
    style="padding: 10px;margin-bottom: 10px!important; font-size: larger;font-weight: bolder">
    ${tc.name}
</ul>
<div class="tabbable">
    <ul class="nav nav-tabs" id="myTab">
        <li class="active">
            <a data-toggle="tab" href="#detail">
                <i class="green ace-icon fa fa-hand-o-down  bigger-120"></i>
                课程评价
            </a>
        </li>
        <div class="buttons pull-right" style="margin-bottom: 8px;margin-left: 10px; ">
            <a href="${ctx}/m/cet/index" class="btn btn-xs btn-success">
                <i class="ace-icon fa fa-reply"></i>
                返回
            </a>
        </div>
    </ul>
    <div class="tab-content">
       <c:import url="/m/cet/eva_page_next?trainCourseId=${param.trainCourseId}"/>
    </div>
</div>
<style>
    .title-td{
        font-size: 12pt;
        padding: 10px;
        text-align: center;
        vertical-align: middle;
        width: 30px;
        vertical-align: middle!important;
    }
    .selected-td{
        background: #FFCCCC;
        font-weight: bold;
    }
    .rank-name{
        border-right: none!important;
        font-size: 12pt;
    }
</style>
<link rel="stylesheet" href="${ctx}/assets/css/jquery-ui.custom.css"/>
<script src="${ctx}/assets/js/jquery-ui.custom.js"></script>
<script src="${ctx}/assets/js/jquery.ui.touch-punch.js"></script>
<script>
   /* function refreshScore() {
        var score = $("#score").slider("value");
        $("#scoreShow").text(score);
    }
    $("#score").css({width: '100%', 'float': 'left'}).empty().slider({
        value: $("#scoreShow").text(),
        range: "min",
        animate: true,
        slide: refreshScore,
        change: refreshScore
    });*/

    function _submit(id){

        var feedback = $.trim($("textarea#feedback").val());
        /*if (feedback == '') {
            $("textarea#feedback").val('').focus();
            return;
        }*/
        //alert(feedback)
        //var score = $("#score").slider("value");
        $.post("${ctx}/m/cet/eva", {feedback: feedback,id:id}, function (ret) {
            if (ret.success) {
                location.href = "${ctx}/m/cet/index";
            }
        });
    };

    function getSelectedRankId() {

        var $i = $("#rank-table").find("i:visible");
        //console.log($i.length)
        return ($i != undefined && $i.length > 0) ? $i.data("rank-id") : -1;
    }
    //alert(getSelectedRankId())

    $.register.m_click(".rank-name, .rank-check", function () {
        $("#rank-table i").hide();
        $("#rank-table td").removeClass("selected-td");
        // console.log($(this).closest("tr").find("i"))
        var $tr = $(this).closest("tr");
        $("td", $tr).not(".title-td").addClass("selected-td");
        var $i = $tr.find("i");
        $i.show();
    })

    $.register.m_click(".last-step", function () {
        if (!$(this).prop("disabled")) {
            var rankId = getSelectedRankId();
            //return;
            var step = $(this).data("step");
            var lastStep = $(this).data("last-step");
            $(".tab-content").load("${ctx}/m/cet/eva_page_next?trainCourseId=${tc.id}&step=" +step+"&lastStep="+lastStep+"&lastRankId=" + rankId);
        }
    })

    $.register.m_click(".next-step", function () {
        if (!$(this).prop("disabled")) {
            var rankId = getSelectedRankId();
            //return;
            if (rankId == -1) {
                SysMsg.warning('请选择评估等级')
                return;
            }
            var step = $(this).data("step");
            var lastStep = $(this).data("last-step");
            $(".tab-content").load("${ctx}/m/cet/eva_page_next?trainCourseId=${tc.id}&step=" +step+"&lastStep="+lastStep+"&lastRankId=" + rankId);
        }
    })

    $.register.m_click(".first-step", function () {
        if (!$(this).prop("disabled")) {
            $(".tab-content").load("${ctx}/m/cet/eva_page_next?trainCourseId=${tc.id}&step=1");
        }
    })
    $.register.m_click(".max-step", function () {
        if (!$(this).prop("disabled")) {
            var maxStep = $(this).data("max-step");
            $(".tab-content").load("${ctx}/m/cet/eva_page_next?trainCourseId=${tc.id}&step=" + maxStep);
        }
    })

</script>