<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
        <div class="myTableDiv"
             data-url-au="${ctx}/dispatch_au"
             data-url-page="${ctx}/dispatch_page"
             data-url-del="${ctx}/dispatch_del"
             data-url-bd="${ctx}/dispatch_batchDel"
             data-url-export="${ctx}/dispatch_data"
             data-url-co="${ctx}/dispatch_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year ||not empty param.typeId ||not empty param.code
            ||not empty param._pubTime ||not empty param._workTime ||not empty param._meetingTime || not empty param.code}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="dispatch:edit">
                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                </shiro:hasPermission>
                <a href="javascript:;" class="jqEditBtn btn btn-primary btn-sm">
                    <i class="fa fa-edit"></i> 修改信息</a>
                <a class="jqOpenViewBtn btn btn-success btn-sm"
                   data-open-by="page" data-id-name="dispatchId" data-url="${ctx}/dispatch_cadres">
                    <i class="fa fa-plus"></i> 添加干部任免
                </a>
                <a class="exportBtn btn btn-success btn-sm tooltip-success"
                   data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                <shiro:hasPermission name="dispatch:del">
                    <a class="jqDelBtn btn btn-danger btn-sm"><i class="fa fa-trash"></i> 删除</a>
                </shiro:hasPermission>

            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs" style="margin-right: 20px">
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
                                            <label>年份</label>
                                                <div class="input-group">
                                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                                    <input class="form-control date-picker" placeholder="请选择年份" name="year" type="text"
                                                           data-date-format="yyyy" data-date-min-view-mode="2" value="${param.year}" />
                                                </div>
                                        </div>

                                        <div class="form-group">
                                            <label>任免日期</label>
                                                <div class="input-group tooltip-success" data-rel="tooltip" title="任免日期范围">
                                                    <span class="input-group-addon">
                                                        <i class="fa fa-calendar bigger-110"></i>
                                                    </span>
                                                    <input placeholder="请选择任免日期范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_workTime" value="${param._workTime}"/>
                                                </div>
                                        </div>
                                        <div class="form-group">
                                            <label>发文类型</label>
                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/dispatchType_selects"
                                                        name="dispatchTypeId" data-placeholder="请选择发文类型">
                                                    <option value="${dispatchType.id}">${dispatchType.name}</option>
                                                </select>
                                        </div>
                                        <div class="form-group">
                                            <label>发文日期</label>
                                                <div class="input-group tooltip-success" data-rel="tooltip" title="发文日期范围">
                                                    <span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>
                                                    <input placeholder="请选择发文日期范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_pubTime" value="${param._pubTime}"/>
                                                </div>
                                        </div>
                                        <div class="form-group">
                                            <label>发文号</label>
                                                <input class="form-control search-query" name="code" type="text" value="${param.code}"
                                                       placeholder="请输入发文号">
                                        </div>
                                        <div class="form-group">
                                            <label>常委会</label>
                                                <div class="input-group tooltip-success" data-rel="tooltip" title="党委常委会日期范围">
                                            <span class="input-group-addon">
                                                <i class="fa fa-calendar bigger-110"></i>
                                            </span>
                                                    <input placeholder="请选择党委常委会日期范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_meetingTime" value="${param._meetingTime}"/>
                                                </div>
                                        </div>
                                <div class="clearfix form-actions center">
                                        <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query || not empty param.sort}">&nbsp;
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
        <div id="item-content"> </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<link rel="stylesheet" href="${ctx}/extend/css/jquery.webui-popover.min.css" type="text/css" />
<script src="${ctx}/extend/js/jquery.webui-popover.min.js"></script>
<script type="text/template" id="dispatch_del_file_tpl">
    <a class="btn btn-success btn-sm" onclick="dispatch_del_file({{=id}}, '{{=type}}')">
        <i class="fa fa-check"></i> 确定</a>&nbsp;
    <a class="btn btn-default btn-sm" onclick="hideDel()"><i class="fa fa-trash"></i> 取消</a>
</script>
<script type="text/template" id="sort_tpl">
<a href="#" class="jqOrderBtn" data-id="{{=id}}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
<input type="text" value="1" class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
<a href="#" class="jqOrderBtn" data-id="{{=id}}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>
</script>

<script>

    function hideDel(){
        $(".dispatch_del_file").webuiPopover("hide")
    }
    function dispatch_del_file(id, type){
        $.post("${ctx}/dispatch_del_file",{id:id, type:type},function(data){
            if(data.success) {
                hideDel();
                $("#jqGrid").trigger("reloadGrid");
                SysMsg.success('操作成功。', '成功');
            }
        });
    }
    register_date($('.date-picker'));
    $('[data-rel="select2"]').select2();
    register_dispatchType_select($('#searchForm select[name=dispatchTypeId]'), $("#searchForm input[name=year]"));

    $("#jqGrid").jqGrid({
        url: '${ctx}/dispatch_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '年份', name: 'year',resizable:false, width: 75, frozen:true },
            { label:'发文类型', name: 'dispatchType', width: 100  , formatter:function(cellvalue, options, rowObject){
                return cellvalue.name;
            },frozen:true},
            { label:'发文号',  name: 'dispatchCode', width: 180,formatter:function(cellvalue, options, rowObject){
                if(rowObject.fileName && rowObject.fileName!='')
                    return '<a href="javascript:void(0)" onclick="swf_preview({0}, \'file\')">{1}</a>'.format(rowObject.id, cellvalue);
                else return cellvalue;
            }, frozen:true },
                <c:if test="${!_query}">
            { label:'排序',width: 100, index:'sort', formatter:function(cellvalue, options, rowObject){
                return _.template($("#sort_tpl").html().replace(/\n|\r|(\r\n)/g,''))({id:rowObject.id})
            }, frozen:true },
            </c:if>
            { label: '党委常委会日期',  name: 'meetingTime', width: 130 },
            { label: '发文日期',  name: 'pubTime', width: 100 },
            { label: '任免日期',  name: 'workTime', width: 100 },
            { label: '任免文件',  width: 100, formatter:function(cellvalue, options, rowObject){
                if(rowObject.fileName && rowObject.fileName!='')
                    return '<a href="javascript:void(0)" onclick="swf_preview({0}, \'file\')">查看</a>'
                            .format(rowObject.id) + '&nbsp;<a href="javascript:void(0)" class="dispatch_del_file"'
                            + 'data-id="{0}" data-type="file">删除</a>'.format(rowObject.id);
                else return '';
            } },
            { label: '上会ppt',  width: 100, formatter:function(cellvalue, options, rowObject){
                if(rowObject.pptName && rowObject.pptName!='')
                    return '<a href="javascript:void(0)" onclick="swf_preview({0}, \'ppt\')">查看</a>'
                                    .format(rowObject.id) + '&nbsp;<a href="javascript:void(0)" class="dispatch_del_file"'
                            + 'data-id="{0}" data-type="ppt">删除</a>'.format(rowObject.id);
                else return '';
            } },
            { label:'备注', name: 'remark', width: 550 }
            ]
    }).jqGrid("setFrozenColumns").on("initGrid", function(){
        $(".dispatch_del_file").each(function(){
            var id = $(this).data('id');
            var type = $(this).data('type');
            $(this).webuiPopover({width:'180px',animation:'pop',
                content:function(){
                    return  _.template($("#dispatch_del_file_tpl").html())({id:id, type:type})
                }});
        });
        $('[data-rel="tooltip"]').tooltip();
    });
    $(window).triggerHandler('resize.jqGrid');
</script>