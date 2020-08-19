<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${sysResource.name}
    <c:if test="${not empty sysResource.permission}">（${sysResource.permission}）</c:if> - 所属角色</h3>
</div>
<div class="modal-body popup-jqgrid" style="padding-top: 0">
    <form class="form-inline search-form" id="searchForm_popup" method="post"
          action="${ctx}/sysResource_updateRole?addOrDel=1">
        <input type="hidden" name="resourceId" value="${param.resourceId}">
        <select class="form-control" name="roleId"
                data-rel="select2"
                data-width="450"
                data-placeholder="请选择">
            <option></option>
            <c:forEach items="${sysRoleMap}" var="sysRole">
                <option value="${sysRole.key}">${sysRole.value.name} - ${sysRole.value.code}</option>
            </c:forEach>
        </select>
        <button id="addRoleBtn" type="button"
                data-loading-text="<i class='fa fa-spinner fa-spin '></i> 添加中"
                class="btn btn-success btn-sm"><i class="fa fa-plus"></i> 添加
        </button>
        <button type="button" class="jqBatchBtn btn btn-danger btn-sm"
            data-url="${ctx}/sysResource_updateRole"
                data-grid-id="#jqGrid_popup"
                data-querystr="&addOrDel=0&resourceId=${param.resourceId}"
                data-callback="_reload_popup" data-msg="确定删除该角色？" data-title="删除资源角色">
                  <i class="fa fa-times"></i> 删除</button>
    </form>
    <table id="jqGrid_popup" class="table-striped"></table>
    <div id="jqGridPager_popup"></div>
</div>
<script>

    $("#addRoleBtn").click(function(){$("#searchForm_popup").submit();return false;});
    $("#searchForm_popup").validate({
        submitHandler: function (form) {
            var $btn = $("#addRoleBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        _reload_popup()
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    function _reload_popup(){
        $("#jqGrid_popup").trigger("reloadGrid");
    }
    $("#searchForm_popup select").select2();
    //$.register.user_select($('#searchForm_popup select[name=userId]'));
    //$('#searchForm_popup [data-rel="select2"]').select2();
    $("#jqGrid_popup").jqGrid({
       /* multiselect: false,*/
        height: 390,
        width: 720,
        rowNum: 10,
        ondblClickRow: function () {
        },
        pager: "jqGridPager_popup",
        url: "${ctx}/sysRole_data?callback=?&resourceId=${param.resourceId}&${cm:encodeQueryString(pageContext.request.queryString)}",
        colModel: [
            {label: '角色代码', name: 'code', width: 150},
            {label: '角色名称', name: 'name', width: 200},
            {label: '备注', name: 'remark', width: 300, align: 'left'},
          /*  {
                "name": "_del", "label": "删除", "width": 80, formatter: function (cellvalue, options, rowObject) {
                    return ('<button class="confirm btn btn-danger btn-xs" ' +
                        'data-url="${ctx}/sysResource_updateRole?addOrDel=0&roleId={0}&resourceId=${param.resourceId}" ' +
                        'data-callback="_reload_popup" data-msg="确定删除该角色？" data-title="删除资源角色">' +
                        '<i class="fa fa-times"></i> 删除</button>')
                        .format(rowObject.id);
                }
            },*/
        ]
    }).jqGrid("setFrozenColumns");
    $.initNavGrid("jqGrid_popup", "jqGridPager_popup");
</script>