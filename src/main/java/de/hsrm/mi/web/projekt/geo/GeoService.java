package de.hsrm.mi.web.projekt.geo;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface GeoService {
    List<AdressInfo> findeAdressInfo(String adresse);
    double abstandInKm(AdressInfo adr1, AdressInfo adr2);
    double abstandKmNachGrad(double abstand);
}
