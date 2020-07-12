/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thorm.Weather;

import java.util.Date;
import gamerobot.GameRobot;
import gamerobot.GameRobot;
import java.util.HashMap;
import queryweather.QueryWeather;
import static utils.HttpUtil.httpRequest;

/**
 *
 * @author tank
 */
public class GetMessage {

    private static HashMap<String, String> htmlFiter(String html) {
        HashMap<String, String> map = new HashMap();
        String thehour = "";
        String daydata[] = html.split("id=\"hidden_title\" value=\"");
        String daydata1[] = daydata[1].split("\" />");
        String thedaydata[] = daydata1[0].split(" ");
        for (int i = 0; i < thedaydata.length; i++) {
            if (i == 2) {
                map.put("天气", thedaydata[3]);
            }
            if (i == 3) {
                map.put("天温度", thedaydata[5]);
            }
        }

        int hour = new Date().getHours();
        if (hour < 10) {
            thehour = "0" + String.valueOf(hour);
        } else {
            thehour = String.valueOf(hour);
        }
        String timedata[] = html.split("\"od2\":\\[\\{");
        String timedata1[] = timedata[1].split("\\}]");
        String timedata2[] = timedata1[0].split("\\},\\{");
        String timedata4[];
        String timedata3[];
        boolean flag = false;
        boolean theflag = false;
        for (int i = 0; i < timedata2.length - 1; i++) {

            timedata2[i] = timedata2[i].replaceAll("\"", "");
            timedata3 = timedata2[i].split(",");
            for (int z = 0; z < timedata3.length; z++) {
                timedata4 = timedata3[z].split(":");
                if (z == 0) {
                    if (timedata4[1].equals(thehour)) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                }
                if (z == 1 && flag) {
                    map.put("时温度", timedata4[1]);
                }
                if (z == 3 && flag) {
                    map.put("风向", timedata4[1]);
                }
                if (z == 4 && flag) {
                    map.put("风级", timedata4[1]);
                }
                if (z == 5 && flag) {
                    map.put("降水", timedata4[1]);
                }
                if (z == 6 && flag) {
                    map.put("湿度", timedata4[1]);
                }
                if (z == 7) {
                    if (theflag) {

                        if (timedata4.length == 1) {
                            map.put("空气", "");
                        } else {
                            map.put("空气", timedata4[1]);
                        }
                        theflag = false;
                    }
                    if (flag) {
                        if (timedata4.length == 1) {
                            map.put("空气", "");
                        }
                        theflag = true;
                    }
                }

            }
        }

        return map;
    }

    public static String getTodayTemperatureInfo(String cityname) throws Exception {
        // 调用第一个方法，获取html字符串
        String result;
        if (QueryWeather.code.GetCode(cityname) != null) {
            String html = httpRequest("http://www.weather.com.cn/html/weather/" + QueryWeather.code.GetCode(cityname) + ".shtml");
            HashMap<String, String> map = htmlFiter(html);
            result = "           " + cityname + "   " + "\n" + map.get("天气") + " " + map.get("天温度") + "\n" + "温度：" + map.get("时温度") + "\n" + "风向:" + map.get("风向") + map.get("风级") + "级" + "\n" + "降水量：" + map.get("降水") + "\n" + "空气湿度：" + map.get("湿度") + "\n" + "空气质量：" + map.get("空气");
        } else {
            result = "错误，没有这个城市";
        }

        return result;
    }

}
