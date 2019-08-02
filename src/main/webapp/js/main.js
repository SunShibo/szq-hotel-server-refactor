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
    admin: '/admin/getAdminById' //管理员列表查询   已修改
    , delAdmin: '/admin/delAdmins' //管理员删除   已修改
    , updateAdmin: '/admin/updateAdminUser' //管理员编辑   已修改
    , addAdmin: '/admin/adminRegister' //管理员添加   已修改
    , queryUserIntgeralInfo: '/member/getMemberInfo'//积分查询   已修改
    , addIntegral: '/member/integralChange'//积分增加  已修改
    , storedValue: '/member/storedValueChange'//储值调整  已修改
    // , deductionIntegral: '/integral/updateIntegral?v=1'//积分减少?userId=3&number=20  废弃
    , queryMember: '/member/selectMember'//会员查询?query=张三      已修改
    , addMember: '/member/addMember?v=1'//会员添加   已修改
    , updateMember: '/member/updateMember'//会员信息修改?userId   已修改
    , memberLevel: '/memberLevel/selectmemberLevel?v=1'//获取会员级别000
    , consumeRecord:'/member/getConsumptionRecord'//消费明细  已修改
    , integralRecord:'/integralRecord/getIntegralRecord'//积分明细   已修改
    , storedRecord:'/storedValueRecord/getStoredValueRecord'//储值明细   已修改
    , certificate: '/Dictionary/getDic?kid=1'//获取证件000
    , getMemberByCre: '/user/queryByCredentialNumber?credentialNumber='//根据证件号码获取会员信息
    , getHouseType: '/queryRoomTypeNum'//获取房屋类型000
    , getHouse: '/room/roomTypeMain'//获取房间信息
    // , yuImport: '/manage/upload'//预导入  废弃
    , cardImport: '/memberCard/importMemberCard'//会员卡导入
    , import: '/member/importMember'//会员导入
    , exportExcel: '/manage/outExce?v=1'//导出会员信息
    , offMember: '/member/deleteMember?id='//注销会员userId   已修改
    , roomDelete: '/room/deleteByPrimaryKey'  //客房删除   已修改
    , roomQuery: '/room/queryRoom'   //客房查询    已修改
    , roomAdd: '/room/insertSelective'   //新增客房  已修改
    , updateRoomInfo: '/room/updateByPrimaryKeySelective'//修改客房信息  已修改
    // , roomUnLock: '/room/openLockRoom'   //客房解锁   废弃
    , roomLockRoom: '/room/updatelockRoomState'    //客房锁房   已修改
    , queryMemberById: '/member/detail?userId='//根据会员id查询
    , pickRoomQuery: '/room/quertRm'//选房查询000
    , checkIn: '/checkin/addCheckin?v=1'//入住
    , yueCheckIn: '/order/getReservationRoomInfo?v=1'//预约入住信息查询000
    , roomQueryById: '/room/toUpdateRoomInfo?v=1'//按roomId查询
    , roomHouseType: '/room/houseType?v=1'
    , queryRoomInfo: '/room/queryRoomTypeAndHotelAndFloor'
    , querySale: '/user/queryDiscountId?cerfiticateNumber='//根据证件号查询折扣信息
    , queryOrder: '/order/queryOrderList'  //订单查询    已修改
    , queryIdFlag: 'member/selectMemberByNumber?v=1'//通过手机号或者证件号查询会员信息000
    , queryOrderById: '/order/getRoomInfoById'//查询子订单信息   已修改
    , queryOrderInfo: '/order/getOrderInfoById'//查询子订单详情信息   已修改
    , checkInYuYue: '/order/reservationRoom?v=1'//预约入住
    , refundOnclick: '/OrderManage/refundOnclick?v=1'//获取结账信息
    , cash: '/chilOrder/addCashPledge'//押金  已修改
    // , refundprice: '/OrderManage/refundprice?v=1'//退款  废弃
    , info: '/chilOrder/recorded'//客账入账  已修改
    , cancelSubQuery: '/order/getSubscribeOrderChild'//取消查询   已修改
    , cancelSubUpdate: '/order/closeOrder'//取消订单   已修改
    , outRoom: '/order/getCheckOutInfo'//查看退房信息  已修改
    , subitemOnclick: '/chilOrder/queryChildleAccounts'//查询子订单单项结账信息   已修改
    , subitem: '/chilOrder/childleAccounts'//子订单结账  已修改
    , transfer: '/chilOrder/transferAccounts' //消费项目转账  已修改
    , outRoomBtn: '/order/checkOut'//退房按钮   已修改
    , outRoomRefound: '/order/checkOutRollback'//退房回滚  已修改
    , refund: '/chilOrder/queryAccounts'//查询总结账信息  已修改
    , refundBtn: '/chilOrder/accounts'//总结账按钮  已修改
    , exemption: '/chilOrder/free'//冲减  已修改
    , updatePriceTime: '/room/updatePriceTime'//查询修改时间是否冲突
    , largeUpdatePriceTime: '/room/largeUpdatePriceTime'//批量修改全天房时间
    , updatePriceAll: '/room/largeUpdatePriceTimeByOtherPrice'//批量修改其他价格  除全天房以外的其他价格
    , reserveRoom: 'order/reservationRoom'//预定房间000
    // , stamp: '/OrderManage/stamp' //打印数据 废弃
    , priceAll: '/room/queryRoomPrice'//查询当前roomId的所有未过期价格
    // , queryCondition: '/dealShiftServiceController/queryCondition'//交班信息   废弃
    , offDuty: '/shiftRecords/shifRecord'//确定交班  已修改
    , handoverList: '/shiftRecords/queryShiftRecordList'//交班列表  已修改
    // , beforehandDuty: '/dealShiftServiceController/beforehandDuty'//预交班  废弃
    , beforeOrder: 'OrderManage/closeAccounts'//已结账的订单查询详情
    // , cancelOrder: '/subcribe/cancelTheReservation'//取消预约  取消入住待支付  废弃
    , memberPrice: '/memberRoomType/selectMemberRoomType?memberLevelId='//根据会员级别获取价格000
    , memberUpdatePrice: '/memberRoomType/updateMemberRoomType'//根据会员级修改价格
    , queryHouseType: '/roomType/queryRoomType'//查询房型      已修改
    , queryRt: '/room/queryRt'//查询下拉框的房型数据      已修改
    // , toHouseType: '/room/toHouseType'//按ID查询房型信息   废弃
    , updateHouseType: '/roomType/updateRoomType'//修改房型      已修改
    , deleteHouseType: '/roomType/deleteRoomType'//删除房型
    , addHouseType: '/roomType/insertRoomType'//添加房型      已修改
    // , queryPettyCash: '/dealShiftServiceController/queryPettyCash?v=1'//备用金查询   废弃
    // , addPettyCash: '/dealShiftServiceController/addPettyCash?v=1'//备用金增减    废弃
    , channelDiscount: '/CooperativePrice/detail?id='
    , allChannel: '/Dictionary/getDic?kid=2'//预定页面查询合作机构000
    , addChannel: '/CooperativePrice/add?name='
    , updateChannel: 'CooperativePrice/update'
    // , homePage: '/roomMain/homePageInfo?v=1'//获取首页数据  废弃
    , cashPay: '/order/pay'//现金支付接口000   ??  任何支付方式都走这个接口
    , queryPayment: '/checkin/queryPayment'//查询订单应支付金额


    , homeRoom: '/home/home'//首页获取房间类型信息  已修改
    , homeRoomType: '/home/homeCount'//获取首页房型、房态数据  已修改

    , homeItemInfo: '/order/getCheckInInfo'//首页在住信息   已修改
    , homeRoomInfo: '/room/getRoomMessage'//首页房屋信息   已修改
    , homeReservationInfo: '/order/getReservationInfo'//首页预约信息   已修改
    , homeQueryIndexRoom: '/room/queryIndexRoomState'//首页十五天预约信息   已修改
    , homeRoomRecord: '/roomRecord/selectRoomRecord'//首页房间操作日志信息   已修改
    , homeUpdateRoomMaintain: '/room/updateRoomMaintain'//修改客房维修状态   已修改
    , homeCleanThe: '/room/updateroomMajorState'//切换脏房状态 已修改
    , updateMainCommment: '/room/updateRoomRemark'//首页房屋备注   已修改
    , updateCheckInData: '/order/updCheckInInfo'//首页入住信息修改   已修改
    , jointHousingt: '/order/getAlRoom '//查询可加入联房信息   已修改
    , join: '/order/addAlRoom'//加入联房   已修改
    , dismiss: '/order/updAlRoom'//解散联房  已修改


    , reportList: '/room/querySs'//按时间段查询图表000
    , otherView: '/room/querySc'//数据表格  已修改


    , subPrinting: '/chilOrder/alonePrint'//子项打印
    , mainOrderPrinting: ''//主订单打印


    // , delRoomItemPrice: '/room/deleteRoomPrice'//删除特殊价格  废弃
    // , operationLog: '/roomDetailsController/operationLog'//首页弹出层操作记录
    , checkIdentify: '/checkin/isCheckinByIdNumber?idNumber='//查看这个证件号码是否有在住信息
    , cashInfo: '/commodity/queryCommodiry'//现金订单000
    , cashAdd: '/commodity/addCommodity'//商品交易添加000
    // , stayOver: '/roomDetailsController/stayOver'//续住  废弃
    // , stayOverPay: '/roomDetailsController/stayOverPay'//续住后支付  废弃
    // , updateCheckInComment: '/order/updateCheckInRemark'//首页入住房屋备注  废弃
    , hotelInfo: '/hotel/queryLoginHotel'//权限页酒店list   已修改
    , hotel: '/OrderManage/hotel'//权限页酒店list   已修改
    , detail: '/admin/detail'//权限列表  已修改
    , getReport: 'room/todayPictureView'//room/todayPictureView?v=1
    , getRoomInfoById: '/order/getRemainingLease?orderChildId='//在住换房获取价格信息
    , changeRoomPay: '/checkin/roomChangePay?v=1'
    , adminAdd: '/admin/add'//添加权限  已修改
    , adminQuery: '/admin/query'//按ID查找权限  已修改
    , adminUpdate: '/admin/update'//权限修改  已修改
    , updateStatus: '/admin/updateStatus'//权限注销  已修改
    , getCardNoByLeaveId: '/member/getMemberCardNumber?memberCardLevelId='//根据级别id查询卡费用信息   已修改
    , getCardUpdate: '/member/updateGetMemberCardNumber?v=1'//根据用户id卡级别判断是否缴费member/queryCartUpdate?userId=1&leaveId=1   已修改
    , cartInfo: '/memberCard/selectMemberCard'//会员卡查询   已修改
    , cartDelete: '/memberCard/deleteMemberCard'//会员卡删除   已修改
    , cartExport: '/memberCard/exportMemberCard'//会员卡导出    已修改
    , cartAdd: '/memberCard/addMemberCard'//会员卡添加   已修改
    // , cartDetail: '/Cart/detail?v=1'//会员卡回显  废弃
    , cartUpdate: '/memberCard/updateMemberCard'//会员卡编辑     已修改
    , checkRoomInfo: '/room/ifCheckIn'//判断选的房间是否可用  已修改
    , updatePassWord: 'login/updatePassWord'//修改密码
    , changeHotel: 'login/changeHotel'//切换酒店
    , logout: '/admin/exitLogin'//退出登录      已修改
    // , getScheduleById: '/order/queryOrderMsg'//根据订单号码查询预订信息  废弃
    // , updateFixRoomState: '/room/updateFixRoomState'//更改维修状态  废弃
    , queryStatement: '/order/queryStatement'//在住报表和预离店报表  已修改
    , addMemberLev: '/memberLevel/addMemberLevel?v=1'//添加会员级别000
    , getEditScheduleRoom: '/room/UpdateOrderInfoBySelectRoom?v=1'//修改预定房间模块获取房间信息
    , getEditScheduleRoomType: '/room/houseTypeAndPhoneAndNet?v=1'//修改预定房间类型模块获取房间信息
    , queryClassess: '/classes/queryClasses'//查询当前酒店下的所有班次000
    , deleteClasses: '/classes/deleteClasses'//删除班次000
    , addClasses: 'classes/addClasses'//添加班次000
    // , addRoomPerson: '/roomDetailsController/addRoomPerson'//添加同来人  废弃
    , updateClasses: '/classes/updateClasses'//修改班次000
    , queryClassessByHotelId: '/classes/getClasses'//按酒店查班次      已修改
    // , cartLogout: '/Cart/logout'//会员卡注销  废弃
    // , cashStamp: 'commodity/queryCommodiryById'//打印000  废弃
    // , addMaseTo: '/roomDetailsController/addMaseTo'//添加同来  废弃
    , waitIn: '/checkin/laterCheckIn?v=1'//稍后入住
    , calcPrice2: '/room/updateOrderInfo?v=1'//预定修改，选房确定后需要调用算价格
    , calcPrice1: '/room/sumRoomPriceAndHouseTypePrice?v=1'//选房确定后需要调用算价格
    , scheduleUpdate: '/subcribe/updateSub?v=1'//选房确定后需要调用算价格
    // , orderInfoHistory: 'OrderManage/orderInfoHistory'//消费明细  废弃
    , queryOrderByRoom: '/commodity/querySuspend'//商品交易挂账000
    , buying: '/commodity/suspend'//商品交易挂账生成订单信息 已修改
    , FormAccountDetail: '/FormAccountDetailController/FormAccountDetail'//收银报表
    , FormManangeResponse: '/managementReport/getManagementReport'//管理层报表000
    // , stamOrder: '/OrderManage/stamOrder'//在住打印  废弃
    // , queryRoomPerson: 'roomDetailsController/queryRoomPerson'//查询同来人  废弃
    // , delRoomPerson: '/roomDetailsController/delRoomPerson'//删除同来人  废弃
    // , updateRoomPerson: '/roomDetailsController/updateRoomPerson'//修改同来人 废弃
    , addHotel: 'hotel/addHotel'//添加酒店000
    , queryHotelInfo: 'hotel/queryHotel'//查询酒店000
    , updateHotelInfo: 'hotel/updateHotel'//编辑酒店000
    , queryStoreValue: '/member/getStoreValueIntegral'//查询会员积分和储值   已修改
    , queryFloor: 'floor/queryFloor'//查询楼层000
    , addFloor: 'floor/addFloor'//添加楼层000
    , updateFloor: 'floor/updateFloor'//修改楼层000
    , deleteFloor: 'floor/deleteFloor'//删除楼层000
    , roleAllData: '/admin/getAllRoleMenu'//查询角色     已修改
    , roleAll: '/admin/getRoleList'//查询所有角色（下拉框）     已修改
    , roleDelete: '/admin/delRoleByIds' //角色删除      已修改
    , roleUpdate: '/admin/grantAuthority' //角色编辑      已修改
    , roleAdd: '/admin/addRoleGrantAuthority' //角色添加      已修改
    , permissionsMenu: '/admin/getPermissionsMenu'  //查询所有菜单  已修改
}

//全局定义一次, 加载formSelects
layui.config({
    base: './js/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4.min'
});
layui.use(['jquery', 'element'], function () {
    $ = layui.$;
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
                $('.integral').text(res.integralMoney);
                $('.prepaidCard').text(res.storeValue);
                $('.optional_1').show();
            });
        }
        //显示会员详情
        $('.optional_1').show();
    });

    renderMenu($, element);



})

function renderSelect(id, key, value, url, f, callback) {
    layui.$.getJSON(url + "&random=" + Date.now(), function (rs) {
        var dom = $("#" + id);
        var str = '';
        if (rs.success) {
            for (var i = 0; i < rs.data.length; i++) {
                str += '<option value="' + rs.data[i][key] + '">' + rs.data[i][value] + '</option>';
            }
            dom.append(str)
        }
        f.render("select");
        if (callback) {
            callback(str, rs.data)
        }
    })
}


//清空表单
function clearIDForm(form, formName) {
    form.val(formName, {
        IDCard: '',
        integral: ''
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
        return "" + y + "/" + m1 + "/" + d1 + " " + h1 + ":" + mm1 + ":" + s1;
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
        return y + "/" + m1 + "/" + d1;
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

function DateToLStr5(dt) {
    try {
        var y, m, m1, d, d1, h, h1, mm, mm1, s, s1;
        y = dt.getFullYear();
        m = dt.getMonth() + 1; //1-12
        d = dt.getDate();

        m1 = (m < 10 ? "0" + m : m);
        d1 = (d < 10 ? "0" + d : d);
        return "" + y + "-" + m1 + "-" + d1 + " ";
    } catch (e) {
        console.log("error");
        return "";
    }
}

//判断字符是否为空的方法
function isEmpty(obj) {
    return typeof obj === "undefined" || obj == null || obj === "";
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
        if (item.children) {
            result.children = formatMenu(item.children);
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
            url: api.offDuty,
            type: 'POST',
            dataType: 'json',
            data: {type: 'no'},
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
                data: {type: 'yes'},
                success: function (data) {
                    if (data.success) {
                        sessionStorage.setItem('handover', JSON.stringify(data.data));
                        parent.location.href = 'handover.html?s=1';
                    } else {
                        showMsg(data.errMsg, 2, false);
                    }
                }
            });
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
        time: 5000 //5秒关闭（如果不配置，默认是3秒）
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


//主房态
function roomMajorState(room) {
    switch (room) {
        case 'vacant':
            return '空房';
        case 'inthe':
            return '已入住';
        case 'dirty':
            return '脏房';
        case 'timeout':
            return '超时';
    }
}

//订单状态
function queryOrderState(orderState) {
    switch (orderState) {
        case '':
            return '';
        case 'reservation':
            return '预约中';
        case 'notpay':
            return '入住待支付';
        case 'admissions':
            return '入住中';
        case 'notpaid':
            return '退房待结账';
        case 'accomplish':
            return '已结账';
        case 'cancel':
            return '已取消';
    }
}

//支付方式
function orderPayType(payType) {
    switch (payType) {
        case '':
            return '';
        case 'cash':
            return '现金';
        case 'cart':
            return '银行卡';
        case 'wechat':
            return '微信';
        case 'alipay':
            return '支付宝';
        case 'other':
            return '其他支付';
        case 'stored':
            return '储值';
        case 'integral':
            return '积分';
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
    $.getJSON(api.getReport + "?random=" + Date.now(), function (rs) {
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
            times += Number(a[i].rotio.split("%")[0]);
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
                a[i].rotio,
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
            (times / a.length).toFixed(2), '%</p>'].join(""))
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
        data: {certificateNumber: ID},
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

//身份证验证并查询会员信息
function handleInput() {
    var val = $('#card').val(),
        flag = new RegExp(IDREG).test(val) || new RegExp(HMPASSCHECK).test(val) || new RegExp(TAIWANPASS).test(val) || new RegExp(PASSPORT).test(val) || new RegExp(OFFICERS).test(val);
    //再进行正则判断
    if (flag) {
        //获取用户会员信息
        setUserInfo(val, function (res, success) {
            if (success) {
                $('.integral').text(res.integralMoney);
                $('.prepaidCard').text(res.storeValue);
                $('.optional_1').show();
            } else {
                layer.msg(res);
                $('.optional_1').hide();
            }
        });

    } else {
        layer.msg('请输入正确的证件号码');
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

// 日期，在原有日期基础上，增加days天数，默认增加1天
function addDate(date, days) {

    if (days == undefined || days == '') {
        days = 1;
    }
    var date = new Date(date);
    date.setDate(date.getDate() + days);
    var month = date.getMonth() + 1;
    var day = date.getDate();
    return date.getFullYear() + '/' + getFormatDate(month) + '/' + getFormatDate(day);
}

// 日期月份/天的显示，如果是1位数，则在前面加上'0'
function getFormatDate(arg) {
    if (arg == undefined || arg == '') {
        return '';
    }

    var re = arg + '';
    if (re.length < 2) {
        re = '0' + re;
    }

    return re;
}

//选择完成开始和结束时间，计算时间差
function updateTimeInfo(v) {
    if (state.roomType != 2) {
        //全天房或者免费房
        $("#leaveDate1").val(v.split(" ")[0] + LEAVETIME);
        v.split(" ")[0] + LEAVETIME;
        document.getElementById("days1").value =
            GetDateDiff($("#startTime").val(), $("#leaveDate1").val());
        $(".typea").removeClass("layui-hide")
        $(".typeb").addClass("layui-hide")
    } else {
        // console.info($("#startTime").val(),v)
        //钟点房

        $(".typea").addClass("layui-hide")
        $(".typeb").removeClass("layui-hide")
    }
    clearRoomInfo();
}

//清除选择的时间
function clearTimeInfo() {

    document.getElementById("leaveDate1").value = "";
    document.getElementById("days1").value = "";
}

//关闭页面
function closeWin(){
    if (navigator.userAgent.indexOf("Firefox") != -1 || navigator.userAgent.indexOf("Chrome") !=-1) {
        window.location.href="about:blank";
        window.close();
    } else {
        window.parent.opener = null;
        window.parent.open("", "_self");
        window.parent.close();
    }
}

//监听用户是否激活当前页面
function visibilityChange() {
    var hiddenProperty = 'hidden' in document ? 'hidden' :
        'webkitHidden' in document ? 'webkitHidden' :
            'mozHidden' in document ? 'mozHidden' :
                null;
    var visibilityChangeEvent = hiddenProperty.replace(/hidden/i, 'visibilitychange');
    var onVisibilityChange = function(){
        if (!document[hiddenProperty]) {
            console.log('页面激活');
            location.reload();
        }else{
            console.log('页面非激活');
        }
    };
    document.addEventListener(visibilityChangeEvent, onVisibilityChange);
}