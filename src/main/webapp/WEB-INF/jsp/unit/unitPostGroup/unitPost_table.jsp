<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=SystemConstants.UNIT_POST_STATUS_NORMAL%>" var="UNIT_POST_STATUS_NORMAL"/>
<c:set var="toSelectPosts" value="${(empty unitPostGroup.postIds || param.type=='edit')
&& cm:isPermitted('unitPostGroup:edit')}"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${unitPostGroup.name}（包含的岗位列表）</h3>
</div>
<div class="modal-body">
    <c:if test="${toSelectPosts}">
        <form class="form-inline search-form" id="searchForm_popup" style="float: left">
            <input type="hidden" name="id" value="${param.id}">
            <input type="hidden" name="showSelected" value="${showSelected?1:0}">
            <div class="form-group">
                <label>岗位名称</label>
                <input class="form-control search-query" name="name" type="text" value="${param.name}"
                       placeholder="请输入岗位名称">
            </div>
            <div class="form-group">
                <label>岗位编号</label>
                <input class="form-control search-query" name="code" type="text" value="${param.code}"
                       placeholder="请输入岗位编号">
            </div>
            <c:set var="_query" value="${not empty param.name || not empty param.code}"/>
            <div class="form-group">
                <button type="button" data-url="${ctx}/unitPostGroup_table?type=${param.type}"
                        data-target="#unitPostDiv" data-form="#searchForm_popup"
                        class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找
                </button>
                <c:if test="${_query}">
                    <button type="button"
                            data-url="${ctx}/unitPostGroup_table?type=${param.type}"
                            data-querystr="id=${param.id}"
                            data-target="#unitPostDiv"
                            class="reloadBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
            </div>
        </form>
        <div class="type-select">
                <span class="typeCheckbox ${showSelected?"checked":""}">
                <input ${showSelected?"checked":""} type="checkbox"
                                                             value="1"> 只显示已包含岗位
                </span>
        </div>
    </c:if>
    <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <c:if test="${toSelectPosts}">
                <th class="center">
                    <label class="pos-rel">
                        <input type="checkbox" class="ace checkAll" name="checkAll">
                        <span class="lbl"></span>
                    </label>
                </th>
            </c:if>
            <th nowrap>岗位编号</th>
            <th nowrap>岗位名称</th>
            <th nowrap>分管工作</th>
            <th nowrap>所在单位</th>
            <th nowrap>岗位级别</th>
            <%--<th nowrap>职务类别</th>--%>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${unitPosts}" var="unitPost" varStatus="st">
            <tr>
                <c:if test="${toSelectPosts}">
                    <td class="center">
                        <label class="pos-rel">
                            <input type="checkbox" name="unitPostId"
                                   value="${unitPost.id}"
                                   class="ace" ${(unitPost.groupId>0 && unitPost.groupId!=cm:toInt(param.id))?"disabled":""}>
                            <span class="lbl"></span>
                        </label>
                    </td>
                </c:if>
                    <%-- <c:set value="${cm:getDispatch(dispatchCadre.dispatchId)}" var="dispatch"/>--%>
                <td nowrap>${unitPost.code}</td>
                <td title="${unitPost.name}">
                    <div style="width: 250px;overflow: hidden;white-space: nowrap"><span class="${unitPost.status != UNIT_POST_STATUS_NORMAL?'delete':''}">${unitPost.name}</span></div>
                </td>
                <td nowrap>${unitPost.job}</td>
                <td nowrap>${cm:getUnitById(unitPost.unitId).name}</td>
                <td nowrap>${cm:getMetaType(unitPost.adminLevel).name}</td>
                <%--<td nowrap>${cm:getMetaType(unitPost.postClass).name}</td>--%>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${!empty commonList && commonList.pageNum>1 }">
        <wo:page commonList="${commonList}" uri="${ctx}/unitPostGroup_table?id=${param.id}&type=${param.type}"
                 target="#unitPostDiv"
                 pageNum="5"
                 model="3"/>
    </c:if>
</div>
<shiro:hasPermission name="unitPostGroup:edit">
<div class="modal-footer">
    <c:if test="${toSelectPosts}">
        <c:if test="${toSelectPosts && not empty unitPostGroup.postIds}">
        <button class="popupBtn btn btn-default"
                data-url="${ctx}/unitPostGroup_addPost?id=${param.id}"
                data-width="1000"><i class="fa fa-reply"></i> 返回</button>
        </c:if>
        <button onclick="addPost()" class="btn btn-success"><i class="fa fa-save"></i> 保存</button>
    </c:if>
    <c:if test="${!toSelectPosts}">
        <button class="popupBtn btn btn-primary"
                data-url="${ctx}/unitPostGroup_addPost?id=${param.id}&type=edit"
                data-width="1000"><i class="fa fa-edit"></i> 编辑
        </button>
    </c:if>
</div>
</shiro:hasPermission>
<script>
    initSelectedPostIds();
    $(":checkbox", ".typeCheckbox").click(function () {
        $("#searchForm_popup input[name=showSelected]").val($(this).prop("checked") ? 1 : 0);
        $("#searchForm_popup .jqSearchBtn").click();
    })
</script>