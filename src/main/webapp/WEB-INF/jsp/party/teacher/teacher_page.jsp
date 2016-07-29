<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
        <div class="myTableDiv"
             data-url-page="${ctx}/teacher_page"
             data-url-export="${ctx}/teacher_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId ||not empty param.code || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="teacher:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/teacher_au"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/teacher_au"
                       data-grid-id="#jqGrid"
                       data-querystr="&"
                       data-width="900"><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="teacher:del">
                    <button data-url="${ctx}/teacher_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</a>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
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
                            <label>账号ID</label>
                            <input class="form-control search-query" name="userId" type="text" value="${param.userId}"
                                   placeholder="请输入账号ID">
                        </div>
                        <div class="form-group">
                            <label>工作证号</label>
                            <input class="form-control search-query" name="code" type="text" value="${param.code}"
                                   placeholder="请输入工作证号">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
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
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        </div>
        <div id="item-content"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/teacher_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '账号ID',name: 'userId'},
            { label: '工作证号',name: 'code'},
            { label: '姓名',name: 'realname'},
            { label: '性别',name: 'gender'},
            { label: '出生日期',name: 'birth'},
            { label: '籍贯',name: 'nativePlace'},
            { label: '民族',name: 'nation'},
            { label: '身份证号',name: 'idcard'},
            { label: '最高学历',name: 'education'},
            { label: '最高学位',name: 'degree'},
            { label: '学位授予日期',name: 'degreeTime'},
            { label: '所学专业',name: 'major'},
            { label: '毕业学校',name: 'school'},
            { label: '毕业学校类型',name: 'schoolType'},
            { label: '到校日期',name: 'arriveTime'},
            { label: '编制类别',name: 'authorizedType'},
            { label: '人员分类',name: 'staffType'},
            { label: '人员状态',name: 'staffStatus'},
            { label: '岗位类别',name: 'postClass'},
            { label: '岗位子类别',name: 'postType'},
            { label: '在岗情况',name: 'onJob'},
            { label: '专业技术职务',name: 'proPost'},
            { label: '专技岗位等级',name: 'proPostLevel'},
            { label: '职称级别',name: 'titleLevel'},
            { label: '管理岗位等级',name: 'manageLevel'},
            { label: '工勤岗位等级',name: 'officeLevel'},
            { label: '行政职务',name: 'post'},
            { label: '任职级别',name: 'postLevel'},
            { label: '人才/荣誉称号',name: 'talentTitle'},
            { label: '居住地址',name: 'address'},
            { label: '婚姻状况',name: 'maritalStatus'},
            { label: '联系邮箱',name: 'email'},
            { label: '联系手机',name: 'mobile'},
            { label: '家庭电话',name: 'phone'},
            { label: '是否退休',name: 'isRetire'},
            { label: '退休时间',name: 'retireTime'},
            { label: '是否离休',name: 'isHonorRetire'},
            { label: '创建时间',name: 'createTime'},
            { label: '更新时间',name: 'updateTime'}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid');
    })

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>