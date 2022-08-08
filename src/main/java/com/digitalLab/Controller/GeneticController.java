package com.digitalLab.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digitalLab.Entity.Genetic;
import com.digitalLab.Service.GeneticService;

@Controller
public class GeneticController {

	@Autowired
	private GeneticService geneticService;
	
	@GetMapping("api/genetic/genetic_depth")
	@ResponseBody
	private List<Genetic> geneticDepthList(Genetic genetic){
		
		System.out.println("genetic : " + genetic);
		
		return geneticService.selectGeneticDepth(genetic);
	}
	
}
