package com.snail.common.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.codec.binary.Base64;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Created by Administrator on 2016/5/14.
 */
public class AliyunUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(AliyunUtils.class);

    public static final String REGION_CN_BEIJING = "cn-beijing";
    // 当前 STS API 版本
    public static final String STS_API_VERSION = "2015-04-01";

    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    //private static String bucketName = "more-image";
    private static String accessKeyId = "xoy2pdpNnrxuPJT2";
    private static String accessKeySecret = "6JBLs2HbpGE24wsnqkI9AP949wBr0T";

    private static AssumeRoleResponse assumeRole(String accessKeyId, String accessKeySecret,
                                                 String roleArn, String roleSessionName, String policy,
                                                 ProtocolType protocolType) throws ClientException {
        try {
            // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
            IClientProfile profile = DefaultProfile.getProfile(REGION_CN_BEIJING,
                    accessKeyId, accessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);

            // 创建一个 AssumeRoleRequest 并设置请求参数
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setVersion(STS_API_VERSION);
            request.setMethod(MethodType.POST);
            request.setProtocol(protocolType);

            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy);

            // 发起请求，并得到response
            final AssumeRoleResponse response = client.getAcsResponse(request);

            return response;
        } catch (ClientException e) {
            throw e;
        }
    }

    public static Credentials doAssumeRole(HttpServletRequest request, HttpServletResponse response)
            throws ClientException {
        // 只有 RAM用户（子账号）才能调用 AssumeRole 接口
        // 阿里云主账号的AccessKeys不能用于发起AssumeRole请求
        // 请首先在RAM控制台创建一个RAM用户，并为这个用户创建AccessKeys


        // AssumeRole API 请求参数: RoleArn, RoleSessionName, Polciy, and DurationSeconds
        // RoleArn 需要在 RAM 控制台上获取
        String roleArn = "acs:ram::1815048524820708:role/aliyunosstokengeneratorrole";

        // RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
        // 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
        // 具体规则请参考API文档中的格式要求
        String roleSessionName = "external-username";

        // 如何定制你的policy?
        String policy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:*\" \n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:*\"\n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        // 此处必须为 HTTPS
        ProtocolType protocolType = ProtocolType.HTTPS;


        final AssumeRoleResponse stsResponse = assumeRole(accessKeyId, accessKeySecret,
                roleArn, roleSessionName, policy, protocolType);

        //logger.debug("Expiration: " + stsResponse.getCredentials().getExpiration());
        //logger.debug("Access Key Id: " + stsResponse.getCredentials().getAccessKeyId());
        //logger.debug("Access Key Secret: " + stsResponse.getCredentials().getAccessKeySecret());
        //logger.debug("Security Token: " + stsResponse.getCredentials().getSecurityToken());

        return  stsResponse.getCredentials();
    }

    public static AssumeRoleResponse getSTSToken(HttpServletRequest request, HttpServletResponse response)
            throws ClientException {
        // 只有 RAM用户（子账号）才能调用 AssumeRole 接口
        // 阿里云主账号的AccessKeys不能用于发起AssumeRole请求
        // 请首先在RAM控制台创建一个RAM用户，并为这个用户创建AccessKeys


        // AssumeRole API 请求参数: RoleArn, RoleSessionName, Polciy, and DurationSeconds
        // RoleArn 需要在 RAM 控制台上获取
        String roleArn = "acs:ram::1815048524820708:role/aliyunosstokengeneratorrole";

        // RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
        // 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
        // 具体规则请参考API文档中的格式要求
        String roleSessionName = "external-username";

        // 如何定制你的policy?
        String policy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:*\" \n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:*\"\n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        // 此处必须为 HTTPS
        ProtocolType protocolType = ProtocolType.HTTPS;


        final AssumeRoleResponse stsResponse = assumeRole(accessKeyId, accessKeySecret,
                roleArn, roleSessionName, policy, protocolType);

        //logger.debug("Expiration: " + stsResponse.getCredentials().getExpiration());
        //logger.debug("Access Key Id: " + stsResponse.getCredentials().getAccessKeyId());
        //logger.debug("Access Key Secret: " + stsResponse.getCredentials().getAccessKeySecret());
        //logger.debug("Security Token: " + stsResponse.getCredentials().getSecurityToken());

        return  stsResponse;
    }

    public static String postObject(Credentials credentials, InputStream in, String bucketName, String suffix)
            throws OSSException, ClientException {
        OSSClient client = null;
        String key = getObjectName(suffix);

        try {
            client = new OSSClient(endpoint, credentials.getAccessKeyId(),
                    credentials.getAccessKeySecret(), credentials.getSecurityToken());
            int capacity = client.getBucketStorageCapacity(bucketName).getStorageCapacity();

            LOGGER.info("#### The " + bucketName + "'s capacity: " + capacity);

            PutObjectResult result = client.putObject(bucketName, key, in);
        } finally {
            if(client != null) {
                client.shutdown();
            }
        }

        String urlStr = endpoint.replace("http://", "http://" + bucketName+ ".");

        return urlStr +"/"+ key ;
    }

    public static void postObject(Credentials credentials,
                                  String key, String localFilePath, String bucketName) throws Exception {
        // 提交表单的URL为bucket域名
        String urlStr = endpoint.replace("http://", "http://" + bucketName+ ".");
        // 表单域
        Map<String, String> textMap = new LinkedHashMap<String, String>();

        // key
        textMap.put("key", key);
        // Content-Disposition
        textMap.put("Content-Disposition", "attachment;filename="
                + localFilePath);
        // OSSAccessKeyId
        textMap.put("OSSAccessKeyId", accessKeyId);
        // policy
        String policy = "{\"expiration\": \"2130-01-01T12:00:00.000Z\",\"conditions\": [[\"content-length-range\", 0, 104857600]]}";
        String encodePolicy = new String(Base64.encodeBase64(policy.getBytes()));
        textMap.put("policy", encodePolicy);
        // Signature

        String signaturecom = com.aliyun.oss.common.auth.ServiceSignature
                .create().computeSignature(accessKeySecret, encodePolicy);
        textMap.put("Signature", signaturecom);

        Map<String, String> fileMap = new HashMap<String, String>();
        fileMap.put("file", localFilePath);

        String ret = formUpload(urlStr, textMap, fileMap);

    }

    public static void deleteObject(Credentials credentials, String key, String bucketName)
            throws OSSException, ClientException {
        OSSClient client = null;

        try {
            client = new OSSClient(endpoint, credentials.getAccessKeyId(),
                    credentials.getAccessKeySecret(), credentials.getSecurityToken());

            client.deleteObject(bucketName, key);
        } finally {
            if(client != null) {
                client.shutdown();
            }
        }
    }

    private static String formUpload(String urlStr, Map<String, String> textMap,
                                     Map<String, String> fileMap) throws Exception {
        String res = "";
        HttpURLConnection conn = null;
        String boundary = "9431149156168";

        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            OutputStream out = new DataOutputStream(conn.getOutputStream());

            // text
            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator();
                int i = 0;

                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = entry.getKey();
                    String inputValue = entry.getValue();

                    if (inputValue == null) {
                        continue;
                    }

                    if (i == 0) {
                        strBuf.append("--").append(boundary).append("\r\n");
                        strBuf.append("Content-Disposition: form-data; name=\""
                                + inputName + "\"\r\n\r\n");
                        strBuf.append(inputValue);
                    } else {
                        strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
                        strBuf.append("Content-Disposition: form-data; name=\""
                                + inputName + "\"\r\n\r\n");
                        strBuf.append(inputValue);
                    }

                    i++;
                }
                out.write(strBuf.toString().getBytes());
            }

            // file
            if (fileMap != null) {
                Iterator<Map.Entry<String, String>> iter = fileMap.entrySet().iterator();

                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();

                    if (inputValue == null) {
                        continue;
                    }

                    File file = new File(inputValue);
                    String filename = file.getName();
                    String contentType = new MimetypesFileTypeMap().getContentType(file);
                    if (contentType == null || contentType.equals("")) {
                        contentType = "application/octet-stream";
                    }

                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(boundary)
                            .append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\""
                            + inputName + "\"; filename=\"" + filename
                            + "\"\r\n");
                    strBuf.append("Content-Type: " + contentType + "\r\n\r\n");

                    out.write(strBuf.toString().getBytes());

                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }

                StringBuffer strBuf = new StringBuffer();
                out.write(strBuf.toString().getBytes());
            }

            byte[] endData = ("\r\n--" + boundary + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (Exception e) {
            System.err.println("Send post request exception: " + e);
            throw e;
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }

        return res;
    }

    private static String getObjectName(String suffix) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        int month=cal.get(Calendar.MONTH)+1;//获取月份
        int day=cal.get(Calendar.DATE);//获取日

        String strMonth;
        if (month < 10){
            strMonth = "0" + month;
        } else {
            strMonth = "" + month;
        }

        String strDay;
        if (day < 10){
            strDay = "0" + day;
        } else {
            strDay = "" + day;
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        StringBuilder builder = new StringBuilder();
        builder.append(year)
                .append("/")
                .append(strMonth)
                .append("/")
                .append(strDay)
                .append("/")
                .append(uuid)
                .append(suffix);

        return builder.toString();
    }


    public static void main(String[] argv) {
    }
}
