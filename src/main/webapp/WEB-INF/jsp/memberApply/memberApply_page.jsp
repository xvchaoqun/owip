<%@ page import="sys.constants.SystemConstants" %>
<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="APPLY_STAGE_INIT" value="<%=SystemConstants.APPLY_STAGE_INIT%>"/>
<c:set var="APPLY_STAGE_PASS" value="<%=SystemConstants.APPLY_STAGE_PASS%>"/>
<c:set var="APPLY_STAGE_ACTIVE" value="<%=SystemConstants.APPLY_STAGE_ACTIVE%>"/>
<c:set var="APPLY_STAGE_CANDIDATE" value="<%=SystemConstants.APPLY_STAGE_CANDIDATE%>"/>
<c:set var="APPLY_STAGE_PLAN" value="<%=SystemConstants.APPLY_STAGE_PLAN%>"/>
<c:set var="APPLY_STAGE_DRAW" value="<%=SystemConstants.APPLY_STAGE_DRAW%>"/>
<c:set var="APPLY_STAGE_GROW" value="<%=SystemConstants.APPLY_STAGE_GROW%>"/>
<c:set var="APPLY_STAGE_POSITIVE" value="<%=SystemConstants.APPLY_STAGE_POSITIVE%>"/>

<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="tabbable tabs-left">
                        <%
                            String[] colors= new String[7];
                            colors[0]= "badge-grey";
                            colors[1]= "badge-inverse";
                            colors[2]= "badge-danger";
                            colors[3]= "badge-info";
                            colors[4]= "badge-purple";
                            colors[5]= "badge-warning";
                            colors[6]= "badge-pink";
                            //colors[0]= "badge-yellow";
                        %>
                        <c:set value="<%=colors%>" var="colors"/>
                        <ul class="nav nav-tabs" id="myTab3">
                            <c:forEach items="#{APPLY_STAGE_MAP}" var="applyStage">
                                <li class="<c:if test="${stage==applyStage.key}">active</c:if>">
                                    <a href="javascript:;" onclick="_go(${empty type?1:type}, ${applyStage.key})">
                                        <%--<i class='${(stage==applyStageType.key)?"pink":"blue"} ace-icon fa fa-rocket bigger-110'></i>--%>
                                        <span class="badge ${colors[applyStage.key==0?0:applyStage.key-1]}">${applyStage.key==0?1:applyStage.key}</span>
                                        ${applyStage.value}
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>

                        <div class="tab-content" style="padding-top: 0px">
                            <div id="home3" class="tab-pane in active">

                                <div class="tabbable" >
                                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                                        <li class="<c:if test="${type==1}">active</c:if>">
                                            <a href="javascript:;" onclick="_go(1, ${empty stage?1:stage})"><i class="fa fa-graduation-cap"></i> 学生</a>
                                        </li>

                                        <li class="<c:if test="${type==2}">active</c:if>">
                                            <a href="javascript:;" onclick="_go(2, ${empty stage?1:stage})"><i class="fa fa-user-secret"></i> 教职工</a>
                                        </li>
                                    </ul>

                                    <div class="tab-content" >
                                        <div id="home4" class="tab-pane in active">
                                <div class="myTableDiv"
                                     data-url-au="${ctx}/memberApply_au"
                                     data-url-page="${ctx}/memberApply_page"
                                     data-url-del="${ctx}/memberApply_del"
                                     data-url-bd="${ctx}/memberApply_batchDel"
                                     data-url-co="${ctx}/memberApply_changeOrder"
                                     data-querystr="${pageContext.request.queryString}">
                                    <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">

                                        <input type="hidden" name="type" value="${type}">
                                        <input type="hidden" name="stage" value="${stage}">
                                        <select data-rel="select2-ajax" data-ajax--url="${ctx}/sysUser_selects"
                                                name="userId" data-placeholder="请输入账号或姓名或学工号">
                                            <option value="${sysUser.id}">${sysUser.username}</option>
                                        </select>
                                        <select class="form-control" name="partyId" data-rel="select2" data-placeholder="请选择分党委">
                                            <option></option>
                                            <c:forEach items="${partyMap}" var="party">
                                                <option value="${party.key}">${party.value.name}</option>
                                            </c:forEach>
                                        </select>
                                        <script>
                                            $("#searchForm select[name=partyId]").val('${param.partyId}');
                                        </script>
                                        <select class="form-control" name="branchId" data-rel="select2" data-placeholder="请选择党支部">
                                            <option></option>
                                            <c:forEach items="${branchMap}" var="branch">
                                                <option value="${branch.key}">${branch.value.name}</option>
                                            </c:forEach>
                                        </select>
                                        <script>
                                            $("#searchForm select[name=branchId]").val('${param.branchId}');
                                        </script>

                                        <a class="searchBtn btn btn-sm"><i class="fa fa-search"></i> 查找</a>
                                        <c:set var="_query" value="${not empty param.userId ||not empty param.partyId ||not empty param.branchId || not empty param.code || not empty param.sort}"/>
                                        <c:if test="${_query}">
                                            <button type="button" onclick="_reset()" class="btn btn-warning btn-sm">
                                                <i class="fa fa-reply"></i> 重置
                                            </button>
                                        </c:if>
                                        <div class="vspace-12"></div>
                                        <div class="buttons pull-right">

                                            <c:if test="${commonList.recNum>0}">
                                                <a class="exportBtn btn btn-success btn-sm tooltip-success"><i class="fa fa-download"></i> 导出</a>
                                                <shiro:hasPermission name="memberApply:del">
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
                                                <th>${type==1?"学生证号":"工作证号"}</th>
                                                <th>姓名</th>
                                                <th>所属组织机构</th>
                                                <c:if test="${stage<=APPLY_STAGE_PASS}">
                                                <th>提交书面申请书时间</th>
                                                </c:if>
                                                <c:if test="${stage==APPLY_STAGE_ACTIVE}">
                                                <th>确定为入党积极分子时间</th>
                                                </c:if>
                                                <c:if test="${stage==APPLY_STAGE_CANDIDATE}">
                                                    <th>确定为发展对象时间</th>
                                                </c:if>
                                                <c:if test="${stage==APPLY_STAGE_PLAN}">
                                                    <th>列入发展计划时间</th>
                                                </c:if>
                                                <c:if test="${stage==APPLY_STAGE_DRAW}">
                                                    <th>领取志愿书时间</th>
                                                </c:if>
                                                <c:if test="${stage==APPLY_STAGE_GROW||stage==APPLY_STAGE_POSITIVE}">
                                                    <th>入党时间</th>
                                                </c:if>
                                                <c:if test="${stage==APPLY_STAGE_POSITIVE}">
                                                    <th>转正时间</th>
                                                </c:if>

                                                <th>${(stage==APPLY_STAGE_POSITIVE)?"状态":"下一阶段状态"}</th>
                                                <c:if test="${stage!=APPLY_STAGE_POSITIVE}">
                                                <th nowrap></th>
                                                    </c:if>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${memberApplys}" var="memberApply" varStatus="st">
                                                <tr>
                                                    <td class="center">
                                                        <label class="pos-rel">
                                                            <input type="checkbox" value="${memberApply.userId}" class="ace">
                                                            <span class="lbl"></span>
                                                        </label>
                                                    </td>
                                                    <c:set var="user" value="${cm:getUserById(memberApply.userId)}"/>
                                                    <td>${user.username}</td>
                                                    <td>${user.realname}</td>
                                                    <td>${partyMap.get(memberApply.partyId).name}-${branchMap.get(memberApply.branchId).name}</td>
                                                    <c:if test="${stage<=APPLY_STAGE_PASS}">
                                                    <td>${cm:formatDate(memberApply.applyTime,'yyyy-MM-dd')}</td>
                                                    </c:if>
                                                    <c:if test="${stage==APPLY_STAGE_ACTIVE}">
                                                    <td>${cm:formatDate(memberApply.activeTime,'yyyy-MM-dd')}</td>
                                                    </c:if>
                                                    <c:if test="${stage==APPLY_STAGE_CANDIDATE}">
                                                        <td>${cm:formatDate(memberApply.candidateTime,'yyyy-MM-dd')}</td>
                                                    </c:if>
                                                    <c:if test="${stage==APPLY_STAGE_PLAN}">
                                                        <td>${cm:formatDate(memberApply.planTime,'yyyy-MM-dd')}</td>
                                                    </c:if>
                                                    <c:if test="${stage==APPLY_STAGE_DRAW}">
                                                        <td>${cm:formatDate(memberApply.drawTime,'yyyy-MM-dd')}</td>
                                                    </c:if>
                                                    <c:if test="${stage==APPLY_STAGE_GROW||stage==APPLY_STAGE_POSITIVE}">
                                                        <td>${cm:formatDate(memberApply.growTime,'yyyy-MM-dd')}</td>
                                                    </c:if>
                                                    <c:if test="${stage==APPLY_STAGE_POSITIVE}">
                                                        <td>${cm:formatDate(memberApply.positiveTime,'yyyy-MM-dd')}</td>
                                                    </c:if>
                                                    <td>
                                                    <a href="${ctx}/applyLog?userId=${memberApply.userId}">${cm:getApplyStatus(memberApply)}</a>
                                                    </td>
                                                    <c:if test="${stage!=APPLY_STAGE_POSITIVE}">
                                                    <td>
                                                        <div class="hidden-sm hidden-xs action-buttons">
                                                            <c:choose>
                                                                <c:when test="${memberApply.stage==APPLY_STAGE_INIT}">
                                                                    <button onclick="apply_pass(${memberApply.userId})" class="btn btn-success btn-mini">
                                                                        <i class="fa fa-check"></i> 通过
                                                                    </button>
                                                                    <button onclick="apply_deny(${memberApply.userId})" class="btn btn-danger btn-mini">
                                                                        <i class="fa fa-times"></i> 不通过
                                                                    </button>
                                                                </c:when>
                                                                <c:when test="${memberApply.stage==APPLY_STAGE_PASS}">
                                                                    <button onclick="apply_active(${memberApply.userId})" class="btn btn-success btn-mini">
                                                                        <i class="fa fa-check"></i> 确定为入党积极分子
                                                                    </button>
                                                                </c:when>
                                                                <c:when test="${memberApply.stage==APPLY_STAGE_ACTIVE}">
                                                                    <c:if test="${empty memberApply.candidateStatus}">
                                                                        <button onclick="apply_candidate(${memberApply.userId})" class="btn btn-success btn-mini">
                                                                            <i class="fa fa-check"></i> 确定为发展对象
                                                                        </button>
                                                                    </c:if>
                                                                    <c:if test="${memberApply.candidateStatus==0}">
                                                                        <button onclick="apply_candidate_check(${memberApply.userId})" class="btn btn-success btn-mini">
                                                                            <i class="fa fa-check"></i> 审核
                                                                        </button>
                                                                    </c:if>
                                                                </c:when>

                                                                <c:when test="${memberApply.stage==APPLY_STAGE_CANDIDATE}">
                                                                    <c:if test="${empty memberApply.planStatus}">
                                                                        <button onclick="apply_plan(${memberApply.userId})" class="btn btn-success btn-mini">
                                                                            <i class="fa fa-check"></i> 列入发展计划
                                                                        </button>
                                                                    </c:if>
                                                                    <c:if test="${memberApply.planStatus==0}">
                                                                        <button onclick="apply_plan_check(${memberApply.userId})" class="btn btn-success btn-mini">
                                                                            <i class="fa fa-check"></i> 审核
                                                                        </button>
                                                                    </c:if>
                                                                </c:when>
                                                                <c:when test="${memberApply.stage==APPLY_STAGE_PLAN}">
                                                                    <c:if test="${empty memberApply.drawStatus}">
                                                                        <button onclick="apply_draw(${memberApply.userId})" class="btn btn-success btn-mini">
                                                                            <i class="fa fa-check"></i> 领取志愿书
                                                                        </button>
                                                                    </c:if>
                                                                    <c:if test="${memberApply.drawStatus==0}">
                                                                        <button onclick="apply_draw_check(${memberApply.userId})" class="btn btn-success btn-mini">
                                                                            <i class="fa fa-check"></i> 审核
                                                                        </button>
                                                                    </c:if>
                                                                </c:when>
                                                                <c:when test="${memberApply.stage==APPLY_STAGE_DRAW}">
                                                                    <c:if test="${empty memberApply.growStatus}">
                                                                        <button onclick="apply_grow(${memberApply.userId})" class="btn btn-success btn-mini">
                                                                            <i class="fa fa-check"></i> 领取志愿书
                                                                        </button>
                                                                    </c:if>
                                                                    <c:if test="${memberApply.growStatus==0}">
                                                                        <button onclick="apply_grow_check(${memberApply.userId})" class="btn btn-success btn-mini">
                                                                            <i class="fa fa-check"></i> 审核1
                                                                        </button>
                                                                    </c:if>
                                                                    <c:if test="${memberApply.growStatus==1}">
                                                                        <button onclick="apply_grow_check2(${memberApply.userId})" class="btn btn-success btn-mini">
                                                                            <i class="fa fa-check"></i> 审核2
                                                                        </button>
                                                                    </c:if>
                                                                </c:when>
                                                                <c:when test="${memberApply.stage==APPLY_STAGE_GROW}">
                                                                    <c:if test="${empty memberApply.positiveStatus}">
                                                                        <button onclick="apply_positive(${memberApply.userId})" class="btn btn-success btn-mini">
                                                                            <i class="fa fa-check"></i> 发展为预备党员
                                                                        </button>
                                                                    </c:if>
                                                                    <c:if test="${memberApply.positiveStatus==0}">
                                                                        <button onclick="apply_positive_check(${memberApply.userId})" class="btn btn-success btn-mini">
                                                                            <i class="fa fa-check"></i> 审核1
                                                                        </button>
                                                                    </c:if>
                                                                    <c:if test="${memberApply.positiveStatus==1}">
                                                                        <button onclick="apply_positive_check2(${memberApply.userId})" class="btn btn-success btn-mini">
                                                                            <i class="fa fa-check"></i> 审核2
                                                                        </button>
                                                                    </c:if>
                                                                </c:when>
                                                            </c:choose>
                                                        </div>
                                                        <div class="hidden-md hidden-lg">
                                                            <div class="inline pos-rel">
                                                                <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                                                    <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                                                </button>

                                                            </div>
                                                        </div>
                                                    </td>
                                                    </c:if>
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
                                                            <wo:page commonList="${commonList}" uri="${ctx}/memberApply_page" target="#page-content" pageNum="5"
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

                            <div id="profile3" class="tab-pane">
                                <p>Food truck fixie locavore, accusamus mcsweeney's marfa nulla single-origin coffee squid.</p>
                                <p>Raw denim you probably haven't heard of them jean shorts Austin.</p>
                            </div>

                            <div id="dropdown13" class="tab-pane">
                                <p>Etsy mixtape wayfarers, ethical wes anderson tofu before they sold out mcsweeney's organic lomo retro fanny pack lo-fi farm-to-table readymade.</p>
                                <p>Raw denim you probably haven't heard of them jean shorts Austin.</p>
                            </div>
                        </div>
                    </div>

    </div>

</div>
<script>
    function apply_deny(userId){
        bootbox.confirm("确定拒绝该申请？", function (result) {
            if(result){
                $.post("${ctx}/apply_deny",{userId:userId},function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }
    function apply_pass(userId){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/apply_pass",{userId:userId},function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }
    function apply_active(userId){

        loadModal("${ctx}/apply_active?userId="+userId);
    }
    function apply_candidate(userId){

        loadModal("${ctx}/apply_candidate?userId="+userId);
    }
    function apply_candidate_check(userId){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/apply_candidate_check",{userId:userId},function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    function apply_plan(userId){

        loadModal("${ctx}/apply_plan?userId="+userId);
    }
    function apply_plan_check(userId){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/apply_plan_check",{userId:userId},function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    function apply_draw(userId){

        loadModal("${ctx}/apply_draw?userId="+userId);
    }
    function apply_draw_check(userId){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/apply_draw_check",{userId:userId},function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    function apply_grow(userId){

        loadModal("${ctx}/apply_grow?userId="+userId);
    }
    function apply_grow_check(userId){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/apply_grow_check",{userId:userId},function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }
    function apply_grow_check2(userId){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/apply_grow_check2",{userId:userId},function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }
    function apply_positive(userId){

        loadModal("${ctx}/apply_positive?userId="+userId);
    }
    function apply_positive_check(userId){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/apply_positive_check",{userId:userId},function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }
    function apply_positive_check2(userId){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/apply_positive_check2",{userId:userId},function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    function _reset(){

        _tunePage(1, "", "${ctx}/memberApply_page", "#page-content", "", "&type=${type}&stage=${stage}");
    }
    function _go(type, stage){

        location.href="${ctx}/memberApply?type="+type + "&stage="+ stage;
    }
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
</div>