<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/pmd/constants.jsp"%>
<fmt:message key="global.session.timeout" bundle="${spring}" var="_timeout"/>
<script>
    var colModel =[
        { label: '账号', name: 'username', width: 140,frozen:true },
        { label: '学工号', name: 'code', width: 140, formatter:function(cellvalue, options, rowObject){
                return $.user(rowObject.id, cellvalue);
            },frozen:true },
        { label: '姓名',name: 'realname', width: 120, formatter:function(cellvalue, options, rowObject){
                return $.user(rowObject.id, cellvalue);
            },frozen:true  },
        { label:'头像', name: 'avatar', width: 50, formatter:function(cellvalue, options, rowObject){
                if($.trim(rowObject.username)=='') return ''

                <c:if test="${param.type=='admin'}">
                var html ='<img title="点击修改头像" src="${ctx}/avatar?path={0}&_={1}"'
                    +'class="avatar" data-id="{2}"'
                    +'data-hasimg="{3}" data-avatar="{4}">';
                html = html.format(rowObject.avatar, new Date().getTime(), rowObject.id, rowObject.avatar!='', rowObject.avatar);
                return html;
                </c:if>
                <c:if test="${param.type!='admin'}">
                return ('<a class="various" title="{2}" data-path="${ctx}/avatar?path={0}&_={1}" data-fancybox-type="image" href="${ctx}/avatar?path={0}&_={1}">'+
                    '<img title="点击放大头像" src="${ctx}/avatar?path={0}&_={1}" class="avatar"/>'
                    +'</a>')
                    .format(encodeURI(rowObject.avatar), new Date().getTime(), rowObject.realname + ".jpg");
                </c:if>

            },frozen:true},
        { label: '类别', name: 'type', formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '';
                return _cMap.USER_TYPE_MAP[cellvalue];
            }},
        { label: '性别',  name: 'gender', width: 50, formatter:$.jgrid.formatter.GENDER},
        { label: '系统角色',  name: 'roleIds', align:'left', width: 156 , formatter:function(cellvalue, options, rowObject){

                if(cellvalue==undefined) return '-'
                var roleIdArray = cellvalue.split(",");
                return $.map(roleIdArray, function(item){
                    var role = _cMap.roleMap[item];
                    return role?role.description:null;
                });
            } },
        <c:if test="${param.type=='admin'}">
        { label: '身份证号码',  name: 'idcard', width: 160 },
        { label: '办公电话',  name: 'phone', width: 150 },
        { label: '手机号',  name: 'mobile', width: 150 },
        { label: '邮箱',  name: 'email', width: 150 },
        </c:if>
        { label: '账号来源', name: 'source', formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '';
                return _cMap.USER_SOURCE_MAP[cellvalue];
            } },
        { label: '状态', name: 'lockedName', width: 60, formatter:function(cellvalue, options, rowObject){
                return (rowObject.locked)?"禁用":"正常";
            } },
        <c:if test="${param.type=='admin'}">
        { label: '登录超时', name: 'timeout', width: 80, formatter:function(cellvalue, options, rowObject){

                if(rowObject.timeout>0) return rowObject.timeout + "分钟";
                var loginTimeout = parseInt('${_sysConfig.loginTimeout}');
                if(loginTimeout>0) return loginTimeout + "分钟";

                return '${cm:stripTrailingZeros(_timeout/(60*1000))}' + "分钟";
            } },
        { label:'创建时间', name: 'createTime', width: 150 },
        {  hidden:true, name: 'locked',formatter:function(cellvalue, options, rowObject){
                return (rowObject.locked)?1:0;
            }}
        </c:if>
    ]
</script>