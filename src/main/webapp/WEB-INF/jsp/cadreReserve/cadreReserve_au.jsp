<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="<%=CadreConstants.CADRE_STATUS_SET%>" var="CADRE_STATUS_SET"/>
<c:set value="${CADRE_STATUS_SET.contains(cadre.status)}" var="isCadre"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cadreReserve!=null?'编辑':'添加'}${cm:getMetaType(reserveType).name}
    </h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreReserve_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="reserveId" value="${cadreReserve.id}">
        <input type="hidden" name="reserveType" value="${reserveType}"/>
        <div class="form-group">
            <label class="col-xs-4 control-label">类别</label>
            <div class="col-xs-6">
                <input  name="_isCadre"type="radio" value="0" class="big" ${!isCadre?'checked':''}> 非干部&nbsp;
                <input name="_isCadre" type="radio" value="1" class="big" ${isCadre?'checked':''}> 干部
            </div>
        </div>
        <div id="_isCadreDiv" style="${!isCadre?'display: none':''}">
            <div class="form-group">
                <label class="col-xs-4 control-label"><span class="star">*</span>选择干部</label>
                <div class="col-xs-6">
                    <select ${isCadre?'required':''} data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                            name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                        <option value="${cadre.userId}">${cadre.realname}-${cadre.code}</option>
                    </select>
                </div>
            </div>
        </div>
        <div id="_notCadreDiv" style="${isCadre?'display: none':''}">
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>账号</label>
				<div class="col-xs-6">
                    <select ${!isCadre?'required':''} data-rel="select2-ajax" data-ajax-url="${ctx}/notCadre_selects"
                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                        <option value="${cadre.userId}">${cadre.realname}-${cadre.code}</option>
                    </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">所在单位及职务</label>
				<div class="col-xs-6">
                    <textarea class="form-control" rows="3" name="title">${cadre.title}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control limited" name="reserveRemark" rows="5">${cadreReserve.remark}</textarea>
				</div>
			</div>
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreReserve!=null}">确定</c:if><c:if test="${cadreReserve==null}">添加</c:if>"/>
</div>

<script>

    $("input[name=_isCadre]").change(function(){
        var val = $(this).val();
        var $cadreSelect = $("#modalForm select[name=cadreId]");
        var $userSelect = $("#modalForm select[name=userId]");
        if(val==0){
            $cadreSelect.removeAttr("required");
            $userSelect.attr("required", "required");
            $("#_isCadreDiv").hide();
            $("#_notCadreDiv").show();
        }else if(val==1){
            $userSelect.removeAttr("required");
            $cadreSelect.attr("required", "required");
            $("#_isCadreDiv").show();
            $("#_notCadreDiv").hide();
        }
        $userSelect.val(null).trigger("change");
        $cadreSelect.val(null).trigger("change");
    });

    jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
    jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();
    $('textarea.limited').inputlimiter();
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.user_select($('[data-rel="select2-ajax"]'));
</script>