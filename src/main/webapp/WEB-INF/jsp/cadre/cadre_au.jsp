<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="<%=SystemConstants.CADRE_STATUS_NOW%>" var="CADRE_STATUS_NOW"/>
<c:set value="<%=SystemConstants.CADRE_STATUS_TEMP%>" var="CADRE_STATUS_TEMP"/>
<c:set value="<%=SystemConstants.CADRE_STATUS_LEAVE%>" var="CADRE_STATUS_LEAVE"/>
<c:set value="<%=SystemConstants.CADRE_STATUS_LEADER_LEAVE%>" var="CADRE_STATUS_LEADER_LEAVE"/>
<c:set value="<%=SystemConstants.CADRE_STATUS_MAP%>" var="CADRE_STATUS_MAP"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadre!=null}">编辑</c:if><c:if test="${cadre==null}">添加</c:if>
        <c:if test="${status==CADRE_STATUS_TEMP}">临时干部</c:if>
        <c:if test="${status==CADRE_STATUS_NOW}">现任干部</c:if>
        <c:if test="${status==CADRE_STATUS_LEAVE}">离任处级干部</c:if>
        <c:if test="${status==CADRE_STATUS_LEADER_LEAVE}">离任校领导干部</c:if>
    </h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadre_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadre.id}">
        <input type="hidden" name="status" value="${status}">
			<div class="form-group">
				<label class="col-xs-3 control-label">账号</label>
				<div class="col-xs-6">
                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/notCadre_selects"
                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                        <option value="${sysUser.id}">${sysUser.realname}</option>
                    </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">行政级别</label>
				<div class="col-xs-6">
                    <select required data-rel="select2" name="typeId" data-placeholder="请选择行政级别">
                        <option></option>
                        <jsp:include page="/metaTypes?__code=mc_admin_level"/>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=typeId]").val(${cadre.typeId});
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">职务属性</label>
				<div class="col-xs-6">
                    <select required data-rel="select2" name="postId" data-placeholder="请选择职务属性">
                        <option></option>
                        <jsp:include page="/metaTypes?__code=mc_post"/>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=postId]").val(${cadre.postId});
                    </script>
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-3 control-label">所属单位</label>
                <div class="col-xs-8">
                    <select required class="form-control" name="unitId" data-rel="select2" data-placeholder="请选择所属单位">
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
                <label class="col-xs-3 control-label">职务</label>
                <div class="col-xs-6">
                    <input required class="form-control" type="text" name="post" value="${cadre.post}">
                </div>
            </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所在单位及职务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="title" value="${cadre.title}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control limited" name="remark" rows="5">${cadre.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadre!=null}">确定</c:if><c:if test="${cadre==null}">添加</c:if>"/>
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
                        SysMsg.success('提交成功。', '成功',function(){
                            $("#jqGrid").trigger("reloadGrid");
                        });
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    register_user_select($('[data-rel="select2-ajax"]'));
</script>