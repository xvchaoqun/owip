<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="${!isMobile?'active':''}">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/sysResource?isMobile=0">
                            <i class="fa fa-internet-explorer ${!isMobile?'fa-1g':''}"></i> 网页端</a>
                    </li>
                    <li class="${isMobile?'active':''}">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/sysResource?isMobile=1">
                            <i class="fa fa-mobile ${isMobile?'fa-1g':''}"></i> 手机端</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <c:set var="_query" value="${not empty param.name||not empty param.permission}"/>
                    <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                        <div class="widget-header">
                            <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>
                            <div class="widget-toolbar">
                                <a href="javascript:;" data-action="collapse">
                                    <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                                </a>
                            </div>
                        </div>
                        <div class="widget-body">
                            <div class="widget-main no-padding">
                                <form class="form-inline search-form" id="searchForm">
                                    <div class="form-group">
                                        <label>节点名称</label>
                                        <input class="form-control search-query search-input"
                                               name="name" type="text"
                                               value="${param.name}" placeholder="请输入">
                                    </div>
                                    <div class="form-group">
                                        <label>权限字符串</label>
                                        <input class="form-control search-query search-input"
                                               name="permission" type="text"
                                               value="${param.permission}" placeholder="请输入">
                                    </div>

                                    <div class="form-group">
                                        <label>资源路径</label>
                                        <input class="form-control search-query search-input"
                                               name="url" type="text"
                                               value="${param.url}" placeholder="请输入">
                                    </div>

                                    <div class="clearfix form-actions center">
                                        <a class="jqSearchBtn btn btn-default btn-sm"
                                           data-url="${ctx}/sysResource?isMobile=${isMobile}"
                                           data-target="#page-content"
                                           data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                        <c:if test="${_query}">&nbsp;
                                            <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                    data-url="${ctx}/sysResource?isMobile=${isMobile}"
                                                    data-target="#page-content">
                                                <i class="fa fa-reply"></i> 重置
                                            </button>
                                        </c:if>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane in active">
                        <table id="jqGrid" class="jqGrid table-striped"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">

    $('#jqGrid').jqGrid({
        url: '${ctx}/sysResource_data?${cm:encodeQueryString(pageContext.request.queryString)}',
        "colModel": [
            {"name": "name", "label": "名称", "width": 300, align: 'left'},
            {"name": "type", "label": "类型", "width": 80},
            {"name": "sortOrder", "label": "排序", "width": 50},
            {
                "name": "menuCss", "label": "菜单样式", "width": 70, formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return "--"
                    return '<i class="{0}"></i>'
                        .format(cellvalue);
                }
            },
            {"name": "url", "label": "URL路径", "width": 320, align: 'left'},
            {"name": "permission", "label": "权限字符串", "width": 180, align: 'left'},
            {"name": "remark", "label": "备注", "width": 170},
            {
                "name": "_add", "label": "添加子节点", "width": 100, formatter: function (cellvalue, options, rowObject) {

                    return '<button href="javascript:;" onclick="_appendChild({0})" class="btn btn-success btn-xs"><i class="fa fa-plus"></i> 添加子节点</button>'
                        .format(rowObject.id);
                }
            },
            {
                "name": "_update", "label": "修改", "width": 80, formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.parentId > 0)
                        return '<button data-url="${ctx}/sysResource_au?id={0}" class="popupBtn btn btn-primary btn-xs"><i class="fa fa-edit"></i> 修改</button>'
                            .format(rowObject.id);
                    return "-"
                }
            },
            {
                label: '所属角色', name: 'roleCount', width: 110, formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.parentId > 0)
                    return ('<button class="popupBtn btn btn-warning btn-xs" data-width="750" data-url="${ctx}/sysResource_roles?resourceId={0}">' +
                        '<i class="fa fa-search"></i> 查看({1})</button>')
                        .format(rowObject.id, cellvalue==undefined?'0':cellvalue);
                     return "-"
                }
            },
            {
                "name": "_del", "label": "删除", "width": 80, formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.parentId > 0)
                        return '<button onclick="_del({0},{1})" class="btn btn-danger btn-xs"><i class="fa fa-times"></i> 删除</button>'
                            .format(rowObject.id, rowObject.parentId);
                    return "-";
                }
            },
            {"name": "countCacheKeys", "label": "缓存数量", "width": 170},
            {"name": "countCacheRoles", "label": "缓存数量所属角色", "width": 170}
        ],
        "hoverrows": false,
        "viewrecords": false,
        "gridview": true,
        // enable tree grid
        "treeGrid": true,
        // which column is expandable
        "ExpandColumn": "name",
        // datatype
        "treedatatype": "json",
        // the model used
        "treeGridModel": "adjacency",
        // configuration of the data comming from server
        "treeReader": {
            parent_id_field: "parentId",  //值必须为父级菜单的id值。
            "level_field": "level",
            "leaf_field": "isLeaf",
            "expanded_field": "expanded",
            "loaded": "loaded",
            "icon_field": "icon"
        },
        "datatype": "json",
        "pager": false,
        gridComplete: function () {
            //console.log($("#jqGrid tbody tr[role='row'].jqgrow:first"))
            setTimeout(function () {
                var $t = $(".treeclick", $("#jqGrid tbody tr[role='row'].jqgrow:first"))
                if ($t.hasClass("tree-plus"))
                    $t.click()
            }, 10);
        }
    })
    $(window).triggerHandler('resize.jqGrid');

    function _appendChild(parentId) {
        url = "${ctx}/sysResource_au?parentId=" + parentId;
        $.loadModal(url);

    }

    function _del(id, parentid) {

        SysMsg.confirm("确定删除该资源吗？", function (result) {
            $.post("${ctx}/sysResource_del", {id: id}, function (ret) {
                if (ret.success) {
                    $("#modal").modal('hide');
                    $("#jqGrid").jqGrid("delRowData", id);
                    //SysMsg.success('操作成功。', '成功');
                }
            });
        });
    }
</script>
