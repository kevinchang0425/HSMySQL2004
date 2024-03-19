//Filter原本不支援spring技術
//將spring資源引進給Filter
//之後只要繼承BaseFilter即可

package com.hs.filter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


//@Component
//public class BaseFilter extends HttpFilter {

    //@Override
    //public void init() throws ServletException {
        //SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getFilterConfig().getServletContext());
    //}
    
//}
