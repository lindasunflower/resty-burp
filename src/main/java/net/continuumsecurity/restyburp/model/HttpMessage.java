/*******************************************************************************
 * BDD-Security, application security testing framework
 * 
 * Copyright (C) `2012 Stephen de Vries`
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see `<http://www.gnu.org/licenses/>`.
 ******************************************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.continuumsecurity.restyburp.model;

import burp.IHttpRequestResponse;
import burp.IHttpService;

import javax.xml.bind.annotation.XmlRootElement;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

/**
 *
 * @author stephen
 */
@XmlRootElement
public class HttpMessage {
    private String host;
    private int port;
    private String protocol;
    private byte[] request;
    private byte[] response;
    private short statusCode;
    private String comment;
    private String highlight;
    private URL url;
    private IHttpService httpService;

    public HttpMessage() {}
    
    public HttpMessage(HttpMessage msg) {
    	host = msg.getHost();
    	port = msg.getPort();
    	protocol = msg.getProtocol();
    	request = Arrays.copyOf(msg.getRequest(),msg.getRequest().length);
    	response = Arrays.copyOf(msg.getResponse(),msg.getResponse().length);
    	statusCode = msg.statusCode;
    	comment = msg.getComment();
    	highlight = msg.highlight;
    	url = msg.getUrl();
    	httpService = msg.httpService;
    }
    
    public HttpMessage(IHttpRequestResponse ihrr) {
        host = ihrr.getHost();
        port = ihrr.getPort();

        try {
            protocol = ihrr.getProtocol();
            request = ihrr.getRequest();
            response = ihrr.getResponse();
            statusCode = ihrr.getStatusCode();
            url = ihrr.getUrl();
            comment = ihrr.getComment();
            highlight = ihrr.getHighlight();
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String getResponseAsString() {
        return new String(getResponse());
    }

    public String getRequestAsString() {
        return new String(getRequest());
    }

    private String extractBody(String content) {
        return content.substring(content.indexOf("\r\n\r\n"),content.length());
    }

    public String getResponseBody() {
        return extractBody(new String(getResponse()));
    }

    public String getRequestBody() {
        return extractBody(new String(getRequest()));
    }

    public String getHost() {
        return host;
    }

    public void replaceCookies(Map<String,String> cookies) {
        String content = new String(getRequest());
        for (String key : cookies.keySet()) {
            content = content.replaceAll(key+"=.+?;",key+"="+cookies.get(key)+";");
            content = content.replaceAll(key+"=[^;]+?\\r\\n",key+"="+cookies.get(key)+"\r\n");
        }
        request = content.getBytes();
    }

    /**
     * Set the value of host
     *
     * @param host new value of host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * @param protocol the protocol to set
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * @return the request
     */
    public byte[] getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(byte[] request) {
        this.request = request;
    }

    /**
     * @return the response
     */
    public byte[] getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(byte[] response) {
        this.response = response;
    }

    /**
     * @return the statusCode
     */
    public short getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(short statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return the url
     */
    public URL getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(URL url) {
        this.url = url;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

}
