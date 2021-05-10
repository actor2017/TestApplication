package com.actor.testapplication.activity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Pair;
import android.view.View;

import com.actor.myandroidframework.widget.ItemSpinnerLayout;
import com.actor.myandroidframework.widget.ItemTextInputLayout;
import com.actor.testapplication.R;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.EncryptUtils;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: 加密解密
 * https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/EncryptUtilsTest.java
 * @see com.actor.testapplication.R.string#encode_instruction 加密解密说明
 *
 * date       : 2020/9/12 o 14:43
 * @version 1.0
 */
public class EncryptActivity extends BaseActivity {

    @BindView(R.id.isl_encode_type)//加密类型
    ItemSpinnerLayout islEncodeType;
    @BindView(R.id.itil_encode)//要加密的字符串
    ItemTextInputLayout itilEncode;
    @BindView(R.id.itil_encode_result)//加密结果
    ItemTextInputLayout itilEncodeResult;
    @BindView(R.id.itil_decode_result)//解密结果
    ItemTextInputLayout itilDecodeResult;

    //密码加盐值
    private static final String PWD_SALT = "MUIE*/82-DAlc3b1`";

    //DES
    private static final String keyDES       = "6801020304050607";
    private static final byte[] bytesKeyDES  = ConvertUtils.hexString2Bytes(keyDES);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);
        ButterKnife.bind(this);

        setTitle("加密解密");
        itilEncode.setText("这是密码123456");
    }

    @OnClick({R.id.btn_encode, R.id.btn_decode})
    public void onViewClicked(View view) {
        int position = islEncodeType.getSelectedItemPosition();
        switch (view.getId()) {
            case R.id.btn_encode://加密
                switch (position) {
                    case 0://DES(双向对称)
                        encodeDES();
                        break;
                    case 1://3DES(DES的加强版)
                        encode3DES();
                        break;
                    case 2://AES
                        encodeAES();
                        break;
                    case 3://RSA(双向非对称)
                        encodeRSA();
                        break;
                    default:
                        break;
                }
                break;
            case R.id.btn_decode://解密
                switch (position) {
                    case 0://DES(双向对称)
                        decodeDES();
                        break;
                    case 1://3DES(DES的加强版)
                        decode3DES();
                        break;
                    case 2://AES
                        decodeAES();
                        break;
                    case 3://RSA(双向非对称)
                        decodeRSA();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    //DES(双向对称加密)
    private void encodeDES() {
        if (isNoEmpty(itilEncode)) {
            String input = PWD_SALT + getText(itilEncode);//盐 + 输入字符串
            String hexString = ConvertUtils.bytes2HexString(input.getBytes());//16进制
            byte[] bytes = ConvertUtils.hexString2Bytes(hexString);
            /**
             * 参1: 16进制字符串bytes
             * 参2: 16进制key
             * 参3: 转换的名称，例如DES/CBC/PKCS5Padding  /NoPadding(不自动填充, 有可能报错)
             * 参4: 该缓冲液与IV.的内容, 复制缓冲区以防止后续修改。
             * 结果:16进制String
             */
            String result = EncryptUtils.encryptDES2HexString(bytes, bytesKeyDES, "DES/ECB/PKCS5Padding", null);
            itilEncodeResult.setText(result);
        }
    }
    //DES(双向对称解密)
    private void decodeDES() {
        if (isNoEmpty(itilEncodeResult)) {
            /**
             * 参1: 16进制String
             * 参2: 16进制key
             * 结果:bytes
             */
            byte[] bytes = EncryptUtils.decryptHexStringDES(getText(itilEncodeResult), bytesKeyDES, "DES/ECB/PKCS5Padding", null);
            String hexString = ConvertUtils.bytes2HexString(bytes);//16进制String
            byte[] bytes1 = ConvertUtils.hexString2Bytes(hexString);
            String result = ConvertUtils.bytes2String(bytes1);
            itilDecodeResult.setText(result);
        }
    }



    private static final String key3DES       = "111111111111111111111111111111111111111111111111";
    private static final byte[] bytesKeyDES3  = ConvertUtils.hexString2Bytes(key3DES);
    //3DES(双向对称加密)
    private void encode3DES() {
        if (isNoEmpty(itilEncode)) {
            String input = PWD_SALT + getText(itilEncode);//盐 + 输入字符串
            String hexString = ConvertUtils.bytes2HexString(input.getBytes());//16进制
            byte[] bytes = ConvertUtils.hexString2Bytes(hexString);
            /**
             * 参1: 16进制字符串bytes
             * 参2: 16进制key, key size must be 128 or 192 bits
             * 参3: 转换的名称
             * 参4: 该缓冲液与IV.的内容, 复制缓冲区以防止后续修改。
             * 结果:16进制String
             */
            String result = EncryptUtils.encrypt3DES2HexString(bytes, bytesKeyDES3, "DESede/ECB/PKCS5Padding", null);
            itilEncodeResult.setText(result);
        }
    }
    //3DES(双向对称解密)
    private void decode3DES() {
        if (isNoEmpty(itilEncodeResult)) {
            /**
             * 参1: 16进制String
             * 参2: 16进制key
             * 结果:bytes
             */
            byte[] bytes = EncryptUtils.decryptHexString3DES(getText(itilEncodeResult), bytesKeyDES3, "DESede/ECB/PKCS5Padding", null);
            String hexString = ConvertUtils.bytes2HexString(bytes);//16进制String
            byte[] bytes1 = ConvertUtils.hexString2Bytes(hexString);
            String result = ConvertUtils.bytes2String(bytes1);
            itilDecodeResult.setText(result);
        }
    }



    private static final String keyAES       = "11111111111111111111111111111111";
    private static final byte[] bytesKeyAES  = ConvertUtils.hexString2Bytes(keyAES);
    //AES(双向对称加密)
    private void encodeAES() {
        if (isNoEmpty(itilEncode)) {
            String input = PWD_SALT + getText(itilEncode);//盐 + 输入字符串
            String hexString = ConvertUtils.bytes2HexString(input.getBytes());//16进制
            byte[] bytes = ConvertUtils.hexString2Bytes(hexString);
            /**
             * 参1: 16进制字符串bytes
             * 参2: 16进制key
             * 参3: 转换的名称，例如DES/CBC/PKCS5Padding  /NoPadding(不自动填充, 有可能报错)
             * 参4: 该缓冲液与IV.的内容, 复制缓冲区以防止后续修改。
             * 结果:16进制String
             */
            String result = EncryptUtils.encryptAES2HexString(bytes, bytesKeyAES, "AES/ECB/PKCS5Padding", null);
            itilEncodeResult.setText(result);
        }
    }
    //AES(双向对称解密)
    private void decodeAES() {
        if (isNoEmpty(itilEncodeResult)) {
            /**
             * 参1: 16进制String
             * 参2: 16进制key
             * 结果:bytes
             */
            byte[] bytes = EncryptUtils.decryptHexStringAES(getText(itilEncodeResult), bytesKeyAES, "AES/ECB/PKCS5Padding", null);
            String hexString = ConvertUtils.bytes2HexString(bytes);//16进制String
            byte[] bytes1 = ConvertUtils.hexString2Bytes(hexString);
            String result = ConvertUtils.bytes2String(bytes1);
            itilDecodeResult.setText(result);
        }
    }



    //RSA(双向非对称加密)
    private void encodeRSA() {
        if (isNoEmpty(itilEncode)) {
            String input = PWD_SALT + getText(itilEncode);//盐 + 输入字符串
            String hexString = ConvertUtils.bytes2HexString(input.getBytes());//16进制
            byte[] bytes = ConvertUtils.hexString2Bytes(hexString);

            int keySize = 1024;
            Pair<String, String> publicPrivateKey = genKeyPair(keySize);//公钥/私钥

            String publicKey = publicPrivateKey.first;
            String privateKey = publicPrivateKey.second;
            byte[] publicKeyByte = Base64.decode(publicKey.getBytes(), Base64.NO_WRAP);
            /**
             * 参1: 16进制字符串bytes
             * 参2: 公钥
             * 参3: 转换的名称，例如DES/CBC/PKCS5Padding  /NoPadding(不自动填充, 有可能报错)
             * 参4: 该缓冲液与IV.的内容, 复制缓冲区以防止后续修改。
             * 结果:16进制String
             */
            String result = EncryptUtils.encryptRSA2HexString(bytes, publicKeyByte, keySize, "RSA/None/PKCS1Padding");
            itilEncodeResult.setText(result);
        }
    }
    //RSA(双向非对称解密)
    private void decodeRSA() {
        if (isNoEmpty(itilEncodeResult)) {
            int keySize = 1024;
            Pair<String, String> publicPrivateKey = genKeyPair(keySize);//公钥/私钥

            String publicKey = publicPrivateKey.first;
            String privateKey = publicPrivateKey.second;
            byte[] privateKeyByte = Base64.decode(privateKey.getBytes(), Base64.NO_WRAP);
            /**
             * 参1: 16进制String
             * 参2: 16进制key
             * 结果:bytes
             */
            byte[] bytes = EncryptUtils.decryptHexStringRSA(getText(itilEncodeResult), privateKeyByte, keySize, "RSA/None/PKCS1Padding");
            String hexString = ConvertUtils.bytes2HexString(bytes);//16进制String
            byte[] bytes1 = ConvertUtils.hexString2Bytes(hexString);
            String result = ConvertUtils.bytes2String(bytes1);
            itilDecodeResult.setText(result);
        }
    }



    private Pair<String, String> genKeyPair(int size) {

        if (size == 1024) {
            return Pair.create(
                    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCYHGvdORdwsK5i+s9rKaMPL1O5eDK2XwNHRUWaxmGB/cxLxeinJrrqdAN+mME7XtGN9bklnOR3MUBQLVnWIn/IU0pnIJY9DpPTVc7x+1zFb8UUq1N0BBo/NpUG5olxuQULuAAHZOg28pnP/Pcb5XVEvpNKL0HaWjN8pu/Dzf8gZwIDAQAB",
                    "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJgca905F3CwrmL6z2spow8vU7l4MrZfA0dFRZrGYYH9zEvF6Kcmuup0A36YwTte0Y31uSWc5HcxQFAtWdYif8hTSmcglj0Ok9NVzvH7XMVvxRSrU3QEGj82lQbmiXG5BQu4AAdk6Dbymc/89xvldUS+k0ovQdpaM3ym78PN/yBnAgMBAAECgYAFdX+pgNMGiFC53KZ1AhmIAfrPPTEUunQzqpjE5Tm6oJEkZwXiedFbeK5nbLQCnXSH07nBT9AjNvFH71i6BqLvT1l3/ezPq9pmRPriHfWQQ3/J3ASf1O9F9CkYbq/s/qqkXEFcl8PdYQV0xU/kS4jZPP+60Lv3sPkLg2DpkhM+AQJBANTl+/v6sBqqQSS0Anl5nE15Ck3XGBcq0nvATHfFkJYtG9rrXz3ZoRATLxF1iJYwGSAtirhev9W7qFayjci0ztcCQQC25/kkFbeMEWT6/kyV8wcPIog1mKy8RVB9+2l6C8AzbWBPZYtLlB7uaGSJeZBTEGfvRYzpFm5xO0JqwCfDddjxAkBmxtgM3wqg9MwaAeSn6/Nu2x4EUfBJTtzp7P19XJzeQsyNtM73ttYwQnKYhRr5FiMrC5FKTENj1QIBSJV17QNlAkAL5cUAAuWgl9UQuo/yxQ81fdKMYfUCfiPBPiRbSv5imf/Eyl8oOGdWrLW1d5HaxVttZgHHe60NcoRce0la3oSRAkAe8OqLsm9ryXNvBtZxSG+1JUvePVxpRSlJdZIAUKxN6XQE0S9aEe/IkNDBgVeiUEtop76R2NkkGtGTwzbzl0gm"
            );
        } else if (size == 2048) {
            return Pair.create(
                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjLLeJZIO7dfQKb6tHE+TlhvD1m3UdTefKvl4uNQboDXy2ztgPcksjLDXxsT+znxMBh4RpXxfVPgnrcSLewGVhTb3uXh9sWo6tvvshNaMKBTebaZePhE7grq+LHH3NILscVssK24rDSvIquZ4nUbDipF/Iscge4LwnypcCuun/3RCn4HYzXW+0YFFZC8Vq4zabIxtzzkvgZlAlvuD6tT76Uuo5kD8b36yYNALI+ZStOj283wlL8PgyyitRGaqCH+MjWYqDb5C0DN31kcoSU7ARTGWgNNAoexAdNujkBvVRFyR2cH9FpjJDu18Oa8v9uSjlRftVWPj0OQXE7vRUsrrawIDAQAB",
                    "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCMst4lkg7t19Apvq0cT5OWG8PWbdR1N58q+Xi41BugNfLbO2A9ySyMsNfGxP7OfEwGHhGlfF9U+CetxIt7AZWFNve5eH2xajq2++yE1owoFN5tpl4+ETuCur4scfc0guxxWywrbisNK8iq5nidRsOKkX8ixyB7gvCfKlwK66f/dEKfgdjNdb7RgUVkLxWrjNpsjG3POS+BmUCW+4Pq1PvpS6jmQPxvfrJg0Asj5lK06PbzfCUvw+DLKK1EZqoIf4yNZioNvkLQM3fWRyhJTsBFMZaA00Ch7EB026OQG9VEXJHZwf0WmMkO7Xw5ry/25KOVF+1VY+PQ5BcTu9FSyutrAgMBAAECggEAHJQ4i2kfnzA3GEOi5h1D3TnGjcfBYA3sRs5ltyVedyx+KAnngqVaZzmEmtto5ohY6OUysGqS8q91X9aMfm/T7zs7FnFjFqZ9Rq3lXRY3YezbQWqJuhHGBMfp2R1NGV1+qYfbcPbvx70dBZnK5id5kKv9JxNLhcsTFUGFcLJtbXXixY2CGiS/dIbFvFHGMbAz3+9l9HXaL4AS7KQXvnauwJW1a5vIAVFYZVBj0qY9Viy2vq6ShH+9pdxOSsWBt08WpxIhjkTr+ZkFck67la2Jn0SBlClB0FIygTqbAmsM3p1nqcR55jdx3hfs31rIfM1Rx5epMm48KYErb2ktowngAQKBgQDL9FEumMMagPy4+EjR1puFHNvADlAi8tIUNt1W5zKKnd+T6gYGn8nqiiy5pvwLLUp8JISmq50tMC3cgAPw+G4kIe5zoBO2EU9X6aPhMd/ScUlVdk0IzEMXa3kMAOjOInWvoevJ4cwWcBPH2aRuDg5wZdh3TpB9LQP4uQ0QHwmE3wKBgQCwmkL6rJDrNo1GNUsjw+WIsXkuS3PYJahbg/uhRdGSsX2BRIPQVCRJP7MkgaUMhZRilt1ROfQy4d2BPxTxvUiGJcKfpsW8xi39PrYWZC5TvEA839q39Uak+ISCsYtZaHk5dvzmE9nF5gv0ivjCr81N2/1KwXO8VmNofzWUqNd+9QKBgQCs39QICRgm2Ppd1qXyp1N/SuzBJ+CpHuUOmUqXpLRkZljiSVT+PGar1J8AZhfxaVxfSZzeoUxCxzm4UxIEKK9DFTfG7gKHKrj0LWfpM5siB0A/nlzBflHIAiLCF+s8/lx+mGMB5dBVnH5HwaTsXCHFB66pwgAa+hMJueDmr0gkRQKBgDKhd1Rwxvd4Y1ZejxVI43SmFOzt2t98JGFgXHLnFmdtFWNLJlNC3EhXx99Of+gwH9OIFxljeRxhXuTgFfwcXT+AceTdplExrBuvr/qJbDK7hNsu/oDBBCjlyu/BQQc4CZEtCOJZjJTNGF5avWjrh/urd1nITosPZV6fIdhl86pFAoGAfOwK0Wte6gO5glAHP9RNktDeyFJCfFH1KUFiAG7XUww6bRpL2fEAqBIcDVgsS565ihxDSbUjgQgg/Ckh2+iBrwf1K9ViO4XUuwWqRS26rn4Is/W5kbPtnC4HS5cQIH1aWi3xUMJcWxV4ZrwiMVdw91leYWC0IbXC/yrc/PBW+sE="
            );
        }

        SecureRandom secureRandom = new SecureRandom();

        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        keyPairGenerator.initialize(size, secureRandom);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        Key publicKey = keyPair.getPublic();

        Key privateKey = keyPair.getPrivate();

        byte[] publicKeyBytes = publicKey.getEncoded();
        byte[] privateKeyBytes = privateKey.getEncoded();

        String publicKeyBase64 = EncodeUtils.base64Encode2String(publicKeyBytes);
        String privateKeyBase64 = EncodeUtils.base64Encode2String(privateKeyBytes);

        return Pair.create(publicKeyBase64, privateKeyBase64);
    }
}