<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${_pMap['memberApply_timeLimit']=='true'}" var="_memberApply_timeLimit"/>
<c:set var="OW_APPLY_STAGE_MAP" value="<%=OwConstants.OW_APPLY_STAGE_MAP%>"/>
<c:set var="OW_APPLY_STAGE_REMOVE" value="<%=OwConstants.OW_APPLY_STAGE_REMOVE%>"/>
<c:set var="OW_APPLY_STAGE_OUT" value="<%=OwConstants.OW_APPLY_STAGE_OUT%>"/>
<c:set var="OW_APPLY_STAGE_DENY" value="<%=OwConstants.OW_APPLY_STAGE_DENY%>"/>
<c:set var="OW_APPLY_TYPE_TEACHER" value="<%=OwConstants.OW_APPLY_TYPE_TEACHER%>"/>
<c:set var="OW_APPLY_TYPE_STU" value="<%=OwConstants.OW_APPLY_TYPE_STU%>"/>
<c:set var="OW_APPLY_STAGE_INIT" value="<%=OwConstants.OW_APPLY_STAGE_INIT%>"/>
<c:set var="OW_APPLY_STAGE_PASS" value="<%=OwConstants.OW_APPLY_STAGE_PASS%>"/>
<c:set var="OW_APPLY_STAGE_ACTIVE" value="<%=OwConstants.OW_APPLY_STAGE_ACTIVE%>"/>
<c:set var="OW_APPLY_STAGE_CANDIDATE" value="<%=OwConstants.OW_APPLY_STAGE_CANDIDATE%>"/>
<c:set var="OW_APPLY_STAGE_PLAN" value="<%=OwConstants.OW_APPLY_STAGE_PLAN%>"/>
<c:set var="OW_APPLY_STAGE_POSITIVE" value="<%=OwConstants.OW_APPLY_STAGE_POSITIVE%>"/>
<c:set var="OW_APPLY_STAGE_DRAW" value="<%=OwConstants.OW_APPLY_STAGE_DRAW%>"/>
<c:set var="OW_APPLY_STAGE_GROW" value="<%=OwConstants.OW_APPLY_STAGE_GROW%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
            <div class="myTableDiv"
                 data-url-page="${ctx}/memberApply"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId
            ||not empty param.partyId ||not empty param.branchId ||not empty param.growStatus ||not empty param.positiveStatus || not empty param.code || not empty param.sort}"/>
            <div class="widget-box transparent">
                <div class="widget-header">
                    <jsp:include page="menu.jsp"/>
                </div>
                <div class="widget-body">
                    <div class="widget-main" style="padding: 5px 0 0 0">
                        <div class="tab-content padding-4">
                            <div class="tab-pane in active">

                                <div class="tabbable tabs-left">
                                    <%
                                        String[] colors= new String[8];
                                        colors[0]= "badge-grey";
                                        colors[1]= "badge-inverse";
                                        colors[2]= "badge-danger";
                                        colors[3]= "badge-info";
                                        colors[4]= "badge-purple";
                                        colors[5]= "badge-warning";
                                        colors[6]= "badge-pink";
                                        colors[7]= "badge-pink";
                                        //colors[0]= "badge-yellow";
                                    %>
                                    <c:set value="<%=colors%>" var="colors"/>
                                    <ul class="nav nav-tabs" id="stages">
                                        <li class="<c:if test="${stage==-4}">active</c:if>">
                                            <a href="javascript:;" class="hashchange" data-url='${ctx}/memberApply_layout?cls=${cls}&type=${type}&stage=-4'>
                                                 <i class="fa fa-list"></i>  全部申请
                                            </a>
                                        </li>
                                        <li class="<c:if test="${stage==OW_APPLY_STAGE_REMOVE}">active</c:if>">
                                            <a href="javascript:;" class="hashchange" data-url='${ctx}/memberApply_layout?cls=${cls}&type=${type}&stage=${OW_APPLY_STAGE_REMOVE}'>
                                                <i class="fa fa-eraser"></i> 已移除的申请
                                            </a>
                                        </li>
                                        <li class="<c:if test="${stage==OW_APPLY_STAGE_OUT}">active</c:if>">
                                            <a href="javascript:;" class="hashchange" data-url='${ctx}/memberApply_layout?cls=${cls}&type=${type}&stage=${OW_APPLY_STAGE_OUT}'>
                                                <i class="fa fa-outdent"></i>  已转出的申请
                                            </a>
                                        </li>
                                        <c:forEach items="#{OW_APPLY_STAGE_MAP}" var="applyStage">
                                            <li class="<c:if test="${stage==applyStage.key}">active</c:if>">
                                                <a href="javascript:;" class="hashchange" data-url='${ctx}/memberApply_layout?cls=${cls}&type=${type}&stage=${applyStage.key}'>
                                                        <%--<i class='${(stage==applyStageType.key)?"pink":"blue"} ace-icon fa fa-rocket bigger-110'></i>--%>
                                                    <c:set value="${applyStage.key==-1?0:(applyStage.key==0?1:applyStage.key)}" var="colorKey"/>
                                                    <span class="badge ${colors[colorKey]}">${colorKey}</span>
                                                        ${applyStage.value}
                                                            <c:if test="${applyStage.key==OW_APPLY_STAGE_INIT}">
                                                                <c:set var="stageCount" value="${stageCountMap[OW_APPLY_STAGE_INIT]+stageCountMap[OW_APPLY_STAGE_PASS]}"/>
                                                            </c:if>
                                                            <c:if test="${applyStage.key!=OW_APPLY_STAGE_INIT}">
                                                                <c:set var="stageCount" value="${stageCountMap[applyStage.key]}"/>
                                                             </c:if>

                                                    <c:if test="${stageCount>0}">
                                                    <span class="badge badge-success pull-right"
                                                        <c:if test="${applyStage.key!=OW_APPLY_STAGE_POSITIVE}"> data-rel="tooltip" title="${stageCount}条待处理审批" </c:if> >${stageCount}</span>
                                                    </c:if>
                                                </a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                    <div class="tab-content no-padding-top no-padding-bottom" style="overflow-x: hidden">
                                        <div class="tab-pane in active">
                                            <div class="tabbable" >
                                                <ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
                                                    <c:if test="${(stage == OW_APPLY_STAGE_INIT || stage == OW_APPLY_STAGE_DENY) &&
                                                    (_pMap['memberApply_needContinueDevelop']=='true')}">
                                                        <li class="dropdown <c:if test="${type==OW_APPLY_TYPE_STU}">active</c:if>" >
                                                            <a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
                                                                <i class="fa fa-graduation-cap"></i> 学生
                                                                <c:if test="${stage==OW_APPLY_STAGE_INIT}">
                                                                    <c:set value="${OW_APPLY_STAGE_INIT}_${OW_APPLY_TYPE_STU}" var="_key1"/>
                                                                    <c:set value="${OW_APPLY_STAGE_PASS}_${OW_APPLY_TYPE_STU}" var="_key2"/>
                                                                    <c:set var="stageTypeCount" value="${stageTypeCountMap.get(_key1)+stageTypeCountMap.get(_key2)}"/>
                                                                </c:if>
                                                                <c:if test="${stage!=OW_APPLY_STAGE_INIT}">
                                                                    <c:set value="${stage}_${OW_APPLY_TYPE_STU}" var="_key"/>
                                                                    <c:set var="stageTypeCount" value="${stageTypeCountMap.get(_key)}"/>
                                                                </c:if>
                                                                <c:if test="${stageTypeCount>0}">
                                                                <span class="badge badge-success"
                                                                      data-placement="right"
                                                                      <c:if test="${stage!=OW_APPLY_STAGE_POSITIVE}">data-rel="tooltip" title="${stageTypeCount}条待处理审批"</c:if>>${stageTypeCount}</span>
                                                                </c:if>
                                                                <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
                                                            </a>
                                                            <ul class="dropdown-menu dropdown-info" style="min-width: 100px">
                                                                <li>
                                                                    <a href="javascript:;" class="loadPage"
                                                                       data-url="${ctx}/memberApply_layout?cls=${cls}&type=${OW_APPLY_TYPE_STU}&stage=${stage}">
                                                                        <i class="fa fa-hand-o-right"></i> 申请入党</a>
                                                                </li>
                                                                <li>
                                                                    <a href="javascript:;" class="loadPage"
                                                                       data-url="${ctx}/memberApply_layout?cls=${cls}&type=${OW_APPLY_TYPE_STU}&stage=${stage}&isApply=0">
                                                                        <i class="fa fa-hand-o-right"></i> 申请继续培养</a>
                                                                </li>
                                                            </ul>
                                                        </li>
                                                        <li class="dropdown <c:if test="${type==OW_APPLY_TYPE_TEACHER}">active</c:if>" >
                                                            <a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
                                                                <i class="fa fa-user-secret"></i> 教职工
                                                                <c:set value="${stage}_${OW_APPLY_TYPE_TEACHER}" var="_key"/>
                                                                <c:if test="${stage==OW_APPLY_STAGE_INIT}">
                                                                    <c:set value="${OW_APPLY_STAGE_INIT}_${OW_APPLY_TYPE_TEACHER}" var="_key1"/>
                                                                    <c:set value="${OW_APPLY_STAGE_PASS}_${OW_APPLY_TYPE_TEACHER}" var="_key2"/>
                                                                    <c:set var="stageTypeCount" value="${stageTypeCountMap.get(_key1)+stageTypeCountMap.get(_key2)}"/>
                                                                </c:if>
                                                                <c:if test="${stage!=OW_APPLY_STAGE_INIT}">
                                                                    <c:set value="${stage}_${OW_APPLY_TYPE_TEACHER}" var="_key"/>
                                                                    <c:set var="stageTypeCount" value="${stageTypeCountMap.get(_key)}"/>
                                                                </c:if>

                                                                <c:if test="${stageTypeCount>0}">
                                                                <span class="badge badge-success"
                                                                      data-placement="right"
                                                                        <c:if test="${stage!=OW_APPLY_STAGE_POSITIVE}">  data-rel="tooltip" title="${stageTypeCount}条待处理审批" </c:if>>${stageTypeCount}</span>
                                                                </c:if>
                                                                <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
                                                            </a>
                                                            <ul class="dropdown-menu dropdown-info" style="min-width: 100px">
                                                                <li>
                                                                    <a href="javascript:;" class="loadPage"
                                                                       data-url="${ctx}/memberApply_layout?cls=${cls}&type=${OW_APPLY_TYPE_TEACHER}&stage=${stage}">
                                                                        <i class="fa fa-hand-o-right"></i> 申请入党</a>
                                                                </li>
                                                                <li>
                                                                    <a href="javascript:;" class="loadPage"
                                                                       data-url="${ctx}/memberApply_layout?cls=${cls}&type=${OW_APPLY_TYPE_TEACHER}&stage=${stage}&isApply=0">
                                                                        <i class="fa fa-hand-o-right"></i> 申请继续培养</a>
                                                                </li>
                                                            </ul>
                                                        </li>
                                                    </c:if>

                                                    <c:if test="${(stage != OW_APPLY_STAGE_INIT && stage != OW_APPLY_STAGE_DENY) ||
                                                    (_pMap['memberApply_needContinueDevelop']!='true')}">
                                                        <li class="<c:if test="${type==OW_APPLY_TYPE_STU}">active</c:if>">
                                                            <a href="javascript:;" class="hashchange"
                                                               data-url='${ctx}/memberApply_layout?cls=${cls}&type=${OW_APPLY_TYPE_STU}&stage=${stage}'>
                                                                <i class="fa fa-graduation-cap"></i>学生
                                                                <c:if test="${stage==OW_APPLY_STAGE_INIT}">
                                                                    <c:set value="${OW_APPLY_STAGE_INIT}_${OW_APPLY_TYPE_STU}" var="_key1"/>
                                                                    <c:set value="${OW_APPLY_STAGE_PASS}_${OW_APPLY_TYPE_STU}" var="_key2"/>
                                                                    <c:set var="stageTypeCount" value="${stageTypeCountMap.get(_key1)+stageTypeCountMap.get(_key2)}"/>
                                                                </c:if>
                                                                <c:if test="${stage!=OW_APPLY_STAGE_INIT}">
                                                                    <c:set value="${stage}_${OW_APPLY_TYPE_STU}" var="_key"/>
                                                                    <c:set var="stageTypeCount" value="${stageTypeCountMap.get(_key)}"/>
                                                                </c:if>

                                                                <c:if test="${stageTypeCount>0}">
                                                                <span class="badge badge-success"
                                                                      data-placement="right"
                                                                      <c:if test="${stage!=OW_APPLY_STAGE_POSITIVE}">data-rel="tooltip" title="${stageTypeCount}条待处理审批"</c:if>>${stageTypeCount}</span>
                                                                </c:if>
                                                            </a>
                                                        </li>

                                                        <li class="<c:if test="${type==OW_APPLY_TYPE_TEACHER}">active</c:if>">
                                                            <a href="javascript:;" class="hashchange" data-url='${ctx}/memberApply_layout?cls=${cls}&type=${OW_APPLY_TYPE_TEACHER}&stage=${stage}'>
                                                                <i class="fa fa-user-secret"></i> 教职工
                                                                <c:set value="${stage}_${OW_APPLY_TYPE_TEACHER}" var="_key"/>
                                                                <c:if test="${stage==OW_APPLY_STAGE_INIT}">
                                                                    <c:set value="${OW_APPLY_STAGE_INIT}_${OW_APPLY_TYPE_TEACHER}" var="_key1"/>
                                                                    <c:set value="${OW_APPLY_STAGE_PASS}_${OW_APPLY_TYPE_TEACHER}" var="_key2"/>
                                                                    <c:set var="stageTypeCount" value="${stageTypeCountMap.get(_key1)+stageTypeCountMap.get(_key2)}"/>
                                                                </c:if>
                                                                <c:if test="${stage!=OW_APPLY_STAGE_INIT}">
                                                                    <c:set value="${stage}_${OW_APPLY_TYPE_TEACHER}" var="_key"/>
                                                                    <c:set var="stageTypeCount" value="${stageTypeCountMap.get(_key)}"/>
                                                                </c:if>

                                                                <c:if test="${stageTypeCount>0}">
                                                                <span class="badge badge-success"
                                                                      data-placement="right"
                                                                        <c:if test="${stage!=OW_APPLY_STAGE_POSITIVE}">  data-rel="tooltip" title="${stageTypeCount}条待处理审批" </c:if>>${stageTypeCount}</span>
                                                                </c:if>
                                                            </a>
                                                        </li>
                                                    </c:if>

                                                    <c:if test="${isApply}">
                                                    <div class="buttons" style="left: 300px;position: absolute">
                                                        <c:if test="${stage>=OW_APPLY_STAGE_INIT&&stage<OW_APPLY_STAGE_GROW}">
                                                        <a href="javascript:;" class="openView btn btn-info btn-sm"
                                                        data-url="${ctx}/memberApply_au?stage=${stage}&op=add">
                                                            <i class="fa fa-plus"></i> 添加</a>
                                                        <shiro:hasPermission name="memberApply:import">
                                                            <a class="popupBtn btn btn-primary btn-sm tooltip-primary"
                                                               data-url="${ctx}/memberApply_import"
                                                               data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                                                                批量导入</a>
                                                        </shiro:hasPermission>
                                                        </c:if>
                                                        <a class="popupBtn btn btn-danger btn-sm"
                                                               data-rel="tooltip" data-placement="bottom" title="可查询所有教职工和学生的党员发展状态"
                                                                    data-url="${ctx}/memberApply_search"><i class="fa fa-search"></i> 全校党员发展查询</a>
                                                    </div>
                                                    </c:if>
                                                </ul>
                                                <div class="tab-content no-padding-bottom" style="overflow: hidden">
                                                    <div class="tab-pane in active">
                                                        <c:if test="${isApply}">
                                                            <jsp:include page="/WEB-INF/jsp/member/memberApply/memberApply_apply.jsp"/>
                                                        </c:if>
                                                        <c:if test="${!isApply}">
                                                            <jsp:include page="/WEB-INF/jsp/member/memberApply/memberApply_continue.jsp"/>
                                                        </c:if>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
        </div>
        <div id="body-content-view">

        </div>
    </div>
</div>
<script>

    function goto_next(gotoNext){
        if(gotoNext==1){
            if($("#next").hasClass("disabled") && $("#last").hasClass("disabled") )
                $.hashchange();
            else if(!$("#next").hasClass("disabled"))
                $("#next").click();
            else
                $("#last").click();
        }else{
            page_reload();
        }
    }
    function apply_deny(userId, gotoNext){

        SysMsg.confirm("确定拒绝该申请？", "操作确认", function () {
            $.post("${ctx}/apply_deny",{ids:[userId]},function(ret){
                if(ret.success){
                    //page_reload();
                    //SysMsg.success('操作成功。', '成功');
                    goto_next(gotoNext);
                }
            });
        });
    }
    function apply_pass(userId, gotoNext){
        SysMsg.confirm("确定通过该申请？", "操作确认", function () {
            $.post("${ctx}/apply_pass",{ids:[userId]},function(ret){
                if(ret.success){
                    //page_reload();
                    //SysMsg.success('操作成功。', '成功');
                    goto_next(gotoNext);
                }
            });
        });
    }
    function apply_active(userId, gotoNext){
        var url = "${ctx}/apply_active?ids="+userId;
        if(gotoNext!=undefined)
            url += "&gotoNext="+ gotoNext;
        $.loadModal(url);
    }
    function apply_candidate(userId, gotoNext){
        var url = "${ctx}/apply_candidate?ids="+userId;
        if(gotoNext!=undefined)
            url += "&gotoNext="+ gotoNext;
        $.loadModal(url);
    }
    function apply_candidate_check(userId, gotoNext){

        SysMsg.confirm("确定通过该申请？", "操作确认", function () {
            $.post("${ctx}/apply_candidate_check",{ids:[userId]},function(ret){
                if(ret.success){
                    //page_reload();
                    //SysMsg.success('操作成功。', '成功');
                    goto_next(gotoNext);
                }
            });
        });
    }

    function apply_plan(userId, gotoNext){

        var url = "${ctx}/apply_plan?ids="+userId;
        if(gotoNext!=undefined)
            url += "&gotoNext="+ gotoNext;
        $.loadModal(url);
    }
    function apply_plan_check(userId, gotoNext){
        SysMsg.confirm("确定通过该申请？", "操作确认", function () {
            $.post("${ctx}/apply_plan_check",{ids:[userId]},function(ret){
                if(ret.success){
                    //page_reload();
                    //SysMsg.success('操作成功。', '成功');
                    goto_next(gotoNext);
                }
            });
        });
    }

    function apply_draw(userId, gotoNext){

        var url = "${ctx}/apply_draw?ids="+userId;
        if(gotoNext!=undefined)
            url += "&gotoNext="+ gotoNext;
        $.loadModal(url);
    }

    function apply_grow(userId, gotoNext){
        var url = "${ctx}/apply_grow?ids="+userId;
        if(gotoNext!=undefined)
            url += "&gotoNext="+ gotoNext;
        $.loadModal(url);
    }
    function apply_grow_check(userId, gotoNext){
        SysMsg.confirm("确定通过该申请？", "操作确认", function () {
            $.post("${ctx}/apply_grow_check",{ids:[userId]},function(ret){
                if(ret.success){
                    //page_reload();
                    //SysMsg.success('操作成功。', '成功');
                    goto_next(gotoNext);
                }
            });
        });
    }
    function apply_grow_od_check(userId, gotoNext){

        var url = "${ctx}/apply_grow_od_check?ids="+userId;
        if(gotoNext!=undefined)
            url += "&gotoNext="+ gotoNext;
        $.loadModal(url);
    }
    function apply_positive(userId, gotoNext){
        var url = "${ctx}/apply_positive?ids="+userId;
        if(gotoNext!=undefined)
            url += "&gotoNext="+ gotoNext;
        $.loadModal(url)
    }
    function apply_positive_check(userId, gotoNext){
        SysMsg.confirm("确定通过该申请？", "操作确认", function () {
            $.post("${ctx}/apply_positive_check",{ids:[userId]},function(ret){
                if(ret.success){
                    //page_reload();
                    //SysMsg.success('操作成功。', '成功');
                    goto_next(gotoNext);
                }
            });
        });
    }
    function apply_positive_check2(userId, gotoNext){
        SysMsg.confirm("确定通过该申请？", "操作确认", function () {
            $.post("${ctx}/apply_positive_check2",{ids:[userId]},function(ret){
                if(ret.success){
                    //page_reload();
                    //SysMsg.success('操作成功。', '成功');
                    goto_next(gotoNext);
                }
            });
        });
    }
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip({container:'#page-content'});
    $.register.user_select($('#searchForm select[name=userId]'));

</script>