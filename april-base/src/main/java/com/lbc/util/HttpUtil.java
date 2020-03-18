//package com.lbc.util;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author lbc
// * @classname HttpUtil
// * @date 11:37 AM
// */
//public class HttpUtil {
//
//    public static String get(String url) throws Exception {
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()){
//                throw new Exception("调用异常");
//            }
//            String result = response.body().string();
//            return result;
//        }
//    }
//    public static void main(String[] args) throws Exception {
//        int total = 200000;
//        int pagesize = 20;
//        int totalPage;
//        if (total%pagesize == 0 ){
//            totalPage = total/pagesize;
//        }else{
//            totalPage = total/pagesize+1;
//        }
//
//
//        for (int currpage=4000;currpage<=totalPage;currpage++){
//            Thread.sleep(10);
//            try {
//                System.out.println("currpage:"+currpage);
//                String list = get("http://replay.qima-inc.com/api/inconsistency/list.json?appName=trade-detail&pageSize="+String.valueOf(pagesize)+"&page="+String.valueOf(currpage));
//                JSONObject objlist = (JSONObject) JSON.parse(list);
//                if (objlist==null){
//                    System.out.println("skip currpage:"+currpage);
//                    continue;
//                }
//                if (objlist.getJSONObject("data")==null){
//                    System.out.println("skip currpage:"+currpage);
//                    continue;
//                }
//                objlist.getJSONObject("data").getJSONArray("list").forEach(x->{
//                    String id = ((JSONObject) x).get("id").toString();
//                    String methodName = ((JSONObject) x).get("methodName").toString();
//                    String serviceName = ((JSONObject) x).get("serviceName").toString();
//
//                    try {
//                        String detail = get("http://replay.qima-inc.com/api/inconsistency/detail.json?id="+id);
//                        JSONObject objdetail = (JSONObject) JSON.parse(detail);
//                        Map map = new HashMap();
//                        JSONObject params = (JSONObject) objdetail.getJSONObject("data").getJSONArray("args").get(0);
////                    String paramClass = params.remove("class").toString();
//                        if (params.containsKey("kdtId")) {
//                            map.put("kdtId", Long.valueOf(params.getString("kdtId")));
//                        }
//                        ArrayList orderNoList = new ArrayList<String>();
//                        if (params.containsKey("orderNo")){
//                            map.put("orderNo",params.get("orderNo"));
//                        }else if (params.containsKey("orderNos")){
//                            JSONArray jsonArray = (JSONArray)params.get("orderNos");
//                            if (jsonArray != null) {
//                                int len = jsonArray.size();
//                                for (int i=0;i<len;i++){
//                                    orderNoList.add(jsonArray.get(i).toString());
//                                }
//                            }
//                            map.put("orderNos",orderNoList);
//                        }
//
//                        JSONObject newobj = new JSONObject();
//                        newobj.putAll(map);
//
////                    String fileString = serviceName+"|"+methodName+"|"+paramClass+"|"+params;
//
//                        String path = "/Users/lbc/jmx/detailonline.csv";
//
//                        if (serviceName.equals("com.youzan.trade.detail.api.service.OrderInfoService")){
//                            if (methodName.equals("getRealTimeOrder")){
//                                path = "/Users/lbc/jmx/DInfoRealTime.json";
//                            }else if (methodName.equals("getOrders")){
//                                path = "/Users/lbc/jmx/DInfogetOrders.json";
//                            }else if (methodName.equals("get")){
//                                path = "/Users/lbc/jmx/DInfoget.json";
//                            }
//                        }else if(serviceName.equals("com.youzan.trade.detail.api.service.GatewayService")){
//                            if (methodName.equals("get")){
//                                path = "/Users/lbc/jmx/Dgatewayget.json";
//                            }
//                        }else if(serviceName.equals("com.youzan.trade.detail.api.service.OrderDetailService")){
//                            if (methodName.equals("getOrders")){
//                                path = "/Users/lbc/jmx/DdetailGetOrders.json";
//                            }
//                        }
//                        WriterUtil.write(path,newobj.toString()+",");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                });
//            }catch (Exception e){
//                continue;
//            }
//
//        }
//    }
//}
