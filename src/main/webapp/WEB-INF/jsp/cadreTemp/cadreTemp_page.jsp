<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div  class="myTableDiv"
                  data-url-page="${ctx}/cadreTemp_page"
                  data-url-bd="${ctx}/cadreTemp_batchDel"
                  data-url-co="${ctx}/cadreTemp_changeOrder"
                  data-url-export="${ctx}/cadreTemp_data"
                  data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId ||not empty param.typeId
            ||not empty param.postId ||not empty param.title || not empty param.code }"/>

        <div class="tabbable">
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <c:forEach var="_status" items="${CADRE_TEMP_STATUS_MAP}">
                    <li class="<c:if test="${status==_status.key}">active</c:if>">
                        <a href="?status=${_status.key}"><i class="fa fa-flag"></i> ${_status.value}</a>
                    </li>
                </c:forEach>
            </ul>

            <div class="tab-content">
                <div id="home4" class="tab-pane in active rownumbers">
                    <div class="jqgrid-vertical-offset buttons">
                        <c:if test="${status==CADRE_TEMP_STATUS_NORMAL}">
                        <shiro:hasPermission name="cadreTemp:edit">
                            <a class="popupBtn btn btn-info btn-sm btn-success"
                               data-url="${ctx}/cadreTemp_au"><i class="fa fa-plus"></i>
                                提任干部
                            </a>
                        </shiro:hasPermission>
                        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                data-url="${ctx}/cadreTemp_au">
                            <i class="fa fa-edit"></i> 修改信息
                        </button>
                        <button class="jqOpenViewBtn btn btn-success btn-sm"
                                data-url="${ctx}/cadreTemp_pass">
                            <i class="fa fa-check"></i> 通过常委会任命
                        </button>
                        <button data-url="${ctx}/cadreTemp_abolish"
                                data-title="撤销考察对象"
                                data-msg="确认撤销该考察对象？"
                                class="jqItemBtn btn btn-danger btn-sm">
                            <i class="fa fa-reply"></i> 撤销
                        </button>
                        <shiro:hasPermission name="cadreTemp:edit">
                        <a class="popupBtn btn btn-primary btn-sm tooltip-success"
                           data-url="${ctx}/cadreTemp_import"
                           data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i> 导入</a>
                        </shiro:hasPermission>
                        </c:if>
                        <button class="jqOpenViewBtn btn btn-warning btn-sm"
                                data-url="${ctx}/cadreAdLog_page"
                                data-id-name="tempId"
                                data-open-by="page">
                            <i class="fa fa-search"></i> 任免操作记录
                        </button>
                        <a class="jqExportBtn btn btn-success btn-sm"
                           data-rel="tooltip" data-placement="bottom"
                           title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i> 导出</a>
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
                                    <input name="status" type="hidden" value="${status}">
                                            <div class="form-group">
                                                <label>姓名</label>
                                                    <div class="input-group">
                                                        <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                                name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                            <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
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
                                            <button type="button" class="resetBtn btn btn-warning btn-sm"
                                                    data-querystr="status=${status}">
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
        rownumbers: true,
        url: '${ctx}/cadreTemp_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'user.code', width: 100, frozen: true},
            {
                label: '姓名', name: 'user.realname', width: 120, formatter: function (cellvalue, options, rowObject) {
                return '<a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?cadreId={0}">{1}</a>'
                        .format(rowObject.id, cellvalue);
            }, frozen: true
            },
            <c:if test="${status==CADRE_TEMP_STATUS_NORMAL}">
            {
                label: '排序', width: 80, index: 'sort', formatter: function (cellvalue, options, rowObject) {
                return _.template($("#sort_tpl").html().NoMultiSpace())({id: rowObject.tempId})
            }, frozen: true
            },
            </c:if>
            {label: '现所在单位', name: 'unit.name', width: 200},
            {label: '现任职务', name: 'post', width: 350},
            {label: '现所在单位及职务', name: 'title', width: 350},
            {
                label: '现行政级别',
                name: 'typeId',
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '';
                    return _cMap.adminLevelMap[cellvalue].name;
                }
            },
            {label: '现职务属性', name: 'postType.name', width: 150},
            {label: '手机号', name: 'mobile'},
            {label: '办公电话', name: 'phone'},
            {label: '家庭电话', name: 'homePhone'},
            {label: '电子邮箱', name: 'email', width: 150},
            {label: '备注', name: 'tempRemark', width: 150}, {hidden: true, key: true, name: 'tempId'}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $('[data-rel="tooltip"]').tooltip();
    });

    $(window).triggerHandler('resize.jqGrid');
    _initNavGrid("jqGrid", "jqGridPager");
    $('[data-rel="select2"]').select2();
    register_user_select($('#searchForm select[name=userId]'));
</script>