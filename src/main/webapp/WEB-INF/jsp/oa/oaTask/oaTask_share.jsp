<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>共享任务（${oaTask.name}）</h3>
</div>
<div class="modal-body">
    <select data-rel="select2-ajax" data-width="200"
            data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG},${USER_TYPE_RETIRE}"
            name="userId" data-placeholder="请输入账号或姓名或工号">
        <option></option>
    </select>
    <button id="selectBtn" type="button"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 操作中"
            class="btn btn-success"><i
            class="fa fa-plus"></i> 添加
    </button>
    <div class="space-4"></div>
    <table class="table table-striped table-bordered table-condensed table-unhover2 table-center">
        <thead>
        <tr>
            <th width="50">序号</th>
            <th width="80">工作证号</th>
            <th width="80">姓名</th>
            <th>所在单位及职务</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userList}" var="u" varStatus="vs">
            <tr>
                <td>${vs.index+1}</td>
                <td>${u.code}</td>
                <td>${u.realname}</td>
                <td style="text-align: left">${cm:getCadreByUserId(u.userId).title}</td>
                <td>
                    <a href="javascript:;" class="popoverConfirm btn btn-xs btn-danger"
                       data-user-id="${u.id}"><i class="fa fa-minus-circle"></i> 移除 </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <div class="note">
        注：任务共享人员与任务创建人拥有相同的操作权限
    </div>
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>
<script type="text/template" id="popover_tpl">
    <button class="runBtn btn btn-success btn-sm"
       data-url="${ctx}/oa/oaTask_share?taskId=${param.taskId}&userId={{=userId}}&share=0"
       data-callback="_removeSuccess" data-user-id="{{=userId}}" data-tip="no">
        <i class="fa fa-check"></i> 确定</button>&nbsp;
     <button class="btn btn-default btn-sm" onclick="_hidePopover()"><i class="fa fa-times"></i> 取消</button>
</script>
<script>
    function _hidePopover(){
        $(".popoverConfirm").webuiPopover("hide")
    }
    $(".popoverConfirm").each(function(){
        var $this = $(this);
        $this.webuiPopover({
                title:"确认移除该共享人员？",
                width: '180px',
                animation: 'pop',
                content: _.template($("#popover_tpl").html())({userId: $this.data("user-id")})
        });
    })
    function _removeSuccess(btn){
        _hidePopover();
        $(".popoverConfirm[data-user-id="+$(btn).data("user-id")+"]").closest("tr").remove();
        $("#jqGrid").trigger("reloadGrid");
    }

    $.register.user_select($('#modal [data-rel="select2-ajax"]'));
    $("#selectBtn").click(function () {
        var userId = $("#modal select[name=userId]").val();
        if(userId=='') return;

        var $btn = $("#selectBtn").button('loading');
        $.post("${ctx}/oa/oaTask_share?taskId=${param.taskId}&share=1",
            {userId:userId},function (ret) {
            if(ret.success){
                $.loadModal("${ctx}/oa/oaTask_share?taskId=${param.taskId}");
                $("#jqGrid").trigger("reloadGrid");
            }
             $btn.button('reset');
        })
    });
</script>