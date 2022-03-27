package com.taller1SM.service;
import com.taller1SM.model.prod.Location;

public interface LocationService {
	
public void saveLocation(Location location);
public void editLocation(Location location,Integer id);
}
