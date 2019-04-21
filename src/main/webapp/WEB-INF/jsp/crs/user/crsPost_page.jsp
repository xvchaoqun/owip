<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/crs/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <c:if test="${postCount>0}">

        <div id="body-content" class="myTableDiv">
            <div class="jqgrid-vertical-offset buttons">
                <a class="popupBtn btn btn-info btn-sm"
                   data-width="800"
                   data-url="${ctx}/hf_content?code=hf_crs_note">
                    <i class="fa fa-search"></i> 应聘程序</a>
                <a class="popupBtn btn btn-warning btn-sm"
                   data-width="650"
                   data-url="${ctx}/hf_content?code=hf_crs_require">
                    <i class="fa fa-exclamation-triangle"></i> 纪律要求</a>

                <a class="popupBtn btn btn-primary btn-sm" data-url="${ctx}/user/recommend_form_download">
                    <i class="fa fa-download"></i> 推荐表格下载</a>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view"></div>
            <div id="body-content-view2"></div>
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
    function _reload() {
        $("#modal").modal('hide');
        $("#jqGrid").trigger("reloadGrid");
    }
    var hasApplys = [];
    var canAfterApplyPostIds = [];
    $("#jqGrid").jqGrid({
        multiselect:false,
        rownumbers: true,
        url: '${ctx}/user/crsPost_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '应聘报名', name: '_apply', width: 150,  formatter: function (cellvalue, options, rowObject) {
                var hasApply = false;
                var hasQuit = false;
                $.each(hasApplys, function(i, applicant){

                    hasApply = (applicant.postId==rowObject.id);
                    hasQuit = applicant.isQuit;
                    //console.log(hasApply + " " + applicant.isQuit)
                    if(hasApply) return false;
                })
                var canApply = ($.inArray(rowObject.id, canAfterApplyPostIds)>=0)

                if(rowObject.switchStatus!='${CRS_POST_ENROLL_STATUS_OPEN}') {
                    if (rowObject.endTime != null) {
                        var nowTime = $.date(new Date(), 'yyyy-MM-dd HH:mm');
                        var endTime = $.date(rowObject.endTime, 'yyyy-MM-dd HH:mm');
                        //console.log(nowTime + "--" + endTime);
                        if (endTime < nowTime) {
                            if(!canApply) return '报名结束'
                        }else {
                            return '未开启';
                        }
                    }
                }
                if(hasApply){
                    if(hasQuit){
                        return '已退出 <button class="confirm btn btn-success btn-xs" ' +
                                'data-msg="确定重新报名？" data-callback="_reload" '+
                                'data-url="${ctx}/user/crsPost_reApply?postId={0}"><i class="fa fa-hand-pointer-o"></i> 重新报名</button>'
                                        .format(rowObject.id);
                    }
                    return '已报名'
                }

                return '<button class="openView btn btn-success btn-xs" ' +
                        'data-url="${ctx}/user/crsPost_apply?postId={0}"><i class="fa fa-hand-pointer-o"></i> 应聘</button>'
                        .format(rowObject.id)
            }, frozen: true},
            {
                label: '编号', name: 'seq', formatter: function (cellvalue, options, rowObject) {
                var type = _cMap.CRS_POST_TYPE_MAP[rowObject.type];
                return type + "〔" + rowObject.year + "〕" + rowObject.seq + "号";

            }, width: 190, frozen: true
            },
            {label: '招聘岗位', name: 'name', width: '300', frozen: true, align:'left'},
            {label: '分管工作', name: 'job', width: '300', align:'left', formatter: $.jgrid.formatter.NoMultiSpace},
            {label: '岗位职责', name: 'postDuty', width: 90, formatter: function (cellvalue, options, rowObject) {
                if($.trim(cellvalue)=='') return '--'
                return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/user/crsPost_postDuty?postId={0}">查看</a>'
                        .format(rowObject.id)
            }},
            {label: '行政级别', name: 'adminLevel', formatter:$.jgrid.formatter.MetaType},
            {label: '所属单位', name: 'unit.name', width: 200, align:'left'},
            {label: '招聘公告', name: 'notice', width: 90, formatter: function (cellvalue, options, rowObject) {
                if($.trim(cellvalue)=='') return '--'
                    return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/swf/preview?path={0}&filename={1}">查看</a>'
                            .format(encodeURI(rowObject.notice), encodeURI(rowObject.name+"招聘公告.pdf"))
            }},
            {label: '基本条件', name: 'requirement', width: 90, formatter: function (cellvalue, options, rowObject) {
                if($.trim(cellvalue)=='') return '--'
                    return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/user/crsPost_requirement?postId={0}">查看</a>'
                            .format(rowObject.id)
            }},
            {label: '任职资格', name: 'qualification', width: 90, formatter: function (cellvalue, options, rowObject) {
                if($.trim(cellvalue)=='') return '--'
                return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/user/crsPost_qualification?postId={0}">查看</a>'
                        .format(rowObject.id)
            }},
            {label: '招聘人数', name: 'num', width: 90},
            {label: '应聘截止时间', name: 'endTime', width: 150, formatter: $.jgrid.formatter.date,
                formatoptions: {srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i'}}
        ],
        beforeProcessing:function(data, status, xhr){
            //console.log(data)
            hasApplys = data.hasApplys;
            canAfterApplyPostIds = data.canAfterApplyPostIds;
        }
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $(window).triggerHandler('resize.jqGrid');
    })
    $.initNavGrid("jqGrid", "jqGridPager");
</script>
</c:if>