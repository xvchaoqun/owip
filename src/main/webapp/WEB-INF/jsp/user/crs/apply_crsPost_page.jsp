<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv">
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${cls==1}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/user/apply_crsPost?cls=1"><i
                                class="fa fa-circle-o-notch fa-spin"></i> 应聘岗位</a>
                    </li>
                    <li class="<c:if test="${cls==2}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/user/apply_crsPost?cls=2"><i
                                class="fa fa-check"></i> 应聘历史记录</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active rownumbers">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${cls==1}">
                            <button data-url="${ctx}/user/crsPost_quit"
                                    data-title="退出"
                                    data-msg="确定退出竞聘？"
                                    data-grid-id="#jqGrid"
                                    class="jqItemBtn btn btn-danger btn-sm">
                                <i class="fa fa-minus-circle"></i> 退出
                            </button>
                            <a class="openView btn btn-success btn-sm"
                               data-url="${ctx}/user/crsApplicantAdjust"
                               data-querystr="&"><i class="fa fa-random"></i>
                                调整岗位</a>

                            <a class="openView btn btn-primary btn-sm"
                               data-url="${ctx}/user/crsApplicantAdjust_page"
                               data-querystr="&"><i class="fa fa-history"></i>
                                调整记录</a>
                            </c:if>
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

    function _reload() {
        $("#modal").modal('hide');
        $("#jqGrid").trigger("reloadGrid");
    }

    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/user/apply_crsPost_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '编号', name: 'seq', formatter: function (cellvalue, options, rowObject) {
                var type = _cMap.CRS_POST_TYPE_MAP[rowObject.type];
                return type + "[" + rowObject.year + "]" + rowObject.seq + "号";

            }, width: 150, frozen: true
            },
            {label: '招聘岗位', name: 'name', width: '300', frozen: true},
            {label: '分管工作', name: 'job', width: '300', formatter: $.jgrid.formatter.NoMultiSpace},
            {
                label: '行政级别', name: 'adminLevel', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.adminLevelMap[cellvalue].name;
            }
            },
            {label: '所属单位', name: 'unit.name', width: 200},
            {label: '招聘公告', name: 'notice', width: 90, formatter: function (cellvalue, options, rowObject) {
                if ($.trim(rowObject.notice) == '') return '-'
                    return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/swf/preview?path={0}&filename={1}">查看</a>'
                            .format(encodeURI(rowObject.notice), encodeURI(rowObject.name+"招聘公告.pdf"))
            }},
            {label: '基本条件', name: 'requirement', width: 90, formatter: function (cellvalue, options, rowObject) {
                return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/user/crsPost_requirement?postId={0}">查看</a>'
                        .format(rowObject.id)
            }},
            {label: '任职资格', name: 'qualification', width: 90, formatter: function (cellvalue, options, rowObject) {
                return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/user/crsPost_qualification?postId={0}">查看</a>'
                        .format(rowObject.id)
            }},
            {label: '招聘人数', name: 'num', width: 90},
            {label: '应聘截止时间', name: 'endTime', width: 150, formatter: 'date',
                formatoptions: {srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i'}},
            {label: '招聘会召开时间', name: 'meetingTime', width: 150,  formatter: function (cellvalue, options, rowObject) {
                if($.trim(cellvalue) == '') return '待定'

                return cellvalue;
            }},
            {label: '报名情况', name: '_apply', width: 150, formatter: function (cellvalue, options, rowObject) {

                if(!rowObject.applicantIsQuit) return "已报名";
                return '已退出 <a href="javascript:void(0)" class="confirm" ' +
                                'data-msg="确定重新报名？" data-callback="_reload" '+
                        'data-url="${ctx}/user/crsPost_reApply?postId={0}">重新报名</a>'
                                .format(rowObject.id)
            }},
            <c:if test="${cls==1}">
            {label: '打印报名表', name: '_print',  formatter: function (cellvalue, options, rowObject) {

                return '-'
            }}
            </c:if>
        ]
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $(window).triggerHandler('resize.jqGrid');
    })
    $.initNavGrid("jqGrid", "jqGridPager");
</script>