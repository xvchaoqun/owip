<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=CrpConstants.CRP_RECORD_TYPE_OUT%>" var="CRP_RECORD_TYPE_OUT"/>
<c:set value="<%=CrpConstants.CRP_RECORD_TYPE_IN%>" var="CRP_RECORD_TYPE_IN"/>
<c:set value="<%=CrpConstants.CRP_RECORD_TYPE_TRANSFER%>" var="CRP_RECORD_TYPE_TRANSFER"/>
<c:if test="${param.type==CRP_RECORD_TYPE_OUT}">
    <c:set var="unitCode" value="mc_temppost_out_unit"/>
    <c:set var="postCode" value="mc_temppost_out_post"/>
    <c:set var="unitCodeOther" value="mt_temppost_out_unit_other"/>
    <c:set var="postCodeOther" value="mt_temppost_out_post_other"/>
</c:if>
<c:if test="${param.type==CRP_RECORD_TYPE_IN}">
    <c:set var="unitCode" value="mc_temppost_in_unit"/>
    <c:set var="postCode" value="mc_temppost_in_post"/>
    <c:set var="unitCodeOther" value="mt_temppost_in_unit_other"/>
    <c:set var="postCodeOther" value="mt_temppost_in_post_other"/>
</c:if>
<c:if test="${param.type==CRP_RECORD_TYPE_TRANSFER}">
    <c:set var="unitCode" value="mc_temppost_transfer_unit"/>
    <c:set var="postCode" value="mc_temppost_transfer_post"/>
    <c:set var="unitCodeOther" value="mt_temppost_transfer_unit_other"/>
    <c:set var="postCodeOther" value="mt_temppost_transfer_post_other"/>
</c:if>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/crpRecord"
             data-url-export="${ctx}/crpRecord_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.userId  ||not empty param.realname
                   ||not empty param.isPresentCadre ||not empty param.toUnitType
                   ||not empty param.unit||not empty param.unitId||not empty param.startDate
                   ||not empty param.tempPostType || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${!isFinished}">active</c:if>">
                        <a href="javascript:;" class="loadPage"
                           data-url="${ctx}/crpRecord?type=${param.type}&isFinished=0"><i
                                class="fa fa-circle-o-notch fa-spin"></i> 正在挂职</a>
                    </li>
                    <li class="<c:if test="${isFinished}">active</c:if>">
                        <a href="javascript:;" class="loadPage"
                           data-url="${ctx}/crpRecord?type=${param.type}&isFinished=1"><i class="fa fa-history"></i>
                            挂职结束</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="crpRecord:edit">
                                <a class="popupBtn btn btn-info btn-sm" data-width="900"
                                   data-url="${ctx}/crpRecord_au?type=${param.type}&isFinished=${isFinished?1:0}"><i
                                        class="fa fa-plus"></i>
                                    添加</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm" data-width="900"
                                   data-url="${ctx}/crpRecord_au"
                                   data-grid-id="#jqGrid"
                                   data-querystr="&type=${param.type}&isFinished=${isFinished?1:0}"><i
                                        class="fa fa-edit"></i>
                                    修改</a>
                            </shiro:hasPermission>
                            <c:if test="${!isFinished}">
                                <a class="jqOpenViewBtn btn btn-warning btn-sm"
                                   data-url="${ctx}/crpRecord_finish"
                                   data-grid-id="#jqGrid"><i class="fa fa-power-off"></i>
                                    挂职结束</a>
                            </c:if>
                            <shiro:hasPermission name="crpRecord:del">
                                <button data-url="${ctx}/crpRecord_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
                            <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-download"></i> 导出</a>
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
                                        <input name="type" type="hidden" value="${param.type}"/>
                                        <input name="isFinished" type="hidden" value="${isFinished}"/>

                                        <div class="form-group">
                                            <label>姓名</label>
                                            <c:if test="${param.type!=CRP_RECORD_TYPE_TRANSFER}">
                                                <select data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                                                        name="userId" data-placeholder="请输入账号或姓名或教工号">
                                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                </select>
                                            </c:if>
                                            <c:if test="${param.type==CRP_RECORD_TYPE_TRANSFER}">
                                                <input class="form-control search-query" name="realname" type="text"
                                                       value="${param.realname}"
                                                       placeholder="请输入姓名">
                                            </c:if>
                                        </div>
                                        <c:if test="${param.type!=CRP_RECORD_TYPE_TRANSFER}">
                                            <div class="form-group">
                                                <label>是否现任干部</label>
                                                <select name="isPresentCadre" data-width="100" data-rel="select2"
                                                        data-placeholder="请选择">
                                                    <option></option>
                                                    <option value="1">是</option>
                                                    <option value="0">否</option>
                                                </select>
                                                <script>
                                                    $("#searchForm select[name=isPresentCadre]").val('${param.isPresentCadre}');
                                                </script>
                                            </div>
                                        </c:if>
                                        <c:if test="${param.type!=CRP_RECORD_TYPE_OUT}">
                                            <div class="form-group">
                                                <label>挂职单位</label>
                                                <select name="unitId" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/unit_selects"
                                                        data-placeholder="请选择">
                                                    <option value="${unit.id}"
                                                            title="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                                                </select>
                                                <script>
                                                    $.register.del_select($("#searchForm select[name=unitId]"), 200)
                                                </script>
                                            </div>
                                        </c:if>
                                        <c:if test="${param.type==CRP_RECORD_TYPE_OUT}">
                                            <div class="form-group">
                                                <label>挂职单位</label>
                                                <input class="form-control search-query" name="unit" type="text"
                                                       value="${param.unit}"
                                                       placeholder="请输入挂职单位">
                                            </div>
                                        </c:if>
                                        <div class="form-group">
                                            <label>挂职时间</label>
                                            <div class="input-group tooltip-success" data-rel="tooltip" title="挂职时间范围">
                                                <span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>
                                                <input placeholder="请选择挂职时间范围" data-rel="date-range-picker"
                                                       class="form-control date-range-picker" type="text"
                                                       name="startDate" value="${param.startDate}"/>
                                            </div>
                                        </div>
                                        <c:if test="${param.type!=CRP_RECORD_TYPE_IN}">
                                            <div class="form-group">
                                                <label>委派单位</label>
                                                <select required data-rel="select2" name="toUnitType"
                                                        data-placeholder="请选择">
                                                    <option></option>
                                                    <c:import url="/metaTypes?__code=${unitCode}"/>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#searchForm select[name=toUnitType]").val(${param.toUnitType});
                                                </script>
                                            </div>
                                        </c:if>
                                        <div class="form-group">
                                            <label>挂职类别</label>
                                            <select required data-rel="select2" name="tempPostType"
                                                    data-placeholder="请选择">
                                                <option></option>
                                                <c:import url="/metaTypes?__code=${postCode}"/>
                                            </select>
                                            <script type="text/javascript">
                                                $("#searchForm select[name=tempPostType]").val(${param.tempPostType});
                                            </script>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-querystr="type=${param.type}&isFinished=${isFinished}">
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
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/crpRecord_data?type=${param.type}&isFinished=${isFinished}&callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            <c:if test="${param.type!=CRP_RECORD_TYPE_TRANSFER}">
            {label: '工作证号', name: 'user.code', width: 110, frozen: true},
            </c:if>
            {
                label: '姓名', name: 'realname', width: 120, formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.type == '${CRP_RECORD_TYPE_TRANSFER}') {
                        return cellvalue;
                    }
                    if (rowObject.cadre && rowObject.cadre.id > 0)
                        return $.cadre(rowObject.cadre.id, rowObject.cadre.realname);

                    return rowObject.user.realname;
                }, frozen: true
            },
            <c:if test="${param.type!=CRP_RECORD_TYPE_TRANSFER}">
            {label: '是否现任干部', name: 'isPresentCadre', formatter: $.jgrid.formatter.TRUEFALSE},
            </c:if>
            {label: '时任职务', name: 'presentPost', width: 250, align: 'left'},
            {label: '联系电话', name: 'phone', width: 150},

            {
                label: '委派单位', name: 'toUnitType', formatter: function (cellvalue, options, rowObject) {
                    <c:if test="${param.type==CRP_RECORD_TYPE_IN}">
                    return rowObject.toUnit;
                    </c:if>
                    <c:if test="${param.type!=CRP_RECORD_TYPE_IN}">
                    if (cellvalue == undefined) return '--';
                    return _cMap.metaTypeMap[cellvalue].name +
                        ((cellvalue == '${cm:getMetaTypeByCode(unitCodeOther).id}') ? ("：" + rowObject.toUnit) : "");
                    </c:if>
                }, width: 150
            },
            {
                label: '挂职类别', name: 'tempPostType', width: 180, formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    return _cMap.metaTypeMap[cellvalue].name +
                        ((cellvalue == '${cm:getMetaTypeByCode(postCodeOther).id}') ? ("：" + rowObject.tempPost) : "");
                }
            },
            {label: '挂职项目', name: 'project', width: 300, align: 'left'},
            {
                label: '挂职单位',
                name: '_unit',
                width: 200,
                align: 'left',
                formatter: function (cellvalue, options, rowObject) {
                    <c:if test="${param.type==CRP_RECORD_TYPE_OUT}">
                    return $.trim(rowObject.unit);
                    </c:if>
                    <c:if test="${param.type!=CRP_RECORD_TYPE_OUT}">
                    return $.jgrid.formatter.unit(rowObject.unitId)
                    </c:if>
                }
            },
            {label: '所任职务', name: 'post', width: 150, align: 'left'},
            {
                label: '挂职开始时间',
                name: 'startDate',
                width: 120,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m'}
            },
            <c:if test="${!isFinished}">
            {
                label: '挂职拟结束时间',
                name: 'endDate',
                width: 120,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m'}
            },
            </c:if>
            <c:if test="${isFinished}">
            {
                label: '挂职实际结束时间',
                name: 'realEndDate',
                width: 150,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m'}
            },
            </c:if>
            {label: '备注', name: 'remark', width: 300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.user_select($('#searchForm select[name=userId]'));
</script>