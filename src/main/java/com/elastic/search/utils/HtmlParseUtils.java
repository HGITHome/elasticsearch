package com.elastic.search.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.autoconfigure.web.ResourceProperties;


import javax.swing.text.AbstractDocument;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @Title:
 * @Package
 * @Description:
 * @Company
 * @date 2020/11/7 18:10
 */
public class HtmlParseUtils {

    /**
     * 爬取京东商城搜索数据
     * @param keyWork
     * @return
     * @throws IOException
     */
    public static List<ResourceProperties.Content> listGoods(String keyWork) throws IOException {
        String url = "https://search.jd.com/Search?keyword="+keyWork;
        Document document = Jsoup.parse(new URL(url),30000);
        Element element = document.getElementById("J_goodsList");
        Elements li = element.getElementsByTag("li");
        List<ResourceProperties.Content> list = new ArrayList<>();
        for (Element elements : li) {
            String img = elements.getElementsByTag("img").eq(0).attr("src");
            String price = elements.getElementsByClass("p-price").eq(0).text();
            String title = elements.getElementsByClass("p-name").eq(0).text();
            ResourceProperties.Content content = new ResourceProperties.Content();

        }
        return list;
    }
}
