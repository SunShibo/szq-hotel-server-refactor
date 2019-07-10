var HOME = "home.html",
    LOGIN = "login.html";
var LEAVETIME = ' 14:00:00';
var PAYJSON = {
    "msgTp": "0200",
    "payTp": "0",
    "procTp": "00",
    "procCd": "000000",
    "amt": "0.01",
    "orderNo": "201712011721520"
};

var OUTPAYJSON = {
    "msgTp": "0200",
    "procTp": "00",
    "procCd": "000000",
    "amt": "0.01",
    "orderNo": "201712011721520"
};

var payValue = {
    integValue: null,
    streValue: null,
    integral: null
};

//身份证号码正则
var IDREG = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
//港澳通行证
var HMPASSCHECK = /^([A-Z]\d{6,10}(\(\w{1}\))?)$/;
//台湾通行证
var TAIWANPASS = /^\d{8}|^[a-zA-Z0-9]{10}|^\d{18}$/;
//护照
var PASSPORT = /^([a-zA-z]|[0-9]){5,17}$/;
//军官证
var OFFICERS = /^[\u4E00-\u9FA5](字第)([0-9a-zA-Z]{4,8})(号?)$/;


var api = {
    queryUserIntgeralInfo: '/integral/queryUserIntgralInfo?v=1'//积分查询?userId=3
    , addIntegral: '/integral/updateAddIntegral?v=1'//积分增加?userId=3&number=20
    , deductionIntegral: '/integral/updateIntegral?v=1'//积分减少?userId=3&number=20
    , queryMember: '/member/detailList'//会员查询?query=张三
    , addMember: '/member/add?v=1'//会员信息添加?
    , updateMember: '/member/update?v=1'//会员信息修改?userId
    , memberLevel: '/member/cartLeaveDetail?v=1'//获取会员级别
    , certificate: '/certificateType/queryAll?v=1'//获取证件
    , getMemberByCre: '/user/queryByCredentialNumber?credentialNumber='//根据证件号码获取会员信息
    , getHouseType: '/room/houseType?v=1'//获取房屋类型
    , getHouse: '/room/roomTypeMain?v=1'//获取房间信息
    , yuImport: '/manage/upload'//预导入
    , import: '/manage/channel'//导入
    , exportExcel: '/manage/outExce?v=1'//导出会员信息
    , offMember: '/member/logout?userId='//注销会员userId
    , roomDelete: '/room/deleteRoom?v=1'
    , roomQuery: '/room/roomTypeMain?v=1'
    , roomLockRoom: '/room/lockRoom?v=1'
    , roomUnLock: '/room/openLockRoom?v=1'
    , queryMemberById: '/member/detail?userId='//根据会员id查询
    , roomAdd: '/room/addRoomInfo'//新增客房
    , pickRoomQuery: '/room/QueryRoomInfoByState?v=1'//选房查询
    , checkIn: '/checkin/addCheckin?v=1'//入住
    , yueCheckIn: '/user/queryUserMake?v=1'//预约入住信息查询
    , roomQueryById: '/room/toUpdateRoomInfo?v=1'//按roomId查询
    , roomHouseType: '/room/houseType?v=1'
    , queryRoomInfo: '/room/queryRoomTypeAndHotelAndFloor'
    , querySale: '/user/queryDiscountId?cerfiticateNumber='//根据证件号查询折扣信息
    , queryOrder: '/orderInfo/OrderInfoList'
    , queryIdFlag: 'user/queryByCredentialNumber?v=1'//通过手机号或者证件号查询会员信息String credentialNumber,String phoneNumber
    , queryOrderInfo: 'OrderManage/queryOrderInfoByUser'//查询子订单信息
    , queryRowsOrderInfo: 'OrderManage/queryOrderInfoById'//点击表格查询子订单信息
    , checkInYuYue: '/checkin/subscribeCheckin?v=1'//预约入住
    , refundOnclick: '/OrderManage/refundOnclick?v=1'//获取结账信息
    , cash: '/OrderManage/cash'//押金
    , refundprice: '/OrderManage/refundprice?v=1'//退款
    , info: '/OrderManage/info?v=1'//其他金额
    , outRoom: '/OrderManage/outRoomClick'//查看退房信息
    , outRoomBtn: '/OrderManage/outRoom'//退房
    , refund: '/OrderManage/refundOnclick'//结账
    , subitemOnclick: 'OrderManage/subitemOnclick'//结账
    , refundBtn: '/OrderManage/refund'//结账
    , outRoomRefound: '/OrderManage/outRoomRefound'//退房回滚
    , exemption: '/OrderManage/exemption'//免单
    , updatePriceTime: '/room/updatePriceTime'//查询修改时间是否冲突
    , updateRoomInfo: '/room/updateRoomInfo'//修改客房信息
    , largeUpdatePriceTime: '/room/largeUpdatePriceTime'//批量修改全天房时间
    , updatePriceAll: '/room/largeUpdatePriceTimeByOtherPrice'//批量修改其他价格  除全天房以外的其他价格
    , reserveRoom: '/subcribe/phoneSubcribe'//预定房间
    , stamp: '/OrderManage/stamp' //打印数据
    , priceAll: '/room/queryRoomPrice'//查询当前roomId的所有未过期价格
    , queryCondition: '/dealShiftServiceController/queryCondition'//交班信息
    , offDuty: '/dealShiftServiceController/offDuty'//确定交班
    , beforeOrder: 'OrderManage/closeAccounts'//已结账的订单查询详情
    , cancelOrder: '/subcribe/cancelTheReservation'//取消预约  取消入住待支付
    , memberPrice: '/MemberPrice/detail?id='//根据会员级别获取价格
    , memberUpdatePrice: '/MemberPrice/update'//根据会员级修改价格
    , queryHouseType: '/room/selectHouseType'//查询房型
    , toHouseType: '/room/toHouseType'//按ID查询房型信息
    , updateHouseType: '/room/updateHouseType'//修改房型
    , queryPettyCash: '/dealShiftServiceController/queryPettyCash?v=1'//备用金查询
    , addPettyCash: '/dealShiftServiceController/addPettyCash?v=1'//备用金增减
    , channelDiscount: '/CooperativePrice/detail?id='
    , allChannel: '/CooperativePrice/query?v=1'
    , addChannel: '/CooperativePrice/add?name='
    , updateChannel: 'CooperativePrice/update'
    , homePage: '/roomMain/homePageInfo?v=1'//获取首页数据
    , cashPay: '/checkin/cashPayment'//现金支付接口
    , queryPayment: '/checkin/queryPayment'//查询订单应支付金额
    , homeItemInfo: '/roomDetailsController/queryRoomInfo'//首页弹出层信息
    , delRoomItemPrice: '/room/deleteRoomPrice'//删除特殊价格
    , handoverList: '/dealShiftServiceController/queryAllDS'//交班列表
    , operationLog: '/roomDetailsController/operationLog'//首页弹出层操作记录
    , checkIdentify: '/checkin/isCheckinByIdNumber?idNumber='//查看这个证件号码是否有在住信息
    , homeRoomType: '/roomMain/homePageHouse'//首页获取房间类型信息
    , cashInfo: '/cashAccount/info'//现金订单
    , cashAdd: '/cashAccount/add'//商品交易添加
    , stayOver: '/roomDetailsController/stayOver'//续住
    , stayOverPay: '/roomDetailsController/stayOverPay'//续住后支付
    , updateRoomComment: '/room/updateRoomComment'//首页房屋备注
    , updateMainCommment: '/order/updateMainCommment'//首页房屋备注
    , updateCheckInComment: '/order/updateCheckInRemark'//首页入住房屋备注
    , cleanThe: '/roomDetailsController/cleanThe'//待打扫变空房
    , hotelInfo: '/hotel/queryLoginHotel'//权限页酒店list
    , hotel: '/OrderManage/hotel'//权限页酒店list
    , detail: '/admin/detail'//权限列表
    , getReport: '/room/todayPictureView?v=1'
    , getRoomInfoById: '/roomDetailsController/roomChange?roomId='
    , changeRoomPay: '/checkin/roomChangePay?v=1'
    , adminAdd: '/admin/add'//添加权限
    , adminQuery: '/admin/query'//按ID查找权限
    , adminUpdate: '/admin/update'//权限修改
    , updateStatus: '/admin/updateStatus'//权限注销
    , reportList: '/room/pictureView?v=1'//按时间段查询图表
    , getCardNoByLeaveId: '/member/queryCart?leaveId='//根据级别id查询卡费用信息
    , getCardUpdate: '/member/queryCartUpdate?v=1'//根据用户id卡级别判断是否缴费member/queryCartUpdate?userId=1&leaveId=1
    , cartInfo: '/Cart/info?v=1'//会员卡设置
    , cartDelete: '/Cart/delete?v=1'//会员卡删除
    , cartExport: '/Cart/export?v=1'//会员卡导出
    , cartAdd: '/Cart/add?v=1'//会员卡添加
    , cartDetail: '/Cart/detail?v=1'//会员卡回显
    , cartUpdate: '/Cart/update?v=1'//会员卡编辑
    , checkRoomInfo: '/room/ifCheckIn'//判断选的房间是否可用
    , updatePassWord: 'login/updatePassWord'//修改密码
    , changeHotel: 'login/changeHotel'//切换酒店
    , logout: '/login/logout?v=1'//退出登录
    , getScheduleById: '/order/queryOrderMsg'//根据订单号码查询预订信息
    , updateFixRoomState: '/room/updateFixRoomState'//更改维修状态
    , queryStatement: '/order/queryStatement'//在住报表和预离店报表
    , addMemberLev: '/MemberPrice/add?v=1'//添加会员级别
    , getEditScheduleRoom: '/room/UpdateOrderInfoBySelectRoom?v=1'//修改预定房间模块获取房间信息
    , getEditScheduleRoomType: '/room/houseTypeAndPhoneAndNet?v=1'//修改预定房间类型模块获取房间信息
    , queryClassess: '/classes/queryClassess'//查询当前酒店下的所有班次
    , deleteClasses: '/classes/deleteClasses'//删除班次
    , addClasses: 'classes/addClasses'//添加班次
    , updateClasses: '/classes/updateClasses'//修改班次
    , queryClassessByHotelId: '/classes/queryClassessByHotelId'//按酒店查班次
    , cartLogout: '/Cart/logout'//会员卡注销
    , cashStamp: 'cashAccount/stamp'//打印
    , addMaseTo: '/roomDetailsController/addMaseTo'//添加同来
    , waitIn: '/checkin/laterCheckIn?v=1'//稍后入住
    , jointHousingt: '/OrderManage/jointHousing '//联房按钮
    , calcPrice2: '/room/updateOrderInfo?v=1'//预定修改，选房确定后需要调用算价格
    , calcPrice1: '/room/sumRoomPriceAndHouseTypePrice?v=1'//选房确定后需要调用算价格
    , scheduleUpdate: '/subcribe/updateSub?v=1'//选房确定后需要调用算价格
    , otherView: '/room/otherView'//数据表格
    , orderInfoHistory: 'OrderManage/orderInfoHistory'//消费明细
    , queryOrderByRoom: 'OrderManage/queryOrderByRoom'//商品交易挂账
    , buying: 'OrderManage/buying'//挂账
    , FormAccountDetail: '/FormAccountDetailController/FormAccountDetail'//收银报表
    , FormManangeResponse: '/FormAccountDetailController/FormManangeResponse'//管理层报表
    , cancelSubQuery: '/subcribe/cancelSubQuery'//取消查询
    , cancelSubUpdate: '/subcribe/cancelSubUpdate'//取消订单
    , beforehandDuty: '/dealShiftServiceController/beforehandDuty'//预交班
    , subitem: '/OrderManage/subitem'//子订单结账
    , stamOrder: '/OrderManage/stamOrder'//在住打印
    , addHouseType: '/room/addHouseType'//添加房型
    , queryRoomPerson: 'roomDetailsController/queryRoomPerson'//查询同来人
    , delRoomPerson: '/roomDetailsController/delRoomPerson'//删除同来人
    , updateRoomPerson: '/roomDetailsController/updateRoomPerson'//修改同来人
    , addRoomPerson: '/roomDetailsController/addRoomPerson'//添加同来人
    , deleteHouseType: '/room/deleteHouseType'//删除房型
    , join: '/OrderManage/join'//加入联房
    , dismiss: '/OrderManage/dismiss'//解散联房
    , addHotel: 'hotel/addHotel'//添加酒店
    , queryHotelInfo: 'hotel/queryHotel'//查询酒店
    , updateHotelInfo: 'hotel/updateHotel'//编辑酒店
    , queryStoreValue: '/checkin/queryStoreValue'//查询会员积分和储值
    , queryFloor:'floor/queryFloor'//查询楼层
    , addFloor:'floor/addFloor'//添加楼层
    , updateFloor:'floor/updateFloor'//修改楼层
    , deleteFloor:'floor/deleteFloor'//删除楼层
}
layui.use(['jquery', 'element'], function () {
    $ = layui.jquery;
    element = layui.element;
    $(document).on("click", '.outLogin', function () {
        $.ajax({
            url: api.logout,
            type: 'GET',
            dataType: "json",
            success: function (rs) {
                location.href = LOGIN
            }
        })
    })
    $(document).on('click', '#updatePass', function () {
        layer.open({
            type: 2,
            skin: 'demo-class',
            area: ['500px', '300px'],
            title: '修改密码',
            shade: 0.6,
            shadeClose: true,
            content: 'iframe_updatePass.html',
            end: function (index, layero) {
                location.reload();
            }
        });
    });
    $(document).on('click', '#updateHotel', function () {
        layer.open({
            type: 2,
            skin: 'demo-class',
            area: ['500px', '300px'],
            title: '更换酒店',
            shade: 0.6,
            shadeClose: true,
            content: 'iframe_updateHotel.html',
            end: function (index, layero) {
                location.reload();
            }
        });
    });
    //点击扫描证件事件
    $(document).on('click', '#IDCard', function () {
        var CVR_IDCard = IDCardReader();
        if (!!CVR_IDCard) {
            clearIDForm(form, 'formData');
            form.val('formData', {
                IDCard: CVR_IDCard.CardNo
            });
            //设置会员详情
            setUserInfo(CVR_IDCard.CardNo, function (res) {
                $('.integral').text(res.integral);
                $('.prepaidCard').text(res.streValue);
                //存储会员信息
                for (var item in res) {
                    payValue[item] = res[item];
                }
                $('.optional_1').show();
            });
        }
        //显示会员详情
        $('.optional_1').show();
    });
    // //输入框的值改变时触发
    // $("#card").on("input", );

    renderMenu($, element);
    // renderReport($,element)
})


//清空表单
function clearIDForm(form, formName) {
    //重新初始化信息
    for (var item in payValue) {
        payValue[item] = null;
    }
    form.val(formName, {
        IDCard: '',
        glx: ''
    });
    $('.optional_1').hide();
}

function IDCardReaderInfo(dom) {
    var arr = [
        '<object classid="clsid:10946843-7507-44FE-ACE8-2B3483D179B7" codebase="IDCardReader.ocx" id="CVR_IDCard" name="CVR_IDCard" width="0" height="0">',
        '</object>',
    ].join(' ');
    dom.append(arr);
}

//获取身份证信息
function IDCardReader() {
    var CVR_IDCard = document.getElementById("CVR_IDCard");
    var strReadResult = CVR_IDCard.ReadCard();
    if (strReadResult === '0') {
        return CVR_IDCard;
    } else {
        showMsg(strReadResult, 2, false);
        return null;
    }
}

//身份证日期格式化
function formatDate(date) {
    var y = date.substring(0, 4);
    var m = date.substring(4, 6);
    var d = date.substring(6, 8);
    return y + "-" + m + "-" + d;
}

/**
 * 计算时间相差多少天
 * GetDateDiff('2018-10-23','2018-10-24')
 */
function GetDateDiff(startDate, endDate) {

    var c = new Date(endDate.split(" ")[0].replace(/-/g, "/")) - new Date(startDate.split(" ")[0].replace(/-/g, "/"));
    c = c / 86400000;
    var t = startDate.split(" ");
    if (t.length == 2) {
        var hour = Number(t[1].split(":")[0]);
        if (hour < 6) {
            return c + 1;
        }
        return c;
    }


}

/**
 * 计算两个时间相差小时数
 * @param s1
 * @param s2
 * @returns {number}
 */

function getHour(s1, s2) {
    s1 = new Date(s1.replace(/-/g, '/'));
    s2 = new Date(s2.replace(/-/g, '/'));
    var ms = Math.abs(s1.getTime() - s2.getTime());
    return Math.ceil(ms / 1000 / 60 / 60)
}

/**
 * 时间转长时间串
 * DateToLStr(new Date())
 * return yyyy-MM-dd hh:mm:ss
 */
function DateToLStr(dt) {
    try {
        var y, m, m1, d, d1, h, h1, mm, mm1, s, s1;
        y = dt.getFullYear();
        m = dt.getMonth() + 1; //1-12
        d = dt.getDate();
        h = dt.getHours();
        mm = dt.getMinutes();
        s = dt.getSeconds();

        m1 = (m < 10 ? "0" + m : m);
        d1 = (d < 10 ? "0" + d : d);
        h1 = (h < 10 ? "0" + h : h);
        mm1 = (mm < 10 ? "0" + mm : mm);
        s1 = (s < 10 ? "0" + s : s);
        return "" + y + "-" + m1 + "-" + d1 + " " + h1 + ":" + mm1 + ":" + s1;
    } catch (e) {
        console.log("error");
        return "";
    }
}

function DateToLStr2(dt) {
    try {
        var y, m, m1, d, d1, h, h1, mm, mm1, s, s1;
        y = dt.getFullYear();
        m = dt.getMonth() + 1; //1-12
        d = dt.getDate();
        h = dt.getHours();
        mm = dt.getMinutes();
        s = dt.getSeconds();

        m1 = (m < 10 ? "0" + m : m);
        d1 = (d < 10 ? "0" + d : d);
        h1 = (h < 10 ? "0" + h : h);
        mm1 = (mm < 10 ? "0" + mm : mm);
        s1 = (s < 10 ? "0" + s : s);
        return "" + y + "/" + m1 + "/" + d1 + " " + h1 + ":" + mm1 + ":" + s1;
    } catch (e) {
        console.log("error");
        return "";
    }
}


function DateToLStr3(dt) {
    try {
        var y, m, m1, d, d1;
        y = dt.getFullYear();
        m = dt.getMonth() + 1; //1-12
        d = dt.getDate();

        m1 = (m < 10 ? "0" + m : m);
        d1 = (d < 10 ? "0" + d : d);
        return y + "-" + m1 + "-" + d1;
    } catch (e) {
        console.log("error");
        return "";
    }
}

function DateToLStr4(dt) {
    try {
        var y, m, m1, d, d1, h, h1, mm, mm1, s, s1;
        y = dt.getFullYear();
        m = dt.getMonth() + 1; //1-12
        d = dt.getDate();

        m1 = (m < 10 ? "0" + m : m);
        d1 = (d < 10 ? "0" + d : d);
        return "" + y + "/" + m1 + "/" + d1 + " ";
    } catch (e) {
        console.log("error");
        return "";
    }
}

//判断字符是否为空的方法
function isEmpty(obj) {
    return typeof obj === "undefined" || obj == null || obj === "";
}

function renderSelect(id, key, value, url, f, callback) {
    $.getJSON(url + "&random=" + Date.now(), function (rs) {
        var dom = $("#" + id);
        var str = '';
        if (url.indexOf("/member/cartLeaveDetail") > -1) {
            // rs.data = rs.data.cartLeaveBOs;

            if (rs.success) {
                for (var i = 0; i < rs.data.cartLeaveBOs.length; i++)
                    // dom.append(
                    str += '<option value="' + rs.data.cartLeaveBOs[i][key] + '">' + rs.data.cartLeaveBOs[i][value] + '</option>'
                // );
                dom.append(str)
            }
            f.render("select");
            if (callback) callback(str, rs.data.cartLeaveBOs);
        } else {


            if (rs.success) {
                for (var i = 0; i < rs.data.length; i++)
                    // dom.append(
                    str += '<option value="' + rs.data[i][key] + '">' + rs.data[i][value] + '</option>'
                // );
                dom.append(str)
            }
            f.render("select");
            if (callback) callback(str, rs.data);
        }

    })
}

function getUrl(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (name) {
        if (r != null)
            return decodeURI(r[2]);
        return null;
    } else {
        r = window.location.search.substr(1);
        if (r != null)
            return decodeURI(r);
        return null;
    }
}

/*
*       arr.sort(compare("age"))
* */
var compare = function (prop) {
    return function (obj1, obj2) {
        var val1 = obj1[prop];
        var val2 = obj2[prop];
        if (!isNaN(Number(val1)) && !isNaN(Number(val2))) {
            val1 = Number(val1);
            val2 = Number(val2);
        }
        if (val1 < val2) {
            return -1;
        } else if (val1 > val2) {
            return 1;
        } else {
            return 0;
        }
    }
}

//取消并且关闭选择框
function no() {
    parent.layer.close(parent.layer.getFrameIndex(window.name));
}

//根据后台数据格式化菜单
function formatMenu(data) {
    return data.map(function (item) {
        if (!item) {
            return null;
        }
        var path = item.path, name = item.menuName, id = item.id;

        var result = {
            path: path,
            name: name,
            id: id
        };

        if (item.pid > 0) {
            result.exact = true;
        }
        if (item.ch) {
            var children = formatMenu(item.ch);
            result.children = children;
        }
        return result;
    }).filter(function (item) {
        return item
    });
}

//渲染菜单
function renderMenu($, e) {
    var m = $("#navMenu");
    m.empty();
    if (!!localStorage.hotelMenu) {
        //格式化菜单
        var n = formatMenu(JSON.parse(localStorage.hotelMenu));
        if (!!n) {
            var d = [];
            for (var i = 0, len = n.length; i < len; i++) {
                var z = n[i];
                if (z.children.length > 0) { //有二级菜单
                    //先拼接一级菜单
                    d.push([
                        '<li class="layui-nav-item">',
                        '<a href="' + z.path + '">' + z.name + '</a>',
                        '<dl class="layui-nav-child">'].join(''));
                    //再循环拼接二级菜单
                    for (var j = 0, jLen = z.children.length; j < jLen; j++) {
                        var l = z.children[j];
                        d.push([
                            '<dd>',
                            '<a href="' + l.path + '">' + l.name + '</a>',
                            '</dd>'
                        ].join(''))
                    }
                    //闭合标签
                    d.push(['</dl></li>'].join(''));
                } else {// 没有二级菜单
                    //直接拼接标签
                    d.push([
                        '<li class="layui-nav-item">',
                        '<a href="' + z.path + '">' + z.name + '</a>',
                        '</li>'].join(''));
                }
            }
            m.append(d.join(' '));
            m.parent().css('z-index', '999');
            try {
                $("#hotelusername").html(" " + JSON.parse(localStorage.User).name + '')
            } catch (e) {
                console.info("获取用户名失败")
            }
            e.render();
        }
    }
}


function showConfirm(index) {
    if (index == 1) {
        $.ajax({
            url: api.beforehandDuty,
            type: 'POST',
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    sessionStorage.setItem('handover', JSON.stringify(data.data));
                    parent.location.href = 'handover.html?s=0';
                } else {
                    showMsg(data.errMsg, 2, false);
                }
            }
        })
    } else if (index == 2) {
        layui.layer.confirm('是否交班', {icon: 3, title: '提示'}, function (index) {
            //do something

            $.ajax({
                url: api.offDuty,
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        sessionStorage.setItem('handover', JSON.stringify(data.data));
                        parent.location.href = 'handover.html?s=1';
                    } else {
                        showMsg(data.errMsg, 2, false);
                    }
                }
            })
            layer.close(index);
        });
    }
}

/*
* 提示框
* title为提示标题
* iconID为图标样式
* isRefresh是否需要刷新页面  false为不刷新  true为刷新
*/
function showMsg(title, iconId, isRefresh) {
    parent.layer.msg(title, {
        icon: iconId,
        time: 3000 //2秒关闭（如果不配置，默认是3秒）
    }, function () {
        if (!isRefresh) {
            return
        }
        location.reload();
    });
}

/*
* 提示框
* title为提示标题
* iconID为图标样式
* isRefresh是否需要刷新页面  false为不刷新  true为刷新
*/
function showMsg1(title, iconId, isRefresh) {
    layer.msg(title, {
        icon: iconId,
        time: 5000 //2秒关闭（如果不配置，默认是3秒）
    }, function () {
        if (!isRefresh) {
            return
        }
        location.reload();
    });
}


//钟点房时间限制
function limitHours(t) {
    if (t.value > 6) {
        t.value = 6;
        layer.msg('时间不可大于6小时', {icon: 2, shift: 6});
    }
    if (t.value < 3) {
        t.value = 3;
        layer.msg('时间不可小于3小时', {icon: 2, shift: 6});
    }
}

function backHome() {
    location.href = HOME;
}

//状态判断
function stateJudgment(state) {
    switch (state) {
        case 1:
            return '空房';
        case 2:
            return '预约中';
        case 3:
            return '已入住';
        case 4:
            return '预离店';
        case 5:
            return '入住超时';
        case 6:
            return '待打扫';
        case 7:
            return '维修';
        case 8:
            return '全部锁房';
        case 9:
            return '门店锁房';
        case 10:
            return '网络锁房';
    }
}

function shopAdd() {
    parent.layer.open({
        type: 2,
        skin: 'demo-class',
        area: ['500px', '550px'],
        title: '商品交易',
        shade: 0.6,
        shadeClose: true,
        content: 'iframe_shop.html?v=' + Date.now()
    });
}

function renderReport($, ele) {
    $('body').append(['<section id="side">',
        '<div class="side_row side_title">',
        '<p class="side_td side_td1">房型</p>',
        '<p class="side_td">房价</p>',
        '<p class="side_td">可用</p>',
        '<p class="side_td">入住中</p>',
        '<p class="side_td">预约中</p>',
        '<p class="side_td">入住率</p>',
        '</div>',
        '<div id="avg">',
        '</div>',
        '<div class="side_row side_count" id="_count">',
        '</div>',
        '<div class="side_row side_title">',
        '<p class="side_td side_td1">类型</p>',
        '<p class="side_td">间数</p>',
        '</div>',
        '<div id="allc">',
        '</div>',
        '</section>'].join(""));
    document.getElementById("side").style.height = (document.body.clientWidth - 100) + "px";
    getReport($);
}

function getReport($) {
    $.getJSON(api.getReport + "&random=" + Date.now(), function (rs) {
        var $avg = $("#avg");
        var $allc = $("#allc");
        $avg.empty();
        $allc.empty();
        if (rs.data == '') return;
        var a = rs.data.first;
        var b = rs.data.second;
        var astr = '';
        var bstr = '';
        var sumCount = 0;
        var times = 0;
        var a1 = 0, a2 = 0, a3 = 0, a4 = 0, a5 = 0;
        for (var i = 0; i < a.length; i++) {
            sumCount += a[i].sumCount;
            times += a[i].times;
            a1 += a[i].typePrice;
            a2 += a[i].count;
            a3 += a[i].countChinkRoom;
            a4 += a[i].countOrderRoom;
            // a5+=a[i].typePrice;

            astr += ['<div class="side_row">',
                '<p class="side_td side_td1">',
                a[i].name,
                '</p>',
                '<p class="side_td">',
                a[i].typePrice,
                '</p>',
                '<p class="side_td">',
                a[i].count,
                '</p>',
                '<p class="side_td">',
                a[i].countChinkRoom,
                '</p>',
                '<p class="side_td">',
                a[i].countOrderRoom,
                '</p>',
                '<p class="side_td">',
                a[i].ratio,
                '</p>',
                '</div>'].join("")
        }
        $("#_count").html(['<p class="side_td side_td1">统计</p>',
            '<p class="side_td">',
            rs.data.three,
            '</p>',
            '<p class="side_td">',
            a2,
            '</p>',
            '<p class="side_td">',
            a3,
            '</p>',
            '<p class="side_td">',
            a4,
            '</p>',
            '<p class="side_td">',
            ((times / sumCount) * 100).toFixed(2), '%</p>'].join(""))
        $avg.append(astr);
        for (var i = 0; i < b.length; i++) {
            bstr += ['<div class="side_row">',
                ' <p class="side_td side_td1">',
                b[i].name,
                '</p>',
                '<p class="side_td">',
                b[i].number,
                '</p>',
                ' </div>'].join("")
        }
        $allc.append(bstr);


    })
    $("#side").on("click", function () {
        if ($("#side").hasClass("side-open")) {
            $("#side").removeClass("side-open")
        } else {
            $("#side").addClass("side-open")
        }
    });
}

function showPosPort() {
    layer.prompt({
        title: '端口号设置',
        formType: 3,
        value: localStorage.posport ? localStorage.posport : 6
    }, function (pass, index) {

        if (isNaN(pass)) {
            layer.alert("请输入数字")
            return;
        }
        localStorage.posport = pass;
        layer.close(index);

    });
}

//强制保留2位小数，如：2，会在2后面补上00.即2.00 
function toDecimal(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return false;
    }
    var f = Math.round(x * 100) / 100;
    var s = f.toString();
    var rs = s.indexOf('.');
    if (rs < 0) {
        rs = s.length;
        s += '.';
    }
    while (s.length <= rs + 2) {
        s += '0';
    }
    return s;
}

function clearModifyPrice() {
    localStorage.modifyPrice = "";
}

//获取用户会员信息
function setUserInfo(ID, callback) {
    $.ajax({
        url: api.queryStoreValue,
        type: 'POST',
        dataType: 'json',
        data: {credentialNumber: ID},
        success: function (res) {
            if (res.success) {
                callback(res.data, res.success);
            } else {
                callback(res.errMsg, res.success);
                return;
            }
        }
    })
}

//格式化支付传值
function formatPayTp(_p) {
    var payTp = 0;
    payTp = _p == 2 ? 0 : '';
    payTp = _p == 3 ? 1 : payTp;
    payTp = _p == 4 ? 2 : payTp;
    payTp = _p == 5 ? 3 : payTp;
    payTp = _p == 6 ? 4 : payTp;
    payTp = _p == 7 ? 5 : payTp;
    return payTp;
}

//格式化POS支付方式
function checkPayState(_p) {
    var obj = {}
    if (_p == 2) {
        obj.payTp = 0;
    }
    if (_p == 3) {
        obj.payTp = 11;
        obj.procCd = 660000;
    }
    if (_p == 4) {
        obj.payTp = 12;
        obj.procCd = 660000;
    }
    return obj;
}

//身份证验证并查询会员信息
function handleInput() {
    var val = $('#card').val(),
        flag = new RegExp(IDREG).test(val) || new RegExp(HMPASSCHECK).test(val) || new RegExp(TAIWANPASS).test(val) || new RegExp(PASSPORT).test(val) || new RegExp(OFFICERS).test(val);
    //再进行正则判断
    if (flag) {
        //获取用户会员信息
        setUserInfo(val, function (res, success) {
            if (success) {
                $('.integral').text(res.integral);
                $('.prepaidCard').text(res.streValue);
                //存储会员信息
                for (var item in res) {
                    payValue[item] = res[item];
                }
                $('.optional_1').show();
            } else {
                layer.msg(res);
                //重新初始化信息
                for (var item in payValue) {
                    payValue[item] = null;
                }
                $('.optional_1').hide();
            }
        });

    } else {
        layer.msg('请输入正确的证件号码');
        //重新初始化信息
        for (var item in payValue) {
            payValue[item] = null;
        }
        $('.optional_1').hide();
    }
}

// 防抖
function debounce(fn, wait) {
    var timeout = null;
    return function () {
        if (timeout !== null) clearTimeout(timeout);
        timeout = setTimeout(fn, wait);
    }
}