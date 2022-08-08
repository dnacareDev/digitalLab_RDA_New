package com.digitalLab.Controller;

import com.digitalLab.Entity.Plate;
import com.digitalLab.Service.PlateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PlateController {

    @Autowired
    private PlateService plateService;

    @GetMapping("/plate/{plate_id}")
    @ResponseBody()
    public Plate getPlate(@PathVariable int plate_id){
        return plateService.findPlateById(plate_id);
    }
}
