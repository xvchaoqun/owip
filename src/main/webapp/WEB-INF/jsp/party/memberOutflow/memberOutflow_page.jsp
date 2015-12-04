<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_TYPE_MAP" value="<%=SystemConstants.MEMBER_TYPE_MAP%>"/>
<c:set var="OR_STATUS_MAP" value="<%=SystemConstants.OR_STATUS_MAP%>"/>

            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects"
                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}</option>
                </select>
                <select data-rel="select2" name="type" data-placeholder="请选择类别">
                    <option></option>
                    <c:forEach items="${MEMBER_TYPE_MAP}" var="_type">
                        <option value="${_type.key}">${_type.value}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#searchForm select[name=type]").val(${param.type});
                </script>

                <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                        name="partyId" data-placeholder="请选择分党委">
                    <option value="${party.id}">${party.name}</option>
                </select>
                <span style="${(empty branch)?'display: none':''}" id="branchDiv">
                <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects"
                        name="branchId" data-placeholder="请选择党支部">
                    <option value="${branch.id}">${branch.name}</option>
                </select>
                </span>
                <script>
                    register_party_branch_select($("#searchForm"), "branchDiv",
                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}" );
                </script>
                <a class="btn btn-sm" onclick="_search()"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.userId ||not empty param.type ||not empty param.partyId ||not empty param.branchId || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="btn btn-warning btn-sm" onclick="_reset()">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="memberOutflow:edit">
                    <a class="btn btn-info btn-sm" onclick="_au()"><i class="fa fa-plus"></i> 添加流出党员</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="btn btn-success btn-sm tooltip-success" onclick="_export()"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    </c:if>
                </div>
            </mytag:sort-form>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace checkAll">
                                <span class="lbl"></span>
                            </label>
                        </th>
							<th>用户</th>
							<th>所属组织机构</th>
							<th>原职业</th>
							<th>外出流向</th>
							<th>流出时间</th>
							<th>流出省份</th>
							<th>流出原因</th>
							<th>是否持有《中国共产党流动党员活动证》</th>
							<th>组织关系状态</th>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${memberOutflows}" var="memberOutflow" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${memberOutflow.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td>${cm:getUserById(memberOutflow.userId).realname}</td>
								<td>
                                        ${memberOutflow.partyName}
                                    <c:if test="${not empty memberOutflow.branchName}">
                                        -${memberOutflow.branchName}
                                    </c:if>
                                </td>
								<td>${jobMap.get(memberOutflow.originalJob).name}</td>
								<td>${flowDirectionMap.get(memberOutflow.direction).name}</td>
								<td>${cm:formatDate(memberOutflow.flowTime,'yyyy-MM-dd')}</td>
								<td>${locationMap.get(memberOutflow.province).name}</td>
								<td>${memberOutflow.reason}</td>
								<td>${memberOutflow.hasPapers?"是":"否"}</td>
								<td>${OR_STATUS_MAP.get(memberOutflow.orStatus)}</td>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="memberOutflow:edit">
                                    <button onclick="_au(${memberOutflow.id})" class="btn btn-mini">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="memberOutflow:del">
                                    <button class="btn btn-danger btn-mini" onclick="_del(${memberOutflow.id})">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
                                </div>
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
                                    <wo:page commonList="${commonList}" uri="${ctx}/memberOutflow_page" target="#home4" pageNum="5"
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
<script>

    function _au(id) {
        url = "${ctx}/memberOutflow_au?cadreId=${param.cadreId}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url, 800);
    }

    function _del(id){
        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/memberOutflow_del", {id: id}, function (ret) {
                    if (ret.success) {
                        _reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }
    function _export() {
        location.href = "${ctx}/memberOutflow_export?" + $("searchForm").serialize();
    }
    function _search() {
        _tunePage(1, "", "${ctx}/memberOutflow_page", "#home4", "", "&" + $("#searchForm").serialize());
    }

    function _reset() {
        _tunePage(1, "", "${ctx}/memberOutflow_page", "#home4", "", "");
    }
    function _reload(){
        $("#modal").modal('hide');
        $("#home4").load("${ctx}/memberOutflow_page?${pageContext.request.queryString}");
    }

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_user_select($('#searchForm select[name=userId]'));
</script>