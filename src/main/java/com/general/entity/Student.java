package com.general.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.general.constants.CollectionNames;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = CollectionNames.STUDENTS)
public class Student {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    @Field(name = "first_name")
    private String firstName;
    @Field(name = "last_name")
    private String lastName;
    @Field(name = "class")
    private Integer studentClass;
    @Field(name = "gender")
    private String gender;
    @Field(name = "dob")
    private String dateOfBirth;
    @Field(name = "school_joining_date")
    private String schoolJoiningDate;

}
