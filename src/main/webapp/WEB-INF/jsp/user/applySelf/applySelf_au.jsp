<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

    <form class="form-horizontal" action="${ctx}/user/applySelf_au" id="modalForm" method="post">
		<div class="form-group">
			<label class="col-xs-3 control-label">前往国家或地区</label>
			<div class="col-xs-6">
				<input type="text" name="toCountry" id="form-field-tags" value="${applySelf.toCountry}" placeholder="输入后选择国家或按回车 ..." />
			</div>
		</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">出行时间</label>
				<div class="col-xs-6">
					<select name="type" data-rel="select2" data-placeholder="请选择出行时间">
						<option></option>
						<c:forEach items="${APPLY_SELF_DATE_TYPE_MAP}" var="type">
							<option value="${type.key}">${type.value}</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=type]").val('${applySelf.type}');
					</script>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">出发时间</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_startDate" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(applySelf.startDate,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">返回时间</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_endDate" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(applySelf.endDate,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">出国事由</label>
				<div class="col-xs-6">
					<textarea required class="form-control limited" type="text" name="reason" rows="2">${applySelf.reason}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">同行人员</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="peerStaff" value="${applySelf.peerStaff}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">费用来源</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="costSource" value="${applySelf.costSource}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所需证件</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="needPassports" value="${applySelf.needPassports}">
				</div>
			</div>
			<%--<div class="form-group">
				<label class="col-xs-3 control-label">其他说明材料</label>
				<div class="col-xs-6">
                        <textarea class="form-control" name="files">${applySelf.files}</textarea>
				</div>
			</div>--%>
    </form>
<div class="center" style="font:bold 30px Verdana, Arial, Helvetica, sans-serif; padding: 50px;">
	<input id="agree" type="checkbox" class="chkBox" style="width: 30px; height: 30px; margin: 0;"/> 信息已确认无误
</div>
<div class="modal-footer center">
	<input id="submit" class="btn btn-success" value="提交申请"/>
</div>
<script src="${ctx}/assets/js/bootstrap-tag.js"></script>
<script src="${ctx}/assets/js/ace/elements.typeahead.js"></script>
<script>

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

    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
	$('.date-picker').datepicker({
		language:"zh-CN",
		autoclose: true,
		todayHighlight: true
	})
	register_user_select($('[data-rel="select2-ajax"]'));
	$('textarea.limited').inputlimiter();
</script>