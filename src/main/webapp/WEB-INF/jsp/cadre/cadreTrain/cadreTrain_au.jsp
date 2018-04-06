<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreTrain!=null}">编辑</c:if><c:if test="${cadreTrain==null}">添加</c:if>培训记录</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreTrain_au?toApply=${param.toApply}&cadreId=${cadre.id}" id="modalForm" method="post">
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
				<label class="col-xs-3 control-label">起始时间</label>
				<div class="col-xs-6">
                    <div class="input-group" style="width: 120px">
                        <input required class="form-control date-picker" name="_startTime" type="text"
                               data-date-format="yyyy.mm.dd" value="${cm:formatDate(cadreTrain.startTime,'yyyy.MM.dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">结束时间</label>
				<div class="col-xs-6">
                    <div class="input-group" style="width: 120px">
                        <input required class="form-control date-picker" name="_endTime" type="text"
                               data-date-format="yyyy.mm.dd" value="${cm:formatDate(cadreTrain.endTime,'yyyy.MM.dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">培训内容</label>
				<div class="col-xs-6">
                    <textarea required class="form-control" name="content">${cadreTrain.content}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">主办单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unit" value="${cadreTrain.unit}">
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-3 control-label">备注</label>
                <div class="col-xs-6">
                    <textarea class="form-control" name="remark">${cadreTrain.remark}</textarea>
                </div>
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreTrain!=null}">确定</c:if><c:if test="${cadreTrain==null}">添加</c:if>"/>
</div>

<script>

    $.register.date($('.date-picker'));

    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        <c:if test="${param.toApply!=1}">
                        $("#jqGrid_cadreTrain").trigger("reloadGrid");
                        </c:if>
                        <c:if test="${param.toApply==1}">
                        <c:if test="${param._isUpdate==1}">
                        $("#body-content-view").load("${ctx}/modifyCadreTrain_detail?applyId=${param.applyId}&_="+new Date().getTime())
                        </c:if>
                        <c:if test="${param._isUpdate!=1}">
                        $.hashchange('cls=1&module=${MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN}');
                        </c:if>
                        </c:if>
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>