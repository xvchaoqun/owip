<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers multi-row-head-table"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <jsp:include page="menu.jsp"/>
            <div class="space-4"></div>
            <c:set var="_query" value="${not empty param.unitId ||not empty param.name
            ||not empty param.isPrincipal||not empty param.leaderType||not empty param.isMainPost ||not empty param.isCpc  ||not empty param.cadreId
            ||not empty param.adminLevel ||not empty param.postType ||not empty param.postClass
            ||not empty param.unitTypes||not empty param.adminLevels
            ||not empty param.startNowPostAge||not empty param.endNowPostAge||not empty param.startNowLevelAge||not empty param.endNowLevelAge
            || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="unitPost:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-width="900"
                            data-url="${ctx}/unitPost_au?jqGrid=jqGrid">
                        <i class="fa fa-plus"></i> 添加
                    </button>
                     <button class="jqOpenViewBtn btn btn-primary btn-sm"
                             data-width="900"
                        data-url="${ctx}/unitPost_au?jqGrid=jqGrid"
                        data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                         修改</button>
                  <%--   <button class="jqOpenViewBtn btn btn-warning btn-sm"
                            data-width="600"
                            data-url="${ctx}/unitPost_group?jqGrid=jqGrid"
                            data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        关联岗位分组</button>--%>
                 </shiro:hasPermission>
                <c:if test="${cls==1}">
                <div class="type-select">
                        <span class="typeCheckbox ${param.displayType==1?"checked":""}">
                        <input ${param.displayType==1?"checked":""} type="checkbox"
                                                                     value="1"> 只显示空缺岗位
                        </span>
                </div>
                <shiro:hasPermission name="unitPost:edit">
                    <button class="popupBtn btn btn-info btn-sm tooltip-info"
                            data-url="${ctx}/unitPost_import"
                            data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                        批量导入
                    </button>
                </shiro:hasPermission>
                </c:if>
                <shiro:hasPermission name="unitPost:export">
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                        data-url="${ctx}/unitPost_data"
                        data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出
                </button>
                    </shiro:hasPermission>
                <shiro:hasPermission name="unitPost:edit">
                <div class="btn-group">
                    <button data-toggle="dropdown"
                            data-rel="tooltip" data-placement="top" data-html="true"
                            title="<div style='width:180px'>按指定条件进行批量排序</div>"
                            class="btn btn-success btn-sm dropdown-toggle tooltip-success">
                        <i class="fa fa-sort"></i> 批量排序 <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu dropdown-success" role="menu">
                        <li>
                            <a href="javascript:;" class="runBtn"
                                    data-url="${ctx}/unitPost_sortByCode?asc=1"
                                    data-callback="_callback_sortByCode">
                                <i class="fa fa-sort-alpha-asc"></i> 按每个单位的岗位编号排序（升序）
                            </a>
                        </li>
                        <li role="separator" class="divider"></li>
                        <li>
                            <a href="javascript:;" class="runBtn"
                                    data-url="${ctx}/unitPost_sortByCode?asc=0"
                                    data-callback="_callback_sortByCode">
                                <i class="fa fa-sort-alpha-desc"></i> 按每个单位的岗位编号排序（降序）
                            </a>
                        </li>
                    </ul>
                </div>
                <shiro:hasRole name="${ROLE_SUPER}">
                    <button class="popupBtn btn btn-info btn-sm tooltip-info"
                            data-url="${ctx}/unitPost_collectUnitName"
                            data-rel="tooltip" data-placement="top" title="根据岗位名称提取单位名称"><i class="fa fa-upload"></i>
                        提取单位名称
                    </button>
                </shiro:hasRole>
                 <button data-url="${ctx}/unitPost_batchDel"
                         data-title="删除"
                         data-msg="确定删除这{0}个岗位？<br/>（删除后不可恢复，请谨慎操作！）"
                         data-grid-id="#jqGrid"
                         class="jqBatchBtn btn btn-danger btn-sm">
                     <i class="fa fa-trash"></i> 删除
                 </button>
                <c:if test="${cls==1}">
                <span class="text-primary" style="padding-left: 10px">【注：岗位的维护也可在单位档案页中进行操作】</span>
                </c:if>
                </shiro:hasPermission>
            </div>
            <div style="clear: both"></div>
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
                            <div class="form-group">
                                <label>岗位编号</label>
                                <input class="form-control search-query" name="code" type="text" value="${param.code}"
                                       placeholder="请输入岗位编号">
                            </div>
                            <div class="form-group">
                                <label>岗位名称</label>
                                <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                       placeholder="请输入岗位名称">
                            </div>
                            <div class="form-group">
                                <label>所属单位</label>
                                <select name="unitId" data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                                        data-placeholder="请选择所属内设机构">
                                    <option value="${unit.id}" delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                                </select>
                                <script>
                                    $.register.del_select($("#searchForm select[name=unitId]"), 250)
                                </script>
                            </div>
                            <div class="form-group">
                                <label>单位类型</label>
                                <select class="multiselect" multiple="" name="unitTypes">
                                    <c:import url="/metaTypes?__code=mc_unit_type"/>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>是否正职岗位</label>
                                <select name="isPrincipal" data-width="100"
                                        data-rel="select2" data-placeholder="请选择">
                                    <option></option>
                                    <option value="1">是</option>
                                    <option value="0">否</option>
                                </select>
                                <script>
                                    $("#searchForm select[name=isPrincipal]").val('${param.isPrincipal}');
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
                                <label>岗位级别</label>
                                <select  data-rel="select2" name="adminLevel" data-width="272" data-placeholder="请选择">
                                    <option></option>
                                    <jsp:include page="/metaTypes?__code=mc_admin_level"/>
                                </select>
                                <script type="text/javascript">
                                    $("#searchForm select[name=adminLevel]").val('${param.adminLevel}');
                                </script>
                            </div>
                            <div class="form-group">
                                <label>职务属性</label>
                                <select name="postType" data-rel="select2" data-placeholder="请选择职务属性">
                                    <option></option>
                                    <c:import url="/metaTypes?__code=mc_post"/>
                                </select>
                                <script>
                                    $("#searchForm select[name=postType]").val('${param.postType}');
                                </script>
                            </div>
                            <div class="form-group">
                                <label>职务类别</label>
                                <select data-rel="select2" name="postClass" data-width="100"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <c:import url="/metaTypes?__code=mc_post_class"/>
                                </select>
                                <script type="text/javascript">
                                    $("#searchForm select[name=postClass]").val(${param.postClass});
                                </script>
                            </div>
                            <div class="form-group">
                                <label>是否占干部职数</label>
                                <select name="isCpc" data-width="100"
                                        data-rel="select2" data-placeholder="请选择">
                                    <option></option>
                                    <option value="1">是</option>
                                    <option value="0">否</option>
                                </select>
                                <script>
                                    $("#searchForm select[name=isCpc]").val('${param.isCpc}');
                                </script>
                            </div>
                            <div class="form-group">
                                <label>任职类型</label>
                                <select name="isMainPost" data-width="100"
                                        data-rel="select2" data-placeholder="请选择">
                                    <option></option>
                                    <option value="1">主职</option>
                                    <option value="0">兼职</option>
                                </select>
                                <script>
                                    $("#searchForm select[name=isMainPost]").val('${param.isMainPost}');
                                </script>
                            </div>
                            <div class="form-group">
                                <label>现任职干部</label>
                                <select data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/cadre_selects?status=${status}"
                                        name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>干部级别</label>
                                <select class="multiselect" multiple="" name="adminLevels">
                                    <c:import url="/metaTypes?__code=mc_admin_level"/>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>现职务始任年限</label>
                                <input class="num" type="text" name="startNowPostAge" style="width: 50px"
                                       value="${param.startNowPostAge}"> 至 <input class="num"
                                                                                  type="text" style="width: 50px"
                                                                                  name="endNowPostAge"
                                                                                  value="${param.endNowPostAge}">

                            </div>
                            <%--<div class="form-group">
                                <label>现职级始任年限</label>
                                <input class="num" type="text" name="startNowLevelAge" style="width: 50px"
                                       value="${param.startNowLevelAge}"> 至 <input class="num"
                                                                                   type="text" style="width: 50px"
                                                                                   name="endNowLevelAge"
                                                                                   value="${param.endNowLevelAge}">

                            </div>--%>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/unitPost?cls=${cls}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/unitPost?cls=${cls}"
                                            data-target="#page-content">
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
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="colModel.jsp?list=0"/>
<script type="text/template" id="dispatch_select_tpl">
    <button class="popupBtn btn {{=(count>0)?'btn-warning':'btn-success'}} btn-xs"
            data-url="${ctx}/cadrePost_addDispatchs?id={{=id}}&cadreId={{=cadreId}}"
            data-width="1000"><i class="fa fa-link"></i>
        任免文件({{=count}})
    </button>
</script>
<script>
    function _callback_sortByCode(){
        $("#jqGrid").trigger("reloadGrid");
    }
    $(":checkbox", ".typeCheckbox").click(function () {
        $("#searchForm input[name=displayType]").val($(this).prop("checked") ? 1 : 0);
        $("#searchForm .jqSearchBtn").click();
    })

    $.register.multiselect($('#searchForm select[name=unitTypes]'), ${cm:toJSONArray(selectUnitTypes)});
    $.register.multiselect($('#searchForm select[name=adminLevels]'), ${cm:toJSONArray(selectAdminLevels)});
    $.register.user_select($('#searchForm select[name=cadreId]'));
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/unitPost_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>