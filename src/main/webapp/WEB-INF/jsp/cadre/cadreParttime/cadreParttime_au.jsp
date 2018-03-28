<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreParttime!=null}">编辑</c:if><c:if test="${cadreParttime==null}">添加</c:if>社会或学术兼职</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreParttime_au?toApply=${param.toApply}&cadreId=${cadre.id}" id="modalForm" method="post">
        <input type="hidden" name="_isUpdate" value="${param._isUpdate}">
        <input type="hidden" name="applyId" value="${param.applyId}">
        <input type="hidden" name="id" value="${cadreParttime.id}">
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
                               data-date-min-view-mode="1"
                               data-date-format="yyyy.mm" value="${cm:formatDate(cadreParttime.startTime,'yyyy.MM')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">结束时间</label>
				<div class="col-xs-6">
                    <div class="input-group" style="width: 120px">
                        <input class="form-control date-picker" name="_endTime" type="text"
                               data-date-min-view-mode="1"
                               data-date-format="yyyy.mm" value="${cm:formatDate(cadreParttime.endTime,'yyyy.MM')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">兼职单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unit" value="${cadreParttime.unit}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">兼任职务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="post" value="${cadreParttime.post}">
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-3 control-label">备注</label>
                <div class="col-xs-6">
                    <textarea class="form-control" name="remark">${cadreParttime.remark}</textarea>
                </div>
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreParttime!=null}">确定</c:if><c:if test="${cadreParttime==null}">添加</c:if>"/>
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
                        $("#jqGrid_cadreParttime").trigger("reloadGrid");
                        </c:if>
                        <c:if test="${param.toApply==1}">
                        <c:if test="${param._isUpdate==1}">
                        $("#item-content").load("${ctx}/modifyCadreParttime_detail?applyId=${param.applyId}&_="+new Date().getTime())
                        </c:if>
                        <c:if test="${param._isUpdate!=1}">
                        $.hashchange('cls=1&module=${MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME}');
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