<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<jsp:include page="/WEB-INF/jsp/cadre/colModels.jsp"/>
<c:if test="${param._auth!='self'}">
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
    <c:if test="${param.hideBack!=1}">
    <h4 class="widget-title lighter smaller breadcrumb-back">
        <a href="javascript:;"
           data-load-el="#${param.loadEl}" data-hide-el="#${param.hideEl}"
           class="hideView btn btn-xs btn-success">
            <i class="ace-icon fa fa-reply"></i>
            返回</a>
    </h4>
    </c:if>
</c:if>
<c:if test="${param._auth=='self'}">
    <div id="body-content-view">
</c:if>

<div class="widget-box transparent cadreView" id="view-box">
    <div class="widget-header" style="border-bottom:none">
        <div class="widget-toolbar no-border"
             style="float:left;border-bottom: 1px solid #dce8f1;">
            <ul class="nav nav-tabs">
                <shiro:hasPermission name="cadreAdform:*">
                    <li class="${to=='cadreAdform_page'?'active':''}">
                        <a href="javascript:;"
                           data-url="${ctx}/cadreAdform_page?cadreId=${param.cadreId}&_auth=${param._auth}">干部任免审批表</a>
                    </li>
                </shiro:hasPermission>
                <li class="${to=='cadre_base'?'active':''}">
                    <a href="javascript:;" data-url="${ctx}/cadre_base?cadreId=${param.cadreId}&_auth=${param._auth}">基本信息</a>
                </li>
                <shiro:hasPermission name="cadreEdu:*">
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
                <shiro:hasPermission name="cadrePost:*">
                    <li class="${to=='cadrePost_page'?'active':''}">
                        <a href="javascript:;"
                           data-url="${ctx}/cadrePost_page?cadreId=${param.cadreId}&_auth=${param._auth}">任职情况</a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadrePostInfo:*">
                    <li class="${to=='cadrePostInfo_page'?'active':''}">
                        <a href="javascript:;"
                           data-url="${ctx}/cadrePostInfo_page?cadreId=${param.cadreId}&_auth=${param._auth}">岗位过程信息</a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreParttime:*">
                    <li class="${to=='cadreParttime_page'?'active':''}">
                        <a href="javascript:;"
                           data-url="${ctx}/cadreParttime_page?cadreId=${param.cadreId}&_auth=${param._auth}">社会或学术兼职</a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreTrain:*">
                    <li class="${to=='cadreTrain_page'?'active':''}">
                        <a href="javascript:;"
                           data-url="${ctx}/cadreTrain_page?cadreId=${param.cadreId}&_auth=${param._auth}">培训情况</a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreCourse:*">
                    <li class="${to=='cadreCourse_page'?'active':''}">
                        <a href="javascript:;"
                           data-url="${ctx}/cadreCourse_page?cadreId=${param.cadreId}&_auth=${param._auth}">教学经历</a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreResearch:*">
                    <li class="${to=='cadreResearch_page'?'active':''}">
                        <a href="javascript:;"
                           data-url="${ctx}/cadreResearch_page?cadreId=${param.cadreId}&_auth=${param._auth}">科研情况</a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreReward:*">
                    <li class="${to=='cadreReward_page'?'active':''}">
                        <a href="javascript:;"
                           data-url="${ctx}/cadreReward_page?cadreId=${param.cadreId}&_auth=${param._auth}">其他奖惩情况</a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreFamily:*">
                    <li class="${to=='cadreFamily_page'?'active':''}">
                        <a href="javascript:;"
                           data-url="${ctx}/cadreFamily_page?cadreId=${param.cadreId}&_auth=${param._auth}">家庭成员信息</a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreCompany:*">
                    <li class="${to=='cadreCompany'?'active':''}">
                        <a href="javascript:;"
                           data-url="${ctx}/cadreCompany?cadreId=${param.cadreId}&_auth=${param._auth}">企业、社团兼职</a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreConcat:*">
                    <li>
                        <a href="javascript:;"
                           data-url="${ctx}/cadreConcat_page?cadreId=${param.cadreId}&_auth=${param._auth}">联系方式</a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreInspectInfo:*">
                    <shiro:hasPermission name="cisInspectObj:list">
                    <li>
                        <a href="javascript:;"
                           data-url="${ctx}/cadreInspectInfo_page?cadreId=${param.cadreId}&_auth=${param._auth}">考察情况</a>
                    </li>
                    </shiro:hasPermission>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreEva:*">
                    <li>
                        <a href="javascript:;"
                           data-url="${ctx}/cadreEva_page?cadreId=${param.cadreId}&_auth=${param._auth}">年度考核记录</a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreInfo:check">
                    <li class="${to=='cadreInfoCheck_table'?'active':''}">
                        <a href="javascript:;"
                           data-url="${ctx}/cadreInfoCheck_table?cadreId=${param.cadreId}&_auth=${param._auth}">干部信息完整性校验表</a>
                    </li>
                </shiro:hasPermission>

                <shiro:hasPermission name="cadreInfoForm:*">
                    <li>
                        <a href="javascript:;"
                           data-url="${ctx}/cadreInfoForm_page?cadreId=${param.cadreId}&_auth=${param._auth}">干部信息采集表</a>
                    </li>
                </shiro:hasPermission>

                <shiro:hasPermission name="cadreInfoForm2:*">
                    <li>
                        <a href="javascript:;"
                           data-url="${ctx}/cadreInfoForm2_page?cadreId=${param.cadreId}&_auth=${param._auth}">干部信息表</a>
                    </li>
                </shiro:hasPermission>
                <%--<shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                    <li>

                        <a href="javascript:;"
                           data-url="${ctx}/cadreModifyHelp?cadreId=${param.cadreId}&_auth=${param._auth}"
                           style="padding-bottom: 5px;">
                        <span class="label label-info">
                            <i class="fa fa-info-circle"></i> 说 明</span></a>

                    </li>
                </shiro:lacksPermission>--%>
            </ul>

        </div>

    </div>
    <div class="widget-body" style="clear:left">
        <div class="widget-main no-padding">
            <div class="tab-content">
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