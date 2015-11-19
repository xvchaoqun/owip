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
          <li  class="active">
            <a href="${ctx}/profile">
              <i class="green ace-icon fa fa-pencil-square-o bigger-125"></i>
              基本信息
            </a>
          </li>

          <li>
            <a href="${ctx}/setting">
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
          <div id="edit-basic" class="tab-pane in active">
            <h4 class="header blue bolder smaller">基本信息</h4>

            <div class="row">
              <div class="col-xs-12 col-sm-4">
                <input type="file" />
              </div>
              <div class="vspace-12-sm"></div>

              <div class="col-xs-12 col-sm-8">
                <div class="form-group">
                  <label class="col-sm-4 control-label no-padding-right" >账号</label>

                  <div class="col-sm-8">
                      <input readonly="" type="text" id="form-input-readonly" value="<shiro:principal property="username"/>">
                  </div>
                </div>

                <div class="space-4"></div>

                <div class="form-group">
                  <label class="col-sm-4 control-label no-padding-right" >姓名</label>

                  <div class="col-sm-8">
                    <input type="text"  />
                  </div>
                </div>
              </div>
            </div>

            <hr />
            <div class="form-group">
              <label class="col-sm-3 control-label no-padding-right">年龄</label>

              <div class="col-sm-9">
                <div class="input-medium">
                  <div class="input-group">
                    <input class="input-medium date-picker" id="form-field-date" type="text" data-date-format="yyyy-mm-dd" placeholder="yyyy-mm-dd" />
																			<span class="input-group-addon">
																				<i class="ace-icon fa fa-calendar"></i>
																			</span>
                  </div>
                </div>
              </div>
            </div>

            <div class="space-4"></div>

            <div class="form-group">
              <label class="col-sm-3 control-label no-padding-right">性别</label>

              <div class="col-sm-9">
                <label class="inline">
                  <input name="form-field-radio" type="radio" class="ace" />
                  <span class="lbl middle"> 男</span>
                </label>

                &nbsp; &nbsp; &nbsp;
                <label class="inline">
                  <input name="form-field-radio" type="radio" class="ace" />
                  <span class="lbl middle"> 女</span>
                </label>
              </div>
            </div>

            <div class="space"></div>
            <h4 class="header blue bolder smaller">联系方式</h4>

            <div class="form-group">
              <label class="col-sm-3 control-label no-padding-right" for="form-field-email">邮箱</label>

              <div class="col-sm-9">
                        <span class="input-icon input-icon-right">
                            <input type="email" id="form-field-email" />
                            <i class="ace-icon fa fa-envelope"></i>
                        </span>
              </div>
            </div>

            <div class="space-4"></div>

            <div class="form-group">
              <label class="col-sm-3 control-label no-padding-right" for="form-field-phone">手机</label>

              <div class="col-sm-9">
                        <span class="input-icon input-icon-right">
                            <input class="input-mask-phone"
                                   type="text" id="form-field-phone"/>
                            <i class="ace-icon fa fa-phone fa-flip-horizontal"></i>
                        </span>
              </div>
            </div>

          </div>

          <div id="edit-settings" class="tab-pane">
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
          <shiro:hasPermission name="profile:modifyPasswd">
          <form  id="passwordForm" action="${ctx}/password" method="post">
          <div id="edit-password" class="tab-pane">
            <div class="space-10"></div>

              <div class="form-group">
                  <label class="col-sm-3 control-label no-padding-right">原密码</label>
                  <div class="col-sm-9">
                    <input type="password" name="oldPassword">
                  </div>
              </div>

            <div class="form-group">
              <label class="col-sm-3 control-label no-padding-right">新密码</label>
              <div class="col-sm-9">
                <input type="password"  name="password">
              </div>
            </div>

            <div class="space-4"></div>

            <div class="form-group">
              <label class="col-sm-3 control-label no-padding-right">新密码确认</label>

              <div class="col-sm-9">
                <input type="password" name="repassword">
              </div>
            </div>
          </div>
            </form>
          </shiro:hasPermission>
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