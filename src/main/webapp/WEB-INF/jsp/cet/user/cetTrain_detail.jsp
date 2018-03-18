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
                    <div class="panel panel-primary" style="margin-bottom: 10px">
                        <div class="panel-heading">
                            <h3 class="panel-title"><span class="bolder"><i
                                    class="fa fa-check-square-o"></i>   已选课列表</span>
                            </h3>
                        </div>
                        <div class="collapse in">
                            <div class="panel-body">
                                <table id="jqGrid2" data-width-reduce="30"
                                       class="jqGrid4 table-striped"></table>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default" style="margin-bottom: 0">
                        <div class="panel-heading">
                            <h3 class="panel-title"><span class="text-danger bolder"><i
                                    class="fa fa-times-rectangle-o"></i>   未选课列表</span>
                            </h3>
                        </div>
                        <div class="collapse in">
                            <div class="panel-body">
                                <table id="jqGrid3" data-width-reduce="30"
                                       class="jqGrid4 table-striped"></table>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
<script>

    var selectedCetTraineeCourses = ${cm:toJSONArray(selectedCetTraineeCourses)};
    $("#jqGrid2").jqGrid({
        pager: null,
        multiboxonly: true,
        multiselect:false,
        height:200,
        datatype: "local",
        data: selectedCetTraineeCourses,
        colModel: [
           <c:if test="${!cetTrain.isFinished}">
            {
                label: '退课', name: '_unApply', width: 90, formatter: function (cellvalue, options, rowObject) {
                //console.log(options)
                if(rowObject.isFinished) return '-'
                return ('<button class="confirm btn btn-danger btn-xs" ' +
                'data-url="${ctx}/user/cet/cetTrain_apply_item?isApply=0&trainCourseId={0}" '
                        +'data-msg="确定退课？（{1}）" data-apply="false" data-callback="_applyReload"><i class="fa fa-minus-circle"></i> 退课</button>')
                        .format(rowObject.cetTrainCourse.id, rowObject.cetTrainCourse.cetCourse.name)
            }, frozen:true},
            </c:if>
            {label: '课程编号', name: 'cetTrainCourse.cetCourse.sn', frozen:true},
            {label: '课程名称', name: 'cetTrainCourse.cetCourse.name', width: 300, align: 'left', frozen:true},
            {label: '主讲人', name: 'cetTrainCourse.cetCourse.cetExpert.realname'},
            {label: '所在单位', name: 'cetTrainCourse.cetCourse.cetExpert.unit', width: 300, align: 'left'},
            {label: '职务和职称', name: 'cetTrainCourse.cetCourse.cetExpert.post', width: 120, align: 'left'},
            {
                label: '开始时间',
                name: 'cetTrainCourse.startTime',
                width: 130,
                formatter: 'date',
                formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'}
            },
            {
                label: '结束时间',
                name: 'cetTrainCourse.endTime',
                width: 130,
                formatter: 'date',
                formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'},
            },
            {label: '上课地点', name: 'cetTrainCourse.address', width: 300}
        ]
    }).jqGrid("setFrozenColumns");

    var unSelectedCetTrainCourses = ${cm:toJSONArray(unSelectedCetTrainCourses)};
    $("#jqGrid3").jqGrid({
        pager: null,
        multiboxonly: true,
        multiselect:false,
        height:200,
        datatype: "local",
        data: unSelectedCetTrainCourses,
        colModel: [
            <c:if test="${!cetTrain.isFinished}">
            {
                label: '选课', name: '_apply', width: 90, formatter: function (cellvalue, options, rowObject) {
                //console.log(options)
                if(rowObject.isFinished) return '-'
                return ('<button class="confirm btn btn-success btn-xs" ' +
                'data-url="${ctx}/user/cet/cetTrain_apply_item?isApply=1&trainCourseId={0}" '
                +'data-msg="确定选课？（{1}）" data-apply="true" data-callback="_applyReload"><i class="fa fa-plus-circle"></i> 选课</button>')
                        .format(rowObject.id, rowObject.cetCourse.name)
            }, frozen:true},
            </c:if>
            {label: '课程编号', name: 'cetCourse.sn'},
            {label: '课程名称', name: 'cetCourse.name', width: 300, align: 'left', frozen:true},
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
    $(window).triggerHandler('resize.jqGrid4');

    function _applyReload(btn){

        var isApply = $(btn).data("apply");
        var courseCount = selectedCetTraineeCourses.length;
        if(!isApply && courseCount==1){
            SysMsg.info("您已将所选课程全部退出，不再参加此次培训。如果需要重新选课，请在“培训班次”菜单中选课。",function(){
                $.hideView();
            });
        }else{
            $.loadView("${ctx}/user/cet/cetTrain_detail?trainId=${cetTrain.id}")
        }
    }
</script>