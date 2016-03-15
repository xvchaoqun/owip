<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->
            <div class="myTableDiv"
                 data-url-au="${ctx}/metaClass_au"
                 data-url-page="${ctx}/metaClass_page"
                 data-url-del="${ctx}/metaClass_del"
                 data-url-bd="${ctx}/metaClass_batchDel"
                 data-url-co="${ctx}/metaClass_changeOrder"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <input class="form-control search-query" name="name" type="text" value="${param.name}"
                       placeholder="请输入名称">
                <shiro:hasRole name="admin">
                <input class="form-control search-query" name="code" type="text" value="${param.code}"
                       placeholder="请输入代码">
                </shiro:hasRole>
                <a class="searchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.name || not empty param.code
                || (not empty param.sort&&param.sort!='sort_order')}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasRole name="admin">
                    <shiro:hasPermission name="metaClass:edit">
                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    </shiro:hasRole>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果"><i class="fa fa-download"></i> 导出</a>
                        <shiro:hasRole name="admin">
                        <shiro:hasPermission name="metaClass:del">
                    <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-times"></i> 删除</a>
                    </shiro:hasPermission>
                        </shiro:hasRole>
                    </c:if>
                </div>
            </mytag:sort-form>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
                <div class="table-container">
                    <table style="min-width: 1200px"class="overflow-y table table-actived table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th class="center">
                        <label class="pos-rel">
                            <input type="checkbox" class="ace checkAll">
                            <span class="lbl"></span>
                        </label>
                    </th>
                    <mytag:sort-th field="name">名称</mytag:sort-th>
                    <th nowrap class="visible-lg">所属一级目录</th>
                    <th nowrap class="visible-lg">所属二级目录</th>
                    <shiro:hasRole name="admin">
                    <mytag:sort-th field="code" css="visible-lg">代码</mytag:sort-th>
                    <th nowrap class="visible-lg">布尔属性名称</th>
                    <th nowrap class="visible-lg">附加属性名称</th>
                    </shiro:hasRole>
                    <shiro:hasPermission name="metaClass:changeOrder">
                    <c:if test="${!_query && commonList.recNum>1}">
                        <th nowrap>排序</th>
                    </c:if>
                    </shiro:hasPermission>
                    <%--<th nowrap>状态</th>--%>
                    <th nowrap></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${metaClasss}" var="metaClass" varStatus="st">
                    <tr>
                        <td class="center">
                            <label class="pos-rel">
                                <input type="checkbox" value="${metaClass.id}" class="ace">
                                <span class="lbl"></span>
                            </label>
                        </td>
                        <td nowrap><a href="javascript:;" onclick="openView(${metaClass.id})">${metaClass.name}</a></td>
                        <td nowrap class="visible-lg">${metaClass.firstLevel}</td>
                        <td nowrap class="visible-lg">${metaClass.secondLevel}</td>
                        <shiro:hasRole name="admin">
                        <td nowrap class="visible-lg">${metaClass.code}</td>
                        <td nowrap class="visible-lg">${metaClass.boolAttr}</td>
                        <td nowrap class="visible-lg">${metaClass.extraAttr}</td>
                        <shiro:hasPermission name="metaClass:changeOrder">
                        <c:if test="${!_query && commonList.recNum>1}">
                            <td nowrap>
                                <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${metaClass.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                <input type="text" value="1"
                                       class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改排序步长">
                                <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${metaClass.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                            </td>
                        </c:if>
                        </shiro:hasPermission>
                        </shiro:hasRole>
                        <%--<td nowrap>${available}</td>--%>
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">

                             <shiro:hasPermission name="metaClass:edit">
                            <button data-id="${metaClass.id}" class="editBtn btn btn-default btn-mini btn-xs">
                                <i class="fa fa-edit"></i> 编辑
                            </button>
                                 <button onclick="openView(${metaClass.id})"  class="btn btn-mini btn-xs btn-success">
                                     <i class="fa fa-bars"></i> 编辑属性
                                 </button>
                             </shiro:hasPermission>
                                <shiro:hasRole name="admin">
                                    <button class="btn btn-warning btn-mini btn-xs" onclick="updateRole(${metaClass.id})">
                                        <i class="fa fa-pencil"></i> 修改角色
                                    </button>
                             <%--<shiro:hasPermission name="metaClass:del">
                            <button class="delBtn btn btn-danger btn-mini btn-xs" data-id="${metaClass.id}">
                                <i class="fa fa-times"></i> 删除
                            </button>
                             </shiro:hasPermission>--%>
                                </shiro:hasRole>
                            </div>
                            <div class="hidden-md hidden-lg">
                                <div class="inline pos-rel">
                                    <button class="btn btn-mini btn-xser btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
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
                                        <shiro:hasPermission name="metaClass:edit">
                                        <li>
                                            <a href="#" data-id="${metaClass.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                            </a>
                                        </li>
                                        </shiro:hasPermission>
                                            <shiro:hasPermission name="metaClass:del">
                                        <li>
                                            <a href="#" data-id="${metaClass.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                <wo:page commonList="${commonList}" uri="${ctx}/metaClass_page" target="#page-content" pageNum="5"
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
    stickheader();
    function openView(classId, pageNo){
        pageNo = pageNo||1;
        loadModal( "${ctx}/metaClass_type?id="+classId + "&pageNo="+pageNo,
                '<shiro:hasRole name="admin">800</shiro:hasRole><shiro:lacksRole name="admin">400</shiro:lacksRole>');
    }
    function updateRole(id) {
        loadModal( "${ctx}/metaClassRole?id=" + id);
    }
    $("#searchForm select").select2();
    $('[data-rel="tooltip"]').tooltip();
</script>