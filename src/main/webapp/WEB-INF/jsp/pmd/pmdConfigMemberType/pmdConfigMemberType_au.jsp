<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/pmd/constants.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${pmdConfigMemberType!=null}">编辑</c:if><c:if test="${pmdConfigMemberType==null}">添加</c:if>党员计费类别</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmd/pmdConfigMemberType_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${pmdConfigMemberType.id}">

        <div class="form-group" id="formulaDiv">
            <label class="col-xs-3 control-label">类别</label>
                <div class="col-xs-6">
                    <select data-rel="select2" name="type"
                            data-width="270"
                            data-placeholder="请选择">
                        <option></option>
                        <c:forEach items="${PMD_MEMBER_TYPE_MAP}" var="_type">
                            <option value="${_type.key}">${_type.value}</option>
                        </c:forEach>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=type]").val(${pmdConfigMemberType.type});
                    </script>
                </div>
        </div>

			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>党员分类别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${pmdConfigMemberType.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">党费计算方法</label>
				<div class="col-xs-6">
                    <select data-rel="select2" name="normId"
                            data-width="270"
                            data-placeholder="请选择">
                        <option></option>
                        <c:forEach items="${pmdNorms}" var="norm">
                            <option value="${norm.id}">${norm.name}</option>
                        </c:forEach>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=normId]").val(${pmdConfigMemberType.normId});
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否系统默认设置</label>
				<div class="col-xs-6">
                    <input name="isAuto" ${pmdConfigMemberType.isAuto?"checked":""}  type="checkbox" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control" name="remark" rows="5">${pmdConfigMemberType.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${pmdConfigMemberType!=null}">确定</c:if><c:if test="${pmdConfigMemberType==null}">添加</c:if></button>
</div>

<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
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
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>