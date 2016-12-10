package abd.p1;

/**
 * Stores 2 Double values for latitude and longitude.  
 */
public class Coordinate
{
	private Double latitude, longitude; 

	public Coordinate(Double latitude, Double longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(Double latitude)
	{
		this.latitude = latitude;
	}

	public Double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(Double longitude)
	{
		this.longitude = longitude;
	}
}