<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script>
  var colModel =  [
<c:if test="${param.cls!=-1}">
      {label: '年份', name: 'year', width: 80, frozen: true},
    </c:if>
      {label: '调阅日期', name: 'accessDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
          <c:if test="${param.cls!=-1}">
      {
          label: '调阅函', name: 'accessFile', width: 80, formatter: function (cellvalue, options, rowObject) {
          return $.swfPreview(rowObject.accessFile, "调阅函", "查看");
      }
      },
      </c:if>
      {
          label: '调阅单位', width: 200, name: 'unitId', formatter: $.jgrid.formatter.unit, frozen: true
      },
<c:if test="${param.cls!=-1}">
      {
          label: '调阅类型', name: '_isCopy', formatter: function (cellvalue, options, rowObject) {
          return (rowObject.isCopy) ? '复印件' : '原件';
      }
      },
    </c:if>
      {label: '调阅用途', name: 'purpose', width: 230},
<c:if test="${param.cls!=-1}">
      {
          label: '调阅明细', name: '_items', formatter: function (cellvalue, options, rowObject) {
          return ('<button class="popupBtn btn btn-link btn-xs" ' +
          'data-url="${ctx}/sc/scMatterAccess_items?accessId={0}">查看</button>')
                  .format(rowObject.id);
      }
      },
    </c:if>
      {label: '办理日期', name: 'handleDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
      {label: '经办人', name: 'handleUser.realname'},
<c:if test="${param.cls!=-1}">
      {label: '接收人', name: 'receiver'},
      {
          label: '接收手续', name: 'receivePdf', width: 80, formatter: function (cellvalue, options, rowObject) {
          if ($.trim(rowObject.receivePdf) == '') return ''
          return '<a class="various" title="{1}" data-path="{0}" data-fancybox-type="image" href="${ctx}/pic?path={0}">查看</a>'
                  .format(encodeURI(rowObject.receivePdf), "接收手续.jpg");
      }
      },
      {
          label: '归还时间', name: 'returnDate', formatter: function (cellvalue, options, rowObject) {
          if (rowObject.isCopy) return '-'
          if (cellvalue == undefined) return ''
          return $.date(cellvalue, "yyyy-MM-dd");
      }
      },
      {
          label: '归还接收人', name: 'returnUser.realname', formatter: function (cellvalue, options, rowObject) {
          if (rowObject.isCopy) return '-'
          return $.trim(cellvalue)
      }
      },
    </c:if>
      {label: '备注', name: 'remark'}, {hidden: true, name: 'isCopy'}
  ]
</script>
