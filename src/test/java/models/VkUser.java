package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VkUser {
    private String username;
    private String password;
    private String id;
    private String accessToken;
}
