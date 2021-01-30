package ppppp.web;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ppppp.g_dao.CartMapper;
import ppppp.pojo.Cart;
import ppppp.pojo.User;
import ppppp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author lppppp
 * @create 2021-01-03 20:11
 */
@Controller
@RequestMapping("/client/userServlet")
public class UserServlet{
    @Autowired
    UserService userService;
    private void ajaxexistUsername(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String username = req.getParameter("username");
        boolean existUsername = userService.existUsername(username);

        HashMap<String, Boolean> map = new HashMap<>();
        map.put("existUsername", existUsername);
        res.getWriter().write( new Gson().toJson(map));

    }
    // @RequestMapping("/register")
  /*
    private void register(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println("come into do register ...");
        // 封装User

    */
/*  String username = req.getParameter("username");
    String password = req.getParameter("password");
    String email = req.getParameter("email");
   */  /*

        Map<String, String[]> parameterMap = req.getParameterMap();
        User user = WebUtils.copyBean(parameterMap,new User("kk11112", "kk1"));


        String repwd = req.getParameter("repwd");
        String code = req.getParameter("code");
        req.setAttribute("username",user.getUsername());
        req.setAttribute("password",user.getPassword());
        req.setAttribute("repwd",repwd);
        req.setAttribute("email",user.getEmail());
        req.setAttribute("code",code);

          */
/*防止用户重复提交*/  /*

        //获取session
        // String token = (String)req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        // // 将session中的该值置为null
        // // req.getSession().setAttribute(KAPTCHA_SESSION_KEY,null);
        // req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);
        //比较验证码
        boolean existUsername = userService.existUsername(user.getUsername());
        // if(token!=null && token.equalsIgnoreCase(code)){
            if(existUsername){
                // 用户名已存在
                req.setAttribute("msg","用户名已存在");
                req.getRequestDispatcher("/pages/user/register.jsp").forward(req,res);
            }else {
                //注册成功
                userService.register(user);
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req,res);
            }
        // }else {
        //     req.setAttribute("msg","验证码有误，请重新输入");
        //     req.getRequestDispatcher("/pages/user/register.jsp").forward(req,res);
        // }

    }
  */
    @RequestMapping("/logout")
    private String logout(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getSession().removeAttribute("user");
        //还要清空购物车session
        req.getSession().removeAttribute("cart");
        req.getSession().removeAttribute("totalCount");
        return "forward:/pages/client/index.jsp";
    }

    @Autowired
    CartMapper cartMapper;
    @RequestMapping("/login")
    private String login(HttpServletRequest req, User user) throws ServletException, IOException {
        System.out.println("come into login");
        User login = userService.login(user);
        if(login!=null){
            // login succeed
            req.setAttribute("msg","login succeed");
            req.getSession().setAttribute("user",login);
            req.getSession().setMaxInactiveInterval(60*60*24*7);

            Integer cartId = login.getId();
            Cart cart = cartMapper.selectByPrimaryKey(cartId);
            // 购物车中有货的话 一进入就能看到
            if(cart!=null){
                req.getSession().setAttribute("cart",cart);
                // 为了第一次能在页面显示 购物车中的数量信息
                req.getSession().setAttribute("totalCount",cart.getCount());
            }

            return "forward:/pages/user/login_success.jsp";
        }else {
            // failed
            req.setAttribute("msg","用户名或密码不存在");
            req.setAttribute("username", URLEncoder.encode(user.getUsername(), "UTF-8"));

            req.setAttribute("password",user.getPassword());
            return "forward:/pages/user/login.jsp";
        }
    }

}

