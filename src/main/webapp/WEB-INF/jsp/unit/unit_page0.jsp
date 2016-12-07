<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
        <div class="tabbable">
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <li  class="<c:if test="${status==1}">active</c:if>">
                    <a href="?status=1"><i class="fa fa-circle-o-notch fa-spin"></i> 正在运转单位</a>
                </li>
                <li  class="<c:if test="${status==2}">active</c:if>">
                    <a href="?status=2"><i class="fa fa-history"></i> 历史单位</a>
                </li>
            </ul>

            <div class="tab-content">
                <div id="home4" class="tab-pane in active">
        <div class="myTableDiv"
             data-url-au="${ctx}/unit_au"
             data-url-page="${ctx}/unit_page"
             data-url-del="${ctx}/unit_del"
             data-url-bd="${ctx}/unit_batchDel"
             data-url-co="${ctx}/unit_changeOrder?status=${status}"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <input type="hidden" name="status" value="${status}">
                <input class="form-control search-query" name="code" type="text" value="${param.code}"
                       placeholder="请输入单位编号">
                <input class="form-control search-query" name="name" type="text" value="${param.name}"
                       placeholder="请输入单位名称">
                <select data-rel="select2" name="typeId" data-placeholder="请选择单位类型">
                    <option></option>
                    <c:forEach var="unitType" items="${unitTypeMap}">
                        <option value="${unitType.value.id}">${unitType.value.name}</option>
                    </c:forEach>
                </select>
                <script type="text/javascript">
                    $("#searchForm select[name=typeId]").val('${param.typeId}');
                </script>
                <a class="searchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.code ||not empty param.name ||not empty param.typeId || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm" data-querystr="status=${status}">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <c:if test="${status==1}">
                    <shiro:hasPermission name="unit:edit">
                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加单位</a>
                    </shiro:hasPermission>
                    </c:if>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出单位</a>
                    <shiro:hasPermission name="unit:del">
                    <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-trash"></i> 删除</a>
                     </shiro:hasPermission>
                    </c:if>
                </div>
            </mytag:sort-form>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
            <div class="table-container">
                <table style="min-width: 1300px" class="overflow-y table table-actived table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace checkAll">
                                <span class="lbl"></span>
                            </label>
                        </th>
                            <mytag:sort-th field="code">单位编号</mytag:sort-th>
							<th>单位名称</th>
							<th class="hidden-480 hidden-xs">单位类型</th>
                            <mytag:sort-th field="work_time" css="hidden-480 hidden-xs">成立时间</mytag:sort-th>
							<th class="hidden-480 hidden-xs">备注</th>
                        <shiro:hasPermission name="unit:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap class="hidden-480 hidden-xs">排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${units}" var="unit" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${unit.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td >${unit.code}</td>
								<td >
                                    <a href="javascript:;" class="openView" data-url="${ctx}/unit_view?id=${unit.id}">
                                            ${unit.name}
                                    </a>
								</td>
								<td  class="hidden-480 hidden-xs">${unitTypeMap.get(unit.typeId).name}</td>
								<td  class="hidden-480 hidden-xs">${cm:formatDate(unit.workTime, "yyyy-MM-dd")}</td>

								<td  class="hidden-480 hidden-xs">${unit.remark}</td>
                            <shiro:hasPermission name="unit:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td  class="hidden-480 hidden-xs">
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${unit.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${unit.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>
                            <td >
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="unit:edit">
                                    <button data-id="${unit.id}" class="editBtn btn btn-default btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                    <c:if test="${status==1}">
                                    <shiro:hasPermission name="unit:abolish">
                                        <button data-id="${unit.id}" class="abolishBtn btn btn-warning btn-xs">
                                            <i class="fa fa-recycle"></i> 转移
                                        </button>
                                    </shiro:hasPermission>
                                    </c:if>
                                    <shiro:hasPermission name="unit:history">
                                        <button class="historyBtn btn btn-primary btn-xs" data-id="${unit.id}">
                                            <i class="fa fa-history"></i> 编辑历史单位
                                        </button>
                                    </shiro:hasPermission>
                                     <%--<shiro:hasPermission name="unit:del">
                                        <button class="delBtn btn btn-danger btn-xs" data-id="${unit.id}">
                                            <i class="fa fa-trash"></i> 删除
                                        </button>
                                      </shiro:hasPermission>--%>
                                </div>
                                <div class="hidden-md hidden-lg">
                                    <div class="inline pos-rel">
                                        <button class="btn btn-xser btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                            <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                        </button>

                                        <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                            <shiro:hasPermission name="unit:edit">
                                            <li>
                                                <a href="#" data-id="${unit.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="unit:del">
                                            <li>
                                                <a href="#" data-id="${unit.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                </div>
                <wo:page commonList="${commonList}" uri="${ctx}/unit_page" target="#page-content" pageNum="5"
                         model="3"/>
            </c:if>
            <c:if test="${commonList.recNum==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </div>
    </div>
    </div></div></div>
    </div>
    <div id="item-content">
        </div>
</div>

<script>
    stickheader();

    /*$(".tabbable li a").click(function(){
        $this = $(this);
        $(".tabbable li").removeClass("active");
        $this.closest("li").addClass("active");
        $(".myTableDiv #searchForm input[name=status]").val($this.data("status"));
        $(".myTableDiv .searchBtn").click();
    });*/

    // 编辑历史单位
    $(".myTableDiv .historyBtn").click(function(){
        loadModal("${ctx}/unit_history?id="+$(this).data("id"));
    });

    $(".abolishBtn").click(function(){
        var id = $(this).data("id");
        bootbox.confirm("确定转移该单位到历史单位吗？", function (result) {
            if (result) {
                $.post("${ctx}/unit_abolish", {id: id}, function (ret) {
                    if (ret.success) {
                        page_reload();
                        //SysMsg.success('操作成功。', '成功');
                    }
                });
            }
        });
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

</script>
