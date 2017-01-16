<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="myTableDiv"
                 data-url-au="${ctx}/cadreLeader_au"
                 data-url-page="${ctx}/cadreLeader_page"
                 data-url-del="${ctx}/cadreLeader_del"
                 data-url-bd="${ctx}/cadreLeader_batchDel"
                 data-url-co="${ctx}/cadreLeader_changeOrder"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.cadreId ||not empty param.typeId
                ||not empty param.job || not empty param.code}"/>
                <div class="jqgrid-vertical-offset buttons">
                    <shiro:hasPermission name="cadreLeader:edit">
                        <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <a href="javascript:;" class="jqEditBtn btn btn-primary btn-sm">
                        <i class="fa fa-edit"></i> 修改信息</a>

                    <shiro:hasPermission name="cadreLeaderUnit:list">
                        <button  class="jqOpenViewBtn btn btn-sm btn-warning"
                                 data-url="${ctx}/cadreLeader_unit">
                            <i class="fa fa-sitemap"></i> 编辑联系单位
                        </button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="cadreLeader:del">
                        <a class="jqDelBtn btn btn-danger btn-sm"><i class="fa fa-trash"></i> 删除</a>
                    </shiro:hasPermission>
                </div>
                <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                    <div class="widget-header">
                        <h4 class="widget-title">搜索</h4>
                        <div class="widget-toolbar">
                            <a href="#" data-action="collapse">
                                <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                            </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main no-padding">
                            <form class="form-inline search-form" id="searchForm">
                                        <div class="form-group">
                                            <label>账号</label>
                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                                                        name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
                                                </select>
                                        </div>
                                        <div class="form-group">
                                            <label>类别</label>
                                                <select data-rel="select2" name="typeId" data-placeholder="请选择类别">
                                                    <option></option>
                                                    <c:forEach var="leaderType" items="${leaderTypeMap}">
                                                        <option value="${leaderType.value.id}">${leaderType.value.name}</option>
                                                    </c:forEach>
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
                                        <button type="button" class="resetBtn btn btn-warning btn-sm">
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
        <div id="item-content"></div>
    </div>
</div>
<script type="text/template" id="sort_tpl">
<a href="#" class="jqOrderBtn" data-id="{{=id}}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
<input type="text" value="1" class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
<a href="#" class="jqOrderBtn" data-id="{{=id}}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>
</script>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/cadreLeader_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '工作证号',  name: 'user.code', width: 100 ,frozen:true},
            { label: '姓名', name: 'user.realname', width: 75, formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?cadreId={0}">{1}</a>'
                        .format(rowObject.cadre.id, cellvalue);
            },frozen:true  },
            { label: '所在单位及职务',  name: 'cadre.title', width: 300,frozen:true  },
            <c:if test="${!_query}">
            { label:'排序',width: 100, index:'sort', formatter:function(cellvalue, options, rowObject){
                return _.template($("#sort_tpl").html().NoMultiSpace())({id:rowObject.id})
            },frozen:true },
            </c:if>
            { label:'行政级别',  name: 'cadre.adminLevelType.name', width: 280},
            { label: '类别',  name: 'leaderType.name', width: 100 },
            { label: '分管工作', align:'left', name: 'job', width: 750 }

        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $('[data-rel="tooltip"]').tooltip();
    });
    $(window).triggerHandler('resize.jqGrid');
    _initNavGrid("jqGrid", "jqGridPager");
    register_user_select($('[data-rel="select2-ajax"]'));
    $('[data-rel="select2"]').select2();
</script>