<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${modifyCadreAuth!=null}">编辑</c:if><c:if test="${modifyCadreAuth==null}">添加</c:if>干部信息修改权限设置</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/modifyCadreAuth_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${modifyCadreAuth.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">干部</label>
				<div class="col-xs-6">
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                            name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                        <option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">起始时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input class="form-control date-picker" name="_startTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(modifyCadreAuth.startTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">结束时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input class="form-control date-picker" name="_endTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(modifyCadreAuth.endTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-3 control-label">是否永久有效</label>
                <div class="col-xs-6">
                    <label>
                        <input name="isUnlimited" ${modifyCadreAuth.isUnlimited?"checked":""}  type="checkbox" />
                        <span class="lbl"></span>
                    </label>
                </div>
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${modifyCadreAuth!=null}">确定</c:if><c:if test="${modifyCadreAuth==null}">添加</c:if>"/>
</div>

<script>
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
    $("#modalForm :checkbox").bootstrapSwitch();
    register_date($('.date-picker'));
    register_user_select($('#modalForm select[name=cadreId]'));
</script>