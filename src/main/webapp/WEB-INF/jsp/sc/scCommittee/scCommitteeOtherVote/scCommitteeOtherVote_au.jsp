<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scCommitteeOtherVote!=null}">编辑</c:if><c:if test="${scCommitteeOtherVote==null}">添加</c:if>其他事项表决</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scCommitteeOtherVote_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scCommitteeOtherVote.id}">
        <input type="hidden" name="topicId" value="${topicId}">

			<div class="form-group">
                <div style="height:0px;z-index: 999;position:relative;top: 5px;margin-left: 280px;">表决情况</div>
                <textarea id="memoId">${scCommitteeOtherVote.memo}</textarea>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control limited"
                              name="remark">${scCommitteeOtherVote.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scCommitteeOtherVote!=null}">确定</c:if><c:if test="${scCommitteeOtherVote==null}">添加</c:if></button>
</div>
<script type="text/javascript" src="${ctx}/extend/ke4/kindeditor-all-min.js"></script>
<script>
    var memoKe = KindEditor.create('#memoId', {
        //cssPath:"${ctx}/css/ke.css",
        items: ["source", "|", "fullscreen"],
        height: '400px',
        width: '590px',
        minWidth: 590
    });

    $("#submitBtn").click(function(){
        if($.trim(memoKe.html())==''){
            $.tip({
                $target: $("#modalForm #memoId").closest("div").find(".ke-container"),
                at: 'top center', my: 'bottom center', type: 'info',
                msg: "请填写表决情况。"
            });
        }
        $("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                data: {memo: memoKe.html()},
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>