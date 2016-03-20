<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!-- PAGE CONTENT BEGINS -->
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="closeView btn btn-mini btn-xs btn-success">
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
                <table id="jqGrid2" class="table-striped"> </table>
                <div id="jqGridPager2"> </div>
            </div>
        </div><!-- /.widget-main -->
    </div><!-- /.widget-body -->
</div><!-- /.widget-box -->
<script>
    <c:if test="${param.type=='user'}">
    var url = '${ctx}/user/passportDraw_data?callback=?&passportId=${passport.id}'
    </c:if>
    <c:if test="${param.type!='user'}">
    var url = '${ctx}/passportDraw_data?callback=?&passportId=${passport.id}';
    </c:if>

    $("#jqGrid2").jqGrid({
        //forceFit:true,
        pager:"jqGridPager2",
        url: url,
        colModel: [
            { label: '申请日期', align:'center', name: 'applyDate', width: 100 },
            { label: '申请编码',align:'center', name: 'id',resizable:false, width: 75, formatter:function(cellvalue, options, rowObject){
                return 'A{0}'.format(cellvalue);
            } },
            { label: '因私出国（境）行程',align:'center',  name: 'applyId', width: 180 , formatter:function(cellvalue, options, rowObject){
                if(rowObject.type=='${PASSPORT_DRAW_TYPE_SELF}')
                    return 'S{0}'.format(cellvalue);
                return '-';
            }},
            { label: '出行时间', align:'center', name: 'startDate', width: 100  , formatter:function(cellvalue, options, rowObject){
                if(rowObject.type=='${PASSPORT_DRAW_TYPE_SELF}')
                    return rowObject.applySelf.startDate;
                return cellvalue;
            }},
            { label: '回国时间', align:'center', name: 'endDate', width: 100  , formatter:function(cellvalue, options, rowObject){
                if(rowObject.type=='${PASSPORT_DRAW_TYPE_SELF}')
                    return rowObject.applySelf.endDate;
                return cellvalue;
            }},
            { label: '前往国家或地区', align:'center', name: 'realToCountry',width: 150 , formatter:function(cellvalue, options, rowObject){
                if(rowObject.type=='${PASSPORT_DRAW_TYPE_SELF}')
                    return rowObject.applySelf.toCountry;
                if(rowObject.type=='${PASSPORT_DRAW_TYPE_TW}')
                    return '台湾';
                if(rowObject.type=='${PASSPORT_DRAW_TYPE_OTHER}')
                    return '-';
                return cellvalue;
            }},
            { label:'因私出国境事由', align:'center', name: 'reason', width: 150, formatter:function(cellvalue, options, rowObject){
                if(rowObject.type=='${PASSPORT_DRAW_TYPE_SELF}')
                    return rowObject.applySelf.reason;
                return cellvalue;
            } },
            { label:'借出日期', align:'center', name: 'drawTime', width: 100 },
            { label:'归还日期', align:'center', name: 'realReturnDate', width: 100 }
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');

</script>