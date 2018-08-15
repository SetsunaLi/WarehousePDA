package com.example.ridko.warehousepda.client;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientService {

    private static Map<Class,Object> beanContext = new ConcurrentHashMap<>();
    private static ApplicationConfig applicationConfig;
    private static RegistryConfig registry;

    static {
        registry = new RegistryConfig();
        registry.setAddress("zookeeper://192.168.1.16:2181");

        applicationConfig = new ApplicationConfig();
        applicationConfig.setName("inventory-app");
        applicationConfig.setRegistry(registry);
    }

    public static <T> T getBean(Class<T> classzz) {
        //noinspection unchecked
        if(!beanContext.containsKey(classzz)){
            synchronized (ClientService.class){
                ReferenceConfig<T> reference = new ReferenceConfig<T>();
                reference.setApplication(applicationConfig);
                reference.setInterface(classzz);
                T instance = reference.get();
                beanContext.put(classzz,instance);
                return instance;
            }
        }else{
            //noinspection unchecked
            return (T) beanContext.get(classzz);
        }
    }
}
