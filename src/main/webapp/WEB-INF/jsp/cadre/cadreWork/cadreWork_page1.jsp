<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
                <div class="vspace-12"></div>
                <div class="buttons">
                    <shiro:hasPermission name="cadreWork:edit">
                        <a class="popupBtn btn btn-success btn-sm"
                           data-url="${ctx}/cadreWork_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                            添加工作经历</a>
                        <a class="jqOpenViewBtn btn btn-primary btn-sm"
                           data-url="${ctx}/cadreWork_au"
                           data-grid-id="#jqGrid_cadreWork"
                           data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
                            修改工作经历</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="cadreWork:del">
                        <button data-url="${ctx}/cadreWork_batchDel"
                                data-title="删除"
                                data-msg="确定删除这{0}条数据？"
                                data-grid-id="#jqGrid_cadreWork"
                                class="jqBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> 删除
                        </button>
                    </shiro:hasPermission>
                </div>
            <div class="space-4"></div>
            <table id="jqGrid_cadreWork"></table>
<script>
    $("#jqGrid_cadreWork").jqGrid({
        ondblClickRow:function(){},
        //datatype:"json",
        responsive:false,
        pager:null,
        url: '${ctx}/cadreWork_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '开始日期', name: 'startTime',formatter:'date',formatoptions: {newformat:'Y.m'}, width:180},
            {label: '结束日期', name: 'endTime',formatter:'date',formatoptions: {newformat:'Y.m'}},
            {label: '工作单位', name: 'unit'},
            {label: '担任职务或者专技职务', name: 'post', width:180},
            {label: '行政级别', name: 'typeId', formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return ''
                return _metaTypeMap[cellvalue]
            }},
            {label: '工作类型', name: 'workType', formatter:function(cellvalue, options, rowObject){
                return _metaTypeMap[cellvalue]
            }, width:120}
        ], "treeGrid":true,
        "treedatatype":"jsonp",
        // the model used
        "treeGridModel":"nested",
        // configuration of the data comming from server
        "treeReader":{
            "level_field":"level",
            "leaf_field":"isLeaf"
        },
        ExpandColumn: 'startTime'
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        //$(window).triggerHandler('resize.jqGrid4');
    });

    function showSubWork(id){
        loadModal("${ctx}/cadreWork_page?cadreId=${param.cadreId}&fid="+id, 1000);
    }

    var _id;
    function showDispatch(id){
        _id = id;
        loadModal("${ctx}/cadreWork_addDispatchs?cadreId=${cadre.id}&id="+id, 1000);
    }

    function closeSwfPreview(){
        showDispatch(_id);
    }

    function _reload(){
        $("#modal").modal('hide');
        $("#view-box .tab-content").load("${ctx}/cadreWork_page?${cm:encodeQueryString(pageContext.request.queryString)}");
    }

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>
