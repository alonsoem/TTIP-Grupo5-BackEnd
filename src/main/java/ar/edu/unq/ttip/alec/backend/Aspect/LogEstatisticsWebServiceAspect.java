package ar.edu.unq.ttip.alec.backend.Aspect;


import ar.edu.unq.ttip.alec.backend.service.StatisticsService;
import ar.edu.unq.ttip.alec.backend.service.dtos.CalculationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Aspect
@Component
@Order(1)
public class LogEstatisticsWebServiceAspect {

	@Autowired
	private StatisticsService service;

	@Around("execution(* ar.edu.unq.ttip.alec.backend.webservices.BrokerController.calculate(..)) && args(request)")

	public Object logWebServiceAspect(ProceedingJoinPoint joinPoint, CalculationDTO request) throws Throwable {
		Object proceed = joinPoint.proceed();
		service.update(request.getTaxId());
		return proceed;
	}
}