package com.elastic.search.task;

import com.elastic.search.dto.Item;
import com.elastic.search.utils.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @Title:
 * @Package
 * @Description:
 * @Company
 * @date 2020/11/9 15:22
 */

@Component
public class ItemTask {

    @Autowired
    private HttpUtils httpUtils;


    private static final ObjectMapper MAPPER = new ObjectMapper();

    //  当下载任务完成后，间隔多长时间进行下一次的任务
    @Scheduled(fixedDelay =1000*1000 )
    public void itemTask() throws Exception{

        //  声明需要解析的初始地址
        String url = "https://search.jd.com/Search?keyword=java&page=";

        //  遍历页面对手机的搜索进行遍历结果
        for (int i = 1; i < 10; i=i+2) {
            String html = this.httpUtils.doGetHtml(url+i);
            //  解析页面，获取商品数据并存储
            this.parse(html);
        }
        System.out.println("手机数据抓取完成！！！");
    }

    /**
     * 解析页面，获取商品数据并存储
     * @param html
     */
    private void parse(String html) throws Exception {
        //  解析HTML获取Document
        Document doc = Jsoup.parse(html);
        //  获取spu
        Elements spuEles = doc.select("div#J_goodsList > ul > li");
        List<Item> items = new ArrayList<>();
        //  遍历获取spu数据
        for (Element spuEle : spuEles) {
            //  获取spu
//            long spu = Long.parseLong(spuEle.attr("data-spu"));
            //  获取sku信息
            Elements skuEles = spuEles.select("li.gl-item");
            for (Element skuEle : skuEles) {
                //  获取sku
                long sku = Long.parseLong(skuEle.select("[data-sku]").attr("data-sku"));
                //  根据sku查询商品数据
                Item item = new Item();
                item.setSku(sku);
                //  设置商品的spu
//                item.setSpu(spu);

                //  获取商品的详情信息
                String itemUrl = "https://item.jd.com/"+sku+".html";
                item.setUrl(itemUrl);

                //  商品图片
                String picUrl = skuEle.select("img[data-img]").first().attr("data-lazy-img");
                //	图片路径可能会为空的情况
                if(!StringUtils.isNotBlank(picUrl)){
                    picUrl =skuEle.select("img[data-img]").first().attr("data-lazy-img-slave");
                }
                picUrl ="https:"+picUrl.replace("/n9/","/n1/");	//	替换图片格式
                String picName = this.httpUtils.doGetImage(picUrl);
                item.setPic(picName);

                //  商品价格
                String priceJson = this.httpUtils.doGetHtml("https://p.3.cn/prices/mgets?skuIds=J_" + sku);
                double price = MAPPER.readTree(priceJson).get(0).get("p").asDouble();
                item.setPrice(price);

                //  商品标题
                String itemInfo = this.httpUtils.doGetHtml(item.getUrl());
                String title = Jsoup.parse(itemInfo).select("title").text();
                item.setTitle(title);

                //  商品创建时间
                item.setCreated(new Date());
                //  商品修改时间
                item.setUpdated(item.getCreated());
                items.add(item);
            }
        }
    }
}
