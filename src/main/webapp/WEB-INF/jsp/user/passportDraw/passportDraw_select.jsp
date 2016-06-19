<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<div class="modal-body">
    <form class="form-horizontal">
        <div class="well">
            <blockquote>
                <h4 class="green bolder bigger-150">因私出国（境）</h4>
                因私出国（境）申请已提交，现申请领取证件用了办理签证/签注或者出行。
                <button ${fn:length(passports)==0?"disabled":""} data-url="${ctx}/user/passportDraw_self" class="openView btn ${fn:length(passports)==0?"btn-default":"btn-success"}" type="button">
                    <i class="ace-icon fa fa-forward bigger-110"></i>
                    进入
                </button></blockquote>
        </div>
        <div class="well">
            <blockquote>
                <h4 class="orange2 bolder bigger-150">因公赴台、长期因公出国</h4>
                因公赴台：已完成因公赴台审批手续，并持有国台办批件。<br/>长期因公出国：30天及以上因公出国（境），已完成审批手续并持有批件。
                <button ${passportTw==null?"disabled":""} onclick="location.href='${ctx}/user/passportDraw_tw'" class="btn ${passportTw==null?"btn-default":"btn-warning"}" type="button">
                    <i class="ace-icon fa fa-forward bigger-110"></i>
                    进入
                </button></blockquote>
        </div>
        <div class="well">
            <blockquote>
                <h4 class="blue bolder bigger-150">处理其他事务</h4>
                因私出国（境）、因公赴台和长期因公出国之外，需使用因私出国（境）证件办理的事务。
                <button ${fn:length(passports)==0?"disabled":""} data-url="${ctx}/user/passportDraw_other" class="openView btn ${fn:length(passports)==0?"btn-default":"btn-info"}" type="button">
                    <i class="ace-icon fa fa-forward bigger-110"></i>
                    进入
                </button></blockquote>
        </div>
    </form>
</div>
<div class="modal-footer center">
    <input id="cancel" class="btn btn-default" value="返回"/>
</div>
<script>
    $("#cancel").click(function(){
        page_reload();
    });
</script>