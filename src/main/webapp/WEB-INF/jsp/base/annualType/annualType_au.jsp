<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="ANNUAL_TYPE_MODULE_MAP" value="<%=SystemConstants.ANNUAL_TYPE_MODULE_MAP%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${annualType!=null}">编辑</c:if><c:if test="${annualType==null}">添加</c:if>${ANNUAL_TYPE_MODULE_MAP.get(cm:toByte(param.module))}</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/annualType_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${annualType.id}">
        <input type="hidden" name="module" value="${param.module}">
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>所属年份</label>
                <div class="col-xs-6">
                    <div class="input-group">
                        <input required autocomplete="off" class="form-control date-picker" placeholder="请选择年份" name="year" type="text"
                               data-date-format="yyyy" data-date-min-view-mode="2" value="${annualType.year}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${annualType.name}">
				</div>
			</div>
            <c:if test="${param.hasAttr==1}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>属性</label>
				<div class="col-xs-6">
                    <input required class="form-control" type="text" name="attr" value="${annualType.attr}">
				</div>
			</div>
            </c:if>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${annualType!=null}">确定</c:if><c:if test="${annualType==null}">添加</c:if></button>
</div>
<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        _reloadDiv(${param.module})
                    }
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>