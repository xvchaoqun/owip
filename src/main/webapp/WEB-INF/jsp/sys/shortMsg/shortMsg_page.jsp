<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
        <div class="myTableDiv"
             data-url-au="${ctx}/shortMsg_au"
             data-url-page="${ctx}/shortMsg_page"
             data-url-del="${ctx}/shortMsg_del"
             data-url-bd="${ctx}/shortMsg_batchDel"
             data-url-co="${ctx}/shortMsg_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                        name="receiverId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}</option>
                </select>
                <input class="form-control search-query" name="mobile" type="text" value="${param.mobile}"
                       placeholder="请输入手机号码">
                <a class="searchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.receiverId ||not empty param.mobile || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <%--<div class="buttons pull-right">
                    <shiro:hasPermission name="shortMsg:edit">
                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="shortMsg:del">
                    <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-times"></i> 批量删除</a>
                     </shiro:hasPermission>
                    </c:if>
                </div>--%>
            </mytag:sort-form>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
            <div class="table-container">
                <table style="min-width: 1200px"class="overflow-y table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace checkAll">
                                <span class="lbl"></span>
                            </label>
                        </th>
							<th>接收方</th>
							<th>类别</th>
							<th>手机号码</th>
							<th>短信内容</th>
							<th>发送时间</th>
							<th>IP</th>
							<th>是否成功</th>
							<th>返回结果</th>
							<th>备注</th>
                        <%--
                        <th nowrap></th>--%>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${shortMsgs}" var="shortMsg" varStatus="st">
                        <c:set var="user" value="${cm:getUserById(shortMsg.receiverId)}"/>
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${shortMsg.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td>
                                    <a href="javascript:;" class="openView" data-url="${ctx}/sysUser_view?userId=${user.id}">
								${user.realname}
                                </a>
                                </td>
								<td>${shortMsg.type}</td>
								<td>${shortMsg.mobile}</td>
								<td title="${shortMsg.content}">${cm:substr(shortMsg.content, 0, 30, "...")}</td>
								<td>${cm:formatDate(shortMsg.createTime, "yyyy-MM-dd HH:mm")}</td>
								<td>${shortMsg.ip}</td>
								<td>
								<c:if test="${!shortMsg.status}">
                                    <span class="label label-danger">否</span>
								</c:if>
                                    <c:if test="${shortMsg.status}">
                                        <span class="label label-success">是</span>
                                    </c:if>
                                </td>
								<td>${shortMsg.ret}</td>
								<td>${shortMsg.remark}</td>
                            <%--<td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="shortMsg:edit">
                                    <button data-id="${shortMsg.id}" class="editBtn btn btn-mini">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="shortMsg:del">
                                    <button class="delBtn btn btn-danger btn-mini" data-id="${shortMsg.id}">
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
                                            &lt;%&ndash;<li>
                                            <a href="#" class="tooltip-info" data-rel="tooltip" title="查看">
                                                        <span class="blue">
                                                            <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                                        </span>
                                            </a>
                                        </li>&ndash;%&gt;
                                            <shiro:hasPermission name="shortMsg:edit">
                                            <li>
                                                <a href="#" data-id="${shortMsg.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="shortMsg:del">
                                            <li>
                                                <a href="#" data-id="${shortMsg.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                </div>
                <wo:page commonList="${commonList}" uri="${ctx}/shortMsg_page" target="#page-content" pageNum="5"
                         model="3"/>
            </c:if>
            <c:if test="${commonList.recNum==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </div></div>
            <div id="item-content">
            </div>
    </div>
</div>
<script>
    stickheader()
    $('#searchForm [data-rel="select2"]').select2();
    register_user_select($('#searchForm select[name=receiverId]'));
</script>