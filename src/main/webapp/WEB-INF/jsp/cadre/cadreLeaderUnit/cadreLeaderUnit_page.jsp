<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
            <div class="myTableDiv" data-url-page="${ctx}/cadreLeaderUnit">
                <c:set var="_query" value="${not empty param.cadreId ||not empty param.typeId}"/>
                <jsp:include page="../cadreLeader/menu.jsp"/>
                <div class="space-4"></div>
                <div class="jqgrid-vertical-offset buttons">
<shiro:hasPermission name="cadreLeaderUnit:edit">
                    <a class="popupBtn btn btn-warning btn-sm"
                       data-url="${ctx}/cadreLeaderUnit_escape"><i class="fa fa-search"></i> 未分配校领导的单位（${fn:length(units)}）</a>
</shiro:hasPermission>
                    <shiro:hasPermission name="cadreLeaderUnit:del">
                        <a class="jqBatchBtn btn btn-danger btn-sm"
                           data-url="${ctx}/cadreLeaderUnit_batchDel" data-title="删除"
                           data-msg="确定删除这{0}条记录吗？"><i class="fa fa-trash"></i> 删除</a>
                    </shiro:hasPermission>
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
                                            <label>账号</label>
                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects?status=${CADRE_STATUS_LEADER}"
                                                        name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                                                </select>
                                        </div>
                                        <div class="form-group">
                                            <label>类别</label>
                                                <select data-rel="select2" name="typeId" data-placeholder="请选择类别">
                                                    <option></option>
                                                    <c:import url="/metaTypes?__code=mc_leader_unit"/>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#searchForm select[name=typeId]").val('${param.typeId}');
                                                </script>
                                        </div>

                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="resetBtn btn btn-warning btn-sm" data-querystr="cls=${cls}">
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
        url: '${ctx}/cadreLeaderUnit_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '工作证号',  name: 'cadre.code', width: 120 ,frozen:true},
            { label: '姓名', name: 'cadre.realname', width: 75, formatter:function(cellvalue, options, rowObject){
                if(rowObject.cadre==undefined) return ''
                return $.cadre(rowObject.cadre.id, cellvalue);
            },frozen:true  },
            { label: '所在单位及职务',  name: 'cadre.title', width: 300,frozen:true  },
            {label: '行政级别', name: 'cadre.typeId', formatter:$.jgrid.formatter.MetaType},
            { label: '类别',  name: 'typeId', width: 100, formatter: $.jgrid.formatter.MetaType},
            { label: '联系单位', name: 'unitId', width: 350, formatter: $.jgrid.formatter.unit}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $('[data-rel="tooltip"]').tooltip();
    });
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('[data-rel="select2"]').select2();
</script>