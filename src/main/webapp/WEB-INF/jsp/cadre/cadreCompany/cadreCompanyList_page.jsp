<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv multi-row-head-table"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.cadreId ||not empty param.type
             ||not empty param.unit ||not empty param.post ||not empty param.startTime
              ||not empty param.finishTime||not empty param.approvalUnit ||not empty param.hasPay  || not empty param.sort}"/>

            <jsp:include page="menu.jsp"/>
            <div class="space-4"></div>
            <div class="jqgrid-vertical-offset buttons">

                <shiro:hasPermission name="dispatchWorkFile:list">
                    <button class="popupBtn btn btn-warning btn-sm " data-width="750"
                            data-url="${ctx}/cadreCompanyFiles?type=${module==1?1:0}"><i class="fa fa-files-o"></i>
                            ${module==1?'校领导兼职管理文件':'干部兼职管理文件'}
                    </button>
                </shiro:hasPermission>
                <c:if test="${cls==1}">
                    <c:if test="${module==1}">
                        <div class="btn-group">
                            <button data-toggle="dropdown"
                                    data-rel="tooltip" data-placement="top" data-html="true"
                                    title="<div style='width:180px'>导出选中记录或所有搜索结果</div>"
                                    class="btn btn-info btn-sm dropdown-toggle tooltip-info">
                                <i class="fa fa-download"></i> 导出 <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu dropdown-success" role="menu">
                                <li>
                                    <a href="javascript:;" class="jqExportBtn"
                                       data-need-id="false" data-url="${ctx}/cadreCompany_data"
                                       data-querystr="module=${module}&cadreStatus=<%=CadreConstants.CADRE_STATUS_LEADER%>">
                                        <i class="fa fa-file-excel-o"></i> 导出现任校领导</a>
                                </li>
                                <li>
                                    <a href="javascript:;" class="jqExportBtn"
                                       data-need-id="false" data-url="${ctx}/cadreCompany_data"
                                       data-querystr="module=${module}&cadreStatus=<%=CadreConstants.CADRE_STATUS_LEADER_LEAVE%>">
                                        <i class="fa fa-file-excel-o"></i> 导出离任校领导</a>
                                </li>
                            </ul>
                        </div>
                    </c:if>
                    <c:if test="${module==2}">
                         <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                        <shiro:hasPermission name="cadreCompany:import">
                            <button class="popupBtn btn btn-info btn-sm tooltip-info"
                                    data-url="${ctx}/cadreCompany_import"
                                    data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                                批量导入
                            </button>
                        </shiro:hasPermission>
                        </shiro:lacksPermission>
                        <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                           data-url="${ctx}/cadreCompany_data"
                           data-querystr="module=${module}"
                           data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果">
                            <i class="fa fa-download"></i> 导出</a>
                    </c:if>
                </c:if>
                <c:if test="${cls==1}">
                    <span class="text-primary" style="padding-left: 10px">【注：如需单个添加兼职，请进入干部档案页操作】</span>
                </c:if>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>
                    <div class="widget-toolbar">
                        <a href="javascript:;" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm">
                            <div class="form-group">
                                <label>所属干部</label>
                                <c:set var="cadre" value="${cm:getCadreById(param.cadreId)}"/>
                                <select data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/cadre_selects?types=${CADRE_STATUS_MIDDLE},${CADRE_STATUS_LEADER},${CADRE_STATUS_MIDDLE_LEAVE},${CADRE_STATUS_LEADER_LEAVE}"
                                        name="cadreId" data-placeholder="请输入账号或姓名或教工号">
                                    <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>兼职类型</label>
                                <select data-rel="select2" name="type"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <c:import url="/metaTypes?__code=mc_cadre_company_type"/>
                                </select>
                                <script type="text/javascript">
                                    $("#searchForm select[name=type]").val(${param.type});
                                </script>
                            </div>
                            <div class="form-group">
                                <label>兼职单位</label>
                                <input class="form-control" type="text" name="unit"
                                       value="${param.unit}">
                            </div>
                            <div class="form-group">
                                <label>兼任职务</label>
                                <input class="form-control" type="text" name="post"
                                       value="${param.post}">
                            </div>
                            <div class="form-group">
                                <label>兼职起始时间</label>
                                <div class="input-group" style="width: 110px">
                                    <input class="form-control date-picker" name="startTime" type="text"
                                           data-date-min-view-mode="1"
                                           data-date-format="yyyy.mm"
                                           value="${param.startTime}"/>
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>兼职结束时间</label>
                                <div class="input-group" style="width: 110px">
                                    <input class="form-control date-picker" name="finishTime" type="text"
                                           data-date-min-view-mode="1"
                                           data-date-format="yyyy.mm"
                                           value="${param.finishTime}"/>
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>审批单位</label>
                                <input class="form-control" type="text" name="approvalUnit"
                                       value="${param.approvalUnit}">
                            </div>
                            <div class="form-group">
                                <label>是否取酬</label>
                                <select name="hasPay" data-width="80"
                                        data-rel="select2" data-placeholder="请选择">
                                    <option></option>
                                    <option value="1">是</option>
                                    <option value="0">否</option>
                                </select>
                                <script>
                                    $("#searchForm select[name=hasPay]").val('${param.hasPay}');
                                </script>
                            </div>
                            <div class="clearfix form-actions center">
                                 <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/cadreCompanyList?cls=${cls}&module=${module}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cadreCompanyList?cls=${cls}&module=${module}"
                                            data-target="#page-content">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <%--<div class="space-4"></div>--%>
            <table id="jqGrid" class="jqGrid"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/cadre/cadreCompany/colModels.jsp?type=list"/>
<script>
    function _reload2() {
        $("#jqGrid").trigger("reloadGrid");
    }

    $("#jqGrid").jqGrid({
        //forceFit:true,
        pager: "#jqGridPager",
        url: '${ctx}/cadreCompany_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels_cadreCompany
    }).jqGrid("setFrozenColumns");

    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $.register.date($('.date-picker'));
</script>