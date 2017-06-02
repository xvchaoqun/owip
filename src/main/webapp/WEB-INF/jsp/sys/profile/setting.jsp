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
        <jsp:include page="menu.jsp"/>

        <div class="tab-content profile-edit-tab-content">
          暂无
          <%--<div>
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
          </div>--%>
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