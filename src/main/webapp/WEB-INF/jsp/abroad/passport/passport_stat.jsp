<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue jqgrid-vertical-offset">
                    <jsp:include page="menu.jsp"/>
                </ul>

                <div class="tab-content">
                    <div class="tab-pane in active">

                        <div class="space-4"></div>
                            <div style="float: left;width: 650px" class="jqgrid-vertical-offset">
                                <table id="jqGrid1"> </table>
                            </div>
                            <div style="float: left;width: 500px">
                                <table id="jqGrid2"> </table>
                            </div>

                        <div class="row col-xs-12" style="padding-top: 10px">
                            <table id="jqGrid3" class="jqGrid3 table-striped"> </table>
                        </div>
                    </div>
                </div></div></div>
    <div id="body-content-view">
    </div>
    </div>
</div>
<style>
    .footrow td:first-child{
        text-align: right!important;
    }
</style>
<script>
    var classBeans = ${fn:length(classBeans)>0?classBeans:"[]"};
    $("#jqGrid1").jqGrid({
        caption:'<span style="font-size: 18px;font-weight: bolder"><i class="ace-icon fa fa-circle-o"></i> 按照证件的类型和状态统计</span>',
        responsive:false,
        multiselect:false,
        footerrow:true,
        datatype: "local",
        height:112,
        data:classBeans,
        colModel: [
            { label: '证件名称', name: 'passportClass.name', width: 180 },
            { label: '数量',  name: 'num', width: 50 },
            { label: '集中管理',  name: 'keepNum', width: 100 },
            { label: '丢失',  name: 'lostNum', width: 60 },
            { label: '作废',  name: 'abolishNum', width: 60 },
            { label: '取消集中管理（未确认）', name: 'unconfirmNum', width: 180 }
        ],
        gridComplete:function(){
            $(window).triggerHandler('resize.jqGrid3');
            var rowNum=parseInt($(this).getGridParam("records"),10);
            if(rowNum>0){
                //alert(rowNum)
                var num=$(this).getCol("num",false,"sum");
                var keepNum=$(this).getCol("keepNum",false,"sum");
                var lostNum=$(this).getCol("lostNum",false,"sum");
                var abolishNum=$(this).getCol("abolishNum",false,"sum");
                var unconfirmNum=$(this).getCol("unconfirmNum",false,"sum");
                //alert(selfNum)
                $(this).footerData("set",{  'passportClass.name':"合计","num":num,"keepNum":keepNum,
                    "lostNum":lostNum,"abolishNum":abolishNum,"unconfirmNum":unconfirmNum});
            }
        }
    });
    var lentBeans = ${fn:length(lentBeans)>0?lentBeans:"[]"};
    $("#jqGrid2").jqGrid({
        caption:'<span style="font-size: 18px;font-weight: bolder"><i class="ace-icon fa fa-circle-o"></i> 按照证件的借出情况统计</span>',
        responsive:false,
        multiselect:false,
        footerrow:true,
        datatype: "local",
        height:112,
        data:lentBeans,
        colModel: [
            { label: '证件名称', name: 'passportClass.name', width: 180 },
            { label: '集中管理总数量',  name: 'num', width: 150 },
            { label: '未借出',  name: 'unlentNum', width: 80 ,formatter:function(cellvalue, options, rowObject) {
                return rowObject.num - rowObject.lentNum;
            }},
            { label: '借出',  name: 'lentNum', width: 80 }
        ],
        gridComplete:function(){
            $(window).triggerHandler('resize.jqGrid3');
            var rowNum=parseInt($(this).getGridParam("records"),10);
            if(rowNum>0){
                var num=$(this).getCol("num",false,"sum");
                var unlentNum=$(this).getCol("unlentNum",false,"sum");
                var lentNum=$(this).getCol("lentNum",false,"sum");
                $(this).footerData("set",{  'passportClass.name':"合计","num":num,"unlentNum":unlentNum,
                    "lentNum":lentNum});
            }
        }
    });
    var postBeans = ${fn:length(postBeans)>0?postBeans:"[]"};
    $("#jqGrid3").jqGrid({
        caption:'<span style="font-size: 18px;font-weight: bolder"><i class="ace-icon fa fa-circle-o"></i> 按照职务属性统计</span>',
        responsive:false,
        multiselect:false,
        footerrow:true,
        datatype: "local",
        rowNum:postBeans.length,
        data:postBeans,
        colModel: [
            { label: '职务属性', name: 'post.name', width: 340 },
            { label: '因私普通护照',  name: 'selfNum', width: 200 },
            { label: '内地居民往来港澳通行证', name: 'hkNum',width: 200,formatter:function(cellvalue, options, rowObject) {
                return rowObject.num - rowObject.selfNum - rowObject.twNum;
            }},
            { label: '大陆居民往来台湾通行证', name: 'twNum', width: 200 },
            { label: '合计', name: 'num', width: 200 }
        ],
        gridComplete:function(){
            $(window).triggerHandler('resize.jqGrid3');
            var rowNum=parseInt($(this).getGridParam("records"),10);
            if(rowNum>0){
                //alert(rowNum)
                var selfNum=$(this).getCol("selfNum",false,"sum");
                var hkNum=$(this).getCol("hkNum",false,"sum");
                var twNum=$(this).getCol("twNum",false,"sum");
                var num=$(this).getCol("num",false,"sum");
                //alert(selfNum)
                $(this).footerData("set",{  'post.name':"合计","selfNum":selfNum,"hkNum":hkNum,"twNum":twNum,"num":num});                               //将合计值显示出来
            }
        }
    });
</script>
