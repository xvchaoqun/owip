<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/cisInspectObj"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.cadreId ||not empty param.year ||not empty param.typeId ||not empty param._inspectDate
                   ||not empty param.seq || not empty param.inspectorId}"/>
            <div class="tabbable">
                <div class="tab-content">
                    <div class="tab-pane in active rownumbers">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="cisInspectObj:edit">
                                <button class="popupBtn btn btn-success btn-sm" data-url="${ctx}/cisInspectObj_au"><i
                                        class="fa fa-plus"></i> 添加</button>
                                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/cisInspectObj_au"
                                   data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                    修改</button>
                                <button id="inspectorEditBtn" class="jqOpenViewBtn btn btn-info btn-sm"
                                   data-url="${ctx}/cisObjInspectors"
                                   data-grid-id="#jqGrid"
                                   data-id-name="objId"><i class="fa fa-edit"></i>
                                    编辑考察组成员</button>
                                <button class="jqOpenViewBtn btn btn-warning btn-sm"
                                   data-url="${ctx}/cisInspectObj_report" data-id-name="objId"
                                   data-grid-id="#jqGrid"><i class="fa fa-hand-stop-o"></i>
                                    考察期间有举报</button>
                                <button class="jqOpenViewBtn btn btn-success btn-sm"
                                   data-url="${ctx}/cisInspectObj_reuse" data-id-name="objId" data-width="1050"
                                   data-grid-id="#jqGrid"><i class="fa fa-check-circle-o"></i>
                                    考察复用</button>
                            </shiro:hasPermission>
                            <a class="jqExportBtn btn btn-primary btn-sm tooltip-success"
                               data-url="${ctx}/cisInspectObj_data"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-download"></i> 导出工作安排</a>
                            <shiro:hasPermission name="cisInspectObj:del">
                                <button data-url="${ctx}/cisInspectObj_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
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
                                        <input type="hidden" name="cls" value="${cls}">
                                        <div class="form-group">
                                            <label>年份</label>
                                            <div class="input-group">
                                                    <span class="input-group-addon"> <i
                                                            class="fa fa-calendar bigger-110"></i></span>
                                                <input class="form-control date-picker" placeholder="请选择年份"
                                                       name="year" type="text"
                                                       data-date-format="yyyy" data-date-min-view-mode="2"
                                                       value="${param.year}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>考察类型</label>
                                            <select required data-rel="select2" name="typeId" data-placeholder="请选择">
                                                <option></option>
                                                <c:import url="/metaTypes?__code=mc_cis_type"/>
                                            </select>
                                            <script type="text/javascript">
                                                $("#searchForm select[name=typeId]").val(${param.typeId});
                                            </script>
                                        </div>
                                        <div class="form-group">
                                            <label>编号</label>
                                            <input class="form-control search-query" name="seq" type="text"
                                                   value="${param.seq}"
                                                   placeholder="请输入编号">
                                        </div>
                                        <div class="form-group">
                                            <label>考察对象</label>
                                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects?type=0"
                                                    name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>考察日期</label>
                                            <div class="input-group tooltip-success" data-rel="tooltip"
                                                 title="考察日期范围">
                                                    <span class="input-group-addon"><i
                                                            class="fa fa-calendar bigger-110"></i></span>
                                                <input placeholder="请选择考察日期范围" data-rel="date-range-picker"
                                                       class="form-control date-range-picker" type="text"
                                                       name="_inspectDate" value="${param._inspectDate}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>考察组成员</label>
                                            <select required data-rel="select2" name="inspectorId" data-placeholder="请选择"  data-width="270">
                                                <option></option>
                                                <c:forEach var="i" items="${inspectors}">
                                                    <option value="${i.id}">${i.user.realname}-${i.user.code}</option>
                                                </c:forEach>
                                            </select>
                                            <script type="text/javascript">
                                                $("#searchForm select[name=inspectorId]").val(${param.inspectorId});
                                            </script>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm" data-querystr="cls=${cls}">
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
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<jsp:include page="/WEB-INF/jsp/cadre/colModels.jsp"/>
<script>
    $.register.date($('.date-picker'));
    $.register.user_select($('#searchForm select[name=cadreId]'))
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/cisInspectObj_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel:colModels.cisInspectObj, onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id, id, status);
            //console.log(id)
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#inspectorEditBtn").prop("disabled", true);
            } else if (ids.length == 1) {
                var rowData = $(this).getRowData(ids[0]);
                //console.log(rowData.inspectorType)
                $("#inspectorEditBtn").prop("disabled", rowData.inspectorType=='<%=CisConstants.CIS_INSPECTOR_TYPE_OTHER%>')
            } else {
                $("#inspectorEditBtn").prop("disabled", false);
            }
        }
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>