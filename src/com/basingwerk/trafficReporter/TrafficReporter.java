package com.basingwerk.trafficReporter;

import java.io.*;
import java.util.ArrayList;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

@SuppressWarnings("unused")

public class TrafficReporter extends DefaultHandler {
    static private Writer out;

    public ReportItem reportItem = null;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: cmd xmlfilename");
            System.exit(1);
        }

        try {
            out = new OutputStreamWriter(System.out, "UTF8");
            DefaultHandler handler = new TrafficReporter();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(new File(args[0]), handler);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public void startElement(String namespaceURI, String sName, String qName, Attributes attrs) throws SAXException {
        String eName = sName;
        if ("".equals(eName)) {
            eName = qName;
        }

        if (eName.equals("packet")) {
            reportItem = new ReportItem();
        }

        if (attrs != null) {
            for (int i = 0; i < attrs.getLength(); i++) {
                String aName = attrs.getLocalName(i);
                if ("".equals(aName))
                    aName = attrs.getQName(i);
                if (aName.equals("name")) {
                    String theName = attrs.getValue(i);
                    String tmp = null;

                    if (theName.equals("ip.src")) {
                        tmp = attrs.getValue("show");
                        reportItem.setSourceIp(tmp);
                    }
                    if (theName.equals("ip.dst")) {
                        tmp = attrs.getValue("show");
                        reportItem.setDestIp(tmp);
                    }

                    if (theName.equals("tcp")) {
                        tmp = attrs.getValue("show");
                        reportItem.setTcpOrUdp(ReportItem.protoType.TCP);
                    }
                    if (theName.equals("udp")) {
                        tmp = attrs.getValue("show");
                        reportItem.setTcpOrUdp(ReportItem.protoType.UDP);
                    }
                    if (reportItem.getTcpOrUdp() == ReportItem.protoType.TCP) {
                        if (theName.equals("tcp.srcport")) {
                            tmp = attrs.getValue("show");
                            reportItem.setSourcePort(tmp);
                        }
                        if (theName.equals("tcp.dstport")) {
                            tmp = attrs.getValue("show");
                            reportItem.setDestPort(tmp);
                        }
                    }

                    if (reportItem.getTcpOrUdp() == ReportItem.protoType.UDP) {
                        if (theName.equals("udp.srcport")) {
                            tmp = attrs.getValue("show");
                            reportItem.setSourcePort(tmp);
                        }
                        if (theName.equals("udp.dstport")) {
                            tmp = attrs.getValue("show");
                            reportItem.setDestPort(tmp);
                        }
                    }
                }
            }
        }
    }

    public void endElement(String namespaceURI, String sName, String qName) throws SAXException {
        String eName = sName;
        if ("".equals(eName)) {
            eName = qName;
        }
        if (eName.equals("packet")) {
            if (reportItem.isComplete()) {
                System.out.println(reportItem);
                reportItem = null;
            } else {
                System.out.println("Report item was incomplete: " + reportItem);
            }
        }
    }

    public void characters(char buf[], int offset, int len) throws SAXException {
        String s = new String(buf, offset, len);
    }

    public void startDocument() throws SAXException {
    }

    public void endDocument() throws SAXException {
        try {
            out.flush();
        } catch (IOException e) {
            throw new SAXException("I/O error", e);
        }
    }
}
