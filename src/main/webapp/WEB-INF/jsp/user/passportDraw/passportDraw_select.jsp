<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<div class="modal-body">
    <form class="form-horizontal">
        <div class="well">
            <blockquote>
                <h4 class="green bolder bigger-150">因私出国（境）</h4>
                因私出国（境）申请已通过审批，现领取证件用来签证/签注或者出行。
                <button data-url="${ctx}/user/passportDraw_self" class="openView btn btn-success" type="button">
                    <i class="ace-icon fa fa-forward bigger-110"></i>
                    进入
                </button></blockquote>
        </div>
        <div class="well">
            <blockquote>
                <h4 class="orange2 bolder bigger-150">因公出访台湾</h4>
                已完成因公出访台湾的审批手续，并持有国台办的批件。
                <button onclick="location.href='${ctx}/user/passportDraw_tw'" class="btn btn-warning" type="button">
                    <i class="ace-icon fa fa-forward bigger-110"></i>
                    进入
                </button></blockquote>
        </div>
        <div class="well">
            <blockquote>
                <h4 class="blue bolder bigger-150">处理其他事务</h4>
                因私出国（境）和因公出访台湾之外的其他事项。
                <button data-url="${ctx}/user/passportDraw_other" class="openView btn btn-info" type="button">
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