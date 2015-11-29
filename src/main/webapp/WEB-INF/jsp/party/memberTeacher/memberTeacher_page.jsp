<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->

        <div class="tabbable">
            <jsp:include page="/WEB-INF/jsp/party/member/member_menu.jsp"/>

            <div class="tab-content">
                <div id="home4" class="tab-pane in active">

        <div class="myTableDiv"
             data-url-au="${ctx}/memberTeacher_au"
             data-url-page="${ctx}/memberTeacher_page"
             data-url-del="${ctx}/memberTeacher_del"
             data-url-bd="${ctx}/memberTeacher_batchDel"
             data-url-co="${ctx}/memberTeacher_changeOrder"
             data-querystr="${pageContext.request.queryString}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <input type="hidden" name="type" value="${type}">
                <select data-rel="select2-ajax" data-ajax--url="${ctx}/sysUser_selects"
                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}</option>
                </select>
                <a class="searchBtn btn btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.userId || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" onclick="_reset()" class="btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="memberTeacher:edit">
                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="memberTeacher:del">
                    <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-times"></i> 批量删除</a>
                     </shiro:hasPermission>
                    </c:if>
                </div>
            </mytag:sort-form>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace checkAll">
                                <span class="lbl"></span>
                            </label>
                        </th>
							<th>工作证号</th>
							<th>姓名</th>
							<th>性别</th>
							<th>年龄</th>
							<th>最高学历</th>
							<th>岗位类别</th>
							<th>专业技术职务</th>
							<th>所属组织机构</th>
							<th>入党时间</th>
							<th>联系手机</th>
                        <shiro:hasPermission name="memberTeacher:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap class="hidden-480">排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${memberTeachers}" var="memberTeacher" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${memberTeacher.userId}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
                            <td>
                                <a href="#" onclick="openMemberView(${memberTeacher.userId})">
                            ${memberTeacher.code}
                            </a>
                            </td>
                            <td>${memberTeacher.realname}</td>
                            <td>${memberTeacher.gender}</td>
                            <td>${cm:formatDate(memberTeacher.birth,'yyyy-MM-dd')}</td>
                            <td>${memberTeacher.education}</td>
                            <td>${memberTeacher.postClass}</td>
                            <td>${memberTeacher.proPost}</td>
                            <td>${partyMap.get(memberTeacher.partyId).name}
                                <c:if test="${not empty memberTeacher.branchId}">
                                    -${branchMap.get(memberTeacher.branchId).name}
                                </c:if></td>
							<td>${cm:formatDate(memberTeacher.growTime,'yyyy-MM-dd')}</td>
							<td>${memberTeacher.mobile}</td>
                            <shiro:hasPermission name="memberTeacher:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td class="hidden-480">
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${memberTeacher.userId}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${memberTeacher.userId}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="memberTeacher:edit">
                                    <button data-id="${memberTeacher.userId}" class="editBtn btn btn-mini">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="memberTeacher:del">
                                    <button class="delBtn btn btn-danger btn-mini" data-id="${memberTeacher.userId}">
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
                                            <shiro:hasPermission name="memberTeacher:edit">
                                            <li>
                                                <a href="#" data-id="${memberTeacher.userId}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="memberTeacher:del">
                                            <li>
                                                <a href="#" data-id="${memberTeacher.userId}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                <c:if test="${!empty commonList && commonList.pageNum>1 }">
                    <div class="row my_paginate_row">
                        <div class="col-xs-6">第${commonList.startPos}-${commonList.endPos}条&nbsp;&nbsp;共${commonList.recNum}条记录</div>
                        <div class="col-xs-6">
                            <div class="my_paginate">
                                <ul class="pagination">
                                    <wo:page commonList="${commonList}" uri="${ctx}/memberTeacher_page" target="#page-content" pageNum="5"
                                             model="3"/>
                                </ul>
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:if>
            <c:if test="${commonList.recNum==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </div>
                    </div></div></div>
    </div>
</div>
<script>

        function openMemberView(userId){

            loadModal("${ctx}/member_view?userId="+userId, 1000, ".modal-footer.draggable");
        }
    function _reset(){

        _tunePage(1, "", "${ctx}/memberStudent_page", "#page-content", "", "&type=${type}");
    }
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
        function formatState (state) {

            if (!state.id) { return state.text; }
            var $state = state.text;
            if(state.code!=undefined && state.code.length>0)
                $state += '-' + state.code;
            if(state.unit!=undefined && state.unit.length>0){
                $state += '-' + state.unit;
            }
            //console.log($state)
            return $state;
        };

        $('#searchForm select[name=userId]').select2({
            templateResult: formatState,
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
</div>