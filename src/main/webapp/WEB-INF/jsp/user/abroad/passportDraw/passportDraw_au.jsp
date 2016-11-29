<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${passportDraw!=null}">编辑</c:if><c:if test="${passportDraw==null}">添加</c:if>领取证件</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/user/passportDraw_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${passportDraw.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">申请人</label>
				<div class="col-xs-6">
					<select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
							name="cadreId" data-placeholder="请选择干部">
						<option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">类型</label>
				<div class="col-xs-6">
					<select name="type" data-rel="select2" data-placeholder="请选择类型">
						<option></option>
						<c:forEach items="${PASSPORT_DRAW_TYPE_MAP}" var="type">
							<option value="${type.key}">${type.value}</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=type]").val('${passportDraw.type}');
					</script>
				</div>
			</div>

		<%--	<div class="form-group">
				<label class="col-xs-3 control-label">证件</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="passportId" value="${passportDraw.passportId}">
				</div>
			</div>--%>
			<div class="form-group">
				<label class="col-xs-3 control-label">申请日期</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_applyDate" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(passportDraw.applyDate,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">出发时间</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_startDate" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(passportDraw.startDate,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">返回时间</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_endDate" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(passportDraw.endDate,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">出国事由</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="reason" value="${passportDraw.reason}">
				</div>
			</div>


			<div class="form-group">
				<label class="col-xs-3 control-label">应交组织部日期</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_expectDate" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(passportDraw.expectDate,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">实交组织部日期</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_handleDate" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(passportDraw.handleDate,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${passportDraw.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${passportDraw!=null}">确定</c:if><c:if test="${passportDraw==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
	$('.date-picker').datepicker({
		language:"zh-CN",
		autoclose: true,
		todayHighlight: true
	})
	register_user_select($('[data-rel="select2-ajax"]'));
</script>