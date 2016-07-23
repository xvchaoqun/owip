<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div  class="myTableDiv"
                  data-url-au="${ctx}/cadre_au?status=${status}"
                  data-url-page="${ctx}/cadre_page"
                  data-url-del="${ctx}/cadre_del"
                  data-url-bd="${ctx}/cadre_batchDel"
                  data-url-co="${ctx}/cadre_changeOrder?status=${status}"
                  data-url-export="${ctx}/cadre_data"
                  data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.cadreId ||not empty param.typeId
            ||not empty param.postId ||not empty param.title || not empty param.code }"/>

        <div class="tabbable">
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <c:forEach var="cadreStatus" items="${CADRE_STATUS_MAP}">
                <li class="<c:if test="${status==cadreStatus.key}">active</c:if>">
                    <a href="?status=${cadreStatus.key}"><i class="fa fa-flag"></i> ${cadreStatus.value}</a>
                </li>
                </c:forEach>

            </ul>

            <div class="tab-content">
                <div id="home4" class="tab-pane in active">
                    <div class="jqgrid-vertical-offset buttons">
                        <c:if test="${status==CADRE_STATUS_LEAVE||status==CADRE_STATUS_LEADER_LEAVE}">
                        <button class="jqBatchBtn btn btn-warning btn-sm"
                                data-title="重新任用"
                                data-msg="确定任用这{0}个干部吗？"
                                data-url="${ctx}/cadre_assign">
                            <i class="fa fa-reply"></i> 重新任用
                        </button>
                        </c:if>
                        <shiro:hasPermission name="cadre:edit">
                            <a class="editBtn btn btn-info btn-sm btn-success"><i class="fa fa-plus"></i>
                                <c:if test="${status==CADRE_STATUS_TEMP}">提任干部</c:if>
                                <c:if test="${status==CADRE_STATUS_NOW}">添加现任干部</c:if>
                                <c:if test="${status==CADRE_STATUS_LEAVE}">添加离任处级干部</c:if>
                                <c:if test="${status==CADRE_STATUS_LEADER_LEAVE}">添加离任校领导干部</c:if>
                            </a>
                        </shiro:hasPermission>

                        <button class="jqEditBtn btn btn-primary btn-sm">
                            <i class="fa fa-edit"></i> 修改信息
                        </button>

                        <c:if test="${status==CADRE_STATUS_TEMP}">
                            <button onclick="_pass()" class="btn btn-success btn-sm">
                                <i class="fa fa-edit"></i> 通过常委会任命
                            </button>
                        </c:if>

                        <c:if test="${status==CADRE_STATUS_NOW}">
                            <button class="jqOpenViewBtn btn btn-success btn-sm"
                                    data-url="${ctx}/cadre_leave" data-querystr="&status=${CADRE_STATUS_LEAVE}">
                                <i class="fa fa-edit"></i> 离任
                            </button>
                        </c:if>
                        <c:if test="${status==CADRE_STATUS_NOW}">
                            <button class="jqOpenViewBtn btn btn-warning btn-sm"
                                    data-url="${ctx}/cadre_additional_post" data-rel="tooltip" data-placement="bottom"
                                    title="添加职务——仅用于因私出国（境）审批人身份设定">
                                <i class="fa fa-plus"></i> 兼审单位
                            </button>
                        </c:if>
                        <a class="popupBtn btn btn-primary btn-sm tooltip-success"
                           data-url="${ctx}/cadre_import?status=${status}"
                           data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i> 导入</a>
                        <a class="jqExportBtn btn btn-success btn-sm"
                           data-rel="tooltip" data-placement="bottom" title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i> 导出</a>
                        <shiro:hasPermission name="cadre:del">
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
                                                <label>姓名</label>
                                                    <div class="input-group">
                                                        <input type="hidden" name="status" value="${status}">
                                                        <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                                                                name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                            <option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
                                                        </select>
                                                    </div>
                                            </div>
                                            <div class="form-group">
                                                <label>行政级别</label>
                                                    <select data-rel="select2" name="typeId" data-placeholder="请选择行政级别">
                                                        <option></option>
                                                        <jsp:include page="/metaTypes?__code=mc_admin_level"/>
                                                    </select>
                                                    <script type="text/javascript">
                                                        $("#searchForm select[name=typeId]").val(${param.typeId});
                                                    </script>
                                            </div>
                                            <div class="form-group">
                                                <label>职务属性</label>
                                                    <select data-rel="select2" name="postId" data-placeholder="请选择职务属性">
                                                        <option></option>
                                                        <jsp:include page="/metaTypes?__code=mc_post"/>
                                                    </select>
                                                    <script type="text/javascript">
                                                        $("#searchForm select[name=postId]").val(${param.postId});
                                                    </script>
                                            </div>
                                            <div class="form-group">
                                                <label>单位及职务</label>
                                                    <input class="form-control search-query" name="title" type="text" value="${param.title}"
                                                           placeholder="请输入单位及职务">
                                            </div>
                                    <div class="clearfix form-actions center">
                                        <a class="jqSearchBtn btn btn-default btn-sm" ><i class="fa fa-search"></i> 查找</a>

                                        <c:if test="${_query || not empty param.sort}">&nbsp;
                                            <button type="button" class="resetBtn btn btn-warning btn-sm" data-querystr="status=${status}">
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

                </div></div></div>
                </div>
        </div>
        <div id="item-content">

        </div>
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
        url: '${ctx}/cadre_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '工作证号', name: 'user.code', width: 100,frozen:true },
            { label: '姓名', name: 'user.realname', width: 120, formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?id={0}">{1}</a>'
                        .format(rowObject.id, cellvalue);
            },frozen:true  },
            { label:'排序', width: 80, index:'sort', formatter:function(cellvalue, options, rowObject){
                return _.template($("#sort_tpl").html().NoMultiSpace())({id:rowObject.id})
            },frozen:true },
            { label: '行政级别', name: 'adminLevelType.name' },
            { label: '职务属性', name: 'postType.name', width: 150 },
            { label: '所在单位', name: 'unit.name', width: 200 },
            { label: '职务', name: 'post', width: 350 },
            { label: '所在单位及职务', name: 'title', width: 350 },
            { label: '兼审单位', name: 'additional', formatter:function(cellvalue, options, rowObject){
                var cadreAdditionalPosts = rowObject.cadreAdditionalPosts;
                if(cadreAdditionalPosts.length==0) return ''
                return '<button class="popupBtn btn btn-xs btn-warning"' +
                        'data-url="${ctx}/cadre_additional_post?id={0}"><i class="fa fa-search"></i> 查看</button>'
                        .format(rowObject.id);
            } },
            { label: '手机号', name: 'mobile' },
            { label: '办公电话', name: 'officePhone' },
            { label: '家庭电话', name: 'homePhone' },
            { label: '电子邮箱', name: 'email', width: 150 },
            { label: '备注', name: 'remark', width: 150 }
        ]}).jqGrid("setFrozenColumns").on("initGrid",function(){
        $('[data-rel="tooltip"]').tooltip();
    });
    $(window).triggerHandler('resize.jqGrid');
    _initNavGrid("jqGrid", "jqGridPager");
    function _pass(){

        var grid = $("#jqGrid");
        var id  = grid.getGridParam("selrow");
        if(!id){
            SysMsg.warning("请选择行", "提示");
            return ;
        }
        var data = grid.getRowData(id);

        bootbox.confirm("姓名：{0}，工号：{1}，确定通过常委会任命吗？".format(data['user.realname'], data['user.code']), function (result) {
            if (result) {
                $.post("${ctx}/cadre_pass", {id: id}, function (ret) {
                    if (ret.success) {
                        SysMsg.success('操作成功。', '成功',function(){
                            location.href='${ctx}/cadre?status=1'
                        });
                    }
                });
            }
        });
    }

    function openView(id){
        $("#body-content").hide();
        $("#item-content").load("${ctx}/cadre_view?id="+id).show();
    }
    function closeView(){
        $("#body-content").show();
        $("#item-content").hide();
    }

    /*$(".tabbable li a").click(function(){
        $this = $(this);
        $(".tabbable li").removeClass("active");
        $this.closest("li").addClass("active");
        $(".myTableDiv #searchForm input[name=status]").val($this.data("status"));
        $(".myTableDiv .searchBtn").click();
    });*/

    $('[data-rel="select2"]').select2();


        register_user_select($('#searchForm select[name=cadreId]'));
</script>