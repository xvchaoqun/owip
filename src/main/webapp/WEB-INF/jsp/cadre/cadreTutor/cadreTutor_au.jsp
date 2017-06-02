<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreTutor!=null}">编辑</c:if><c:if test="${cadreTutor==null}">添加</c:if>导师信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreTutor_au?cadreId=${cadre.id}" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreTutor.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${sysUser.realname}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">类型</label>
            <div class="col-xs-6">
                <select required data-rel="select2" name="type"
                        data-placeholder="请选择" data-width="200">
                    <option></option>
                    <c:forEach items="${CADRE_TUTOR_TYPE_MAP}" var="_type">
                        <option value="${_type.key}">${_type.value}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#modalForm select[name=type]").val(${cadreTutor.type});
                </script>
            </div>
        </div>
        <div class="form-group">
				<label class="col-xs-4 control-label">导师姓名</label>
				<div class="col-xs-3">
                        <input required class="form-control" type="text" name="name" value="${cadreTutor.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">所在单位及职务（职称）</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="title" value="${cadreTutor.title}">
				</div>
			</div>

    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreTutor!=null}">确定</c:if><c:if test="${cadreTutor==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        $("#jqGrid_cadreTutor").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('#modalForm [data-rel="select2-ajax"]').select2({
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