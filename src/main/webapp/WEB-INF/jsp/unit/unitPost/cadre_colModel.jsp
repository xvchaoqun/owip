<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script>
    var colModel = [
        {label: '工作证号', name: 'cadre.code', width: 110, frozen: true},
        {
            label: '姓名', name: 'cadre.realname', width: 120, formatter: function (cellvalue, options, rowObject) {
                return $.cadre(rowObject.cadre.id, cellvalue);
            }, frozen: true
        },
        {label: '所在单位及职务', name: 'cadre.title', align: 'left', width: 350},
        {label: '行政级别', name: 'cadre.adminLevel', formatter: $.jgrid.formatter.MetaType},
        {label: '职务属性', name: 'cadre.postType', width: 150, formatter: $.jgrid.formatter.MetaType},
        {label: '是否正职', name: 'cadre.isPrincipalPost', width: 80, formatter: $.jgrid.formatter.TRUEFALSE},
        {
            label: '是否<br/>班子负责人', name: 'leaderType', width: 120, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--';
                return _cMap.UNIT_POST_LEADER_TYPE_MAP[cellvalue];
            }
        },
        {label: '性别', name: 'cadre.gender', width: 50, formatter: $.jgrid.formatter.GENDER},
        {label: '民族', name: 'cadre.nation', width: 60},
        {label: '出生时间', name: 'cadre.birth', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}},
        {label: '年龄', name: 'cadre.birth', width: 50, formatter: $.jgrid.formatter.AGE},
        {label: '党派', name: '_cadreParty', width: 80, formatter: $.jgrid.formatter.cadreParty, formatoptions:{useCadre:true}},
        {label: '党派<br/>加入时间', name: '_growTime', formatter: $.jgrid.formatter.growTime, formatoptions:{useCadre:true}},
        {
            label: '参加工作时间', name: 'cadre.workTime', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}
        },
        {label: '最高学历', name: 'cadre.eduId', formatter: $.jgrid.formatter.MetaType},
        {label: '最高学位', name: 'cadre.degree'},
        {label: '所学专业', name: 'cadre.major', width: 180, align: 'left'},
        {label: '专业技术职务', name: 'cadre.proPost', width: 120},
        {
            label: '任现职时间',
            name: 'cadre.lpWorkTime',
            formatter: $.jgrid.formatter.date,
            formatoptions: {newformat: 'Y-m-d'}
        },
        {
            label: '现职务<br/>始任时间',
            name: 'cadre.npWorkTime',
            formatter: $.jgrid.formatter.date,
            formatoptions: {newformat: 'Y-m-d'}
        },
        {
            label: '现职务<br/>始任年限',
            name: 'cadre.cadrePostYear',
            formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--';
                return cellvalue == 0 ? "未满一年" : cellvalue;
            }
        },
        {
            label: '现职级<br/>始任时间',
            name: 'cadre.sWorkTime',
            formatter: $.jgrid.formatter.date,
            formatoptions: {newformat: 'Y-m-d'}
        },
        {
            label: '任现职级年限',
            width: 120,
            name: 'cadre.adminLevelYear',
            formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--';
                return cellvalue == 0 ? "未满一年" : cellvalue;
            }
        }
    ]
</script>