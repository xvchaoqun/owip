<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<jsp:include page="/WEB-INF/jsp/cadre/colModels.jsp"/>
<div id="cadreTitleDiv" class="breadcrumb-title hidden-xs hidden-sm" title="${cadre.title}">
    【${cadre.realname}】<c:if test="${not empty cadre.title}"> — ${cadre.title}</c:if>
</div>
<script>
    $("#breadcrumbs").show();
    //console.log('$("#breadcrumbs").position().left=' + $("#breadcrumbs").position().left)
    //console.log('$("#breadcrumbs ul.breadcrumb").outerWidth(true)=' + $("#breadcrumbs ul.breadcrumb").outerWidth(true))
    var _w = $("#breadcrumbs").position().left + $("#breadcrumbs ul.breadcrumb").outerWidth(true);
    $("#cadreTitleDiv").css("left", _w + "px")
</script>
<div class="widget-box transparent cadreView" id="view-box">
    <div class="widget-header" style="border-bottom:none">
        <div class="widget-toolbar no-border"
             style="float:left;border-bottom: 1px solid #dce8f1;">

            <ul class="nav nav-tabs">
                <li>
                    <h4 class="widget-title lighter smaller" style="padding-right: 20px;margin-bottom: 0">
                        <a href="javascript:;"
                           data-load-el="#${param.loadEl}" data-hide-el="#${param.hideEl}"
                           class="hideView btn btn-xs btn-success">
                            <i class="ace-icon fa fa-reply"></i>
                            返回</a>
                    </h4>
                </li>
                <li class="${to=='cadre_base'?'active':''}">
                    <a href="javascript:;" data-url="${ctx}/cadre_base?cadreId=${param.cadreId}&_auth=${param._auth}">基本信息</a>
                </li>
                <%--<shiro:hasPermission name="cadreEdu:*">
                    <li class="${to=='cadreEdu_page'?'active':''}">
                        <a href="javascript:;"
                           data-url="${ctx}/cadreEdu_page?cadreId=${param.cadreId}&_auth=${param._auth}">学习经历</a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreWork:*">
                    <li class="${to=='cadreWork_page'?'active':''}">
                        <a href="javascript:;"
                           data-url="${ctx}/cadreWork_page?cadreId=${param.cadreId}&_auth=${param._auth}">工作经历</a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreTrain:*">
                    <li class="${to=='cadreTrain_page'?'active':''}">
                        <a href="javascript:;"
                           data-url="${ctx}/cadreTrain_page?cadreId=${param.cadreId}&_auth=${param._auth}">培训情况</a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreCompany:*">
                    <li class="${to=='cadreCompany'?'active':''}">
                        <a href="javascript:;"
                           data-url="${ctx}/cadreCompany?cadreId=${param.cadreId}&_auth=${param._auth}">企业、社团兼职</a>
                    </li>
                </shiro:hasPermission>--%>
                <shiro:hasPermission name="partyPost:list">
                    <li>
                        <a href="javascript:;" data-url="${ctx}/party/partyPost?userId=${cadre.userId}">党内任职经历</a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="partyReward:list">
                    <li>
                        <a href="javascript:;" data-url="${ctx}/party/partyReward?userId=${cadre.userId}&type=3">党内奖励</a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="partyPunish:list">
                    <li>
                        <a href="javascript:;" data-url="${ctx}/party/partyPunish?userId=${cadre.userId}&type=3">党内处分</a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="partyEva:*">
                <li>
                    <a href="javascript:;"
                       data-url="${ctx}/partyEva_page?userId=${cadre.userId}">年度考核记录</a>
                </li>
                </shiro:hasPermission>
                <c:if test="${not empty branchSecretary}">
                 <shiro:hasPermission name="partyMemberInfoForm:*">
                <li>
                    <a href="javascript:;"
                       data-url="${ctx}/partyMemberInfoForm_page?cadreId=${param.cadreId}&branchId=${param.branchId}&_auth=${param._auth}">支部书记信息采集表</a>
                </li>
                 </shiro:hasPermission>
                    </c:if>
                <li>
                    <a href="javascript:;"
                       data-url="${ctx}/memberInfoForm_page?cadreId=${param.cadreId}">党员信息采集表</a>
                </li>
            </ul>

        </div>

    </div>
    <div class="widget-body" style="clear:left">
        <div class="widget-main no-padding">
            <div class="tab-content" id="partyMemberViewContent">
                <c:import url="/${to}"/>
            </div>
        </div>
        <!-- /.widget-main -->
    </div>
    <!-- /.widget-body -->
</div>
<c:if test="${param._auth=='self'}">
</div>
</c:if>
<!-- /.widget-box -->
<script>
    $(document).off("change", ".cadre-info-check").on("change", ".cadre-info-check", function (e) {
        var $this = $(this);
        var name = $this.data("name");
        var isChecked = $this.prop("checked");

        $.post("${ctx}/cadreInfoCheck_update?cadreId=${param.cadreId}&toApply=1", {
            name: name,
            isChecked: isChecked
        }, function (ret) {
            //console.log(name + ":" + isChecked)
            if (ret.success) {
                $this.tip({content: '操作成功'});
                $("button.btn").prop("disabled", isChecked);
            } else {
                $this.prop("checked", !isChecked);
            }
        })
    })
    $(document).off("click", ".cadre-info-check-edit").on("click", ".cadre-info-check-edit", function (e) {
        e.preventDefault();
        var $this = $(this);
        var isAdmin = ${cm:isPermitted(PERMISSION_CADREADMIN)};
        //console.log(location.hash)
        if(location.hash.startWith("#/user/cadre")) isAdmin=false;
        var url = "${ctx}{0}".format((isAdmin ? '/cadre_view' : '/user/cadre')) + $this.data("url");
        if (isAdmin)
            $.openView(url);
        else
            $.loadPage({url: url});
    })

</script>