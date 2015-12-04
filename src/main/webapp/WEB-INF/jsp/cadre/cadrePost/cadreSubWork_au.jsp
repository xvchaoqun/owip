<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreSubWork!=null}">编辑</c:if><c:if test="${cadreSubWork==null}">添加</c:if>干部兼职</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreSubWork_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreSubWork.id}">
        <input type="hidden" name="cadreId" value="${cadre.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">所属干部</label>
            <div class="col-xs-6">
                <input type="text" value="${sysUser.realname}" disabled>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">所属单位</label>
            <div class="col-xs-6">
                <select required data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                        name="unitId" data-placeholder="请选择所属单位">
                    <option value="${unit.id}">${unit.name}</option>
                </select>
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">兼任职务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="post" value="${cadreSubWork.post}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">兼任职务任职日期</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_postTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(cadreSubWork.postTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">兼任职务始任日期</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_startTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(cadreSubWork.startTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreSubWork!=null}">确定</c:if><c:if test="${cadreSubWork==null}">添加</c:if>"/>
</div>

<script>
    register_date($('.date-picker'));

    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        _reload();
                        toastr.success('操作成功。', '成功');
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
            delay: 300,
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