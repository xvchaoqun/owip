<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/parttime/constants.jsp" %>
    <h3 class="header">兼职申报变更</h3>
    <form class="form-horizontal" action="${ctx}/parttime/parttimeApply_change" autocomplete="off" disableautocomplete id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${parttimeApply.id}">
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>干部</label>
				<div class="col-xs-6">
					<select required data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
							name="cadreId" data-placeholder="请选择干部" disabled="disabled">
						<option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">兼职单位类别</label>
				<div class="col-xs-6" style="margin-top:3px">
					<select name="type" data-rel="select2" data-width="255"
							data-placeholder="请选择" required>
						<option></option>
						<c:forEach items="${PARTTIME_TYPE_MAP}" var="type">
							<option value="${type.key}">${type.value}</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=type]").val('${parttimeApply.type}');
					</script>
				</div>
			</div>
		<div class="form-group">
			<label class="col-xs-4 control-label">兼职单位及职务</label>
			<div class="col-xs-8">
				<input required type="text" name="title" value="${parttimeApply.title}"  style="width: 255px" />
			</div>
		</div>

		<div class="form-group">
			<label class="col-xs-4 control-label">兼职开始日期</label>
			<div class="col-xs-8">
				<div class="input-group"  style="width: 255px">
					<input required class="form-control  date-range-picker" data-rel="date-range-picker"
						   data-date-format="yyyy-mm-dd" name="startTime" type="text"
						   value="${cm:formatDate(parttimeApply.endTime,'yyyy-MM-dd')}" />
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>

		<div class="form-group">
			<label class="col-xs-4 control-label">兼职结束日期</label>
			<div class="col-xs-8">
				<div class="input-group"  style="width: 255px">
					<input required class="form-control date-range-picker" data-rel="date-range-picker"
						   data-date-format="yyyy-mm-dd" name="endTime" type="text"
						   value="${cm:formatDate(parttimeApply.endTime,'yyyy-MM-dd')}" />
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>

		<div class="form-group">
			<label class="col-xs-4 control-label">首次/连任</label>
			<div class="col-xs-8">
				<div class="input-group">
					<div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
						<input required type="radio" name="isFirst" id="first" value="0">
						<label for="first">
							首次
						</label>
					</div>
					<div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
						<input required type="radio" name="isFirst" id="series" value="1">
						<label for="series">
							连任
						</label>
					</div>
				</div>
				<script>
					var value = ${parttimeApply.isFirst ? 1 : 0};
					$("#modalForm input[name=isFirst]").eq(value).attr('checked', true);
				</script>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label">是否有国境外背景</label>
			<div class="col-xs-8">
				<div class="input-group">
					<div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
						<input required type="radio" name="background" id="no" value="0">
						<label for="no">
							否
						</label>
					</div>
					<div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
						<input required type="radio" name="background" id="yes" value="1">
						<label for="yes">
							是
						</label>
					</div>
					<script>
						var value = ${parttimeApply.background ? 1 : 0};
						$("#modalForm input[name=background]").eq(value).attr('checked', true);
					</script>
				</div>
			</div>
		</div>

		<div class="form-group">
			<label class="col-xs-4 control-label">是否取酬</label>
			<div class="col-xs-8">
				<div class="input-group">
					<div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
						<input required type="radio" name="hasPay" id="notincome" value="0">
						<label for="notincome">
							否
						</label>
					</div>
					<div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
						<input required type="radio" name="hasPay" id="income" value="1">
						<label for="income">
							是
						</label>
					</div>
					<script>
						var value = ${parttimeApply.hasPay ? 1 : 0};
						$("#modalForm input[name=hasPay]").eq(value).attr('checked', true);
					</script>
				</div>
			</div>
		</div>

		<div class="form-group">
			<label class="col-xs-4 control-label">取酬金额</label>
			<div class="col-xs-8">
				<input type="text" name="balance" value="${parttimeApply.balance}"  style="width: 255px" />
			</div>
		</div>

		<div class="form-group">
			<label class="col-xs-4 control-label">本人说明材料</label>
			<div class="col-xs-2">
			<input  class="form-control" type="file" name="_modifyProof" style="width: 220px"/>
				</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span>变更原因</label>
			<div class="col-xs-6">
				<textarea required class="form-control" name="modifyRemark" style="width: 255px"></textarea>
			</div>
		</div>
    </form>
<div class="clearfix form-actions">
	<div class="col-md-offset-3 col-md-9">
		<button class="btn btn-info" type="submit">
			<i class="ace-icon fa fa-check bigger-110"></i>
			提交
		</button>
		&nbsp; &nbsp; &nbsp;
		<button class="hideView btn btn-default" type="button">
			<i class="ace-icon fa fa-undo bigger-110"></i>
			取消
		</button>
	</div>
</div>

<script src="${ctx}/assets/js/bootstrap-tag.js"></script>
<script src="${ctx}/assets/js/ace/elements.typeahead.js"></script>
<script>

	$.fileInput($("input[name=_modifyProof]"))

	$("button[type=submit]").click(function(){$("#modalForm").submit(); return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {

			var hasPay = $("#modalForm input[name=hasPay]:checked").val();
			var balance = $("#modalForm input[name=balance]").val();
			if (hasPay == 1) {
				if (balance == '') {
					SysMsg.info("请填写取酬金额");
					$("input[name=balance]").val('').focus();
					return;
				}
			} else if (hasPay == 0) {
				if (balance != '' && balance > 0) {
					SysMsg.info("未取酬时取酬金额应为0或空");
					$("input[name=balance]").val('').focus();
					return;
				}
			}

			$(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
						//SysMsg.success('修改成功。', '成功',function(){
							$("#jqGrid").trigger("reloadGrid");
							$.hideView();
						//});
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
	$.register.date($('.date-picker'))
	$.register.datetime($('.datetime-picker'));
	$.register.user_select($('[data-rel="select2-ajax"]'));
	$('textarea.limited').inputlimiter();
</script>