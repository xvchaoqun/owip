<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/sc/scMatterCheckItem"
             data-url-export="${ctx}/sc/scMatterCheckItem_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year || not empty param.num || not empty param.isRandom
             || not empty param.userId || not empty param.confirmType
            || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="../scMatterCheck/menu.jsp"/>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="scMatterCheckItem:edit">
                                <a class="jqOpenViewBtn btn btn-success btn-sm"
                                   data-url="${ctx}/sc/scMatterCheckItem_au"
                                   data-grid-id="#jqGrid"
                                   data-open-by="page"
                                   ><i class="fa fa-edit"></i>
                                    编辑核查结果</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/sc/scMatterCheckItem_ow"
                                   data-grid-id="#jqGrid"
                                   ><i class="fa fa-gear"></i>
                                    组织处理</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="scMatterCheckItem:del">
                                <button data-url="${ctx}/sc/scMatterCheckItem_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
                            <%--<a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-download"></i> 导出</a>--%>
                        </div>
                        <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                            <div class="widget-header">
                                <h4 class="widget-title">搜索</h4>

                                <div class="widget-toolbar">
                                    <a href="#" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main no-padding">
                                    <form class="form-inline search-form" id="searchForm">
                                        <input type="hidden" name="cls" value="${cls}">
                                        <div class="form-group">
                                            <label>年份</label>
                                            <div class="input-group" style="width: 150px">
                                                <input required class="form-control date-picker" placeholder="请选择年份" name="year"
                                                       type="text"
                                                       data-date-format="yyyy" data-date-min-view-mode="2" value="${param.year}"/>
                                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>核查编号</label>
                                            <input style="width: 120px" class="form-control search-query num" name="num" type="text"
                                                   value="${param.num}"
                                                   placeholder="请输入核查编号">
                                        </div>
                                        <div class="form-group">
                                            <label>核查类型</label>
                                            <select required data-rel="select2"
                                                    name="isRandom" data-placeholder="请选择" data-width="150">
                                                <option></option>
                                                <option value="1">年度随机抽查</option>
                                                <option value="0">重点抽查</option>
                                            </select>
                                            <script>
                                                <c:if test="${not empty param.isRandom}">
                                                $("#searchForm select[name=isRandom]").val('${param.isRandom}')
                                                </c:if>
                                            </script>
                                        </div>
                                        <div class="form-group">
                                            <label>核查对象</label>
                                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/sc/scMatterUser_selects"
                                                    name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>认定结果</label>
                                            <select required data-rel="select2"
                                                    name="confirmType" data-placeholder="请选择" data-width="120">
                                                <option></option>
                                                <c:forEach items="<%=ScConstants.SC_MATTER_CHECK_ITEM_CONFIRM_TYPE_MAP%>" var="_type">
                                                    <option value="${_type.key}">${_type.value}</option>
                                                </c:forEach>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=confirmType]").val('${param.confirmType}')
                                            </script>
                                        </div>

                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-querystr="cls=${cls}">
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
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/sc/scMatterCheckItem_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年份', name: 'year', width: 80, frozen: true},
            {
                label: '核查编号', name: 'num', width: 180, formatter: function (cellvalue, options, rowObject) {
                return "个人事项核查〔{0}〕{1}号".format(rowObject.year, rowObject.num)
            }, frozen: true
            },
            {label: '核查日期', name: 'checkDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {
                label: '核查类型', name: 'isRandom', width: 130, formatter: function (cellvalue, options, rowObject) {
                return (rowObject.isRandom) ? '年度随机抽查' : '重点抽查';
            }
            },
            { label: '工作证号',name: 'code', width: 120,frozen: true},
            { label: '姓名',name: 'realname', frozen: true},
            {label: '比对日期', name: 'compareDate', formatter:function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined) return '-'
                    return $.date(cellvalue, "yyyy-MM-dd");
                }},
            {label: '干部监督机构查核结果', width: 300, name: 'resultType', formatter:$.jgrid.formatter.defaultString},
            {label: '认定结果', name: 'confirmType', formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '-';
                    return _cMap.SC_MATTER_CHECK_ITEM_CONFIRM_TYPE_MAP[cellvalue]
                }},
            {label: '认定日期', name: 'confirmDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '本人说明材料', name: 'selfFile', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '-'
                return $.swfPreview(rowObject.selfFile, "本人说明材料", "查看");
            }},
            {label: '认定依据', width: 200, name: 'checkReason', formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined) return '-'

                    return cellvalue;
                }},
            {label: '干部管理机构处理意见', width: 200, name: 'handleType', formatter:$.jgrid.formatter.defaultString},
            {label: '核查情况表', name: 'checkFile', formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined) return '-'
                return $.swfPreview(rowObject.checkFile, "核查情况表", "查看");
            }},
            {label: '组织处理方式', width: 130, name: 'owHandleType', formatter:$.jgrid.formatter.defaultString},
            {label: '组织处理日期', name: 'owHandleDate', width: 130, formatter:function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined) return '-'
                    return $.date(cellvalue, "yyyy-MM-dd");
                }},
            {label: '组织处理记录', width: 130, name: 'owHandleFile', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '-'
                return $.swfPreview(rowObject.owHandleFile, "组织处理记录", "查看");
            }},
            {label: '组织处理影响期', width: 130, name: 'owAffectDate', formatter:function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined) return '-'
                    return $.date(cellvalue, "yyyy-MM-dd");
                }},
            {label: '备注', name: 'remark', width: 300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $.register.user_select($('#searchForm [data-rel="select2-ajax"]'));
    $.register.date($('.date-picker'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>