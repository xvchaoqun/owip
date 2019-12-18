<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="widget-box transparent">
	<div class="widget-header">
		<h4 class="widget-title lighter smaller">
			<a href="javascript:;" class="hideView btn btn-xs btn-success">
				<i class="ace-icon fa fa-backward"></i>
				返回</a>
		</h4>
	</div>
	<div class="widget-body">
		<div class="widget-main" style="width: 900px">
			<form class="form-horizontal" action="${ctx}/cadrePositionReport_au" id="modalForm" method="post">
				<table class="table table-bordered table-unhover">
					<input class="form-control" type="hidden" name="id"
						   value="${param.id}">
					<tr>
						<td><span class="star">*</span>年度</td>
						<td colspan="3">
							<c:if test="${!edit}">
								${cadrePositionReport.year}
							</c:if>
							<c:if test="${edit}">
									<div class="input-group" style="width: 300px">
										<input required ${admin==0?"disabled":""} class="date-picker"
											   name="year" type="text"
											   data-date-start-view="2"
											   data-date-min-view-mode="2"
											   data-date-max-view-mode="2"
											   data-date-format="yyyy"
											   style="width: 300px"
											   value="${cadrePositionReport.year}"/>
										</span>
									</div>
							</c:if>
						</td>
					</tr>
					<tr>
						<td width="100"><span class="star">*</span>姓名</td>
						<td>
							<c:if test="${!edit}">
								${cadrePositionReport.cadre.user.realname}
							</c:if>
							<c:if test="${edit}">
								<select ${admin==0?"disabled":""} required data-rel="select2-ajax"
										data-ajax-url="${ctx}/cadre_selects?types=${CADRE_STATUS_MIDDLE},${CADRE_STATUS_MIDDLE_LEAVE}"
										name="cadreId" data-width="300" data-placeholder="请输入账号或姓名或学工号">
									<option value="${cadrePositionReport.cadreId}">${cadrePositionReport.cadre.user.realname}-${cadrePositionReport.cadre.user.code}</option>
								</select>
							</c:if>
						</td>
						<td><span class="star">*</span>职工号</td>
						<td>
							<c:if test="${!edit}">
								${cadrePositionReport.cadre.user.code}
							</c:if>
							<c:if test="${edit}">
								<input required disabled class="form-control" type="text" name="code" value="${cadrePositionReport.cadre.user.code}">
							</c:if>
						</td>
					</tr>

					<tr>
						<td><span class="star">*</span>所在单位及职务</td>
						<td colspan="3">
							<c:if test="${!edit}">
								${cadrePositionReport.cadre.title}
							</c:if>
							<c:if test="${edit}">
								<input required disabled class="form-control" type="text" name="title" value="${cadrePositionReport.cadre.title}">
							</c:if>
						</td>

					</tr>

					<tr>
						<td style="height: 500px"><span class="star">*</span>个人述职</td>
						<td colspan="3">
							<c:if test="${!edit}">
								<p style="text-indent:2em">${cadrePositionReport.content}</p>
							</c:if>
							<c:if test="${edit}">
                            <textarea  required name="content" rows="30" class="limited"
									   style="width: 100%">${cadrePositionReport.content}</textarea>
							</c:if>
						</td>
					</tr>
					<c:if test="${edit}">
						<tr>
							<td colspan="4">

								<div class="modal-footer center">

									<button id="submitBtn"
											class="btn btn-success btn-xlg"><i
											class="fa fa-check"></i> 确定
									</button>
								</div>

							</td>
						</tr>
					</c:if>
				</table>
			</form>
		</div>
	</div>
</div>
<style>
	.table.table-unhover > tbody > tr > td:nth-of-type(odd) {
		text-align: center;
		font-size: 18px;
		font-weight: bolder;
		vertical-align: middle;
	}

	textarea {
		text-indent: 32px;
		line-height: 25px;
		/*font-family: "Arial";*/
		font-size: 16px;
		padding: 2px;
		margin: 2px;
		border: none;
		background: #FFFFFF url(/img/dot_25.gif);
		resize: none;
	}
</style>
<script>
	$('#modalForm select[name="cadreId"]').on('change',function(){
		var cadreId=$('#modalForm select[name="cadreId"]').val();
		if ($.isBlank(cadreId)){
			return;
		}
		var data = $('#modalForm select[name="cadreId"]').select2("data")[0];
		$("#modalForm input[name=code]").val(data.code);
		$("#modalForm input[name=title]").val(data.title);
	});
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
			var $btn = $("#submitBtn").button('loading');
			$('#modalForm input[name="year"]').removeAttr("disabled");
			$('#modalForm select[name="cadreId"]').removeAttr("disabled");
            $(form).ajaxSubmit({
                success:function(ret){
					if(ret.success){
						$("#modal").modal('hide');
						SysMsg.success("保存成功。", function () {
							$.hideView();
							$("#jqGrid").trigger("reloadGrid");
						});
					}
					$('#modalForm input[name="year"]').attr("disabled",true);
					$('#modalForm select[name="cadreId"]').attr("disabled",true);
					$btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
	$('textarea.limited').inputlimiter({
		limit: 1500,
		remText: '当前剩余%n字，',
		limitText: '最多输入%n字.'
	});
</script>