<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="MEMBER_POLITICAL_STATUS_MAP" value="<%=SystemConstants.MEMBER_POLITICAL_STATUS_MAP%>"/>
<c:set var="MEMBER_TYPE_MAP" value="<%=SystemConstants.MEMBER_TYPE_MAP%>"/>
<c:set var="MEMBER_STATUS_MAP" value="<%=SystemConstants.MEMBER_STATUS_MAP%>"/>
    <h3>${op}党员</h3>
	<hr/>
    <form class="form-horizontal" action="${ctx}/member_au" id="modalForm" method="post">
        <input type="hidden" name="userId" value="${member.userId}">
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="col-xs-3 control-label">账号</label>
					<div class="col-xs-6">
						<select ${not empty sysUser?"disabled data-theme='default'":""} required data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects"
								name="userId" data-placeholder="请输入账号或姓名或学工号">
							<option value="${sysUser.id}">${sysUser.realname}</option>
						</select>
						<script>
							//$("#modalForm select[name=userId]").prop("disabled", true);
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">所属分党委</label>
					<div class="col-xs-6">
						<select required class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
								name="partyId" data-placeholder="请选择" data-width="320">
							<option value="${party.id}">${party.name}</option>
						</select>
					</div>
				</div>
				<div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
					<label class="col-xs-3 control-label">所属党支部</label>
					<div class="col-xs-6">
						<select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects"
								name="branchId" data-placeholder="请选择" data-width="320">
							<option value="${branch.id}">${branch.name}</option>
						</select>
					</div>
				</div>
				<script>
					var $container = $("#modalForm");
					$('select[name=partyId], select[name=branchId]', $container).select2({
						ajax: {
							dataType: 'json',
							delay: 300,
							data: function (params) {
								return {
									searchStr: params.term,
									pageSize: 10,
									pageNo: params.page,
									partyId: $('select[name=partyId]', $container).val()
								};
							},
							processResults: function (data, params) {
								params.page = params.page || 1;
								return {results: data.options,  pagination: {
									more: (params.page * 10) < data.totalCount
								}};
							},
							cache: true
						}
					});
					$('select[name=partyId]', $container).on("change", function () {

						var $party_class = $(this).select2("data")[0].class || "${party.classId}";
						//alert($party_class)
						if($(this).val()>0 && $party_class !='${cm:getMetaTypeByCode("mt_direct_branch").id}'){
							$("#branchDiv", $container).show();
						}else{
							$('select[name=branchId]', $container).val(null).trigger("change");
							$("#branchDiv", $container).hide();
						}
					}).change();
				</script>

				<div class="form-group">
					<label class="col-xs-3 control-label">政治面貌</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="politicalStatus" data-placeholder="请选择"  data-width="120">
							<option></option>
							<c:forEach items="${MEMBER_POLITICAL_STATUS_MAP}" var="_status">
								<option value="${_status.key}">${_status.value}</option>
							</c:forEach>
						</select>
						<script>
							$("#modalForm select[name=politicalStatus]").val(${member.politicalStatus});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">类别</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="type" data-placeholder="请选择"  data-width="120">
							<option></option>
							<c:forEach items="${MEMBER_TYPE_MAP}" var="_type">
								<option value="${_type.key}">${_type.value}</option>
							</c:forEach>
						</select>
						<script>
							$("#modalForm select[name=type]").val(${member.type});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">状态</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="status" data-placeholder="请选择"  data-width="120">
							<option></option>
							<c:forEach items="${MEMBER_STATUS_MAP}" var="_status">
								<option value="${_status.key}">${_status.value}</option>
							</c:forEach>
						</select>
						<script>
							$("#modalForm select[name=status]").val(${member.status});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">组织关系转入时间</label>
					<div class="col-xs-6">
						<div class="input-group">
							<input class="form-control date-picker" name="_transferTime" type="text"
								   data-date-format="yyyy-mm-dd" value="${cm:formatDate(member.transferTime,'yyyy-MM-dd')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">提交书面申请书时间</label>
					<div class="col-xs-6">
						<div class="input-group">
							<input required class="form-control date-picker" name="_applyTime" type="text"
								   data-date-format="yyyy-mm-dd" value="${cm:formatDate(member.applyTime,'yyyy-MM-dd')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">确定为入党积极分子时间</label>
					<div class="col-xs-6">
						<div class="input-group">
							<input required class="form-control date-picker" name="_activeTime" type="text"
								   data-date-format="yyyy-mm-dd" value="${cm:formatDate(member.activeTime,'yyyy-MM-dd')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">确定为发展对象时间</label>
					<div class="col-xs-6">
						<div class="input-group">
							<input required class="form-control date-picker" name="_candidateTime" type="text"
								   data-date-format="yyyy-mm-dd" value="${cm:formatDate(member.candidateTime,'yyyy-MM-dd')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">入党时间</label>
					<div class="col-xs-6">
						<div class="input-group">
							<input required class="form-control date-picker" name="_growTime" type="text"
								   data-date-format="yyyy-mm-dd" value="${cm:formatDate(member.growTime,'yyyy-MM-dd')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">转正时间</label>
					<div class="col-xs-6">
						<div class="input-group">
							<input class="form-control date-picker" name="_positiveTime" type="text"
								   data-date-format="yyyy-mm-dd" value="${cm:formatDate(member.positiveTime,'yyyy-MM-dd')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>

			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="col-xs-3 control-label">党内职务</label>
					<div class="col-xs-6">
						<textarea class="form-control limited" type="text"
								  name="partyPost" rows="6">${member.partyPost}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">党内奖励</label>
					<div class="col-xs-6">
						<textarea class="form-control limited" type="text"
								  name="partyReward" rows="6">${member.partyReward}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">其他奖励</label>
					<div class="col-xs-6">
						<textarea  class="form-control limited" type="text"
								   name="otherReward" rows="6">${member.otherReward}</textarea>
					</div>
				</div>

			</div>
		</div>
    </form>
<div class="clearfix form-actions">
	<div class="col-md-offset-3 col-md-9">
		<button class="btn btn-info" type="submit">
			<i class="ace-icon fa fa-check bigger-110"></i>
			提交
		</button>

		&nbsp; &nbsp; &nbsp;
		<button class="closeView btn" type="button">
			<i class="ace-icon fa fa-undo bigger-110"></i>
			取消
		</button>
	</div>
</div>

<script>
	$('textarea.limited').inputlimiter();
	$('.date-picker').datepicker({
		language:"zh-CN",
		autoclose: true,
		todayHighlight: true
	})
	$("#item-content button[type=submit]").click(function(){$("#modalForm").submit(); return false;});
	$("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
	function formatState (state) {

		if (!state.id) { return state.text; }
		var $state = state.text;
		if(state.code!=undefined && state.code.length>0)
			$state += '-' + state.code;
		if(state.unit!=undefined && state.unit.length>0){
			$state += '-' + state.unit;
		}
		//console.log($state)
		return $state;
	};

	$('#modalForm select[name=userId]').select2({
		templateResult: formatState,
        ajax: {
            dataType: 'json',
            delay: 200,
            data: function (params) {
                return {
                    searchStr: params.term,
                    pageSize: 10,
                    pageNo: params.page
                };
            },
            processResults: function (data, params) {
                params.page = params.page || 1;
                return {results: data.options,  pagination: {
                    more: (params.page * 10) < data.totalCount
                }};
            },
            cache: true
        }
    });
</script>