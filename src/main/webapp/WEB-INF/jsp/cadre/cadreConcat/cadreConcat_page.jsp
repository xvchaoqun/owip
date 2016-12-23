<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="col-xs-offset-1 width500">
    <div class="page-header">
        <h1>
            <i class="fa fa-user"></i>
            ${cadre.user.realname} - 联系方式
<c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
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
                        <span class="editable" >${cadre.user.mobile}</span>
                    </div>
                </div>
                <div class="profile-info-row">
                    <div class="profile-info-name"> 短信称谓</div>

                    <div class="profile-info-value">
                        <span class="editable" >
                        ${empty cadre.user.msgTitle?cadre.user.realname:cadre.user.msgTitle}</span>
                    </div>
                </div>
                <div class="profile-info-row">
                    <div class="profile-info-name">办公电话 </div>

                    <div class="profile-info-value">
                        <span class="editable" >${cadre.user.phone}</span>
                    </div>
                </div>
                <div class="profile-info-row">
                    <div class="profile-info-name">家庭电话 </div>

                    <div class="profile-info-value">
                        <span class="editable" >${cadre.user.homePhone}</span>
                    </div>
                </div>
                <div class="profile-info-row">
                    <div class="profile-info-name">电子邮箱 </div>

                    <div class="profile-info-value">
                        <span class="editable" >${cadre.user.email}</span>
                    </div>
                </div>
            </div>
    </div>
</div>
<div class="footer-margin"/>
<style>
    .profile-info-name{
        width: 100px;
    }
</style>
<script>

    function _au() {
        loadModal("${ctx}/cadreConcat_au?cadreId=${param.cadreId}");
    }

    function _reload(){
        $("#modal").modal('hide');
        $("#view-box .tab-content").load("${ctx}/cadreConcat_page?${cm:encodeQueryString(pageContext.request.queryString)}");
    }
</script>