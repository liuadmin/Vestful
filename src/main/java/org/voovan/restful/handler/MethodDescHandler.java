package org.voovan.restful.handler;

import org.voovan.http.server.HttpBizHandler;
import org.voovan.http.server.HttpRequest;
import org.voovan.http.server.HttpResponse;
import org.voovan.restful.dto.MethodElement;
import org.voovan.tools.TFile;
import org.voovan.tools.json.JSON;

import java.io.UnsupportedEncodingException;

/**
 * Restful 方法文档处理类
 *
 * @author helyho
 * <p>
 * Vestful Framework.
 * WebSite: https://github.com/helyho/Vestful
 * Licence: Apache v2 License
 */
public class MethodDescHandler implements HttpBizHandler {
    private MethodElement methodElement;
    private String htmlContent;

    /**
     * 构造函数
     *
     * @param methodElement methodElement 对象
     */
    public MethodDescHandler(MethodElement methodElement) throws UnsupportedEncodingException {
        this.methodElement = methodElement;
        this.htmlContent = new String(TFile.loadResource("MethodDesc.html"),"UTF-8");
    }

    @Override
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) {
        String methodData = JSON.toJSON(methodElement).replace("\"class ", "\"");
        htmlContent =  htmlContent.replaceAll(":methodData",methodData);
        httpResponse.body().write(htmlContent);
    }

}
