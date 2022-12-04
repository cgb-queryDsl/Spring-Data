package com.nhnacademy.certificate.controller.web;

import com.nhnacademy.certificate.dto.resident.ResidentResponse;
import com.nhnacademy.certificate.entity.Resident;
import com.nhnacademy.certificate.service.resident.ResidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ResidentController {

    private final ResidentService residentService;

    @GetMapping
    public String index(@PageableDefault(size = 5) Pageable pageable, ModelMap modelMap) {
        Page<Resident> residentPage = residentService.getResidents(pageable);
        List<Resident> residents = residentPage.getContent();
        int totalPages = residentPage.getTotalPages();

        modelMap.addAttribute("residents", residents);
        modelMap.addAttribute("totalPages", totalPages);

        return "index";
    }

    @GetMapping("/web/residents/{residentSerialNumber}")
    public String getCertificationMain(@PathVariable Long residentSerialNumber, ModelMap modelMap) {
        ResidentResponse resident = residentService.findBySerialNumber(residentSerialNumber);
        modelMap.addAttribute("resident", resident);
        return "certificationMain";
    }
}
