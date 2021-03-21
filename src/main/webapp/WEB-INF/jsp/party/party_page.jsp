<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/party/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
        <div class="myTableDiv"
             data-url-au="${ctx}/party_au?type=${type}"
             data-url-page="${ctx}/party?type=${type}"
             data-url-export="${ctx}/party_data"
             data-url-co="${ctx}/party_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param._foundTime || not empty param.code ||not empty param.name ||not empty param.unitId
            ||not empty param.classId ||not empty param.typeId ||not empty param.unitTypeId
            ||not empty param.isEnterpriseBig||not empty param.isEnterpriseNationalized||not empty param.isSeparate||not empty param.isPycj||not empty param.isBg
            || not empty param.code ||not empty param._integrity}"/>

            <div class="tabbable">
                <jsp:include page="menu.jsp"/>

                <div class="tab-content">
                    <div class="tab-pane in active multi-row-head-table">

            <div class="jqgrid-vertical-offset buttons">

                <c:if test="${cls==1}">
                <shiro:hasPermission name="party:add">
                    <a class="editBtn btn btn-info btn-sm" data-width="900"><i class="fa fa-plus"></i> 添加</a>
                </shiro:hasPermission>
                </c:if>
                <shiro:hasPermission name="party:edit">
                <a href="javascript:;" class="jqEditBtn btn btn-primary btn-sm"  data-width="900">
                    <i class="fa fa-edit"></i> 修改信息</a>

                <%--<shiro:hasPermission name="member:edit">
                    <button data-url="${ctx}/member_au"
                            data-id-name="partyId"
                            data-open-by="page"
                            class="jqOpenViewBtn btn btn-success btn-sm">
                        <i class="fa fa-user"></i> 添加党员
                    </button>
                </shiro:hasPermission>--%>
                    </shiro:hasPermission>

                <c:if test="${cls==1}">
                    <shiro:hasPermission name="party:edit">
                    <button data-url="${ctx}/org_admin?isPartyAdmin=1"
                            data-id-name="partyId" class="jqOpenViewBtn btn btn-warning btn-sm">
                        <i class="fa fa-user"></i> 编辑管理员
                    </button>
                    </shiro:hasPermission>
                    <shiro:hasRole name="${ROLE_SUPER}">
                        <button class="popupBtn btn btn-info btn-sm tooltip-info"
                                data-url="${ctx}org/orgAdmin_import?type=${OW_ORG_ADMIN_PARTY}"
                                data-rel="tooltip" data-placement="top" title="批量导入管理员"><i class="fa fa-upload"></i>
                            导入管理员
                        </button>
                    </shiro:hasRole>
                    <shiro:hasPermission name="party:add">
                        <button class="popupBtn btn btn-info btn-sm tooltip-info"
                                data-url="${ctx}/party_import"
                                data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                            批量导入
                        </button>
                    </shiro:hasPermission>

                    <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i> 导出</a>

                    <shiro:hasPermission name="party:del">
                        <a class="jqBatchBtn btn btn-danger btn-sm"
                           data-callback="updateCache"
                           data-url="${ctx}/party_batchDel" data-title="撤销${_p_partyName}"
                           data-msg="确定撤销这{0}个${_p_partyName}吗？"><i class="fa fa-history"></i> 撤销</a>
                        【注：撤销操作将删除其下所有的党支部及班子和相关管理员权限，请谨慎操作！】
                    </shiro:hasPermission>
                </c:if>
                <c:if test="${type==1}">
                    <shiro:hasPermission name="party:del">
                        <a class="jqBatchBtn btn btn-danger btn-sm"
                           data-callback="updateCache"
                           data-url="${ctx}/pgb_batchDel" data-title="删除"
                           data-msg="确定撤销这{0}个${_p_partyName}吗？"><i class="fa fa-trash"></i> 删除</a>
                    </shiro:hasPermission>
                </c:if>
                <c:if test="${cls==2}">
                    <shiro:hasPermission name="party:del">
                        <a class="jqBatchBtn btn btn-success btn-sm"
                           data-callback="updateCache"
                           data-url="${ctx}/party_batchDel"
                           data-querystr="isDeleted=0"
                           data-title="恢复已撤销${_p_partyName}"
                           data-msg="确定恢复这{0}个${_p_partyName}吗？"><i class="fa fa-reply"></i> 恢复</a>
                        【注：恢复操作之后需要重新设置党支部及相关管理员权限！】
                    </shiro:hasPermission>
                </c:if>
                <div class="pull-right hidden-sm hidden-xs">
                    <select id="sortBy" data-placeholder="请选择排序方式" data-width="250">
                        <option></option>
                        <option value="foundTime_asc">按成立时间排序(升序)</option>
                        <option value="foundTime_desc">按成立时间排序(降序)</option>
                        <option value="appointTime_asc">按任命时间排序(升序)</option>
                        <option value="appointTime_desc">按任命时间排序(降序)</option>
                        <option value="tranTime_asc">按应换届时间排序(升序)</option>
                        <option value="tranTime_desc">按应换届时间排序(降序)</option>
                    </select>
                    <script>
                        $("#sortBy").val('${param.sortBy}');
                        $("#searchForm input[name=sortBy]").val('${param.sortBy}');
                        $("#sortBy").select2({
                            theme: "default"
                        }).change(function () {
                            $("#searchForm input[name=sortBy]").val($(this).val());
                            $("#searchForm .jqSearchBtn").click();
                            if($(this).val()==''){
                                throw new Error();
                            }
                        })
                    </script>
                </div>
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
                            <input type="hidden" name="sortBy">
                            <input type="hidden" name="cls" value="${cls}">
                                    <div class="form-group">
                                        <label>编号</label>
                                         <input class="form-control search-query" name="code" type="text" value="${param.code}"   placeholder="请输入编号">
                                    </div>

                                    <div class="form-group">
                                        <label>名称</label>
                                            <input class="form-control search-query" name="name" type="text" value="${param.name}"            placeholder="请输入名称">
                                    </div>
                                    <div class="form-group">
                                        <label>关联单位</label>
                                            <select name="unitId" data-rel="select2" data-placeholder="请选择"> 
                                                <option></option>
                                                  <c:forEach items="${unitMap}" var="unit"> 
                                                    <option value="${unit.key}">${unit.value.name}</option>
                                                      </c:forEach>  </select> 
                                            <script>         $("#searchForm select[name=unitId]").val('${param.unitId}');     </script>
                                    </div>
                                    <div class="form-group">
                                        <label>${_p_partyName}类别</label>
                                            <select name="classId" data-rel="select2" data-placeholder="请选择"> 
                                                <option></option>
                                                  <c:import url="/metaTypes?__code=mc_party_class"/>
                                            </select> 
                                            <script>         $("#searchForm select[name=classId]").val('${param.classId}');     </script>
                                             
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
                                        <label>组织类型</label>
                                        <select data-rel="select2" name="typeId" data-placeholder="请选择组织类型">
                                            <option></option>
                                            <c:import url="/metaTypes?__code=mc_party_type"/>
                                        </select>
                                        <script>
                                            $("#searchForm select[name=typeId]").val('${param.typeId}');
                                        </script>
                                    </div>
                                    <div class="form-group">
                                        <label>单位属性</label>
                                            <select name="unitTypeId" data-width="120" data-rel="select2" data-placeholder="请选择"> 
                                                <option></option>
                                                  <c:import url="/metaTypes?__code=mc_party_unit_type"/>
                                            </select> 
                                            <script>         $("#searchForm select[name=unitTypeId]").val('${param.unitTypeId}');     </script>
                                    </div>
                                <div class="form-group">
                                    <label>是否大中型</label>
                                    <select name="isEnterpriseBig"
                                            data-rel="select2"
                                            data-width="80"
                                            data-placeholder="请选择"> 
                                        <option></option>
                                        <option value="1">是</option>
                                        <option value="0">否</option>
                                    </select> 
                                    <script>
                                        $("#searchForm select[name=isEnterpriseBig]").val('${param.isEnterpriseBig}');
                                    </script>
                                </div>

                                <div class="form-group">
                                    <label>是否国有独资</label>
                                    <select name="isEnterpriseNationalized" data-width="80" data-rel="select2" data-placeholder="请选择"> 
                                        <option></option>
                                        <option value="1">是</option>
                                        <option value="0">否</option>
                                    </select> 
                                    <script>
                                        $("#searchForm select[name=isEnterpriseNationalized]").val('${param.isEnterpriseNationalized}');
                                    </script>
                                </div>

                                <div class="form-group">
                                    <label>是否独立法人</label>
                                    <select name="isSeparate" data-width="80" data-rel="select2" data-placeholder="请选择"> 
                                        <option></option>
                                        <option value="1">是</option>
                                        <option value="0">否</option>
                                    </select> 
                                    <script>
                                        $("#searchForm select[name=isSeparate]").val('${param.isSeparate}');
                                    </script>
                                </div>
                                <div class="form-group">
                                    <label>是否培育创建单位</label>
                                    <select name="isPycj" data-width="80" data-rel="select2" data-placeholder="请选择"> 
                                        <option></option>
                                        <option value="1">是</option>
                                        <option value="0">否</option>
                                    </select> 
                                    <script>
                                        $("#searchForm select[name=isPycj]").val('${param.isPycj}');
                                    </script>
                                </div>
                                <div class="form-group">
                                    <label>是否标杆院系</label>
                                    <select name="isBg" data-width="80" data-rel="select2" data-placeholder="请选择"> 
                                        <option></option>
                                        <option value="1">是</option>
                                        <option value="0">否</option>
                                    </select> 
                                    <script>
                                        $("#searchForm select[name=isBg]").val('${param.isBg}');
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
    $("#jqGrid").jqGrid({
        url: '${ctx}/party_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '编号',name: 'code',frozen:true,width:120},
            { label: '名称',  name: 'name', align:'left', width: 400,formatter:function(cellvalue, options, rowObject){
                return $.party(rowObject.id);
            },frozen:true },
            <shiro:hasPermission name="party:changeOrder">
            <c:if test="${cls==1 && !_query && empty param.sortBy}">
            { label:'排序', formatter: $.jgrid.formatter.sortOrder,formatoptions: {url: "${ctx}/party_changeOrder?type=${type}"},frozen:true },
            </c:if>
            </shiro:hasPermission>
            <c:if test="${type==1}">
                { label: '上级党组织', name: 'fid',align:'left', width: 350 ,  formatter:function(cellvalue, options, rowObject){
                        return $.party(rowObject.fid);
                    }},
            </c:if>
            <c:if test="${_p_owCheckIntegrity}">
            {label: '信息完整度', name: 'integrity',frozen: true,width: 120,formatter: function (cellvalue, options, rowObject) {

                    var progress = Math.formatFloat(Math.trimToZero(rowObject.integrity)*100, 1) + "%";
                    return ('<a href="javascript:;" class="jqEditBtn" data-url="${ctx}/party_integrity" data-id-name="partyId">' +
                        '<div class="progress progress-striped pos-rel" data-percent="{0}">' +
                        '<div class="progress-bar progress-bar-{1}" style="width:{0}"></div></div></a>')
                        .format(progress,rowObject.integrity==1?"success":"danger")
                },sortable: true, align: 'left'},
            </c:if>
            <c:if test="${type==0}">
                { label:'支部<br/>数量', name: 'branchCount', width: 50, formatter:function(cellvalue, options, rowObject){
                    if(rowObject.classId=='${cm:getMetaTypeByCode("mt_direct_branch").id}')
                        return 1;
                    if(cellvalue==undefined|| cellvalue==0) return 0;
                     <shiro:hasPermission name="branch:list">
                    return '<a href="#${ctx}/branch?partyId={0}" target="_blank">{1}</a>'.format(rowObject.id, cellvalue);
                        </shiro:hasPermission>
                        <shiro:lacksPermission name="branch:list">
                        return cellvalue;
                    </shiro:lacksPermission>
                }},
                { label:'党员<br/>总数', name: 'memberCount', width: 50, formatter:function(cellvalue, options, rowObject){
                    if(cellvalue==undefined|| cellvalue==0) return 0;
                     <shiro:hasPermission name="member:list">
                    return '<a href="#${ctx}/member?cls=10&partyId={0}" target="_blank">{1}</a>'.format(rowObject.id, cellvalue);
                        </shiro:hasPermission>
                        <shiro:lacksPermission name="member:list">
                        return cellvalue;
                    </shiro:lacksPermission>
                }},
                { label:'正式党员<br/>总数', name: 'positiveCount', width: 70, formatter:function(cellvalue, options, rowObject){
                    if(cellvalue==undefined|| cellvalue==0) return 0;
                     <shiro:hasPermission name="member:list">
                    return '<a href="#${ctx}/member?cls=10&partyId={0}&politicalStatus=${MEMBER_POLITICAL_STATUS_POSITIVE}" target="_blank">{1}</a>'.format(rowObject.id, cellvalue);
                        </shiro:hasPermission>
                        <shiro:lacksPermission name="member:list">
                        return cellvalue;
                    </shiro:lacksPermission>
                }},
                { label:'在职<br/>教职工', name: 'teacherMemberCount', width: 60, formatter:function(cellvalue, options, rowObject){
                    if(cellvalue==undefined|| cellvalue==0) return 0;
                    <shiro:hasPermission name="member:list">
                        return '<a href="#${ctx}/member?cls=2&partyId={0}" target="_blank">{1}</a>'.format(rowObject.id, cellvalue);
                    </shiro:hasPermission>
                    <shiro:lacksPermission name="member:list">
                        return cellvalue;
                    </shiro:lacksPermission>
                }},
                { label:'离退休<br/>党员', name: 'retireMemberCount', width: 60, formatter:function(cellvalue, options, rowObject){
                    if(cellvalue==undefined|| cellvalue==0) return 0;
                    <shiro:hasPermission name="member:list">
                        return '<a href="#${ctx}/member?cls=3&partyId={0}" target="_blank">{1}</a>'.format(rowObject.id, cellvalue);
                    </shiro:hasPermission>
                    <shiro:lacksPermission name="member:list">
                        return cellvalue;
                    </shiro:lacksPermission>
                }},
                { label:'学生', name: 'studentMemberCount', width: 50, formatter:function(cellvalue, options, rowObject){
                    if(cellvalue==undefined|| cellvalue==0) return 0;
                    <shiro:hasPermission name="member:list">
                        return '<a href="#${ctx}/member?cls=1&partyId={0}" target="_blank">{1}</a>'.format(rowObject.id, cellvalue);
                    </shiro:hasPermission>
                    <shiro:lacksPermission name="member:list">
                        return cellvalue;
                    </shiro:lacksPermission>
                }},
            </c:if>
            <shiro:hasPermission name="partyMemberGroup:list">
            /*{ label:'委员会<br/>总数', name: 'groupCount', width: 60, formatter:function(cellvalue, options, rowObject){
                return cellvalue==undefined?0:cellvalue;
            }},*/
            { label:'是否已设立<br/>现任领导班子', name: 'presentGroupId', width: 110, formatter:function(cellvalue, options, rowObject){
                return cellvalue>0?"是":"否";
            },cellattr: function (rowId, val, rowObject, cm, rdata) {
                    if (rowObject.presentGroupId==undefined)
                        return "class='danger'";
                }},
            { label: '成立时间', name: 'foundTime',formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '任命时间', name: 'appointTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '应换届<br/>时间', name: 'tranTime',
                formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'},
                cellattr: function (rowId, val, rowObject, cm, rdata) {
                    if (rowObject.presentGroupId > 0){
                        if($.yearOffNow(rowObject.tranTime) > 0) {
                            return "class='dark-danger'"; // 超过1年，深红
                        }else if($.dayOffNow(rowObject.tranTime) > 0){
                            return "class='danger'";
                        }
                    }
                }},
            </shiro:hasPermission>
            /*{
                label: '实际换届<br/>时间',
                name: 'actualTranTime',
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },*/
            { label:'简称', name: 'shortName', align:'left', width: 180},
            { label: '党组织类别', name: 'classId', width: 100, align:'left', formatter:function(cellvalue, options, rowObject){
                    var _classId = '${cm:getMetaTypeByCode("mt_direct_branch").id}';
                    var str = $.jgrid.formatter.MetaType(cellvalue);
                    //console.log($.jgrid.formatter.MetaType(rowObject.branchType))
                    if (_classId==cellvalue){
                        str += '&nbsp;<span class="prompt" style="cursor: default!important;" data-title="直属党支部类型" data-width="252"' +
                            'data-prompt="<ul>' +
                            '<li>' + $.jgrid.formatter.MetaType(rowObject.branchType) + '</li>' +
                            '</ul>"><i class="fa fa-info-circle"></i></span>' +
                            '</label>';
                    }
                    return str;
                }},
            { label: '组织类别', name: 'typeId', width: 180, formatter: $.jgrid.formatter.MetaType},
            { label:'关联单位', name: 'unitId', width: 180, align:'left', formatter: $.jgrid.formatter.unit},
            { label: '关联单位<br/>属性', name: 'unitTypeId' , formatter: $.jgrid.formatter.MetaType},
            { label: '是否<br/>大中型', name: 'isEnterpriseBig', width: 60, formatter:$.jgrid.formatter.TRUEFALSE},
            { label: '是否<br/>国有独资', name: 'isEnterpriseNationalized', width: 70, formatter:$.jgrid.formatter.TRUEFALSE},
            { label: '是否<br/>独立法人', name: 'isSeparate', width: 70, formatter:$.jgrid.formatter.TRUEFALSE},
            { label: '是否培育<br/>创建单位', name: 'isPycj', width: 70, formatter:$.jgrid.formatter.TRUEFALSE},
            {label: '评选培育创建<br/>单位时间', name: 'pycjDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            { label: '是否<br/>标杆院系', name: 'isBg', width: 70, formatter:$.jgrid.formatter.TRUEFALSE},
            {label: '评选标杆<br/>院系时间', name: 'bgDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            { label: '联系电话', name: 'phone' },
            { label: '传真', name: 'fax' },
            { label: '邮箱', name: 'email' },
            { label: '信箱', name: 'mailbox' }
        ]
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    function updateCache(){
        $.reloadMetaData(function(){
            $("#jqGrid").trigger("reloadGrid");
        });
    }
</script>