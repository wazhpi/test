package com.tiger.xml;

import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class XmlHandler {
	private static final SAXParserFactory spf = SAXParserFactory.newInstance();

	public DefaultHandler XMLParse(String data, DefaultHandler handler) {
		Class clazz = handler.getClass();
		try {
			handler = (DefaultHandler) clazz.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader reader = sp.getXMLReader();
			reader.setContentHandler(handler);
			InputSource inputSource = new InputSource(new StringReader(data));
			reader.parse(inputSource);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return handler;
	}

}
