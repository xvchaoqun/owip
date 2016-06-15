<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
        <div class="myTableDiv"
             data-url-au="${ctx}/branch_au"
             data-url-page="${ctx}/branch_page"
             data-url-export="${ctx}/branch_data"
             data-url-del="${ctx}/branch_del"
             data-url-bd="${ctx}/branch_batchDel"
             data-url-co="${ctx}/branch_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param._foundTime || not empty param.code
                                ||not empty param.name ||not empty param.partyId
                                ||not empty param.isStaff||not empty param.isPrefessional||not empty param.isBaseTeam
                                ||not empty param.typeId ||not empty param.unitTypeId}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasAnyRoles name="admin,odAdmin,partyAdmin">
                <shiro:hasPermission name="branch:edit">
                    <a class="editBtn btn btn-info btn-sm" data-width="900"><i class="fa fa-plus"></i> 添加</a>
                </shiro:hasPermission>
                </shiro:hasAnyRoles>
                <a href="javascript:;" class="jqEditBtn btn btn-primary btn-sm"  data-width="900">
                    <i class="fa fa-edit"></i> 修改信息</a>
                <shiro:hasPermission name="member:edit">
                    <button data-url="${ctx}/member_au"
                            data-id-name="branchId"
                            data-open-by="page"
                            class="jqOpenViewBtn btn btn-success btn-sm">
                        <i class="fa fa-user"></i> 添加党员
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="branchMemberGroup:edit">
                    <button data-url="${ctx}/branchMemberGroup_au"
                            data-id-name="branchId" class="jqOpenViewBtn btn btn-primary btn-sm">
                        <i class="fa fa-users"></i> 添加支部委员会
                    </button>
                </shiro:hasPermission>
                <button data-url="${ctx}/org_admin"
                        data-id-name="branchId" class="jqOpenViewBtn btn btn-warning btn-sm">
                    <i class="fa fa-user"></i> 编辑管理员
                </button>
                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                <shiro:hasPermission name="branch:del">
                    <a class="jqDelBtn btn btn-danger btn-sm  tooltip-warning"
                       data-rel="tooltip" data-placement="top" title="如果党支部已经设置了管理员、支部委员会或添加了党员，则不可以删除"><i class="fa fa-trash"></i> 删除</a>
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
                                        <label>分党委</label>
                                        <select data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects?auth=1&notDirect=1"
                                                name="partyId" data-placeholder="请选择">
                                            <option value="${party.id}">${party.name}</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>类别</label>
                                            <select name="typeId" data-rel="select2" data-placeholder="请选择"> 
                                                <option></option>
                                                  <c:forEach items="${typeMap}" var="type"> 
                                                    <option value="${type.key}">${type.value.name}</option>
                                                      </c:forEach>  </select> 
                                            <script>         $("#searchForm select[name=typeId]").val('${param.typeId}');     </script>
                                             
                                    </div>
                                    <div class="form-group">
                                        <label>单位属性</label>
                                            <select name="unitTypeId" data-rel="select2" data-placeholder="请选择所在单位属性"> 
                                                <option></option>
                                                  <c:forEach items="${branchUnitTypeMap}" var="unitType"> 
                                                    <option value="${unitType.key}">${unitType.value.name}</option>
                                                      </c:forEach>  </select> 
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
                                    <label>是否是专业教师党支部</label>
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
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script type="text/template" id="sort_tpl">
<a href="#" class="jqOrderBtn" data-id="{{=id}}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
<input type="text" value="1" class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
<a href="#" class="jqOrderBtn" data-id="{{=id}}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>
</script>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/branch_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '编号',align:'center', name: 'code', frozen:true },
            { label: '名称',  name: 'name',align:'left', width: 250,formatter:function(cellvalue, options, rowObject){

                return '<a href="javascript:;" class="openView" data-url="${ctx}/branch_view?id={0}">{1}</a>'
                        .format(rowObject.id, cellvalue);
            }, frozen:true },
            { label: '所属分党委', name: 'party.name',align:'left', width: 400 , frozen:true},
           /* <c:if test="${!_query}">
            { label:'排序',width: 100, index:'sort', formatter:function(cellvalue, options, rowObject){
                return _.template($("#sort_tpl").html().replace(/\n|\r|(\r\n)/g,''))({id:rowObject.id})
            } },
            </c:if>*/
            { label:'类别', align:'center', name: 'branchType.name', width: 150},
            { label: '是否是教工党支部', name: 'isStaff', width: 150, formatter:function(cellvalue, options, rowObject){
                return cellvalue?"是":"否";
            }},
            { label: '是否是专业教师党支部', name: 'isPrefessional' , width: 170,  formatter:function(cellvalue, options, rowObject){
                return cellvalue?"是":"否";
            }},
            { label: '是否建立在团队', name: 'isBaseTeam' , width: 130, formatter:function(cellvalue, options, rowObject){
                return cellvalue?"是":"否";
            }},
            { label:'单位属性', align:'center', name: 'unitType.name', width: 150},
            { label: '联系电话', align:'center', name: 'phone', width: 130 },
            { label: '传真', align:'center', name: 'fax', width: 100 },
            { label: '邮箱', align:'center', name: 'email', width: 100 },
            { label: '成立时间', align:'center', name: 'foundTime', width: 100 }
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_party_select($('#searchForm select[name=partyId]'));
</script>