package org.exercise.travellers.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exercise.travellers.parser.TravellerParser;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.exercise.travellers.dto.TravellerDto;
import org.exercise.travellers.services.TravellersService;

@RestController
@RequestMapping(path = "/v1/api/traveller")
@RequiredArgsConstructor
@Slf4j
public class TravellerController {

    private final TravellersService travellersService;

    @GetMapping()
    public TravellerDto getTraveller(@RequestParam String searchValue) {
        log.info("Search for the traveller with: " + searchValue);
        return TravellerParser.toDto(travellersService.getTraveller(searchValue));
    }

    @PostMapping
    public TravellerDto addTraveller(@RequestBody TravellerDto travellerDto) {
        log.info("Add traveller with: " + travellerDto.firstName());
        return travellersService.addTraveller(travellerDto);
    }

    @PutMapping
    public TravellerDto updateTraveller(@RequestBody TravellerDto travellerDto) {
        log.info("Update traveller with: " + travellerDto.firstName());
        return travellersService.updateTraveller(travellerDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTraveller(@PathVariable String id) {
        log.info("Delete traveller with id: " + id);
        travellersService.deleteTraveller(id);
    }
}
