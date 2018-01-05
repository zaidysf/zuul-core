package com.uangteman.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uangteman.common.utils.rest.RestResponse;
import com.uangteman.core.model.AgeRangeDTO;
import com.uangteman.core.model.BankDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DummyController {

    public String getAgeRange(String jsonString, String ServiceUrl){

        ObjectMapper mapper = new ObjectMapper();
        RestResponse value = new RestResponse();
        try {

            value = mapper.readValue(jsonString, RestResponse.class);
            List<Map> ageRangeList = mapper.convertValue(value.getData(), List.class);
            String url = ServiceUrl + "/master-service/bank/search/";

            List<AgeRangeDTO> listOfAgeRange = ageRangeList.stream().map(ar -> getBank(ar, url)).collect(Collectors.toList());

            return mapper.writeValueAsString(RestResponse.ok(value.getResult_code(), value.getMessage(), listOfAgeRange));

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }

    private AgeRangeDTO getBank(Map ar, String url){

        ObjectMapper mapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        RestResponse response = restTemplate.getForObject(url+ar.get("id"), RestResponse.class);

        AgeRangeDTO arDTO = new AgeRangeDTO();

        BankDTO bankDTO = mapper.convertValue(response.getData(), BankDTO.class);

        arDTO.setId((Integer) ar.get("id"));
        arDTO.setName((String) ar.get("name"));
        arDTO.setState((String) ar.get("state"));
        arDTO.setCreatedAt((Long) ar.get("createdAt"));
        arDTO.setCreatedBy((Integer) ar.get("createdBy"));
        arDTO.setUpdatedAt((Long) ar.get("updatedAt"));
        arDTO.setUpdatedBy((Integer) ar.get("UpdatedBy"));
        arDTO.setBankName(bankDTO.getName());

        return arDTO;

    }

}
