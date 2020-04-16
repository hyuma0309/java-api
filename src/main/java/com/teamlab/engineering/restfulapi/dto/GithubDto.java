package com.teamlab.engineering.restfulapi.dto;

import org.springframework.social.github.api.GitHubUserProfile;

/**
 * GithubユーザープロフィールDTO
 *
 * @author asada
 */
public class GithubDto {
  private Long id;
  private String name;
  private String email;
  private String blog;
  private String company;
  private String location;
  private String profileImageUrl;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getBlog() {
    return blog;
  }

  public void setBlog(String blog) {
    this.blog = blog;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public void setProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }

  public GithubDto(GitHubUserProfile gitHubUserProfile) {
    id = gitHubUserProfile.getId();
    name = gitHubUserProfile.getName();
    email = gitHubUserProfile.getEmail();
    blog = gitHubUserProfile.getBlog();
    company = gitHubUserProfile.getCompany();
    location = gitHubUserProfile.getLocation();
    profileImageUrl = gitHubUserProfile.getProfileImageUrl();
  }
}
