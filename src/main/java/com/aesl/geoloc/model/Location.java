package com.aesl.geoloc.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "location")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String longitude;
    private String latitude;



}
