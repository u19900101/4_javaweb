package ppppp.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lppppp
 * @create 2021-01-30 22:10
 */
// 全局异常的处理
@ControllerAdvice
public class ExceptionConfig {
    //升级版 带回异常信息
    @ExceptionHandler(ArithmeticException.class)
    public ModelAndView e2_handler(Exception e){
        ModelAndView view = new ModelAndView("forward:/pages/error/errorMath.jsp");
        view.addObject("msg",e);
        return view;
    }

    //升级版 带回异常信息
    @ExceptionHandler(Exception.class)
    public ModelAndView e_handler(Exception e){
        ModelAndView view = new ModelAndView("forward:/pages/error/errorMath.jsp");
        view.addObject("msg",e);
        return view;
    }

}