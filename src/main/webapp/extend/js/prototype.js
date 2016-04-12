String.prototype.endWith=function(s){
    if(s==null||s==""||this.length==0||s.length>this.length)
        return false;
    if(this.substring(this.length-s.length)==s)
        return true;
    else
        return false;
    return true;
}

String.prototype.startWith=function(s){
    if(s==null||s==""||this.length==0||s.length>this.length)
        return false;
    if(this.substr(0,s.length)==s)
        return true;
    else
        return false;
    return true;
}

String.prototype.format = function()
{
    var args = arguments;
    return this.replace(/\{(\d+)\}/g,                
        function(m,i){
            return args[i];
        });
}
String.prototype.NoSpace = function()
{
    return this.replace(/\s+/g, "");
}

Date.prototype.format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

//计算天数差的函数，通用
function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式
    var  aDate,  oDate1,  oDate2,  iDays
    aDate  =  sDate1.split("-")
    oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    //转换为12-18-2006格式
    aDate  =  sDate2.split("-")
    oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])
    iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数
    return  iDays
}


var SysMsg = {};
SysMsg.error = function(msg, title){
    $("body").css('padding-right','0px');
    bootbox.alert(msg);
    //toastr.error(msg, title);
}
SysMsg.warning = function(msg, title){
    $("body").css('padding-right','0px');
    //toastr.warning(msg, title);
    bootbox.alert(msg);
}
SysMsg.success = function(msg, title, callback){
    $("body").css('padding-right','0px');
    //toastr.success(msg, title);
    bootbox.alert({
        message:msg,
        callback:callback,
        title:title
    });
}
SysMsg.info = function(msg, title, callback){
    $("body").css('padding-right','0px');
    //toastr.info(msg, title);
    bootbox.alert({
        message:msg,
        callback:callback,
        title:title
    });
}
SysMsg.confirm = function(msg, title, callback){
    $("body").css('padding-right','0px');
    //toastr.success(msg, title);
    bootbox.confirm({
        message:msg,
        callback:callback,
        title:title,
        closeButton:false
    });
}