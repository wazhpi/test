package com.tiger.xml;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.tiger.bean.ListItemBean;

public class ListHandler extends DefaultHandler {

	private String s;

	public List<ListItemBean> list = new ArrayList<ListItemBean>();
	private ListItemBean itemBean;

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		String part = new String(ch, start, length);
		if (!part.equals("\n"))
			if (s != null)
				s += part;
			else
				s = part;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);

		if (localName.equalsIgnoreCase("item")) {
			if (itemBean != null) {
				list.add(itemBean);
			}
			itemBean = new ListItemBean();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		if (s != null)
			s = s.trim();
		if (localName.equalsIgnoreCase("contentid")) {
			itemBean.setContentID(s);
		} else if (localName.equalsIgnoreCase("channelid")) {
			itemBean.setChannelID(s);
		} else if (localName.equalsIgnoreCase("title")) {
			itemBean.setTitle(s);
		} else if (localName.equalsIgnoreCase("imageurl")) {
			itemBean.setImageUrl(s);
		} else if (localName.equalsIgnoreCase("summary")) {
			itemBean.setSummary(s);
		} else if (localName.equalsIgnoreCase("adddate")) {
			itemBean.setAddDate(s);
		}
		s = null;
	}

}
