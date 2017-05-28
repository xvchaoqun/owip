<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>编辑干部职数配置情况</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cpcAllocation_au" id="modalForm" method="post">
        <input type="hidden" name="unitId" value="${unit.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">单位</label>
				<div class="col-xs-6 label-text">
                        ${unit.name}
				</div>
			</div>
        <c:forEach items="${adminLevels}" var="adminLevel">
            <c:set value="adminLevel_${adminLevel.id}" var="key"></c:set>
            <div class="form-group">
                <label class="col-xs-3 control-label">${adminLevel.name}</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="${key}" value="${cpcAdminLevelMap[adminLevel.id]}">
                </div>
            </div>
        </c:forEach>

    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="更新"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        _reload();
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>