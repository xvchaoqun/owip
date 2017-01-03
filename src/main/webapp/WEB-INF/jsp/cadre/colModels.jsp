<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script>
    var colModels = function(){};
    colModels.cadreEdu = [
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
        }, {label: '备注', name: 'remark', width: 180}, {hidden: true, name: 'id'}];

    colModels.cadreBook = [
        {label: '出版日期', name: 'pubTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, frozen: true},
        {label: '著作名称', name: 'name', width: 350},
        {label: '出版社', name: 'publisher', width: 280},
        {
            label: '类型', name: 'type', width: 120, formatter: function (cellvalue, options, rowObject) {
            return _cMap.CADRE_BOOK_TYPE_MAP[cellvalue]
        }
        },
        {label: '备注', name: 'remark', width: 350}, {hidden: true, name: 'id'}
    ];

    colModels.cadrePaper = [
        {label: '发表日期', name: 'pubTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, frozen: true},
        {label: '论文', name: 'fileName', width: 750},
        {
            label: '预览', formatter: function (cellvalue, options, rowObject) {
            if (rowObject.fileName && rowObject.fileName != '')
                return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/swf/preview?path={0}&filename={1}">预览</a>'
                                .format(encodeURI(rowObject.filePath), encodeURI(rowObject.fileName))
                        + '&nbsp;&nbsp;<a href="${ctx}/attach/download?path={0}&filename={1}">下载</a>'
                                .format(encodeURI(rowObject.filePath), encodeURI(rowObject.fileName));
            else return '';
        }
        },
        {label: '备注', name: 'remark', width: 350}, {hidden: true, name: 'id'}
    ];

    colModels.cadreReward = [
        {label: '日期', name: 'rewardTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}, frozen: true},
        {label: '获得奖项', name: 'name', width: 350},
        {label: '颁奖单位', name: 'unit', width: 280},
        {
            label: '获奖证书', name: 'proof', width: 250,
            formatter: function (cellvalue, options, rowObject) {
                if (rowObject.proof == undefined) return '-';
                return '<a class="various" title="{1}" data-path="{0}" data-fancybox-type="image" href="${ctx}/pic?path={0}">{1}</a>'
                        .format(encodeURI(rowObject.proof), rowObject.proofFilename);
            }
        },
        {
            label: '排名', name: 'rank', formatter: function (cellvalue, options, rowObject) {
            if (cellvalue == 0) return '-';
            return '第{0}'.format(cellvalue);
        }
        },
        {label: '备注', name: 'remark', width: 350}, {hidden: true, name: 'id'}
    ];

    colModels.cadreResearch = [
        {
            label: '项目起始时间',
            width: 120,
            name: 'startTime',
            formatter: 'date',
            formatoptions: {newformat: 'Y-m-d'},
            frozen: true
        },
        {
            label: '项目结题时间',
            width: 120,
            name: 'endTime',
            formatter: 'date',
            formatoptions: {newformat: 'Y-m-d'},
            frozen: true
        },
        {label: '项目名称', name: 'name', width: 250},
        {label: '项目类型', name: 'type', width: 250},
        {label: '委托单位', name: 'unit', width: 250},
        {label: '备注', name: 'remark', width: 350}, {hidden: true, name: 'id'}
    ];

    colModels.cadreParttime = [
        {label: '起始时间', name: 'startTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}, frozen: true},
        {label: '结束时间', name: 'endTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}, frozen: true},
        {label: '兼职单位', name: 'unit', width: 280},
        {label: '兼任职务', name: 'post', width: 280},
        {label: '备注', name: 'remark', width: 350}, {hidden: true, name: 'id'}
    ];

    colModels.cadreTrain = [
        {label: '起始时间', name: 'startTime', formatter: 'date', formatoptions: {newformat: 'Y.m.d'},frozen:true },
        {label: '结束时间', name: 'endTime', formatter: 'date', formatoptions: {newformat: 'Y.m.d'},frozen:true },
        {label: '培训内容', name: 'content', width: 350},
        {label: '主办单位', name: 'unit', width: 280},
        {label: '备注', name: 'remark', width: 350}, {hidden: true, name: 'id'}
    ];

    colModels.cadreCourse =[
        {
            label: '类型', name: 'type', width: 120, formatter: function (cellvalue, options, rowObject) {
            return _cMap.CADRE_COURSE_TYPE_MAP[cellvalue]
        }
        },
        {label: '课程名称', name: 'name', width: 250},
        <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
        {
            label: '排序', width: 80, index: 'sort', formatter: function (cellvalue, options, rowObject) {
            return _.template($("#sort_tpl").html().NoMultiSpace())({id: rowObject.id})
        }, frozen: true
        },
        </c:if>
        {label: '备注', name: 'remark', width: 350}, {hidden: true, name: 'id'}
    ];

    colModels.cadreCompany=[
        { label:'兼职类型', name: 'type', width: 140, formatter:function(cellvalue, options, rowObject){
            if(cellvalue==undefined) return '';
            var ret = _cMap.CADRE_COMPANY_TYPE_MAP[cellvalue];
            if(cellvalue=='${CADRE_COMPANY_TYPE_OTHER}'){
                if(rowObject.typeOther!=''){
                    ret = ret + ":"+  rowObject.typeOther;
                }
            }
            return ret;
        },frozen:true},
        {label: '是否取酬', name: 'hasPay', formatter:function(cellvalue, options, rowObject){
            if(cellvalue==undefined) return "";
            return cellvalue?"是":"否";
        }, width:80,frozen:true},
        {label: '兼职起始时间', name: 'startTime', width: 120, formatter: 'date', formatoptions: {newformat: 'Y.m'},frozen:true },
        {label: '兼职单位及职务', name: 'unit', width: 350},
        {label: '报批单位', name: 'reportUnit', width: 280},
        {label: '批复文件', name: 'paper', width: 250,
            formatter: function (cellvalue, options, rowObject) {
                if(rowObject.paper==undefined) return '-';
                return '<a href="${ctx}/attach/download?path={0}&filename={1}">{2}</a>'
                        .format(encodeURI(rowObject.paper),encodeURI(rowObject.paperFilename), rowObject.paperFilename);
            }},
        {label: '备注', name: 'remark', width: 350}, {hidden: true, name: 'id'}
    ];
</script>