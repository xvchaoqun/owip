<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="RETIRE_QUIT_TYPE_MAP" value="<%=SystemConstants.RETIRE_QUIT_TYPE_MAP%>"/>
<c:set var="MEMBER_STATUS_NORMAL" value="<%=SystemConstants.MEMBER_STATUS_NORMAL%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${memberQuit!=null}">编辑</c:if><c:if test="${memberQuit==null}">添加</c:if>党员出党</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberQuit_au" id="modalForm" method="post">
        <c:if test="${not empty memberQuit}">
        <input type="hidden" name="userId" value="${memberQuit.userId}">
        </c:if>
        <div class="form-group">
				<label class="col-xs-3 control-label">账号ID</label>
				<div class="col-xs-6">
                    <c:if test="${empty memberQuit}">
                    <select required  class="form-control" data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects?status=${MEMBER_STATUS_NORMAL}"
                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                        <option value="${sysUser.id}">${sysUser.realname}</option>
                    </select>
                    </c:if>
                    <c:if test="${not empty memberQuit}">
                        <input type="text" disabled value="${sysUser.realname}">
                     </c:if>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">类型</label>
				<div class="col-xs-6">
                        <select required class="form-control" data-rel="select2" name="type" data-placeholder="请选择">
                            <option></option>
                            <c:forEach items="${RETIRE_QUIT_TYPE_MAP}" var="quitType">
                                <option value="${quitType.key}">${quitType.value}</option>
                            </c:forEach>
                        </select>
                    <script>
                        $("#modalForm select[name=type]").val("${memberQuit.type}");
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">出党时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_quitTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberQuit.quitTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-3 control-label">备注</label>
                <div class="col-xs-6">
                    <textarea class="form-control limited" name="remark" rows="5">${memberQuit.remark}</textarea>
                </div>
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${memberQuit!=null}">确定</c:if><c:if test="${memberQuit==null}">添加</c:if>"/>
</div>

<script>

    $('.date-picker').datepicker({
        language:"zh-CN",
        autoclose: true,
        todayHighlight: true
    })
    $('textarea.limited').inputlimiter();

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
    $('#modalForm [data-rel="select2"]').select2();
    $('#modalForm select[name=userId]').select2({
        templateResult: formatState,
        ajax: {
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
                return {results: data.options,  pagination: {
                    more: (params.page * 10) < data.totalCount
                }};
            },
            cache: true
        }
    });

    $("#modal form").validate({
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
</script>