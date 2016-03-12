<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
        <div class="myTableDiv"
             data-url-au="${ctx}/dispatchCadre_au"
             data-url-page="${ctx}/dispatchCadre_page"
             data-url-del="${ctx}/dispatchCadre_del"
             data-url-bd="${ctx}/dispatchCadre_batchDel"
             data-url-co="${ctx}/dispatchCadre_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="widget-box hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>
                    <div class="widget-toolbar">
                        <a href="#" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-up"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                <mytag:sort-form css="form-horizontal hidden-sm hidden-xs" id="searchForm">
                    <div class="row">
                        <div class="col-xs-4">
                            <%--<div class="form-group">
                            <label class="col-xs-3 control-label">发文</label>
                            <div class="col-xs-6">
                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/dispatch_selects"
                                        name="dispatchId" data-placeholder="请选择">
                                    <option value="${dispatch.id}">${dispatch.code}</option>
                                </select>
                            </div>
                        </div>--%>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">干部</label>
                                    <div class="col-xs-6">
                                        <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                                                name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                            <option value="${cadre.id}">${sysUser.username}</option>
                                        </select>
                                    </div>
                                </div>

                        </div>

                        <div class="col-xs-4">
                            <div class="form-group">
                                <label class="col-xs-3 control-label">任免方式</label>
                                <div class="col-xs-6">
                                    <select data-rel="select2" name="wayId" data-placeholder="请选择">
                                        <option></option>
                                        <c:forEach var="way" items="${wayMap}">
                                            <option value="${way.value.id}">${way.value.name}</option>
                                        </c:forEach>
                                    </select>
                                    <script type="text/javascript">
                                        $("#searchForm select[name=wayId]").val('${param.wayId}');
                                    </script>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">任免程序</label>
                                <div class="col-xs-6">
                                    <select data-rel="select2" name="procedureId" data-placeholder="请选择">
                                        <option></option>
                                        <c:forEach var="procedure" items="${procedureMap}">
                                            <option value="${procedure.value.id}">${procedure.value.name}</option>
                                        </c:forEach>
                                    </select>
                                    <script type="text/javascript">
                                        $("#searchForm select[name=procedureId]").val('${param.procedureId}');
                                    </script>
                                </div>
                            </div>
                            </div>
                        <div class="col-xs-4">
                            <div class="form-group">
                                <label class="col-xs-3 control-label">行政级别</label>
                                <div class="col-xs-6">
                                    <select data-rel="select2" name="adminLevelId" data-placeholder="请选择">
                                        <option></option>
                                        <c:forEach var="adminLevel" items="${adminLevelMap}">
                                            <option value="${adminLevel.value.id}">${adminLevel.value.name}</option>
                                        </c:forEach>
                                    </select>
                                    <script type="text/javascript">
                                        $("#searchForm select[name=adminLevelId]").val('${param.adminLevelId}');
                                    </script>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">所属单位</label>
                                <div class="col-xs-6">
                                    <select name="unitId" data-rel="select2" data-placeholder="请选择">
                                        <option></option>
                                        <c:forEach items="${unitMap}" var="unit">
                                            <option value="${unit.key}">${unit.value.name}</option>
                                        </c:forEach>
                                    </select>
                                    <script>
                                        $("#searchForm select[name=unitId]").val('${param.unitId}');
                                    </script>
                                </div>
                            </div>


                            </div>
                        </div>

                    <div class="clearfix form-actions center">

                <a class="searchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.dispatchId ||not empty param.typeId ||not empty param.wayId ||not empty param.procedureId ||not empty param.cadreId ||not empty param.adminLevelId ||not empty param.unitId ||  not empty param.sort}"/>
                <c:if test="${_query}">&nbsp; &nbsp; &nbsp;
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                </div>

            </mytag:sort-form>
                        </div>
                    </div>
                </div>
            <div class="buttons pull-right">
                <a class="openView btn btn-info btn-sm" data-url="${ctx}/dispatch_cadres"><i class="fa fa-plus"></i> 添加干部任免</a>
                <%--<shiro:hasPermission name="dispatchCadre:edit">
                    <a class="editBtn btn btn-info btn-sm" data-width="700"><i class="fa fa-plus"></i> 添加</a>
                </shiro:hasPermission>--%>
                <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="dispatchCadre:del">
                        <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-times"></i> 批量删除</a>
                    </shiro:hasPermission>
                </c:if>
            </div>
            <h4>&nbsp;</h4>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
            <div class="table-container">
                <table style="min-width: 2000px" class="table table-actived table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace checkAll">
                                <span class="lbl"></span>
                            </label>
                        </th>
							<th>年度</th>
							<th>发文号</th>
							<th>任免日期</th>
							<th>类别</th>
							<th>任免方式</th>
							<th>任免程序</th>
							<th>干部类型</th>
							<th>工作证号</th>
							<th>姓名</th>
                            <th>职务</th>
                            <th>职务属性</th>
							<th>行政级别</th>
							<th>所属单位</th>
							<th>单位类型</th>
                            <th>发文类型</th>
                            <th>党委常委会日期</th>
                            <th>发文日期</th>
                            <th>任免文件</th>
                            <th>上会ppt</th>
                        <shiro:hasPermission name="dispatchCadre:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap>排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${dispatchCadres}" var="dispatchCadre" varStatus="st">
                        <c:set value="${dispatchMap.get(dispatchCadre.dispatchId)}" var="dispatch"/>
                        <c:set value="${unitMap.get(dispatchCadre.unitId)}" var="unit"/>
                        <c:set value="${cm:getUserById(cadreMap.get(dispatchCadre.cadreId).userId)}" var="user"/>
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${dispatchCadre.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td nowrap>${dispatch.year}</td>
                                    <c:if test="${not empty dispatch.fileName}">
                            <td nowrap><a href="javascript:void(0)" onclick="swf_preview(${dispatch.id}, 'file')">
                                    ${cm:getDispatchCode(dispatch.code, dispatch.dispatchTypeId, dispatch.year)}</a></td>
                            </c:if>
                            <c:if test="${empty dispatch.fileName}">
                                <td nowrap>${cm:getDispatchCode(dispatch.code, dispatch.dispatchTypeId, dispatch.year)}</td>
                            </c:if>
								<td nowrap>${cm:formatDate(dispatch.workTime,'yyyy-MM-dd')}</td>
								<td nowrap>${DISPATCH_CADRE_TYPE_MAP.get(dispatchCadre.type)}</td>
								<td nowrap>${wayMap.get(dispatchCadre.wayId).name}</td>
								<td nowrap>${procedureMap.get(dispatchCadre.procedureId).name}</td>
								<td nowrap>${cadreTypeMap.get(dispatchCadre.cadreTypeId).name}</td>
								<td nowrap>${user.code}</td>
								<td nowrap>${user.realname}</td>
                                <td nowrap>${dispatchCadre.post}</td>
                                <td nowrap>${postMap.get(dispatchCadre.postId).name}</td>
								<td nowrap>${adminLevelMap.get(dispatchCadre.adminLevelId).name}</td>
								<td nowrap>${unit.name}</td>
								<td nowrap>${unitTypeMap.get(unit.typeId).name}</td>
                                <td nowrap>${dispatchTypeMap.get(dispatch.dispatchTypeId).name}</td>
                                <td nowrap>${cm:formatDate(dispatch.meetingTime,'yyyy-MM-dd')}</td>
                                <td nowrap>${cm:formatDate(dispatch.pubTime,'yyyy-MM-dd')}</td>
                                <td width="100"><c:if test="${not empty dispatch.fileName}">
                                    <a href="javascript:void(0)" onclick="swf_preview(${dispatch.id}, 'file')">查看</a>
                                    &nbsp;
                                    <a href="javascript:void(0)" class="dispatch_del_file"
                                       data-id="${dispatch.id}" data-type="file">删除</a>
                                </c:if>
                                </td>
                                <td width="100"><c:if test="${not empty dispatch.pptName}">
                                    <a href="javascript:void(0)" onclick="swf_preview(${dispatch.id}, 'ppt')">查看</a>
                                    &nbsp;
                                    <a href="javascript:void(0)" class="dispatch_del_file"
                                       data-id="${dispatch.id}" data-type="ppt">删除</a>
                                </c:if>
                                </td>
                            <shiro:hasPermission name="dispatchCadre:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td nowrap>
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${dispatchCadre.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${dispatchCadre.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>
                            <td nowrap>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="dispatchCadre:edit">
                                    <button data-id="${dispatchCadre.id}" class="editBtn btn btn-default btn-mini btn-xs" data-width="700">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="dispatchCadre:del">
                                    <button class="delBtn btn btn-danger btn-mini btn-xs" data-id="${dispatchCadre.id}">
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
                                            <shiro:hasPermission name="dispatchCadre:edit">
                                            <li>
                                                <a href="#" data-id="${dispatchCadre.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="dispatchCadre:del">
                                            <li>
                                                <a href="#" data-id="${dispatchCadre.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
            <wo:page commonList="${commonList}" uri="${ctx}/dispatchCadre_page" target="#page-content" pageNum="5"
                     model="3"/>
            </c:if>
            <c:if test="${commonList.recNum==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </div>
        </div>
        <div id="item-content"> </div>
</div>
</div>
<script>
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_user_select($('#searchForm select[name=cadreId]'));
</script>