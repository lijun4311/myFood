package resource;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "wxpay")
@PropertySource("classpath:wxpay.properties")


public class WxPayResource {
    @Getter
    private static String qrcodeKey;
    @Getter
    private static long qrcodeExpire;
    @Getter
    private static String appId;
    @Getter
    private static String merchantId;
    @Getter
    private static String secrectKey;
    @Getter
    private static String spbillCreateIp;
    @Getter
    private static String notifyUrl;
    @Getter
    private static String tradeType;
    @Getter
    private static String placeOrderUrl;

    public void setQrcodeKey(String qrcodeKey) {
        WxPayResource.qrcodeKey = qrcodeKey;
    }

    public void setQrcodeExpire(long qrcodeExpire) {
        WxPayResource.qrcodeExpire = qrcodeExpire;
    }

    public void setAppId(String appId) {
        WxPayResource.appId = appId;
    }

    public void setMerchantId(String merchantId) {
        WxPayResource.merchantId = merchantId;
    }

    public void setSecrectKey(String secrectKey) {
        WxPayResource.secrectKey = secrectKey;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        WxPayResource.spbillCreateIp = spbillCreateIp;
    }

    public void setNotifyUrl(String notifyUrl) {
        WxPayResource.notifyUrl = notifyUrl;
    }

    public void setTradeType(String tradeType) {
        WxPayResource.tradeType = tradeType;
    }

    public void setPlaceOrderUrl(String placeOrderUrl) {
        WxPayResource.placeOrderUrl = placeOrderUrl;
    }
}
