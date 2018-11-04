<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row dispatch_au">
	<div class="preview">
		<div class="widget-box">
			<div class="widget-header">
				<h4 class="smaller">
					常委会文件预览
					<div style="position: absolute; left:180px;top:8px;">
						<form action="${ctx}/sc/scCommittee_upload"
							  enctype="multipart/form-data" method="post"
							  class="btn-upload-form">
							<button type="button"
									data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中..."
									class="hideView btn btn-xs btn-primary">
								<i class="ace-icon fa fa-upload"></i>
								上传常委会文件
							</button>
							<input type="file" name="file" id="upload-file"/>
						</form>
					</div>
					<div class="buttons pull-right ">

						<a href="javascript:;" class="hideView btn btn-xs btn-success"
						   style="margin-right: 10px; top: -5px;">
							<i class="ace-icon fa fa-backward"></i>
							返回</a>
					</div>
				</h4>
			</div>
			<div class="widget-body">
				<div class="widget-main">
					<div id="dispatch-file-view">
						<c:import url="${ctx}/swf/preview?type=html&path=${scCommittee.filePath}"/>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="au">
		<div id="dispatch-cadres-view">
			<div class="widget-box">
				<div class="widget-header">
					<h4 class="smaller">
						${scCommittee!=null?"修改":"添加"}信息
					</h4>
				</div>
				<div class="widget-body">
					<div class="widget-main">
						<form class="form-horizontal" action="${ctx}/sc/scCommittee_au" id="committeeForm" method="post"
							  enctype="multipart/form-data">
							<div class="row">
								<input type="hidden" name="id" value="${scCommittee.id}">
								<input type="hidden" name="filePath" value="${scCommittee.filePath}">

								<div class="form-group">
									<label class="col-xs-3 control-label">年份</label>

									<div class="col-xs-6">
										<div class="input-group">
											<input required class="form-control date-picker" placeholder="请选择年份"
												   name="year"
												   type="text"
												   data-date-format="yyyy" data-date-min-view-mode="2"
												   value="${empty scCommittee.year?_thisYear:scCommittee.year}"/>
                                            <span class="input-group-addon"> <i
													class="fa fa-calendar bigger-110"></i></span>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">党委常委会日期</label>

									<div class="col-xs-6">
										<div class="input-group">
											<input required class="form-control date-picker" name="holdDate"
												   type="text"
												   data-date-format="yyyy-mm-dd"
												   value="${cm:formatDate(scCommittee.holdDate,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
													class="fa fa-calendar bigger-110"></i></span>
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="col-xs-3 control-label">常委总数</label>

									<div class="col-xs-6">
										<input required class="form-control digits"
											   autocomplete="off" disableautocomplete
											   type="text" name="committeeMemberCount" value="${committeeMemberCount}">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">实际参会常委</label>

									<div class="col-xs-6">
										<select class="multiselect" name="userIds" multiple="">
											<c:forEach items="${committeeMembers}" var="m">
												<option value="${m.userId}">${m.realname}-${m.title}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">请假常委</label>

									<div class="col-xs-6">

										<select class="multiselect" name="absentUserIds" multiple="" >
											<c:forEach items="${committeeMembers}" var="m">
												<option value="${m.userId}">${m.realname}-${m.title}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">列席人</label>

									<div class="col-xs-6">
										<input class="form-control" type="text"
											   autocomplete="off" disableautocomplete
											   name="attendUsers" value="${scCommittee.attendUsers}">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">上会PPT</label>
									<div class="col-xs-6">
										<input class="form-control" type="file" name="_pptFile"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">议题数量</label>

									<div class="col-xs-6">
										<input required class="form-control digits"
											   autocomplete="off" disableautocomplete
											   type="text" name="topicNum" value="${scCommittee.topicNum}">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">会议记录</label>
									<div class="col-xs-6">
										<input class="form-control" type="file" name="_logFile"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">备注</label>

									<div class="col-xs-6">
                                        <textarea class="form-control limited"
												  name="remark">${scCommittee.remark}</textarea>
									</div>
								</div>
							</div>
							<div class="clearfix form-actions center">
								<button class="btn btn-info btn-sm" type="submit">
									<i class="ace-icon fa fa-check "></i>
									${scCommittee!=null?"修改":"添加"}
								</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${ctx}/assets/js/bootstrap-multiselect.js"></script>
<link rel="stylesheet" href="${ctx}/assets/css/bootstrap-multiselect.css"/>
<script>
	$.register.multiselect($('#committeeForm select[name=userIds]'), ${cm:toJSONArray(selectUserIds)}, {buttonWidth: '100%'});
	$.register.multiselect($('#committeeForm select[name=absentUserIds]'), ${cm:toJSONArray(selectAbsentUserIds)}, {buttonWidth: '100%'});

	$.fileInput($("#committeeForm input[name=_pptFile]"));

	$.fileInput($("#committeeForm input[name=_logFile]"),{
		allowExt: ['pdf'],
		allowMime: ['application/pdf']
	});

	$.register.user_select($('#committeeForm [data-rel="select2-ajax"]'));
	$.register.date($('.date-picker'));
	$('#committeeForm [data-rel="select2"]').select2();
	$("#upload-file").change(function () {
		if ($("#upload-file").val() != "") {
			var $this = $(this);
			var $form = $this.closest("form");
			var $btn = $("button", $form).button('loading');
			var viewHtml = $("#dispatch-file-view").html()
			$("#dispatch-file-view").html('<img src="${ctx}/img/loading.gif"/>')
			$form.ajaxSubmit({
				success: function (ret) {
					if (ret.success) {
						//console.log(ret)
						$("#dispatch-file-view").load("${ctx}/swf/preview?type=html&path=" + encodeURI(ret.filePath));

						$("#committeeForm input[name=filePath]").val(ret.filePath);
					} else {
						$("#dispatch-file-view").html(viewHtml)
					}
					$btn.button('reset');
					$this.removeAttr("disabled");
				}
			});
			$this.attr("disabled", "disabled");
		}
	});

	$("#submitBtn").click(function () {
		$("#committeeForm").submit();
		return false;
	});
	$("#committeeForm").validate({
		submitHandler: function (form) {

			$(form).ajaxSubmit({
				data: {items: new Base64().encode(JSON.stringify(selectedUsers))},
				success: function (ret) {
					if (ret.success) {
						$.hideView();
					}
				}
			});
		}
	});
</script>