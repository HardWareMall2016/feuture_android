package com.zhan.framework.network;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 作者：伍岳 on 2016/4/22 14:20
 * 邮箱：wuyue8512@163.com
 //
 //         .............................................
 //                  美女坐镇                  BUG辟易
 //         .............................................
 //
 //                       .::::.
 //                     .::::::::.
 //                    :::::::::::
 //                 ..:::::::::::'
 //              '::::::::::::'
 //                .::::::::::
 //           '::::::::::::::..
 //                ..::::::::::::.
 //              ``::::::::::::::::
 //               ::::``:::::::::'        .:::.
 //              ::::'   ':::::'       .::::::::.
 //            .::::'      ::::     .:::::::'::::.
 //           .:::'       :::::  .:::::::::' ':::::.
 //          .::'        :::::.:::::::::'      ':::::.
 //         .::'         ::::::::::::::'         ``::::.
 //     ...:::           ::::::::::::'              ``::.
 //    ```` ':.          ':::::::::'                  ::::..
 //                       '.:::::'                    ':'````..
 //
 */
public class BaseCachePolicy {
    public final static String VISITOR="visitor";//访客模式
    /***
     * 返回缓存数据对应的所有者
     *
     * @return
     */
    public String getCacheOwner() {
        return VISITOR;
    }

    /***
     * 生成缓存的key值
     *
     * @param apiUrl
     * @param requestParams
     * @return
     */
    public String generateCacheKey(String apiUrl, Object requestParams) throws Exception {
        //这里需要用Md5加密
        return md5(apiUrl);
    }

    /***
     * 默认20分钟过期
     * @return
     */
    public long getExpiredSeconds(){
        return -1;
    }


    protected final String md5(String string) {
        byte[] hash = null;
        try {
            hash = string.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh,UTF-8 should be supported?", e);
        }
        return computeMD5(hash);
    }

    protected final String computeMD5(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input, 0, input.length);
            byte[] md5bytes = md.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < md5bytes.length; i++) {
                String hex = Integer.toHexString(0xff & md5bytes[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
