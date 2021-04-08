<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=MemberConstants.MEMBER_TYPE_TEACHER%>" var="MEMBER_TYPE_TEACHER"/>
<c:set value="<%=MemberConstants.MEMBER_TYPE_STUDENT%>" var="MEMBER_TYPE_STUDENT"/>
<c:set value="<%=MemberConstants.MEMBER_STATUS_NORMAL%>" var="MEMBER_STATUS_NORMAL"/>

<c:set value="${_pMap['owCheckIntegrity']=='true'}" var="_p_owCheckIntegrity"/>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/member_au"
             data-url-page="${ctx}/member"
             data-url-export="${ctx}/member_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId ||not empty param.realname ||not empty param.unitId
             ||not empty param.age ||not empty param.startAge ||not empty param.endAge||not empty param.gender||not empty selectNations||not empty param.nativePlace
             ||not empty param.eduLevel ||not empty param.eduType
             ||not empty param.education ||not empty param.postClass||not empty param.staffStatus
             ||not empty param._retireTime ||not empty param.isHonorRetire
             ||not empty param.politicalStatus||not empty param.userSource
                ||not empty param._growTime ||not empty param._positiveTime
                ||not empty param._outHandleTime || not empty param.partyId
                ||not empty param._integrity||not empty param.studentType||not empty param.grade
                ||not empty param.remark||not empty param.remark1
                ||not empty param.remark2||not empty param.remark3||not empty param.remark4
                ||not empty param.remark5||not empty param.remark6}"/>
            <div class="tabbable">
                <jsp:include page="/WEB-INF/jsp/member/member/member_menu.jsp"/>

                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${cls<6}">
                                <shiro:hasPermission name="member:changeCode">
                                    <a href="javascript:;" class="jqEditBtn btn btn-warning btn-sm"
                                       data-url="${ctx}/member_changeCode"
                                       data-id-name="userId">
                                        <i class="fa fa-refresh"></i> 更换学工号</a>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="member:modifyStatus">
                                    <a href="javascript:;" class="jqEditBtn btn btn-info btn-sm"
                                       data-url="${ctx}/member_modify_status"
                                       data-id-name="userId">
                                        <i class="fa fa-star-o"></i> 修改党籍状态</a>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="member:edit">
                                    <a href="javascript:;" class="jqEditBtn btn btn-primary btn-sm"
                                       data-open-by="page" data-id-name="userId">
                                        <i class="fa fa-star"></i> 修改党籍信息</a>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="memberBaseInfo:edit">
                                    <button class="jqOpenViewBtn btn btn-success btn-sm tooltip-success"
                                            data-url="${ctx}/memberBaseInfo_au"
                                            data-open-by="page" data-id-name="userId"
                                            data-rel="tooltip" data-placement="top" title="修改账号基本信息">
                                        <i class="fa fa-info-circle"></i> 修改基础信息
                                    </button>
                                </shiro:hasPermission>
                            <shiro:hasPermission name="memberHistory:edit">
                                <a class="jqOpenViewBatchBtn btn btn-info btn-sm"
                                   data-url="${ctx}/transferToHistory" data-title="转移至历史党员库"
                                   data-msg="确定转移这{0}个党员至历史党员吗？"><i class="fa fa-random"></i> 转移至历史党员库</a>
                            </shiro:hasPermission>
                                </c:if>
                            <shiro:hasPermission name="member:edit">
                            <button class="jqOpenViewBtn btn btn-warning btn-sm"
                                    data-url="${ctx}/memberModify"
                                    data-id-name="userId"
                                    data-open-by="page">
                                <i class="fa fa-history"></i> 修改记录
                            </button>
                            </shiro:hasPermission>
                            <div class="btn-group">
                                <button data-toggle="dropdown"
                                        data-rel="tooltip" data-placement="top" data-html="true"
                                        title="<div style='width:180px'>导出选中记录或所有搜索结果</div>"
                                        class="btn btn-success btn-sm dropdown-toggle tooltip-success">
                                    <i class="fa fa-download"></i> 导出 <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu dropdown-success" role="menu" style="z-index: 1031">
                                    <li class="dropdown-hover" data-stopPropagation="true">
                                        <a href="javascript:;">
                                            <i class="fa fa-file-excel-o"></i> 导出党员信息
                                            <i class="ace-icon fa fa-caret-right pull-right"></i>
                                        </a>
                                        <div class="dropdown-menu" style="width: 675px;top:-220px;">
                                            <form class="form-horizontal" id="exportForm">
                                                <div style="padding: 7px 0 10px 10px">
                                                    <c:forEach items="${titles}" var="title" varStatus="vs">
                                                        <div style="padding-left:5px;float: left;width:220px">
                                                            <input class="big" type="checkbox" value="${vs.index}"
                                                                   checked>
                                                                ${fn:split(title, "|")[0]}</div>
                                                    </c:forEach>
                                                    <div style="clear: both"/>
                                                </div>
                                                <div class="form-actions center">
                                                    <div style="position: absolute; float:left; left:10px;padding-top: 3px">
                                                        <input type="button" id="btnSelectAll"
                                                               class="btn btn-success btn-xs" value="全选"/>
                                                        <input type="button" id="btnDeselectAll"
                                                               class="btn btn-danger btn-xs" value="全不选"/>
                                                    </div>
                                                    <button type="button" class="jqExportBtn btn btn-success"
                                                            data-need-id="false" data-url="${ctx}/member_data?cls=${cls}"
                                                            data-querystr="format=1">
                                                        <i class="fa fa-file-excel-o"></i> 导出
                                                    </button>
                                                </div>
                                            </form>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                            <c:if test="${cls<6}">
                                <shiro:hasPermission name="member:del">
                                    <a class="jqBatchBtn btn btn-danger btn-sm"
                                       data-url="${ctx}/member_batchDel" data-title="删除"
                                       data-msg="确定删除这{0}位党员吗？<br/>（相关党员数据将全部删除，请谨慎操作！）"><i class="fa fa-trash"></i> 删除</a>
                                </shiro:hasPermission>
                            </c:if>
                        </div>
                        <div class="jqgrid-vertical-offset widget-box collapsed<%--${_query?'':'collapsed'}--%> hidden-sm hidden-xs">
                            <div class="widget-header">
                                <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>
                                <div class="widget-toolbar">
                                    <a href="javascript:;" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-up<%--${_query?'up':'down'}--%>"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main no-padding">
                                    <form class="form-inline search-form" id="searchForm">
                                        <input type="hidden" name="cols">
                                        <input type="hidden" name="cls" value="${cls}">
                                        <input type="hidden" name="userType" value="${param.userType}">
                                        <c:if test="${cls<6}">
                                            <div class="form-group">
                                                <label>所在${_p_partyName} <span class="prompt" data-title="查询说明"
							  data-prompt="选择${_p_partyName}后，会出现党支部的选择（二级联动）"><i class="fa fa-question-circle-o"></i></span></label>
                                                <select class="form-control" data-width="250" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/party_selects?auth=1"
                                                        name="partyId" data-placeholder="请选择">
                                                    <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                                </select>
                                            </div>
                                            <div class="form-group" style="${(empty branch)?'display: none':''}"
                                                 id="branchDiv">
                                                <label>所在党支部</label>
                                                <select class="form-control" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/branch_selects?auth=1"
                                                        name="branchId" data-placeholder="请选择党支部">
                                                    <option value="${branch.id}" delete="${branch.isDeleted}">${branch.name}</option>
                                                </select>
                                            </div>
                                            <script>
                                                $.register.party_branch_select($("#searchForm"), "branchDiv",
                                                    '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                            </script>
                                        </c:if>
                                        <div class="form-group">
                                            <label>党籍状态</label>
                                            <select required data-rel="select2" name="politicalStatus"
                                                    data-placeholder="请选择" data-width="120">
                                                <option></option>
                                                <c:forEach items="${MEMBER_POLITICAL_STATUS_MAP}" var="_status">
                                                    <option value="${_status.key}">${_status.value}</option>
                                                </c:forEach>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=politicalStatus]").val(${param.politicalStatus});
                                            </script>
                                        </div>
                                        <div class="form-group">
                                            <label>姓名/学工号 <span class="prompt" data-title="查询说明"
							                    data-prompt="按姓名或学工号进行模糊查询"><i class="fa fa-question-circle-o"></i></span></label>
                                            <div class="input-group">
                                                <input class="form-control search-query" name="realname" type="text"
                                                       value="${param.realname}"
                                                       placeholder="请输入">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>党员姓名<span class="prompt" data-title="查询说明"
							  data-prompt="按姓名或学工号进行精准查询"><i class="fa fa-question-circle-o"></i></span></label>
                                            <div class="input-group">
                                                <c:if test="${cls==1 || cls==6}">
                                                    <c:set var="_type" value="${MEMBER_TYPE_STUDENT}"/>
                                                </c:if>
                                                <c:if test="${cls==2 || cls==7}">
                                                    <c:set var="_type" value="${MEMBER_TYPE_TEACHER}"/>
                                                </c:if>
                                                <c:if test="${cls==1 || cls==2}">
                                                    <c:set var="_status" value="${MEMBER_STATUS_NORMAL}"/>
                                                </c:if>
                                                <c:if test="${cls==6 || cls==7}">
                                                    <c:set var="_status" value="${MEMBER_STATUS_OUT}"/>
                                                </c:if>
                                                <select data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/member_selects?type=${_type}&status=${_status}"
                                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                </select>

                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>身份证号码</label>
                                            <input class="form-control search-query" name="idcard" type="text"
                                                   value="${param.idcard}"
                                                   placeholder="请输入身份证号码">
                                        </div>
                                        <%--<div class="form-group">
                                            <label>${_p_partyName}所在单位</label>
                                            <select name="unitId" data-rel="select2" data-placeholder="请选择">
                                                <option></option>
                                                <c:forEach items="${unitMap}" var="unit">
                                                    <option value="${unit.key}">${unit.value.name}</option>
                                                </c:forEach>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=unitId]").val('${param.unitId}');
                                            </script>
                                        </div>--%>
                                        <div class="form-group">
                                            <label>性别</label>
                                            <div class="input-group">
                                                <select name="gender" data-width="100" data-rel="select2"
                                                        data-placeholder="请选择">
                                                    <option></option>
                                                    <c:forEach items="${GENDER_MAP}" var="entity">
                                                        <option value="${entity.key}">${entity.value}</option>
                                                    </c:forEach>
                                                </select>
                                                <script>
                                                    $("#searchForm select[name=gender]").val('${param.gender}');
                                                </script>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>年龄</label>
                                            <select name="age" data-width="150" data-rel="select2"
                                                    data-placeholder="请选择">
                                                <option></option>
                                                <c:forEach items="<%=MemberConstants.MEMBER_AGE_MAP%>" var="age">
                                                    <option value="${age.key}">${age.value}</option>
                                                </c:forEach>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=age]").val('${param.age}');
                                            </script>
                                        </div>
                                        <div class="form-group">
                                            <label>起止年龄</label>
                                            <div class="input-group">
                                                <input class="num" type="text" name="startAge"
                                                       value="${param.startAge}" style="width: 50px!important;"> 至 <input class="num" type="text" name="endAge" value="${param.endAge}" style="width: 50px!important;">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>民族</label>
                                            <div class="input-group">
                                                <select class="multiselect" multiple="" name="nation">
                                                    <c:forEach var="nation" items="${cm:getMetaTypes('mc_nation').values()}">
                                                        <option value="${nation.name}">${nation.name}</option>
                                                    </c:forEach>
                                                    <option value="0">无数据</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>籍贯</label>
                                            <div class="input-group">
                                                <select class="multiselect" name="nativePlace" multiple="">
                                                    <option value="0">无数据</option>
                                                    <c:forEach items="${nativePlaces}" var="nativePlace">
                                                        <option value="${nativePlace}">${nativePlace}</option>
                                                    </c:forEach>
                                                </select>

                                            </div>
                                        </div>
                                        <c:if test="${cls==1||cls==6}">
                                            <div class="form-group">
                                                <label>学生类别</label>
                                                <input class="form-control search-query" name="studentType" type="text"
                                                       value="${param.studentType}"
                                                       placeholder="请输入学生类别">
                                            </div>
                                            <div class="form-group">
                                                <label>年级</label>
                                                <input class="form-control search-query" name="grade" type="text" style="width: 100px!important;"
                                                       value="${param.grade}"
                                                       placeholder="请输入年级">
                                            </div>
                                        </c:if>
                                        <c:if test="${cls==1 || cls==6}">
                                            <div class="form-group">
                                                <label>培养层次</label>
                                                <input type="text" name="eduLevel" value="${param.eduLevel}">
                                            </div>
                                            <div class="form-group">
                                                <label>培养类型</label>
                                                <input type="text" name="eduType" value="${param.eduType}">
                                            </div>
                                        </c:if>

                                        <c:if test="${cls==2 || cls==3 || cls==7}">
                                            <div class="form-group">
                                                <label>最高学历</label>
                                                <div class="input-group">
                                                    <select name="education" data-rel="select2" data-placeholder="请选择">
                                                        <option></option>
                                                        <c:forEach items="${teacherEducationTypes}" var="education">
                                                            <option value="${education}">${education}</option>
                                                        </c:forEach>
                                                        <option value="0">无数据</option>
                                                    </select>
                                                    <script>
                                                        $("#searchForm select[name=education]").val('${param.education}');
                                                    </script>
                                                </div>
                                            </div>

                                            <%--<div class="form-group">
                                                <label>岗位类别</label>
                                                <select name="postClass" data-width="150" data-rel="select2"
                                                        data-placeholder="请选择">
                                                    <option></option>
                                                    <c:forEach items="${teacherPostClasses}" var="postClass">
                                                        <option value="${postClass}">${postClass}</option>
                                                    </c:forEach>
                                                </select>
                                                <script>
                                                    $("#searchForm select[name=postClass]").val('${param.postClass}');
                                                </script>
                                            </div>--%>
                                            <div class="form-group">
                                                <label>人员状态</label>
                                                <select name="staffStatus" data-width="150" data-rel="select2"
                                                        data-placeholder="请选择">
                                                    <option></option>
                                                    <c:forEach items="${staffStatuses}" var="staffStatus">
                                                        <option value="${staffStatus}">${staffStatus}</option>
                                                    </c:forEach>
                                                </select>
                                                <script>
                                                    $("#searchForm select[name=staffStatus]").val('${param.staffStatus}');
                                                </script>
                                            </div>
                                            <c:if test="${cls==3 || cls==7}">
                                                <%--<div class="form-group">
                                                    <label>退休时间</label>
                                                    <div class="input-group tooltip-success" data-rel="tooltip"
                                                         title="退休时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                        <input placeholder="请选择退休时间范围" data-rel="date-range-picker"
                                                               class="form-control date-range-picker"
                                                               type="text" name="_retireTime"
                                                               value="${param._retireTime}"/>
                                                    </div>
                                                </div>--%>
                                                <%--<div class="form-group">
                                                    <label>是否离休</label>
                                                    <select name="isHonorRetire" data-width="100" data-rel="select2"
                                                            data-placeholder="请选择">
                                                        <option></option>
                                                        <option value="1">是</option>
                                                        <option value="0">否</option>
                                                    </select>
                                                    <script>
                                                        $("#searchForm select[name=isHonorRetire]").val('${param.isHonorRetire}');
                                                    </script>
                                                </div>--%>
                                            </c:if>
                                        </c:if>

                                        <div class="form-group">
                                            <label>入党时间</label>
                                            <div class="input-group tooltip-success" data-rel="tooltip" title="入党时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                <input placeholder="请选择入党时间范围" data-rel="date-range-picker"
                                                       class="form-control date-range-picker"
                                                       type="text" name="_growTime" value="${param._growTime}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>转正时间</label>
                                            <div class="input-group tooltip-success" data-rel="tooltip" title="转正时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                <input placeholder="请选择转正时间范围" data-rel="date-range-picker"
                                                       class="form-control date-range-picker"
                                                       type="text" name="_positiveTime" value="${param._positiveTime}"/>
                                            </div>
                                        </div>


                                        <c:if test="${cls==6||cls==7}">
                                            <div class="form-group">
                                                <label>转出时间</label>
                                                <div class="input-group tooltip-success" data-rel="tooltip"
                                                     title="转出时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                    <input placeholder="请选择转出时间范围" data-rel="date-range-picker"
                                                           class="form-control date-range-picker"
                                                           type="text" name="_outHandleTime"
                                                           value="${param._outHandleTime}"/>
                                                </div>
                                            </div>
                                        </c:if>

                                        <div class="form-group">
                                            <label>账号来源</label>
                                            <select name="userSource" data-width="120" data-placeholder="请选择"
                                                    data-rel="select2">
                                                <option></option>
                                                <c:forEach items="<%=SystemConstants.USER_SOURCE_MAP%>"
                                                           var="userSource">
                                                    <option value="${userSource.key}">${userSource.value}</option>
                                                </c:forEach>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=userSource]").val('${param.userSource}');
                                            </script>
                                        </div>
                                        <c:if test="${_p_owCheckIntegrity}">
                                        <div class="form-group">
                                            <label>信息完整度</label>
                                            <select name="_integrity" data-width="100" data-rel="select2" data-placeholder="请选择">
                                                <option></option>
                                                <option value="1">完整</option>
                                                <option value="0">不完整</option>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=_integrity]").val('${param._integrity}');
                                            </script>
                                        </div>
                                            </c:if>
                                        <div class="form-group">
                                            <label>备注<span class="prompt" data-title="查询说明"
							  data-prompt="从“备注1”至“备注3”中查询"><i class="fa fa-question-circle-o"></i></span></label>
                                            <input type="text" name="remark" value="${param.remark}" style="width: 80px">
                                        </div>
                                        <div class="form-group">
                                            <label>备注1</label>
                                            <input type="text" name="remark1" value="${param.remark1}" style="width: 80px">
                                        </div>
                                        <div class="form-group">
                                            <label>备注2</label>
                                            <input type="text" name="remark2" value="${param.remark2}" style="width: 80px">
                                        </div>
                                        <div class="form-group">
                                            <label>备注3</label>
                                            <input type="text" name="remark3" value="${param.remark3}" style="width: 80px">
                                        </div>
                                        <%--<div class="form-group">
                                            <label>备注4</label>
                                            <input type="text" name="remark4" value="${param.remark4}" style="width: 80px">
                                        </div>
                                        <div class="form-group">
                                            <label>备注5</label>
                                            <input type="text" name="remark5" value="${param.remark5}" style="width: 80px">
                                        </div>
                                        <div class="form-group">
                                            <label>备注6</label>
                                            <input type="text" name="remark6" value="${param.remark6}" style="width: 80px">
                                        </div>--%>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query || not empty param.sort}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-querystr="cls=${cls}">
                                                    <i class="fa fa-reply"></i> 重置
                                                </button>
                                            </c:if>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="space-4"></div>
                        <table id="jqGrid" class="jqGrid table-striped"></table>
                        <div id="jqGridPager"></div>

                    </div>
                </div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>

    $("ul.dropdown-menu").on("click", "[data-stopPropagation]", function (e) {
        //console.log($(e.target).hasClass("jqExportBtn"))
        if (!$(e.target).hasClass("jqExportBtn")) {
            e.stopPropagation();
        }
    });
    $("#btnSelectAll").click(function () {
        $("#exportForm :checkbox").prop("checked", true);
        _updateCols()
    });
    $("#btnDeselectAll").click(function () {
        $("#exportForm :checkbox").prop("checked", false);
        _updateCols()
    });
    $("#exportForm :checkbox").click(function () {
        _updateCols()
    });

    function _updateCols() {
        var cols = $.map($("#exportForm :checkbox:checked"), function (chk) {
            return $(chk).val();
        });
        $("#searchForm input[name=cols]").val(cols.join(','));
    }

    $('#searchForm [data-rel="select2"]').select2();

    $.register.multiselect($('#searchForm select[name=nation]'), ${cm:toJSONArray(selectNations)});
    $.register.multiselect($('#searchForm select[name=nativePlace]'), ${cm:toJSONArray(selectNativePlaces)});

    $('[data-rel="tooltip"]').tooltip();
    $.register.user_select($('#searchForm select[name=userId]'));

    $("#jqGrid").jqGrid({
        /*multiboxonly:false,*/
        ondblClickRow: function () {
        },
        url: '${ctx}/member_data?callback=?&cls=${cls}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '姓名', name: 'realname', width: 75, formatter: function (cellvalue, options, rowObject) {
                    return $.member(rowObject.userId, cellvalue);
                }, frozen: true
            },
            {label: '学工号', name: 'code', width: 120, frozen: true},
            <c:if test="${cls==10}">
                {label: '人员类别', name: 'userType', formatter : function (cellvalue, options, rowObject) {
                    return _cMap.USER_TYPE_MAP[cellvalue];
                }},
            </c:if>
            <c:if test="${_p_owCheckIntegrity}">
            {label: '信息完整度', name: 'integrity',frozen: true,width: 120,formatter: function (cellvalue, options, rowObject) {

                    var progress = Math.formatFloat(Math.trimToZero(rowObject.integrity)*100, 1) + "%";
                    return ('<a href="javascript:;" class="jqEditBtn" data-url="${ctx}/member_integrity" data-id-name="userId">' +
                        '<div class="progress progress-striped pos-rel" data-percent="{0}">' +
                        '<div class="progress-bar progress-bar-{1}" style="width:{0}"></div></div></a>')
                        .format(progress,rowObject.integrity==1?"success":"danger")
                },sortable: true, align: 'left'},
            </c:if>
            {label: '性别', name: 'gender', width: 55, formatter: $.jgrid.formatter.GENDER},
            {label: '民族', name: 'nation'},
            {label: '籍贯', name: 'nativePlace', width: 120},
            {label: '年龄', name: 'birth', width: 75,sortable: true, formatter: $.jgrid.formatter.AGE},

                {
                    label: '所在党组织', name: 'party', width: 550, formatter: function (cellvalue, options, rowObject) {
                        return $.party(rowObject.partyId, rowObject.branchId);
                    }, sortable: true, align: 'left'
                },
            {
                label: '党籍状态', name: 'politicalStatus', formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue)
                        return _cMap.MEMBER_POLITICAL_STATUS_MAP[cellvalue];
                    return "-";
                }
            },
            {
                label: '入党时间',
                name: 'growTime',
                width: 120,
                sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {
                label: '转正时间',
                name: 'positiveTime',
                sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            <c:if test="${cls==1 || cls==6}">
            {label: '学生类别', name: 'studentType', width: 150},
            {label: '年级', name: 'grade', width: 90},
            {label: '培养层次', name: 'eduLevel'},
            {label: '培养类型', name: 'eduType'},
            </c:if>
            <c:if test="${cls==2 || cls==7}">
            {label: '最高学历', name: 'education', width: 120},
            {label: '编制类别', name: 'authorizedType'},
            {label: '人员类别', name: 'staffType'},
            /*{label: '岗位类别', name: 'postClass'},*/
            {label: '专业技术职务', name: 'proPost', width: 150},
            {label: '联系手机', name: 'mobile', width: 110},
            </c:if>
            <c:if test="${cls==6 || cls==7}">
            {
                label: '转出时间',
                name: 'outHandleTime',
                sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            </c:if>
            <c:if test="${cls==2 || cls==3||cls==7}">
            {label: '人员状态', name: 'staffStatus', width: 120},
            </c:if>
            {label: '备注1', name: 'remark1', width: 150,align:'left'},
            {label: '备注2', name: 'remark2', width: 150,align:'left'},
            {label: '备注3', name: 'remark3', width: 150,align:'left'},
            /*{label: '备注4', name: 'remark4', width: 150,align:'left'},
            {label: '备注5', name: 'remark5', width: 150,align:'left'},
            {label: '备注6', name: 'remark6', width: 150,align:'left'},*/
            /*{label: '所在单位', name: 'unitId', width: 180, align: 'left', formatter: $.jgrid.formatter.unit},
            {label: '所在院系', name: 'unit', width: 180, align: 'left'},*/
            {hidden: true, key: true, name: 'userId'}, {hidden: true, name: 'partyId'}, {hidden: true, name: 'source'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    $.initNavGrid("jqGrid", "jqGridPager");
    <shiro:hasRole name="${ROLE_PARTYADMIN}">
    $("#jqGrid").navButtonAdd('#jqGridPager', {
        caption: "${_p_partyName}内部组织关系变动",
        btnbase: "branchChangeBtn btn btn-info btn-xs",
        buttonicon: "fa fa-random",
        onClickButton: function () {
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length == 0) {
                SysMsg.warning("请选择行", "提示");
                return;
            }
            //alert(ids)
            var rowData = $(this).getRowData(ids[0]);
            //console.log("ids[0]" + ids[0] +rowData)
            $.loadModal("${ctx}/member_changeBranch?ids={0}&partyId={1}".format(ids, rowData.partyId))
        }
    });
    </shiro:hasRole>
    <shiro:hasRole name="${ROLE_ODADMIN}">
    $("#jqGrid").navButtonAdd('#jqGridPager', {
        caption: "校内组织关系转移",
        btnbase: "partyChangeBtn btn btn-danger btn-xs",
        buttonicon: "fa fa-random",
        onClickButton: function () {
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length == 0) {
                SysMsg.warning("请选择行", "提示");
                return;
            }
            $.loadModal("${ctx}/member_changeParty?ids={0}".format(ids))
        }
    });
    </shiro:hasRole>
</script>