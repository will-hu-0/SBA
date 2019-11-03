package com.sba.auth.service;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sba.auth.client.AccountServiceClient;
import com.sba.auth.model.SbaUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private AccountServiceClient accountclient;

  @Autowired
	private Gson gson;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    try {
      ResponseEntity<Object> result = accountclient.getUser(username);
      JsonObject accountresult = getResult(result);

      if (accountresult.get("code").getAsInt() == 404) {
        throw new UsernameNotFoundException("用户 " + username + " 不存在");
      } else {
        SbaUser user = getAccount(accountresult);
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_" + user.getRole());
        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
      }
    } catch (Exception ex) {
      throw new UsernameNotFoundException(ex.getMessage());
    }
  }

  public JsonObject getResult(ResponseEntity<Object> result) {
    String jsonResultStr = gson.toJson(result.getBody());
    JsonParser parser = new JsonParser();
    JsonObject object = (JsonObject) parser.parse(jsonResultStr);
    return object;
  }

  public SbaUser getAccount(JsonObject result) {
    SbaUser user = gson.fromJson(result.get("data").toString(), SbaUser.class);
    return user;
  }
}
