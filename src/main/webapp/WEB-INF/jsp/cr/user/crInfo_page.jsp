<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/crs/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv">
            <div class="jqgrid-vertical-offset buttons">
                <a class="popupBtn btn btn-info btn-sm"
                   data-width="800"
                   data-url="${ctx}/hf_content?code=hf_crs_note">
                    <i class="fa fa-search"></i> 应聘程序</a>
                <a class="popupBtn btn btn-warning btn-sm"
                   data-width="800"
                   data-url="${ctx}/hf_content?code=hf_crs_require">
                    <i class="fa fa-exclamation-triangle"></i> 纪律要求</a>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    var hasApplyInfoMap = [];
    $("#jqGrid").jqGrid({
        multiselect:false,
        rownumbers: true,
        url: '${ctx}/user/crInfo_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '应聘报名', name: '_apply', width: 150,  formatter: function (cellvalue, options, rowObject) {

                if(hasApplyInfoMap[rowObject.id]!=undefined){
                    return '<button class="openView btn btn-success btn-xs" ' +
                        'data-url="${ctx}/user/crInfo_apply?infoId={0}"><i class="fa fa-search"></i> 已报名</button>'
                        .format(rowObject.id)
                }
                var nowTime = $.date(new Date(), 'yyyy-MM-dd HH:mm');
                var endTime = $.date(rowObject.endTime, 'yyyy-MM-dd HH:mm');
                if (endTime < nowTime) {
                    return '报名结束'
                }

                return '<button class="openView btn btn-primary btn-xs" ' +
                        'data-url="${ctx}/user/crInfo_apply?infoId={0}"><i class="fa fa-hand-pointer-o"></i> 应  聘</button>'
                        .format(rowObject.id)
            }, frozen: true},
            {label: '应聘报名表', name: '_hasApply', width:'110', frozen: true,  formatter: function (cellvalue, options, rowObject) {

                if(hasApplyInfoMap[rowObject.id]!=undefined){
                    return ('<button class="downloadBtn btn btn-xs btn-success" ' +
                            'data-url="${ctx}/user/crInfo_export?infoId={0}"><i class="fa fa-download"></i> 下载</button>')
                            .format(rowObject.id);
                }
                return '--'
            }},
            {label: '年度', name: 'year', width:'60', frozen: true},
            {label: '添加日期', name: 'addDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}, frozen: true},
            {
                label: '招聘通知', name: 'notice', width: 80, formatter: function (cellvalue, options, rowObject) {
                    return $.pdfPreview(rowObject.notice, "招聘通知", "查看");
                }, frozen: true
            },
            {label: '招聘岗位', name: '_detail', formatter: function (cellvalue, options, rowObject) {
                return ('<button class="openView btn btn-info btn-xs" ' +
                'data-url="${ctx}/user/crPost?infoId={0}"><i class="fa fa-search"></i> 查看({1})</button>')
                        .format(rowObject.id, rowObject.postNum);
            }, frozen: true},
            {label: '招聘人数',name: 'requireNum', width: 80},
            {label: '报名截止时间', name: 'endTime', width: 150, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '--'
                return $.date(cellvalue, "yyyy-MM-dd HH:mm");
            }}
        ],
        beforeProcessing:function(data, status, xhr){
            hasApplyInfoMap = data.hasApplyInfoMap;
        }
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
</script>