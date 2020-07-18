package resource;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "alipay")
@PropertySource("classpath:alipay.properties")

public class AliPayResource {
    @Getter
    private static String appId;
    @Getter
    private static String merchantPrivateKey;
    @Getter
    private static String alipayPublicKey;

    @Getter
    private static String notifyUrl;
    @Getter
    private static String returnUrl;

    @Getter
    private static String signType;
    @Getter
    private static String charset;
    @Getter
    private static String gatewayUrl;

    public void setAppId(String appId) {
        AliPayResource.appId = appId;
    }

    public void setMerchantPrivateKey(String merchantPrivateKey) {
        AliPayResource.merchantPrivateKey = merchantPrivateKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        AliPayResource.alipayPublicKey = alipayPublicKey;
    }

    public void setNotifyUrl(String notifyUrl) {
        AliPayResource.notifyUrl = notifyUrl;
    }

    public void setReturnUrl(String returnUrl) {
        AliPayResource.returnUrl = returnUrl;
    }

    public void setSignType(String signType) {
        AliPayResource.signType = signType;
    }

    public void setCharset(String charset) {
        AliPayResource.charset = charset;
    }

    public void setGatewayUrl(String gatewayUrl) {
        AliPayResource.gatewayUrl = gatewayUrl;
    }
}
