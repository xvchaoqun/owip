<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <c:if test="${postCount>0}">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv">
            <div class="jqgrid-vertical-offset buttons">
                <a class="linkBtn btn btn-success btn-sm" data-url="${ctx}/faq?type=hf_crs_note" data-target="_blank">
                    <i class="fa fa-info-circle"></i> 报名须知</a>
                <a class="popupBtn btn btn-primary btn-sm" data-url="${ctx}/user/recommend_form_download">
                    <i class="fa fa-download"></i> 推荐表格下载</a>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="item-content"></div>
        </c:if>
        <c:if test="${postCount==0}">
        <div class="well well-lg" >
            目前没有竞聘岗位。
        </div>
        </c:if>
    </div>
</div>
<c:if test="${postCount>0}">
<script>
    $("#jqGrid").jqGrid({
        multiselect:false,
        rownumbers: true,
        url: '${ctx}/user/crsPost_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '应聘报名', name: '_apply',  formatter: function (cellvalue, options, rowObject) {

                var postIds = ${cm:toJSONArray(postIds)};
                //console.log(postIds.indexOf(rowObject.id))
                var hasApply = (postIds.indexOf(rowObject.id)>=0);

                var disabled = false;
                if(rowObject.switchStatus!='${CRS_POST_ENROLL_STATUS_OPEN}'){
                    disabled = true;
                }

                return '<button class="openView btn {2} btn-xs" {3} data-url="${ctx}/user/crsPost_apply?postId={0}"><i class="fa fa-hand-pointer-o"></i> {1}</button>'
                        .format(rowObject.id, hasApply?"已报名":"应聘", hasApply?"btn-success":"btn-primary",  disabled?"disabled":"")
            }, frozen: true},
            {
                label: '编号', name: 'seq', formatter: function (cellvalue, options, rowObject) {
                var type = _cMap.CRS_POST_TYPE_MAP[rowObject.type];
                return type + "[" + rowObject.year + "]" + rowObject.seq + "号";

            }, width: 180, frozen: true
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
                formatoptions: {srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i'}}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $(window).triggerHandler('resize.jqGrid');
    })
    $.initNavGrid("jqGrid", "jqGridPager");
</script>
</c:if>