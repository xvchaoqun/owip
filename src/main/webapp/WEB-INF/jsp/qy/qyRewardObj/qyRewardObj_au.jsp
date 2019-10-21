<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${qyRewardObj!=null?'编辑':'添加'}获表彰<c:if test="${param.type==1}">院系级党委</c:if>
		<c:if test="${param.type==2}">党支部</c:if>
        <c:if test="${param.type==3}">党员</c:if>
        <c:if test="${param.type==4}">党日活动</c:if></h3>
</div>
<div class="modal-body">
	  <form class="form-horizontal" action="${ctx}/qyRewardObj_au?type=${param.type}" autocomplete="off" disableautocomplete id="modalForm" method="post">
		  <input type="hidden" name="recordId" value="${param.recordId}">
		<%--  <div class="form-group">
			  <label class="col-xs-3 control-label"><span class="star">*</span>选择${_p_partyName}</label>
			  <div class="col-xs-6">
				  <select required class="form-control"  data-rel="select2-ajax"
						  data-ajax-url="${ctx}/party_selects?del=0&notDirect=1"
						  name="partyId" data-placeholder="请选择" data-width="320">
					  <option value="${party.id}">${party.name}</option>
				  </select>
			  </div>
		  </div>
		  <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
			  <label class="col-xs-3 control-label">选择党支部</label>
			  <div class="col-xs-6">
				  <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects?del=0"
						  name="branchId" data-placeholder="请选择" data-width="320">
					  <option value="${branch.id}">${branch.name}</option>
				  </select>
			  </div>
		  </div>
		  <script>
			  $.register.party_branch_select($("#modalForm"), "branchDiv",
					  '${cm:getMetaTypeByCode("mt_direct_branch").id}',
					  "${party.id}", "${party.classId}", "partyId", "branchId", true);
		  </script>--%>
		  <div class="form-group">
			  <label class="col-xs-4 control-label"><span class="star">*</span>${param.type==1?"  获表彰院系级党委":"  所属党委"}</label>
			  <div class="col-xs-6">
				  <select required class="form-control"  data-rel="select2-ajax"
						  data-ajax-url="${ctx}/party_selects?auth=1"
						  data-width="270"
						  name="partyId" data-placeholder="请选择">
					  <option value="${qyRewardObjView.partyId}">${qyRewardObjView.partyName}</option>
				  </select>
			  </div>
		  </div>
		 <%-- <div class="form-group">
			  <label class="col-xs-4 control-label"><span class="star">*</span>${param.type==1?"  获表彰院系级党委名称":"  所属党委名称"}</label>
			  <div class="col-xs-6">
				  <input required class="form-control search-query" name="partyName" type="text"
				  &lt;%&ndash;value="${qyRewardObjView.partyName}"&ndash;%&gt;
						 placeholder="请输入院系级党委名称">
			  </div>
		  </div>--%>
		  <c:if test="${param.type==2||param.type==4}">
			  <div class="form-group" id="branchDiv">
				  <label class="col-xs-4 control-label"><span class="star">*</span>${param.type==2?"  获表彰党支部":"  所属党支部"}</label>
				  <div class="col-xs-6">
					  <select  required class="form-control"  data-rel="select2-ajax"
							  data-ajax-url="${ctx}/branch_selects?auth=1"
							  name="branchId" data-placeholder="请选择" data-width="272">
						  <option value="${qyRewardObjView.branchId}">${qyRewardObjView.branchName}</option>
					  </select>
				  </div>
			  </div>
		  </c:if>
		  <script>
			  $.register.party_branch_select($("#modalForm"), "branchDiv",
					  '${cm:getMetaTypeByCode("mt_direct_branch").id}',
					  "${qyRewardObjView.partyId}", "${qyRewardObjView.party.classId}","partyId","branchId",true);
		  </script>
			  <%--<div class="form-group">
				  <label class="col-xs-3 control-label"><span class="star">*</span>${param.type==2?"  获表彰党支部名称":"  所属党支部名称"}</label>
				  <div class="col-xs-6">
					  <input required class="form-control search-query" name="branchName" type="text"
						  &lt;%&ndash;value="${qyRewardObjView.branchName}"&ndash;%&gt;
							 placeholder="请输入党支部名称">
				  </div>
			  </div>--%>


		  <c:if test="${param.type==3}">
			  <div class="form-group">
				  <label class="col-xs-4 control-label"><span class="star">*</span> 获表彰党员</label>
				  <div class="col-xs-6">
					  <select disabled required data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects"
							  data-width="270" name="userId" data-placeholder="请选择">
						  <option value="${qyRewardObjView.userId}">${qyRewardObjView.user.realname}</option>
					  </select>
				  </div>
			  </div>
		  </c:if>
		  <c:if test="${param.type==4}">
			  <div class="form-group">
				  <label class="col-xs-4 control-label"><span class="star">*</span> 获表彰党日活动</label>
				  <div class="col-xs-6">
					  <input required class="form-control search-query" name="meetingName" type="text"
						   value="${qyRewardObjView.MeetingName}"
							 placeholder="请输入党日活动名称">
				  </div>
			  </div>
		  </c:if>

		<%--  <div class="form-group">
			  <label class="col-xs-4 control-label"> 备注</label>
			  <div class="col-xs-6">
				  <textarea class="form-control" type="text" name="remark">${qyRewardObjView.remark}</textarea>
			  </div>
		  </div>--%>
	  </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty qyRewardObj?'确定':'添加'}</button>
</div>
<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                    //    $("#modal").modal('hide');
						$.tip({$target:$("#submitBtn"), msg:"保存成功", type:'success'})
						$("#jqGrid2").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });

	$('#modalForm select[name="partyId"]').on('change',function(){
		var partyId=$('#modalForm select[name="partyId"]').val();
		if ($.isBlank(partyId)){
			// $('#modalForm input[name="partyName"]').val("");
			$("#modalForm select[name=userId]").attr("disabled",true);
			return;
		}
		//var data=$('#modalForm select[name="partyId"]').select2("data")[0];
		//$('#modalForm input[name="partyName"]').val(data.text);
		$("#modalForm select[name=userId]").data('ajax-url', "${ctx}/member_selects?partyId="+partyId);
		$("#modalForm select[name=userId]").removeAttr("disabled");
		$.register.user_select($("#modalForm select[name=userId]"));

	});

	//$("#modalForm :checkbox").bootstrapSwitch();
	$.register.user_select($("#modalForm select[name=userId]"));
	$('#modalForm [data-rel="select2"]').select2();
	$('[data-rel="tooltip"]').tooltip();
	$('textarea.limited').inputlimiter();
	//$.register.date($('.date-picker'));
</script>