package com.batchapp.dbcsv.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Setter;

@Entity(name = "DataUser")
@Table(name = "data_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class DataUser {

    @Id
    private Long id;
    private String name;
    private String email;
}

