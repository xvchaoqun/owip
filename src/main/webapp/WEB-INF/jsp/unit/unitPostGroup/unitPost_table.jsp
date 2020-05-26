<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=SystemConstants.UNIT_POST_STATUS_NORMAL%>" var="UNIT_POST_STATUS_NORMAL"/>
<div id="unitPostDiv">
    <c:if test="${type=='edit'}">
        <form class="form-inline search-form" id="searchForm_popup">
            <input type="hidden" name="id" value="${param.id}">
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
           <%-- <div class="form-group">
                <label>所属单位</label>
                <select name="unitId" data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                        data-placeholder="请选择所属内设机构">
                    <option value="${unit.id}" delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                </select>
                <script>
                    $.register.del_select($("#searchForm select[name=unitId]"), 250)
                </script>
            </div>--%>
            <c:set var="_query" value="${not empty param.name || not empty param.code}"/>
            <div  class="form-group">
                <button type="button" data-url="${ctx}/unitPostGroup_table?type=${param.type}"
                        data-target="#unitPostDiv" data-form="#searchForm_popup"
                        class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</button>
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
    </c:if>
    <c:if test="${fn:length(unitPosts)>0}">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-actived table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <c:if test="${type=='edit'}">
                        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                            <th class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" class="ace checkAll" name="checkAll">
                                    <span class="lbl"></span>
                                </label>
                            </th>
                        </shiro:lacksPermission>
                    </c:if>
                    <th nowrap>岗位编号</th>
                    <th nowrap>岗位名称</th>
                    <th nowrap>单位名称</th>
                    <th nowrap>分管工作</th>
                    <th nowrap>岗位级别</th>
                    <th nowrap>职务类别</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${unitPosts}" var="unitPost" varStatus="st">
                    <tr>
                        <c:if test="${type=='edit'}">
                            <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                                <td class="center">
                                    <label class="pos-rel">
                                        <input type="checkbox" name="unitPostId"
                                               value="${unitPost.id}" class="ace" <%--${unitPost.groupId==param.id?"checked":""}--%>>
                                        <span class="lbl"></span>
                                    </label>
                                </td>
                            </shiro:lacksPermission>
                        </c:if>
                            <%-- <c:set value="${cm:getDispatch(dispatchCadre.dispatchId)}" var="dispatch"/>--%>
                        <td nowrap>${unitPost.code}</td>
                        <td nowrap><span class="${unitPost.status != UNIT_POST_STATUS_NORMAL?'delete':''}">${unitPost.name}</span> </td>
                        <td nowrap>${cm:getUnitById(unitPost.unitId).name}</td>
                        <td nowrap>${unitPost.job}</td>
                        <td nowrap>${cm:getMetaType(unitPost.adminLevel).name}</td>
                        <td nowrap>${cm:getMetaType(unitPost.postClass).name}</td>
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
        </c:if>
    </c:if>
    <c:if test="${fn:length(unitPosts)==0}">
        <div class="well well-lg center">
            <h4 class="green lighter">没有该岗位信息</h4>
        </div>
    </c:if>
</div>

<script>

    $("input[name='unitPostId'],input[name='checkAll']").change(function(){
        $("input[name='unitPostId']").each(function () {
            var postId = $(this).val();
            if (this.checked) { //被选中的复选框
                if (idArray.toString() == "") {
                    idArray.push(postId);
                } else {  //判断id数组中是否含有以前存入的元素，没有则添加
                    if ($.inArray(postId, idArray) < 0) {
                        idArray.push(postId);
                    }
                }
            } else {  //未被选中的复选框
                if ($.inArray(postId, idArray) > -1) {
                    idArray.splice($.inArray(postId, idArray), postId.length);
                }
            }
        });
        console.log(idArray);
    });

    function setCheckBox() {
        $("input[name='unitPostId']").each(function () {
            var postId = $(this).val();
            if ($.inArray(postId, idArray)> -1) {
                this.checked = true;
            }
        })
    }
    setCheckBox();
</script>