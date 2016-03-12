<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadrePost!=null}">编辑</c:if><c:if test="${cadrePost==null}">添加</c:if>任职级经历</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadrePost_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadrePost.id}">
        <input type="hidden" name="cadreId" value="${cadre.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">所属干部</label>
            <div class="col-xs-6">
                <input type="text" value="${sysUser.realname}" disabled>
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">行政级别</label>
				<div class="col-xs-6">
                    <select required data-rel="select2" name="adminLevelId" data-placeholder="请选择">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_admin_level"/>
                    </select>
                    <script type="text/javascript">
                        $("#modal form select[name=adminLevelId]").val(${cadrePost.adminLevelId});
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否现任职级</label>
				<div class="col-xs-6">
                    <label>
                        <input name="isPresent" ${cadrePost.isPresent?"checked":""}  type="checkbox" />
                        <span class="lbl"></span>
                    </label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">职级始任日期</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_startTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(cadrePost.startTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">职级始任文号</label>
				<div class="col-xs-6">
                        <input  class="form-control" type="text" name="startDispatchCadreId" value="${cadrePost.startDispatchCadreId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">职级结束日期</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_endTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(cadrePost.endTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">职级结束文号</label>
				<div class="col-xs-6">
                        <input  class="form-control" type="text" name="endDispatchCadreId" value="${cadrePost.endDispatchCadreId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <textarea  class="form-control limited"  name="remark"  rows="5">${cadrePost.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadrePost!=null}">确定</c:if><c:if test="${cadrePost==null}">添加</c:if>"/>
</div>

<script>
    $("#modal :checkbox").bootstrapSwitch();
    $('textarea.limited').inputlimiter();

    register_date($('.date-picker'));

    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        _reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>