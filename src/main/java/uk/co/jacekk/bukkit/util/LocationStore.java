package uk.co.jacekk.bukkit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Location;

public class LocationStore {
	
	private HashMap<String, Location> locations;
	private File storageFile;
	
	public LocationStore(File storageFile){
		this.locations = new HashMap<String, Location>();
		this.storageFile = storageFile;
		
		if (this.storageFile.exists() == false){
			try{
				this.storageFile.createNewFile();
				
				ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(this.storageFile));
				
				stream.writeObject(this.locations);
				stream.flush();
				stream.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void load(){
		try{
			this.locations.clear();
			
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream(this.storageFile));
			
			for (Entry<String, LocationStorable> location : ((HashMap<String, LocationStorable>) stream.readObject()).entrySet()){
				this.locations.put(location.getKey(), location.getValue().toLocation());
			}
			
			stream.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void save(){
		try{
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(this.storageFile));
			
			HashMap<String, LocationStorable> write = new HashMap<String, LocationStorable>();
			
			for (Entry<String, Location> location : this.locations.entrySet()){
				write.put(location.getKey(), new LocationStorable(location.getValue()));
			}
			
			stream.writeObject(write);
			
			stream.flush();
			stream.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean contains(String key){
		return this.locations.containsKey(key);
	}
	
	public boolean contains(Location location){
		return this.locations.containsValue(location);
	}
	
	public Location get(String key){
		return this.locations.get(key);
	}
	
	public void add(String key, Location location){
		this.locations.put(key, location);
		this.save();
	}
	
	public void remove(String key){
		this.locations.remove(key);
		this.save();
	}
	
	public Set<String> getKeys(){
		return this.locations.keySet();
	}
	
}
