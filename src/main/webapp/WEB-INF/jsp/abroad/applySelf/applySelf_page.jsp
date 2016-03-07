<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
        <div class="myTableDiv"
             data-url-au="${ctx}/applySelf_au"
             data-url-page="${ctx}/applySelf_page"
             data-url-del="${ctx}/applySelf_del"
             data-url-bd="${ctx}/applySelf_batchDel"
             data-url-co="${ctx}/applySelf_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">

                <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                        name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${cadre.id}">${sysUser.realname}</option>
                </select>
                <div class="input-group tooltip-success" data-rel="tooltip" title="申请日期范围">
                                            <span class="input-group-addon">
                                                <i class="fa fa-calendar bigger-110"></i>
                                            </span>
                    <input placeholder="请选择申请日期范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_applyDate" value="${param._applyDate}"/>
                </div>
                <select name="type" data-rel="select2" data-placeholder="请选择出行时间范围">
                    <option></option>
                    <c:forEach items="${APPLY_SELF_DATE_TYPE_MAP}" var="type">
                        <option value="${type.key}">${type.value}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#searchForm select[name=type]").val('${param.type}');
                </script>

                <a class="searchBtn btn btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.cadreId ||not empty param._applyDate ||not empty param.type || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="applySelf:edit">
                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="applySelf:del">
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
							<th>申请日期</th>
                            <th>工作证号</th>
                            <th>姓名</th>
                            <th>所在单位及职务</th>
							<th>出行时间</th>
							<th>回国时间</th>
							<th>出行天数</th>
							<th>前往国家或地区</th>
							<th>因私出国（境）事由</th>
							<th>组织部初审</th>
                        <c:forEach items="${approverTypeMap}" var="type">
                            <th>${type.value.name}审批</th>
                        </c:forEach>
							<th>组织部终审</th>
							<th>短信提醒</th>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${applySelfs}" var="applySelf" varStatus="st">
                        <c:set var="cadre" value="${cadreMap.get(applySelf.cadreId)}"/>
                        <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${applySelf.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td>${cm:formatDate(applySelf.applyDate,'yyyy-MM-dd')}</td>
                            <td>${sysUser.code}</td>
                            <td><a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?id=${applySelf.cadreId}">
                                    ${sysUser.realname}
                            </a></td>
                            <td>${unitMap.get(cadre.unitId).name}-${cadre.title}</td>
								<td>${cm:formatDate(applySelf.startDate,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(applySelf.endDate,'yyyy-MM-dd')}</td>
                            <td>${cm:getDayCountBetweenDate(applySelf.startDate, applySelf.endDate)}</td>
								<td>${applySelf.toCountry}</td>
								<td>${applySelf.reason}</td>
                                <td></td>
                                <c:forEach items="${approverTypeMap}" var="type">
                                    <td></td>
                                </c:forEach>
                                <td></td>
                                <td></td>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <button data-id="${applySelf.id}" class="detailBtn btn btn-mini">
                                        <i class="fa fa-edit"></i> 详情
                                    </button>
                                     <shiro:hasPermission name="applySelf:del">
                                    <button class="delBtn btn btn-danger btn-mini" data-id="${applySelf.id}">
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
                                            <shiro:hasPermission name="applySelf:edit">
                                            <li>
                                                <a href="#" data-id="${applySelf.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="applySelf:del">
                                            <li>
                                                <a href="#" data-id="${applySelf.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                <wo:page commonList="${commonList}" uri="${ctx}/applySelf_page" target="#page-content" pageNum="5"
                         model="3"/>
            </c:if>
            <c:if test="${commonList.recNum==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </div>
    </div>
    <div id="item-content">
    </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('#searchForm [data-rel="select2-ajax"]').select2({
        ajax: {
            dataType: 'json',
            delay: 200,
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