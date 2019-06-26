package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;


@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Customer {

    @Id
    public String id;

    public String firstName;
    public String lastName;
}
