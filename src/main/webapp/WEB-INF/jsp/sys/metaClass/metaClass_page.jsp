<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/metaClass_au"
             data-url-page="${ctx}/metaClass_page"
             data-url-export="${ctx}/metaClass_data"
             data-url-co="${ctx}/metaClass_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.name || not empty param.code
                || (not empty param.sort&&param.sort!='sort_order')}"/>
            <!-- PAGE CONTENT BEGINS -->
            <div class="col-sm-12">
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
                            <mytag:sort-form css="form-horizontal " id="searchForm">
                                <div class="row">

                                    <div class="col-xs-4">
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">名称</label>
                                            <div class="col-xs-6">
                                                <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                                       placeholder="请输入名称">
                                            </div>
                                        </div>
                                    </div>
                                    <shiro:hasRole name="admin">
                                    <div class="col-xs-4">
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">代码</label>
                                            <div class="col-xs-6">
                                                <input class="form-control search-query" name="code" type="text" value="${param.code}"
                                                       placeholder="请输入代码">
                                            </div>
                                        </div>
                                    </div>
                                    </shiro:hasRole>
                                </div>

                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="resetBtn btn btn-warning btn-sm">
                                            <i class="fa fa-reply"></i> 重置
                                        </button>
                                    </c:if>
                                </div>
                            </mytag:sort-form>
                        </div>
                    </div>
                </div>
                <div class="buttons">
                    <shiro:hasRole name="admin">
                        <shiro:hasPermission name="metaClass:edit">
                            <a class="editBtn btn btn-info btn-sm">
                                <i class="fa fa-plus"></i> 添加
                            </a>
                            <button class="jqEditBtn btn btn-primary btn-sm">
                                <i class="fa fa-edit"></i> 修改信息
                            </button>
                            <button class="jqOpenViewBtn btn btn-success btn-sm"
                                    data-url="${ctx}/metaClass_type"
                                    data-width="<shiro:hasRole name="admin">800</shiro:hasRole><shiro:lacksRole name="admin">400</shiro:lacksRole>">
                                <i class="fa fa-bars"></i> 编辑属性
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasRole name="admin">
                            <button class="jqOpenViewBtn btn btn-warning btn-sm"
                                    data-url="${ctx}/metaClassRole">
                                <i class="fa fa-pencil"></i> 修改角色
                            </button>
                            </shiro:hasRole>
                        <a class="exportBtn btn btn-success btn-sm tooltip-success"
                           data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                        <shiro:hasPermission name="metaClass:del">
                            <a class="jqBatchBtn btn btn-danger btn-sm"
                               data-url="${ctx}/metaClass_batchDel" data-title="删除"
                               data-msg="确定删除这{0}个元数据分类吗？"><i class="fa fa-trash"></i> 删除</a>
                        </shiro:hasPermission>
                    </shiro:hasRole>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid" class="jqGrid table-striped"> </table>
                <div id="jqGridPager"> </div>
            </div>
        </div><div id="item-content"></div>
    </div>
</div>

<script type="text/template" id="sort_tpl">
<a href="#" class="jqOrderBtn" data-id="{{=id}}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
<input type="text" value="1" class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
<a href="#" class="jqOrderBtn" data-id="{{=id}}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>
</script>
<script>
    $("#jqGrid").jqGrid({
        //forceFit:true,
        url: '${ctx}/metaClass_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '名称', align:'center', name: 'name', width: 250 ,frozen:true},
            { label: '所属一级目录', align:'center', name: 'firstLevel', width: 200 ,frozen:true},
            { label: '所属二级目录', align:'center', name: 'secondLevel', width: 200 ,frozen:true},
            <c:if test="${!_query}">
            { label:'排序',width: 100, index:'sort', formatter:function(cellvalue, options, rowObject){
                return _.template($("#sort_tpl").html().replace(/\n|\r|(\r\n)/g,''))({id:rowObject.id})
            }, frozen:true },
            </c:if>
            <shiro:hasRole name="admin">
            { label: '代码', align:'center', name: 'code', width: 200 },
            { label: '布尔属性名称', align:'center',  name: 'boolAttr', width: 150 },
            { label: '附加属性名称', align:'center',  name: 'extraAttr', width: 150 }
                </shiro:hasRole>
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    function openView(classId, pageNo){
        pageNo = pageNo||1;
        loadModal( "${ctx}/metaClass_type?id="+classId + "&pageNo="+pageNo,
                '<shiro:hasRole name="admin">800</shiro:hasRole><shiro:lacksRole name="admin">400</shiro:lacksRole>');
    }

    register_date($('.date-picker'));
</script>