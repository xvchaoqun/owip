<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${unit!=null}">编辑</c:if><c:if test="${unit==null}">添加</c:if>${UNIT_STATUS_MAP.get(status)}</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/unit_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${unit.id}">
        <input type="hidden" name="status" value="${status}">
			<div class="form-group">
				<label class="col-xs-3 control-label">选择学校单位</label>
				<div class="col-xs-6">
					<select name="schoolUnitCode" data-width="272" data-placeholder="请选择">
						<option></option>
						<c:forEach items="${schoolUnits}" var="su">
							<option value="${su.code}">${su.name}</option>
						</c:forEach>
					</select>
					<span class="help-block">注：选择学校单位时会覆盖以下单位编码和单位名称</span>
					<script>
						$("#modalForm select[name=schoolUnitCode]").val('${unit.code}');
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>单位编号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="code" value="${unit.code}">
					    <span class="help-block">注：正在运转单位中的单位编号不可重复（但可与历史单位的单位编号重复）</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>单位名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${unit.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>单位类型</label>
				<div class="col-xs-6">
                        <select required data-rel="select2" name="typeId"
								data-width="272"
								data-placeholder="请选择单位类型">
                            <option></option>
                            <c:import url="/metaTypes?__code=mc_unit_type"/>
                        </select>
                        <script type="text/javascript">
                            $("#modal form select[name=typeId]").val(${unit.typeId});
                        </script>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <textarea class="form-control limited" name="remark">${unit.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
        <c:if test="${unit!=null}">确定</c:if><c:if test="${unit==null}">添加</c:if></button>
</div>
<script>
    $('textarea.limited').inputlimiter();
    $.fileInput($('#modalForm input[name=_file]'),{
        no_file: '请选择pdf文件 ...',
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
    });
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modal form").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $.reloadMetaData(function(){
                            page_reload();
                        });
                        //SysMsg.success('操作成功。', '成功');
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    var $schoolUnitSelect = $('#modalForm select[name=schoolUnitCode]').select2();
    $schoolUnitSelect.on("change", function () {
		var code = $.trim($(this).val());
		var name = $.trim($("option:checked", this).text());
		console.log('name=' + name)
		if(code != ''){
			$('#modalForm input[name=code]').val(code);
		}
		if(name != ''){
			$('#modalForm input[name=name]').val(name);
		}
	})

    $('[data-rel="tooltip"]').tooltip();
</script>