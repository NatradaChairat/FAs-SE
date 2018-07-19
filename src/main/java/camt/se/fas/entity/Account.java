package camt.se.fas.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@JsonIgnoreType
public class Account {
    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;*/
    @JsonView/*(View.Login.class)*/
    String accountId;
    @JsonView/*(View.Login.class)*/
    String username;
    @JsonView/*(View.Login.class)*/
    String password;
    @JsonView/*(View.Login.class)*/
    String email;

    @JsonView/*(View.Login.class)*/
    String firstname;
    @JsonView/*(View.Login.class)*/
    String lastname;


    @JsonView/*(View.Login.class)*/
    String studentId;
    @JsonView/*(View.Login.class)*/
    String dateofbirth;
    @JsonView/*(View.Login.class)*/
    String phonenumber;
    @JsonView/*(View.Login.class)*/
    String status;

   /* String[] videos;
    String[] images;*/

    List<String> videos;
    List<String> images;

}
