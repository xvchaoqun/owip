<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3 style="margin: 0">学习详情（${sysUser.realname}）</h3>
</div>
<div class="modal-body popup-jqgrid" style="padding-top: 0">
    <div class="space-4"></div>
    <table id="jqGrid_popup" class="table-striped"></table>
    <div id="jqGridPager_popup"></div>
</div>

<script>

    $("#jqGrid_popup").jqGrid({
        rownumbers:true,
        multiselect:false,
        height: 390,
        width: 1050,
        rowNum: 10,
        ondblClickRow: function () {},
        pager: "jqGridPager_popup",
        url: "${ctx}/cet/cetTrainCourse_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}",
        colModel:[
            {
                label: '课程名称',
                name: 'cetCourse.name',
                width: 300,
                align: 'left', frozen:true
            },
            /*{label: '课程要点', name: '_summary', width: 80, formatter: function (cellvalue, options, rowObject) {

                if (rowObject.cetCourse.hasSummary==false) return '-'

                return ('<button class="popupBtn btn btn-primary btn-xs" data-width="750" ' +
                'data-url="${ctx}/cet/cetCourse_summary?id={0}&view=1"><i class="fa fa-search"></i> 查看</button>')
                        .format(rowObject.cetCourse.id);
            }},*/
            {label: '主讲人', name: 'cetCourse.cetExpert.realname', frozen:true},
            {label: '学时', name: 'cetCourse.period', width: 70},
            { label: '选课方式',name: '_status', width: 80, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.cetTraineeCourseView==undefined) return '-'
                return rowObject.cetTraineeCourseView.canQuit?("<span class='{0}'>可选</span>").format(rowObject.cetTraineeCourseView.isFinished?"text-success bolder":"text-default"):
                        ("<span class='{0} bolder'>必选</span>").format(rowObject.cetTraineeCourseView.isFinished?"text-success":"text-danger");
            }},
            { label: '选课时间',name: 'cetTraineeCourseView.chooseTime', width: 160, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.cetTraineeCourseView==undefined) return '-'
                return rowObject.cetTraineeCourseView.chooseTime;
            }},
            { label: '选课操作人',name: 'cetTraineeCourseView.chooseUserId', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '-'
                return cellvalue=='${sysUser.id}'?'本人':rowObject.cetTraineeCourseView.chooseUserName;
            }},
            { label: '签到情况',name: '_status', width: 80, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.cetTraineeCourseView==undefined) return '-'
                return rowObject.cetTraineeCourseView.isFinished?("<span class='text-success'>按时签到</span>")
                        :("<span class='text-danger'>未签到</span>");
            }},
            { label: '完成学时数',name: '_finish', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.cetTraineeCourseView==undefined) return '-'
                return rowObject.cetTraineeCourseView.isFinished?rowObject.cetCourse.period:'0';
            }}
        ]
    }).jqGrid("setFrozenColumns");
    $.initNavGrid("jqGrid_popup", "jqGridPager_popup");
</script>