package com.digitalLab.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Override 
	public void addInterceptors(InterceptorRegistry registry){
		
		registry.addInterceptor(new LoginCheckInterceptor())
		.order(1)// 인터셉터 체인 순서
		.addPathPatterns("/**") // 모든 requestURL에 대해 적용
		.excludePathPatterns("/" // 제외하고 싶은 whitelist
							, "/auth/ssoLogin"
							, "/auth/testLogin"
							, "/css/**"
							, "/js/**"
							, "/images/**"
							, "/*.ico" 
							, "/error"
							, "/api/analysis-schedule"
							, "/api/batch-plate"
							, "/api/analysis-list"
							, "/api/batch/modify"
							, "/api/reagent"
							, "/api/equipment"
							, "/api/method"
							, "/api/sample_analysis"
							, "/api/reagent/list"
							, "/api/method/list"
							, "/api/equipment/list"
							, "/api/analysis-plan"
							, "/api/analysis-detail"
							, "/api/method/count"
							, "/api/reagent/count"
							, "/api/equipment/count"
							, "/analysis/sample_analysis_modify"
							, "/sendRepository"
							, "/sendNote"
							, "/checkRepository"
							, "/downloadRepository"
							, "/metadataRepository"
							);
		
//		registry.addInterceptor(new ExampleInterceptor())
//		.order(2) .addPathPatterns("/**") 
//		.excludePathPatterns("/css/**" , "/*.ico" , "/error"); 
	} 
	
	// 외부 파일 경로 추가
	 @Override
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
	      registry.addResourceHandler("/upload/**")
	            //.addResourceLocations("file:///data/upload/");	//리눅스 root(data)에서 시작하는 폴더 경로
	      		.addResourceLocations("file:///root/upload/");	//진흥청 root에서 시작하는 폴더 경로
	 }
}