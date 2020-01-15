<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
<table id="treetable" class="table table-hover table-striped">

    <thead>
    <tr>  <th>省、市、区</th> <th>编码</th><th>操作</th>  </tr>
    </thead>
    <tbody>
    <tr> <td></td><td></td> <td></td>  </tr>
    </tbody>
</table>
</div>
</div>

<script src="${ctx}/extend/js/jquery-ui.min.js" type="text/javascript"></script>
<link href="${ctx}/extend/css/ui.fancytree.min.css" rel="stylesheet" type="text/css">
<script src="${ctx}/extend/js/jquery.fancytree-all.min.js" type="text/javascript"></script>
<style type="text/css">
    /* Define custom width and alignment of table columns */
    #treetable {
        table-layout: fixed;
    }
</style>
<script type="text/javascript">

    function _appendChild(parentCode) {
        url = "${ctx}/location_au?parentCode=" + parentCode;

        $.loadModal(url);

    }
    function _au(id) {

        url = "${ctx}/location_au?id=" + id;

        $.loadModal(url);

    }

    function _del(id) {

        SysMsg.confirm("确定删除该节点吗？", "操作确认", function () {
            $.post("${ctx}/location_del", {id: id}, function (ret) {
                if(ret.success) {
                    _reload();
                    //SysMsg.success('操作成功。', '成功');
                }
            });
        });
    }

    function _reload() {

        $.hashchange();
    }

    glyph_opts = {
        map: {
            doc: "glyphicon glyphicon-file",
            docOpen: "glyphicon glyphicon-file",
            checkbox: "glyphicon glyphicon-unchecked",
            checkboxSelected: "glyphicon glyphicon-check",
            checkboxUnknown: "glyphicon glyphicon-share",
            dragHelper: "glyphicon glyphicon-play",
            dropMarker: "glyphicon glyphicon-arrow-right",
            error: "glyphicon glyphicon-warning-sign",
            expanderClosed: "fa fa-plus",
            expanderLazy: "fa fa-plus",  // glyphicon-expand
            expanderOpen: "fa fa-minus",  // glyphicon-collapse-down
            folder: "glyphicon glyphicon-folder-close",
            folderOpen: "glyphicon glyphicon-folder-open",
            loading: "glyphicon glyphicon-refresh"
        }
    };
    $(function(){

        $("#treetable").fancytree({
            extensions: ["edit", "glyph", "table"],
            glyph: glyph_opts,
            source: {url: "${ctx}/location_node", debugDelay: 200},
            table: {
                nodeColumnIdx: 0
            },
            lazyLoad: function(event, data) {

                data.result = {url: "${ctx}/location_node?parentCode="+data.node.data.code, debugDelay: 200};
            },
            postProcess: function(event, data) {
                var orgResponse = data.response;
                if( orgResponse.success) {
                    data.result = orgResponse.result;
                } else {
                    data.result = {
                        error: "ERROR #" + orgResponse.msg
                    }
                }
            },
            renderColumns: function(event, data) {
                var node = data.node,
                 $tdList = $(node.tr).find(">td");
                $tdList.eq(1).html(node.data.code);
                $tdList.eq(2).html("<a class='btn btn-default btn-xs' onclick='_au("+node.data.id+")'><i class='fa fa-edit'></i> 编辑</a> &nbsp;&nbsp;" +
                "<a class='btn btn-xs btn-success' onclick='_appendChild("+node.data.code+")'><i class='fa fa-plus'></i> 添加子节点</a>&nbsp;&nbsp;" +
                "<a class='btn btn-xs btn-danger' onclick='_del("+node.data.id+")'><i class='fa fa-trash'></i> 删除</a>");
            }
        });
    });
</script>