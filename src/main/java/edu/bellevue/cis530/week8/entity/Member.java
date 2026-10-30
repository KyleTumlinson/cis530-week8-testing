package edu.bellevue.cis530.week8.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // we want usernames to be at least 2 characters and max of 100 characters
    @Size(min = 2, max = 100)
    @NotBlank
    private String username;

    // we want our passwords to be at least 2 characters and max 255 characters
    @Size(min = 2, max = 255)
    @NotBlank
    private String password;

    // we want our role options to only be ROLE_USER and ROLE_ADMIN. Usually i would use a DB table.
    private String role;
    private boolean enabled = true;
}
