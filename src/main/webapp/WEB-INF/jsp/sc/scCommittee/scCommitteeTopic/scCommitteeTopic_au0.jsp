<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scCommitteeTopic!=null}">编辑</c:if><c:if test="${scCommitteeTopic==null}">添加</c:if>党委常委会议题</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scCommitteeTopic_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${scCommitteeTopic.id}">
		<div class="col-xs-12">
			<div class="col-xs-5">
				<div class="form-group">
					<label class="col-xs-3 control-label" style="white-space: nowrap"><span class="star">*</span>党委常委会</label>
					<div class="col-xs-9">
						<select required name="committeeId" data-rel="select2"
								data-width="240"
								data-placeholder="请选择">
							<option></option>
							<c:forEach var="scCommittee" items="${scCommittees}">
								<option value="${scCommittee.id}">党委常委会[${cm:formatDate(scCommittee.holdDate, "yyyyMMdd")}]号</option>
							</c:forEach>
						</select>
						<script>
							$("#modalForm select[name=committeeId]").val("${committeeId}");
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label"><span class="star">*</span>议题名称</label>
					<div class="col-xs-9">
						<input required class="form-control" type="text" name="name" value="${scCommitteeTopic.name}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">表决情况</label>
					<div class="col-xs-9 label-text">
							<input type="checkbox" class="big" name="hasVote" ${scCommitteeTopic.hasVote?'checked':''}> 干部选拔任用表决
							<br/>
							<input type="checkbox" class="big" name="hasOtherVote"  ${scCommitteeTopic.hasOtherVote?'checked':''}> 其他事项表决
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">表决票</label>
					<div class="col-xs-9">
						<input class="form-control" type="file" name="_voteFilePath"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">备注</label>
					<div class="col-xs-9">
						 <textarea class="form-control limited" rows="7"
								   name="remark">${scCommitteeTopic.remark}</textarea>
					</div>
				</div>
			</div>
			<div class="col-xs-7">
				<div style="height:0px;z-index: 999;position:relative;top: 5px;margin-left: 220px;">议题内容</div>
				<textarea id="contentId">${scCommitteeTopic.content}</textarea>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scCommitteeTopic!=null}">确定</c:if><c:if test="${scCommitteeTopic==null}">添加</c:if></button>
</div>

<script type="text/javascript" src="${ctx}/extend/ke4/kindeditor-all-min.js"></script>
<script>
	$.fileInput($("#modalForm input[name=_voteFilePath]"),{
		allowExt: ['pdf'],
		allowMime: ['application/pdf']
	});

	var contentKe = KindEditor.create('#contentId', {
		//cssPath:"${ctx}/css/ke.css",
		items: ["wordpaste", "source", "|", "fullscreen"],
		height: '350px',
		width: '480px',
		minWidth: 480,
		filterMode:false,
		pasteType:0
	});

    $("#submitBtn").click(function(){
		if($("#modalForm :checkbox:checked").length==0){
			$.tip({
				$target: $("#modalForm [name=hasVote]"),
				at: 'top center', my: 'bottom center', type: 'info',
				msg: "请选择表决情况。"
			});
		}
		if($.trim(contentKe.html())==''){
			$.tip({
				$target: $("#modalForm #contentId").closest("div").find(".ke-container"),
				at: 'top center', my: 'bottom center', type: 'info',
				msg: "请填写议题内容。"
			});
		}

		$("#modalForm").submit();
		return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
			if($("#modalForm :checkbox:checked").length==0){
				return;
			}
			var content = $.trim(contentKe.html());
			if(content==''){
				return;
			}

            $(form).ajaxSubmit({
				data: {content:content},
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });

    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>