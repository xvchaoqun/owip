<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${sysUser!=null}">修改</c:if><c:if test="${sysUser==null}">添加</c:if>账号</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sysUser_au" id="modalForm" method="post">
    	<input type="hidden" name="id" value="${sysUser.id}">
    	<div class="form-group">
          <label class="col-xs-3 control-label">用户名</label>
          <div class="col-xs-6">
			<c:if test="${not empty sysUser}">
			  <div class="label-text">${sysUser.username}</div>
			</c:if>
			  <c:if test="${empty sysUser}">
			  <input class="form-control" type="text" name="username" value="${sysUser.username}">
			  <span class="help-block">由5~15数字和小写字母组成</span>
			  </c:if>
          </div>
        </div>
        <c:if test="${sysUser!=null}">
        <div class="form-group">
          <label class="col-xs-3 control-label">密码</label>
          <div class="col-xs-6">
			   <input class="form-control" autocomplete="off" type="password" name="passwd" >
          </div>
        </div>
        </c:if>
		<div class="form-group">
			<label class="col-xs-3 control-label">类别</label>
			<div class="col-xs-9">
				<div class="radio">
					<c:forEach var="userType" items="${userTypeMap}">
					<label>
						<input name="type" type="radio" class="ace" value="${userType.key}"
							   <c:if test="${sysUser.type==userType.key}">checked</c:if>/>
						<span class="lbl"> ${userType.value}</span>
					</label>
					</c:forEach>
				</div>
			</div>
		</div>
    	<div class="form-group">
          <label class="col-xs-3 control-label">学工号</label>
          <div class="col-xs-6">
			   <input required class="form-control" type="text" name="code" value="${sysUser.code}">
          </div>
        </div>
    	<div class="form-group">
          <label class="col-xs-3 control-label">真实姓名</label>
          <div class="col-xs-6">
			  <input required class="form-control" type="text" name="realname" value="${sysUser.realname}">
          </div>
        </div>
		<div class="form-group">
			<label class="col-xs-3 control-label">身份证</label>
			<div class="col-xs-6">
				<input class="form-control" type="text" name="idcard" value="${sysUser.idcard}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">手机</label>
			<div class="col-xs-6">
				<input class="form-control" type="text" name="mobile" value="${sysUser.mobile}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">邮箱</label>
			<div class="col-xs-6">
				<input class="form-control" type="text" name="email" value="${sysUser.email}">
			</div>
		</div>
        </form>
  </div>
  <div class="modal-footer">
  <c:if test="${sysUser==null}"><p class="pull-left text-error">注：初始化密码与账号相同</p></c:if>
  <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
  <input type="submit" class="btn btn-primary" value="<c:if test="${sysUser!=null}">确认</c:if><c:if test="${sysUser==null}">添加</c:if>"/>
  </div>

  <script>
	  jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
	  jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();

		$("#modal input[type=submit]").click(function(){$("#modal form").submit();return false;});
		$("#modal form").validate({
				rules: {
					username:{
						required:true
					},
					type:{
						required:true
					}
				},
				submitHandler: function (form) {

					/*var $passwd = $("input[name=passwd]");
					$passwd.val(hex_md5($passwd.val()));*/

					$(form).ajaxSubmit({
						success:function(data){
							if(data.success){
								$("#modal").modal('hide');
								SysMsg.success('操作成功。', '成功',function(){
									$("#jqGrid").trigger("reloadGrid");
									<c:if test="${not empty param.applyId}">
									page_reload()
									</c:if>
								});
							}
						}
					});
				}
			});
</script>