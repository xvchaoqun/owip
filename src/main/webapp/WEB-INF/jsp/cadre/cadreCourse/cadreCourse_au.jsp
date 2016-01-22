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
    <form class="form-horizontal" action="${ctx}/cadreCourse_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreCourse.id}">
        <input type="hidden" name="cadreId" value="${cadre.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">所属干部</label>
            <div class="col-xs-6">
                <input type="text" value="${sysUser.realname}" disabled>
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">课程名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${cadreCourse.name}">
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