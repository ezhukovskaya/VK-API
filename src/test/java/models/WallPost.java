package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WallPost {
    @JsonProperty("owner_id")
    private String ownerId;
    @JsonProperty("message")
    private String message;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("v")
    private String version;
}
