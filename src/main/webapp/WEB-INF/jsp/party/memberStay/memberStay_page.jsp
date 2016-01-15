<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_STAY_STATUS_MAP" value="<%=SystemConstants.MEMBER_STAY_STATUS_MAP%>"/>
<c:set var="MEMBER_STAY_STATUS_APPLY" value="<%=SystemConstants.MEMBER_STAY_STATUS_APPLY%>"/>
<c:set var="MEMBER_STAY_STATUS_PARTY_VERIFY" value="<%=SystemConstants.MEMBER_STAY_STATUS_PARTY_VERIFY%>"/>
<c:set var="MEMBER_STAY_STATUS_OW_VERIFY" value="<%=SystemConstants.MEMBER_STAY_STATUS_OW_VERIFY%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
        <!-- PAGE CONTENT BEGINS -->
        <div class="myTableDiv"
             data-url-au="${ctx}/memberStay_au"
             data-url-page="${ctx}/memberStay_page"
             data-url-del="${ctx}/memberStay_del"
             data-url-bd="${ctx}/memberStay_batchDel"
             data-url-co="${ctx}/memberStay_changeOrder"
             data-querystr="${cm:escape(pageContext.request.queryString)}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects"
                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}</option>
                </select>
                <a class="searchBtn btn btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.userId || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="memberStay:edit">
                        <a href="javascript:;" class="openView btn btn-info btn-sm" data-url="${ctx}/memberStay_au">
                    <%--<a class="editBtn btn btn-info btn-sm" data-width="900">--%><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="memberStay:del">
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
							<th>用户</th>
							<th>出国时间</th>
							<th>预计回国时间</th>
							<th>手机号码</th>
							<th>状态</th>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${memberStays}" var="memberStay" varStatus="st">
                        <c:set value="${cm:getUserById(memberStay.userId)}" var="_sysUser"/>
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${memberStay.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td>
                                    <a href="javascript:;" class="openView" data-url="${ctx}/member_view?userId=${memberStay.userId}">
								${cm:getUserById(memberStay.userId).realname}
                                </a>
                                </td>
								<td>${cm:formatDate(memberStay.abroadTime,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(memberStay.returnTime,'yyyy-MM-dd')}</td>
								<td>${memberStay.mobile}</td>
								<td>${MEMBER_STAY_STATUS_MAP.get(memberStay.status)}</td>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">

                                    <c:if test="${memberStay.status==MEMBER_STAY_STATUS_APPLY}">
                                        <button onclick="_deny(${memberStay.id}, '${_sysUser.realname}')" class="btn btn-danger btn-mini">
                                            <i class="fa fa-times"></i> 不通过
                                        </button>
                                        <button onclick="_check1(${memberStay.id})" class="btn btn-success btn-mini">
                                            <i class="fa fa-check"></i> 审核1
                                        </button>
                                    </c:if>
                                    <c:if test="${memberStay.status==MEMBER_STAY_STATUS_PARTY_VERIFY}">
                                        <button onclick="_check2(${memberStay.id})" class="btn btn-success btn-mini">
                                            <i class="fa fa-check"></i> 审核2
                                        </button>
                                    </c:if>

                                    <shiro:hasPermission name="memberStay:edit">
                                    <button data-url="${ctx}/memberStay_au?id=${memberStay.id}" class="openView  btn btn-mini" data-width="900">
                                        <i class="fa fa-search"></i> 查看
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
                                            <shiro:hasPermission name="memberStay:edit">
                                            <li>
                                                <a href="#" data-id="${memberStay.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="memberStay:del">
                                            <li>
                                                <a href="#" data-id="${memberStay.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                <wo:page commonList="${commonList}" uri="${ctx}/memberStay_page" target="#page-content" pageNum="5"
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
<script>

    function _deny(id, realname){
        loadModal("${ctx}/memberStay_deny?id=" + id + "&realname="+encodeURIComponent(realname));
    }

    function _check1(id){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/memberStay_check1",{id:id},function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }
    function _check2(id){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/memberStay_check2",{id:id},function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_user_select($('#searchForm select[name=userId]'));
</script>