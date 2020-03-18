//package com.lbc.others;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import org.testng.ITestContext;
//import org.testng.annotations.DataProvider;
//
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Arrays;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//public class DataProviderBase {
//
//    /**
//     * json参数文件存在于(classname测试类名).json
//     * 数据处理类存在于(classname测试类名)Data.java
//     * 数据处理方法名为(testname测试方法名)DataFactory
//     * 使用方法：
//     * 1.test类  test.json数据类 testmethodnamedatafacory数据处理类
//     * 2.test类 读取 testjson数据类 根据测试组（下面的规定解释）获取测试方法参数
//     * 3.获取参数之后使用各个测试方法匹配的datafactory处理数据
//     * 规定：
//     * test类中测试方法命名： testgroup_testxxxx_xxxx
//     * 第一个下划线"_"之前的testgroup将被认为一个测试组
//     * 一个测试组共用同一块json文件中的数据
//     * 数据分组根据各个业务模块而定，比如根据biz模块分组等
//     */
//    @DataProvider(name = "data")
//    public static Object[][] getTestData(Method method, ITestContext context) throws Exception {
//
//        String methodName = method.getName();
//        Class testClass = method.getDeclaringClass();
//        String className = method.getDeclaringClass().getCanonicalName();
//
//        Object[][] testData = new Object[0][];
//
//        try {
//            //获取数据
//            Type[] genericParameterTypes = method.getGenericParameterTypes();
//            Class[] paramList = Arrays.stream(genericParameterTypes)
//                    .map(allParam -> {
//                        if (allParam instanceof ParameterizedType) {
//                            //就规定一个泛型参数吧。两个泛型参数这个代码直接可以重构了 遇到再说吧
//                            //可以list里面存数组，遇到的大哥麻烦修改下 谢谢 本人懒 QAQ
//                            return ((ParameterizedType) allParam).getActualTypeArguments()[0];
//                        } else {
//                            return allParam;
//                        }
//                    }).toArray(Class[]::new);
//            String jsonFilename = method.getDeclaringClass().getSimpleName() + ".json";
//            URL jsonUrl = testClass.getResource(jsonFilename);
//            String jsonData = readJsonData(jsonUrl.toURI());
//            JSONArray jsonArr = matchJsonData(jsonData, methodName);
//            testData = parserJsonData(jsonArr, paramList);
//
//            //如果需要的话数据工厂操作数据
//            Class<?> clazz = Class.forName(className + "Data");
//            Object o = clazz.newInstance();
//            Method declaredMethod = o.getClass().getDeclaredMethod(methodName + "DataFactory", Object[][].class);
//            testData = (Object[][]) declaredMethod.invoke(o, (Object) testData);
//
//        } catch (ClassNotFoundException e) {
//            return testData;
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            return testData;
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//        return testData;
//    }
//
//    /**
//     * 根据classname获取jsonstr
//     */
//    private static String readJsonData(URI uri) {
//        try {
//            Stream<String> stringStream = Files.lines(Paths.get(uri));
//            return stringStream.collect(Collectors.joining());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    /**
//     * 从jsonstr parser的json对象中 匹配methodname的参数
//     * 测试方法前缀相同为一组方法 一组方法使用同一块数据，之后通过factory去重写应该会恰当一点
//     * 否则文件太大 数据太多 得不偿失。
//     */
//    private static JSONArray matchJsonData(String jsonStr, String methodName) throws Exception {
//        JSONObject jsonObject = JSON.parseObject(jsonStr);
//        String keyName = methodName.split("_")[0];
//        JSONArray methodParamArr = jsonObject.getJSONArray(keyName);
//        if (null == methodParamArr) {
//            throw new Exception("未找到测试请求参数");
//        }
//        return methodParamArr;
//    }
//
//    private static Object[][] parserJsonData(JSONArray jsonArray, Class[] paramTypes) {
//        Object[][] objectsArr = new Object[jsonArray.size()][];
//
//        for (int i = 0; i < jsonArray.size(); i++) {
//            //参数列表 and 断言 i
//            JSONObject paramListi = jsonArray.getJSONObject(i);
//            JSONArray dataArr = paramListi.getJSONArray("data");
//
//            objectsArr[i] = new Object[dataArr.size()];
//            for (int j = 0; j < objectsArr[i].length; j++) {
//                if (j < objectsArr[i].length) {
//                    //data
//                    Object ij = dataArr.get(j);
//                    if (ij instanceof JSONObject) {
//                        objectsArr[i][j] = JSONObject.parseObject(((JSONObject) ij).toJSONString(), paramTypes[j]);
//                    } else if (ij instanceof JSONArray) {
//                        objectsArr[i][j] = JSONObject.parseArray(((JSONArray) ij).toJSONString(), paramTypes[j]);
//                    } else {
//                        objectsArr[i][j] = ij;
//                    }
//                }
//            }
//        }
//        return objectsArr;
//    }
//}
