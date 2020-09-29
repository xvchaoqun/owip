<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>编辑学习情况</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetPlanCourseObjResult_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="planCourseId" value="${param.planCourseId}">
        <input type="hidden" name="objId" value="${param.objId}">
			<div class="form-group">
				<label class="col-xs-3 control-label">学员姓名</label>
				<div class="col-xs-6 label-text">
                    ${sysUser.realname}
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>完成总学时数</label>
				<div class="col-xs-6">
                    <input required class="form-control period" type="text" name="period" value="${cetPlanCourseObj.period}">
				</div>
			</div>
		<c:if test="${_p_cetSupportCert}">
			<div class="form-group">
				<label class="col-xs-3 control-label">是否结业</label>
				<div class="col-xs-6">
                    <input type="checkbox" class="big" name="isFinished" ${(cetPlanCourseObj.isFinished)?"checked":""}/>
				</div>
			</div>
		</c:if>
			<div class="form-group">
				<label class="col-xs-3 control-label">提交学习心得数</label>
				<div class="col-xs-6">
                    <input class="form-control digits" type="text" name="num" value="${cetPlanCourseObj.num}">
				</div>
			</div>
            <c:forEach items="${cetCourseItemMap}" var="entity" varStatus="vs">
                <c:set var="cetPlanCourseObjResult" value="${resultMap.get(entity.key)}"/>
			<div class="form-group">
				<label class="col-xs-3 control-label">专题班${vs.count}完成课程</label>
				<div class="col-xs-6">
                    <input class="form-control digits" type="text" name="courseNum_${entity.key}" value="${cetPlanCourseObjResult.courseNum}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">专题班${vs.count}完成学时数</label>
				<div class="col-xs-6">
                    <input class="form-control period" type="text" name="period_${entity.key}" value="${cetPlanCourseObjResult.period}">
				</div>
			</div>
            </c:forEach>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> 确定</button>
</div>

<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
</script>