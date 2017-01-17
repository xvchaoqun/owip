<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script>
    var colModels = function(){};
    colModels.cadre = [
        {label: '工作证号', name: 'user.code', width: 100, frozen: true},
        {
            label: '姓名', name: 'user.realname', width: 120, formatter: function (cellvalue, options, rowObject) {
            return '<a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?cadreId={0}">{1}</a>'
                    .format(rowObject.id, cellvalue);
        }, frozen: true
        },
        {
            label: '排序', width: 80, index: 'sort', formatter: function (cellvalue, options, rowObject) {
            return _.template($("#sort_tpl").html().NoMultiSpace())({id: rowObject.id})
        }, frozen: true
        },
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
        {
            label: '是否正职', name: 'mainCadrePost.postId', formatter: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '-';
            return _cMap.postMap[cellvalue].boolAttr ? "是" : "否"
        }
        },
        {
            label: '性别', name: 'gender', width: 50, formatter: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '-';
            return _cMap.GENDER_MAP[cellvalue];
        }
        },
        {label: '民族', name: 'nation', width: 60},
        {label: '籍贯', name: 'nativePlace', width: 120},
        {label: '出生地', name: 'user.homeplace', width: 120},
        {label: '身份证号', name: 'idcard', width: 170},
        {label: '出生时间', name: 'birth', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
        {
            label: '年龄', name: 'birth', width: 50,
            formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return yearOffNow(cellvalue);
            }
        },
        {
            label: '党派', name: 'isDp', width: 80, formatter: function (cellvalue, options, rowObject) {

            if (!rowObject.isDp && rowObject.growTime != undefined) return "中共党员";
            if (rowObject.isDp) return _cMap.metaTypeMap[rowObject.dpTypeId].name;
            return "-";
        }
        },
        {
            label: '党派加入时间', name: 'growTime', width: 120, formatter: function (cellvalue, options, rowObject) {

            if (rowObject.isDp && rowObject.dpAddTime != undefined) return rowObject.dpAddTime.substr(0, 10);
            if (rowObject.growTime != undefined) return rowObject.growTime.substr(0, 10);
            return "-"
        }
        },
        {
            label: '参加工作时间', width: 120, formatter: function (cellvalue, options, rowObject) {
            return "-"
        }
        },
        {label: '到校时间', name: 'arriveTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
        {
            label: '最高学历', name: 'eduId', formatter: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '-';
            return _cMap.metaTypeMap[cellvalue].name
        }
        },
        {label: '最高学位', name: 'degree'},
        {label: '毕业时间', name: 'finishTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}},
        {
            label: '学习方式', name: 'learnStyle', formatter: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '-';
            return _cMap.metaTypeMap[cellvalue].name
        }
        },
        {label: '毕业学校', name: 'school', width: 150},
        {
            label: '学校类型', name: 'schoolType', formatter: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '-';
            return _cMap.CADRE_SCHOOL_TYPE_MAP[cellvalue]
        }
        },
        {label: '所学专业', name: 'major'},
        {label: '岗位类别', name: 'postClass'},
        {label: '主岗等级', name: 'mainPostLevel', width: 150},
        {label: '专业技术职务', name: 'proPost', width: 120},
        {label: '专技职务评定时间', name: 'proPostTime', width: 130, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
        {label: '专技岗位等级', name: 'proPostLevel', width: 150},
        {label: '专技岗位分级时间', name: 'proPostLevelTime', width: 130, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
        {label: '管理岗位等级', name: 'manageLevel', width: 150},
        {label: '管理岗位分级时间', name: 'manageLevelTime', width: 130, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
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
            label: '现职务始任年限',
            width: 120,
            name: 'mainCadrePost.dispatchCadreRelateBean.first.workTime',
            formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                var year = yearOffNow(cellvalue);
                return year == 0 ? "未满一年" : year;
            }
        },
        {
            label: '现职级始任时间',
            width: 120,
            name: 'presentAdminLevel.startDispatch.workTime',
            formatter: 'date',
            formatoptions: {newformat: 'Y-m-d'}
        },
        {
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
        },
        {
            label: '是否双肩挑', name: 'mainCadrePost.isDouble', formatter: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '-';
            return cellvalue ? "是" : "否";
        }
        },
        {
            label: '双肩挑单位',
            name: 'mainCadrePost.doubleUnitId',
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
                if (rowObject.partyId == undefined) return '';
                var party = _cMap.partyMap[rowObject.partyId].name;
                if (rowObject.branchId != undefined)
                    var branch = _cMap.branchMap[rowObject.branchId].name;
                return party + (($.trim(branch) == '') ? '' : '-' + branch);
            }
        },
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
        {label: '备注', name: 'remark', width: 150}
    ];

    colModels.cadreLeave = [
        {label: '工作证号', name: 'user.code', width: 100, frozen: true},
        {
            label: '姓名', name: 'user.realname', width: 120, formatter: function (cellvalue, options, rowObject) {
            return '<a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?cadreId={0}">{1}</a>'
                    .format(rowObject.id, cellvalue);
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
        {label: '兼职单位', name: 'unit', width: 250},
        {label: '兼职职务', name: 'post', width: 150},
        {label: '报批单位', name: 'reportUnit', width: 280},
        {label: '批复文件', name: 'paper', width: 250,
            formatter: function (cellvalue, options, rowObject) {
                if(rowObject.paper==undefined) return '-';
                /*return '<a href="${ctx}/attach/download?path={0}&filename={1}">{2}</a>'
                        .format(encodeURI(rowObject.paper),encodeURI(rowObject.paperFilename), rowObject.paperFilename);*/

                if (rowObject.paperFilename && rowObject.paperFilename != '')
                    return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/swf/preview?path={0}&filename={1}">预览</a>'
                                    .format(encodeURI(rowObject.paper), encodeURI(rowObject.paperFilename))
                            + '&nbsp;&nbsp;<a href="${ctx}/attach/download?path={0}&filename={1}">下载</a>'
                                    .format(encodeURI(rowObject.paper), encodeURI(rowObject.paperFilename));
                else return '';

            }},
        {label: '备注', name: 'remark', width: 350}, {hidden: true, name: 'id'}
    ];
</script>