<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script>
    var colModel = [
            {label: '工作证号', name: 'user.code', width: 110, frozen: true},
            {
                label: '姓名', name: 'user.realname', formatter: function (cellvalue, options, rowObject) {

                var str = '<i class="fa fa-user-circle-o purple" title="管理员"></i> ';
                <shiro:lacksPermission name="partyMember:archive">
                    return (rowObject.isAdmin?str:'')+ cellvalue;
                </shiro:lacksPermission>
                <shiro:hasPermission name="partyMember:archive">
                if(rowObject.cadre==undefined)
                    return (rowObject.isAdmin?str:'')+ cellvalue;

                var params = {params:'cls=1'};
                <c:if test="${empty param.isHistory}">
                params.loadId = 'body-content-view2';
                params.hideId = 'body-content-view';
                </c:if>
                return (rowObject.isAdmin?str:'')+ $.cadre(rowObject.cadre.id, cellvalue, params);
                </shiro:hasPermission>

            }, frozen: true
            },
            <c:if test="${empty param.isHistory}">
            {label: '是否离任', name: 'isHistory', width: 80, formatter:$.jgrid.formatter.TRUEFALSE,
                formatoptions:{on:'<span class="red bolder">是</span>', off:"否"}, frozen: true},
            </c:if>
            <c:if test="${not empty param.groupId}">
             <shiro:hasPermission name="partyMember:edit">
            {
                label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{grid:'#jqGrid2', url: "${ctx}/partyMember_changeOrder"}, frozen: true
            },
             </shiro:hasPermission>
             </c:if>
            <c:if test="${empty param.isHistory || param.isHistory!=1}">
            <shiro:hasPermission name="partyMember:edit">
            {label: '管理员', name: 'isAdmin',width:'80',formatter: function (cellvalue, options, rowObject) {
                if(rowObject.isHistory){
                    return '--'
                }
                if (cellvalue)
                 return '<button data-url="${ctx}/partyMember_admin?id={0}" data-msg="确定撤销该管理员？" data-loading="#body-content-view" data-callback="_adminCallback" class="confirm btn btn-danger btn-xs"><i class="fa fa-minus-circle"></i> 撤销</button>'.format(rowObject.id);
                else
                    return '<button data-url="${ctx}/partyMember_admin?id={0}" data-msg="确定设置该委员为管理员？" data-loading="#body-content-view" data-callback="_adminCallback" class="confirm btn btn-success btn-xs"><i class="fa fa-plus-circle"></i> 设置</button>'.format(rowObject.id);
            }},
            </shiro:hasPermission>
            </c:if>
            {label: '所在单位', name: 'unitId', width: 180, align:'left', formatter: $.jgrid.formatter.unit},
            {label: '所属${_p_partyName}', name: 'groupPartyId', width: 350, align:'left',formatter: function (cellvalue, options, rowObject) {
                return $.party(rowObject.groupPartyId);
            }},
            {label: '职务', name: 'postId', formatter:$.jgrid.formatter.MetaType},
            {
                label: '分工', name: 'typeIds', align:'left', width: 180, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--';
                var typeIdStrs = [];
                var typeIds = cellvalue.split(",");
                for(i in typeIds){
                    var typeId = typeIds[i];
                    //console.log(typeId)
                    if(typeId instanceof Function == false)
                        typeIdStrs.push($.jgrid.formatter.MetaType(typeId));
                }
                //console.log(typeIdStrs)
                return typeIdStrs.join(",");
            }
            },
            {label: '任职时间', name: 'assignDate', width: 80, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            <c:if test="${empty param.isHistory || param.isHistory==1}">
            {label: '离任时间', name: 'dismissDate', width: 80, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            </c:if>
            {
                label: '性别', name: 'gender', width: 50, formatter:$.jgrid.formatter.GENDER
            },
            {label: '民族', name: 'nation', width: 60},
            {label: '身份证号', name: 'idcard', width: 170},

            {
                label: '出生日期', name: 'birth', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}
            },
            {label: '政治面貌', name: '_cadreParty', width: 80, formatter: $.jgrid.formatter.cadreParty},
            {label: '党派加入时间', name: '_growTime', width: 120, formatter: $.jgrid.formatter.growTime},
            {label: '党龄', name: '_growAge', width: 50, formatter: $.jgrid.formatter.growAge},
            {label: '到校时间', name: 'arriveTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '岗位类别', name: 'postClass'},
            {label: '主岗等级', name: 'mainPostLevel', width: 150},
            {label: '专业技术职务', name: 'proPost', width: 120},
            {label: '职称级别', name: 'proPostLevel', width: 150},
            /*{label: '管理岗位等级', name: 'manageLevel', width: 150},*/
            { label: '办公电话', name: 'officePhone' },
            { label: '手机号', name: 'mobile', width: 110 },
            /*{
                label: '所在党组织',
                name: 'partyId',
                align: 'left',
                width: 550,
                formatter: function (cellvalue, options, rowObject) {
                    return $.party(rowObject.partyId, rowObject.branchId);
                }
            }*/
        ]
</script>