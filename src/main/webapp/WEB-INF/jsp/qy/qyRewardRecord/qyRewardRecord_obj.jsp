<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="<%=QyConstants.QY_REWARD_MAP%>" var="QY_REWARD_MAP"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>设置表彰记录</h3>
</div>
<div class="modal-body" style="overflow:auto">
	<div class="widget-box">
		<div class="widget-header">
			<h4 class="widget-title">
				<c:if test="${param.type==1}">
					添加获表彰院系级党委
				</c:if>
				<c:if test="${param.type==2}">
					添加获表彰党支部
				</c:if>
				<c:if test="${param.type==3}">
					添加获表彰党员
				</c:if>
				<c:if test="${param.type==4}">
			     	添加获表彰党日活动
			    </c:if>
			</h4>
		</div>
		<div class="widget-body">
			<div class="widget-main">
				<form class="form-horizontal no-footer" action="${ctx}/qyRewardObj_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
					<input type="hidden" name="recordId" value="${param.recordId}">
					<div class="form-group">
						<label class="col-xs-3 control-label"><span class="star">*</span>${param.type==1?"  获表彰院系级党委":"  所属党委"}</label>
						<div class="col-xs-6">
							<select required class="form-control"  data-rel="select2-ajax"
									data-ajax-url="${ctx}/party_selects?auth=1"
									data-width="270"
									name="partyId" data-placeholder="请选择">
								<option value="${party.id}">${party.name}</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label"><span class="star">*</span>${param.type==1?"  获表彰院系级党委名称":"  所属党委名称"}</label>
						<div class="col-xs-6">
							<input required class="form-control search-query" name="partyName" type="text"
								   <%--value="${qyRewardObjView.partyName}"--%>
								   placeholder="请输入院系级党委名称">
						</div>
					</div>
					<c:if test="${param.type==2||param.type==4}">
						<div class="form-group" id="branchIdDiv">
							<label class="col-xs-3 control-label"><span class="star">*</span>${param.type==2?"  获表彰党支部":"  所属党支部"}</label>
							<div class="col-xs-6">
								<select disabled required class="form-control"  data-rel="select2-ajax"
										data-ajax-url="${ctx}/branch_selects?auth=1"
										name="branchId" data-placeholder="请选择" data-width="272">
									<option value="${branch.id}">${branch.name}</option>
								</select>
							</div>
						</div>

						<div class="form-group" id="branchNameDiv">
							<label class="col-xs-3 control-label"><span class="star">*</span>${param.type==2?"  获表彰党支部名称":"  所属党支部名称"}</label>
							<div class="col-xs-6">
								<input required class="form-control search-query" name="branchName" type="text"
									   <%--value="${qyRewardObjView.branchName}"--%>
									   placeholder="请输入党支部名称">
							</div>
						</div>
					</c:if>
					<c:if test="${param.type==3}">
						<div class="form-group">
							<label class="col-xs-3 control-label"><span class="star">*</span> 获表彰党员</label>
							<div class="col-xs-6">
								<select disabled required data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects"
										data-width="270" name="userId" data-placeholder="请选择">
									<option></option>
								</select>
							</div>
						</div>
					</c:if>
					<c:if test="${param.type==4}">
						<div class="form-group">
							<label class="col-xs-3 control-label"><span class="star">*</span> 获表彰党日活动</label>
							<div class="col-xs-6">
								<input required class="form-control search-query" name="meetingName" type="text"
									  <%-- value="${qyRewardObjView.partyName}"--%>
									   placeholder="请输入党日活动名称">
							</div>
						</div>
					</c:if>

					<div class="form-group">
						<label class="col-xs-3 control-label"> 备注</label>
						<div class="col-xs-6">
							<textarea class="form-control" type="text" name="remark"></textarea>
						</div>
					</div>
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info btn-sm" type="submit">
								<i class="ace-icon fa fa-check "></i>
								确定
							</button>
							&nbsp; &nbsp; &nbsp;
							<button class="btn btn-default btn-sm" type="reset">
								<i class="ace-icon fa fa-undo"></i>
								重置
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
<div class="space-10"></div>
<div class="popTableDiv"
	 data-url-page="${ctx}/qyRewardRecord_obj?recordId=${param.recordId}&type=${param.type}"
	 data-url-del="${ctx}/qyRewardObj_del"
	 data-url-co="${ctx}/qyRewardObj_changeOrder">
	<c:if test="${commonList.recNum>0}">
		<table class="table table-actived table-striped table-bordered table-hover">
			<thead>
			<tr>
				<th>年度</th>
                <th>奖项</th>
                <c:if test="${param.type==4}">
				  <th>获表彰党日活动</th>
					<th>所属党支部</th>
                </c:if>
                <c:if test="${param.type==3}">
                  <th>获表彰党员</th>
                </c:if>
                <th width="300">${param.type==1?"获表彰院系级党委":"所属党委"}</th>
                <c:if test="${param.type==1}">
                  <th>党委编号</th>
                </c:if>
				<c:if test="${param.type==2}">
					<th>获表彰党支部</th>
                  <th >党支部编号</th>
                </c:if>
				<c:if test="${commonList.recNum>1}">
					<th style="width: 50px">排序</th>
				</c:if>
				<th nowrap></th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${qyRewardObjView}" var="qyRewardObjView" varStatus="st">
				<tr>
                    <td nowrap>${qyRewardObjView.year}</td>
                    <td nowrap>${qyRewardObjView.rewardName}</td>
                    <c:if test="${param.type==4}">
                        <td nowrap>${qyRewardObjView.meetingName}</td>
						<td nowrap>${qyRewardObjView.branchName}</td>
                    </c:if>
                    <c:if test="${param.type==3}">
                        <td nowrap>${qyRewardObjView.user.realname}</td>
                    </c:if>
                    <td nowrap>${qyRewardObjView.partyName}</td>
                    <c:if test="${param.type==1}">
                        <td nowrap>${qyRewardObjView.party.code}</td>
                    </c:if>
                    <c:if test="${param.type==2}">
						<td nowrap>${qyRewardObjView.branchName}</td>
                        <td nowrap>${qyRewardObjView.branch.code}</td>
                    </c:if>
					<c:if test="${commonList.recNum>1}">
						<td nowrap>
							<a href="javascript:;"
							   <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if>
							   class="changeOrderBtn" data-id="${qyRewardObjView.id}" data-direction="1" title="上升"><i
									class="fa fa-arrow-up"></i></a>
							<input type="text" value="1"
								   class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
								   title="修改操作步长">
							<a href="javascript:;"
							   <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if>
							   class="changeOrderBtn" data-id="${qyRewardObjView.id}" data-direction="-1"
							   title="下降"><i class="fa fa-arrow-down"></i></a></td>
						</td>
					</c:if>
					<td nowrap>
						<div class="hidden-sm hidden-xs action-buttons">
							<button class="delBtn btn btn-danger btn-xs" data-id="${qyRewardObjView.id}">
								<i class="fa fa-trash"></i> 删除
							</button>
						</div>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<c:if test="${!empty commonList && commonList.pageNum>1 }">
			<wo:page commonList="${commonList}" uri="${ctx}/qyRewardRecord_obj?recordId=${param.recordId}&type=${param.type}" target="#modal .modal-content"
					 pageNum="5"
					 model="3"/>
		</c:if>
	</c:if>
	<c:if test="${commonList.recNum==0}">
		<div class="well well-lg center">
			<h4 class="green lighter">暂无记录</h4>
		</div>
	</c:if>
</div>
</div>
<script>

	$('#modalForm select[name="partyId"]').on('change',function(){
		var partyId=$('#modalForm select[name="partyId"]').val();
		if ($.isBlank(partyId)){
			$('#modalForm input[name="partyName"]').val("");
			$("#modalForm select[name=userId]").attr("disabled",true);
		   return;
		}
			var data=$('#modalForm select[name="partyId"]').select2("data")[0];
			$('#modalForm input[name="partyName"]').val(data.text);
            $("#modalForm select[name=userId]").data('ajax-url', "${ctx}/member_selects?partyId="+partyId);
		    $("#modalForm select[name=userId]").removeAttr("disabled");
		    $.register.user_select($("#modalForm select[name=userId]"));



		if(data.class==${cm:getMetaTypeByCode("mt_direct_branch").id}){
			$("#modalForm select[name=branchId]").removeAttr("required");
			$("#modalForm input[name=branchName]").removeAttr("required");
			$("#branchIdDiv").hide();
			$("#branchNameDiv").hide();
		}else{
			$("#modalForm select[name=branchId]").data('ajax-url', "${ctx}/branch_selects?auth=1&partyId="+partyId);
			$("#modalForm select[name=branchId]").removeAttr("disabled");
			$.register.ajax_select($('[data-rel="select2-ajax"]'));
		}

	});
	$('#modalForm select[name="branchId"]').on('change',function(){
		var branchId=$('#modalForm select[name="branchId"]').val();
		if ($.isBlank(branchId)){
			$('#modalForm input[name="branchName"]').val("");
			return;
		}
		var data=$('#modalForm select[name="branchId"]').select2("data")[0];
		$('#modalForm input[name="branchName"]').val(data.text);
	});
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
						pop_reload();
                    }

                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($("#modalForm select[name=userId]"));
    $.register.ajax_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>