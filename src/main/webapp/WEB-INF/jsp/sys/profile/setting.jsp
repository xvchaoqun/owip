<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
  <div class="col-xs-12">
<div id="user-profile-3" class="user-profile row">
  <div class="col-sm-offset-1 col-sm-10">

    <div class="space"></div>

    <form class="form-horizontal">
      <div class="tabbable">
        <ul class="nav nav-tabs padding-16">
          <li>
            <a href="${ctx}/profile" >
              <i class="green ace-icon fa fa-pencil-square-o bigger-125"></i>
              基本信息
            </a>
          </li>

          <li class="active">
            <a href="${ctx}/setting" >
              <i class="purple ace-icon fa fa-cog bigger-125"></i>
              个人设置
            </a>
          </li>
          <shiro:hasPermission name="password:modify">
            <li>
              <a href="${ctx}/password" >
                <i class="blue ace-icon fa fa-key bigger-125"></i>
                密码修改
              </a>
            </li>
          </shiro:hasPermission>
        </ul>

        <div class="tab-content profile-edit-tab-content">

          <div>
            <div class="space-10"></div>
            <div>
              <label class="inline">
                <input type="checkbox" name="form-field-checkbox" class="ace" />
                <span class="lbl"> 信息公开</span>
              </label>
            </div>

            <div class="space-8"></div>

            <div>
              <label class="inline">
                <input type="checkbox" name="form-field-checkbox" class="ace" />
                <span class="lbl"> 邮件通知</span>
              </label>
            </div>
          </div>
        </div>
      </div>

      <div class="clearfix form-actions">
        <div class="col-md-offset-3 col-md-9">
          <button class="btn btn-info" type="button">
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

</script>