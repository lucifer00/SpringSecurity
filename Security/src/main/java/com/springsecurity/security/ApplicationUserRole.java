package com.springsecurity.security;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.google.common.collect.Sets;
import lombok.Getter;

@Getter
public enum ApplicationUserRole {
  STUDENT(Sets.newHashSet()), 
  ADMIN(Sets.newHashSet(ApplicationUserPermission.STUDENT_READ,
                        ApplicationUserPermission.STUDENT_WRITE,
                        ApplicationUserPermission.COURSE_READ,
                        ApplicationUserPermission.COURSE_WRITE)),
  ADMINTRAINEE(Sets.newHashSet(ApplicationUserPermission.STUDENT_READ,
                               ApplicationUserPermission.COURSE_READ));

  private Set<ApplicationUserPermission> applicationUserPermissions;

  private ApplicationUserRole
      (Set<ApplicationUserPermission> applicationUserPermissions) {
    this.applicationUserPermissions = applicationUserPermissions;
  }

  public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
    Set<SimpleGrantedAuthority> permissions =
        getApplicationUserPermissions()
          .stream()
          .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
          .collect(Collectors.toSet());
    permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return permissions;
  }
}
