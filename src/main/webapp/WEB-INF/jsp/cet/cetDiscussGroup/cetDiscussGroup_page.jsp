<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:" class="openView btn btn-xs btn-success"
               data-url="${ctx}/cet/cetProjectPlan_detail?planId=${cetProjectPlan.id}">
                <i class="ace-icon fa fa-backward"></i> 返回</a>
        </h4>
        <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
                    ${cetDiscuss.name}
                    （${cm:formatDate(cetDiscuss.startDate, "yyyy-MM-dd")} ~ ${cm:formatDate(cetDiscuss.endDate, "yyyy-MM-dd")}，${CET_PROJECT_PLAN_TYPE_MAP.get(cetProjectPlan.type)}, ${cetProject.name}）
        </span>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs" id="detail-ul">
                <li class="active">
                    <a href="javascript:;">讨论小组列表</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-12 no-padding-left no-padding-right no-padding-bottom">
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cetDiscussGroup:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/cet/cetDiscussGroup_au?discussId=${param.discussId}">
                        <i class="fa fa-plus"></i> 添加
                    </button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/cet/cetDiscussGroup_au"
                            data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                        修改
                    </button>
                    <button class="jqOpenViewBtn btn btn-warning btn-sm"
                            data-url="${ctx}/cet/cetDiscussGroup_result"
                            data-grid-id="#jqGrid2"><i class="fa fa-upload"></i>
                        上传开会信息
                    </button>
                    <%--<button class="jqOpenViewBtn btn btn-warning btn-sm"
                            data-open-by="page"
                            data-url="${ctx}/cet/cetProject_detail_obj?cls=5&projectId=${cetProject.id}"
                            data-id-name="discussGroupId"
                            data-grid-id="#jqGrid2"><i class="fa fa-users"></i>
                        设置小组成员
                    </button>--%>
                </shiro:hasPermission>
                <shiro:hasPermission name="cetDiscussGroup:del">
                    <button data-url="${ctx}/cet/cetDiscussGroup_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？（关联数据都将删除，请谨慎操作！）"
                            data-grid-id="#jqGrid2"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid2" class="jqGrid2 table-striped"></table>
            <div id="jqGridPager2"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    var unitType = ${cetDiscuss.unitType};
    $("#jqGrid2").jqGrid({
        rownumbers:true,
        pager: "jqGridPager2",
        url: '${ctx}/cet/cetDiscussGroup_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '小组成员', name: 'objCount',width: 80, formatter: function (cellvalue, options, rowObject) {
                return ('<button class="openView btn btn-success btn-xs" ' +
                'data-url="${ctx}/cet/cetProject_detail_obj?cls=5&projectId=${cetProject.id}&discussGroupId={0}"><i class="fa fa-search"></i> 详情</button>')
                        .format(rowObject.id);
            }},
            {label: '状态', name: '_status', formatter: function (cellvalue, options, rowObject) {
                return '未召开'
            }, frozen: true},
            {label: '组别', name: 'name',width: 150, frozen: true},
            {
                label: '排序', align: 'center',formatter: $.jgrid.formatter.sortOrder,
                formatoptions: {grid:'#jqGrid2', url: "${ctx}/cet/cetDiscussGroup_changeOrder"}, frozen: true
            },
            {label: '召集人', name: 'holdUser.realname', frozen: true},
            {label: '研讨主题', name: 'subject',width: 250, align:'left', frozen: true},
            <c:if test="${cetDiscuss.unitType!=CET_DISCUSS_UNIT_TYPE_OW}">
            {label: '是否允许修改主题', name: 'subjectCanModify',width: 150, formatter: $.jgrid.formatter.TRUEFALSE},
            </c:if>
            {label: '召开时间', name: 'discussTime',width: 150, formatter: 'date',
                formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'}},
            {label: '召开地点', name: 'discussAddress',width: 250, align:'left'},
            /*{label: '小组成员', name: 'objCount',width: 80, formatter: function (cellvalue, options, rowObject) {
                return "-";
            }},
            {label: '实际参会情况', name: '_count',width: 120, formatter: function (cellvalue, options, rowObject) {
                return '-'
            }},*/
            <c:if test="${cetDiscuss.unitType!=CET_DISCUSS_UNIT_TYPE_OW}">
            {label: '负责单位', name: 'unitId',width: 250, formatter: function (cellvalue, options, rowObject) {
                if(unitType==${CET_DISCUSS_UNIT_TYPE_OW}) return '-'
                if(unitType==${CET_DISCUSS_UNIT_TYPE_PARTY})
                    return rowObject.cetParty==undefined?'':rowObject.cetParty.partyName;
                if(unitType==${CET_DISCUSS_UNIT_TYPE_UNIT})
                    return rowObject.cetUnit==undefined?'':rowObject.cetUnit.unitName;
                if(unitType==${CET_DISCUSS_UNIT_TYPE_PARTY_SCHOOL})
                    return rowObject.cetPartySchool==undefined?'':rowObject.cetPartySchool.partySchoolName;

            } },
            {label: '负责单位管理员', name: 'adminUser.realname',width: 150},
            </c:if>
            <c:if test="${cetDiscuss.unitType==CET_DISCUSS_UNIT_TYPE_OW}">
            {label: '管理员', name: 'adminUser.realname',width: 150},
            </c:if>
            {label: '备注', name: 'remark',width: 350}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
</script>