<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${branchMember!=null}">编辑</c:if><c:if test="${branchMember==null}">添加</c:if>基层党组织成员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/branchMember_au" id="modalForm" method="post">
        <input type="hidden" name="groupId" value="${param.groupId}">
        <input type="hidden" name="id" value="${branchMember.id}">
        <c:set var="sysUser" value="${cm:getUserById(branchMember.userId)}"/>
        <div class="form-group">
            <label class="col-xs-3 control-label">账号</label>
            <div class="col-xs-6">
                <select required data-rel="select2-ajax" data-width="272" data-ajax-url="${ctx}/sysUser_selects"
                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.username}</option>
                </select></div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">选择类别</label>
            <div class="col-xs-6">
                <select required data-rel="select2" data-width="272" name="typeId" data-placeholder="请选择类别">
                    <option></option>
                    <jsp:include page="/metaTypes?__code=mc_branch_member_type"/>
                </select>
                <script>
                    $("#modal select[name=typeId]").val('${branchMember.typeId}');
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">任职时间</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input class="form-control date-picker" name="assignDate" type="text"
                           data-date-min-view-mode="1"
                           data-date-format="yyyy.mm" value="${cm:formatDate(branchMember.assignDate,'yyyy.MM')}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">办公电话</label>

            <div class="col-xs-6">
                <input class="form-control" type="text" name="officePhone" value="${branchMember.officePhone}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">手机号</label>

            <div class="col-xs-6">
                <input class="form-control mobile" type="text" name="mobile" value="${branchMember.mobile}">
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${branchMember!=null}">确定</c:if><c:if test="${branchMember==null}">添加</c:if>"/>
</div>

<script>
    $.register.date($('.date-picker'));
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide")
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $.register.user_select($('#modalForm select[name=userId]'));
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>