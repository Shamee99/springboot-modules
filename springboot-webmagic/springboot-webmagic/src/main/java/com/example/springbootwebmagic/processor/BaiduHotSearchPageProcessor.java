package com.example.springbootwebmagic.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.selector.XpathSelector;

import java.util.List;

public class BaiduHotSearchPageProcessor implements PageProcessor {

    // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    /**
     * 定制爬虫逻辑的核心接口，在这里编写抽取逻辑
     * @param page
     */
    @Override
    public void process(Page page) {

        System.out.println(page.getHtml());
        /**
         * 通过page.getHtml()可以获取到main函数中Spider.create(new BaiduHotSearchPageProcessor()).addUrl中的地址的网页内容
         * 1、通过$或css()方法获取到该page html下某元素dom
         */
        Selectable selectable = page.getHtml().$(".theme-hot").select(
                new XpathSelector("a[@class='item-wrap_2oCLZ']")
        );
        List<Selectable> nodes = selectable.nodes();

        /**
         * 获取到指定的dom后，从这些dom中提取元素内容。
         */
        System.out.println("今日百度热搜：");
        for (int i = 1; i <= nodes.size() - 1; i++) {
            Selectable node = nodes.get(i);
            String link = node.$(".item-wrap_2oCLZ", "href").get();
            String title = node.$(".c-single-text-ellipsis", "text").get();
            System.out.printf("%d、%s，访问地址：%s%n", i, title, link);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        // 创建一个Spider，并把我们的处理器放进去
        Spider.create(new BaiduHotSearchPageProcessor())
                // 添加这个Spider要爬取的网页地址
                .addUrl("https://top.baidu.com/board?platform=pc&sa=pcindex_entry")
                // 开启5个线程执行，并开始爬取
                .thread(5).run();
    }
}
