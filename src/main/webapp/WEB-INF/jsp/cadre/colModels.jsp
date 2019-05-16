<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script>
    var colModels = function () {
    };
    colModels.cadre = [
        {label: '工作证号', name: 'code', width: 110, frozen: true},
        {
            label: '姓名', name: 'realname', width: 120, formatter: function (cellvalue, options, rowObject) {
            return $.cadre(rowObject.id, cellvalue);
        }, frozen: true
        },
        <shiro:hasPermission name="cadre:changeOrder">
        {
            label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder, frozen: true
        },
        </shiro:hasPermission>
        <c:if test="${status==CADRE_STATUS_MIDDLE||status==CADRE_STATUS_MIDDLE_LEAVE}">
        <c:if test="${_p_hasKjCadre}">
        {label: '类型', name: 'type', width: 90, formatter: function (cellvalue, options, rowObject) {
            if($.trim(cellvalue)=='') return '--';
            return _cMap.CADRE_TYPE_MAP[cellvalue]
        }},
        </c:if>
        </c:if>
        {label: '${_pMap['cadreStateName']}', name: 'state', formatter: $.jgrid.formatter.MetaType},
        {label: '部门属性', name: 'unit.unitType.name', width: 150},
        {label: '所在单位', name: 'unitId', width: 200, align:'left', formatter: $.jgrid.formatter.unit},
        {label: '现任职务', name: 'post', align: 'left', width: 350},
        {label: '是否<br/>常委', name: 'isCommitteeMember', width: 50, formatter: $.jgrid.formatter.TRUEFALSE},
        {label: '所在单位及职务', name: 'title', align: 'left', width: 350},
        {label: '行政级别', name: 'adminLevel', formatter:$.jgrid.formatter.MetaType},
        {label: '职务属性', name: 'postType', width: 150, formatter:$.jgrid.formatter.MetaType},
        {label: '是否正职', name: 'isPrincipalPost', width: 80, formatter: $.jgrid.formatter.TRUEFALSE},
        { label: '是否<br/>班子负责人',name: 'leaderType', width: 120, formatter:function(cellvalue, options, rowObject){
            if(cellvalue==undefined) return '--';
            return _cMap.UNIT_POST_LEADER_TYPE_MAP[cellvalue];
        }},
        {label: '性别', name: 'gender', width: 50, formatter:$.jgrid.formatter.GENDER},
        {label: '民族', name: 'nation', width: 60},
        {label: '籍贯', name: 'nativePlace', width: 120},
        {label: '出生地', name: 'user.homeplace', width: 120},
        {label: '身份证号', name: 'idcard', width: 170},
        {label: '出生时间', name: 'birth', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}},
        {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE},
        {label: '党派', name: '_cadreParty', width: 80, formatter: $.jgrid.formatter.cadreParty},
        {label: '党派<br/>加入时间', name: '_growTime', formatter: $.jgrid.formatter.growTime},
        {label: '党龄', name: '_growAge', width: 50, formatter: $.jgrid.formatter.growAge},
        {
            label: '参加工作时间', name: 'workTime', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}
        },
        {label: '到校时间', name: 'arriveTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}},
        {label: '最高学历', name: 'eduId', formatter: $.jgrid.formatter.MetaType},
        {label: '最高学位', name: 'degree'},
        {label: '毕业时间', name: 'finishTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
        {label: '学习方式', name: 'learnStyle', formatter: $.jgrid.formatter.MetaType},
        {label: '毕业学校', name: 'school', width: 150},
        {
            label: '学校类型', name: 'schoolType', formatter: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '--';
            return _cMap.CADRE_SCHOOL_TYPE_MAP[cellvalue]
        }
        },
        {label: '所学专业', name: 'major', width: 180, align: 'left'},
        {
            label: '全日制教育学历', name: 'fulltimeEdu', width: 130, formatter: function (cellvalue, options, rowObject) {
            var cadreEdus = rowObject.cadreEdus;
            if (cadreEdus == undefined || cadreEdus == null || cadreEdus[0] == undefined || cadreEdus[0].eduId == undefined) return '--';
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
                if (cadreEdus == undefined || cadreEdus == null || cadreEdus[0] == undefined) return '--';
                return cadreEdus[0].school + cadreEdus[0].dep + cadreEdus[0].major;
            }
        },
        {
            label: '在职教育学历', name: 'onjobEdu', width: 120, formatter: function (cellvalue, options, rowObject) {
            var cadreEdus = rowObject.cadreEdus;
            if (cadreEdus == undefined || cadreEdus == null || cadreEdus[1] == undefined || cadreEdus[1].eduId == undefined) return '--';
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
                if (cadreEdus == undefined || cadreEdus == null || cadreEdus[1] == undefined) return '--';
                return cadreEdus[1].school + cadreEdus[1].dep + cadreEdus[1].major;
            }
        },
        {label: '岗位类别', name: 'postClass'},
        {label: '主岗等级', name: 'mainPostLevel', width: 150},
        {label: '专业技术职务', name: 'proPost', width: 120},
        {label: '专技职务<br/>评定时间', name: 'proPostTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
        {label: '专技岗位等级', name: 'proPostLevel', width: 150},
        {
            label: '专技岗位<br/>分级时间',
            name: 'proPostLevelTime',
            formatter: $.jgrid.formatter.date,
            formatoptions: {newformat: 'Y.m'}
        },
        {label: '管理岗位等级', name: 'manageLevel', width: 150},
        {
            label: '管理岗位<br/>分级时间',
            name: 'manageLevelTime',
            formatter: $.jgrid.formatter.date,
            formatoptions: {newformat: 'Y.m'}
        },
        {
            label: '现职务任命文件',
            width: 150,
            name: 'mainCadrePost.dispatchCadreRelateBean.first',
            formatter: function (cellvalue, options, rowObject) {
                if (!cellvalue || cellvalue.id == undefined) return '--';
                var dispatchCode = cellvalue.dispatchCode;

                return $.swfPreview(cellvalue.file, cellvalue.fileName, dispatchCode, dispatchCode);
            }
        },
        {
            label: '任现职时间',
            name: 'lpWorkTime',
            formatter: $.jgrid.formatter.date,
            formatoptions: {newformat: 'Y-m-d'}
        },
        {
            label: '现职务<br/>始任时间',
            name: 'npWorkTime',
            formatter: $.jgrid.formatter.date,
            formatoptions: {newformat: 'Y-m-d'}
        },
        {
            label: '现职务<br/>始任年限',
            name: 'cadrePostYear',
            formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--';
                return cellvalue == 0 ? "未满一年" : cellvalue;
            }
        },
        {
            label: '现职级<br/>始任时间',
            name: 'sWorkTime',
            formatter: $.jgrid.formatter.date,
            formatoptions: {newformat: 'Y-m-d'}
        },
        {
            label: '任现职级年限',
            width: 120,
            name: 'adminLevelYear',
            formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--';
                return cellvalue == 0 ? "未满一年" : cellvalue;
            }
        },
        {label: '是否<br/>双肩挑', width:60, name: 'isDouble', formatter: $.jgrid.formatter.TRUEFALSE},
        /*{
            label: '双肩挑单位',
            name: 'doubleUnitId',
            width: 150,
            formatter: $.jgrid.formatter.unit
        },*/
        {
            label: '双肩挑单位', name: 'doubleUnitIds',width: 250,formatter: function (cellvalue, options, rowObject) {

            if($.trim(cellvalue)=='') return '--'
            return ($.map(cellvalue.split(","), function(unitId){
                return $.jgrid.formatter.unit(unitId);
            })).join("，")

        }},
        {label: '联系方式', name: 'mobile', width: 120},
        /*{ label: '办公电话', name: 'phone' },
         { label: '家庭电话', name: 'homePhone' },*/
        {label: '电子邮箱', name: 'email', width: 180, align:'left'},
        <c:if test="${_p_hasPartyModule}">
        {
            label: '所属党组织',
            name: 'partyId',
            align: 'left',
            width: 550,
            formatter: function (cellvalue, options, rowObject) {
                return $.party(rowObject.partyId, rowObject.branchId);
            }
        },
        </c:if>
        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
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
        </shiro:lacksPermission>
        {label: '备注', name: 'remark', width: 150, formatter: $.jgrid.formatter.htmlencodeWithNoSpace}
    ];
    colModels.cadre2 = [
        {label: '工作证号', name: 'code', width: 110, frozen: true},
        {
            label: '姓名', name: 'realname', width: 120, formatter: function (cellvalue, options, rowObject) {
            return $.cadre(rowObject.id, cellvalue);
        }, frozen: true
        },
        {label: '部门属性', name: 'unit.unitType.name', width: 150},
        {label: '所在单位', name: 'unitId', width: 200, align:'left', formatter: $.jgrid.formatter.unit},
        {label: '所在单位及职务', name: 'title', align: 'left', width: 350},
        {label: '性别', name: 'gender', width: 50, formatter:$.jgrid.formatter.GENDER},
        {label: '身份证号', name: 'idcard', width: 170},
        {label: '出生时间', name: 'birth', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}},
        {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE},
        {label: '最高学历', name: 'eduId', formatter: $.jgrid.formatter.MetaType},
        {label: '专业技术职务', name: 'proPost', width: 120},
        {
            label: '任现职时间',
            name: 'lpWorkTime',
            formatter: $.jgrid.formatter.date,
            formatoptions: {newformat: 'Y-m-d'}
        },
        {label: '行政级别', name: 'adminLevel', formatter:$.jgrid.formatter.MetaType},
        {label: '职务属性', name: 'postType', width: 150, formatter:$.jgrid.formatter.MetaType},
        {label: '党派', name: '_cadreParty', width: 80, formatter: $.jgrid.formatter.cadreParty},
        {label: '党派加入时间', name: '_growTime', width: 120, formatter: $.jgrid.formatter.growTime},
        {label: '联系方式', name: 'mobile', width: 120},
        {label: '电子邮箱', name: 'email', width: 180}
    ];

    colModels.cadreLeave = [
        {label: '工作证号', name: 'code', width: 110, frozen: true},
        {
            label: '姓名', name: 'realname', width: 120, formatter: function (cellvalue, options, rowObject) {
            return $.cadre(rowObject.id, cellvalue);
        }, frozen: true
        },
        <shiro:hasPermission name="cadre:changeOrder">
        {
            label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder, frozen: true
        },
        </shiro:hasPermission>
        <c:if test="${status==CADRE_STATUS_MIDDLE||status==CADRE_STATUS_MIDDLE_LEAVE}">
        <c:if test="${_p_hasKjCadre}">
        {label: '类型', name: 'type', width: 90, formatter: function (cellvalue, options, rowObject) {
            if($.trim(cellvalue)=='') return '--';
            return _cMap.CADRE_TYPE_MAP[cellvalue]
        }},
        </c:if>
        </c:if>
        {label: '${_pMap['cadreStateName']}', name: 'state', formatter: $.jgrid.formatter.MetaType},
        {label: '所在单位', name: 'unitId', width: 200, align:'left', formatter: $.jgrid.formatter.unit},
        {label: '原职务', name: 'post', width: 350, align: 'left'},
        {label: '离任后所在单位及职务', name: 'title', width: 350, align:'left'},
        <c:if test="${status==CADRE_STATUS_MIDDLE_LEAVE||status==CADRE_STATUS_LEADER_LEAVE}">
        {
            label: '离任文件', name: 'dispatch', width: 180, formatter: function (cellvalue, options, rowObject) {

            if (cellvalue == undefined) return '--';
            return $.swfPreview(cellvalue.file, cellvalue.fileName, cellvalue.dispatchCode, cellvalue.dispatchCode);

        }, frozen: true
        },
        {label: '离任日期', name: 'dispatch.workTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
        </c:if>
        {label: '原行政级别', name: 'adminLevel', formatter:$.jgrid.formatter.MetaType},
        {label: '原职务属性', name: 'postType', width: 150, formatter:$.jgrid.formatter.MetaType},
        {label: '手机号', name: 'mobile', width: 120},
        {label: '办公电话', name: 'phone'},
        {label: '家庭电话', name: 'homePhone'},
        {label: '电子邮箱', name: 'email', width: 180},
        {label: '备注', name: 'remark', width: 150, formatter: $.jgrid.formatter.htmlencodeWithNoSpace}
    ];
    colModels.cadreLeave2 = [
        {label: '工作证号', name: 'code', width: 110, frozen: true},
        {label: '姓名', name: 'realname', width: 120, frozen: true},
        {label: '原所在单位', name: 'unit.name', width: 200},
        {label: '离任后所在单位及职务', name: 'title', width: 350, align:'left'},
        {label: '原行政级别', name: 'adminLevel', formatter:$.jgrid.formatter.MetaType},
        {label: '原职务属性', name: 'postType', width: 150, formatter:$.jgrid.formatter.MetaType},
        {label: '手机号', name: 'mobile', width: 120},
        {label: '电子邮箱', name: 'email', width: 180}
    ];

    colModels.cadreEdu = [
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
        {
            label: '学校类型', name: 'schoolType', formatter: function (cellvalue, options, rowObject) {
            return _cMap.CADRE_SCHOOL_TYPE_MAP[cellvalue]
        }, width: 90
        },

        //{label: '学制', name: 'schoolLen', width:50},
        {label: '学习方式', name: 'learnStyle', formatter: $.jgrid.formatter.MetaType},
        {label: '是否获得学位', name: 'hasDegree', formatter: $.jgrid.formatter.TRUEFALSE},
        {
            label: '学位', name: 'degree', formatter: function (cellvalue, options, rowObject) {
            return rowObject.hasDegree ? cellvalue : "-";
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
                if (cellvalue != undefined) {
                    var filePaths = cellvalue.split(",");
                    filesArray.push('<a class="various" rel="group{2}" title="证件{1}" data-fancybox-type="image" data-path="{0}" href="${ctx}/pic?path={0}">证件{1}</a>'.format(encodeURI(filePaths[0]), 1, rowObject.id));
                    if (filePaths.length == 2)
                        filesArray.push('<a class="various" rel="group{2}" title="证件{1}" data-fancybox-type="image" data-path="{0}"  href="${ctx}/pic?path={0}">证件{1}</a>'.format(encodeURI(filePaths[1]), 2, rowObject.id));
                }

                return filesArray.join("，");
            }
        },{label: '补充说明', name: 'note', width: 280},
        {label: '备注', name: 'remark', width: 180, align:'left'},  {hidden: true, key: true, name: 'id'}];

    colModels.cadreBook = [
        {label: '出版日期', name: 'pubTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}, frozen: true},
        {label: '著作名称', name: 'name', width: 350, align:'left'},
        {label: '出版社', name: 'publisher', width: 280, align:'left'},
        {
            label: '类型', name: 'type', width: 120, formatter: function (cellvalue, options, rowObject) {
            return _cMap.CADRE_BOOK_TYPE_MAP[cellvalue]
        }
        },
        {label: '备注', name: 'remark', width: 350, align:'left'}, {hidden: true, key: true, name: 'id'}
    ];

    colModels.cadreWork = [
        {label: '开始日期', name: 'startTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
        {label: '结束日期', name: 'endTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
        {label: '工作单位及担任职务（或专技职务）', name: 'detail', width: 380},
        {label: '工作类型', name: 'workType', width: 140, formatter: $.jgrid.formatter.MetaType},
        {label: '是否担任领导职务', name: 'isCadre', width: 150, formatter: $.jgrid.formatter.TRUEFALSE},
        {label: '备注', name: 'remark', width: 150},
        {
            label: '干部任免文件', name: 'dispatchCadreRelates', formatter: function (cellvalue, options, rowObject) {
            if(cellvalue==undefined) return ''
            var count = cellvalue.length;
            <shiro:lacksPermission name="${PERMISSION_CADREADMIN}">
            if(count==0) return ''
            </shiro:lacksPermission>
            return  _.template($("#dispatch_select_tpl").html().NoMultiSpace())
            ({id: rowObject.id, cadreId: rowObject.cadreId, count: count});
        }, width: 120
        }, {hidden: true, key: true, name: 'id'}
    ];

    colModels.cadrePaper = [
        {label: '发表日期', name: 'pubTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}, frozen: true},
        {label: '论文题目', name: 'name', width: 650, align:'left'},
        {label: '期刊名称', name: 'press', width: 350, align:'left'},
        /*{label: '论文', name: 'fileName', width: 150},*/
        {
            label: '预览', width: 70, formatter: function (cellvalue, options, rowObject) {

            return $.swfPreview(rowObject.filePath, rowObject.fileName, "预览");
        }
        },
        {label: '备注', name: 'remark', width: 150, align:'left'}, {hidden: true, key: true, name: 'id'}
    ];

    colModels.cadreReward = [
        {label: '奖励级别', name: 'rewardLevel', width: 90,
            formatter: $.jgrid.formatter.MetaType,
            cellattr: function (rowId, val, rowObject, cm, rdata) {
            if($.trim(rowObject.rewardLevel)=='')
                return "class='danger'";
        }, frozen: true},
        {label: '获奖年份', name: 'rewardTime', formatter: $.jgrid.formatter.date,
            formatoptions: {newformat: 'Y'}, frozen: true},
        {label: '获得奖项', name: 'name', width: 350, align:'left', frozen: true},
        {label: '颁奖单位', name: 'unit', width: 280, align:'left', cellattr: function (rowId, val, rowObject, cm, rdata) {
            if($.trim(val)=='')
                return "class='danger'";
        }},
        {
            label: '获奖证书', name: 'proof', width: 250,
            formatter: function (cellvalue, options, rowObject) {
                if (rowObject.proof == undefined) return '--';
                return '<a class="various" title="{1}" data-path="{0}" data-fancybox-type="image" href="${ctx}/pic?path={0}">{1}</a>'
                        .format(encodeURI(rowObject.proof), rowObject.proofFilename);
            }
        },
        {label: '是否独立获奖', name: 'isIndependent', width: 120, formatter: $.jgrid.formatter.TRUEFALSE},
        {
            label: '排名', name: 'rank', formatter: function (cellvalue, options, rowObject) {
            if (rowObject.isIndependent) return '--';
            if (cellvalue == undefined) return '--';
            return '第{0}'.format(cellvalue);
        }, cellattr: function (rowId, val, rowObject, cm, rdata) {
            if(!rowObject.isIndependent && $.trim(rowObject.rank)=='')
                return "class='danger'";
        }},
        {label: '备注', name: 'remark', width: 350}, {hidden: true, key: true, name: 'id'}
    ];

    colModels.cadreResearch = [
        {
            label: '项目起始时间',
            width: 120,
            name: 'startTime',
            formatter: $.jgrid.formatter.date,
            formatoptions: {newformat: 'Y.m'},
            frozen: true
        },
        {
            label: '项目结题时间',
            width: 120,
            name: 'endTime',
            formatter: $.jgrid.formatter.date,
            formatoptions: {newformat: 'Y.m'},
            frozen: true
        },
        {label: '项目名称', name: 'name', width: 450, align:'left'},
        {label: '项目类型', name: 'type', width: 400, align:'left'},
        {label: '委托单位', name: 'unit', width: 300, align:'left'},
        {label: '备注', name: 'remark', width: 250}, {hidden: true, key: true, name: 'id'}
    ];

    colModels.cadreParttime = [
        {label: '起始时间', name: 'startTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}, frozen: true},
        {label: '结束时间', name: 'endTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}, frozen: true},
        {label: '兼职单位', name: 'unit', width: 380, align:'left'},
        {label: '兼任职务', name: 'post', width: 280, align:'left'},
        {label: '备注', name: 'remark', width: 150, align:'left'}, {hidden: true, key: true, name: 'id'}
    ];

    colModels.cadrePostPro = [
        {label: '是否当前专技岗位', width: 150, name: 'isCurrent',formatter: function (cellvalue, options, rowObject) {
            return cellvalue ? "是" : "否";
        }, frozen:true},
        {label: '岗位类别', width: 120, name: 'type', formatter: $.jgrid.formatter.MetaType, frozen:true},
        {label: '职级', name: 'postLevel', frozen:true},
        {label: '专业技术职务', name: 'post', width: 250, formatter: $.jgrid.formatter.MetaType},
        {label: '专技职务任职时间', name: 'holdTime', width: 150, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
        {label: '专技岗位等级', name: 'level', width: 160, formatter: $.jgrid.formatter.MetaType},
        {label: '专技岗位分级时间', name: 'gradeTime', width: 150, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
        {label: '专技岗位备注', name: 'remark', width: 350}, {hidden: true, key: true, name: 'id'}
    ];

    colModels.cadrePostAdmin = [
        {label: '是否当前管理岗位', width: 150, name: 'isCurrent',formatter: function (cellvalue, options, rowObject) {
            return cellvalue ? "是" : "否";
        }},
        {label: '管理岗位等级', name: 'level', width: 150, formatter: $.jgrid.formatter.MetaType},
        {label: '管理岗位分级时间', name: 'gradeTime', width: 150, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
        {label: '管理岗位备注', name: 'remark', width: 350}, {hidden: true, key: true, name: 'id'}
    ];

    colModels.cadrePostWork = [
        {label: '是否当前工勤岗位', width: 150, name: 'isCurrent',formatter: function (cellvalue, options, rowObject) {
            return cellvalue ? "是" : "否";
        }},
        {label: '工勤岗位等级', name: 'level', width: 150, formatter: $.jgrid.formatter.MetaType},
        {label: '工勤岗位分级时间', name: 'gradeTime', width: 150, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
        {label: '工勤岗位备注', name: 'remark', width: 350}, {hidden: true, key: true, name: 'id'}
    ];

    colModels.cadreTrain = [
        {label: '起始时间', name: 'startTime', formatter: $.jgrid.formatter.date,
            formatoptions: {newformat: 'Y.m.d'}, frozen: true},
        {label: '结束时间', name: 'endTime', formatter: $.jgrid.formatter.date,
            formatoptions: {newformat: 'Y.m.d'}, frozen: true},
        {label: '培训内容', name: 'content', width: 550, align:'left'},
        {label: '主办单位', name: 'unit', width: 280},
        {label: '备注', name: 'remark', width: 350}, {hidden: true, key: true, name: 'id'}
    ];

    colModels.cadreFamily = [
        {
            label: '称谓', name: 'title', frozen: true, formatter: function (cellvalue, options, rowObject) {
            return _cMap.CADRE_FAMILY_TITLE_MAP[cellvalue]
        }
        },
        <shiro:hasPermission name="${PERMISSION_CADREADMIN}">
        {
                label: '排序',formatter: $.jgrid.formatter.sortOrder, width:90,
                formatoptions: {url: "${ctx}/cadreFamily_changeOrder",grid:'#jqGrid_cadreFamily'}
        },
        </shiro:hasPermission>
        {label: '姓名', width: 120, name: 'realname'},
        {label: '出生年月', name: 'birthday', formatter: function (cellvalue, options, rowObject) {
            if(rowObject.withGod) return '--'
            return $.date(cellvalue, "yyyy.MM");
        },
            cellattr: function (rowId, val, rowObject, cm, rdata) {
            if(!rowObject.withGod && $.trimHtml(val)=='')
                return "class='danger'";
        }},
        {label: '政治面貌', name: 'politicalStatus', formatter: $.jgrid.formatter.MetaType},
        {label: '工作单位及职务', name: 'unit', width: 650, align:"left"}, {hidden: true, key: true, name: 'id'}
    ];

    colModels.cadreFamilyAbroad = [
        {
            label: '称谓',
            name: 'cadreFamily.title',
            frozen: true,
            formatter: function (cellvalue, options, rowObject) {
                return _cMap.CADRE_FAMILY_TITLE_MAP[cellvalue]
            }
        },
        {label: '姓名', name: 'cadreFamily.realname'},
        {label: '移居国家（地区）', name: 'country', width: 200},
        {label: '现居住城市', name: 'city', width: 150}, {hidden: true, key: true, name: 'id'},
        {label: '移居时间', name: 'abroadTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
        {label: '移居类别', name: 'type', formatter: $.jgrid.formatter.MetaType}
    ];

    colModels.cadreCourse = [
        {
            label: '类型', name: 'type', width: 120, formatter: function (cellvalue, options, rowObject) {
            return _cMap.CADRE_COURSE_TYPE_MAP[cellvalue]
        }
        },
        {label: '课程名称', name: 'name', width: 550, align:'left'},
        <c:if test="${param._sort ne 'no' && (cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth)}">
        {
            label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
            formatoptions:{grid:'#jqGrid_cadreCourse', url: "${ctx}/cadreCourse_changeOrder?cadreId=${param.cadreId}"}, frozen: true
        },
        </c:if>
        {label: '备注', name: 'remark', width: 350}, {hidden: true, key: true, name: 'id'}
    ];

    colModels.cisInspectObj = [
        {label: '编号', name: 'sn', width: 180, frozen: true},
        {label: '考察日期', name: 'inspectDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}, frozen: true},
        {label: '工作证号', name: 'cadre.code', frozen: true},
        {label: '考察对象', name: 'cadre.realname', formatter: function (cellvalue, options, rowObject) {
            return $.cadre(rowObject.cadre.id, cellvalue);
        }, frozen: true},
        {label: '所在单位及职务', name: 'post', align: 'left', width: 200},
        {label: '拟任职务', name: 'assignPost', align: 'left', width: 200},
        {
            label: '考察主体', name: '_inspectorType', formatter: function (cellvalue, options, rowObject) {
            var type = _cMap.CIS_INSPECTOR_TYPE_MAP[rowObject.inspectorType];
            if (rowObject.inspectorType == '<%=CisConstants.CIS_INSPECTOR_TYPE_OTHER%>') {
                type += "：" + rowObject.otherInspectorType;
            }
            return type;
        }, width: 200
        },
        {label: '考察组负责人', name: 'chiefInspector.user.realname', width: 120, formatter: function (cellvalue, options, rowObject) {

            var val = $.trim(cellvalue);
            if(val=='') return '--'
            if(rowObject.chiefInspector.status!='<%=CisConstants.CIS_INSPECTOR_STATUS_NOW%>'){
                return '<span class="delete">{0}</span>'.format(val)
            }
            return val
        }},
        {
            label: '考察组成员', name: 'inspectors', formatter: function (cellvalue, options, rowObject) {
            if (rowObject.inspectorType == '<%=CisConstants.CIS_INSPECTOR_TYPE_OTHER%>') return '--'
            if (cellvalue == undefined || cellvalue.length == 0) return '--';
            var names = []
            cellvalue.forEach(function(inspector, i){
                if (inspector.user.realname)
                    names.push(inspector.user.realname)
            })

            return names.join("，")
        }, width: 250
        },
        /*{label: '谈话人数', name: 'talkUserCount'},*/
        {
            label: '考察材料', name: 'summary', formatter: function (cellvalue, options, rowObject) {
            if (rowObject.summary && rowObject.summary != ''){
                var str = '';
                <c:if test="${empty type}">
                str +='<button class="openView btn btn-info btn-xs" data-url="${ctx}cisInspectObj_summary?objId={0}"><i class="fa fa-search"></i> 查看</button>'
                        .format(rowObject.id) + "&nbsp;";
                </c:if>
                return  str + '<button class="downloadBtn btn btn-success btn-xs" data-url="${ctx}/cisInspectObj_summary_export?objId={0}"><i class="fa fa-download"></i> 导出</button>'
                                .format(rowObject.id);
            } else return '<button class="openView btn btn-primary btn-xs" data-url="${ctx}cisInspectObj_summary?objId={0}"><i class="fa fa-edit"></i> 编辑</button>'
                    .format(rowObject.id)
        }, width: 150
        },
        {
            label: '考察原始记录', formatter: function (cellvalue, options, rowObject) {

            var ret = "-";
            var logFile = rowObject.logFile;
            if ($.trim(logFile) != '') {

                var code =  _cMap.metaTypeMap[rowObject.typeId].name + "[" + rowObject.year + "]" + rowObject.seq + "号";
                var fileName = code + "考察原始记录.pdf";
                ret = $.swfPreview(logFile, fileName, '<button class="btn btn-xs btn-warning"><i class="fa fa-search"></i> 预览</button>');
            }

            return ret;
        }
        },
        {
            label: '归档记录', name:'archiveId', width:80, formatter: function (cellvalue, options, rowObject) {
            if(cellvalue==undefined) return '--'
            return '<button class="linkBtn btn btn-xs btn-primary" data-url="#/sc/scAdArchive?objId={0}" data-target="_blank"><i class="fa fa-search"></i> 查看</button>'
                    .format(rowObject.id);
        }},
        {label: '备注', name: 'remark'}, {hidden: true, name: 'inspectorType'}
    ];
    colModels.cadreReport = [
        {label: '形成日期', name: 'createDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}, frozen: true},
        {label: '工作证号', name: 'cadre.code', frozen: true},
        {label: '姓名', name: 'cadre.realname', frozen: true},
        {label: '所在单位及职务', name: 'cadre.title', align: 'left', width: 300},
        {
            label: '材料内容', name: 'filePath', formatter: function (cellvalue, options, rowObject) {

            return $.swfPreview(rowObject.filePath, rowObject.fileName, "查看");
        }
        },
        {label: '备注', name: 'remark'}
    ];
    colModels.cisEvaluate = [
        {label: '形成日期', name: 'createDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}, frozen: true},
        {label: '工作证号', name: 'cadre.code', frozen: true},
        {label: '考察对象', name: 'cadre.realname', frozen: true},
        {label: '所在单位及职务', name: 'cadre.title', align: 'left', width: 450},
        {
            label: '材料类型', name: 'type', width: 120, formatter: function (cellvalue, options, rowObject) {
            return _cMap.CIS_EVALUATE_TYPE_MAP[cellvalue];
        }
        },
        /*{
            label: '材料内容', name: 'filePath', formatter: function (cellvalue, options, rowObject) {

            return $.swfPreview(rowObject.filePath, rowObject.fileName, "查看");
        }
        },*/
        {
                label: '材料内容', width: 200, align:'left', formatter: function (cellvalue, options, rowObject) {

                var ret = "-";
                var pdfFilePath = rowObject.pdfFilePath;
                if ($.trim(pdfFilePath) != '') {
                    var fileName = (rowObject.fileName || rowObject.id) + (pdfFilePath.substr(pdfFilePath.indexOf(".")));
                    //console.log(fileName + " =" + pdfFilePath.substr(pdfFilePath.indexOf(".")))
                    ret = '<button href="javascript:void(0)" data-url="${ctx}/swf/preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                                    .format(encodeURI(pdfFilePath), encodeURI(fileName))
                            + '&nbsp;<button data-url="${ctx}/attach/download?path={0}&filename={1}" title="下载PDF文件" class="downloadBtn btn btn-xs btn-warning"><i class="fa fa-file-pdf-o"></i> PDF</button>'
                                    .format(encodeURI(pdfFilePath), encodeURI(fileName));
                }
                var wordFilePath = rowObject.wordFilePath;
                if ($.trim(wordFilePath) != '') {

                    var fileName = (rowObject.fileName || rowObject.id) + (wordFilePath.substr(wordFilePath.indexOf(".")));
                    ret += '&nbsp;<button data-url="${ctx}/attach/download?path={0}&filename={1}"  title="下载WORD文件" class="downloadBtn btn btn-xs btn-success"><i class="fa fa-file-word-o"></i> DOC</button>'
                            .format(encodeURI(wordFilePath), encodeURI(fileName));
                }
                return ret;
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

            if(rowObject.type=='<%=CrpConstants.CRP_RECORD_TYPE_IN%>'){
                return rowObject.toUnit;
            }

            if(rowObject.type=='<%=CrpConstants.CRP_RECORD_TYPE_OUT%>'){
                if (cellvalue == undefined) return '--';
                return _cMap.metaTypeMap[cellvalue].name +
                        ((cellvalue == '${cm:getMetaTypeByCode("mt_temppost_out_unit_other").id}') ? ("：" + rowObject.toUnit) : "");
            }
        }, width: 150
        },
        {
            label: '挂职类别', name: 'tempPostType', formatter: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '--';
            var postCodeOther = (rowObject.type=='<%=CrpConstants.CRP_RECORD_TYPE_IN%>')?
                    '${cm:getMetaTypeByCode("mt_temppost_in_post_other").id}':
                    '${cm:getMetaTypeByCode("mt_temppost_out_post_other").id}';
            return _cMap.metaTypeMap[cellvalue].name +
                    ((cellvalue == postCodeOther) ? ("：" + rowObject.tempPost) : "");
        }},
        {label: '挂职项目', name: 'project', width: 300},
        {label: '挂职单位及所任职务', name: 'title', width: 300},
        {label: '挂职开始时间', name: 'startDate', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
        {label: '挂职结束时间', name: 'endDate', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}}
    ];
</script>