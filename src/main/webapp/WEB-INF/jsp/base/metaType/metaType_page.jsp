<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div class="myTableDiv"
             data-url-au="${ctx}/metaType_au?classId=${metaClass.id}"
             data-url-page="${ctx}/metaType"
             data-url-del="${ctx}/metaType_del"
             data-url-bd="${ctx}/metaType_batchDel"
             data-url-co="${ctx}/metaType_changeOrder?classId=${metaClass.id}"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <t:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/metaClass_selects"
                        name="classId" data-placeholder="请输入分类名称">
                    <option value="${metaClass.id}">${metaClass.name}</option>
                </select>
                <input class="form-control search-query" name="name" type="text" value="${param.name}"
                       placeholder="请输入名称">
                <input class="form-control search-query" name="code" type="text" value="${param.code}"
                       placeholder="请输入代码">
                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.classId || not empty param.name || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="metaType:edit">
                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="metaType:del">
                    <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-trash"></i> 批量删除</a>
                     </shiro:hasPermission>
                    </c:if>
                </div>
            </t:sort-form>
            <div class="space-4"></div>
            <c:set var="_query2" value="${not empty param.classId && empty param.name && empty param.code && empty param.sort}"/>
            <c:if test="${commonList.recNum>0}">
                <table class="table table-actived table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace checkAll">
                                <span class="lbl"></span>
                            </label>
                        </th>
							<th>所属分类</th>
                            <t:sort-th field="name">名称</t:sort-th>
							<th>代码</th>
							<th>布尔属性</th>
							<th>附加属性</th>
							<th>备注</th>
                        <shiro:hasPermission name="metaType:changeOrder">
                            <c:if test="${_query2 && commonList.recNum>1}">
                                <th nowrap>排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${metaTypes}" var="metaType" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${metaType.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td nowrap><a href="${ctx}/metaType?classId=${metaType.classId}">${metaClassMap.get(metaType.classId).name}</a></td>
								<td nowrap>${metaType.name}</td>
								<td nowrap>${metaType.code}</td>
								<td nowrap>
                                    <c:if test="${not empty metaType.boolAttr}">
                                        ${metaType.boolAttr?"是":"否"}
                                    </c:if>
								</td>
								<td nowrap>${metaType.extraAttr}</td>
								<td>${metaType.remark}</td>
                            <shiro:hasPermission name="metaType:changeOrder">
                            <c:if test="${_query2 && commonList.recNum>1}">
                                <td nowrap>
                                    <a href="javascript:;" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${metaType.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="javascript:;" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${metaType.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>

                                </td>
                            </c:if>

                            </shiro:hasPermission>
                            <td nowrap>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="metaType:edit">
                                    <button data-id="${metaType.id}" class="editBtn btn btn-default btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="metaType:del">
                                    <button class="delBtn btn btn-danger btn-xs" data-id="${metaType.id}">
                                        <i class="fa fa-pencil"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
                                </div>
                                <div class="hidden-md hidden-lg">
                                    <div class="inline pos-rel">
                                        <button class="btn btn-xser btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                            <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                        </button>

                                        <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                            <%--<li>
                                            <a href="javascript:;" class="tooltip-info" data-rel="tooltip" title="查看">
                                                        <span class="blue">
                                                            <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                                        </span>
                                            </a>
                                        </li>--%>
                                            <shiro:hasPermission name="metaType:edit">
                                            <li>
                                                <a href="javascript:;" data-id="${metaType.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="metaType:del">
                                            <li>
                                                <a href="javascript:;" data-id="${metaType.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                <wo:page commonList="${commonList}" uri="${ctx}/metaType" target="#page-content" pageNum="5"
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
    $('[data-rel="tooltip"]').tooltip();
    $('[data-rel="select2-ajax"]').select2({
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