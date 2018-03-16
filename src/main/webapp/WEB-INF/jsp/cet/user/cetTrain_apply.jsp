<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="widget-box transparent">
            <div class="widget-header">
                <h4 class="widget-title lighter smaller  jqgrid-vertical-offset">
                    <a href="javascript:" class="hideView btn btn-xs btn-success">
                        <i class="ace-icon fa fa-backward"></i>
                        返回</a>
                </h4>
                <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
                    培训班名称：${cetTrain.name}
                </span>
            </div>
            <div class="widget-body rownumbers">
                <div class="tab-content padding-8">
                    <table id="jqGrid2" class="jqGrid2 table-striped"></table>
                </div>
                <div class="modal-footer center jqgrid-vertical-offset">
                    <c:if test="${cetTrain.switchStatus!=CET_TRAIN_ENROLL_STATUS_OPEN}">
                        <span class="text-danger bolder">还未开始选课</span>
                    </c:if>
                    <c:if test="${cetTrain.switchStatus==CET_TRAIN_ENROLL_STATUS_OPEN}">
                    <button id="selectBtn" ${cetTrainee.courseCount>0?'disabled':''}
                            data-loading-text="报名中..." data-success-text="已报名成功"
                            autocomplete="off" class="btn btn-info btn-lg"><i class="fa fa-check-square-o"></i>
                        ${cetTrainee.courseCount>0?'已报名':'报&nbsp;名'}
                    </button>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
<script>

    $("#selectBtn").click(function(){

        var trainCourseIds = $("#jqGrid2").getGridParam("selarrrow");
        //console.log(trainCourseIds)
        var num = trainCourseIds.length;
        if(num==0){
            SysMsg.info("请先选择培训课程，再点击“报名”按钮。");
            return;
        }

        SysMsg.confirm("您选择了{0}门课程，是否确认报名？".format(num), function(){
            $.post("${ctx}/user/cet/cetTrain_apply", {trainId:${cetTrain.id} ,trainCourseIds:trainCourseIds},function(ret){
                if(ret.success){
                    SysMsg.success("您选择的课程已成功提交，可在“参训情况”菜单栏目下查看详情。请按时参加培训。谢谢！ ",function(){
                        $.hideView();
                    });
                }
            });
        });
    })

    var cetTrainCourses = ${cm:toJSONArray(cetTrainCourses)};
    $("#jqGrid2").jqGrid({
        pager: null,
        rownumbers: true,
        multiselect:${(cetTrain.switchStatus!=CET_TRAIN_ENROLL_STATUS_OPEN||cetTrainee.courseCount>0)?'false':'true'},
        multiboxonly: false,
        datatype: "local",
        data: cetTrainCourses,
        colModel: [
            {label: '课程编号', name: 'cetCourse.sn'},
            {
                label: '课程名称',
                name: 'cetCourse.name',
                width: 300,
                align: 'left'
            },
            {label: '主讲人', name: 'cetCourse.cetExpert.realname'},
            {label: '所在单位', name: 'cetCourse.cetExpert.unit', width: 300, align: 'left'},
            {label: '职务和职称', name: 'cetCourse.cetExpert.post', width: 120, align: 'left'},
            {
                label: '开始时间',
                name: 'startTime',
                width: 130,
                formatter: 'date',
                formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'}
            },
            {
                label: '结束时间',
                name: 'endTime',
                width: 130,
                formatter: 'date',
                formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'},
            },
            {label: '上课地点', name: 'address', width: 300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
</script>