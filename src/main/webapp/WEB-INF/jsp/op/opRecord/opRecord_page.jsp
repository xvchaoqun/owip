<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.id ||not empty param.startDate ||not empty param.userId ||not empty param.adminLevel ||not empty param.type ||not empty param.way ||not empty param.talkUserId ||not empty param.issue || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="opRecord:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/op/opRecord_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/op/opRecord_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                    <button data-url="${ctx}/op/opRecord_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/op/opRecord_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>
                    <span class="widget-note">${note_searchbar}</span>
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
                                <label>处理对象</label>
                                <div class="input-group">
                                    <select data-rel="select2-ajax"
                                            data-ajax-url="${ctx}/op/opRecord_selects"
                                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>执行日期</label>
                                <div class="input-group tooltip-success" data-rel="tooltip" title="执行日期范围">
                                                    <span class="input-group-addon">
                                                        <i class="fa fa-calendar bigger-110"></i>
                                                    </span>
                                    <input placeholder="请选择执行日期范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="startDate" value="${param.startDate}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>行政级别</label>
                                <select data-width="300" name="adminLevel" data-rel="select2" data-placeholder="请选择">
                                    <option></option>
                                    <c:import url="/metaTypes?__code=mc_admin_level"/>
                                </select>
                                <script>         $("#searchForm select[name=adminLevel]").val('${param.adminLevel}');     </script>
                            </div>
                            <div class="form-group opType">
                                <label>组织处理方式</label>
                                <select data-width="300" name="type" data-rel="select2" data-placeholder="请选择">
                                    <option></option>
                                    <c:import url="/metaTypes?__code=mc_op_type"/>
                                </select>
                                <script>         $("#searchForm select[name=type]").val('${param.type}');     </script>
                            </div>
                            <div class="form-group ask" disabled hidden>
                                <label>针对问题-函询</label>
                                <select data-width="300" name="issue" data-rel="select2" data-placeholder="请选择">
                                    <option></option>
                                    <c:import url="/metaTypes?__code=mc_op_type_ask"/>
                                </select>
                                <script>         $("#searchForm select[name=issue]").val('${param.issue}');     </script>
                            </div>
                            <div class="form-group remind" disabled hidden>
                                <label>针对问题-提醒</label>
                                <select data-width="300" name="issue" data-rel="select2" data-placeholder="请选择">
                                    <option></option>
                                    <c:import url="/metaTypes?__code=mc_op_type_remind"/>
                                </select>
                                <script>         $("#searchForm select[name=issue]").val('${param.issue}');     </script>
                            </div>
                            <div class="form-group encourage" disabled hidden>
                                <label>针对问题-诫勉</label>
                                <select data-width="300" name="issue" data-rel="select2" data-placeholder="请选择">
                                    <option></option>
                                    <c:import url="/metaTypes?__code=mc_op_type_encourage"/>
                                </select>
                                <script>         $("#searchForm select[name=issue]").val('${param.issue}');     </script>
                            </div>
                            <div class="form-group">
                                <label>开展方式</label>
                                <select data-width="300" name="way" data-rel="select2" data-placeholder="请选择">
                                    <option></option>
                                    <c:import url="/metaTypes?__code=mc_op_way"/>
                                </select>
                                <script>         $("#searchForm select[name=way]").val('${param.way}');     </script>
                            </div>
                            <div class="form-group">
                                <label>具体谈话人</label>
                                <div class="input-group">
                                    <select data-rel="select2-ajax"
                                            data-ajax-url="${ctx}/op/opRecord_talkUser_selects"
                                            name="talkUserId" data-placeholder="请输入账号或姓名或学工号">
                                        <option value="${talkUser.id}">${talkUser.realname}-${talkUser.code}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/op/opRecord"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/op/opRecord"
                                            data-target="#page-content">
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
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>

    var opType1 = $(".opType select[name=type]");//组织处理方式
    var opIssueAsk1 = $(".ask select[name=issue]");//针对问题-函询
    var opIssueRemind1 = $(".remind select[name=issue]");//针对问题-提醒
    var opIssueEncourage1 = $(".encourage select[name=issue]");//针对问题-诫勉
    var typeText = opType1.find("option:selected").text();

    opType1.change(function(){
        opType1Change();
    });

    if (typeText == "") {
        $(".ask").hide();
        $(".remind").hide();
        $(".encourage").hide();
        $(".ask").attr("disabled", true);
        $(".remind").attr("disabled", true);
        $(".encourage").attr("disabled", true);
    }else if (typeText == "提醒") {
        $(".ask").hide();
        $(".remind").show();
        $(".encourage").hide();
        $(".ask").attr("disabled", true);
        $(".remind").attr("disabled", false);
        $(".encourage").attr("disabled", true);
    } else if (typeText == "函询") {
        $(".ask").show();
        $(".remind").hide();
        $(".encourage").hide();
        $(".ask").attr("disabled", false);
        $(".remind").attr("disabled", true);
        $(".encourage").attr("disabled", true);
    } else if (typeText == "诫勉") {
        $(".ask").hide();
        $(".remind").hide();
        $(".encourage").show();
        $(".ask").attr("disabled", true);
        $(".remind").attr("disabled", true);
        $(".encourage").attr("disabled", false);
    }

    function opType1Change(){
        var typeText1 = opType1.find("option:selected").text();
        if (typeText1=='函询'){
            $(".ask").show();
            $(".remind").hide();
            $(".encourage").hide();
            $(".ask").attr("disabled",false);
            $(".remind").attr("disabled",true);
            $(".encourage").attr("disabled",true);

            //清空选择框中的值
            opIssueRemind1.val(null);
            opIssueEncourage1.val(null);
        }else if (typeText1=='提醒') {
            $(".ask").hide();
            $(".remind").show();
            $(".encourage").hide();
            $(".ask").attr("disabled",true);
            $(".remind").attr("disabled",false);
            $(".encourage").attr("disabled",true);

            //清空选择框中的值
            opIssueAsk1.val(null);
            opIssueEncourage1.val(null);
        }else if (typeText1=='诫勉') {
            $(".ask").hide();
            $(".remind").hide();
            $(".encourage").show();
            $(".ask").attr("disabled",true);
            $(".remind").attr("disabled",true);
            $(".encourage").attr("disabled",false);

            //清空选择框中的值
            opIssueAsk1.val(null);
            opIssueRemind1.val(null);
        }else{
            $(".ask").hide();
            $(".remind").hide();
            $(".encourage").hide();
            $(".ask").attr("disabled",true);
            $(".remind").attr("disabled",true);
            $(".encourage").attr("disabled",true);
        }
    }

    $("#jqGrid").jqGrid({
        url: '${ctx}/op/opRecord_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '工作证号',name: 'user.code',width:110,frozen:true},
                { label: '处理对象',name: 'user.realname',width:120,formatter:function (cellvalue, options, rowObject) {
                        return $.trim(cellvalue);
                    },frozen:true},
                { label: '执行日期',name: 'startDate',formatter:$.jgrid.formatter.date,formatoptions:{newformat:'Y.m.d'}},
                { label: '时任职务',name: 'post',align:'left',width:350},
                { label: '行政级别',name: 'adminLevel',formatter:$.jgrid.formatter.MetaType},
                { label: '组织处理方式',name: 'type',formatter:$.jgrid.formatter.MetaType},
                { label: '开展方式',name: 'way',width:210,formatter:$.jgrid.formatter.MetaType},
                { label: '谈话人类型',name: 'talkType',width:150,formatter:$.jgrid.formatter.MetaType},
                { label: '具体谈话人',name: 'talkUser.realname'},
                { label: '针对问题',name: 'issue',width:350,formatter:$.jgrid.formatter.MetaType},
                { label: '其他针对问题',name: 'issueOther',width:200},
                { label: '附件',name: 'files',formatter: function (cellvalue, options, rowObject) {
                    return '<button class="popupBtn btn btn-warning btn-xs" data-width="500" data-callback="_reload"' +
                        'data-url="${ctx}/op/opAttatch?recordId={0}"><i class="fa fa-search"></i> 附件{1}</button>'
                            .format(rowObject.id, rowObject.countFile>0?"("+rowObject.countFile+")":"")
                }},
                { label: '备注',name: 'remark',width:200},{hidden:true,key:true,name:'id'},{hidden:true,name:'userId'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>