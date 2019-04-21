<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/crs/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv">
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${cls==1}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/user/apply_crsPost?cls=1"><i
                                class="fa fa-circle-o-notch fa-spin"></i> 当前应聘岗位</a>
                    </li>
                    <li class="<c:if test="${cls==2}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/user/apply_crsPost?cls=2"><i
                                class="fa fa-check"></i> 历史应聘岗位</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div class="tab-pane in active rownumbers">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${cls==1}">
                            <button data-url="${ctx}/user/crsPost_quit"
                                    data-title="退出"
                                    data-msg="确定退出竞聘？"
                                    data-grid-id="#jqGrid"
                                    data-id-name="postId"
                                    class="jqItemBtn btn btn-danger btn-sm">
                                <i class="fa fa-minus-circle"></i> 退出
                            </button>
                            <button class="jqOpenViewBtn btn btn-info btn-sm"
                                    data-grid-id="#jqGrid"
                                    data-url="${ctx}/user/applicant_log"
                                    data-id-name="postId"
                                    data-width="650"
                                    >
                                <i class="fa fa-history"></i> 操作记录
                            </button>
                            </c:if>
                        </div>
                        <div class="space-4"></div>
                        <table id="jqGrid" class="jqGrid table-striped"></table>
                        <div id="jqGridPager"></div>
                    </div>
                </div>
            </div>
        </div>
        <div id="body-content-view"></div>
        <div id="body-content-view2"></div>
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
            <c:if test="${cls==1}">
            {label: '应聘材料', name: '_applyDetail',  formatter: function (cellvalue, options, rowObject) {

                return '<button class="openView btn btn-success btn-xs" data-url="${ctx}/user/crsPost_apply?postId={0}"><i class="fa fa-search"></i> 详情</button>'
                        .format(rowObject.id)
            }, width: 90, frozen: true},
            {label: '招聘会公告、上传应聘PPT', name: '_applyDetail',  formatter: function (cellvalue, options, rowObject) {

                return '<button class="openView btn btn-success btn-xs" data-url="${ctx}/user/crsPost_apply_notice?postId={0}"><i class="fa fa-search"></i> 详情</button>'
                        .format(rowObject.id)
            }, width: 220, frozen: true},
                </c:if>
            {
                label: '编号', name: 'seq', formatter: function (cellvalue, options, rowObject) {
                var type = _cMap.CRS_POST_TYPE_MAP[rowObject.type];
                return type + "〔" + rowObject.year + "〕" + rowObject.seq + "号";

            }, width: 160, frozen: true
            },
            {label: '招聘岗位', name: 'name', width: '300', frozen: true},
            <c:if test="${cls==1}">
            /*{label: '报名情况', name: '_apply', width: 150, formatter: function (cellvalue, options, rowObject) {

                if(!rowObject.applicantIsQuit) return "已报名";
                return '已退出 <button class="confirm btn btn-success btn-xs" ' +
                        'data-msg="确定重新报名？" data-callback="_reload" '+
                        'data-url="${ctx}/user/crsPost_reApply?postId={0}"><i class="fa fa-hand-pointer-o"></i> 重新报名</button>'
                                .format(rowObject.id)
            }},*/
            {label: '干部应聘报名表', name: '_preview',  formatter: function (cellvalue, options, rowObject) {

                return '<button class="openView btn btn-primary btn-xs" data-url="${ctx}/user/crsApplicant_preview?applicantId={0}"><i class="fa fa-search"></i> 预览</button>'
                        .format(rowObject.applicantId)
            }, width: 125},
            /*{label: '干部应聘报名表', name: '_export', width: 125,  formatter: function (cellvalue, options, rowObject) {

                return '<button class="downloadBtn btn btn-primary btn-xs" ' +
                        'data-url="${ctx}/user/crsApplicant_export?applicantId={0}"><i class="fa fa-download"></i> 导出</button>'
                        .format(rowObject.applicantId)
            }},*/
            </c:if>
            <c:if test="${cls==2}">
            {label: '应聘情况', name: '_apply', width: 150, formatter: function (cellvalue, options, rowObject) {

                if(rowObject.applicantIsQuit) return "退出应聘";
                if(rowObject.infoCheckStatus==${CRS_APPLICANT_INFO_CHECK_STATUS_UNPASS}) return "信息审核未通过"
                if(!rowObject.isRequireCheckPass
                        && rowObject.requireCheckStatus
                        ==${CRS_APPLICANT_REQUIRE_CHECK_STATUS_UNPASS}) return "资格审核未通过"
                if(rowObject.isRequireCheckPass) return "参加招聘会" + '&nbsp;<button class="openView btn btn-success btn-xs" data-url="${ctx}/user/crsPost_apply_notice?postId={0}"><i class="fa fa-search"></i> 详情</button>'
                                .format(rowObject.id)

                return "已报名"
            }},
            {label: '应聘材料', name: '_applyDetail',  formatter: function (cellvalue, options, rowObject) {

                return '<button class="openView btn btn-success btn-xs" data-url="${ctx}/user/crsPost_apply?postId={0}"><i class="fa fa-search"></i> 详情</button>'
                        .format(rowObject.id)
            }},
            </c:if>
            {label: '分管工作', name: 'job', width: '300', formatter: $.jgrid.formatter.NoMultiSpace},
            {label: '岗位职责', name: 'postDuty', width: 90, formatter: function (cellvalue, options, rowObject) {
                if ($.trim(cellvalue) == '') return '--'

                return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/user/crsPost_postDuty?postId={0}">查看</a>'
                        .format(rowObject.id)
            }},
            {label: '行政级别', name: 'adminLevel', formatter:$.jgrid.formatter.MetaType},
            {label: '所属单位', name: 'unit.name', width: 200},
            {label: '招聘公告', name: 'notice', width: 90, formatter: function (cellvalue, options, rowObject) {
                if ($.trim(cellvalue) == '') return '--'
                    return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/swf/preview?path={0}&filename={1}">查看</a>'
                            .format(encodeURI(rowObject.notice), encodeURI(rowObject.name+"招聘公告.pdf"))
            }},
            {label: '基本条件', name: 'requirement', width: 90, formatter: function (cellvalue, options, rowObject) {
                if ($.trim(cellvalue) == '') return '--'
                return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/user/crsPost_requirement?postId={0}">查看</a>'
                        .format(rowObject.id)
            }},
            {label: '任职资格', name: 'qualification', width: 90, formatter: function (cellvalue, options, rowObject) {
                if ($.trim(cellvalue) == '') return '--'
                return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/user/crsPost_qualification?postId={0}">查看</a>'
                        .format(rowObject.id)
            }},
            {label: '招聘人数', name: 'num', width: 90},
            {label: '应聘截止时间', name: 'endTime', width: 150, formatter: $.jgrid.formatter.date,
                formatoptions: {srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i'}},
            /*{label: '招聘会召开时间', name: 'meetingTime', width: 150,  formatter: function (cellvalue, options, rowObject) {
                if($.trim(cellvalue) == '') return '待定'

                return cellvalue;
            }}*/
        ]
    }).jqGrid("setFrozenColumns");
    $.initNavGrid("jqGrid", "jqGridPager");
    $(window).triggerHandler('resize.jqGrid');
</script>