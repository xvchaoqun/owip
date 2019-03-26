<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script>
  var colModel = [
            { label: '详情', name: '_detail', width: 80, formatter:function(cellvalue, options, rowObject){
                return ('<button class="openView btn btn-primary btn-xs" ' +
                    'data-hide-el="#div-content" data-load-el="#div-content-view"' +
                    'data-url="${ctx}/unitTeamPlan?unitTeamId={0}&load=${param.load}"><i class="fa fa-search"></i> {1}</button>')
                        .format(rowObject.id, '详情');
            },frozen:true },
            {label: '行政班子名称', name: 'name', align: 'left', width: 300, frozen: true},
            {label: '届数', name: 'year', width: 60},
            {label: '是否现任班子', name: 'isPresent', formatter: $.jgrid.formatter.TRUEFALSE},
            {label: '任职文件', name: 'appoint.file', width: 180, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.appoint==undefined) return '--'
                return $.swfPreview(cellvalue, rowObject.name + "-任职文件", rowObject.appoint.dispatchCode);
            }},
            {label: '任职时间', name: 'appointDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '应换届时间', name: 'expectDeposeDate',
                cellattr: function (rowId, val, rowObject, cm, rdata) {

                    if(rowObject.deposeDate ==undefined && rowObject.expectDeposeDate !=undefined &&
                        $.date(new Date() , "yyyy-MM-dd")>= $.date(rowObject.expectDeposeDate, "yyyy-MM-dd"))
                        return "class='danger'";
                },
                formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '类型', name: '_type', width:180, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.deposeDate !=undefined){
                    return "已换届";
                }
                if(rowObject.expectDeposeDate ==undefined) return '--'

                if($.date(new Date() , "yyyy-MM-dd")>= $.date(rowObject.expectDeposeDate, "yyyy-MM-dd")){
                    return "未按时启动换届的单位"
                }
                <c:if test="${not empty _sysConfig.termEndDate}">
                if('${cm:formatDate(_sysConfig.termEndDate,'yyyy-MM-dd')}' >= $.date(rowObject.expectDeposeDate, "yyyy-MM-dd")){
                    return "本学期应启动换届单位"
                }
                </c:if>
                if('${_thisYear}'==$.date(rowObject.expectDeposeDate, "yyyy")){
                    return "本年度应启动换届单位"
                }
                return '其他时段应启动换届单位';
            }},
            {label: '免职文件', name: 'depose.file', align: 'left', width: 170, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.depose==undefined) return '--'
                return $.swfPreview(cellvalue, rowObject.name + "-免职文件", rowObject.depose.dispatchCode);
            }},
            {label: '免职时间', name: 'deposeDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            /*{label: '干部配置方案', name: '_untPosts'},
            {label: '行政正职', name: '_untPosts'},
            {label: '行政副职', name: '_untPosts'},*/
            {label: '备注', name: 'remark', align: 'left', width: 300},
        ]
</script>