<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/passport_au"
             data-url-page="${ctx}/passport_page"
             data-url-del="${ctx}/passport_del"
             data-url-bd="${ctx}/passport_batchDel"
             data-url-ba="${ctx}/passport_abolish"
             data-url-co="${ctx}/passport_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.cadreId ||not empty param.classId
                ||not empty param.safeBoxId ||not empty param.type || not empty param.code }"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <jsp:include page="menu.jsp"/>

                    <div class="buttons pull-right" style="top: -3px; right:10px; position: relative">
                        <c:if test="${status==PASSPORT_TYPE_KEEP}">
                                <shiro:hasPermission name="passport:edit">
                                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加证件信息</a>
                                </shiro:hasPermission>
                                <a class="importBtn btn btn-success btn-sm tooltip-success"
                                   data-rel="tooltip" data-placement="top" title="导入"><i class="fa fa-upload"></i> 导入</a>
                                    <a class="batchAbolishBtn btn btn-warning btn-sm">
                                        <i class="fa fa-times"></i> 作废
                                    </a>
                                    <shiro:hasPermission name="passport:del">
                                        <a class="jqDelBtn btn btn-danger btn-sm"><i class="fa fa-times"></i> 删除</a>
                                    </shiro:hasPermission>
                        </c:if>
                        <c:if test="${status==PASSPORT_TYPE_CANCEL}">
                            <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                    data-url="${ctx}/shortMsg_view" data-querystr="&type=passport">
                                <i class="fa fa-info-circle"></i> 短信通知
                            </button>
                        </c:if>
                        <c:if test="${status==PASSPORT_TYPE_CANCEL}">
                            <a class="jqOpenViewBtn btn btn-success btn-sm"
                               data-open-by="page" data-url="${ctx}/passport_cancel">
                                <i class="fa fa-check-circle-o"></i> 确认单
                            </a>
                        </c:if>

                        <c:if test="${status==3}">
                                <shiro:hasPermission name="passport:edit">
                                    <a class="addLostBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加丢失证件</a>
                                </shiro:hasPermission>
                        </c:if>
                        <c:if test="${status==5}">
                                <shiro:hasPermission name="safeBox:edit">
                                    <a class="btn btn-success btn-sm" onclick="openView_safeBox()"><i class="fa fa-plus"></i> 保险柜管理</a>
                                </shiro:hasPermission>
                        </c:if>
                        <shiro:hasPermission name="passport:edit">
                            <button class="jqEditBtn btn btn-primary btn-sm">
                                <i class="fa fa-edit"></i> 修改信息
                            </button>
                        </shiro:hasPermission>
                    </div>
                </ul>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
            <div class="widget-up-jqgrid widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
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
                        <mytag:sort-form css="form-horizontal " id="searchForm">
                            <div class="row">
                                <div class="col-xs-4">
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">姓名</label>
                                        <div class="col-xs-6">
                                            <div class="input-group">
                                                <input type="hidden" name="status" value="${status}">
                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                                                        name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${cadre.id}">${sysUser.realname}</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">证件名称</label>
                                        <div class="col-xs-6">
                                            <select data-rel="select2" name="classId" data-placeholder="请选择证件名称">
                                                <option></option>
                                                <c:import url="/metaTypes?__code=mc_passport_type"/>
                                            </select>
                                            <script type="text/javascript">
                                                $("#searchForm select[name=classId]").val(${param.classId});
                                            </script>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-4">
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">保险柜</label>
                                        <div class="col-xs-6">
                                            <select data-rel="select2" name="safeBoxId" data-placeholder="请选择保险柜">
                                                <option></option>
                                                <c:forEach items="${safeBoxMap}" var="safeBox">
                                                    <option value="${safeBox.key}">${safeBox.value.code}</option>
                                                </c:forEach>
                                            </select>
                                            <script type="text/javascript">
                                                $("#searchForm select[name=safeBoxId]").val(${param.safeBoxId});
                                            </script>
                                        </div>
                                    </div>

                                </div>
                                <div class="col-xs-4">
                                    <div class="form-group">
                                        <label class="col-xs-4 control-label">证件号码</label>
                                        <div class="col-xs-6">
                                            <input class="form-control search-query" name="code" type="text" value="${param.code}"
                                                   placeholder="请输入证件号码">
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="clearfix form-actions center">
                                <a class="searchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query || not empty param.sort}">&nbsp;
                                    <button type="button" class="resetBtn btn btn-warning btn-sm" data-querystr="status=${status}">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </mytag:sort-form>
                    </div>
                </div>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="table-striped"> </table>
            <div id="jqGridPager"> </div>
    </div>
                </div></div></div>
    <div id="item-content">
    </div>
    </div>
</div>

<script>
    $("#jqGrid").jqGrid({
        //forceFit:true,
        url: '${ctx}/passport_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '工作证号', align:'center', name: 'user.code', width: 100 ,frozen:true},
            { label: '姓名',align:'center', name: 'user.realname',resizable:false, width: 75, formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/member_view?userId={0}">{1}</a>'
                        .format(rowObject.user.id, cellvalue);
    } ,frozen:true },
            { label: '所在单位及职务',  name: 'cadre.title', width: 250 },
            { label: '职位属性', align:'center', name: 'cadre.postType.name', width: 200 },
            { label: '证件名称', align:'center', name: 'passportClass.name', width: 200 },
            { label: '证件号码', align:'center', name: 'code', width: 100 },
            { label:'发证机关', align:'center',name: 'authority', width: 180},
            { label:'发证日期', align:'center', name: 'issueDate', width: 100 },
            { label:'有效期', align:'center', name: 'expiryDate', width: 100 },
            <c:if test="${status!=PASSPORT_TYPE_LOST}">
            { label:'集中保管日期', align:'center', name: 'keepDate', width: 120 },
            { label:'存放保险柜编号', align:'center', name: 'safeBox.code', width: 130 },
            { label:'是否借出', align:'center', name: 'isLent', width: 100, formatter:function(cellvalue){
                return cellvalue?"借出":"-";
            } },
            </c:if>
            <c:if test="${status==4 || status==5}">
            { label:'类型', align:'center', name: 'passportType', width: 100 },
            </c:if>
            <c:if test="${status==PASSPORT_TYPE_LOST}">
            { label:'丢失证明', align:'center', width: 100, formatter:function(cellvalue, options, rowObject){
                return '<a href="${ctx}/passport_lostProof_download?id={0}" target="_blank">丢失证明</a>'.format(rowObject.id);
            } },
            </c:if>
            <c:if test="${status==PASSPORT_TYPE_CANCEL}">
            { label:'取消集中保管原因', align:'center', name: 'cancelType', width: 140 },
            { label:'状态', align:'center', name: 'cancelConfirm', width: 100, formatter:function(cellvalue){
                return cellvalue?"已确认":"未确认";
            } }
            </c:if>
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    function openView_safeBox(pageNo){
        pageNo = pageNo||1;
        loadModal( "${ctx}/safeBox_page?pageNo="+pageNo, '400');
    }

    $(".importBtn").click(function(){
        loadModal("${ctx}/passport_import");
    });

    $(".addLostBtn").click(function(){
        loadModal("${ctx}/passport_au?type=${PASSPORT_TYPE_LOST}");
    });
    $(".cancelConfirmBtn").click(function(){
        loadModal("${ctx}/passport_cancel_confirm?id="+$(this).data("id"));
    });

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_user_select($('[data-rel="select2-ajax"]'));
</script>