package com.lyhorng.pedssystem.dto.location;

import java.util.List;

import com.lyhorng.pedssystem.model.property.location.Commune;
import com.lyhorng.pedssystem.model.property.location.District;
import com.lyhorng.pedssystem.model.property.location.Province;
import com.lyhorng.pedssystem.model.property.location.Village;

public class LocationHierarchyDTO {
    private List<Province> provinces;
    private List<District> districts;
    private List<Commune> communes;
    private List<Village> villages;

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public List<Commune> getCommunes() {
        return communes;
    }

    public void setCommunes(List<Commune> communes) {
        this.communes = communes;
    }

    public List<Village> getVillages() {
        return villages;
    }

    public void setVillages(List<Village> villages) {
        this.villages = villages;
    }
}
