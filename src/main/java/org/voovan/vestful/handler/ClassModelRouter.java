package org.voovan.vestful.handler;

import org.voovan.http.server.HttpRequest;
import org.voovan.http.server.HttpResponse;
import org.voovan.http.server.HttpRouter;
import org.voovan.tools.TFile;
import org.voovan.tools.json.JSON;
import org.voovan.tools.log.Logger;
import org.voovan.vestful.dto.ClassElement;

import java.io.UnsupportedEncodingException;

/**
 * Restful 类文档处理类
 *
 * @author helyho
 * <p>
 * Vestful Framework.
 * WebSite: https://github.com/helyho/Vestful
 * Licence: Apache v2 License
 */
public class ClassModelRouter implements HttpRouter {
    private ClassElement classElement;
    private String htmlContent;

    /**
     * 构造函数
     * @param classElement ClassElement 对象
     */
    public ClassModelRouter(ClassElement classElement) {
        this.classElement = classElement;
    }

    @Override
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) {
        String methodData = JSON.toJSON(classElement).replace("\"class ", "\"");
        httpResponse.body().write(methodData);
    }

}
