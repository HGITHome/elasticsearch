package com.elastic.search.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

/**
 * @author
 * @Title:
 * @Package
 * @Description:
 * @Company
 * @date 2020/11/9 15:14
 */
@Component
public class HttpUtils {


    private PoolingHttpClientConnectionManager cm;

    public HttpUtils() {
        this.cm = new PoolingHttpClientConnectionManager();

        //    设置最大连接数
        cm.setMaxTotal(100);
        //    设置每个主机的并发数
        cm.setDefaultMaxPerRoute(10);
    }

    /**
     * 根据请求地址下载页面数据
     * @param url
     * @return 页面数据
     */
    public String doGetHtml(String url) {

        // 获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        // 声明httpGet请求对象
        HttpGet httpGet = new HttpGet(url);
        // 设置请求参数RequestConfig
        httpGet.setConfig(this.getConfig());

        // 浏览器表示
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36");
        // 传输的类型
//        httpGet.addHeader("Cookie","Cookie地址");  //Cookie地址是你搜索过后，开发者工具里面的request Header地址，这里太长了省略不写

        //	上述两行关于浏览的代码，是表示声明你是正常的方式访问该网页(可以理解为登录后正常访问)

        CloseableHttpResponse response = null;
        try {
            //  使用HttpClient发起请求，获取响应
            response = httpClient.execute(httpGet);
            //  解析响应，返回结果
            if (response.getStatusLine().getStatusCode()==200){
                //  判断响应Entity是否不为空，如果不为空就可以使用EntityUtils
                if (response.getEntity() !=null){
                    String content = EntityUtils.toString(response.getEntity(), "utf8");
                    return content;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    // 关闭连接
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 下载图片
     * @param url
     * @return  图片名称
     */
    public String doGetImage(String url){
        //  获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();
        //  设置hTTPGet请求对象，设置url地址
        HttpGet httpGet = new HttpGet(url);

        //  设置请求信息
        httpGet.setConfig(this.getConfig());

        CloseableHttpResponse response = null;

        try {
            //  使用HttpClient发起请求，获取响应
            response = httpClient.execute(httpGet);
            //  解析响应，返回结果
            if (response.getStatusLine().getStatusCode()==200){
                //  判断响应Entity是否不为空，如果不为空就可以使用EntityUtils
                if (response.getEntity()!=null){
                    //  下载图片
                    //  获取图片的后缀
                    String extName = url.substring(url.lastIndexOf("."));
                    //  创建图片名，并重命名图片
                    String picName = UUID.randomUUID().toString() + extName;
                    //  下载图片
                    //  声明 OutPutStream
//                    HttpEntity entity = response.getEntity();
//                    toPng(EntityUtils.toByteArray(entity),picName);
                    OutputStream out = new FileOutputStream(new File("D:\\images\\" + picName));
                    response.getEntity().writeTo(out);
                    //  返回图片名称
                    return picName;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //  关闭response
            if(response != null){
                try{
                    response.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        //  如果下载失败，返回空字符串
        return "";
    }

    // 保存照片
    private void toPng(byte[] bytes,String picName) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            BufferedImage bi1 = ImageIO.read(bais);
            File w2 = new File("D:\\images\\" + picName);//可以是jpg,png,gif格式
            ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取请求参数对象
    private RequestConfig getConfig() {
        RequestConfig config = RequestConfig.custom()
                // 设置创建连接的超时时间
                .setConnectTimeout(1000)
                // 设置获取连接的超时时间
                .setConnectionRequestTimeout(500)
                // 设置连接的超时时间
                .setSocketTimeout(10000)
                .build();

        return config;
    }
}
