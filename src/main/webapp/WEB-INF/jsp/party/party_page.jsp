<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
        <div class="myTableDiv"
             data-url-au="${ctx}/party_au"
             data-url-page="${ctx}/party_page"
             data-url-export="${ctx}/party_data"
             data-url-del="${ctx}/party_del"
             data-url-bd="${ctx}/party_batchDel"
             data-url-co="${ctx}/party_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.code ||not empty param.name ||not empty param.unitId
            ||not empty param.classId ||not empty param.typeId ||not empty param.unitTypeId
            || not empty param.code}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="party:edit">
                    <a class="editBtn btn btn-info btn-sm" data-width="900"><i class="fa fa-plus"></i> 添加</a>
                </shiro:hasPermission>
                <a href="javascript:;" class="jqEditBtn btn btn-primary btn-sm"  data-width="900">
                    <i class="fa fa-edit"></i> 修改信息</a>

                <shiro:hasPermission name="member:edit">
                    <button data-url="${ctx}/member_au"
                            data-id-name="partyId"
                            data-open-by="page"
                            class="jqOpenViewBtn btn btn-success btn-sm">
                        <i class="fa fa-user"></i> 添加党员
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="partyMemberGroup:edit">
                    <button data-url="${ctx}/partyMemberGroup_au"
                            data-id-name="partyId" class="jqOpenViewBtn btn btn-primary btn-sm">
                        <i class="fa fa-users"></i> 添加分党委班子
                    </button>
                </shiro:hasPermission>
                <button data-url="${ctx}/org_admin"
                        data-id-name="partyId" class="jqOpenViewBtn btn btn-warning btn-sm">
                    <i class="fa fa-user"></i> 编辑管理员
                </button>
                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                <shiro:hasPermission name="party:del">
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
                        <mytag:sort-form css="form-horizontal " id="searchForm">
                            <div class="row">
                                <div class="col-xs-4">
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">编号</label>
                                        <div class="col-xs-6">
                                            <input class="form-control search-query" name="code" type="text" value="${param.code}"   placeholder="请输入编号">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">名称</label>
                                        <div class="col-xs-6">
                                            <input class="form-control search-query" name="name" type="text" value="${param.name}"            placeholder="请输入名称">
                                        </div>
                                    </div>

                                </div>
                                <div class="col-xs-4">
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">所属单位</label>
                                        <div class="col-xs-6">
                                            <select name="unitId" data-rel="select2" data-placeholder="请选择"> 
                                                <option></option>
                                                  <c:forEach items="${unitMap}" var="unit"> 
                                                    <option value="${unit.key}">${unit.value.name}</option>
                                                      </c:forEach>  </select> 
                                            <script>         $("#searchForm select[name=unitId]").val('${param.unitId}');     </script>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">组织类别</label>
                                        <div class="col-xs-6">
                                            <select name="classId" data-rel="select2" data-placeholder="请选择"> 
                                                <option></option>
                                                  <c:forEach items="${partyClassMap}" var="cls"> 
                                                    <option value="${cls.key}">${cls.value.name}</option>
                                                      </c:forEach>  </select> 
                                            <script>         $("#searchForm select[name=classId]").val('${param.classId}');     </script>
                                             
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-4">

                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">组织类型</label>
                                        <div class="col-xs-6">
                                            <select name="typeId" data-rel="select2" data-placeholder="请选择"> 
                                                <option></option>
                                                  <c:forEach items="${partyTypeMap}" var="type"> 
                                                    <option value="${type.key}">${type.value.name}</option>
                                                      </c:forEach>  </select> 
                                            <script>         $("#searchForm select[name=typeId]").val('${param.typeId}');     </script>
                                             
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">单位属性</label>
                                        <div class="col-xs-6">
                                            <select name="unitTypeId" data-rel="select2" data-placeholder="请选择"> 
                                                <option></option>
                                                  <c:forEach items="${partyUnitTypeMap}" var="unitType"> 
                                                    <option value="${unitType.key}">${unitType.value.name}</option>
                                                      </c:forEach>  </select> 
                                            <script>         $("#searchForm select[name=unitTypeId]").val('${param.unitTypeId}');     </script>
                                        </div>
                                    </div>

                                </div>
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
        url: '${ctx}/party_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '编号',align:'center', name: 'code',resizable:false, width: 75, frozen:true },
            { label: '名称',  name: 'name', width: 400,formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/party_view?id={0}">{1}</a>'
                        .format(rowObject.id, cellvalue);
            } ,frozen:true},
            <c:if test="${!_query}">
            { label:'排序',width: 100, index:'sort', formatter:function(cellvalue, options, rowObject){
                return _.template($("#sort_tpl").html().replace(/\n|\r|(\r\n)/g,''))({id:rowObject.id})
            }, frozen:true },
            </c:if>
            { label:'所属单位', align:'center', name: 'unit.name', width: 280},
            { label: '党总支类别', align:'center', name: 'partyClass.name', width: 130 },
            { label: '联系电话', align:'center', name: 'phone', width: 100 }
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid');
    })


    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>