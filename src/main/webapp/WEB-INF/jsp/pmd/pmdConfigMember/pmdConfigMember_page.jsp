<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pmd/constants.jsp"%>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/pmd/pmdConfigMember"
             data-url-export="${ctx}/pmd/pmdConfigMember_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId ||not empty param.isOnlinePay ||not empty param.hasReset ||not empty param.configMemberType
            ||not empty param.configMemberTypeId || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <%--<shiro:hasPermission name="pmdConfigMember:edit">

                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/pmd/pmdConfigMember_updateType"
                       ><i class="fa fa-edit"></i>
                        修改党员类别</a>
                </shiro:hasPermission>--%>
                <%--<shiro:hasPermission name="pmdConfigMember:del">
                    <button data-url="${ctx}/pmd/pmdConfigMember_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}个党员？【提示：已有的缴费记录不会删除】"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>--%>
                <a class="popupBtn btn btn-success btn-sm"
                   data-width="800"
                   data-url="${ctx}/pmd/pmdConfigReset"><i
                        class="fa fa-gear"></i> 缴费基数调整</a>
                <button class="jqOpenViewBtn btn btn-info btn-sm"
                        data-open-by="page"
                        data-id-name="userId"
                        data-url="${ctx}/pmd/pmdOrder">
                    <i class="fa fa-search"></i> 历史支付订单
                </button>

                <a class="popupBtn btn btn-primary btn-sm"
                   data-url="${ctx}/pmd/pmdConfigMember_exportSalary"><i
                        class="fa fa-download"></i> 导出工资数据</a>
               <%-- <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</a>--%>
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
                                <label>缴费方式</label>
                                <select data-rel="select2" name="isOnlinePay"
                                        data-width="120"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <option value="1">线上缴费</option>
                                    <option value="0">现金缴费</option>
                                </select>
                                <script>
                                    $("#searchForm select[name=isOnlinePay]").val("${param.isOnlinePay}")
                                </script>
                            </div>
                            <div class="form-group">
                                <label>姓名</label>
                                <select data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/member_selects?noAuth=1"
                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                </select>
                            </div>
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
                            <div class="form-group">
                                <label>确认额度</label>
                                <select data-rel="select2" name="hasReset"
                                        data-width="120"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <option value="1">已确认</option>
                                    <option value="0">未确认</option>
                                </select>
                                <script>
                                    $("#searchForm select[name=hasReset]").val("${param.hasReset}")
                                </script>
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
        url: '${ctx}/pmd/pmdConfigMember_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'user.code', width: 120, frozen: true},
            {label: '姓名', name: 'user.realname', frozen: true, formatter: function (cellvalue, options, rowObject){
                 return $.member(rowObject.user.id, cellvalue);
            }},
            {label: '手机号码', name: 'mobile', width: 110, frozen: true},
            { label: '缴费方式',name: 'isOnlinePay', width: 90, formatter: $.jgrid.formatter.TRUEFALSE,
                formatoptions: {on: '线上缴费', off:'<span class="text-success bolder">现金缴费</span>'}, frozen: true},
            {
                label: '党员类别', name: 'configMemberType', formatter: function (cellvalue, options, rowObject) {
                return _cMap.PMD_MEMBER_TYPE_MAP[cellvalue];
            }, width: 120
            },
            {label: '党员分类别', name: 'pmdConfigMemberType.name', width: 220},
            {label: '缴纳额度', name: 'duePay'},
            { label: '确认额度',name: 'hasReset', formatter: function (cellvalue, options, rowObject) {

                if(rowObject.pmdConfigMemberType==undefined
                        ||rowObject.pmdConfigMemberType.pmdNorm==undefined) return "-";
                if(rowObject.hasReset) return '--'

                if(rowObject.pmdConfigMemberType.pmdNorm.setType == ${PMD_NORM_SET_TYPE_SET}){
                   return '<span class="text-danger bolder">未确认</span>'
                }

                return '--'
            }},
            {
                label: '党费计算基数', name: 'retireSalary', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.configMemberType != '${PMD_MEMBER_TYPE_RETIRE}') return '--'
                return $.trim(cellvalue)
            }
            },
            {
                label: '岗位工资', name: 'gwgz', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.configMemberType != '${PMD_MEMBER_TYPE_ONJOB}') return '--'
                return $.trim(cellvalue)
            }
            },
            {
                label: '薪级工资', name: 'xjgz', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.configMemberType != '${PMD_MEMBER_TYPE_ONJOB}') return '--'
                return $.trim(cellvalue)
            }
            },
            {
                label: '岗位津贴', name: 'gwjt', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.configMemberType != '${PMD_MEMBER_TYPE_ONJOB}') return '--'
                return $.trim(cellvalue)
            }
            },
            {
                label: '职务补贴', name: 'zwbt', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.configMemberType != '${PMD_MEMBER_TYPE_ONJOB}') return '--'
                return $.trim(cellvalue)
            }
            },
            {
                label: '职务补贴1', name: 'zwbt1', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.configMemberType != '${PMD_MEMBER_TYPE_ONJOB}') return '--'
                return $.trim(cellvalue)
            }
            },
            {
                label: '生活补贴', name: 'shbt', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.configMemberType != '${PMD_MEMBER_TYPE_ONJOB}') return '--'
                return $.trim(cellvalue)
            }
            },
            {
                label: '书报费', name: 'sbf', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.configMemberType != '${PMD_MEMBER_TYPE_ONJOB}') return '--'
                return $.trim(cellvalue)
            }
            },
            {
                label: '洗理费', name: 'xlf', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.configMemberType != '${PMD_MEMBER_TYPE_ONJOB}') return '--'
                return $.trim(cellvalue)
            }
            },
            {
                label: '工资冲销', name: 'gzcx', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.configMemberType != '${PMD_MEMBER_TYPE_ONJOB}') return '--'
                return $.trim(cellvalue)
            }
            },
            {
                label: '失业保险', name: 'shiyebx', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.configMemberType != '${PMD_MEMBER_TYPE_ONJOB}') return '--'
                return $.trim(cellvalue)
            }
            },
            {
                label: '养老保险', name: 'yanglaobx', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.configMemberType != '${PMD_MEMBER_TYPE_ONJOB}') return '--'
                return $.trim(cellvalue)
            }
            },
            {
                label: '医疗保险', name: 'yiliaobx', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.configMemberType != '${PMD_MEMBER_TYPE_ONJOB}') return '--'
                return $.trim(cellvalue)
            }
            },
            {
                label: '工伤保险', name: 'gsbx', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.configMemberType != '${PMD_MEMBER_TYPE_ONJOB}') return '--'
                return $.trim(cellvalue)
            }
            },
            {
                label: '生育保险', name: 'shengyubx', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.configMemberType != '${PMD_MEMBER_TYPE_ONJOB}') return '--'
                return $.trim(cellvalue)
            }
            },
            {
                label: '企业年金', name: 'qynj', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.configMemberType != '${PMD_MEMBER_TYPE_ONJOB}') return '--'
                return $.trim(cellvalue)
            }
            },
            {
                label: '职业年金', name: 'zynj', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.configMemberType != '${PMD_MEMBER_TYPE_ONJOB}') return '--'
                return $.trim(cellvalue)
            }
            },
            {
                label: '公积金', name: 'gjj', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.configMemberType != '${PMD_MEMBER_TYPE_ONJOB}') return '--'
                return $.trim(cellvalue)
            }
            },{hidden:true, name:'userId', key:true}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>