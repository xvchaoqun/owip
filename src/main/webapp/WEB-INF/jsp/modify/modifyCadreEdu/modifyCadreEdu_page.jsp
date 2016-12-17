<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv">
            <c:set var="_query" value="${not empty param.userId}"/>
            <div class="tabbable">
                <jsp:include page="/WEB-INF/jsp/modify/modifyTableApply/menu.jsp"/>
                <div class="space-4"></div>
                <div class="jqgrid-vertical-offset buttons">
                        <a class="popupBtn btn btn-success btn-sm"
                           data-url="${ctx}/user/cadreEdu_au"
                           data-width="900"><i class="fa fa-plus"></i>
                            添加学习经历</a>
                        <a class="jqOpenViewBtn btn btn-primary btn-sm"
                           data-url="${ctx}/user/cadreEdu_au"
                           data-grid-id="#jqGrid_cadreEdu"
                           data-querystr="&cadreId=${param.cadreId}"
                           data-width="900"><i class="fa fa-edit"></i>
                            修改学习经历</a>
                        <button data-url="${ctx}/user/cadreEdu_del"
                                data-title="删除"
                                data-msg="申请删除这条学习经历？"
                                data-grid-id="#jqGrid_cadreEdu"
                                data-callback="_delCallback"
                                class="jqItemBtn btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> 删除
                        </button>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid_cadreEdu" class="jqGrid"></table>
            </div>
        </div>
        <div id="item-content">
        </div>
    </div>
</div>
<script>
    function _delCallback(type) {
        $("#modal").modal("hide");
        location.href='?cls=1&module=${MODIFY_TABLE_APPLY_MODULE_CADRE_EDU}';
    }
    $("#jqGrid_cadreEdu").jqGrid({
        pager: null,
        ondblClickRow: function () {
        },
        datatype: "local",
        data:${cm:toJSONArray(cadreEdus)},
        colModel: [
            {
                label: '学历', name: 'eduId', frozen: true, formatter: function (cellvalue, options, rowObject) {
                return _cMap.metaTypeMap[cellvalue].name
            }
            },
            {
                label: '毕业/在读', width: 90, name: 'isGraduated', formatter: function (cellvalue, options, rowObject) {
                return cellvalue ? "毕业" : "在读";
            }
            },
            {label: '入学时间', name: 'enrolTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}, width: 80},
            {label: '毕业时间', name: 'finishTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}, width: 80},
            {
                label: '是否最高学历', width: 110, name: 'isHighEdu', formatter: function (cellvalue, options, rowObject) {
                return cellvalue ? "是" : "否";
            }
            },
            {label: '毕业/在读学校', name: 'school', width: 280},
            {label: '院系', name: 'dep', width: 380},
            {label: '所学专业', name: 'major', width: 380},
            {
                label: '学校类型', name: 'schoolType', formatter: function (cellvalue, options, rowObject) {
                return _cMap.CADRE_SCHOOL_TYPE_MAP[cellvalue]
            }, width: 80
            },

            //{label: '学制', name: 'schoolLen', width:50},
            {
                label: '学习方式', name: 'learnStyle', formatter: function (cellvalue, options, rowObject) {
                return _cMap.metaTypeMap[cellvalue].name
            }
            },
            {
                label: '学位', name: 'degree', formatter: function (cellvalue, options, rowObject) {
                return rowObject.hasDegree ? cellvalue : "-";
            }
            },
            {
                label: '是否最高学位', name: 'isHighDegree', formatter: function (cellvalue, options, rowObject) {
                if (!rowObject.hasDegree) return "-";
                return cellvalue ? "是" : "否";
            }, width: 110
            },
            {
                label: '学位授予国家',
                name: 'degreeCountry',
                width: 110,
                formatter: function (cellvalue, options, rowObject) {
                    return rowObject.hasDegree ? cellvalue : "-";
                }
            },
            {
                label: '学位授予单位', name: 'degreeUnit', width: 150, formatter: function (cellvalue, options, rowObject) {
                return rowObject.hasDegree ? cellvalue : "-";
            }
            },
            {label: '学位授予日期', name: 'degreeTime', width: 110, formatter: 'date', formatoptions: {newformat: 'Y.m'}},
            {
                label: '导师姓名', name: 'tutorName', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.eduId == "${cm:getMetaTypeByCode("mt_edu_master").id}" || rowObject.eduId == "${cm:getMetaTypeByCode("mt_edu_doctor").id}") {
                    return cellvalue == undefined ? '' : cellvalue;
                } else return '-'
            }
            },
            {
                label: '导师现所在单位及职务（或职称）', name: 'tutorTitle', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.eduId == "${cm:getMetaTypeByCode("mt_edu_master").id}" || rowObject.eduId == "${cm:getMetaTypeByCode("mt_edu_doctor").id}") {
                    return cellvalue == undefined ? '' : cellvalue;
                } else return '-'
            }, width: 250
            },
            {
                label: '学历学位证书',
                name: 'certificate',
                width: 150,
                formatter: function (cellvalue, options, rowObject) {
                    var filesArray = [];
                    if (cellvalue != undefined) {
                        var filePaths = cellvalue.split(",");
                        filesArray.push('<a class="various" rel="group{2}" title="证件{1}" data-fancybox-type="image" data-path="{0}" href="${ctx}/pic?path={0}">证件{1}</a>'.format(encodeURI(filePaths[0]), 1, rowObject.id));
                        if (filePaths.length == 2)
                            filesArray.push('<a class="various" rel="group{2}" title="证件{1}" data-fancybox-type="image" data-path="{0}"  href="${ctx}/pic?path={0}">证件{1}</a>'.format(encodeURI(filePaths[1]), 2, rowObject.id));
                    }

                    return filesArray.join("，");
                }
            }, {label: '备注', name: 'remark', width: 180}]
    }).jqGrid("setFrozenColumns");
    register_fancybox(function () {
        //console.log(this)
        this.title = '<div class="title">' + this.title + '<div class="download">【<a href="${ctx}/attach/download?path={0}" target="_blank">点击下载</a>】</div></div>'
                        .format($(this.element).data('path'));
    });
    $(window).triggerHandler('resize.jqGrid');
</script>