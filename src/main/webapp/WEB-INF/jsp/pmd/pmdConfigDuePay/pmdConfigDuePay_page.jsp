<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pmd/constants.jsp"%>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/pmd/pmdConfigDuePay"
             data-url-export="${ctx}/pmd/pmdConfigDuePay_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId ||not empty param.isOnlinePay ||not empty param.hasReset ||not empty param.configMemberType
            ||not empty param.configMemberTypeId || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">

                <button id="selectMemberTypeBtn" class="jqOpenViewBatchBtn btn btn-primary btn-sm"
                        data-url="${ctx}/pmd/pmdMember_selectMemberType?isUser=1"
                        data-grid-id="#jqGrid">
                    <i class="fa fa-check-square-o"></i> 选择党员类别
                </button>
                <button id="helpSetSalaryBtn" class="jqOpenViewBtn btn btn-warning btn-sm"
                        data-width="600"
                        data-url="${ctx}/user/pmd/pmdMember_setSalary"
                        data-grid-id="#jqGrid"
                        data-querystr="&isSelf=0"
                        data-id-name="userId">
                    <i class="fa fa-rmb"></i> 修改党费应交额</button>
                <button id="showSalaryBtn" class="jqOpenViewBtn btn btn-success btn-sm"
                        data-width="600"
                        data-url="${ctx}/user/pmd/pmdMember_setSalary"
                        data-grid-id="#jqGrid"
                        data-querystr="&isSelf=0&view=1"
                        data-id-name="userId">
                    <i class="fa fa-rmb"></i> 查看党费应交额</button>
                <button class="jqOpenViewBtn btn btn-info btn-sm"
                        data-url="${ctx}/sysApprovalLog"
                        data-width="850"
                        data-querystr="&displayType=1&hideStatus=1&type=<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_USER%>">
                    <i class="fa fa-history"></i> 操作记录
                </button>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

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
                                <select data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/member_selects?noAuth=1"
                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>所属${_p_partyName}</label>

                                    <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                                            name="partyId" data-placeholder="请选择">
                                        <option value="${party.id}">${party.name}</option>
                                    </select>

                            </div>
                            <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                <label>所属党支部</label>

                                    <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects"
                                            name="branchId" data-placeholder="请选择党支部">
                                        <option value="${branch.id}">${branch.name}</option>
                                    </select>

                            </div>
                            <script>
                                $.register.party_branch_select($("#searchForm"), "branchDiv",
                                    '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}" );
                            </script>
                            <div class="form-group">
                                <label>党员类别</label>
                                <select data-rel="select2" name="configMemberType"

                                        data-placeholder="请选择">
                                    <option></option>
                                    <c:forEach items="${PMD_MEMBER_TYPE_MAP}" var="_type">
                                        <option value="${_type.key}">${_type.value}</option>
                                    </c:forEach>
                                </select>
                                <script type="text/javascript">
                                    $("#searchForm select[name=configMemberType]").val(${param.configMemberType});
                                </script>
                            </div>
                            <div class="form-group">
                                <label>党员分类别</label>
                                <select data-rel="select2" name="configMemberTypeId"
                                        data-placeholder="请选择">
                                    <option></option>
                                </select>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm">
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
        <div id="body-content-view"></div>
    </div>
</div>
<script>

    var typeMap = ${cm:toJSONObject(typeMap)};
    $("select[name=configMemberType]").change(function () {
        var $this = $(this);
        var configMemberType = $this.val();
        if ($.trim(configMemberType) != '') {
            var types = typeMap[configMemberType];
            //console.log(types);
            var $configMemberTypeId = $("#searchForm select[name=configMemberTypeId]")
                    .empty().prepend("<option></option>").select2();
             $.each(types, function (i, type) {
                var selected = (type.id == '${param.configMemberTypeId}');
                $configMemberTypeId.append(new Option(type.name, type.id, selected, selected))
            })
            $configMemberTypeId.trigger('change');
        }
    }).change();

    $.register.user_select($('#searchForm select[name=userId]'));
    $("#jqGrid").jqGrid({
        url: '${ctx}/pmd/pmdConfigDuePay_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'user.code', width: 120, frozen: true},
            {label: '姓名', name: 'user.realname', frozen: true, formatter: function (cellvalue, options, rowObject){
                 return $.member(rowObject.user.id, cellvalue);
            }},
            { label: '所属${_p_partyName}',name: 'partyId',width: 300,align: 'left',formatter: function (cellvalue, options, rowObject) {
                    return cellvalue==undefined?"":_cMap.partyMap[cellvalue].name;
                }},
            { label: '所在党支部',name: 'branchId',width: 250,align: 'left',formatter: function (cellvalue, options, rowObject) {
                    return cellvalue==undefined?"":_cMap.branchMap[cellvalue].name;
                }},
            {
                label: '党员类别', name: 'type', formatter: function (cellvalue, options, rowObject) {
                return _cMap.PMD_MEMBER_TYPE_MAP[rowObject.configMemberType];
            }, width: 120
            },
            {label: '党员分类别', name: 'pmdConfigMemberType.name', width: 220},
            {label: '缴纳额度', name: 'duePay'},
            /*{
                label: '工资项', name: 'salary', width:400, formatter: function (cellvalue, options, rowObject) {
                if (rowObject.configMemberType != '${PMD_MEMBER_TYPE_ONJOB}') return '--'
                return $.trim(cellvalue)
            }
            },*/
            {hidden:true, name:'userId', key:true},
            {hidden: true, name: 'configMemberType'},
            {hidden: true, name: 'formulaType', formatter: function (cellvalue, options, rowObject) {
                    if(rowObject.pmdConfigMemberType==undefined
                        ||rowObject.pmdConfigMemberType.pmdNorm==undefined) return -1;

                    return rowObject.pmdConfigMemberType.pmdNorm.formulaType;
            }}
        ],
        onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id, id, status);
            _onSelectRow(this)
        },
        onSelectAll: function (aRowids, status) {
            saveJqgridSelected("#" + this.id);
            _onSelectRow(this)
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    function _onSelectRow(grid) {
        var ids = $(grid).getGridParam("selarrrow");

        if (ids.length > 1) {
            $("#helpSetSalaryBtn, #showSalaryBtn").prop("disabled", true);
        } else if (ids.length == 1) {
            var rowData = $(grid).getRowData(ids[0]);

            $("#helpSetSalaryBtn, #showSalaryBtn").prop("disabled",
                (rowData.formulaType!=${PMD_FORMULA_TYPE_ONJOB}
                    &&rowData.formulaType!=${PMD_FORMULA_TYPE_EXTERNAL}
                    &&rowData.formulaType!=${PMD_FORMULA_TYPE_RETIRE}));

        }

        var configMemberType; // 选择的党员类别（设定党员分类别时的url参数，此时要求是同一类别）
        var selectMemberTypeBtn = true;
        $.each(ids, function(i, id){
            var rowData = $(grid).getRowData(id);

            if(configMemberType != -1){
                if(configMemberType!=undefined && rowData.configMemberType != configMemberType){
                    configMemberType = -1; // 选择的党员不是同一类别
                }else {
                    configMemberType = rowData.configMemberType;
                }
            }
        })

        $("#selectMemberTypeBtn").each(function(){
            var querystr = "&auth=1&configMemberType=" + configMemberType;
            $(this).data("querystr", querystr);
        });

        $("#selectMemberTypeBtn").prop("disabled", !selectMemberTypeBtn || configMemberType==-1);
    }
</script>