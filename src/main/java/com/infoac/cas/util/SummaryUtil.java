package com.infoac.cas.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SummaryUtil {
    public static String spiltByLabel(String htmlStr){
        //定义script的正则表达式
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>";
        //定义style的正则表达式
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>";
        //定义HTML标签的正则表达式
        String regEx_html="<[^>]+>";
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script=p_script.matcher(htmlStr);
        //过滤script标签
        htmlStr=m_script.replaceAll("");
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style=p_style.matcher(htmlStr);
        //过滤style标签
        htmlStr=m_style.replaceAll("");
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(htmlStr);
        //过滤html标签
        htmlStr=m_html.replaceAll("");
        System.out.println(htmlStr.trim());
        //替换空格
        //替换空格
        htmlStr = htmlStr.replaceAll("&nbsp;","");
        htmlStr = htmlStr.replaceAll("&ldquo;","");
        htmlStr = htmlStr.replaceAll("&quot;","");
        htmlStr = htmlStr.replaceAll("&rdquo;","");
        htmlStr = htmlStr.replaceAll("&mdash;","——");
        htmlStr = htmlStr.replaceAll("&mdash;","-");
        htmlStr = htmlStr.replaceAll("&middot;","·");

        return htmlStr.trim();
    }
}
