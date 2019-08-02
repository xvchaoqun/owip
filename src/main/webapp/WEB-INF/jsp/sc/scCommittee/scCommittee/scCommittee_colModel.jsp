<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script>
  var colModel = [
            {label: '年份', name: 'year', width: 60, frozen: true},
            {
                label: '编号', name: 'code', width: 200, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '--'
                if($.trim(rowObject.filePath)=='') return cellvalue;
                return $.pdfPreview(rowObject.filePath, cellvalue);
            }, frozen: true},
            {label: '党委常委会<br/>日期', name: 'holdDate', width: 95, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            { label: '议题数量',name: 'topicNum', width: 70},
            { label: '常委总数',name: 'committeeMemberCount', width: 70},
            { label: '应参会<br/>常委数',name: '_total', width: 60, formatter: function (cellvalue, options, rowObject) {
                return rowObject.count + rowObject.absentCount;
            }},
            {
                label: '实际参会<br/>常委数', name: 'count', width: 70, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==0) return '--'
                return ('<a href="javascript:;" class="popupBtn bolder" ' +
                'data-url="${ctx}/sc/scCommitteeMember?committeeId={0}&isAbsent=0"><u>{1}</u></a>')
                        .format(rowObject.id, cellvalue);
            }},
            {
                label: '请假<br/>常委数', name: 'absentCount', width: 60, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==0) return '--'
                return ('<a href="javascript:;" class="popupBtn bolder" ' +
                'data-url="${ctx}/sc/scCommitteeMember?committeeId={0}&isAbsent=1"><u>{1}</u></a>')
                        .format(rowObject.id, cellvalue);
            }},
            { label: '列席人',name: 'attendUsers', width: 140, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.attendUsers==undefined) return '--';
                if(rowObject.attendUsers.length<=8) return cellvalue;
                return rowObject.attendUsers.substring(0,8) + "...";
            },cellattr:function(rowId, val, rowObject, cm, rdata) {
                if(rowObject.attendUsers!=undefined)
                    return "title='"+rowObject.attendUsers+"'";
            }},
            /*{ label: '列席人',name: 'attendUsers', width: 400,align:'left'},*/
            {label: '会议记录', name: 'logFile', width: 80, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.logFile==undefined) return '--';
                return $.pdfPreview(rowObject.logFile, '会议记录', '<button class="btn btn-xs btn-primary"><i class="fa fa-search"></i> 查看</button>');
            }},
            {label: '上会PPT', name: 'pptFile', width: 80, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.pptFile==undefined) return '--';
                return ('&nbsp;<button class="downloadBtn btn btn-warning btn-xs" ' +
                'data-url="${ctx}/attach_download?path={0}&filename={1}"><i class="fa fa-download"></i> 下载</button>')
                        .format(rowObject.pptFile, rowObject.code+"(上会PPT)")
            }},
            {
                label: '查看<br/>讨论议题', width: 80, formatter: function (cellvalue, options, rowObject) {

                return '<button class="loadPage btn btn-primary btn-xs" data-url="${ctx}/sc/scCommittee?cls=2&committeeId={0}"><i class="fa fa-search"></i> 查看</button>'
                        .format(rowObject.id);
            }
            },
            { label: '备注',name: 'remark', width: 320}
        ]
</script>
