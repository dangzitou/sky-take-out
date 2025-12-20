package com.sky.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Component
@Slf4j
public class BaiduMapUtil {

    @Value("${sky.baidu.ak}")
    private String ak;

    @Value("${sky.shop.address}")
    private String shopAddress;

    private static final String GEOCODING_URL = "https://api.map.baidu.com/geocoding/v3/?address=%s&output=json&ak=%s";

    private static final double EARTH_RADIUS = 6378.137;

    /**
     * 计算指定地址与店铺地址的距离（单位：千米）
     * 修复：去掉了 static，以便使用注入的 shopAddress
     */
    public Double calculateDistance(String address) {
        // 1. 获取店铺的经纬度
        Point shopPoint = getCoordinate(shopAddress);
        // 2. 获取用户地址的经纬度
        Point userPoint = getCoordinate(address);

        if (shopPoint == null || userPoint == null) {
            return null;
        }

        // 3. 计算两点距离
        return getDistance(shopPoint.lng, shopPoint.lat, userPoint.lng, userPoint.lat);
    }

    /**
     * 调用百度地图接口，将地址转换为经纬度
     * 修复：使用 HttpURLConnection 替代 Java 11 HttpClient，兼容 Java 8
     */
    private Point getCoordinate(String address) {
        try {
            if (address == null || "".equals(address)) return null;

            String encodedAddress = URLEncoder.encode(address, "UTF-8");
            String urlStr = String.format(GEOCODING_URL, encodedAddress, ak);

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();

            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
            conn.disconnect();

            String jsonStr = sb.toString();
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            log.info("Baidu Map API response: {}", jsonStr);

            if (!"0".equals(jsonObject.getString("status"))) {
                return null;
            }

            JSONObject location = jsonObject.getJSONObject("result").getJSONObject("location");
            return new Point(location.getDouble("lng"), location.getDouble("lat"));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据经纬度计算两点间的距离（单位：千米）
     */
    private double getDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = Math.toRadians(lat1);
        double radLat2 = Math.toRadians(lat2);
        double a = radLat1 - radLat2;
        double b = Math.toRadians(lng1) - Math.toRadians(lng2);

        double s = 2 * Math.asin(Math.sqrt(
                Math.pow(Math.sin(a / 2), 2) +
                        Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)
        ));

        s = s * EARTH_RADIUS;
        return Math.round(s * 100.0) / 100.0;
    }

    private static class Point {
        double lng;
        double lat;

        public Point(double lng, double lat) {
            this.lng = lng;
            this.lat = lat;
        }
    }
}
