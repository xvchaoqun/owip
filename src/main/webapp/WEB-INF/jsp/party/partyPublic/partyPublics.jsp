<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="OW_PARTY_PUBLIC_TYPE_MAP" value="<%=OwConstants.OW_PARTY_PUBLIC_TYPE_MAP%>"/>
document.write('<ul class="list"><c:forEach items="${partyPublics}" var="pp"><li class="item"><a href="${_sysConfig.siteHome}/public/partyPublic?id=${cm:base64Encode(pp.id)}" target="_blank">${pp.partyName} ${OW_PARTY_PUBLIC_TYPE_MAP.get(pp.type)}（${cm:formatDate(pp.pubDate, "yyyy.MM")}）</a></li></c:forEach></ul> ')