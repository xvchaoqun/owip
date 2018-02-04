<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!-- PAGE CONTENT BEGINS -->
<div class="widget-box transparent" id="useLogs">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">${sysUser.realname}-${passportTypeMap.get(passport.classId).name}使用记录</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <div class="jqgrid-vertical-offset ">
                <form class="form-inline hidden-sm hidden-xs" id="useLogForm">
                    <div class="input-group" style="width: 120px">
                        <input class="form-control date-picker" name="year" type="text"
                               data-date-format="yyyy" placeholder="年份" data-date-min-view-mode="2" value="${param.year}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                    <a class="_searchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                    <c:if test="${not empty param.year}">
                        <button type="button" class="resetBtn btn btn-warning btn-sm">
                            <i class="fa fa-reply"></i> 重置
                        </button>
                    </c:if>
                    <div class="buttons pull-right">
                        <a class="exportBtn btn btn-primary btn-sm tooltip-success"
                           data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）">
                            <i class="fa fa-download"></i> 导出</a>
                    </div>
                </form>

                </div>
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped"> </table>
                <div id="jqGridPager2"> </div>
            </div>
        </div><!-- /.widget-main -->
    </div><!-- /.widget-body -->
</div><!-- /.widget-box -->
<script>
    register_date($('.date-picker'));
    $("#useLogForm ._searchBtn").click(function(){
        var year = $("#useLogForm input[name=year]").val();
        if(year==''){
            $("#useLogForm input[name=year]").focus();
            return;
        }
        $("#item-content").load("${ctx}/${param.type=='user'?'user/':''}abroad/passport_useLogs?type=${param.type}"+
        "&id=${passport.id}&year="+year);
    });
    $("#useLogForm .resetBtn").click(function(){
        $("#item-content").load("${ctx}/${param.type=='user'?'user/':''}abroad/passport_useLogs?type=${param.type}"+
        "&id=${passport.id}");
    });
    $("#useLogForm .exportBtn").click(function(){

        var year = $("#useLogForm input[name=year]").val();
        location.href = "${ctx}/${param.type=='user'?'user/':''}abroad/passportDraw_data?export=1&passportId=${passport.id}"+
        "&year="+year;
    });


    $("#jqGrid2").jqGrid({
        //forceFit:true,
        pager:"jqGridPager2",
        url: "${ctx}/${param.type=='user'?'user/':''}abroad/passportDraw_data?callback=?&passportId=${passport.id}&year=${param.year}",
        colModel: [
            { label: '申请日期', name: 'applyDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            { label: '申请编码', name: 'id', width: 75, formatter:function(cellvalue, options, rowObject){
                return 'D{0}'.format(cellvalue);
            } },
            { label: '用途', name:'type', width: 150 , formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '';
                return _cMap.PASSPORT_DRAW_TYPE_MAP[cellvalue];
            }},
            { label: '行程',  name: 'applyId', width: 75 , formatter:function(cellvalue, options, rowObject){
                if(rowObject.type=='${PASSPORT_DRAW_TYPE_SELF}')
                    return 'S{0}'.format(cellvalue);
                return  '-';
            }},
            { label: '出行时间', name: 'startDate'  , formatter:function(cellvalue, options, rowObject){
                if(rowObject.type=='${PASSPORT_DRAW_TYPE_SELF}')
                    return $.date(rowObject.applySelf.startDate, "yyyy-MM-dd");
                if(rowObject.type=='${PASSPORT_DRAW_TYPE_OTHER}')
                    return '-';
                return cellvalue;
            }},
            { label: '回国时间', name: 'endDate'  , formatter:function(cellvalue, options, rowObject){
                if(rowObject.type=='${PASSPORT_DRAW_TYPE_SELF}')
                    return $.date(rowObject.applySelf.endDate, "yyyy-MM-dd");
                if(rowObject.type=='${PASSPORT_DRAW_TYPE_OTHER}')
                    return '-';
                return cellvalue;
            }},
            { label: '前往国家或地区', name: 'realToCountry',width: 150 , formatter:function(cellvalue, options, rowObject){
                if(rowObject.type=='${PASSPORT_DRAW_TYPE_SELF}')
                    return rowObject.applySelf.toCountry;
                /*if(rowObject.type=='${PASSPORT_DRAW_TYPE_TW}')
                    return '台湾';
                if(rowObject.type=='${PASSPORT_DRAW_TYPE_OTHER}')
                    return '-';
                return cellvalue;*/
                return '-';
            }},
            { label:'因私出国境事由', name: 'reason', width: 150, formatter:function(cellvalue, options, rowObject){
                if(rowObject.type=='${PASSPORT_DRAW_TYPE_SELF}')
                    return rowObject.applySelf.reason.replace(/\+\+\+/g, ',');;
                return cellvalue;
            } },
            { label:'借出日期', name: 'drawTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'} },
            { label:'归还日期', name: 'realReturnDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'} }
        ],
        gridComplete:function(){
            $(window).triggerHandler('resize.jqGrid2');
        }
    });


</script>