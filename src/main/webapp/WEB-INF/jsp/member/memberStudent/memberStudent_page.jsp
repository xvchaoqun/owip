<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
                 data-url-au="${ctx}/member_au"
                 data-url-page="${ctx}/memberStudent"
                 data-url-export="${ctx}/memberStudent_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId ||not empty param.unitId
            ||not empty param.age ||not empty param.gender||not empty param.nation||not empty param.nativePlace
            ||not empty param.type ||not empty param.grade
            ||not empty param.eduLevel ||not empty param.eduType
            ||not empty param.politicalStatus
                ||not empty param._growTime ||not empty param._positiveTime ||not empty param._outHandleTime || not empty param.partyId }"/>
            <div class="tabbable">
                <jsp:include page="/WEB-INF/jsp/member/member/member_menu.jsp"/>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="member:modifyStatus">
                                <a href="javascript:;" class="jqEditBtn btn btn-info btn-sm"
                                   data-url="${ctx}/member_modify_status"
                                   data-id-name="userId">
                                    <i class="fa fa-edit"></i> 修改党籍状态</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="member:edit">
                            <a href="javascript:;" class="jqEditBtn btn btn-primary btn-sm"
                               data-open-by="page" data-id-name="userId">
                                <i class="fa fa-edit"></i> 修改党籍信息</a>
                                <button class="jqOpenViewBtn btn btn-success btn-sm"
                                        data-url="${ctx}/memberModify"
                                        data-id-name="userId"
                                        data-open-by="page">
                                    <i class="fa fa-search"></i> 查看修改记录
                                </button>
                                <button id="baseEditBtn" class="jqOpenViewBtn btn btn-warning btn-sm tooltip-success"
                                        data-url="${ctx}/member_studentInfo_au"
                                        data-open-by="page" data-id-name="userId"
                                        data-rel="tooltip" data-placement="top" title="只能修改系统注册账号的基本信息">
                                    <i class="fa fa-edit"></i> 修改基础信息</button>
                            </shiro:hasPermission>

                            <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-download"></i> 导出</a>
                            <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN}">
                                <a class="jqBatchBtn btn btn-danger btn-sm"
                                   data-url="${ctx}/member_batchDel" data-title="删除"
                                   data-msg="确定删除这{0}条数据吗？"><i class="fa fa-trash"></i> 删除</a>
                            </shiro:hasAnyRoles>
                            </div>
                        <div class="jqgrid-vertical-offset widget-box collapsed<%--${_query?'':'collapsed'}--%> hidden-sm hidden-xs">
                            <div class="widget-header">
                                <h4 class="widget-title">搜索</h4>
                                <div class="widget-toolbar">
                                    <a href="javascript:;" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-up<%--${_query?'up':'down'}--%>"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main no-padding">
                                    <form class="form-inline search-form" id="searchForm">
                                        <input type="hidden" name="cls" value="${cls}">
                                                <div class="form-group">
                                                    <label>用户</label>
                                                        <div class="input-group">
                                                            <c:set var="_status" value="${MEMBER_STATUS_NORMAL}"/>
                                                            <c:if test="${cls==6 || cls==7}">
                                                                <c:set var="_status" value="${MEMBER_STATUS_TRANSFER}"/>
                                                            </c:if>
                                                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects?type=${MEMBER_TYPE_STUDENT}&status=${_status}"
                                                                    name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                            </select>
                                                        </div>
                                                </div>

                                                <div class="form-group">
                                                    <label>性别</label>
                                                        <div class="input-group">
                                                            <select name="gender" data-width="100" data-rel="select2" data-placeholder="请选择">
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
                                                        <select name="age" data-width="150" data-rel="select2" data-placeholder="请选择">
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
                                                    <label>民族</label>
                                                    <div class="input-group">
                                                        <select class="multiselect" name="nation" multiple="" >
                                                            <c:forEach items="${studentNations}" var="nation">
                                                                <option value="${nation}">${nation}</option>
                                                            </c:forEach>
                                                        </select>

                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label>籍贯</label>
                                                    <div class="input-group">
                                                        <select class="multiselect" name="nativePlace" multiple="" >
                                                            <c:forEach items="${studentNativePlaces}" var="nativePlace">
                                                                <option value="${nativePlace}">${nativePlace}</option>
                                                            </c:forEach>
                                                        </select>

                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label>学生类别</label>
                                                        <div class="input-group">
                                                            <select name="type" data-rel="select2" data-placeholder="请选择">
                                                                <option></option>
                                                                <c:forEach items="${studentTypes}" var="type">
                                                                    <option value="${type}">${type}</option>
                                                                </c:forEach>
                                                            </select>
                                                            <script>
                                                                $("#searchForm select[name=type]").val('${param.type}');
                                                            </script>
                                                        </div>
                                                </div>
                                                <div class="form-group">
                                                    <label>年级</label>
                                                        <select name="grade" data-width="100" data-rel="select2" data-placeholder="请选择">
                                                            <option></option>
                                                            <c:forEach items="${studentGrades}" var="grade">
                                                                <option value="${grade}">${grade}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=grade]").val('${param.grade}');
                                                        </script>
                                                </div>

                                                <div class="form-group">
                                                    <label>所在${_p_partyName}</label>
                                                        <select class="form-control" data-width="350"  data-rel="select2-ajax"
                                                                data-ajax-url="${ctx}/party_selects?auth=1"
                                                                name="partyId" data-placeholder="请选择">
                                                            <option value="${party.id}" title="${party.isDeleted}">${party.name}</option>
                                                        </select>
                                                </div>
                                                <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                                    <label>所在党支部</label>
                                                        <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects?auth=1"
                                                                name="branchId" data-placeholder="请选择党支部">
                                                            <option value="${branch.id}" title="${branch.isDeleted}">${branch.name}</option>
                                                        </select>
                                                </div>
                                                <script>
                                                    $.register.party_branch_select($("#searchForm"), "branchDiv",
                                                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}" );
                                                </script>
                                        <div class="form-group">
                                            <label>党籍状态</label>
                                            <select required data-rel="select2" name="politicalStatus" data-placeholder="请选择"  data-width="120">
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
                                            <label>入党时间</label>
                                            <div class="input-group tooltip-success" data-rel="tooltip" title="入党时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                <input placeholder="请选择入党时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                                       type="text" name="_growTime" value="${param._growTime}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>转正时间</label>
                                            <div class="input-group tooltip-success" data-rel="tooltip" title="转正时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                <input placeholder="请选择转正时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                                       type="text" name="_positiveTime" value="${param._positiveTime}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>培养层次</label>
                                            <input type="text" name="eduLevel" value="${param.eduLevel}">
                                        </div>
                                        <div class="form-group">
                                            <label>培养类型</label>
                                            <input type="text" name="eduType" value="${param.eduType}">
                                        </div>
                                        <div class="form-group">
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
                                        </div>
                                        <c:if test="${cls==6}">
                                        <div class="form-group">
                                            <label>转出时间</label>
                                            <div class="input-group tooltip-success" data-rel="tooltip" title="转出时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                <input placeholder="请选择转出时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                                       type="text" name="_outHandleTime" value="${param._outHandleTime}"/>
                                            </div>
                                        </div>
                                        </c:if>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                            <c:if test="${_query || not empty param.sort}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm" data-querystr="cls=${cls}">
                                                    <i class="fa fa-reply"></i> 重置
                                                </button>
                                            </c:if>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <%--<div class="space-4"></div>--%>
                        <table id="jqGrid" class="jqGrid table-striped"> </table>
                        <div id="jqGridPager"> </div>
                    </div></div></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>

    $('#searchForm [data-rel="select2"]').select2();

    $.register.multiselect($('#searchForm select[name=nation]'), ${cm:toJSONArray(selectNations)});
    $.register.multiselect($('#searchForm select[name=nativePlace]'), ${cm:toJSONArray(selectNativePlaces)});

    $('[data-rel="tooltip"]').tooltip();
    $.register.user_select($('#searchForm select[name=userId]'));

    $("#jqGrid").jqGrid({
        /*multiboxonly:false,*/
        ondblClickRow:function(){},
        url: '${ctx}/memberStudent_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        sortname:'party',
        colModel: [
            { label: '姓名', name: 'realname', width: 75, formatter:function(cellvalue, options, rowObject){
                return $.member(rowObject.userId, cellvalue);
            }, frozen:true  },
            { label: '学生证号',  name: 'code', width: 120, frozen:true },
            { label: '性别',  name: 'gender', width: 55, frozen:true,formatter:$.jgrid.formatter.GENDER},
            { label: '民族',  name: 'nation', width: 80},
            { label: '籍贯',  name: 'nativePlace', width: 80},
            {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE},
            { label: '学生类别',  name: 'type', width: 150 },
            { label: '年级',  name: 'grade', width: 90 },
            { label:'所属组织机构', name: 'party', width: 550, formatter:function(cellvalue, options, rowObject){
                return $.party(rowObject.partyId, rowObject.branchId);
            },sortable:true, align:'left' },
            { label:'党籍状态',  name: 'politicalStatus', formatter:function(cellvalue, options, rowObject){
                if(cellvalue)
                    return _cMap.MEMBER_POLITICAL_STATUS_MAP[cellvalue];
                return "-";
            }},
            { label:'入党时间',  name: 'growTime', width: 120, sortable:true,formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}},
            { label:'转正时间',  name: 'positiveTime',formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}},
            <c:if test="${cls==6}">
            { label:'转出时间',  name: 'outHandleTime',formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}},
                </c:if>
            { label:'培养层次',  name: 'eduLevel' },
            { label:'培养类型',  name: 'eduType' },
            { label:'所在单位',  name: 'unitId', width: 180, formatter: $.jgrid.formatter.unit},
            {hidden:true, key:true, name:'userId'}, {hidden: true, name: 'partyId'},{hidden: true, name: 'source'}
        ],onSelectRow: function (id, status) {
            saveJqgridSelected("#"+this.id);
            //console.log(id)
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#baseEditBtn").prop("disabled", true);
            } else if (ids.length==1) {

                var rowData = $(this).getRowData(ids[0]);
                //console.log(rowData.source)
                $("#baseEditBtn").prop("disabled", rowData.source == "${USER_SOURCE_JZG}"
                        ||rowData.source == "${USER_SOURCE_BKS}"||rowData.source == "${USER_SOURCE_YJS}");
            }else{
                $("#baseEditBtn").prop("disabled", false);
            }
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    $.initNavGrid("jqGrid", "jqGridPager");
    <shiro:hasRole name="${ROLE_PARTYADMIN}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
                caption:"${_p_partyName}内部组织关系变动",
                btnbase:"branchChangeBtn btn btn-info btn-xs",
                buttonicon:"fa fa-random",
                onClickButton: function(){
                    var ids  = $(this).getGridParam("selarrrow");
                    if(ids.length==0){
                        SysMsg.warning("请选择行", "提示");
                        return ;
                    }
                    //alert(ids)
                    var rowData = $(this).getRowData(ids[0]);
                    //console.log("ids[0]" + ids[0] +rowData)
                    $.loadModal("${ctx}/member_changeBranch?ids[]={0}&partyId={1}".format(ids, rowData.partyId))
                }
            });
    </shiro:hasRole>
    <shiro:hasRole name="${ROLE_ODADMIN}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"校内组织关系转移",
        btnbase:"partyChangeBtn btn btn-danger btn-xs",
        buttonicon:"fa fa-random",
        onClickButton: function(){
            var ids  = $(this).getGridParam("selarrrow");
            if(ids.length==0){
                SysMsg.warning("请选择行", "提示");
                return ;
            }
            $.loadModal("${ctx}/member_changeParty?ids[]={0}".format(ids))
        }
    });
    </shiro:hasRole>
</script>