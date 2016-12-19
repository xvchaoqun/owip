<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="CADRE_COURSE_TYPE_MAP" value="<%=SystemConstants.CADRE_COURSE_TYPE_MAP%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreCourse!=null}">编辑</c:if><c:if test="${cadreCourse==null}">添加</c:if>教学课程</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreCourse_au?cadreId=${cadre.id}" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreCourse.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${sysUser.realname}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">类型</label>
            <div class="col-xs-6">
                <select required data-rel="select2" class="form-control" name="type" data-placeholder="请选择">
                    <option></option>
                    <c:forEach var="courseType" items="${CADRE_COURSE_TYPE_MAP}">
                        <option value="${courseType.key}">${courseType.value}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#modal select[name=type]").val('${cadreCourse.type}');
                </script>
            </div>
        </div>
        <c:if test="${empty cadreCourse}">
			<div class="form-group">
				<label class="col-xs-3 control-label" style="padding-top: 100px">课程名称（多门课程以#号分隔）</label>
				<div class="col-xs-8">
                        <textarea required class="form-control" name="names" rows="10"></textarea>
				</div>
			</div>
        </c:if>
        <c:if test="${not empty cadreCourse}">
            <div class="form-group">
                <label class="col-xs-3 control-label" >课程名称</label>
                <div class="col-xs-6">
                    <input required class="form-control" type="text" name="name" value="${cadreCourse.name}">
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
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreCourse!=null}">确定</c:if><c:if test="${cadreCourse==null}">添加</c:if>"/>
</div>

<script>
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        $("#jqGrid_cadreCourse").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>