package de.hsrm.mi.web.projekt.geo;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GeoServiceImpl implements GeoService {

    Logger logger = LoggerFactory.getLogger(GeoServiceImpl.class);
    
    @Override
    public List<AdressInfo> findeAdressInfo(String adresse) {
        WebClient webClient = WebClient.create(
            "https://nominatim.openstreetmap.org"
            );
        List<AdressInfo> antwort = webClient.get()
                            .uri(uriBuilder -> uriBuilder
                                .path("/search")
                                .queryParam("q", adresse)
                                .queryParam("format", "json")
                                .build())
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToFlux(AdressInfo.class)
                            .collectList()
                            .block();
        
        if (antwort == null || antwort.isEmpty()) {
            return Collections.emptyList();
        } else {
            return antwort;
        }
    }

    @Override
    public double abstandInKm(AdressInfo adr1, AdressInfo adr2) {
        final double ERDRADIUS_KM = 6370;
        var grad2rad = Math.PI / 180.0;
        var phi_a = adr1.lat() * grad2rad;
        var lambda_a = adr1.lon() * grad2rad;
        var phi_b = adr2.lat() * grad2rad;
        var lambda_b = adr2.lon() * grad2rad;
        var zeta = Math.acos(Math.sin(phi_a) * Math.sin(phi_b)
        + Math.cos(phi_a)*Math.cos(phi_b)*Math.cos(lambda_b-lambda_a));
        return zeta * ERDRADIUS_KM;
    }

    @Override
    public double abstandKmNachGrad(double abstand) {
        return abstand/111.139;
    }
    
}
