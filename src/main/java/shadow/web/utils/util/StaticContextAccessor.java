package shadow.web.utils.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StaticContextAccessor {

    private static StaticContextAccessor instance;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void registerInstance() {
        instance = this;
    }

    public static Object getBean(String name) {
        return instance.applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return instance.applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return instance.applicationContext.getBean(name, clazz);
    }
    
    public static Object getBean(String name, Object ... args) {
        return instance.applicationContext.getBean(name, args);
    }
}