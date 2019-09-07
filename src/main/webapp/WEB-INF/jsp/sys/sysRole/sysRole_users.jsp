<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${sysRole.name} - 权限拥有人</h3>
</div>
<div class="modal-body popup-jqgrid">
    <form class="form-inline search-form" id="searchForm_popup">
        <div class="form-group">
            <label>姓名</label>
            <select name="userId" data-rel="select2-ajax"
                    data-ajax-url="${ctx}/sysUser_selects"
                    data-placeholder="请输入账号或姓名或教工号">
                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
            </select>
        </div>
        <div class="form-group">
            <label>账号类别</label>
            <select name="type" data-placeholder="请选择" data-width="120" data-ref="select2">
                <option></option>
                <c:forEach items="${USER_TYPE_MAP}" var="userType">
                    <option value="${userType.key}">${userType.value}</option>
                </c:forEach>
            </select>
            <script>
                $("#searchForm_popup select[name=type]").val('${param.type}');
            </script>
        </div>
        <div class="form-group">
            <label>账号来源</label>
            <select name="source" data-placeholder="请选择" data-width="120" data-ref="select2">
                <option></option>
                <c:forEach items="<%=SystemConstants.USER_SOURCE_MAP%>" var="userSource">
                    <option value="${userSource.key}">${userSource.value}</option>
                </c:forEach>
            </select>
            <script>
                $("#searchForm_popup select[name=source]").val('${param.source}');
            </script>
        </div>
        <c:set var="_query" value="${not empty param.userId || not empty param.type || not empty param.source}"/>
        <div class="form-group">
            <button type="button" data-url="${ctx}/sysRole_users?locked=0&roleId=${param.roleId}"
                    data-target="#modal .modal-content" data-form="#searchForm_popup"
                    class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找
            </button>
            <c:if test="${_query}">
                <button type="button"
                        data-url="${ctx}/sysRole_users?locked=0&roleId=${param.roleId}"
                        data-target="#modal .modal-content"
                        class="reloadBtn btn btn-warning btn-sm">
                    <i class="fa fa-reply"></i> 重置
                </button>
            </c:if>
        </div>
        <c:if test="${!sysRole.isSysHold}">
         <div class="buttons pull-right">
            <button type="button" onclick="_addRole(this)" data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中"
                    class="jqSearchBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加
            </button>&nbsp;
            <button type="button" class="jqBatchBtn btn btn-danger btn-sm"
                                    data-grid-id="#jqGrid_popup"
                                    data-callback="_reload2"
                                   data-url="${ctx}/sysUser_updateRole?roleId=${param.roleId}&del=1" data-title="删除人员"
                                   data-msg="确定删除这{0}个人员吗？"><i class="fa fa-times"></i> 删除</button>
         </div>
        </c:if>
    </form>
    <table id="jqGrid_popup" class="table-striped"></table>
    <div id="jqGridPager_popup"></div>
</div>
<jsp:include page="../sysUser/sysUser_colModel.jsp"/>
<script>

    function _addRole(btn) {

        var userId = $('#searchForm_popup select[name=userId]').val();
        if(userId==''){
             $.tip({
                $target: $('#searchForm_popup select[name=userId]').closest("div").find(".select2-container"),
                at: 'top center', my: 'bottom center', type: 'info',
                msg: "请选择"
            });
            return;
        }
        var $btn = $(btn).button('loading');
        $.post("${ctx}/sysUser_updateRole?roleId=${param.roleId}",{'ids[]':userId}, function (ret) {
            if(ret.success){
                _reload2()
            }
             $btn.button('reset');
        })
    }

    function _reload2() {
        $("#jqGrid_popup").trigger("reloadGrid");
    }
    $("#searchForm_popup [data-ref=select2]").select2();
    $.register.user_select($('#searchForm_popup select[name=userId]'));
    //$('#searchForm_popup [data-rel="select2"]').select2();
    $("#jqGrid_popup").jqGrid({
        multiselect: ${!sysRole.isSysHold},
        height: 390,
        width: 970,
        rowNum: 10,
        ondblClickRow: function () {
        },
        pager: "jqGridPager_popup",
        url: "${ctx}/sysUser_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}",
        colModel: colModel
    }).jqGrid("setFrozenColumns");
    $.initNavGrid("jqGrid_popup", "jqGridPager_popup");
    $.register.fancybox();
</script>