package com.aesl.geoloc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBodyDto {

    private String licence;
    private String osm_id;
    private String osm_type;
    private List<String> boundingbox;
    private String place_id;
    private String lat;
    private String lon;
    private String display_name;

    private Object address;

}
