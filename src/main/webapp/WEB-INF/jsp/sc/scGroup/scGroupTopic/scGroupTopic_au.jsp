<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row dispatch_au">
	<div class="preview">
		<div class="widget-box">
			<div class="widget-header">
				<h4 class="smaller">
					讨论议题文件预览
					<div style="position: absolute; left:180px;top:8px;">
						<form action="${ctx}/sc/scGroupTopic_upload"
							  enctype="multipart/form-data" method="post"
							  class="btn-upload-form">
							<button type="button"
									data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中..."
									class="hideView btn btn-xs btn-primary">
								<i class="ace-icon fa fa-upload"></i>
								上传讨论议题文件
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
						<c:import url="${ctx}/swf/preview?type=html&path=${scGroupTopic.filePath}"/>
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
						${scGroupTopic!=null?"修改":"添加"}讨论议题
					</h4>
				</div>
				<div class="widget-body">
					<div class="widget-main">
						<form class="form-horizontal" action="${ctx}/sc/scGroupTopic_au" id="modalForm" method="post"
							  enctype="multipart/form-data">
							<div class="row">
								<input type="hidden" name="id" value="${scGroupTopic.id}">
								<input type="hidden" name="filePath" value="${scGroupTopic.filePath}">

								<div class="form-group">
									<label class="col-xs-3 control-label">所属干部小组会</label>
									<div class="col-xs-6">
										<select required name="groupId" data-rel="select2"
												data-width="240"
												data-placeholder="请选择">
											<option></option>
											<c:forEach var="scGroup" items="${scGroups}">
												<option value="${scGroup.id}">干部小组会[${cm:formatDate(scGroup.holdDate, "yyyyMMdd")}]号</option>
											</c:forEach>
										</select>
										<script>
											$("#modalForm select[name=groupId]").val("${groupId}");
										</script>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">议题名称</label>

									<div class="col-xs-6">
										<input required class="form-control" type="text" name="name" value="${scGroupTopic.name}">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">涉及单位</label>

									<div class="col-xs-8">
										<select class="multiselect" name="unitIds[]" multiple="">
											<optgroup label="正在运转单位">
												<c:forEach items="${runUnits}" var="unit">
													<option value="${unit.id}">${unit.name}</option>
												</c:forEach>
											</optgroup>
											<optgroup label="历史单位">
												<c:forEach items="${historyUnits}" var="unit">
													<option value="${unit.id}">${unit.name}</option>
												</c:forEach>
											</optgroup>
										</select>
									</div>
								</div>

								<div class="form-group">
									<div class="col-xs-12" style="margin-left: 10px;">
										<div style="height:0px;position: relative;z-index: 999;top: 5px;left: 220px;">议题内容</div>
										<textarea id="contentId">${scGroupTopic.content}</textarea>
									</div>
								</div>
								<div class="form-group">
									<div class="col-xs-12" style="margin-left: 10px;">
										<div style="height:0px;position: relative;z-index: 999;top: 5px;left: 210px;">议题讨论备忘</div>
										<textarea id="memoId">${scGroupTopic.memo}</textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">备注</label>

									<div class="col-xs-6">
                                        <textarea class="form-control limited"
												  name="remark">${scGroupTopic.remark}</textarea>
									</div>
								</div>
							</div>
							<div class="clearfix form-actions center">
								<button class="btn btn-info btn-sm" type="button" id="submitBtn">
									<i class="ace-icon fa fa-check "></i>
									${scGroupTopic!=null?"修改":"添加"}
								</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="${ctx}/extend/ke4/kindeditor-all-min.js"></script>
<script src="${ctx}/assets/js/bootstrap-multiselect.js"></script>
<link rel="stylesheet" href="${ctx}/assets/css/bootstrap-multiselect.css" />
<script>
	var contentKe = KindEditor.create('#contentId', {
		//cssPath:"${ctx}/css/ke.css",
		items: ["source", "|", "fullscreen"],
		height: '200px',
		width: '480px',
		minWidth: 480
	});

	var memoKe = KindEditor.create('#memoId', {
		//cssPath:"${ctx}/css/ke.css",
		items: ["source", "|", "fullscreen"],
		height: '200px',
		width: '480px',
		minWidth: 480
	});

	$.register.multiselect($('#modalForm select[name="unitIds[]"]'), ${selectUnitIds},{enableClickableOptGroups: true,
		enableCollapsibleOptGroups: true, buttonWidth:'240px'});

	$.register.date($('.date-picker'));
	$('#modalForm [data-rel="select2"]').select2();
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

						$("#modalForm input[name=filePath]").val(ret.filePath);
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
		var unitIds = $('#modalForm select[name="unitIds[]"]').val();
		console.log(unitIds)
		if(unitIds==undefined || unitIds.length==0){
			$.tip({
				$target: $('#modalForm select[name="unitIds[]"]').closest("div").find(".multiselect-native-select"),
				type: 'info', msg: "请选择涉及单位。"
			});
		}
		$("#modalForm").submit();
		return false;
	});
	$("#modalForm").validate({
		submitHandler: function (form) {
			var unitIds = $('#modalForm select[name="unitIds[]"]').val();
			if(unitIds.length==0){
				return;
			}
			$(form).ajaxSubmit({
				data: {content:contentKe.html(), memo: memoKe.html()},
				success: function (ret) {
					if (ret.success) {
						$.hideView();
					}
				}
			});
		}
	});
</script>