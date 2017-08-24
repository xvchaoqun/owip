<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<jsp:include page="/WEB-INF/jsp/cadre/colModels.jsp"/>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="myTableDiv"
                 data-url-page="${ctx}/cadre"
                 data-url-co="${ctx}/cadre_changeOrder"
                 data-url-export="${ctx}/cadre_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.cadreId ||not empty param.gender
                ||not empty param.startAge||not empty param.endAge||not empty param.startDpAge||not empty param.endDpAge
                ||not empty param.startNowPostAge||not empty param.endNowPostAge||not empty param.startNowLevelAge||not empty param.endNowLevelAge
                ||not empty param._birth||not empty param._cadreGrowTime
                ||not empty param.dpTypes||not empty param.unitIds||not empty param.unitTypes||not empty param.adminLevels||not empty param.maxEdus
                ||not empty param.proPosts ||not empty param.postIds ||not empty param.proPostLevels
                ||not empty param.isPrincipalPost ||not empty param.isDouble || not empty param.code }"/>

                <div class="tabbable">
                    <shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
                        <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                            <li class="<c:if test="${status==CADRE_STATUS_MIDDLE}">active</c:if>">
                                <a href="javascript:;" data-url="/cadre?status=${CADRE_STATUS_MIDDLE}" class="loadPage"><i
                                        class="fa fa-flag"></i> ${CADRE_STATUS_MAP.get(CADRE_STATUS_MIDDLE)}</a>
                            </li>

                            <li class="<c:if test="${status==CADRE_STATUS_MIDDLE_LEAVE}">active</c:if>">
                                <a href="javascript:;" data-url="/cadre?status=${CADRE_STATUS_MIDDLE_LEAVE}"
                                   class="loadPage"><i class="fa fa-flag"></i> ${CADRE_STATUS_MAP.get(CADRE_STATUS_MIDDLE_LEAVE)}</a>
                            </li>
                            <shiro:hasPermission name="cadre:list">
                            <div class="buttons pull-left hidden-sm hidden-xs" style="left:50px; position: relative">
                                <a class="popupBtn btn btn-danger btn-sm"
                                   data-url="${ctx}/cadre/search"><i class="fa fa-search"></i> 查询账号所属干部库</a>
                            </div>
                            </shiro:hasPermission>
                        </ul>
                    </shiro:lacksRole>
                    <div class="tab-content">
                        <div id="home4" class="tab-pane in active rownumbers">
                            <div class="jqgrid-vertical-offset buttons">
                                <c:if test="${status==CADRE_STATUS_MIDDLE_LEAVE}">
                                    <shiro:hasPermission name="cadre:list">
                                    <button class="jqBatchBtn btn btn-warning btn-sm"
                                            data-title="重新任用"
                                            data-msg="确定重新任用这{0}个干部吗？（添加到考察对象中）"
                                            data-url="${ctx}/cadre_re_assign" data-callback="_reAssignCallback">
                                        <i class="fa fa-reply"></i> 重新任用
                                    </button>
                                    </shiro:hasPermission>
                                </c:if>
                                <shiro:hasPermission name="cadre:edit">
                                    <a class="popupBtn btn btn-info btn-sm btn-success"
                                       data-url="${ctx}/cadre_au?status=${status}"><i class="fa fa-plus"></i>
                                        <c:if test="${status==CADRE_STATUS_MIDDLE}">添加现任中层干部</c:if>
                                        <c:if test="${status==CADRE_STATUS_MIDDLE_LEAVE}">添加离任中层干部</c:if>
                                    </a>
                                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                            data-url="${ctx}/cadre_au"
                                            data-querystr="&status=${status}">
                                        <i class="fa fa-edit"></i> 修改信息
                                    </button>
                                </shiro:hasPermission>
                                <c:if test="${status==CADRE_STATUS_MIDDLE}">
                                    <shiro:hasPermission name="cadre:leave">
                                        <button class="jqOpenViewBtn btn btn-success btn-sm"
                                                data-width="700"
                                                data-url="${ctx}/cadre_leave"
                                                data-querystr="&status=${CADRE_STATUS_MIDDLE_LEAVE}">
                                            <i class="fa fa-edit"></i> 离任
                                        </button>
                                    </shiro:hasPermission>
                                </c:if>
                                <c:if test="${status==CADRE_STATUS_MIDDLE}">
                                    <shiro:hasPermission name="cadreAdditionalPost:edit">
                                        <button class="jqOpenViewBtn btn btn-warning btn-sm"
                                                data-url="${ctx}/cadre_additional_post" data-rel="tooltip"
                                                data-placement="bottom"
                                                title="添加职务——仅用于因私出国（境）审批人身份设定">
                                            <i class="fa fa-plus"></i> 因私出国境兼审单位
                                        </button>
                                    </shiro:hasPermission>
                                </c:if>
                                <shiro:hasPermission name="cadre:import">
                                    <a class="popupBtn btn btn-primary btn-sm tooltip-success"
                                       data-url="${ctx}/cadre_import?status=${status}"
                                       data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                                        导入</a>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="cadre:export">
                                    <a class="jqExportBtn btn btn-success btn-sm"
                                       data-rel="tooltip" data-placement="bottom" title="导出选中记录或所有搜索结果"><i
                                            class="fa fa-download"></i> 导出</a>
                                </shiro:hasPermission>
                                <c:if test="${status==CADRE_STATUS_MIDDLE}">
                                    <shiro:hasPermission name="cadre:exportFamliy">
                                        <a class="jqExportBtn btn btn-success btn-sm"
                                           data-url="${ctx}/cadreFamliy_data"
                                           data-rel="tooltip" data-placement="bottom" title="导出选中记录或所有搜索结果"><i
                                                class="fa fa-download"></i> 导出家庭成员</a>
                                    </shiro:hasPermission>
                                </c:if>
                                <shiro:hasPermission name="cadre:del">
                                    <button data-url="${ctx}/cadre_batchDel"
                                            data-title="删除"
                                            data-msg="确定删除这{0}条数据（<span class='text-danger'>相关联数据全部删除，不可恢复</span>）？"
                                            data-grid-id="#jqGrid"
                                            class="jqBatchBtn btn btn-danger btn-sm">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                </shiro:hasPermission>
                            </div>
                            <div class="jqgrid-vertical-offset widget-box collapsed hidden-sm hidden-xs">
                                <div class="widget-header">
                                    <h4 class="widget-title">搜索</h4>

                                    <div class="widget-toolbar">
                                        <a href="javascript:;" data-action="collapse">
                                            <i class="ace-icon fa fa-chevron-down"></i>
                                        </a>
                                    </div>
                                </div>
                                <div class="widget-body">
                                    <div class="widget-main no-padding">
                                        <form class="form-inline search-form" id="searchForm">
                                            <table>
                                                <tr>
                                                    <td class="name">姓名</td>
                                                    <td class="input">
                                                        <input type="hidden" name="status" value="${status}">
                                                        <select data-rel="select2-ajax"
                                                                data-ajax-url="${ctx}/cadre_selects?status=${status}"
                                                                name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                            <option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
                                                        </select>
                                                    </td>
                                                    <td class="name">性别</td>
                                                    <td class="input">
                                                        <select name="gender" data-width="100" data-rel="select2"
                                                                data-placeholder="请选择">
                                                            <option></option>
                                                            <option value="${GENDER_MALE}">男</option>
                                                            <option value="${GENDER_FEMALE}">女</option>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=gender]").val('${param.gender}');
                                                        </script>
                                                    </td>
                                                    <td class="name">党派</td>
                                                    <td class="input">
                                                        <select class="multiselect" multiple="" name="dpTypes"
                                                                style="width: 250px;">
                                                            <option value="-1">非党干部</option>
                                                            <option value="0">中共党员</option>
                                                            <c:forEach var="entry" items="${democraticPartyMap}">
                                                                <option value="${entry.key}">${entry.value.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="name">部门属性</td>
                                                    <td class="input">
                                                        <select class="multiselect" multiple="" name="unitTypes">
                                                            <c:forEach var="unitType" items="${unitTypeMap}">
                                                                <option value="${unitType.value.id}">${unitType.value.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td class="name">出生日期</td>
                                                    <td class="input">
                                                        <div class="input-group tooltip-success" data-rel="tooltip"
                                                             title="出生日期范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                            <input placeholder="请选择出生日期范围" data-rel="date-range-picker"
                                                                   class="form-control date-range-picker"
                                                                   type="text" name="_birth" value="${param._birth}"/>
                                                        </div>
                                                    </td>
                                                    <td class="name">党派加入时间</td>
                                                    <td class="input">
                                                        <div class="input-group tooltip-success" data-rel="tooltip"
                                                             title="党派加入时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                            <input placeholder="请选择党派加入时间范围"
                                                                   data-rel="date-range-picker"
                                                                   class="form-control date-range-picker"
                                                                   type="text" name="_cadreGrowTime"
                                                                   value="${param._cadreGrowTime}"/>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="name">所在单位</td>
                                                    <td class="input">
                                                        <select class="multiselect" multiple="" name="unitIds">
                                                            <c:forEach var="unitType" items="${unitTypeMap}">
                                                                <optgroup label="${unitType.value.name}">
                                                                    <c:forEach
                                                                            items="${unitListMap.get(unitType.value.id)}"
                                                                            var="unitId">
                                                                        <c:set var="unit"
                                                                               value="${unitMap.get(unitId)}"></c:set>
                                                                        <option value="${unit.id}">${unit.name}</option>
                                                                    </c:forEach>
                                                                </optgroup>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td class="name">年龄</td>
                                                    <td class="input">
                                                        <input class="num" type="text" name="startAge"
                                                               value="${param.startAge}"> 至 <input class="num"
                                                                                                   type="text"
                                                                                                   name="endAge"
                                                                                                   value="${param.endAge}">

                                                    </td>
                                                    <td class="name">党龄</td>
                                                    <td class="input">
                                                        <input class="num" type="text" name="startDpAge"
                                                               value="${param.startDpAge}"> 至 <input class="num"
                                                                                                     type="text"
                                                                                                     name="endDpAge"
                                                                                                     value="${param.endDpAge}">

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="name">行政级别</td>
                                                    <td class="input">
                                                        <select class="multiselect" multiple="" name="adminLevels">
                                                            <c:forEach items="${adminLevelMap}" var="entry">
                                                                <option value="${entry.key}">${entry.value.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td class="name">最高学历</td>
                                                    <td class="input">
                                                        <select class="multiselect" multiple="" name="maxEdus">
                                                            <c:forEach items="${eduMap}" var="entry">
                                                                <option value="${entry.key}">${entry.value.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td class="name">专业技术职务</td>
                                                    <td class="input">
                                                        <select class="multiselect" multiple="" name="proPosts">
                                                            <c:forEach items="${proPosts}" var="proPost">
                                                                <option value="${proPost}">${proPost}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="name">职务属性</td>
                                                    <td class="input">
                                                        <select class="multiselect" multiple="" name="postIds">
                                                            <c:forEach items="${postMap}" var="entry">
                                                                <option value="${entry.key}">${entry.value.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td class="name">现职务始任年限</td>
                                                    <td class="input">
                                                        <input class="num" type="text" name="startNowPostAge"
                                                               value="${param.startNowPostAge}"> 至 <input class="num"
                                                                                                          type="text"
                                                                                                          name="endNowPostAge"
                                                                                                          value="${param.endNowPostAge}">

                                                    </td>
                                                    <td class="name">专技岗位等级</td>
                                                    <td class="input">
                                                        <select class="multiselect" multiple="" name="proPostLevels">
                                                            <c:forEach items="${proPostLevels}" var="proPostLevel">
                                                                <option value="${proPostLevel}">${proPostLevel}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="name">是否正职</td>
                                                    <td class="input">
                                                        <select name="isPrincipalPost" data-width="100"
                                                                data-rel="select2" data-placeholder="请选择">
                                                            <option></option>
                                                            <option value="1">是</option>
                                                            <option value="0">否</option>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=isPrincipalPost]").val('${param.isPrincipalPost}');
                                                        </script>
                                                    </td>
                                                    <td class="name">现职级始任年限</td>
                                                    <td class="input">
                                                        <input class="num" type="text" name="startNowLevelAge"
                                                               value="${param.startNowLevelAge}"> 至 <input class="num"
                                                                                                           type="text"
                                                                                                           name="endNowLevelAge"
                                                                                                           value="${param.endNowLevelAge}">

                                                    </td>
                                                    <%--<td class="name">现职级始任年限 </td>
                                                    <td class="input">
                                                        <select name="age" data-width="150" data-rel="select2" data-placeholder="请选择">
                                                            <option></option>
                                                            <c:forEach items="${MEMBER_AGE_MAP}" var="age">
                                                                <option value="${age.key}">${age.value}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=age]").val('${param.age}');
                                                        </script>
                                                    </td>--%>
                                                    <td class="name">是否双肩挑</td>
                                                    <td class="input">
                                                        <select name="isDouble" data-width="100" data-rel="select2"
                                                                data-placeholder="请选择">
                                                            <option></option>
                                                            <option value="1">是</option>
                                                            <option value="0">否</option>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=isDouble]").val('${param.isDouble}');
                                                        </script>
                                                    </td>
                                                </tr>
                                            </table>

                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"><i
                                                        class="fa fa-search"></i> 查找</a>

                                                <c:if test="${_query || not empty param.sort}">&nbsp;
                                                    <button type="button" class="resetBtn btn btn-warning btn-sm"
                                                            data-querystr="status=${status}">
                                                        <i class="fa fa-reply"></i> 重置
                                                    </button>
                                                </c:if>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <div class="space-4"></div>

                            <table id="jqGrid" class="jqGrid table-striped"></table>
                            <div id="jqGridPager"></div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="item-content">

        </div>
    </div>
</div>
<style>
    #searchForm table {
        margin-bottom: 10px;
    }

    #searchForm table tr {
        height: 40px;
    }

    #searchForm .name {
        width: 120px;
        padding-right: 10px;
        text-align: right;
    }

    #searchForm .input {
        width: 250px;
        text-align: left;
    }

    #searchForm .num {
        width: 50px;
    }
</style>
<script type="text/template" id="sort_tpl">
<a href="javascript:;" class="jqOrderBtn" data-id="{{=id}}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
<input type="text" value="1" class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
<a href="javascript:;" class="jqOrderBtn" data-id="{{=id}}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>
</script>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script src="${ctx}/assets/js/bootstrap-multiselect.js"></script>
<link rel="stylesheet" href="${ctx}/assets/css/bootstrap-multiselect.css"/>
<script>
    register_multiselect($('#searchForm select[name=dpTypes]'), ${cm:toJSONArray(selectDpTypes)});
    register_multiselect($('#searchForm select[name=unitIds]'), ${cm:toJSONArray(selectUnitIds)}, {
        enableClickableOptGroups: true,
        enableCollapsibleOptGroups: true, collapsed: true, selectAllJustVisible: false
    });
    register_multiselect($('#searchForm select[name=unitTypes]'), ${cm:toJSONArray(selectUnitTypes)});
    register_multiselect($('#searchForm select[name=adminLevels]'), ${cm:toJSONArray(selectAdminLevels)});
    register_multiselect($('#searchForm select[name=maxEdus]'), ${cm:toJSONArray(selectMaxEdus)});
    register_multiselect($('#searchForm select[name=postIds]'), ${cm:toJSONArray(selectPostIds)});
    register_multiselect($('#searchForm select[name=proPosts]'), ${cm:toJSONArray(selectProPosts)});
    register_multiselect($('#searchForm select[name=proPostLevels]'), ${cm:toJSONArray(selectProPostLevels)});

    function _reAssignCallback() {
        $.hashchange('', '${ctx}/cadreInspect');
    }
    <c:if test="${status==CADRE_STATUS_MIDDLE}">
    $("#jqGrid").jqGrid({
        //forceFit:true,
        rownumbers: true,
        url: '${ctx}/cadre_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: ${cm:isPermitted("cadre:list")?"colModels.cadre":"colModels.cadre2"}
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $('[data-rel="tooltip"]').tooltip();
    });
    </c:if>

    <c:if test="${status!=CADRE_STATUS_MIDDLE}">
    $("#jqGrid").jqGrid({
        //forceFit:true,
        rownumbers: true,
        url: '${ctx}/cadre_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: ${cm:isPermitted("cadre:list")?"colModels.cadreLeave":"colModels.cadreLeave2"}
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $('[data-rel="tooltip"]').tooltip();
    });
    </c:if>
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $('[data-rel="select2"]').select2();
    register_user_select($('#searchForm select[name=cadreId]'));
</script>