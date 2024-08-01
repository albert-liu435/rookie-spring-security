//package com.rookie.bigdata.filter;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletRequestWrapper;
//import org.apache.commons.io.IOUtils;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Class RequestBodyServletRequestWrapper
// * @Description
// * @Author rookie
// * @Date 2024/8/1 10:22
// * @Version 1.0
// */
//public class RequestBodyServletRequestWrapper extends HttpServletRequestWrapper {
//
//    /**
//     * 请求体数据
//     */
//    private final byte[] requestBody;
//
//    /**
//     * 重新参数Map
//     */
//    private final Map<String, String[]> paramMap;
//
//    /**
//     * Constructs a request object wrapping the given request.
//     *
//     * @param request The request to wrap
//     * @throws IllegalArgumentException if the request is null
//     */
//    public RequestBodyServletRequestWrapper(HttpServletRequest request) throws IOException {
//        super(request);
//
//        //重写requestBody
//        requestBody = IOUtils.toByteArray(request.getReader(), StandardCharsets.UTF_8);
//
//        //重写参数Map
//        paramMap = new HashMap<>();
//
//        if (requestBody.length == 0) {
//            return;
//        }
//        getParamMap();
//
////            JSON.parseObject(getRequestBody()).forEach((key, value) -> paramMap.put(key, new String[]{String.valueOf(value)}));
//
//
//    }
//
//    public String getRequestBody() {
//        return new String(requestBody, StandardCharsets.UTF_8);
//    }
//
//    @Override
//    public Map<String, String[]> getParamMap() {
//        return paramMap;
//    }
//}
