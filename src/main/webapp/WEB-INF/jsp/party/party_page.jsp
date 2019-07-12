<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
        <div class="myTableDiv"
             data-url-au="${ctx}/party_au"
             data-url-page="${ctx}/party"
             data-url-export="${ctx}/party_data"
             data-url-co="${ctx}/party_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param._foundTime || not empty param.code ||not empty param.name ||not empty param.unitId
            ||not empty param.classId ||not empty param.typeId ||not empty param.unitTypeId
            ||not empty param.isEnterpriseBig||not empty param.isEnterpriseNationalized||not empty param.isSeparate
            || not empty param.code}"/>

            <div class="tabbable">
                <jsp:include page="menu.jsp"/>

                <div class="tab-content">
                    <div class="tab-pane in active">

            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="party:add">
                    <a class="editBtn btn btn-info btn-sm" data-width="900"><i class="fa fa-plus"></i> 添加</a>
                </shiro:hasPermission>
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

                <button data-url="${ctx}/org_admin"
                        data-id-name="partyId" class="jqOpenViewBtn btn btn-warning btn-sm">
                    <i class="fa fa-user"></i> 编辑管理员
                </button>
                <shiro:hasPermission name="party:add">
                    <button class="popupBtn btn btn-info btn-sm tooltip-info"
                            data-url="${ctx}/party_import"
                            data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                        批量导入
                    </button>
                </shiro:hasPermission>

                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i> 导出</a>
                <c:if test="${cls==1}">
                    <shiro:hasPermission name="party:del">
                        <a class="jqBatchBtn btn btn-danger btn-sm"
                           data-url="${ctx}/party_batchDel" data-title="撤销${_p_partyName}"
                           data-msg="确定撤销这{0}个${_p_partyName}吗？"><i class="fa fa-history"></i> 撤销</a>
                        【注：撤销操作将删除其下所有的党支部及班子和相关管理员权限，请谨慎操作！】
                    </shiro:hasPermission>
                </c:if>
                <c:if test="${cls==2}">
                    <shiro:hasPermission name="party:del">
                        <a class="jqBatchBtn btn btn-success btn-sm"
                           data-url="${ctx}/party_batchDel"
                           data-querystr="isDeleted=0"
                           data-title="恢复已撤销${_p_partyName}"
                           data-msg="确定恢复这{0}个${_p_partyName}吗？"><i class="fa fa-reply"></i> 恢复</a>
                        【注：恢复操作之后需要重新设置党支部及相关管理员权限！】
                    </shiro:hasPermission>
                </c:if>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>
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
                                         <input class="form-control search-query" name="code" type="text" value="${param.code}"   placeholder="请输入编号">
                                    </div>

                                    <div class="form-group">
                                        <label>名称</label>
                                            <input class="form-control search-query" name="name" type="text" value="${param.name}"            placeholder="请输入名称">
                                    </div>
                                    <div class="form-group">
                                        <label>所属单位</label>
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
            { label: '编号',name: 'code', width: 75 ,frozen:true},
            { label: '名称',  name: 'name', align:'left', width: 400,formatter:function(cellvalue, options, rowObject){
                return $.party(rowObject.id);
            },frozen:true },
            <shiro:hasPermission name="party:changeOrder">
            <c:if test="${cls==1 && !_query}">
            { label:'排序', formatter: $.jgrid.formatter.sortOrder,frozen:true },
            </c:if>
            </shiro:hasPermission>
            { label:'支部数量', name: 'branchCount', width: 70, formatter:function(cellvalue, options, rowObject){
                return cellvalue==undefined?0:cellvalue;
            }},
            { label:'党员总数', name: 'memberCount', width: 80, formatter:function(cellvalue, options, rowObject){
                return cellvalue==undefined?0:cellvalue;
            }},
            { label:'正式党员总数', name: 'positiveCount', width: 120, formatter:function(cellvalue, options, rowObject){
                return cellvalue==undefined?0:cellvalue;
            }},
            { label:'在职教职工', name: 'teacherMemberCount', width: 90, formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined|| cellvalue==0) return 0;
                <shiro:hasPermission name="member:list">
                    return '<a href="#${ctx}/member?cls=2&partyId={0}" target="_blank">{1}</a>'.format(rowObject.id, cellvalue);
                </shiro:hasPermission>
                <shiro:lacksPermission name="member:list">
                    return cellvalue;
                </shiro:lacksPermission>
            }},
            { label:'离退休党员', name: 'retireMemberCount', width: 90, formatter:function(cellvalue, options, rowObject){
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
            { label:'委员会总数', name: 'groupCount', width: 90, formatter:function(cellvalue, options, rowObject){
                return cellvalue==undefined?0:cellvalue;
            }},
            { label:'是否已设立现任委员会', name: 'presentGroupCount', width: 160, formatter:function(cellvalue, options, rowObject){
                return cellvalue>=1?"是":"否";
            }},
            { label:'简称', name: 'shortName', align:'left', width: 180},
            { label:'所属单位', name: 'unitId', width: 180, formatter: $.jgrid.formatter.unit},
            { label: '${_p_partyName}类别', name: 'classId', formatter: $.jgrid.formatter.MetaType},
            { label: '组织类别', name: 'typeId', width: 180, formatter: $.jgrid.formatter.MetaType},
            { label: '所在单位属性', name: 'unitTypeId', width: 110 , formatter: $.jgrid.formatter.MetaType},
            { label: '是否大中型', name: 'isEnterpriseBig', formatter:$.jgrid.formatter.TRUEFALSE},
            { label: '是否国有独资', name: 'isEnterpriseNationalized', width: 110, formatter:$.jgrid.formatter.TRUEFALSE},
            { label: '是否独立法人', name: 'isSeparate', width: 110, formatter:$.jgrid.formatter.TRUEFALSE},
            { label: '联系电话', name: 'phone' },
            { label: '传真', name: 'fax' },
            { label: '邮箱', name: 'email' },
            { label: '信箱', name: 'mailbox' },
            { label: '成立时间', name: 'foundTime',formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}}
        ]
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>