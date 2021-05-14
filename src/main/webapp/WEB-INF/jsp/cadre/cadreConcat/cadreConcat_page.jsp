<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="col-xs-offset-1 width500">
    <div class="page-header">
        <h1>
            <i class="fa fa-user"></i>
            ${cadre.realname} - 联系方式
            <c:if test="${hasDirectModifyCadreAuth}">
            <shiro:hasPermission name="cadreConcat:edit">
                <a class="btn btn-info btn-xs" onclick="_au()"><i class="fa fa-edit"></i>   编辑</a>
            </shiro:hasPermission>
            </c:if>
        </h1>
    </div>
    <div class="row">
            <div class="profile-user-info profile-user-info-striped">
                <div class="profile-info-row">
                    <div class="profile-info-name"> 手机号 </div>

                    <div class="profile-info-value">
                        <span class="editable" ><t:mask src="${cadre.mobile}" type="mobile"/></span>
                    </div>
                </div>
                <c:if test="${not empty cadre.user.msgMobile}">
                <div class="profile-info-row">
                    <div class="profile-info-name"> 代收短信 </div>

                    <div class="profile-info-value">
                        <span class="editable" ><t:mask src="${cadre.user.msgMobile}" type="mobile"/></span>
                    </div>
                </div>
                </c:if>
                <div class="profile-info-row">
                    <div class="profile-info-name"> 接收短信 </div>
                    <div class="profile-info-value">
                        <span class="editable" >${cadre.user.notSendMsg?'否':'是'}</span>
                    </div>
                </div>
                <div class="profile-info-row">
                    <div class="profile-info-name"> 短信称谓</div>

                    <div class="profile-info-value">
                        <span class="editable" >
                        ${empty cadre.msgTitle?cadre.realname:cadre.msgTitle}</span>
                    </div>
                </div>
                <div class="profile-info-row">
                    <div class="profile-info-name">办公电话 </div>

                    <div class="profile-info-value">
                        <span class="editable" ><t:mask src="${cadre.phone}" type="fixedPhone"/></span>
                    </div>
                </div>
                <div class="profile-info-row">
                    <div class="profile-info-name">家庭电话 </div>

                    <div class="profile-info-value">
                        <span class="editable" ><t:mask src="${cadre.homePhone}" type="fixedPhone"/></span>
                    </div>
                </div>
                <div class="profile-info-row">
                    <div class="profile-info-name">电子邮箱 </div>

                    <div class="profile-info-value">
                        <span class="editable" ><t:mask src="${cadre.email}" type="email"/></span>
                    </div>
                </div>
            </div>
    </div>
</div>

<style>
    .profile-info-name{
        width: 100px;
    }
</style>
<script>

    function _au() {
        $.loadModal("${ctx}/cadreConcat_au?cadreId=${param.cadreId}");
    }

    function _reload(){
        $("#modal").modal('hide');
        $("#tab-content").loadPage("${ctx}/cadreConcat_page?${cm:encodeQueryString(pageContext.request.queryString)}");
    }
</script>