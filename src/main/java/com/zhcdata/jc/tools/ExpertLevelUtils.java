package com.zhcdata.jc.tools;


import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description 专家等级计算
 * @Author cuishuai
 * @Date 2019/8/16 16:59
 */
@Component
public class ExpertLevelUtils {
    public  String expertLeve(Integer experience){
        String leve = "0";
        if(experience > 0 && experience < 15){
             leve = "1";
        }
        if(experience >= 15 && experience < 30){
            leve = "2";
        }
        if(experience >= 30 && experience < 45){
            leve = "3";
        }
        if(experience >= 45 && experience < 60){
            leve = "4";
        }
        if(experience >= 60 && experience < 75){
            leve = "5";
        }
        if(experience >= 75 && experience < 90){
            leve = "6";
        }
        if(experience >= 90 && experience < 105){
            leve = "7";
        }
        if(experience >= 105 && experience < 120){
            leve = "8";
        }
        if(experience >= 120 && experience < 135){
            leve = "9";
        }
        if(experience >= 135 && experience < 150){
            leve = "10";
        }
        if(experience >= 150 && experience < 165){
            leve = "11";
        }
        if(experience >= 165 && experience < 180){
            leve = "12";
        }
        if(experience >= 180 && experience < 195){
            leve = "13";
        }
        if(experience >= 195 && experience < 210){
            leve = "14";
        }
        if(experience >= 210 && experience < 225){
            leve = "15";
        }
        if(experience >= 225 && experience < 240){
            leve = "16";
        }
        if(experience >= 240 && experience < 255){
            leve = "17";
        }
        if(experience >= 255 && experience < 270){
            leve = "18";
        }
        if(experience >= 270 && experience < 285){
            leve = "19";
        }
        if(experience >= 285 && experience < 300){
            leve = "20";
        }
        if(experience >= 300 && experience < 315){
            leve = "21";
        }
        if(experience >= 315 && experience < 330){
            leve = "22";
        }
        if(experience >= 330 && experience < 345){
            leve = "23";
        }
        if(experience >= 345 && experience < 360){
            leve = "24";
        }
        if(experience >= 360 && experience < 375){
            leve = "25";
        }
        if(experience >= 375 && experience < 390){
            leve = "26";
        }
        if(experience >= 390 && experience < 405){
            leve = "27";
        }
        if(experience >= 405 && experience < 420){
            leve = "28";
        }
        if(experience >= 420 && experience < 435){
            leve = "29";
        }
        if(experience >= 435 && experience < 450){
            leve = "30";
        }
        if(experience >= 450 && experience < 500){
            leve = "31";
        }
        if(experience >= 500 && experience < 550){
            leve = "32";
        }
        if(experience >= 550 && experience < 600){
            leve = "33";
        }
        if(experience >= 600 && experience < 650){
            leve = "34";
        }
        if(experience >= 650 && experience < 750){
            leve = "35";
        }
        if(experience >= 750 && experience < 850){
            leve = "36";
        }
        if(experience >= 850 && experience < 950){
            leve = "37";
        }
        if(experience >= 950 && experience < 1050){
            leve = "38";
        }
        if(experience >= 1015 && experience < 1150){
            leve = "39";
        }
        if(experience >= 1150 && experience < 1350){
            leve = "40";
        }
        if(experience >= 1350 && experience < 1550){
            leve = "41";
        }
        if(experience >= 1550 && experience < 1750){
            leve = "42";
        }
        if(experience >= 1750 && experience < 1950){
            leve = "43";
        }
        if(experience >= 1950 && experience < 2150){
            leve = "44";
        }
        if(experience >= 2150 && experience < 2650){
            leve = "45";
        }
        if(experience >= 2650 && experience < 3150){
            leve = "46";
        }
        if(experience >= 3150 && experience < 3650){
            leve = "47";
        }
        if(experience >= 3650 && experience < 4150){
            leve = "48";
        }
        if(experience >= 4150 && experience < 4650){
            leve = "49";
        }
        if(experience >= 4650 && experience < 5450){
            leve = "50";
        }
        if(experience >= 5450 && experience < 6250){
            leve = "51";
        }
        if(experience >= 6250 && experience < 7050){
            leve = "52";
        }
        if(experience >= 7050 && experience < 7850){
            leve = "53";
        }
        if(experience >= 7850 && experience < 8650){
            leve = "54";
        }
        if(experience >= 8650 && experience < 9450){
            leve = "55";
        }
        if(experience >= 9450 && experience < 10250){
            leve = "56";
        }
        if(experience >= 10250 && experience < 11050){
            leve = "57";
        }
        if(experience >= 11050 && experience < 11850){
            leve = "58";
        }
        if(experience >= 11850 && experience < 12650){
            leve = "59";
        }
        if(experience >= 12650 && experience < 13650){
            leve = "60";
        }
        if(experience >= 13650 && experience < 14650){
            leve = "61";
        }
        if(experience >= 14650 && experience < 15650){
            leve = "62";
        }
        if(experience >= 15650 && experience < 16650){
            leve = "63";
        }
        if(experience >= 16650 && experience < 17650){
            leve = "64";
        }
        if(experience >= 17650 && experience < 18650){
            leve = "65";
        }
        if(experience >= 18650 && experience < 19650){
            leve = "66";
        }
        if(experience >= 19650 && experience < 20650){
            leve = "67";
        }
        if(experience >= 20650 && experience < 21650){
            leve = "68";
        }
        if(experience >= 21650 && experience < 22650){
            leve = "69";
        }
        if(experience >= 22650 && experience < 24150){
            leve = "70";
        }
        if(experience >= 24150 && experience < 25650){
            leve = "71";
        }
        if(experience >= 25650 && experience < 27150){
            leve = "72";
        }
        if(experience >= 27150 && experience < 28650){
            leve = "73";
        }
        if(experience >= 28650 && experience < 30150){
            leve = "74";
        }
        if(experience >= 30150 && experience < 31650){
            leve = "75";
        }
        if(experience >= 31650 && experience < 33150){
            leve = "76";
        }
        if(experience >= 33150 && experience < 34650){
            leve = "77";
        }
        if(experience >= 34650 && experience < 36150){
            leve = "78";
        }
        if(experience >= 36150 && experience < 37650){
            leve = "79";
        }
        if(experience >= 37650 && experience < 100000){
            leve = "80";
        }
        if(experience >= 100000){
            leve = "81";
        }
        return leve;
    }


}
