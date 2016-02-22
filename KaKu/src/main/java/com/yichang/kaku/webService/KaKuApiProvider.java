package com.yichang.kaku.webService;

import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.request.*;
import com.yichang.kaku.response.*;

public class KaKuApiProvider extends WebApiProvider {

    //1000 退出
    public final static void exit(final ExitReq req,
                                  final BaseCallback<ExitResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //1001 自动登录
    public final static void autologin(final AutoLoginReq req,
                                       final BaseCallback<AutoLoginResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //1002 手动登录
    public final static void login(final LoginReq req,
                                   final BaseCallback<LoginResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //1003 获取验证码
    public final static void getCaptcha(MobileReq req,
                                        BaseCallback<MobileResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //10010 检查更新
    public final static void checkUpdate(CheckUpdateReq req,
                                        BaseCallback<CheckUpdateResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //10011 获取司机member页面信息
    public final static void getMemberDriverInfo(MemberDriverReq req,
                                                 BaseCallback<MemberDriverResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //10012 获取member页面 司机个人资料,用户资料
    public final static void getDriverInfo(MemberDriverInfoReq req,
                                                 BaseCallback<MemberDriverInfoResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //10014 我的地址
    public final static void getAddr(AddrReq req,
                                     BaseCallback<AddrResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //10015 新建/编辑收货地址
    public final static void NewAddr(NewAddrReq req,
                                                 BaseCallback<NewAddrResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //10016 删除地址
    public final static void DeleteAddr(DeleteAddrReq req,
                                                 BaseCallback<DeleteAddrResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //10017 默认地址
    public final static void MorenAddr(AddrMorenReq req,
                                                 BaseCallback<AddrMorenResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //10018 选择区域
    public final static void Area(AreaReq req,
                                                 BaseCallback<AreaResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //10021 获取积分历史页面数据
    public final static void getMemberPointInfo(PointHistoryReq req,
                                  BaseCallback<PointHistoryResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //10022 获取我——>关注店铺  页面数据
    public final static void getAttentionShops(AttentionShopsReq req,
                                                BaseCallback<AttentionShopsResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    //10023 获取我——>服务通知  页面数据
    public final static void getServiceNotices(MemberMsgReq req,
                                               BaseCallback<MemberMsgResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //10024 获取我——>优惠券列表
    public final static void getCouponList(MemberCouponsReq req,
                                               BaseCallback<MemberCouponsResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    //10025 修改用户姓名
    public final static void modifyDriverName(ModifyDriverNameReq req,
                                               BaseCallback<ModifyDriverNameResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //10026 提交实名认证
    public final static void submitDriverCertification(DriverCertificationReq req,
                                              BaseCallback<DriverCertificationResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //10027 上传用户头像
    public final static void uploadDriverIcon(MemberUploadIconReq req,
                                                       BaseCallback<MemberUploadIconResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //10029 上传车辆认证信息
    public final static void uploadCartCertificationInfo(MemberCartCertificationReq req,
                                              BaseCallback<MemberCartCertificationResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //10030 提交我的建议
    public final static void submitSuggestion(SubmitSuggestionReq req,
                                                         BaseCallback<SubmitSuggestionResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //10031 获取我的建议列表
    public final static void getSuggestions(GetSuggestionReq req,
                                              BaseCallback<GetSuggestionResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }


    //10032 获取推荐页面信息
    public final static void getMemberRecommendInfo(MemberRecommendReq req,
                                                         BaseCallback<MemberRecommendResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    //10033 奖励明细
    public final static void JiangLiMingXi(JiangLiMingXiReq req,
                                                         BaseCallback<JiangLiMingXiResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //10036 红包分享内容
    public final static void getRedEnvelopeShareContent(RedEnvelopeShareReq req,
                                           BaseCallback<RedEnvelopeShareResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    //10028 我的车辆认证
    public final static void MyLoveCar(MyLoveCarReq req,
                                                 BaseCallback<MyLoveCarResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //2001 8001 首页
    public final static void home(final HomeReq req,
                                  final BaseCallback<HomeResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //2002 我的爱车
    public final static void GetMyCar(final MyCarReq req,
                                      final BaseCallback<MyCarResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //2003 车辆选择
    public final static void ChooseCar(final ChooseCarReq req,
                                       final BaseCallback<ChooseCarResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //2004 车辆列表
    public final static void CarList(final CarListReq req,
                                     final BaseCallback<CarListResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //2005 我的爱车—保存
    public final static void OkCar(final OkCarReq req,
                                   final BaseCallback<OkCarResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //2006 我的爱车—默认
    public final static void MoRenMyCar(final MoRenCarReq req,
                                        final BaseCallback<MoRenCarResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //2007 我的爱车—删除
    public final static void DeleteMyCar(final DeleteMyCarReq req,
                                         final BaseCallback<DeleteMyCarResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //2008 品牌选择
    public final static void PinPaiXuanZe(final PinPaiXuanZeReq req,
                                          final BaseCallback<PinPaiXuanZeResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //20010 我的爱车—获取
    public final static void GetOkCar(final GetOkCarReq req,
                                      final BaseCallback<GetOkCarResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //20011 获取机油品牌列表
    public final static void OilBrand(final OilBrandReq req,
                                      final BaseCallback<OilBrandResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //20012 搜索
    public final static void SouSuo(final SouSuoReq req,
                                    final BaseCallback<SouSuoResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

//20014 选车
    public final static void XuanChe(final XuanCheReq req,
                                    final BaseCallback<XuanCheResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
 //20013 提交添加的爱车信息
    public final static void submitCarInfos(final EditCarInfoReq req,
                                    final BaseCallback<EditCarInfoResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //20015 提交添加的爱车信息
    public final static void submitMyCarDetail(final CarDetailSubmitReq req,
                                            final BaseCallback<CarDetailSubmitResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    //20016 获取爱车信息
    public final static void getMyCarDetail(final MyCarDetailReq req,
                                               final BaseCallback<MyCarDetailResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    //	3001 获取车品商城列表
    public final static void getShopMallProductsLst(final ShopMallProductsReq req,
                                                    final BaseCallback<ShopMallProductsResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    //	3002 点击商品列表条目，获取商品详情页面地址
    public final static void getProductDetailUrl(final ProductDetailUrlReq req,
                                                    final BaseCallback<ProductDetailUrlResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    // todo   3002商品详情
    //	3003 添加至购物车
    public final static void addProductToCart(final ShopMallAdd2CartReq req,
                                              final BaseCallback<ShopMallAdd2CartResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //	3004 更新购物车
    public final static void updateShopCart(final ShopCartEditUpdateReq req,
                                            final BaseCallback<ShopCartEditUpdateResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }


    //	3005 获取车品商城购物车商品列表
    public final static void getShopCartProductsLst(final ShopCartProductsReq req,
                                                    final BaseCallback<ShopCartProductsResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //	3006 获取车品商城购物车商品列表
    public final static void deleteShopCartItem(final ShopCartDeleteReq req,
                                                final BaseCallback<ShopCartDeleteResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //	3007 获取确认订单页面信息
    public final static void getConfirmOrderInfo(final ConfirmOrderReq req,
                                                 final BaseCallback<ConfirmOrderResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //	3008 结算车品订单
    public final static void generateOrderInfo(final GenerateOrderReq req,
                                               final BaseCallback<GenerateOrderResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //	3009 立即支付车品订单
    public final static void payTruckOrder(final TruckOrderPayReq req,
                                           final BaseCallback<TruckOrderPayResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    // todo   30010车品订单收货成功
    public final static void comfirmReceiptTruckOrder(final TruckOrderConfirmReceiptReq req, final BaseCallback<TruckOrderConfirmReceiptResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

//todo    30011车品订单评价成功

    //	30012 取消车品订单
    public final static void cancleTruckOrder(final TruckOrderCancleReq req,
                                              final BaseCallback<TruckOrderCancleResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //   30013车品订单删除成功
    public final static void deleteTruckOrder(final TruckOrderDeleteReq req,
                                              final BaseCallback<TruckOrderDeleteResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }


    //	30015 获取车品订单列表
    public final static void getTruckOrderList(final TruckOrderListReq req,
                                               final BaseCallback<TruckOrderListResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //	30016 获取车品订单详情信息
    public final static void getTruckOrderDetailInfo(final TruckOrderDetailReq req,
                                                     final BaseCallback<TruckOrderDetailResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    // 30017 获取评价页面订单信息
    public final static void getCommentProductInfo(final TruckOrderCommentReq req,
                                                     final BaseCallback<TruckOrderCommentResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    // 30018 提交车品订单评价
    public final static void submitTruckOrderComment(final TruckOrderCommentSubmitReq req,
                                                     final BaseCallback<TruckOrderCommentSubmitResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //30021 获取微信支付参数
    public final static void getWXPayInfo(final WXPayInfoReq req,
                                          final BaseCallback<WXPayInfoResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //30022 发送车品订单商品评价
    public final static void sendTruckOrderComment(final SendTruckOrderCommentReq req,
                                          final BaseCallback<SendTruckOrderCommentResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //30023 获取好礼商城车品详情（原生页面）
    public final static void getProductDetailInfo(final ProductDetailInfoReq req,
                                                   final BaseCallback<ProductDetailInfoResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //30024 获取好礼商城车品评价列表（原生页面）
    public final static void getProductCommentList(final GetProductCommentListReq req,
                                                  final BaseCallback<GetProductCommentListResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //4001 品牌服务站
    public final static void PinPaiFuWuZhan(final PinPaiFuWuZhanReq req,
                                            final BaseCallback<PinPaiFuWuZhanResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //40019 获取微信支付订单信息
    public final static void getWxPayOrder(final WxPayReq req,
                                           final BaseCallback<WxpayResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);

    }//4008 店铺保养

    public final static void AddOil(final AddOilReq req,
                                    final BaseCallback<AddOilResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //4009 获取订单积分
    public final static void GetOrder(final GetOrderReq req,
                                      final BaseCallback<GetOrderResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //40010 获取可用优惠券
    public final static void GetYouHuiQuan(final YouHuiQuanReq req,
                                      final BaseCallback<YouHuiQuanResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //40011 提交保养订单
    public final static void CommitOrder(final CommitOrderReq req,
                                         final BaseCallback<CommitOrderResp> resp) {
        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //4002 服务站详情
    public final static void ShopDetail(final ShopDetailReq req,
                                        final BaseCallback<ShopDetailResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //4003 服务站详情(网页)
    public final static void ShopDetailWeb(final ShopDetailWebReq req,
                                           final BaseCallback<ShopDetailWebResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //4004 收藏店铺
    public final static void CollectShop(final CollectShopReq req,
                                         final BaseCallback<CollectShopResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //4005 取消收藏店铺
    public final static void CancleCollect(final CancleCollectReq req,
                                           final BaseCallback<CancleCollectResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //4006 保养
    public final static void BaoYang(final BaoYangReq req,
                                     final BaseCallback<BaoYangResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    //40012 预约保养订单支付成功
    public final static void payServiceOrder(final ServiceOrderPayReq req,
                                             final BaseCallback<ServiceOrderPayResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    //40013 确定完成订单
    public final static void QueDingWanCheng(final QueDingWanChengReq req,
                                             final BaseCallback<QueDingWanChengResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //40015 取消订单
    public final static void QuXiaoDingDan(final QuXiaoDingDanReq req,
                                           final BaseCallback<QuXiaoDingDanResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //40015 订单-申请返修
    public final static void FanXiu(final FanXiuReq req,
                                    final BaseCallback<FanXiuResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //40017 删除订单
    public final static void DeleteOrder(final DeleteOrderReq req,
                                         final BaseCallback<DeleteOrderResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //40020 服务站地图
    public final static void MapFWZ(final MapFWZReq req,
                                    final BaseCallback<MapFWZResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //40021 保养订单列表
    public final static void SerViceOrderList(final OrderListReq req,
                                              final BaseCallback<OrderListResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //40022 保养订单列表
    public final static void OrderDetail(final OrderDetailReq req,
                                         final BaseCallback<OrderDetailResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //40023 获取待评价的订单列表
    public final static void GetPingJiaOrder(final GetPingJiaOrderReq req,
                                             final BaseCallback<GetPingJiaOrderResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //40024 评价列表
    public final static void PingJia(final PingJiaReq req,
                                     final BaseCallback<PingJiaResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //40025 评价列表
    public final static void FanXiuDetail(final FanXiuDetailReq req,
                                          final BaseCallback<FanXiuDetailResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
   /* //40026 获取微信支付参数
    public final static void getWXPayInfo(final WXPayInfoReq req,
                                          final BaseCallback<WXPayInfoResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }*/

    //40027 油料筛选
    public final static void ShaiXuanOil(final ShaiXuanOilReq req,
                                          final BaseCallback<ShaiXuanOilResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //40028 待安装订单申请取消
    public final static void ShenQingQuXiao(final ShenQingQuXiaoReq req,
                                          final BaseCallback<ShenQingQuXiaoResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //5001 待安装订单申请取消
    public final static void Yue(final YueReq req,
                                          final BaseCallback<YueResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //5004 获取银行卡列表
    public final static void getBankCardList(final BankCardListReq req,
                                 final BaseCallback<BankCardListResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //5005 添加新银行卡
    public final static void addNewBankCard(final AddBankCardReq req,
                                             final BaseCallback<BaseResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //5006 设置提现密码获取验证码
    public final static void getWithDrawCaptcha(final WithDrawCaptchaReq req,
                                            final BaseCallback<BaseResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //5007 设置提现密码
    public final static void setWithDrawCode(final WithDrawCodeReq req,
                                                final BaseCallback<BaseResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //5008 校验提现验证码
    public final static void checkWithDrawCode(final CheckWithDrawCodeReq req,
                                            final BaseCallback<BaseResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //6001 找货首页列表
    public final static void ZhaoHuo(final ZhaoHuoReq req,
                                    final BaseCallback<ZhaoHuoResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //6002 找货货源详情
    public final static void HuoYuanDetail(final HuoYuanDetailReq req,
                                    final BaseCallback<HuoYuanDetailResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //6003 我的货单
    public final static void MyHuo(final MyHuoReq req,
                                    final BaseCallback<MyHuoResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //6004 我的货单打电话
    public final static void Call(final CallReq req,
                                    final BaseCallback<CallResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //6005 我的车源
    public final static void MyCheYuan(final MyCheYuanReq req,
                                    final BaseCallback<MyCheYuanResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //6006 发布我的车源
    public final static void FaBu(final FaBuReq req,
                                    final BaseCallback<FaBuResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //6007 发布我的车源
    public final static void LianXiJiLu(final LianXiJiLuReq req,
                                    final BaseCallback<LianXiJiLuResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //6008 评价货单
    public final static void PingJiaHuoDan(final PingJiaHuoDanReq req,
                                    final BaseCallback<PingJiaHuoDanResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //60011 任务详情
    public final static void GetAdd(final GetAddReq req,
                                    final BaseCallback<GetAddResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //60012 任务跳转
    public final static void TaskJump(final TaskJumpReq req,
                                    final BaseCallback<TaskJumpResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //60013 检验任务推荐码
    public final static void CheckCode(final CheckCodeReq req,
                                    final BaseCallback<CheckCodeResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //60014 获取日历列表
    public final static void getCalendarList(final AdCalendarReq req,
                                       final BaseCallback<AdCalendarResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //60015 上传图片
    public final static void uploadImage(final UploadImageReq req,
                                       final BaseCallback<UploadImageResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //60016 获取图片历史
    public final static void getImageList(final ImageHisReq req,
                                       final BaseCallback<ImageHisResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //60017 获取寻宝图标显示状态
    public final static void getCoinFlagShow(final GetFlagShowReq req,
                                          final BaseCallback<GetFlagShowResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //60019 获取车贴页面分享内容
    public final static void getStickerShareInfo(final StickerShareReq req,
                                             final BaseCallback<StickerShareResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //60031 获取车贴列表
    public final static void getCheTieList(final CheTieListReq req,
                                             final BaseCallback<CheTieListResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //60032 获取车贴详情
    public final static void getCheTieDetail(final GetCheTieDetailReq req,
                                             final BaseCallback<GetCheTieDetailResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //60033 抢车贴——上传行驶证
    public final static void QiangImage(final QiangImageReq req,
                                             final BaseCallback<QiangImageResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //60034 抢车贴——生成订单
    public final static void generateStickOrder(final GenerateStickerOrderReq req,
                                        final BaseCallback<GenerateStickerOrderResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
//60035 抢车贴——订单列表
    public final static void CheTieOrder(final CheTieOrderReq req,
                                             final BaseCallback<CheTieOrderResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    //60036 抢车贴——订单详情
    public final static void getCheTieOrderDetail(final CheTieOrderDetailReq req,
                                                final BaseCallback<CheTieOrderDetailResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //60037 抢车贴——取消订单
    public final static void cancleCheTieOrder(final CheTieOrderCancleReq req,
                                                  final BaseCallback<BaseResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //天气
    public final static void Weather(final WeatherReq req,
                                    final BaseCallback<WeatherResp> resp) {

        postRequest(req, UrlCtnt.WEATHERIP, resp);
    }


    //7001 获取发现列表
    public final static void getDiscoveryList(final DiscoveryListReq req,
                                           final BaseCallback<DiscoveryListResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    //7002 获取发现详情页面地址
    public final static void getDiscoveryDetailUrl(final DiscoveryDetailReq req,
                                              final BaseCallback<DiscoveryDetailResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    //7003 获取收藏列表
    public final static void getDiscoveryFavorList(final DiscoveryListReq req,
                                              final BaseCallback<DiscoveryListResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    //7004 发现，添加收藏
    public final static void addDiscoveryFavor(final DiscoveryAddFavorReq req,
                                              final BaseCallback<DiscoveryAddFavorResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    //7005 发现，取消收藏
    public final static void cancelDiscoveryFavor(final DiscoveryCancelFavorReq req,
                                               final BaseCallback<DiscoveryCancelFavorResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    //7006 发现，获取评论列表
    public final static void getDiscoveryCommentsList(final DiscoveryCommentsReq req,
                                                  final BaseCallback<DiscoveryCommentsResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //7007 发现，发布评论
    public final static void sendDiscoveryComment(final DiscoveryCommentSendReq req,
                                                      final BaseCallback<DiscoveryCommentSendResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    //7008 获取分享信息
    public final static void getShareInfos(final DiscoveryShareReq req,
                                               final BaseCallback<DiscoveryShareResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    //70020 现场领取
    public final static void XianChangLingQu(final XianChangLingQuReq req,
                                               final BaseCallback<XianChangLingQuResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    //70021 我的奖品
    public final static void MyPrize(final MyPrizeReq req,
                                               final BaseCallback<MyPrizeResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    //70022 获取默认地址
    public final static void GetAddr2(final GetAddrReq req,
                                               final BaseCallback<GetAddrResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    //70023 领取奖品
    public final static void LingQuPrize(final LingQuPrizeReq req,
                                               final BaseCallback<LingQuPrizeResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
    //70024 抽奖
    public final static void ChouJiang(final ChouJiangReq req,
                                               final BaseCallback<ChouJiangResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //70039进入新用户抽奖密令网页
    public final static void getLotteryUrl(final LotteryUrlReq req,
                                       final BaseCallback<LotteryUrlResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //70040进入新用户抽奖密令网页
    public final static void validateLotteryCode(final ValidateCodeReq req,
                                           final BaseCallback<ValidateCodeResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    ///8002 获取每日积分详情
    public final static void getDailySignInfo(final DailySignReq req,
                                       final BaseCallback<DailySignResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
	
	//9000 获取违章查询预留信息
    public final static void getIllegalDriverInfo(final IllegalDriverInfoReq req,
                                       final BaseCallback<IllegalDirverInfoResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //9001 违章获取城市列表
    public final static void CityQuery(final CityInfoReq req,
                                       final BaseCallback<IllegalResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
	
	//9002 违章查询
    public final static void IllegalQuery(final IllegalQueryReq req,
                                          final BaseCallback<IllegalQueryResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }	
	
	public final static void BrandQuery(final BaseReq req,
                                          final BaseCallback<BrandListResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
	
    ///8003 一键救援
    public final static void SOS(final SOSReq req,
                                       final BaseCallback<SOSResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    ///8009 获取秒杀商品详情列表
    public final static void getSeckillDetailList(final SeckillDetailReq req,
                                              final BaseCallback<SeckillDetailResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //	80010 获取确认订单页面信息
    public final static void getSeckillOrderInfo(final SeckillOrderReq req,
                                                 final BaseCallback<SeckillOrderResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //	9007 获取确认订单页面信息
    public final static void getQuestionDetail(final QuestionDetailReq req,
                                                 final BaseCallback<String> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //	80011 获取订单是否过期
    public final static void isOrderOverTime(final OrderOverTimeReq req,
                                               final BaseCallback<OrderOverTimeResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //	80012 获取服务器当前时间
    public final static void getServerDateTime(final ServerDateTimeReq req,
                                                 final BaseCallback<ServerDateTimeResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //	80013 获取订单限制时间
    public final static void getOrderTimeLimit(final OrderTimeLimitReq req,
                                               final BaseCallback<OrderTimeLimitResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }

    //	qn01 获取七牛云token
    public final static void QiNiuYunToken(final QiNiuYunTokenReq req,
                                               final BaseCallback<QiNiuYunTokenResp> resp) {

        postRequest(req, UrlCtnt.BASEIP, resp);
    }
}
