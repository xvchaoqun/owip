<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreCompany!=null}">编辑</c:if><c:if test="${cadreCompany==null}">添加</c:if>干部企业兼职情况</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreCompany_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreCompany.id}">
        <input type="hidden" name="cadreId" value="${cadre.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">所属干部</label>
            <div class="col-xs-6">
                <input type="text" value="${sysUser.realname}" disabled>
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">兼职起始时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_startTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(cadreCompany.startTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">兼职单位及职务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unit" value="${cadreCompany.unit}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">报批单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="reportUnit" value="${cadreCompany.reportUnit}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreCompany!=null}">确定</c:if><c:if test="${cadreCompany==null}">添加</c:if>"/>
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
</script>