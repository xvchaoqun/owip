<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
  <div class="col-xs-12">
<div id="user-profile-3" class="user-profile row">
  <div class="col-sm-offset-1 col-sm-10">

    <div class="space"></div>

    <form class="form-horizontal" id="form" action="${ctx}/password"  method="post">
      <div class="tabbable">
        <jsp:include page="menu.jsp"/>

        <div class="tab-content profile-edit-tab-content">
          <div>
            <div class="space-10"></div>

              <div class="form-group">
                  <label class="col-sm-3 control-label no-padding-right">原密码</label>
                  <div class="col-sm-9">
                    <input required type="password" name="oldPassword">
                  </div>
              </div>

            <div class="form-group">
              <label class="col-sm-3 control-label no-padding-right">新密码</label>
              <div class="col-sm-9">
                <input required type="password"  name="password" id="password">
              </div>
            </div>

            <div class="space-4"></div>

            <div class="form-group">
              <label class="col-sm-3 control-label no-padding-right">新密码确认</label>

              <div class="col-sm-9">
                <input required type="password" name="repassword">
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="clearfix form-actions">
        <div class="col-md-offset-3 col-md-9">
          <button class="btn btn-info" type="submit">
            <i class="ace-icon fa fa-check bigger-110"></i>
            保存
          </button>

          &nbsp; &nbsp;
          <button class="btn" type="reset">
            <i class="ace-icon fa fa-undo bigger-110"></i>
            重置
          </button>
        </div>
      </div>
    </form>
  </div><!-- /.span -->
</div>
  </div>
</div>
<script>
  $("#form button[type=submit]").click(function(){$("#form").submit();return false;});
  $("#form").validate({
    rules: {
      repassword:{
        equalTo:'#password'
      }
    },
    submitHandler: function (form) {

      $(form).ajaxSubmit({
        success:function(data){
          if(data.success){
            SysMsg.success('修改密码成功，请重新登录。', '成功', function(){
                location.href ="${ctx}/logout"
            });
          }
        }
      });
    }
  });
</script>