<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreLeader!=null}">编辑</c:if><c:if test="${cadreLeader==null}">添加</c:if>校领导</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreLeader_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreLeader.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">校领导</label>
				<div class="col-xs-6">
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                            name="cadreId" data-placeholder="请选择干部">
                        <option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">类别</label>
				<div class="col-xs-6">
                    <select data-rel="select2" name="typeId" data-placeholder="请选择类别">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_leader_type"/>
                    </select>
                    <script type="text/javascript">
                        $("#modal form select[name=typeId]").val(${cadreLeader.typeId});
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">分管工作</label>
				<div class="col-xs-6">
                        <textarea required class="form-control limited" name="job" rows="5">${cadreLeader.job}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreLeader!=null}">确定</c:if><c:if test="${cadreLeader==null}">添加</c:if>"/>
</div>

<script>
    $('textarea.limited').inputlimiter();
    $("#modal form").validate({
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