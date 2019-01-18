<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="col-xs-offset-1 col-xs-10">

    <div class="page-header">
        <h1>
            <i class="fa fa-user"></i>
            ${sysUser.realname}
        </h1>
    </div>
    <div class="row">
        <div class="col-xs-6">
            <div class="profile-user-info profile-user-info-striped">
                <div class="profile-info-row">
                    <div class="profile-info-name"> 所在单位</div>

                    <div class="profile-info-value">
                        <span class="editable">${unit}</span>
                    </div>
                </div>
                <div class="profile-info-row">
                    <div class="profile-info-name"> ${(sysUser.type==USER_TYPE_JZG)?"教工号":"学号"} </div>

                    <div class="profile-info-value">
                                    <span class="editable" id="username">${sysUser.code}

                                    </span>
                    </div>
                </div>

                <div class="profile-info-row">
                    <div class="profile-info-name"> 性别</div>

                    <div class="profile-info-value">
                        <span class="editable">${GENDER_MAP.get(sysUser.gender)}</span>
                    </div>
                </div>

                <div class="profile-info-row">
                    <div class="profile-info-name"> 出生年月</div>

                    <div class="profile-info-value">
                        <span class="editable">${cm:formatDate(sysUser.birth,'yyyy-MM-dd')}</span>
                    </div>
                </div>

                <div class="profile-info-row">
                    <div class="profile-info-name"> 身份证</div>

                    <div class="profile-info-value">
                        <span class="editable">${sysUser.idcard}</span>
                    </div>
                </div>
                <div class="profile-info-row">
                    <div class="profile-info-name"> 手机</div>

                    <div class="profile-info-value">
                        <span class="editable">${sysUser.mobile}</span>
                    </div>
                </div>
                <div class="profile-info-row">
                    <div class="profile-info-name">邮箱</div>

                    <div class="profile-info-value">
                        <span class="editable">${sysUser.email}</span>
                    </div>
                </div>
                <div class="profile-info-row">
                    <div class="profile-info-name">系统角色</div>
                    <div class="profile-info-value">
                                    <span class="editable">
                                        <c:forEach items="${fn:split(sysUser.roleIds,',')}" var="id" varStatus="vs">
                                            ${roleMap.get(cm:toInt(id)).description}
                                            <c:if test="${!vs.last}">,</c:if>
                                        </c:forEach>
                                    </span>
                    </div>
                </div>
                <c:if test="${fn:length(adminPartyIdList)>0}">
                    <div class="profile-info-row">
                        <div class="profile-info-name">管理分党委</div>
                        <div class="profile-info-value">
                                    <span class="editable">
                                        <c:forEach items="${adminPartyIdList}" var="partyId" varStatus="vs">
                                            ${cm:displayParty(partyId, null)}
                                            <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN}">
                                            <a class="confirm btn btn-danger btn-xs"
                                               data-url="${ctx}/partyAdmin_del?userId=${param.userId}&partyId=${partyId}"
                                               data-msg='确定删除该管理员[${cm:displayParty(partyId, null)}]？'
                                               data-callback="_delAdminCallback">删除</a>
                                            </shiro:hasAnyRoles>
                                            <c:if test="${!vs.last}">,</c:if>
                                        </c:forEach>
                                    </span>
                        </div>
                    </div>
                </c:if>
                <c:if test="${fn:length(adminBranchIdList)>0}">
                    <div class="profile-info-row">
                        <div class="profile-info-name">管理党支部</div>
                        <div class="profile-info-value">
                                    <span class="editable">
                                        <c:forEach items="${adminBranchIdList}" var="branchId" varStatus="vs">
                                            <c:set var="branch" value="${branchMap.get(branchId)}"/>
                                            ${cm:displayParty(branch.partyId, branch.id)}
                                            <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN}">
                                            <a class="confirm btn btn-danger btn-xs"
                                               data-url="${ctx}/branchAdmin_del?userId=${param.userId}&branchId=${branch.id}"
                                               data-msg='确定删除该管理员[${cm:displayParty(branch.partyId, branch.id)}]？'
                                               data-callback="_delAdminCallback">删除</a>
                                            </shiro:hasAnyRoles>
                                            <c:if test="${!vs.last}">,</c:if>
                                        </c:forEach>
                                    </span>
                        </div>
                    </div>
                </c:if>

            </div>
        </div>
        <div>
            <c:set var="content" value="${_sysConfig.siteHome}?code=${sysUser.code}"/>
            <img src="${ctx}/qrcode?content=${cm:encodeURI(content)}&_=<%=new Date().getTime()%>"/>
        </div>
    </div>
    <div class="clearfix form-actions center">
        <c:if test="${sysUser.source==USER_SOURCE_YJS
                            || sysUser.source==USER_SOURCE_BKS || sysUser.source==USER_SOURCE_JZG}">
            <button class="btn btn-info  btn-pink" onclick="sync_user(${param.userId}, this)" type="button"
                    data-loading-text="<i class='fa fa-refresh fa-spin'></i> 同步中..." autocomplete="off">
                <i class="ace-icon fa fa-random"></i>
                同步学校信息
            </button>
            &nbsp; &nbsp; &nbsp;
        </c:if>
        <button class="hideView btn" type="button">
            <i class="ace-icon fa fa-undo"></i>
            返回
        </button>
    </div>
</div>
<script>
    function _reload() {
        $("#body-content-view").load("${ctx}/sysUser_view?userId=${sysUser.id}&_=" + new Date().getTime());
    }

    function sync_user(userId, btn) {
        var $btn = $(btn).button('loading')
        var $container = $("#body-content-view");
        $container.showLoading({
            'afterShow':
                function () {
                    setTimeout(function () {
                        $container.hideLoading();
                        $btn.button('reset');
                    }, 2000);
                }
        });
        $.post("${ctx}/sync_user", {userId: userId}, function (ret) {

            if (ret.success) {
                $container.hideLoading();
                _reload();
                $btn.button('reset');
            }
        });
    }

    function _delAdminCallback(target) {
        $.loadView("${ctx}/sysUser_view?userId=${param.userId}")
    }
</script>