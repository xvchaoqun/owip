<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreInspect!=null}">编辑</c:if><c:if test="${cadreInspect==null}">添加</c:if>考察对象
    </h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreInspect_au" id="modalForm" method="post">
        <input type="hidden" name="inspectId" value="${cadreInspect.id}">
			<div class="form-group">
				<label class="col-xs-4 control-label">账号</label>
                <div class="col-xs-6 ${cadre!=null?'label-text':''}">
                    <c:if test="${cadre==null}">
                        <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                name="userId" data-placeholder="请输入账号或姓名或学工号">
                            <option value="${cadre.userId}">${cadre.realname}-${cadre.code}</option>
                        </select>
                    </c:if>
                    <c:if test="${cadre!=null}">
                        <input type="hidden" name="userId" value="${cadre.userId}">
                        ${cadre.realname}-${cadre.code}
                    </c:if>
                </div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">现行政级别</label>
				<div class="col-xs-6">
                    <select  data-rel="select2" name="typeId" data-placeholder="请选择行政级别">
                        <option></option>
                        <jsp:include page="/metaTypes?__code=mc_admin_level"/>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=typeId]").val(${cadre.typeId});
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">现职务属性</label>
				<div class="col-xs-6">
                    <select  data-rel="select2" name="postId" data-placeholder="请选择职务属性">
                        <option></option>
                        <jsp:include page="/metaTypes?__code=mc_post"/>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=postId]").val(${cadre.postId});
                    </script>
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-4 control-label">现所在单位</label>
                <div class="col-xs-8">
                    <select  class="form-control" name="unitId" data-rel="select2" data-placeholder="请选择所属单位">
                        <option></option>
                        <c:forEach items="${unitMap}" var="unit">
                            <option value="${unit.key}">${unit.value.name}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modalForm select[name=unitId]").val('${cadre.unitId}');
                    </script>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">现任职务</label>
                <div class="col-xs-6">
                    <input  class="form-control" type="text" name="post" value="${cadre.post}">
                </div>
            </div>
			<div class="form-group">
				<label class="col-xs-4 control-label">现所在单位及职务</label>
				<div class="col-xs-6">
                        <input  class="form-control" type="text" name="title" value="${cadre.title}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control limited" name="inspectRemark" rows="5">${cadreInspect.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreInspect!=null}">确定</c:if><c:if test="${cadreInspect==null}">添加</c:if>"/>
</div>

<script>
    jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
    jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();
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
    register_user_select($('[data-rel="select2-ajax"]'));
</script>