package cn.tongdun.android.demo.paas.bean

/**
 * @Author yang.bai.
 * Date: 2023/2/6
 */
class FraudApiResponse {
    var result: HashMap<String, Any> = HashMap() // 风险类型

    var code: String = ""

    override fun toString(): String {
        return "FraudApiResponse{" +
                "result=" + result +
                ", code='" + code + '\'' +
                '}'
    }
}