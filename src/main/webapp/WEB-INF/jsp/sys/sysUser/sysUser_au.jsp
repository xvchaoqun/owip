<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${sysUser!=null}">修改</c:if><c:if test="${sysUser==null}">添加</c:if>账号</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sysUser_au" autocomplete="off" disableautocomplete id="modalForm"
          method="post">
        <input type="hidden" name="id" value="${sysUser.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>用户名</label>
            <div class="col-xs-6">
                <c:if test="${not empty sysUser}">
                    <div class="label-text">${sysUser.username}</div>
                </c:if>
                <c:if test="${empty sysUser}">
                    <input required class="form-control" type="text" name="username" value="${sysUser.username}">
                    <span class="help-block">由5~15数字和小写字母组成</span>
                </c:if>
            </div>
        </div>
        <c:if test="${sysUser==null || sysUser.source==USER_SOURCE_ADMIN||sysUser.source==USER_SOURCE_REG}">
            <div class="form-group">
                <label class="col-xs-3 control-label">${empty sysUser?'<span class="star">*</span>':''}密码</label>
                <div class="col-xs-6">
                    <input ${empty sysUser?'required':''} class="form-control" type="text" name="passwd">
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>类别</label>
            <div class="col-xs-6 label-text">
                <div class="input-group">
                    <c:forEach var="userType" items="${USER_TYPE_MAP}">
                        <label>
                            <input required name="type" type="radio" class="ace" value="${userType.key}"
                                    <c:if test="${sysUser.type==userType.key}">checked</c:if>/>
                            <span class="lbl" style="padding-right: 5px;"> ${userType.value}</span>
                        </label>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>学工号</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="code" value="${sysUser.code}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>姓名</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="realname" value="${sysUser.realname}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">登录超时(单位分钟)</label>
            <div class="col-xs-6">
                <input class="form-control digits" type="text" name="timeout" value="${sysUser.timeout}">
                <span class="help-block">* 留空则使用系统默认的时间（${not empty _sysConfig.loginTimeout?_sysConfig.loginTimeout:cm:stripTrailingZeros(_global_session_timeout/(60*1000))}分钟）</span>
            </div>
        </div>
        <c:if test="${not empty sysUser}">
        <div class="form-group">
            <label class="col-xs-3 control-label">账号来源</label>
            <div class="col-xs-6">
                <select data-rel="select2" name="source" data-width="272" data-placeholder="请选择">
                    <option></option>
                    <c:forEach items="<%=SystemConstants.USER_SOURCE_MAP%>" var="source">
                        <option value="${source.key}">${source.value}</option>
                    </c:forEach>
                </select>
                <span class="help-block">注：如果是校园账号，系统会自动定时同步覆盖此字段</span>
                <script>
                    $("#modalForm select[name=source]").val('${sysUser.source}');
                </script>
            </div>
        </div>
        </c:if>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary"
           value="<c:if test="${sysUser!=null}">确认</c:if><c:if test="${sysUser==null}">添加</c:if>"/>
</div>

<script>
    jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
    jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();

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
                        <c:if test="${not empty param.applyId}">
                        page_reload();
                        </c:if>
                        //});
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
</script>