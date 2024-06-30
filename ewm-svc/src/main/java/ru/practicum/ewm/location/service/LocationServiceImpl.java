package ru.practicum.ewm.location.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.model.Coordinate;
import ru.practicum.ewm.exception.LocationAlreadyExistsException;
import ru.practicum.ewm.exception.LocationNotFoundException;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.location.model.dto.*;
import ru.practicum.ewm.location.storage.LocationRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.location.model.dto.LocationMapper.locationFromNewLocationDto;
import static ru.practicum.ewm.location.model.dto.LocationMapper.locationToFullDto;

@AllArgsConstructor
@Service
@Slf4j
public class LocationServiceImpl implements LocationService {
	private LocationRepository locationRepository;

	@Override
	@Transactional
	public LocationFullDto saveLocation(NewLocationDto newLocationDto) {
		try {
			Location location = locationRepository.save(locationFromNewLocationDto(newLocationDto));
			log.info("Добавлена новая локация {}", location);
			return locationToFullDto(location);
		} catch (DataIntegrityViolationException e) {
			throw new LocationAlreadyExistsException(
					"Локация с названием '" + newLocationDto.getName() + "' уже существует"
			);
		}
	}

	@Override
	@Transactional
	public void deleteLocation(int locationId) {
		if (!locationRepository.existsById(locationId))
			throw new LocationNotFoundException("Локация с id=" + locationId + " не найдена");
		locationRepository.deleteById(locationId);
		log.info("Удалена локация с id={}", locationId);
	}

	@Override
	@Transactional
	public LocationFullDto updateLocation(LocationUpdateRequest request) {
		int locationId = request.getId();
		Location location = getLocation(locationId);
		String name = request.getName();
		String description = request.getDescription();
		Coordinate coordinate = request.getCoordinate();
		Double radius = request.getRadius();
		if (name != null) {
			if (locationRepository.existsByNameAndIdNot(name, locationId))
				throw new LocationAlreadyExistsException("Локация с названием '" + name + "' уже существует");
			location.setName(name);
		}
		if (coordinate != null) {
			location.setLon(coordinate.getLon());
			location.setLat(coordinate.getLat());
		}
		if (description != null) location.setDescription(description);
		if (radius != null) location.setRadius(radius);
		locationRepository.save(location);
		log.info("Обновлена локация {}", location);
		return locationToFullDto(location);
	}

	@Override
	@Transactional(readOnly = true)
	public LocationFullDto getLocationById(int locationId) {
		Location location = getLocation(locationId);
		log.info("Запрошена локация {}", location);
		return locationToFullDto(location);
	}

	@Override
	@Transactional(readOnly = true)
	public List<LocationShortDto> getLocations(String text, int from, int size) {
		log.info("Запрошен список локаций");
		int page = from / size;
		Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
		return locationRepository.findLocations(text, pageable).stream()
				.map(LocationMapper::locationToShortDto)
				.collect(Collectors.toList());
	}

	private Location getLocation(int locationId) {
		return locationRepository.findById(locationId)
				.orElseThrow(() -> new LocationNotFoundException("Локация с id=" + locationId + " не найдена"));
	}
}