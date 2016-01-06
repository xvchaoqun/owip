<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="myTableDiv"
             data-url-au="${ctx}/dispatchUnit_au"
             data-url-page="${ctx}/dispatchUnit_page"
             data-url-del="${ctx}/dispatchUnit_del"
             data-url-bd="${ctx}/dispatchUnit_batchDel"
             data-url-co="${ctx}/dispatchUnit_changeOrder"
             data-querystr="${pageContext.request.queryString}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">

                <div class="input-group">
                    <input class="form-control date-picker" placeholder="请选择年份" name="year" type="text"
                           data-date-format="yyyy" data-date-min-view-mode="2" value="${param.year}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
                <select name="unitId" data-rel="select2" data-placeholder="请选择所属单位">
                    <option></option>
                    <c:forEach items="${unitMap}" var="unit">
                        <option value="${unit.key}">${unit.value.name}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#searchForm select[name=unitId]").val('${param.unitId}');
                </script>
                <select data-rel="select2" name="typeId" data-placeholder="请选择单位发文类型">
                    <option></option>
                    <c:forEach var="dispatchUnitType" items="${dispatchUnitTypeMap}">
                        <option value="${dispatchUnitType.value.id}">${dispatchUnitType.value.name}</option>
                    </c:forEach>
                </select>
                <script type="text/javascript">
                    $("#searchForm select[name=typeId]").val('${param.typeId}');
                </script>

                <a class="searchBtn btn btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.year ||not empty param.unitId ||not empty param.typeId || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="dispatchUnit:edit">
                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="dispatchUnit:del">
                    <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-times"></i> 批量删除</a>
                     </shiro:hasPermission>
                    </c:if>
                </div>
            </mytag:sort-form>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
                <table class="table table-actived table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace checkAll">
                                <span class="lbl"></span>
                            </label>
                        </th>
							<th>所属发文</th>
							<th>所属单位</th>
							<th>类型</th>
							<th>年份</th>
							<th>备注</th>
                        <shiro:hasPermission name="dispatchUnit:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap>排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${dispatchUnits}" var="dispatchUnit" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${dispatchUnit.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
                                <td nowrap>${dispatchMap.get(dispatchUnit.dispatchId).code}</td>
								<td nowrap>${unitMap.get(dispatchUnit.unitId).name}</td>
								<td nowrap>${dispatchUnitTypeMap.get(dispatchUnit.typeId).name}</td>
								<td nowrap>${dispatchUnit.year}</td>
								<td>${dispatchUnit.remark}</td>
                            <shiro:hasPermission name="dispatchUnit:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td nowrap>
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${dispatchUnit.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${dispatchUnit.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>
                            <td nowrap>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="dispatchUnit:edit">
                                    <button data-id="${dispatchUnit.id}" class="editBtn btn btn-mini">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                    <shiro:hasPermission name="dispatchUnitRelate:list">
                                        <button data-id="${dispatchUnit.id}" class="relateBtn btn btn-primary btn-mini">
                                            <i class="fa fa-sitemap"></i> 编辑关联单位
                                        </button>
                                    </shiro:hasPermission>
                                     <shiro:hasPermission name="dispatchUnit:del">
                                    <button class="delBtn btn btn-danger btn-mini" data-id="${dispatchUnit.id}">
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
                                            <shiro:hasPermission name="dispatchUnit:edit">
                                            <li>
                                                <a href="#" data-id="${dispatchUnit.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="dispatchUnit:del">
                                            <li>
                                                <a href="#" data-id="${dispatchUnit.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                <wo:page commonList="${commonList}" uri="${ctx}/dispatchUnit_page" target="#page-content" pageNum="5"
                         model="3"/>
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

    register_date($('.date-picker'));

    // 关联单位
    $(".myTableDiv .relateBtn").click(function(){
        loadModal("${ctx}/dispatchUnit_relate?id="+$(this).data("id"));
    });

    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>