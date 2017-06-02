<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>修改对应工作单位</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreWork_updateUnitId?cadreId=${cadre.id}" id="modalForm" method="post">
            <input type="hidden" name="id" value="${cadreWork.id}">
			<div class="form-group">
				<label class="col-xs-4 control-label">姓名</label>
				<div class="col-xs-6 label-text">
                    ${sysUser.realname}
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-4 control-label">任职单位</label>
                <div class="col-xs-6 label-text">
                    ${cadreWork.unit}
                </div>
            </div>
			<div class="form-group">
				<label class="col-xs-4 control-label">对应工作单位</label>
				<div class="col-xs-6">
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects?status=1"
                            name="unitId" data-placeholder="请选择单位">
                        <option value="${unit.id}">${unit.name}</option>
                    </select>
                    <div class="space-4"/>
                    <span class="help-block">* 留空则删除对应工作单位</span>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        $("#jqGrid_cadreWork").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    register_ajax_select('#modalForm [data-rel="select2-ajax"]')
</script>