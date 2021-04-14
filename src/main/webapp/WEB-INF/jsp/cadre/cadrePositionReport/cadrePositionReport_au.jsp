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
					<input class="form-control" type="hidden" name="id" value="${param.id}">
					<input class="form-control" type="hidden" name="type" value="${type}">
					<tr>
						<td width="20%"><span class="star">*</span>年度</td>
						<td colspan="3">
							<c:if test="${!edit}">
								${cadrePositionReport.year}
							</c:if>
							<c:if test="${edit}">
								<c:if test="${admin==0}">
									<span>${cadrePositionReport.year}</span>
									<input hidden type="text" name="year" value="${cadrePositionReport.year}">
								</c:if>
								<c:if test="${admin==1}">
								   <input required class="date-picker"
									   name="year" type="text"
									   data-date-start-view="2"
									   data-date-min-view-mode="2"
									   data-date-max-view-mode="2"
									   data-date-format="yyyy"
									   style="width: 100px"
									   value="${cadrePositionReport.year}"/>
								</c:if>
							</c:if>
						</td>
					</tr>
					<tr>
						<td><span class="star">*</span>姓名</td>
						<td style="width: 300px;">
							<c:if test="${!edit}">
								${cadrePositionReport.cadre.user.realname}
							</c:if>
							<c:if test="${edit}">
								<c:if test="${admin==0}">
									<span>${cadrePositionReport.cadre.user.realname}</span>
									<input hidden type="text" name="cadreId" value="${cadrePositionReport.cadreId}">
								</c:if>
								<c:if test="${admin==1}">
									<select required data-rel="select2-ajax"
											data-ajax-url="${ctx}/cadre_selects?types=${CADRE_STATUS_CJ},${CADRE_STATUS_CJ_LEAVE}"
											name="cadreId" data-width="300" data-placeholder="请输入账号或姓名或学工号">
										<option value="${cadrePositionReport.cadreId}">${cadrePositionReport.cadre.user.realname}-${cadrePositionReport.cadre.user.code}</option>
									</select>
								</c:if>
							</c:if>
						</td>
						<td width="20%"><span class="star">*</span>职工号</td>
						<td>
							<c:if test="${!edit}">
								${cadrePositionReport.cadre.user.code}
							</c:if>
							<c:if test="${edit}">
								<span id="code">${cadrePositionReport.cadre.user.code}</span>
							</c:if>
						</td>
					</tr>

					<tr>
						<td width="20%"><span class="star">*</span>所在单位及职务</td>
						<td colspan="3">
							<c:if test="${!edit}">
								${cadrePositionReport.title}
							</c:if>
							<c:if test="${edit}">
								<c:if test="${admin!=1}">
									<span>${cadrePositionReport.title==null?cadrePositionReport.cadre.title:cadrePositionReport.title}</span>
									<input hidden type="text" name="title" value="${cadrePositionReport.title==null?cadrePositionReport.cadre.title:cadrePositionReport.title}">
								</c:if>
								<c:if test="${admin==1}">
									<input required class="form-control" type="text" name="title" style="width: 300px"value="${cadrePositionReport.title}">
								</c:if>
							</c:if>
						</td>

					</tr>

					<tr>
						<td style="height: 500px"><span class="star">*</span>
							个人述职<br/>（${type==2 ?2000:1500}字以内）
						</td>
						<td colspan="3">
							<c:if test="${!edit}">
								<pre style="text-indent:2em;white-space: pre-wrap!important;word-wrap: break-word!important;*white-space:normal!important;">${cadrePositionReport.content}</pre>
							</c:if>
							<c:if test="${edit}">
                            <textarea  required name="content" rows="20" class="limited canEnter"
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
			$("#code").text(null);
			$("#modalForm input[name=title]").val("");
			return;
		}
		var data = $('#modalForm select[name="cadreId"]').select2("data")[0];
        //console.log(data);
		$("#code").text(data.code);
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
						//SysMsg.success("保存成功。", function () {
							$.hideView();
							$("#jqGrid").trigger("reloadGrid");
					//	});
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
	/*$('textarea.limited').inputlimiter({
		limit: ${limitNum},
		remText: '当前剩余%n字，',
		limitText: '最多输入%n字.'
	});*/
</script>