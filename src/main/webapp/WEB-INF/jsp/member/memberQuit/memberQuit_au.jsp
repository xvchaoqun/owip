<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="MEMBER_QUIT_STATUS_APPLY" value="<%=MemberConstants.MEMBER_QUIT_STATUS_APPLY%>"/>

<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${memberQuit!=null}">编辑</c:if><c:if test="${memberQuit==null}">添加</c:if>减员信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberQuit_au" id="modalForm" method="post">
        <c:if test="${not empty memberQuit}">
        <input type="hidden" name="userId" value="${memberQuit.userId}">
        <input type="hidden" name="resubmit">
        </c:if>
        <div class="form-group">
				<label class="col-xs-3 control-label"><c:if test="${empty memberQuit}"><span class="star">*</span></c:if>账号</label>
				<div class="col-xs-6">
                    <c:if test="${empty memberQuit}">
                    <select required  class="form-control" data-rel="select2-ajax"
                            data-ajax-url="${ctx}/member_selects?status=${MEMBER_STATUS_NORMAL}"
                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
                    </c:if>
                    <c:if test="${not empty memberQuit}">
                        <input type="text" disabled value="${sysUser.realname}">
                     </c:if>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>减员原因</label>
				<div class="col-xs-6">
                        <select required class="form-control" data-rel="select2" name="type" data-placeholder="请选择">
                            <option></option>
                            <c:forEach items="<%=MemberConstants.MEMBER_QUIT_TYPE_MAP%>" var="quitType">
                                <option value="${quitType.key}">${quitType.value}</option>
                            </c:forEach>
                        </select>
                    <script>
                        $("#modalForm select[name=type]").val("${memberQuit.type}");
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>减员时间</label>
				<div class="col-xs-6">
                    <div class="input-group" style="width: 200px">
                        <input required class="form-control date-picker" name="_quitTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberQuit.quitTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
            <%--<div class="form-group">
                <label class="col-xs-3 control-label">返回修改原因</label>
                <div class="col-xs-6">
                    <textarea class="form-control limited" name="remark" rows="5">${memberQuit.remark}</textarea>
                </div>
            </div>--%>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${memberQuit!=null}">确定</c:if><c:if test="${memberQuit==null}">添加</c:if>"/>
    <c:if test="${memberQuit!=null && memberQuit.status<MEMBER_QUIT_STATUS_APPLY}">
    <input type="button" id="resubmit" class="btn btn-warning" value="修改并重新提交"/>
    </c:if>
</div>

<script>
    jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
    jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();
    $.register.date($('.date-picker'));
    $('textarea.limited').inputlimiter();

    $.register.user_select($('#modalForm select[name=userId]'));
    $('#modalForm [data-rel="select2"]').select2();

    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        page_reload()
                    }
                }
            });
        }
    });
    $("#resubmit").click(function(){
        $("input[name=resubmit]", "#modal form").val("1");
        $("#modal form").submit();
        return false;
    });
</script>