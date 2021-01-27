package utils;

import config.TxConfig;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pojo.User;
import service.BookService;
import service.impl.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lppppp
 * @create 2021-01-04 8:38
 */
public class WebUtils {

    public static <T>T copyBean(Map map, T t){

        try {
            BeanUtils.populate(t,map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(t);
        return t;
    }

    public static <T>T getBean(Class<T> clazz){

        ApplicationContext context = new AnnotationConfigApplicationContext(TxConfig.class);
        System.out.println(context.getBean(clazz));
        return context.getBean(clazz);
    }

    @Test
    public void T(){
        BookService bean = getBean(BookService.class);
        System.out.println(bean);
    }


    public static int parseInt(String s, int defaultValue) {
        try {
            return Integer.parseInt(s);
        }catch (Exception e){
            // e.printStackTrace();
            System.out.println("使用默认值1");
        }
        return defaultValue;
    }
}
