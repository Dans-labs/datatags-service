package nl.knaw.dans.datatags.service.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Objects;

/**
 * UserData
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-02-20T09:42:15.612+01:00[Europe/Amsterdam]")
public class UserData  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("objectPid")
  private String objectPid = null;

  @JsonProperty("apiKey")
  private String apiKey = null;

  @JsonProperty("sourceUrl")
  private String sourceUrl = null;

  public UserData objectPid(String objectPid) {
    this.objectPid = objectPid;
    return this;
  }

  /**
   * The pid of the source object.
   * @return objectPid
  **/
  @ApiModelProperty(example = "doi:123/abcdefg", value = "The pid of the source object.")
  
    public String getObjectPid() {
    return objectPid;
  }

  public void setObjectPid(String objectPid) {
    this.objectPid = objectPid;
  }

  public UserData apiKey(String apiKey) {
    this.apiKey = apiKey;
    return this;
  }

  /**
   * Get apiKey
   * @return apiKey
  **/
  @ApiModelProperty(value = "")
  
    public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public UserData sourceUrl(String sourceUrl) {
    this.sourceUrl = sourceUrl;
    return this;
  }

  /**
   * Get sourceUrl
   * @return sourceUrl
  **/
  @ApiModelProperty(example = "https://dataverse.nl", value = "")
  
    public String getSourceUrl() {
    return sourceUrl;
  }

  public void setSourceUrl(String sourceUrl) {
    this.sourceUrl = sourceUrl;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserData userData = (UserData) o;
    return Objects.equals(this.objectPid, userData.objectPid) &&
        Objects.equals(this.apiKey, userData.apiKey) &&
        Objects.equals(this.sourceUrl, userData.sourceUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(objectPid, apiKey, sourceUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserData {\n");
    
    sb.append("    objectPid: ").append(toIndentedString(objectPid)).append("\n");
    sb.append("    apiKey: ").append(toIndentedString(apiKey)).append("\n");
    sb.append("    sourceUrl: ").append(toIndentedString(sourceUrl)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
