package com.young.in.youngk.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@Data
public class User {

    @Id
    private String id;

    private String userPublicId;
    private String password;
    private String name;
    private String birth;
    private String addressLine1;
    private String addressLine2;
    private String zipCode;
    private String phoneNumber;
}
