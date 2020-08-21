<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="<c:if test="${cls==1}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/pcs/pcsPartyList?cls=1">
      <i class="fa fa-navicon"></i> ${_p_partyName}列表</a>
  </li>
  <li class="<c:if test="${cls==2}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/pcs/pcsPartyList?cls=2">
      <i class="fa fa-sitemap"></i> 党支部列表</a>
  </li>
  <li class="<c:if test="${cls==3}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/pcs/pcsPartyList?cls=3">
      <i class="fa fa-close "></i> 不参与党代会的党支部列表</a>
  </li>
  <div class="buttons pull-left hidden-sm hidden-xs" style="left:20px; position: relative">
    <shiro:hasRole name="${ROLE_SUPER}">
     <shiro:hasPermission name="pcsPartyList:edit">
        <a class="confirm btn btn-success btn-sm"
           data-url="${ctx}/pcs/pcsParty_sync"
           data-title="同步当前党组织"
           data-msg="确定同步当前党组织？"
           data-callback="_syncCallback"><i class="fa fa-random"></i>
          同步当前党组织</a>
     </shiro:hasPermission>
      </shiro:hasRole>
  </div>
</ul>
<script>
  function _syncCallback(){
    SysMsg.success("同步成功")
  }
</script>