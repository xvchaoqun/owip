<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div  class="myTableDiv"

                  data-url-page="${ctx}/cadre_page"
                  data-url-del="${ctx}/cadre_del"
                  data-url-bd="${ctx}/cadre_batchDel"
                  data-url-co="${ctx}/cadre_changeOrder?status=${status}"
                  data-url-export="${ctx}/cadre_data"
                  data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.cadreId ||not empty param.typeId
            ||not empty param.postId ||not empty param.title || not empty param.code }"/>

        <div class="tabbable">
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <c:forEach var="cadreStatus" items="${CADRE_STATUS_MAP}">
                <li class="<c:if test="${status==cadreStatus.key}">active</c:if>">
                    <a href="?status=${cadreStatus.key}"><i class="fa fa-flag"></i> ${cadreStatus.value}</a>
                </li>
                </c:forEach>

            </ul>

            <div class="tab-content">
                <div id="home4" class="tab-pane in active rownumbers">
                    <div class="jqgrid-vertical-offset buttons">
                        <c:if test="${status==CADRE_STATUS_LEAVE||status==CADRE_STATUS_LEADER_LEAVE}">
                        <button class="jqBatchBtn btn btn-warning btn-sm"
                                data-title="重新任用"
                                data-msg="确定任用这{0}个干部吗？（移动到考察对象中）"
                                data-url="${ctx}/cadre_assign">
                            <i class="fa fa-reply"></i> 重新任用
                        </button>
                        </c:if>
                        <shiro:hasPermission name="cadre:edit">
                            <a class="popupBtn btn btn-info btn-sm btn-success"
                               data-url="${ctx}/cadre_au?status=${status}"><i class="fa fa-plus"></i>
                                <c:if test="${status==CADRE_STATUS_TEMP}">提任干部</c:if>
                                <c:if test="${status==CADRE_STATUS_NOW}">添加现任干部</c:if>
                                <c:if test="${status==CADRE_STATUS_LEAVE}">添加离任中层干部</c:if>
                                <c:if test="${status==CADRE_STATUS_LEADER_LEAVE}">添加离任校领导干部</c:if>
                            </a>
                        </shiro:hasPermission>

                        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                data-url="${ctx}/cadre_au"
                                data-querystr="&status=${status}">
                            <i class="fa fa-edit"></i> 修改信息
                        </button>

                        <c:if test="${status==CADRE_STATUS_TEMP}">
                            <button class="jqOpenViewBtn btn btn-success btn-sm"
                                    data-url="${ctx}/cadre_temp_pass_au">
                                <i class="fa fa-edit"></i> 通过常委会任命
                            </button>
                        </c:if>

                        <c:if test="${status==CADRE_STATUS_NOW}">
                            <button class="jqOpenViewBtn btn btn-success btn-sm"
                                    data-width="700"
                                    data-url="${ctx}/cadre_leave" data-querystr="&status=${CADRE_STATUS_LEAVE}">
                                <i class="fa fa-edit"></i> 离任
                            </button>
                        </c:if>
                        <c:if test="${status==CADRE_STATUS_NOW}">
                            <button class="jqOpenViewBtn btn btn-warning btn-sm"
                                    data-url="${ctx}/cadre_additional_post" data-rel="tooltip" data-placement="bottom"
                                    title="添加职务——仅用于因私出国（境）审批人身份设定">
                                <i class="fa fa-plus"></i> 因私出国境兼审单位
                            </button>
                        </c:if>
                        <a class="popupBtn btn btn-primary btn-sm tooltip-success"
                           data-url="${ctx}/cadre_import?status=${status}"
                           data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i> 导入</a>
                        <a class="jqExportBtn btn btn-success btn-sm"
                           data-rel="tooltip" data-placement="bottom" title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i> 导出</a>
                        <shiro:hasPermission name="cadre:del">
                            <a class="jqDelBtn btn btn-danger btn-sm"><i class="fa fa-trash"></i> 删除</a>
                        </shiro:hasPermission>
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
                                                <label>姓名</label>
                                                    <div class="input-group">
                                                        <input type="hidden" name="status" value="${status}">
                                                        <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                                                                name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                            <option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
                                                        </select>
                                                    </div>
                                            </div>
                                            <div class="form-group">
                                                <label>行政级别</label>
                                                    <select data-rel="select2" name="typeId" data-placeholder="请选择行政级别">
                                                        <option></option>
                                                        <jsp:include page="/metaTypes?__code=mc_admin_level"/>
                                                    </select>
                                                    <script type="text/javascript">
                                                        $("#searchForm select[name=typeId]").val(${param.typeId});
                                                    </script>
                                            </div>
                                            <div class="form-group">
                                                <label>职务属性</label>
                                                    <select data-rel="select2" name="postId" data-placeholder="请选择职务属性">
                                                        <option></option>
                                                        <jsp:include page="/metaTypes?__code=mc_post"/>
                                                    </select>
                                                    <script type="text/javascript">
                                                        $("#searchForm select[name=postId]").val(${param.postId});
                                                    </script>
                                            </div>
                                            <div class="form-group">
                                                <label>单位及职务</label>
                                                    <input class="form-control search-query" name="title" type="text" value="${param.title}"
                                                           placeholder="请输入单位及职务">
                                            </div>
                                    <div class="clearfix form-actions center">
                                        <a class="jqSearchBtn btn btn-default btn-sm" ><i class="fa fa-search"></i> 查找</a>

                                        <c:if test="${_query || not empty param.sort}">&nbsp;
                                            <button type="button" class="resetBtn btn btn-warning btn-sm" data-querystr="status=${status}">
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

                </div></div></div>
                </div>
        </div>
        <div id="item-content">

        </div>
    </div>
</div>
<script type="text/template" id="sort_tpl">
<a href="#" class="jqOrderBtn" data-id="{{=id}}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
<input type="text" value="1" class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
<a href="#" class="jqOrderBtn" data-id="{{=id}}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>
</script>
<script>

    <c:if test="${status==CADRE_STATUS_NOW}">
    $("#jqGrid").jqGrid({
        //forceFit:true,
        rownumbers: true,
        url: '${ctx}/cadre_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '工作证号', name: 'user.code', width: 100,frozen:true },
            { label: '姓名', name: 'user.realname', width: 120, formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?id={0}">{1}</a>'
                        .format(rowObject.id, cellvalue);
            },frozen:true  },
            { label:'排序', width: 80, index:'sort', formatter:function(cellvalue, options, rowObject){
                return _.template($("#sort_tpl").html().NoMultiSpace())({id:rowObject.id})
            },frozen:true },
            { label: '部门属性', name: 'unit.unitType.name', width: 150},
            { label: '所在单位', name: 'unit.name', width: 200 },
            { label: '现任职务', name: 'post',  align:'left',width: 350 },
            { label: '所在单位及职务', name: 'title', align:'left', width: 350 },
            { label: '行政级别', name: 'typeId',formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '';
                return _cMap.adminLevelMap[cellvalue].name;
            } },
            { label: '职务属性', name: 'postId', width: 150,formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '';
                return _cMap.postMap[cellvalue].name;
            }  },
            { label: '是否正职', name: 'mainCadrePost.postId', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return _cMap.postMap[cellvalue].boolAttr ? "是" : "否"
            } },
            { label: '性别', name: 'gender', width: 50, formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '';
                return _cMap.GENDER_MAP[cellvalue];
            }  },
            { label: '民族', name: 'nation', width: 60},
            { label: '籍贯', name: 'nativePlace', width: 120},
            { label: '身份证号', name: 'idcard', width: 150 },
            { label: '出生时间', name: 'birth',formatter:'date',formatoptions: {newformat:'Y-m-d'} },
            { label: '年龄', name: 'birth', width: 50,
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '';
                    var month = MonthDiff(cellvalue, new Date().format("yyyy-MM-dd"));
                    var year = Math.floor(month / 12);
                    return year;
                } },
            { label: '党派', name: 'isDp', width: 80,formatter:function(cellvalue, options, rowObject){

                if(!rowObject.isDp && rowObject.growTime!=undefined) return "中共党员";
                if(rowObject.isDp) return _metaTypeMap[rowObject.dpTypeId];
                return "";
            }},
            { label: '党派加入时间', name: 'growTime', width: 120,formatter:function(cellvalue, options, rowObject){

                if(rowObject.isDp) return rowObject.dpAddTime.substr(0,10);
                if(rowObject.growTime!=undefined) return rowObject.growTime.substr(0,10);
                return ""
            }},
            { label: '到校时间', name: 'arriveTime',formatter:'date',formatoptions: {newformat:'Y-m-d'} },
            { label: '最高学历', name: 'eduId', formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '';
                return _metaTypeMap[cellvalue]
            }},
            { label: '最高学位', name: 'degree'},
            { label: '毕业时间', name: 'finishTime',formatter:'date',formatoptions: {newformat:'Y.m'}},
            { label: '学习方式', name: 'learnStyle', formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '';
                return _metaTypeMap[cellvalue]
            }},
            { label: '毕业学校、学院', name: 'school', width: 150},
            { label: '学校类型', name: 'schoolType', formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '';
                return _cMap.CADRE_SCHOOL_TYPE_MAP[cellvalue]
            }},
            { label: '所学专业', name: 'major'},

            { label: '岗位类别', name: 'postClass'},
            { label: '专业技术职务', name: 'proPost', width: 120},
            { label: '专技岗位等级', name: 'proPostLevel', width: 150},
            { label: '管理岗位等级', name: 'manageLevel', width: 150},
            {
                label: '现职务任命文件',
                width: 150,
                name: 'mainCadrePost.dispatchCadreRelateBean.first',
                formatter: function (cellvalue, options, rowObject) {
                    if (!cellvalue || cellvalue.id == undefined) return '';
                    var dispatchCode = cellvalue.dispatchCode;
                    if (cellvalue.fileName && cellvalue.fileName != '')
                        return '<a href="javascript:void(0)" onclick="swf_preview({0}, \'file\')">{1}</a>'.format(cellvalue.id, dispatchCode);
                    else return dispatchCode;
                }
            },
            {
                label: '任现职时间',
                name: 'mainCadrePost.dispatchCadreRelateBean.last.workTime',
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },
            {
                label: '现职务始任时间',
                width: 150,
                name: 'mainCadrePost.dispatchCadreRelateBean.first.workTime',
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },
            {
                label: '任现职务年限',
                width: 120,
                name: 'mainCadrePost.dispatchCadreRelateBean.first.workTime',
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '';
                    var month = MonthDiff(cellvalue, new Date().format("yyyy-MM-dd"));
                    var year = Math.floor(month / 12);
                    return year == 0 ? "未满一年" : year;
                }
            },
            {
                label: '职级始任日期',
                width: 120,
                name: 'presentAdminLevel.startDispatch.workTime',
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },
            {
                label: '任职级年限',
                width: 120,
                name: 'workYear',
                formatter: function (cellvalue, options, rowObject) {
                    //console.log(rowObject.endDispatch)
                    if (rowObject.presentAdminLevel==undefined || rowObject.presentAdminLevel.startDispatch==undefined) return '';

                    var end;
                    if( rowObject.presentAdminLevel.endDispatch!=undefined)
                        end = rowObject.presentAdminLevel.endDispatch.workTime;
                    if(rowObject.presentAdminLevel.adminLevelId == rowObject.mainCadrePost.adminLevelId)
                        end = new Date().format("yyyy-MM-dd");
                    if (rowObject.presentAdminLevel.startDispatch.workTime==undefined|| end==undefined) return '';

                    var month = MonthDiff(rowObject.presentAdminLevel.startDispatch.workTime, end);
                    var year = Math.floor(month / 12);
                    return year == 0 ? "未满一年" : year;
                }
            },
            {
                label: '是否双肩挑', name: 'mainCadrePost.isDouble', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return cellvalue ? "是" : "否";
            }
            },
            {
                label: '双肩挑单位', name: 'mainCadrePost.doubleUnitId', width: 150, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return _cMap.unitMap[cellvalue].name
            }
            },
            { label: '联系方式', name: 'mobile' },
            /*{ label: '办公电话', name: 'officePhone' },
            { label: '家庭电话', name: 'homePhone' },*/
            { label: '电子邮箱', name: 'email', width: 150 },
            { label: '所属党组织', name: 'partyId',align:'left', width: 550, formatter:function(cellvalue, options, rowObject){
                if(rowObject.partyId==undefined) return '';
                var party = _cMap.partyMap[rowObject.partyId].name;
                if(rowObject.branchId!=undefined)
                    var branch =_cMap.branchMap[rowObject.branchId].name;
                return party + (($.trim(branch)=='')?'':'-'+branch);
            } },
            { label: '因私出国境兼审单位', width: 150 , name: 'additional', formatter:function(cellvalue, options, rowObject){
                var cadreAdditionalPosts = rowObject.cadreAdditionalPosts;
                if(cadreAdditionalPosts.length==0) return '';
                return '<button class="popupBtn btn btn-xs btn-warning"' +
                        'data-url="${ctx}/cadre_additional_post?id={0}"><i class="fa fa-search"></i> 查看</button>'
                                .format(rowObject.id);
            } },
            { label: '备注', name: 'remark', width: 150 }
        ]}).jqGrid("setFrozenColumns").on("initGrid",function(){
        $('[data-rel="tooltip"]').tooltip();
    });
    </c:if>

    <c:if test="${status!=CADRE_STATUS_NOW}">
    $("#jqGrid").jqGrid({
        //forceFit:true,
        rownumbers: true,
        url: '${ctx}/cadre_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '工作证号', name: 'user.code', width: 100,frozen:true },
            { label: '姓名', name: 'user.realname', width: 120, formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?id={0}">{1}</a>'
                        .format(rowObject.id, cellvalue);
            },frozen:true  },
            { label:'排序', width: 80, index:'sort', formatter:function(cellvalue, options, rowObject){
                return _.template($("#sort_tpl").html().NoMultiSpace())({id:rowObject.id})
            },frozen:true },
            { label: '${status==CADRE_STATUS_TEMP?"现":"原"}所在单位', name: 'unit.name', width: 200 },
            { label: '${status==CADRE_STATUS_TEMP?"现任":"原"}职务', name: 'post', width: 350 },
            { label: '${status==CADRE_STATUS_TEMP?"现":"离任后"}所在单位及职务', name: 'title', width: 350 },
            <c:if test="${status==CADRE_STATUS_LEAVE||status==CADRE_STATUS_LEADER_LEAVE}">
            { label:'离任文件',  name: 'dispatch', width: 180,formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '';
                return '<a href="javascript:void(0)" onclick="swf_preview({0}, \'file\')">{1}</a>'.format(cellvalue.id, cellvalue.dispatchCode);
            },frozen:true },
            {label: '离任日期', name: 'dispatch.workTime', formatter: 'date', formatoptions: {newformat: 'Y.m.d'}},
                </c:if>
            /*{ label: '${status==CADRE_STATUS_TEMP?"现":""}行政级别', name: 'presentAdminLevelType.name' },*/
            { label: '${status==CADRE_STATUS_TEMP?"现":"原"}行政级别', name: 'typeId',formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '';
                return _cMap.adminLevelMap[cellvalue].name;
            } },
            { label: '${status==CADRE_STATUS_TEMP?"现":"原"}职务属性', name: 'postType.name', width: 150 },
            { label: '手机号', name: 'mobile' },
            { label: '办公电话', name: 'officePhone' },
            { label: '家庭电话', name: 'homePhone' },
            { label: '电子邮箱', name: 'email', width: 150 },
            { label: '备注', name: 'remark', width: 150 }
        ]}).jqGrid("setFrozenColumns").on("initGrid",function(){
        $('[data-rel="tooltip"]').tooltip();
    });
    </c:if>
    $(window).triggerHandler('resize.jqGrid');
    _initNavGrid("jqGrid", "jqGridPager");
   /* function _pass(){

        var grid = $("#jqGrid");
        var id  = grid.getGridParam("selrow");
        if(!id){
            SysMsg.warning("请选择行", "提示");
            return ;
        }
        var data = grid.getRowData(id);

        bootbox.confirm("姓名：{0}，工号：{1}，确定通过常委会任命吗？".format(data['user.realname'], data['user.code']), function (result) {
            if (result) {
                $.post("${ctx}/cadre_pass", {id: id}, function (ret) {
                    if (ret.success) {
                        SysMsg.success('操作成功。', '成功',function(){
                            location.href='${ctx}/cadre?status=1'
                        });
                    }
                });
            }
        });
    }*/

    function openView(id){
        $("#body-content").hide();
        $("#item-content").load("${ctx}/cadre_view?id="+id).show();
    }
    function closeView(){
        $("#body-content").show();
        $("#item-content").hide();
    }

    /*$(".tabbable li a").click(function(){
        $this = $(this);
        $(".tabbable li").removeClass("active");
        $this.closest("li").addClass("active");
        $(".myTableDiv #searchForm input[name=status]").val($this.data("status"));
        $(".myTableDiv .searchBtn").click();
    });*/

    $('[data-rel="select2"]').select2();


        register_user_select($('#searchForm select[name=cadreId]'));
</script>