<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="CADRE_RESERVE_ORIGIN_WAY_MAP" value="<%=CadreConstants.CADRE_RESERVE_ORIGIN_WAY_MAP%>"/>

<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreReserveOrigin!=null}">编辑</c:if><c:if test="${cadreReserveOrigin==null}">添加</c:if></h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreReserveOrigin_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreReserveOrigin.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">产生方式</label>
				<div class="col-xs-6">
					<select required data-rel="select2" name="way" data-width="272" data-placeholder="请选择">
						<option></option>
						<c:forEach items="${CADRE_RESERVE_ORIGIN_WAY_MAP}" var="entity">
							<option value="${entity.key}">${entity.value}</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=way]").val(${cadreReserveOrigin.way});
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">推荐人选</label>
				<c:if test="${not empty cadreReserveOrigin}">
					<div class="col-xs-6 label-text">
							${cadreReserveOrigin.cadre.realname}
					</div>
				</c:if>
				<c:if test="${empty cadreReserveOrigin}">
				<div class="col-xs-6">
					<select required data-rel="select2-ajax" data-width="272" data-ajax-url="${ctx}/cadre_selects?key=1&type=0"
							name="userId" data-placeholder="请输入账号或姓名或学工号">
						<option></option>
					</select>
				</div>
				</c:if>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">类别</label>
				<div class="col-xs-6">
					<select required data-rel="select2" name="reserveType" data-width="272" data-placeholder="请选择">
						<option></option>
						<c:forEach items="${cm:getMetaTypes('mc_cadre_reserve_type')}" var="entity">
							<option value="${entity.key}">${entity.value.name}</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=reserveType]").val(${cadreReserveOrigin.reserveType});
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">推荐单位</label>
				<div class="col-xs-6">
                    <input required class="form-control" type="text" name="recommendUnit" value="${cadreReserveOrigin.recommendUnit}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">推荐日期</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input class="form-control date-picker" name="recommendDate" type="text"
							   data-date-format="yyyy-mm-dd"
							   value="${empty cadreReserveOrigin?_today:cm:formatDate(cadreReserveOrigin.recommendDate,'yyyy-MM-dd')}"/>
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">推荐材料(PDF)</label>
				<div class="col-xs-6">
					<input class="form-control" type="file" name="_pdfFilePath"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">推荐材料(WORD)</label>
				<div class="col-xs-6">
					<input class="form-control" type="file" name="_wordFilePath"/>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">考察材料</label>
				<div class="col-xs-6">
					<select data-width="272" name="objId" data-placeholder="请选择">
						<option value="${cisInspectObj.id}">${cisInspectObj.sn}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
					<textarea class="form-control limited"
							  name="remark">${cadreReserveOrigin.remark}</textarea>
				</div>
			</div>

    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cadreReserveOrigin!=null}">确定</c:if><c:if test="${cadreReserveOrigin==null}">添加</c:if></button>
</div>
<script>

	$.fileInput($("#modalForm input[name=_pdfFilePath]"),{
		allowExt: ['pdf'],
		allowMime: ['application/pdf']
	});
	$.fileInput($("#modalForm input[name=_wordFilePath]"),{
		allowExt: ['doc', 'docx'],
		allowMime: ['application/msword','application/vnd.openxmlformats-officedocument.wordprocessingml.document']
	});

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
	var cadreId = '${cadreReserveOrigin.cadre.id}';
	var selectUser = $.register.user_select($('select[name=userId]'));
	selectUser.on("change", function(){
		cadreId = $(this).select2("data")[0]['cadreId'] || '';
	});
	$.register.ajax_select($('select[name=objId]'), {
		ajax: {
			url:function(){return "${ctx}/cadreReserveOrigin_selectInspectObj?cadreId=" + cadreId},
			dataType: 'json',
			delay: 300,
			data: function (params) {
				return {
					searchStr: params.term,
					pageSize: 10,
					pageNo: params.page
				};
			},
			processResults: function (data, params) {
				params.page = params.page || 1;
				return {
					results: data.options, pagination: {
						more: (params.page * 10) < data.totalCount
					}
				};
			},
			cache: true
		},
		templateResult:function(state){
			if(state.sn==undefined) return state.text
			return state.sn + "（" + state.date + "）";
		},
		templateSelection:function(state){
			if(state.sn==undefined) return state.text
				return state.sn;
		}
	});

    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>