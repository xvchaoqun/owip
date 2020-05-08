<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${_pMap['owCheckIntegrity']=='true'}" var="_p_owCheckIntegrity"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
        <div class="myTableDiv"
             data-url-au="${ctx}/branch_au"
             data-url-page="${ctx}/branch"
             data-url-export="${ctx}/branch_data"
             data-url-co="${ctx}/branch_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param._foundTime || not empty param.code
                                ||not empty param.name ||not empty param.partyId
                                ||not empty param.isStaff||not empty param.isPrefessional||not empty param.isBaseTeam
                                ||not empty param.types ||not empty param.unitTypeId ||not empty param._integrity}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>

                <div class="tab-content">
                    <div class="tab-pane in active">
            <div class="jqgrid-vertical-offset buttons">
            <c:if test="${cls==1}">
                    <shiro:hasPermission name="branch:add">
                    <a class="editBtn btn btn-info btn-sm" data-width="900"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="branch:edit">
                     <a href="javascript:;" class="jqEditBtn btn btn-primary btn-sm"  data-width="900">
                    <i class="fa fa-edit"></i> 修改信息</a>
                    </shiro:hasPermission>
                 <shiro:hasPermission name="branch:import">
                    <button class="popupBtn btn btn-info btn-sm tooltip-info"
                            data-url="${ctx}/branch_import"
                            data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                        批量导入
                    </button>
                </shiro:hasPermission>
                <%--<shiro:hasPermission name="member:edit">
                    <button data-url="${ctx}/member_au"
                            data-id-name="branchId"
                            data-open-by="page"
                            class="jqOpenViewBtn btn btn-success btn-sm">
                        <i class="fa fa-user"></i> 添加党员
                    </button>
                </shiro:hasPermission>--%>

                <button data-url="${ctx}/org_admin"
                        data-id-name="branchId" class="jqOpenViewBtn btn btn-warning btn-sm">
                    <i class="fa fa-user"></i> 编辑管理员
                </button>
                </c:if>

                <shiro:hasPermission name="branch:transfer">
                <a href="javascript:;" class="jqOpenViewBatchBtn btn btn-danger btn-sm" data-url="${ctx}/branch_batchTransfer">
                    <i class="fa fa-random"></i> 支部转移</a>
                </shiro:hasPermission>

                <div class="btn-group">
                    <button data-toggle="dropdown"
                            data-rel="tooltip" data-placement="top" data-html="true"
                            title="<div style='width:180px'>导出选中记录或所有搜索结果</div>"
                            class="btn btn-success btn-sm dropdown-toggle tooltip-success">
                        <i class="fa fa-download"></i> 导出  <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu dropdown-success" role="menu" style="z-index: 1031">
                        <li>
                            <a href="javascript:;" class="jqExportBtn"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i> 导出</a>
                        </li>
                        <li role="separator" class="divider"></li>
                        <li>
                            <a class="jqExportBtn"
                               data-querystr="exportType=secretary"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i> 导出党支部书记</a>
                        </li>
                        <li role="separator" class="divider"></li>
                        <li>
                            <a class="jqExportBtn"
                               data-querystr="exportType=groupMember"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i> 导出党小组成员</a>
                        </li>
                    </ul>
                </div>

                <%--<a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i> 导出</a>

                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-querystr="exportType=secretary"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i> 导出党支部书记</a>

                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-querystr="exportType=groupMember"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i> 导出党小组成员</a>--%>
                <c:if test="${cls==1}">
                    <shiro:hasPermission name="branch:del">
                        <a class="jqBatchBtn btn btn-danger btn-sm"
                           data-url="${ctx}/branch_batchDel" data-title="撤销党支部"
                           data-msg="确定撤销这{0}个党支部吗？"><i class="fa fa-history"></i> 撤销</a>
                        【注：撤销操作将同时删除其下的支部委员会及相关管理员权限，请谨慎操作！】
                    </shiro:hasPermission>
                </c:if>
                <c:if test="${cls==2}">
                    <shiro:hasPermission name="branch:del">
                        <a class="jqBatchBtn btn btn-success btn-sm"
                           data-url="${ctx}/branch_batchDel"
                           data-querystr="isDeleted=0"
                           data-title="恢复已删除党支部"
                           data-msg="确定恢复这{0}个党支部吗？"><i class="fa fa-reply"></i> 恢复</a>
                        【注：恢复操作之后需要重新设置支部委员会及相关管理员权限！】
                    </shiro:hasPermission>
                </c:if>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>
                    <div class="widget-toolbar">
                        <a href="javascript:;" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm">
                            <input type="hidden" name="cls" value="${cls}">
                                    <div class="form-group">
                                        <label>编号</label>
                                            <input class="form-control search-query" name="code" type="text" value="${param.code}"            placeholder="请输入编号">
                                    </div>

                                    <div class="form-group">
                                        <label>名称</label>
                                            <input class="form-control search-query" name="name" type="text"
                                                   value="${param.name}"   placeholder="请输入名称">
                                    </div>

                                    <div class="form-group">
                                        <label>成立时间</label>
                                            <div class="input-group tooltip-success" data-rel="tooltip" title="成立时间范围">
                                                    <span class="input-group-addon">
                                                        <i class="fa fa-calendar bigger-110"></i>
                                                    </span>
                                                <input placeholder="请选择成立时间范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_foundTime" value="${param._foundTime}"/>
                                            </div>
                                    </div>
                                    <div class="form-group">
                                        <label>所属${_p_partyName}</label>
                                        <select data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects?auth=1&notDirect=1"
                                                name="partyId" data-placeholder="请选择">
                                            <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>支部类型</label>
                                         <select class="multiselect" multiple="" name="types"
                                                                data-placeholder="请选择">
                                            <c:import url="/metaTypes?__code=mc_branch_type"/>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>单位属性</label>
                                            <select name="unitTypeId" data-rel="select2" data-placeholder="请选择所在单位属性"> 
                                                <option></option>
                                                  <c:import url="/metaTypes?__code=mc_branch_unit_type"/>
                                            </select> 
                                            <script>         $("#searchForm select[name=unitTypeId]").val('${param.unitTypeId}');     </script>
                                    </div>
                                <div class="form-group">
                                    <label>是否是教工党支部</label>
                                    <select name="isStaff"
                                            data-rel="select2"
                                            data-width="80"
                                            data-placeholder="请选择"> 
                                        <option></option>
                                        <option value="1">是</option>
                                        <option value="0">否</option>
                                    </select> 
                                    <script>
                                        $("#searchForm select[name=isStaff]").val('${param.isStaff}');
                                    </script>
                                </div>

                                <div class="form-group">
                                    <label>是否一线教学科研党支部</label>
                                    <select name="isPrefessional" data-width="80" data-rel="select2" data-placeholder="请选择"> 
                                        <option></option>
                                        <option value="1">是</option>
                                        <option value="0">否</option>
                                    </select> 
                                    <script>
                                        $("#searchForm select[name=isPrefessional]").val('${param.isPrefessional}');
                                    </script>
                                </div>

                                <div class="form-group">
                                    <label>是否建立在团队</label>
                                    <select name="isBaseTeam" data-width="80" data-rel="select2" data-placeholder="请选择"> 
                                        <option></option>
                                        <option value="1">是</option>
                                        <option value="0">否</option>
                                    </select> 
                                    <script>
                                        $("#searchForm select[name=isBaseTeam]").val('${param.isBaseTeam}');
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
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"> </table>
            <div id="jqGridPager"> </div>
        </div>
        </div>
            </div></div></div>
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $.register.multiselect($('#searchForm select[name=types]'), ${cm:toJSONArray(selectTypes)});

    $("#jqGrid").jqGrid({
        url: '${ctx}/branch_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '编号', name: 'code',width:140, frozen:true },
            { label: '名称',  name: 'name',align:'left', width: 400,formatter:function(cellvalue, options, rowObject){

                return $.party(null, rowObject.id);
            }, frozen:true },
            <shiro:hasPermission name="branchGroup:*">
            {
                label: '党小组管理', name: 'bgCount', formatter: function (cellvalue, options, rowObject) {

                    return ('<button class="openView btn btn-warning btn-xs" ' +
                        'data-url="${ctx}/branchGroup?branchId={0}">'
                        + '<i class="fa fa-search"></i> 查看({1})</button>')
                        .format(rowObject.id, cellvalue==undefined?"0":cellvalue);
                },frozen:true},
            </shiro:hasPermission>
            <c:if test="${cls==1 && !_query}">
            { label:'排序', formatter: $.jgrid.formatter.sortOrder,frozen:true },
            </c:if>
            <c:if test="${_p_owCheckIntegrity}">
            {label: '信息完整度', name: 'integrity',frozen: true,width: 120,formatter: function (cellvalue, options, rowObject) {

                    if(Math.trimToZero(rowObject.integrity)==0)
                        return '--'
                    var progress = Math.formatFloat(Math.trimToZero(rowObject.integrity)*100, 1) + "%";
                    return ('<a href="javascript:;" class="jqEditBtn" data-url="${ctx}/branch_integrity_view" data-id-name="branchId">' +
                        '<div class="progress progress-striped pos-rel" data-percent="{0}">' +
                        '<div class="progress-bar progress-bar-{1}" style="width:{0}"></div></div></a>')
                        .format(progress,rowObject.integrity==1?"success":"danger")
                },sortable: true, align: 'left'},
            </c:if>
            { label: '所属${_p_partyName}', name: 'partyId',align:'left', width: 350 ,  formatter:function(cellvalue, options, rowObject){
                return $.party(rowObject.partyId);
            }},
            { label:'支部转移记录', name: 'transferCount', formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined||cellvalue==0) return '--';
                return '<a href="javascript:void(0)" class="openView" data-url="${ctx}/branchTransferLog?branchId={0}" class="openView">查看({1})</a>'.format(rowObject.id, cellvalue);
            }},
            { label:'党员总数', name: 'memberCount', width: 70, formatter:function(cellvalue, options, rowObject){
                return cellvalue==undefined?0:cellvalue;
            }},
            { label:'在职教职工', name: 'teacherMemberCount', width: 90, formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined|| cellvalue==0) return 0;
                return '<a href="#${ctx}/member?cls=2&partyId={0}&branchId={1}" target="_blank">{2}</a>'.format(rowObject.partyId, rowObject.id, cellvalue);
            }},
            { label:'离退休党员', name: 'retireMemberCount', width: 90, formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined|| cellvalue==0) return 0;
                return '<a href="#${ctx}/member?cls=3&partyId={0}&branchId={1}" target="_blank">{2}</a>'.format(rowObject.partyId, rowObject.id, cellvalue);
            }},
            { label:'学生', name: 'studentMemberCount', width: 50, formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined || cellvalue==0) return 0;
                return '<a href="#${ctx}/member?cls=1&partyId={0}&branchId={1}" target="_blank">{2}</a>'.format(rowObject.partyId, rowObject.id, cellvalue);
            }},
            { label:'委员会总数', name: 'groupCount', width: 90, formatter:function(cellvalue, options, rowObject){
                return cellvalue==undefined?0:cellvalue;
            }},
            { label:'是否已设立现任委员会', name: 'presentGroupId', width: 160, formatter:function(cellvalue, options, rowObject){
                return cellvalue>0?"是":"否";
            }},
            {label: '任命时间', name: 'appointTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '应换届时间', name: 'tranTime', width: 130,
                formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'},
                cellattr: function (rowId, val, rowObject, cm, rdata) {
                    if (rowObject.presentGroupId>0 &&
                        rowObject.tranTime <= $.date(new Date(), 'yyyy-MM-dd'))
                        return "class='danger'";
                }},
            {
                label: '实际换届时间',
                name: 'actualTranTime',
                width: 130,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {label: '支部类型', name: 'types', align:'left', width: 150, formatter: function (cellvalue, options, rowObject) {
                if($.trim(cellvalue)=='') return '--'
                return ($.map(cellvalue.split(","), function(label){
                    return $.jgrid.formatter.MetaType(label);
                })).join("，")
            }},
            { label: '是否是教工党支部', name: 'isStaff', width: 150, formatter:$.jgrid.formatter.TRUEFALSE},
            { label: '是否一线教学科研党支部', name: 'isPrefessional' , width: 170,  formatter:$.jgrid.formatter.TRUEFALSE},
            { label: '是否建立在团队', name: 'isBaseTeam' , width: 130, formatter:$.jgrid.formatter.TRUEFALSE},
            { label:'单位属性', name: 'unitTypeId', width: 150, formatter: $.jgrid.formatter.MetaType},
            { label: '联系电话', name: 'phone', width: 130 },
            { label: '传真', name: 'fax' },
            { label: '邮箱', name: 'email' },
            { label: '成立时间', name: 'foundTime',formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'} }
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.del_select($('#searchForm select[name=partyId]'));
</script>