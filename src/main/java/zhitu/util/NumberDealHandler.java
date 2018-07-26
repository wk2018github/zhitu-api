package zhitu.util;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberDealHandler
{
  public static float objectTofloat(Object obj)
  {
    if (obj == null) {
      return 0.0F;
    }
    return Float.valueOf(obj.toString()).floatValue();
  }
  
  public static int objectToInt(Object temobj, Object obj)
  {
    if (obj == null) {
      return objectToInt(temobj);
    }
    return objectToInt(obj);
  }
  
  public static Integer objectToInteger(Object obj)
  {
    if (obj == null) {
      return null;
    }
    String temp = String.valueOf(obj);
    if (StringHandler.isEmptyOrNull(temp)) {
      return null;
    }
    return Integer.valueOf(temp);
  }
  
  public static Long objectToLong(Object obj)
  {
    if (obj == null) {
      return null;
    }
    String temp = String.valueOf(obj);
    if (StringHandler.isEmptyOrNull(temp)) {
      return null;
    }
    return Long.valueOf(temp);
  }
  
  public static Double objectToDouble(Object obj)
  {
    if (obj == null) {
      return null;
    }
    String temp = String.valueOf(obj);
    if (StringHandler.isEmptyOrNull(temp)) {
      return null;
    }
    return Double.valueOf(temp);
  }
  
  public static Float objectToFloat(Object obj)
  {
    if (obj == null) {
      return null;
    }
    String temp = String.valueOf(obj);
    if (StringHandler.isEmptyOrNull(temp)) {
      return null;
    }
    return Float.valueOf(temp);
  }
  
  public static int objectToInt(Object obj)
  {
    if (obj == null) {
      return 0;
    }
    return Integer.parseInt(obj.toString());
  }
  
  public static double objectTodouble(Object obj)
  {
    if (obj == null) {
      return 0.0D;
    }
    return Double.parseDouble(obj.toString());
  }
  
  public static int doubleToInt(double source)
  {
    Double db = new Double(source);
    return db.intValue();
  }
  
  public static long objectTolong(Object obj)
  {
    if (obj == null) {
      return 0L;
    }
    return Long.valueOf(obj.toString()).longValue();
  }
  
  public static float percent(float source)
  {
    Double target = Double.valueOf(source * Math.pow(100.0D, -1.0D));
    return Float.parseFloat(target.toString());
  }
  
  public static double percent(double sourceDouble)
  {
    return sourceDouble * Math.pow(100.0D, -1.0D);
  }
  
  public static float percent(String source)
  {
    float targets = stringTranceFloat(source);
    Double target = Double.valueOf(targets * Math.pow(100.0D, -1.0D));
    return Float.parseFloat(target.toString());
  }
  
  public static float stringTranceFloat(String sourceString)
  {
    if ((sourceString == null) || ("".equals(sourceString.trim()))) {
      return 0.0F;
    }
    float temp = 0.0F;
    try
    {
      temp = Float.parseFloat(sourceString.trim());
    }
    catch (NumberFormatException e)
    {
      temp = -2.0F;
    }
    return temp;
  }
  
  public static double stringTranceDouble(String sourceString)
  {
    if (StringHandler.isEmptyOrNull(sourceString)) {
      return 0.0D;
    }
    double temp = 0.0D;
    try
    {
      temp = Double.parseDouble(sourceString.trim());
    }
    catch (NumberFormatException e)
    {
      temp = -1.0D;
    }
    return temp;
  }
  
  public static float myPow(float targetFloat)
  {
    if (targetFloat == 0.0F) {
      return -1.0F;
    }
    Double b = Double.valueOf(Math.pow(targetFloat, -1.0D));
    return Float.parseFloat(b.toString());
  }
  
  public static double myPow(double targetDouble)
  {
    if (targetDouble == 0.0D) {
      return -1.0D;
    }
    return Math.pow(targetDouble, -1.0D);
  }
  
  public static boolean checkInt(String source)
  {
    boolean flag = false;
    if (!StringHandler.isEmptyOrNull(source))
    {
      String regex = "^[1-9]\\d*|0$";
      Pattern p = Pattern.compile(regex);
      Matcher m = p.matcher(source.trim());
      flag = m.matches();
    }
    return flag;
  }
  
  public static int stringTranceInt(String sourceString)
  {
    if (StringHandler.isEmptyOrNull(sourceString)) {
      return 0;
    }
    try
    {
      return Integer.parseInt(sourceString.trim());
    }
    catch (NumberFormatException e) {}
    return -2;
  }
  
  public static int stringTranceInt(String sourceString, int defaultValue)
  {
    if (StringHandler.isEmptyOrNull(sourceString)) {
      return defaultValue;
    }
    try
    {
      return Integer.parseInt(sourceString.trim());
    }
    catch (NumberFormatException e) {}
    return -2;
  }
  
  public static long stringTranceLong(String sourceString)
  {
    if ((sourceString == null) || ("".equals(sourceString.trim()))) {
      return 0L;
    }
    return Long.parseLong(sourceString.trim());
  }
  
  public static int round(double source)
  {
    DecimalFormat df = new DecimalFormat("#");
    return Integer.parseInt(df.format(source));
  }
  
  public static String decimal(double source)
  {
    DecimalFormat df = new DecimalFormat("#,##0.00");
    return df.format(source);
  }
  
  public static String decimalThree(double source)
  {
    DecimalFormat df = new DecimalFormat("##0.000");
    return df.format(source);
  }
  
  public static String decimalTwo(double source)
  {
    DecimalFormat df = new DecimalFormat("##0.00");
    return df.format(source);
  }
  
  public static String decimalToOne(double source)
  {
    DecimalFormat df = new DecimalFormat("##0.0");
    return df.format(source);
  }
  
  public static String decimalFour(double source)
  {
    DecimalFormat df = new DecimalFormat("##0.0000");
    return df.format(source);
  }
  
  public static String decimalFive(double source)
  {
    DecimalFormat df = new DecimalFormat("##0.00000");
    return df.format(source);
  }
  
  public static String decimalTwo(float source)
  {
    double temp = new Float(source).doubleValue();
    return decimalTwo(temp);
  }
  
  public static String decimalFour(float source)
  {
    double temp = new Float(source).doubleValue();
    return decimalFour(temp);
  }
  
  public static String decimalOne(double source)
  {
    DecimalFormat df = new DecimalFormat("#,##0.##");
    return df.format(source);
  }
  
  public static String decimalOne1(double source)
  {
    DecimalFormat df = new DecimalFormat("##0.##");
    return df.format(source);
  }
  
  public static String doubleString(double source)
  {
    return decimal(source);
  }
  
  public static double decimala(double a)
  {
    DecimalFormat format = new DecimalFormat("#0.0000");
    return Double.valueOf(format.format(a)).doubleValue();
  }
  
  public static boolean isDouble(String str)
  {
    boolean row = false;
    if (StringHandler.isNotEmptyOrNull(str)) {
      try
      {
        Double.parseDouble(str);
        row = true;
      }
      catch (NumberFormatException e)
      {
        row = false;
      }
    }
    return row;
  }
  
  public static String truncate2Decimal(Double value)
  {
    if (value == null) {
      return "0.00";
    }
    DecimalFormat format = new DecimalFormat("##0.000");
    String result = format.format(value);
    return result.substring(0, result.length() - 1);
  }
  
  public static String truncate2DecimalStr(String value)
  {
    if (StringHandler.isEmptyOrNull(value)) {
      return "0.00";
    }
    DecimalFormat format = new DecimalFormat("##0.000");
    String result = format.format(Double.valueOf(value));
    return result.substring(0, result.length() - 1);
  }
  
  public static String truncate4Decimal(Double value)
  {
    if (value == null) {
      return "0.0000";
    }
    DecimalFormat format = new DecimalFormat("##0.00000");
    String result = format.format(value);
    return result.substring(0, result.length() - 1);
  }
  
  public static String truncate4DecimalStr(String value)
  {
    if (StringHandler.isEmptyOrNull(value)) {
      return "0.0000";
    }
    DecimalFormat format = new DecimalFormat("##0.00000");
    String result = format.format(Double.valueOf(value));
    return result.substring(0, result.length() - 1);
  }
  
  public static double truncate2DecimalDouble(double value)
  {
    DecimalFormat format = new DecimalFormat("##0.000");
    String valueString = format.format(value);
    return Double.parseDouble(valueString.substring(0, 
      valueString.length() - 1));
  }
  
  public static double truncate4DecimalDouble(double value)
  {
    DecimalFormat format = new DecimalFormat("##0.00000");
    String valueString = format.format(value);
    return Double.parseDouble(valueString.substring(0, 
      valueString.length() - 1));
  }
  
  public static float truncate2DecimalFloat(float value)
  {
    DecimalFormat format = new DecimalFormat("##0.000");
    String valueString = format.format(value);
    return Float.parseFloat(valueString.substring(0, 
      valueString.length() - 1));
  }
  
  public static int getPageCount(int total, int pageSize)
  {
    int pagetotal = 1;
    if (total <= pageSize) {
      pagetotal = 1;
    } else if (total % pageSize == 0) {
      pagetotal = total / pageSize;
    } else {
      pagetotal = total / pageSize + 1;
    }
    return pagetotal;
  }
  
  public static double changeDouble(String flag, double soure)
  {
    if ("0".equals(flag)) {
      return soure * 10000.0D;
    }
    return soure;
  }
  
  public static String format(int val, int digit)
  {
    return String.format("%0" + digit + "d", new Object[] { Integer.valueOf(val) });
  }
  
  public static double[] getMaxAndMin(double[] numbers)
  {
    double[] resutl = new double[2];
    double max = numbers[0];
    double min = numbers[0];
    int arrLen = numbers.length;
    for (int i = 0; i < arrLen; i++)
    {
      if (numbers[i] >= max) {
        max = numbers[i];
      }
      if (numbers[i] <= min) {
        min = numbers[i];
      }
    }
    resutl[0] = min;
    resutl[1] = max;
    return resutl;
  }
  
  public static int[] getMaxAndMin(int[] numbers)
  {
    int[] resutl = new int[2];
    int max = numbers[0];
    int min = numbers[0];
    int arrLen = numbers.length;
    for (int i = 0; i < arrLen; i++)
    {
      if (numbers[i] >= max) {
        max = numbers[i];
      }
      if (numbers[i] <= min) {
        min = numbers[i];
      }
    }
    resutl[0] = min;
    resutl[1] = max;
    return resutl;
  }
  
  public static Integer[] stringArrayToIntegerArray(String[] sources)
  {
    if ((sources == null) || (sources.length == 0)) {
      return new Integer[0];
    }
    int len = sources.length;
    Integer[] results = new Integer[len];
    for (int i = 0; i < len; i++) {
      results[i] = objectToInteger(sources[i]);
    }
    return results;
  }
  
  public static double createRandom(int rand)
  {
    int a = new Random().nextInt(rand);
    return new Integer(a).doubleValue();
  }
}
