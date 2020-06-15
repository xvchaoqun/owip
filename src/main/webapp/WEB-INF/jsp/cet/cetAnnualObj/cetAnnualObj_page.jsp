<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:set var="_query"
       value="${not empty param.userId ||not empty param.adminLevels
        || not empty param.postTypes || not empty param.isFinishedOffline || not empty param.isFinishedOnline || not empty param.needUpdateRequire}"/>
<div class="jqgrid-vertical-offset buttons">

     <div class="type-select">
        <span class="typeCheckbox ${param.sortByFinished==1?"checked":""}">
        <input ${param.sortByFinished==1?"checked":""}  data-name="sortByFinished"
                type="checkbox" value="1"> 按照已完成学时数排序
        </span>
        <span class="typeCheckbox ${param.displayFinishedOffline==1?"checked":""}">
        <input ${param.displayFinishedOffline==1?"checked":""}  data-name="displayFinishedOffline"
                type="checkbox" value="1"> 线下已完成
        </span>
        <span class="typeCheckbox ${param.displayUnfinishedOffline==1?"checked":""}">
        <input ${param.displayUnfinishedOffline==1?"checked":""}  data-name="displayUnfinishedOffline"
                type="checkbox" value="1"> 线下未完成
        </span>
        <span class="typeCheckbox ${param.displayFinishedOnline==1?"checked":""}">
        <input ${param.displayFinishedOnline==1?"checked":""}  data-name="displayFinishedOnline"
                type="checkbox" value="1"> 网络已完成
        </span>
        <span class="typeCheckbox ${param.displayUnfinishedOnline==1?"checked":""}">
        <input ${param.displayUnfinishedOnline==1?"checked":""}  data-name="displayUnfinishedOnline"
                type="checkbox" value="1"> 网络未完成
        </span>
    </div>

    <c:if test="${!isQuit}">
    <shiro:hasPermission name="cetAnnualObj:edit">
        <button class="popupBtn btn btn-success btn-sm"
                data-url="${ctx}/cet/cetAnnualObj_add?annualId=${param.annualId}">
            <i class="fa fa-plus"></i> 添加
        </button>
       <button data-url="${ctx}/cet/cetAnnualObj_quit?isQuit=1"
                data-title="退出"
                data-msg="确定将这{0}个人员转移到“退出培训人员”？"
                data-grid-id="#jqGrid2"
                data-callback="_detailReload"
                class="jqBatchBtn btn btn-primary btn-sm">
            <i class="fa fa-sign-out"></i> 退出
        </button>
        <button class="jqOpenViewBatchBtn btn btn-warning btn-sm"
                data-url="${ctx}/cet/cetAnnualObj_updateRequire"
                data-grid-id="#jqGrid2"><i class="fa fa-clock-o"></i>
            设定年度学习任务
        </button>

         <button data-url="${ctx}/cet/cetAnnualObj_sync?annualId=${param.annualId}"
                data-title="同步培训对象信息"
                data-msg="确定同步培训对象信息？"
                data-need-id="false"
                data-callback="_sync_callback"
                data-loading-text="<i class='fa fa-spinner fa-spin'></i> 同步中，请稍后..."
                class="jqItemBtn btn btn-primary btn-sm">
            <i class="fa fa-refresh"></i> 同步培训对象信息
        </button>

         <button data-url="${ctx}/cet/archiveObjFinishPeriod?annualId=${param.annualId}"
                data-title="归档已完成学时"
                data-msg="确定归档已完成学时？"
                data-grid-id="#jqGrid2"
                 data-callback="_archive_callback"
                data-loading-text="<i class='fa fa-spinner fa-spin'></i> 统计中，请稍后..."
                class="jqBatchBtn btn btn-warning btn-sm">
            <i class="fa fa-refresh"></i> 归档已完成学时
        </button>
    </shiro:hasPermission>

        <button class="jqExportBtn btn btn-info btn-sm"
                        data-grid-id="#jqGrid2"
                data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"
                data-url="${ctx}/cet/cetAnnual_exportObjs?annualId=${param.annualId}">
            <i class="fa fa-download"></i> 导出学时情况统计表</button>

     <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
             data-url="${ctx}/cet/cetAnnualObj_data?annualId=${param.annualId}"
             data-grid-id="#jqGrid2"
             data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
         <i class="fa fa-download"></i> 导出明细表
     </button>
        </c:if>
    <c:if test="${isQuit}">
    <shiro:hasPermission name="cetProjectObj:edit">
        <button data-url="${ctx}/cet/cetAnnualObj_quit?isQuit=0"
                data-title="返回培训对象"
                data-msg="确定将这{0}个人员转移到“培训对象”？"
                data-grid-id="#jqGrid2"
                data-callback="_detailReload"
                class="jqBatchBtn btn btn-success btn-sm">
            <i class="fa fa-reply"></i> 返回培训对象
        </button>
    </shiro:hasPermission>
    </c:if>
    <button class="jqOpenViewBtn btn btn-info btn-sm"
            data-grid-id="#jqGrid2"
            data-url="${ctx}/sysApprovalLog"
            data-width="850"
            data-querystr="&displayType=1&hideStatus=1&type=<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_ANNUAL%>">
        <i class="fa fa-history"></i> 操作记录
    </button>
    <shiro:hasPermission name="cetAnnualObj:del">
        <button data-url="${ctx}/cet/cetAnnualObj_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}条数据？"
                data-grid-id="#jqGrid2"
                class="jqBatchBtn btn btn-danger btn-sm">
            <i class="fa fa-trash"></i> 删除
        </button>
    </shiro:hasPermission>


</div>
<div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
    <div class="widget-header">
        <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main no-padding">
            <form class="form-inline search-form" id="searchForm2">
                <input type="hidden" name="sortByFinished" value="${param.sortByFinished}">
                <input type="hidden" name="displayFinishedOffline" value="${param.displayFinishedOffline}">
                <input type="hidden" name="displayFinishedOnline" value="${param.displayFinishedOnline}">
                <input type="hidden" name="displayUnfinishedOffline" value="${param.displayUnfinishedOffline}">
                <input type="hidden" name="displayUnfinishedOnline" value="${param.displayUnfinishedOnline}">

                <div class="form-group">
                    <label>姓名</label>
                     <select data-rel="select2-ajax"
                            data-ajax-url="${ctx}/cadre_selects?key=1&type=1"
                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>行政级别</label>
                        <select class="multiselect" multiple="" name="adminLevels">
                            <c:import url="/metaTypes?__code=mc_admin_level"/>
                        </select>
                </div>
                <div class="form-group">
                    <label>职务属性</label>
                        <select class="multiselect" multiple="" name="postTypes">
                            <c:import url="/metaTypes?__code=mc_post"/>
                        </select>
                </div>
                <div class="form-group">
                    <label>线下培训完成情况(查询归档学时)</label>
                    <select data-rel="select2" name="isFinishedOffline" data-width="160" data-placeholder="请选择">
                        <option></option>
                        <option value="1">完成(>=100%)</option>
                        <option value="0">未完成(<100%)</option>
                    </select>
                    <script>
                        $("#searchForm2 select[name=isFinishedOffline]").val('${param.isFinishedOffline}')
                    </script>
                </div>
                <div class="form-group">
                    <label>网络培训完成情况(查询归档学时)</label>
                    <select data-rel="select2" name="isFinishedOnline" data-width="160" data-placeholder="请选择">
                        <option></option>
                        <option value="1">完成(>=100%)</option>
                        <option value="0">未完成(<100%)</option>
                    </select>
                    <script>
                        $("#searchForm2 select[name=isFinishedOnline]").val('${param.isFinishedOnline}')
                    </script>
                </div>
                <div class="form-group">
                    <label>行政级别是否变更</label>
                    <select data-rel="select2" name="needUpdateRequire" data-width="160" data-placeholder="请选择">
                        <option></option>
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                    <script>
                        $("#searchForm2 select[name=needUpdateRequire]").val('${param.needUpdateRequire}')
                    </script>
                </div>

                <div class="clearfix form-actions center">
                    <a class="jqSearchBtn btn btn-default btn-sm"
                       data-url="${ctx}/cet/cetAnnualObj?annualId=${param.annualId}"
                       data-target="#detail-content"
                       data-form="#searchForm2"><i class="fa fa-search"></i> 查找</a>
                    <c:if test="${_query}">&nbsp;
                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                data-url="${ctx}/cet/cetAnnualObj?annualId=${param.annualId}"
                                data-target="#detail-content">
                            <i class="fa fa-reply"></i> 重置
                        </button>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="space-4"></div>
<table id="jqGrid2" class="jqGrid2 table-striped"></table>
<div id="jqGridPager2"></div>
<style>
    span.overflow{
        color: red;
    }
</style>
<script>

    $(".typeCheckbox input").click(function () {
        var $input = $(this);
        var name = $(this).data("name");
        var isChecked = $input.is(":checked");
        if(name=='displayFinishedOffline'&& isChecked){
            $("#searchForm2 input[name=displayUnfinishedOffline]").val("");
        }else if(name=='displayUnfinishedOffline'&& isChecked){
            $("#searchForm2 input[name=displayFinishedOffline]").val("");
        }else if(name=='displayFinishedOnline'&& isChecked){
            $("#searchForm2 input[name=displayUnfinishedOnline]").val("");
        }else if(name=='displayUnfinishedOnline'&& isChecked){
            $("#searchForm2 input[name=displayFinishedOnline]").val("");
        }
        $("#searchForm2 input[name="+name+"]").val(isChecked?$input.val():"");
        $("#searchForm2 .jqSearchBtn").click();
    })

    function _archive_callback(){
        $("#jqGrid2").trigger("reloadGrid");
    }

    function _sync_callback($btn, ret){
        //console.log(ret)
        if(ret.adminLevelChangedCount>0){
            SysMsg.info("共有"+ ret.adminLevelChangedCount + "个人行政级别变更了，请修改其年度学习任务。")
        }

        $("#jqGrid2").trigger("reloadGrid");
    }

    $.register.user_select($("#searchForm2 select[name=userId]"));
    $.register.multiselect($('#searchForm2 select[name=adminLevels]'), ${cm:toJSONArray(selectAdminLevels)});
    $.register.multiselect($('#searchForm2 select[name=postTypes]'), ${cm:toJSONArray(selectPostTypes)});

    $("#jqGrid2").jqGrid({
        pager: "#jqGridPager2",
        page:"${param._pageNo}",
        rownumbers: true,
        url: '${ctx}/cet/cetAnnualObj_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '详情',name: '_detail', width: 80, formatter: function (cellvalue, options, rowObject) {

                var pageNo = $(this).getGridParam("page");
                return ('<button class="openView btn btn-success btn-xs" ' +
                'data-url="${ctx}/cet/cetAnnualObj_detail?objId={0}&_pageNo={1}"><i class="fa fa-search"></i> 详情</button>')
                        .format(rowObject.id, pageNo);
            }, frozen: true},
            {label: '工作证号', name: 'user.code', frozen: true},
            {label: '姓名', name: 'user.realname', frozen: true},
            {label: '时任单位及职务', name: 'title', align: 'left', width: 350},
            {label: '行政级别', name: 'adminLevel', formatter: $.jgrid.formatter.MetaType},
            {label: '职务属性', name: 'postType', width: 150, formatter: $.jgrid.formatter.MetaType},
            {label: '任现职时间', name: 'lpWorkTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '年度学习任务<br/>(线下)', name: 'periodOffline'},
            {
                label: '线下已完成<br/>/归档学时', name: '_finishPeriodOffline', formatter: function (cellvalue, options, rowObject) {

                    return getFinishPeriodOffline(rowObject) + "/" + rowObject.finishPeriodOffline;
                }
            },
            { label: '完成百分比<br/>(线下)',name: '_finishOffline',formatter: function (cellvalue, options, rowObject) {
                if(Math.trimToZero(rowObject.periodOffline)==0) return '--'
                    var a = getFinishPeriodOffline(rowObject);
                var b = rowObject.periodOffline;
                   a = (a>b?b:a)
                var progress = Math.formatFloat(a*100/b, 1) + "%";
               return ('<div class="progress progress-striped pos-rel" data-percent="{0}">' +
                '<div class="progress-bar progress-bar-success" style="width:{0};"></div></div>').format(progress)
            }},
            {label: '年度学习任务<br/>(网络)', name: 'periodOnline'},
            {
                label: '网络已完成<br/>/归档学时', name: '_finishPeriodOnline', formatter: function (cellvalue, options, rowObject) {

                    return getFinishPeriodOnline(rowObject) + "/" + rowObject.finishPeriodOnline;
                }
            },
            { label: '完成百分比<br/>(网络)',name: '_finishOnline',formatter: function (cellvalue, options, rowObject) {
                if(Math.trimToZero(rowObject.periodOnline)==0) return '--'

                     var a = getFinishPeriodOnline(rowObject);
                var b = rowObject.periodOnline;
                   a = (a>b?b:a)

                var progress = Math.formatFloat(a*100/b, 1) + "%";
               return ('<div class="progress progress-striped pos-rel" data-percent="{0}">' +
                '<div class="progress-bar progress-bar-success" style="width:{0};"></div></div>').format(progress)
            }},
            {
                label: '党校专题培训<br/>完成学时数', name: 'specialPeriod', width: 110
                , formatter: function (cellvalue, options, rowObject) {
                    return (rowObject.hasArchived)?Math.trimToZero(cellvalue)
                        :Math.trimToZero(rowObject.r.specialPeriod);
                }
            },
            {
                label: '党校日常培训<br/>完成学时数', name: 'dailyPeriod', width: 110
                , formatter: function (cellvalue, options, rowObject) {
                    return (rowObject.hasArchived)?Math.trimToZero(cellvalue)
                        :Math.trimToZero(rowObject.r.dailyPeriod);
                }
            },
            {
                label: '二级党委培训<br/>完成学时数', name: 'unitPeriod'
                , formatter: function (cellvalue, options, rowObject) {
                    return (rowObject.hasArchived)?Math.trimToZero(cellvalue)
                        :Math.trimToZero(rowObject.r.unitPeriod);
                }
            },
            {
                label: '上级调训<br/>完成学时数', name: 'upperPeriod'
                , formatter: function (cellvalue, options, rowObject) {
                    return (rowObject.hasArchived)?Math.trimToZero(cellvalue)
                        :Math.trimToZero(rowObject.r.upperPeriod);
                }
            },
            {label: '联系方式', name: 'user.mobile', width: 110},
            {label: '电子邮箱', name: 'user.email', width: 180, align: 'left'},
            /*{label: '备注', name: 'remark', width: 250},*/
        ],rowattr: function(rowData, currentObj, rowId)
        {
            if(currentObj.needUpdateRequire) {
                //console.log(rowData)
                return {'class':'danger'}
            }
        }
    }).jqGrid("setFrozenColumns");

    function getFinishPeriodOffline(rowObject){

        if (!rowObject.hasArchived) {
            var finish = Math.trimToZero(rowObject.r.specialPeriod);
             + Math.trimToZero(rowObject.r.dailyPeriod);
             + Math.trimToZero(rowObject.r.unitPeriod);
             + Math.trimToZero(rowObject.r.upperPeriod);
            return finish - getFinishPeriodOnline(rowObject);
        }else{
            return Math.trimToZero(rowObject.finishPeriodOffline);
        }
    }

    function getFinishPeriodOnline(rowObject){

        if (!rowObject.hasArchived) {
            return rowObject.r.onlinePeriod
        }else{
            return Math.trimToZero(rowObject.finishPeriodOnline);
        }
    }
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm2 [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>