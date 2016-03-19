<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
        <div class="tabbable">
            <jsp:include page="/WEB-INF/jsp/party/member/member_menu.jsp"/>

            <div class="tab-content">
                <div id="home4" class="tab-pane in active">

        <div class="myTableDiv"
             data-url-au="${ctx}/member_au"
             data-url-page="${ctx}/memberTeacher_page"
             data-url-bd="${ctx}/member_batchDel"
             data-url-export="${ctx}/memberTeacher_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <input type="hidden" name="cls" value="${cls}">

                <select  class="form-control" data-rel="select2-ajax"
                         data-ajax-url="${ctx}/member_selects?type=${MEMBER_TYPE_TEACHER}&status=${MEMBER_STATUS_NORMAL}"
                         name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}</option>
                </select>
                <a class="searchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.userId || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm" data-querystr="cls=${cls}">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <a href="javascript:;" class="openView btn btn-info btn-sm" data-url="${ctx}/member_au">
                        <i class="fa fa-plus"></i> 添加党员</a>
                    <a href="javascript:;" class="jqEditBtn btn btn-primary btn-sm"
                       data-open-by="page" data-id-name="userId">
                        <i class="fa fa-edit"></i> 修改信息</a>
                    <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）">
                        <i class="fa fa-download"></i> 导出</a>
                    <a class="batchDelBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </a>
                </div>
            </mytag:sort-form>
            <div class="space-4"></div>
            <table id="jqGrid" class="table-striped"> </table>
            <div id="jqGridPager"> </div>
        </div>
                    </div></div></div>
            </div>
            <div id="item-content"></div>
    </div>
</div>
<script>

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_user_select($('#searchForm select[name=userId]'));

    function partyFormatter (cellvalue, options, rowObject)
    {
        var party = rowObject.party;
        var branch = rowObject.branch;
        return party + ((branch=='')?'':'-'+branch);
    }
    function nameFormatter(cellvalue, options, rowObject){

        return '<a href="javascript:;" class="openView" data-url="${ctx}/member_view?userId={0}">{1}</a>'
                .format(rowObject.userId, cellvalue);
    }
    $("#jqGrid").jqGrid({
        url: '${ctx}/memberTeacher_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '姓名',align:'center', name: 'realname',resizable:false, width: 75, formatter:nameFormatter ,frozen:true },
            <c:if test="${cls==4}">
            { label: ' ', align:'center',  width: 200 ,frozen:true, formatter:function(cellvalue, options, rowObject){
                if(rowObject.retireApply.status!=0)
                    return '<button onclick="_retireApply({0})" class="btn btn-primary btn-mini btn-xs">'.format(rowObject.retireApply.userId)
                            +'<i class="fa fa-edit"></i> 提交党员退休'
                            +'</button>';
                if(rowObject.retireApply.status==0)
                    return '<button onclick="_retireApply({0})" class="btn btn-success btn-mini btn-xs">'.format(rowObject.retireApply.userId)
                            +'<i class="fa fa-check"></i> 审核党员退休'
                            +'</button>';
            }},
            </c:if>
            { label: '工作证号', align:'center', name: 'code', width: 100 ,frozen:true},
            { label: '性别', align:'center', name: 'gender', width: 55 },
            { label: '年龄', align:'center', name: 'age', width: 55 },
            { label: '最高学历', align:'center', name: 'education', width: 100 },
            { label: '岗位类别', align:'center', name: 'postClass', width: 100 },
            { label: '专业技术职务', align:'center', name: 'proPost', width: 150 },
            { label:'所属组织机构', name: 'party', width: 550, formatter:partyFormatter },
            { label:'入党时间', align:'center', name: 'growTime', width: 100 },
            { label:'联系手机', align:'center', name: 'mobile', width: 100},
            <c:if test="${cls>=3}">
            { label:'退休时间', align:'center', name: 'retireTime', width: 100 },
            { label:'是否离休', align:'center', name: 'isHonorRetire', width: 100 },
            </c:if>
            {hidden:true, key:true, name:'retireApply.userId'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    function _retireApply(userId){

        loadModal("${ctx}/retireApply?userId="+userId);
    }
</script>