package org.voovan.vestful.entity;

import org.voovan.tools.ObjectPool;
import org.voovan.tools.json.JSON;
import org.voovan.tools.log.Logger;
import org.voovan.tools.reflect.TReflect;
import org.voovan.vestful.VestfulGlobal;
import org.voovan.vestful.annotation.Param;
import org.voovan.vestful.annotation.Restful;
import org.voovan.vestful.exception.RestfulException;

/**
 * 类文字命名
 *
 * @author helyho
 * <p>
 * Vestful Framework.
 * WebSite: https://github.com/helyho/Vestful
 * Licence: Apache v2 License
 */
public class DirectObject {

    private static ObjectPool objectPool = VestfulGlobal.getObjectPool();

    /**
     * 构造一个对象
     * @param className 对象字符串描述
     * @param params 参数
     * @return 返回新建的对象
     * @throws ReflectiveOperationException
     */
    @Restful( method="GET", desc="Create new object in server side.")
    public static int createObject(
            @Param(name="className", desc = "Class full path name.")
            String className,
            @Param(name="params", desc = "Constructor method param")
            Object ...params) throws Exception {
        Object object = TReflect.newInstance(className, params);
        Logger.simple(object.hashCode());
        return objectPool.add(object);
    }

    /**
     * 调用对象池中对象的指定方法
     * @param pooledObjectId 对象在对象池中的 ID
     * @param methodName  方法名
     * @param params 参数集合
     * @return 方法返回值
     */
    @Restful( method="GET", desc="Invoke Object method.")
    public static String invoke(
            @Param(name="pooledObjectId", desc="Object id in ObjectPool.")
            int pooledObjectId,
            @Param(name="methodName", desc="name of which method you want to invoke.")
            String methodName,
            @Param(name="params", desc="Method invoke params.")
            Object ...params) throws Exception {
        Object obj = objectPool.get(pooledObjectId);
        if(obj==null){
            throw new RestfulException("Object not found, Object id: " + pooledObjectId);
        }
        Object result = TReflect.invokeMethod(obj,methodName,params);
        return JSON.toJSON(result);

    }

    /**
     * 从对象池中指定的对象
     * @param pooledObjectId 对象在对象池中的 ID
     * @return 方法返回值
     */
    @Restful( method="GET", desc="Invoke Object method.")
    public static void release(
            @Param(name="pooledObjectId", desc="Object id in ObjectPool.")
                    int pooledObjectId
          ) throws Exception {
        objectPool.remove(pooledObjectId);
    }

    @Restful( method="GET", desc="Get script of browser invoke server side object .")
    public static String genScript(String className, int clazzPoolId){
        return "";
    }
}
