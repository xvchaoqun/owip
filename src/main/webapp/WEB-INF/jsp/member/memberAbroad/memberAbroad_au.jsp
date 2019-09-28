<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${memberAbroad!=null}">编辑</c:if><c:if test="${memberAbroad==null}">添加</c:if>党员出国境信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberAbroad_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberAbroad.id}">
        <c:if test="${not empty memberAbroad}">
            <input type="hidden" name="userId" value="${memberAbroad.userId}">
        </c:if>
			<div class="form-group">
				<label class="col-xs-3 control-label"><c:if test="${empty memberAbroad}"><span class="star">*</span></c:if>党员</label>
				<div class="col-xs-6">
                    <c:if test="${empty memberAbroad}">
                        <select required  class="form-control" data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects?status=${MEMBER_STATUS_NORMAL}"
                                name="userId" data-placeholder="请输入账号或姓名或学工号">
                            <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                        </select>
                    </c:if>
                    <c:if test="${not empty memberAbroad}">
                        <input type="text" disabled value="${sysUser.realname}">
                    </c:if>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>出国时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_abroadTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberAbroad.abroadTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>出国缘由</label>
				<div class="col-xs-6">
                        <textarea required class="form-control" type="text" name="reason">${memberAbroad.reason}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>预计归国时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_expectReturnTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberAbroad.expectReturnTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>实际归国时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_actualReturnTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberAbroad.actualReturnTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${memberAbroad!=null}">确定</c:if><c:if test="${memberAbroad==null}">添加</c:if>"/>
</div>

<script>
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        //SysMsg.success('提交成功。', '成功',function(){
                            $("#jqGrid").trigger("reloadGrid");
                        //});
                    }
                }
            });
        }
    });
    $.register.date($('.date-picker'));
    $.register.user_select($('#modalForm select[name=userId]'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>