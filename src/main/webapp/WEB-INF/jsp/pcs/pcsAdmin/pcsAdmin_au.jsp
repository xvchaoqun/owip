<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${empty pcsAdmin?"添加":"修改"}分党委管理员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/${empty param.partyId?"pcsAdmin_au":"pcsPartyAdmin_au"}" id="modalForm" method="post">
        <input type="hidden" name="id" value="${pcsAdmin.id}">

        <c:if test="${empty param.partyId}">
            <div class="form-group">
                <label class="col-xs-3 control-label">所属分党委</label>
                <div class="col-xs-6">
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects?del=0"
                            name="partyId" data-placeholder="请选择">
                        <option value="${party.id}">${party.name}</option>
                    </select>
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-3 control-label">用户</label>
            <div class="col-xs-6">
                <select required data-rel="select2-ajax"
                        data-ajax-url="${ctx}/member_selects?noAuth=1&partyId=${param.partyId}&status=${MEMBER_STATUS_NORMAL}"
                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">手机号码</label>

            <div class="col-xs-8" >
                <input required class="form-control" style="width: 200px" type="text" name="mobile" value="${sysUser.mobile}">
                <span class="help-block">* 该手机号码将用于各类通知，请确保真实有效</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>

            <div class="col-xs-6">
                <textarea style="width: 200px" rows="6" class="form-control limited" type="text"
                          name="remark" maxlength="200">${pcsAdmin.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        <c:if test="${empty param.partyId}">
                        // 系统管理员
                        $("#jqGrid").trigger("reloadGrid");
                        </c:if>
                        <c:if test="${not empty param.partyId}">
                        // 分党委管理员
                        _reload();
                        </c:if>
                    }
                }
            });
        }
    });
    $.register.del_select($('#modalForm select[name=partyId]'));
    $.register.user_select($('#modalForm select[name=userId]')).on("change",function(){
        //console.log($(this).select2("data")[0])
        var mobile = $(this).select2("data")[0]['user'].mobile||'';
        $('#modalForm input[name=mobile]').val(mobile);
    });

    $('textarea.limited').inputlimiter();
</script>