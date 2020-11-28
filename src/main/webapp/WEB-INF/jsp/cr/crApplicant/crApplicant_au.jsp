<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${crApplicant!=null?'编辑':'添加'}报名人员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crApplicant_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${crApplicant.id}">
        <input type="hidden" name="infoId" value="${infoId}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 报名人员</label>
				<div class="col-xs-8">
					<select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
							name="userId" data-width="273" data-placeholder="请输入账号或姓名或工作证号">
						<option value="${crApplicant.user.id}">${crApplicant.user.realname}-${crApplicant.user.code}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> ${crInfo.applyPostNum==1?"填报志愿":"第一志愿"}</label>
				<div class="col-xs-8">
					<select required name="firstPostId" data-width="273" data-placeholder="请选择" data-rel="select2">
						<option></option>
						<c:forEach items="${crPosts}" var="post">
							<option value="${post.id}">${post.name}</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=firstPostId]").val('${crApplicant.firstPostId}');
					</script>
				</div>
			</div>
		<c:if test="${crInfo.applyPostNum==2}">
			<div class="form-group">
				<label class="col-xs-3 control-label"> 第二志愿</label>
				<div class="col-xs-8">
					<select name="secondPostId" data-width="273" data-rel="select2" data-placeholder="请选择">
						<option></option>
						<c:forEach items="${crPosts}" var="post">
							<option value="${post.id}">${post.name}</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=secondPostId]").val('${crApplicant.secondPostId}');
					</script>
				</div>
			</div>
		</c:if>
			<div class="form-group">
				<label class="col-xs-3 control-label">近三年<br/>年度考核结果</label>
				<div class="col-xs-8">
					<c:set var="idx" value="0"/>
					<c:forEach begin="${crInfo.year-3}" end="${crInfo.year-1}" var="y">
					  <span style="margin-bottom: 5px;display: inline-block"> ${y}年：
						  <select ${empty cadre?"":"disabled"} data-rel="select2" data-width="100"
									name="eva" id="eva_${y}" data-placeholder="请选择">
								<option></option>
								<c:import url="/metaTypes?__code=mc_cadre_eva"/>
							</select>
						&nbsp;&nbsp;&nbsp;&nbsp;
						  </span>
						<script>
							$("#modalForm select[id=eva_${y}]").val('${evas.get(idx)}');
						</script>
						<c:set var="idx" value="${idx+1}"/>
					</c:forEach>
					<span class="help-block">注：如果是现任干部无需选择，将同步年度考核结果</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">竞聘理由</label>
				<div class="col-xs-8">
                        <textarea class="form-control limited"
								  maxlength="300" name="reason" rows="8">${crApplicant.reason}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty crApplicant?'确定':'添加'}</button>
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
                        $("#jqGrid2").trigger("reloadGrid");
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>