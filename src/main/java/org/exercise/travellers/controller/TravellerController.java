package org.exercise.travellers.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exercise.travellers.dto.CreateTravellerDto;
import org.exercise.travellers.dto.TravellerDto;
import org.exercise.travellers.parser.TravellerParser;
import org.exercise.travellers.services.TravellersService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public TravellerDto addTraveller(@Valid @RequestBody CreateTravellerDto createTravellerDto) {
        log.info("Add traveller with: " + createTravellerDto.getFirstName());
        return TravellerParser.toDto(travellersService.addTraveller(createTravellerDto));
    }

    @PutMapping
    public TravellerDto updateTraveller(@Valid @RequestBody TravellerDto updateTravellerDto) {
        log.info("Update traveller with: " + updateTravellerDto.firstName());
        return TravellerParser.toDto(travellersService.updateTraveller(updateTravellerDto));
    }

    @DeleteMapping("/{id}")
    public void deleteTraveller(@PathVariable Long id) {
        log.info("Deleting traveller with id: " + id);
        travellersService.deleteTraveller(id);
    }
}
