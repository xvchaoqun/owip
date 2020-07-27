<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div style="width: ${empty param.width?'900':param.width}px">
  ${cm:htmlUnescape(htmlFragment.content)}
</div>
