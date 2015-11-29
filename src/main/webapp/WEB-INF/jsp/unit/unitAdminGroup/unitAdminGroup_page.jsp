<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="unitAdminGroup:edit">
                    <a class="btn btn-info btn-sm" onclick="_au()"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                </div>
            <h4>&nbsp;</h4>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
							<th>名称</th>
							<th>应换届时间</th>
							<th>实际换届时间</th>
							<th>任命时间</th>
                            <th>上一届</th>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${unitAdminGroups}" var="unitAdminGroup" varStatus="st">
                        <tr <c:if test="${unitAdminGroup.isPresent}">class="success" </c:if>>
								<td><c:if test="${unitAdminGroup.isPresent}">
                                    <span class="label label-sm label-primary arrowed-in arrowed-in-right">现任班子</span>
                                </c:if>${unitAdminGroup.name}</td>
                                <td>${cm:formatDate(unitAdminGroup.tranTime,'yyyy-MM-dd')}</td>
                                <td>${cm:formatDate(unitAdminGroup.actualTranTime,'yyyy-MM-dd')}</td>
                                <td>${cm:formatDate(unitAdminGroup.appointTime,'yyyy-MM-dd')}</td>
                                <td>${unitAdminGroup.fid}</td>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="unitAdminGroup:edit">
                                    <button onclick="_au(${unitAdminGroup.id})" class="btn btn-mini">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                    <button onclick="unitAdmin_page(${unitAdminGroup.id})" class="btn btn-primary btn-mini">
                                        <i class="fa fa-user"></i> 编辑成员
                                    </button>
                                     <shiro:hasPermission name="unitAdminGroup:del">
                                    <button class="btn btn-danger btn-mini" onclick="_del(${unitAdminGroup.id})">
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
                                    <wo:page commonList="${commonList}" uri="${ctx}/unitAdminGroup_page" target="#page-content" pageNum="5"
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

    function unitAdmin_page(groupId){
        var url = "${ctx}/unitAdmin_page?groupId="+groupId;
        loadModal(url, 1000);
    }


    function _au(id) {
        var url = "${ctx}/unitAdminGroup_au?unitId=${param.unitId}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url);
    }

    function _del(id){
        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/unitAdminGroup_del", {id: id}, function (ret) {
                    if (ret.success) {
                        _reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    function _reload(){
        $("#modal").modal('hide');
        $("#view-box .tab-content").load("${ctx}/unitAdminGroup_page?${pageContext.request.queryString}");
    }
    
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('#searchForm [data-rel="select2-ajax"]').select2({
        ajax: {
            dataType: 'json',
            delay: 300,
            data: function (params) {
                return {
                    searchStr: params.term,
                    pageSize: 10,
                    pageNo: params.page
                };
            },
            processResults: function (data, params) {
                params.page = params.page || 1;
                return {results: data.options,  pagination: {
                    more: (params.page * 10) < data.totalCount
                }};
            },
            cache: true
        }
    });
</script>
</div>