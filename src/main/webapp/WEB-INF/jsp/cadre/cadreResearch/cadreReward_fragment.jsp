<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:if test="${fn:length(cadreRewards)>0}"><p>获奖情况：</p></c:if>
<c:forEach items="${cadreRewards}" var="cadreReward">
  <p style="text-indent: 2em">${cm:formatDate(cadreReward.rewardTime, "yyyy")}年${cadreReward.name}<c:if test="${cadreReward.rank>0}">(排名第${cadreReward.rank})</c:if>，${cadreReward.unit}</p>
</c:forEach>
