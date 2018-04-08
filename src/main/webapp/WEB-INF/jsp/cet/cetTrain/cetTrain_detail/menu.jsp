<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:" class="openView btn btn-xs btn-success"
                    data-url="${ctx}/cet/cetProjectPlan_detail?planId=${cetTrain.planId}">
                <i class="ace-icon fa fa-reply"></i>
                返回</a>
        </h4>
                <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
                    培训班：${cetTrain.name}
                </span>
        <div class="jqgrid-vertical-offset widget-toolbar no-border">
            <ul class="nav nav-tabs" id="detail-ul2">
                <li class="active">
                    <a href="javascript:;" class="loadPage"
                       data-load-el="#detail-content2" data-callback="$.menu.liSelected"
                       data-url="${ctx}/cet/cetTrainCourse?trainId=${param.trainId}">
                        <i class="green ace-icon fa fa-list bigger-120"></i> 课程及选课和学习情况</a>
                </li>
                <li>
                    <a href="javascript:;" class="loadPage"
                       data-load-el="#detail-content2" data-callback="$.menu.liSelected"
                       data-url="${ctx}/cet/cetTrain_detail/time?trainId=${param.trainId}">
                        <i class="green ace-icon fa fa-history bigger-120"></i> 选课时间管理</a>
                </li>
                <li>
                    <a href="javascript:;" class="loadPage"
                       data-load-el="#detail-content2" data-callback="$.menu.liSelected"
                       data-url="${ctx}/cet/cetTrain_detail/msg?trainId=${param.trainId}">
                        <i class="green ace-icon fa fa-calendar bigger-120"></i> 开课通知</a>
                </li>
                <li>
                    <a href="javascript:;" class="loadPage"
                       data-load-el="#detail-content2" data-callback="$.menu.liSelected"
                       data-url="${ctx}/cet/cetTrainee?trainId=${param.trainId}">
                        <i class="green ace-icon fa fa-users bigger-120"></i> 学员学习情况汇总</a>
                </li>
                <li>
                    <a href="javascript:;" class="loadPage"
                       data-load-el="#detail-content2" data-callback="$.menu.liSelected"
                       data-url="${ctx}/cet/cetTrain_detail_eva?trainId=${param.trainId}">
                        <i class="green ace-icon fa fa-pencil-square-o bigger-120"></i> 评课</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-12 no-padding-left no-padding-right no-padding-bottom">
            <div class="tab-content padding-4" id="detail-content2">
                <c:import url="${ctx}/cet/cetTrainCourse"/>
            </div>
        </div>
    </div>
</div>
<script>
    function _detailReload2(){
        $("#detail-ul2 li.active .loadPage").click()
    }
</script>