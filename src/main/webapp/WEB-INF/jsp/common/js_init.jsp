<%@ page language="java" contentType="application/javascript;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="taglibs.jsp"%>
var ctx="${ctx}", isMobile=false, _hasLoginPage= ${_hasLoginPage=='true'},
		_ps='${cm:base64Encode(cm:toJSONObject(cm:findPermissions(_user.username, false)))}',
	_uploadMaxSize=${_uploadMaxSize}, _mobileRegex="${fn:replace(_pMap['mobileRegex'], "\\", "\\\\")}";
