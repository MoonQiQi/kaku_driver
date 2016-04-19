package com.yichang.kaku.webService;

import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.request.*;

public class KaKuApiProvider extends WebApiProvider {

    //1000 退出
    public final static void exit(final ExitReq req,
                                  final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "login/login_exit", resp);
    }

    //1001 自动登录
    public final static void autologin(final AutoLoginReq req,
                                       final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "login/login_auto", resp);
    }

    //1002 手动登录
    public final static void login(final LoginReq req,
                                   final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "login/login_normal", resp);
    }

    //1004 获取验证码
    public final static void getCaptcha(final MobileReq req,
                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "login/get_vcode", resp);
    }

    //10010 检查更新
    public final static void checkUpdate(final CheckUpdateReq req,
                                         final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "login/version_check", resp);
    }

    //10011 获取司机member页面信息
    public final static void getMemberDriverInfo(final MemberDriverReq req,
                                                 final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/personal_center", resp);
    }

    //10012 获取member页面 司机个人资料,用户资料
    public final static void getDriverInfo(final MemberDriverInfoReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/personal_information", resp);
    }

    //10014 我的地址
    public final static void getAddr(final AddrReq req,
                                     final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/address/address_list", resp);
    }

    //10015 新建/编辑收货地址
    public final static void NewAddr(final NewAddrReq req,
                                     final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/address/address_submit", resp);
    }

    //10016 删除地址
    public final static void DeleteAddr(final DeleteAddrReq req,
                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/address/address_del", resp);
    }

    //10017 默认地址
    public final static void MorenAddr(final AddrMorenReq req,
                                       final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/address/address_default", resp);
    }

    //10018 选择区域
    public final static void Area(final AreaReq req,
                                  final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "login/area_list", resp);
    }

    //10021 获取积分历史页面数据
    public final static void getMemberPointInfo(final PointHistoryReq req,
                                                final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/point_his_list", resp);
    }

    //10022 获取我——>关注店铺  页面数据
    public final static void getAttentionShops(final AttentionShopsReq req,
                                               final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/shop_list", resp);
    }

    //10023 获取我——>服务通知  页面数据
    public final static void getServiceNotices(final MemberMsgReq req,
                                               final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/notice_list", resp);
    }

    //10024 获取我——>优惠券列表
    public final static void getCouponList(final MemberCouponsReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/coupon_list", resp);
    }

    //10025 修改用户姓名
    public final static void modifyDriverName(final ModifyDriverNameReq req,
                                              final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/name_edit", resp);
    }

    //10026 提交实名认证
    public final static void submitDriverCertification(final DriverCertificationReq req,
                                                       final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/card_submit", resp);
    }

    //10027 上传用户头像
    public final static void headUpload(final MemberUploadIconReq req,
                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/head_upload", resp);
    }

    //10029 上传车辆认证信息
    public final static void uploadCartCertificationInfo(final MemberCartCertificationReq req,
                                                         final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //10030 提交我的建议
    public final static void submitSuggestion(final SubmitSuggestionReq req,
                                              final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/suggest_submit", resp);
    }

    //10031 获取我的建议列表
    public final static void getSuggestions(final GetSuggestionReq req,
                                            final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/suggest_list", resp);
    }


    //10032 获取推荐页面信息
    public final static void getMemberRecommendInfo(final MemberRecommendReq req,
                                                    final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/recommended_driver", resp);
    }

    //10033 奖励明细
    public final static void JiangLiMingXi(final JiangLiMingXiReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/recommended_list", resp);
    }

    //10036 红包分享内容
    public final static void getRedEnvelopeShareContent(final RedEnvelopeShareReq req,
                                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //10028 我的车辆认证
    public final static void MyLoveCar(final MyLoveCarReq req,
                                       final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //2001 8001 首页
    public final static void home(final HomeReq req,
                                  final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "homepage/home_page", resp);
    }

    //2002 我的爱车
    public final static void GetMyCar(final MyCarReq req,
                                      final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "car/car_list", resp);
    }

    //2006 我的爱车—默认
    public final static void MoRenMyCar(final MoRenCarReq req,
                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "car/car_default", resp);
    }

    //2007 我的爱车—删除
    public final static void DeleteMyCar(final DeleteMyCarReq req,
                                         final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "car/car_del", resp);
    }

    //2008 品牌选择
    public final static void PinPaiXuanZe(final PinPaiXuanZeReq req,
                                          final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "car/brand_list_android", resp);
    }


    //20011 获取机油品牌列表
    public final static void OilBrand(final OilBrandReq req,
                                      final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //8004 搜索
    public final static void SouSuo(final SouSuoReq req,
                                    final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "homepage/shop_search", resp);
    }

    //20014 选车
    public final static void XuanChe(final XuanCheReq req,
                                     final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "car/car_select", resp);
    }

    //20013 提交添加的爱车信息
    public final static void submitCarInfos(final EditCarInfoReq req,
                                            final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "car/car_submit", resp);
    }

    //20015 提交添加的爱车信息
    public final static void submitMyCarDetail(final CarDetailSubmitReq req,
                                               final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "car/car_selected", resp);
    }

    //20016 获取爱车信息
    public final static void getMyCarDetail(final MyCarDetailReq req,
                                            final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "car/car_information", resp);
    }

    //	3001 获取车品商城列表
    public final static void getShopMallProductsLst(final ShopMallProductsReq req,
                                                    final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/goods/goods_list", resp);
    }

    // todo   3002商品详情
    //	3003 添加至购物车
    public final static void addProductToCart(final ShopMallAdd2CartReq req,
                                              final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/shopcar/shopcar_add", resp);
    }

    //	3004 更新购物车
    public final static void updateShopCart(final ShopCartEditUpdateReq req,
                                            final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/shopcar/shopcar_update", resp);
    }

    //	3005 购物车车品列表
    public final static void getShopCartProductsLst(final ShopCartProductsReq req,
                                                    final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/shopcar/shopcar_list", resp);
    }

    //	3006 购物车车品删除
    public final static void deleteShopCartItem(final ShopCartDeleteReq req,
                                                final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/shopcar/shopcar_del", resp);
    }

    //	3007 获取确认订单页面信息
    public final static void getConfirmOrderInfo(final ConfirmOrderReq req,
                                                 final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/bill_confirm", resp);
    }

    //	3008 结算车品订单
    public final static void generateOrderInfo(final GenerateOrderReq req,
                                               final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/bill_submit", resp);
    }

    // todo   30010车品订单收货成功
    public final static void comfirmReceiptTruckOrder(final TruckOrderConfirmReceiptReq req,
                                                      final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/bill_delivery", resp);
    }

//todo    30011车品订单评价成功

    //	30012取消车品订单
    public final static void cancleTruckOrder(final TruckOrderCancleReq req,
                                              final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/bill_cancel", resp);
    }

    //   30013车品订单删除成功
    public final static void deleteTruckOrder(final TruckOrderDeleteReq req,
                                              final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/bill_del", resp);
    }


    //	30015 获取车品订单列表
    public final static void getTruckOrderList(final TruckOrderListReq req,
                                               final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/bill_list", resp);
    }

    //	30016 获取车品订单详情信息
    public final static void getTruckOrderDetailInfo(final TruckOrderDetailReq req,
                                                     final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/bill_information", resp);
    }

    // 30017 获取评价页面订单信息
    public final static void getCommentProductInfo(final TruckOrderCommentReq req,
                                                   final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/bill_shopcar", resp);
    }

    //30021 获取微信支付参数
    public final static void getWXPayInfo(final WXPayInfoReq req,
                                          final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/bill_wechat_pay", resp);
    }

    //30022 发送车品订单商品评价
    public final static void sendTruckOrderComment(final SendTruckOrderCommentReq req,
                                                   final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/goods/goods_eval_submit", resp);
    }

    //30023 获取好礼商城车品详情（原生页面）
    public final static void getProductDetailInfo(final ProductDetailInfoReq req,
                                                  final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/goods/goods_information", resp);
    }

    //30024 获取好礼商城车品评价列表（原生页面）
    public final static void getProductCommentList(final GetProductCommentListReq req,
                                                   final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/goods/goods_eval_list", resp);
    }

    //30025 进入现场购买订单结算页面
    public final static void getXianChangBuyOrder(final XianChangBuyReq req,
                                                   final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/bill_confirm_scene", resp);
    }

    //30026 现场购买订单结算页面的提交订单
    public final static void commitXianChangBuyOrder(final XianChangCommitReq req,
                                                   final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/bill_submit_scene", resp);
    }

    //8003 品牌服务站
    public final static void PinPaiFuWuZhan(final PinPaiFuWuZhanReq req,
                                            final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "homepage/shop_list", resp);
    }

    //4008 店铺保养

    public final static void AddOil(final AddOilReq req,
                                    final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //4009 获取订单积分
    public final static void GetOrder(final GetOrderReq req,
                                      final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //40010 获取可用优惠券
    public final static void GetYouHuiQuan(final YouHuiQuanReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //40011 提交保养订单
    public final static void CommitOrder(final CommitOrderReq req,
                                         final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //8005 服务站详情
    public final static void ShopDetail(final ShopDetailReq req,
                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "homepage/shop_information", resp);
    }

    //4003 服务站详情(网页)
    public final static void ShopDetailWeb(final ShopDetailWebReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //4004 收藏店铺
    public final static void CollectShop(final CollectShopReq req,
                                         final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "homepage/shop_collection_add", resp);
    }

    //4005 取消收藏店铺
    public final static void CancleCollect(final CancleCollectReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "homepage/shop_collection_cancel", resp);
    }

    //40013 确定完成订单
    public final static void QueDingWanCheng(final QueDingWanChengReq req,
                                             final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //40015 取消订单
    public final static void QuXiaoDingDan(final QuXiaoDingDanReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //40017 删除订单
    public final static void DeleteOrder(final DeleteOrderReq req,
                                         final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //40020 服务站地图
    public final static void MapFWZ(final MapFWZReq req,
                                    final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "homepage/shop_map", resp);
    }

    //40021 保养订单列表
    public final static void SerViceOrderList(final OrderListReq req,
                                              final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //40022 保养订单列表
    public final static void OrderDetail(final OrderDetailReq req,
                                         final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //8006 评价列表
    public final static void PingJia(final PingJiaReq req,
                                     final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "homepage/shop_eval_list", resp);
    }

    //40025 返修详情
    public final static void FanXiuDetail(final FanXiuDetailReq req,
                                          final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //40027 油料筛选
    public final static void ShaiXuanOil(final ShaiXuanOilReq req,
                                         final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //40028 待安装订单申请取消
    public final static void ShenQingQuXiao(final ShenQingQuXiaoReq req,
                                            final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //5001 我的余额
    public final static void Yue(final YueReq req,
                                 final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "pay/money_list", resp);
    }

    //5004 获取银行卡列表
    public final static void getBankCardList(final BankCardListReq req,
                                             final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "pay/bankcard_list", resp);
    }

    //5005 添加新银行卡
    public final static void addNewBankCard(final AddBankCardReq req,
                                            final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "pay/bankcard_add", resp);
    }

    //5006 设置提现密码获取验证码
    public final static void getWithDrawCaptcha(final WithDrawCaptchaReq req,
                                                final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "pay/get_code", resp);
    }

    //5007 设置提现密码
    public final static void setWithDrawCode(final WithDrawCodeReq req,
                                             final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "pay/password_submit", resp);
    }

    //5008 校验提现验证码
    public final static void checkWithDrawCode(final CheckWithDrawCodeReq req,
                                               final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "pay/password_check", resp);
    }

    //6001 找货首页列表
    public final static void ZhaoHuo(final ZhaoHuoReq req,
                                     final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "findgoods/find_goods_list", resp);
    }

    //6002 找货货源详情
    public final static void HuoYuanDetail(final HuoYuanDetailReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "findgoods/find_goods_information", resp);
    }

    //6003 我的货单
    public final static void MyHuo(final MyHuoReq req,
                                   final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //6004 我的货单打电话
    public final static void Call(final CallReq req,
                                  final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //6005 我的车源
    public final static void MyCheYuan(final MyCheYuanReq req,
                                       final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //6006 发布我的车源
    public final static void FaBu(final FaBuReq req,
                                  final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //6007 发布我的车源
    public final static void LianXiJiLu(final LianXiJiLuReq req,
                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //6008 评价货单
    public final static void PingJiaHuoDan(final PingJiaHuoDanReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //60011 任务详情
    public final static void GetAdd(final GetAddReq req,
                                    final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advert/advert_information0", resp);
    }

    //60012 任务跳转
    public final static void TaskJump(final TaskJumpReq req,
                                      final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advert/check_recommended", resp);
    }

    //60013 检验任务推荐码
    public final static void CheckCode(final CheckCodeReq req,
                                       final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advert/binding_recommended", resp);
    }

    //600140 获取日历列表
    public final static void getCalendarList(final AdCalendarReq req,
                                             final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advert/advert_calendar", resp);
    }

    //60018 上传图片
    public final static void uploadImage(final UploadImageReq req,
                                         final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advert/advert_upload_pictures_qn", resp);
    }

    //60016 获取图片历史
    public final static void getImageList(final ImageHisReq req,
                                          final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advert/advert_upload_list", resp);
    }

    //60017 获取寻宝图标显示状态
    public final static void getCoinFlagShow(final GetFlagShowReq req,
                                             final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advert/advert_wing_show", resp);
    }

    //60019 获取车贴页面分享内容
    public final static void getStickerShareInfo(final StickerShareReq req,
                                                 final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advert/advert_share", resp);
    }

    //60020 车贴任务初次上传
    public final static void uploadCheTieImage(final UploadCheTieImageReq req,
                                               final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advert/advert_upload_pictures_first", resp);
    }

    //60021 向被推荐人申请车贴自动生成订单
    public final static void XiaDan(final XiaDanReq req,
                                    final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advert/advert_bill_recommended", resp);
    }

    //60031 获取车贴列表
    public final static void getCheTieList(final CheTieListReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advertrob/advertrob_list", resp);
    }

    //60032 获取车贴详情
    public final static void getCheTieDetail(final GetCheTieDetailReq req,
                                             final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advertrob/advertrob_information", resp);
    }

    //60033 抢车贴——上传行驶证
    public final static void QiangImage(final QiangImageReq req,
                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advertrob/upload_license", resp);
    }

    //60034 抢车贴——生成订单
    public final static void generateStickOrder(final GenerateStickerOrderReq req,
                                                final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advertrob/advertrob_bill_submit", resp);
    }

    //60035 抢车贴——订单列表
    public final static void CheTieOrder(final CheTieOrderReq req,
                                         final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advertrob/advertrob_bill_list", resp);
    }

    //60036 抢车贴——订单详情
    public final static void getCheTieOrderDetail(final CheTieOrderDetailReq req,
                                                  final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advertrob/advertrob_bill_information", resp);
    }

    //60037 抢车贴——取消订单
    public final static void cancleCheTieOrder(final CheTieOrderCancleReq req,
                                               final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advertrob/advertrob_bill_cancel", resp);
    }

    //60039 车贴订单——确认收货
    public final static void QueRenShouHuo(final QueRenShouHuoReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advertrob/advertrob_bill_delivery", resp);
    }

    //70010 获取发现列表
    public final static void getDiscoveryList(final DiscoveryListReq req,
                                              final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "news/news_list", resp);
    }

    //7002 获取发现详情页面地址
    public final static void getDiscoveryDetailUrl(final DiscoveryDetailReq req,
                                                   final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "news/news_information", resp);
    }

    //7003 获取收藏列表
    public final static void getDiscoveryFavorList(final DiscoveryListReq req,
                                                   final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "news/news_collection_list", resp);
    }

    //7004 发现，添加收藏
    public final static void addDiscoveryFavor(final DiscoveryAddFavorReq req,
                                               final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "news/news_collection_add", resp);
    }

    //7005 发现，取消收藏
    public final static void cancelDiscoveryFavor(final DiscoveryCancelFavorReq req,
                                                  final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "news/news_collection_cancel", resp);
    }

    //7006 发现，获取评论列表
    public final static void getDiscoveryCommentsList(final DiscoveryCommentsReq req,
                                                      final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "news/news_eval_list", resp);
    }

    //7007 发现，发布评论
    public final static void sendDiscoveryComment(final DiscoveryCommentSendReq req,
                                                  final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "news/news_eval_submit", resp);
    }

    //7008 获取分享信息
    public final static void getShareInfos(final DiscoveryShareReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "news/news_share", resp);
    }

    //70020 现场领取
    public final static void XianChangLingQu(final XianChangLingQuReq req,
                                             final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "wheel/wheel_receive_scene", resp);
    }

    //70021 我的奖品
    public final static void MyPrize(final MyPrizeReq req,
                                     final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "wheel/wheel_prize_list", resp);
    }

    //70022 获取默认地址
    public final static void GetAddr2(final GetAddrReq req,
                                      final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "wheel/wheel_addr", resp);
    }

    //70023 领取奖品
    public final static void LingQuPrize(final LingQuPrizeReq req,
                                         final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "wheel/wheel_receive_online", resp);
    }

    //70024 抽奖
    public final static void ChouJiang(final ChouJiangReq req,
                                       final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "wheel/wheel_web", resp);
    }

    //70040进入新用户抽奖密令网页
    public final static void validateLotteryCode(final ValidateCodeReq req,
                                                 final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //8002 获取每日积分详情
    public final static void getDailySignInfo(final DailySignReq req,
                                              final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "homepage/sign", resp);
    }

    //8007 店铺点评提交
    public final static void commitShop(final ShopCommitReq req,
                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "homepage/shop_eval_submit", resp);
    }

    //9000 获取违章查询预留信息
    public final static void getIllegalDriverInfo(final IllegalDriverInfoReq req,
                                                  final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "checkillegal/illegal_default", resp);
    }

    //9001 违章获取城市列表
    public final static void CityQuery(final CityInfoReq req,
                                       final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "checkillegal/city_list", resp);
    }

    //9002 违章查询
    public final static void IllegalQuery(final IllegalQueryReq req,
                                          final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "checkillegal/check_illegal", resp);
    }

    public final static void BrandQuery(final BaseReq req,
                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    ///8003 一键救援
    public final static void SOS(final SOSReq req,
                                 final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "homepage/shop_list", resp);
    }

    ///8009 获取秒杀商品详情列表
    public final static void getSeckillDetailList(final SeckillDetailReq req,
                                                  final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //	80010 获取确认订单页面信息
    public final static void getSeckillOrderInfo(final SeckillOrderReq req,
                                                 final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //	80011 获取订单是否过期
    public final static void isOrderOverTime(final OrderOverTimeReq req,
                                             final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/bill_is_late", resp);
    }

    //	80012 获取服务器当前时间
    public final static void getServerDateTime(final ServerDateTimeReq req,
                                               final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //	80013 获取订单限制时间
    public final static void getOrderTimeLimit(final OrderTimeLimitReq req,
                                               final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/bill_time_limit", resp);
    }

    //	qn01 获取七牛云token
    public final static void QiNiuYunToken(final QiNiuYunTokenReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "base/qiniuyun_key", resp);
    }
}
