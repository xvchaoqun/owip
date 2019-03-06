<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreCourse!=null}">编辑</c:if><c:if test="${cadreCourse==null}">添加</c:if>教学课程</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreCourse_au?toApply=${param.toApply}&cadreId=${cadre.id}" id="modalForm" method="post">
        <input type="hidden" name="_isUpdate" value="${param._isUpdate}">
        <input type="hidden" name="applyId" value="${param.applyId}">
        <input type="hidden" name="id" value="${cadreCourse.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${sysUser.realname}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>类型</label>
            <div class="col-xs-6">
                <select required data-rel="select2" class="form-control" name="type" data-placeholder="请选择">
                    <option></option>
                    <c:forEach var="courseType" items="<%=CadreConstants.CADRE_COURSE_TYPE_MAP%>">
                        <option value="${courseType.key}">${courseType.value}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#modal select[name=type]").val('${cadreCourse.type}');
                </script>
            </div>
        </div>
        <c:if test="${empty cadreCourse && param.toApply!=1}">
			<div class="form-group">
				<label class="col-xs-3 control-label" style="padding-top: 100px"><span class="star">*</span>课程名称（多门课程以#号分隔）</label>
				<div class="col-xs-8">
                        <textarea required class="form-control" name="names" rows="10"></textarea>
                    <span class="help-block">注：不要加书名号。</span>
				</div>
			</div>
        </c:if>
        <c:if test="${not empty cadreCourse || param.toApply==1}">
            <div class="form-group">
                <label class="col-xs-3 control-label" ><span class="star">*</span>课程名称</label>
                <div class="col-xs-6">
                    <input required class="form-control" type="text" name="name" value="${cadreCourse.name}">
                    <span class="help-block">注：不要加书名号。</span>
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-3 control-label" >备注</label>
            <div class="col-xs-6">
                <textarea class="form-control" name="remark" rows="5">${cadreCourse.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreCourse!=null}">确定</c:if><c:if test="${cadreCourse==null}">添加</c:if>"/>
</div>

<script>
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        <c:if test="${param.toApply!=1}">
                        $("#jqGrid_cadreCourse").trigger("reloadGrid");
                        </c:if>
                        <c:if test="${param.toApply==1}">
                        <c:if test="${param._isUpdate==1}">
                        $("#body-content-view").load("${ctx}/modifyTableApply_detail?module=${param.module}&applyId=${param.applyId}&_="+new Date().getTime())
                        </c:if>
                        <c:if test="${param._isUpdate!=1}">
                        $.hashchange('cls=1&module=${param.module}');
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