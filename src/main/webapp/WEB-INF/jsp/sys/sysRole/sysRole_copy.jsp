<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=SystemConstants.SYS_ROLE_TYPE_ADD%>" var="SYS_ROLE_TYPE_ADD"/>
<c:set value="<%=SystemConstants.SYS_ROLE_TYPE_MINUS%>" var="SYS_ROLE_TYPE_MINUS"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>复制角色</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sysRole_copy" autocomplete="off" disableautocomplete id="modalForm"
          method="post">
        <input type="hidden" name="id" value="${id}">
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>代码</label>

            <div class="col-xs-6">
                <input required class="form-control" ${(!cm:isSuperAccount(_user.username) && sysRole.code eq ROLE_ADMIN)?'disabled':''}
                       type="text" name="code" value="">
            </div>
        </div>
        <div class="form-group">
            <label required class="col-xs-3 control-label"><span class="star">*</span>名称</label>

            <div class="col-xs-6">
                <input class="form-control" type="text" name="name" value="">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>类别</label>
            <div class="col-xs-6 label-text">
                <input required name="type" type="radio" class="ace" value="${SYS_ROLE_TYPE_ADD}" ${type!=SYS_ROLE_TYPE_MINUS?'checked':''}/>
                      <span class="lbl" style="padding-right: 5px;"> 加权限</span>
                <input required name="type" type="radio" class="ace" value="${SYS_ROLE_TYPE_MINUS}" ${type==SYS_ROLE_TYPE_MINUS?'checked':''}/>
                      <span class="lbl" style="padding-right: 5px;"> 减权限</span>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary"
           value="<c:if test="${sysUser!=null}">确认</c:if><c:if test="${sysUser==null}">添加</c:if>"/>
</div>

<script>
    $("#modal input[type=submit]").click(function () {
        $("#modal form").submit();
        return false;
    });
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        if (ret.error) {
                            $.tip({$form: $(form), field: ret.field, msg: ret.msg})
                            return;
                        }

                        $("#modal").modal('hide');
                        //SysMsg.success('操作成功。', '成功',function(){
                        $("#jqGrid").trigger("reloadGrid");
                        //});
                    }
                }
            });
        }
    });
</script>