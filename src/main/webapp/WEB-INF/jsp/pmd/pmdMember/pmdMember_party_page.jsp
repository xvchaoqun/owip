<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>${branch.name}-缴费详情</h3>
</div>
<div class="modal-body">
  <form class="form-inline search-form" id="searchForm_popup">
    <input type="hidden" name="cls" value="${param.cls}">
    <input type="hidden" name="partyId" value="${param.partyId}">
    <input type="hidden" name="branchId" value="${param.branchId}">
    <input type="hidden" name="monthId" value="${param.monthId}">
    <div class="form-group">
      <label>姓名</label>
      <select data-rel="select2-ajax"
              data-ajax-url="${ctx}/member_selects?noAuth=1&partyId=${param.partyId}&branchId=${param.branchId}&status=${MEMBER_STATUS_NORMAL}"
              name="userId" data-placeholder="请输入账号或姓名或学工号">
        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
      </select>
    </div>
    <div class="form-group">
      <label>缴费状态</label>
      <select data-rel="select2" name="hasPay"
              data-width="100"
              data-placeholder="请选择">
        <option></option>
        <option value="0">未缴费</option>
        <option value="1">已缴费</option>
      </select>
      <script>
        $("#searchForm_popup select[name=hasPay]").val("${param.hasPay}")
      </script>
    </div>
    <div class="form-group">
      <label>按时/延迟缴费</label>
      <select data-rel="select2" name="isDelay"
              data-width="120"
              data-placeholder="请选择">
        <option></option>
        <option value="0">按时缴费</option>
        <option value="1">延迟缴费</option>
      </select>
      <script>
        $("#searchForm_popup select[name=isDelay]").val("${param.isDelay}")
      </script>
    </div>
    <div class="form-group">
      <label>缴费方式</label>
      <select data-rel="select2" name="isSelfPay"
              data-width="120"
              data-placeholder="请选择">
        <option></option>
        <option value="0">代缴党费</option>
        <option value="1">线上缴费</option>
      </select>
      <script>
        $("#searchForm_popup select[name=isSelfPay]").val("${param.isSelfPay}")
      </script>
    </div>
    <c:set var="_query" value="${not empty param.userId ||not empty param.hasPay
             || not empty param.isDelay || not empty param.isSelfPay}"/>
    <div  class="form-group">
      <button type="button" data-url="${ctx}/pmd/pmdMember"
              data-target="#modal .modal-content" data-form="#searchForm_popup"
              class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</button>
      <c:if test="${_query}">
        <button type="button"
                data-url="${ctx}/pmd/pmdMember"
                data-querystr="cls=${param.cls}&partyId=${param.partyId}&branchId=${param.branchId}&monthId=${param.monthId}"
                data-target="#modal .modal-content"
                class="resetBtn btn btn-warning btn-sm">
          <i class="fa fa-reply"></i> 重置
        </button>
      </c:if>
    </div>
  </form>
  <table id="jqGrid_popup" class="table-striped"> </table>
  <div id="jqGridPager_popup"> </div>
</div>
<jsp:include page="pmdMember_colModel.jsp?type=admin"/>
<script>

    function _reload2(){
      $("#jqGrid_popup").trigger("reloadGrid");
    }

  register_user_select($('#searchForm_popup select[name=userId]'));
  $('#searchForm_popup [data-rel="select2"]').select2();
  $("#jqGrid_popup").jqGrid({
    multiselect:false,
    height:390,
    width:1160,
    rowNum:10,
    ondblClickRow:function(){},
    pager:"jqGridPager_popup",
    url: "${ctx}/pmd/pmdMember_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}",
    colModel: colModel
  });
  $.initNavGrid("jqGrid_popup", "jqGridPager_popup");
</script>