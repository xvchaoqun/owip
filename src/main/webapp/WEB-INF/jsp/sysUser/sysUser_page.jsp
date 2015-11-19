<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="col-sm-12">
                <mytag:sort-form css="form-inline hidden-xs hidden-sm" id="searchForm">

                    <select name="typeId" data-placeholder="请选择类别" class="select2 tag-input-style">
                        <option></option>
                        <c:forEach items="${metaTypeMap}" var="metaType">
                            <option value="${metaType.key}">${metaType.value.name}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#searchForm select[name=typeId]").val('${param.typeId}');
                    </script>
                    <select name="roleId" data-placeholder="请选择角色" class="select2 tag-input-style">
                        <option></option>
                        <c:forEach items="${roleMap}" var="role">
                            <option value="${role.key}">${role.value.description}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#searchForm select[name=roleId]").val('${param.roleId}');
                    </script>
                    <select name="locked" data-placeholder="状态">
                        <option></option>
                        <option value="0">正常账号</option>
                        <option value="1">禁用账号</option>
                    </select>
                    <script>
                        $("#searchForm select[name=locked]").val('${param.locked}');
                    </script>
                    <input class="form-control search-query" name="username" type="text" value="${param.username}"
                           placeholder="请输入账号">
                    <a class="btn btn-sm" onclick="_search()"><i class="fa fa-search"></i> 查找</a>
                    <c:set var="_query" value="${not empty param.username ||not empty param.roleId ||not empty param.typeId || not empty param.locked || not empty param.sort}"/>
                    <c:if test="${_query}">
                        <button type="button" class="btn btn-warning btn-sm" onclick="_reset()">
                            <i class="fa fa-reply"></i> 重置
                        </button>
                    </c:if>
                    <div class="vspace-12"></div>
                    <div class="buttons pull-right">
                        <a onclick="au()" class="btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加账号</a>
                        <a onclick="_export()" class="btn btn-success btn-sm"><i class="fa fa-download"></i> 导出账号</a>
                     </div>
                </mytag:sort-form>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
            <table class="table table-striped table-bordered table-hover table-condensed">
                <thead>
                <tr>
                    <th class="center">
                        <label class="pos-rel">
                            <input type="checkbox" class="ace">
                            <span class="lbl"></span>
                        </label>
                    </th>
                    <mytag:sort-th field="username">账号</mytag:sort-th>
                    <mytag:sort-th field="code">学工号</mytag:sort-th>
                    <th class="hidden-480">类别</th>
                    <th class="hidden-480">真实姓名</th>
                    <th class="hidden-480">身份证</th>
                    <th class="hidden-480">手机</th>
                    <th class="hidden-480">邮箱</th>
                    <th class="hidden-480">账号来源</th>
                    <th>拥有角色</th>
                    <th class="hidden-480">创建时间</th>
                    <th class="hidden-480"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sysUsers}" var="sysUser" varStatus="st">
                    <tr class="<c:if test="${sysUser.locked}">danger</c:if> ">
                        <td class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace">
                                <span class="lbl"></span>
                            </label>
                        </td>
                        <td nowrap>${sysUser.username}</td>
                        <td nowrap>${sysUser.code}</td>
                        <td nowrap>${metaTypeMap.get(sysUser.typeId).name}</td>
                        <td nowrap>${sysUser.realname}</td>
                        <td nowrap>${sysUser.idcard}</td>
                        <td nowrap>${sysUser.mobile}</td>
                        <td nowrap>${sysUser.email}</td>
                        <td nowrap>${sourceMap.get(sysUser.sourceId).name}</td>
                        <td>
                            <c:forEach items="${fn:split(sysUser.roleIds,',')}" var="id" varStatus="vs">
                                ${roleMap.get(cm:parseInt(id)).description}
                                <c:if test="${!vs.last}">,</c:if>
                            </c:forEach>
                        </td>
                        <td nowrap class="hidden-480">${cm:formatDate(sysUser.createTime, "yyyy-MM-dd HH:mm")}</td>
                        <td nowrap class="hidden-480">
                            <button onclick="au(${sysUser.id})" class="btn btn-mini">
                                <i class="fa fa-edit"></i> 编辑
                            </button>
                            <button class="btn btn-warning btn-mini" onclick="updateUserRole(${sysUser.id})">
                                <i class="fa fa-pencil"></i> 修改角色
                            </button>
                            <c:if test="${sysUser.locked}">
                            <button onclick="_del('${sysUser.username}', 0)" class="btn btn-success btn-mini">
                                <i class="fa fa-edit"></i> 解禁
                            </button>
                            </c:if>
                            <c:if test="${!sysUser.locked}">
                                <button onclick="_del('${sysUser.username}', 1)" class="btn btn-danger btn-mini">
                                    <i class="fa fa-edit"></i> 禁用
                                </button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
                <c:if test="${!empty commonList && commonList.pageNum>1 }">
                    <div class="row my_paginate_row">
                        <div class="col-xs-6">第${commonList.startPos}-${commonList.endPos}条&nbsp;&nbsp;共${commonList.recNum}条记录</div>
                        <div class="col-xs-6">
                            <div class="my_paginate">
                                <ul class="pagination">
                                    <wo:page commonList="${commonList}" uri="${ctx}/sysUser_page" target="#page-content" pageNum="5"
                                             model="3"/>
                                </ul>
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:if>
            <c:if test="${commonList.recNum==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </div>
    </div>
</div>
<script>

    $(".table th.sortable").click(function(){

        var $this = $(this);
        var order = $this.hasClass("asc")?"desc":"asc";

        $("#searchForm input[name=sort]").val($this.data("field"));
        $("#searchForm input[name=order]").val(order);
        //alert($("div.myTableDiv #searchForm").serialize())
        _tunePage(1, "", "${ctx}/sysUser_page", "#page-content", "", "&" + $("#searchForm").serialize());
    });

    $("#searchForm select").select2();
    function au(id) {

        url = "${ctx}/sysUser_au";
        if (id > 0)
            url += "?id=" + id;

        loadModal(url);
    }

    function _del(username, locked){

        bootbox.confirm("确定"+(locked==0?"解禁":"禁用")+"该账号吗？", function (result) {
            if (result) {
                $.post("${ctx}/sysUser_del", {username: username, locked:locked}, function (ret) {
                    if (ret.success) {
                        _reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    function updateUserRole(id) {

        url = "${ctx}/sysUserRole?id=" + id;

        loadModal(url);
    }
    function _export() {

        location.href = "${ctx}/sysUser_export?" + $("searchForm").serialize();
    }
    function _search() {

        _tunePage(1, "", "${ctx}/sysUser_page", "#page-content", "", "&" + $("#searchForm").serialize());
    }
    function _reset() {

        _tunePage(1, "", "${ctx}/sysUser_page", "#page-content", "", "");
    }
    function _reload() {
        $("#modal").modal('hide');
        $("#page-content").load("${ctx}/sysUser_page?${pageContext.request.queryString}");
    }
</script>
</div>