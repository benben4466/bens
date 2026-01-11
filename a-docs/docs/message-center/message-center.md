# 生成规则
## 消息模板编码规则
编码规范：业务域.业务线.场景.渠道.动作.版本
长度要求：总长度 <= 64；单段 <= 15；

注意：
* 不要带-、_之类的分隔符，日志采集时会被当做列分割符。
* 不要出现日期（20260104），日期放在 version 字段中，code 只代表逻辑版本。
* 不要把变量放入，code 必须是静态枚举。

~~~tex
编码各项示例：
业务域：（Domain）
* USER：用户中心。
* PAY：支付。
* RISK：风控。
* MKT：营销。
* SUP：供应链。
业务线：（BizLine）
* CB：C 端 APP。
* MP：小程序。
* OP：运营后台。
* MER：商户端。
场景：（Scene）
* ORDER：订单
* RECHARGE：充值
* WITHDRAW：提现
* PROMO：活动
* LOGIN：登录
渠道：（Channel）
* SMS：短信。
* PUSH App：推送。
* EMAIL：邮件。
* WECHAT：微信模板消息。
* STATION：站内信。
动作：（Action）
* SUCCESS：成功。
* FAIL：失败。
* REMIND：提醒。
* CODE：验证码。
* MARKET：营销。
版本：（Version）
* V1：第一版。
* V2：第二版。
* HOT：热补版（临时紧急，用完废止）
~~~

## 消息渠道配置表
消息渠道配置表(message_channel_config)的渠道编码(channel_code)编码规则
~~~tex
channel_code: [渠道类型(TYPE)].[供应商(supplier)]_[用途/环境(SCENE)]  TYPE.SUPPLIER.DOMAIN.SCENE.VERSION
TYPE(一级分类): 表示通信方式(如 SMS, MAIL, WECHAT)
SUPPLIER(二级分类): 表示服务提供商(如 ALIYUN, TENCENT)
DOMAIN(三级分类): 标识业务域(USER, PAY)
SCENE(三级分类 - 可选): 当同一个供应商有多个账号或用途时使用(如 ORDER, RECHARGE); 可参考[消息模板编码规则]的编码各项示例中场景的枚举。
VERSION(四级分类 - 可选): 版本。
~~~

