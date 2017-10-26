<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/crsApplyRule"
             data-url-export="${ctx}/crsApplyRule_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">

                        </div>
                        <div class="space-4"></div>
                        <table id="jqGrid" class="jqGrid table-striped"></table>
                        <div id="jqGridPager"></div>
                    </div>
                </div>
            </div>
        </div>
        <div id="item-content"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/crsStatApplicant_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '应聘人工作证号', name: 'code', width: 150, frozen: true},
            {
                label: '应聘人姓名', name: 'realname', width: 120, formatter: function (cellvalue, options, rowObject) {
                return $.cadre(rowObject.id, cellvalue);
            }, frozen: true
            },
            {label: '所在单位及职务', name: 'title', align: 'left', width: 350},
            {label: '应聘次数', name: 'totalCount'},
            {label: '参加招聘会次数', name: 'passCount', width: 150},
            {
                label: '详情', name: '_detail', width: 120, formatter: function (cellvalue, options, rowObject) {
                return '<a href="javascript:;" class="openView" data-url="${ctx}/crsStatApplicant_detail?userId={0}">查看</a>'
                        .format(rowObject.userId, cellvalue);
            }
            }
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
</script>