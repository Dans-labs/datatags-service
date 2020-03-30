package nl.knaw.dans.datatags.service.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * DataTagSchemaProgress
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-02-20T09:42:15.612+01:00[Europe/Amsterdam]")
public class DataTagSchemaProgress  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("version")
  private String version = null;

  @JsonProperty("pidUrl")
  private String pidUrl = null;

  @JsonProperty("questionNodeName")
  private String questionNodeName = null;

  @JsonProperty("answer")
  private String answer = null;

  public DataTagSchemaProgress name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(example = "DANS DataTags", required = true, value = "")
      @NotNull

    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DataTagSchemaProgress version(String version) {
    this.version = version;
    return this;
  }

  /**
   * Get version
   * @return version
  **/
  @ApiModelProperty(example = "v1", value = "")
  
    public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public DataTagSchemaProgress pidUrl(String pidUrl) {
    this.pidUrl = pidUrl;
    return this;
  }

  /**
   * Get pidUrl
   * @return pidUrl
  **/
  @ApiModelProperty(example = "http://ergens.io/123", value = "")
  
    public String getPidUrl() {
    return pidUrl;
  }

  public void setPidUrl(String pidUrl) {
    this.pidUrl = pidUrl;
  }

  public DataTagSchemaProgress questionNodeName(String questionNodeName) {
    this.questionNodeName = questionNodeName;
    return this;
  }

  /**
   * Get questionNodeName
   * @return questionNodeName
  **/
  @ApiModelProperty(example = "dans-s2-personal-info-view", value = "")
  
    public String getQuestionNodeName() {
    return questionNodeName;
  }

  public void setQuestionNodeName(String questionNodeName) {
    this.questionNodeName = questionNodeName;
  }

  public DataTagSchemaProgress answer(String answer) {
    this.answer = answer;
    return this;
  }

  /**
   * Get answer
   * @return answer
  **/
  @ApiModelProperty(value = "")
  
    public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DataTagSchemaProgress dataTagSchemaProgress = (DataTagSchemaProgress) o;
    return Objects.equals(this.name, dataTagSchemaProgress.name) &&
        Objects.equals(this.version, dataTagSchemaProgress.version) &&
        Objects.equals(this.pidUrl, dataTagSchemaProgress.pidUrl) &&
        Objects.equals(this.questionNodeName, dataTagSchemaProgress.questionNodeName) &&
        Objects.equals(this.answer, dataTagSchemaProgress.answer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, version, pidUrl, questionNodeName, answer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DataTagSchemaProgress {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    pidUrl: ").append(toIndentedString(pidUrl)).append("\n");
    sb.append("    questionNodeName: ").append(toIndentedString(questionNodeName)).append("\n");
    sb.append("    answer: ").append(toIndentedString(answer)).append("\n");
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
