package com.example.hw06;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Rama Vamshi Krishna on 10/14/2016.
 */
public class WeatherItemsUtil {
    static public class ParseWeatherItems{
        static public ArrayList<WeatherItems> doXmlPullParsing(InputStream in) throws XmlPullParserException, IOException, ParseException {
            ArrayList<WeatherItems> weatheritemslist=new ArrayList<WeatherItems>();
            WeatherItems weatheritem =null;
            XmlPullParser parser= XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in,"UTF_8");
            int event=parser.getEventType();
            while(event!=XmlPullParser.END_DOCUMENT){
                switch(event) {
                    case(XmlPullParser.START_TAG):

                        if(parser.getName().equals("time")) {
                            weatheritem = new WeatherItems();
                            weatheritem.setTodate(parser.getAttributeValue(null,"to"));
                            weatheritem.setFromdate(parser.getAttributeValue(null,"from"));

                        }
                        if(parser.getName().equals("symbol") && weatheritem!=null ){
                            weatheritem.setSymbolforimage(parser.getAttributeValue(null,"var"));
                        }
                        else if(parser.getName().equals("windDirection") && weatheritem!=null){
                            weatheritem.setWinddirection(parser.getAttributeValue(null,"code"));
                            weatheritem.setWinddegrees(parser.getAttributeValue(null,"deg"));

                        }
                        else if(parser.getName().equals("windSpeed") && weatheritem!=null){
                            weatheritem.setWindSpeed(parser.getAttributeValue(null,"mps"));
                        }
                        else if(parser.getName().equals("temperature") && weatheritem!=null){
                            weatheritem.setTemperature(parser.getAttributeValue(null,"value"));
                        }
                        else if(parser.getName().equals("pressure") && weatheritem!=null){
                            weatheritem.setPressure(parser.getAttributeValue(null,"value"));
                            weatheritem.setPunit(parser.getAttributeValue(null,"unit"));

                        }
                        else if(parser.getName().equals("humidity") && weatheritem!=null){
                            weatheritem.setHumidity(parser.getAttributeValue(null,"value"));
                            weatheritem.setHunit(parser.getAttributeValue(null,"unit"));


                        }
                        else if(parser.getName().equals("clouds") && weatheritem!=null){
                            weatheritem.setClouds(parser.getAttributeValue(null,"value"));

                        }

                        break;
                    case (XmlPullParser.END_TAG):
                        if(parser.getName().equals("time")) {
                            weatheritemslist.add(weatheritem);
                            weatheritem = null;
                            break;
                        }
                    default:
                        break;

                }
                event=parser.next();

            }


            return weatheritemslist;
        }
    }
}



