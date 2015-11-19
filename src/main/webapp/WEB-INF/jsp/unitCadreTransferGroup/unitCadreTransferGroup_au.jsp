<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${unitCadreTransferGroup!=null}">编辑</c:if><c:if test="${unitCadreTransferGroup==null}">添加</c:if>单位任免分组</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/unitCadreTransferGroup_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${unitCadreTransferGroup.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所属单位</label>
				<div class="col-xs-6">
                    <select data-rel="select2-ajax" data-ajax--url="${ctx}/unit_selects"
                            name="unitId" data-placeholder="请选择所属单位">
                        <option value="${unit.id}">${unit.name}</option>
                    </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">分组名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${unitCadreTransferGroup.name}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${unitCadreTransferGroup!=null}">确定</c:if><c:if test="${unitCadreTransferGroup==null}">添加</c:if>"/>
</div>

<script>
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('[data-rel="select2-ajax"]').select2({
        ajax: {
            dataType: 'json',
            delay: 200,
            data: function (params) {
                return {
                    searchStr: params.term,
                    pageSize: 10,
                    pageNo: params.page
                };
            },
            processResults: function (data, params) {
                params.page = params.page || 1;
                return {results: data.options,  pagination: {
                    more: (params.page * 10) < data.totalCount
                }};
            },
            cache: true
        }
    });
</script>