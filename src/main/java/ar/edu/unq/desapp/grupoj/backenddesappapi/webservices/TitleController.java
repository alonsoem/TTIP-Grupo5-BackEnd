package ar.edu.unq.desapp.grupoj.backenddesappapi.webservices;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.titles.Title;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.TitleService;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.InverseReq;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.TitleDTO;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.NonExistentTitleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins ="*")
@RestController
@EnableAutoConfiguration
public class TitleController {

    @Autowired
    private TitleService titleService;

    @GetMapping("/title")
    @Description("Full title list")
    public ResponseEntity<List<TitleDTO>> getAllTitles() {
        return ResponseEntity.ok(titleService.findAll()
                .stream()
                .map(i->TitleDTO.fromModel(i))
                .collect(Collectors.toList()));
    }

    @GetMapping("/title/{id}")
    @Description("Title by Id")
    public ResponseEntity<TitleDTO> getTitleById(@PathVariable(value = "id") Integer id) throws NonExistentTitleException {
        return ResponseEntity
                .ok(
                    TitleDTO.fromModel(titleService.getByTitleId(id))
                    );
    }

    @PostMapping("/title/inverse")
    @Description("Inverse Search")
    public ResponseEntity<List<TitleDTO>> getAllTitlesMatching(@RequestBody InverseReq req) {
        List<Title> titles = titleService.inverseQuery(req);
        return new ResponseEntity(
                titles.stream()
                .map(i -> TitleDTO.fromModel(i))
                .collect(Collectors.toList())
                , HttpStatus.OK);
    }

    @GetMapping("/title/cached/{id}")
    @Description("Cache search")
    public ResponseEntity<TitleDTO> getCachedTitleInfoById(@PathVariable(value = "id") Integer id) throws NonExistentTitleException {
        return ResponseEntity
                .ok(
                        TitleDTO.fromModel(titleService.getTitleInfo(id))
                );
    }



}


