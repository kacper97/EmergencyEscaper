package org.wit.emergencyescape.models;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// Not used in the end due to security reasons

public class LocationModel implements Serializable {
    public Long id;
    public String fbId;
    public double lng;
    public double lat;
    public float zoom;
}
