/*
package ppppp.filter;

import utils.DBUtils;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

*/
/**
 * @author lppppp
 * @create 2021-01-09 11:09
 *//*

public class TransactionFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(req, resp);
            DBUtils.commitAndcloseResource();
        } catch (ServletException e) {
            e.printStackTrace();
            DBUtils.rollbackAndcloseResource();
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
*/
