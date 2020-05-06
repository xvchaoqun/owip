<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div id="body-content" class="multi-row-head-table">
     <c:set var="_query" value="${not empty param.unitId ||not empty param.unitTypes
        ||not empty param.cadreIsPrincipal||not empty param.leaderType ||not empty param.gender  ||not empty param.cadreId
         ||not empty param.cadrePostType ||
         (not empty startNowPostAge && startNowPostAge!=8 && startNowPostAge!=10)
         ||not empty param.endNowPostAge
        || not empty param.code || not empty param.sort}"/>
    <div class="jqgrid-vertical-offset buttons">
        <div class="type-select" style="float: left;">
            同一岗位任职满：
            <span class="typeCheckbox">
            <input ${startNowPostAge==8?"checked disabled":""} type="checkbox" class="big"
                                                         value="8"> 8年
            </span>
            <span class="typeCheckbox">
            <input ${startNowPostAge==10?"checked disabled":""} type="checkbox" class="big"
                                                         value="10"> 10年
            </span>
            <span class="typeCheckbox">
            <input ${startNowPostAge!=8 && startNowPostAge!=10?"checked disabled":""} type="checkbox" class="big"
                                                         value="-1"> 其他年限
            </span>
        </div>
        <div style="float: left;">
            <select id="sortBy" data-placeholder="请选择排序方式">
                <option></option>
                <option value="lpWorkTime_asc">按任现职时间排序(升序)</option>
                <option value="lpWorkTime_desc">按任现职时间排序(降序)</option>
                <option value="sWorkTime_asc">按现职级时间排序(升序)</option>
                <option value="sWorkTime_desc">按现职级时间排序(降序)</option>
            </select>
            <script>
                $("#sortBy").val('${param.sortBy}');
                $("#searchForm input[name=sortBy]").val('${param.sortBy}');
                $("#sortBy").select2({
                    theme: "default"
                }).change(function () {
                    $("#searchForm input[name=sortBy]").val($(this).val());
                    $("#searchForm .jqSearchBtn").click();
                    if($(this).val()==''){
                        throw new Error();
                    }
                })
            </script>
        </div>
        <div style="float: right;padding-right: 30px">
        <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                data-url="${ctx}/unitPost_data"
                data-querystr="exportType=1"
                data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
            <i class="fa fa-download"></i> 导出
        </button>
        </div>
        <div style="clear: both"/>
    </div>
    <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
        <div class="widget-header">
            <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

            <div class="widget-toolbar">
                <a href="#" data-action="collapse">
                    <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                </a>
            </div>
        </div>
        <div class="widget-body">
            <div class="widget-main no-padding">
                <form class="form-inline search-form" id="searchForm">
                    <input type="hidden" name="displayType" value="${param.displayType}">
                    <c:set var="unit" value="${cm:getUnitById(param.unitId)}"/>
                    <c:set var="cadre" value="${cm:getCadreById(param.cadreId)}"/>
                    <div class="form-group">
                        <label>姓名</label>
                        <select data-rel="select2-ajax"
                                data-ajax-url="${ctx}/cadre_selects?status=${status}"
                                name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                            <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>性别</label>
                         <select name="gender" data-width="100" data-rel="select2"
                                                    data-placeholder="请选择">
                                <option></option>
                                <option value="<%=SystemConstants.GENDER_MALE%>">男</option>
                                <option value="<%=SystemConstants.GENDER_FEMALE%>">女</option>
                            </select>
                            <script>
                                $("#searchForm select[name=gender]").val('${param.gender}');
                            </script>
                    </div>
                    <div class="form-group">
                        <label>职务属性</label>
                        <select name="cadrePostType" data-rel="select2" data-placeholder="请选择职务属性">
                            <option></option>
                            <c:import url="/metaTypes?__code=mc_post"/>
                        </select>
                        <script>
                            $("#searchForm select[name=cadrePostType]").val('${param.cadrePostType}');
                        </script>
                    </div>
                    <div class="form-group">
                        <label>是否正职干部</label>
                        <select name="cadreIsPrincipal" data-width="100"
                                data-rel="select2" data-placeholder="请选择">
                            <option></option>
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                        <script>
                            $("#searchForm select[name=cadreIsPrincipal]").val('${param.cadreIsPrincipal}');
                        </script>
                    </div>
                    <div class="form-group">
                        <label>是否班子负责人</label>
                        <select name="leaderType" data-placeholder="请选择" data-rel="select2" data-width="130">
                            <option></option>
                            <c:forEach items="<%=SystemConstants.UNIT_POST_LEADER_TYPE_MAP%>" var="leaderType">
                                <option value="${leaderType.key}">${leaderType.value}</option>
                            </c:forEach>
                        </select>
                        <script>
                            $("#searchForm select[name=leaderType]").val('${param.leaderType}');
                        </script>
                    </div>
                    <div class="form-group">
                        <label>现职务始任年限</label>
                        <input class="num" type="text" name="startNowPostAge" style="width: 50px"
                               value="${startNowPostAge}"> 至 <input class="num"
                                                                          type="text" style="width: 50px"
                                                                          name="endNowPostAge"
                                                                          value="${param.endNowPostAge}">

                    </div>
                    <div class="clearfix form-actions center">
                        <a class="jqSearchBtn btn btn-default btn-sm"
                           data-url="${ctx}/unitPostList"
                           data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                        <c:if test="${_query}">&nbsp;
                            <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                    data-url="${ctx}/unitPostList?displayType=${param.displayType}">
                                <i class="fa fa-reply"></i> 重置
                            </button>
                        </c:if>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%--<div class="space-4"></div>--%>
    <table id="jqGrid" class="jqGrid table-striped"></table>
    <div id="jqGridPager"></div>
</div>
<div id="body-content-view"></div>
<script type="text/template" id="dispatch_select_tpl">
    <button class="popupBtn btn {{=(count>0)?'btn-warning':'btn-success'}} btn-xs"
            data-url="${ctx}/cadrePost_addDispatchs?id={{=id}}&cadreId={{=cadreId}}"
            data-width="1000"><i class="fa fa-link"></i>
        任免文件({{=count}})
    </button>
</script>
<jsp:include page="/WEB-INF/jsp/unit/unitPost/cadre_colModel.jsp?list=0"/>
<script>
    $(":checkbox", ".typeCheckbox").click(function () {
        if($(this).val()>0) {
            $("#searchForm input[name=startNowPostAge]").val($(this).prop("checked") ? $(this).val() : '');
            $("#searchForm .jqSearchBtn").click();
        }else{
            $("#searchForm input[name=startNowPostAge]").val('')
            $(this).prop("disabled", true);
            $(":checkbox", ".typeCheckbox").not(this).prop("disabled", false).prop("checked", false)
            if($(".widget-header").closest(".widget-box").hasClass('collapsed')) {
                $(".widget-header").click();
            }
        }
    })

    $.register.user_select($('#searchForm select[name=cadreId]'));
    function _reload() {
        $("#modal").modal('hide');
        $("#jqGrid").trigger("reloadGrid");
    }

    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/unitPost_data?callback=?&startNowPostAge=${startNowPostAge}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>