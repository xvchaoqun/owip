<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="row">
    <div class="col-xs-12">
        <div class="myTableDiv"
             data-url-page="${ctx}/pcsVoteGroup_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="candidate-table rownumbers">
                <div class="space-4"></div>
                <div class="jqgrid-vertical-offset buttons">
                    <a class="popupBtn btn btn-info btn-sm" data-url="${ctx}/pcsVoteGroup_au"><i class="fa fa-plus"></i>
                        添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/pcsVoteGroup_au"
                       data-grid-id="#jqGrid2"
                       data-querystr="&"><i class="fa fa-edit"></i>
                        修改</a>
                    <button data-url="${ctx}/pcsVoteGroup_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid2"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="120"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<script>
    $("#jqGrid2").jqGrid({
        pager: "#jqGridPager2",
        rownumbers: true,
        url: '${ctx}/pcsVoteGroup_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '录入计票数据', name: 'hasReport', width: 150, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return cellvalue?"已报送":"未报送";
            }
            },
            {label: '小组名称', name: 'name', width: 150},
            {label: '小组负责人', name: 'leader', width: 120},
            {label: '小组成员', name: 'member', width: 200, align: 'left'},
            {label: '计票录入人员', name: 'recordUser.realname', width: 120},
            {label: '领回选票张数', name: 'vote', width:120},
            {label: '有效票数', name: 'valid', width: 80},
            {label: '无效票数', name: 'invalid', width: 80}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
</script>