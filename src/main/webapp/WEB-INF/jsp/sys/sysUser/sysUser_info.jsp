<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
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
                    <div class="profile-info-name"> 所在院系/专业</div>

                    <div class="profile-info-value">
                        <span class="editable">${unit}</span>
                    </div>
                </div>
                <div class="profile-info-row">
                    <div class="profile-info-name"> ${(sysUser.type==USER_TYPE_JZG)?"工作证号":"学号"} </div>

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
                        <span class="editable">${cm:formatDate(sysUser.birth,'yyyy.MM')}</span>
                    </div>
                </div>

                <div class="profile-info-row">
                    <div class="profile-info-name"> 证件号码</div>

                    <div class="profile-info-value">
                        <span class="editable"><t:mask src="${sysUser.idcard}" type="idCard"/></span>
                    </div>
                </div>
                <div class="profile-info-row">
                    <div class="profile-info-name"> 手机</div>

                    <div class="profile-info-value">
                        <span class="editable"><t:mask src="${sysUser.mobile}" type="mobile"/></span>
                    </div>
                </div>
                <div class="profile-info-row">
                    <div class="profile-info-name">邮箱</div>

                    <div class="profile-info-value">
                        <span class="editable email"><t:mask src="${sysUser.email}" type="email"/></span>
                    </div>
                </div>
                <div class="profile-info-row">
                    <div class="profile-info-name">系统角色</div>
                    <div class="profile-info-value">
                                    <span class="editable">
                                        <c:forEach items="${fn:split(sysUser.roleIds,',')}" var="id" varStatus="vs">
                                            ${roleMap.get(cm:toInt(id)).name}
                                            <c:if test="${!vs.last}">,</c:if>
                                        </c:forEach>
                                    </span>
                    </div>
                </div>
                <c:if test="${fn:length(adminPartyIdList)>0}">
                    <div class="profile-info-row">
                        <div class="profile-info-name">管理${_p_partyName}</div>
                        <div class="profile-info-value">
                                    <span class="editable">
                                        <c:forEach items="${adminPartyIdList}" var="partyId" varStatus="vs">
                                            ${cm:displayParty(partyId, null)}
                                            <shiro:hasPermission name="${PERMISSION_OWADMIN}">
                                            <a class="confirm btn btn-danger btn-xs"
                                               data-url="${ctx}/partyAdmin_del?userId=${param.userId}&partyId=${partyId}"
                                               data-msg='确定删除该管理员[${cm:displayParty(partyId, null)}]？'
                                               data-callback="_delAdminCallback">删除</a>
                                            </shiro:hasPermission>
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
                                            <shiro:hasPermission name="${PERMISSION_OWADMIN}">
                                            <a class="confirm btn btn-danger btn-xs"
                                               data-url="${ctx}/branchAdmin_del?userId=${param.userId}&branchId=${branch.id}"
                                               data-msg='确定删除该管理员[${cm:displayParty(branch.partyId, branch.id)}]？'
                                               data-callback="_delAdminCallback">删除</a>
                                            </shiro:hasPermission>
                                            <c:if test="${!vs.last}">,</c:if>
                                        </c:forEach>
                                    </span>
                        </div>
                    </div>
                </c:if>

            </div>
        </div>
        <div>
            <c:set var="content" value="${_p_siteHome}?code=${sysUser.code}"/>
            <img src="${ctx}/qrcode?content=${cm:sign(content)}&_=<%=new Date().getTime()%>"/>
        </div>
    </div>
    <shiro:hasPermission name="sysSync:user">
    <c:if test="${sysUser.casUser}">
        <div class="clearfix form-actions center">
            <button class="btn btn-info  btn-pink" onclick="sync_user(${param.userId}, this)" type="button"
                    data-loading-text="<i class='fa fa-refresh fa-spin'></i> 同步中..." autocomplete="off">
                <i class="ace-icon fa fa-random"></i>
                同步校园账号信息
            </button>
        </div>
    </c:if>
    </shiro:hasPermission>
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
        $.openView("${ctx}/sysUser_view?userId=${param.userId}")
    }
</script>