package platform.common.base.collect;


public enum TargetCode {
  /***** 新增用户 *****/
  @Target( code = "011101", desc = "当天激活用户" )
  cur_day_active_user_count, @Target( code = "011102", desc = "当天注册用户" )
  cur_day_register_user_count, @Target( code = "011103", desc = "累计激活用户" )
  sum_day_active_user_count, @Target( code = "011104", desc = "累计注册用户" )
  sum_day_register_user_count,
  /***** 新增用户 *****/

  /***** 活跃用户 *****/
  @Target( code = "012101", desc = "每日活跃用户" )
  cur_day_action_user_count, @Target( code = "012102", desc = "每日付费用户" )
  cur_day_pay_user_count,
  /***** 活跃用户 *****/

  /***** 基本付费指标 *****/
  @Target( code = "041101", desc = "付费用户数" )
  pay_user_count, @Target( code = "041106", desc = "每日ARPU" )
  cur_day_arpu, @Target( code = "041107", desc = "每周ARPU" )
  seven_day_arpu, @Target( code = "041109", desc = "每日成交金额" )
  cur_day_order_money, @Target( code = "041110", desc = "每日销售金额" )
  cur_day_income_money,
  /***** 基本付费指标 *****/

  /***** 交易分析 *****/
  @Target( code = "042201", desc = "每日订单数" )
  cur_day_order_count, @Target( code = "042202", desc = "每日订单交易成功率" )
  cur_day_order_success_ratio, @Target( code = "042204_31", desc = "每日销售额-储值卡" )
  cur_day_cunzhika_income_money, @Target( code = "042204_12", desc = "每日销售额-买单" )
  cur_day_maidan_income_money, @Target( code = "042204_03", desc = "每日销售额-金币商城" )
  cur_day_jinbishangcheng_income_money,

  @Target( code = "042205_1", desc = "每日订单数-美食" )
  cur_day_order_count_meishi, @Target( code = "042205_12", desc = "每日订单数-购物" )
  cur_day_order_count_gouwu, @Target( code = "042205_23", desc = "每日订单数-休闲" )
  cur_day_order_count_xiuxian, @Target( code = "042205_32", desc = "每日订单数-娱乐" )
  cur_day_order_count_yule, @Target( code = "042205_38", desc = "每日订单数-旅游" )
  cur_day_order_count_lvyou, @Target( code = "042205_43", desc = "每日订单数-生活服务" )
  cur_day_order_count_shenghuofuwu,
  /***** 交易分析 *****/

  /**** 储值卡充值 ****/
  @Target( code = "052102", desc = "储值卡充值订单数" )
  cur_day_order_count_cunzhikachongzhi, @Target( code = "052103", desc = "储值卡充值订单交易成功率" )
  cur_day_order_success_ratio_cunzhikachongzhi, @Target( code = "052104", desc = "储值卡充值每日成交金额" )
  cur_day_order_money_cunzhika, @Target( code = "052105", desc = "储值卡充值每日销售金额" )
  cur_day_income_money_cunzhika, @Target( code = "052106", desc = "储值卡充值客单价" )
  cur_day_cunzhika_kedanjia, /*
                              * @Target( code = "052107", desc =
                              * "储值卡充值当天储值卡各档位销售数量" ) cur_day_active_user,
                              */@Target( code = "052108", desc = "储值卡充值人均储值卡数量" )
  cur_day_cunzhikachongzhi_per_count;
  /**** 储值卡充值 ****/

  /**
   * 返回指标
   */
  public Target getTarget() {
    Target target;
    try {
      target = this.getClass().getField(this.name()).getAnnotation(Target.class);
    }
    catch(Exception e) {
      return null;
    }
    return target;
  }

}
