package com.sba.account.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="account")
@Data
public class User {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "role")
  private String role;

  @Column(name = "status")
  private Boolean status;

}
