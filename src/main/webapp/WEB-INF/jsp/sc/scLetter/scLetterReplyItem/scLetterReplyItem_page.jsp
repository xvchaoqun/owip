<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/sc/scLetterReplyItem"
             data-url-export="${ctx}/sc/scLetterReplyItem_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.userId ||not empty param.letterYear ||not empty param.letterType
                   ||not empty param.letterNum ||not empty param.replyNum
                   ||not empty param.replyDate || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="../scLetter/menu.jsp"/>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">

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

                                            <div class="input-group">
                                                <input required class="form-control date-picker" placeholder="请选择年份"
                                                       name="letterYear"
                                                       type="text"
                                                       data-date-format="yyyy" data-date-min-view-mode="2"
                                                       value="${param.letterYear}"/>
                                                <span class="input-group-addon"> <i
                                                        class="fa fa-calendar bigger-110"></i></span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>函询对象</label>
                                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                                                    name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>函询类型</label>

                                            <div class="input-group">
                                                <select data-rel="select2" name="letterType" data-placeholder="请选择" data-width="100">
                                                    <option></option>
                                                    <c:import url="/metaTypes?__code=mc_sc_letter_type"/>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#searchForm select[name=type]").val(${param.letterType});
                                                </script>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>函询编号</label>
                                            <input class="form-control search-query num" name="letterNum" type="text"
                                                   value="${param.letterNum}" style="width: 50px"
                                                   placeholder="请输入函询编号">
                                        </div>
                                        <div class="form-group">
                                            <label>回复编号</label>
                                            <input class="form-control search-query num" name="replyNum" type="text"
                                                   value="${param.replyNum}" style="width: 50px"
                                                   placeholder="请输入回复编号">
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
    //年份、 函询编号、 函询日期、 纪委
    //回复文件编号、 纪委回复日期、 函询对象工作证号、 函询对象姓名、 纪委回复情况
    $("#jqGrid").jqGrid({
        url: '${ctx}/sc/scLetterReplyItem_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年份', name: 'letterYear', width: 80},
            {
                label: '函询编号', name: 'letterNum', width: 180, formatter: function (cellvalue, options, rowObject) {
                var _num = _cMap.metaTypeMap[rowObject.letterType].name+"[{0}]{1}号".format(rowObject.letterYear, rowObject.letterNum)
                return $.swfPreview(rowObject.letterFilePath, rowObject.letterFileName, _num, _num);
            }, frozen: true
            },
            {label: '函询日期', name: 'letterQueryDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {
                label: '纪委回复文件编号', name: 'replyNum', width: 180, formatter: function (cellvalue, options, rowObject) {
                var _num = _cMap.metaTypeMap[rowObject.replyType].name+"[{0}]{1}号".format(rowObject.letterYear, rowObject.replyNum)
                return $.swfPreview(rowObject.replyFilePath, rowObject.replyFileName, _num, _num);
            }},
            {label: '纪委回复日期', name: 'replyDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '函询对象工作证号', name: 'code', width: 130},
            {label: '函询对象姓名', name: 'realname', width: 110},
            {label: '纪委回复情况', name: 'content', width: 500},
            {label: '备注', name: 'remark', width: 320}

        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $.register.user_select($('#searchForm [data-rel="select2-ajax"]'));
    $.register.date($('.date-picker'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>