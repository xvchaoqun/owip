<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreInfo!=null}">编辑</c:if><c:if test="${cadreInfo==null}">添加</c:if>干部联系方式</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreInfo_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreInfo.id}">
        <input type="hidden" name="cadreId" value="${cadre.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">所属干部</label>
            <div class="col-xs-6">
                <input type="text" value="${sysUser.realname}" disabled>
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">手机号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="mobile" value="${cadreInfo.mobile}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">办公电话</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="officePhone" value="${cadreInfo.officePhone}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">家庭电话</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="homePhone" value="${cadreInfo.homePhone}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">电子邮箱</label>
				<div class="col-xs-6">
                        <input required class="form-control email" type="text" name="email" value="${cadreInfo.email}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreInfo!=null}">确定</c:if><c:if test="${cadreInfo==null}">添加</c:if>"/>
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