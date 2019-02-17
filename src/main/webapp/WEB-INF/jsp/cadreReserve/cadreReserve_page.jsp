<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=CadreConstants.CADRE_RESERVE_STATUS_NORMAL%>" var="CADRE_RESERVE_STATUS_NORMAL"/>
<c:set value="<%=CadreConstants.CADRE_RESERVE_STATUS_ABOLISH%>" var="CADRE_RESERVE_STATUS_ABOLISH"/>
<c:set value="<%=CadreConstants.CADRE_RESERVE_STATUS_TO_INSPECT%>" var="CADRE_RESERVE_STATUS_TO_INSPECT"/>
<c:set value="<%=CadreConstants.CADRE_RESERVE_STATUS_ASSIGN%>" var="CADRE_RESERVE_STATUS_ASSIGN"/>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/cadreReserve"

                 data-url-co="${ctx}/cadreReserve_changeOrder"
                 data-url-export="${ctx}/cadreReserve_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.cadreId ||not empty param.adminLevel
            ||not empty param.postType ||not empty param.title || not empty param.code }"/>
                <div class="tabbable">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">

                        <c:forEach var="_type" items="${cm:getMetaTypes('mc_cadre_reserve_type')}">
                            <li class="${status==CADRE_RESERVE_STATUS_NORMAL&&_type.key==reserveType?'active':''}">
                                <a href="javascript:;" class="loadPage" data-url="${ctx}/cadreReserve?reserveType=${_type.key}">
                                    <i class="fa fa-flag"></i>
                                        ${_type.value.name}(${normalCountMap.get(_type.key)})</a>
                            </li>
                        </c:forEach>
                        <shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
                        <c:forEach var="_status" items="<%=CadreConstants.CADRE_RESERVE_STATUS_MAP%>">
                            <c:if test="${_status.key!=CADRE_RESERVE_STATUS_NORMAL}">
                                <li class="<c:if test="${status==_status.key}">active</c:if>">
                                    <a href="javascript:;" class="loadPage" data-url="${ctx}/cadreReserve?status=${_status.key}">
                                        <c:if test="${_status.key==CADRE_RESERVE_STATUS_ABOLISH}">
                                        <i class="fa fa-times"></i>
                                        </c:if>
                                        <c:if test="${_status.key==CADRE_RESERVE_STATUS_ASSIGN}">
                                            <i class="fa fa-check"></i>
                                        </c:if>
                                        <c:if test="${_status.key==CADRE_RESERVE_STATUS_TO_INSPECT}">
                                            <i class="fa fa-circle-o-notch fa-spin"></i>
                                        </c:if>
                                        ${_status.value}(${statusCountMap.get(_status.key)})</a>
                                </li>
                            </c:if>
                        </c:forEach>
                            <div class="buttons pull-left hidden-sm hidden-xs" style="left:50px; position: relative">
                                <a class="popupBtn btn btn-danger btn-sm"
                                   data-url="${ctx}/cadreReserve/search"><i class="fa fa-search"></i> 查询账号所属类别</a>
                            </div>
                        </shiro:lacksRole>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane in active rownumbers">
                            <div class="jqgrid-vertical-offset buttons">
                                <shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
                                <c:if test="${status==CADRE_RESERVE_STATUS_NORMAL}">
                                    <shiro:hasPermission name="cadreReserve:edit">
                                        <a class="popupBtn btn btn-info btn-sm btn-success"
                                           data-url="${ctx}/cadreReserve_au?reserveType=${reserveType}"><i
                                                class="fa fa-plus"></i>
                                            添加
                                        </a>
                                    </shiro:hasPermission>
                                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                            data-url="${ctx}/cadreReserve_au"
                                            data-querystr="&status=${status}&reserveType=${reserveType}">
                                        <i class="fa fa-edit"></i> 修改信息
                                    </button>
                                    <button class="jqOpenViewBtn btn btn-success btn-sm"
                                            data-url="${ctx}/cadreReserve_pass">
                                        <i class="fa fa-check"></i> 列为考察对象
                                    </button>

                                    <button data-url="${ctx}/cadreReserve_abolish"
                                            data-title="撤销"
                                            data-msg="确认撤销？"
                                            class="jqItemBtn btn btn-danger btn-sm">
                                        <i class="fa fa-times"></i> 撤销
                                    </button>
                                    <shiro:hasPermission name="cadreReserve:edit">
                                        <a class="popupBtn btn btn-info btn-sm tooltip-info"
                                           data-url="${ctx}/cadreReserve_import?reserveType=${reserveType}"
                                           data-rel="tooltip" data-placement="top" title="批量导入"><i
                                                class="fa fa-upload"></i> 批量导入</a>
                                    </shiro:hasPermission>
                                </c:if>
                                <button class="jqOpenViewBtn btn btn-warning btn-sm"
                                        data-url="${ctx}/cadreAdLog"
                                        data-id-name="reserveId"
                                        data-open-by="page">
                                    <i class="fa fa-search"></i> 任免操作记录
                                </button>

                                <c:if test="${status==CADRE_RESERVE_STATUS_TO_INSPECT}">
                                    <button class="jqBatchBtn btn btn-danger btn-sm"
                                       data-url="${ctx}/cadreReserve_batchDelPass" data-title="删除"
                                       data-msg="确定删除这{0}条记录吗？（该考察对象的关联数据都将删除，不可恢复。）"><i class="fa fa-trash"></i> 删除</button>
                                </c:if>
                                <c:if test="${status==CADRE_RESERVE_STATUS_ABOLISH}">
                                    <button data-url="${ctx}/cadreReserve_unAbolish"
                                            data-title="重新入库"
                                            data-msg="确认重新入库？"
                                            class="jqItemBtn btn btn-success btn-sm">
                                        <i class="fa fa-reply"></i> 重新入库
                                    </button>
                                    <button class="jqBatchBtn btn btn-danger btn-sm"
                                       data-url="${ctx}/cadreReserve_batchDel" data-title="删除"
                                       data-msg="确定删除这{0}条记录吗？"><i class="fa fa-trash"></i> 删除</button>
                                </c:if>
                                </shiro:lacksRole>
                                <a class="jqExportBtn btn btn-success btn-sm"
                                   data-rel="tooltip" data-placement="bottom"
                                   title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i> 导出</a>
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
                                            <input name="reserveType" type="hidden" value="${reserveType}">
                                            <input name="status" type="hidden" value="${status}">

                                            <div class="form-group">
                                                <label>姓名</label>

                                                <div class="input-group">
                                                    <select data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/cadreReserve_selects?reserveStatus=${status}&reserveType=${reserveType}"
                                                            name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                        <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>行政级别</label>
                                                <select data-rel="select2" name="adminLevel" data-placeholder="请选择行政级别">
                                                    <option></option>
                                                    <jsp:include page="/metaTypes?__code=mc_admin_level"/>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#searchForm select[name=adminLevel]").val(${param.adminLevel});
                                                </script>
                                            </div>
                                            <div class="form-group">
                                                <label>职务属性</label>
                                                <select data-rel="select2" name="postType" data-placeholder="请选择职务属性">
                                                    <option></option>
                                                    <jsp:include page="/metaTypes?__code=mc_post"/>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#searchForm select[name=postType]").val(${param.postType});
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
                                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                            data-querystr="status=${status}&reserveType=${reserveType}">
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
        <div id="body-content-view"></div>
    </div>
</div>
<script type="text/template" id="sort_tpl">
<a href="javascript:;" class="jqOrderBtn" data-id="{{=id}}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
<input type="text" value="1" class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
           title="修改操作步长">
<a href="javascript:;" class="jqOrderBtn" data-id="{{=id}}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>
</script>
<script>
    $("#jqGrid").jqGrid({
        //forceFit:true,
        rownumbers: true,
        url: '${ctx}/cadreReserve_data?reserveType=${reserveType}&callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'user.code', width: 110, frozen: true},
            {
                label: '姓名', name: 'user.realname', width: 120, formatter: function (cellvalue, options, rowObject) {
                return $.cadre(rowObject.id, cellvalue);
            }, frozen: true
            },
            <shiro:hasPermission name="cadreReserve:changeOrder">
            <c:if test="${status==CADRE_RESERVE_STATUS_NORMAL}">
            {
                label: '排序', width: 80, index: 'sort', formatter: function (cellvalue, options, rowObject) {
                return _.template($("#sort_tpl").html().NoMultiSpace())({id: rowObject.reserveId})
            }, frozen: true
            },
            </c:if>
                </shiro:hasPermission>
            {label: '部门属性', name: 'unit.unitType.name', width: 150},
            {label: '所在单位', name: 'unit.name', width: 200, align:'left'},
            {label: '现任职务', name: 'post', align: 'left', width: 250},
            {label: '所在单位及职务', name: 'title', align: 'left', width: 350},
            {label: '行政级别', name: 'adminLevel', formatter:$.jgrid.formatter.MetaType},
            {label: '职务属性', name: 'postType', width: 150, formatter:$.jgrid.formatter.MetaType},
            {
                label: '是否正职', name: 'mainCadrePost.postType', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return _cMap.metaTypeMap[cellvalue].boolAttr ? "是" : "否"
            }
            },
            {label: '性别', name: 'gender', width: 50, formatter:$.jgrid.formatter.GENDER},
            {label: '民族', name: 'nation', width: 60},
            {label: '籍贯', name: 'nativePlace', width: 120},
            {label: '身份证号', name: 'idcard', width: 150},
            {label: '出生时间', name: 'birth', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE},
            {label: '党派', name: '_cadreParty', width: 80, width: 80, formatter: $.jgrid.formatter.cadreParty},
            {label: '党派加入时间', name: '_growTime', width: 120, formatter: $.jgrid.formatter.growTime},
            {label: '党龄', name: '_growAge', width: 50, formatter: $.jgrid.formatter.growAge},
            {label: '到校时间', name: 'arriveTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '最高学历', name: 'eduId', formatter: $.jgrid.formatter.MetaType},
            {label: '最高学位', name: 'degree'},
            {label: '毕业时间', name: 'finishTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}},
            {label: '学习方式', name: 'learnStyle', formatter: $.jgrid.formatter.MetaType},
            {label: '毕业学校、学院', name: 'school', width: 150},
            {
                label: '学校类型', name: 'schoolType', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return _cMap.CADRE_SCHOOL_TYPE_MAP[cellvalue]
            }
            },
            {label: '所学专业', name: 'major'},

            {label: '岗位类别', name: 'postClass'},
            {label: '专业技术职务', name: 'proPost', width: 120},
            {label: '专技岗位等级', name: 'proPostLevel', width: 150},
            {label: '管理岗位等级', name: 'manageLevel', width: 150},
            {
                label: '现职务任命文件',
                width: 150,
                name: 'mainCadrePost.dispatchCadreRelateBean.first',
                formatter: function (cellvalue, options, rowObject) {
                    if (!cellvalue || cellvalue.id == undefined) return '';
                    var dispatchCode = cellvalue.dispatchCode;
                    return $.swfPreview(cellvalue.file, cellvalue.fileName, dispatchCode, dispatchCode);
                }
            },
            {
                label: '任现职时间',
                name: 'mainCadrePost.dispatchCadreRelateBean.last.workTime',
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },
            {
                label: '现职务始任时间',
                width: 150,
                name: 'mainCadrePost.dispatchCadreRelateBean.first.workTime',
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },
            {
                label: '任现职务年限',
                width: 120,
                name: 'mainCadrePost.dispatchCadreRelateBean.first.workTime',
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '';
                    var year = $.yearOffNow(cellvalue);
                    return year == 0 ? "未满一年" : year;
                }
            },
            {
                label: '职级始任日期',
                width: 120,
                name: 'presentAdminLevel.startDispatch.workTime',
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },
            {
                label: '任职级年限',
                width: 120,
                name: 'workYear',
                formatter: function (cellvalue, options, rowObject) {
                    //console.log(rowObject.endDispatch)
                    if (rowObject.presentAdminLevel == undefined || rowObject.presentAdminLevel.startDispatch == undefined) return '';

                    var end;
                    if (rowObject.presentAdminLevel.endDispatch != undefined)
                        end = rowObject.presentAdminLevel.endDispatch.workTime;
                    if (rowObject.presentAdminLevel.adminLevel == rowObject.mainCadrePost.adminLevel)
                        end = new Date().format("yyyy-MM-dd");
                    if (rowObject.presentAdminLevel.startDispatch.workTime == undefined || end == undefined) return '';

                    var month = $.monthDiff(rowObject.presentAdminLevel.startDispatch.workTime, end);
                    var year = Math.floor(month / 12);
                    return year == 0 ? "未满一年" : year;
                }
            },
            {
                label: '是否双肩挑', name: 'mainCadrePost.isDouble', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return cellvalue ? "是" : "否";
            }
            },
           /* {
                label: '双肩挑单位',
                name: 'mainCadrePost.doubleUnitId',
                width: 150,
                formatter: $.jgrid.formatter.unit
            },*/
            {
                label: '双肩挑单位', name: 'mainCadrePost.doubleUnitIds', width: 250, formatter: function (cellvalue, options, rowObject) {

                if($.trim(cellvalue)=='') return '-'
                return ($.map(cellvalue.split(","), function(unitId){
                    return $.jgrid.formatter.unit(unitId);
                })).join("，")

            }},
            {label: '联系方式', name: 'mobile'},
            /*{ label: '办公电话', name: 'phone' },
             { label: '家庭电话', name: 'homePhone' },*/
            {label: '电子邮箱', name: 'email', width: 150},
            <c:if test="${_hasPartyModule}">
            {
                label: '所属党组织',
                name: 'partyId',
                align: 'left',
                width: 550,
                formatter: function (cellvalue, options, rowObject) {
                    return $.party(rowObject.partyId, rowObject.branchId);
                }
            },
            </c:if>
            <shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
            {
                label: '短信称谓', name: 'msgTitle', width: 80, formatter: function (cellvalue, options, rowObject) {
                // 短信称谓
                var msgTitle = $.trim(cellvalue);
                if (msgTitle == '' || msgTitle == rowObject.user.realname) {
                    msgTitle = '无'
                }
                return msgTitle;
            }
            },
            </shiro:lacksRole>
            {label: '备注', name: 'reserveRemark', width: 150}, {hidden: true, key: true, name: 'reserveId'}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $('[data-rel="tooltip"]').tooltip();
    });
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('[data-rel="select2"]').select2();
    $.register.user_select($('#searchForm select[name=cadreId]'));
</script>