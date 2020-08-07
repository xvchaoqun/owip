<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="_dpEdu_needSubject" value="${_pMap['dpEdu_needSubject']=='true'}"/>
<script>
    var dpColModels = function () {
    };
    dpColModels.dpFamily = [
        {label: '称谓', name: 'title', frozen: true, formatter: $.jgrid.formatter.MetaType},
        {
            label: '排序',formatter: $.jgrid.formatter.sortOrder, width:90,
            formatoptions: {url: "${ctx}/dp/dpFamily_changeOrder",grid:'#jqGrid2'}
        },
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
        {label: '工作单位及职务', name: 'unit', width: 650, align:"left"},
        {label: '备注', name: 'remark', width: 300},{hidden: true, key: true, name: 'id'}
    ];
    dpColModels.dpReward = [
        { label: '获奖类别',name: 'rewardType',width:100,formatter:$.jgrid.formatter.MetaType},
        {label: '奖励级别', name: 'rewardLevel', width: 90,
            formatter: $.jgrid.formatter.MetaType,
            cellattr: function (rowId, val, rowObject, cm, rdata) {
                if($.trim(rowObject.rewardLevel)=='')
                    return "class='danger'";
            }, frozen: true},
        { label: '获奖年月',name: 'rewardTime',formatter:$.jgrid.formatter.date,
            formatoptions:{newformat:'Y.m'},frozen:true},
        { label: '获得奖项',name: 'name',width:350,align:'left',frozen:true},
        { label: '颁奖单位',name: 'unit',width:200,align:'left'},
        { label: '获奖证书',name: 'proof',width:200,
            formatter:function (cellvalue, options, rowObject) {
                return $.imgPreview(rowObject.proof, rowObject.proofFilename)
            }},
        {label: '是否独立获奖', name: 'isIndependent', width: 120, formatter: $.jgrid.formatter.TRUEFALSE},
        { label: '排名',name: 'rank',formatter:function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--';
                return '第{0}'.format(cellvalue);
            },cellattr:function (rowId, val, rowObject, cm, rdata) {
                if ($.trim(rowObject.rank)=='')
                    return "class='danger'";
            }},
        { label: '备注',name: 'remark',width:350},
        {hidden: true, key: true, name: 'id'}
    ];
    dpColModels.dpEdu = [
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
        {label: '备注', name: 'remark', width: 180, align:'left'},  {hidden: true, key: true, name: 'id'}];
</script>