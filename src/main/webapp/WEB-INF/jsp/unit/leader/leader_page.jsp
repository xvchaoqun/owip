<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
        <div class="myTableDiv"
             data-url-au="${ctx}/leader_au"
             data-url-page="${ctx}/leader_page"
             data-url-del="${ctx}/leader_del"
             data-url-bd="${ctx}/leader_batchDel"
             data-url-co="${ctx}/leader_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">

            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                        name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${cadre.id}">${sysUser.realname}</option>
                </select>
                <select data-rel="select2" name="typeId" data-placeholder="请选择类别">
                    <option></option>
                    <c:forEach var="leaderType" items="${leaderTypeMap}">
                        <option value="${leaderType.value.id}">${leaderType.value.name}</option>
                    </c:forEach>
                </select>
                <script type="text/javascript">
                    $("#searchForm select[name=typeId]").val('${param.typeId}');
                </script>
                <input class="form-control search-query" name="job" type="text" value="${param.job}"
                       placeholder="请输入分管工作">
                <a class="searchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.cadreId ||not empty param.typeId ||not empty param.job || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="leader:edit">
                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="leader:del">
                    <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-times"></i> 批量删除</a>
                     </shiro:hasPermission>
                    </c:if>
                </div>
            </mytag:sort-form>
            <div class="space-4"></div>
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
							<th nowrap>工作证号</th>
							<th>姓名</th>
							<th>职务</th>
							<th nowrap>行政级别</th>
                            <th nowrap>分管工作</th>
							<th>类别</th>
                        <shiro:hasPermission name="leader:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap>排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${leaders}" var="leader" varStatus="st">
                        <c:set var="cadre" value="${cadreMap.get(leader.cadreId)}"/>
                        <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${leader.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td nowrap>${sysUser.code}</td>
								<td nowrap>
                                    <a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?id=${leader.cadreId}">
								        ${sysUser.realname}
                                    </a>
                                </td>
								<td nowrap>${cadre.title}</td>
								<td nowrap>${adminLevelMap.get(cadre.typeId).name}</td>
								<td>${leader.job}</td>
                                <td nowrap>${leaderTypeMap.get(leader.typeId).name}</td>
                            <shiro:hasPermission name="leader:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td nowrap>
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${leader.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${leader.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>
                            <td nowrap>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="leader:edit">
                                    <button data-id="${leader.id}" class="editBtn btn btn-default btn-mini btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                    <shiro:hasPermission name="leaderUnit:list">
                                        <button data-id="${leader.id}" class="leaderUnitBtn btn btn-mini btn-xs btn-primary">
                                            <i class="fa fa-sitemap"></i> 编辑联系单位
                                        </button>
                                    </shiro:hasPermission>
                                     <shiro:hasPermission name="leader:del">
                                    <button class="delBtn btn btn-danger btn-mini btn-xs" data-id="${leader.id}">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
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
                                            <shiro:hasPermission name="leader:edit">
                                            <li>
                                                <a href="#" data-id="${leader.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="leader:del">
                                            <li>
                                                <a href="#" data-id="${leader.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                <wo:page commonList="${commonList}" uri="${ctx}/leader_page" target="#page-content" pageNum="5"
                         model="3"/>
            </c:if>
            <c:if test="${commonList.recNum==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </div>
    </div><div id="item-content"> </div>
</div>
</div>
<script>
    // 联系单位
    $(".myTableDiv .leaderUnitBtn").click(function(){
        loadModal("${ctx}/leader_unit?id="+$(this).data("id"));
    });

    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    register_user_select($('[data-rel="select2-ajax"]'));
</script>
