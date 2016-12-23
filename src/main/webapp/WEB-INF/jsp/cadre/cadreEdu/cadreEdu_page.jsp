<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<shiro:hasRole name="${ROLE_CADREADMIN}">
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="${type==1?"active":""}">
        <a href="javascript:" onclick="_innerPage(1)"><i class="fa fa-flag"></i> 学习经历</a>
    </li>
    <li class="${type==2?"active":""}">
        <a href="javascript:" onclick="_innerPage(2)"><i class="fa fa-flag"></i> 预览</a>
    </li>
</ul>
</shiro:hasRole>
<c:if test="${type==1}">
    <c:if test="${cm:hasRole(ROLE_CADREADMIN) || hasDirectModifyCadreAuth}">
    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
        <shiro:hasPermission name="cadreEdu:edit">
            <a class="popupBtn btn btn-warning btn-sm"
               data-url="${ctx}/cadreEdu_rule?cadreId=${param.cadreId}"><i class="fa fa-search"></i>
                学历学位的认定规则</a>
            <a class="popupBtn btn btn-success btn-sm"
               data-url="${ctx}/cadreEdu_au?cadreId=${param.cadreId}"
               data-width="900"><i class="fa fa-plus"></i>
                添加学习经历</a>
            <a class="jqOpenViewBtn btn btn-primary btn-sm"
               data-url="${ctx}/cadreEdu_au"
               data-grid-id="#jqGrid_cadreEdu"
               data-querystr="&cadreId=${param.cadreId}"
               data-width="900"><i class="fa fa-edit"></i>
                修改学习经历</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="cadreEdu:del">
            <button data-url="${ctx}/cadreEdu_batchDel"
                    data-title="删除"
                    data-msg="确定删除这{0}条数据？"
                    data-grid-id="#jqGrid_cadreEdu"
                    data-querystr="cadreId=${param.cadreId}"
                    class="jqBatchBtn btn btn-danger btn-sm">
                <i class="fa fa-trash"></i> 删除
            </button>
        </shiro:hasPermission>
    </div>
    </c:if>
    <div class="space-4"></div>
    <table id="jqGrid_cadreEdu" class="jqGrid2"></table>
    <div id="jqGridPager_cadreEdu"></div>
</c:if>
<c:if test="${type==2}">
    <div class="row two-frames">
        <div class="left">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="smaller">
                        初始数据
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main" style="min-height: 647px" id="orginal">
                        <c:forEach items="${cadreEdus}" var="cadreEdu">
                            <p>${cm:formatDate(cadreEdu.enrolTime, "yyyy.MM")}${(cadreEdu.finishTime!=null)?"-":"-至今"}${cm:formatDate(cadreEdu.finishTime, "yyyy.MM")}&nbsp;${cadreEdu.school}${cadreEdu.dep}${cadreEdu.major}专业&nbsp;${cadreEdu.degree}</p>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <div class="right">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="smaller">
                        最终数据（<span
                            style="font-weight: bolder; color: red;">最近保存时间：${empty cadreInfo.lastSaveDate?"未保存":cm:formatDate(cadreInfo.lastSaveDate, "yyyy-MM-dd HH:mm")}</span>）
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main" style="margin-bottom: 10px">
                        <textarea id="content">${cadreInfo.content}</textarea>
                    </div>
                    <div class="modal-footer center">
                        <a href="javascript:" onclick="copyOrginal()" class="btn btn-sm btn-success">
                            <i class="ace-icon fa fa-copy"></i>
                            同步自动生成的数据
                        </a>
                        <input type="button" onclick="updateCadreInfo()" class="btn btn-primary" value="保存"/>

                    </div>
                </div>
            </div>

        </div>
    </div>
</c:if>
<div class="footer-margin"/>
<c:if test="${type==2}">
    <script type="text/javascript" src="${ctx}/extend/ke4/kindeditor-all-min.js"></script>
    <script>
        var ke = KindEditor.create('#content', {
            items: ["source", "|", "fullscreen"],
            height: '550px',
            width: '700px'
        });
        function updateCadreInfo() {
            $.post("${ctx}/cadreInfo_updateContent", {
                cadreId: '${param.cadreId}',
                content: ke.html(),
                type: "${CADRE_INFO_TYPE_EDU}"
            }, function (ret) {
                if (ret.success) {
                    SysMsg.info("保存成功", "", function () {
                        _innerPage(2)
                    });
                }
            });
        }
        function copyOrginal() {
            //console.log($("#orginal").html())
            ke.html($("#orginal").html());
            SysMsg.info("复制成功，请务必点击\"保存\"按钮进行保存")
        }
    </script>
</c:if>
<c:if test="${type==1}">
    <script>
        function _innerPage(type) {
            $("#view-box .tab-content").load("${ctx}/cadreEdu_page?cadreId=${param.cadreId}&type=" + type)
        }
        $("#jqGrid_cadreEdu").jqGrid({
            <c:if test="${!cm:hasRole(ROLE_CADREADMIN) && !hasDirectModifyCadreAuth}">
            multiselect:false,
            </c:if>
            pager: "#jqGridPager_cadreEdu",
            ondblClickRow: function () {
            },
            url: '${ctx}/cadreEdu_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
            colModel: [
                {
                    label: '学历', name: 'eduId', frozen: true, formatter: function (cellvalue, options, rowObject) {
                    return _cMap.metaTypeMap[cellvalue].name
                }
                },
                {
                    label: '毕业/在读',
                    width: 90,
                    name: 'isGraduated',
                    formatter: function (cellvalue, options, rowObject) {
                        return cellvalue ? "毕业" : "在读";
                    }
                },
                {label: '入学时间', name: 'enrolTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}, width: 80},
                {label: '毕业时间', name: 'finishTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}, width: 80},
                {
                    label: '是否最高学历',
                    width: 110,
                    name: 'isHighEdu',
                    formatter: function (cellvalue, options, rowObject) {
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
                    label: '学位授予单位',
                    name: 'degreeUnit',
                    width: 150,
                    formatter: function (cellvalue, options, rowObject) {
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
                            filesArray.push('<a class="various" rel="group{2}" title="证件{1}" data-fancybox-type="image" data-path="{0}" href="${ctx}/pic?path={0}">证件{1}</a>'
                                    .format(encodeURI(filePaths[0]), 1, rowObject.id));
                            if (filePaths.length == 2)
                                filesArray.push('<a class="various" rel="group{2}" title="证件{1}" data-fancybox-type="image" data-path="{0}"  href="${ctx}/pic?path={0}">证件{1}</a>'
                                        .format(encodeURI(filePaths[1]), 2, rowObject.id));
                        }

                        return filesArray.join("，");
                    }
                }, {label: '备注', name: 'remark', width: 180}]
        }).jqGrid("setFrozenColumns").on("initGrid", function () {
            $(window).triggerHandler('resize.jqGrid2');
        });

        register_fancybox(function () {
            //console.log(this)
            this.title = '<div class="title">' + this.title + '<div class="download">【<a href="${ctx}/attach/download?path={0}" target="_blank">点击下载</a>】</div></div>'
                            .format($(this.element).data('path'));
        });
    </script>
</c:if>