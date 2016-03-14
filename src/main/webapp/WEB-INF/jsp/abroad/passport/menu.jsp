<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<li  class="<c:if test="${status==0}">active</c:if>">
<a href="?status=0"><i class="fa fa-circle-o"></i> 证件信息统计</a>
</li>
<li  class="<c:if test="${status==1}">active</c:if>">
<a href="?status=1"><i class="fa fa-circle-o"></i> 集中管理证件</a>
</li>
<li  class="<c:if test="${status==2}">active</c:if>">
<a href="?status=2"><i class="fa fa-circle-o"></i> 取消集中管理证件</a>
</li>
<li  class="<c:if test="${status==3}">active</c:if>">
<a href="?status=3"><i class="fa fa-circle-o"></i> 丢失的证件</a>
</li>
<li  class="<c:if test="${status==4}">active</c:if>">
<a href="?status=4"><i class="fa fa-times"></i> 已作废证件</a>
</li>
<li  class="<c:if test="${status==5}">active</c:if>">
<a href="?status=5"><i class="fa fa-inbox"></i> 保险柜管理</a>
</li>
