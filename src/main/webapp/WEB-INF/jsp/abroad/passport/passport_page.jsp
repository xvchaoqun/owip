<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/abroad/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/abroad/passport_au?type=1"
             data-url-page="${ctx}/abroad/passport"
             data-url-export="${ctx}/abroad/passport_data"
             data-url-co="${ctx}/abroad/passport_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.unitId ||not empty param.cadreId ||not empty param.classId
                ||not empty param.safeBoxId ||not empty param.type || not empty param.code|| not empty param.isLent }"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <jsp:include page="menu.jsp"/>
                </ul>

                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${status==ABROAD_PASSPORT_TYPE_KEEP}">
                                <shiro:hasPermission name="passport:edit">
                                    <a class="editBtn btn btn-success btn-sm"><i class="fa fa-plus"></i> 添加证件</a>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="passport:edit">
                                    <button class="jqEditBtn btn btn-primary btn-sm">
                                        <i class="fa fa-edit"></i> 修改信息
                                    </button>
                                </shiro:hasPermission>
                                <a class="jqOpenViewBtn btn btn-warning btn-sm"
                                   data-width="500" data-url="${ctx}/abroad/passport_abolish">
                                    <i class="fa fa-recycle"></i> 取消集中管理
                                </a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/abroad/passport_lost" >
                                    <i class="fa fa-times"></i> 丢失
                                </a>
                                <button class="jqOpenViewBtn btn btn-success btn-sm"
                                        data-url="${ctx}/abroad/shortMsg_view" data-querystr="&type=passportInfo">
                                    <i class="fa fa-info-circle"></i> 发送证件信息
                                </button>
                                <button class="jqOpenViewBtn btn btn-info btn-sm" data-open-by="page"
                                        data-url="${ctx}/abroad/passport_uploadPic">
                                    <i class="fa fa-upload"></i> 上传证件首页
                                </button>
                                <shiro:hasPermission name="passport:del">
                                    <a class="jqBatchBtn btn btn-danger btn-sm"
                                       data-url="${ctx}/abroad/passport_batchDel" data-title="证件删除"
                                       data-msg="确定删除这{0}个证件吗？"><i class="fa fa-trash"></i> 删除</a>
                                </shiro:hasPermission>
                                <a class="importBtn btn btn-info btn-sm tooltip-info"
                                   data-rel="tooltip" data-placement="top" title="导入"><i class="fa fa-upload"></i> 批量导入</a>
                            </c:if>

                            <c:if test="${status==ABROAD_PASSPORT_TYPE_CANCEL || status==4}">
                                <button data-url="${ctx}/abroad/passport_unabolish"
                                        data-title="返回集中管理"
                                        data-msg="确定将该证件返回集中管理？"
                                        class="jqItemBtn btn btn-info btn-sm">
                                    <i class="fa fa-reply"></i> 返回集中管理
                                </button>
                                <shiro:hasPermission name="passport:edit">
                                    <button class="jqEditBtn btn btn-primary btn-sm">
                                        <i class="fa fa-edit"></i> 修改信息
                                    </button>
                                </shiro:hasPermission>
                            </c:if>
                            <c:if test="${status==ABROAD_PASSPORT_TYPE_CANCEL}">

                                <button class="jqOpenViewBtn btn btn-warning btn-sm"
                                        data-url="${ctx}/abroad/shortMsg_view" data-querystr="&type=passport">
                                    <i class="fa fa-info-circle"></i> 短信通知
                                </button>
                                <a class="jqOpenViewBtn btn btn-success btn-sm"
                                   data-open-by="page" data-url="${ctx}/abroad/passport_cancel">
                                    <i class="fa fa-check-circle-o"></i> 确认单
                                </a>
                            </c:if>
                            <c:if test="${status==4}">
                                <a class="jqOpenViewBtn btn btn-success btn-sm"
                                   data-open-by="page" data-url="${ctx}/abroad/passport_cancel_view">
                                    <i class="fa fa-check-circle-o"></i> 取消集中管理证明
                                </a>
                            </c:if>

                            <c:if test="${status==ABROAD_PASSPORT_TYPE_LOST}">
                                <shiro:hasPermission name="passport:edit">
                                    <a class="addLostBtn btn btn-primary btn-sm"><i class="fa fa-plus"></i> 添加丢失证件</a>
                                </shiro:hasPermission>
                                <a class="jqOpenViewBtn btn btn-success btn-sm"
                                   data-open-by="page" data-url="${ctx}/abroad/passport_lost_view">
                                    <i class="fa fa-search"></i> 丢失证明
                                </a>
                                <shiro:hasPermission name="passport:edit">
                                    <button class="jqEditBtn btn btn-primary btn-sm">
                                        <i class="fa fa-edit"></i> 修改信息
                                    </button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="passport:del">
                                    <button class="jqBatchBtn btn btn-danger btn-sm"
                                       data-url="${ctx}/abroad/passport_batchDel" data-title="证件删除"
                                       data-msg="确定删除这{0}个证件吗？"><i class="fa fa-trash"></i> 删除</button>
                                </shiro:hasPermission>
                            </c:if>
                            <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i
                                    class="fa fa-download"></i> 导出</a>
                            <a class="jqOpenViewBtn btn btn-info btn-sm"
                               data-open-by="page" data-url="${ctx}/abroad/passport_useLogs">
                                <i class="fa fa-history"></i> 使用记录
                            </a>
                            <c:if test="${status==ABROAD_PASSPORT_TYPE_LOST}">
                            <a class="jqOpenViewBtn btn btn-warning btn-sm"
                               data-url="${ctx}/abroad/passport_au" data-querystr="&op=back">
                                <i class="fa fa-search"></i> 证件找回
                            </a>
                            </c:if>

                            <c:if test="${status==ABROAD_PASSPORT_TYPE_KEEP}">
                                <button disabled id="hasFindBtn" class="jqOpenViewBtn btn btn-warning btn-sm"
                                        data-url="${ctx}/abroad/passport_remark" data-open-by="page">
                                    <i class="fa fa-search"></i> 丢失情况
                                </button>
                            </c:if>
                        </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>
                    <div class="widget-toolbar">
                        <a href="javascript:;" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm">
                                    <div class="form-group">
                                        <label>所属单位</label>
                                        <select  class="form-control" name="unitId" data-rel="select2" data-placeholder="请选择所属单位">
                                            <option></option>
                                            <c:forEach items="${unitMap}" var="unit">
                                                <option value="${unit.key}">${unit.value.name}</option>
                                            </c:forEach>
                                        </select>
                                        <script type="text/javascript">
                                            $("#searchForm select[name=unitId]").val(${param.unitId});
                                        </script>
                                    </div>
                                    <div class="form-group">
                                        <label>姓名</label>
                                            <div class="input-group">
                                                <input type="hidden" name="status" value="${status}">
                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects?type=0"
                                                        name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
                                                </select>
                                            </div>
                                    </div>

                                    <div class="form-group">
                                        <label>证件名称</label>
                                            <select data-rel="select2" name="classId" data-placeholder="请选择证件名称">
                                                <option></option>
                                                <c:import url="/metaTypes?__code=mc_passport_type"/>
                                            </select>
                                            <script type="text/javascript">
                                                $("#searchForm select[name=classId]").val(${param.classId});
                                            </script>
                                    </div>
                                    <div class="form-group">
                                        <label>保险柜</label>
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
                                    <div class="form-group">
                                        <label>证件号码</label>
                                            <input class="form-control search-query" name="code" type="text" value="${param.code}"
                                                   placeholder="请输入证件号码">
                                    </div>
                                    <c:if test="${status!=ABROAD_PASSPORT_TYPE_LOST && status!=4}">
                                    <div class="form-group">
                                        <label>是否借出</label>
                                        <select name="isLent" data-width="100" data-rel="select2" data-placeholder="请选择">
                                            <option></option>
                                            <option value="1">是</option>
                                            <option value="0">否</option>
                                        </select>
                                        <script>
                                            $("#searchForm select[name=isLent]").val('${param.isLent}');
                                        </script>
                                    </div>
                                        </c:if>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query || not empty param.sort}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm" data-querystr="status=${status}">
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
    </div>
                </div></div></div>
    <div id="body-content-view">
    </div>
    </div>
</div>

<script>
    $("#jqGrid").jqGrid({
        //forceFit:true,
        url: '${ctx}/abroad/passport_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '工作证号', name: 'user.code',frozen:true },
            { label: '姓名',align:'center', name: 'user.realname', width: 75, formatter:function(cellvalue, options, rowObject){
                return $.cadre(rowObject.cadre.id, cellvalue);},frozen:true  },
            { label: '所在单位及职务',  name: 'cadre.title', width: 250, align:'left' },
            { label: '职务属性', name: 'cadre.postType', width: 150, formatter:$.jgrid.formatter.MetaType},
            { label: '干部类型', name: 'cadre.status', width: 150 , formatter:function(cellvalue, options, rowObject){
               return _cMap.CADRE_STATUS_MAP[cellvalue];
            }},
            { label: '证件名称', name: 'passportClass.name', width: 200 },
            { label: '证件号码', name: 'code' },
            { label: '证件首页', name: '_pic', width: 80, formatter:function(cellvalue, options, rowObject){
                if($.trim(rowObject.pic)=='') return '-'
                return '<a class="various" title="{1}" data-path="{0}" data-fancybox-type="image" href="${ctx}/pic?path={0}">查看</a>'
                        .format(encodeURI(rowObject.pic), rowObject.code + ".jpg");
            } },
            { label:'发证机关',name: 'authority', width: 180},
            { label:'发证日期', name: 'issueDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'} },
            { label:'有效期', name: 'expiryDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'} },
            { label:'集中管理日期', name: 'keepDate', width: 120, formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '';
                else if(rowObject.type=='${ABROAD_PASSPORT_TYPE_LOST}'&&cellvalue>rowObject.lostTime) {
                    return '';
                }
                return $.date(cellvalue, "yyyy-MM-dd")
            }  },
            <c:if test="${status!=ABROAD_PASSPORT_TYPE_LOST && status!=4}">
            { label:'所在保险柜', name: 'safeBox.code', width: 130 },
            { label:'是否借出', name: 'isLent', formatter:function(cellvalue, options, rowObject){
                return cellvalue?($.trim(rowObject.refuseReturnReason)!=''?"拒不交回":"借出"):"-";
            }, title:false,cellattr: function (rowId, val, rawObject, cm, rdata) {
                //console.log(rawObject)
                if($.trim(rawObject.refuseReturnReason)!=''){
                    return 'data-tooltip="tooltip" data-container="#body-content" data-html="true" data-original-title="'+rawObject.refuseReturnReason+'"';
                } }},
            </c:if>
            <c:if test="${status==4}">
            { label:'取消集中保管日期', name: 'cancelTime', width: 140, formatter: 'date', formatoptions: {newformat: 'Y-m-d'} },
            </c:if>
            <c:if test="${status==ABROAD_PASSPORT_TYPE_LOST}">
            { label:'登记丢失日期', name: 'lostTime', width: 120, formatter: 'date', formatoptions: {newformat: 'Y-m-d'} },
            </c:if>
            <c:if test="${status==2||status==4}">
            { label:'取消集中保管原因', name: 'cancelType', width: 140, formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '';
                var ret = _cMap.ABROAD_PASSPORT_CANCEL_TYPE_MAP[cellvalue];
                if(cellvalue=='${ABROAD_PASSPORT_CANCEL_TYPE_OTHER}'){
                    if(rowObject.cancelTypeOther!=''){
                        ret = ret + ":"+  rowObject.cancelTypeOther;
                    }
                }
                return ret;
            }  },
            { label:'状态', name: 'cancelConfirm', formatter:function(cellvalue){
                if(cellvalue==undefined) return ''
                return cellvalue?"已确认":"未确认";
            } },
            </c:if>
            <c:if test="${status==4 || status==ABROAD_PASSPORT_TYPE_LOST}">
            { label:'备注', name: 'cancelRemark',align:'left', width: 350},
            </c:if>
            {hidden:true, name:'canEdit', formatter:function(cellvalue, options, rowObject) {
                var type = rowObject.type;
                var lostType = rowObject.lostType;
                //console.log(rowObject)
                return !(type=='${ABROAD_PASSPORT_TYPE_LOST}' && lostType=='${ABROAD_PASSPORT_LOST_TYPE_TRANSFER}')?1:0; // 转移的丢失证件，不可以更新
            }},{hidden:true, name:'hasFind', formatter:function(cellvalue, options, rowObject) {
                var hasFind = rowObject.hasFind;
                return hasFind?1:0;
            }}
        ],
        onSelectRow: function(id,status){
            saveJqgridSelected("#"+this.id);
            //console.log(id)
            var rowData = $(this).getRowData(id);
            $(".jqEditBtn,.jqBatchBtn").prop("disabled",rowData.canEdit==0);
            //console.log(rowData.hasFind);
            $("#hasFindBtn").prop("disabled",rowData.hasFind==0);
        }
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $('[data-tooltip="tooltip"]').tooltip({html:true});
    });
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    function openView_safeBox(pageNo){
        pageNo = pageNo||1;
        $.loadModal( "${ctx}/abroad/safeBox?pageNo="+pageNo, '400');
    }

    $(".importBtn").click(function(){
        $.loadModal("${ctx}/abroad/passport_import");
    });

    $(".addLostBtn").click(function(){
        $.loadModal("${ctx}/abroad/passport_au?type=${ABROAD_PASSPORT_TYPE_LOST}");
    });
    $(".cancelConfirmBtn").click(function(){
        $.loadModal("${ctx}/abroad/passport_cancel_confirm?id="+$(this).data("id"));
    });

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.user_select($('[data-rel="select2-ajax"]'));

    $.register.fancybox(function () {
        //console.log(this)
        this.title = '<div class="title">' + this.title + '<div class="download">【<a href="${ctx}/attach/download?path={0}&filename={1}" target="_blank">点击下载</a>】</div></div>'
                        .format($(this.element).data('path'), this.title);
    });
</script>