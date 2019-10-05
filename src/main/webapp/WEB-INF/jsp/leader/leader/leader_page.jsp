<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
            <div class="myTableDiv"
                 data-url-au="${ctx}/leader_au"
                 data-url-page="${ctx}/leader"
                 data-url-co="${ctx}/leader_changeOrder"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.cadreId ||not empty param.typeId
                ||not empty param.job || not empty param.code}"/>
                <jsp:include page="menu.jsp"/>
                <div class="space-4"></div>
                <div class="jqgrid-vertical-offset buttons">
                    <shiro:hasPermission name="leader:edit">

                        <button class="popupBtn btn btn-info btn-sm"
                                data-url="${ctx}/leader_fromLeader" data-width="750">
                            <i class="fa fa-search-plus"></i> 从校领导中提取
                        </button>
                        <button class="popupBtn btn btn-info btn-sm"
                                data-url="${ctx}/leader_fromCm" data-width="800">
                            <i class="fa fa-search-plus"></i> 从党委常委中提取
                        </button>

                    <a href="javascript:;" class="jqEditBtn btn btn-primary btn-sm">
                        <i class="fa fa-edit"></i> 编辑信息</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="leader:unit">
                        <button  class="jqOpenViewBtn btn btn-sm btn-warning"
                                 data-url="${ctx}/leader_unit">
                            <i class="fa fa-sitemap"></i> 编辑联系单位
                        </button>
                    </shiro:hasPermission>

                    <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                   data-url="${ctx}/leader_data?export=1"
                                   data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i
                                        class="fa fa-download"></i> 导出</a>

                    <shiro:hasPermission name="leader:del">
                        <a class="jqBatchBtn btn btn-danger btn-sm"
                           data-url="${ctx}/leader_batchDel" data-title="删除"
                           data-msg="确定删除这{0}位校领导吗？（删除后不可恢复，请谨慎操作）"><i class="fa fa-trash"></i> 删除</a>
                    </shiro:hasPermission>
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
                                            <label>账号</label>
                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                                                        name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                                                </select>
                                        </div>
                                        <div class="form-group">
                                            <label>类别</label>
                                                <select data-rel="select2" name="typeId" data-placeholder="请选择类别">
                                                    <option></option>
                                                    <c:import url="/metaTypes?__code=mc_leader_type"/>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#searchForm select[name=typeId]").val('${param.typeId}');
                                                </script>
                                        </div>
                                        <div class="form-group">
                                            <label>分管工作</label>
                                                <input class="form-control search-query"
                                                       style="width: 300px"
                                                       name="job" type="text" value="${param.job}"
                                                       placeholder="请输入分管工作">
                                        </div>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm" data-querystr="cls=${cls}">
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
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/leader_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '工作证号',  name: 'user.code', width: 120 ,frozen:true},
            { label: '姓名', name: 'user.realname', width: 75, formatter:function(cellvalue, options, rowObject){
                if(rowObject.cadre==undefined) return cellvalue
                return $.cadre(rowObject.cadre.id, cellvalue);
            },frozen:true  },
            { label: '所在单位及职务',  name: 'cadre.title', width: 300,frozen:true  },
            <shiro:hasPermission name="cadre:changeOrder">
            <c:if test="${!_query}">
            { label:'排序', formatter: $.jgrid.formatter.sortOrder,frozen:true },
            </c:if>
            </shiro:hasPermission>
            {label: '行政级别', name: 'cadre.adminLevel', formatter:$.jgrid.formatter.MetaType},
            { label: '类别',  name: 'typeId', formatter: $.jgrid.formatter.MetaType},
            {label: '是否校领导', name: 'cadreStatus', formatter:function(cellvalue, options, rowObject){
                return (rowObject.cadreStatus==${CADRE_STATUS_LEADER} ||
                    rowObject.cadreStatus==${CADRE_STATUS_LEADER_LEAVE})?"是":"否";
            }},
            {label: '是否常委', name: 'isCommitteeMember', width: 80, formatter: $.jgrid.formatter.TRUEFALSE},
            { label: '分管工作', align:'left', name: 'job', width: 750 }
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $('[data-rel="tooltip"]').tooltip();
    });
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('[data-rel="select2"]').select2();
</script>