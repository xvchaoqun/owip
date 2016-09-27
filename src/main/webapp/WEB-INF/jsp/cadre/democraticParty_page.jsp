<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div  class="myTableDiv"
                  data-url-bd="${ctx}/democraticParty_batchDel"
                  data-url-export="${ctx}/democraticParty_data"
                  data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.cadreId ||not empty param.typeId
            ||not empty param.postId ||not empty param.title || not empty param.code }"/>
        <div class="tabbable">

            <div class="tab-content">
                <div id="home4" class="tab-pane in active rownumbers">
                    <div class="jqgrid-vertical-offset buttons">
                        <shiro:hasPermission name="democraticParty:edit">
                            <a class="popupBtn btn btn-info btn-sm btn-success"
                               data-url="${ctx}/democraticParty_au"><i class="fa fa-plus"></i> 添加民主党派干部</a>
                        </shiro:hasPermission>
                        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                data-url="${ctx}/democraticParty_au" data-id-name="cadreId">
                            <i class="fa fa-edit"></i> 修改信息
                        </button>
                        <shiro:hasPermission name="democraticParty:del">
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
<script>
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/democraticParty_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '工作证号', name: 'user.code', width: 100,frozen:true },
            { label: '姓名', name: 'user.realname', width: 120, formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?id={0}">{1}</a>'
                        .format(rowObject.id, cellvalue);
            },frozen:true  },
            { label: '部门属性', name: 'unit.unitType.name', width: 150},
            { label: '所在单位', name: 'unit.name', width: 200 },
            { label: '现任职务', name: 'post', width: 350 },
            { label: '所在单位及职务', name: 'title', width: 350 },
            { label: '行政级别', name: 'typeId',formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '';
                return _cMap.adminLevelMap[cellvalue].name;
            } },
            { label: '职务属性', name: 'postId', width: 150,formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '';
                return _cMap.postMap[cellvalue].name;
            }  },
            { label: '在任情况', name: 'status',formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '';
                if(cellvalue=='${CADRE_STATUS_NOW}') return '现任干部';
                if(cellvalue=='${CADRE_STATUS_TEMP}') return '考察对象';
                if(cellvalue=='${CADRE_STATUS_LEAVE}') return '离任中层干部';
                if(cellvalue=='${CADRE_STATUS_LEADER_LEAVE}') return '离任校领导'
            } },
            { label: '民主党派', name: 'dpTypeId', formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '';
                return _metaTypeMap[cellvalue]
            }},
            { label: '党派加入时间', name: 'dpAddTime',formatter:'date',formatoptions: {newformat:'Y-m-d'} , width: 120},
            { label: '担任党派职务', name: 'dpPost' , width: 250},
            { label: '备注', name: 'dpRemark', width: 350 }
        ]}).jqGrid("setFrozenColumns").on("initGrid",function(){
        $('[data-rel="tooltip"]').tooltip();
    });
    $(window).triggerHandler('resize.jqGrid');
    _initNavGrid("jqGrid", "jqGridPager");
    $('[data-rel="select2"]').select2();
    register_user_select($('#searchForm select[name=cadreId]'));
</script>