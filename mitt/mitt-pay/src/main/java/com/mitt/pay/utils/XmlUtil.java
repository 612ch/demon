package com.mitt.pay.utils;


import com.mitt.pay.wxpay.sdk.WXPayUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * xml转换工具
 *
 * @author mit
 * @date 2021/04/14
 */
public class XmlUtil {
    /**
     * JavaBean转换成xml
     * 默认编码UTF-8
     *
     * @param obj
     * @return
     */
    public static String convertToXml(Object obj) {
        return convertToXml(obj, "UTF-8", true);
    }

    public static String convertToXml(Object obj, boolean format) {
        return convertToXml(obj, "UTF-8", format);
    }

    /**
     * 生成没有 standalone="yes"
     *
     * @param obj
     * @return
     */
    public static String convertToXmlStandalone(Object obj) {
        return convertToXmlStandalone(obj, "UTF-8", true);
    }

    public static String convertToXmlStandalone(Object obj, boolean format) {
        return convertToXmlStandalone(obj, "UTF-8", format);
    }

    /**
     * 转换实体类成为xml,不需要声明部分
     *
     * @param obj java对象
     * @return
     */
    public static String convertToXmlIgnoreXmlHead(Object obj) {
        return convertToXmlIgnoreXmlHead(obj, "UTF-8");
    }

    /**
     * JavaBean转换成xml去除xml声明部分
     *
     * @param obj      内容
     * @param encoding 编码
     * @return
     */
    public static String convertToXmlIgnoreXmlHead(Object obj, String encoding) {
        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
//            marshaller.setListener(new MarshallerListener());
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
			result = getSubUtilStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * JavaBean转换成xml
     *
     * @param obj      内容
     * @param encoding 编码
     * @param format   是否个格式化
     * @return
     */
    public static String convertToXml(Object obj, String encoding, boolean format) {
        String result = null;
        StringWriter writer = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, format);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
			result = getSubUtilStr(result);
        } catch (Exception e) {
            e.printStackTrace();
            WXPayUtil.getLogger().error(e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * JavaBean转换成xml
     *
     * @param obj      内容
     * @param encoding 编码
     * @param format   是否个格式化
     * @return
     */
    public static String convertToXmlStandalone(Object obj, String encoding, boolean format) {
        String result = null;
        StringWriter writer = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, format);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
//            marshaller.setListener(new MarshallerListener());
//			marshaller.setProperty(Marshaller.JAXB_FRAGMENT,
//				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer = new StringWriter();
            // 2) 自定义生成
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            marshaller.marshal(obj, writer);
            result = writer.toString();
            //封闭标签
			result = getSubUtilStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * xml转换成JavaBean
     *
     * @param xml xml字符
     * @param c   对象实体类
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertToJava(String xml, Class<T> c) {
        if (xml == null || xml.equals(""))
            return null;
        T t = null;
        xml = getSubUtilResTag(xml);
        StringReader reader = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            reader = new StringReader(xml);
            t = (T) unmarshaller.unmarshal(reader);
        } catch (UnmarshalException e) {
            WXPayUtil.getLogger().error(e.getMessage());
        } catch (Exception e) {
            WXPayUtil.getLogger().error(e.getMessage());
        } finally {
            if (reader != null)
                reader.close();
        }
        return t;
    }

    /**
     * xml文件转换成实体对象
     *
     * @param filePath 文件路径
     * @param c        实体类
     * @param <T>      返回对象
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertToJava(File filePath, Class<T> c) throws IOException {
        if (!filePath.exists())
            return null;
        T t = null;
        FileReader reader = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            reader = new FileReader(filePath);
            t = (T) unmarshaller.unmarshal(reader);
        } catch (Exception e) {
            WXPayUtil.getLogger().error(e.getMessage());
        } finally {
            if (reader != null)
                reader.close();
        }
        return t;
    }


    /**
     * 文本文件转换为指定编码的字符串
     *
     * @param file     文本文件
     * @param encoding 编码类型
     * @return 转换后的字符串
     * @throws IOException
     */
    public static String file2String(File file, String encoding) {
        InputStreamReader reader = null;
        StringWriter writer = new StringWriter();
        try {
            if (encoding == null || "".equals(encoding.trim())) {
                reader = new InputStreamReader(new FileInputStream(file), encoding);
            } else {
                reader = new InputStreamReader(new FileInputStream(file));
            }
            // 将输入流写入输出流
            char[] buffer = new char[1024];
            int n = 0;
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            WXPayUtil.getLogger().error(e.getMessage());
            return null;
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    WXPayUtil.getLogger().error(e.getMessage());
                }
        }
        return writer.toString();
    }

    /**
     * 将字符串写入指定文件(当指定的父路径中文件夹不存在时，会最大限度去创建，以保证保存成功！)
     *
     * @param res      原字符串
     * @param filePath 文件路径
     * @return 成功标记
     */
    public static boolean string2File(String res, String filePath) {
        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File distFile = new File(filePath);
            if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char buf[] = new char[1024];         //字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
            return flag;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
    /**
     * xml-->java异常
     * @param soap 字符串
     * @return
     */
    public static String getSubUtilResTag(String soap) {
        if(null == soap || soap.equals("")){
            return soap;
        }
        try {
            String start = "unformat failed! Tag";
            String end = "not";
            String rgex = start+"(.*?)"+end;
            List<String> list = new ArrayList<String>();
            Pattern pattern = Pattern.compile(rgex);// 匹配的模式
            Matcher m = pattern.matcher(soap);
            while (m.find()) {
                int i = 1;
                list.add(m.group(i));
                i++;
            }
            if (null != list && list.size() > 0) {
                try {
                    for (String str : list) {
                        String s = start+str+end;
                        String replace = s.replaceAll("<","");
                        replace = replace.replaceAll(">","");
                        soap = soap.replace(s,replace);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return soap;
        }catch (Exception e){
            e.printStackTrace();
        }
        return soap;
    }
    /**
     * 正则表达式匹配两个指定字符串中间的内容
     * String rgex = "abc(.*?)abc";
     *
     * @param soap
     * @return
     */
    public static String getSubUtilStr(String soap) {
        String rgex = "<(.*?)/>";
        List<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            int i = 1;
            list.add(m.group(i));
            i++;
        }
        if (null != list && list.size() > 0) {
            try {
                for (String str : list) {
					String s = "<"+str+"/>";
					String replace = "<"+str+"></"+str+">";
					soap = soap.replace(s,replace);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return soap;
    }

    /**
     * 正则表达式匹配两个指定字符串中间的内容
     * String rgex = "abc(.*?)abc";
     *
     * @param soap
     * @return
     */
    public static List<String> getSubUtil(String soap, String rgex) {
        List<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            int i = 1;
            list.add(m.group(i));
            i++;
        }
        return list;
    }

    /**
     * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样
     *
     * @param soap
     * @param rgex
     * @return
     */
    public static String getSubUtilSimple(String soap, String rgex) {
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }
}
