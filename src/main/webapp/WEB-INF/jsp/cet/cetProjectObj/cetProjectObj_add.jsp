<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="OW_APPLY_STAGE_ACTIVE" value="<%=OwConstants.OW_APPLY_STAGE_ACTIVE%>"/>
<c:set var="OW_APPLY_STAGE_CANDIDATE" value="<%=OwConstants.OW_APPLY_STAGE_CANDIDATE%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h4>添加培训对象</h4>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetProjectObj_add" autocomplete="off" disableautocomplete
          id="modalForm" method="post">
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span> 参训人员类型</label>
            <div class="col-xs-7">
                <select required data-rel="select2" name="traineeTypeId" data-placeholder="请选择"  data-width="272">
                    <option></option>
                    <c:forEach items="${cetTraineeTypes}" var="cetTraineeType">
                        <option value="${cetTraineeType.id}">${cetTraineeType.name}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#modalForm select[name=traineeTypeId]").val('${param.traineeTypeId}')
                </script>
            </div>
        </div>
        <div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 选择参训人员</label>
			<div class="col-xs-7">
				 <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects" data-width="272"
                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option></option>
                </select>
			</div>
		</div>
        <div class="form-group">
            <label class="col-xs-3 control-label">时任单位及职务</label>
            <div class="col-xs-6">
                <textarea class="form-control noEnter" rows="3" name="title"></textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">时任职务属性</label>
            <div class="col-xs-7">
                <select  data-rel="select2" name="postType" data-placeholder="请选择时任职务属性" data-width="272">
                    <option></option>
                    <jsp:include page="/metaTypes?__code=mc_post"/>
                </select>
            </div>
        </div>
        <c:if test="${cm:getMetaTypes('mc_cet_identity').size()>0}">
            <div class="form-group owAuType">
                <label class="col-xs-3 control-label"> 参训人身份</label>
                <div class="col-xs-7">
                    <div class="input-group">
                        <c:forEach items="${cm:getMetaTypes('mc_cet_identity')}" var="entity">
                            <div class="checkbox checkbox-inline checkbox-sm">
                                <input type="checkbox" name="identities[]" id="identity${entity.key}" value="${entity.key}">
                                <label for="identity${entity.key}">${entity.value.name}</label>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </c:if>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">确定
    </button>
</div>

<script>
    $.register.user_select($('#modalForm [data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({

        submitHandler: function (form) {

            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                data: {projectId: "${param.projectId}"},
                success: function (data) {
                    if (data.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>