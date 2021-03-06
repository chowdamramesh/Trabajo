/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2016-10-17 16:43:55 UTC)
 * on 2016-10-28 at 06:02:40 UTC 
 * Modify at your own risk.
 */

package application.backend.myApi.model;

/**
 * Model definition for ProjectDetails.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the myApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class ProjectDetails extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String desc;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime endDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long projId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String projName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime startDate;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDesc() {
    return desc;
  }

  /**
   * @param desc desc or {@code null} for none
   */
  public ProjectDetails setDesc(java.lang.String desc) {
    this.desc = desc;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getEndDate() {
    return endDate;
  }

  /**
   * @param endDate endDate or {@code null} for none
   */
  public ProjectDetails setEndDate(com.google.api.client.util.DateTime endDate) {
    this.endDate = endDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getProjId() {
    return projId;
  }

  /**
   * @param projId projId or {@code null} for none
   */
  public ProjectDetails setProjId(java.lang.Long projId) {
    this.projId = projId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getProjName() {
    return projName;
  }

  /**
   * @param projName projName or {@code null} for none
   */
  public ProjectDetails setProjName(java.lang.String projName) {
    this.projName = projName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getStartDate() {
    return startDate;
  }

  /**
   * @param startDate startDate or {@code null} for none
   */
  public ProjectDetails setStartDate(com.google.api.client.util.DateTime startDate) {
    this.startDate = startDate;
    return this;
  }

  @Override
  public ProjectDetails set(String fieldName, Object value) {
    return (ProjectDetails) super.set(fieldName, value);
  }

  @Override
  public ProjectDetails clone() {
    return (ProjectDetails) super.clone();
  }

}
