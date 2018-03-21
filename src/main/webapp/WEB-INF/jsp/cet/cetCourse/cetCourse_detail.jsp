<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="widget-box transparent">
            <div class="widget-header">
                <h4 class="widget-title lighter smaller  jqgrid-vertical-offset">
                    <a href="javascript:" class="hideView btn btn-xs btn-success">
                        <i class="ace-icon fa fa-backward"></i>
                        返回</a>
                </h4>
                <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
                    课程名称：${cetCourse.name}
                </span>
            </div>
            <div class="widget-body rownumbers">
                <div class="tab-content padding-8">
                    <div class="panel panel-default" style="margin-bottom: 10px">
                        <div class="panel-heading">
                            <h3 class="panel-title"><span class="text-success bolder"><i
                                    class="fa fa-list"></i>   所属重点专题</span>
                            </h3>
                        </div>
                        <div class="collapse in">
                            <div class="panel-body">
                                <table id="jqGrid2" data-width-reduce="30"
                                       class="jqGrid4 table-striped"></table>
                                <div id="jqGridPager2"></div>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default" style="margin-bottom: 0">
                        <div class="panel-heading">
                            <h3 class="panel-title"><span class="text-danger bolder"><i
                                    class="fa fa-line-chart"></i>   被选课情况</span>
                            </h3>
                        </div>
                        <div class="collapse in">
                            <div class="panel-body">
                                <table id="jqGrid3" data-width-reduce="30"
                                       class="jqGrid4 table-striped"></table>
                                <div id="jqGridPager3"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $("#jqGrid2").jqGrid({
        pager: "#jqGridPager2",
        rownumbers: true,
        url: '${ctx}/cet/cetColumnCourse_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '所属重点专题', name: 'columnName', width:300},
            {label: '子专题', name: 'fColumnName', width:300}
        ]
    }).jqGrid("setFrozenColumns");
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $("#jqGrid3").jqGrid({
        pager: "#jqGridPager3",
        rownumbers: true,
        url: '${ctx}/cet/cetTrainCourseStat_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '被使用的培训班', name: 'name', width:300},
            {label: '选课人数', name: 'selectedCount'},
            {label: '签到人数', name: 'finishCount'},
            {label: '测评人数', name: 'evaFinishCount'},
            {label: '评分', name: 'score'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid4');
    $.initNavGrid("jqGrid3", "jqGridPager3");
</script>