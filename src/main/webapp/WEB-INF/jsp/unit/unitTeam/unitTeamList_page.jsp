<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div id="body-content" class="multi-row-head-table">
<div class="jqgrid-vertical-offset buttons">
        <button class="popupBtn btn btn-success btn-sm" data-url="${ctx}/unitTeam_term">
            <i class="fa fa-plus"></i> 设定本学期起止时间
            (${empty _sysConfig.termStartDate?'':(cm:formatDate(_sysConfig.termStartDate,'yyyy.MM.dd'))}
            - ${empty _sysConfig.termEndDate?'':(cm:formatDate(_sysConfig.termEndDate,'yyyy.MM.dd'))})</button>
    <span>列表默认显示已到应换届时间的班子，如需查询其他，请从搜索中查询</span>
</div>
<c:set var="_query" value="${not empty param.timeLevel || not empty param._deposeTime || not empty param.name}"/>
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
                        <div class="form-group">
                            <label>类型</label>
                            <select class="form-control" data-rel="select2" name="timeLevel"
                                    data-placeholder="请选择">
                                <option></option>
                                <option value="1">本年度应启动换届单位</option>
                                <option value="2">本学期应启动换届单位</option>
                                <option value="3">其他时段应启动换届单位</option>
                            </select>
                            <script type="text/javascript">
                                $("#searchForm select[name=timeLevel]").val('${param.timeLevel}');
                            </script>
                        </div>
                        <div class="form-group" id="otherTimeDiv" style="display: ${param.timeLevel==3?'':'none'}">
                            <label>起止时间</label>
                            <div class="input-group tooltip-success" data-rel="tooltip" title="请选择时间范围">
                                            <span class="input-group-addon">
                                                <i class="fa fa-calendar bigger-110"></i>
                                            </span>
                                <input placeholder="请选择时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                       type="text" name="_deposeTime" value="${param._deposeTime}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>行政班子名称</label>
                                <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                       placeholder="请输入行政班子名称">
                        </div>

                        <div class="clearfix form-actions center">
                            <a class="jqSearchBtn btn btn-default btn-sm"
                               data-url="${ctx}/unitTeam?list=2"
                               data-target="#page-content"
                               data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                            <c:if test="${_query}">&nbsp;
                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                        data-url="${ctx}/unitTeam?list=2"
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
<table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="60"></table>
<div id="jqGridPager2"></div>
</div>
<div id="body-content-view"></div>
<jsp:include page="colModel.jsp?load=page"/>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#searchForm select[name=timeLevel]").change(function(){
        var timeLevel = $(this).val();
        if(timeLevel==3){
            $("#otherTimeDiv").show();
        }else{
            $("#otherTimeDiv input[name=_deposeTime]").val('');
            $("#otherTimeDiv").hide();
        }
    })

    $("#jqGrid2").jqGrid({
        rownumbers: true,
        pager:"#jqGridPager2",
        url: '${ctx}/unitTeam_data?callback=?&timeLevel=${empty param.timeLevel?'0':param.timeLevel}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel:colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");

    function _reload2() {
        $("#modal").modal('hide');
       $("#jqGrid2").trigger("reloadGrid");
    }

    $('#searchForm [data-rel="select2"]').select2();
    // $('[data-rel="tooltip"]').tooltip();
</script>