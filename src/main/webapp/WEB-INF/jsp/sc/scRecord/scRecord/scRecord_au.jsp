<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${scRecord!=null?'编辑':'添加'}纪实</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scRecord_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scRecord.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">年份</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    <input required style="width: 80px;" autocomplete="off" class="form-control date-picker" placeholder="请选择年份"
                           name="year" type="text"
                           data-date-format="yyyy" data-date-min-view-mode="2" value="${empty scRecord.year?_thisYear:scRecord.year}"/>
                </div>
            </div>
        </div>

			<div class="form-group">
				<label class="col-xs-3 control-label">调整岗位名称</label>
				<div class="col-xs-6">
                    <select data-rel="select2" name="motionId" data-width="273" data-placeholder="请选择动议">
                        <option></option>
                        <c:forEach var="scMotion" items="${scMotions}">
                            <option value="${scMotion.id}">${scMotion.code}</option>
                        </c:forEach>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=motionId]").val(${scRecord.motionId});
                    </script>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control" name="remark">${scRecord.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scRecord!=null}">确定</c:if><c:if test="${scRecord==null}">添加</c:if></button>
</div>
<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>