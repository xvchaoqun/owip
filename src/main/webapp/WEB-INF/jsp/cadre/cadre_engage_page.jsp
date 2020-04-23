<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${empty _pMap['label_staffType']?'个人身份':_pMap['label_staffType']}" var="_p_label_staffType"/>
<jsp:include page="/WEB-INF/jsp/cadre/colModels.jsp"/>
<c:set var="ROLE_SUPER" value="<%=RoleConstants.ROLE_SUPER%>"/>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
            <div class="myTableDiv"
                 data-url-page="${ctx}/cadre"
                 data-url-co="${ctx}/cadre_changeOrder"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.cadreId ||not empty param.gender ||not empty param.nation
                ||not empty param.startAge||not empty param.endAge||not empty param.startDpAge||not empty param.endDpAge
                ||not empty param.startNowPostAge||not empty param.endNowPostAge||not empty param.startNowLevelAge||not empty param.endNowLevelAge
                ||not empty param._birth||not empty param._cadreGrowTime
                ||not empty param.dpTypes||not empty param.unitIds||not empty param.unitTypes||not empty param.adminLevels
                ||not empty param.maxEdus ||not empty param.degreeType
                ||not empty param.proPosts ||not empty param.postTypes ||not empty param.proPostLevels
                ||not empty param.isPrincipal ||not empty param.isDouble ||not empty param.hasCrp || not empty param.code
                ||not empty param.leaderTypes  ||not empty param.type  ||not empty param.isDep
                 ||not empty param.state  ||not empty param.title ||not empty param.labels }"/>

                <div class="tabbable">
                    <div class="tab-content multi-row-head-table">
                        <div class="tab-pane in active rownumbers">
                            <div class="jqgrid-vertical-offset widget-box collapsed hidden-sm hidden-xs">
                                <div class="widget-header">
                                    <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

                                    <div class="widget-toolbar">
                                        <a href="javascript:;" data-action="collapse">
                                            <i class="ace-icon fa fa-chevron-down"></i>
                                        </a>
                                    </div>
                                </div>
                                <div class="widget-body">
                                    <div class="widget-main no-padding">
                                        <form class="form-inline search-form" id="searchForm">
                                            <input type="hidden" name="cols">
                                            <input type="hidden" name="sortBy">
                                            <input type="hidden" name="isKeepSalary" value="${param.isKeepSalary}" />
                                            <input type="hidden" name="isEngage" value="${param.isEngage}" />
                                            <table>
                                                <tr>
                                                    <td class="name">姓名</td>
                                                    <td class="input">
                                                        <input type="hidden" name="status" value="${status}">
                                                        <select data-rel="select2-ajax"
                                                                data-ajax-url="${ctx}/cadre_selects?status=${status}"
                                                                name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                            <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                                                        </select>
                                                    </td>
                                                    <td class="name">性别</td>
                                                    <td class="input">
                                                        <select name="gender" data-width="100" data-rel="select2"
                                                                data-placeholder="请选择">
                                                            <option></option>
                                                            <option value="<%=SystemConstants.GENDER_MALE%>">男</option>
                                                            <option value="<%=SystemConstants.GENDER_FEMALE%>">女
                                                            </option>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=gender]").val('${param.gender}');
                                                        </script>
                                                    </td>
                                                    <td class="name">政治面貌</td>
                                                    <td class="input">
                                                        <select class="multiselect" multiple="" name="dpTypes"
                                                                style="width: 250px;">
                                                            <option value="-1">非中共党员</option>
                                                            <option value="0">中共党员</option>
                                                            <c:import url="/metaTypes?__code=mc_democratic_party"/>
                                                           <shiro:hasRole name="${ROLE_SUPER}">
                                                            <option value="-2">空</option>
                                                           </shiro:hasRole>
                                                        </select>
                                                    </td>
                                                    <c:if test="${fn:length(staffTypes)>0}">
                                                    <td class="name">${_p_label_staffType}</td>
                                                    <td class="input">
                                                        <select class="multiselect" multiple="" name="staffTypes">
                                                            <c:forEach items="${staffTypes}" var="staffType">
                                                                <option value="${staffType}">${staffType}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td class="name">民族</td>
                                                    <td class="input">
                                                        <select class="multiselect" multiple="" name="nation">
                                                            <c:forEach var="nation" items="${cm:getMetaTypes('mc_nation').values()}">
                                                                <option value="${nation.name}">${nation.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td class="name">所在单位及职务</td>
                                                    <td class="input">
                                                        <input type="text" style="width: 200px" name="title"
                                                               value="${param.title}">
                                                    </td>
                                                    <c:if test="${cm:getMetaTypes('mc_cadre_label').size()>0}">
                                                    <td class="name">干部标签</td>
                                                    <td class="input">
                                                        <select class="multiselect" multiple="" name="labels"
                                                                data-placeholder="请选择">
                                                            <c:import url="/metaTypes?__code=mc_cadre_label"/>
                                                        </select>
                                                    </td>
                                                        </c:if>
                                                    <c:if test="${_p_useCadreState}">
                                                        <td class="name">${_pMap['cadreStateName']}</td>
                                                        <td class="input">
                                                            <select data-rel="select2" data-width="100" name="state"
                                                                    data-placeholder="请选择">
                                                                <option></option>
                                                                <c:import url="/metaTypes?__code=mc_cadre_state"/>
                                                            </select>
                                                            <script type="text/javascript">
                                                                $("#searchForm select[name=state]").val(${param.state});
                                                            </script>
                                                        </td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <c:if test="${fn:length(authorizedTypes)>0}">
                                                    <td class="name">编制类别</td>
                                                    <td class="input">
                                                        <select class="multiselect" multiple="" name="authorizedTypes">
                                                            <c:forEach items="${authorizedTypes}" var="authorizedType">
                                                                <option value="${authorizedType}">${authorizedType}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                        </c:if>
                                                    <td class="name">部门属性</td>
                                                    <td class="input">
                                                        <select class="multiselect" multiple="" name="unitTypes">
                                                            <c:import url="/metaTypes?__code=mc_unit_type"/>
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
                                                            <c:forEach var="unitType"
                                                                       items="${cm:getMetaTypes('mc_unit_type')}">
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
                                                    <td class="name">最高学历</td>
                                                    <td class="input">
                                                        <select class="multiselect" multiple="" name="maxEdus">
                                                            <c:import url="/metaTypes?__code=mc_edu"/>
                                                            <option value="-1">无(仅查询无最高学历的干部)</option>
                                                        </select>
                                                    </td>
                                                    <td class="name">最高学位</td>
                                                    <td class="input">
                                                        <select data-rel="select2" data-placeholder="请选择"
                                                                name="degreeType">
                                                            <option></option>
                                                            <c:forEach items="<%=SystemConstants.DEGREE_TYPE_MAP%>"
                                                                       var="_type">
                                                                <option value="${_type.key}">${_type.value}</option>
                                                            </c:forEach>
                                                            <option value="-1">无</option>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=degreeType]").val('${param.degreeType}');
                                                        </script>
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
                                                        <select class="multiselect" multiple="" name="postTypes">
                                                            <c:import url="/metaTypes?__code=mc_post"/>
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
                                                    <td class="name">职称级别</td>
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
                                                        <select name="isPrincipal" data-width="100"
                                                                data-rel="select2" data-placeholder="请选择">
                                                            <option></option>
                                                            <option value="1">是</option>
                                                            <option value="0">否</option>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=isPrincipal]").val('${param.isPrincipal}');
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
                                                <tr>
                                                    <td class="name">是否班子负责人</td>
                                                    <td class="input">
                                                        <select class="multiselect" multiple="" name="leaderTypes">
                                                            <c:forEach
                                                                    items="<%=SystemConstants.UNIT_POST_LEADER_TYPE_MAP%>"
                                                                    var="leaderType">
                                                                <option value="${leaderType.key}">${leaderType.value}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <c:if test="${_p_hasKjCadre}">
                                                        <td class="name">干部类型</td>
                                                        <td class="input">
                                                            <select name="type" data-width="150" data-rel="select2"
                                                                    data-placeholder="请选择">
                                                                <option></option>
                                                                <c:forEach items="<%=CadreConstants.CADRE_TYPE_MAP%>"
                                                                           var="_type">
                                                                    <option value="${_type.key}">${_type.value}</option>
                                                                </c:forEach>
                                                            </select>
                                                            <script>
                                                                $("#searchForm select[name=type]").val('${param.type}');
                                                            </script>
                                                        </td>
                                                    </c:if>
                                                    <td class="name">干部类别</td>
                                                    <td class="input">
                                                        <select name="isDep" data-width="150" data-rel="select2"
                                                                data-placeholder="请选择">
                                                            <option></option>
                                                            <option value="1">院系干部</option>
                                                            <option value="0">机关干部</option>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=isDep]").val('${param.isDep}');
                                                        </script>
                                                    </td>

                                                    <td class="name">是否有挂职经历</td>
                                                    <td class="input">
                                                        <select name="hasCrp" data-width="100" data-rel="select2"
                                                                data-placeholder="请选择">
                                                            <option></option>
                                                            <option value="1">是</option>
                                                            <option value="0">否</option>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=hasCrp]").val('${param.hasCrp}');
                                                        </script>
                                                    </td>
                                                </tr>
                                            </table>
                                            <div>
                                            </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"><i
                                                        class="fa fa-search"></i> 查找</a>

                                                <c:if test="${_query || not empty param.sort}">&nbsp;
                                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                            data-querystr="status=${status}&isKeepSalary=${param.isKeepSalary}&isEngage=${param.isEngage}">
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
        <div id="body-content-view">

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
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>

    $.register.multiselect($('#searchForm select[name=nation]'), ${cm:toJSONArray(selectNations)});
    $.register.multiselect($('#searchForm select[name=dpTypes]'), ${cm:toJSONArray(selectDpTypes)});
    $.register.multiselect($('#searchForm select[name=unitIds]'), ${cm:toJSONArray(selectUnitIds)}, {
        enableClickableOptGroups: true,
        enableCollapsibleOptGroups: true, collapsed: true, selectAllJustVisible: false
    });
    $.register.multiselect($('#searchForm select[name=unitTypes]'), ${cm:toJSONArray(selectUnitTypes)});
    $.register.multiselect($('#searchForm select[name=adminLevels]'), ${cm:toJSONArray(selectAdminLevels)});
    $.register.multiselect($('#searchForm select[name=maxEdus]'), ${cm:toJSONArray(selectMaxEdus)});
    $.register.multiselect($('#searchForm select[name=postTypes]'), ${cm:toJSONArray(selectPostTypes)});
    $.register.multiselect($('#searchForm select[name=proPosts]'), ${cm:toJSONArray(selectProPosts)});
    $.register.multiselect($('#searchForm select[name=proPostLevels]'), ${cm:toJSONArray(selectProPostLevels)});
    $.register.multiselect($('#searchForm select[name=leaderTypes]'), ${cm:toJSONArray(selectLeaderTypes)});
    $.register.multiselect($('#searchForm select[name=labels]'), ${cm:toJSONArray(selectLabels)});
    $.register.multiselect($('#searchForm select[name=staffTypes]'), ${cm:toJSONArray(selectStaffTypes)});
    $.register.multiselect($('#searchForm select[name=authorizedTypes]'), ${cm:toJSONArray(selectAuthorizedTypes)});

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
    $.register.user_select($('#searchForm select[name=cadreId]'));
</script>