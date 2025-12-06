package ma.emsi.ingestionvideo.controller;

import lombok.RequiredArgsConstructor;
import ma.emsi.ingestionvideo.service.ReporterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reporter")
@RequiredArgsConstructor
public class ReporterController {

    final ReporterService service;

    @GetMapping("/test")
    public String test(){
        return "The ms is working!";
    }

}
