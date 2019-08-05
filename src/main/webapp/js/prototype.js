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
String.prototype.replaceAll = function(s1,s2){
    return this.replace(new RegExp(s1,"gm"),s2);
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
// 回车换行用空格代替
String.prototype.trim2=function() {
    return this.replace(/(^\s*)|(\s*$)/g,'').replace(/\r|\n|(\r\n)/g,' ');
};
/**把连续的空格替换成一个空格**/
String.prototype.NoMultiSpace = function()
{
    return this.replace(/\s+/g, " ").trim2();
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
// 移除span标签（保留文本）
String.prototype.removeSpan = function(){
    return this.replace(/<span[^\>]*>(.*?)<\/span>/g, '$1');
}

// 不足位数在前面填充0
if (!String.prototype.zfill) {
    String.prototype.zfill = function(len) {
        if (len == undefined || typeof len != 'number' || this.length >= len) {return this.toString()}
        return Array(len - this.length + 1).join('0') + this;
    }
}

// 不足位数在前面填充0
if (!Number.prototype.zfill) {
    Number.prototype.zfill = function(len) {
        if (len == undefined || typeof len != 'number' || this.toString().length >= len) {return this.toString()}
        return Array(len - this.toString().length + 1).join('0') + this;
    }
}

Math.formatFloat = function (f, digit) {
    return parseFloat(parseFloat(f).toFixed(digit));
    /*var m = Math.pow(10, digit);
     return parseInt(f * m, 10) / m;*/
}
Math.trimToZero = function (n) {
    return ($.trim(n) == '') ? 0 : n;
}

/*Array.prototype.indexOf = function(val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};*/
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
        "H+": this.getHours(), //小时
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



