package com.infoac.cas.util;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;


public class StringUtil {
    /**
     * 获取指定指标之间的内容
     *
     * @param sContent 要处理的字符串
     * @param sTagName 指标名称
     * @return 指标的内容
     */
    public static String getTagValue(String sContent, String sTagName) {
        String sTagValue = "";
        String sTemp;
        if (sContent != null && sTagName != null) {
            sTemp = "<" + sTagName + ">";
            int nPosBegin = sContent.indexOf(sTemp);
            if (nPosBegin >= 0) {
                sTemp = "</" + sTagName + ">";
                int nPosEnd = sContent.indexOf(sTemp);
                if (nPosEnd >= 0) {
                    sTagValue = sContent.substring(nPosBegin + sTagName.length() + 2, nPosEnd);
                }
            }
        }
        return sTagValue;
    }

    public static String[] getTagValues(String sContent, String sTagName) {
        String[] sTagValues = null;
        String sTemp = "";
        String sEndTag = "</" + sTagName + ">";
        int nCount = 0;
        int nFromIndex = 0;
        int nPos = 0;
        int i = 0;

        for (nPos = 0; (nPos = sContent.indexOf(sEndTag, nFromIndex)) > 0; ) {
            nCount++;
            nFromIndex = nPos + sEndTag.length();
        }
        if (nCount > 0) {
            sTagValues = new String[nCount];
        }

        nFromIndex = 0;

        for (nPos = 0; (nPos = sContent.indexOf(sEndTag, nFromIndex)) > 0; ) {
            sTemp = sContent.substring(nFromIndex);
            sTagValues[i] = getTagValue(sTemp, sTagName);
            nFromIndex = nPos + sEndTag.length();
            i++;
        }

        return sTagValues;
    }

    /**
     * 将给定的字符串按照给定的分隔符分割成一个数组
     *
     * @param strToSplit 要处理的字符串
     * @param separator  分隔符
     * @return 分解后形成的数组
     */
    public static String[] splitString2Array(String strToSplit, String separator) {
        if (strToSplit == null || strToSplit.length() == 0)
            return null;
        if (separator == null || separator.length() == 0)
            return null;
        if (strToSplit.startsWith(separator))  //如果是以分隔符打头，则将打头的分隔符去掉
        {
            strToSplit = strToSplit.substring(separator.length());
        }
        int count = 0;
        int index = 0;
        int lastIndex = strToSplit.lastIndexOf(separator);
        if (lastIndex + separator.length() < strToSplit.length())   //如果不是以分割符结尾则补一个
        {
            strToSplit += separator;
        }
        for (int pos = 0; (pos = strToSplit.indexOf(separator, index)) > 0; ) {
            count++;
            index = pos + separator.length();
        }

        if (count == 0)
            return (new String[]{strToSplit});
        String retStrArray[] = new String[count];
        index = 0;
        int pos;
        for (int i = 0; (pos = strToSplit.indexOf(separator, index)) > 0; i++) {
            retStrArray[i] = strToSplit.substring(index, pos);
            index = pos + separator.length();
        }
        return retStrArray;
    }

    /**
     * id数组转换成一个字符串
     *
     * @param sArray id数组
     * @return 拼凑好的字符串
     */
    public static String stringArray2SqlIn(String[] sArray) {
        StringBuffer sBuffer = new StringBuffer(256);
        if (sArray == null) {
            return sBuffer.toString();
        }

        int nLen = sArray.length;
        for (int i = 0; i < nLen; i++) {
            if (i == 0) {
                sBuffer.append(sArray[i]);
            } else {
                sBuffer.append(",").append(sArray[i]);
            }
        }
        return sBuffer.toString();
    }

    /**
     * list集合转换成一个字符串
     *
     * @param list list集合
     * @return 拼凑好的字符串
     */
    public static String stringList2SqlInt(List<?> list) {
        StringBuffer sBuffer = new StringBuffer(256);
        if (list == null || list.size() <= 0) {
            return sBuffer.toString();
        }

        int nLen = list.size();
        for (int i = 0; i < nLen; i++) {
            String id = (String) list.get(i);
            if (i == 0) {
                sBuffer.append(id);
            } else {
                sBuffer.append(",").append(id);
            }
        }

        return sBuffer.toString();
    }

    /**
     * list集合转换成一个字符串
     *
     * @param list list集合
     * @return 拼凑好的字符串
     */
    public static String intList2SqlInt(List<?> list) {
        StringBuffer sBuffer = new StringBuffer(256);
        if (list == null || list.size() <= 0) {
            return sBuffer.toString();
        }

        int nLen = list.size();
        for (int i = 0; i < nLen; i++) {
            Integer id = (Integer) list.get(i);
            if (i == 0) {
                sBuffer.append(id);
            } else {
                sBuffer.append(",").append(id);
            }
        }

        return sBuffer.toString();
    }

    /**
     * 替换字符串
     *
     * @param strSource 源字符串
     * @param strOld    要替换的
     * @param strNew    替换成
     * @return 替换后的字符串
     */
    public static String replaceString(String strSource, String strOld, String strNew) {
        if (strSource == null) return null;

        int length = strSource.length();
        if (length < 1) return strSource;

        int offset = 0;
        int index = strSource.indexOf(strOld, offset);
        if (index < 0) return strSource;

        int oldLength = strOld.length();
        if (oldLength < 1) return strSource;

        StringBuffer strBuff = new StringBuffer(1024);
        while (index > -1) {
            strBuff.append(strSource.substring(offset, index));
            offset = index + oldLength;
            strBuff.append(strNew);

            index = strSource.indexOf(strOld, offset);
        }
        if (offset < length) {
            strBuff.append(strSource.substring(offset, length));
        }
        return strBuff.toString();
    }

    /**
     * 将source中的from串替换未to
     *
     * @param source 要进行替换操作的字符串
     * @param from   要替换的串
     * @param to     取代原来的串
     * @return 结果
     */
    public static String replace(String source, String from, String to) {
        if (!from.equals(to)) {
            int index = source.indexOf(from);
            if (index >= 0) {
                source = source.substring(0, index) + to + source.substring(index + from.length());
                source = replace(source, from, to);
            }
        }
        return source;
    }

    /**
     * 在content中查找第count次出现seach的索引,从后向前查找
     *
     * @param content
     * @param seach
     * @param count
     * @return
     * @throws Exception
     */
    public static int lastIndexOfCount(String content, String seach, int count) throws Exception {
        int index = -1;
        for (int i = 1; i <= count; i++) {
            index = content.lastIndexOf(seach);

            if (index > -1) {
                if (count > 1) {
                    content = content.substring(0, index);
                    index = content.length();
                }
            } else {
                break;
            }
        }

        return index;
    }

    public static String isNull(Object isNullParam) {
        if (isNullParam == null) {
            return "";
        }
        return String.valueOf(isNullParam);
    }

    /**
     * 根据文件名获取文件名称和后缀名
     *
     * @param filename
     * @return str[0]:文件名称,str[1]:后缀名
     */
    public static String[] getNameAndSuffix(String filename) {
        String[] str = null;
        int index = filename.lastIndexOf(".");
        if (index > -1) {
            str = new String[2];
            str[0] = filename.substring(0, index);
            str[1] = filename.substring(index + 1);
        } else {
            str = new String[1];
            str[0] = filename;
        }

        return str;
    }

    /**
     * 去除字符串中html代码
     *
     * @param inputString
     * @return
     * @throws Exception
     */
    public static String htmlToText(String inputString) throws Exception {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        java.util.regex.Matcher m_script;
        Pattern p_style;
        java.util.regex.Matcher m_style;
//		java.util.regex.Pattern p_html;
//		java.util.regex.Matcher m_html;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            // }
            // String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            // p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
            // m_html = p_html.matcher(htmlStr);

            // htmlStr = m_html.replaceAll(""); //过滤html标签
            // 过滤html标签
            htmlStr = htmlStr.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "")
                    .replaceAll("</[a-zA-Z]+[1-9]?[^><]*>", "");
            //htmlStr = htmlStr.replaceAll("\\s*|\t|\r|\n", "");// 去除字符串中的空格,回车,换行符,制表符
            htmlStr = htmlStr.replaceAll("&nbsp;", "");// 去除字符串中的空格

            textStr = htmlStr;

        } catch (Exception e) {
            throw new Exception();
        }

        return getContent(textStr);// 返回文本字符串
    }

    /**
     * 把一个字符串内的指定标签标记替换成空值
     *
     * @param res  内容字符串
     * @param tags 标签数组
     * @throws Exception
     * @return 替换后的字符串
     */
    public static String replaceTag(String res, String... tags) throws Exception {
        for (String tag : tags) {
            String startTag = "<" + tag;
            String endTag = "</" + tag + ">";
            res = res.replaceAll(endTag, "");
            while (true) {
                int startIndex = res.indexOf(startTag);
                if (startIndex > -1) {
                    int endIndex = res.indexOf(">", startIndex);
                    String tagText = res.substring(startIndex, endIndex + 1);
                    res = res.replace(tagText, "");
                } else {
                    break;
                }
            }
        }

        return res;
    }

    /**
     * 把一个字符串[@@]内的字符串替换成空值
     *
     * @param content 传进的字符串
     * @return 替换成空值后的字符串
     */
    public static String clearFaceCode(String content) {
        while (true) {
            int startIndex = content.indexOf("[@");
            int endIndex = content.indexOf("@]");

            if (startIndex != -1 && endIndex != -1) {
                content = content.replace(content.substring(startIndex, (endIndex + 2)), "");
            } else {
                break;
            }
        }
        return content;
    }

    /**
     * html转义源符号
     *
     * @param s
     * @return
     */
    public static String getContent(String s) {
        s = s.replaceAll("&ensp;", " ");
        s = s.replaceAll("&nbsp;", " ");
        s = s.replaceAll("&emsp;", "　");
        s = s.replaceAll("&reg;", "®");
        s = s.replaceAll("&lt;", "<");
        s = s.replaceAll("&gt;", ">");
        s = s.replaceAll("&ldquo;", "“");
        s = s.replaceAll("&rdquo;", "”");
        s = s.replaceAll("&quot;", "“");
        s = s.replaceAll("&rsquo;", "’");
        s = s.replaceAll("&lsquo;", "‘");
        s = s.replaceAll("&mdash;", "—");
        s = s.replaceAll("&ndash;", "–");
        s = s.replaceAll("&middot;", "·");
        s = s.replaceAll("&trade;", "™");
        s = s.replaceAll("&copy;", "©");
        s = s.replaceAll("&hellip;", "…");
        s = s.replaceAll("<br>", "\r\n");
        s = s.replaceAll("<br/>", "\r\n");
        s = s.replaceAll("<br />", "\r\n");
        s = s.replaceAll("  ", "　");
        s = s.replaceAll("&amp;", "&");
        return s;
    }

    /**
     * html转义特殊符号
     *
     * @param s
     * @return
     */
    public static String getHtmlContent(String s) {
        s = s.replaceAll("&", "&amp;");
        s = s.replaceAll("　", " ");
        s = s.replaceAll("…", "&hellip;");
        s = s.replaceAll("®", "&reg;");
        s = s.replaceAll("<", "&lt;");
        s = s.replaceAll(">", "&gt;");
        s = s.replaceAll("\r\n", "<br>");
        s = s.replaceAll("\r\n", "<br/>");
        s = s.replaceAll("\r\n", "<br />");
        s = s.replaceAll("“", "&ldquo;");
        s = s.replaceAll("”", "&rdquo;");
        s = s.replaceAll("“", "&quot;");
        s = s.replaceAll("’", "&rsquo;");
        s = s.replaceAll("‘", "&lsquo;");
        s = s.replaceAll("—", "&mdash;");
        s = s.replaceAll("–", "&ndash;");
        s = s.replaceAll("·", "&middot;");
        s = s.replaceAll("™", "&trade;");
        s = s.replaceAll("©", "&copy;");
        return s;
    }

    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     *
     * @param s 需要得到长度的字符串
     * @return int 得到的字符串长度
     */
    public static int length(String s) {
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    public static String subString(String source, int length) {
        char[] c = source.toCharArray();
        int len = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
            sb.append(c[i]);
            if (len >= length) {
                break;
            }
        }
        sb.append("...");
        return sb.toString();
    }

    /**
     * 判断content是否为空 null 或者 ""
     *
     * @param content
     * @return boolean  true 为空  false 不为空
     */
    public static boolean isEmpty(String content) {
        return null == content || 0 == content.trim().length();
    }

    /**
     * 判断content是否不为空 即不为 null 和 ""
     *
     * @param content
     * @return true 不为空   false 为空
     */
    public static boolean isNotEmpty(String content) {
        return !isEmpty(content);
    }

    /**
     * @param target
     * @return
     */
    public static final String convertFirstChar2LowerCase(String target) {
        if (isEmpty(target)) {
            throw new IllegalArgumentException("The input string is empty!");
        }
        String firstChar = target.substring(0, 1);
        return firstChar.toLowerCase() + target.substring(1, target.length());
    }
    
    public static String replaceLast(String src, String regex, String replacement) {
    	if(null == src || null==regex || null == replacement)
    		return src;
        return src.replaceFirst("(?s)"+regex+"(?!.*?"+regex+")", replacement);
    }

    /**
     * 对字符串处理:将指定位置到指定位置的字符以星号代替
     * @param content 传入的字符串
     * @param begin 开始位置
     * @param end 结束位置
     * @return 带星号的字符串
     */
    public static String getStarString(String content, int begin, int end) {

        if (begin >= content.length() || begin < 0) {
            return content;
        }
        if (end >= content.length() || end < 0) {
            return content;
        }
        if (begin >= end) {
            return content;
        }
        String starStr = "";
        for (int i = begin; i < end; i++) {
            starStr = starStr + "*";
        }
        return content.substring(0, begin) + starStr + content.substring(end, content.length());

    }

    /**
     * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替
     * 如果两个字符，则显示一个
     * @param content 传入的字符串
     * @param frontNum 保留前面字符的位数
     * @param endNum 保留后面字符的位数
     * @return 带星号的字符串
     */
    public static String getStarString2(String content, int frontNum, int endNum) {

        if (frontNum >= content.length() || frontNum < 0) {
            return content;
        }
        if (endNum >= content.length() || endNum < 0) {
            return content;
        }
        if(frontNum + endNum == content.length()) {
            return getStarString2(content, 1, 0);
        }
        if (frontNum + endNum > content.length()) {
            return content;
        }
        StringBuilder starStr = new StringBuilder();
        for (int i = 0; i < (content.length() - frontNum - endNum); i++) {
            starStr.append("*");
        }
        return content.substring(0, frontNum) + starStr
                + content.substring(content.length() - endNum, content.length());
    }

    public static String toStringWithNull(Object object){
        if(object == null){
            return null;
        }
        return String.valueOf(object);
    }
    
    /**得到32位的uuid
	 * @return
	 */
	public static String get32UUID(){
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}

    public static void main(String[] args) {
        System.out.println(getStarString2("你好", 1, 1));
        System.out.println(getStarString2("好", 1, 1));
    }

    /**
     * 填空题\选项拼接
     */
    public static String gapText(String topic,List<String> content){
        String[] split = topic.split("value=\"\"");
        String news="";
        int length = split.length;
        if(length>1){
            for (int i = 0; i < length; i++) {
                System.out.println(split[i]);
                if(i+1<length){
                    news += split[i]+"value=\"" +content.get(i)+"\"";
                }else {
                    news += split[i];
                }
            }
        }else {
            news = topic;
        }
        return news;
    }
    /**
     * 泛型方法(通用)，把list转换成以“,”相隔的字符串 调用时注意类型初始化（申明类型） 如：List<Integer> intList = new ArrayList<Integer>(); 调用方法：StringUtil.listTtoString(intList); 效率：list中4条信息，1000000次调用时间为850ms左右
     *
     * @author fengliang
     * @serialData 2008-01-09
     * @param <T>
     *            泛型
     * @param list
     *            list列表
     * @return 以“,”相隔的字符串
     */
    public static <T> String listTtoString(List<T> list) {
        if (list == null || list.size() < 1)
            return "";
        Iterator<T> i = list.iterator();
        if (!i.hasNext())
            return "";
        StringBuilder sb = new StringBuilder();
        for (;;) {
            T e = i.next();
            sb.append(e);
            if (!i.hasNext())
                return sb.toString();
            sb.append(",");
        }
    }

}
