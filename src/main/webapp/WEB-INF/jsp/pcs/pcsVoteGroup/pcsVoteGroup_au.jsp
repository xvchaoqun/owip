<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${pcsVoteGroup!=null}">编辑</c:if><c:if test="${pcsVoteGroup==null}">添加</c:if>小组</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcsVoteGroup_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${pcsVoteGroup.id}">
        <input type="hidden" name="type" value="${param.type}">
        <div class="form-group">
            <label class="col-xs-4 control-label">小组名称</label>

            <div class="col-xs-6">
                <input required class="form-control" type="text" name="name" value="${pcsVoteGroup.name}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"> 小组负责人</label>

            <div class="col-xs-6">
                <input required class="form-control" type="text" name="leader" value="${pcsVoteGroup.leader}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">小组成员</label>

            <div class="col-xs-6">
                <input required class="form-control" type="text" name="member" value="${pcsVoteGroup.member}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">计票录入人员</label>

            <div class="col-xs-6">
                <c:set var="recordUser" value="${pcsVoteGroup.recordUser}"/>
                <select data-width="270" data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                        name="recordUserId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${recordUser.id}">${recordUser.realname}-${recordUser.code}</option>
                </select>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary"
           value="<c:if test="${pcsVoteGroup!=null}">确定</c:if><c:if test="${pcsVoteGroup==null}">添加</c:if>"/>
</div>

<script>
    $.register.user_select($('#modal [data-rel="select2-ajax"]'));
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                }
            });
        }
    });
</script>