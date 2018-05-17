<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
<c:set var="_query" value="${not empty param.hasChosen ||not empty param.isCurrentGroup
 ||not empty param.isFinish||not empty param.hasUploadWrite ||not empty param.userId ||not empty param.dpTypes||not empty param.adminLevels
                ||not empty param.postIds || not empty param.code || not empty param.sort}"/>
<div class="jqgrid-vertical-offset buttons">
    <div class="type-select">
        <c:forEach items="${cetTraineeTypes}" var="cetTraineeType">
        <span class="typeCheckbox ${traineeTypeId==cetTraineeType.id?"checked":""}">
        <input ${traineeTypeId==cetTraineeType.id?"checked":""} type="checkbox" value="${cetTraineeType.id}"> ${cetTraineeType.name}
        </span>
        </c:forEach>
    </div>
<c:if test="${cls==1}">
    <c:if test="${!isQuit}">
    <shiro:hasPermission name="cetProjectObj:edit">
        <button class="popupBtn btn btn-info btn-sm"
                data-url="${ctx}/cet/cetProjectObj_add?projectId=${cetProject.id}&traineeTypeId=${traineeTypeId}">
            <i class="fa fa-plus"></i> 添加
        </button>
        <button data-url="${ctx}/cet/cetProjectObj_quit?isQuit=1"
                data-title="退出"
                data-msg="确定将这{0}个人员转移到“退出培训人员”？"
                data-grid-id="#jqGrid2"
                data-callback="_detailReload"
                class="jqBatchBtn btn btn-warning btn-sm">
            <i class="fa fa-sign-out"></i> 退出
        </button>

        <button class="jqOpenViewBatchBtn btn btn-primary btn-sm"
                data-url="${ctx}/cet/cetProjectObj_shouldFinishPeriod?projectId=${cetProject.id}"
                data-need-id="false"
                data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
            设置应完成学时</button>

        <button data-url="${ctx}/cet/cetProjectObj_autoGraduate?projectId=${cetProject.id}"
                data-title="自动结业"
                data-msg="确定自动结业？（根据已设置的达到结业要求的学时数和学员已完成学时数自动计算）"
                data-grid-id="#jqGrid2"
                data-callback="_detailReload"
                class="confirm btn btn-success btn-sm">
            <i class="fa fa-sign-out"></i> 自动结业
        </button>

        <button data-url="${ctx}/cet/cetProjectObj_forceGraduate"
                data-title="手动结业"
                data-msg="确定将这{0}个人员手动结业？"
                data-grid-id="#jqGrid2"
                class="jqBatchBtn btn btn-warning btn-sm">
            <i class="fa fa-sign-out"></i> 手动结业
        </button>
    </shiro:hasPermission>
    </c:if>
    <c:if test="${isQuit}">
    <shiro:hasPermission name="cetProjectObj:edit">
        <button data-url="${ctx}/cet/cetProjectObj_quit?isQuit=0"
                data-title="重新学习"
                data-msg="确定将这{0}个人员转移到“培训对象”？"
                data-grid-id="#jqGrid2"
                data-callback="_detailReload"
                class="jqBatchBtn btn btn-success btn-sm">
            <i class="fa fa-reply"></i> 重新学习
        </button>
    </shiro:hasPermission>
    </c:if>
    <shiro:hasPermission name="cetProjectObj:del">
        <button data-url="${ctx}/cet/cetProjectObj_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}条数据？<br/>（相关数据将全部清除，请谨慎操作）"
                data-grid-id="#jqGrid2"
                class="jqBatchBtn btn btn-danger btn-sm">
            <i class="fa fa-trash"></i> 删除
        </button>
    </shiro:hasPermission>
    </c:if>
    <c:if test="${cls==2}">
    <button data-url="${ctx}/cet/cetProjectObj_apply?projectId=${cetProject.id}&opType=1&trainCourseId=${param.trainCourseId}"
            data-title="全部设置为必选"
            data-msg="确定全部设置为必选？<br/>（注：已签到的学员除外）"
            data-callback="_callback2"
            class="confirm btn btn-primary btn-sm">
        <i class="fa fa-check-circle"></i> 全部设置为必选
    </button>
    <button data-url="${ctx}/cet/cetProjectObj_apply?projectId=${cetProject.id}&opType=2&trainCourseId=${param.trainCourseId}"
            data-title="全部设置为可选"
            data-msg="确定全部设置为可选？<br/>（注：此操作将把所有的已选课但未签到的学员重置为未选课，即退课）"
            data-callback="_callback2"
            class="confirm btn btn-warning btn-sm">
        <i class="fa fa-times-circle"></i> 全部设置为可选
    </button>
    <button data-url="${ctx}/cet/cetProjectObj_apply?projectId=${cetProject.id}&opType=1&trainCourseId=${param.trainCourseId}"
            data-title="设置为必选学员"
            data-msg="确定将这{0}个学员设置为必选学员？"
            data-grid-id="#jqGrid2"
            data-callback="_callback2"
            class="jqBatchBtn btn btn-primary btn-sm">
        <i class="fa fa-check"></i> 设置为必选学员
    </button>
    <button data-url="${ctx}/cet/cetProjectObj_apply?projectId=${cetProject.id}&opType=2&trainCourseId=${param.trainCourseId}"
            data-title="设置为可选学员"
            data-msg="确定将这{0}个学员设置为可选学员？<br/>（注：此操作将把已选课但未签到的学员重置为未选课，即退课）"
            data-grid-id="#jqGrid2"
            data-callback="_callback2"
            class="jqBatchBtn btn btn-warning btn-sm">
        <i class="fa fa-times"></i> 设置为可选学员
    </button>
    <button data-url="${ctx}/cet/cetProjectObj_apply?projectId=${cetProject.id}&opType=3&trainCourseId=${param.trainCourseId}"
            data-title="选课"
            data-msg="确定为这{0}个学员选课？"
            data-grid-id="#jqGrid2"
            data-callback="_callback2"
            class="jqBatchBtn btn btn-success btn-sm">
        <i class="fa fa-check"></i> 选课
    </button>
    <button data-url="${ctx}/cet/cetProjectObj_apply?projectId=${cetProject.id}&opType=4&trainCourseId=${param.trainCourseId}"
            data-title="退课"
            data-msg="确定将这{0}个学员退课？<br/>（注：已签到的学员除外）"
            data-grid-id="#jqGrid2"
            data-callback="_callback2"
            class="jqBatchBtn btn btn-danger btn-sm">
        <i class="fa fa-times"></i> 退课
    </button>
    <button data-url="${ctx}/cet/cetProjectObj_apply?projectId=${cetProject.id}&opType=4&trainCourseId=${param.trainCourseId}"
            data-title="全部退课"
            data-msg="确定全部退课？<br/>（注：此操作将把所有的已选课但未签到的学员重置为未选课，即全部退课）"
            data-callback="_callback2"
            class="confirm btn btn-danger btn-sm">
        <i class="fa fa-times-circle"></i> 全部退课
    </button>
        <button id="logBtn" class="jqOpenViewBtn btn btn-info btn-sm"
                data-grid-id="#jqGrid2"
                data-url="${ctx}/sysApprovalLog"
                data-width="850"
                data-id-name="objId">
            <i class="fa fa-history"></i> 操作记录
        </button>
    </c:if>
    <c:if test="${cls==3 || cls==6}">
    <button data-url="${ctx}/cet/cetPlanCourse_selectObjs?select=1&planCourseId=${param.planCourseId}"
            data-title="选择学员"
            data-msg="确定将这{0}个学员设置为学员？"
            data-grid-id="#jqGrid2"
            data-callback="_callback2"
            class="jqBatchBtn btn btn-success btn-sm">
        <i class="fa fa-plus"></i> 选择学员
    </button>
    <button data-url="${ctx}/cet/cetPlanCourse_selectObjs?select=0&planCourseId=${param.planCourseId}"
            data-title="取消选择"
            data-msg="确定将这{0}个学员取消选择？"
            data-grid-id="#jqGrid2"
            data-callback="_callback2"
            class="jqBatchBtn btn btn-warning btn-sm">
        <i class="fa fa-minus"></i> 取消选择
    </button>
        <c:if test="${cls==3}">
        <button class="popupBtn btn btn-primary btn-sm tooltip-success"
                    data-url="${ctx}/cet/cetPlanCourseObjResult_import?planCourseId=${param.planCourseId}"
                    data-rel="tooltip" data-placement="top"
                title="从Excel中导入上传学习情况"><i class="fa fa-upload"></i> 上传学习情况</button>
        <button id="resultEditBtn" class="jqOpenViewBtn btn btn-info btn-sm"
                    data-grid-id="#jqGrid2"
                    data-id-name="objId"
                    data-url="${ctx}/cet/cetPlanCourseObjResult_au?planCourseId=${param.planCourseId}">
                <i class="fa fa-edit"></i> 编辑学习情况</button>
        <button data-url="${ctx}/cet/cetPlanCourseObjResult_clear?planCourseId=${param.planCourseId}"
                data-title="清除学习情况"
                data-msg="确定清除这{0}个学员的学习情况？"
                data-grid-id="#jqGrid2"
                data-callback="_callback2"
                class="jqBatchBtn btn btn-warning btn-sm">
            <i class="fa fa-eraser"></i> 清除学习情况
        </button>
        </c:if>
        <c:if test="${cls==6}">
            <button data-url="${ctx}/cet/cetPlanCourseObj_finish?finish=1&planCourseId=${param.planCourseId}"
                    data-title="完成自学"
                    data-msg="确定将这{0}个学员设置为已完成自学？（仅对已选学员有效）"
                    data-grid-id="#jqGrid2"
                    data-callback="_callback2"
                    class="jqBatchBtn btn btn-primary btn-sm">
                <i class="fa fa-check"></i> 完成自学
            </button>
            <button data-url="${ctx}/cet/cetPlanCourseObj_finish?finish=0&planCourseId=${param.planCourseId}"
                    data-title="未完成自学"
                    data-msg="确定将这{0}个学员修改为未完成自学？（仅对已选学员有效）"
                    data-grid-id="#jqGrid2"
                    data-callback="_callback2"
                    class="jqBatchBtn btn btn-danger btn-sm">
                <i class="fa fa-times"></i> 未完成自学
            </button>
            <c:if test="${cetPlanCourse.needNote}">
            <button id="uploadNoteBtn" class="jqOpenViewBtn btn btn-info btn-sm tooltip-success"
                    data-url="${ctx}/cet/cetPlanCourseObj_uploadNote?planCourseId=${param.planCourseId}"
                    data-grid-id="#jqGrid2"
                    data-rel="tooltip" data-placement="top"
                    title="上传学习心得"><i class="fa fa-upload"></i> 上传学习心得</button>
            </c:if>
        </c:if>
    </c:if>
    <c:if test="${cls==4}">
    <button class="jqOpenViewBtn btn btn-primary btn-sm tooltip-success"
                data-url="${ctx}/cet/cetProjectObj_uploadWrite"
                data-grid-id="#jqGrid2"
                data-rel="tooltip" data-placement="top"
            title="上传"><i class="fa fa-upload"></i> 上传心得体会</button>
        <button data-url="${ctx}/cet/cetProjectObj_clearWrite"
                data-title="删除心得体会"
                data-msg="确定删除这{0}个学员的心得体会？"
                data-grid-id="#jqGrid2"
                data-callback="_callback2"
                class="jqBatchBtn btn btn-warning btn-sm">
            <i class="fa fa-eraser"></i> 删除心得体会
        </button>

        <button class="jqExportBtn btn btn-success btn-sm"
                data-grid-id="#jqGrid2"
                data-search-form-id="#searchForm2"
                data-url="${ctx}/cet/cetProjectObj_data?projectId=${cetProject.id}"><i class="fa fa-download"></i>
            打包下载</button>
    </c:if>
    <c:if test="${cls==5}">
        <button data-url="${ctx}/cet/cetDiscussGroup_selectObjs?select=1&discussGroupId=${param.discussGroupId}"
                data-title="选择学员"
                data-msg="确定将这{0}个学员设置为学员？（此操作对已在其他分组中的学员无效）"
                data-grid-id="#jqGrid2"
                data-callback="_callback2"
                class="jqBatchBtn btn btn-success btn-sm">
            <i class="fa fa-plus"></i> 选择学员
        </button>
        <button data-url="${ctx}/cet/cetDiscussGroup_selectObjs?select=0&discussGroupId=${param.discussGroupId}"
                data-title="取消选择"
                data-msg="确定将这{0}个学员取消选择？（此操作对已在其他分组中的学员无效）"
                data-grid-id="#jqGrid2"
                data-callback="_callback2"
                class="jqBatchBtn btn btn-warning btn-sm">
            <i class="fa fa-minus"></i> 取消选择
        </button>
        <button data-url="${ctx}/cet/cetDiscussGroup_finish?finish=1&discussGroupId=${param.discussGroupId}"
                data-title="参会"
                data-msg="确定将这{0}个学员设置为已参会？（此操作只针对本组未参会成员）"
                data-grid-id="#jqGrid2"
                data-callback="_callback2"
                class="jqBatchBtn btn btn-primary btn-sm">
            <i class="fa fa-check"></i> 参会
        </button>
        <button data-url="${ctx}/cet/cetDiscussGroup_finish?finish=0&discussGroupId=${param.discussGroupId}"
                data-title="取消参会"
                data-msg="确定将这{0}个学员取消参会？（此操作只针对本组已参会成员）"
                data-grid-id="#jqGrid2"
                data-callback="_callback2"
                class="jqBatchBtn btn btn-danger btn-sm">
            <i class="fa fa-times"></i> 取消参会
        </button>
    </c:if>
</div>
<div class="space-4"></div>
<div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
    <div class="widget-header">
        <h4 class="widget-title">搜索</h4>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main no-padding">
            <form class="form-inline search-form" id="searchForm2">
                <input type="hidden" name="traineeTypeId" value="${traineeTypeId}">
                <input type="hidden" name="isQuit" value="${isQuit}">
                <input type="hidden" name="cls" value="${cls}">

                <c:if test="${cls==2}">
                <div class="form-group">
                    <label>是否选课</label>
                    <select data-rel="select2" data-width="100" name="hasChosen"  data-placeholder="请选择">
                        <option></option>
                        <option value="0">未选课</option>
                        <option value="1">已选课</option>
                    </select>
                    <script>
                        $("#searchForm2 select[name=hasChosen]").val('${param.hasChosen}')
                    </script>
                </div>
                </c:if>
                <c:if test="${cls==5}">
                <div class="form-group">
                    <label>分组状态</label>
                    <select data-rel="select2" data-width="100" name="isCurrentGroup"  data-placeholder="请选择">
                        <option></option>
                        <option value="0">其他组</option>
                        <option value="1">本组</option>
                    </select>
                    <script>
                        $("#searchForm2 select[name=groupStatus]").val('${param.groupStatus}')
                    </script>
                </div>
                </c:if>
                <c:if test="${cls==6}">
                <div class="form-group">
                    <label>学习情况</label>
                    <select data-rel="select2" data-width="100" name="isFinish"  data-placeholder="请选择">
                        <option></option>
                        <option value="0">正在进行</option>
                        <option value="1">已完成</option>
                    </select>
                    <script>
                        $("#searchForm2 select[name=isFinish]").val('${param.isFinish}')
                    </script>
                </div>
                </c:if>
                <c:if test="${cls==4}">
                <div class="form-group">
                    <label>心得体会</label>
                    <select data-rel="select2" data-width="100" name="hasUploadWrite"  data-placeholder="请选择">
                        <option></option>
                        <option value="0">未上传</option>
                        <option value="1">已上传</option>
                    </select>
                    <script>
                        $("#searchForm2 select[name=hasUploadWrite]").val('${param.hasUploadWrite}')
                    </script>
                </div>
                </c:if>
                <div class="form-group">
                    <label>姓名</label>
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/cet/cetProjectObj_selects?projectId=${cetProject.id}"
                            data-width="280"
                            name="userId" data-placeholder="请输入账号或姓名或教工号">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>行政级别</label>
                        <select class="multiselect" multiple="" name="adminLevels">
                            <c:forEach items="${adminLevelMap}" var="entry">
                                <option value="${entry.key}">${entry.value.name}</option>
                            </c:forEach>
                        </select>
                </div>
                <div class="form-group">
                    <label>职务属性</label>
                        <select class="multiselect" multiple="" name="postIds">
                            <c:forEach items="${postMap}" var="entry">
                                <option value="${entry.key}">${entry.value.name}</option>
                            </c:forEach>
                        </select>
                </div>
                <div class="form-group">
                    <label>党派</label>
                    <select class="multiselect" multiple="" name="dpTypes"
                            style="width: 250px;">
                        <option value="-1">非党干部</option>
                        <option value="0">中共党员</option>
                        <c:forEach var="entry" items="${democraticPartyMap}">
                            <option value="${entry.key}">${entry.value.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="clearfix form-actions center">
                    <a class="jqSearchBtn btn btn-default btn-sm"
                       data-target="#detail-content-view"
                       data-form="#searchForm2"
                       data-url="${ctx}/cet/cetProjectObj?projectId=${cetProject.id}&planId=${cetProjectPlan.id}&trainCourseId=${param.trainCourseId}&planCourseId=${param.planCourseId}&discussGroupId=${param.discussGroupId}"><i class="fa fa-search"></i> 查找</a>
                    <c:if test="${_query}">&nbsp;
                        <button type="button" class="resetBtn btn btn-warning btn-sm"
                                data-target="#detail-content-view"
                                data-url="${ctx}/cet/cetProjectObj?projectId=${cetProject.id}&planId=${cetProjectPlan.id}&traineeTypeId=${traineeTypeId}&cls=${cls}&isQuit=${isQuit}&trainCourseId=${param.trainCourseId}&planCourseId=${param.planCourseId}&discussGroupId=${param.discussGroupId}">
                            <i class="fa fa-reply"></i> 重置
                        </button>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="space-4"></div>
<table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="${(cls==2||cls==3)?0:20}"></table>
<div id="jqGridPager2"></div>
<style>
    .type-select {
        float:right;
        padding: 5px 20px 0 5px;
    }

    .type-select a {
        padding-left: 20px;
    }

    .type-select .typeCheckbox {
        padding: 10px;
        cursor: pointer;
    }

    .type-select .typeCheckbox.checked {
        color: darkred;
        font-weight: bolder;
    }
</style>
<script src="${ctx}/assets/js/bootstrap-multiselect.js"></script>
<link rel="stylesheet" href="${ctx}/assets/css/bootstrap-multiselect.css"/>
<script>
    function _callback2(){
        $("#jqGrid2").trigger("reloadGrid");
    }
    $.register.multiselect($('#searchForm2 select[name=dpTypes]'), ${cm:toJSONArray(selectDpTypes)});
    $.register.multiselect($('#searchForm2 select[name=adminLevels]'), ${cm:toJSONArray(selectAdminLevels)});
    $.register.multiselect($('#searchForm2 select[name=postIds]'), ${cm:toJSONArray(selectPostIds)});

    $(".typeCheckbox").click(function () {
        var $input = $("input", $(this));
        $("#searchForm2 input[name=traineeTypeId]").val($input.val());
        $("#searchForm2 .jqSearchBtn").click();
    })

    //var period = parseFloat('${cetProject.period}');
    //var requirePeriod = parseFloat('${cetProject.requirePeriod}');

    var discussGroupId = '${param.discussGroupId}';

    //console.log("period=" + period)
    //console.log("requirePeriod=" + requirePeriod)

    $.register.user_select($("#searchForm2 select[name=userId]"));
    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        rownumbers:true,
        url: '${ctx}/cet/cetProjectObj_data?callback=?&traineeTypeId=${traineeTypeId}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel:[
            <c:if test="${cls==1}">
            { label: '学习情况',name: '_status', width: 80, formatter: function (cellvalue, options, rowObject) {
                return rowObject.finishPeriod
            }, frozen: true},
            {label: '是否结业', name: 'isGraduate',formatter: $.jgrid.formatter.TRUEFALSE, width: 70, frozen: true},
            {label: '应完成学时数', name: 'shouldFinishPeriod', formatter: $.jgrid.formatter.defaultString, width: 110, frozen: true},
            </c:if>
            <c:if test="${cls==2}">
            { label: '选课方式',name: '_status', width: 80, formatter: function (cellvalue, options, rowObject) {
                return rowObject.objInfo.canQuit?("<span class='{0}'>可选</span>").format(rowObject.objInfo.isFinished?"text-success bolder":"text-default"):
                        ("<span class='{0} bolder'>必选</span>").format(rowObject.objInfo.isFinished?"text-success":"text-danger");
            }, frozen: true},
            { label: '选课时间',name: 'objInfo.chooseTime', width: 160, frozen: true},
            { label: '选课操作人',name: 'objInfo.chooseUserId', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '-'
                return cellvalue==rowObject.userId?'本人':rowObject.objInfo.chooseUserName;
            }, frozen: true},
            <c:if test="${cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_ONLINE}">
            { label: '学习情况',name: 'objInfo.isFinished', width: 80, formatter: function (cellvalue, options, rowObject) {
                return cellvalue?"已学习":"未学习"
            },frozen: true},
            </c:if>
            { name: 'traineeId', hidden:true, formatter: function (cellvalue, options, rowObject) {
                return rowObject.objInfo.traineeId;;
            }},
            </c:if>
            <c:if test="${cls==3}">
            { label: '是否结业',name: 'objInfo.isFinished', width: 80, formatter: function (cellvalue, options, rowObject) {
                if($.trim(rowObject.objInfo.planCourseObjId)=='') return '-'
                return cellvalue?"是":"否"
            },frozen: true},
            { label: '完成学时情况',name: '_finish', width: 120, formatter: function (cellvalue, options, rowObject) {
                if($.trim(rowObject.objInfo.planCourseObjId)=='') return '-'
                return "{0}/{1}".format(Math.trimToZero(rowObject.objInfo.period), '${cm:stripTrailingZeros(cetProjectPlan.period)}')
            }, frozen: true},
            { label: '学习详情',name: '_detail', width: 80, formatter: function (cellvalue, options, rowObject) {
                if($.trim(rowObject.objInfo.planCourseObjId)=='') return '-'
                return ('<button class="popupBtn btn btn-success btn-xs" ' +
                'data-url="${ctx}/cet/cetPlanCourseObjResult_au?view=1&objId={0}&planCourseId={1}"><i class="fa fa-search"></i> 详情</button>')
                        .format(rowObject.id, '${param.planCourseId}');
            }, frozen: true},
            { label: '选课时间',name: 'objInfo.chooseTime', width: 160, formatter: function (cellvalue, options, rowObject) {
                if($.trim(rowObject.objInfo.planCourseObjId)=='') return '-'
                return cellvalue;
            }, frozen: true},
            { label: '选课操作人',name: 'objInfo.chooseUserId', formatter: function (cellvalue, options, rowObject) {
                if($.trim(rowObject.objInfo.planCourseObjId)=='') return '-'
                return cellvalue==rowObject.userId?'本人':rowObject.objInfo.chooseUserName;
            }, frozen: true},
            {name:'planCourseObjId', hidden:true, formatter: function (cellvalue, options, rowObject){
                return $.trim(rowObject.objInfo.planCourseObjId)
            }},
            </c:if>
            <c:if test="${cls==6}">
            { label: '学习情况',name: 'objInfo.isFinished', width: 80, formatter: function (cellvalue, options, rowObject) {
                if($.trim(rowObject.objInfo.planCourseObjId)=='') return '-'
                return cellvalue?"已完成":"正在进行"
            },frozen: true},
            <c:if test="${cetPlanCourse.needNote}">
            { label: '学习心得',name: 'objInfo.note', width: 80, formatter: function (cellvalue, options, rowObject) {
                if($.trim(rowObject.objInfo.planCourseObjId)=='') return '-'
                return ($.trim(cellvalue)=='')?"未上传": $.swfPreview(cellvalue,
                        "学习心得({0})".format(rowObject.realname), '<button class="btn btn-xs btn-primary"><i class="fa fa-search"></i> 查看</button>')
            },frozen: true},
            </c:if>
            {name:'planCourseObjId', hidden:true, formatter: function (cellvalue, options, rowObject){
                return $.trim(rowObject.objInfo.planCourseObjId)
            },frozen: true},
            </c:if>
            <c:if test="${cls==4}">
            {
                label: '心得体会', width: 200, formatter: function (cellvalue, options, rowObject) {

                var ret = "";
                var fileName = "心得体会({0})".format(rowObject.realname);
                var pdfFilePath = rowObject.pdfWrite;
                if ($.trim(pdfFilePath) != '') {
                    //console.log(fileName + " =" + pdfFilePath.substr(pdfFilePath.indexOf(".")))
                    ret = '<button href="javascript:void(0)" data-url="${ctx}/swf/preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                                    .format(encodeURI(pdfFilePath), encodeURI(fileName))
                            + '&nbsp;<button data-url="${ctx}/attach/download?path={0}&filename={1}" title="下载PDF文件" class="linkBtn btn btn-xs btn-warning"><i class="fa fa-file-pdf-o"></i> PDF</button>'
                                    .format(encodeURI(pdfFilePath), encodeURI(fileName));
                }
                var wordFilePath = rowObject.wordWrite;
                if ($.trim(wordFilePath) != '') {

                    ret += '&nbsp;<button data-url="${ctx}/attach/download?path={0}&filename={1}"  title="下载WORD文件" class="linkBtn btn btn-xs btn-success"><i class="fa fa-file-word-o"></i> DOC</button>'
                            .format(encodeURI(wordFilePath), encodeURI(fileName));
                }
                return ret==""?"未上传":ret;
            }, frozen: true},
            { label: '学时',name: '_period', width: 80, formatter: function (cellvalue, options, rowObject) {
                if ($.trim(rowObject.pdfWrite) == '' && $.trim(rowObject.wordWrite) == '') {
                    return 0;
                }
                return '${cm:stripTrailingZeros(cetProjectPlan.period)}'
            }, frozen: true},
            </c:if>
            <c:if test="${cls==5}">
            { label: '分组状态',name: 'objInfo.discussGroupId', width: 80, formatter: function (cellvalue, options, rowObject) {

                if(cellvalue>0){
                    //console.log(discussGroupObj.discussGroupId + " " + parseInt(discussGroupId))
                    if(cellvalue==parseInt(discussGroupId)){
                        return '<span class="text-success">本组</span>'
                    }else{
                        return '<span class="text-danger">其他组</span>'
                    }
                }else{
                    return '未分组'
                }
            }, frozen: true},
            { label: '参会情况',name: 'objInfo.isFinished', width: 80, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue!=undefined &&
                        rowObject.objInfo.discussGroupId==parseInt(discussGroupId)){
                    return cellvalue?"已参会":"未参会"
                }
                return '-'
            }, frozen: true},
            { label: '完成学时数',name: 'objInfo.isFinished', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.objInfo.isFinished==undefined) return '-'
                return rowObject.objInfo.isFinished?${cetDiscuss.period}:0
            }, frozen: true},
            </c:if>
            {label: '工作证号', name: 'code', width: 100, frozen: true},
            {label: '姓名', name: 'realname', width: 120, formatter: function (cellvalue, options, rowObject) {
                return $.cadre(rowObject.cadreId, cellvalue, "_blank");

            }, frozen: true},
            {label: '所在单位及职务', name: 'title', align: 'left', width: 350},
            {
                label: '行政级别', name: 'typeId', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.adminLevelMap[cellvalue].name;
            }},
            {
                label: '职务属性', name: 'postId', width: 150, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.postMap[cellvalue].name;
            }},
            {
                label: '党派', name: 'cadreDpType', width: 80, formatter: function (cellvalue, options, rowObject) {

                if (cellvalue == 0) return "中共党员"
                else if (cellvalue > 0){
                    var dpType = _cMap.metaTypeMap[rowObject.dpTypeId];
                    return (dpType==undefined)?rowObject.dpTypeId:dpType.name;
                }
                return "-";
            }},
            {label: '专业技术职务', name: 'proPost', width: 120},
            {
                label: '任现职时间',
                name: 'lpWorkTime',
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },
            {label: '联系方式', name: 'mobile', width: 120},
            {label: '电子邮箱', name: 'email', width: 250},
            <c:if test="${cls==1}">
            {label: '已完成学时数', name: 'finishPeriod', width: 110},
            {label: '完成百分比', name: '_finishPercent', width: 110, formatter: function (cellvalue, options, rowObject) {

                if(isNaN(rowObject.shouldFinishPeriod) || rowObject.shouldFinishPeriod<=0) return '-';
                return Math.formatFloat(rowObject.finishPeriod*100/rowObject.shouldFinishPeriod, 2) + "%";
            }}
            </c:if>
            /*,
            {label: '是否达到结业要求', name: '_enough', width: 150, formatter: function (cellvalue, options, rowObject) {

                if(isNaN(requirePeriod) || requirePeriod<=0) return '-';
                return rowObject.finishPeriod/requirePeriod >= 0.9?"<span class='text-success'>达到</span>"
                        :"<span class='text-danger'>未达到</span>";
            }}*/
        ],
        onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id, id, status);
            _onSelectRow(this)
        },
        onSelectAll: function (aRowids, status) {
            saveJqgridSelected("#" + this.id);
            _onSelectRow(this)
        },gridComplete:function(){
            $(this).jqGrid("setFrozenColumns");
        }
    });

    function _onSelectRow(grid) {
        var ids = $(grid).getGridParam("selarrrow");
        if (ids.length > 1) {
            $("#logBtn,#resultEditBtn, #uploadNoteBtn").prop("disabled", true);
            return;
        }
        var rowData = $(grid).getRowData(ids[0]);
        var traineeId = rowData.traineeId;
        $("#logBtn").prop("disabled", $.trim(traineeId)=='');
        var planCourseObjId = rowData.planCourseObjId;
        //console.log("planCourseObjId="+ $.trim(planCourseObjId))
        $("#resultEditBtn,#uploadNoteBtn").prop("disabled", $.trim(planCourseObjId)=='');

        var querystr = "&displayType=1&hideStatus=1&type=${SYS_APPROVAL_LOG_TYPE_CET_TRAINEE}&id="+traineeId;
        $("#logBtn").data("querystr", querystr);


    }
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $('#searchForm2 [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>