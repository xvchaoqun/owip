<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="col-sm-10">

            <a href="javascript:au()" class="btn btn-success btn-sm"><i class="fa fa-plus"></i> 添加角色</a>
            <div class="space-4"></div>
            <table class="table table-bordered table-striped table-condensed">
                <thead>
                <tr>
                    <th>名称</th>
                    <th>描述</th>
                    <th></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${sysRoles}" var="sysRole" varStatus="st">
                    <tr>
                        <td>${sysRole.role }</td>
                        <td>${sysRole.description }</td>
                        <td>
                            <div class="buttons">
                                <button class="btn btn-warning btn-mini" onclick="au(${sysRole.id})">
                                    <i class="fa fa-edit"></i>  更新权限
                                </button>
                                <a href="javascript:;" onclick="del(${sysRole.id})" class="btn btn-danger btn-mini">
                                    <i class="fa fa-times"></i> 删除</a>
                            </div>

                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="text-center">
                <div class="pagination pagination-centered">
                    共 ${commonList.recNum} 条记录
                    <c:if test="${!empty commonList && commonList.pageNum>1 }">
                        <wo:page commonList="${commonList}" uri="${ctx}/sysRole_page" target="#page-content" pageNum="5"
                                 model="3"/>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>


<script>

    function au(id) {

        url = "${ctx}/sysRole_au";
        if (id > 0)
            url += "?id=" + id;

        loadModal(url);
    }

    function del(id, type) {
        bootbox.confirm("确定删除该角色吗？", function (result) {
            if (result) {
                $.post("${ctx}/sysRole_del", {id: id}, function (ret) {
                    if(ret.success) {
                        _reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    function _search() {

        _tunePage(1, "", "${ctx}/sysRole_page", "#page-content", "", "&" + $("#searchForm").serialize());
    }

    function _reset() {

        _tunePage(1, "", "${ctx}/sysRole_page", "#page-content", "", "");
    }

    function _reload() {
        $("#modal").modal('hide');
        $("#page-content").load("${ctx}/sysRole_page?${cm:encodeQueryString(pageContext.request.queryString)}");
    }
</script>
