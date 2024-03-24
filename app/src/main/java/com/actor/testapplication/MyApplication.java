package com.actor.testapplication;

import com.actor.myandroidframework.application.ActorApplication;
import com.actor.myandroidframework.utils.AssetsUtils;
import com.actor.myandroidframework.utils.LogUtils;
import com.actor.myandroidframework.utils.database.GreenDaoUtils;
import com.actor.myandroidframework.utils.okhttputils.OkHttpConfigUtils;
import com.actor.testapplication.utils.ConfigUtils2;
import com.actor.testapplication.utils.Global;
import com.actor.testapplication.utils.okhttputils.MyOkHttpUtils;
import com.actor.testapplication.utils.retrofit.RetrofitNetwork;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okio.Buffer;

/**
 * Description: 类的描述
 *  2.1.1.HTTPS未校验服务器证书漏洞
 *  测评目的: 检测App在使用HTTPS协议传输数据时是否对服务器证书进行校验。
 *  危险等级: 中
 *
 *  危害:
 *   使用HTTPS协议时，客户端必须对服务器身份进行完整性校验，以验证服务器是真实合法的目标服务器。
 *   如果没有校验，客户端可能与仿冒的服务器建立通信链接，即“中间人攻击”。
 *   Android中默认的HTTPS证书验证机制不接受不可信的连接，因而是安全的，但Android允许开发者重定义证书验证方法：
 *    1）使用X509TrustManager类检查证书是否合法并且是否未过期；
 *    2）使用HostnameVerifier类检查证书中的主机名与使用该证书的服务器的主机名是否一致。
 *       重写的X509TrustManager，其中的checkServerTrusted()方法不对验证失败做任何处理，
 *       即不对证书进行正确校验结果，是导致“中间人攻击”的主要原因之一。当发生中间人攻击时，
 *       仿冒的中间人可以冒充服务器与手机客户端进行交互，同时冒充手机客户端与服务器进行交互，
 *       在充当中间人转发信息的时候，窃取手机号，账号，密码等敏感信息，甚至可能对通信内容进行篡改。
 *
 *  测评结果描述: 该App应用在使用HTTPS进行数据传输时未校验服务器证书。
 *
 *  解决方案: 开发者自查：开发者在使用https时应对服务器证书进行合法性校验并且当校验失败时正确处理。
 *  以下为修复代码示例：{@link HttpsUtils.UnSafeTrustManager#checkServerTrusted(X509Certificate[], String)}
 *  private class DemoTrustManager implements X509TrustManager {
 *      @Override
 *      public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
 *      }
 *      @Override
 *      public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
 *      for (X509Certificate cert : chain) {
 *          // Make sure that it hasn't expired.
 *          cert.checkValidity();
 *          // Verify the certificate's public key chain.
 *          try {
 *              cert.verify(((X509Certificate) ca).getPublicKey());
 *              } catch (NoSuchAlgorithmException e) {
 *              } catch (InvalidKeyException e) {
 *              } catch (NoSuchProviderException e) {
 *              } catch (SignatureException e) {
 *              }
 *          }
 *      }
 *      @Override
 *      public X509Certificate[] getAcceptedIssuers() {
 *          return new X509Certificate[0];
 *      }
 *  }
 *
 *  以上来自评测结果文档.
 *
 *  还可参考: https://github.com/jeasonlzy/okhttp-OkGo/wiki/Init#5-https%E9%85%8D%E7%BD%AE%E4%BB%A5%E4%B8%8B%E5%87%A0%E7%A7%8D%E6%96%B9%E6%A1%88%E6%A0%B9%E6%8D%AE%E9%9C%80%E8%A6%81%E8%87%AA%E5%B7%B1%E8%AE%BE%E7%BD%AE
 *           https://www.jianshu.com/p/54dd21c50f21
 *
 * Date       : 2019-9-4 on 09:14
 *
 * @version 1.0
 */
public class MyApplication extends ActorApplication {

    //使用字符串替代服务器.cer证书(公钥)
    private static final String CER_SERVER = "-----BEGIN CERTIFICATE-----\n" +
            "MIIDWzCCAkOgAwIBAgIEU7j1ZTANBgkqhkiG9w0BAQsFADBdMQswCQYDVQQGEwJjbjEPMA0GA1UE\n" +
            "CBMGc2hhbnhpMQ0wCwYDVQQHEwR4aWFuMQ4wDAYDVQQKEwV6aGFuZzEOMAwGA1UECxMFemhhbmcx\n" +
            "DjAMBgNVBAMTBXpoYW5nMCAXDTE4MTEwMTA2NTAxOVoYDzMwMTcwMzA0MDY1MDE5WjBdMQswCQYD\n" +
            "VQQGEwJjbjEPMA0GA1UECBMGc2hhbnhpMQ0wCwYDVQQHEwR4aWFuMQ4wDAYDVQQKEwV6aGFuZzEO\n" +
            "MAwGA1UECxMFemhhbmcxDjAMBgNVBAMTBXpoYW5nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIB\n" +
            "CgKCAQEAnXHDbJBAK9pFR0cuOriWLHhM1DjnAc5sC8rkgyOsbSncDJJ0ZXwofUnBZf2iWaMXjFGW\n" +
            "zg1BjG/70uScDUUXzQkD9e6EVwDPFsOAm8IcJh53lrQgTZa774m3Y6BKLCFyvEvbqqXMqL6hDa7E\n" +
            "JoL0khz2rkq6rfVUHygNIIyJmV63IR95qbDZ7KLNLWk49oZGZy0Qo8bfJaGIDjF6FkyhDm87enSa\n" +
            "xPI9rOP3YkdxYRHwSyq73mTKZqt4RqVFK+FE46dGQQ6KRPELk0XquoI7+iO3U25tEOGDSPawJ7+9\n" +
            "DLQcPJKRX9LxlTpNbzYaEHJ0l6elGRFC7YrJLh3HwhvXZwIDAQABoyEwHzAdBgNVHQ4EFgQU11aV\n" +
            "b5kPGXETeH8HT/vBjqpLGRgwDQYJKoZIhvcNAQELBQADggEBAB++qyekOgLZQFplNZgv9+9nfR8/\n" +
            "JSwL6W0BWgEE1efTxbR1hnqCClj0alTDsz9pr3RpEoKIHZm3/RKlZTOlPWGXbMTfAaY8AkUHCk+C\n" +
            "wKu+1N9nXVqDMSpMEdHpn1noPay7WDd47lH0i1nZGNFMiY6Wek72z4J/1YlxiGbRPZy1wduW62+S\n" +
            "r62wnAw6JlFtH1hczHEYVkQSPH6+5fWdHQrZVOPKdWn24Wp/fGujRa//p0CKNFh708gKhYoXp2f0\n" +
            "5pDfpw+Y5FBbzhCLAd4Ab8UKydr5BvzgPjk+MlKge7M/QKn3/HNCa1D6mVka8ht4MGKqfcgljSUO\n" +
            "9e1txbZlG4o=\n" +
            "-----END CERTIFICATE-----";

    @Override
    public void onCreate() {
        super.onCreate();
        boolean appDebug = isAppDebug();
        //数据库
        boolean success = AssetsUtils.copyFile2InternalDbsDir(true, Global.DBNAME);
        if (success) {
            //                                               这是填写你的密码(请自己重新创建数据库,自己设置密码自己玩!)
            GreenDaoUtils.init(this, appDebug, Global.DBNAME, ConfigUtils2.DB_PASSWORD);
        }


        //配置张鸿洋的OkHttpUtils
        MyOkHttpUtils.setBaseUrl(Global.BASE_URL);
        OkHttpClient.Builder builder = MyOkHttpUtils.initOkHttp(isAppDebug());
        //然后可以在 builder 中自定义设置, 添加拦截器等
        builder = configOkHttpClientBuilder(builder);
        //OkHttp配置完后, 再增加1个日志拦截器, 用于打印非常标准的请求日志
        OkHttpClient okHttpClient = OkHttpConfigUtils.addLogInterceptor(builder, isAppDebug());
        //最后将okHttpClient设置进去
        MyOkHttpUtils.setOkHttpClient(okHttpClient);

        //配置Retrofit
        RetrofitNetwork.setBaseUrl(Global.BASE_URL);
        RetrofitNetwork.setOkHttpClient(okHttpClient);
    }

    private OkHttpClient.Builder configOkHttpClientBuilder(OkHttpClient.Builder builder) {
        //持久化Cookie & Session框架:PersistentCookieJar
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));

        /**
         * Android Https相关完全解析 当OkHttp遇到Https_Hongyang-CSDN博客.html
         * https://blog.csdn.net/lmj623565791/article/details/48129405
         * srca.cer      : 服务器公钥证书
         * keytool -printcert -rfc -file srca.cer: 也可以将公钥证书转成字符串CER_SERVER
         * zhy_server.jks: 服务器下生成自签名证书部署服务
         * zhy_server.cer: 利用zhy_server.jks生成包含公钥的证书
         *
         * zhy_client.jks: "双向认证"时, 客户端的私钥(会报错, 客户端默认使用.bks格式↓)
         * zhy_client.bks
         * zhy_client.cer: "双向认证"时, 服务器的公钥(会报错, 服务端默认使用.jks格式↓)
         * zhy_client_for_sever.jks
         *
         */
        InputStream certificate = new Buffer().writeUtf8(CER_SERVER).inputStream();//okio.Buffer
        /**
         * 方式1: 字符串方式传入公钥, 生成流↑
         */
        HttpsUtils.SSLParams sslParams = null;
        try (InputStream bksFile = AssetsUtils.open("zhy_client.bks")) {
            //证书,可多个证书(服务端的公钥)
            InputStream[] certificates = {certificate};
            /**
             * @param InputStream[] certificates 注意1: 如果单向认证, 只需要这1个参数
             *                                   注意2: 如果"双向认证", 需要下方2个参数!!!
             *
             * @param InputStream bksFile .bks文件,"双向认证"本地证书(服务器还有个"公钥",银行,金融)
             * @param String password     本地证书密码
             *
             *
             * 说明:
             * 都为null:                 设置可访问所有的https网站
             * 证书的InputStream不为null: 设置具体的证书
             * 都不为null:               双向认证
             */
            sslParams = HttpsUtils.getSslSocketFactory(certificates, bksFile, "123456");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * 方式2: 把.cer证书放在assets目录下读取, 生成流
         */
//        try (InputStream certificate0 = AssetsUtils.open("service.cer");
//             //可多个.cer证书...
//             InputStream certificate2 = AssetsUtils.open("service2.cer");
//
//             InputStream bksFile = AssetsUtils.open("zhy_client.bks")) {
//            InputStream[] certificates = {certificate0, certificate2/*; 可多个证书...*/};
//            sslParams = HttpsUtils.getSslSocketFactory(certificates, bksFile, "123456");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        return builder
                //Log
//                .addInterceptor(new LoggerInterceptor(getPackageName() + ",Interceptor:", isAppDebug()))//tag, showResponse
//                .addNetworkInterceptor(new LoggerInterceptor(getPackageName() + ",NetworkInterceptor:", isDebugMode))
//                .authenticator(new Authenticator() {//认证x
//                    @Override
//                    public Request authenticate(Route route, Response response) throws IOException {
//                        return null;
//                    }
//                })
                .cache(new Cache(getFilesDir(), 1024*1024*10))//10Mb

//                .certificatePinner(CertificatePinner.DEFAULT)//x
//                .connectionPool(new ConnectionPool())
//                .connectionSpecs(new ArrayList<ConnectionSpec>())
//                .connectTimeout(10_000L, TimeUnit.MILLISECONDS)//连接超时,默认10秒

//                new CookieJarImpl(new MemoryCookieStore());//cookie信息存在内存中
//                new CookieJarImpl(new PersistentCookieStore(this)); //持久化cookie
//                new CookieJarImpl(new SerializableHttpCookie()); //持久化cookie
                .cookieJar(cookieJar)//Cookie & Session

//                .dispatcher(new Dispatcher())
//                .dns(Dns.SYSTEM)
//                .followRedirects(false)
//                .followSslRedirects(false)
                /**
                 * 域名校验
                 */
                .hostnameVerifier(new HostnameVerifier() {//很多子类
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        /**
                         * 此类是用于主机名验证的基接口。 在握手期间，如果 URL 的主机名和服务器的标识主机名不匹配，
                         * 则验证机制可以回调此接口的实现程序来确定是否应该允许此连接。策略可以是基于证书的或依赖于其他验证方案。
                         * 当验证 URL 主机名使用的默认规则失败时使用这些回调。如果主机名是可接受的，则返回 true
                         * @see okhttp3.internal.tls.OkHostnameVerifier
                         */
                        try {
                            //获取证书链中的所有证书
                            Certificate[] peerCertificates = session.getPeerCertificates();
                            for (Certificate c : peerCertificates) {//打印所有证书内容
                                LogUtils.errorFormat("verify = %s\n\n", c.toString());
                                String type = c.getType();
                                String str = null;
                                try {
                                    byte[] encoded = c.getEncoded();
                                    str = new String(encoded, StandardCharsets.UTF_8);
                                    System.out.println(str);
                                } catch (CertificateEncodingException e) {
                                    e.printStackTrace();
                                }
                                PublicKey publicKey = c.getPublicKey();
                                String format = publicKey.getFormat();
                                byte[] encoded1 = publicKey.getEncoded();
                                String str1 = null;
                                str1 = new String(encoded1, StandardCharsets.UTF_8);
                                System.out.println(str1);
                                String algorithm = publicKey.getAlgorithm();
                                System.out.println(111);

                                //如果证书和服务器证书不一致 TODO
//                                if (!CER_SERVER.equals(c.getPublicKey()...)) {
//                                    return false;
//                                }
                            }
                        } catch (SSLPeerUnverifiedException e) {
                            e.printStackTrace();
                        }
//                        OkHostnameVerifier.INSTANCE.verifyIpAddress(hostname, session);
//                        return Util.verifyAsIpAddress(hostname) ?
//                                Util.verifyIpAddress(hostname, session)
//                                : Util.verifyHostname(hostname, session);

                        boolean verify = HttpsURLConnection.getDefaultHostnameVerifier().verify(hostname, session);
                        return verify;
                    }
                })
//                .interceptors()//List<Interceptor>
//                .networkInterceptors()//List<Interceptor>
//                .protocols(new ArrayList<Protocol>())
//                .proxy(null)
//                .proxyAuthenticator(null)
//                .proxySelector(null)
//                .readTimeout(10_000L, TimeUnit.MILLISECONDS)//读超时,默认10秒
                .retryOnConnectionFailure(true)//默认true
//                .socketFactory(null)

                /**
                 * 如果使用的是HTTPS SSL加密协议，
                 * 对链路中的SSL加密Cert证书做合法性的校验checkClientTrusted/checkServerTrusted等函数，
                 * 以防止传输过程中的数据被解密，从而协议被逆向破解。
                 *
                 * https请求,对服务端返回的一些信息进行相关校验,用于客户端判断所连接的服务端是否可信,默认返回true
                 * 不一定要要设置 sslSocketFactory, 可直接进行验证证书
                 */
//                .sslSocketFactory(null)//过时
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)//设置具体的证书
//                .writeTimeout(10_000L, TimeUnit.MILLISECONDS)//写超时,默认10秒
                ;
    }

    @Override
    protected void onUncaughtException(Throwable e) {
        super.onUncaughtException(e);
    }
}
