<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <table id="jqGrid" class="jqGrid table-striped"></table>
    </div>
</div>
<script src="${ctx}/assets/js/bootstrap-multiselect.js"></script>
<link rel="stylesheet" href="${ctx}/assets/css/bootstrap-multiselect.css"/>
<script type="text/javascript">

    $('#jqGrid').jqGrid({
        url: '${ctx}/sysResource_data?${cm:encodeQueryString(pageContext.request.queryString)}',
        "colModel": [
            {"name": "name", "label": "名称", "width": 200, align: 'left'},
            {"name": "type", "label": "类型", "width": 80},
            {"name": "sortOrder", "label": "排序", "width": 50},
            {
                "name": "menuCss", "label": "菜单样式", "width": 70, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return ""
                return '<i class="{0}"></i>'
                        .format(cellvalue);
            }
            },
            {"name": "url", "label": "URL路径", "width": 270, align: 'left'},
            {"name": "permission", "label": "权限字符串", "width": 150, align: 'left'},
            {"name": "remark", "label": "备注", "width": 170},
            {
                "name": "_add", "label": "添加子节点", "width": 100, formatter: function (cellvalue, options, rowObject) {

                return '<button href="javascript:;" onclick="_appendChild({0})" class="btn btn-success btn-xs"><i class="fa fa-plus"></i> 添加子节点</button>'
                        .format(rowObject.id);
            }
            },
            {
                "name": "_update", "label": "修改", "width": 80, formatter: function (cellvalue, options, rowObject) {
                return '<button href="javascript:;" onclick="_update({0})" class="btn btn-primary btn-xs"><i class="fa fa-edit"></i> 修改</button>'
                        .format(rowObject.id);
            }
            },
            {
                "name": "_del", "label": "删除", "width": 80, formatter: function (cellvalue, options, rowObject) {
                if (rowObject.parentId == undefined) return "";
                return '<button href="javascript:;" onclick="_del({0},{1})" class="btn btn-danger btn-xs"><i class="fa fa-times"></i> 删除</button>'
                        .format(rowObject.id, rowObject.parentId);
            }
            },
            {"name": "countCacheKeys", "label": "缓存数量", "width": 170},
            {"name": "countCacheRoles", "label": "缓存数量所属角色", "width": 170},


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
            setTimeout(function () {
                var $t = $(".treeclick", $("#jqGrid [role='row'][id=" + 1 + "]"))
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
    function _update(id) {

        url = "${ctx}/sysResource_au?id=" + id;
        $.loadModal(url);
    }

    function _del(id, parentid) {

        bootbox.confirm("确定删除该资源吗？", function (result) {
            if (result) {
                $.post("${ctx}/sysResource_del", {id: id}, function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid").jqGrid("delRowData", id);
                        //SysMsg.success('操作成功。', '成功');
                    }
                });
            }
        });
    }
</script>
