<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreTrain!=null}">编辑</c:if><c:if test="${cadreTrain==null}">添加</c:if>培训记录</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreTrain_au?toApply=${param.toApply}&cadreId=${cadre.id}" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="_isUpdate" value="${param._isUpdate}">
        <input type="hidden" name="applyId" value="${param.applyId}">
        <input type="hidden" name="id" value="${cadreTrain.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${sysUser.realname}
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>起始时间</label>
				<div class="col-xs-6">
                    <div class="input-group date" data-date-format="yyyy.mm.dd" style="width: 130px">
                        <input required class="form-control" name="_startTime" type="text"
                                value="${cm:formatDate(cadreTrain.startTime,'yyyy.MM.dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>结束时间</label>
				<div class="col-xs-6">
                    <div class="input-group date" data-date-format="yyyy.mm.dd" style="width: 130px">
                        <input required class="form-control" name="_endTime" type="text"
                               value="${cm:formatDate(cadreTrain.endTime,'yyyy.MM.dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>培训内容</label>
				<div class="col-xs-6">
                    <textarea required class="form-control" name="content">${cadreTrain.content}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>主办单位</label>
				<div class="col-xs-6">
                    <textarea required class="form-control" name="unit">${cadreTrain.unit}</textarea>
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-3 control-label">相关文件</label>
                <div class="col-xs-6 uploader">
                    <input class="form-control" type="file" name="_file" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">备注</label>
                <div class="col-xs-6">
                    <textarea class="form-control noEnter" name="remark">${cadreTrain.remark}</textarea>
                </div>
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button type="button" id="submitBtn" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
        ${cadreTrain!=null?'确定':'添加'}
    </button>
</div>
<script>

    <shiro:hasPermission name="cadre:updateWithoutRequired">
        $('input[name=unit]').closest(".form-group").find('span.star').remove();
        $('input[name=unit]').prop("required", false);
    </shiro:hasPermission>

    $.register.date($('.input-group.date'));
    $.fileInput($('#modalForm input[type=file]'),{
        no_file:'请上传pdf文件',
        allowExt: ['pdf']
    })
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        <c:if test="${param.toApply!=1}">
                        $("#jqGrid_cadreTrain").trigger("reloadGrid");
                        </c:if>
                        <c:if test="${param.toApply==1}">
                        <c:if test="${param._isUpdate==1}">
                        $("#body-content-view").load("${ctx}/modifyTableApply_detail?module=${param.module}&opType=${param.opType}&applyId=${param.applyId}&_="+new Date().getTime())
                        </c:if>
                        <c:if test="${param._isUpdate!=1}">
                        $.hashchange('cls=1&module=${param.module}');
                        </c:if>
                        </c:if>
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>