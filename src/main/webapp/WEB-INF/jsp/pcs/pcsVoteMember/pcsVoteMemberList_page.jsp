<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="tabbable">
                <jsp:include page="../pcsVoteStat/menu.jsp"/>
                <div class="jqgrid-vertical-offset buttons" style="padding: 10px">
                    <a class="linkBtn btn btn-info btn-sm" style="float: left"
                       data-url="${ctx}/pcsVoteMember_export"><i class="fa fa-download"></i>
                        当选名单</a>
                    <div style="line-height: 30px; float: left;margin-left: 20px;">
                        <input class="typeCheckbox" ${param.type==PCS_USER_TYPE_DW?"checked":""} type="checkbox" value="${PCS_USER_TYPE_DW}"> 党委委员
                        <input class="typeCheckbox" ${param.type==PCS_USER_TYPE_JW?"checked":""} type="checkbox" value="${PCS_USER_TYPE_JW}"> 纪委委员
                    </div>
                </div>

                <div class="space-4"></div>
                <div class="tab-content">

                    <div class="tab-pane in active rownumbers">
                        <div class="space-4"></div>
                        <table id="jqGrid" class="jqGrid table-striped"></table>
                        <div id="jqGridPager"></div>
                    </div>
                </div>
            </div>
        </div>
        <div id="item-content"></div>
    </div>
</div>
<style>
    .type-select{
        padding: 10px 0 0 5px;
        float: left;
        margin-right: 50px;
    }
    .type-select .typeCheckbox{
        padding: 10px;
        cursor: pointer;
    }
    .type-select .typeCheckbox.checked{
        color: darkred;
        font-weight: bolder;
    }

    .modal .tip ul{
        margin-left: 50px;
    }
    .modal .tip ul li{
        /*font-size: 13px;*/
        text-align: left;
    }
</style>
<script>
    $(".typeCheckbox").click(function(){
        $("#page-content").loadPage({url:"${ctx}/pcsVoteStat?cls=4&type="+$(this).val()})
    })

    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/pcsVoteMember_data?callback=?&orderType=0&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '候选人姓名', name: 'realname', width: 120, frozen: true},
            {label: '赞成票数', name: 'agree', width: 80},
            {label: '不赞成票数', name: 'degree', width: 110},
            {label: '弃权票数', name: 'abstain', width: 80},
            {
                label: '性别', name: 'gender', width: 50, formatter: $.jgrid.formatter.GENDER
            },
            {label: '民族', name: 'nation', width: 80},
            {label: '职称', name: 'proPost', width: 200},
            {label: '出生年月', name: 'birth', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '年龄', name: 'age', width: 80},
            {
                label: '入党时间',
                name: 'growTime',
                width: 120,
                sortable: true,
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },
            {label: '所在单位及职务', name: 'title', width: 350, align:'left'}, {hidden: true, key: true, name: 'userId'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
</script>