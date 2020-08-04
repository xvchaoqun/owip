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
                name: 'name',
                width: 300,
                align: 'left', frozen:true
            },
            {label: '主讲人', name: 'teacher', frozen:true},
            {label: '学时', name: 'period', width: 70},
            { label: '选课方式',name: '_status', width: 80, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.trainObj==undefined) return '--'
                return rowObject.trainObj.canQuit?("<span class='{0}'>可选</span>").format(rowObject.trainObj.isFinished?"text-success bolder":"text-default"):
                        ("<span class='{0} bolder'>必选</span>").format(rowObject.trainObj.isFinished?"text-success":"text-danger");
            }},
            { label: '选课时间',name: 'trainObj.chooseTime', width: 160, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.trainObj==undefined) return '--'
                return rowObject.trainObj.chooseTime;
            }},
            { label: '选课操作人',name: 'trainObj.chooseUserId', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '--'
                return cellvalue=='${sysUser.id}'?'本人':rowObject.trainObj.chooseUserName;
            }},
            { label: '签到情况',name: '_status', width: 80, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.trainObj==undefined) return '--'
                return rowObject.trainObj.isFinished?("<span class='text-success'>按时签到</span>")
                        :("<span class='text-danger'>未签到</span>");
            }},
            { label: '完成学时数',name: '_finish', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.trainObj==undefined) return '--'
                return rowObject.trainObj.isFinished?rowObject.period:'0';
            }}
        ]
    }).jqGrid("setFrozenColumns");
    $.initNavGrid("jqGrid_popup", "jqGridPager_popup");
</script>