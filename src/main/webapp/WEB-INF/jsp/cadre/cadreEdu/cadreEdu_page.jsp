<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

        <!-- PAGE CONTENT BEGINS -->
            <div class="buttons pull-right">
                <shiro:hasPermission name="cadreEdu:edit">
                <a class="btn btn-info btn-sm" onclick="_au()" data-width="900"><i class="fa fa-plus"></i> 添加学习经历</a>
                </shiro:hasPermission>
                <c:if test="${commonList.recNum>0}">
                <shiro:hasPermission name="cadreEdu:del">
                 <a class="btn btn-danger btn-sm" onclick="_batchDel()"><i class="fa fa-times"></i> 批量删除</a>
                 </shiro:hasPermission>
                </c:if>
            </div>
            <h4>&nbsp;</h4>
            <div class="space-4"></div>
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
							<th>所属干部</th>
							<th>学历</th>
							<th>毕业学校</th>
							<th>院系</th>
							<th>入学时间</th>
							<th>毕业时间</th>
							<th>学位</th>
                        <shiro:hasPermission name="cadreEdu:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap class="hidden-480">排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${cadreEdus}" var="cadreEdu" varStatus="st">
                        <tr>
								<td>${cm:getUserById(cadreMap.get(cadreEdu.cadreId).userId).realname}</td>
								<td>${eduMap.get(cadreEdu.eduId).name}</td>
								<td>${cadreEdu.school}</td>
								<td>${cadreEdu.dep}</td>
								<td>${cm:formatDate(cadreEdu.enrolTime,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(cadreEdu.finishTime,'yyyy-MM-dd')}</td>
								<td>${cadreEdu.degree}</td>
                            <shiro:hasPermission name="cadreEdu:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td class="hidden-480">
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${cadreEdu.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${cadreEdu.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="cadreEdu:edit">
                                    <button onclick="_au(${cadreEdu.id})" class="btn btn-mini">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="cadreEdu:del">
                                    <button  onclick="_del(${cadreEdu.id})" class="btn btn-danger btn-mini">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
                                </div>
                                <div class="hidden-md hidden-lg">
                                    <div class="inline pos-rel">
                                        <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                            <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                        </button>

                                        <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                            <%--<li>
                                            <a href="#" class="tooltip-info" data-rel="tooltip" title="查看">
                                                        <span class="blue">
                                                            <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                                        </span>
                                            </a>
                                        </li>--%>
                                            <shiro:hasPermission name="cadreEdu:edit">
                                            <li>
                                                <a href="#" data-id="${cadreEdu.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="cadreEdu:del">
                                            <li>
                                                <a href="#" data-id="${cadreEdu.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
                                                    <span class="red">
                                                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                        </ul>
                                    </div>
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
                                    <wo:page commonList="${commonList}" uri="${ctx}/cadreEdu_page" target="#view-box .tab-content" pageNum="5"
                                             model="3"/>
                                </ul>
                            </div>
                        </div>
                    </div>
                </c:if>
<script>

    function _au(id) {
        url = "${ctx}/cadreEdu_au?cadreId=${param.cadreId}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url, 900);
    }

    function _del(id){
        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/cadreEdu_del", {id: id}, function (ret) {
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
        $("#view-box .tab-content").load("${ctx}/cadreEdu_page?${pageContext.request.queryString}");
    }

    function _batchDel(){

        var ids = $.map($("#view-box .table td :checkbox:checked"),function(item, index){
            return $(item).val();
        });
        if(ids.length==0){
            toastr.warning("请选择行", "提示");
            return ;
        }
        bootbox.confirm("确定删除这"+ids.length+"条数据？",function(result){
            if(result){
                $.post("${ctx}/cadreEdu_batchDel",{ids:ids},function(ret){
                    if(ret.success) {
                        _reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
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