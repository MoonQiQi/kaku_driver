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

    //8009 首页--更多服务
    public final static void MoreService(final ExitReq req,
                                         final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "homepage/more_services", resp);
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
    public final static void getMemberRecommendInfo(final ExitReq req,
                                                    final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/recommended_driver", resp);
    }

    //10033 奖励明细
    public final static void JiangLiMingXi(final JiangLiMingXiReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/recommended_list", resp);
    }

    //10034 我的优惠券
    public final static void MyCoupon(final ExitReq req,
                                      final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/coupon_list", resp);
    }

    //10035 客服帮助
    public final static void KeFuHelp(final ExitReq req,
                                      final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/service_help", resp);
    }

    //10036 红包分享内容
    public final static void getRedEnvelopeShareContent(final RedEnvelopeShareReq req,
                                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //10037 赚钱
    public final static void money(final ExitReq req,
                                   final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "personal/make_money", resp);
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
        postRequest(req, UrlCtnt.BASEIP + "car/car_list_new", resp);
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

    //20017 选发动机
    public final static void XuanFaDongJi(final XuanFaDongJiReq req,
                                          final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "car/engine_select", resp);
    }

    //20018 发动机信息
    public final static void FaDongJiInfo(final FaDongJiInfoReq req,
                                          final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "car/engine_submit", resp);
    }

    //20019 车辆保存
    public final static void SaveCar(final SaveCarReq req,
                                     final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "car/car_save", resp);
    }

    //20021 车辆修改
    public final static void ModifyCar(final ModifyFaDongJiReq req,
                                       final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "car/car_information_new", resp);
    }

    //20022 输码识车
    public final static void GetHandCode(final GetHandDataReq req,
                                         final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "car/car_code_find", resp);
    }

    //20023 保养更换爱车
    public final static void BYChangeCar(final BYChooseCarReq req,
                                         final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "car/car_default_new", resp);
    }

    //  3000 获取车品商城分类列表
    public final static void getShopMallFenLeiList(final ExitReq req,
                                                   final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/goods/goods_types", resp);
    }

    //	3001 获取车品商城列表
    public final static void getShopMallProductsLst(final ShopMallProductsReq req,
                                                    final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/goods/goods_list", resp);
    }


    //	3002 搜索中的热门商品
    public final static void SouSuoHot(final ExitReq req,
                                       final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/goods/goods_hot", resp);
    }

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
    public final static void getXianChangBuyOrder(final ConfirmOrderReq req,
                                                  final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/bill_confirm_scene", resp);
    }

    //30026 现场购买订单结算页面的提交订单
    public final static void commitXianChangBuyOrder(final XianChangCommitReq req,
                                                     final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/bill_submit_scene", resp);
    }

    //30028 缺货登记
    public final static void QueHuo(final QueHuoReq req,
                                    final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/bill_goods_stockout", resp);
    }

    //30029 再次购买
    public final static void BuyAgain(final BuyAgainReq req,
                                      final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/bill_buy_again", resp);
    }

    //30030 充话费获取初始价格
    public final static void HuaFei(final ExitReq req,
                                    final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/bill_phone_recharge_price", resp);
    }

    //30031 立即充值
    public final static void ChongZhi(final ChongZhiReq req,
                                      final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/bill_phone_recharge", resp);
    }

    //30032 立即充值支付
    public final static void ChongZhiZhiFu(final ChongZhiZhiFuReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "bill/bill_phone_pay", resp);
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
        postRequest(req, UrlCtnt.BASEIP + "bill/bill_coupon_list", resp);
    }

    //400107 获取可用优惠券
    public final static void GetYouHuiQuan2(final YouHuiQuanReq req,
                                            final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/service_coupon_list", resp);
    }

    //400117 保养店铺活动
    public final static void ShopHuoDong(final BaoYangListReq req,
                                         final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/upkeep_activity", resp);
    }

    //400118 我的仓库
    public final static void CangKu(final CangKuReq req,
                                    final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/upkeep_warehouse", resp);
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

    //40052 保养套餐详情
    public final static void BaoYangDetail(final BaoYangDetailReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/upkeep_information", resp);
    }

    //40053 保养订单确认
    public final static void OrderSure(final BaoYangDetailReq req,
                                       final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/upkeep_bill_confirm", resp);
    }

    //400116 保养订单提交
    public final static void CommitBill(final GenerateServiceOrderReq req,
                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/upkeep_submit", resp);
    }

    //40055 上传滤芯图片
    public final static void UploadFilter(final UploadFilterReq req,
                                          final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/upkeep_bill_picture", resp);
    }

    //400126 保养订单取消
    public final static void BaoYangOrderCancle(final BaoYangOrderCancleReq req,
                                                final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/bill_cancel", resp);
    }

    //40058 保养订单确认收货
    public final static void BaoYangOrderShou(final BaoYangOrderCancleReq req,
                                              final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/upkeep_bill_delivery", resp);
    }

    //400121 保养订单列表
    public final static void BaoYangOrderList(final TruckOrderListReq req,
                                              final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/bill_list", resp);
    }

    //400123 保养评价提交
    public final static void BaoYangPingJiaSubmit(final BaoYangPingJiaReq req,
                                                  final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/eval_submit", resp);
    }

    //40060 获取选择滤芯图片
    public final static void GetFilter(final GetFilterImageReq req,
                                       final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/upkeep_picture", resp);
    }

    //400127 删除保养订单
    public final static void BaoYangOrderDelete(final BaoYangOrderDeleteReq req,
                                                final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/bill_del", resp);
    }

    //400128 保养订单确认服务
    public final static void BaoYangOrderFuWu(final BaoYangOrderCancleReq req,
                                              final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/bill_confirm_driver", resp);
    }

    //400113  保养方案
    public final static void BaoYangShopDetail(final BaoYangListReq req,
                                               final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/upkeep_shop_choose", resp);
    }

    //400112 保养方案更换机油
    public final static void BaoYangChangeOil(final BaoYangListReq req,
                                              final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/upkeep_oil_list", resp);
    }

    //400114 保养方案更换滤芯
    public final static void BaoYangChangeFilter(final BaoYangListReq req,
                                                 final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/upkeep_filter_list", resp);
    }

    //40073 保养方案更换
    public final static void BaoYangSubmit(final BaoYangSubmitReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/upkeep_plan_submit", resp);
    }

    //40074 保养选门店列表
    public final static void BYChooseShop(final ChooseShopReq req,
                                          final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/upkeep_shop_list", resp);
    }

    //400115 保养订单
    public final static void BaoYangOrder(final GetBaoYangOrderReq req,
                                          final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/upkeep_confirm", resp);
    }

    //40076  提交门店申请
    public final static void CommitMen(final CommitShopReq req,
                                       final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/upkeep_shop_submit", resp);
    }

    //400125  保养清单
    public final static void BaoYangQingDan(final ExitReq req,
                                            final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/service_item_list", resp);
    }

    //400122 保养订单详情
    public final static void BaoYangOrderDetail(final BaoYangOrderDetailReq req,
                                                final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/bill_information", resp);
    }

    //40082 打黄油订单评价页面信息
    public final static void OilPingJia(final OilPingJiaReq req,
                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/upkeep_bill_information_shop", resp);
    }

    //400101  打黄油获取当前任务状态
    public final static void YellowOil(final YellowOilReq req,
                                       final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/service_state", resp);
    }

    //400102  打黄油获取当前服务价格
    public final static void XuanZeFuWu(final XuanZeFuWuReq req,
                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/service_price", resp);
    }

    //400104  取消服务
    public final static void QuXiaoFuWu(final BaoYangOrderCancleReq req,
                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/service_cancel", resp);
    }

    //400103  呼叫服务
    public final static void HuJiaoFuWu(final HuJiaoFuWuReq req,
                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/service_call", resp);
    }

    //400105  打黄油确认服务
    public final static void YellowOilOrder(final YellowOilOrderReq req,
                                            final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/service_confirm", resp);
    }

    //400106  打黄油轮胎，确认支付更改支付方式
    public final static void ModifyPayType(final ModifyPayTypeReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/service_pay_change", resp);
    }

    //400107  打黄油提交订单
    public final static void YellowOilSubmit(final YellowOilSubmitReq req,
                                             final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/service_submit", resp);
    }

    //400111 保养方案
    public final static void BaoYang(final BaoYangReq req,
                                     final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "upkeep/upkeep_shop_state", resp);
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

    //60010 车贴总结
    public final static void CheTieZongJie(final CheTieZongJieReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advert/advert_information_total", resp);
    }

    //60011 任务详情
    public final static void GetAdd(final GetAddReq req,
                                    final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advert/advert_information", resp);
    }

    //600111 大日历
    public final static void BigCalendar(final BaseReq req,
                                         final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advert/advert_information_month", resp);
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

    //60022 广告评价
    public final static void commitAd(final AdCommitReq req,
                                      final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advert/advert_eval_submit", resp);
    }

    //60023 广告评价列表
    public final static void adPingJiaList(final AdPingJialistReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advert/advert_eval_list", resp);
    }

    //60024 获取拍照反馈获得的优惠券
    public final static void GetFanKui(final ExitReq req,
                                       final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advert/advert_get_coupon", resp);
    }

    //60031 获取车贴列表
    public final static void getQiangCheTieList(final CheTieListReq req,
                                                final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advertrob/advertrob_list", resp);
    }

    //600100 获取车贴列表
    public final static void getCheTieList(final CheTieListReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advert/advert_list", resp);
    }

    //60032 获取车贴详情
    public final static void getCheTieDetail(final GetCheTieDetailReq req,
                                             final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advertrob/advertrob_information", resp);
    }

    //60033 抢车贴——上传行驶证
    public final static void QiangImage(final QiangImageReq req,
                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "homepage/zero_buy_submit", resp);
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

    //60051 记账本
    public final static void MoneyBook(final MoneyBookReq req,
                                       final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "findgoods/account_list", resp);
    }

    //60052 添加记账本
    public final static void WhiteMoney(final WhiteMoneyReq req,
                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "findgoods/account_add", resp);
    }

    //60053 网络电话通讯记录
    public final static void CallList(final ExitReq req,
                                      final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "findgoods/duration_list", resp);
    }

    //60054 网络电话拨打提交
    public final static void CallSubmit(final CallSubmitReq req,
                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "findgoods/duration_submit", resp);
    }

    //60055 网络电话更多列表
    public final static void CallMore(final ExitReq req,
                                      final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "findgoods/duration_get_list", resp);
    }

    //60057 当前在线听广播人数
    public final static void gbPeople(final ExitReq req,
                                      final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "findgoods/fm_get_num", resp);
    }

    //70010 获取头条列表
    public final static void getDiscoveryList(final DiscoveryListReq req,
                                              final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "news/news_list", resp);
    }

    //70011 获取圈子列表
    public final static void getDiscoveryList2(final DiscoveryListReq req,
                                               final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "news/circle_list", resp);
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

    //70012 圈子详情
    public final static void getQuanziDetail(final QuanziDetailReq req,
                                             final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "news/circle_information", resp);
    }

    //70013 圈子提交
    public final static void QuanziSubmit(final QuanziSubmitReq req,
                                          final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "news/circle_submit", resp);
    }

    //70015 圈子回复
    public final static void QuanziHuifu(final QuanziHuifuReq req,
                                         final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "news/circle_eval_submit", resp);
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
    public final static void getDailySignInfo(final ExitReq req,
                                              final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "homepage/sign", resp);
    }

    //8006 评价列表
    public final static void PingJia(final PingJiaReq req,
                                     final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "homepage/shop_eval_list", resp);
    }


    //8007 店铺点评提交
    public final static void commitShop(final ShopCommitReq req,
                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "homepage/shop_eval_submit", resp);
    }

    //8008 设置签到提醒
    public final static void qiandaotixing(final QianDaoTiXingReq req,
                                           final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "homepage/sign_remind", resp);
    }

    //9000 获取违章查询预留信息
    public final static void getIllegalDriverInfo(final IllegalDriverInfoReq req,
                                                  final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "checkillegal/illegal_default", resp);
    }

    //9001 违章获取城市列表
    public final static void CityQuery(final ExitReq req,
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
    public final static void zero(final ExitReq req,
                                  final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "homepage/zero_buy", resp);
    }

    //	80011 0元购提交申请
    public final static void zerocommit(final ExitReq req,
                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "homepage/zero_buy_submit", resp);
    }

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

    //	600110 获取车贴id和type
    public final static void GetAdType(final AdTypeReq req,
                                       final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "advert/advert_information_type", resp);
    }

    //	70026 优惠券转盘分享成功
    public final static void ShareAfter(final ExitReq req,
                                        final KakuResponseListener resp) {
        postRequest(req, UrlCtnt.BASEIP + "wheel/coupon_rotary_share", resp);
    }
}
