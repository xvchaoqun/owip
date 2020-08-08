<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script>
    var colModel = [
        <c:if test="${param.type=='list'}">
        {label: '年度', name: 'year', width: 80},
        </c:if>
        {label: '工作证号', name: 'cadre.code', width: 110, frozen: true},
        {
            label: '姓名', name: 'cadre.realname', width: 120, formatter: function (cellvalue, options, rowObject) {
                return $.cadre(rowObject.cadre.id, cellvalue, '${param.type!='list'?'_blank':''}');
            }, frozen: true
        },
        {
            label: '类别', name: 'type', width: 60, formatter: function (cellvalue, options, rowObject) {

                var cls = "text-success";
                if(cellvalue==<%=ScConstants.SC_BORDER_ITEM_TYPE_CHANGE%>){
                    cls = "text-primary"
                }else if(cellvalue==<%=ScConstants.SC_BORDER_ITEM_TYPE_DELETE%>){
                    cls = "text-danger"
                }

                return ('<span class="{0} bolder">'+_cMap.SC_BORDER_ITEM_TYPE_MAP[cellvalue] + '</span>')
                    .format(cls)
            }, frozen: true
        },
        {label: '报备时所作单位及职务', name: 'title', width: 320, align: 'left'},
        {label: '行政级别', name: 'adminLevel', formatter: $.jgrid.formatter.MetaType},
         <c:if test="${param.type=='list'}">
         {label: '报备编号', name: 'code', width: 250, frozen: true},
            {label: '报备日期', name: 'recordDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
        {
                label: '报备表', name: '_file', width: 120, formatter: function (cellvalue, options, rowObject) {

                    var ret = "-";
                    var pdfFilePath = rowObject.addFile;
                    if(rowObject.type==<%=ScConstants.SC_BORDER_ITEM_TYPE_CHANGE%>){
                        pdfFilePath = rowObject.changeFile;
                    }else if(rowObject.type==<%=ScConstants.SC_BORDER_ITEM_TYPE_DELETE%>){
                          pdfFilePath = rowObject.deleteFile;
                    }

                    if ($.trim(pdfFilePath) != '') {
                        ret = '<button href="javascript:void(0)" data-url="${ctx}/pdf_preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                                .format(pdfFilePath, '报备表')
                            + '&nbsp;<button data-url="${ctx}/attach_download?path={0}&filename={1}" title="下载PDF文件" class="downloadBtn btn btn-xs btn-warning"><i class="fa fa-file-pdf-o"></i> PDF</button>'
                                .format(pdfFilePath,  '报备表');
                    }
                    return ret;
                }
            },
        {
                label: '电子报备', name: 'recordFile', width: 80, formatter: function (cellvalue, options, rowObject) {

                    var ret = "-";
                    var pdfFilePath = rowObject.recordFile;
                    if ($.trim(pdfFilePath) != '') {
                        ret = '<button data-url="${ctx}/attach_download?path={0}&filename={1}" title="下载文件" class="downloadBtn btn btn-xs btn-warning"><i class="fa fa-download"></i> 下载</button>'
                                .format(pdfFilePath,  '电子报备');
                    }
                    return ret;
                }
            },
        </c:if>
        {
            label: '报备涉及的任免文件', name: 'dispatchCadreIds', width: 180, formatter: function (cellvalue, options, rowObject) {

                var files = [];
                if(cellvalue!=undefined) files = cellvalue.split(",");
                return ('<button class="popupBtn btn {0} btn-xs" ' +
                    '        data-url="${ctx}/sc/scBorderItem_addDispatchs?id={1}&cadreId={3}" ' +
                    '        data-width="1000"><i class="fa fa-link"></i> ' +
                    '        任免文件({2}) ' +
                    '    </button>').format(files.length == 0 ? "btn-success" : "btn-warning",
                    rowObject.id, files.length, rowObject.cadreId)
            }
        },
        {label: '备注', name: 'remark', width: 320, align: 'left'}
    ]
</script>