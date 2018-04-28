<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="alert alert-info" style="width: 1220px">
        ${cm:getHtmlFragment('hf_abroad_passport_apply_progress').content}
</div>
<div id="apply-content">
<c:import url="/user/abroad/passportApply_select?cadreId=${param.cadreId}&auth=${param.auth}"/>
</div>
