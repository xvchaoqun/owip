<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!-- PAGE CONTENT BEGINS -->
<div class="widget-box transparent" id="useLogs">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:">${cm:getUserById(param.userId).realname}-应聘详情</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped"> </table>
                <div id="jqGridPager2"> </div>
            </div>
        </div><!-- /.widget-main -->
    </div><!-- /.widget-body -->
</div><!-- /.widget-box -->
<script>
    $("#jqGrid2").jqGrid({
        multiselect:false,
        ondblClickRow:function(){},
        pager:"jqGridPager2",
        url: "${ctx}/crsStatApplicant_detail_data?callback=?&userId=${param.userId}",
        colModel:  [
            {label: '年度', name: 'crsPostYear', width:'60', frozen: true},
            {
                label: '应聘岗位编号', name: 'crsPostSeq', formatter: function (cellvalue, options, rowObject) {
                var type = _cMap.CRS_POST_TYPE_MAP[rowObject.crsPostType];
                return type + "[" + rowObject.crsPostYear + "]" + rowObject.crsPostSeq + "号";

            }, width: 180, frozen: true
            },
            {label: '应聘岗位', name: 'crsPostName', width:'300', frozen: true, formatter: function (cellvalue, options, rowObject) {
                return '<a href="javascript:;" class="openView" data-url="${ctx}/crsPost_detail?id={0}">{1}</a>'
                        .format(rowObject.postId, cellvalue);
            }},
            {label: '分管工作', name: 'crsPostJob', width:'300', formatter: $.jgrid.formatter.NoMultiSpace},
            {
                label: '推荐/自荐', name: 'isRecommend', width: 180, formatter: function (cellvalue, options, rowObject) {

                var str = [];
                if (cellvalue) {
                    if ($.trim(rowObject.recommendOw) != '') str.push(rowObject.recommendOw);
                    if ($.trim(rowObject.recommendCadre) != '') str.push(rowObject.recommendCadre);
                    if ($.trim(rowObject.recommendCrowd) != '') str.push(rowObject.recommendCrowd);

                    return $.swfPreview(rowObject.recommendPdf, rowObject.crsPostName+"-推荐-" + rowObject.realname + ".pdf", str.join(","));
                } else {
                    return "个人报名";
                }
                return '<a href="javascript:void(0)" class="loadPage" data-load-el="#step-item-content" ' +
                        'data-url="${ctx}/crsApplicant_recommend?id={0}&cls=${param.cls}">推荐/自荐</a>'.format(rowObject.id);
            }},
            {label: '应聘情况', name: '_applyStatus', width: 150, formatter: function (cellvalue, options, rowObject) {
                return _applyStatus(rowObject)
            }},
            {label: '此岗位答辩总人数', name: 'applicantCount', width: 150, formatter: function (cellvalue, options, rowObject) {
                return (_applyStatus(rowObject)!='参加招聘会')?"-":cellvalue;
            }},
            {label: '专家推荐排名', name: 'isFirst', width: 120, formatter: function (cellvalue, options, rowObject) {
                if(_applyStatus(rowObject)!='参加招聘会' || cellvalue==undefined) return '-'
                return cellvalue?"排名第1":"排名第2";
            }},
            {label: '专家组人数', name: 'expertCount', width: 120, formatter: function (cellvalue, options, rowObject) {
                return (_applyStatus(rowObject)!='参加招聘会')?"-":cellvalue;
            }},
            {label: '排第1票数', name: 'recommendFirstCount', width: 120, formatter: function (cellvalue, options, rowObject) {
                return (_applyStatus(rowObject)!='参加招聘会')?"-":cellvalue;
            }},
            {label: '排第2票数', name: 'recommendSecondCount', width: 120, formatter: function (cellvalue, options, rowObject) {
                return (_applyStatus(rowObject)!='参加招聘会')?"-":cellvalue;
            }},
            {label: '应聘报名表', name: '_table', formatter: function (cellvalue, options, rowObject) {
                if(_applyStatus(rowObject)!='参加招聘会') return "-";

                return '<button class="linkBtn btn btn-success btn-xs" ' +
                        'data-url="${ctx}/crsApplicant_export?ids[]={0}"><i class="fa fa-download"></i> 导出</button>'
                                .format(rowObject.id)
            }},
            {label: '应聘PPT', name: 'ppt', formatter: function (cellvalue, options, rowObject) {
                if(_applyStatus(rowObject)!='参加招聘会') return "-";
                if(rowObject.ppt==undefined) return ''
                return '<a href="${ctx}/attach/download?path={0}&filename={1}">下载</a>'
                        .format(rowObject.ppt, rowObject.pptName)
            }}
        ]
    });
    function _applyStatus(rowObject){

        if(rowObject.applicantIsQuit) return "退出应聘";
        if(rowObject.infoCheckStatus==${CRS_APPLICANT_INFO_CHECK_STATUS_UNPASS}) return "信息审核未通过"
        if(!rowObject.isRequireCheckPass
                && rowObject.requireCheckStatus
                ==${CRS_APPLICANT_REQUIRE_CHECK_STATUS_UNPASS}) return "资格审核未通过"
        if(rowObject.isRequireCheckPass) return "参加招聘会"

        return "已报名"

    }
    $(window).triggerHandler('resize.jqGrid2');
</script>