<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/modify/constants.jsp"%>
<div class="jqgrid-vertical-offset clearfix" style="background-color: #f5f5f5;padding: 5px 0 5px 0">
    <div class="col-md-9">
        <button class="hideView btn btn-success btn-sm" type="button">
            <i class="ace-icon fa fa-backward bigger-110"></i>
            返回
        </button>
    </div>
</div>
<c:if test="${param.type=='check'}">
<div class="jqgrid-vertical-offset widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="ace-icon fa fa-info-circle blue "></i> 申请人信息</h4>
        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <span class="info"><span class="name">工作证号：</span><span class="value">${uv.code}</span></span>
            <span class="info"><span class="name">姓名：</span><span class="value">${uv.realname}</span></span>
            <span class="info"><span class="name">所在单位及职务：</span><span class="value">${cadre.title}</span></span>
        </div>
    </div>
</div>
    </c:if>

<div class="widget-box" id="useLogs">
    <div class="widget-header">
        <h4 class="widget-title"><i class="ace-icon fa fa-list blue "></i> 修改字段审核情况</h4>
        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="30"> </table>
            <div id="jqGridPager2"> </div>
        </div><!-- /.widget-main -->
    </div><!-- /.widget-body -->
</div><!-- /.widget-box -->
<style>
    .info{
        display: inline-block;
    }
    .info .name{font-weight: bolder}
    .info .value{padding-right: 50px;}
</style>
<script>
    $.register.date($('.date-picker'));

    $("#jqGrid2").jqGrid({
        //forceFit:true,
        pager:"jqGridPager2",
        url: "${ctx}/modifyBaseItem_data?callback=?&applyId=${param.applyId}",
        colModel: [
            { label: '序号', name: 'id', width: 80 },
            { label: '申请时间', name: 'createTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'} },
            { label: '字段名称', name: 'name', width: 150},
            { label: '修改前', name:'orginalValue', width: 150,formatter:function(cellvalue, options, rowObject){

                if(cellvalue==undefined) return '';
                if(rowObject.type=='${MODIFY_BASE_ITEM_TYPE_IMAGE}'){

                    return '<a class="various" title="{1}" data-fancybox-type="image" href="${ctx}/avatar?path={0}"> <img class="avatar" src="${ctx}/avatar?path={0}"/></a>'
                            .format(encodeURI(cellvalue), "原头像");
                }else if(rowObject.code=='health'){
                   return $.jgrid.formatter.MetaType(cellvalue, options, rowObject);
                }else{
                    return cellvalue;
                }
            }},
            { label: '修改后',  name: 'modifyValue', width: 150,formatter:function(cellvalue, options, rowObject){

                if(cellvalue==undefined) return '';
                if(rowObject.type=='${MODIFY_BASE_ITEM_TYPE_IMAGE}'){
                    var path = '${ctx}/avatar?path={0}&_='.format(encodeURI(cellvalue)) + new Date().getTime();
                    return '<a class="various" title="{1}" data-fancybox-type="image" href="{0}"><img class="avatar" src="{0}"/></a>'
                            .format(path, "新头像");
                }else if(rowObject.code=='health'){
                    return $.jgrid.formatter.MetaType(cellvalue, options, rowObject);
                }else{
                    return cellvalue;
                }
            }},
            <c:if test="${param.type!='check'}">
            { label: '审核状态', name: 'status'  , formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return ''
                return _cMap.MODIFY_BASE_ITEM_STATUS_MAP[cellvalue];
            }},
            </c:if>
            <c:if test="${param.type=='check'}">
            { label: '组织部审核', name: 'status'  , formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return ''
                if (rowObject.status != '${MODIFY_BASE_APPLY_STATUS_APPLY}') {
                    return _cMap.MODIFY_BASE_ITEM_STATUS_MAP[cellvalue];
                }else{
                    return '<button data-url="${ctx}/modifyBaseItem_approval?id={0}" class="popupBtn btn btn-success btn-xs">'
                                    .format(rowObject.id)
                            + '<i class="fa fa-check"></i> 审核</button>'
                }
            }},
            { label:'审核人', name: 'checkUser.realname' },
            { label:'依据', name: 'checkReason', width: 150 },
            { label:'备注', name: 'checkRemark', width: 250 },
            </c:if>
            { label: '审核时间', name: 'checkTime', width: 150/*, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}*/},
            <c:if test="${param.type!='check' && apply.status!=MODIFY_BASE_APPLY_STATUS_DELETE && apply.userId==_user.id}">
            { label:'编辑', name: '_edit',formatter: function (cellvalue, options, rowObject) {
                if (rowObject.status != '${MODIFY_BASE_APPLY_STATUS_APPLY}') {
                    return '-';
                }
                return '<button data-url="${ctx}/user/modifyBaseItem_au?id={0}" class="popupBtn btn btn-primary btn-xs">'
                                .format(rowObject.id)
                        + '<i class="fa fa-edit"></i> 编辑</button>'
            }},
            { label:'删除', name: '_del',formatter: function (cellvalue, options, rowObject) {
                if (rowObject.status != '${MODIFY_BASE_APPLY_STATUS_APPLY}') {
                    return '-';
                }
                return '<button data-url="${ctx}/user/modifyBaseItem_del?id={0}" data-msg="确定删除此字段修改？" data-callback="_reload" class="confirm btn btn-danger btn-xs">'
                                .format(rowObject.id)
                        + '<i class="fa fa-times"></i> 删除</button>'
            }}
            </c:if>
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    function _reload(){
        $("#modal").modal('hide');
        $("#jqGrid2").trigger("reloadGrid");
    }
</script>