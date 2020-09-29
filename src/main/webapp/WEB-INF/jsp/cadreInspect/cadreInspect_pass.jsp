<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>通过常委会任命</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreInspect_pass" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="inspectId" value="${cadreInspect.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">账号</label>
				<div class="col-xs-6 label-text">
                    ${cadre.realname}-${cadre.code}
				</div>
			</div>
        <c:if test="${_p_hasKjCadre}">
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>干部类型</label>
                <div class="col-xs-8">
                    <div class="input-group">
                        <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                            <input required type="radio" name="status" id="status1" value="${CADRE_STATUS_CJ}">
                            <label for="status1">
                                    处级干部
                            </label>
                        </div>
                        <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                            <input required type="radio" name="status" id="status2" value="${CADRE_STATUS_KJ}">
                            <label for="status2">
                                    科级干部
                            </label>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${_p_useCadreState}">
        <div class="form-group">
            <label class="col-xs-3 control-label">${cm:getTextFromHTML(_pMap['cadreStateName'])}</label>
            <div class="col-xs-8">
                <select data-rel="select2" data-width="100" name="state" data-placeholder="请选择">
                    <option></option>
                    <c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_cadre_state').id}"/>
                </select>
                <script type="text/javascript">
                    $("#modalForm select[name=state]").val(${cadre.state});
                </script>
            </div>
        </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-3 control-label">任职单位及职务</label>
            <div class="col-xs-6">
                <textarea class="form-control" rows="3" name="title">${cadre.title}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" name="inspectRemark" rows="5">${cadreInspect.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-success" value="通过任命"/>
</div>

<script>
    $('textarea.limited').inputlimiter();
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $.openView('${ctx}/cadre_view?cadreId=${cadre.id}&to=cadrePost_page')
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $("#modal :checkbox").bootstrapSwitch();

    $.register.user_select($('[data-rel="select2-ajax"]'));
</script>