package com.sintec.MCPServer.service;

import com.sintec.MCPServer.model.Gadget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class GadgetsService {
    private static final Logger log = LoggerFactory.getLogger(GadgetsService.class);
    private static final String BASE_URL = "https://api.restful-api.dev";

    private final RestClient restClient;
    private final RestTemplate restTemplate = new RestTemplate();

    public GadgetsService() {
        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @Tool(name="getAllGadgets", description = "gets all the gadgets available in the repository")
    public String getAllGadgets() {
        log.info("Calling get all gadgets end point..");
        var response = "No gadgets found..";
        List<Gadget> gadgets = restClient.get().uri("/objects").retrieve().body(new ParameterizedTypeReference<List<Gadget>>() {});

        if (gadgets != null) {
              response = gadgets.stream()
                      .map(gadget -> String.format("""
                              Gadget id: %s
                              Gadget name: %s
                              Gadget capacity: %s
                              Gadget price: %s
                              """,
                              gadget.getId(),
                              gadget.getName(),
                              gadget.getData() != null ? gadget.getData().get("capacity") : "No value",
                              gadget.getData() != null ? gadget.getData().get("price") : "No value"))
                      .collect(Collectors.joining("\n"));
        }

        log.info("fetched gadgets: {}", response);
        return response;
    }

    @Tool(name = "getSpecificGadget", description = "gets a specific gadget based on the id")
    String getSpecificGadget(String id) {
        log.info("Calling get specific gadget end point..");
        var response = "No gadget found..";
        Gadget gadget = restClient.get().uri("/objects/"+ id).retrieve().body(new ParameterizedTypeReference<>() {});
        if (gadget != null) {
            response =  String.format("""
                              Gadget id: %s
                              Gadget name: %s
                              Gadget capacity: %s
                              Gadget price: %s
                              """,
                    gadget.getId(),
                    gadget.getName(),
                    gadget.getData() != null ? gadget.getData().get("capacity") : "No value",
                    gadget.getData() != null ? gadget.getData().get("price") : "No value"
            );
        }

        log.info("fetched gadget: {}", response);
        return response;
    }

    @Tool(name = "addGadget", description = "adds a new Gadget object via REST API")
    public String addGadget(Gadget gadget) {
        log.info("Calling add Gadget end point..");
        Map<String, Object> response = restClient.post()
                .uri("/objects")
                .contentType(MediaType.APPLICATION_JSON)
                .body(gadget)
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, Object>>() {});

        String id = (String) response.get("id");

        log.info("gadget added with id: {}", id);
        return "Added gadget to the repository with id=" + id;
    }

    @Tool(name = "updateGadget", description = "updates an existing Gadget object by id via REST API")
    public String updateGadget(String id, Gadget gadget) {
        log.info("Calling updateGadget end point..");
        restClient.put()
                .uri("/objects/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(gadget)
                .retrieve()
                .toBodilessEntity();

        log.info("gadget with id: {} updated..", id);
        return "Gadget with id=" + id + " successfully updated!";
    }
}
