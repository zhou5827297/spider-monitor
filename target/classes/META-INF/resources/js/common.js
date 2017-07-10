//var REMOTEPATH = "http://localhost:7777/";
//var REMOTEPATH = "http://192.168.0.156:7777/";
var REMOTEPATH = "/";
var ROOTPATH = "http://192.168.0.127:8888/html/";
var REPLACESTR = "/data/spider_cluster/data/snapshot/html/";
//var ROOTPATH = "http://192.168.0.127:9999/html/";
//var REPLACESTR = "/data/spider_cluster/test/snapshot/html/";

var SUBSCRIBE_REMOTEPATH = "http://subscribe.dangmeitoutiao.com/";

/**
 * 状态码值转换
 * @param statusNum
 * @returns {*}
 */
var trustStatus = function (statusNum) {
    var status = statusNum;
    //1：待执行，2：执行中，3：执行成功，4：执行失败，5：执行超时
    switch (statusNum) {
        case 1:
            status = '待执行';
            break;
        case 2:
            status = '执行中';
            break;
        case 3:
            status = '执行成功';
            break;
        case 4:
            status = '执行失败';
            break;
        case 5:
            status = '执行超时';
            break;
    }
    return status;
}
/**
 * 日期格式化
 * @param fmt
 * @returns {*}
 * @constructor
 */
Date.prototype.Format = function (fmt) {
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

/**
 * 组装查询条件参数
 * @param model
 * @returns {string}
 */
var constructReqParams = function (model) {
    var params = '';
    angular.forEach(model, function (item, key, array) {
        if (item) {
            params = params + '&' + key + "=" + item;
        }
    });
    return params;

}
