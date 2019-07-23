<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scMotion!=null}">编辑</c:if><c:if test="${scMotion==null}">添加</c:if>动议</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scMotion_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${scMotion.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>年份</label>
				<div class="col-xs-6">
					<div class="input-group">
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						<input required style="width: 80px;" autocomplete="off" class="form-control date-picker" placeholder="请选择年份"
							   name="year" type="text"
							   data-date-format="yyyy" data-date-min-view-mode="2" value="${empty scMotion.year?_thisYear:scMotion.year}"/>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>动议日期</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" autocomplete="off" name="holdDate" type="text"
							   data-date-format="yyyy-mm-dd" value="${empty scMotion.holdDate?_today:(cm:formatDate(scMotion.holdDate,'yyyy-MM-dd'))}"/>
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">拟调整岗位</label>
				<div class="col-xs-6">
					<select name="unitPostId" data-rel="select2-ajax" data-ajax-url="${ctx}/unitPost_selects"
							data-placeholder="请选择">
						<option value="${unitPost.id}" delete="${unitPost.status==UNIT_POST_STATUS_DELETE}">${unitPost.code}-${unitPost.name}</option>
					</select>
					<script>
						$.register.del_select($("#modalForm select[name=unitPostId]"), {
							width:273,
							templateResult: function (state) {
								var txt = state.text;
								if(state.code) txt = state.code+"-"+txt;
								return '<span class="{0}">{1}</span>'.format(state.del || $(state.element).attr('delete') == 'true' ? "delete" : "", txt);
							},
							templateSelection: function (state) {
								var txt = state.text;
								if(state.code) txt = state.code+"-"+txt;
								return '<span class="{0}">{1}</span>'.format(state.del || $(state.element).attr('delete') == 'true' ? "delete" : "", txt);
							}
						})
					</script>
				</div>
			</div>
			<%--<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>动议编号</label>
				<div class="col-xs-6">
                        <input required class="form-control num" type="text" name="num" value="${scMotion.num}">
				</div>
			</div>--%>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>选拔任用方式</label>
				<div class="col-xs-6">
					<select required data-rel="select2" data-width="273"
							name="scType" data-placeholder="请选择">
						<option></option>
						<jsp:include page="/metaTypes?__code=mc_sc_motion_sctype"/>
					</select>
					<script type="text/javascript">
						$("#modalForm select[name=scType]").val(${scMotion.scType});
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>动议形式</label>
				<div class="col-xs-6">
					<select required data-rel="select2" name="way"
							data-placeholder="请选择" data-width="273">
						<option></option>
						<c:forEach items="<%=ScConstants.SC_MOTION_WAY_MAP%>" var="way">
							<option value="${way.key}">${way.value}</option>
						</c:forEach>
					</select>
					<script type="text/javascript">
						$("#modalForm select[name=way]").val(${scMotion.way});
					</script>
				</div>
			</div>
			<div class="form-group" id="wayOtherDiv">
				<label class="col-xs-3 control-label"><span class="star">*</span>其他动议形式</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="wayOther" value="${scMotion.wayOther}">
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
					<textarea class="form-control" name="remark">${scMotion.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer"><a href="#" data-dismiss="modal" class="btn btn-default">取消</a>

    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scMotion!=null}">确定</c:if><c:if test="${scMotion==null}">添加</c:if></button>
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
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));

	function wayChange(){

		if($("#modalForm select[name=way]").val() == '<%=ScConstants.SC_MOTION_WAY_OTHER%>'){
			$("#wayOtherDiv").show();
			$("#modalForm input[name=wayOther]").attr("required", "required");
		}else{
			$("#wayOtherDiv").hide();
			$("#modalForm input[name=wayOther]").removeAttr("required");
		}
	}
	$("#modalForm select[name=way]").change(function(){
		wayChange();
	});
	wayChange();
</script>