<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${qyYearReward!=null?'编辑':'添加'}七一表彰年度奖项</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/qyYearReward_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${qyYearReward.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 年度</label>
				<div class="col-xs-6">
					${qyYear.year!=null?qyYear.year:qyYearReward.qyYear.year}
					<input class="form-control" type="hidden" name="yearId" value="${qyYear.id}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 奖项</label>
				<div class="col-xs-6">
					<select required data-rel="select2-ajax" data-ajax-url="${ctx}/qyReward_selects"
							data-width="223" name="rewardId" data-placeholder="请选择奖项">
						<option value="${qyYearReward.id}">${qyYearReward.qyReward.name}</option>
					</select>
					<script>
						$.register.ajax_select($("#modalForm select[name=rewardId]"))
					</script>
                      <%--  <input required class="form-control" type="text" name="rewardId" value="${qyYearReward.rewardId}">--%>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label"> 备注</label>
				<div class="col-xs-6">
					<textarea  class="form-control" type="text" name="remark" >${qyYearReward.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty qyYearReward?'确定':'添加'}</button>
</div>
<script>

	<%--$('#modalForm select[name="yearId"]').on('change',function(){--%>

		<%--var yearId=$('#modalForm select[name="yearId"]').val();--%>
		<%--console.log(yearId);--%>
		<%--if ($.isBlank(yearId)){--%>
			<%--return;--%>
		<%--}--%>
		<%--console.log(yearId);--%>
		<%--$('#modalForm select[name="rewardId"]').data('ajax-url', "${ctx}/qyYearReward_selects?yearId="+yearId);--%>
		<%--$.register.ajax_select($("#modalForm select[name=rewardId]"))--%>
	<%--});--%>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    $.register.ajax_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>