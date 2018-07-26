package zhitu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class StringHandler
{
  
  public static String removeLastString(String sourceString, String targetString)
  {
    if (isEmptyOrNull(sourceString)) {
      return "";
    }
    int index = sourceString.lastIndexOf(targetString);
    return sourceString.substring(0, index);
  }
  
  public static boolean isChinese(String source)
  {
    if (isEmptyOrNull(source)) {
      return false;
    }
    Pattern p = Pattern.compile("[一-龥]");
    Matcher m = p.matcher(source);
    return m.find();
  }
  
  public static String objectToString(Object obj)
  {
    if (isEmptyOrNull(obj)) {
      return "";
    }
    String str = String.valueOf(obj).trim();
    
    return str;
  }
  
  public static boolean isEmptyOrNull(Object obj)
  {
    if (obj == null) {
      return true;
    }
    String temp = String.valueOf(obj).trim();
    return isEmptyOrNull(temp);
  }
  
  public static boolean isNotEmptyOrNull(String source)
  {
    return !isEmptyOrNull(source);
  }
  
  public static String objectToString(Object temobj, Object obj)
  {
    if (obj == null) {
      return objectToString(temobj);
    }
    return objectToString(obj);
  }
  
  public static String objectToString(String obj)
  {
    if (isEmptyOrNull(obj)) {
      return "";
    }
    return obj.trim();
  }
  
  public static boolean decideString(String str1, String str2)
  {
    boolean result = false;
    String tempstr1 = objectToString(str1);
    String tempstr2 = objectToString(str2);
    result = tempstr1.equals(tempstr2);
    return result;
  }
  
  public static String interceptStr(String sourceString, int index)
  {
    if (isEmptyOrNull(sourceString)) {
      return "0";
    }
    String tempstr1 = objectToString(sourceString);
    return String.valueOf(tempstr1.charAt(index));
  }
  
  public static int interceptInt(String sourceString, int index)
  {
    if (isEmptyOrNull(sourceString)) {
      return 0;
    }
    String tempstr1 = objectToString(sourceString);
    String temp = String.valueOf(tempstr1.charAt(index));
    return NumberDealHandler.stringTranceInt(temp);
  }
  
  public static boolean checkStringZ(String sourceString, int index, int end)
  {
    if (isEmptyOrNull(sourceString)) {
      return true;
    }
    String regex = "^[0]*$";
    String tempstr = objectToString(sourceString);
    String temp = tempstr.substring(index, end + 1);
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(temp);
    return m.matches();
  }
  
  public static String objectToString(String obj, String defaultval)
  {
    if (isEmptyOrNull(obj)) {
      return defaultval;
    }
    return obj.trim();
  }
  
  public static boolean regxStringOfIp(String ip)
  {
    String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(ip);
    return m.matches();
  }
  
  public static boolean regexLettOrNum(String source)
  {
    String regex = "^([a-zA-Z_]{1})([\\w]*)$";
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(source);
    return m.matches();
  }
  
  public static boolean regexNumber(String source)
  {
    Pattern p = Pattern.compile("\\d+");
    Matcher m = p.matcher(source);
    return m.matches();
  }
  
  public static String[] split(String source, int index)
  {
    String[] str = new String[2];
    if (isEmptyOrNull(source))
    {
      str[0] = "";
      str[1] = "";
    }
    else
    {
      int len = source.length();
      str[0] = source.substring(0, len - index);
      str[1] = source.substring(len - index, len);
    }
    return str;
  }
  
  public static String[] split(String source, String separatorChars)
  {
    if (isEmptyOrNull(source)) {
      return null;
    }
    if (!source.contains(separatorChars)) {
      return new String[] { source };
    }
    return StringUtils.split(source, separatorChars);
  }
  
  public static String getIndexString(String source, int index)
  {
    if ((source == null) || ("".equals(source.trim()))) {
      return "0";
    }
    return String.valueOf(source.charAt(index));
  }
  
  public static int getIndexInt(String source, int index)
  {
    String temp = getIndexString(source, index);
    return Integer.parseInt(temp);
  }
  
  public static String clobToString(Clob clob)
    throws SQLException
  {
    if (clob == null) {
      return "";
    }
    try
    {
      Reader reader = clob.getCharacterStream();
      BufferedReader br = new BufferedReader(reader, 20480);
      StringBuffer sb = new StringBuffer();
      String str = br.readLine();
      while (str != null)
      {
        sb.append(str);
        str = br.readLine();
      }
      return sb.toString();
    }
    catch (IOException e) {}
    return "";
  }
  
  public static String removeHtmlFlag(String source)
  {
    if ((source == null) || ("".equals(source.trim()))) {
      return "";
    }
    return source.replaceAll("</?[^>]+>", "");
  }
  
  public static String strEncode(String source, String code)
  {
    if ((source == null) || ("".equals(source)) || ("null".equals(source))) {
      return "null";
    }
    try
    {
      return URLEncoder.encode(source, code);
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    return "";
  }
  
  public static String strEncode(String source)
  {
    if ((source == null) || ("".equals(source)) || ("null".equals(source))) {
      return "";
    }
    try
    {
      return URLEncoder.encode(source.trim(), "UTF-8");
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    return "";
  }
  
  public static String strDencode(String source)
  {
    if (isEmptyOrNull(source)) {
      return "";
    }
    try
    {
      return URLDecoder.decode(source.trim(), "UTF-8");
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    return "";
  }
  
  public static boolean checkTwoFloat(String source)
  {
    if ((source == null) || ("".equals(source.trim()))) {
      return false;
    }
    String regex = "^[1-9]+\\d*[\\.\\d]?\\d{0,2}$";
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(source);
    return m.matches();
  }
  
  public static boolean checkSpace(String source)
  {
    if (isEmptyOrNull(source)) {
      return false;
    }
    if (source.contains(" ")) {
      return true;
    }
    return false;
  }
  
  public static long ipToLong(String strIp)
  {
    String[] ip = strIp.split("\\.");
    long a0 = Long.parseLong(ip[0]);
    long a1 = Long.parseLong(ip[1]);
    long a2 = Long.parseLong(ip[2]);
    long a3 = Long.parseLong(ip[3]);
    long result = (a0 << 24) + (a1 << 16) + (a2 << 8) + a3;
    return result;
  }
  
  public static String repale(String source, String reg, String rep)
  {
    String result = "";
    if ((source == null) || ("".equals(source.trim()))) {
      result = "";
    } else if (source.trim().startsWith(reg)) {
      result = source.replaceFirst(reg, rep);
    } else {
      result = source;
    }
    return result;
  }
  
  public static boolean isEmpty(String source)
  {
    return StringUtils.isEmpty(source);
  }
  
  public static boolean isEmptyOrNull(String source)
  {
    return (StringUtils.isEmpty(source)) || 
      ("null".equalsIgnoreCase(source)) || 
      ("undefined".equalsIgnoreCase(source));
  }
  
  public static int checkTwoString(String start, String end)
  {
    return start.compareToIgnoreCase(end);
  }
  
  public static String repalce(String source, int big, String target)
  {
    if ((source == null) || ("".equals(source))) {
      return "";
    }
    if (source.length() < big) {
      return "";
    }
    if (target == null) {
      return source;
    }
    int index = source.length() - big;
    String temp = source.substring(index, source.length());
    String tempValue = "";
    for (int i = 0; i < big; i++) {
      tempValue = tempValue + temp.replace(temp, target);
    }
    String result = source.substring(0, index) + tempValue;
    return result;
  }
  
  public static boolean regexDec(String source)
  {
    if ((source == null) || ("".equals(source.trim()))) {
      return false;
    }
    String regex = "^[0-9]+([.]{1}[0-9]+?)?$";
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(source);
    return m.matches();
  }
  
  public static String[] teilen(String source, String targ)
  {
    if ((source == null) || (targ == null)) {
      return null;
    }
    return source.split(targ);
  }
  
  public static boolean checkString(String[] soures, String targetstr)
  {
    boolean flag = false;
    if ((soures == null) || (isEmptyOrNull(targetstr))) {
      return false;
    }
    for (int i = 0; i < soures.length; i++)
    {
      String temp = objectToString(soures[i]);
      if (temp.equals(targetstr))
      {
        flag = true;
        break;
      }
    }
    return flag;
  }
  
  public static String unescapeHtml(String unescapeStr)
  {
    return StringEscapeUtils.unescapeHtml(unescapeStr);
  }
  
  public static boolean matcheHtml(String matcheStr)
  {
    return Pattern.matches("<(.*)>.*<\\/\\1>|<(.*)\\/>", matcheStr);
  }
  
  public static boolean stringToBoolean(String source)
  {
    if (isEmptyOrNull(source)) {
      return false;
    }
    Boolean b = Boolean.valueOf(source);
    return b.booleanValue();
  }
  
  public static boolean checkStringNumber(String source)
  {
    Pattern p = Pattern.compile("[A-Za-z0-9_-]+");
    Matcher m = p.matcher(source);
    return m.matches();
  }
  
  public static String subStringWithBack(String val, int len)
  {
    if (val == null) {
      return "";
    }
    int length = val.length();
    if (length < len) {
      return val;
    }
    return val.substring(length - len);
  }
  
  public static boolean checkImageType(String imageType)
  {
    boolean flag = false;
    if (!isEmptyOrNull(imageType)) {
      if (("image/bmp".equalsIgnoreCase(imageType.trim())) || 
        ("image/gif".equalsIgnoreCase(imageType.trim())) || 
        ("image/jpg".equals(imageType.trim())) || 
        ("image/x-png".equalsIgnoreCase(imageType.trim())) || 
        ("image/pjpeg".equalsIgnoreCase(imageType.trim()))) {
        flag = true;
      } else {
        flag = false;
      }
    }
    return flag;
  }
  
  public static boolean isMobileNo(String mobiles)
  {
    if (isEmptyOrNull(mobiles)) {
      return false;
    }
    Pattern p = 
      Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
    Matcher m = p.matcher(mobiles);
    return m.matches();
  }
  
  public static boolean isEmail(String emails)
  {
    if (isEmptyOrNull(emails)) {
      return false;
    }
    String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
    Pattern p = Pattern.compile(str);
    Matcher m = p.matcher(emails);
    return m.matches();
  }
  
  public static boolean isPhone(String phones)
  {
    if (isEmptyOrNull(phones)) {
      return false;
    }
    String str1 = "[0]{1}[0-9]{2,3}-[0-9]{7,8}";
    String str2 = "[0]{1}[0-9]{2,3}[0-9]{7,8}";
    Pattern p1 = Pattern.compile(str1);
    Matcher m1 = p1.matcher(phones);
    Pattern p2 = Pattern.compile(str2);
    Matcher m2 = p2.matcher(phones);
    return (m1.matches()) || (m2.matches());
  }
  
  public static String urlAddParmert(String url, String parameter, String value)
  {
    StringBuffer buf = new StringBuffer();
    if (!isEmptyOrNull(url))
    {
      buf.append(url);
      if (url.indexOf("?") > -1) {
        buf.append("&");
      } else {
        buf.append("?");
      }
      buf.append(parameter);
      buf.append("=");
      buf.append(value);
    }
    return buf.toString();
  }
  
  public static String urlAddParmert(String url, String value)
  {
    if (isEmptyOrNull(url)) {
      return "";
    }
    if (isEmptyOrNull(value)) {
      return url;
    }
    StringBuffer buf = new StringBuffer();
    buf.append(url);
    if (url.indexOf("?") > -1) {
      buf.append("&");
    } else {
      buf.append("?");
    }
    buf.append(value);
    return buf.toString();
  }
  
  public static String strChangeCode(String source, String orig, String dest)
  {
    if (isEmptyOrNull(source)) {
      return "";
    }
    try
    {
      return new String(source.getBytes(orig), dest);
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    return "";
  }
  
  public static String strISOToUtf8(String source)
  {
    return strChangeCode(source, "ISO-8859-1", "UTF-8");
  }
  
  public static String strISOToGB2312(String source)
  {
    return strChangeCode(source, "ISO-8859-1", "GB2312");
  }
  
  public static String strUtf8ToGB2312(String source)
  {
    return strChangeCode(source, "UTF-8", "GB2312");
  }
  
  public static String firstLetterUpperCase(String source)
  {
    if (isEmptyOrNull(source)) {
      return "";
    }
    byte[] items = source.getBytes();
    items[0] = ((byte)((char)items[0] - 'a' + 65));
    return new String(items);
  }
  
  public static String replaceBlank(String source)
  {
    if (isEmptyOrNull(source)) {
      return "";
    }
    Pattern p = Pattern.compile("\\s*|\t|\r|\n");
    Matcher m = p.matcher(source);
    source = m.replaceAll("");
    return source;
  }
  
  public static String replaceBlank(String source, String replaceStr)
  {
    if (isEmptyOrNull(source)) {
      return "";
    }
    Pattern p = Pattern.compile("\\s*|\t|\r|\n");
    Matcher m = p.matcher(source);
    source = m.replaceAll(replaceStr);
    return source;
  }
  
  public static String repetition(String source, String prefix)
  {
    if (isEmptyOrNull(source)) {
      return "";
    }
    String temp = source;
    if (temp.startsWith(prefix)) {
      temp = temp.replaceFirst(prefix, "");
    }
    if (temp.endsWith(prefix)) {
      temp = removeLastString(temp, ",");
    }
    String[] temps = split(source, prefix);
    if ((temps == null) || (temps.length == 0)) {
      return "";
    }
    Set<String> sets = new LinkedHashSet<String>();
    for (String value : temps) {
      sets.add(value);
    }
    StringBuffer sb = new StringBuffer();
    for (String string : sets) {
      sb.append(string).append(",");
    }
    return removeLastString(sb.toString(), ",");
  }
  
  public static String contain(Map<String, String> map, String seachr)
  {
    if ((map == null) || (map.isEmpty())) {
      return seachr;
    }
    Set<String> sets = map.keySet();
    if ((sets == null) || (sets.isEmpty())) {
      return seachr;
    }
    StringBuffer sb = new StringBuffer();
    for (String key : sets) {
      if ((key.contains(seachr)) || (seachr.contains(key))) {
        sb.append(key).append(",");
      }
    }
    return removeLastString(sb.toString(), ",");
  }
  
  public static String domainToIp(String domain)
    throws UnknownHostException
  {
    StringBuffer s = new StringBuffer();
    if (isNotEmptyOrNull(domain))
    {
      String port = "";
      if (domain.contains(":"))
      {
        String[] domins = split(domain, ":");
        domain = domins[0];
        port = domins[1];
      }
      InetAddress hostAddr = InetAddress.getByName(domain);
      byte[] bts = hostAddr.getAddress();
      for (int i = 0; i < bts.length; i++) {
        if (bts[i] >= 0)
        {
          if (i == bts.length - 1) {
            s.append(bts[i]);
          } else {
            s.append(bts[i] + ".");
          }
        }
        else if (i == bts.length - 1) {
          s.append(bts[i] + 256);
        } else {
          s.append(bts[i] + 256 + ".");
        }
      }
      if (isNotEmptyOrNull(port)) {
        s.append(":").append(port);
      }
    }
    return s.toString();
  }
  
  public static String getBirthDate(String card)
  {
    if (isEmptyOrNull(card)) {
      return "";
    }
    String result = "";
    int len = card.length();
    if (len == 15)
    {
      String str_7 = String.valueOf(card.charAt(6));
      String str_8 = String.valueOf(card.charAt(7));
      String years = "19" + str_7 + str_8;
      String months = String.valueOf(card.charAt(8)) + 
        String.valueOf(card.charAt(9));
      String days = String.valueOf(card.charAt(10)) + 
        String.valueOf(card.charAt(11));
      result = years + "-" + months + "-" + days;
    }
    else if (len == 18)
    {
      String years = String.valueOf(card.charAt(6)) + 
        String.valueOf(card.charAt(7)) + 
        String.valueOf(card.charAt(8)) + 
        String.valueOf(card.charAt(9));
      String months = String.valueOf(card.charAt(10)) + 
        String.valueOf(card.charAt(11));
      String days = String.valueOf(card.charAt(12)) + 
        String.valueOf(card.charAt(13));
      result = years + "-" + months + "-" + days;
    }
    return result;
  }
  
  public static String getSex(String card)
  {
    if (isEmptyOrNull(card)) {
      return "";
    }
    String result = "";
    int len = card.length();
    if (len == 15)
    {
      String str_15 = String.valueOf(card.charAt(14));
      int num = NumberDealHandler.stringTranceInt(str_15);
      result = num % 2 == 0 ? "女" : "男";
    }
    else if (len == 18)
    {
      String str_17 = String.valueOf(card.charAt(16));
      int num = NumberDealHandler.stringTranceInt(str_17);
      result = num % 2 == 0 ? "女" : "男";
    }
    return result;
  }
  
  public static String base64Encoder(String str)
    throws UnsupportedEncodingException
  {
    if (isEmptyOrNull(str)) {
      return "";
    }
    String rs = "";
    byte[] b = str.getBytes("utf-8");
    if ((b != null) && (b.length > 0))
    {
      Base64 base64 = new Base64();
      byte[] b1 = base64.encode(b);
      rs = new String(b1);
    }
    return rs;
  }
  
  public static byte[] base64StringEncoderToByte(String str)
    throws UnsupportedEncodingException
  {
    if (isEmptyOrNull(str)) {
      return null;
    }
    byte[] b = str.getBytes("utf-8");
    byte[] rs = null;
    if ((b != null) && (b.length > 0))
    {
      Base64 base64 = new Base64();
      rs = base64.encode(b);
    }
    return rs;
  }
  
  public static String base64Encoder(byte[] b)
  {
    String rs = "";
    if ((b != null) && (b.length > 0))
    {
      Base64 base64 = new Base64();
      byte[] b1 = base64.encode(b);
      rs = new String(b1);
    }
    return rs;
  }
  
  public static String base64Decoder(String str)
    throws IOException
  {
    if (isEmptyOrNull(str)) {
      return "";
    }
    String rs = "";
    Base64 base64 = new Base64();
    byte[] b = base64.decode(str);
    if ((b != null) && (b.length > 0)) {
      rs = new String(b, "utf-8");
    }
    return rs;
  }
  
  public static byte[] base64StringDecoderToByte(String str)
    throws IOException
  {
    if (isEmptyOrNull(str)) {
      return null;
    }
    Base64 base64 = new Base64();
    byte[] b = base64.decode(str);
    return b;
  }
  
  public static String sqlFilter(String source)
  {
    StringBuffer sb = new StringBuffer(source);
    String str1 = source.toLowerCase();
    String inj_str = "and|exec|insert|select|where|sum|order|by|group by|by|group|like|delete|update|count|chr|mid|master|truncate|char|declare|or";
    String[] inj_stra = split(inj_str, "|");
    for (int i = 0; i < inj_stra.length; i++)
    {
      String inj = inj_stra[i];
      int index = str1.indexOf(inj);
      if (index >= 0)
      {
        sb = sb.replace(index, index + inj.length(), "");
        str1 = str1.replace(inj, "");
      }
    }
    return sb.toString().replaceAll("'", "");
  }
  
  public static boolean carLicense(String carLicense)
  {
    String regex = "[一-龥]{1}[A-Z]{1}[A-Z_0-9]{5}";
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(carLicense);
    return m.matches();
  }
  
  public static String getOSName()
  {
    return System.getProperty("os.name").toLowerCase();
  }
  
  public static String getLocalIP()
    throws Exception
  {
    if (isWindowsOS()) {
      return InetAddress.getLocalHost().getHostAddress();
    }
    return getLinuxLocalIp();
  }
  
  public static boolean isWindowsOS()
  {
    boolean isWindowsOS = false;
    String osName = getOSName();
    if (osName.indexOf("windows") > -1) {
      isWindowsOS = true;
    }
    return isWindowsOS;
  }
  
  public static boolean isLinuxOS()
  {
    boolean isLinuxOS = false;
    String osName = getOSName();
    if (osName.indexOf("linux") > -1) {
      isLinuxOS = true;
    }
    return isLinuxOS;
  }
  
  private static String getLinuxLocalIp()
    throws Exception
  {
    String ip = "";
    try
    {
      Enumeration<NetworkInterface> e1 = NetworkInterface.getNetworkInterfaces();
      while (e1.hasMoreElements())
      {
        NetworkInterface ni = (NetworkInterface)e1.nextElement();
        if (ni.getName().equals("eth0"))
        {
          Enumeration<InetAddress> e2 = ni.getInetAddresses();
          while (e2.hasMoreElements())
          {
            InetAddress ia = (InetAddress)e2.nextElement();
            if (!(ia instanceof Inet6Address)) {
              ip = ia.getHostAddress();
            }
          }
          break;
        }
      }
    }
    catch (Exception ex)
    {
      ip = "127.0.0.1";
      throw ex;
    }
    return ip;
  }
}
