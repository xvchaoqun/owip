<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="MEMBER_POLITICAL_STATUS_MAP" value="<%=SystemConstants.MEMBER_POLITICAL_STATUS_MAP%>"/>
<c:set var="MEMBER_INOUT_TYPE_MAP" value="<%=SystemConstants.MEMBER_INOUT_TYPE_MAP%>"/>
<c:set var="GENDER_MAP" value="<%=SystemConstants.GENDER_MAP%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${memberIn!=null}">编辑</c:if><c:if test="${memberIn==null}">添加</c:if>组织关系转入</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberIn_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberIn.id}">
			<div class="row">
				<div class="col-xs-4">
					<div class="form-group">
						<label class="col-xs-5 control-label">用户</label>
						<div class="col-xs-6">
							<select required data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects"
									name="userId" data-placeholder="请输入账号或姓名或学工号">
								<option value="${sysUser.id}">${sysUser.realname}</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">姓名</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="realname" value="${memberIn.realname}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">性别</label>
						<div class="col-xs-6">
							<select required data-rel="select2" name="gender" data-placeholder="请选择" data-width="100">
								<option></option>
								<c:forEach items="${GENDER_MAP}" var="_gender">
									<option value="${_gender.key}">${_gender.value}</option>
								</c:forEach>
							</select>
							<script>
								$("#modalForm select[name=gender]").val(${memberIn.gender});
							</script>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">年龄</label>
						<div class="col-xs-6">
							<input required class="form-control digits" type="text" name="age" value="${memberIn.age}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">民族</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="nation" value="${memberIn.nation}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">政治面貌</label>
						<div class="col-xs-6">
							<select required data-rel="select2" name="politicalStatus" data-placeholder="请选择"  data-width="120">
								<option></option>
								<c:forEach items="${MEMBER_POLITICAL_STATUS_MAP}" var="_status">
									<option value="${_status.key}">${_status.value}</option>
								</c:forEach>
							</select>
							<script>
								$("#modalForm select[name=politicalStatus]").val(${memberIn.politicalStatus});
							</script>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">身份证号</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="idcard" value="${memberIn.idcard}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">类别</label>
						<div class="col-xs-6">
							<select required data-rel="select2" name="type" data-placeholder="请选择"  data-width="100">
								<option></option>
								<c:forEach items="${MEMBER_INOUT_TYPE_MAP}" var="_type">
									<option value="${_type.key}">${_type.value}</option>
								</c:forEach>
							</select>
							<script>
								$("#modalForm select[name=type]").val(${memberIn.type});
							</script>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">转入单位</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="toUnit" value="${memberIn.toUnit}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">分党委</label>
						<div class="col-xs-6">
							<select required class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
									name="partyId" data-placeholder="请选择">
								<option value="${party.id}">${party.name}</option>
							</select>
						</div>
					</div>
					<div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
						<label class="col-xs-5 control-label">党支部</label>
						<div class="col-xs-6">
							<select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects"
									name="branchId" data-placeholder="请选择">
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

				</div>
				<div class="col-xs-4">
					<div class="form-group">
						<label class="col-xs-5 control-label">转出单位</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="fromUnit" value="${memberIn.fromUnit}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">转出单位抬头</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="fromTitle" value="${memberIn.fromTitle}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">转出单位地址</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="fromAddress" value="${memberIn.fromAddress}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">转出单位联系电话</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="fromPhone" value="${memberIn.fromPhone}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">转出单位传真</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="fromFax" value="${memberIn.fromFax}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">转出单位邮编</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="fromPostCode" value="${memberIn.fromPostCode}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">党费缴纳至年月</label>
						<div class="col-xs-6">
							<div class="input-group">
								<input required class="form-control date-picker" name="_payTime" type="text"
									   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberIn.payTime,'yyyy-MM-dd')}" />
								<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">介绍信有效期天数</label>
						<div class="col-xs-6">
							<input required class="form-control digits" type="text" name="validDays" value="${memberIn.validDays}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">转出办理时间</label>
						<div class="col-xs-6">
							<div class="input-group">
								<input required class="form-control date-picker" name="_fromHandleTime" type="text"
									   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberIn.fromHandleTime,'yyyy-MM-dd')}" />
								<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">转入办理时间</label>
						<div class="col-xs-6">
							<div class="input-group">
								<input required class="form-control date-picker" name="_handleTime" type="text"
									   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberIn.handleTime,'yyyy-MM-dd')}" />
								<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
							</div>
						</div>
					</div>

				</div>
				<div class="col-xs-4">

					<div class="form-group">
						<label class="col-xs-5 control-label">提交书面申请书时间</label>
						<div class="col-xs-6">
							<div class="input-group">
								<input required class="form-control date-picker" name="_applyTime" type="text"
									   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberIn.applyTime,'yyyy-MM-dd')}" />
								<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">确定为入党积极分子时间</label>
						<div class="col-xs-6">
							<div class="input-group">
								<input required class="form-control date-picker" name="_activeTime" type="text"
									   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberIn.activeTime,'yyyy-MM-dd')}" />
								<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">确定为发展对象时间</label>
						<div class="col-xs-6">
							<div class="input-group">
								<input required class="form-control date-picker" name="_candidateTime" type="text"
									   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberIn.candidateTime,'yyyy-MM-dd')}" />
								<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">入党时间</label>
						<div class="col-xs-6">
							<div class="input-group">
								<input required class="form-control date-picker" name="_growTime" type="text"
									   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberIn.growTime,'yyyy-MM-dd')}" />
								<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">转正时间</label>
						<div class="col-xs-6">
							<div class="input-group">
								<input required class="form-control date-picker" name="_positiveTime" type="text"
									   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberIn.positiveTime,'yyyy-MM-dd')}" />
								<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">是否有回执</label>
						<div class="col-xs-6">
							<label>
								<input name="hasReceipt" ${memberIn.hasReceipt?"checked":""} class="ace ace-switch ace-switch-5" type="checkbox" />
								<span class="lbl"></span>
							</label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">返回修改原因</label>
						<div class="col-xs-6">
							<textarea required class="form-control limited" type="text" name="reason" rows="5">${memberIn.reason}</textarea>
						</div>
					</div>
				</div>
			</div>


    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${memberIn!=null}">确定</c:if><c:if test="${memberIn==null}">添加</c:if>"/>
</div>

<script>
	$('textarea.limited').inputlimiter();
	$('.date-picker').datepicker({
		language:"zh-CN",
		autoclose: true,
		todayHighlight: true
	})
    $("#modal form").validate({
        submitHandler: function (form) {
			if(!$("#branchDiv").is(":hidden")){
				if($('#modalForm select[name=branchId]').val()=='') {
					toastr.warning("请选择支部。", "提示");
					return;
				}
			}
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