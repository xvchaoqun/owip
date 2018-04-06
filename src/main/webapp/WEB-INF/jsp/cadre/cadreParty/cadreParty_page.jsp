<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="myTableDiv"
                 data-url-page="${ctx}/cadreParty"
                 data-url-export="${ctx}/cadreParty_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId ||not empty param.typeId||not empty param.classId
            ||not empty param.postId ||not empty param.title || not empty param.code }"/>
                <div class="tabbable">

                    <div class="tab-content" style="padding: 0">
                        <div id="home4" class="tab-pane in active rownumbers">
                            <div class="jqgrid-vertical-offset buttons">
                                <shiro:hasPermission name="cadreParty:edit">
                                    <a class="popupBtn btn btn-sm btn-success"
                                       data-url="${ctx}/cadreParty_au?type=${type}"><i class="fa fa-plus"></i> 添加</a>
                                </shiro:hasPermission>
                                <c:if test="${type==2}">
                                <a class="popupBtn btn btn-warning btn-sm tooltip-success"
                                   data-url="${ctx}/cadreParty_import"
                                   data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                                    批量导入</a>
                                </c:if>
                                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                        data-url="${ctx}/cadreParty_au">
                                    <i class="fa fa-edit"></i> 修改信息
                                </button>
                                <shiro:hasPermission name="cadreParty:del">
                                    <a class="jqBatchBtn btn btn-danger btn-sm"
                                       data-url="${ctx}/cadreParty_batchDel" data-title="删除"
                                       data-msg="确定删除这{0}条数据吗？"><i class="fa fa-trash"></i> 删除</a>
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
                                            <input type="hidden" name="type" value="${type}">

                                            <div class="form-group">
                                                <label>姓名</label>

                                                <div class="input-group">
                                                    <select data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/cadre_selects?key=1"
                                                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <c:if test="${param.type==1}">
                                                <div class="form-group">
                                                    <label>民主党派</label>
                                                    <select data-rel="select2" name="dpTypeId"
                                                            data-placeholder="请选择民主党派">
                                                        <option></option>
                                                        <jsp:include page="/metaTypes?__code=mc_democratic_party"/>
                                                    </select>
                                                    <script type="text/javascript">
                                                        $("#searchForm select[name=dpTypeId]").val(${param.dpTypeId});
                                                    </script>
                                                </div>
                                            </c:if>
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
                                                <input class="form-control search-query" name="title" type="text"
                                                       value="${param.title}"
                                                       placeholder="请输入单位及职务">
                                            </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"><i
                                                        class="fa fa-search"></i> 查找</a>

                                                <c:if test="${_query || not empty param.sort}">&nbsp;
                                                    <button type="button" class="resetBtn btn btn-warning btn-sm"
                                                            data-querystr="type=${type}">
                                                        <i class="fa fa-reply"></i> 重置
                                                    </button>
                                                </c:if>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <div class="space-4"></div>

                            <table id="jqGrid" class="jqGrid table-striped"></table>
                            <div id="jqGridPager"></div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="body-content-view">

        </div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/cadreParty_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'user.code', width: 100, frozen: true},
            {
                label: '姓名', name: 'user.realname', width: 120, formatter: function (cellvalue, options, rowObject) {
                return $.cadre(rowObject.id, cellvalue);
            }, frozen: true
            },
            <c:if test="${type==1}">
            {label: '民主党派', name: 'dpTypeId', formatter: $.jgrid.formatter.MetaType},
            {label: '党派加入时间', name: 'dpGrowTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, width: 120},
            {label: '担任党派职务', name: 'dpPost',align:'left',  width: 250},
            </c:if>
            <c:if test="${type==2}">
            {label: '党派加入时间', name: 'owGrowTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, width: 120},
            {
                label: '是否存在于党员信息库', width: 180, name: 'memberStatus', formatter: function (cellvalue, options, rowObject) {
                var str = "否";
                var ms = $.trim(cellvalue);
                if(ms!='' && (ms=='${MEMBER_STATUS_NORMAL}' || ms=='${MEMBER_STATUS_TRANSFER}')){
                    str = "是";
                }
                return str;
            }, cellattr:function(rowId, val, rowObject, cm, rdata) {
                return ($.trim(rowObject.memberStatus)=='')?"":"class='danger'";
            }},
            </c:if>
            {label: '部门属性', name: 'unit.unitType.name', width: 150},
            {label: '所在单位', name: 'unit.name',align:'left',  width: 200},
            {label: '现任职务', name: 'post', align:'left', width: 250},
            {label: '所在单位及职务', name: 'title',  align:'left', width: 350},
            {
                label: '行政级别', name: 'typeId', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return _cMap.adminLevelMap[cellvalue].name;
            }
            },
            {
                label: '职务属性', name: 'postId', width: 150, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return _cMap.postMap[cellvalue].name;
            }
            },
            {
                label: '在任情况', name: 'status', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';

                return _cMap.CADRE_STATUS_MAP[cellvalue];
            }
            },
            <c:if test="${type==1}">
            {label: '备注', name: 'dpRemark', align:'left', width: 350}, {name: 'dpId', key:true, hidden:true}
            </c:if>
            <c:if test="${type==2}">
            {label: '备注', name: 'owRemark', align:'left', width: 350}, {name: 'owId', key:true, hidden:true}
            </c:if>
        ]
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $('[data-rel="tooltip"]').tooltip();
    });
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('[data-rel="select2"]').select2();
    $.register.user_select($('#searchForm select[name=userId]'));
</script>