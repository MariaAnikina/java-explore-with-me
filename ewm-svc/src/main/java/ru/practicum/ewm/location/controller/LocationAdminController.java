package ru.practicum.ewm.location.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.location.model.dto.LocationFullDto;
import ru.practicum.ewm.location.model.dto.LocationUpdateRequest;
import ru.practicum.ewm.location.model.dto.NewLocationDto;
import ru.practicum.ewm.location.service.LocationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/locations")
@AllArgsConstructor
public class LocationAdminController {
	private LocationService locationService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public LocationFullDto addLocation(@Valid @RequestBody NewLocationDto newLocationDto) {
		return locationService.saveLocation(newLocationDto);
	}

	@DeleteMapping("/{locationId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteLocation(@PathVariable Integer locationId) {
		locationService.deleteLocation(locationId);
	}

	@PatchMapping("/{locationId}")
	public LocationFullDto updateLocation(
			@PathVariable Integer locationId,
			@Valid @RequestBody LocationUpdateRequest request
	) {
		request.setId(locationId);
		return locationService.updateLocation(request);
	}
}