<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" onclick="openView_safeBox()" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${safeBox!=null}">编辑</c:if><c:if test="${safeBox==null}">添加</c:if>保险柜</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/safeBox_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${safeBox.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">保险柜编号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="code" value="${safeBox.code}">
				</div>
			</div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" type="text" name="remark" rows="2" value="${safeBox.remark}"></textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" onclick="openView_safeBox()" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${safeBox!=null}">确定</c:if><c:if test="${safeBox==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        SysMsg.success('操作成功。', '成功', function(){
                            openView_safeBox("${param.pageNo}");
                        });
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
    $('textarea.limited').inputlimiter();
</script>