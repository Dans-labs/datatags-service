package nl.knaw.dans.datatags.service.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DataTagSchemaNode
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-02-20T09:42:15.612+01:00[Europe/Amsterdam]")
public class DataTagSchemaNode  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("nodeName")
  private String nodeName = null;

  @JsonProperty("title")
  private String title = null;

  @JsonProperty("subTitle")
  private String subTitle = null;

  @JsonProperty("contentText")
  private String contentText = null;

  @JsonProperty("question")
  private String question = null;

  @JsonProperty("answers")
  @Valid
  private List<Action> answers = null;

  @JsonProperty("qaList")
  @Valid
  private List<QA> qaList = null;

  public DataTagSchemaNode nodeName(String nodeName) {
    this.nodeName = nodeName;
    return this;
  }

  /**
   * Get nodeName
   * @return nodeName
  **/
  @ApiModelProperty(value = "")
  
    public String getNodeName() {
    return nodeName;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }

  public DataTagSchemaNode title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
  **/
  @ApiModelProperty(value = "")
  
    public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public DataTagSchemaNode subTitle(String subTitle) {
    this.subTitle = subTitle;
    return this;
  }

  /**
   * Get subTitle
   * @return subTitle
  **/
  @ApiModelProperty(value = "")
  
    public String getSubTitle() {
    return subTitle;
  }

  public void setSubTitle(String subTitle) {
    this.subTitle = subTitle;
  }

  public DataTagSchemaNode contentText(String contentText) {
    this.contentText = contentText;
    return this;
  }

  /**
   * Get contentText
   * @return contentText
  **/
  @ApiModelProperty(value = "")
  
    public String getContentText() {
    return contentText;
  }

  public void setContentText(String contentText) {
    this.contentText = contentText;
  }

  public DataTagSchemaNode question(String question) {
    this.question = question;
    return this;
  }

  /**
   * Get question
   * @return question
  **/
  @ApiModelProperty(value = "")
  
    public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public DataTagSchemaNode answers(List<Action> answers) {
    this.answers = answers;
    return this;
  }

  public DataTagSchemaNode addAnswersItem(Action answersItem) {
    if (this.answers == null) {
      this.answers = new ArrayList<>();
    }
    this.answers.add(answersItem);
    return this;
  }

  /**
   * Get answers
   * @return answers
  **/
  @ApiModelProperty(value = "")
      @Valid
    public List<Action> getAnswers() {
    return answers;
  }

  public void setAnswers(List<Action> answers) {
    this.answers = answers;
  }

  public DataTagSchemaNode qaList(List<QA> qaList) {
    this.qaList = qaList;
    return this;
  }

  public DataTagSchemaNode addQaListItem(QA qaListItem) {
    if (this.qaList == null) {
      this.qaList = new ArrayList<>();
    }
    this.qaList.add(qaListItem);
    return this;
  }

  /**
   * Get qaList
   * @return qaList
  **/
  @ApiModelProperty(value = "")
      @Valid
    public List<QA> getQaList() {
    return qaList;
  }

  public void setQaList(List<QA> qaList) {
    this.qaList = qaList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DataTagSchemaNode dataTagSchemaNode = (DataTagSchemaNode) o;
    return Objects.equals(this.nodeName, dataTagSchemaNode.nodeName) &&
        Objects.equals(this.title, dataTagSchemaNode.title) &&
        Objects.equals(this.subTitle, dataTagSchemaNode.subTitle) &&
        Objects.equals(this.contentText, dataTagSchemaNode.contentText) &&
        Objects.equals(this.question, dataTagSchemaNode.question) &&
        Objects.equals(this.answers, dataTagSchemaNode.answers) &&
        Objects.equals(this.qaList, dataTagSchemaNode.qaList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nodeName, title, subTitle, contentText, question, answers, qaList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DataTagSchemaNode {\n");
    
    sb.append("    nodeName: ").append(toIndentedString(nodeName)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    subTitle: ").append(toIndentedString(subTitle)).append("\n");
    sb.append("    contentText: ").append(toIndentedString(contentText)).append("\n");
    sb.append("    question: ").append(toIndentedString(question)).append("\n");
    sb.append("    answers: ").append(toIndentedString(answers)).append("\n");
    sb.append("    qaList: ").append(toIndentedString(qaList)).append("\n");
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
