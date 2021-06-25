package com.actor.testapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.actor.cpptest.ConstUtils;
import com.actor.myandroidframework.application.ActorApplication;
import com.actor.myandroidframework.utils.LogUtils;
import com.actor.testapplication.utils.AssetsUtils12;
import com.actor.testapplication.utils.Global;
import com.actor.testapplication.utils.GreenDaoUtils12;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okio.Buffer;

/**
 * Description: 类的描述
 * Date       : 2019-9-4 on 09:14
 *
 * @version 1.0
 */
public class MyApplication extends ActorApplication {

    //使用字符串替代服务器.cer证书
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
        ConstUtils.jniInit(this, appDebug);
        //数据库
        boolean success = AssetsUtils12.copyFile2DatabaseDir(true, Global.DBNAME);
        if (success) {
            GreenDaoUtils12.init(this, appDebug, Global.DBNAME);
        }
    }

    @Nullable
    @Override
    protected OkHttpClient.Builder configOkHttpClientBuilder(OkHttpClient.Builder builder) {
        //持久化Cookie & Session框架:PersistentCookieJar
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));
        /**
         * InputStream[] certificates:  证书,可多个证书(公钥)
         * InputStream bksFile:         .bks文件,本地证书(双向认证时本地私钥,银行,金融)
         * String password:             本地证书密码
         * 都为null:                 设置可访问所有的https网站
         * 证书的InputStream不为null: 设置具体的证书
         * 都不为null:               双向认证
         */
        InputStream certificate = new Buffer().writeUtf8(CER_SERVER).inputStream();//okio.Buffer
        HttpsUtils.SSLParams sslParams = null;
        //@Deprecated AssetsUtils 下一版本更新
        try (InputStream bksFile = AssetsUtils12.open("zhy_client.bks")) {//使用zhy_client.jks要报错?
            InputStream[] certificates = {certificate};
            sslParams = HttpsUtils.getSslSocketFactory(certificates, bksFile, "123456");
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                //对服务端返回的一些信息进行相关校验,用于客户端判断所连接的服务端是否可信,默认返回true
                .hostnameVerifier(new HostnameVerifier() {//很多子类
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        try {
                            //获取证书链中的所有证书
                            Certificate[] peerCertificates = session.getPeerCertificates();
                            for (Certificate c : peerCertificates) {//打印所有证书内容
                                LogUtils.error("verify: "+c.toString(), true);
                            }
                        } catch (SSLPeerUnverifiedException e) {
                            e.printStackTrace();
                        }
//                        OkHostnameVerifier.INSTANCE.verifyIpAddress(hostname, session);
//                        return Util.verifyAsIpAddress(hostname) ?
//                                Util.verifyIpAddress(hostname, session)
//                                : Util.verifyHostname(hostname, session);
                        return true;
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
//                .sslSocketFactory(null)//过时
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)//设置具体的证书
//                .writeTimeout(10_000L, TimeUnit.MILLISECONDS)//写超时,默认10秒
                ;
    }

    @NonNull
    @Override
    protected String getBaseUrl(boolean isDebugMode) {
        return Global.BASE_URL;
    }

    @Override
    protected void onUncaughtException(Throwable e) {
//        System.exit(-1);//退出
    }
}
