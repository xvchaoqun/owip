<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/cla/constants.jsp" %>
<div class="tabbable" style="width: 800px;">
	<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
		<div style="margin-bottom: 8px">

			<div class="buttons">
				<a href="javascript:;" class="hideView btn btn-sm btn-success">
					<i class="ace-icon fa fa-backward"></i>
					返回
				</a>
			</div>
		</div>
	</ul>

	<div class="tab-content">
		<div class="tab-pane in active">
			<form class="form-horizontal" action="${ctx}/user/cla/claApply_au" id="applyForm" method="post" enctype="multipart/form-data">
				<input type="hidden" name="id" value="${claApply.id}">
				<input type="hidden" name="cadreId" value="${param.cadreId}">
				<div class="form-group">
					<label class="col-xs-4 control-label">类别</label>
					<div class="col-xs-6">
						<select name="type" data-rel="select2" data-width="255"
								data-placeholder="请选择">
							<option></option>
							<c:forEach items="${CLA_APPLY_TYPE_MAP}" var="type">
								<option value="${type.key}">${type.value}</option>
							</c:forEach>
						</select>
						<script>
							$("#applyForm select[name=type]").val('${claApply.type}');
						</script>
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-4 control-label">出发时间</label>
					<div class="col-xs-8">
						<div class="input-group"  style="width: 255px">
							<input  class="form-control datetime-picker" name="startTime" type="text"
								   value="${cm:formatDate(claApply.startTime,'yyyy-MM-dd HH:mm')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">返校时间</label>
					<div class="col-xs-8">
						<div class="input-group"  style="width: 255px">
							<input class="form-control datetime-picker" name="endTime" type="text"
								   value="${cm:formatDate(claApply.endTime,'yyyy-MM-dd HH:mm')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">目的地</label>
					<div class="col-xs-8">
						<input type="text" name="destination" value="${claApply.destination}"  style="width: 255px" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">请假事由</label>
					<div class="col-xs-8">
						<textarea  class="form-control limited" name="reason" maxlength="100" style="width: 255px">${claApply.reason}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">同行人员</label>
					<div class="col-xs-8">
						<input type="text" name="peerStaff" value="${claApply.peerStaff}"  style="width: 255px" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">备注</label>
					<div class="col-xs-8">
						<textarea  class="form-control limited" name="remark" maxlength="100" style="width: 255px" >${claApply.remark}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">其他说明材料</label>
					<div class="col-xs-8 file">
						<div class="files"  style="width: 255px">
							<input class="form-control" type="file" name="_files[]"  />
						</div>
						<button type="button" onclick="addFile()" class="btn btn-default btn-xs"><i class="fa fa-plus"></i></button>
					</div>
				</div>
				<c:if test="${fn:length(files)>0}">
					<div class="form-group" id="fileGroup">
						<label class="col-xs-4 control-label">已上传材料</label>
						<div class="col-xs-8">
							<c:forEach items="${files}" var="file">
								<div id="file${file.id}" class="file row well well-sm col-xs-12">
									<div class="col-xs-9 ">
										<a href="${ctx}/cla/claApply_download?id=${file.id}" target="_blank">${file.fileName}</a></div>
									<div class="col-xs-3"><a href="javascript:;" onclick="_delFile(${file.id}, '${file.fileName}')">删除</a></div>
								</div>
							</c:forEach>
						</div>
					</div>
				</c:if>
			</form>
			<%--<div class="center" style="font:bold 30px Verdana, Arial, Helvetica, sans-serif; padding-bottom: 50px;">
				<input id="agree" type="checkbox" class="chkBox" style="width: 30px; height: 30px; margin: 0;"/> 信息已确认无误
			</div>--%>
			<div class="modal-footer center">
				<input id="submitBtn"
					   data-loading-text="提交中..."
					   data-success-text="您的申请已提交成功"
					   autocomplete="off" class="btn btn-primary btn-lg" value="${param.edit==1?"修改提交":"提交申请"}"/>
			</div>
		</div>
	</div>
</div>

<style>
	#applyForm input[type=radio], #applyForm input[type=checkbox]{
		width: 20px;
		height: 20px;
		_vertical-align: -1px;/*针对IE6使用hack*/
		vertical-align: -5px;
	}
	#applyForm .tags{
		width: 256px;
		min-height: 50px;
	}
	#applyForm  .control-label{
		font-size: 20px;
		font-weight: bolder;
	}
	.choice{
		font-size: 20px;
	}
	#applyForm .form-group{
		padding-bottom: 5px;
		padding-top:0px!important;
	}
	.file label{
		margin-bottom:15px;
	}

</style>
<script src="${ctx}/assets/js/bootstrap-tag.js"></script>
<script src="${ctx}/assets/js/ace/elements.typeahead.js"></script>
<script>

	function _delFile(id, name){

		SysMsg.confirm("确定删除'"+name+"'吗？", "操作确认", function () {
			$.post("${ctx}/user/cla/claApplyFile_del",{id:id},function(ret){
				if(ret.success){
					SysMsg.success("删除成功",'',function(){
						$("#file"+id).remove();
						if($("#fileGroup").find(".file").length==0){
							$("#fileGroup").remove();
						}
					});
				}
			});
		});
	}

	$.fileInput($('input[type=file]'));

	function addFile(){
		var _file = $('<input class="form-control" type="file" name="_files[]" />');
		$(".files").append(_file);
		$.fileInput(_file);
		return false;
	}
	var tag_input = $('#form-field-tags');
	try{
		tag_input.tag(
				{
					placeholder:tag_input.attr('placeholder'),
					//enable typeahead by specifying the source array
					source: ${countryList}
				}
		)
	} catch(e) {
		//display a textarea for old IE, because it doesn't support this plugin or another one I tried!
		tag_input.after('<textarea id="'+tag_input.attr('id')+'" name="'+tag_input.attr('name')+'" rows="3">'+tag_input.val()+'</textarea>').remove();
		//autosize($('#form-field-tags'));
	}

	$("#submitBtn").click(function(){$("#applyForm").submit();return false;});
    $("#applyForm").validate({
        submitHandler: function (form) {

			// 出行时间
			var type = $("#applyForm select[name=type]").val().trim();
			if(type==''){
				SysMsg.info("请选择类别");
				$("input[name=type]").val('').focus();
				return;
			}

			var startTime = $("#applyForm input[name=startTime]").val().trim();
			if(startTime==''){
				SysMsg.info("请选择出发时间",'',function(){
					$("input[name=startTime]").val('').focus();
				});
				return;
			}
			var endTime = $("#applyForm input[name=endTime]").val().trim();
			if(endTime==''){
				SysMsg.info("请选择返校时间",'',function(){
					$("input[name=endTime]").val('').focus();
				});
				return;
			}

			var destination = $("#applyForm input[name=destination]").val().trim();
			if($.trim(destination)==''){
				SysMsg.info("请填写目的地",'',function(){
					$("input[name=destination]").val('').focus();
				});
				return;
			}
			var reason = $("#applyForm textarea[name=reason]").val().trim();
			if($.trim(reason)==''){
				SysMsg.info("请填写请假事由",'',function(){
					$("textarea[name=reason]").val('').focus();
				});
				return;
			}
			/*var peerStaff = $("#applyForm input[name=peerStaff]").val().trim();
			if($.trim(peerStaff)==''){
				SysMsg.info("请填写同行人员",'',function(){
					$("input[name=peerStaff]").val('').focus();
				});
				return;
			}*/

			var $btn = $("#submitBtn").button('loading');
			//setTimeout(function () { $btn.button('reset'); },1000);
            $(form).ajaxSubmit({
				dataType:'json',
                success:function(ret){
                    if(ret.success){
						$btn.button("success").addClass("btn-success");
						$.hideView();
                    }else{
						$btn.button('reset');
					}
                }
            });
        }
    });

    $('#applyForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
	$.register.datetime($('.datetime-picker'));
	$.register.user_select($('[data-rel="select2-ajax"]'));
	$('textarea.limited').inputlimiter();
</script>