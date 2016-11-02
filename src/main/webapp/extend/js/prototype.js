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

/** trim() method for String */
String.prototype.trim=function() {
    return this.replace(/(^\s*)|(\s*$)|\r|\n|(\r\n)/g,'');
};
/**把连续的空格替换成一个空格**/
String.prototype.NoMultiSpace = function()
{
    return this.replace(/\s{2}/g, " ").trim();
}

String.prototype.htmlencode = function(){
    var div = document.createElement('div');
    div.appendChild(document.createTextNode(this));
    return div.innerHTML;
}
String.prototype.htmldecode = function(){
    var div = document.createElement('div');
    div.innerHTML = this;
    return div.innerText || div.textContent;
}

Array.prototype.indexOf = function(val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};
Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1); // 会改变原始数组
    }
};

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

/* 计算两日期相差的日期年月日等 */
Date.prototype.dateDiff = function(interval,objDate2)
{
    var d=this, i={}, t=d.getTime(), t2=objDate2.getTime();
    i['y']=objDate2.getFullYear()-d.getFullYear();
    i['q']=i['y']*4+Math.floor(objDate2.getMonth()/4)-Math.floor(d.getMonth()/4);
    i['m']=i['y']*12+objDate2.getMonth()-d.getMonth();
    i['ms']=objDate2.getTime()-d.getTime();
    i['w']=Math.floor((t2+345600000)/(604800000))-Math.floor((t+345600000)/(604800000));
    i['d']=Math.floor(t2/86400000)-Math.floor(t/86400000);
    i['h']=Math.floor(t2/3600000)-Math.floor(t/3600000);
    i['n']=Math.floor(t2/60000)-Math.floor(t/60000);
    i['s']=Math.floor(t2/1000)-Math.floor(t/1000);
    return i[interval];
}

//计算月份差
function MonthDiff(date1,date2){
    //默认格式为"2003-03-03",根据自己需要改格式和方法
    var year1 =  date1.substr(0,4);
    var year2 =  date2.substr(0,4);
    var month1 = date1.substr(5,2);
    var month2 = date2.substr(5,2);
    var day1 = date1.substr(8,2);
    var day2 = date2.substr(8,2);

    var len=(year2-year1)*12+(month2-month1);

    if(len>0 && month1==month2 && day2<day1) // 月份相同，日在后面，则这个月不能算
        len--;

    return len;

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
        title:title/*,
        closeButton:false*/
    });
}