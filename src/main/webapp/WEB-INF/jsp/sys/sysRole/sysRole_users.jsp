<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>${sysRole.description} - 权限拥有人</h3>
</div>
<div class="modal-body popup-jqgrid">
  <form class="form-inline search-form" id="searchForm_popup">
    <div class="form-group">
      <label>账号</label>
      <input class="form-control search-query" name="username" type="text" value="${param.username}"
             style="width: 120px;" placeholder="请输入账号">
    </div>
    <div class="form-group">
      <label>学工号</label>
      <input class="form-control search-query" name="code" type="text" value="${param.code}"
             style="width: 120px;" placeholder="请输入学工号">
    </div>
    <div class="form-group">
      <label>姓名</label>
      <input class="form-control search-query" name="realname" type="text" value="${param.realname}"
             style="width: 120px;" placeholder="请输入姓名">
    </div>
    <div class="form-group">
      <label>类别</label>
      <select name="type" data-placeholder="请选择" data-width="120" data-ref="select2">
        <option></option>
        <c:forEach items="${USER_TYPE_MAP}" var="userType">
          <option value="${userType.key}">${userType.value}</option>
        </c:forEach>
      </select>
      <script>
          $("#searchForm_popup select[name=type]").val('${param.type}');
      </script>
    </div>
    <div class="form-group">
      <label>来源</label>
      <select name="source" data-placeholder="请选择" data-width="120" data-ref="select2">
        <option></option>
        <c:forEach items="<%=SystemConstants.USER_SOURCE_MAP%>" var="userSource">
          <option value="${userSource.key}">${userSource.value}</option>
        </c:forEach>
      </select>
      <script>
          $("#searchForm_popup select[name=source]").val('${param.source}');
      </script>
    </div>
    <c:set var="_query" value="${not empty param.username ||not empty param.code
             || not empty param.realname || not empty param.type || not empty param.source}"/>
    <div  class="form-group">
      <button type="button" data-url="${ctx}/sysRole_users?locked=0&roleId=${param.roleId}"
              data-target="#modal .modal-content" data-form="#searchForm_popup"
              class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</button>
      <c:if test="${_query}">
        <button type="button"
                data-url="${ctx}/sysRole_users?locked=0&roleId=${param.roleId}"
                data-target="#modal .modal-content"
                class="reloadBtn btn btn-warning btn-sm">
          <i class="fa fa-reply"></i> 重置
        </button>
      </c:if>
    </div>
  </form>
  <table id="jqGrid_popup" class="table-striped"> </table>
  <div id="jqGridPager_popup"> </div>
</div>
<jsp:include page="../sysUser/sysUser_colModel.jsp"/>
<script>

    function _reload2(){
      $("#jqGrid_popup").trigger("reloadGrid");
    }
    $("#searchForm_popup select").select2();
  //$.register.user_select($('#searchForm_popup select[name=userId]'));
  //$('#searchForm_popup [data-rel="select2"]').select2();
  $("#jqGrid_popup").jqGrid({
    multiselect:false,
    height:390,
    width:920,
    rowNum:10,
    ondblClickRow:function(){},
    pager:"jqGridPager_popup",
    url: "${ctx}/sysUser_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}",
    colModel: colModel
  }).jqGrid("setFrozenColumns");
  $.initNavGrid("jqGrid_popup", "jqGridPager_popup");

    $.register.fancybox(function () {
        //console.log(this)
        this.title = '<div class="title">' + this.title + '</div>';
    });

</script>