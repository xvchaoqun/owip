<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="APPLY_STAGE_MAP" value="<%=SystemConstants.APPLY_STAGE_MAP%>"/>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="myTableDiv"
             data-target="#home2"
             data-url-au="${ctx}/applyLog_au"
             data-url-page="${ctx}/applyLog"
             data-url-del="${ctx}/applyLog_del"
             data-url-bd="${ctx}/applyLog_batchDel"
             data-url-co="${ctx}/applyLog_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">

                <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                </select>
                <select class="form-control" name="stage" data-rel="select2" data-placeholder="请选择阶段">
                    <option></option>
                    <c:forEach items="${applyStageTypeMap}" var="applyStageType">
                        <option value="${applyStageType.key}">${applyStageType.value}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#searchForm select[name=stage]").val('${param.stage}');
                </script>
                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.userId ||not empty param.stage || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <c:if test="${commonList.recNum>0}">
                    <%--<a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i> 导出</a>--%>
                    <shiro:hasPermission name="applyLog:del">
                    <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-trash"></i> 批量删除</a>
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
							<th>申请人</th>
							<th>操作人</th>
							<th>当前阶段</th>
							<th>操作内容</th>
							<th>IP</th>
							<th>时间</th>
                        <%--<th nowrap></th>--%>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${applyLogs}" var="applyLog" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${applyLog.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td>${cm:getUserById(applyLog.userId).realname}</td>
								<td>${cm:getUserById(applyLog.operatorId).realname}</td>
								<td>${APPLY_STAGE_MAP.get(applyLog.stage)}</td>
								<td>${applyLog.content}</td>
								<td>${applyLog.ip}</td>
								<td>${cm:formatDate(applyLog.createTime,'yyyy-MM-dd HH:mm:ss')}</td>

                            <%--<td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="applyLog:edit">
                                    <button data-id="${applyLog.id}" class="editBtn btn btn-default btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="applyLog:del">
                                    <button class="delBtn btn btn-danger btn-xs" data-id="${applyLog.id}">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
                                </div>
                                <div class="hidden-md hidden-lg">
                                    <div class="inline pos-rel">
                                        <button class="btn btn-xser btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                            <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                        </button>

                                        <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                            &lt;%&ndash;<li>
                                            <a href="javascript:;" class="tooltip-info" data-rel="tooltip" title="查看">
                                                        <span class="blue">
                                                            <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                                        </span>
                                            </a>
                                        </li>&ndash;%&gt;
                                            <shiro:hasPermission name="applyLog:edit">
                                            <li>
                                                <a href="javascript:;" data-id="${applyLog.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="applyLog:del">
                                            <li>
                                                <a href="javascript:;" data-id="${applyLog.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
                                                    <span class="red">
                                                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                        </ul>
                                    </div>
                                </div>
                            </td>--%>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <wo:page commonList="${commonList}" uri="${ctx}/applyLog" target="#home2" pageNum="5"
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
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    register_user_select($('#searchForm select[name=userId]'));
</script>