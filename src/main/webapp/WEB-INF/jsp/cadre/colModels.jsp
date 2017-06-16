<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script>
    var colModels = function () {
    };
    colModels.cadre = [
        {label: '工作证号', name: 'user.code', width: 100, frozen: true},
        {
            label: '姓名', name: 'user.realname', width: 120, formatter: function (cellvalue, options, rowObject) {
            return $.cadre(rowObject.id, cellvalue);
        }, frozen: true
        },
        <shiro:hasPermission name="cadre:changeOrder">
        {
            label: '排序', width: 80, index: 'sort', formatter: function (cellvalue, options, rowObject) {
            return _.template($("#sort_tpl").html().NoMultiSpace())({id: rowObject.id})
        }, frozen: true
        },
        </shiro:hasPermission>
        {label: '部门属性', name: 'unit.unitType.name', width: 150},
        {label: '所在单位', name: 'unit.name', width: 200},
        {label: '现任职务', name: 'post', align: 'left', width: 350},
        {label: '所在单位及职务', name: 'title', align: 'left', width: 350},
        {
            label: '行政级别', name: 'typeId', formatter: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '-';
            return _cMap.adminLevelMap[cellvalue].name;
        }
        },
        {
            label: '职务属性', name: 'postId', width: 150, formatter: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '-';
            return _cMap.postMap[cellvalue].name;
        }
        },
        {label: '是否正职', name: 'isPrincipalPost', formatter: $.jgrid.formatter.TRUEFALSE},
        {label: '性别', name: 'gender', width: 50, formatter:$.jgrid.formatter.GENDER},
        {label: '民族', name: 'nation', width: 60},
        {label: '籍贯', name: 'nativePlace', width: 120},
        {label: '出生地', name: 'user.homeplace', width: 120},
        {label: '身份证号', name: 'idcard', width: 170},
        {label: '出生时间', name: 'birth', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
        {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE},
        {
            label: '党派', name: 'cadreDpType', width: 80, formatter: function (cellvalue, options, rowObject) {

            if (cellvalue == 0) return "中共党员"
            else if (cellvalue > 0) return _cMap.metaTypeMap[rowObject.dpTypeId].name
            return "-";
        }
        },
        {
            label: '党派加入时间', name: 'cadreGrowTime', width: 120, formatter: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '-';
            return cellvalue.substr(0, 10);
        }
        },
        {
            label: '党龄', name: '_growBirth', width: 50,
            formatter: function (cellvalue, options, rowObject) {
                if (rowObject.cadreGrowTime == undefined) return '-';
                return $.yearOffNow(rowObject.cadreGrowTime);
            }
        },
        {
            label: '参加工作时间', name: 'workStartTime', width: 120, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}
        },
        {label: '到校时间', name: 'arriveTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
        {label: '最高学历', name: 'eduId', formatter: $.jgrid.formatter.MetaType},
        {label: '最高学位', name: 'degree'},
        {label: '毕业时间', name: 'finishTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}},
        {label: '学习方式', name: 'learnStyle', formatter: $.jgrid.formatter.MetaType},
        {label: '毕业学校', name: 'school', width: 150},
        {
            label: '学校类型', name: 'schoolType', formatter: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '-';
            return _cMap.CADRE_SCHOOL_TYPE_MAP[cellvalue]
        }
        },
        {label: '所学专业', name: 'major', width: 180, align: 'left'},
        {
            label: '全日制教育学历', name: 'fulltimeEdu', width: 130, formatter: function (cellvalue, options, rowObject) {
            var cadreEdus = rowObject.cadreEdus;
            if (cadreEdus == undefined || cadreEdus == null || cadreEdus[0] == undefined) return '-';
            return _cMap.metaTypeMap[cadreEdus[0].eduId].name/* + ((cadreEdus[0].degree==undefined)?'':cadreEdus[0].degree)*/;
        }
        },
        {
            label: '全日制教育毕业院校系及专业',
            name: 'fulltimeMajor',
            width: 350,
            align: 'left',
            formatter: function (cellvalue, options, rowObject) {
                var cadreEdus = rowObject.cadreEdus;
                //console.log(cadreEdus)
                if (cadreEdus == undefined || cadreEdus == null || cadreEdus[0] == undefined) return '';
                return cadreEdus[0].school + cadreEdus[0].dep + cadreEdus[0].major;
            }
        },
        {
            label: '在职教育学历', name: 'onjobEdu', width: 120, formatter: function (cellvalue, options, rowObject) {
            var cadreEdus = rowObject.cadreEdus;
            if (cadreEdus == undefined || cadreEdus == null || cadreEdus[1] == undefined) return '-';
            return _cMap.metaTypeMap[cadreEdus[1].eduId].name/* + ((cadreEdus[1].degree==undefined)?'':cadreEdus[1].degree)*/;
        }
        },
        {
            label: '在职教育毕业院系及专业',
            name: 'onjobMajor',
            width: 350,
            align: 'left',
            formatter: function (cellvalue, options, rowObject) {
                var cadreEdus = rowObject.cadreEdus;
                if (cadreEdus == undefined || cadreEdus == null || cadreEdus[1] == undefined) return '';
                return cadreEdus[1].school + cadreEdus[1].dep + cadreEdus[1].major;
            }
        },
        {label: '岗位类别', name: 'postClass'},
        {label: '主岗等级', name: 'mainPostLevel', width: 150},
        {label: '专业技术职务', name: 'proPost', width: 120},
        {label: '专技职务评定时间', name: 'proPostTime', width: 130, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
        {label: '专技岗位等级', name: 'proPostLevel', width: 150},
        {
            label: '专技岗位分级时间',
            name: 'proPostLevelTime',
            width: 130,
            formatter: 'date',
            formatoptions: {newformat: 'Y-m-d'}
        },
        {label: '管理岗位等级', name: 'manageLevel', width: 150},
        {
            label: '管理岗位分级时间',
            name: 'manageLevelTime',
            width: 130,
            formatter: 'date',
            formatoptions: {newformat: 'Y-m-d'}
        },
        {
            label: '现职务任命文件',
            width: 150,
            name: 'mainCadrePost.dispatchCadreRelateBean.first',
            formatter: function (cellvalue, options, rowObject) {
                if (!cellvalue || cellvalue.id == undefined) return '';
                var dispatchCode = cellvalue.dispatchCode;
                if (cellvalue.fileName && cellvalue.fileName != '')
                    return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/swf/preview?path={0}&filename={1}">{2}</a>'
                            .format(encodeURI(cellvalue.file), cellvalue.fileName, dispatchCode);
                else return dispatchCode;
            }
        },
        {
            label: '任现职时间',
            name: 'lpWorkTime',
            formatter: 'date',
            formatoptions: {newformat: 'Y-m-d'}
        },
        {
            label: '现职务始任时间',
            width: 150,
            name: 'npWorkTime',
            formatter: 'date',
            formatoptions: {newformat: 'Y-m-d'}
        },
        /*{
            label: '现职务始任年限',
            width: 120,
            name: 'mainCadrePost.dispatchCadreRelateBean.first.workTime',
            formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                var year = yearOffNow(cellvalue);
                return year == 0 ? "未满一年" : year;
            }
        },*/
        {
            label: '现职务始任年限',
            width: 120,
            name: 'cadrePostYear',
            formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return cellvalue == 0 ? "未满一年" : cellvalue;
            }
        },
        {
            label: '现职级始任时间',
            width: 120,
            name: 'presentAdminLevel.startDispatch.workTime',
            formatter: 'date',
            formatoptions: {newformat: 'Y-m-d'}
        },
        /*{
            label: '任现职级年限',
            width: 120,
            name: 'workYear',
            formatter: function (cellvalue, options, rowObject) {
                //console.log(rowObject.endDispatch)
                if (rowObject.presentAdminLevel == undefined || rowObject.presentAdminLevel.startDispatch == undefined) return '';

                var end;
                if (rowObject.presentAdminLevel.endDispatch != undefined)
                    end = rowObject.presentAdminLevel.endDispatch.workTime;
                if (rowObject.presentAdminLevel.adminLevelId == rowObject.mainCadrePost.adminLevelId)
                    end = new Date().format("yyyy-MM-dd");
                if (rowObject.presentAdminLevel.startDispatch.workTime == undefined || end == undefined) return '';

                var month = MonthDiff(rowObject.presentAdminLevel.startDispatch.workTime, end);
                var year = Math.floor(month / 12);
                return year == 0 ? "未满一年" : year;
            }
        },*/
        {
            label: '任现职级年限',
            width: 120,
            name: 'adminLevelYear',
            formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return cellvalue == 0 ? "未满一年" : cellvalue;
            }
        },
        {label: '是否双肩挑', name: 'isDouble', formatter: $.jgrid.formatter.TRUEFALSE},
        {
            label: '双肩挑单位',
            name: 'doubleUnitId',
            width: 150,
            formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.unitMap[cellvalue].name
            }
        },
        {label: '联系方式', name: 'mobile'},
        /*{ label: '办公电话', name: 'phone' },
         { label: '家庭电话', name: 'homePhone' },*/
        {label: '电子邮箱', name: 'email', width: 150},
        {
            label: '所属党组织',
            name: 'partyId',
            align: 'left',
            width: 550,
            formatter: function (cellvalue, options, rowObject) {
                return $.party(rowObject.partyId, rowObject.branchId);
            }
        },
        <shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
        {
            label: '因私出国境兼审单位',
            width: 150,
            name: 'additional',
            formatter: function (cellvalue, options, rowObject) {
                var cadreAdditionalPosts = rowObject.cadreAdditionalPosts;
                if (cadreAdditionalPosts.length == 0) return '-';
                return '<button class="popupBtn btn btn-xs btn-warning"' +
                        'data-url="${ctx}/cadre_additional_post?id={0}"><i class="fa fa-search"></i> 查看</button>'
                                .format(rowObject.id);
            }
        },
        {
            label: '短信称谓', name: 'msgTitle', width: 80, formatter: function (cellvalue, options, rowObject) {
            // 短信称谓
            var msgTitle = $.trim(cellvalue);
            if (msgTitle == '' || msgTitle == rowObject.user.realname) {
                msgTitle = '无'
            }
            return msgTitle;
        }
        },
        </shiro:lacksRole>
        {label: '备注', name: 'remark', width: 150}
    ];

    colModels.cadreLeave = [
        {label: '工作证号', name: 'user.code', width: 100, frozen: true},
        {
            label: '姓名', name: 'user.realname', width: 120, formatter: function (cellvalue, options, rowObject) {
            return $.cadre(rowObject.id, cellvalue);
        }, frozen: true
        },
        {
            label: '排序', width: 80, index: 'sort', formatter: function (cellvalue, options, rowObject) {
            return _.template($("#sort_tpl").html().NoMultiSpace())({id: rowObject.id})
        }, frozen: true
        },
        {label: '原所在单位', name: 'unit.name', width: 200},
        {label: '原职务', name: 'post', width: 350},
        {label: '离任后所在单位及职务', name: 'title', width: 350},
        <c:if test="${status==CADRE_STATUS_MIDDLE_LEAVE||status==CADRE_STATUS_LEADER_LEAVE}">
        {
            label: '离任文件', name: 'dispatch', width: 180, formatter: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '';
            return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/swf/preview?path={0}&filename={1}">{2}</a>'
                    .format(encodeURI(cellvalue.file), cellvalue.fileName, cellvalue.dispatchCode);
        }, frozen: true
        },
        {label: '离任日期', name: 'dispatch.workTime', formatter: 'date', formatoptions: {newformat: 'Y.m.d'}},
        </c:if>
        {
            label: '原行政级别',
            name: 'typeId',
            formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return _cMap.adminLevelMap[cellvalue].name;
            }
        },
        {label: '原职务属性', name: 'postType.name', width: 150},
        {label: '手机号', name: 'mobile'},
        {label: '办公电话', name: 'phone'},
        {label: '家庭电话', name: 'homePhone'},
        {label: '电子邮箱', name: 'email', width: 150},
        {label: '备注', name: 'remark', width: 150}
    ];

    colModels.cadreEdu = [
        {label: '学历', name: 'eduId', frozen: true, formatter: $.jgrid.formatter.MetaType},
        {
            label: '毕业/在读', width: 90, name: 'isGraduated', formatter: function (cellvalue, options, rowObject) {
            return cellvalue ? "毕业" : "在读";
        }
        },
        {label: '入学时间', name: 'enrolTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}, width: 80},
        {label: '毕业时间', name: 'finishTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}, width: 80},
        {label: '是否最高学历', width: 110, name: 'isHighEdu', formatter: $.jgrid.formatter.TRUEFALSE},
        {label: '毕业/在读学校', name: 'school', width: 280},
        {label: '院系', name: 'dep', width: 380},
        {label: '所学专业', name: 'major', width: 380},
        {
            label: '学校类型', name: 'schoolType', formatter: function (cellvalue, options, rowObject) {
            return _cMap.CADRE_SCHOOL_TYPE_MAP[cellvalue]
        }, width: 80
        },

        //{label: '学制', name: 'schoolLen', width:50},
        {label: '学习方式', name: 'learnStyle', formatter: $.jgrid.formatter.MetaType},
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
        {label: '获奖年份', name: 'rewardTime', formatter: 'date', formatoptions: {newformat: 'Y'}, frozen: true},
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
        {label: '起始时间', name: 'startTime', formatter: 'date', formatoptions: {newformat: 'Y.m.d'}, frozen: true},
        {label: '结束时间', name: 'endTime', formatter: 'date', formatoptions: {newformat: 'Y.m.d'}, frozen: true},
        {label: '培训内容', name: 'content', width: 350},
        {label: '主办单位', name: 'unit', width: 280},
        {label: '备注', name: 'remark', width: 350}, {hidden: true, name: 'id'}
    ];

    colModels.cadreCourse = [
        {
            label: '类型', name: 'type', width: 120, formatter: function (cellvalue, options, rowObject) {
            return _cMap.CADRE_COURSE_TYPE_MAP[cellvalue]
        }
        },
        {label: '课程名称', name: 'name', width: 250},
        <c:if test="${param._sort ne 'no' && (cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth)}">
        {
            label: '排序', width: 80, index: 'sort', formatter: function (cellvalue, options, rowObject) {
            return _.template($("#sort_tpl").html().NoMultiSpace())({id: rowObject.id})
        }, frozen: true
        },
        </c:if>
        {label: '备注', name: 'remark', width: 350}, {hidden: true, name: 'id'}
    ];

    colModels.cadreCompany = [
        {
            label: '兼职类型', name: 'type', width: 140, formatter: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '';
            var ret = _cMap.CADRE_COMPANY_TYPE_MAP[cellvalue];
            if (cellvalue == '${CADRE_COMPANY_TYPE_OTHER}') {
                if (rowObject.typeOther != '') {
                    ret = ret + ":" + rowObject.typeOther;
                }
            }
            return ret;
        }, frozen: true
        },
        {
            label: '是否取酬', name: 'hasPay', formatter: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return "";
            return cellvalue ? "是" : "否";
        }, width: 80, frozen: true
        },
        {
            label: '兼职起始时间',
            name: 'startTime',
            width: 120,
            formatter: 'date',
            formatoptions: {newformat: 'Y.m'},
            frozen: true
        },
        {label: '兼职单位', name: 'unit', width: 250},
        {label: '兼职职务', name: 'post', width: 150},
        {label: '报批单位', name: 'reportUnit', width: 280},
        {
            label: '批复文件', name: 'paper', width: 250,
            formatter: function (cellvalue, options, rowObject) {
                if (rowObject.paper == undefined) return '-';
                /*return '<a href="
                ${ctx}/attach/download?path={0}&filename={1}">{2}</a>'
                 .format(encodeURI(rowObject.paper),encodeURI(rowObject.paperFilename), rowObject.paperFilename);*/

                if (rowObject.paperFilename && rowObject.paperFilename != '')
                    return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/swf/preview?path={0}&filename={1}">预览</a>'
                                    .format(encodeURI(rowObject.paper), encodeURI(rowObject.paperFilename))
                            + '&nbsp;&nbsp;<a href="${ctx}/attach/download?path={0}&filename={1}">下载</a>'
                                    .format(encodeURI(rowObject.paper), encodeURI(rowObject.paperFilename));
                else return '';

            }
        },
        {label: '备注', name: 'remark', width: 350}, {hidden: true, name: 'id'}
    ];

    colModels.cisInspectObj = [
        {
            label: '编号', name: 'seq', formatter: function (cellvalue, options, rowObject) {
            var type = _cMap.metaTypeMap[rowObject.typeId].name;
            return type + "[" + rowObject.year + "]" + rowObject.seq + "号";

        }, width: 180, frozen: true
        },
        {label: '考察日期', name: 'inspectDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, frozen: true},
        {label: '工作证号', name: 'cadre.user.code', frozen: true},
        {label: '考察对象', name: 'cadre.user.realname', frozen: true},
        {label: '所在单位及职务', name: 'cadre.title', align: 'left', width: 200},
        {
            label: '考察主体', name: '_inspectorType', formatter: function (cellvalue, options, rowObject) {
            var type = _cMap.CIS_INSPECTOR_TYPE_MAP[rowObject.inspectorType];
            if (rowObject.inspectorType == '${CIS_INSPECTOR_TYPE_OTHER}') {
                type += "：" + rowObject.otherInspectorType;
            }
            return type;
        }, width: 200
        },
        {label: '考察组负责人', name: 'chiefInspector.realname', width: 120},
        {
            label: '考察组成员', name: 'inspectors', formatter: function (cellvalue, options, rowObject) {
            if (rowObject.inspectorType == '${CIS_INSPECTOR_TYPE_OTHER}') return '-'
            if (cellvalue == undefined || cellvalue.length == 0) return '';
            var names = []
            for (var i in cellvalue) {
                var inspector = cellvalue[i];
                if (inspector.realname)
                    names.push(inspector.realname)
            }
            return names.join("，")
        }, width: 250
        },
        /*{label: '谈话人数', name: 'talkUserCount'},*/
        {
            label: '考察材料', name: 'summary', formatter: function (cellvalue, options, rowObject) {
            if (rowObject.summary && rowObject.summary != '')
                return '<button class="openView btn btn-info btn-xs" data-url="${ctx}cisInspectObj_summary?objId={0}"><i class="fa fa-search"></i> 查看</button>'
                                .format(rowObject.id)
                        + '&nbsp;<button class="linkBtn btn btn-success btn-xs" data-url="${ctx}/cisInspectObj_summary_export?objId={0}"><i class="fa fa-download"></i> 导出</button>'
                                .format(rowObject.id);
            else return '<button class="openView btn btn-primary btn-xs" data-url="${ctx}cisInspectObj_summary?objId={0}"><i class="fa fa-edit"></i> 编辑</button>'
                    .format(rowObject.id)
        }, width: 150
        },
        {
            label: '考察原始记录', width: 150, formatter: function (cellvalue, options, rowObject) {

            var ret = "-";
            var logFile = rowObject.logFile;
            if ($.trim(logFile) != '') {

                var code =  _cMap.metaTypeMap[rowObject.typeId].name + "[" + rowObject.year + "]" + rowObject.seq + "号";
                var fileName = code + "考察原始记录.pdf";
                //console.log(fileName + " =" + pdfFilePath.substr(pdfFilePath.indexOf(".")))
                ret = '<button href="javascript:void(0)" data-url="${ctx}/swf/preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                                .format(encodeURI(logFile), encodeURI(fileName))
                        + '&nbsp;<button data-url="${ctx}/attach/download?path={0}&filename={1}" title="下载PDF文件" class="linkBtn btn btn-xs btn-warning"><i class="fa fa-download"></i> PDF</button>'
                                .format(encodeURI(logFile), encodeURI(fileName));
            }

            return ret;
        }
        },
        {label: '备注', name: 'remark'}, {hidden: true, name: 'inspectorType'}
    ];
    colModels.cadreReport = [
        {label: '形成日期', name: 'createDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, frozen: true},
        {label: '工作证号', name: 'cadre.user.code', frozen: true},
        {label: '姓名', name: 'cadre.user.realname', frozen: true},
        {label: '所在单位及职务', name: 'cadre.title', align: 'left', width: 300},
        {
            label: '材料内容', name: 'filePath', formatter: function (cellvalue, options, rowObject) {
            if (rowObject.fileName && rowObject.fileName != '')
                return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/swf/preview?path={0}&filename={1}">查看</a>'
                                .format(encodeURI(rowObject.filePath), encodeURI(rowObject.fileName))
                        + '&nbsp;<a href="${ctx}/attach/download?path={0}&filename={1}">下载</a>'
                                .format(encodeURI(rowObject.filePath), encodeURI(rowObject.fileName));
            else return '';
        }
        },
        {label: '备注', name: 'remark'}
    ];
    colModels.cisEvaluate = [
        {label: '形成日期', name: 'createDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, frozen: true},
        {label: '工作证号', name: 'cadre.user.code', frozen: true},
        {label: '考察对象', name: 'cadre.user.realname', frozen: true},
        {label: '所在单位及职务', name: 'cadre.title', align: 'left', width: 300},
        {
            label: '材料类型', name: 'type', formatter: function (cellvalue, options, rowObject) {
            return _cMap.CIS_EVALUATE_TYPE_MAP[cellvalue];
        }
        },
        {
            label: '材料内容', name: 'filePath', formatter: function (cellvalue, options, rowObject) {
            if (rowObject.fileName && rowObject.fileName != '')
                return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/swf/preview?path={0}&filename={1}">查看</a>'
                                .format(encodeURI(rowObject.filePath), encodeURI(rowObject.fileName))
                        + '&nbsp;<a href="${ctx}/attach/download?path={0}&filename={1}">下载</a>'
                                .format(encodeURI(rowObject.filePath), encodeURI(rowObject.fileName));
            else return '';
        }
        },
        {label: '备注', name: 'remark'}
    ];

    colModels.cadreCrpRecord = [

        {label: '挂职类型', name: 'type', formatter: function (cellvalue, options, rowObject) {
            return _cMap.CRP_RECORD_TYPE_MAP[cellvalue];
        }},
        {label: '时任职务', name: 'presentPost', width: 250},
        {
            label: '委派单位', name: 'toUnitType', formatter: function (cellvalue, options, rowObject) {

            if(rowObject.type=='${CRP_RECORD_TYPE_IN}'){
                return rowObject.toUnit;
            }

            if(rowObject.type=='${CRP_RECORD_TYPE_OUT}'){
                if (cellvalue == undefined) return '-';
                return _cMap.metaTypeMap[cellvalue].name +
                        ((cellvalue == '${cm:getMetaTypeByCode("mt_temppost_out_unit_other").id}') ? ("：" + rowObject.toUnit) : "");
            }
        }, width: 150
        },
        {
            label: '挂职类别', name: 'tempPostType', formatter: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '-';
            var postCodeOther = (rowObject.type=='${CRP_RECORD_TYPE_IN}')?
                    '${cm:getMetaTypeByCode("mt_temppost_in_post_other").id}':
                    '${cm:getMetaTypeByCode("mt_temppost_out_post_other").id}';
            return _cMap.metaTypeMap[cellvalue].name +
                    ((cellvalue == postCodeOther) ? ("：" + rowObject.tempPost) : "");
        }, width: 100
        },
        {label: '挂职项目', name: 'project', width: 300},
        {label: '挂职单位及所任职务', name: 'title', width: 300},
        {label: '挂职开始时间', name: 'startDate', width: 120, formatter: 'date', formatoptions: {newformat: 'Y-m'}},
        {label: '挂职结束时间', name: 'endDate', width: 120, formatter: 'date', formatoptions: {newformat: 'Y-m'}}
    ];
</script>