package ru.practicum.ewm.location.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.model.dto.EventShortDto;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.location.model.dto.LocationFullDto;
import ru.practicum.ewm.location.model.dto.LocationShortDto;
import ru.practicum.ewm.location.service.LocationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/locations")
@AllArgsConstructor
public class LocationPublicController {
    private LocationService locationService;
    private EventService eventService;

    @GetMapping
    public List<LocationShortDto> getLocations(
            @RequestParam(required = false) String text,
            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
            @Positive @RequestParam(defaultValue = "10") Integer size
    ) {
        return locationService.getLocations(text, from, size);
    }

    @GetMapping("/{locationId}/events")
    public List<EventShortDto> getEventsInLocation(
            @PathVariable int locationId,
            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
            @Positive @RequestParam(defaultValue = "10") Integer size
    ) {
        return eventService.getEventsInLocation(locationId, from, size);
    }

    @GetMapping("/{locationId}")
    public LocationFullDto getLocationById(@PathVariable int locationId) {
        return locationService.getLocationById(locationId);
    }
}