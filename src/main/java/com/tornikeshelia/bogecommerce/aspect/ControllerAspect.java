package com.tornikeshelia.bogecommerce.aspect;

import com.tornikeshelia.bogecommerce.generator.TimeFunctions;
import com.tornikeshelia.bogecommerce.model.persistence.entity.DailyReport;
import com.tornikeshelia.bogecommerce.model.persistence.entity.WebsiteUserAction;
import com.tornikeshelia.bogecommerce.model.persistence.repository.DailyReportRepository;
import com.tornikeshelia.bogecommerce.model.persistence.repository.WebsiteUserActionRepository;
import com.tornikeshelia.bogecommerce.security.model.bean.checkuser.CheckUserAuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
@Slf4j
public class ControllerAspect {

    @Autowired
    private DailyReportRepository dailyReportRepository;

    @Autowired
    private WebsiteUserActionRepository websiteUserActionRepository;

    /**
     *
     * Triggers on any request of Controller package ( does nothing for security-s controller )
     * Logs basic info
     *
     * **/
    @Before("execution(* com.tornikeshelia.bogecommerce.controller.*.*(..))")
    public void preLogger(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("================Received Http Request================");
        log.info("HTTP METHOD = {}", request.getMethod());
        log.info("URI = {}", request.getRequestURI());
        log.info("QUERY = {}", request.getQueryString());
        log.info("CLASS_METHOD = {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("ARGS = {}", Arrays.toString(joinPoint.getArgs()));
        log.info("REQUESTER IP = {}", request.getRemoteAddr());
    }

    /**
     *
     * Triggers on getALL method (Which is basically main page of the application
     * Gets daily report and updates total visits on page (if daily report is NULL -> does nothing)
     *
     * **/
    @Before("execution(* com.tornikeshelia.bogecommerce.controller.ProductsController.getAll())")
    public void totalUserCount() {

        Date today = new Date();
        DailyReport dailyReport = dailyReportRepository.getDailyReportByDateRange(TimeFunctions.getStartOfDay(today), TimeFunctions.getEndOfDay(today));
        if (dailyReport != null) {
            dailyReport.setTotalVisitsOnWebPage(dailyReport.getTotalVisitsOnWebPage() + 1);
            dailyReportRepository.save(dailyReport);
            log.info("Updated total visits on page");
        }
    }

    /**
     * Triggers when user successfully logs in.
     * Checks if user already logged in today :
     *      if - NO -> and dailyReport object is not NULL , updates daily report for uniqueAuthorizedUsers++
     *      if - YES -> does nothing
     * **/
    @AfterReturning(
            value = "execution(* com.tornikeshelia.bogecommerce.security.controller.AuthController.authenticateUser(..))",
            returning = "checkUserAuthResponse")
    public void totalUniqueUsersLoggedIn(CheckUserAuthResponse checkUserAuthResponse) {

        Date today = new Date();
        WebsiteUserAction websiteUserAction = websiteUserActionRepository.getByDateRangeByAuthorizedAndEmail(TimeFunctions.getStartOfDay(today),TimeFunctions.getEndOfDay(today),1L,checkUserAuthResponse.getEmail());
        DailyReport dailyReport = dailyReportRepository.getDailyReportByDateRange(TimeFunctions.getStartOfDay(today), TimeFunctions.getEndOfDay(today));
        if (websiteUserAction == null && dailyReport != null){
            websiteUserAction = new WebsiteUserAction(checkUserAuthResponse.getEmail(), 1L);
            dailyReport.setTotalUniqueAuthorizedUsers(dailyReport.getTotalUniqueAuthorizedUsers()+1);
            websiteUserActionRepository.save(websiteUserAction);
            dailyReportRepository.save(dailyReport);
            log.info("Updated total unique authorized visits on page");
        }

    }

}
