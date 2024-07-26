package com.example.wallet;

import com.example.wallet.models.City;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    public List<City> parse(InputStream is) {
        List<City> cities = new ArrayList<>();
        XmlPullParserFactory factory;

        try {
            factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(is, null);

            int eventType = parser.getEventType();
            City currentCity = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName;
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();
                        if ("city".equals(tagName)) {
                            currentCity = new City(0, 0, 0, "");
                        } else if (currentCity != null) {
                            if ("city_id".equals(tagName)) {
                                currentCity.cityId = Integer.parseInt(parser.nextText());
                            } else if ("country_id".equals(tagName)) {
                                currentCity.countryId = Integer.parseInt(parser.nextText());
                            } else if ("region_id".equals(tagName)) {
                                currentCity.regionId = Integer.parseInt(parser.nextText());
                            } else if ("name".equals(tagName)) {
                                currentCity.name = parser.nextText();
                            }
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        tagName = parser.getName();
                        if ("city".equals(tagName) && currentCity != null) {
                            cities.add(currentCity);
                        }
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return cities;
    }
}
