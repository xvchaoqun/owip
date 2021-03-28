<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%--<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="active">
        <a href="javascript:;" onclick="_innerPage(1)"><i class="fa fa-list"></i> 学习经历</a>
    </li>
</ul>--%>

    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
        <shiro:hasPermission name="dpEdu:edit">
            <button class="popupBtn btn btn-success btn-sm"
               data-url="${ctx}/dp/dpEdu_au?userId=${param.userId}"
               data-width="900"><i class="fa fa-plus"></i>
                添加学习经历</button>
            <button class="jqOpenViewBtn btn btn-primary btn-sm"
               data-url="${ctx}/dp/dpEdu_au"
               data-grid-id="#jqGrid_dpEdu"
               data-querystr="&userId=${param.userId}"
               data-width="900"><i class="fa fa-edit"></i>
                修改学习经历</button>
            <button class="jqOpenViewBtn btn btn-info btn-sm"
                       data-grid-id="#jqGrid_dpEdu"
                       data-url="${ctx}/dp/dpWork_au?isEduWork=1"
                       data-id-name="fid"
                       data-querystr="&userId=${param.userId}"><i class="fa fa-plus"></i>
                        添加其间经历</button>
        </shiro:hasPermission>
        <shiro:hasPermission name="dpEdu:del">
            <button data-url="${ctx}/dp/dpEdu_batchDel"
                    data-title="删除"
                    data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                    data-grid-id="#jqGrid_dpEdu"
                    data-querystr="userId=${param.userId}"
                    class="jqBatchBtn btn btn-danger btn-sm">
                <i class="fa fa-trash"></i> 删除
            </button>
        </shiro:hasPermission>
    </div>

    <div class="space-4"></div>
    <table id="jqGrid_dpEdu" class="jqGrid2" data-height-reduce="30"></table>
    <div id="jqGridPager_dpEdu"></div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<style>
    .noSubWork [aria-describedby="jqGrid_dpEdu_subgrid"] a {
        display: none;
    }
    .table > tbody > tr.ui-subgrid.active > td,
    .table tbody tr.ui-subgrid:hover td, .table tbody tr.ui-subgrid:hover th {
        background-color: inherit !important;
    }

    .ui-subgrid tr.success td {
        background-color: inherit !important;
    }
    .resume p {
         text-indent: -9em;
         margin: 0 0 0 9em;
    }
</style>

    <script type="text/template" id="switch_tpl">
        <button class="switchBtn btn btn-info btn-xs" onclick="_swtich({{=id}}, this)"
                data-id="{{=id}}"><i class="fa fa-folder-o"></i>
            <span>查看其间经历</span>({{=subWorkCount}})
        </button>
    </script>
    <script type="text/template" id="op_tpl">
    <shiro:hasPermission name="dpWork:edit">
        <button class="popupBtn btn btn-xs btn-primary"
                data-url="${ctx}/dp/dpWork_au?id={{=id}}&userId={{=userId}}&&fid={{=parentRowKey}}"><i
                class="fa fa-edit"></i> 编辑
        </button>
    </shiro:hasPermission>
    <shiro:hasPermission name="dpWork:del">
        <button class="confirm btn btn-xs btn-danger"
                data-parent="{{=parentRowKey}}"
                data-url="${ctx}/dp/dpWork_batchDel?ids={{=id}}&userId=${param.userId}"
                data-msg="确定删除该其间经历？<br/>（删除后无法恢复，请谨慎操作！！）"
                data-callback="_callback"><i class="fa fa-times"></i> 删除
        </button>
        </shiro:hasPermission>
    </script>
    <script>
        function _innerPage(type, fn) {
            $("#dp-content").loadPage({url:"${ctx}/dp/dpEdu?userId=${param.userId}&type=" + type, callback:fn})
        }

        var needTutorEduTypes = ${cm:toJSONArray(needTutorEduTypes)};
        $("#jqGrid_dpEdu").jqGrid({
            <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
            multiselect:false,
            </c:if>
            ondblClickRow: function () {
            },
            pager: "#jqGridPager_dpEdu",
            url: '${ctx}/dp/dpEdu_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
            colModel: [{
                label: '其间经历', name: '&nbsp;', formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.subWorkCount == 0) return '--';
                    return _.template($("#switch_tpl").html().NoMultiSpace())({
                            id: rowObject.id,
                            subWorkCount: rowObject.subWorkCount
                    });
                }, width: 130
            },
                        {label: '学历', name: 'eduId', frozen: true, formatter: $.jgrid.formatter.MetaType},
                        {
                            label: '毕业/在读', width: 90, name: 'isGraduated', formatter: function (cellvalue, options, rowObject) {
                                return cellvalue ? "毕业" : "在读";
                            }, frozen: true
                        },
                        {label: '入学时间', name: 'enrolTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}, width: 90, frozen: true},
                        {label: '毕业时间', name: 'finishTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}, width: 90, frozen: true},
                        {label: '是否最高学历', width: 120, name: 'isHighEdu', formatter: $.jgrid.formatter.TRUEFALSE, frozen: true},
                        {label: '毕业/在读学校', name: 'school', width: 180, align:'left'},
                        {label: '院系', name: 'dep', width: 180, align:'left'},
                        {label: '所学专业', name: 'major', width: 250, align:'left'},
                        <c:if test="${_dpEdu_needSubject}">
                        {label: '学科门类', name: 'subject', width: 90, formatter: $.jgrid.formatter.MAP,
                            formatoptions:{mapKey:'layerTypeMap', filed:'name'}},
                        {label: '一级学科', name: 'firstSubject', width: 120, formatter: $.jgrid.formatter.MAP,
                            formatoptions:{mapKey:'layerTypeMap', filed:'name'}},
                        </c:if>
                        {label: '学校类型', name: 'schoolType', formatter: $.jgrid.formatter.MAP,
                            formatoptions:{mapKey:'CADRE_SCHOOL_TYPE_MAP'}, width: 90},
                        {label: '学习方式', name: 'learnStyle', formatter: $.jgrid.formatter.MetaType},
                        {label: '是否获得学位', name: 'hasDegree', formatter: $.jgrid.formatter.TRUEFALSE},
                        {
                            label: '学位', name: 'degree', formatter: function (cellvalue, options, rowObject) {

                                var str = '';
                                if(rowObject.isHighDegree && rowObject.isSecondDegree){
                                    str = '<i class="red" title="双学位">*</i>&nbsp;';
                                }

                                return rowObject.hasDegree ? str+cellvalue : "-";
                            }
                        },
                        {
                            label: '是否最高学位', name: 'isHighDegree', formatter: function (cellvalue, options, rowObject) {
                                if (!rowObject.hasDegree) return "-";
                                return cellvalue ? "是" : "否";
                            }, width: 120
                        },
                        {
                            label: '学位授予国家',
                            name: 'degreeCountry',
                            width: 120,
                            formatter: function (cellvalue, options, rowObject) {
                                return rowObject.hasDegree ? cellvalue : "-";
                            }
                        },
                        {
                            label: '学位授予单位', name: 'degreeUnit', width: 150, formatter: function (cellvalue, options, rowObject) {
                                return rowObject.hasDegree ? cellvalue : "-";
                            }
                        },
                        {label: '学位授予日期', name: 'degreeTime', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
                        {
                            label: '导师姓名', name: 'tutorName', formatter: function (cellvalue, options, rowObject) {
                                if ($.inArray(rowObject.eduId , needTutorEduTypes)>=0) {
                                    return $.trim(cellvalue);
                                } else return '--'
                            }, cellattr: function (rowId, val, rowObject, cm, rdata) {
                                if($.inArray(rowObject.eduId , needTutorEduTypes)>=0 && $.trim(rowObject.tutorName)=='')
                                    return "class='danger'";
                            }},
                        {
                            label: '导师现所在单位及职务（或职称）', name: 'tutorTitle', formatter: function (cellvalue, options, rowObject) {
                                if ($.inArray(rowObject.eduId , needTutorEduTypes)>=0) {
                                    return $.trim(cellvalue);
                                } else return '--'
                            }, cellattr: function (rowId, val, rowObject, cm, rdata) {
                                if($.inArray(rowObject.eduId , needTutorEduTypes)>=0 && $.trim(rowObject.tutorTitle)=='')
                                    return "class='danger'";
                            }, width: 250
                        },
                        {
                            label: '学历学位证书',
                            name: 'certificate',
                            width: 150,
                            formatter: function (cellvalue, options, rowObject) {
                                var filesArray = [];
                                if (rowObject.signCertificates != undefined) {
                                    var filePaths = rowObject.signCertificates.split(",");
                                    filesArray.push('<a class="various" rel="group{2}" title="学历学位证书{1}" data-fancybox-type="image" data-path="{0}" href="${ctx}/pic?path={0}">证书{1}</a>'.format(filePaths[0], 1, rowObject.id));
                                    if (filePaths.length == 2)
                                        filesArray.push('<a class="various" rel="group{2}" title="学历学位证书{1}" data-fancybox-type="image" data-path="{0}"  href="${ctx}/pic?path={0}">证书{1}</a>'.format(filePaths[1], 2, rowObject.id));
                                }

                                return filesArray.join("，");
                            }
                        },{label: '补充说明', name: 'note', width: 280},
                        {label: '备注', name: 'remark', width: 180, align:'left'},  {hidden: true, key: true, name: 'id'},
            ],
            rowattr: function (rowData, currentObj, rowId) {
                //console.log(currentObj)
                if (currentObj.subWorkCount == 0) {
                    //console.log(rowId)
                    //console.log($("#"+rowId).text())
                    return {'class': 'noSubWork'}
                }
            },
            subGrid: true,
            subGridRowExpanded: subGridRowExpanded,
            subGridRowColapsed: subGridRowColapsed,
            subGridOptions: {
                plusicon: "fa fa-folder-o",
                minusicon: "fa fa-folder-open-o"
                // selectOnExpand:true
            }
        }).on("initGrid", function () {

            $('.noSubWork [aria-describedby="jqGrid_dpEdu_subgrid"]').removeClass();

            //console.log(currentExpandRows)
            currentExpandRows.forEach(function(item, i){
                $("#jqGrid_dpEdu").expandSubGridRow(item)
            })
        }).jqGrid("setFrozenColumns");
        $(window).triggerHandler('resize.jqGrid2');

        function _swtich(id, btn) {

            if (!$("i", btn).hasClass("fa-folder-open-o")) {
                $("#jqGrid_dpEdu").expandSubGridRow(id)
            } else {
                $("#jqGrid_dpEdu").collapseSubGridRow(id)
            }
            $.getEvent().stopPropagation();
        }
        var currentExpandRows = [];
        function subGridRowColapsed(parentRowID, parentRowKey) {
            $(".switchBtn i", '#jqGrid_dpEdu #' + parentRowKey).removeClass("fa-folder-open-o");
            $(".switchBtn span", '#jqGrid_dpEdu #' + parentRowKey).html("查看其间经历");
            currentExpandRows.remove(parentRowKey);
        }
        // the event handler on expanding parent row receives two parameters
        // the ID of the grid tow  and the primary key of the row
        function subGridRowExpanded(parentRowID, parentRowKey) {

            $(".switchBtn i", '#jqGrid_dpEdu #' + parentRowKey).addClass("fa-folder-open-o");
            $(".switchBtn span", '#jqGrid_dpEdu #' + parentRowKey).html("隐藏其间经历");
            currentExpandRows.remove(parentRowKey);
            currentExpandRows.push(parentRowKey);

            var childGridID = parentRowID + "_table";
            var childGridPagerID = parentRowID + "_pager";

            var childGridURL = '${ctx}/dp/dpWork_data?isEduWork=1&fid={0}&${cm:encodeQueryString(pageContext.request.queryString)}'.format(parentRowKey);

            $('#' + parentRowID).append('<table id=' + childGridID + '></table><div id=' + childGridPagerID + ' class=scroll></div>');
            $("#" + childGridID).jqGrid({
                ondblClickRow: function () {
                },
                multiselect: false,
                url: childGridURL,
                colModel: [
                    {label: '开始日期', name: 'startTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
                    {label: '结束日期', name: 'endTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
                    {label: '工作单位及担任职务（或专技职务）', name: 'detail', width: 380, align: 'left'},
                    {label: '工作类型', name: 'workTypes', formatter: function (cellvalue, options, rowObject) {
                        if($.trim(cellvalue)=='') return '--'
                        return ($.map(cellvalue.split(","), function(workType){
                            return $.jgrid.formatter.MetaType(workType);
                        })).join("，")
                    }, width: 270},
                    {label: '备注', name: 'remark', width: 150},
                    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
                    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                    {
                        label: '操作', name: 'op', formatter: function (cellvalue, options, rowObject) {
                        //alert(rowObject.id)
                        return _.template($("#op_tpl").html().NoMultiSpace())
                        ({id: rowObject.id, parentRowKey: parentRowKey, userId: rowObject.userId})
                    }, width: ${cm:isPermitted(PERMISSION_CADREADMIN)?200:150}
                    }
                    </shiro:lacksPermission>
                    </c:if>
                ],
                pager: null
            });
        }
        function _callback(target) {
            //_reloadSubGrid($(target).data("parent"))
            $("#jqGrid_dpEdu").trigger("reloadGrid");
        }

        $.register.fancybox();
    </script>